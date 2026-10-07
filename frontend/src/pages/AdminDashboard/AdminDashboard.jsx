import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import Header from '../../components/layout/Header';
import Footer from '../../components/layout/Footer';
import Tabs from '../../components/ui/Tabs';
import UsuariosTab from './UsuariosTab';
import CursosTab from './CursosTab';
import InscripcionesTab from './InscripcionesTab';
import '../../components/ui/ui.css';
import './AdminDashboard.css';

const PLACEHOLDERS = {
  usuarios: 'Buscar un usuario...',
  cursos: 'Buscar un curso...',
  inscripciones: 'Buscar por estudiante o curso...',
};

const TABS = [
  { id: 'usuarios', label: 'Usuarios' },
  { id: 'cursos', label: 'Cursos' },
  { id: 'inscripciones', label: 'Inscripciones' },
];

export default function AdminDashboard() {
  const { user, logout } = useAuth();
  const [tab, setTab] = useState('usuarios');
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
          onChange: (e) => setSearch(e.target.value),
          placeholder: PLACEHOLDERS[tab],
        }}
      />

      <main className="admin-main">
        <Tabs
          tabs={TABS}
          active={tab}
          onChange={(nuevo) => {
            setTab(nuevo);
            setSearch('');
          }}
        />

        {tab === 'usuarios' && <UsuariosTab search={search} />}
        {tab === 'cursos' && <CursosTab search={search} />}
        {tab === 'inscripciones' && <InscripcionesTab search={search} />}
      </main>

      <Footer />
    </div>
  );
}
