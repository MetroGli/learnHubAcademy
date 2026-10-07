-- LearnHub - Datos iniciales para pruebas
-- PostgreSQL 17
-- Uso recomendado: ejecutar sobre una base de datos de desarrollo/pruebas.
-- Este script limpia los datos actuales y vuelve a poblar todas las entidades
-- principales del backend implementado en Fase 2.

BEGIN;

-- ============================================================
-- 1. LIMPIEZA
-- ============================================================
-- Se eliminan primero las tablas dependientes para respetar las FK.

TRUNCATE TABLE
    calificaciones,
    progresos,
    inscripciones,
    respuestas,
    preguntas,
    evaluaciones,
    recursos,
    lecciones,
    modulos,
    cursos,
    usuarios
RESTART IDENTITY CASCADE;

-- ============================================================
-- 2. USUARIOS
-- ============================================================
-- Password de prueba para todos:
-- 12345678
-- Hash BCrypt compatible con PasswordEncoder de Spring Security.

INSERT INTO usuarios
    (id, nombre, apellido, email, password, rol, activo)
VALUES
    (1, 'Administrador', 'LearnHub', 'admin@learnhub.com',
     '$2b$10$jPWp12ulpEBrGvcs8rgO0uP1ErVKdgM2CW98vJYSD/k7zWs/rdjsm',
     'ADMINISTRADOR', true),

    (2, 'Juan Pablo', 'Camacho', 'juan@learnhub.com',
     '$2b$10$FQQxRvWNKTXCvxRmTFq0LuRdfiGDrYxPq0Mr9Zi3.c/CxZPcf.vVq',
     'PROFESOR', true),

    (3, 'Pedro', 'Gómez', 'pedro@learnhub.com',
     '$2b$10$yJ33uchFpgtL9ybtrxo2hutu71GlRLlwiZY5WkhusItP4wMPHY.Jy',
     'ESTUDIANTE', true),

    (4, 'María', 'Rodríguez', 'maria@learnhub.com',
     '$2b$10$iBkOjv01KPmtMmz/i95nm.1G9StKG5M6YgFHwvWRFD0pinl7.BmVy',
     'ESTUDIANTE', true);

-- ============================================================
-- 3. CURSOS
-- ============================================================

INSERT INTO cursos
    (id, nombre, descripcion, profesor_id, activo)
VALUES
    (1, 'Java con Spring Boot',
     'Curso de desarrollo backend con Java y Spring Boot.',
     2, true),

    (2, 'Bases de Datos con PostgreSQL',
     'Fundamentos de modelado, SQL y administración de PostgreSQL.',
     2, true);

-- ============================================================
-- 4. MÓDULOS
-- ============================================================

INSERT INTO modulos
    (id, nombre, descripcion, orden, curso_id)
VALUES
    (1, 'Fundamentos de Spring Boot',
     'Conceptos fundamentales para crear aplicaciones con Spring Boot.',
     1, 1),

    (2, 'Persistencia con JPA',
     'Uso de Spring Data JPA e Hibernate.',
     2, 1),

    (3, 'Fundamentos de PostgreSQL',
     'Conceptos básicos de bases de datos relacionales y PostgreSQL.',
     1, 2),

    (4, 'Consultas SQL',
     'Consultas, filtros, relaciones y agregaciones.',
     2, 2);

-- ============================================================
-- 5. LECCIONES
-- ============================================================

INSERT INTO lecciones
    (id, titulo, contenido, orden, modulo_id)
VALUES
    (1, 'Introducción a Spring Boot',
     'Introducción al ecosistema Spring Boot y estructura de una aplicación.',
     1, 1),

    (2, 'Controladores REST',
     'Creación de endpoints y controladores REST.',
     2, 1),

    (3, 'Spring Data JPA',
     'Persistencia de entidades mediante repositorios JPA.',
     1, 2),

    (4, 'Relaciones entre entidades',
     'Relaciones OneToMany, ManyToOne y otras asociaciones JPA.',
     2, 2),

    (5, 'Introducción a PostgreSQL',
     'Conceptos fundamentales del motor PostgreSQL.',
     1, 3),

    (6, 'SELECT y filtros',
     'Consultas SELECT, WHERE, ORDER BY y operadores.',
     1, 4);

-- ============================================================
-- 6. RECURSOS
-- ============================================================

INSERT INTO recursos
    (id, nombre, tipo, url, leccion_id)
VALUES
    (1, 'Documentación oficial Spring Boot', 'DOCUMENTO',
     'https://spring.io/projects/spring-boot', 1),

    (2, 'Guía de APIs REST', 'ENLACE',
     'https://spring.io/guides', 2),

    (3, 'Documentación Spring Data JPA', 'DOCUMENTO',
     'https://spring.io/projects/spring-data-jpa', 3),

    (4, 'Documentación PostgreSQL', 'DOCUMENTO',
     'https://www.postgresql.org/docs/', 5);

-- ============================================================
-- 7. EVALUACIONES
-- ============================================================

INSERT INTO evaluaciones
    (id, titulo, descripcion, porcentaje, curso_id)
VALUES
    (1, 'Evaluación de Spring Boot',
     'Evaluación sobre fundamentos y desarrollo de APIs REST.',
     30.00, 1),

    (2, 'Evaluación de JPA',
     'Evaluación sobre persistencia y relaciones entre entidades.',
     30.00, 1),

    (3, 'Evaluación de PostgreSQL',
     'Evaluación sobre SQL y fundamentos de PostgreSQL.',
     40.00, 2);

-- ============================================================
-- 8. PREGUNTAS
-- ============================================================

INSERT INTO preguntas
    (id, enunciado, tipo, evaluacion_id)
VALUES
    (1, '¿Qué framework se utiliza para desarrollar la API de LearnHub?',
     'SELECCION_MULTIPLE', 1),

    (2, '¿Qué anotación identifica una clase como entidad JPA?',
     'SELECCION_MULTIPLE', 1),

    (3, '¿Qué componente se utiliza para acceder a la base de datos mediante Spring Data JPA?',
     'SELECCION_MULTIPLE', 2),

    (4, '¿Qué anotación representa una relación de muchos a uno?',
     'SELECCION_MULTIPLE', 2),

    (5, '¿Qué comando SQL permite consultar registros?',
     'SELECCION_MULTIPLE', 3);

-- ============================================================
-- 9. RESPUESTAS
-- ============================================================

INSERT INTO respuestas
    (id, texto, correcta, pregunta_id)
VALUES
    (1, 'Spring Boot', true, 1),
    (2, 'React', false, 1),
    (3, 'Docker', false, 1),
    (4, 'JUnit', false, 1),

    (5, '@Entity', true, 2),
    (6, '@Controller', false, 2),
    (7, '@Service', false, 2),
    (8, '@Repository', false, 2),

    (9, 'JpaRepository', true, 3),
    (10, 'RestController', false, 3),
    (11, 'PasswordEncoder', false, 3),
    (12, 'AuthenticationManager', false, 3),

    (13, '@ManyToOne', true, 4),
    (14, '@OneToOne', false, 4),
    (15, '@ManyToMany', false, 4),
    (16, '@Embedded', false, 4),

    (17, 'SELECT', true, 5),
    (18, 'INSERT', false, 5),
    (19, 'DELETE', false, 5),
    (20, 'UPDATE', false, 5);

-- ============================================================
-- 10. INSCRIPCIONES
-- ============================================================

INSERT INTO inscripciones
    (id, usuario_id, curso_id, fecha_inscripcion, activa)
VALUES
    (1, 3, 1, CURRENT_DATE, true),
    (2, 4, 1, CURRENT_DATE, true),
    (3, 3, 2, CURRENT_DATE, true),
    (4, 4, 2, CURRENT_DATE, false);

-- ============================================================
-- 11. PROGRESO
-- ============================================================

INSERT INTO progresos
    (id, usuario_id, curso_id, porcentaje)
VALUES
    (1, 3, 1, 65.00),
    (2, 4, 1, 35.00),
    (3, 3, 2, 80.00),
    (4, 4, 2, 15.00);

-- ============================================================
-- 12. CALIFICACIONES
-- ============================================================

INSERT INTO calificaciones
    (id, usuario_id, evaluacion_id, nota)
VALUES
    (1, 3, 1, 4.50),
    (2, 3, 2, 4.20),
    (3, 4, 1, 3.80),
    (4, 3, 3, 4.70);

-- ============================================================
-- 13. ACTUALIZAR SECUENCIAS
-- ============================================================
-- Evita conflictos al crear nuevos registros después del seed.

SELECT setval(
    pg_get_serial_sequence('usuarios', 'id'),
    COALESCE((SELECT MAX(id) FROM usuarios), 1)
);

SELECT setval(
    pg_get_serial_sequence('cursos', 'id'),
    COALESCE((SELECT MAX(id) FROM cursos), 1)
);

SELECT setval(
    pg_get_serial_sequence('modulos', 'id'),
    COALESCE((SELECT MAX(id) FROM modulos), 1)
);

SELECT setval(
    pg_get_serial_sequence('lecciones', 'id'),
    COALESCE((SELECT MAX(id) FROM lecciones), 1)
);

SELECT setval(
    pg_get_serial_sequence('recursos', 'id'),
    COALESCE((SELECT MAX(id) FROM recursos), 1)
);

SELECT setval(
    pg_get_serial_sequence('evaluaciones', 'id'),
    COALESCE((SELECT MAX(id) FROM evaluaciones), 1)
);

SELECT setval(
    pg_get_serial_sequence('preguntas', 'id'),
    COALESCE((SELECT MAX(id) FROM preguntas), 1)
);

SELECT setval(
    pg_get_serial_sequence('respuestas', 'id'),
    COALESCE((SELECT MAX(id) FROM respuestas), 1)
);

SELECT setval(
    pg_get_serial_sequence('inscripciones', 'id'),
    COALESCE((SELECT MAX(id) FROM inscripciones), 1)
);

SELECT setval(
    pg_get_serial_sequence('progresos', 'id'),
    COALESCE((SELECT MAX(id) FROM progresos), 1)
);

SELECT setval(
    pg_get_serial_sequence('calificaciones', 'id'),
    COALESCE((SELECT MAX(id) FROM calificaciones), 1)
);

COMMIT;

-- ============================================================
-- DATOS DE PRUEBA CREADOS
-- ============================================================
-- Usuarios:       4
-- Cursos:         2
-- Módulos:        4
-- Lecciones:      6
-- Recursos:       4
-- Evaluaciones:   3
-- Preguntas:      5
-- Respuestas:     20
-- Inscripciones:  4
-- Progreso:       4
-- Calificaciones: 4
--
-- Login de prueba:
-- admin@learnhub.com / 12345678
-- juan@learnhub.com / 12345678
-- pedro@learnhub.com / 12345678
-- maria@learnhub.com / 12345678