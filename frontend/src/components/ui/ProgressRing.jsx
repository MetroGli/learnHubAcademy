/** Anillo de progreso (0-100) con el porcentaje en el centro. */
export default function ProgressRing({ value = 0, size = 88, grosor = 8, children }) {
  const pct = Math.max(0, Math.min(100, Number(value) || 0));
  const r = (size - grosor) / 2;
  const circunferencia = 2 * Math.PI * r;

  return (
    <div className="lh-ring" style={{ width: size, height: size }}>
      <svg width={size} height={size} role="img" aria-label={`${Math.round(pct)}% completado`}>
        <circle cx={size / 2} cy={size / 2} r={r} strokeWidth={grosor} className="lh-ring-fondo" />
        <circle
          cx={size / 2}
          cy={size / 2}
          r={r}
          strokeWidth={grosor}
          className="lh-ring-valor"
          strokeDasharray={circunferencia}
          strokeDashoffset={circunferencia * (1 - pct / 100)}
          transform={`rotate(-90 ${size / 2} ${size / 2})`}
        />
      </svg>
      <div className="lh-ring-centro">{children ?? <strong>{Math.round(pct)}%</strong>}</div>
    </div>
  );
}
