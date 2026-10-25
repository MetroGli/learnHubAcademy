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
import com.learnhub.dto.ProgresoRequest;
import com.learnhub.dto.ProgresoResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Progreso;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.ProgresoRepository;
import com.learnhub.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class ProgresoServiceTest {
 @Mock ProgresoRepository progresoRepository; @Mock UsuarioRepository usuarioRepository; @Mock CursoRepository cursoRepository; @Mock Usuario usuario; @Mock Curso curso; @InjectMocks ProgresoService progresoService;
 @Test void shouldCreateProgreso(){ProgresoRequest q=req(1L,2L,75.0);when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));when(cursoRepository.findById(2L)).thenReturn(Optional.of(curso));when(progresoRepository.save(any(Progreso.class))).thenAnswer(i->i.getArgument(0));ProgresoResponse r=progresoService.crearProgreso(q);assertEquals(75.0,r.getPorcentaje());}
 @Test void shouldThrowWhenCourseMissingOnCreate(){ProgresoRequest q=req(1L,2L,75.0);when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));when(cursoRepository.findById(2L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->progresoService.crearProgreso(q));assertEquals("No se encontró el curso con id: 2",e.getMessage());}
 @Test void shouldListProgresos(){Progreso p=new Progreso();p.setPorcentaje(20.0);when(progresoRepository.findAll()).thenReturn(List.of(p));List<ProgresoResponse> r=progresoService.listarProgresos();assertEquals(1,r.size());assertEquals(20.0,r.get(0).getPorcentaje());assertNull(r.get(0).getUsuarioId());assertNull(r.get(0).getCursoId());}
 @Test void shouldFindProgreso(){Progreso p=new Progreso();p.setUsuario(usuario);p.setCurso(curso);p.setPorcentaje(40.0);when(usuario.getId()).thenReturn(1L);when(curso.getId()).thenReturn(2L);when(progresoRepository.findById(3L)).thenReturn(Optional.of(p));ProgresoResponse r=progresoService.buscarProgresoPorId(3L);assertEquals(40.0,r.getPorcentaje());assertEquals(1L,r.getUsuarioId());assertEquals(2L,r.getCursoId());}
 @Test void shouldThrowWhenProgresoMissing(){when(progresoRepository.findById(9L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->progresoService.buscarProgresoPorId(9L));assertEquals("No se encontró el progreso con id: 9",e.getMessage());}
 @Test void shouldUpdateProgreso(){Progreso p=new Progreso();ProgresoRequest q=req(3L,4L,88.0);when(progresoRepository.findById(3L)).thenReturn(Optional.of(p));when(usuarioRepository.findById(3L)).thenReturn(Optional.of(usuario));when(cursoRepository.findById(4L)).thenReturn(Optional.of(curso));when(progresoRepository.save(p)).thenReturn(p);ProgresoResponse r=progresoService.actualizarProgreso(3L,q);assertEquals(88.0,r.getPorcentaje());assertSame(usuario,p.getUsuario());assertSame(curso,p.getCurso());}
 @Test void shouldDeleteExistingProgreso(){when(progresoRepository.existsById(3L)).thenReturn(true);progresoService.eliminarProgreso(3L);verify(progresoRepository).deleteById(3L);}
 @Test void shouldThrowWhenDeletingMissingProgreso(){when(progresoRepository.existsById(3L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->progresoService.eliminarProgreso(3L));assertEquals("No se encontró el progreso con id: 3",e.getMessage());}
 private ProgresoRequest req(Long u,Long c,Double p){ProgresoRequest r=new ProgresoRequest();r.setUsuarioId(u);r.setCursoId(c);r.setPorcentaje(p);return r;}
}
