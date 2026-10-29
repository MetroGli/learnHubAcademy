/**
 * Muestra el contenido de una lección respetando párrafos y listas:
 *  - una línea en blanco separa párrafos
 *  - las líneas que empiezan con "- " forman una lista
 *  - el texto entre `comillas invertidas` se muestra como código
 */
function enLinea(texto) {
  return texto.split(/(`[^`]+`)/g).map((parte, i) =>
    parte.startsWith('`') && parte.endsWith('`') && parte.length > 2 ? (
      <code key={i}>{parte.slice(1, -1)}</code>
    ) : (
      parte
    )
  );
}

export default function RichText({ texto, className = '' }) {
  if (!texto) return null;

  const bloques = [];
  texto.split(/\n\s*\n/).forEach((bloque) => {
    let parrafo = [];
    let lista = [];

    const cerrarParrafo = () => {
      if (parrafo.length) bloques.push({ tipo: 'p', lineas: parrafo });
      parrafo = [];
    };
    const cerrarLista = () => {
      if (lista.length) bloques.push({ tipo: 'ul', lineas: lista });
      lista = [];
    };

    bloque.split('\n').forEach((linea) => {
      const limpia = linea.trim();
      if (!limpia) return;
      if (/^[-•*]\s+/.test(limpia)) {
        cerrarParrafo();
        lista.push(limpia.replace(/^[-•*]\s+/, ''));
      } else {
        cerrarLista();
        parrafo.push(limpia);
      }
    });
    cerrarParrafo();
    cerrarLista();
  });

  return (
    <div className={`lh-richtext ${className}`}>
      {bloques.map((b, i) =>
        b.tipo === 'ul' ? (
          <ul key={i}>
            {b.lineas.map((l, j) => (
              <li key={j}>{enLinea(l)}</li>
            ))}
          </ul>
        ) : (
          <p key={i}>{enLinea(b.lineas.join(' '))}</p>
        )
      )}
    </div>
  );
}
