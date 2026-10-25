import { createContext, useContext, useState, useCallback } from 'react';
import { authService } from '../api/auth.service';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => authService.getStoredUser());
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const login = useCallback(async (email, password) => {
    setLoading(true);
    setError(null);
    try {
      const userData = await authService.login(email, password);
      localStorage.setItem('learnhub_user', JSON.stringify(userData));
      setUser(userData);
      return userData;
    } catch (err) {
      const message =
        err.response?.status === 401 || err.response?.status === 403
          ? 'Correo o contraseña incorrectos.'
          : 'No se pudo iniciar sesión. Intenta de nuevo.';
      setError(message);
      throw err;
    } finally {
      setLoading(false);
    }
  }, []);

  const logout = useCallback(() => {
    authService.logout();
    setUser(null);
  }, []);

  // Actualiza los datos del usuario en sesión (p. ej. tras editar el perfil).
  const updateUser = useCallback((changes) => {
    const actual = authService.getStoredUser() ?? {};
    const siguiente = { ...actual, ...changes };
    localStorage.setItem('learnhub_user', JSON.stringify(siguiente));
    setUser(siguiente);
  }, []);

  const value = {
    user,
    isAuthenticated: !!user,
    role: user?.rol ?? null,
    login,
    logout,
    updateUser,
    loading,
    error,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const ctx = useContext(AuthContext);
  if (!ctx) throw new Error('useAuth debe usarse dentro de <AuthProvider>');
  return ctx;
}
