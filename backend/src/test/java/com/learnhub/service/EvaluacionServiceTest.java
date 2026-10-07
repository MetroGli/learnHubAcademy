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
import com.learnhub.dto.EvaluacionRequest;
import com.learnhub.dto.EvaluacionResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Evaluacion;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.EvaluacionRepository;

@ExtendWith(MockitoExtension.class)
class EvaluacionServiceTest {
 @Mock EvaluacionRepository evaluacionRepository; @Mock CursoRepository cursoRepository; @Mock Curso curso; @InjectMocks EvaluacionService evaluacionService;
 @Test void shouldCreateEvaluacion(){EvaluacionRequest q=req("Parcial","Eval",30.0,1L);when(cursoRepository.findById(1L)).thenReturn(Optional.of(curso));when(evaluacionRepository.save(any(Evaluacion.class))).thenAnswer(i->i.getArgument(0));EvaluacionResponse r=evaluacionService.crearEvaluacion(q);assertEquals("Parcial",r.getTitulo());assertEquals(30.0,r.getPorcentaje());}
 @Test void shouldThrowWhenCourseMissingOnCreate(){when(cursoRepository.findById(1L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->evaluacionService.crearEvaluacion(req("x","y",10.0,1L)));assertEquals("Course not found with id: 1",e.getMessage());}
 @Test void shouldListEvaluaciones(){Evaluacion e=new Evaluacion();e.setTitulo("Parcial");e.setPorcentaje(20.0);when(evaluacionRepository.findAll()).thenReturn(List.of(e));List<EvaluacionResponse> r=evaluacionService.listarEvaluaciones();assertEquals(1,r.size());assertEquals("Parcial",r.get(0).getTitulo());assertNull(r.get(0).getCursoId());}
 @Test void shouldFindEvaluacion(){Evaluacion e=new Evaluacion();e.setTitulo("Final");e.setCurso(curso);when(curso.getId()).thenReturn(4L);when(evaluacionRepository.findById(3L)).thenReturn(Optional.of(e));EvaluacionResponse r=evaluacionService.buscarEvaluacionPorId(3L);assertEquals("Final",r.getTitulo());assertEquals(4L,r.getCursoId());}
 @Test void shouldThrowWhenEvaluacionMissing(){when(evaluacionRepository.findById(9L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->evaluacionService.buscarEvaluacionPorId(9L));assertEquals("Evaluation not found with id: 9",e.getMessage());}
 @Test void shouldUpdateEvaluacion(){Evaluacion e=new Evaluacion();EvaluacionRequest q=req("Nuevo","Desc",55.0,4L);when(evaluacionRepository.findById(3L)).thenReturn(Optional.of(e));when(cursoRepository.findById(4L)).thenReturn(Optional.of(curso));when(evaluacionRepository.save(e)).thenReturn(e);EvaluacionResponse r=evaluacionService.actualizarEvaluacion(3L,q);assertEquals("Nuevo",r.getTitulo());assertEquals(55.0,r.getPorcentaje());assertSame(curso,e.getCurso());}
 @Test void shouldDeleteExistingEvaluacion(){when(evaluacionRepository.existsById(3L)).thenReturn(true);evaluacionService.eliminarEvaluacion(3L);verify(evaluacionRepository).deleteById(3L);}
 @Test void shouldThrowWhenDeletingMissingEvaluacion(){when(evaluacionRepository.existsById(3L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->evaluacionService.eliminarEvaluacion(3L));assertEquals("Evaluation not found with id: 3",e.getMessage());}
 private EvaluacionRequest req(String t,String d,Double p,Long c){EvaluacionRequest r=new EvaluacionRequest();r.setTitulo(t);r.setDescripcion(d);r.setPorcentaje(p);r.setCursoId(c);return r;}
}
