import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import PageShell from '../../components/layout/PageShell';
import { EQUIPO } from '../../components/layout/Footercontent';
import '../../components/layout/PageShell.css';
import './Soporte.css';

const CORREO_SOPORTE = 'learnhub@gmail.com';

const MOTIVOS = [
  'Problema para ingresar a mi cuenta',
  'No veo mis clases o mis cursos',
  'Error en la plataforma',
  'Sugerencia o comentario',
  'Otro',
];

const ROL_ETIQUETA = {
  ESTUDIANTE: 'Estudiante',
  PROFESOR: 'Profesor',
  ADMINISTRADOR: 'Administrador',
};

export default function Soporte() {
  const { user } = useAuth();
  const [motivo, setMotivo] = useState(MOTIVOS[0]);
  const [mensaje, setMensaje] = useState('');

  const responsable = EQUIPO.find((persona) => persona.rol === 'Líder de Soporte');

  const handleSubmit = (e) => {
    e.preventDefault();

    const asunto = `[LearnHub] ${motivo}`;
    const cuerpo = [
      mensaje.trim(),
      '',
      '---',
      `Usuario: ${user?.nombre ?? ''} ${user?.apellido ?? ''}`.trim(),
      `Correo: ${user?.email ?? ''}`,
      `Rol: ${ROL_ETIQUETA[user?.rol] ?? user?.rol ?? ''}`,
    ].join('\n');

    // Abre el programa de correo del usuario con el mensaje ya redactado.
    window.location.href =
      `mailto:${CORREO_SOPORTE}` +
      `?subject=${encodeURIComponent(asunto)}` +
      `&body=${encodeURIComponent(cuerpo)}`;
  };

  return (
    <PageShell subtitle="Centro de soporte">
      <div className="page-title-block">
        <span className="page-label">RECURSOS</span>
        <h2>Centro de soporte</h2>
        <p>
          ¿Tienes un problema o una duda? Cuéntanos y te ayudamos. Antes de
          escribir, puede que la respuesta ya esté en las{' '}
          <Link to="/ayuda/faq">preguntas frecuentes</Link> o en el{' '}
          <Link to="/ayuda/manual">manual de usuario</Link>.
        </p>
      </div>

      <section className="page-card">
        <h3>Escríbenos</h3>
        <p className="page-card-hint">
          Al enviar se abrirá tu aplicación de correo con el mensaje listo para
          enviar a <strong>{CORREO_SOPORTE}</strong>.
        </p>

        <form className="soporte-form" onSubmit={handleSubmit}>
          <div className="soporte-field">
            <label htmlFor="motivo">Motivo</label>
            <select
              id="motivo"
              value={motivo}
              onChange={(e) => setMotivo(e.target.value)}
            >
              {MOTIVOS.map((opcion) => (
                <option key={opcion} value={opcion}>
                  {opcion}
                </option>
              ))}
            </select>
          </div>

          <div className="soporte-field">
            <label htmlFor="mensaje">Mensaje</label>
            <textarea
              id="mensaje"
              rows={6}
              value={mensaje}
              onChange={(e) => setMensaje(e.target.value)}
              placeholder="Describe qué pasó y en qué pantalla ocurrió."
              required
            />
          </div>

          <button
            type="submit"
            className="soporte-submit"
            disabled={!mensaje.trim()}
          >
            Enviar por correo
          </button>
        </form>
      </section>

      <section className="page-card">
        <h3>Otras formas de contacto</h3>
        <ul className="soporte-contact">
          <li>
            <span>Correo</span>
            <a href={`mailto:${CORREO_SOPORTE}`}>{CORREO_SOPORTE}</a>
          </li>
          {responsable && (
            <li>
              <span>Responsable de soporte</span>
              <strong>{responsable.nombre}</strong>
            </li>
          )}
        </ul>
      </section>
    </PageShell>
  );
}
