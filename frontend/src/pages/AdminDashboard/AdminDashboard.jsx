import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import Header from '../../components/layout/Header';
import Footer from '../../components/layout/Footer';
import Tabs from '../../components/ui/Tabs';
import UsuariosTab from './UsuariosTab';
import CursosTab from './CursosTab';
import InscripcionesTab from './InscripcionesTab';
import ResumenTab from './ResumenTab';
import '../../components/ui/ui.css';
import './AdminDashboard.css';
import './AdminDiseno.css';

const PLACEHOLDERS = {
  resumen: 'Buscar un usuario...',
  usuarios: 'Buscar un usuario...',
  cursos: 'Buscar un curso...',
  inscripciones: 'Buscar por estudiante o curso...',
};

const TABS = [
  { id: 'resumen', label: 'Resumen' },
  { id: 'usuarios', label: 'Usuarios' },
  { id: 'cursos', label: 'Cursos' },
  { id: 'inscripciones', label: 'Inscripciones' },
];

export default function AdminDashboard() {
  const { user, logout } = useAuth();
  const [tab, setTab] = useState('resumen');

  const cambiarTab = (nuevo) => {
    setTab(nuevo);
    setSearch('');
  };
  const [search, setSearch] = useState('');

  return (
    <div className="admin-dashboard">
      <Header
        name={user?.nombre}
        subtitle="Panel de administrador"
        role={user?.rol}
        onLogout={logout}
        search={{
          value: search,
          onChange: (e) => {
            setSearch(e.target.value);
            if (tab === 'resumen') setTab('usuarios');
          },
          placeholder: PLACEHOLDERS[tab],
        }}
      />

      <main className="admin-main">
        <Tabs
          tabs={TABS}
          active={tab}
          onChange={cambiarTab}
        />

        {tab === 'resumen' && (
          <>
            <div className="lh-hero">
              <div>
                <span className="lh-hero-kicker">Administración</span>
                <h2>Resumen de la plataforma</h2>
                <p>Así va LearnHub Academy hoy.</p>
              </div>
            </div>
            <ResumenTab onIr={cambiarTab} />
          </>
        )}

        {tab === 'usuarios' && <UsuariosTab search={search} />}
        {tab === 'cursos' && <CursosTab search={search} />}
        {tab === 'inscripciones' && <InscripcionesTab search={search} />}
      </main>

      <Footer />
    </div>
  );
}
