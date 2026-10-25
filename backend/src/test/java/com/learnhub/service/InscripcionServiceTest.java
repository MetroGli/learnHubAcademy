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
import com.learnhub.dto.InscripcionRequest;
import com.learnhub.dto.InscripcionResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Inscripcion;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.InscripcionRepository;
import com.learnhub.repository.UsuarioRepository;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class InscripcionServiceTest {
 @Mock InscripcionRepository inscripcionRepository; @Mock UsuarioRepository usuarioRepository; @Mock CursoRepository cursoRepository; @Mock Usuario usuario; @Mock Curso curso; @InjectMocks InscripcionService inscripcionService;
 @Test void shouldCreateInscripcion(){InscripcionRequest q=req(1L,2L,true);when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));when(cursoRepository.findById(2L)).thenReturn(Optional.of(curso));when(inscripcionRepository.save(any(Inscripcion.class))).thenAnswer(i->i.getArgument(0));InscripcionResponse r=inscripcionService.crearInscripcion(q);assertTrue(r.isActiva());assertEquals(LocalDate.now(),r.getFechaInscripcion());}
 @Test void shouldThrowWhenCourseMissingOnCreate(){InscripcionRequest q=req(1L,2L,true);when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));when(cursoRepository.findById(2L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->inscripcionService.crearInscripcion(q));assertEquals("No se encontró el curso con id: 2",e.getMessage());}
 @Test void shouldListInscripciones(){Inscripcion i=new Inscripcion();i.setActiva(false);i.setFechaInscripcion(LocalDate.of(2026,1,2));when(inscripcionRepository.findAll()).thenReturn(List.of(i));List<InscripcionResponse> r=inscripcionService.listarInscripciones();assertEquals(1,r.size());assertFalse(r.get(0).isActiva());assertEquals(LocalDate.of(2026,1,2),r.get(0).getFechaInscripcion());}
 @Test void shouldFindInscripcion(){Inscripcion i=new Inscripcion();i.setUsuario(usuario);i.setCurso(curso);i.setFechaInscripcion(LocalDate.of(2026,2,3));when(usuario.getId()).thenReturn(1L);when(curso.getId()).thenReturn(2L);when(inscripcionRepository.findById(4L)).thenReturn(Optional.of(i));InscripcionResponse r=inscripcionService.buscarInscripcionPorId(4L);assertEquals(1L,r.getUsuarioId());assertEquals(2L,r.getCursoId());}
 @Test void shouldThrowWhenInscripcionMissing(){when(inscripcionRepository.findById(8L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->inscripcionService.buscarInscripcionPorId(8L));assertEquals("No se encontró la inscripción con id: 8",e.getMessage());}
 @Test void shouldUpdateInscripcion(){Inscripcion i=new Inscripcion();InscripcionRequest q=req(3L,4L,false);Usuario u=org.mockito.Mockito.mock(Usuario.class);Curso c=org.mockito.Mockito.mock(Curso.class);when(inscripcionRepository.findById(5L)).thenReturn(Optional.of(i));when(usuarioRepository.findById(3L)).thenReturn(Optional.of(u));when(cursoRepository.findById(4L)).thenReturn(Optional.of(c));when(inscripcionRepository.save(i)).thenReturn(i);InscripcionResponse r=inscripcionService.actualizarInscripcion(5L,q);assertFalse(r.isActiva());assertSame(u,i.getUsuario());assertSame(c,i.getCurso());}
 @Test void shouldDeleteExistingInscripcion(){when(inscripcionRepository.existsById(5L)).thenReturn(true);inscripcionService.eliminarInscripcion(5L);verify(inscripcionRepository).deleteById(5L);}
 @Test void shouldThrowWhenDeletingMissingInscripcion(){when(inscripcionRepository.existsById(5L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->inscripcionService.eliminarInscripcion(5L));assertEquals("No se encontró la inscripción con id: 5",e.getMessage());}
 private InscripcionRequest req(Long u,Long c,boolean a){InscripcionRequest r=new InscripcionRequest();r.setUsuarioId(u);r.setCursoId(c);r.setActiva(a);return r;}
}
