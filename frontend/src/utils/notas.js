// Escala de notas usada en el proyecto (los datos de ejemplo van de 0 a 5).
export const NOTA_MAXIMA = 5;

/** 4.5 -> "4.5", 3.333 -> "3.33", null -> "—" */
export function formatearNota(nota) {
  if (nota === null || nota === undefined || Number.isNaN(Number(nota))) {
    return '—';
  }
  return String(Number(Number(nota).toFixed(2)));
}

/** Nota de un estudiante en una evaluación (o null si no tiene). */
export function notaDeEvaluacion(calificaciones, evaluacionId) {
  const calificacion = calificaciones.find(
    (c) => c.evaluacionId === evaluacionId
  );
  return calificacion ? calificacion.nota : null;
}

/**
 * Promedio ponderado por el porcentaje de cada evaluación.
 * Solo cuentan las evaluaciones que ya tienen nota.
 */
export function promedioPonderado(evaluaciones, calificaciones) {
  let suma = 0;
  let pesos = 0;

  evaluaciones.forEach((evaluacion) => {
    const nota = notaDeEvaluacion(calificaciones, evaluacion.id);
    if (nota !== null) {
      suma += nota * evaluacion.porcentaje;
      pesos += evaluacion.porcentaje;
    }
  });

  return pesos > 0 ? suma / pesos : null;
}
