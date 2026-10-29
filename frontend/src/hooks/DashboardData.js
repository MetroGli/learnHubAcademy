import { useCallback, useEffect, useState } from 'react';
import {
  calificacionService,
  cursoService,
  evaluacionService,
  inscripcionService,
  progresoService,
} from '../api/resources';
import { promedioPonderado } from '../utils/notas';
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

        const [allCourses, allEnrollments, allProgresos, allEvaluaciones, allCalificaciones] =
          await Promise.all([
            cursoService.listar(),
            inscripcionService.listar(),
            progresoService.listar(),
            evaluacionService.listar(),
            calificacionService.listar(),
          ]);
        const misNotas = allCalificaciones.filter((c) => c.usuarioId === user.id);

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
            .map((course) => {
              const evaluaciones = allEvaluaciones.filter((e) => e.cursoId === course.id);
              const ids = new Set(evaluaciones.map((e) => e.id));
              const notas = misNotas.filter((c) => ids.has(c.evaluacionId));
              return {
                ...course,
                progreso:
                  allProgresos.find(
                    (p) => p.usuarioId === user.id && p.cursoId === course.id
                  )?.porcentaje ?? 0,
                totalEvaluaciones: evaluaciones.length,
                presentadas: notas.length,
                promedio: promedioPonderado(evaluaciones, notas),
              };
            })
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
  // crear otra (el backend no permite dos inscripciones al mismo curso).
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
