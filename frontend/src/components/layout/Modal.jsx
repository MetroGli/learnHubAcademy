import { useEffect, useRef } from 'react';
import './Modal.css';

// Pila de modales abiertos: Escape solo cierra el que está encima.
const pila = [];

export default function Modal({ open, title, onClose, children, size = 'md' }) {
  const id = useRef(Symbol('modal'));
  const onCloseRef = useRef(onClose);
  onCloseRef.current = onClose;

  useEffect(() => {
    if (!open) return undefined;

    const actual = id.current;
    pila.push(actual);

    function onKeyDown(e) {
      if (e.key === 'Escape' && pila[pila.length - 1] === actual) {
        onCloseRef.current();
      }
    }
    document.addEventListener('keydown', onKeyDown);

    return () => {
      document.removeEventListener('keydown', onKeyDown);
      const posicion = pila.indexOf(actual);
      if (posicion !== -1) pila.splice(posicion, 1);
    };
  }, [open]);

  if (!open) return null;

  return (
    <div className="lh-modal-overlay" onClick={onClose}>
      <div
        className={`lh-modal${size === 'lg' ? ' lh-modal-lg' : ''}`}
        role="dialog"
        aria-modal="true"
        aria-label={title}
        onClick={(e) => e.stopPropagation()}
      >
        <div className="lh-modal-header">
          <h3>{title}</h3>
          <button className="lh-modal-close" onClick={onClose} aria-label="Cerrar">
            ×
          </button>
        </div>
        <div className="lh-modal-body">{children}</div>
      </div>
    </div>
  );
}
