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
import com.learnhub.dto.CursoRequest;
import com.learnhub.dto.CursoResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Usuario;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class CursoServiceTest {
 @Mock CursoRepository cursoRepository; @Mock UsuarioRepository usuarioRepository; @Mock Usuario profesor; @InjectMocks CursoService cursoService;
 @Test void shouldCreateCurso(){CursoRequest q=req("Java","Curso Java",true,1L);when(usuarioRepository.findById(1L)).thenReturn(Optional.of(profesor));when(cursoRepository.save(any(Curso.class))).thenAnswer(i->i.getArgument(0));CursoResponse r=cursoService.crearCurso(q);assertEquals("Java",r.getNombre());assertEquals("Curso Java",r.getDescripcion());assertTrue(r.isActivo());}
 @Test void shouldThrowWhenProfessorMissingOnCreate(){when(usuarioRepository.findById(1L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->cursoService.crearCurso(req("Java","x",true,1L)));assertEquals("No se encontró el profesor con id: 1",e.getMessage());}
 @Test void shouldListCursos(){Curso c=new Curso();c.setNombre("Java");c.setDescripcion("x");c.setActivo(true);when(cursoRepository.findAll()).thenReturn(List.of(c));List<CursoResponse> r=cursoService.listarCursos();assertEquals(1,r.size());assertEquals("Java",r.get(0).getNombre());assertNull(r.get(0).getProfesorId());}
 @Test void shouldFindCurso(){Curso c=new Curso();c.setNombre("Java");c.setProfesor(profesor);when(profesor.getId()).thenReturn(1L);when(cursoRepository.findById(2L)).thenReturn(Optional.of(c));CursoResponse r=cursoService.buscarCursoPorId(2L);assertEquals("Java",r.getNombre());assertEquals(1L,r.getProfesorId());}
 @Test void shouldThrowWhenCursoMissing(){when(cursoRepository.findById(9L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->cursoService.buscarCursoPorId(9L));assertEquals("No se encontró el curso con id: 9",e.getMessage());}
 @Test void shouldUpdateCurso(){Curso c=new Curso();CursoRequest q=req("Python","Nuevo",false,3L);when(cursoRepository.findById(2L)).thenReturn(Optional.of(c));when(usuarioRepository.findById(3L)).thenReturn(Optional.of(profesor));when(cursoRepository.save(c)).thenReturn(c);CursoResponse r=cursoService.actualizarCurso(2L,q);assertEquals("Python",r.getNombre());assertEquals("Nuevo",r.getDescripcion());assertFalse(r.isActivo());assertSame(profesor,c.getProfesor());}
 @Test void shouldDeleteExistingCurso(){when(cursoRepository.existsById(2L)).thenReturn(true);cursoService.eliminarCurso(2L);verify(cursoRepository).deleteById(2L);}
 @Test void shouldThrowWhenDeletingMissingCurso(){when(cursoRepository.existsById(2L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->cursoService.eliminarCurso(2L));assertEquals("No se encontró el curso con id: 2",e.getMessage());}
 private CursoRequest req(String n,String d,boolean a,Long p){CursoRequest r=new CursoRequest();r.setNombre(n);r.setDescripcion(d);r.setActivo(a);r.setProfesorId(p);return r;}
}
