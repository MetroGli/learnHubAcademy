import Icon from './Icon';

/**
 * Fila de indicadores.
 * items: [{ label, value, hint, icon, tono: 'azul' | 'verde' | 'ambar' | 'violeta' | 'rosa' }]
 */
export default function StatCards({ items }) {
  return (
    <div className="lh-stats">
      {items.map((item) => (
        <div className={`lh-stat lh-tono-${item.tono ?? 'azul'}`} key={item.label}>
          <span className="lh-stat-icon">
            <Icon nombre={item.icon ?? 'grafica'} size={20} />
          </span>
          <div className="lh-stat-text">
            <span className="lh-stat-label">{item.label}</span>
            <strong className="lh-stat-value">{item.value}</strong>
            {item.hint && <span className="lh-stat-hint">{item.hint}</span>}
          </div>
        </div>
      ))}
    </div>
  );
}
