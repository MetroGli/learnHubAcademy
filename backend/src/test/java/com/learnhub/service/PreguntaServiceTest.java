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
import com.learnhub.dto.PreguntaRequest;
import com.learnhub.dto.PreguntaResponse;
import com.learnhub.entity.Evaluacion;
import com.learnhub.entity.Pregunta;
import com.learnhub.repository.EvaluacionRepository;
import com.learnhub.repository.PreguntaRepository;

@ExtendWith(MockitoExtension.class)
class PreguntaServiceTest {
 @Mock PreguntaRepository preguntaRepository; @Mock EvaluacionRepository evaluacionRepository; @Mock Evaluacion evaluacion; @InjectMocks PreguntaService preguntaService;
 @Test void shouldCreatePregunta(){PreguntaRequest q=req("2+2?","MULTIPLE",1L);when(evaluacionRepository.findById(1L)).thenReturn(Optional.of(evaluacion));when(preguntaRepository.save(any(Pregunta.class))).thenAnswer(i->i.getArgument(0));PreguntaResponse r=preguntaService.crearPregunta(q);assertEquals("2+2?",r.getEnunciado());assertEquals("MULTIPLE",r.getTipo());}
 @Test void shouldThrowWhenEvaluationMissingOnCreate(){when(evaluacionRepository.findById(1L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->preguntaService.crearPregunta(req("x","T",1L)));assertEquals("No se encontró la evaluación con id: 1",e.getMessage());}
 @Test void shouldListPreguntas(){Pregunta p=new Pregunta();p.setEnunciado("Q");p.setTipo("ABIERTA");when(preguntaRepository.findAll()).thenReturn(List.of(p));List<PreguntaResponse> r=preguntaService.listarPreguntas();assertEquals(1,r.size());assertEquals("Q",r.get(0).getEnunciado());assertNull(r.get(0).getEvaluacionId());}
 @Test void shouldFindPregunta(){Pregunta p=new Pregunta();p.setEnunciado("Q2");p.setEvaluacion(evaluacion);when(evaluacion.getId()).thenReturn(3L);when(preguntaRepository.findById(2L)).thenReturn(Optional.of(p));PreguntaResponse r=preguntaService.buscarPreguntaPorId(2L);assertEquals("Q2",r.getEnunciado());assertEquals(3L,r.getEvaluacionId());}
 @Test void shouldThrowWhenPreguntaMissing(){when(preguntaRepository.findById(9L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->preguntaService.buscarPreguntaPorId(9L));assertEquals("No se encontró la pregunta con id: 9",e.getMessage());}
 @Test void shouldUpdatePregunta(){Pregunta p=new Pregunta();PreguntaRequest q=req("New","VF",4L);when(preguntaRepository.findById(2L)).thenReturn(Optional.of(p));when(evaluacionRepository.findById(4L)).thenReturn(Optional.of(evaluacion));when(preguntaRepository.save(p)).thenReturn(p);PreguntaResponse r=preguntaService.actualizarPregunta(2L,q);assertEquals("New",r.getEnunciado());assertEquals("VF",r.getTipo());assertSame(evaluacion,p.getEvaluacion());}
 @Test void shouldDeleteExistingPregunta(){when(preguntaRepository.existsById(2L)).thenReturn(true);preguntaService.eliminarPregunta(2L);verify(preguntaRepository).deleteById(2L);}
 @Test void shouldThrowWhenDeletingMissingPregunta(){when(preguntaRepository.existsById(2L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->preguntaService.eliminarPregunta(2L));assertEquals("No se encontró la pregunta con id: 2",e.getMessage());}
 private PreguntaRequest req(String e,String t,Long id){PreguntaRequest r=new PreguntaRequest();r.setEnunciado(e);r.setTipo(t);r.setEvaluacionId(id);return r;}
}
