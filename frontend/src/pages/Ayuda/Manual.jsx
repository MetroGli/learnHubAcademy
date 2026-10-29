import { Link } from 'react-router-dom';
import PageShell from '../../components/layout/PageShell';
import '../../components/layout/PageShell.css';
import './Ayuda.css';

const SECCIONES = [
  { id: 'inicio', titulo: 'Primeros pasos' },
  { id: 'estudiante', titulo: 'Estudiantes' },
  { id: 'docente', titulo: 'Docentes' },
  { id: 'admin', titulo: 'Administradores' },
  { id: 'cuenta', titulo: 'Tu cuenta' },
];

export default function Manual() {
  return (
    <PageShell subtitle="Manual de usuario">
      <div className="page-title-block">
        <span className="page-label">RECURSOS</span>
        <h2>Manual de usuario</h2>
        <p>
          Guía rápida para moverte por LearnHub según tu rol. Encuentra la
          sección que te corresponde.
        </p>
      </div>

      <nav className="manual-toc" aria-label="Secciones del manual">
        {SECCIONES.map((seccion) => (
          <a key={seccion.id} href={`#${seccion.id}`}>
            {seccion.titulo}
          </a>
        ))}
      </nav>

      <section className="page-card manual-section" id="inicio">
        <h3>Primeros pasos</h3>
        <ol>
          <li>
            Ingresa con tu correo y contraseña en la pantalla de inicio de
            sesión.
          </li>
          <li>
            Al entrar verás el panel que corresponde a tu rol: estudiante,
            docente o administrador.
          </li>
          <li>
            En la esquina superior derecha, el botón <strong>⋮</strong> abre el
            menú con tu perfil, la ayuda y la opción para cerrar sesión.
          </li>
          <li>
            Por seguridad, la sesión dura 24 horas; pasado ese tiempo tendrás
            que volver a ingresar.
          </li>
        </ol>
      </section>

      <section className="page-card manual-section" id="estudiante">
        <span className="manual-tag">Estudiante</span>
        <h3>Mis clases y evaluaciones</h3>
        <ul>
          <li>
            En <strong>Mis clases</strong> están los cursos en los que estás
            inscrito, con tu progreso. Usa el buscador de arriba para filtrarlos.
          </li>
          <li>
            En <strong>Explorar cursos</strong> ves los cursos activos en los que
            aún no estás y puedes inscribirte con el botón{' '}
            <strong>Inscribirme</strong>.
          </li>
          <li>
            Dentro de un curso, en <strong>Contenido</strong>, abre un módulo con
            el <strong>+</strong> y haz clic en una lección para leerla con sus
            recursos. Márcala con <strong>Marcar como completada</strong> (o con el
            círculo de la lista): tu porcentaje de progreso se actualiza solo. El
            botón <strong>Continuar</strong> te lleva a la siguiente lección pendiente.
          </li>
          <li>
            En <strong>Evaluaciones</strong> presenta cada evaluación con{' '}
            <strong>Presentar evaluación</strong>. Responde todas las preguntas y
            envía: se califica al instante (escala de 0 a 5) y la nota queda
            guardada. Puedes repetirla y la nueva nota reemplaza a la anterior.
          </li>
          <li>
            Arriba del curso ves tu progreso y tu <strong>promedio ponderado</strong>,
            calculado con el porcentaje de cada evaluación.
          </li>
        </ul>
      </section>

      <section className="page-card manual-section" id="docente">
        <span className="manual-tag">Docente</span>
        <h3>Mis cursos</h3>
        <ul>
          <li>
            En el <strong>Panel docente</strong> ves tus cursos y un resumen de
            cursos y estudiantes. Con <strong>+ Nuevo curso</strong> creas uno;
            con <strong>Editar</strong> cambias su nombre, descripción o lo
            activas/desactivas. Un curso inactivo no lo ven los estudiantes.
          </li>
          <li>
            Si un curso ya tiene estudiantes o evaluaciones no se puede eliminar:
            desactívalo.
          </li>
          <li>
            Dentro de tu curso, en <strong>Contenido</strong>, usa{' '}
            <strong>+ Módulo</strong>, <strong>+ Lección</strong> y{' '}
            <strong>+ Recurso</strong> (enlace, documento o video) y edítalos o
            elimínalos cuando quieras.
          </li>
          <li>
            En <strong>Evaluaciones</strong> crea evaluaciones con su porcentaje y
            usa <strong>Preguntas</strong> para agregar preguntas y respuestas.
            Marca cuál es la correcta; así se califican solas. Procura que los
            porcentajes sumen 100%.
          </li>
          <li>
            En <strong>Estudiantes</strong> ves a los inscritos con su progreso y
            sus notas. Haz clic en una nota para registrarla o cambiarla, y usa{' '}
            <strong>Dar de baja</strong> o <strong>Reactivar</strong> para
            gestionar su inscripción.
          </li>
        </ul>
      </section>

      <section className="page-card manual-section" id="admin">
        <span className="manual-tag">Administrador</span>
        <h3>Gestión de la plataforma</h3>
        <ul>
          <li>
            El <strong>Panel de administrador</strong> tiene tres pestañas:{' '}
            <strong>Usuarios</strong>, <strong>Cursos</strong> e{' '}
            <strong>Inscripciones</strong>. El buscador de arriba filtra la
            pestaña activa.
          </li>
          <li>
            En <strong>Usuarios</strong> creas cuentas con{' '}
            <strong>+ Nuevo usuario</strong> y eliges su rol; también puedes
            editarlas o eliminarlas (se pide confirmación).
          </li>
          <li>
            En <strong>Cursos</strong> creas cursos y les asignas un profesor,
            los editas, los activas o desactivas, y con{' '}
            <strong>Gestionar</strong> entras a cualquier curso para editar su
            contenido, evaluaciones y estudiantes.
          </li>
          <li>
            En <strong>Inscripciones</strong> inscribes estudiantes en cursos, las
            activas o desactivas y las eliminas. Un estudiante no puede quedar
            inscrito dos veces en el mismo curso.
          </li>
        </ul>
      </section>

      <section className="page-card manual-section" id="cuenta">
        <h3>Tu cuenta</h3>
        <ul>
          <li>
            Abre el menú <strong>⋮</strong> y elige{' '}
            <strong>Mi perfil y configuración</strong> para cambiar tu nombre,
            tu apellido o tu contraseña.
          </li>
          <li>
            Usa <strong>Cerrar sesión</strong> en ese mismo menú cuando termines,
            sobre todo si compartes el equipo.
          </li>
        </ul>
      </section>

      <p className="help-links">
        ¿Sigues con dudas? Revisa las{' '}
        <Link to="/ayuda/faq">preguntas frecuentes</Link> o escríbenos desde el{' '}
        <Link to="/soporte">centro de soporte</Link>.
      </p>
    </PageShell>
  );
}
