export default function ProgressBar({ value = 0, label }) {
  const porcentaje = Math.max(0, Math.min(100, Math.round(Number(value) || 0)));

  return (
    <div>
      {label && (
        <div className="ui-progress-label">
          <span>{label}</span>
          <strong>{porcentaje}%</strong>
        </div>
      )}
      <div
        className="ui-progress"
        role="progressbar"
        aria-valuenow={porcentaje}
        aria-valuemin={0}
        aria-valuemax={100}
      >
        <div className="ui-progress-bar" style={{ width: `${porcentaje}%` }} />
      </div>
    </div>
  );
}
