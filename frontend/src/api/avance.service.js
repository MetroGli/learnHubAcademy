import client from './client';

/** Avance del usuario que inició sesión (lo calcula el servidor). */
export const avanceService = {
  async delCurso(cursoId) {
    const { data } = await client.get(`/api/mi-avance/cursos/${cursoId}`);
    return data;
  },

  async marcarLeccion(leccionId, completada) {
    const { data } = completada
      ? await client.post(`/api/mi-avance/lecciones/${leccionId}`)
      : await client.delete(`/api/mi-avance/lecciones/${leccionId}`);
    return data;
  },

  /** respuestas: { [preguntaId]: respuestaId } */
  async presentarEvaluacion(evaluacionId, respuestas) {
    const { data } = await client.post(`/api/mi-avance/evaluaciones/${evaluacionId}`, { respuestas });
    return data;
  },
};

export const estudianteService = {
  async listar() {
    const { data } = await client.get('/api/estudiantes');
    return data;
  },
};
