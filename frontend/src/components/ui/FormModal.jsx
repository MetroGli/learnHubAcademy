import { useState } from 'react';
import Modal from '../layout/Modal';
import { getErrorMessage } from '../../api/errors';
import './ui.css';

/**
 * Formulario en ventana modal, definido por una lista de campos:
 *   { name, label, type, required, options, min, max, step, placeholder, numeric }
 * type: text | textarea | number | url | email | password | select | checkbox
 * Con numeric=true el valor (p. ej. el id de un select) se envía como número.
 */
export default function FormModal({
  open,
  title,
  fields,
  initialValues = {},
  onSubmit,
  onClose,
  submitLabel = 'Guardar',
  size = 'md',
  note,
}) {
  return (
    <Modal open={open} title={title} onClose={onClose} size={size}>
      <FormBody
        fields={fields}
        initialValues={initialValues}
        onSubmit={onSubmit}
        onClose={onClose}
        submitLabel={submitLabel}
        note={note}
      />
    </Modal>
  );
}

function valorInicial(campo, initialValues) {
  const valor = initialValues[campo.name];
  if (campo.type === 'checkbox') return Boolean(valor);
  return valor === null || valor === undefined ? '' : String(valor);
}

function FormBody({ fields, initialValues, onSubmit, onClose, submitLabel, note }) {
  // El formulario solo existe mientras el modal está abierto, así que el
  // estado se reinicia solo cada vez que se abre.
  const [values, setValues] = useState(() =>
    Object.fromEntries(
      fields.map((campo) => [campo.name, valorInicial(campo, initialValues)])
    )
  );
  const [error, setError] = useState(null);
  const [saving, setSaving] = useState(false);

  const handleChange = (campo) => (e) => {
    const valor =
      campo.type === 'checkbox' ? e.target.checked : e.target.value;
    setValues((actual) => ({ ...actual, [campo.name]: valor }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    const resultado = {};

    for (const campo of fields) {
      let valor = values[campo.name];

      if (campo.type === 'checkbox') {
        resultado[campo.name] = Boolean(valor);
        continue;
      }

      if (typeof valor === 'string' && campo.type !== 'password') {
        valor = valor.trim();
      }

      if (campo.required && valor === '') {
        setError(`El campo "${campo.label}" es obligatorio.`);
        return;
      }

      if (campo.type === 'url' && valor !== '' && !/^https?:\/\/\S+$/i.test(valor)) {
        setError(`"${campo.label}" debe empezar por http:// o https://`);
        return;
      }

      if (campo.type === 'number' || campo.numeric) {
        if (valor === '') {
          resultado[campo.name] = null;
          continue;
        }
        const numero = Number(valor);
        if (Number.isNaN(numero)) {
          setError(`"${campo.label}" debe ser un número.`);
          return;
        }
        if (campo.min !== undefined && numero < campo.min) {
          setError(`"${campo.label}" no puede ser menor que ${campo.min}.`);
          return;
        }
        if (campo.max !== undefined && numero > campo.max) {
          setError(`"${campo.label}" no puede ser mayor que ${campo.max}.`);
          return;
        }
        resultado[campo.name] = numero;
        continue;
      }

      resultado[campo.name] = valor;
    }

    setSaving(true);
    try {
      await onSubmit(resultado);
      onClose();
    } catch (err) {
      setError(getErrorMessage(err, 'No se pudo guardar. Revisa los datos.'));
      setSaving(false);
    }
  };

  return (
    <form className="ui-form" onSubmit={handleSubmit} noValidate>
      {note && <p className="ui-form-note">{note}</p>}

      {fields.map((campo) => {
        const id = `campo-${campo.name}`;

        if (campo.type === 'checkbox') {
          return (
            <label className="ui-field ui-field-check" key={campo.name}>
              <input
                type="checkbox"
                checked={values[campo.name]}
                onChange={handleChange(campo)}
              />
              <span>{campo.label}</span>
            </label>
          );
        }

        return (
          <div className="ui-field" key={campo.name}>
            <label htmlFor={id}>{campo.label}</label>

            {campo.type === 'textarea' && (
              <textarea
                id={id}
                rows={campo.rows || 3}
                value={values[campo.name]}
                onChange={handleChange(campo)}
                placeholder={campo.placeholder}
              />
            )}

            {campo.type === 'select' && (
              <select
                id={id}
                value={values[campo.name]}
                onChange={handleChange(campo)}
              >
                <option value="">Selecciona...</option>
                {campo.options.map((opcion) => (
                  <option key={opcion.value} value={opcion.value}>
                    {opcion.label}
                  </option>
                ))}
              </select>
            )}

            {campo.type !== 'textarea' && campo.type !== 'select' && (
              <input
                id={id}
                type={campo.type || 'text'}
                value={values[campo.name]}
                onChange={handleChange(campo)}
                placeholder={campo.placeholder}
                min={campo.min}
                max={campo.max}
                step={campo.step}
                autoComplete="off"
              />
            )}
          </div>
        );
      })}

      {error && <div className="ui-error" role="alert">{error}</div>}

      <div className="ui-form-actions">
        <button type="button" className="ui-btn ui-btn-ghost" onClick={onClose}>
          Cancelar
        </button>
        <button type="submit" className="ui-btn ui-btn-primary" disabled={saving}>
          {saving ? 'Guardando...' : submitLabel}
        </button>
      </div>
    </form>
  );
}
