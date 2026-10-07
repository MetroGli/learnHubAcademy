import './LogoMark.css';

export default function LogoMark({ size = 40, animated = true }) {
  return (
    <svg
      className={`lh-logo-mark${animated ? ' animated' : ''}`}
      width={size}
      height={size}
      viewBox="0 0 72 72"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      role="img"
      aria-label="LearnHub"
    >
      <circle cx="36" cy="36" r="34" className="lh-mark-ring" />
      <path
        className="lh-mark-book lh-mark-book-left"
        d="M36 30 C29 26 20 25 14 27 V47 C20 45 29 46 36 50 Z"
      />
      <path
        className="lh-mark-book lh-mark-book-right"
        d="M36 30 C43 26 52 25 58 27 V47 C52 45 43 46 36 50 Z"
      />
      <path className="lh-mark-cap" d="M36 14 L58 22 L36 30 L14 22 Z" />
      <line
        className="lh-mark-cap-string"
        x1="49"
        y1="25.5"
        x2="49"
        y2="34"
      />
      <circle className="lh-mark-cap-tassel" cx="49" cy="35.5" r="1.6" />
    </svg>
  );
}
