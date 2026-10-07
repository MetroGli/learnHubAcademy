/**
 * Saca un mensaje legible de un error.
 * - Error de axios: usa el "message" que mandó el backend, si lo hay.
 * - Error propio (new Error('...')): usa su mensaje.
 * - Cualquier otro caso: el texto por defecto.
 */
export function getErrorMessage(err, fallback = 'Ocurrió un error. Intenta de nuevo.') {
  const delBackend = err?.response?.data?.message;
  if (typeof delBackend === 'string' && delBackend.trim()) return delBackend;

  if (err && !err.isAxiosError && typeof err.message === 'string' && err.message) {
    return err.message;
  }

  return fallback;
}
