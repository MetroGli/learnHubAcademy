import { useAuth } from '../../context/AuthContext';
import Header from './Header';
import Footer from './Footer';
import './PageShell.css';

/**
 * Estructura común de las páginas secundarias (perfil, ayuda, soporte):
 * Header arriba, contenido en el centro y Footer siempre al fondo.
 */
export default function PageShell({ subtitle, children }) {
  const { user, logout } = useAuth();

  return (
    <div className="page-shell">
      <Header
        name={user?.nombre}
        subtitle={subtitle}
        role={user?.rol}
        onLogout={logout}
      />

      <main className="page-shell-main">{children}</main>

      <Footer />
    </div>
  );
}
