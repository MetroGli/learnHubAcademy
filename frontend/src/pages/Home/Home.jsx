import { useAuth } from '../../context/AuthContext';
import Dashboard from '../Dashboard/Dashboard';
import TeacherDashboard from '../TeacherDashboard/TeacherDashboard';
import AdminDashboard from '../AdminDashboard/AdminDashboard';

export default function Home() {
  const { role } = useAuth();

  if (role === 'PROFESOR') {
    return <TeacherDashboard />;
  }

  if (role === 'ADMINISTRADOR') {
    return <AdminDashboard />;
  }

  return <Dashboard />;
}