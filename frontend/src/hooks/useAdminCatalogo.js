import { useCallback, useEffect, useState } from 'react';
import {
  cursoService,
  inscripcionService,
  usuarioService,
} from '../api/resources';

/** Cursos (con sus profesores) para la pestaña "Cursos" del administrador. */
export function useAdminCursos() {
  const [cursos, setCursos] = useState([]);
  const [profesores, setProfesores] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const cargar = useCallback(async () => {
    try {
      setError(null);
      const [allCursos, usuarios] = await Promise.all([
        cursoService.listar(),
        usuarioService.listar(),
      ]);
      setCursos(allCursos);
      setProfesores(usuarios.filter((u) => u.rol === 'PROFESOR'));
    } catch (err) {
      console.error('Error cargando cursos:', err);
      setError('No se pudieron cargar los cursos.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    cargar();
  }, [cargar]);

  const crear = async (payload) => {
    await cursoService.crear(payload);
    await cargar();
  };

  const actualizar = async (id, payload) => {
    await cursoService.actualizar(id, payload);
    await cargar();
  };

  const eliminar = async (id) => {
    await cursoService.eliminar(id);
    await cargar();
  };

  return { cursos, profesores, loading, error, crear, actualizar, eliminar };
}

/** Inscripciones, con estudiantes y cursos, para la pestaña "Inscripciones". */
export function useAdminInscripciones() {
  const [inscripciones, setInscripciones] = useState([]);
  const [usuarios, setUsuarios] = useState([]);
  const [cursos, setCursos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const cargar = useCallback(async () => {
    try {
      setError(null);
      const [allInscripciones, allUsuarios, allCursos] = await Promise.all([
        inscripcionService.listar(),
        usuarioService.listar(),
        cursoService.listar(),
      ]);
      setInscripciones(allInscripciones);
      setUsuarios(allUsuarios);
      setCursos(allCursos);
    } catch (err) {
      console.error('Error cargando inscripciones:', err);
      setError('No se pudieron cargar las inscripciones.');
    } finally {
      setLoading(false);
    }
  }, []);

  useEffect(() => {
    cargar();
  }, [cargar]);

  // El backend no evita inscripciones repetidas, así que se revisa aquí.
  const crear = async ({ usuarioId, cursoId }) => {
    const existente = inscripciones.find(
      (i) => i.usuarioId === usuarioId && i.cursoId === cursoId
    );

    if (existente) {
      if (existente.activa) {
        throw new Error('Ese estudiante ya está inscrito en el curso.');
      }
      await inscripcionService.actualizar(existente.id, {
        usuarioId,
        cursoId,
        activa: true,
      });
    } else {
      await inscripcionService.crear({ usuarioId, cursoId, activa: true });
    }
    await cargar();
  };

  const cambiarEstado = async (inscripcion) => {
    await inscripcionService.actualizar(inscripcion.id, {
      usuarioId: inscripcion.usuarioId,
      cursoId: inscripcion.cursoId,
      activa: !inscripcion.activa,
    });
    await cargar();
  };

  const eliminar = async (id) => {
    await inscripcionService.eliminar(id);
    await cargar();
  };

  return {
    inscripciones,
    estudiantes: usuarios.filter((u) => u.rol === 'ESTUDIANTE'),
    usuarios,
    cursos,
    loading,
    error,
    crear,
    cambiarEstado,
    eliminar,
  };
}
