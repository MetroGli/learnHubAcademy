// El backend guarda el avance como un porcentaje por curso (tabla progresos),
// pero no recuerda QUÉ lecciones se completaron. Esa lista se guarda en este
// navegador y el porcentaje resultante se sincroniza con el backend.
// Cuando el backend tenga una tabla de lecciones completadas, esto se reemplaza.

const clave = (usuarioId, cursoId) =>
  `learnhub_completadas_${usuarioId}_${cursoId}`;

export function leerCompletadas(usuarioId, cursoId) {
  try {
    const guardado = JSON.parse(
      localStorage.getItem(clave(usuarioId, cursoId)) || '[]'
    );
    return new Set(Array.isArray(guardado) ? guardado : []);
  } catch {
    return new Set();
  }
}

export function guardarCompletadas(usuarioId, cursoId, conjunto) {
  try {
    localStorage.setItem(
      clave(usuarioId, cursoId),
      JSON.stringify([...conjunto])
    );
  } catch {
    // Si el navegador no deja guardar, el avance igual se envía al backend.
  }
}
