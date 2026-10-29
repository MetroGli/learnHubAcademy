import { useState } from 'react';
import Modal from '../../components/layout/Modal';
import Icon from '../../components/ui/Icon';
import ProgressRing from '../../components/ui/ProgressRing';
import { getErrorMessage } from '../../api/errors';
import { NOTA_MAXIMA, formatearNota } from '../../utils/notas';

const LETRAS = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'];

function mensajePara(nota) {
  const proporcion = nota / NOTA_MAXIMA;
  if (proporcion >= 0.9) return '¡Excelente trabajo!';
  if (proporcion >= 0.7) return '¡Muy bien!';
  if (proporcion >= 0.6) return 'Aprobaste. Repasa los temas que fallaste.';
  return 'Todavía no apruebas. Repasa las lecciones e inténtalo de nuevo.';
}

/**
 * Evaluación para el estudiante: una opción por pregunta. Las respuestas se
 * envían al servidor, que calcula la nota y la guarda.
 */
export default function QuizModal({
  evaluacion,
  preguntas,
  respuestas,
  notaActual,
  onPresentar,
  onClose,
}) {
  const [elecciones, setElecciones] = useState({}); // preguntaId -> respuestaId
  const [resultado, setResultado] = useState(null);
  const [error, setError] = useState(null);
  const [enviando, setEnviando] = useState(false);

  const opcionesDe = (preguntaId) =>
    respuestas.filter((r) => r.preguntaId === preguntaId);

  const respondidas = preguntas.filter((p) => elecciones[p.id]).length;

  const enviar = async () => {
    setError(null);

    if (respondidas < preguntas.length) {
      setError(`Te faltan ${preguntas.length - respondidas} preguntas por responder.`);
      return;
    }

    setEnviando(true);
    try {
      setResultado(await onPresentar(elecciones));
    } catch (err) {
      setError(getErrorMessage(err, 'No se pudo enviar la evaluación. Intenta de nuevo.'));
    } finally {
      setEnviando(false);
    }
  };

  if (resultado) {
    const detallePorPregunta = new Map(resultado.detalle.map((d) => [d.preguntaId, d]));
    const texto = (respuestaId) => respuestas.find((r) => r.id === respuestaId)?.texto;

    return (
      <Modal open title={evaluacion.titulo} onClose={onClose} size="lg">
        <div className="quiz-resultado">
          <div className="quiz-resultado-cabecera">
            <ProgressRing value={(resultado.nota / NOTA_MAXIMA) * 100} size={112} grosor={10}>
              <div className="quiz-resultado-nota">
                <strong>{formatearNota(resultado.nota)}</strong>
                <span>de {NOTA_MAXIMA}</span>
              </div>
            </ProgressRing>
            <div>
              <h4>{mensajePara(resultado.nota)}</h4>
              <p>
                Acertaste <strong>{resultado.correctas}</strong> de{' '}
                <strong>{resultado.total}</strong> preguntas. Tu nota quedó guardada.
              </p>
            </div>
          </div>

          <ol className="quiz-revision">
            {preguntas.map((pregunta) => {
              const d = detallePorPregunta.get(pregunta.id);
              if (!d) return null;

              return (
                <li key={pregunta.id} className={d.acerto ? 'acierto' : 'fallo'}>
                  <span className="quiz-revision-icono" aria-hidden="true">
                    <Icon nombre={d.acerto ? 'check' : 'cerrar'} size={14} strokeWidth={3} />
                  </span>
                  <div>
                    <strong>{pregunta.enunciado}</strong>
                    <span>Tu respuesta: {texto(d.respuestaElegidaId) ?? 'Sin respuesta'}</span>
                    {!d.acerto && d.respuestaCorrectaId && (
                      <span className="quiz-correcta">Correcta: {texto(d.respuestaCorrectaId)}</span>
                    )}
                  </div>
                </li>
              );
            })}
          </ol>

          <div className="ui-form-actions">
            <button type="button" className="ui-btn ui-btn-primary" onClick={onClose}>
              Cerrar
            </button>
          </div>
        </div>
      </Modal>
    );
  }

  return (
    <Modal open title={evaluacion.titulo} onClose={onClose} size="lg">
      <div className="quiz">
        {evaluacion.descripcion && (
          <p className="quiz-descripcion">{evaluacion.descripcion}</p>
        )}

        <div className="quiz-avance">
          <span>
            {respondidas} de {preguntas.length} respondidas
          </span>
          <div className="ui-progress">
            <div
              className="ui-progress-bar"
              style={{ width: `${(respondidas / preguntas.length) * 100}%` }}
            />
          </div>
        </div>

        {notaActual !== null && (
          <p className="ui-notice ui-notice-warn">
            Tu nota actual es {formatearNota(notaActual)}. Si presentas la
            evaluación de nuevo, la nota nueva reemplazará a la anterior.
          </p>
        )}

        <ol className="quiz-preguntas">
          {preguntas.map((pregunta, i) => (
            <li key={pregunta.id}>
              <p className="quiz-enunciado">
                <span className="quiz-numero">{i + 1}</span>
                {pregunta.enunciado}
              </p>

              <div className="quiz-opciones">
                {opcionesDe(pregunta.id).map((opcion, j) => {
                  const elegida = elecciones[pregunta.id] === opcion.id;
                  return (
                    <label key={opcion.id} className={`quiz-opcion${elegida ? ' elegida' : ''}`}>
                      <input
                        type="radio"
                        name={`pregunta-${pregunta.id}`}
                        checked={elegida}
                        onChange={() =>
                          setElecciones((actual) => ({
                            ...actual,
                            [pregunta.id]: opcion.id,
                          }))
                        }
                      />
                      <span className="quiz-letra">{LETRAS[j] ?? j + 1}</span>
                      <span>{opcion.texto}</span>
                    </label>
                  );
                })}
              </div>
            </li>
          ))}
        </ol>

        {error && <div className="ui-error" role="alert">{error}</div>}

        <div className="ui-form-actions">
          <button type="button" className="ui-btn ui-btn-ghost" onClick={onClose}>
            Cancelar
          </button>
          <button
            type="button"
            className="ui-btn ui-btn-primary"
            onClick={enviar}
            disabled={enviando}
          >
            {enviando ? 'Calificando...' : 'Enviar respuestas'}
          </button>
        </div>
      </div>
    </Modal>
  );
}
