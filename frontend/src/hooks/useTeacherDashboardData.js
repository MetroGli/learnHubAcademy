import { useCallback, useEffect, useState } from 'react';
import { cursoService, inscripcionService } from '../api/resources';
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

        const [allCourses, allEnrollments] = await Promise.all([
          cursoService.listar(),
          inscripcionService.listar(),
        ]);

        // Se muestran todos los cursos del profesor (también los inactivos)
        // para que pueda editarlos o volver a activarlos.
        const teacherCourses = allCourses
          .filter((course) => course.profesorId === user.id)
          .map((course) => ({
            ...course,
            studentCount: allEnrollments.filter(
              (i) => i.cursoId === course.id && i.activa
            ).length,
          }));

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

  const crearCurso = async ({ nombre, descripcion, activo }) => {
    await cursoService.crear({
      nombre,
      descripcion,
      activo,
      profesorId: user.id,
    });
    await cargar({ silencioso: true });
  };

  const actualizarCurso = async (curso, { nombre, descripcion, activo }) => {
    await cursoService.actualizar(curso.id, {
      nombre,
      descripcion,
      activo,
      profesorId: curso.profesorId,
    });
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
