import { useCallback, useEffect, useState } from 'react';
import { usuarioService } from '../api/resources';

export default function useAdminUsuarios() {
  const [usuarios, setUsuarios] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const cargarUsuarios = useCallback(async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await usuarioService.listar();
      setUsuarios(data);
    } catch (err) {
      console.error('Error cargando usuarios:', err);
      setError('No se pudieron cargar los usuarios.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    cargarUsuarios();
  }, [cargarUsuarios]);

  const crearUsuario = async (payload) => {
    await usuarioService.crear(payload);
    await cargarUsuarios();
  };

  const actualizarUsuario = async (id, payload) => {
    await usuarioService.actualizar(id, payload);
    await cargarUsuarios();
  };

  const eliminarUsuario = async (id) => {
    await usuarioService.eliminar(id);
    await cargarUsuarios();
  };

  return {
    usuarios,
    loading,
    error,
    crearUsuario,
    actualizarUsuario,
    eliminarUsuario,
  };
}