package com.learnhub.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.learnhub.dto.AvanceCursoResponse;
import com.learnhub.dto.ResultadoEvaluacionResponse;
import com.learnhub.entity.Calificacion;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Evaluacion;
import com.learnhub.entity.Leccion;
import com.learnhub.entity.LeccionCompletada;
import com.learnhub.entity.Modulo;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

@ExtendWith(MockitoExtension.class)
class MiAvanceServiceTest {

    @Mock CursoRepository cursoRepository;
    @Mock LeccionRepository leccionRepository;
    @Mock LeccionCompletadaRepository completadaRepository;
    @Mock ProgresoRepository progresoRepository;
    @Mock EvaluacionRepository evaluacionRepository;
    @Mock CalificacionRepository calificacionRepository;
    @Mock InscripcionRepository inscripcionRepository;
    @InjectMocks MiAvanceService servicio;

    Usuario usuario;
    Curso curso;

    @BeforeEach
    void preparar() {
        usuario = new Usuario();
        usuario.setId(3L);
        curso = mock(Curso.class);
        lenient().when(curso.getId()).thenReturn(1L);
    }

    @Test
    void presentarCalificaEnElServidorYGuardaLaNota() {
        Evaluacion evaluacion = mock(Evaluacion.class);
        when(evaluacion.getCurso()).thenReturn(curso);
        // Los mocks se crean antes de usarlos en thenReturn (Mockito no permite anidarlos).
        List<Pregunta> preguntas = List.of(
                pregunta(10L, opcion(100L, true), opcion(101L, false)),
                pregunta(11L, opcion(110L, false), opcion(111L, true)));
        when(evaluacion.getPreguntas()).thenReturn(preguntas);
        when(evaluacionRepository.findById(7L)).thenReturn(Optional.of(evaluacion));
        when(inscripcionRepository.existsByUsuarioIdAndCursoIdAndActivaTrue(3L, 1L)).thenReturn(true);
        when(calificacionRepository.findByUsuarioIdAndEvaluacionId(3L, 7L)).thenReturn(Optional.empty());

        // Acierta la primera y falla la segunda.
        ResultadoEvaluacionResponse r = servicio.presentar(usuario, 7L, Map.of(10L, 100L, 11L, 110L));

        assertEquals(1, r.correctas());
        assertEquals(2, r.total());
        assertEquals(2.5, r.nota());
        assertTrue(r.detalle().get(0).acerto());
        assertFalse(r.detalle().get(1).acerto());
        assertEquals(111L, r.detalle().get(1).respuestaCorrectaId());

        ArgumentCaptor<Calificacion> guardada = ArgumentCaptor.forClass(Calificacion.class);
        verify(calificacionRepository).save(guardada.capture());
        assertEquals(2.5, guardada.getValue().getNota());
    }

    @Test
    void presentarSinInscripcionNoGuardaNada() {
        Evaluacion evaluacion = mock(Evaluacion.class);
        when(evaluacion.getCurso()).thenReturn(curso);
        when(evaluacionRepository.findById(7L)).thenReturn(Optional.of(evaluacion));
        when(inscripcionRepository.existsByUsuarioIdAndCursoIdAndActivaTrue(3L, 1L)).thenReturn(false);

        assertThrows(AccessDeniedException.class,
                () -> servicio.presentar(usuario, 7L, Map.of()));
        verify(calificacionRepository, never()).save(any());
    }

    @Test
    void completarLeccionRecalculaElPorcentaje() {
        Leccion leccion = leccionDelCurso(20L);
        when(leccionRepository.findById(20L)).thenReturn(Optional.of(leccion));
        when(inscripcionRepository.existsByUsuarioIdAndCursoIdAndActivaTrue(3L, 1L)).thenReturn(true);
        when(completadaRepository.findByUsuarioIdAndLeccionId(3L, 20L)).thenReturn(Optional.empty());
        List<Leccion> delCurso = List.of(
                leccion, leccionDelCurso(21L), leccionDelCurso(22L), leccionDelCurso(23L));
        when(leccionRepository.findByModuloCursoId(1L)).thenReturn(delCurso);

        LeccionCompletada registro = new LeccionCompletada();
        registro.setLeccion(leccion);
        when(completadaRepository.findByUsuarioIdAndLeccionModuloCursoId(3L, 1L)).thenReturn(List.of(registro));
        when(progresoRepository.findByUsuarioIdAndCursoId(3L, 1L)).thenReturn(Optional.empty());

        AvanceCursoResponse avance = servicio.marcarLeccion(usuario, 20L, true);

        assertEquals(25.0, avance.porcentaje());
        assertEquals(List.of(20L), avance.leccionesCompletadas());
        verify(completadaRepository).save(any(LeccionCompletada.class));

        ArgumentCaptor<Progreso> progreso = ArgumentCaptor.forClass(Progreso.class);
        verify(progresoRepository).save(progreso.capture());
        assertEquals(25.0, progreso.getValue().getPorcentaje());
    }

    @Test
    void completarLeccionSinInscripcionEsRechazado() {
        Leccion leccion = leccionDelCurso(20L);
        when(leccionRepository.findById(20L)).thenReturn(Optional.of(leccion));
        when(inscripcionRepository.existsByUsuarioIdAndCursoIdAndActivaTrue(3L, 1L)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> servicio.marcarLeccion(usuario, 20L, true));
        verify(completadaRepository, never()).save(any());
    }

    private Leccion leccionDelCurso(Long id) {
        Modulo modulo = mock(Modulo.class);
        lenient().when(modulo.getCurso()).thenReturn(curso);
        Leccion leccion = mock(Leccion.class);
        lenient().when(leccion.getId()).thenReturn(id);
        lenient().when(leccion.getModulo()).thenReturn(modulo);
        return leccion;
    }

    private Pregunta pregunta(Long id, Respuesta... opciones) {
        Pregunta p = mock(Pregunta.class);
        lenient().when(p.getId()).thenReturn(id);
        lenient().when(p.getRespuestas()).thenReturn(List.of(opciones));
        return p;
    }

    private Respuesta opcion(Long id, boolean correcta) {
        Respuesta r = mock(Respuesta.class);
        lenient().when(r.getId()).thenReturn(id);
        lenient().when(r.isCorrecta()).thenReturn(correcta);
        return r;
    }
}
