import { useCallback, useEffect, useState } from 'react';
import {
  cursoService,
  inscripcionService,
  progresoService,
} from '../api/resources';
import { useAuth } from '../context/AuthContext';

export default function useDashboardData() {
  const { user } = useAuth();

  const [courses, setCourses] = useState([]);
  const [catalogo, setCatalogo] = useState([]);
  const [misInscripciones, setMisInscripciones] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const cargar = useCallback(
    async ({ silencioso = false } = {}) => {
      if (!user?.id) {
        setCourses([]);
        setCatalogo([]);
        setLoading(false);
        return;
      }

      try {
        if (!silencioso) setLoading(true);
        setError(null);

        const [allCourses, allEnrollments, allProgresos] = await Promise.all([
          cursoService.listar(),
          inscripcionService.listar(),
          progresoService.listar(),
        ]);

        const mias = allEnrollments.filter(
          (inscripcion) => inscripcion.usuarioId === user.id
        );
        const idsActivos = new Set(
          mias.filter((i) => i.activa).map((i) => i.cursoId)
        );
        const cursosActivos = allCourses.filter((course) => course.activo);

        setMisInscripciones(mias);

        setCourses(
          cursosActivos
            .filter((course) => idsActivos.has(course.id))
            .map((course) => ({
              ...course,
              progreso:
                allProgresos.find(
                  (p) => p.usuarioId === user.id && p.cursoId === course.id
                )?.porcentaje ?? 0,
            }))
        );

        setCatalogo(cursosActivos.filter((course) => !idsActivos.has(course.id)));
      } catch (err) {
        console.error('Error cargando dashboard:', err);
        setError('No se pudieron cargar tus clases.');
      } finally {
        setLoading(false);
      }
    },
    [user?.id]
  );

  useEffect(() => {
    cargar();
  }, [cargar]);

  // Si el estudiante ya tuvo una inscripción (inactiva) se reactiva en vez de
  // crear otra, porque el backend no evita inscripciones duplicadas.
  const inscribirse = async (cursoId) => {
    const previa = misInscripciones.find((i) => i.cursoId === cursoId);

    if (previa) {
      await inscripcionService.actualizar(previa.id, {
        usuarioId: user.id,
        cursoId,
        activa: true,
      });
    } else {
      await inscripcionService.crear({
        usuarioId: user.id,
        cursoId,
        activa: true,
      });
    }

    await cargar({ silencioso: true });
  };

  return {
    courses,
    catalogo,
    loading,
    error,
    inscribirse,
  };
}
