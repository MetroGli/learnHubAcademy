package com.learnhub.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.learnhub.dto.CalificacionRequest;
import com.learnhub.dto.CalificacionResponse;
import com.learnhub.entity.Calificacion;
import com.learnhub.entity.Evaluacion;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CalificacionRepository;
import com.learnhub.repository.EvaluacionRepository;
import com.learnhub.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class CalificacionServiceTest {
    @Mock CalificacionRepository calificacionRepository;
    @Mock UsuarioRepository usuarioRepository;
    @Mock EvaluacionRepository evaluacionRepository;
    @Mock Usuario usuario;
    @Mock Evaluacion evaluacion;
    @InjectMocks CalificacionService calificacionService;

    @Test void shouldCreateCalificacion() {
        CalificacionRequest request=request(1L,2L,4.5);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(evaluacionRepository.findById(2L)).thenReturn(Optional.of(evaluacion));
        when(calificacionRepository.save(any(Calificacion.class))).thenAnswer(i->i.getArgument(0));
        CalificacionResponse r=calificacionService.crearCalificacion(request);
        assertEquals(4.5,r.getNota()); assertNull(r.getId());
        verify(calificacionRepository).save(any(Calificacion.class));
    }
    @Test void shouldThrowWhenEvaluationDoesNotExistOnCreate() {
        CalificacionRequest request=request(1L,2L,4.5);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(evaluacionRepository.findById(2L)).thenReturn(Optional.empty());
        IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->calificacionService.crearCalificacion(request));
        assertEquals("Evaluation not found with id: 2",e.getMessage());
    }
    @Test void shouldListCalificacionesIncludingNullAssociations() {
        Calificacion c=new Calificacion(); c.setNota(3.0);
        when(calificacionRepository.findAll()).thenReturn(List.of(c));
        List<CalificacionResponse> r=calificacionService.listarCalificaciones();
        assertEquals(1,r.size()); assertEquals(3.0,r.get(0).getNota()); assertNull(r.get(0).getUsuarioId()); assertNull(r.get(0).getEvaluacionId());
        verify(calificacionRepository).findAll();
    }
    @Test void shouldFindCalificacion() {
        Calificacion c=new Calificacion(); c.setNota(5.0); c.setUsuario(usuario); c.setEvaluacion(evaluacion);
        when(usuario.getId()).thenReturn(1L); when(evaluacion.getId()).thenReturn(2L);
        when(calificacionRepository.findById(7L)).thenReturn(Optional.of(c));
        CalificacionResponse r=calificacionService.buscarCalificacionPorId(7L);
        assertEquals(5.0,r.getNota()); assertEquals(1L,r.getUsuarioId()); assertEquals(2L,r.getEvaluacionId());
    }
    @Test void shouldThrowWhenCalificacionDoesNotExist() {
        when(calificacionRepository.findById(99L)).thenReturn(Optional.empty());
        IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->calificacionService.buscarCalificacionPorId(99L));
        assertEquals("Grade not found with id: 99",e.getMessage());
    }
    @Test void shouldUpdateCalificacion() {
        Calificacion c=new Calificacion(); c.setNota(2.0); CalificacionRequest request=request(1L,2L,4.0);
        when(calificacionRepository.findById(7L)).thenReturn(Optional.of(c)); when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario)); when(evaluacionRepository.findById(2L)).thenReturn(Optional.of(evaluacion));
        when(calificacionRepository.save(c)).thenReturn(c); CalificacionResponse r=calificacionService.actualizarCalificacion(7L,request);
        assertEquals(4.0,r.getNota()); verify(calificacionRepository).save(c);
    }
    @Test void shouldDeleteExistingCalificacion() {
        when(calificacionRepository.existsById(7L)).thenReturn(true); calificacionService.eliminarCalificacion(7L); verify(calificacionRepository).deleteById(7L);
    }
    @Test void shouldThrowWhenDeletingMissingCalificacion() {
        when(calificacionRepository.existsById(7L)).thenReturn(false); IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->calificacionService.eliminarCalificacion(7L)); assertEquals("Grade not found with id: 7",e.getMessage());
    }
    private CalificacionRequest request(Long u,Long ev,Double n){CalificacionRequest r=new CalificacionRequest();r.setUsuarioId(u);r.setEvaluacionId(ev);r.setNota(n);return r;}
}
