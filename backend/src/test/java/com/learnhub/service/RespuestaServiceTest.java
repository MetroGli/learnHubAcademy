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
import com.learnhub.dto.RespuestaRequest;
import com.learnhub.dto.RespuestaResponse;
import com.learnhub.entity.Pregunta;
import com.learnhub.entity.Respuesta;
import com.learnhub.repository.PreguntaRepository;
import com.learnhub.repository.RespuestaRepository;

@ExtendWith(MockitoExtension.class)
class RespuestaServiceTest {
 @Mock RespuestaRepository respuestaRepository; @Mock PreguntaRepository preguntaRepository; @Mock Pregunta pregunta; @InjectMocks RespuestaService respuestaService;
 @Test void shouldCreateRespuesta(){RespuestaRequest q=req("Correcta",true,1L);when(preguntaRepository.findById(1L)).thenReturn(Optional.of(pregunta));when(respuestaRepository.save(any(Respuesta.class))).thenAnswer(i->i.getArgument(0));RespuestaResponse r=respuestaService.crearRespuesta(q);assertEquals("Correcta",r.getTexto());assertTrue(r.isCorrecta());}
 @Test void shouldThrowWhenQuestionMissingOnCreate(){when(preguntaRepository.findById(1L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->respuestaService.crearRespuesta(req("x",false,1L)));assertEquals("No se encontró la pregunta con id: 1",e.getMessage());}
 @Test void shouldListRespuestas(){Respuesta a=new Respuesta();a.setTexto("A");a.setCorrecta(true);when(respuestaRepository.findAll()).thenReturn(List.of(a));List<RespuestaResponse> r=respuestaService.listarRespuestas();assertEquals(1,r.size());assertEquals("A",r.get(0).getTexto());assertTrue(r.get(0).isCorrecta());assertNull(r.get(0).getPreguntaId());}
 @Test void shouldFindRespuesta(){Respuesta a=new Respuesta();a.setTexto("B");a.setPregunta(pregunta);when(pregunta.getId()).thenReturn(3L);when(respuestaRepository.findById(2L)).thenReturn(Optional.of(a));RespuestaResponse r=respuestaService.buscarRespuestaPorId(2L);assertEquals("B",r.getTexto());assertEquals(3L,r.getPreguntaId());}
 @Test void shouldThrowWhenRespuestaMissing(){when(respuestaRepository.findById(9L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->respuestaService.buscarRespuestaPorId(9L));assertEquals("No se encontró la respuesta con id: 9",e.getMessage());}
 @Test void shouldUpdateRespuesta(){Respuesta a=new Respuesta();RespuestaRequest q=req("New",true,4L);when(respuestaRepository.findById(2L)).thenReturn(Optional.of(a));when(preguntaRepository.findById(4L)).thenReturn(Optional.of(pregunta));when(respuestaRepository.save(a)).thenReturn(a);RespuestaResponse r=respuestaService.actualizarRespuesta(2L,q);assertEquals("New",r.getTexto());assertTrue(r.isCorrecta());assertSame(pregunta,a.getPregunta());}
 @Test void shouldDeleteExistingRespuesta(){when(respuestaRepository.existsById(2L)).thenReturn(true);respuestaService.eliminarRespuesta(2L);verify(respuestaRepository).deleteById(2L);}
 @Test void shouldThrowWhenDeletingMissingRespuesta(){when(respuestaRepository.existsById(2L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->respuestaService.eliminarRespuesta(2L));assertEquals("No se encontró la respuesta con id: 2",e.getMessage());}
 private RespuestaRequest req(String t,boolean c,Long p){RespuestaRequest r=new RespuestaRequest();r.setTexto(t);r.setCorrecta(c);r.setPreguntaId(p);return r;}
}
