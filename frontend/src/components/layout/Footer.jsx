import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import LogoMark from '../ui/LogoMark';
import Modal from './Modal';
import { EQUIPO, TERMINOS_TEXTO, PRIVACIDAD_TEXTO } from './Footercontent';
import './Footer.css';

// tipo: 'ruta' -> <Link>, 'modal' -> abre un popup, 'mailto' -> <a href="mailto:...">
// Los enlaces de "Producto" dependen del rol para no mandar a nadie a una pantalla que no puede ver.
function enlacesProducto(role) {
  const principal =
    role === 'ADMINISTRADOR'
      ? { texto: 'Panel de administrador', tipo: 'ruta', valor: '/admin' }
      : role === 'PROFESOR'
        ? { texto: 'Panel docente', tipo: 'ruta', valor: '/docente' }
        : { texto: 'Mis clases', tipo: 'ruta', valor: '/' };

  return [principal, { texto: 'Mi perfil', tipo: 'ruta', valor: '/perfil' }];
}

function construirColumnas(role) {
  return [
    {
      titulo: 'Producto',
      enlaces: enlacesProducto(role),
    },
    {
      titulo: 'Recursos',
      enlaces: [
        { texto: 'Manual de usuario', tipo: 'ruta', valor: '/ayuda/manual' },
        { texto: 'Preguntas frecuentes', tipo: 'ruta', valor: '/ayuda/faq' },
        { texto: 'Centro de soporte', tipo: 'ruta', valor: '/soporte' },
      ],
    },
    {
      titulo: 'Equipo de trabajo',
      enlaces: [
        { texto: 'Quiénes somos', tipo: 'modal', valor: 'nosotros' },
        { texto: 'Contacto', tipo: 'mailto', valor: 'learnhub@gmail.com' },
      ],
    },
    {
      titulo: 'Legal',
      enlaces: [
        { texto: 'Términos de uso', tipo: 'modal', valor: 'terminos' },
        { texto: 'Política de privacidad', tipo: 'modal', valor: 'privacidad' },
      ],
    },
  ];
}

export default function Footer() {
  const { role } = useAuth();
  const [modalAbierto, setModalAbierto] = useState(null); // null | 'nosotros' | 'terminos' | 'privacidad'
  const columnas = construirColumnas(role);

  return (
    <footer className="lh-footer">
      <div className="lh-footer-top">
        <div className="lh-footer-brand">
          <div className="lh-footer-brand-header">
            <LogoMark size={32} animated={false} />
            <span className="lh-footer-brand-name">LearnHub</span>
          </div>
          <p>
            Plataforma para la gestión de cursos, estudiantes, profesores y
            evaluaciones.
          </p>
        </div>

        {columnas.map((columna) => (
          <div className="lh-footer-column" key={columna.titulo}>
            <h4>{columna.titulo}</h4>
            <ul>
              {columna.enlaces.map((enlace) => (
                <li key={enlace.texto}>
                  {enlace.tipo === 'ruta' && (
                    <Link to={enlace.valor}>{enlace.texto}</Link>
                  )}
                  {enlace.tipo === 'modal' && (
                    <button
                      type="button"
                      className="lh-footer-link-btn"
                      onClick={() => setModalAbierto(enlace.valor)}
                    >
                      {enlace.texto}
                    </button>
                  )}
                  {enlace.tipo === 'mailto' && (
                    <a href={`mailto:${enlace.valor}`}>{enlace.texto}</a>
                  )}
                </li>
              ))}
            </ul>
          </div>
        ))}
      </div>

      <div className="lh-footer-bottom">
        <span>LearnHub</span>
        <span>© 2026 Todos los derechos reservados.</span>
      </div>

      <Modal
        open={modalAbierto === 'nosotros'}
        title="Quiénes somos"
        onClose={() => setModalAbierto(null)}
      >
        <p>El equipo detrás de LearnHub:</p>
        <ul className="lh-team-list">
          {EQUIPO.map((persona) => (
            <li className="lh-team-item" key={persona.nombre}>
              <strong>{persona.nombre}</strong>
              <span>{persona.rol}</span>
            </li>
          ))}
        </ul>
      </Modal>

      <Modal
        open={modalAbierto === 'terminos'}
        title="Términos de uso"
        onClose={() => setModalAbierto(null)}
      >
        <p style={{ whiteSpace: 'pre-line' }}>{TERMINOS_TEXTO}</p>
      </Modal>

      <Modal
        open={modalAbierto === 'privacidad'}
        title="Política de privacidad"
        onClose={() => setModalAbierto(null)}
      >
        <p style={{ whiteSpace: 'pre-line' }}>{PRIVACIDAD_TEXTO}</p>
      </Modal>
    </footer>
  );
}