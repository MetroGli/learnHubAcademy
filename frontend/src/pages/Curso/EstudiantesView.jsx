import { useState } from 'react';
import FormModal from '../../components/ui/FormModal';
import ProgressBar from '../../components/ui/ProgressBar';
import useCursoEstudiantes from '../../hooks/useCursoEstudiantes';
import { getErrorMessage } from '../../api/errors';
import { NOTA_MAXIMA, formatearNota } from '../../utils/notas';
import Icon from '../../components/ui/Icon';
import StatCards from '../../components/ui/StatCards';

const iniciales = (nombre) =>
  nombre
    .split(' ')
    .filter(Boolean)
    .slice(0, 2)
    .map((p) => p[0])
    .join('')
    .toUpperCase();

/** Estudiantes del curso: inscripción, progreso y calificaciones (profesor/administrador). */
export default function EstudiantesView({ cursoId, curso, evaluaciones }) {
  const {
    filas,
    disponibles,
    usuariosDisponibles,
    loading,
    error,
    inscribir,
    cambiarEstado,
    guardarNota,
    notaDeEvaluacion,
  } = useCursoEstudiantes(cursoId, evaluaciones, true);

  const [inscribiendo, setInscribiendo] = useState(false);
  const [calificando, setCalificando] = useState(null); // { fila, evaluacion }
  const [aviso, setAviso] = useState(null);

  const activas = filas.filter((f) => f.inscripcion.activa);
  const conPromedio = activas.filter((f) => f.promedio !== null);
  const progresoMedio = activas.length
    ? activas.reduce((t, f) => t + Number(f.porcentaje || 0), 0) / activas.length
    : 0;
  const promedioCurso = conPromedio.length
    ? conPromedio.reduce((t, f) => t + f.promedio, 0) / conPromedio.length
    : null;
  const aprobados = conPromedio.filter((f) => f.promedio >= NOTA_MAXIMA * 0.6).length;

  // Descarga un CSV con progreso y notas (se abre en Excel o Google Sheets).
  const descargarCsv = () => {
    const celda = (v) => `"${String(v ?? '').replace(/"/g, '""')}"`;
    const encabezado = [
      'Estudiante',
      'Correo',
      'Progreso (%)',
      ...evaluaciones.map((e) => `${e.titulo} (${e.porcentaje}%)`),
      'Promedio',
      'Estado',
    ];
    const lineas = filas.map((f) => [
      f.nombre,
      f.usuario?.email ?? '',
      Math.round(f.porcentaje),
      ...evaluaciones.map((e) => formatearNota(notaDeEvaluacion(f.notas, e.id)).replace('—', '')),
      formatearNota(f.promedio).replace('—', ''),
      f.inscripcion.activa ? 'Activa' : 'De baja',
    ]);
    const csv = [encabezado, ...lineas].map((l) => l.map(celda).join(';')).join('\r\n');
    const blob = new Blob(['\ufeff' + csv], { type: 'text/csv;charset=utf-8' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `notas-${(curso?.nombre ?? 'curso').toLowerCase().replace(/[^a-z0-9áéíóúñ]+/gi, '-')}.csv`;
    a.click();
    URL.revokeObjectURL(url);
  };

  const handleEstado = async (inscripcion) => {
    setAviso(null);
    try {
      await cambiarEstado(inscripcion);
    } catch (err) {
      setAviso(getErrorMessage(err, 'No se pudo cambiar el estado de la inscripción.'));
    }
  };

  if (loading) {
    return (
      <div className="curso-state">
        <div className="loader"></div>
        <p>Cargando estudiantes...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="curso-state error">
        <p>{error}</p>
      </div>
    );
  }

  return (
    <section className="curso-estudiantes">
      <div className="ui-section-head">
        <h2>Estudiantes</h2>
        <div className="ui-row-actions">
          <button
            type="button"
            className="ui-btn ui-btn-ghost ui-btn-sm"
            disabled={filas.length === 0}
            onClick={descargarCsv}
          >
            <Icon nombre="descarga" size={15} /> Descargar notas (CSV)
          </button>
          <button
            type="button"
            className="ui-btn ui-btn-primary ui-btn-sm"
            disabled={!usuariosDisponibles || disponibles.length === 0}
            onClick={() => setInscribiendo(true)}
          >
            + Inscribir estudiante
          </button>
        </div>
      </div>

      {filas.length > 0 && (
        <StatCards
          items={[
            { label: 'Inscritos', value: activas.length, hint: `${filas.length - activas.length} dados de baja`, icon: 'usuarios', tono: 'azul' },
            { label: 'Progreso medio', value: `${Math.round(progresoMedio)}%`, hint: 'de las lecciones', icon: 'grafica', tono: 'violeta' },
            { label: 'Promedio del curso', value: formatearNota(promedioCurso), hint: `sobre ${NOTA_MAXIMA}`, icon: 'premio', tono: 'ambar' },
            { label: 'Aprobando', value: `${aprobados}/${conPromedio.length}`, hint: 'con nota ≥ 3', icon: 'checkCirculo', tono: 'verde' },
          ]}
        />
      )}

      {!usuariosDisponibles && (
        <p className="ui-notice ui-notice-warn">
          No se pudo consultar la lista de usuarios con tu cuenta, por eso los
          estudiantes aparecen como "Estudiante #id" y no se puede inscribir
          desde aquí.
        </p>
      )}

      {usuariosDisponibles && disponibles.length === 0 && filas.length > 0 && (
        <p className="curso-nota-pequena">
          Todos los estudiantes activos ya están inscritos en este curso.
        </p>
      )}

      {aviso && <div className="ui-error curso-aviso" role="alert">{aviso}</div>}

      {filas.length === 0 ? (
        <p className="ui-empty">Este curso todavía no tiene estudiantes inscritos.</p>
      ) : (
        <div className="ui-table-wrap">
          <table className="ui-table">
            <thead>
              <tr>
                <th>Estudiante</th>
                <th>Progreso</th>
                {evaluaciones.map((evaluacion) => (
                  <th key={evaluacion.id} title={evaluacion.titulo}>
                    {evaluacion.titulo.length > 18
                      ? `${evaluacion.titulo.slice(0, 17)}…`
                      : evaluacion.titulo}
                    <br />
                    <span className="curso-th-peso">{evaluacion.porcentaje}%</span>
                  </th>
                ))}
                <th>Promedio</th>
                <th>Estado</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filas.map((fila) => (
                <tr key={fila.inscripcion.id}>
                  <td>
                    <div className="lh-persona">
                      <span className="lh-avatar">{iniciales(fila.nombre)}</span>
                      <span className="lh-persona-texto">
                        <strong>{fila.nombre}</strong>
                        {fila.usuario && <span>{fila.usuario.email}</span>}
                      </span>
                    </div>
                  </td>
                  <td className="curso-celda-progreso">
                    <ProgressBar value={fila.porcentaje} label={null} />
                    <span className="curso-pct">{Math.round(fila.porcentaje)}%</span>
                  </td>

                  {evaluaciones.map((evaluacion) => (
                    <td key={evaluacion.id}>
                      <button
                        type="button"
                        className="curso-nota-btn"
                        title="Registrar o cambiar la nota"
                        onClick={() => setCalificando({ fila, evaluacion })}
                      >
                        {formatearNota(notaDeEvaluacion(fila.notas, evaluacion.id))}
                      </button>
                    </td>
                  ))}

                  <td>
                    <strong>{formatearNota(fila.promedio)}</strong>
                  </td>
                  <td>
                    <span
                      className={`ui-badge ${
                        fila.inscripcion.activa ? 'ui-badge-ok' : 'ui-badge-off'
                      }`}
                    >
                      {fila.inscripcion.activa ? 'Activa' : 'Inactiva'}
                    </span>
                  </td>
                  <td>
                    <button
                      type="button"
                      className={`ui-btn ui-btn-sm ${
                        fila.inscripcion.activa ? 'ui-btn-danger' : 'ui-btn-ghost'
                      }`}
                      onClick={() => handleEstado(fila.inscripcion)}
                    >
                      {fila.inscripcion.activa ? 'Dar de baja' : 'Reactivar'}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {evaluaciones.length === 0 && filas.length > 0 && (
        <p className="curso-nota-pequena">
          Cuando el curso tenga evaluaciones podrás registrar aquí las notas.
        </p>
      )}

      <FormModal
        open={inscribiendo}
        title="Inscribir estudiante"
        fields={[
          {
            name: 'usuarioId',
            label: 'Estudiante',
            type: 'select',
            numeric: true,
            required: true,
            options: disponibles.map((u) => ({
              value: u.id,
              label: `${u.nombre} ${u.apellido} (${u.email})`,
            })),
          },
        ]}
        submitLabel="Inscribir"
        onSubmit={({ usuarioId }) => inscribir(usuarioId)}
        onClose={() => setInscribiendo(false)}
      />

      <FormModal
        open={Boolean(calificando)}
        title={
          calificando
            ? `Calificar · ${calificando.fila.nombre}`
            : ''
        }
        note={calificando ? calificando.evaluacion.titulo : undefined}
        fields={[
          {
            name: 'nota',
            label: `Nota (0 a ${NOTA_MAXIMA})`,
            type: 'number',
            required: true,
            min: 0,
            max: NOTA_MAXIMA,
            step: 0.1,
          },
        ]}
        initialValues={
          calificando
            ? {
                nota: notaDeEvaluacion(
                  calificando.fila.notas,
                  calificando.evaluacion.id
                ),
              }
            : {}
        }
        onSubmit={({ nota }) =>
          guardarNota(
            calificando.fila.inscripcion.usuarioId,
            calificando.evaluacion.id,
            nota
          )
        }
        onClose={() => setCalificando(null)}
      />
    </section>
  );
}
