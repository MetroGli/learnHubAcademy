import client from './client';

export const authService = {
  async login(email, password) {
    const { data } = await client.post('/api/auth/login', { email, password });
    return data;
  },

  logout() {
    localStorage.removeItem('learnhub_token');
    localStorage.removeItem('learnhub_user');
  },

  getStoredUser() {
    const raw = localStorage.getItem('learnhub_user');
    return raw ? JSON.parse(raw) : null;
  },
};
