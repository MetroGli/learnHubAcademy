import { Link } from 'react-router-dom';
import PageShell from '../../components/layout/PageShell';
import '../../components/layout/PageShell.css';
import './Ayuda.css';

const GRUPOS = [
  {
    titulo: 'Cuenta y acceso',
    preguntas: [
      {
        q: '¿Cómo inicio sesión?',
        a: 'Escribe tu correo y tu contraseña en la pantalla de inicio de sesión. Si no tienes cuenta, un administrador debe crearla por ti.',
      },
      {
        q: '¿Cómo cambio mi contraseña?',
        a: 'Abre el menú ⋮ de la esquina superior derecha, entra a "Mi perfil y configuración" y usa la sección "Cambiar contraseña". La nueva contraseña debe tener al menos 8 caracteres.',
      },
      {
        q: 'Olvidé mi contraseña, ¿qué hago?',
        a: 'Por ahora no hay recuperación automática. Escríbenos desde el Centro de soporte o pídele a un administrador que restablezca tu contraseña.',
      },
      {
        q: '¿Por qué se cerró mi sesión?',
        a: 'La sesión dura 24 horas. Pasado ese tiempo, o si cierras sesión manualmente, debes ingresar de nuevo.',
      },
      {
        q: '¿Cómo cierro sesión?',
        a: 'Abre el menú ⋮ de la esquina superior derecha y elige "Cerrar sesión".',
      },
    ],
  },
  {
    titulo: 'Cursos y contenido',
    preguntas: [
      {
        q: 'No veo ninguna clase en mi panel, ¿es un error?',
        a: 'Las clases aparecen cuando estás inscrito. Entra a la pestaña "Explorar cursos" e inscríbete en el que quieras, o pídele a un administrador o a tu profesor que te inscriba.',
      },
      {
        q: '¿Cómo se calcula mi progreso?',
        a: 'Abre una lección para leerla y presiona "Marcar como completada", o usa el círculo que aparece junto a cada lección. El progreso es el porcentaje de lecciones completadas y se guarda en tu cuenta, así que lo verás igual desde cualquier equipo. El botón "Continuar" te lleva a la siguiente lección pendiente.',
      },
      {
        q: '¿Cómo presento una evaluación?',
        a: 'En la pestaña "Evaluaciones" del curso presiona "Presentar evaluación", responde todas las preguntas y envía. Se califica al instante en una escala de 0 a 5 y la nota queda guardada.',
      },
      {
        q: '¿Puedo repetir una evaluación?',
        a: 'Sí. La nota nueva reemplaza a la anterior, aunque sea más baja. Al enviar verás qué preguntas acertaste y cuál era la respuesta correcta de las que fallaste.',
      },
      {
        q: '¿Qué es el promedio ponderado?',
        a: 'Es el promedio de tus notas pesado por el porcentaje de cada evaluación. Solo cuentan las evaluaciones que ya presentaste.',
      },
      {
        q: '¿Cómo abro los materiales de una lección?',
        a: 'Entra al curso, presiona el + del módulo y busca los recursos dentro de la lección. Son enlaces que se abren en una pestaña nueva.',
      },
    ],
  },
  {
    titulo: 'Docentes',
    preguntas: [
      {
        q: '¿Cómo creo un curso y su contenido?',
        a: 'En el Panel docente presiona "+ Nuevo curso". Luego entra al curso y usa "+ Módulo", "+ Lección" y "+ Recurso" para armar el contenido.',
      },
      {
        q: '¿Cómo creo una evaluación con preguntas?',
        a: 'En la pestaña "Evaluaciones" presiona "+ Evaluación", y luego "Preguntas" para agregar preguntas y sus respuestas. Marca la respuesta correcta de cada pregunta para que se califique sola.',
      },
      {
        q: '¿Cómo registro o cambio la nota de un estudiante?',
        a: 'En la pestaña "Estudiantes" del curso haz clic sobre la nota de la evaluación (o sobre el guion si aún no tiene) y escribe un valor entre 0 y 5. Con "Descargar notas (CSV)" obtienes la planilla del curso para abrirla en Excel o Google Sheets.',
      },
      {
        q: '¿Por qué no puedo eliminar un curso o una evaluación?',
        a: 'Si tienen estudiantes inscritos, evaluaciones o notas registradas no se pueden borrar. En el caso de un curso, desactívalo para ocultarlo a los estudiantes.',
      },
    ],
  },
  {
    titulo: 'Administración',
    preguntas: [
      {
        q: '¿Cómo creo un usuario nuevo?',
        a: 'En el Panel de administrador, pestaña "Usuarios", presiona "+ Nuevo usuario", completa el formulario y elige el rol.',
      },
      {
        q: '¿Cómo asigno un profesor a un curso o inscribo a un estudiante?',
        a: 'En la pestaña "Cursos" eliges el profesor al crear o editar el curso. En la pestaña "Inscripciones" presionas "+ Nueva inscripción" y eliges estudiante y curso.',
      },
      {
        q: '¿Puedo editar o eliminar un usuario?',
        a: 'Sí. En la tabla de usuarios cada fila tiene los botones "Editar" y "Eliminar". Al eliminar se pide confirmación.',
      },
    ],
  },
];

export default function Faq() {
  return (
    <PageShell subtitle="Preguntas frecuentes">
      <div className="page-title-block">
        <span className="page-label">RECURSOS</span>
        <h2>Preguntas frecuentes</h2>
        <p>Respuestas rápidas a las dudas más comunes sobre LearnHub.</p>
      </div>

      {GRUPOS.map((grupo) => (
        <div key={grupo.titulo}>
          <h3 className="faq-group-title">{grupo.titulo}</h3>
          <div className="faq-list">
            {grupo.preguntas.map((pregunta) => (
              <details className="faq-item" key={pregunta.q}>
                <summary>{pregunta.q}</summary>
                <div className="faq-answer">
                  <p>{pregunta.a}</p>
                </div>
              </details>
            ))}
          </div>
        </div>
      ))}

      <p className="help-links">
        ¿No encontraste lo que buscabas? Consulta el{' '}
        <Link to="/ayuda/manual">manual de usuario</Link> o escríbenos desde el{' '}
        <Link to="/soporte">centro de soporte</Link>.
      </p>
    </PageShell>
  );
}
