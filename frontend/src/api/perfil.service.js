import client from './client';

export const perfilService = {
  async actualizar({ nombre, apellido }) {
    const { data } = await client.put('/api/perfil', { nombre, apellido });
    return data;
  },

  async cambiarPassword({ passwordActual, passwordNueva }) {
    const { data } = await client.put('/api/perfil/password', {
      passwordActual,
      passwordNueva,
    });
    return data;
  },
};
