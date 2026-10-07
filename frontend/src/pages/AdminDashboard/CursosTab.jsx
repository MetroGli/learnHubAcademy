import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAdminCursos } from '../../hooks/useAdminCatalogo';
import { getErrorMessage } from '../../api/errors';
import FormModal from '../../components/ui/FormModal';
import '../../components/ui/ui.css';

export default function CursosTab({ search }) {
  const { cursos, profesores, loading, error, crear, actualizar, eliminar } =
    useAdminCursos();
  const navigate = useNavigate();

  const [dialogo, setDialogo] = useState(null); // { modo, curso }
  const [aviso, setAviso] = useState(null);

  const nombreProfesor = (id) => {
    const profesor = profesores.find((p) => p.id === id);
    return profesor ? `${profesor.nombre} ${profesor.apellido}` : `Profesor #${id}`;
  };

  const filtrados = cursos.filter((curso) =>
    `${curso.nombre} ${nombreProfesor(curso.profesorId)}`
      .toLowerCase()
      .includes(search.toLowerCase())
  );

  const guardar = async ({ nombre, descripcion, profesorId, activo }) => {
    const payload = { nombre, descripcion, profesorId, activo };
    if (dialogo.modo === 'crear') await crear(payload);
    else await actualizar(dialogo.curso.id, payload);
  };

  const handleEliminar = async (curso) => {
    if (!window.confirm(`¿Eliminar el curso "${curso.nombre}" con sus módulos y lecciones?`)) {
      return;
    }
    setAviso(null);
    try {
      await eliminar(curso.id);
    } catch (err) {
      setAviso(
        getErrorMessage(
          err,
          'No se pudo eliminar el curso. Si tiene estudiantes inscritos o evaluaciones, desactívalo en lugar de eliminarlo.'
        )
      );
    }
  };

  return (
    <>
      <div className="admin-section-header">
        <div>
          <span className="admin-section-label">GESTIÓN</span>
          <h2>Cursos</h2>
        </div>
        <div className="admin-header-right">
          <span className="admin-count">
            {filtrados.length} {filtrados.length === 1 ? 'curso' : 'cursos'}
          </span>
          <button
            className="admin-new-button"
            disabled={profesores.length === 0}
            title={profesores.length === 0 ? 'Primero crea un usuario con rol Profesor' : undefined}
            onClick={() => {
              setAviso(null);
              setDialogo({ modo: 'crear', curso: null });
            }}
          >
            + Nuevo curso
          </button>
        </div>
      </div>

      {aviso && <div className="ui-error" role="alert" style={{ marginBottom: 16 }}>{aviso}</div>}

      {loading && (
        <div className="admin-state">
          <div className="loader"></div>
          <p>Cargando cursos...</p>
        </div>
      )}

      {!loading && error && (
        <div className="admin-state error">
          <p>{error}</p>
        </div>
      )}

      {!loading && !error && filtrados.length === 0 && (
        <div className="admin-state">
          {search ? (
            <>
              <h3>No encontramos ese curso</h3>
              <p>Intenta buscar con otro nombre.</p>
            </>
          ) : (
            <>
              <h3>Aún no hay cursos</h3>
              <p>
                {profesores.length === 0
                  ? 'Primero crea un usuario con rol Profesor para poder asignarle cursos.'
                  : 'Crea el primero con el botón "+ Nuevo curso".'}
              </p>
            </>
          )}
        </div>
      )}

      {!loading && !error && filtrados.length > 0 && (
        <div className="admin-table-card">
          <table className="admin-table">
            <thead>
              <tr>
                <th>Curso</th>
                <th>Profesor</th>
                <th>Estado</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filtrados.map((curso) => (
                <tr key={curso.id}>
                  <td>
                    <strong>{curso.nombre}</strong>
                    {curso.descripcion && (
                      <div className="admin-celda-detalle">
                        {curso.descripcion.length > 80
                          ? `${curso.descripcion.slice(0, 79)}…`
                          : curso.descripcion}
                      </div>
                    )}
                  </td>
                  <td>{nombreProfesor(curso.profesorId)}</td>
                  <td>
                    <span className={`admin-estado-badge ${curso.activo ? 'activo' : 'inactivo'}`}>
                      {curso.activo ? 'Activo' : 'Inactivo'}
                    </span>
                  </td>
                  <td className="admin-actions">
                    <button onClick={() => navigate(`/curso/${curso.id}`)}>Gestionar</button>
                    <button
                      onClick={() => {
                        setAviso(null);
                        setDialogo({ modo: 'editar', curso });
                      }}
                    >
                      Editar
                    </button>
                    <button className="admin-delete-button" onClick={() => handleEliminar(curso)}>
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
        open={Boolean(dialogo)}
        title={dialogo?.modo === 'crear' ? 'Nuevo curso' : 'Editar curso'}
        fields={[
          { name: 'nombre', label: 'Nombre del curso', required: true },
          { name: 'descripcion', label: 'Descripción', type: 'textarea', rows: 4 },
          {
            name: 'profesorId',
            label: 'Profesor responsable',
            type: 'select',
            numeric: true,
            required: true,
            options: profesores.map((p) => ({
              value: p.id,
              label: `${p.nombre} ${p.apellido}`,
            })),
          },
          { name: 'activo', label: 'Curso activo (visible para los estudiantes)', type: 'checkbox' },
        ]}
        initialValues={dialogo?.modo === 'editar' ? dialogo.curso : { activo: true }}
        onSubmit={guardar}
        onClose={() => setDialogo(null)}
      />
    </>
  );
}
