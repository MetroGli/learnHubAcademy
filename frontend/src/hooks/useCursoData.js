import { useCallback, useEffect, useState } from 'react';
import {
  cursoService,
  moduloService,
  leccionService,
  recursoService,
  evaluacionService,
  preguntaService,
  respuestaService,
} from '../api/resources';

export default function useCursoData(cursoId) {
  const [curso, setCurso] = useState(null);
  const [modulos, setModulos] = useState([]);
  const [lecciones, setLecciones] = useState([]);
  const [recursos, setRecursos] = useState([]);
  const [evaluaciones, setEvaluaciones] = useState([]);
  const [preguntas, setPreguntas] = useState([]);
  const [respuestas, setRespuestas] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const cargar = useCallback(
    async ({ silencioso = false } = {}) => {
      if (!cursoId) {
        setLoading(false);
        return;
      }

      try {
        if (!silencioso) setLoading(true);
        setError(null);

        const [
          cursoData,
          allModulos,
          allLecciones,
          allRecursos,
          allEvaluaciones,
          allPreguntas,
          allRespuestas,
        ] = await Promise.all([
          cursoService.buscarPorId(cursoId),
          moduloService.listar(),
          leccionService.listar(),
          recursoService.listar(),
          evaluacionService.listar(),
          preguntaService.listar(),
          respuestaService.listar(),
        ]);

        const id = Number(cursoId);

        const cursoModulos = allModulos
          .filter((modulo) => modulo.cursoId === id)
          .sort((a, b) => a.orden - b.orden);
        const moduloIds = new Set(cursoModulos.map((m) => m.id));

        const cursoLecciones = allLecciones
          .filter((leccion) => moduloIds.has(leccion.moduloId))
          .sort((a, b) => a.orden - b.orden);
        const leccionIds = new Set(cursoLecciones.map((l) => l.id));

        const cursoRecursos = allRecursos.filter((recurso) =>
          leccionIds.has(recurso.leccionId)
        );

        const cursoEvaluaciones = allEvaluaciones.filter(
          (evaluacion) => evaluacion.cursoId === id
        );
        const evaluacionIds = new Set(cursoEvaluaciones.map((e) => e.id));

        const cursoPreguntas = allPreguntas.filter((pregunta) =>
          evaluacionIds.has(pregunta.evaluacionId)
        );
        const preguntaIds = new Set(cursoPreguntas.map((p) => p.id));

        const cursoRespuestas = allRespuestas.filter((respuesta) =>
          preguntaIds.has(respuesta.preguntaId)
        );

        setCurso(cursoData);
        setModulos(cursoModulos);
        setLecciones(cursoLecciones);
        setRecursos(cursoRecursos);
        setEvaluaciones(cursoEvaluaciones);
        setPreguntas(cursoPreguntas);
        setRespuestas(cursoRespuestas);
      } catch (err) {
        console.error('Error cargando el curso:', err);
        if (!silencioso) {
          setError('No se pudo cargar la información del curso.');
        }
      } finally {
        setLoading(false);
      }
    },
    [cursoId]
  );

  useEffect(() => {
    cargar();
  }, [cargar]);

  // Recarga sin mostrar el spinner (se usa después de crear/editar/eliminar).
  const reload = useCallback(() => cargar({ silencioso: true }), [cargar]);

  return {
    curso,
    modulos,
    lecciones,
    recursos,
    evaluaciones,
    preguntas,
    respuestas,
    loading,
    error,
    reload,
  };
}
