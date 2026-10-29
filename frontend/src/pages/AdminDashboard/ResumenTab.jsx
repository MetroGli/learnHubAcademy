import { useEffect, useMemo, useState } from 'react';
import {
  calificacionService,
  cursoService,
  inscripcionService,
  progresoService,
  usuarioService,
} from '../../api/resources';
import StatCards from '../../components/ui/StatCards';
import Icon from '../../components/ui/Icon';
import { categoriaDe } from '../../utils/cursos';
import { NOTA_MAXIMA, formatearNota } from '../../utils/notas';

const media = (valores) =>
  valores.length ? valores.reduce((t, v) => t + v, 0) / valores.length : null;

/** Vista general de la plataforma para el administrador. */
export default function ResumenTab({ onIr }) {
  const [datos, setDatos] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    Promise.all([
      usuarioService.listar(),
      cursoService.listar(),
      inscripcionService.listar(),
      progresoService.listar(),
      calificacionService.listar(),
    ])
      .then(([usuarios, cursos, inscripciones, progresos, calificaciones]) =>
        setDatos({ usuarios, cursos, inscripciones, progresos, calificaciones })
      )
      .catch(() => setError('No se pudo cargar el resumen.'));
  }, []);

  const resumen = useMemo(() => {
    if (!datos) return null;
    const { usuarios, cursos, inscripciones, progresos, calificaciones } = datos;
    const activas = inscripciones.filter((i) => i.activa);

    const porCurso = cursos
      .map((curso) => {
        const inscritos = activas.filter((i) => i.cursoId === curso.id);
        const ids = new Set(inscritos.map((i) => i.usuarioId));
        return {
          curso,
          inscritos: inscritos.length,
          progreso: media(
            progresos
              .filter((p) => p.cursoId === curso.id && ids.has(p.usuarioId))
              .map((p) => Number(p.porcentaje))
          ),
        };
      })
      .sort((a, b) => b.inscritos - a.inscritos);

    const recientes = [...inscripciones]
      .sort((a, b) => String(b.fechaInscripcion).localeCompare(String(a.fechaInscripcion)) || b.id - a.id)
      .slice(0, 6)
      .map((i) => ({
        ...i,
        usuario: usuarios.find((u) => u.id === i.usuarioId),
        curso: cursos.find((c) => c.id === i.cursoId),
      }));

    return {
      estudiantes: usuarios.filter((u) => u.rol === 'ESTUDIANTE').length,
      profesores: usuarios.filter((u) => u.rol === 'PROFESOR').length,
      inactivos: usuarios.filter((u) => !u.activo).length,
      cursosActivos: cursos.filter((c) => c.activo).length,
      totalCursos: cursos.length,
      inscripciones: activas.length,
      notaMedia: media(calificaciones.map((c) => Number(c.nota))),
      evaluacionesPresentadas: calificaciones.length,
      porCurso,
      recientes,
    };
  }, [datos]);

  if (error) return <div className="admin-state error"><p>{error}</p></div>;

  if (!resumen) {
    return (
      <div className="admin-state">
        <div className="loader"></div>
        <p>Cargando resumen...</p>
      </div>
    );
  }

  const maximo = Math.max(1, ...resumen.porCurso.map((f) => f.inscritos));
  const fecha = (iso) => {
    const [a, m, d] = String(iso).split('-');
    return d ? `${d}/${m}/${a}` : iso;
  };

  return (
    <div className="admin-resumen lh-aparecer">
      <StatCards
        items={[
          { label: 'Estudiantes', value: resumen.estudiantes, hint: `${resumen.profesores} profesores · ${resumen.inactivos} inactivos`, icon: 'usuarios', tono: 'azul' },
          { label: 'Cursos', value: resumen.cursosActivos, hint: `activos de ${resumen.totalCursos}`, icon: 'libro', tono: 'violeta' },
          { label: 'Inscripciones', value: resumen.inscripciones, hint: 'activas', icon: 'lista', tono: 'verde' },
          { label: 'Nota media', value: formatearNota(resumen.notaMedia), hint: `${resumen.evaluacionesPresentadas} evaluaciones · sobre ${NOTA_MAXIMA}`, icon: 'premio', tono: 'ambar' },
        ]}
      />

      <div className="admin-resumen-grid">
        <section className="admin-panel">
          <div className="admin-panel-head">
            <h3>Cursos por inscritos</h3>
            <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => onIr('cursos')}>
              Ver cursos
            </button>
          </div>
          <ul className="admin-barras">
            {resumen.porCurso.map(({ curso, inscritos, progreso }) => {
              const cat = categoriaDe(curso);
              return (
                <li key={curso.id}>
                  <div className="admin-barras-texto">
                    <span>
                      {curso.nombre}
                      {!curso.activo && <em> · inactivo</em>}
                    </span>
                    <strong>{inscritos}</strong>
                  </div>
                  <div className="admin-barras-pista">
                    <div
                      style={{
                        width: `${(inscritos / maximo) * 100}%`,
                        background: `linear-gradient(90deg, ${cat.c1}, ${cat.c2})`,
                      }}
                    />
                  </div>
                  {progreso !== null && (
                    <small>Progreso medio {Math.round(progreso)}%</small>
                  )}
                </li>
              );
            })}
          </ul>
        </section>

        <section className="admin-panel">
          <div className="admin-panel-head">
            <h3>Inscripciones recientes</h3>
            <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => onIr('inscripciones')}>
              Ver todas
            </button>
          </div>
          <ul className="admin-recientes">
            {resumen.recientes.map((i) => (
              <li key={i.id}>
                <span className="lh-avatar">
                  {`${i.usuario?.nombre?.[0] ?? '?'}${i.usuario?.apellido?.[0] ?? ''}`}
                </span>
                <div className="lh-persona-texto">
                  <strong>
                    {i.usuario ? `${i.usuario.nombre} ${i.usuario.apellido}` : `Usuario #${i.usuarioId}`}
                  </strong>
                  <span>
                    <Icon nombre="libro" size={12} /> {i.curso?.nombre ?? `Curso #${i.cursoId}`}
                  </span>
                </div>
                <time>{fecha(i.fechaInscripcion)}</time>
              </li>
            ))}
          </ul>
        </section>
      </div>
    </div>
  );
}
