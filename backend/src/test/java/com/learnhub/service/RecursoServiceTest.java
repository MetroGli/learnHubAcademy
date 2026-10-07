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
import com.learnhub.dto.RecursoRequest;
import com.learnhub.dto.RecursoResponse;
import com.learnhub.entity.Leccion;
import com.learnhub.entity.Recurso;
import com.learnhub.repository.LeccionRepository;
import com.learnhub.repository.RecursoRepository;

@ExtendWith(MockitoExtension.class)
class RecursoServiceTest {
 @Mock RecursoRepository recursoRepository; @Mock LeccionRepository leccionRepository; @Mock Leccion leccion; @InjectMocks RecursoService recursoService;
 @Test void shouldCreateRecurso(){RecursoRequest q=req("PDF","DOCUMENTO","https://x",1L);when(leccionRepository.findById(1L)).thenReturn(Optional.of(leccion));when(recursoRepository.save(any(Recurso.class))).thenAnswer(i->i.getArgument(0));RecursoResponse r=recursoService.crearRecurso(q);assertEquals("PDF",r.getNombre());assertEquals("DOCUMENTO",r.getTipo());assertEquals("https://x",r.getUrl());}
 @Test void shouldThrowWhenLessonMissingOnCreate(){when(leccionRepository.findById(1L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->recursoService.crearRecurso(req("x","y","z",1L)));assertEquals("Lesson not found with id: 1",e.getMessage());}
 @Test void shouldListRecursos(){Recurso r1=new Recurso();r1.setNombre("R");r1.setTipo("PDF");r1.setUrl("u");when(recursoRepository.findAll()).thenReturn(List.of(r1));List<RecursoResponse> r=recursoService.listarRecursos();assertEquals(1,r.size());assertEquals("R",r.get(0).getNombre());assertNull(r.get(0).getLeccionId());}
 @Test void shouldFindRecurso(){Recurso r1=new Recurso();r1.setNombre("R2");r1.setLeccion(leccion);when(leccion.getId()).thenReturn(3L);when(recursoRepository.findById(2L)).thenReturn(Optional.of(r1));RecursoResponse r=recursoService.buscarRecursoPorId(2L);assertEquals("R2",r.getNombre());assertEquals(3L,r.getLeccionId());}
 @Test void shouldThrowWhenRecursoMissing(){when(recursoRepository.findById(9L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->recursoService.buscarRecursoPorId(9L));assertEquals("Resource not found with id: 9",e.getMessage());}
 @Test void shouldUpdateRecurso(){Recurso r1=new Recurso();RecursoRequest q=req("New","VIDEO","url2",4L);when(recursoRepository.findById(2L)).thenReturn(Optional.of(r1));when(leccionRepository.findById(4L)).thenReturn(Optional.of(leccion));when(recursoRepository.save(r1)).thenReturn(r1);RecursoResponse r=recursoService.actualizarRecurso(2L,q);assertEquals("New",r.getNombre());assertEquals("VIDEO",r.getTipo());assertSame(leccion,r1.getLeccion());}
 @Test void shouldDeleteExistingRecurso(){when(recursoRepository.existsById(2L)).thenReturn(true);recursoService.eliminarRecurso(2L);verify(recursoRepository).deleteById(2L);}
 @Test void shouldThrowWhenDeletingMissingRecurso(){when(recursoRepository.existsById(2L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->recursoService.eliminarRecurso(2L));assertEquals("Resource not found with id: 2",e.getMessage());}
 private RecursoRequest req(String n,String t,String u,Long l){RecursoRequest r=new RecursoRequest();r.setNombre(n);r.setTipo(t);r.setUrl(u);r.setLeccionId(l);return r;}
}
