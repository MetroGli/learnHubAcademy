import { useState } from 'react';
import useAdminUsuarios from '../../hooks/useAdminUsuarios';
import { getErrorMessage } from '../../api/errors';

const FORM_VACIO = {
  nombre: '',
  apellido: '',
  email: '',
  password: '',
  rol: 'ESTUDIANTE',
};

export default function UsuariosTab({ search }) {
  const {
    usuarios,
    loading,
    error,
    crearUsuario,
    actualizarUsuario,
    eliminarUsuario,
  } = useAdminUsuarios();

  const [formVisible, setFormVisible] = useState(false);
  const [editandoId, setEditandoId] = useState(null);
  const [form, setForm] = useState(FORM_VACIO);
  const [formError, setFormError] = useState(null);
  const [guardando, setGuardando] = useState(false);

  const [rol, setRol] = useState(null);

  const usuariosFiltrados = usuarios.filter((usuario) => {
    const texto = `${usuario.nombre} ${usuario.apellido} ${usuario.email}`
      .toLowerCase();
    return texto.includes(search.toLowerCase()) && (!rol || usuario.rol === rol);
  });

  const conteo = (r) => usuarios.filter((u) => u.rol === r).length;

  const abrirFormularioCrear = () => {
    setEditandoId(null);
    setForm(FORM_VACIO);
    setFormError(null);
    setFormVisible(true);
  };

  const abrirFormularioEditar = (usuario) => {
    setEditandoId(usuario.id);
    setForm({
      nombre: usuario.nombre,
      apellido: usuario.apellido,
      email: usuario.email,
      password: '',
      rol: usuario.rol,
    });
    setFormError(null);
    setFormVisible(true);
  };

  const cerrarFormulario = () => {
    setFormVisible(false);
    setEditandoId(null);
    setForm(FORM_VACIO);
    setFormError(null);
  };

  const handleChange = (campo) => (e) => {
    setForm((actual) => ({ ...actual, [campo]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setFormError(null);
    setGuardando(true);

    try {
      if (editandoId) {
        await actualizarUsuario(editandoId, form);
      } else {
        await crearUsuario(form);
      }
      cerrarFormulario();
    } catch (err) {
      setFormError(
        getErrorMessage(err, 'No se pudo guardar el usuario. Revisa los datos.')
      );
    } finally {
      setGuardando(false);
    }
  };

  const handleEliminar = async (usuario) => {
    const confirmado = window.confirm(
      `¿Eliminar a ${usuario.nombre} ${usuario.apellido}?`
    );
    if (!confirmado) return;

    try {
      await eliminarUsuario(usuario.id);
    } catch (err) {
      console.error('Error eliminando usuario:', err);
      window.alert(
        getErrorMessage(
          err,
          'No se pudo eliminar el usuario. Si tiene inscripciones, cursos o notas asociados, no se puede borrar.'
        )
      );
    }
  };

  return (
    <>
        <div className="admin-section-header">
          <div>
            <span className="admin-section-label">GESTIÓN</span>
            <h2>Usuarios</h2>
          </div>
          <div className="admin-header-right">
            <span className="admin-count">
              {usuariosFiltrados.length}{' '}
              {usuariosFiltrados.length === 1 ? 'usuario' : 'usuarios'}
            </span>
            <button className="admin-new-button" onClick={abrirFormularioCrear}>
              + Nuevo usuario
            </button>
          </div>
        </div>

        {!loading && !error && (
          <div className="lh-filtros" role="group" aria-label="Filtrar por rol">
            {[
              [null, 'Todos', usuarios.length],
              ['ESTUDIANTE', 'Estudiantes', conteo('ESTUDIANTE')],
              ['PROFESOR', 'Profesores', conteo('PROFESOR')],
              ['ADMINISTRADOR', 'Administradores', conteo('ADMINISTRADOR')],
            ].map(([valor, etiqueta, n]) => (
              <button
                type="button"
                key={etiqueta}
                className={`lh-filtro${rol === valor ? ' activo' : ''}`}
                onClick={() => setRol(valor)}
              >
                {etiqueta} <span className="admin-filtro-n">{n}</span>
              </button>
            ))}
          </div>
        )}

        {loading && (
          <div className="admin-state">
            <div className="loader"></div>
            <p>Cargando usuarios...</p>
          </div>
        )}

        {!loading && error && (
          <div className="admin-state error">
            <p>{error}</p>
          </div>
        )}

        {!loading && !error && usuariosFiltrados.length === 0 && (
          <div className="admin-state">
            {search ? (
              <>
                <h3>No encontramos ese usuario</h3>
                <p>Intenta buscar con otro nombre o correo.</p>
              </>
            ) : (
              <>
                <h3>Aún no hay usuarios</h3>
                <p>Crea el primero con el botón "+ Nuevo usuario".</p>
              </>
            )}
          </div>
        )}

        {!loading && !error && usuariosFiltrados.length > 0 && (
          <div className="admin-table-card">
            <table className="admin-table">
              <thead>
                <tr>
                  <th>Nombre</th>
                  <th>Email</th>
                  <th>Rol</th>
                  <th>Estado</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {usuariosFiltrados.map((usuario) => (
                  <tr key={usuario.id}>
                    <td>
                      <div className="admin-user-cell">
                        <span className={`lh-avatar admin-avatar-${usuario.rol?.toLowerCase()}`}>
                          {`${usuario.nombre?.charAt(0) ?? ''}${usuario.apellido?.charAt(0) ?? ''}`.toUpperCase()}
                        </span>
                        <strong>
                          {usuario.nombre} {usuario.apellido}
                        </strong>
                      </div>
                    </td>
                    <td>{usuario.email}</td>
                    <td>
                      <span
                        className={`admin-rol-badge rol-${usuario.rol?.toLowerCase()}`}
                      >
                        {usuario.rol}
                      </span>
                    </td>
                    <td>
                      <span
                        className={`admin-estado-badge ${
                          usuario.activo ? 'activo' : 'inactivo'
                        }`}
                      >
                        {usuario.activo ? 'Activo' : 'Inactivo'}
                      </span>
                    </td>
                    <td className="admin-actions">
                      <button onClick={() => abrirFormularioEditar(usuario)}>
                        Editar
                      </button>
                      <button
                        className="admin-delete-button"
                        onClick={() => handleEliminar(usuario)}
                      >
                        Eliminar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      {formVisible && (
        <div className="admin-modal-overlay" onClick={cerrarFormulario}>
          <div className="admin-modal" onClick={(e) => e.stopPropagation()}>
            <h3>{editandoId ? 'Editar usuario' : 'Nuevo usuario'}</h3>

            <form onSubmit={handleSubmit}>
              <label>
                Nombre
                <input
                  type="text"
                  value={form.nombre}
                  onChange={handleChange('nombre')}
                  required
                />
              </label>

              <label>
                Apellido
                <input
                  type="text"
                  value={form.apellido}
                  onChange={handleChange('apellido')}
                  required
                />
              </label>

              <label>
                Email
                <input
                  type="email"
                  value={form.email}
                  onChange={handleChange('email')}
                  required
                />
              </label>

              <label>
                Contraseña
                <input
                  type="password"
                  value={form.password}
                  onChange={handleChange('password')}
                  placeholder={
                    editandoId ? 'Requerida para guardar cambios' : ''
                  }
                  minLength={8}
                  required
                />
              </label>

              <label>
                Rol
                <select value={form.rol} onChange={handleChange('rol')}>
                  <option value="ESTUDIANTE">Estudiante</option>
                  <option value="PROFESOR">Profesor</option>
                  <option value="ADMINISTRADOR">Administrador</option>
                </select>
              </label>

              {formError && <p className="admin-form-error">{formError}</p>}

              <div className="admin-form-actions">
                <button
                  type="button"
                  className="admin-cancel-button"
                  onClick={cerrarFormulario}
                >
                  Cancelar
                </button>
                <button type="submit" disabled={guardando}>
                  {guardando ? 'Guardando...' : 'Guardar'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </>
  );
}