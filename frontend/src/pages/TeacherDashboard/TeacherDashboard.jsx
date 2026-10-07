import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import useTeacherDashboardData from '../../hooks/useTeacherDashboardData';
import { getErrorMessage } from '../../api/errors';
import Header from '../../components/layout/Header';
import Footer from '../../components/layout/Footer';
import FormModal from '../../components/ui/FormModal';
import '../../components/ui/ui.css';
import './TeacherDashboard.css';

const CAMPOS_CURSO = [
  { name: 'nombre', label: 'Nombre del curso', required: true },
  { name: 'descripcion', label: 'Descripción', type: 'textarea', rows: 4 },
  { name: 'activo', label: 'Curso activo (visible para los estudiantes)', type: 'checkbox' },
];

export default function TeacherDashboard() {
  const { user, logout } = useAuth();

  const {
    courses,
    totalCourses,
    totalStudents,
    loading,
    error,
    crearCurso,
    actualizarCurso,
    eliminarCurso,
  } = useTeacherDashboardData();

  const [search, setSearch] = useState('');
  const [dialogo, setDialogo] = useState(null); // { modo: 'crear' | 'editar', curso }
  const [aviso, setAviso] = useState(null);

  const navigate = useNavigate();

  const filteredCourses = courses.filter((course) =>
    course.nombre?.toLowerCase().includes(search.toLowerCase())
  );

  const entrarAlCurso = (courseId) => {
    navigate(`/curso/${courseId}`);
  };

  const guardarCurso = async (valores) => {
    if (dialogo.modo === 'crear') {
      await crearCurso(valores);
    } else {
      await actualizarCurso(dialogo.curso, valores);
    }
  };

  const handleEliminar = async (course) => {
    if (
      !window.confirm(
        `¿Eliminar el curso "${course.nombre}" con sus módulos y lecciones?`
      )
    ) {
      return;
    }

    setAviso(null);
    try {
      await eliminarCurso(course.id);
    } catch (err) {
      setAviso(
        getErrorMessage(
          err,
          'No se pudo eliminar el curso. Si ya tiene estudiantes inscritos o evaluaciones, desactívalo en lugar de eliminarlo.'
        )
      );
    }
  };

  return (
    <div className="teacher-dashboard">
      <Header
        name={user?.nombre}
        subtitle="Panel docente"
        role={user?.rol}
        onLogout={logout}
        search={{
          value: search,
          onChange: (e) => setSearch(e.target.value),
          placeholder: 'Buscar un curso...',
        }}
      />

      <main className="teacher-dashboard-main">
        <div className="teacher-section-header">
          <div>
            <span className="teacher-section-label">ENSEÑANZA</span>
            <h2>Mis cursos</h2>
          </div>

          <div className="teacher-header-right">
            <span className="teacher-course-count">
              {totalCourses} {totalCourses === 1 ? 'curso' : 'cursos'}
            </span>
            <button
              type="button"
              className="ui-btn ui-btn-primary"
              onClick={() => {
                setAviso(null);
                setDialogo({ modo: 'crear', curso: null });
              }}
            >
              + Nuevo curso
            </button>
          </div>
        </div>

        {!loading && !error && (
          <div className="teacher-summary">
            <div className="teacher-summary-card">
              <span className="teacher-summary-label">CURSOS</span>
              <strong>{totalCourses}</strong>
              <span className="teacher-summary-description">Cursos asignados</span>
            </div>

            <div className="teacher-summary-card">
              <span className="teacher-summary-label">ESTUDIANTES</span>
              <strong>{totalStudents}</strong>
              <span className="teacher-summary-description">
                Estudiantes inscritos
              </span>
            </div>
          </div>
        )}

        {aviso && (
          <div className="ui-error" role="alert" style={{ marginBottom: 20 }}>
            {aviso}
          </div>
        )}

        {loading && (
          <div className="teacher-dashboard-state">
            <div className="teacher-loader"></div>
            <p>Cargando tus cursos...</p>
          </div>
        )}

        {!loading && error && (
          <div className="teacher-dashboard-state teacher-error">
            <p>{error}</p>
          </div>
        )}

        {!loading && !error && filteredCourses.length === 0 && (
          <div className="teacher-dashboard-state">
            {search ? (
              <>
                <h3>No encontramos ese curso</h3>
                <p>Intenta buscar con otro nombre.</p>
              </>
            ) : (
              <>
                <h3>Aún no tienes cursos</h3>
                <p>Crea el primero con el botón "+ Nuevo curso".</p>
              </>
            )}
          </div>
        )}

        {!loading && !error && filteredCourses.length > 0 && (
          <div className="teacher-courses-grid">
            {filteredCourses.map((course) => (
              <article
                className="teacher-course-card"
                key={course.id}
                onClick={() => entrarAlCurso(course.id)}
              >
                <div className="teacher-course-cover">
                  <span>{course.activo ? 'CURSO' : 'CURSO INACTIVO'}</span>
                </div>

                <div className="teacher-course-content">
                  <h3>{course.nombre}</h3>
                  <p>{course.descripcion || 'Sin descripción disponible.'}</p>

                  <div className="teacher-course-meta">
                    <span>
                      {course.studentCount}{' '}
                      {course.studentCount === 1 ? 'estudiante' : 'estudiantes'}
                    </span>
                  </div>

                  <div className="ui-row-actions teacher-course-actions">
                    <button
                      type="button"
                      className="ui-btn ui-btn-ghost ui-btn-sm"
                      onClick={(e) => {
                        e.stopPropagation();
                        setAviso(null);
                        setDialogo({ modo: 'editar', curso: course });
                      }}
                    >
                      Editar
                    </button>
                    <button
                      type="button"
                      className="ui-btn ui-btn-danger ui-btn-sm"
                      onClick={(e) => {
                        e.stopPropagation();
                        handleEliminar(course);
                      }}
                    >
                      Eliminar
                    </button>
                  </div>

                  <button
                    type="button"
                    className="teacher-course-button"
                    onClick={(e) => {
                      e.stopPropagation();
                      entrarAlCurso(course.id);
                    }}
                  >
                    Entrar al curso
                    <span>→</span>
                  </button>
                </div>
              </article>
            ))}
          </div>
        )}
      </main>

      <Footer />

      <FormModal
        open={Boolean(dialogo)}
        title={dialogo?.modo === 'crear' ? 'Nuevo curso' : 'Editar curso'}
        fields={CAMPOS_CURSO}
        initialValues={
          dialogo?.modo === 'editar'
            ? dialogo.curso
            : { activo: true }
        }
        onSubmit={guardarCurso}
        onClose={() => setDialogo(null)}
      />
    </div>
  );
}
