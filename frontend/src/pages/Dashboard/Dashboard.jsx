import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import useDashboardData from '../../hooks/DashboardData';
import { getErrorMessage } from '../../api/errors';
import Header from '../../components/layout/Header';
import Footer from '../../components/layout/Footer';
import ProgressBar from '../../components/ui/ProgressBar';
import Tabs from '../../components/ui/Tabs';
import '../../components/ui/ui.css';
import './Dashboard.css';

export default function Dashboard() {
  const { user, logout } = useAuth();
  const { courses, catalogo, loading, error, inscribirse } = useDashboardData();

  const [tab, setTab] = useState('mis-clases');
  const [search, setSearch] = useState('');
  const [inscribiendoId, setInscribiendoId] = useState(null);
  const [aviso, setAviso] = useState(null); // { tipo: 'ok' | 'error', texto }

  const coincide = (course) =>
    course.nombre.toLowerCase().includes(search.toLowerCase());

  const filteredCourses = courses.filter(coincide);
  const filteredCatalogo = catalogo.filter(coincide);

  const lista = tab === 'mis-clases' ? filteredCourses : filteredCatalogo;
  const total = tab === 'mis-clases' ? courses.length : catalogo.length;

  const handleInscribirse = async (course) => {
    setAviso(null);
    setInscribiendoId(course.id);
    try {
      await inscribirse(course.id);
      setAviso({ tipo: 'ok', texto: `Te inscribiste en "${course.nombre}". Ya está en Mis clases.` });
    } catch (err) {
      setAviso({
        tipo: 'error',
        texto: getErrorMessage(err, 'No se pudo completar la inscripción. Intenta de nuevo.'),
      });
    } finally {
      setInscribiendoId(null);
    }
  };

  return (
    <div className="dashboard">
      <Header
        name={user?.nombre}
        subtitle="Panel de estudiante"
        role={user?.rol}
        onLogout={logout}
        search={{
          value: search,
          onChange: (e) => setSearch(e.target.value),
          placeholder: 'Buscar una clase...',
        }}
      />

      <main className="dashboard-main">
        <div className="section-header">
          <div>
            <span className="section-label">APRENDIZAJE</span>
            <h2>{tab === 'mis-clases' ? 'Mis clases' : 'Explorar cursos'}</h2>
          </div>

          <span className="course-count">
            {total} {total === 1 ? 'curso' : 'cursos'}
          </span>
        </div>

        <Tabs
          tabs={[
            { id: 'mis-clases', label: 'Mis clases', count: courses.length },
            { id: 'explorar', label: 'Explorar cursos', count: catalogo.length },
          ]}
          active={tab}
          onChange={(nuevo) => {
            setTab(nuevo);
            setAviso(null);
          }}
        />

        {aviso && (
          <div
            className={aviso.tipo === 'ok' ? 'ui-notice' : 'ui-error'}
            role="status"
            style={{ marginBottom: 20 }}
          >
            {aviso.texto}
          </div>
        )}

        {loading && (
          <div className="dashboard-state">
            <div className="loader"></div>
            <p>Cargando tus clases...</p>
          </div>
        )}

        {!loading && error && (
          <div className="dashboard-state error">
            <p>{error}</p>
          </div>
        )}

        {!loading && !error && lista.length === 0 && (
          <div className="dashboard-state">
            {search ? (
              <>
                <h3>No encontramos ese curso</h3>
                <p>Intenta buscar con otro nombre.</p>
              </>
            ) : tab === 'mis-clases' ? (
              <>
                <h3>Aún no tienes clases</h3>
                <p>
                  Inscríbete en un curso desde la pestaña "Explorar cursos" y
                  aparecerá aquí.
                </p>
              </>
            ) : (
              <>
                <h3>No hay más cursos disponibles</h3>
                <p>Ya estás inscrito en todos los cursos activos.</p>
              </>
            )}
          </div>
        )}

        {!loading && !error && lista.length > 0 && (
          <div className="courses-grid">
            {lista.map((course) => (
              <article className="course-card" key={course.id}>
                <div className="course-cover">
                  <span>CURSO</span>
                </div>

                <div className="course-content">
                  <h3>{course.nombre}</h3>

                  <p>{course.descripcion || 'Sin descripción disponible.'}</p>

                  {tab === 'mis-clases' ? (
                    <>
                      <div className="course-progress">
                        <ProgressBar value={course.progreso} label="Progreso" />
                      </div>

                      <Link to={`/curso/${course.id}`} className="course-button">
                        Entrar a la clase
                        <span>→</span>
                      </Link>
                    </>
                  ) : (
                    <button
                      type="button"
                      className="course-button course-button-action"
                      disabled={inscribiendoId === course.id}
                      onClick={() => handleInscribirse(course)}
                    >
                      {inscribiendoId === course.id ? 'Inscribiendo...' : 'Inscribirme'}
                      <span>+</span>
                    </button>
                  )}
                </div>
              </article>
            ))}
          </div>
        )}
      </main>

      <Footer />
    </div>
  );
}
