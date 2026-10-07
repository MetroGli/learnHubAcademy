import { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import './Login.css';

function LearnHubMark() {
  return (
    <svg
      className="learnhub-mark"
      width="72"
      height="72"
      viewBox="0 0 72 72"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      role="img"
      aria-label="LearnHub"
    >
      <circle cx="36" cy="36" r="34" className="mark-ring" />
      <path
        className="mark-book mark-book-left"
        d="M36 30 C29 26 20 25 14 27 V47 C20 45 29 46 36 50 Z"
      />
      <path
        className="mark-book mark-book-right"
        d="M36 30 C43 26 52 25 58 27 V47 C52 45 43 46 36 50 Z"
      />
      <path
        className="mark-cap"
        d="M36 14 L58 22 L36 30 L14 22 Z"
      />
      <line className="mark-cap-string" x1="49" y1="25.5" x2="49" y2="34" />
      <circle className="mark-cap-tassel" cx="49" cy="35.5" r="1.6" />
    </svg>
  );
}

export default function Login() {
  const { login, loading, error } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await login(email, password);
      const redirectTo = location.state?.from?.pathname || '/';
      navigate(redirectTo, { replace: true });
    } catch {
      // el error ya queda expuesto en el contexto (useAuth().error)
    }
  };

  return (
    <div className="login-page">
      <div className="login-glow login-glow-a" aria-hidden="true" />
      <div className="login-glow login-glow-b" aria-hidden="true" />
      <div className="login-glow login-glow-c" aria-hidden="true" />

      <form className="login-card" onSubmit={handleSubmit}>
        <div className="login-brand">
          <LearnHubMark />
          <div>
            <h1>LearnHub</h1>
            <p className="login-subtitle">Inicia sesión para continuar</p>
          </div>
        </div>

        <div className="login-field">
          <label htmlFor="email">Correo</label>
          <input
            id="email"
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
            autoComplete="email"
            placeholder="tucorreo@ejemplo.com"
          />
        </div>

        <div className="login-field">
          <label htmlFor="password">Contraseña</label>
          <input
            id="password"
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            autoComplete="current-password"
            placeholder="••••••••"
          />
        </div>

        {error && <p className="login-error">{error}</p>}

        <button type="submit" disabled={loading}>
          <span>{loading ? 'Ingresando…' : 'Ingresar'}</span>
        </button>
      </form>
    </div>
  );
}