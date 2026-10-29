import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import useTeacherDashboardData from '../../hooks/useTeacherDashboardData';
import { getErrorMessage } from '../../api/errors';
import Header from '../../components/layout/Header';
import Footer from '../../components/layout/Footer';
import FormModal from '../../components/ui/FormModal';
import Icon from '../../components/ui/Icon';
import CourseCover from '../../components/ui/CourseCover';
import StatCards from '../../components/ui/StatCards';
import { CAMPOS_DETALLE_CURSO } from '../../utils/cursos';
import { NOTA_MAXIMA, formatearNota } from '../../utils/notas';
import './TeacherDashboard.css';
import './TeacherDiseno.css';

const CAMPOS_CURSO = [
  { name: 'nombre', label: 'Nombre del curso', required: true },
  { name: 'descripcion', label: 'Descripción', type: 'textarea', rows: 4 },
  ...CAMPOS_DETALLE_CURSO,
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
    `${course.nombre} ${course.categoria ?? ''}`.toLowerCase().includes(search.toLowerCase())
  );

  const media = (valores) => {
    const v = valores.filter((x) => x !== null && x !== undefined);
    return v.length ? v.reduce((t, x) => t + x, 0) / v.length : null;
  };
  const progresoGeneral = media(courses.map((c) => c.progresoMedio));
  const notaGeneral = media(courses.map((c) => c.notaMedia));

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

        {!loading && !error && courses.length > 0 && (
          <StatCards
            items={[
              { label: 'Cursos', value: totalCourses, hint: `${courses.filter((c) => c.activo).length} activos`, icon: 'libro', tono: 'azul' },
              { label: 'Estudiantes', value: totalStudents, hint: 'inscritos en tus cursos', icon: 'usuarios', tono: 'violeta' },
              { label: 'Progreso medio', value: progresoGeneral === null ? '—' : `${Math.round(progresoGeneral)}%`, hint: 'de tus estudiantes', icon: 'grafica', tono: 'verde' },
              { label: 'Nota media', value: formatearNota(notaGeneral), hint: `sobre ${NOTA_MAXIMA}`, icon: 'premio', tono: 'ambar' },
            ]}
          />
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
                <CourseCover curso={course}>
                  {!course.activo && <span className="lh-chip lh-chip-ambar">Inactivo</span>}
                </CourseCover>

                <div className="teacher-course-content">
                  <h3>{course.nombre}</h3>
                  <p>{course.descripcion || 'Sin descripción disponible.'}</p>

                  <div className="teacher-course-cifras">
                    <div>
                      <span>Estudiantes</span>
                      <strong>{course.studentCount}</strong>
                    </div>
                    <div>
                      <span>Progreso</span>
                      <strong>{course.progresoMedio === null ? '—' : `${Math.round(course.progresoMedio)}%`}</strong>
                    </div>
                    <div>
                      <span>Nota media</span>
                      <strong>{formatearNota(course.notaMedia)}</strong>
                    </div>
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
                    Gestionar curso
                    <Icon nombre="flecha" size={16} />
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
