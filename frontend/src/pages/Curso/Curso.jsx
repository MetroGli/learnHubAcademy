import { useMemo, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import useCursoData from '../../hooks/useCursoData';
import useMiAvance from '../../hooks/useMiAvance';
import Header from '../../components/layout/Header';
import Footer from '../../components/layout/Footer';
import ProgressBar from '../../components/ui/ProgressBar';
import Tabs from '../../components/ui/Tabs';
import { NOTA_MAXIMA, formatearNota, promedioPonderado } from '../../utils/notas';
import ModulosView from './ModulosView';
import EvaluacionesView from './EvaluacionesView';
import EstudiantesView from './EstudiantesView';
import '../../components/ui/ui.css';
import './Curso.css';
import './CursoExtra.css';

const VOLVER = {
  ESTUDIANTE: 'Volver a mis clases',
  PROFESOR: 'Volver a mis cursos',
  ADMINISTRADOR: 'Volver al panel',
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

  const promedio = useMemo(
    () => promedioPonderado(evaluaciones, avance.calificaciones),
    [evaluaciones, avance.calificaciones]
  );

  const handleToggleLeccion = async (leccionId) => {
    setAvisoAvance(null);
    try {
      await avance.toggleLeccion(leccionId);
    } catch {
      setAvisoAvance('No se pudo guardar tu avance. Intenta de nuevo.');
    }
  };

  const tabs = [
    { id: 'contenido', label: 'Contenido', count: modulos.length },
    { id: 'evaluaciones', label: 'Evaluaciones', count: evaluaciones.length },
    ...(puedeGestionar ? [{ id: 'estudiantes', label: 'Estudiantes' }] : []),
  ];

  return (
    <div className="curso-page">
      <Header
        name={user?.nombre}
        subtitle="Detalle del curso"
        role={user?.rol}
        onLogout={logout}
      />

      <div className="curso-content">
        <header className="curso-header">
          <Link to="/" className="curso-back-link">
            ← {VOLVER[role] ?? 'Volver'}
          </Link>

          {curso && (
            <>
              <h1>
                {curso.nombre}
                {!curso.activo && (
                  <span className="ui-badge ui-badge-off curso-badge-inactivo">
                    Inactivo
                  </span>
                )}
              </h1>
              <p className="curso-descripcion">
                {curso.descripcion || 'Sin descripción disponible.'}
              </p>
            </>
          )}
        </header>

        {loading && (
          <div className="curso-state">
            <div className="loader"></div>
            <p>Cargando el curso...</p>
          </div>
        )}

        {!loading && error && (
          <div className="curso-state error">
            <p>{error}</p>
          </div>
        )}

        {!loading && !error && (
          <>
            {esEstudiante && (
              <section className="curso-avance">
                {avance.error ? (
                  <p className="ui-error">{avance.error}</p>
                ) : (
                  <>
                    <ProgressBar value={avance.porcentaje} label="Mi progreso en el curso" />
                    <div className="curso-avance-datos">
                      <span>
                        Lecciones completadas:{' '}
                        <strong>
                          {avance.leccionesCompletadas}/{lecciones.length}
                        </strong>
                      </span>
                      <span>
                        Promedio ponderado:{' '}
                        <strong>{formatearNota(promedio)}</strong> / {NOTA_MAXIMA}
                      </span>
                    </div>
                  </>
                )}
                {avisoAvance && (
                  <p className="ui-error" role="alert">{avisoAvance}</p>
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
                onRegistrarNota={esEstudiante ? avance.registrarNota : undefined}
              />
            )}

            {tab === 'estudiantes' && puedeGestionar && (
              <EstudiantesView cursoId={id} evaluaciones={evaluaciones} />
            )}
          </>
        )}
      </div>

      <Footer />
    </div>
  );
}
