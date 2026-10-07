import { useCallback, useEffect, useMemo, useState } from 'react';
import { calificacionService, progresoService } from '../api/resources';
import { useAuth } from '../context/AuthContext';
import { leerCompletadas, guardarCompletadas } from '../utils/completadas';

/**
 * Avance del estudiante en un curso: porcentaje (tabla progresos),
 * notas (tabla calificaciones) y lecciones marcadas como completadas.
 */
export default function useMiAvance(cursoId, lecciones, evaluaciones, activo) {
  const { user } = useAuth();
  const usuarioId = user?.id;
  const cursoIdNum = Number(cursoId);

  const [progreso, setProgreso] = useState(null);
  const [todasCalificaciones, setTodasCalificaciones] = useState([]);
  const [completadas, setCompletadas] = useState(() =>
    leerCompletadas(usuarioId, cursoIdNum)
  );
  const [loading, setLoading] = useState(Boolean(activo));
  const [error, setError] = useState(null);

  useEffect(() => {
    if (!activo || !usuarioId) {
      setLoading(false);
      return;
    }

    let cancelado = false;

    const cargar = async () => {
      try {
        setLoading(true);
        const [progresos, calificaciones] = await Promise.all([
          progresoService.listar(),
          calificacionService.listar(),
        ]);
        if (cancelado) return;

        setProgreso(
          progresos.find(
            (p) => p.usuarioId === usuarioId && p.cursoId === cursoIdNum
          ) ?? null
        );
        setTodasCalificaciones(
          calificaciones.filter((c) => c.usuarioId === usuarioId)
        );
        setCompletadas(leerCompletadas(usuarioId, cursoIdNum));
      } catch (err) {
        console.error('Error cargando tu avance:', err);
        if (!cancelado) setError('No se pudo cargar tu avance.');
      } finally {
        if (!cancelado) setLoading(false);
      }
    };

    cargar();
    return () => {
      cancelado = true;
    };
  }, [activo, usuarioId, cursoIdNum]);

  // Solo las notas de las evaluaciones de este curso.
  const calificaciones = useMemo(() => {
    const ids = new Set(evaluaciones.map((e) => e.id));
    return todasCalificaciones.filter((c) => ids.has(c.evaluacionId));
  }, [todasCalificaciones, evaluaciones]);

  const leccionesCompletadas = useMemo(
    () => lecciones.filter((l) => completadas.has(l.id)).length,
    [lecciones, completadas]
  );

  const guardarProgreso = useCallback(
    async (porcentaje) => {
      const payload = { usuarioId, cursoId: cursoIdNum, porcentaje };
      const guardado = progreso
        ? await progresoService.actualizar(progreso.id, payload)
        : await progresoService.crear(payload);
      setProgreso(guardado);
    },
    [progreso, usuarioId, cursoIdNum]
  );

  const toggleLeccion = useCallback(
    async (leccionId) => {
      const siguiente = new Set(completadas);
      if (siguiente.has(leccionId)) siguiente.delete(leccionId);
      else siguiente.add(leccionId);

      const total = lecciones.length;
      const hechas = lecciones.filter((l) => siguiente.has(l.id)).length;
      const porcentaje = total > 0 ? Math.round((hechas / total) * 100) : 0;

      setCompletadas(siguiente);
      guardarCompletadas(usuarioId, cursoIdNum, siguiente);

      try {
        await guardarProgreso(porcentaje);
      } catch (err) {
        // Se revierte para que la pantalla no muestre algo que no se guardó.
        setCompletadas(completadas);
        guardarCompletadas(usuarioId, cursoIdNum, completadas);
        throw err;
      }
    },
    [completadas, lecciones, guardarProgreso, usuarioId, cursoIdNum]
  );

  const registrarNota = useCallback(
    async (evaluacionId, nota) => {
      const existente = todasCalificaciones.find(
        (c) => c.evaluacionId === evaluacionId
      );
      const payload = { usuarioId, evaluacionId, nota };

      const guardada = existente
        ? await calificacionService.actualizar(existente.id, payload)
        : await calificacionService.crear(payload);

      setTodasCalificaciones((actuales) => [
        ...actuales.filter((c) => c.evaluacionId !== evaluacionId),
        guardada,
      ]);
    },
    [todasCalificaciones, usuarioId]
  );

  return {
    progreso,
    porcentaje: progreso?.porcentaje ?? 0,
    calificaciones,
    completadas,
    leccionesCompletadas,
    loading,
    error,
    toggleLeccion,
    registrarNota,
  };
}
