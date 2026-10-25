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
import com.learnhub.dto.LeccionRequest;
import com.learnhub.dto.LeccionResponse;
import com.learnhub.entity.Leccion;
import com.learnhub.entity.Modulo;
import com.learnhub.repository.LeccionRepository;
import com.learnhub.repository.ModuloRepository;

@ExtendWith(MockitoExtension.class)
class LeccionServiceTest {
 @Mock LeccionRepository leccionRepository; @Mock ModuloRepository moduloRepository; @Mock Modulo modulo; @InjectMocks LeccionService leccionService;
 @Test void shouldCreateLeccion(){LeccionRequest q=req("Intro","Contenido",1,2L);when(moduloRepository.findById(2L)).thenReturn(Optional.of(modulo));when(leccionRepository.save(any(Leccion.class))).thenAnswer(i->i.getArgument(0));LeccionResponse r=leccionService.crearLeccion(q);assertEquals("Intro",r.getTitulo());assertEquals("Contenido",r.getContenido());assertEquals(1,r.getOrden());}
 @Test void shouldThrowWhenModuleMissingOnCreate(){when(moduloRepository.findById(2L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->leccionService.crearLeccion(req("x","y",1,2L)));assertEquals("No se encontró el módulo con id: 2",e.getMessage());}
 @Test void shouldListLecciones(){Leccion l=new Leccion();l.setTitulo("L1");l.setContenido("C");l.setOrden(1);when(leccionRepository.findAll()).thenReturn(List.of(l));List<LeccionResponse> r=leccionService.listarLecciones();assertEquals(1,r.size());assertEquals("L1",r.get(0).getTitulo());assertNull(r.get(0).getModuloId());}
 @Test void shouldFindLeccion(){Leccion l=new Leccion();l.setTitulo("L2");l.setModulo(modulo);when(modulo.getId()).thenReturn(2L);when(leccionRepository.findById(3L)).thenReturn(Optional.of(l));LeccionResponse r=leccionService.buscarLeccionPorId(3L);assertEquals("L2",r.getTitulo());assertEquals(2L,r.getModuloId());}
 @Test void shouldThrowWhenLeccionMissing(){when(leccionRepository.findById(9L)).thenReturn(Optional.empty());IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->leccionService.buscarLeccionPorId(9L));assertEquals("No se encontró la lección con id: 9",e.getMessage());}
 @Test void shouldUpdateLeccion(){Leccion l=new Leccion();LeccionRequest q=req("New","Updated",4,5L);when(leccionRepository.findById(3L)).thenReturn(Optional.of(l));when(moduloRepository.findById(5L)).thenReturn(Optional.of(modulo));when(leccionRepository.save(l)).thenReturn(l);LeccionResponse r=leccionService.actualizarLeccion(3L,q);assertEquals("New",r.getTitulo());assertEquals(4,r.getOrden());assertSame(modulo,l.getModulo());}
 @Test void shouldDeleteExistingLeccion(){when(leccionRepository.existsById(3L)).thenReturn(true);leccionService.eliminarLeccion(3L);verify(leccionRepository).deleteById(3L);}
 @Test void shouldThrowWhenDeletingMissingLeccion(){when(leccionRepository.existsById(3L)).thenReturn(false);IllegalArgumentException e=assertThrows(IllegalArgumentException.class,()->leccionService.eliminarLeccion(3L));assertEquals("No se encontró la lección con id: 3",e.getMessage());}
 private LeccionRequest req(String t,String c,Integer o,Long m){LeccionRequest r=new LeccionRequest();r.setTitulo(t);r.setContenido(c);r.setOrden(o);r.setModuloId(m);return r;}
}
