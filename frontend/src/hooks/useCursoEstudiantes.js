import { useCallback, useEffect, useMemo, useState } from 'react';
import {
  calificacionService,
  inscripcionService,
  progresoService,
  usuarioService,
} from '../api/resources';
import { notaDeEvaluacion, promedioPonderado } from '../utils/notas';

/**
 * Datos para que profesor/administrador gestionen a los estudiantes de un
 * curso: inscritos, su progreso y sus notas.
 */
export default function useCursoEstudiantes(cursoId, evaluaciones, activo) {
  const cursoIdNum = Number(cursoId);

  const [inscripciones, setInscripciones] = useState([]);
  const [usuarios, setUsuarios] = useState([]);
  const [usuariosDisponibles, setUsuariosDisponibles] = useState(true);
  const [progresos, setProgresos] = useState([]);
  const [calificaciones, setCalificaciones] = useState([]);
  const [loading, setLoading] = useState(Boolean(activo));
  const [error, setError] = useState(null);

  const cargar = useCallback(async () => {
    if (!activo) {
      setLoading(false);
      return;
    }

    try {
      setError(null);

      const [allInscripciones, allProgresos, allCalificaciones] =
        await Promise.all([
          inscripcionService.listar(),
          progresoService.listar(),
          calificacionService.listar(),
        ]);

      setInscripciones(allInscripciones.filter((i) => i.cursoId === cursoIdNum));
      setProgresos(allProgresos.filter((p) => p.cursoId === cursoIdNum));
      setCalificaciones(allCalificaciones);

      // La lista de usuarios puede estar restringida según el rol.
      try {
        setUsuarios(await usuarioService.listar());
        setUsuariosDisponibles(true);
      } catch {
        setUsuarios([]);
        setUsuariosDisponibles(false);
      }
    } catch (err) {
      console.error('Error cargando estudiantes del curso:', err);
      setError('No se pudo cargar la lista de estudiantes.');
    } finally {
      setLoading(false);
    }
  }, [activo, cursoIdNum]);

  useEffect(() => {
    cargar();
  }, [cargar]);

  const evaluacionIds = useMemo(
    () => new Set(evaluaciones.map((e) => e.id)),
    [evaluaciones]
  );

  const filas = useMemo(
    () =>
      inscripciones.map((inscripcion) => {
        const usuario = usuarios.find((u) => u.id === inscripcion.usuarioId);
        const progreso = progresos.find(
          (p) => p.usuarioId === inscripcion.usuarioId
        );
        const notas = calificaciones.filter(
          (c) =>
            c.usuarioId === inscripcion.usuarioId &&
            evaluacionIds.has(c.evaluacionId)
        );

        return {
          inscripcion,
          usuario,
          nombre: usuario
            ? `${usuario.nombre} ${usuario.apellido}`
            : `Estudiante #${inscripcion.usuarioId}`,
          porcentaje: progreso?.porcentaje ?? 0,
          notas,
          promedio: promedioPonderado(evaluaciones, notas),
        };
      }),
    [inscripciones, usuarios, progresos, calificaciones, evaluaciones, evaluacionIds]
  );

  // Estudiantes que todavía no están inscritos en este curso.
  const disponibles = useMemo(() => {
    const inscritos = new Set(inscripciones.map((i) => i.usuarioId));
    return usuarios.filter(
      (u) => u.rol === 'ESTUDIANTE' && u.activo && !inscritos.has(u.id)
    );
  }, [usuarios, inscripciones]);

  const inscribir = async (usuarioId) => {
    await inscripcionService.crear({
      usuarioId,
      cursoId: cursoIdNum,
      activa: true,
    });
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

  const guardarNota = async (usuarioId, evaluacionId, nota) => {
    const existente = calificaciones.find(
      (c) => c.usuarioId === usuarioId && c.evaluacionId === evaluacionId
    );
    const payload = { usuarioId, evaluacionId, nota };

    if (existente) {
      await calificacionService.actualizar(existente.id, payload);
    } else {
      await calificacionService.crear(payload);
    }
    await cargar();
  };

  return {
    filas,
    disponibles,
    usuariosDisponibles,
    loading,
    error,
    inscribir,
    cambiarEstado,
    guardarNota,
    notaDeEvaluacion,
  };
}
