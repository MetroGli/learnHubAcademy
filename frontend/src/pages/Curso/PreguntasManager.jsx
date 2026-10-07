import { useState } from 'react';
import Modal from '../../components/layout/Modal';
import FormModal from '../../components/ui/FormModal';
import { preguntaService, respuestaService } from '../../api/resources';
import { getErrorMessage } from '../../api/errors';

const TIPO_PREGUNTA = 'SELECCION_MULTIPLE';

const CAMPOS_PREGUNTA = [
  { name: 'enunciado', label: 'Enunciado', type: 'textarea', required: true },
];

const CAMPOS_RESPUESTA = [
  { name: 'texto', label: 'Texto de la respuesta', required: true },
  { name: 'correcta', label: 'Es la respuesta correcta', type: 'checkbox' },
];

/** Preguntas y respuestas de una evaluación (vista del profesor/administrador). */
export default function PreguntasManager({
  evaluacion,
  preguntas,
  respuestas,
  onChange,
  onClose,
}) {
  const [dialogo, setDialogo] = useState(null); // { tipo, modo, registro, padreId }
  const [aviso, setAviso] = useState(null);

  const respuestasDe = (preguntaId) =>
    respuestas.filter((r) => r.preguntaId === preguntaId);

  const abrir = (tipo, modo, registro = null, padreId = null) => {
    setAviso(null);
    setDialogo({ tipo, modo, registro, padreId });
  };

  const guardar = async (valores) => {
    const { tipo, modo, registro, padreId } = dialogo;

    if (tipo === 'pregunta') {
      if (modo === 'crear') {
        await preguntaService.crear({
          ...valores,
          tipo: TIPO_PREGUNTA,
          evaluacionId: evaluacion.id,
        });
      } else {
        await preguntaService.actualizar(registro.id, {
          ...valores,
          tipo: registro.tipo,
          evaluacionId: evaluacion.id,
        });
      }
    } else if (modo === 'crear') {
      await respuestaService.crear({ ...valores, preguntaId: padreId });
    } else {
      await respuestaService.actualizar(registro.id, {
        ...valores,
        preguntaId: registro.preguntaId,
      });
    }

    await onChange();
  };

  const eliminar = async (tipo, registro) => {
    const mensaje =
      tipo === 'pregunta'
        ? '¿Eliminar esta pregunta y todas sus respuestas?'
        : '¿Eliminar esta respuesta?';
    if (!window.confirm(mensaje)) return;

    try {
      const servicio = tipo === 'pregunta' ? preguntaService : respuestaService;
      await servicio.eliminar(registro.id);
      await onChange();
    } catch (err) {
      setAviso(getErrorMessage(err, 'No se pudo eliminar.'));
    }
  };

  const titulosDialogo = {
    pregunta: { crear: 'Nueva pregunta', editar: 'Editar pregunta' },
    respuesta: { crear: 'Nueva respuesta', editar: 'Editar respuesta' },
  };

  return (
    <Modal open title={`Preguntas · ${evaluacion.titulo}`} onClose={onClose} size="lg">
      <div className="ui-section-head">
        <span>
          {preguntas.length} {preguntas.length === 1 ? 'pregunta' : 'preguntas'}
        </span>
        <button
          type="button"
          className="ui-btn ui-btn-primary ui-btn-sm"
          onClick={() => abrir('pregunta', 'crear')}
        >
          + Pregunta
        </button>
      </div>

      {aviso && <div className="ui-error" role="alert">{aviso}</div>}

      {preguntas.length === 0 && (
        <p className="ui-empty">
          Esta evaluación todavía no tiene preguntas. Agrega la primera con
          "+ Pregunta" y luego sus respuestas.
        </p>
      )}

      <ol className="pm-lista">
        {preguntas.map((pregunta) => {
          const opciones = respuestasDe(pregunta.id);
          const faltaCorrecta = !opciones.some((r) => r.correcta);

          return (
            <li className="pm-pregunta" key={pregunta.id}>
              <div className="pm-pregunta-head">
                <strong>{pregunta.enunciado}</strong>
                <div className="ui-row-actions">
                  <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => abrir('respuesta', 'crear', null, pregunta.id)}>
                    + Respuesta
                  </button>
                  <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => abrir('pregunta', 'editar', pregunta)}>
                    Editar
                  </button>
                  <button type="button" className="ui-btn ui-btn-danger ui-btn-sm" onClick={() => eliminar('pregunta', pregunta)}>
                    Eliminar
                  </button>
                </div>
              </div>

              {opciones.length === 0 && (
                <p className="pm-aviso">Aún no tiene respuestas.</p>
              )}
              {opciones.length > 0 && faltaCorrecta && (
                <p className="pm-aviso">Falta marcar la respuesta correcta.</p>
              )}

              <ul className="pm-respuestas">
                {opciones.map((respuesta) => (
                  <li key={respuesta.id} className={respuesta.correcta ? 'correcta' : ''}>
                    <span>
                      {respuesta.correcta ? '✓ ' : ''}
                      {respuesta.texto}
                    </span>
                    <span className="curso-mini-acciones">
                      <button type="button" onClick={() => abrir('respuesta', 'editar', respuesta)}>
                        Editar
                      </button>
                      <button type="button" className="peligro" onClick={() => eliminar('respuesta', respuesta)}>
                        Eliminar
                      </button>
                    </span>
                  </li>
                ))}
              </ul>
            </li>
          );
        })}
      </ol>

      <FormModal
        open={Boolean(dialogo)}
        title={dialogo ? titulosDialogo[dialogo.tipo][dialogo.modo] : ''}
        fields={dialogo?.tipo === 'pregunta' ? CAMPOS_PREGUNTA : CAMPOS_RESPUESTA}
        initialValues={dialogo?.modo === 'editar' ? dialogo.registro : {}}
        onSubmit={guardar}
        onClose={() => setDialogo(null)}
      />
    </Modal>
  );
}
