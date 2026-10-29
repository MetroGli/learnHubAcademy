import { useCallback, useEffect, useState } from 'react';
import {
  calificacionService,
  cursoService,
  evaluacionService,
  inscripcionService,
  progresoService,
} from '../api/resources';
import { payloadCurso } from '../utils/cursos';

const promedio = (valores) =>
  valores.length ? valores.reduce((t, v) => t + v, 0) / valores.length : null;
import { useAuth } from '../context/AuthContext';

export default function useTeacherDashboardData() {
  const { user } = useAuth();

  const [courses, setCourses] = useState([]);
  const [totalStudents, setTotalStudents] = useState(0);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const cargar = useCallback(
    async ({ silencioso = false } = {}) => {
      if (!user?.id) {
        setCourses([]);
        setTotalStudents(0);
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

        // Se muestran todos los cursos del profesor (también los inactivos)
        // para que pueda editarlos o volver a activarlos.
        const teacherCourses = allCourses
          .filter((course) => course.profesorId === user.id)
          .map((course) => {
            const inscritos = new Set(
              allEnrollments
                .filter((i) => i.cursoId === course.id && i.activa)
                .map((i) => i.usuarioId)
            );
            const evaluacionIds = new Set(
              allEvaluaciones.filter((e) => e.cursoId === course.id).map((e) => e.id)
            );
            return {
              ...course,
              studentCount: inscritos.size,
              evaluaciones: evaluacionIds.size,
              progresoMedio: promedio(
                allProgresos
                  .filter((p) => p.cursoId === course.id && inscritos.has(p.usuarioId))
                  .map((p) => Number(p.porcentaje))
              ),
              notaMedia: promedio(
                allCalificaciones
                  .filter((c) => evaluacionIds.has(c.evaluacionId))
                  .map((c) => Number(c.nota))
              ),
            };
          });

        const cursoIds = new Set(teacherCourses.map((c) => c.id));
        const estudiantesUnicos = new Set(
          allEnrollments
            .filter((i) => i.activa && cursoIds.has(i.cursoId))
            .map((i) => i.usuarioId)
        );

        setCourses(teacherCourses);
        setTotalStudents(estudiantesUnicos.size);
      } catch (err) {
        console.error('Error cargando dashboard docente:', err);
        setCourses([]);
        setTotalStudents(0);
        setError('No se pudieron cargar tus cursos.');
      } finally {
        setLoading(false);
      }
    },
    [user?.id]
  );

  useEffect(() => {
    cargar();
  }, [cargar]);

  const crearCurso = async (valores) => {
    await cursoService.crear(payloadCurso(valores, user.id));
    await cargar({ silencioso: true });
  };

  const actualizarCurso = async (curso, valores) => {
    await cursoService.actualizar(curso.id, payloadCurso(valores, curso.profesorId));
    await cargar({ silencioso: true });
  };

  const eliminarCurso = async (id) => {
    await cursoService.eliminar(id);
    await cargar({ silencioso: true });
  };

  return {
    courses,
    totalCourses: courses.length,
    totalStudents,
    loading,
    error,
    crearCurso,
    actualizarCurso,
    eliminarCurso,
  };
}
