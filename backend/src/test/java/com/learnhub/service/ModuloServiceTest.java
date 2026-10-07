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
import com.learnhub.dto.ModuloRequest;
import com.learnhub.dto.ModuloResponse;
import com.learnhub.entity.Curso;
import com.learnhub.entity.Modulo;
import com.learnhub.repository.CursoRepository;
import com.learnhub.repository.ModuloRepository;

@ExtendWith(MockitoExtension.class)
class ModuloServiceTest {
 @Mock ModuloRepository moduloRepository; @Mock CursoRepository cursoRepository; @Mock Curso curso; @InjectMocks ModuloService moduloService;
 @Test void shouldCreateModulo(){ModuloRequest q=req("M1","Desc",1,2L);when(cursoRepository.findById(2L)).thenReturn(Optional.of(curso));when(moduloRepository.save(any(Modulo.class))).thenAnswer(i->i.getArgument(0));ModuloResponse r=moduloService.crearModulo(q);assertEquals("M1",r.getNombre());assertEquals("Desc",r.getDescripcion());assertEquals(1,r.getOrden());}
 @Test void shouldThrowWhenCourseMissingOnCreate(){when(cursoRepository.findById(2L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->moduloService.crearModulo(req("x","y",1,2L)));assertEquals("Course not found with id: 2",e.getMessage());}
 @Test void shouldListModulos(){Modulo m=new Modulo();m.setNombre("M1");m.setOrden(1);when(moduloRepository.findAll()).thenReturn(List.of(m));List<ModuloResponse> r=moduloService.listarModulos();assertEquals(1,r.size());assertEquals("M1",r.get(0).getNombre());assertNull(r.get(0).getCursoId());}
 @Test void shouldFindModulo(){Modulo m=new Modulo();m.setNombre("M2");m.setCurso(curso);when(curso.getId()).thenReturn(4L);when(moduloRepository.findById(3L)).thenReturn(Optional.of(m));ModuloResponse r=moduloService.buscarModuloPorId(3L);assertEquals("M2",r.getNombre());assertEquals(4L,r.getCursoId());}
 @Test void shouldThrowWhenModuloMissing(){when(moduloRepository.findById(9L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->moduloService.buscarModuloPorId(9L));assertEquals("Module not found with id: 9",e.getMessage());}
 @Test void shouldUpdateModulo(){Modulo m=new Modulo();ModuloRequest q=req("New","Updated",2,5L);when(moduloRepository.findById(3L)).thenReturn(Optional.of(m));when(cursoRepository.findById(5L)).thenReturn(Optional.of(curso));when(moduloRepository.save(m)).thenReturn(m);ModuloResponse r=moduloService.actualizarModulo(3L,q);assertEquals("New",r.getNombre());assertEquals(2,r.getOrden());assertSame(curso,m.getCurso());}
 @Test void shouldDeleteExistingModulo(){when(moduloRepository.existsById(3L)).thenReturn(true);moduloService.eliminarModulo(3L);verify(moduloRepository).deleteById(3L);}
 @Test void shouldThrowWhenDeletingMissingModulo(){when(moduloRepository.existsById(3L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->moduloService.eliminarModulo(3L));assertEquals("Module not found with id: 3",e.getMessage());}
 private ModuloRequest req(String n,String d,Integer o,Long c){ModuloRequest r=new ModuloRequest();r.setNombre(n);r.setDescripcion(d);r.setOrden(o);r.setCursoId(c);return r;}
}
