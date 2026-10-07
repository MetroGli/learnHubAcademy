import { useState } from 'react';
import { useAdminInscripciones } from '../../hooks/useAdminCatalogo';
import { getErrorMessage } from '../../api/errors';
import FormModal from '../../components/ui/FormModal';
import '../../components/ui/ui.css';

// "2026-10-06" -> "06/10/2026" (sin pasar por Date para evitar desfases de zona horaria)
function formatearFecha(fecha) {
  if (!fecha) return '—';
  const [anio, mes, dia] = String(fecha).split('T')[0].split('-');
  return dia && mes && anio ? `${dia}/${mes}/${anio}` : fecha;
}

export default function InscripcionesTab({ search }) {
  const {
    inscripciones,
    estudiantes,
    usuarios,
    cursos,
    loading,
    error,
    crear,
    cambiarEstado,
    eliminar,
  } = useAdminInscripciones();

  const [creando, setCreando] = useState(false);
  const [aviso, setAviso] = useState(null);

  const nombreEstudiante = (id) => {
    const u = usuarios.find((x) => x.id === id);
    return u ? `${u.nombre} ${u.apellido}` : `Usuario #${id}`;
  };
  const emailEstudiante = (id) => usuarios.find((x) => x.id === id)?.email ?? '';
  const nombreCurso = (id) =>
    cursos.find((c) => c.id === id)?.nombre ?? `Curso #${id}`;

  const filas = inscripciones.filter((i) =>
    `${nombreEstudiante(i.usuarioId)} ${emailEstudiante(i.usuarioId)} ${nombreCurso(i.cursoId)}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  const handleEstado = async (inscripcion) => {
    setAviso(null);
    try {
      await cambiarEstado(inscripcion);
    } catch (err) {
      setAviso(getErrorMessage(err, 'No se pudo cambiar el estado.'));
    }
  };

  const handleEliminar = async (inscripcion) => {
    if (
      !window.confirm(
        `¿Eliminar la inscripción de ${nombreEstudiante(inscripcion.usuarioId)} en "${nombreCurso(inscripcion.cursoId)}"?`
      )
    ) {
      return;
    }
    setAviso(null);
    try {
      await eliminar(inscripcion.id);
    } catch (err) {
      setAviso(getErrorMessage(err, 'No se pudo eliminar la inscripción.'));
    }
  };

  const sinDatos = estudiantes.length === 0 || cursos.length === 0;

  return (
    <>
      <div className="admin-section-header">
        <div>
          <span className="admin-section-label">GESTIÓN</span>
          <h2>Inscripciones</h2>
        </div>
        <div className="admin-header-right">
          <span className="admin-count">
            {filas.length} {filas.length === 1 ? 'inscripción' : 'inscripciones'}
          </span>
          <button
            className="admin-new-button"
            disabled={sinDatos}
            title={sinDatos ? 'Hacen falta al menos un estudiante y un curso' : undefined}
            onClick={() => {
              setAviso(null);
              setCreando(true);
            }}
          >
            + Nueva inscripción
          </button>
        </div>
      </div>

      {aviso && <div className="ui-error" role="alert" style={{ marginBottom: 16 }}>{aviso}</div>}

      {loading && (
        <div className="admin-state">
          <div className="loader"></div>
          <p>Cargando inscripciones...</p>
        </div>
      )}

      {!loading && error && (
        <div className="admin-state error">
          <p>{error}</p>
        </div>
      )}

      {!loading && !error && filas.length === 0 && (
        <div className="admin-state">
          {search ? (
            <>
              <h3>No encontramos esa inscripción</h3>
              <p>Intenta buscar por estudiante o por curso.</p>
            </>
          ) : (
            <>
              <h3>Aún no hay inscripciones</h3>
              <p>Crea la primera con el botón "+ Nueva inscripción".</p>
            </>
          )}
        </div>
      )}

      {!loading && !error && filas.length > 0 && (
        <div className="admin-table-card">
          <table className="admin-table">
            <thead>
              <tr>
                <th>Estudiante</th>
                <th>Curso</th>
                <th>Fecha</th>
                <th>Estado</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filas.map((inscripcion) => (
                <tr key={inscripcion.id}>
                  <td>
                    <strong>{nombreEstudiante(inscripcion.usuarioId)}</strong>
                    <div className="admin-celda-detalle">
                      {emailEstudiante(inscripcion.usuarioId)}
                    </div>
                  </td>
                  <td>{nombreCurso(inscripcion.cursoId)}</td>
                  <td>{formatearFecha(inscripcion.fechaInscripcion)}</td>
                  <td>
                    <span className={`admin-estado-badge ${inscripcion.activa ? 'activo' : 'inactivo'}`}>
                      {inscripcion.activa ? 'Activa' : 'Inactiva'}
                    </span>
                  </td>
                  <td className="admin-actions">
                    <button onClick={() => handleEstado(inscripcion)}>
                      {inscripcion.activa ? 'Desactivar' : 'Activar'}
                    </button>
                    <button className="admin-delete-button" onClick={() => handleEliminar(inscripcion)}>
                      Eliminar
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <FormModal
        open={creando}
        title="Nueva inscripción"
        fields={[
          {
            name: 'usuarioId',
            label: 'Estudiante',
            type: 'select',
            numeric: true,
            required: true,
            options: estudiantes
              .filter((e) => e.activo)
              .map((e) => ({ value: e.id, label: `${e.nombre} ${e.apellido} (${e.email})` })),
          },
          {
            name: 'cursoId',
            label: 'Curso',
            type: 'select',
            numeric: true,
            required: true,
            options: cursos.map((c) => ({
              value: c.id,
              label: c.activo ? c.nombre : `${c.nombre} (inactivo)`,
            })),
          },
        ]}
        submitLabel="Inscribir"
        onSubmit={crear}
        onClose={() => setCreando(false)}
      />
    </>
  );
}
