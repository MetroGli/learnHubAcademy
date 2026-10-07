import client from './client';
export function createCrudService(resourcePath) {
  return {
    listar: async () => {
      const { data } = await client.get(resourcePath);
      return data;
    },
    buscarPorId: async (id) => {
      const { data } = await client.get(`${resourcePath}/${id}`);
      return data;
    },
    crear: async (payload) => {
      const { data } = await client.post(resourcePath, payload);
      return data;
    },
    actualizar: async (id, payload) => {
      const { data } = await client.put(`${resourcePath}/${id}`, payload);
      return data;
    },
    eliminar: async (id) => {
      await client.delete(`${resourcePath}/${id}`);
    },
  };
}
