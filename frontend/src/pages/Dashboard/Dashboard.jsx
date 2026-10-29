import { useMemo, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import useDashboardData from '../../hooks/DashboardData';
import { getErrorMessage } from '../../api/errors';
import Header from '../../components/layout/Header';
import Footer from '../../components/layout/Footer';
import ProgressBar from '../../components/ui/ProgressBar';
import Tabs from '../../components/ui/Tabs';
import Icon from '../../components/ui/Icon';
import CourseCover from '../../components/ui/CourseCover';
import StatCards from '../../components/ui/StatCards';
import { CATEGORIAS } from '../../utils/cursos';
import { NOTA_MAXIMA, formatearNota } from '../../utils/notas';
import './Dashboard.css';
import './DashboardDiseno.css';

function saludo() {
  const hora = new Date().getHours();
  if (hora < 12) return 'Buenos días';
  if (hora < 19) return 'Buenas tardes';
  return 'Buenas noches';
}

export default function Dashboard() {
  const { user, logout } = useAuth();
  const { courses, catalogo, loading, error, inscribirse } = useDashboardData();

  const [tab, setTab] = useState('mis-clases');
  const [search, setSearch] = useState('');
  const [categoria, setCategoria] = useState(null);
  const [inscribiendoId, setInscribiendoId] = useState(null);
  const [aviso, setAviso] = useState(null); // { tipo: 'ok' | 'error', texto }

  const coincide = (course) =>
    `${course.nombre} ${course.categoria ?? ''} ${course.profesorNombre ?? ''}`
      .toLowerCase()
      .includes(search.toLowerCase()) &&
    (!categoria || course.categoria === categoria);

  const filteredCourses = courses.filter(coincide);
  const filteredCatalogo = catalogo.filter(coincide);

  const lista = tab === 'mis-clases' ? filteredCourses : filteredCatalogo;

  // Categorías presentes en la pestaña actual (para los filtros).
  const categoriasVisibles = useMemo(() => {
    const presentes = new Set((tab === 'mis-clases' ? courses : catalogo).map((c) => c.categoria));
    return CATEGORIAS.filter((c) => presentes.has(c.nombre));
  }, [tab, courses, catalogo]);

  const resumen = useMemo(() => {
    const conNota = courses.filter((c) => c.promedio !== null);
    return {
      progreso: courses.length
        ? Math.round(courses.reduce((t, c) => t + Number(c.progreso || 0), 0) / courses.length)
        : 0,
      completados: courses.filter((c) => c.progreso >= 100).length,
      promedio: conNota.length
        ? conNota.reduce((t, c) => t + c.promedio, 0) / conNota.length
        : null,
      pendientes: courses.reduce((t, c) => t + (c.totalEvaluaciones - c.presentadas), 0),
    };
  }, [courses]);

  // Curso para seguir: el que va más avanzado sin estar terminado.
  const continuar = useMemo(
    () =>
      [...courses]
        .filter((c) => c.progreso < 100)
        .sort((a, b) => b.progreso - a.progreso)[0] ?? null,
    [courses]
  );

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

  const cambiarTab = (nuevo) => {
    setTab(nuevo);
    setCategoria(null);
    setAviso(null);
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
          placeholder: 'Buscar un curso, tema o profesor...',
        }}
      />

      <main className="dashboard-main">
        <div className="lh-hero">
          <div>
            <span className="lh-hero-kicker">{saludo()}</span>
            <h1>Hola, {user?.nombre?.split(' ')[0]}</h1>
            <p>
              {courses.length > 0
                ? 'Sigue aprendiendo donde lo dejaste.'
                : 'Explora el catálogo e inscríbete en tu primer curso.'}
            </p>
          </div>
        </div>

        {!loading && !error && courses.length > 0 && (
          <StatCards
            items={[
              { label: 'Mis cursos', value: courses.length, hint: `${resumen.completados} terminados`, icon: 'libro', tono: 'azul' },
              { label: 'Progreso medio', value: `${resumen.progreso}%`, hint: 'de las lecciones', icon: 'grafica', tono: 'violeta' },
              { label: 'Promedio', value: formatearNota(resumen.promedio), hint: `sobre ${NOTA_MAXIMA}`, icon: 'premio', tono: 'ambar' },
              { label: 'Evaluaciones', value: resumen.pendientes, hint: 'por presentar', icon: 'quiz', tono: 'rosa' },
            ]}
          />
        )}

        {!loading && !error && continuar && tab === 'mis-clases' && !search && !categoria && (
          <Link to={`/curso/${continuar.id}`} className="lh-continuar lh-aparecer">
            <CourseCover curso={continuar} size="sm" />
            <div className="lh-continuar-info">
              <span className="lh-hero-kicker">Continúa aprendiendo</span>
              <h3>{continuar.nombre}</h3>
              <p>{continuar.profesorNombre ? `Con ${continuar.profesorNombre}` : continuar.descripcion}</p>
              <ProgressBar value={continuar.progreso} label="Tu progreso" />
            </div>
            <span className="lh-continuar-accion">
              <span className="ui-btn ui-btn-primary">
                <Icon nombre="play" size={15} />
                {continuar.progreso > 0 ? 'Continuar' : 'Empezar'}
              </span>
            </span>
          </Link>
        )}

        <Tabs
          tabs={[
            { id: 'mis-clases', label: 'Mis clases', count: courses.length },
            { id: 'explorar', label: 'Explorar cursos', count: catalogo.length },
          ]}
          active={tab}
          onChange={cambiarTab}
        />

        {categoriasVisibles.length > 1 && (
          <div className="lh-filtros" role="group" aria-label="Filtrar por categoría">
            <button
              type="button"
              className={`lh-filtro${categoria === null ? ' activo' : ''}`}
              onClick={() => setCategoria(null)}
            >
              Todas
            </button>
            {categoriasVisibles.map((c) => (
              <button
                type="button"
                key={c.nombre}
                className={`lh-filtro${categoria === c.nombre ? ' activo' : ''}`}
                style={{ '--c1': c.c1, '--c2': c.c2 }}
                onClick={() => setCategoria(categoria === c.nombre ? null : c.nombre)}
              >
                <span className="lh-filtro-punto" />
                {c.nombre}
              </button>
            ))}
          </div>
        )}

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
            {search || categoria ? (
              <>
                <h3>No encontramos cursos</h3>
                <p>Prueba con otra búsqueda o quita el filtro de categoría.</p>
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
              <article className="course-card lh-aparecer" key={course.id}>
                <CourseCover curso={course}>
                  {tab === 'mis-clases' && course.progreso >= 100 && (
                    <span className="lh-chip lh-chip-verde">
                      <Icon nombre="check" size={13} /> Terminado
                    </span>
                  )}
                </CourseCover>

                <div className="course-content">
                  <h3>{course.nombre}</h3>

                  <div className="lh-meta">
                    {course.profesorNombre && (
                      <span><Icon nombre="usuario" size={14} /> {course.profesorNombre}</span>
                    )}
                    {tab === 'mis-clases' && course.totalEvaluaciones > 0 && (
                      <span>
                        <Icon nombre="quiz" size={14} /> {course.presentadas}/{course.totalEvaluaciones} evaluaciones
                      </span>
                    )}
                    {tab === 'mis-clases' && course.promedio !== null && (
                      <span>
                        <Icon nombre="premio" size={14} /> {formatearNota(course.promedio)}/{NOTA_MAXIMA}
                      </span>
                    )}
                  </div>

                  <p>{course.descripcion || 'Sin descripción disponible.'}</p>

                  {tab === 'mis-clases' ? (
                    <>
                      <div className="course-progress">
                        <ProgressBar value={course.progreso} label="Progreso" />
                      </div>

                      <Link to={`/curso/${course.id}`} className="course-button">
                        {course.progreso >= 100 ? 'Repasar el curso' : course.progreso > 0 ? 'Continuar' : 'Empezar'}
                        <Icon nombre="flecha" size={16} />
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
