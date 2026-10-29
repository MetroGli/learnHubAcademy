import { useState } from 'react';
import FormModal from '../../components/ui/FormModal';
import {
  moduloService,
  leccionService,
  recursoService,
} from '../../api/resources';
import { getErrorMessage } from '../../api/errors';
import Icon from '../../components/ui/Icon';
import { formatearMinutos } from '../../utils/cursos';

const TIPOS_RECURSO = [
  { value: 'ENLACE', label: 'Enlace' },
  { value: 'DOCUMENTO', label: 'Documento' },
  { value: 'VIDEO', label: 'Video' },
];

const CAMPOS = {
  modulo: [
    { name: 'nombre', label: 'Nombre', required: true },
    { name: 'descripcion', label: 'Descripción', type: 'textarea' },
    { name: 'orden', label: 'Orden', type: 'number', required: true, min: 0 },
  ],
  leccion: [
    { name: 'titulo', label: 'Título', required: true },
    {
      name: 'contenido',
      label: 'Contenido',
      type: 'textarea',
      rows: 8,
      placeholder: 'Separa los párrafos con una línea en blanco. Las líneas que empiezan con "- " se muestran como lista.',
    },
    { name: 'orden', label: 'Orden', type: 'number', required: true, min: 0 },
    { name: 'duracionMinutos', label: 'Duración (minutos)', type: 'number', min: 0, max: 600 },
  ],
  recurso: [
    { name: 'nombre', label: 'Nombre', required: true },
    { name: 'tipo', label: 'Tipo', type: 'select', options: TIPOS_RECURSO, required: true },
    { name: 'url', label: 'URL', type: 'url', required: true, placeholder: 'https://...' },
  ],
};

const NOMBRES = { modulo: 'módulo', leccion: 'lección', recurso: 'recurso' };

const siguienteOrden = (lista) =>
  lista.length > 0 ? Math.max(...lista.map((x) => x.orden)) + 1 : 1;

/**
 * Módulos, lecciones y recursos de un curso.
 * - gestion=true: el profesor/administrador puede crear, editar y eliminar.
 * - completadas + onToggleLeccion: el estudiante marca lecciones completadas.
 * - onAbrirLeccion: abre la lección en el lector.
 */
export default function ModulosView({
  cursoId,
  modulos,
  lecciones,
  recursos,
  gestion,
  onChange,
  completadas,
  onToggleLeccion,
  onAbrirLeccion,
}) {
  // Se abre al inicio el primer módulo con lecciones pendientes (o el primero).
  const [abiertos, setAbiertos] = useState(() => {
    const pendiente = modulos.find((m) =>
      lecciones.some((l) => l.moduloId === m.id && !completadas?.has(l.id))
    );
    const inicial = pendiente ?? modulos[0];
    return new Set(inicial ? [inicial.id] : []);
  });

  const alternar = (id) =>
    setAbiertos((actual) => {
      const siguiente = new Set(actual);
      if (siguiente.has(id)) siguiente.delete(id);
      else siguiente.add(id);
      return siguiente;
    });
  const [dialogo, setDialogo] = useState(null); // { tipo, modo, registro, padreId }
  const [aviso, setAviso] = useState(null);

  const leccionesDe = (moduloId) =>
    lecciones.filter((l) => l.moduloId === moduloId);
  const recursosDe = (leccionId) =>
    recursos.filter((r) => r.leccionId === leccionId);

  const abrirCrear = (tipo, padreId) => {
    setAviso(null);
    setDialogo({ tipo, modo: 'crear', registro: null, padreId });
  };

  const abrirEditar = (tipo, registro) => {
    setAviso(null);
    setDialogo({ tipo, modo: 'editar', registro, padreId: null });
  };

  const valoresIniciales = () => {
    if (!dialogo) return {};
    if (dialogo.modo === 'editar') return dialogo.registro;
    if (dialogo.tipo === 'modulo') return { orden: siguienteOrden(modulos) };
    if (dialogo.tipo === 'leccion') {
      return { orden: siguienteOrden(leccionesDe(dialogo.padreId)) };
    }
    return { tipo: 'ENLACE' };
  };

  const guardar = async (valores) => {
    const { tipo, modo, registro, padreId } = dialogo;

    if (tipo === 'modulo') {
      const payload = { ...valores, cursoId: Number(cursoId) };
      if (modo === 'crear') await moduloService.crear(payload);
      else await moduloService.actualizar(registro.id, payload);
    }

    if (tipo === 'leccion') {
      const payload = {
        ...valores,
        moduloId: modo === 'crear' ? padreId : registro.moduloId,
      };
      if (modo === 'crear') await leccionService.crear(payload);
      else await leccionService.actualizar(registro.id, payload);
    }

    if (tipo === 'recurso') {
      const payload = {
        ...valores,
        leccionId: modo === 'crear' ? padreId : registro.leccionId,
      };
      if (modo === 'crear') await recursoService.crear(payload);
      else await recursoService.actualizar(registro.id, payload);
    }

    await onChange();
  };

  const eliminar = async (tipo, registro) => {
    const detalle = {
      modulo: `¿Eliminar el módulo "${registro.nombre}" con todas sus lecciones y recursos?`,
      leccion: `¿Eliminar la lección "${registro.titulo}" y sus recursos?`,
      recurso: `¿Eliminar el recurso "${registro.nombre}"?`,
    }[tipo];

    if (!window.confirm(detalle)) return;

    const servicio = { modulo: moduloService, leccion: leccionService, recurso: recursoService }[tipo];

    try {
      await servicio.eliminar(registro.id);
      await onChange();
    } catch (err) {
      setAviso(getErrorMessage(err, `No se pudo eliminar el ${NOMBRES[tipo]}.`));
    }
  };

  return (
    <section className="curso-modulos">
      <div className="ui-section-head">
        <h2>Contenido del curso</h2>
        {gestion && (
          <button
            type="button"
            className="ui-btn ui-btn-primary ui-btn-sm"
            onClick={() => abrirCrear('modulo')}
          >
            + Módulo
          </button>
        )}
      </div>

      {aviso && (
        <div className="ui-error curso-aviso" role="alert">
          {aviso}
        </div>
      )}

      {modulos.length === 0 && (
        <p className="ui-empty">
          {gestion
            ? 'Este curso todavía no tiene módulos. Crea el primero con "+ Módulo".'
            : 'Este curso todavía no tiene módulos.'}
        </p>
      )}

      <div className="modulos-list">
        {modulos.map((modulo) => {
          const abierto = abiertos.has(modulo.id);
          const leccionesModulo = leccionesDe(modulo.id);
          const hechas = completadas
            ? leccionesModulo.filter((l) => completadas.has(l.id)).length
            : 0;
          const minutos = leccionesModulo.reduce((t, l) => t + (l.duracionMinutos || 0), 0);
          const terminado = onToggleLeccion && leccionesModulo.length > 0 && hechas === leccionesModulo.length;

          return (
            <div className={`modulo-card${abierto ? ' abierto' : ''}`} key={modulo.id}>
              <button
                type="button"
                className="modulo-header"
                onClick={() => alternar(modulo.id)}
                aria-expanded={abierto}
              >
                <span className={`modulo-numero${terminado ? ' terminado' : ''}`}>
                  {terminado ? <Icon nombre="check" size={18} strokeWidth={2.4} /> : modulo.orden}
                </span>
                <div className="modulo-titulo">
                  <h3>{modulo.nombre}</h3>
                  <span className="modulo-meta">
                    {leccionesModulo.length}{' '}
                    {leccionesModulo.length === 1 ? 'lección' : 'lecciones'}
                    {minutos > 0 && ` · ${formatearMinutos(minutos)}`}
                    {onToggleLeccion && leccionesModulo.length > 0 &&
                      ` · ${hechas}/${leccionesModulo.length} completadas`}
                  </span>
                  {onToggleLeccion && leccionesModulo.length > 0 && (
                    <div className="modulo-barra" aria-hidden="true">
                      <div style={{ width: `${(hechas / leccionesModulo.length) * 100}%` }} />
                    </div>
                  )}
                </div>
                <span className="modulo-toggle" aria-hidden="true">
                  {abierto ? '−' : '+'}
                </span>
              </button>

              {abierto && (
                <div className="modulo-body">
                  {modulo.descripcion && (
                    <p className="modulo-descripcion">{modulo.descripcion}</p>
                  )}

                  {gestion && (
                    <div className="ui-row-actions curso-acciones-modulo">
                      <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => abrirCrear('leccion', modulo.id)}>
                        + Lección
                      </button>
                      <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => abrirEditar('modulo', modulo)}>
                        Editar módulo
                      </button>
                      <button type="button" className="ui-btn ui-btn-danger ui-btn-sm" onClick={() => eliminar('modulo', modulo)}>
                        Eliminar módulo
                      </button>
                    </div>
                  )}

                  {leccionesModulo.length === 0 && (
                    <p className="curso-empty">
                      Este módulo todavía no tiene lecciones.
                    </p>
                  )}

                  <ul className="lecciones-list">
                    {leccionesModulo.map((leccion) => {
                      const recursosLeccion = recursosDe(leccion.id);
                      const hecha = completadas?.has(leccion.id);

                      return (
                        <li className={`leccion-fila${hecha ? ' hecha' : ''}`} key={leccion.id}>
                          {onToggleLeccion ? (
                            <button
                              type="button"
                              className="leccion-check-btn"
                              onClick={() => onToggleLeccion(leccion.id)}
                              aria-pressed={hecha}
                              aria-label={hecha ? `Marcar "${leccion.titulo}" como pendiente` : `Marcar "${leccion.titulo}" como completada`}
                              title={hecha ? 'Completada' : 'Marcar como completada'}
                            >
                              {hecha && <Icon nombre="check" size={14} strokeWidth={3} />}
                            </button>
                          ) : (
                            <span className="leccion-icono"><Icon nombre="documento" size={16} /></span>
                          )}

                          <button
                            type="button"
                            className="leccion-abrir"
                            onClick={() => onAbrirLeccion(leccion.id)}
                          >
                            <span className="leccion-titulo">{leccion.titulo}</span>
                            <span className="leccion-datos">
                              {leccion.duracionMinutos ? (
                                <span><Icon nombre="reloj" size={13} /> {formatearMinutos(leccion.duracionMinutos)}</span>
                              ) : null}
                              {recursosLeccion.length > 0 && (
                                <span>
                                  <Icon nombre="enlace" size={13} /> {recursosLeccion.length}{' '}
                                  {recursosLeccion.length === 1 ? 'recurso' : 'recursos'}
                                </span>
                              )}
                            </span>
                          </button>

                          {gestion ? (
                            <div className="ui-row-actions curso-acciones-leccion">
                              <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => abrirCrear('recurso', leccion.id)}>
                                + Recurso
                              </button>
                              <button type="button" className="ui-btn ui-btn-ghost ui-btn-sm" onClick={() => abrirEditar('leccion', leccion)}>
                                Editar
                              </button>
                              <button type="button" className="ui-btn ui-btn-danger ui-btn-sm" onClick={() => eliminar('leccion', leccion)}>
                                Eliminar
                              </button>
                            </div>
                          ) : (
                            <span className="leccion-ir" aria-hidden="true"><Icon nombre="flecha" size={16} /></span>
                          )}

                          {gestion && recursosLeccion.length > 0 && (
                            <ul className="recursos-list">
                              {recursosLeccion.map((recurso) => (
                                <li key={recurso.id}>
                                  <a href={recurso.url} target="_blank" rel="noreferrer">
                                    {recurso.nombre}
                                  </a>
                                  <span className="recurso-tipo">{recurso.tipo}</span>
                                  <span className="curso-mini-acciones">
                                    <button type="button" onClick={() => abrirEditar('recurso', recurso)}>
                                      Editar
                                    </button>
                                    <button type="button" className="peligro" onClick={() => eliminar('recurso', recurso)}>
                                      Eliminar
                                    </button>
                                  </span>
                                </li>
                              ))}
                            </ul>
                          )}
                        </li>
                      );
                    })}
                  </ul>
                </div>
              )}
            </div>
          );
        })}
      </div>

      <FormModal
        open={Boolean(dialogo)}
        title={
          dialogo
            ? `${dialogo.modo === 'crear' ? 'Nuevo' : 'Editar'} ${NOMBRES[dialogo.tipo]}`
            : ''
        }
        fields={dialogo ? CAMPOS[dialogo.tipo] : []}
        initialValues={valoresIniciales()}
        onSubmit={guardar}
        onClose={() => setDialogo(null)}
      />
    </section>
  );
}
