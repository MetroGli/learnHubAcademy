import { useCallback, useEffect, useMemo, useState } from 'react';
import { calificacionService } from '../api/resources';
import { avanceService } from '../api/avance.service';
import { useAuth } from '../context/AuthContext';

/**
 * Avance del estudiante en un curso. Las lecciones completadas, el porcentaje
 * y las notas se guardan y calculan en el servidor.
 */
export default function useMiAvance(cursoId, lecciones, evaluaciones, activo) {
  const { user } = useAuth();
  const usuarioId = user?.id;

  const [avance, setAvance] = useState(null);
  const [todasCalificaciones, setTodasCalificaciones] = useState([]);
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
        const [datosAvance, calificaciones] = await Promise.all([
          avanceService.delCurso(cursoId),
          calificacionService.listar(),
        ]);
        if (cancelado) return;

        setAvance(datosAvance);
        setTodasCalificaciones(calificaciones.filter((c) => c.usuarioId === usuarioId));
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
  }, [activo, usuarioId, cursoId]);

  const completadas = useMemo(
    () => new Set(avance?.leccionesCompletadas ?? []),
    [avance]
  );

  // Solo las notas de las evaluaciones de este curso.
  const calificaciones = useMemo(() => {
    const ids = new Set(evaluaciones.map((e) => e.id));
    return todasCalificaciones.filter((c) => ids.has(c.evaluacionId));
  }, [todasCalificaciones, evaluaciones]);

  const leccionesCompletadas = useMemo(
    () => lecciones.filter((l) => completadas.has(l.id)).length,
    [lecciones, completadas]
  );

  /** Primera lección (en orden) que todavía no se ha completado. */
  const siguienteLeccion = useMemo(
    () => lecciones.find((l) => !completadas.has(l.id)) ?? null,
    [lecciones, completadas]
  );

  const toggleLeccion = useCallback(
    async (leccionId) => {
      const completar = !completadas.has(leccionId);
      const anterior = avance;

      // Se actualiza la pantalla de inmediato y se corrige con la respuesta del servidor.
      setAvance((actual) => ({
        ...actual,
        leccionesCompletadas: completar
          ? [...(actual?.leccionesCompletadas ?? []), leccionId]
          : (actual?.leccionesCompletadas ?? []).filter((id) => id !== leccionId),
      }));

      try {
        setAvance(await avanceService.marcarLeccion(leccionId, completar));
      } catch (err) {
        setAvance(anterior);
        throw err;
      }
    },
    [avance, completadas]
  );

  /** Envía las respuestas; el servidor califica y guarda la nota. */
  const presentarEvaluacion = useCallback(async (evaluacionId, respuestas) => {
    const resultado = await avanceService.presentarEvaluacion(evaluacionId, respuestas);

    setTodasCalificaciones((actuales) => [
      ...actuales.filter((c) => c.evaluacionId !== evaluacionId),
      { evaluacionId, usuarioId, nota: resultado.nota },
    ]);

    return resultado;
  }, [usuarioId]);

  return {
    porcentaje: avance?.porcentaje ?? 0,
    calificaciones,
    completadas,
    leccionesCompletadas,
    siguienteLeccion,
    loading,
    error,
    toggleLeccion,
    presentarEvaluacion,
  };
}
