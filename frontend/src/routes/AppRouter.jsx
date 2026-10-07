import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import ProtectedRoute from './ProtectedRoute';
import Login from '../pages/Login/Login';
import Home from '../pages/Home/Home';
import Curso from '../pages/Curso/Curso';
import TeacherDashboard from '../pages/TeacherDashboard/TeacherDashboard';
import AdminDashboard from '../pages/AdminDashboard/AdminDashboard';
import Perfil from '../pages/Perfil/Perfil';
import Manual from '../pages/Ayuda/Manual';
import Faq from '../pages/Ayuda/Faq';
import Soporte from '../pages/Soporte/Soporte';

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />

        {/* Cualquier usuario con sesión */}
        <Route element={<ProtectedRoute />}>
          <Route path="/" element={<Home />} />
          <Route path="/curso/:id" element={<Curso />} />
          <Route path="/perfil" element={<Perfil />} />
          <Route path="/ayuda/manual" element={<Manual />} />
          <Route path="/ayuda/faq" element={<Faq />} />
          <Route path="/soporte" element={<Soporte />} />
        </Route>

        {/* Solo profesores */}
        <Route element={<ProtectedRoute roles={['PROFESOR']} />}>
          <Route path="/docente" element={<TeacherDashboard />} />
        </Route>

        {/* Solo administradores */}
        <Route element={<ProtectedRoute roles={['ADMINISTRADOR']} />}>
          <Route path="/admin" element={<AdminDashboard />} />
        </Route>

        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  );
}
