import Icon from './Icon';
import { NIVELES, categoriaDe } from '../../utils/cursos';

/** Portada de un curso con el color y el ícono de su categoría. */
export default function CourseCover({ curso, size = 'md', children }) {
  const categoria = categoriaDe(curso);

  return (
    <div
      className={`lh-cover lh-cover-${size}`}
      style={{ '--c1': categoria.c1, '--c2': categoria.c2 }}
    >
      <Icon nombre={categoria.icono} size={size === 'sm' ? 64 : 120} strokeWidth={1.1} className="lh-cover-art" />

      <div className="lh-cover-top">
        <span className="lh-chip lh-chip-glass">
          <Icon nombre={categoria.icono} size={14} />
          {curso?.categoria || 'Curso'}
        </span>
        {children}
      </div>

      {(curso?.nivel || curso?.duracionHoras) && (
        <div className="lh-cover-bottom">
          {curso.nivel && <span className="lh-chip lh-chip-glass">{NIVELES[curso.nivel] ?? curso.nivel}</span>}
          {curso.duracionHoras ? (
            <span className="lh-chip lh-chip-glass">
              <Icon nombre="reloj" size={13} /> {curso.duracionHoras} h
            </span>
          ) : null}
        </div>
      )}
    </div>
  );
}
