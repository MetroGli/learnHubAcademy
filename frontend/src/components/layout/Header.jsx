import { useEffect, useRef, useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import LogoMark from '../ui/LogoMark';
import './Header.css';

const ROL_ETIQUETA = {
  ESTUDIANTE: 'Estudiante',
  PROFESOR: 'Profesor',
  ADMINISTRADOR: 'Administrador',
};

export default function Header({
  welcomeLabel = 'Bienvenido',
  name,
  subtitle,
  role,
  onLogout,
  search,
  actions,
}) {
  const auth = useAuth();
  const [menuAbierto, setMenuAbierto] = useState(false);
  const menuRef = useRef(null);

  const cerrarSesion = onLogout ?? auth.logout;

  // Cierra el menú al hacer clic fuera o al presionar Escape.
  useEffect(() => {
    if (!menuAbierto) return undefined;

    function alHacerClic(e) {
      if (menuRef.current && !menuRef.current.contains(e.target)) {
        setMenuAbierto(false);
      }
    }
    function alPresionarTecla(e) {
      if (e.key === 'Escape') setMenuAbierto(false);
    }

    document.addEventListener('mousedown', alHacerClic);
    document.addEventListener('keydown', alPresionarTecla);
    return () => {
      document.removeEventListener('mousedown', alHacerClic);
      document.removeEventListener('keydown', alPresionarTecla);
    };
  }, [menuAbierto]);

  const cerrarMenu = () => setMenuAbierto(false);

  const handleLogout = () => {
    cerrarMenu();
    cerrarSesion();
  };

  return (
    <header className="lh-header">
      <div className="lh-header-inner">
        <div className="lh-header-brand">
          <Link to="/" className="lh-brand-link">
            <LogoMark size={38} />
            <span className="lh-brand-name">LearnHub</span>
          </Link>

          <div className="lh-header-divider" />

          <div className="lh-header-identity">
            <span className="lh-header-label">{welcomeLabel}</span>
            <h1>{name}</h1>
            {subtitle && <small>{subtitle}</small>}
          </div>
        </div>

        <div className="lh-header-actions">
          {search && (
            <div className="lh-search-box">
              <span className="lh-search-icon">⌕</span>
              <input
                type="text"
                placeholder={search.placeholder || 'Buscar...'}
                value={search.value}
                onChange={search.onChange}
              />
            </div>
          )}

          {actions}

          <div className="lh-user-menu" ref={menuRef}>
            <div className="lh-user-avatar">
              {name?.charAt(0).toUpperCase()}
            </div>

            <div className="lh-user-info">
              <strong>{name}</strong>
              {role && <span>{ROL_ETIQUETA[role] ?? role}</span>}
            </div>

            <button
              type="button"
              className="lh-menu-trigger"
              onClick={() => setMenuAbierto((abierto) => !abierto)}
              aria-haspopup="menu"
              aria-expanded={menuAbierto}
              aria-label="Abrir menú de usuario"
              title="Menú de usuario"
            >
              ⋮
            </button>

            {menuAbierto && (
              <div className="lh-dropdown" role="menu">
                <div className="lh-dropdown-head">
                  <strong>{name}</strong>
                  {role && <span>{ROL_ETIQUETA[role] ?? role}</span>}
                </div>

                <Link
                  to="/perfil"
                  className="lh-dropdown-item"
                  role="menuitem"
                  onClick={cerrarMenu}
                >
                  Mi perfil y configuración
                </Link>

                <Link
                  to="/ayuda/faq"
                  className="lh-dropdown-item"
                  role="menuitem"
                  onClick={cerrarMenu}
                >
                  Ayuda
                </Link>

                <div className="lh-dropdown-separator" />

                <button
                  type="button"
                  className="lh-dropdown-item lh-dropdown-danger"
                  role="menuitem"
                  onClick={handleLogout}
                >
                  Cerrar sesión
                </button>
              </div>
            )}
          </div>
        </div>
      </div>

      <div className="lh-header-accent" />
    </header>
  );
}
