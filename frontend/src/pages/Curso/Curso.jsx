import { useMemo, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import useCursoData from '../../hooks/useCursoData';
import useMiAvance from '../../hooks/useMiAvance';
import Header from '../../components/layout/Header';
import Footer from '../../components/layout/Footer';
import Tabs from '../../components/ui/Tabs';
import Icon from '../../components/ui/Icon';
import ProgressRing from '../../components/ui/ProgressRing';
import { NOTA_MAXIMA, formatearNota, promedioPonderado } from '../../utils/notas';
import { NIVELES, categoriaDe, formatearMinutos } from '../../utils/cursos';
import ModulosView from './ModulosView';
import EvaluacionesView from './EvaluacionesView';
import EstudiantesView from './EstudiantesView';
import LeccionModal from './LeccionModal';
import './Curso.css';
import './CursoExtra.css';
import './CursoDiseno.css';

const VOLVER = {
  ESTUDIANTE: 'Mis clases',
  PROFESOR: 'Mis cursos',
  ADMINISTRADOR: 'Panel',
};

export default function Curso() {
  const { id } = useParams();
  const { user, role, logout } = useAuth();

  const {
    curso,
    modulos,
    lecciones,
    recursos,
    evaluaciones,
    preguntas,
    respuestas,
    loading,
    error,
    reload,
  } = useCursoData(id);

  const esEstudiante = role === 'ESTUDIANTE';

  // El administrador gestiona cualquier curso; el profesor, solo los suyos.
  const puedeGestionar =
    Boolean(curso) &&
    (role === 'ADMINISTRADOR' ||
      (role === 'PROFESOR' && curso.profesorId === user?.id));

  const avance = useMiAvance(id, lecciones, evaluaciones, esEstudiante);

  const [tab, setTab] = useState('contenido');
  const [avisoAvance, setAvisoAvance] = useState(null);
  const [leccionAbiertaId, setLeccionAbiertaId] = useState(null);
  const [guardandoLeccion, setGuardandoLeccion] = useState(false);

  const promedio = useMemo(
    () => promedioPonderado(evaluaciones, avance.calificaciones),
    [evaluaciones, avance.calificaciones]
  );

  // Lecciones en el orden del curso (módulo por módulo).
  const leccionesOrdenadas = useMemo(() => {
    const ordenModulo = new Map(modulos.map((m) => [m.id, m.orden]));
    return [...lecciones].sort(
      (a, b) =>
        (ordenModulo.get(a.moduloId) ?? 0) - (ordenModulo.get(b.moduloId) ?? 0) ||
        a.orden - b.orden
    );
  }, [modulos, lecciones]);

  const minutosTotales = lecciones.reduce((t, l) => t + (l.duracionMinutos || 0), 0);
  const presentadas = avance.calificaciones.length;
  const categoria = categoriaDe(curso);

  const handleToggleLeccion = async (leccionId) => {
    setAvisoAvance(null);
    setGuardandoLeccion(true);
    try {
      await avance.toggleLeccion(leccionId);
    } catch {
      setAvisoAvance('No se pudo guardar tu avance. Intenta de nuevo.');
    } finally {
      setGuardandoLeccion(false);
    }
  };

  const tabs = [
    { id: 'contenido', label: 'Contenido', count: modulos.length },
    { id: 'evaluaciones', label: 'Evaluaciones', count: evaluaciones.length },
    ...(puedeGestionar ? [{ id: 'estudiantes', label: 'Estudiantes' }] : []),
  ];

  const indiceAbierta = leccionesOrdenadas.findIndex((l) => l.id === leccionAbiertaId);
  const leccionAbierta = leccionesOrdenadas[indiceAbierta];
  const cargando = loading || (esEstudiante && avance.loading);

  return (
    <div className="curso-page">
      <Header
        name={user?.nombre}
        subtitle="Detalle del curso"
        role={user?.rol}
        onLogout={logout}
      />

      <section
        className={`curso-hero${esEstudiante ? ' con-panel' : ''}`}
        style={{ '--c1': categoria.c1, '--c2': categoria.c2 }}
      >
        <Icon nombre={categoria.icono} size={260} strokeWidth={0.8} className="curso-hero-art" />
        <div className="curso-hero-inner">
          <nav className="curso-migas" aria-label="Ruta">
            <Link to="/">
              <Icon nombre="flechaIzq" size={15} /> {VOLVER[role] ?? 'Inicio'}
            </Link>
            {curso && (
              <>
                <span aria-hidden="true">/</span>
                <span>{curso.categoria || 'Curso'}</span>
              </>
            )}
          </nav>

          {curso && (
            <>
              <div className="lh-chips curso-hero-chips">
                <span className="lh-chip lh-chip-glass">
                  <Icon nombre={categoria.icono} size={14} /> {curso.categoria || 'Curso'}
                </span>
                {curso.nivel && (
                  <span className="lh-chip lh-chip-glass">{NIVELES[curso.nivel] ?? curso.nivel}</span>
                )}
                {!curso.activo && <span className="lh-chip lh-chip-ambar">Inactivo</span>}
              </div>

              <h1>{curso.nombre}</h1>
              <p className="curso-hero-descripcion">
                {curso.descripcion || 'Sin descripción disponible.'}
              </p>

              <div className="curso-hero-meta">
                {curso.profesorNombre && (
                  <span><Icon nombre="usuario" size={16} /> {curso.profesorNombre}</span>
                )}
                {!loading && (
                  <>
                    <span><Icon nombre="capas" size={16} /> {modulos.length} módulos</span>
                    <span><Icon nombre="libro" size={16} /> {lecciones.length} lecciones</span>
                    <span><Icon nombre="quiz" size={16} /> {evaluaciones.length} evaluaciones</span>
                  </>
                )}
                {(curso.duracionHoras || minutosTotales > 0) && (
                  <span>
                    <Icon nombre="reloj" size={16} />{' '}
                    {curso.duracionHoras ? `${curso.duracionHoras} h de curso` : formatearMinutos(minutosTotales)}
                  </span>
                )}
              </div>
            </>
          )}
        </div>
      </section>

      <div className="curso-content">
        {cargando && (
          <div className="curso-state">
            <div className="loader"></div>
            <p>Cargando el curso...</p>
          </div>
        )}

        {!cargando && error && (
          <div className="curso-state error">
            <p>{error}</p>
          </div>
        )}

        {!cargando && !error && (
          <>
            {esEstudiante && (
              <section className="curso-avance-panel lh-aparecer">
                {avance.error ? (
                  <p className="ui-error">{avance.error}</p>
                ) : (
                  <>
                    <ProgressRing value={avance.porcentaje} size={96} grosor={9} />

                    <div className="curso-avance-cifras">
                      <div>
                        <span>Lecciones</span>
                        <strong>
                          {avance.leccionesCompletadas}
                          <small>/{lecciones.length}</small>
                        </strong>
                      </div>
                      <div>
                        <span>Evaluaciones</span>
                        <strong>
                          {presentadas}
                          <small>/{evaluaciones.length}</small>
                        </strong>
                      </div>
                      <div>
                        <span>Promedio</span>
                        <strong>
                          {formatearNota(promedio)}
                          <small>/{NOTA_MAXIMA}</small>
                        </strong>
                      </div>
                    </div>

                    <div className="curso-avance-accion">
                      {avance.siguienteLeccion ? (
                        <>
                          <span className="curso-avance-sig">Siguiente lección</span>
                          <strong>{avance.siguienteLeccion.titulo}</strong>
                          <button
                            type="button"
                            className="ui-btn ui-btn-primary"
                            onClick={() => {
                              setTab('contenido');
                              setLeccionAbiertaId(avance.siguienteLeccion.id);
                            }}
                          >
                            <Icon nombre="play" size={15} />
                            {avance.leccionesCompletadas === 0 ? 'Empezar curso' : 'Continuar'}
                          </button>
                        </>
                      ) : lecciones.length > 0 ? (
                        <>
                          <span className="curso-avance-sig">¡Felicitaciones!</span>
                          <strong>Completaste todas las lecciones</strong>
                          {presentadas < evaluaciones.length && (
                            <button type="button" className="ui-btn ui-btn-primary" onClick={() => setTab('evaluaciones')}>
                              <Icon nombre="quiz" size={15} /> Ir a las evaluaciones
                            </button>
                          )}
                        </>
                      ) : null}
                    </div>
                  </>
                )}
                {avisoAvance && (
                  <p className="ui-error curso-avance-error" role="alert">{avisoAvance}</p>
                )}
              </section>
            )}

            <Tabs tabs={tabs} active={tab} onChange={setTab} />

            {tab === 'contenido' && (
              <ModulosView
                cursoId={id}
                modulos={modulos}
                lecciones={lecciones}
                recursos={recursos}
                gestion={puedeGestionar}
                onChange={reload}
                completadas={esEstudiante ? avance.completadas : undefined}
                onToggleLeccion={esEstudiante ? handleToggleLeccion : undefined}
                onAbrirLeccion={setLeccionAbiertaId}
              />
            )}

            {tab === 'evaluaciones' && (
              <EvaluacionesView
                cursoId={id}
                evaluaciones={evaluaciones}
                preguntas={preguntas}
                respuestas={respuestas}
                gestion={puedeGestionar}
                onChange={reload}
                calificaciones={esEstudiante ? avance.calificaciones : []}
                onPresentar={esEstudiante ? avance.presentarEvaluacion : undefined}
              />
            )}

            {tab === 'estudiantes' && puedeGestionar && (
              <EstudiantesView cursoId={id} curso={curso} evaluaciones={evaluaciones} />
            )}
          </>
        )}
      </div>

      {leccionAbierta && (
        <LeccionModal
          key={leccionAbierta.id}
          leccion={leccionAbierta}
          modulo={modulos.find((m) => m.id === leccionAbierta.moduloId)}
          recursos={recursos.filter((r) => r.leccionId === leccionAbierta.id)}
          posicion={indiceAbierta + 1}
          total={leccionesOrdenadas.length}
          completada={avance.completadas.has(leccionAbierta.id)}
          onToggle={esEstudiante ? () => handleToggleLeccion(leccionAbierta.id) : undefined}
          guardando={guardandoLeccion}
          onAnterior={
            indiceAbierta > 0
              ? () => setLeccionAbiertaId(leccionesOrdenadas[indiceAbierta - 1].id)
              : undefined
          }
          onSiguiente={
            indiceAbierta < leccionesOrdenadas.length - 1
              ? () => setLeccionAbiertaId(leccionesOrdenadas[indiceAbierta + 1].id)
              : undefined
          }
          onClose={() => setLeccionAbiertaId(null)}
        />
      )}

      <Footer />
    </div>
  );
}
