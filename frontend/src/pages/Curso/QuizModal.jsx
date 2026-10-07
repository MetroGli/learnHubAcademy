import { useState } from 'react';
import Modal from '../../components/layout/Modal';
import { getErrorMessage } from '../../api/errors';
import { NOTA_MAXIMA, formatearNota } from '../../utils/notas';

/**
 * Evaluación para el estudiante: una opción por pregunta. Se califica sola
 * (nota = proporción de aciertos sobre NOTA_MAXIMA) y la nota se guarda.
 */
export default function QuizModal({
  evaluacion,
  preguntas,
  respuestas,
  notaActual,
  onGuardar,
  onClose,
}) {
  const [elecciones, setElecciones] = useState({}); // preguntaId -> respuestaId
  const [resultado, setResultado] = useState(null);
  const [error, setError] = useState(null);
  const [guardando, setGuardando] = useState(false);

  const opcionesDe = (preguntaId) =>
    respuestas.filter((r) => r.preguntaId === preguntaId);

  const enviar = async () => {
    setError(null);

    if (preguntas.some((p) => !elecciones[p.id])) {
      setError('Responde todas las preguntas antes de enviar.');
      return;
    }

    const correctas = preguntas.filter((p) =>
      opcionesDe(p.id).some((r) => r.id === elecciones[p.id] && r.correcta)
    ).length;

    const nota =
      Math.round((correctas / preguntas.length) * NOTA_MAXIMA * 100) / 100;

    setGuardando(true);
    try {
      await onGuardar(nota);
      setResultado({ nota, correctas, total: preguntas.length });
    } catch (err) {
      setError(getErrorMessage(err, 'No se pudo guardar tu nota. Intenta de nuevo.'));
    } finally {
      setGuardando(false);
    }
  };

  return (
    <Modal open title={evaluacion.titulo} onClose={onClose} size="lg">
      {resultado ? (
        <div className="quiz-resultado">
          <p className="quiz-nota">
            {formatearNota(resultado.nota)} <span>/ {NOTA_MAXIMA}</span>
          </p>
          <p>
            Acertaste {resultado.correctas} de {resultado.total} preguntas. Tu
            nota quedó guardada.
          </p>

          <ol className="quiz-revision">
            {preguntas.map((pregunta) => {
              const elegida = opcionesDe(pregunta.id).find(
                (r) => r.id === elecciones[pregunta.id]
              );
              const acerto = Boolean(elegida?.correcta);
              const correcta = opcionesDe(pregunta.id).find((r) => r.correcta);

              return (
                <li key={pregunta.id} className={acerto ? 'acierto' : 'fallo'}>
                  <strong>{pregunta.enunciado}</strong>
                  <span>
                    {acerto
                      ? `✓ ${elegida.texto}`
                      : `✗ ${elegida?.texto ?? 'Sin respuesta'}${
                          correcta ? ` — Correcta: ${correcta.texto}` : ''
                        }`}
                  </span>
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
      ) : (
        <div className="quiz">
          {evaluacion.descripcion && (
            <p className="quiz-descripcion">{evaluacion.descripcion}</p>
          )}

          {notaActual !== null && (
            <p className="ui-notice ui-notice-warn">
              Tu nota actual es {formatearNota(notaActual)}. Si presentas la
              evaluación de nuevo, la nota nueva reemplazará a la anterior.
            </p>
          )}

          <ol className="quiz-preguntas">
            {preguntas.map((pregunta) => (
              <li key={pregunta.id}>
                <p className="quiz-enunciado">{pregunta.enunciado}</p>

                <div className="quiz-opciones">
                  {opcionesDe(pregunta.id).map((opcion) => (
                    <label key={opcion.id} className="quiz-opcion">
                      <input
                        type="radio"
                        name={`pregunta-${pregunta.id}`}
                        checked={elecciones[pregunta.id] === opcion.id}
                        onChange={() =>
                          setElecciones((actual) => ({
                            ...actual,
                            [pregunta.id]: opcion.id,
                          }))
                        }
                      />
                      <span>{opcion.texto}</span>
                    </label>
                  ))}
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
              disabled={guardando}
            >
              {guardando ? 'Enviando...' : 'Enviar respuestas'}
            </button>
          </div>
        </div>
      )}
    </Modal>
  );
}
