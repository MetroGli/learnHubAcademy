// Íconos de línea (24x24) usados en toda la aplicación.
const TRAZOS = {
  libro: 'M4 19.5A2.5 2.5 0 0 1 6.5 17H20V3H6.5A2.5 2.5 0 0 0 4 5.5v14Zm0 0A2.5 2.5 0 0 0 6.5 22H20v-5',
  servidor: 'M4 4h16v6H4zM4 14h16v6H4zM8 7h.01M8 17h.01',
  pantalla: 'M3 4h18v12H3zM8 20h8M12 16v4',
  base: 'M12 3c4.4 0 8 1.3 8 3s-3.6 3-8 3-8-1.3-8-3 3.6-3 8-3Zm8 3v12c0 1.7-3.6 3-8 3s-8-1.3-8-3V6m16 6c0 1.7-3.6 3-8 3s-8-1.3-8-3',
  codigo: 'm8 8-4 4 4 4M16 8l4 4-4 4M14 4l-4 16',
  red: 'M12 3v6M5 15v-3h14v3M3 15h4v6H3zM10 15h4v6h-4zM17 15h4v6h-4zM9 3h6v6H9z',
  caja: 'M21 8 12 3 3 8v8l9 5 9-5V8ZM3 8l9 5 9-5M12 13v8',
  plano: 'M9 3H5a2 2 0 0 0-2 2v4m6-6h10a2 2 0 0 1 2 2v4M9 3v18m0 0h10a2 2 0 0 0 2-2V9M9 21H5a2 2 0 0 1-2-2V9m0 0h18',
  rama: 'M6 3v12M18 9a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm0 0a9 9 0 0 1-9 9M6 21a3 3 0 1 0 0-6 3 3 0 0 0 0 6Z',
  escudo: 'M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10Z',
  reloj: 'M12 22a10 10 0 1 0 0-20 10 10 0 0 0 0 20Zm0-14v4l3 2',
  usuario: 'M20 21a8 8 0 0 0-16 0M12 13a5 5 0 1 0 0-10 5 5 0 0 0 0 10Z',
  usuarios: 'M17 21a6 6 0 0 0-12 0M11 11a4 4 0 1 0 0-8 4 4 0 0 0 0 8ZM22 21a5 5 0 0 0-4-4.9M16 3.1a4 4 0 0 1 0 7.8',
  capas: 'm12 2 10 5-10 5L2 7l10-5ZM2 17l10 5 10-5M2 12l10 5 10-5',
  check: 'M20 6 9 17l-5-5',
  checkCirculo: 'M22 11.1V12a10 10 0 1 1-5.9-9.1M22 4 12 14l-3-3',
  play: 'M6 4l14 8-14 8V4Z',
  premio: 'M12 15a7 7 0 1 0 0-14 7 7 0 0 0 0 14ZM8.2 13.9 7 23l5-3 5 3-1.2-9.1',
  grafica: 'M3 3v18h18M7 15l4-4 3 3 6-6',
  flecha: 'M5 12h14M13 6l6 6-6 6',
  flechaIzq: 'M19 12H5M11 18l-6-6 6-6',
  enlace: 'M10 14a5 5 0 0 0 7 0l3-3a5 5 0 0 0-7-7l-1 1M14 10a5 5 0 0 0-7 0l-3 3a5 5 0 0 0 7 7l1-1',
  documento: 'M14 3H7a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V8l-5-5ZM14 3v5h5M9 13h6M9 17h6',
  video: 'M23 7l-7 5 7 5V7ZM1 5h15v14H1z',
  quiz: 'M9 9a3 3 0 1 1 4 2.8c-.6.3-1 .9-1 1.6V14M12 18h.01M12 22a10 10 0 1 0 0-20 10 10 0 0 0 0 20Z',
  descarga: 'M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4M7 10l5 5 5-5M12 15V3',
  cerrar: 'M18 6 6 18M6 6l12 12',
  estrella: 'm12 2 3.1 6.3 6.9 1-5 4.9 1.2 6.8-6.2-3.2L5.8 21 7 14.2 2 9.3l6.9-1L12 2Z',
  fuego: 'M12 22c4 0 7-3 7-7 0-4-3-6-4-9-1 2-2 3-4 3 0-2 0-4-2-6-1 4-4 6-4 11 0 5 3 8 7 8Z',
  lista: 'M8 6h13M8 12h13M8 18h13M3 6h.01M3 12h.01M3 18h.01',
};

export default function Icon({ nombre, size = 20, className = '', strokeWidth = 1.8 }) {
  const d = TRAZOS[nombre] ?? TRAZOS.libro;
  return (
    <svg
      className={`lh-icon ${className}`}
      width={size}
      height={size}
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      strokeWidth={strokeWidth}
      strokeLinecap="round"
      strokeLinejoin="round"
      aria-hidden="true"
    >
      <path d={d} />
    </svg>
  );
}
