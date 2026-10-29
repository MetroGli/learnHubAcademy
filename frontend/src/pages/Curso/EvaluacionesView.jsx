import { useState } from 'react';
import FormModal from '../../components/ui/FormModal';
import { evaluacionService } from '../../api/resources';
import { getErrorMessage } from '../../api/errors';
import { NOTA_MAXIMA, formatearNota, notaDeEvaluacion } from '../../utils/notas';
import QuizModal from './QuizModal';
import Icon from '../../components/ui/Icon';
import PreguntasManager from './PreguntasManager';

const CAMPOS_EVALUACION = [
  { name: 'titulo', label: 'Título', required: true },
  { name: 'descripcion', label: 'Descripción', type: 'textarea' },
  {
    name: 'porcentaje',
    label: 'Porcentaje del curso (%)',
    type: 'number',
    required: true,
    min: 0,
    max: 100,
    step: 1,
  },
];

/**
 * Evaluaciones de un curso.
 * - gestion=true: crear/editar/eliminar evaluaciones y administrar sus preguntas.
 * - onPresentar: el estudiante presenta la evaluación; el servidor la califica.
 */
export default function EvaluacionesView({
  cursoId,
  evaluaciones,
  preguntas,
  respuestas,
  gestion,
  onChange,
  calificaciones = [],
  onPresentar,
}) {
  const [dialogo, setDialogo] = useState(null); // { modo, registro }
  const [quizDe, setQuizDe] = useState(null);
  const [preguntasDe, setPreguntasDe] = useState(null);
  const [aviso, setAviso] = useState(null);

  const preguntasDeEvaluacion = (id) =>
    preguntas.filter((p) => p.evaluacionId === id);

  // Una pregunta solo se puede responder si tiene al menos una opción.
  const respondibles = (id) =>
    preguntasDeEvaluacion(id).filter((p) =>
      respuestas.some((r) => r.preguntaId === p.id)
    );

  const sumaPorcentajes = evaluaciones.reduce(
    (suma, e) => suma + Number(e.porcentaje || 0),
    0
  );

  const guardar = async (valores) => {
    const payload = { ...valores, cursoId: Number(cursoId) };
    if (dialogo.modo === 'crear') await evaluacionService.crear(payload);
    else await evaluacionService.actualizar(dialogo.registro.id, payload);
    await onChange();
  };

  const eliminar = async (evaluacion) => {
    if (!window.confirm(`¿Eliminar la evaluación "${evaluacion.titulo}" y sus preguntas?`)) {
      return;
    }
    try {
      await evaluacionService.eliminar(evaluacion.id);
      await onChange();
    } catch (err) {
      setAviso(
        getErrorMessage(
          err,
          'No se pudo eliminar la evaluación. Si ya tiene calificaciones registradas, no se puede borrar.'
        )
      );
    }
  };

  const evaluacionDelQuiz = evaluaciones.find((e) => e.id === quizDe);
  const evaluacionDePreguntas = evaluaciones.find((e) => e.id === preguntasDe);

  return (
    <section className="curso-evaluaciones">
      <div className="ui-section-head">
        <h2>Evaluaciones</h2>
        {gestion && (
          <button
            type="button"
            className="ui-btn ui-btn-primary ui-btn-sm"
            onClick={() => {
              setAviso(null);
              setDialogo({ modo: 'crear', registro: null });
            }}
          >
            + Evaluación
          </button>
        )}
      </div>

      {gestion && evaluaciones.length > 0 && (
        <p className={`ui-notice${sumaPorcentajes === 100 ? '' : ' ui-notice-warn'}`}>
          Los porcentajes de las evaluaciones suman {sumaPorcentajes}%.
          {sumaPorcentajes !== 100 && ' Lo ideal es que sumen 100%.'}
        </p>
      )}

      {aviso && <div className="ui-error curso-aviso" role="alert">{aviso}</div>}

      {evaluaciones.length === 0 && (
        <p className="ui-empty">
          {gestion
            ? 'Este curso todavía no tiene evaluaciones. Crea la primera con "+ Evaluación".'
            : 'Este curso todavía no tiene evaluaciones.'}
        </p>
      )}

      <div className="evaluaciones-list">
        {evaluaciones.map((evaluacion) => {
          const total = preguntasDeEvaluacion(evaluacion.id).length;
          const listas = respondibles(evaluacion.id).length;
          const nota = notaDeEvaluacion(calificaciones, evaluacion.id);

          return (
            <div className="evaluacion-card" key={evaluacion.id}>
              <span className={`evaluacion-icono${nota !== null ? ' presentada' : ''}`} aria-hidden="true">
                <Icon nombre={nota !== null ? 'premio' : 'quiz'} size={22} />
              </span>
              <div className="evaluacion-info">
                <h3>{evaluacion.titulo}</h3>
                {evaluacion.descripcion && <p>{evaluacion.descripcion}</p>}
                <div className="evaluacion-datos">
                  <span className="evaluacion-porcentaje">{evaluacion.porcentaje}%</span>
                  <span className="evaluacion-preguntas">
                    {total} {total === 1 ? 'pregunta' : 'preguntas'}
                  </span>
                  {onPresentar && (
                    <span className={`ui-badge ${nota === null ? 'ui-badge-off' : 'ui-badge-ok'}`}>
                      {nota === null
                        ? 'Sin presentar'
                        : `Tu nota: ${formatearNota(nota)} / ${NOTA_MAXIMA}`}
                    </span>
                  )}
                </div>
              </div>

              {onPresentar && (
                <button
                  type="button"
                  className="ui-btn ui-btn-primary ui-btn-sm"
                  disabled={listas === 0}
                  title={listas === 0 ? 'Esta evaluación todavía no tiene preguntas' : undefined}
                  onClick={() => setQuizDe(evaluacion.id)}
                >
                  {nota === null ? 'Presentar evaluación' : 'Repetir evaluación'}
                </button>
              )}

              {gestion && (
                <div className="ui-row-actions">
                  <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => setPreguntasDe(evaluacion.id)}>
                    Preguntas ({total})
                  </button>
                  <button
                    type="button"
                    className="ui-btn ui-btn-ghost ui-btn-sm"
                    onClick={() => {
                      setAviso(null);
                      setDialogo({ modo: 'editar', registro: evaluacion });
                    }}
                  >
                    Editar
                  </button>
                  <button type="button" className="ui-btn ui-btn-danger ui-btn-sm" onClick={() => eliminar(evaluacion)}>
                    Eliminar
                  </button>
                </div>
              )}
            </div>
          );
        })}
      </div>

      <FormModal
        open={Boolean(dialogo)}
        title={dialogo?.modo === 'crear' ? 'Nueva evaluación' : 'Editar evaluación'}
        fields={CAMPOS_EVALUACION}
        initialValues={dialogo?.modo === 'editar' ? dialogo.registro : {}}
        onSubmit={guardar}
        onClose={() => setDialogo(null)}
      />

      {evaluacionDelQuiz && onPresentar && (
        <QuizModal
          evaluacion={evaluacionDelQuiz}
          preguntas={respondibles(evaluacionDelQuiz.id)}
          respuestas={respuestas}
          notaActual={notaDeEvaluacion(calificaciones, evaluacionDelQuiz.id)}
          onPresentar={(elecciones) => onPresentar(evaluacionDelQuiz.id, elecciones)}
          onClose={() => setQuizDe(null)}
        />
      )}

      {evaluacionDePreguntas && gestion && (
        <PreguntasManager
          evaluacion={evaluacionDePreguntas}
          preguntas={preguntasDeEvaluacion(evaluacionDePreguntas.id)}
          respuestas={respuestas}
          onChange={onChange}
          onClose={() => setPreguntasDe(null)}
        />
      )}
    </section>
  );
}
