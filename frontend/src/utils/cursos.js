// Catálogo de categorías y niveles de los cursos, con los colores de su portada.

export const CATEGORIAS = [
  { nombre: 'Backend', icono: 'servidor', c1: '#0f4c81', c2: '#2f80c9' },
  { nombre: 'Frontend', icono: 'pantalla', c1: '#5b21b6', c2: '#9f67f5' },
  { nombre: 'Bases de datos', icono: 'base', c1: '#0f766e', c2: '#22b5a3' },
  { nombre: 'Programación', icono: 'codigo', c1: '#a1460a', c2: '#f0a030' },
  { nombre: 'Redes', icono: 'red', c1: '#155e75', c2: '#22a9c9' },
  { nombre: 'DevOps', icono: 'caja', c1: '#3730a3', c2: '#6f74f0' },
  { nombre: 'Ingeniería de software', icono: 'plano', c1: '#9f1239', c2: '#e8566f' },
  { nombre: 'Herramientas', icono: 'rama', c1: '#334155', c2: '#6b7d95' },
  { nombre: 'Seguridad', icono: 'escudo', c1: '#7f1d1d', c2: '#d64545' },
];

const POR_DEFECTO = { nombre: 'General', icono: 'libro', c1: '#123156', c2: '#2f6da8' };

export function categoriaDe(curso) {
  return CATEGORIAS.find((c) => c.nombre === curso?.categoria) ?? POR_DEFECTO;
}

export const NIVELES = {
  BASICO: 'Básico',
  INTERMEDIO: 'Intermedio',
  AVANZADO: 'Avanzado',
};

export const OPCIONES_CATEGORIA = CATEGORIAS.map((c) => ({ value: c.nombre, label: c.nombre }));
export const OPCIONES_NIVEL = Object.entries(NIVELES).map(([value, label]) => ({ value, label }));

/** Campos del formulario de curso que comparten profesor y administrador. */
export const CAMPOS_DETALLE_CURSO = [
  { name: 'categoria', label: 'Categoría', type: 'select', options: OPCIONES_CATEGORIA },
  { name: 'nivel', label: 'Nivel', type: 'select', options: OPCIONES_NIVEL },
  { name: 'duracionHoras', label: 'Duración (horas)', type: 'number', min: 0, max: 500, step: 1 },
];

/** Deja solo los datos que acepta el backend y convierte "" en null. */
export function payloadCurso(valores, profesorId) {
  const vacio = (v) => (v === '' || v === undefined ? null : v);
  return {
    nombre: valores.nombre,
    descripcion: valores.descripcion,
    activo: valores.activo,
    profesorId,
    categoria: vacio(valores.categoria),
    nivel: vacio(valores.nivel),
    duracionHoras: vacio(valores.duracionHoras),
  };
}

/** 90 -> "1 h 30 min", 25 -> "25 min" */
export function formatearMinutos(minutos) {
  if (!minutos) return null;
  const h = Math.floor(minutos / 60);
  const m = minutos % 60;
  if (h === 0) return `${m} min`;
  return m === 0 ? `${h} h` : `${h} h ${m} min`;
}
