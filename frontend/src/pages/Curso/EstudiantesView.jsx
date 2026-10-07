import { useState } from 'react';
import FormModal from '../../components/ui/FormModal';
import ProgressBar from '../../components/ui/ProgressBar';
import useCursoEstudiantes from '../../hooks/useCursoEstudiantes';
import { getErrorMessage } from '../../api/errors';
import { NOTA_MAXIMA, formatearNota } from '../../utils/notas';

/** Estudiantes del curso: inscripción, progreso y calificaciones (profesor/administrador). */
export default function EstudiantesView({ cursoId, evaluaciones }) {
  const {
    filas,
    disponibles,
    usuariosDisponibles,
    loading,
    error,
    inscribir,
    cambiarEstado,
    guardarNota,
    notaDeEvaluacion,
  } = useCursoEstudiantes(cursoId, evaluaciones, true);

  const [inscribiendo, setInscribiendo] = useState(false);
  const [calificando, setCalificando] = useState(null); // { fila, evaluacion }
  const [aviso, setAviso] = useState(null);

  const handleEstado = async (inscripcion) => {
    setAviso(null);
    try {
      await cambiarEstado(inscripcion);
    } catch (err) {
      setAviso(getErrorMessage(err, 'No se pudo cambiar el estado de la inscripción.'));
    }
  };

  if (loading) {
    return (
      <div className="curso-state">
        <div className="loader"></div>
        <p>Cargando estudiantes...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="curso-state error">
        <p>{error}</p>
      </div>
    );
  }

  return (
    <section className="curso-estudiantes">
      <div className="ui-section-head">
        <h2>Estudiantes</h2>
        <button
          type="button"
          className="ui-btn ui-btn-primary ui-btn-sm"
          disabled={!usuariosDisponibles || disponibles.length === 0}
          onClick={() => setInscribiendo(true)}
        >
          + Inscribir estudiante
        </button>
      </div>

      {!usuariosDisponibles && (
        <p className="ui-notice ui-notice-warn">
          No se pudo consultar la lista de usuarios con tu cuenta, por eso los
          estudiantes aparecen como "Estudiante #id" y no se puede inscribir
          desde aquí.
        </p>
      )}

      {usuariosDisponibles && disponibles.length === 0 && filas.length > 0 && (
        <p className="curso-nota-pequena">
          Todos los estudiantes activos ya están inscritos en este curso.
        </p>
      )}

      {aviso && <div className="ui-error curso-aviso" role="alert">{aviso}</div>}

      {filas.length === 0 ? (
        <p className="ui-empty">Este curso todavía no tiene estudiantes inscritos.</p>
      ) : (
        <div className="ui-table-wrap">
          <table className="ui-table">
            <thead>
              <tr>
                <th>Estudiante</th>
                <th>Progreso</th>
                {evaluaciones.map((evaluacion) => (
                  <th key={evaluacion.id} title={evaluacion.titulo}>
                    {evaluacion.titulo.length > 18
                      ? `${evaluacion.titulo.slice(0, 17)}…`
                      : evaluacion.titulo}
                    <br />
                    <span className="curso-th-peso">{evaluacion.porcentaje}%</span>
                  </th>
                ))}
                <th>Promedio</th>
                <th>Estado</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {filas.map((fila) => (
                <tr key={fila.inscripcion.id}>
                  <td>
                    <strong>{fila.nombre}</strong>
                    {fila.usuario && (
                      <div className="curso-email">{fila.usuario.email}</div>
                    )}
                  </td>
                  <td className="curso-celda-progreso">
                    <ProgressBar value={fila.porcentaje} label={null} />
                    <span className="curso-pct">{Math.round(fila.porcentaje)}%</span>
                  </td>

                  {evaluaciones.map((evaluacion) => (
                    <td key={evaluacion.id}>
                      <button
                        type="button"
                        className="curso-nota-btn"
                        title="Registrar o cambiar la nota"
                        onClick={() => setCalificando({ fila, evaluacion })}
                      >
                        {formatearNota(notaDeEvaluacion(fila.notas, evaluacion.id))}
                      </button>
                    </td>
                  ))}

                  <td>
                    <strong>{formatearNota(fila.promedio)}</strong>
                  </td>
                  <td>
                    <span
                      className={`ui-badge ${
                        fila.inscripcion.activa ? 'ui-badge-ok' : 'ui-badge-off'
                      }`}
                    >
                      {fila.inscripcion.activa ? 'Activa' : 'Inactiva'}
                    </span>
                  </td>
                  <td>
                    <button
                      type="button"
                      className={`ui-btn ui-btn-sm ${
                        fila.inscripcion.activa ? 'ui-btn-danger' : 'ui-btn-ghost'
                      }`}
                      onClick={() => handleEstado(fila.inscripcion)}
                    >
                      {fila.inscripcion.activa ? 'Dar de baja' : 'Reactivar'}
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {evaluaciones.length === 0 && filas.length > 0 && (
        <p className="curso-nota-pequena">
          Cuando el curso tenga evaluaciones podrás registrar aquí las notas.
        </p>
      )}

      <FormModal
        open={inscribiendo}
        title="Inscribir estudiante"
        fields={[
          {
            name: 'usuarioId',
            label: 'Estudiante',
            type: 'select',
            numeric: true,
            required: true,
            options: disponibles.map((u) => ({
              value: u.id,
              label: `${u.nombre} ${u.apellido} (${u.email})`,
            })),
          },
        ]}
        submitLabel="Inscribir"
        onSubmit={({ usuarioId }) => inscribir(usuarioId)}
        onClose={() => setInscribiendo(false)}
      />

      <FormModal
        open={Boolean(calificando)}
        title={
          calificando
            ? `Calificar · ${calificando.fila.nombre}`
            : ''
        }
        note={calificando ? calificando.evaluacion.titulo : undefined}
        fields={[
          {
            name: 'nota',
            label: `Nota (0 a ${NOTA_MAXIMA})`,
            type: 'number',
            required: true,
            min: 0,
            max: NOTA_MAXIMA,
            step: 0.1,
          },
        ]}
        initialValues={
          calificando
            ? {
                nota: notaDeEvaluacion(
                  calificando.fila.notas,
                  calificando.evaluacion.id
                ),
              }
            : {}
        }
        onSubmit={({ nota }) =>
          guardarNota(
            calificando.fila.inscripcion.usuarioId,
            calificando.evaluacion.id,
            nota
          )
        }
        onClose={() => setCalificando(null)}
      />
    </section>
  );
}
