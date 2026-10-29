package com.learnhub.service;

import com.learnhub.dto.AvanceCursoResponse;
import com.learnhub.dto.ResultadoEvaluacionResponse;
import com.learnhub.dto.ResultadoEvaluacionResponse.DetallePregunta;
import com.learnhub.entity.Calificacion;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Evaluacion;
import com.learnhub.entity.Leccion;
import com.learnhub.entity.LeccionCompletada;
import com.learnhub.entity.Pregunta;
import com.learnhub.entity.Progreso;
import com.learnhub.entity.Respuesta;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CalificacionRepository;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.EvaluacionRepository;
import com.learnhub.repository.InscripcionRepository;
import com.learnhub.repository.LeccionCompletadaRepository;
import com.learnhub.repository.LeccionRepository;
import com.learnhub.repository.ProgresoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Acciones del estudiante sobre su propio avance. El servidor calcula el
 * porcentaje del curso y la nota de las evaluaciones, así el navegador no
 * puede enviar valores inventados.
 */
@Service
public class MiAvanceService {

    public static final double NOTA_MAXIMA = 5.0;

    private final CursoRepository cursoRepository;
    private final LeccionRepository leccionRepository;
    private final LeccionCompletadaRepository completadaRepository;
    private final ProgresoRepository progresoRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final CalificacionRepository calificacionRepository;
    private final InscripcionRepository inscripcionRepository;

    public MiAvanceService(
            CursoRepository cursoRepository,
            LeccionRepository leccionRepository,
            LeccionCompletadaRepository completadaRepository,
            ProgresoRepository progresoRepository,
            EvaluacionRepository evaluacionRepository,
            CalificacionRepository calificacionRepository,
            InscripcionRepository inscripcionRepository) {

        this.cursoRepository = cursoRepository;
        this.leccionRepository = leccionRepository;
        this.completadaRepository = completadaRepository;
        this.progresoRepository = progresoRepository;
        this.evaluacionRepository = evaluacionRepository;
        this.calificacionRepository = calificacionRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    @Transactional
    public AvanceCursoResponse avance(Usuario usuario, Long cursoId) {
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró el curso con id: " + cursoId));

        AvanceCursoResponse avance = calcular(usuario, cursoId);

        // Si el profesor agregó o quitó lecciones, el porcentaje guardado se pone al día.
        if (inscripcionRepository.existsByUsuarioIdAndCursoIdAndActivaTrue(usuario.getId(), cursoId)) {
            guardarProgreso(usuario, curso, avance.porcentaje());
        }

        return avance;
    }

    @Transactional
    public AvanceCursoResponse marcarLeccion(Usuario usuario, Long leccionId, boolean completada) {
        Leccion leccion = leccionRepository.findById(leccionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la lección con id: " + leccionId));

        Curso curso = leccion.getModulo().getCurso();
        exigirInscripcion(usuario, curso.getId());

        Optional<LeccionCompletada> existente =
                completadaRepository.findByUsuarioIdAndLeccionId(usuario.getId(), leccionId);

        if (completada && existente.isEmpty()) {
            LeccionCompletada registro = new LeccionCompletada();
            registro.setUsuario(usuario);
            registro.setLeccion(leccion);
            registro.setFecha(LocalDate.now());
            completadaRepository.save(registro);
        }

        if (!completada) {
            existente.ifPresent(completadaRepository::delete);
        }

        AvanceCursoResponse avance = calcular(usuario, curso.getId());
        guardarProgreso(usuario, curso, avance.porcentaje());

        return avance;
    }

    @Transactional
    public ResultadoEvaluacionResponse presentar(
            Usuario usuario, Long evaluacionId, Map<Long, Long> elegidas) {

        Evaluacion evaluacion = evaluacionRepository.findById(evaluacionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "No se encontró la evaluación con id: " + evaluacionId));

        exigirInscripcion(usuario, evaluacion.getCurso().getId());

        // Solo cuentan las preguntas que tienen opciones para responder.
        List<Pregunta> preguntas = evaluacion.getPreguntas().stream()
                .filter(p -> !p.getRespuestas().isEmpty())
                .toList();

        if (preguntas.isEmpty()) {
            throw new IllegalStateException("Esta evaluación todavía no tiene preguntas.");
        }

        List<DetallePregunta> detalle = new ArrayList<>();
        int correctas = 0;

        for (Pregunta pregunta : preguntas) {
            Long elegidaId = elegidas.get(pregunta.getId());

            Long correctaId = pregunta.getRespuestas().stream()
                    .filter(Respuesta::isCorrecta)
                    .map(Respuesta::getId)
                    .findFirst()
                    .orElse(null);

            boolean acerto = elegidaId != null && pregunta.getRespuestas().stream()
                    .anyMatch(r -> r.getId().equals(elegidaId) && r.isCorrecta());

            if (acerto) {
                correctas++;
            }

            detalle.add(new DetallePregunta(pregunta.getId(), elegidaId, correctaId, acerto));
        }

        double nota = Math.round(correctas * NOTA_MAXIMA / preguntas.size() * 100) / 100.0;

        Calificacion calificacion = calificacionRepository
                .findByUsuarioIdAndEvaluacionId(usuario.getId(), evaluacionId)
                .orElseGet(Calificacion::new);
        calificacion.setUsuario(usuario);
        calificacion.setEvaluacion(evaluacion);
        calificacion.setNota(nota);
        calificacionRepository.save(calificacion);

        return new ResultadoEvaluacionResponse(
                evaluacionId, nota, correctas, preguntas.size(), detalle);
    }

    private AvanceCursoResponse calcular(Usuario usuario, Long cursoId) {
        int total = leccionRepository.findByModuloCursoId(cursoId).size();

        List<Long> completadas = completadaRepository
                .findByUsuarioIdAndLeccionModuloCursoId(usuario.getId(), cursoId)
                .stream()
                .map(c -> c.getLeccion().getId())
                .toList();

        double porcentaje = total == 0
                ? 0
                : Math.round(completadas.size() * 10000.0 / total) / 100.0;

        return new AvanceCursoResponse(cursoId, porcentaje, total, completadas);
    }

    private void guardarProgreso(Usuario usuario, Curso curso, double porcentaje) {
        Progreso progreso = progresoRepository
                .findByUsuarioIdAndCursoId(usuario.getId(), curso.getId())
                .orElseGet(Progreso::new);
        progreso.setUsuario(usuario);
        progreso.setCurso(curso);
        progreso.setPorcentaje(porcentaje);
        progresoRepository.save(progreso);
    }

    private void exigirInscripcion(Usuario usuario, Long cursoId) {
        if (!inscripcionRepository.existsByUsuarioIdAndCursoIdAndActivaTrue(usuario.getId(), cursoId)) {
            throw new AccessDeniedException("Debes estar inscrito en el curso.");
        }
    }
}
