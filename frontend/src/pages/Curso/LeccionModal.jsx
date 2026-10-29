import Modal from '../../components/layout/Modal';
import Icon from '../../components/ui/Icon';
import RichText from '../../components/ui/RichText';
import { formatearMinutos } from '../../utils/cursos';

const ICONO_RECURSO = { DOCUMENTO: 'documento', VIDEO: 'video', ENLACE: 'enlace' };
const NOMBRE_RECURSO = { DOCUMENTO: 'Documento', VIDEO: 'Video', ENLACE: 'Enlace' };

function sitio(url) {
  try {
    return new URL(url).hostname.replace(/^www\./, '');
  } catch {
    return url;
  }
}

/**
 * Vista de lectura de una lección: contenido, recursos y navegación.
 * Si se pasa onToggle (estudiante), permite marcarla como completada.
 */
export default function LeccionModal({
  leccion,
  modulo,
  recursos,
  posicion,
  total,
  completada,
  onToggle,
  guardando,
  onAnterior,
  onSiguiente,
  onClose,
}) {
  const duracion = formatearMinutos(leccion.duracionMinutos);

  return (
    <Modal open title={leccion.titulo} onClose={onClose} size="lg">
      <div className="leccion-lector">
        <div className="leccion-lector-meta">
          {modulo && <span className="lh-chip">Módulo {modulo.orden} · {modulo.nombre}</span>}
          <span className="lh-chip lh-chip-gris">
            Lección {posicion} de {total}
          </span>
          {duracion && (
            <span className="lh-chip lh-chip-gris">
              <Icon nombre="reloj" size={13} /> {duracion}
            </span>
          )}
          {completada && (
            <span className="lh-chip lh-chip-verde">
              <Icon nombre="check" size={13} /> Completada
            </span>
          )}
        </div>

        {leccion.contenido ? (
          <RichText texto={leccion.contenido} className="leccion-lector-texto" />
        ) : (
          <p className="ui-empty">Esta lección todavía no tiene contenido.</p>
        )}

        {recursos.length > 0 && (
          <div className="leccion-lector-recursos">
            <h4>Recursos de apoyo</h4>
            <ul>
              {recursos.map((recurso) => (
                <li key={recurso.id}>
                  <a href={recurso.url} target="_blank" rel="noreferrer">
                    <span className="recurso-icono">
                      <Icon nombre={ICONO_RECURSO[recurso.tipo] ?? 'enlace'} size={18} />
                    </span>
                    <span className="recurso-texto">
                      <strong>{recurso.nombre}</strong>
                      <small>{NOMBRE_RECURSO[recurso.tipo] ?? recurso.tipo} · {sitio(recurso.url)}</small>
                    </span>
                    <Icon nombre="flecha" size={16} />
                  </a>
                </li>
              ))}
            </ul>
          </div>
        )}

        <div className="leccion-lector-pie">
          <button
            type="button"
            className="ui-btn ui-btn-ghost ui-btn-sm"
            onClick={onAnterior}
            disabled={!onAnterior}
          >
            <Icon nombre="flechaIzq" size={16} /> Anterior
          </button>

          {onToggle && (
            <button
              type="button"
              className={`ui-btn ${completada ? 'ui-btn-ghost' : 'ui-btn-exito'}`}
              onClick={onToggle}
              disabled={guardando}
            >
              <Icon nombre={completada ? 'cerrar' : 'check'} size={16} />
              {completada ? 'Marcar como pendiente' : 'Marcar como completada'}
            </button>
          )}

          <button
            type="button"
            className="ui-btn ui-btn-primary ui-btn-sm"
            onClick={onSiguiente}
            disabled={!onSiguiente}
          >
            Siguiente <Icon nombre="flecha" size={16} />
          </button>
        </div>
      </div>
    </Modal>
  );
}
