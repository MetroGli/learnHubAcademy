import client from './client';

// Versiones anteriores guardaban el token aquí; se borra si quedó alguno.
localStorage.removeItem('learnhub_token');

export const authService = {
  async login(email, password) {
    const { data } = await client.post('/api/auth/login', { email, password });
    return data;
  },

  async logout() {
    localStorage.removeItem('learnhub_user');
    try {
      await client.post('/api/auth/logout');
    } catch {
      // Si falla, la cookie igual vence sola.
    }
  },

  getStoredUser() {
    const raw = localStorage.getItem('learnhub_user');
    return raw ? JSON.parse(raw) : null;
  },
};
