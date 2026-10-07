import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import { perfilService } from '../../api/perfil.service';
import PageShell from '../../components/layout/PageShell';
import '../../components/layout/PageShell.css';
import './Perfil.css';

const ROL_ETIQUETA = {
  ESTUDIANTE: 'Estudiante',
  PROFESOR: 'Profesor',
  ADMINISTRADOR: 'Administrador',
};

function mensajeDeError(err, porDefecto) {
  return err.response?.data?.message || porDefecto;
}

export default function Perfil() {
  const { user, updateUser } = useAuth();

  // ----- Datos personales -----
  const [nombre, setNombre] = useState(user?.nombre ?? '');
  const [apellido, setApellido] = useState(user?.apellido ?? '');
  const [guardando, setGuardando] = useState(false);
  const [avisoDatos, setAvisoDatos] = useState(null); // { tipo, texto }

  const datosSinCambios =
    nombre.trim() === (user?.nombre ?? '') &&
    apellido.trim() === (user?.apellido ?? '');

  const guardarDatos = async (e) => {
    e.preventDefault();
    setAvisoDatos(null);

    if (!nombre.trim() || !apellido.trim()) {
      setAvisoDatos({ tipo: 'error', texto: 'El nombre y el apellido son obligatorios.' });
      return;
    }

    setGuardando(true);
    try {
      const actualizado = await perfilService.actualizar({
        nombre: nombre.trim(),
        apellido: apellido.trim(),
      });
      updateUser({ nombre: actualizado.nombre, apellido: actualizado.apellido });
      setNombre(actualizado.nombre);
      setApellido(actualizado.apellido);
      setAvisoDatos({ tipo: 'ok', texto: 'Tus datos se guardaron correctamente.' });
    } catch (err) {
      setAvisoDatos({
        tipo: 'error',
        texto: mensajeDeError(err, 'No se pudieron guardar los datos. Intenta de nuevo.'),
      });
    } finally {
      setGuardando(false);
    }
  };

  // ----- Contraseña -----
  const [passwordActual, setPasswordActual] = useState('');
  const [passwordNueva, setPasswordNueva] = useState('');
  const [passwordRepetida, setPasswordRepetida] = useState('');
  const [cambiando, setCambiando] = useState(false);
  const [avisoPassword, setAvisoPassword] = useState(null);

  const cambiarPassword = async (e) => {
    e.preventDefault();
    setAvisoPassword(null);

    if (passwordNueva.length < 8) {
      setAvisoPassword({ tipo: 'error', texto: 'La nueva contraseña debe tener al menos 8 caracteres.' });
      return;
    }
    if (passwordNueva !== passwordRepetida) {
      setAvisoPassword({ tipo: 'error', texto: 'Las contraseñas nuevas no coinciden.' });
      return;
    }

    setCambiando(true);
    try {
      await perfilService.cambiarPassword({ passwordActual, passwordNueva });
      setPasswordActual('');
      setPasswordNueva('');
      setPasswordRepetida('');
      setAvisoPassword({ tipo: 'ok', texto: 'Tu contraseña se cambió correctamente.' });
    } catch (err) {
      setAvisoPassword({
        tipo: 'error',
        texto: mensajeDeError(err, 'No se pudo cambiar la contraseña. Intenta de nuevo.'),
      });
    } finally {
      setCambiando(false);
    }
  };

  return (
    <PageShell subtitle="Mi perfil">
      <div className="page-title-block">
        <span className="page-label">CUENTA</span>
        <h2>Mi perfil y configuración</h2>
        <p>Actualiza tus datos personales y la contraseña de tu cuenta.</p>
      </div>

      <section className="page-card">
        <h3>Datos personales</h3>
        <p className="page-card-hint">
          El correo y el rol los gestiona un administrador y no se pueden
          cambiar aquí.
        </p>

        <form className="perfil-form" onSubmit={guardarDatos}>
          <div className="perfil-grid">
            <div className="perfil-field">
              <label htmlFor="nombre">Nombre</label>
              <input
                id="nombre"
                type="text"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
                maxLength={100}
                autoComplete="given-name"
              />
            </div>

            <div className="perfil-field">
              <label htmlFor="apellido">Apellido</label>
              <input
                id="apellido"
                type="text"
                value={apellido}
                onChange={(e) => setApellido(e.target.value)}
                maxLength={100}
                autoComplete="family-name"
              />
            </div>

            <div className="perfil-field">
              <label htmlFor="email">Correo</label>
              <input id="email" type="email" value={user?.email ?? ''} disabled />
            </div>

            <div className="perfil-field">
              <label htmlFor="rol">Rol</label>
              <input
                id="rol"
                type="text"
                value={ROL_ETIQUETA[user?.rol] ?? user?.rol ?? ''}
                disabled
              />
            </div>
          </div>

          {avisoDatos && (
            <div className={`perfil-aviso perfil-aviso-${avisoDatos.tipo}`} role="status">
              {avisoDatos.texto}
            </div>
          )}

          <button
            type="submit"
            className="perfil-submit"
            disabled={guardando || datosSinCambios}
          >
            {guardando ? 'Guardando...' : 'Guardar cambios'}
          </button>
        </form>
      </section>

      <section className="page-card">
        <h3>Cambiar contraseña</h3>
        <p className="page-card-hint">Usa al menos 8 caracteres.</p>

        <form className="perfil-form" onSubmit={cambiarPassword}>
          <div className="perfil-field">
            <label htmlFor="passwordActual">Contraseña actual</label>
            <input
              id="passwordActual"
              type="password"
              value={passwordActual}
              onChange={(e) => setPasswordActual(e.target.value)}
              autoComplete="current-password"
              required
            />
          </div>

          <div className="perfil-grid">
            <div className="perfil-field">
              <label htmlFor="passwordNueva">Nueva contraseña</label>
              <input
                id="passwordNueva"
                type="password"
                value={passwordNueva}
                onChange={(e) => setPasswordNueva(e.target.value)}
                autoComplete="new-password"
                required
              />
            </div>

            <div className="perfil-field">
              <label htmlFor="passwordRepetida">Repite la nueva contraseña</label>
              <input
                id="passwordRepetida"
                type="password"
                value={passwordRepetida}
                onChange={(e) => setPasswordRepetida(e.target.value)}
                autoComplete="new-password"
                required
              />
            </div>
          </div>

          {avisoPassword && (
            <div className={`perfil-aviso perfil-aviso-${avisoPassword.tipo}`} role="status">
              {avisoPassword.texto}
            </div>
          )}

          <button type="submit" className="perfil-submit" disabled={cambiando}>
            {cambiando ? 'Cambiando...' : 'Cambiar contraseña'}
          </button>
        </form>
      </section>
    </PageShell>
  );
}
