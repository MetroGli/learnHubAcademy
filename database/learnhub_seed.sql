-- LearnHub Academy - Datos de ejemplo
-- PostgreSQL 17
--
-- Ejecutar DESPUÉS de levantar el backend al menos una vez (Hibernate crea las tablas).
-- ATENCIÓN: borra todos los datos actuales y los reemplaza por los de ejemplo.
-- Haz un respaldo antes:  pg_dump -U learnhub_user learnhub > respaldo.sql
--
-- Contraseña de todos los usuarios: 12345678

BEGIN;

TRUNCATE TABLE
    calificaciones,
    progresos,
    lecciones_completadas,
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
-- USUARIOS (23)
-- ============================================================
INSERT INTO usuarios (id, nombre, apellido, email, password, rol, activo) VALUES
    (1, 'Administrador', 'LearnHub', 'admin@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ADMINISTRADOR', true),
    (2, 'Juan Pablo', 'Camacho', 'juan@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'PROFESOR', true),
    (3, 'Pedro', 'Gómez', 'pedro@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (4, 'María', 'Rodríguez', 'maria@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (5, 'Laura', 'Méndez', 'laura.mendez@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'PROFESOR', true),
    (6, 'Andrés', 'Rojas', 'andres.rojas@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'PROFESOR', true),
    (7, 'Catalina', 'Vargas', 'catalina.vargas@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'PROFESOR', true),
    (8, 'Valentina', 'Torres', 'valentina.torres@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (9, 'Santiago', 'Herrera', 'santiago.herrera@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (10, 'Camila', 'Moreno', 'camila.moreno@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (11, 'Daniel', 'Castro', 'daniel.castro@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (12, 'Isabella', 'Ramírez', 'isabella.ramirez@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (13, 'Mateo', 'Jiménez', 'mateo.jimenez@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (14, 'Sofía', 'Ortiz', 'sofia.ortiz@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (15, 'Samuel', 'Rincón', 'samuel.rincon@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (16, 'Mariana', 'Salazar', 'mariana.salazar@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (17, 'Nicolás', 'Pardo', 'nicolas.pardo@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (18, 'Gabriela', 'Cruz', 'gabriela.cruz@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (19, 'Tomás', 'Quintero', 'tomas.quintero@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (20, 'Luciana', 'Acosta', 'luciana.acosta@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (21, 'Emiliano', 'Vega', 'emiliano.vega@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (22, 'Antonella', 'Navarro', 'antonella.navarro@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', true),
    (23, 'Felipe', 'Arango', 'felipe.arango@learnhub.com', '$2b$10$r.YXRN0oSJcWSTxG7KlQquN.tZNGSo6QVNBKz1Ww0GdrIUMqhfzR2', 'ESTUDIANTE', false);

-- ============================================================
-- CURSOS (9)
-- ============================================================
INSERT INTO cursos (id, nombre, descripcion, profesor_id, activo, categoria, nivel, duracion_horas) VALUES
    (1, 'Java con Spring Boot', 'Construye APIs REST profesionales con Java 21 y Spring Boot: controladores, persistencia con JPA, validación y seguridad con JWT.', 2, true, 'Backend', 'INTERMEDIO', 40),
    (2, 'Bases de Datos con PostgreSQL', 'Modelado relacional, SQL desde cero, consultas con JOIN y agregaciones, índices y transacciones en PostgreSQL.', 2, true, 'Bases de datos', 'BASICO', 32),
    (3, 'Desarrollo Web con React', 'Crea interfaces modernas con React 18: componentes, estado, efectos, rutas y consumo de APIs REST.', 5, true, 'Frontend', 'INTERMEDIO', 36),
    (4, 'Fundamentos de Programación con Python', 'Aprende a programar desde cero con Python: variables, decisiones, ciclos, funciones y estructuras de datos.', 5, true, 'Programación', 'BASICO', 30),
    (5, 'Redes de Computadores con Cisco', 'Fundamentos de redes, direccionamiento IPv4, configuración de switches y routers Cisco, VLAN y enrutamiento con Packet Tracer.', 6, true, 'Redes', 'BASICO', 28),
    (6, 'Docker y Despliegue de Aplicaciones', 'Empaqueta aplicaciones en contenedores, orquéstalas con Docker Compose y publícalas con HTTPS detrás de un proxy inverso.', 6, true, 'DevOps', 'INTERMEDIO', 24),
    (7, 'Ingeniería de Software con TSPi', 'Planea, mide y gestiona proyectos de software en equipo con el proceso TSPi: roles, ciclos, estimación, calidad y seguimiento.', 7, true, 'Ingeniería de software', 'AVANZADO', 36),
    (8, 'Git y GitHub para Equipos', 'Control de versiones para trabajar en equipo: commits, ramas, pull requests y resolución de conflictos.', 7, true, 'Herramientas', 'BASICO', 12),
    (9, 'Seguridad en Aplicaciones Web', 'Las vulnerabilidades más comunes según OWASP y cómo prevenirlas en el frontend y el backend. Curso en preparación.', 2, false, 'Seguridad', 'AVANZADO', 30);

-- ============================================================
-- MÓDULOS (24)
-- ============================================================
INSERT INTO modulos (id, nombre, descripcion, orden, curso_id) VALUES
    (1, 'Fundamentos de Spring Boot', 'Qué resuelve Spring Boot, cómo se estructura un proyecto y cómo arranca una aplicación.', 1, 1),
    (2, 'APIs REST', 'Diseño de endpoints, códigos de estado, validación y manejo de errores.', 2, 1),
    (3, 'Persistencia y seguridad', 'Spring Data JPA, relaciones entre entidades y autenticación con JWT.', 3, 1),
    (4, 'Modelo relacional', 'Tablas, llaves y normalización.', 1, 2),
    (5, 'Consultas SQL', 'SELECT, filtros, uniones y agregaciones.', 2, 2),
    (6, 'Rendimiento y transacciones', 'Índices, planes de ejecución y propiedades ACID.', 3, 2),
    (7, 'Componentes y JSX', 'La base de cualquier interfaz en React.', 1, 3),
    (8, 'Estado y efectos', 'Cómo reaccionan los componentes a lo que hace el usuario.', 2, 3),
    (9, 'Aplicaciones completas', 'Rutas, formularios y comunicación con el backend.', 3, 3),
    (10, 'Primeros pasos', 'Variables, tipos de datos y operaciones.', 1, 4),
    (11, 'Control de flujo', 'Tomar decisiones y repetir instrucciones.', 2, 4),
    (12, 'Estructuras de datos', 'Listas, diccionarios y archivos.', 3, 4),
    (13, 'Fundamentos de redes', 'Modelos de referencia, medios y dispositivos.', 1, 5),
    (14, 'Configuración de dispositivos', 'Comandos básicos de IOS en switches y routers.', 2, 5),
    (15, 'VLAN y enrutamiento', 'Segmentación lógica y comunicación entre redes.', 3, 5),
    (16, 'Contenedores', 'Imágenes, contenedores y Dockerfile.', 1, 6),
    (17, 'Docker Compose', 'Aplicaciones de varios servicios.', 2, 6),
    (18, 'Publicar en producción', 'Proxy inverso, dominios y HTTPS.', 3, 6),
    (19, 'El proceso TSPi', 'Ciclos, fases y roles del equipo.', 1, 7),
    (20, 'Planeación y estimación', 'Tamaño, esfuerzo y cronograma.', 2, 7),
    (21, 'Calidad', 'Revisiones, inspecciones y postmortem.', 3, 7),
    (22, 'Git básico', 'Repositorios, commits e historial.', 1, 8),
    (23, 'Trabajo en equipo', 'Remotos, pull requests y conflictos.', 2, 8),
    (24, 'OWASP Top 10', 'Los riesgos de seguridad más críticos en aplicaciones web.', 1, 9);

-- ============================================================
-- LECCIONES (59)
-- ============================================================
INSERT INTO lecciones (id, titulo, contenido, orden, duracion_minutos, modulo_id) VALUES
    (1, 'Introducción a Spring Boot', 'Spring Boot es un proyecto del ecosistema Spring que permite crear aplicaciones listas para producción con muy poca configuración.

Sus ideas principales son:
- Autoconfiguración: detecta las dependencias del proyecto y configura los componentes necesarios.
- Starters: dependencias agrupadas por propósito, como spring-boot-starter-web o spring-boot-starter-data-jpa.
- Servidor embebido: la aplicación se ejecuta como un JAR con Tomcat incluido.

El punto de entrada es una clase anotada con @SpringBootApplication que llama a SpringApplication.run().', 1, 20, 1),
    (2, 'Estructura de un proyecto por capas', 'Una API bien organizada separa responsabilidades en capas:
- controller: recibe las peticiones HTTP y devuelve respuestas.
- service: contiene la lógica de negocio.
- repository: accede a la base de datos.
- entity y dto: representan los datos internos y los que viajan por la API.

Separar entidades y DTO evita exponer campos sensibles, como la contraseña de un usuario.', 2, 25, 1),
    (3, 'Inyección de dependencias', 'Spring crea y conecta los objetos de la aplicación (beans). En lugar de usar new, una clase declara lo que necesita en su constructor y Spring se lo entrega.

La inyección por constructor es la forma recomendada: hace explícitas las dependencias, permite campos final y facilita las pruebas unitarias con Mockito.', 3, 20, 1),
    (4, 'Controladores REST', 'Un controlador se marca con @RestController y agrupa endpoints bajo una ruta con @RequestMapping.

Cada método se asocia a un verbo HTTP con @GetMapping, @PostMapping, @PutMapping o @DeleteMapping. Los datos llegan por @PathVariable, @RequestParam o @RequestBody.', 1, 30, 2),
    (5, 'Códigos de estado HTTP', 'Una API clara responde con el código adecuado:
- 200 OK y 201 Created para operaciones exitosas.
- 204 No Content cuando no hay cuerpo, por ejemplo al eliminar.
- 400 Bad Request cuando los datos no son válidos.
- 401 Unauthorized sin sesión y 403 Forbidden sin permisos.
- 404 Not Found cuando el recurso no existe.', 2, 15, 2),
    (6, 'Validación y manejo de errores', 'Con spring-boot-starter-validation se agregan anotaciones como @NotBlank, @Size o @Email a los DTO, y @Valid en el controlador activa la validación.

Un @RestControllerAdvice centraliza el manejo de excepciones para que todos los errores tengan el mismo formato JSON.', 3, 25, 2),
    (7, 'Spring Data JPA', 'Una entidad es una clase anotada con @Entity que se mapea a una tabla. Al extender JpaRepository se obtienen gratis operaciones como save, findById, findAll y deleteById.

También se pueden declarar consultas por nombre de método, por ejemplo findByEmail o existsByUsuarioIdAndCursoId.', 1, 30, 3),
    (8, 'Relaciones entre entidades', '@ManyToOne representa que muchas filas apuntan a una (muchas lecciones pertenecen a un módulo). Su contraparte es @OneToMany.

Con cascade y orphanRemoval se controla qué pasa con los hijos al guardar o eliminar el padre.', 2, 25, 3),
    (9, 'Autenticación con JWT', 'Un JSON Web Token es una cadena firmada que identifica al usuario. El servidor la genera al iniciar sesión y la valida en cada petición con un filtro de Spring Security.

Guardar el token en una cookie HttpOnly evita que un script malicioso pueda leerlo.', 3, 35, 3),
    (10, 'Introducción a PostgreSQL', 'PostgreSQL es un sistema gestor de bases de datos relacional de código abierto, conocido por su robustez y por cumplir el estándar SQL.

Los datos se organizan en tablas con filas y columnas, y cada columna tiene un tipo: integer, varchar, text, date, boolean, numeric, entre otros.', 1, 20, 4),
    (11, 'Llaves primarias y foráneas', 'La llave primaria identifica de forma única cada fila. La llave foránea relaciona una tabla con otra y garantiza la integridad referencial: no se puede inscribir a un usuario que no existe.

Una restricción UNIQUE impide valores repetidos, por ejemplo el mismo usuario inscrito dos veces en un curso.', 2, 25, 4),
    (12, 'Normalización', 'Normalizar es organizar las tablas para evitar redundancia:
- 1FN: cada celda tiene un solo valor.
- 2FN: cada columna depende de toda la llave primaria.
- 3FN: no hay dependencias entre columnas que no son llave.

Un buen modelo normalizado facilita mantener los datos consistentes.', 3, 30, 4),
    (13, 'SELECT y filtros', 'SELECT elige columnas, FROM indica la tabla y WHERE filtra filas. ORDER BY ordena el resultado y LIMIT restringe cuántas filas se devuelven.

Ejemplo: SELECT nombre, email FROM usuarios WHERE rol = ''ESTUDIANTE'' ORDER BY nombre;', 1, 25, 5),
    (14, 'JOIN entre tablas', 'INNER JOIN devuelve solo las filas con coincidencia en ambas tablas. LEFT JOIN conserva todas las filas de la tabla izquierda aunque no tengan pareja.

Ejemplo: listar cada inscripción con el nombre del curso uniendo inscripciones y cursos por curso_id.', 2, 30, 5),
    (15, 'Agregaciones y GROUP BY', 'Las funciones COUNT, SUM, AVG, MIN y MAX resumen varias filas en un valor. GROUP BY agrupa por una columna y HAVING filtra los grupos.

Ejemplo: el promedio de notas por evaluación con AVG(nota) y GROUP BY evaluacion_id.', 3, 25, 5),
    (16, 'Índices', 'Un índice acelera las búsquedas sobre una columna a cambio de ocupar espacio y hacer un poco más lentas las escrituras. PostgreSQL crea índices automáticamente para llaves primarias y restricciones UNIQUE.

EXPLAIN ANALYZE muestra cómo se ejecuta una consulta y si usa un índice.', 1, 25, 6),
    (17, 'Transacciones y ACID', 'Una transacción agrupa operaciones que se ejecutan todas o ninguna. BEGIN la inicia, COMMIT la confirma y ROLLBACK la deshace.

ACID significa Atomicidad, Consistencia, Aislamiento y Durabilidad.', 2, 25, 6),
    (18, 'Tu primer componente', 'Un componente de React es una función de JavaScript que devuelve JSX, una sintaxis parecida a HTML.

Los nombres de los componentes empiezan con mayúscula y se pueden combinar para construir pantallas completas.', 1, 20, 7),
    (19, 'Props', 'Las props son los datos que un componente padre le pasa a un hijo, como los atributos de una etiqueta HTML.

Un componente nunca debe modificar sus props: son de solo lectura.', 2, 20, 7),
    (20, 'Listas y renderizado condicional', 'Para mostrar listas se usa map() y cada elemento necesita una key estable, normalmente el id del registro.

El renderizado condicional se hace con operadores como && o el ternario condición ? a : b.', 3, 25, 7),
    (21, 'useState', 'useState guarda un valor que, al cambiar, vuelve a renderizar el componente. Devuelve el valor actual y una función para actualizarlo.

Cuando el nuevo estado depende del anterior se usa la forma funcional: setContador(c => c + 1).', 1, 25, 8),
    (22, 'useEffect', 'useEffect sincroniza un componente con algo externo: una API, un temporizador o un evento del navegador.

El arreglo de dependencias indica cuándo se vuelve a ejecutar, y la función de limpieza evita fugas de memoria.', 2, 30, 8),
    (23, 'Hooks personalizados', 'Un hook personalizado es una función que empieza con use y agrupa lógica reutilizable, por ejemplo cargar los datos de un curso.

Así los componentes quedan enfocados en mostrar la información.', 3, 25, 8),
    (24, 'Rutas con React Router', 'React Router asocia URL con componentes. Con rutas protegidas se puede impedir que un usuario sin sesión entre a ciertas pantallas.

useParams lee parámetros de la URL, como el id del curso en /curso/3.', 1, 25, 9),
    (25, 'Consumir una API con Axios', 'Axios simplifica las peticiones HTTP. Una instancia compartida define la URL base y los interceptores, por ejemplo para redirigir al login cuando el servidor responde 401.

Con withCredentials el navegador envía las cookies de sesión.', 2, 30, 9),
    (26, '¿Por qué Python?', 'Python es un lenguaje interpretado, de sintaxis clara y muy usado en análisis de datos, automatización, desarrollo web e inteligencia artificial.

La indentación (los espacios al inicio de la línea) define los bloques de código.', 1, 15, 10),
    (27, 'Variables y tipos', 'Una variable guarda un valor y su tipo se define al asignarla: int, float, str y bool son los más comunes.

La función type() indica el tipo de un valor e input() lee texto del usuario.', 2, 20, 10),
    (28, 'Operadores', 'Los operadores aritméticos son +, -, *, / y también // (división entera), % (residuo) y ** (potencia).

Los operadores de comparación (==, !=, <, >) devuelven True o False.', 3, 20, 10),
    (29, 'Condicionales', 'if, elif y else permiten ejecutar distintos bloques según una condición. Las condiciones se combinan con and, or y not.', 1, 20, 11),
    (30, 'Ciclos for y while', 'for recorre los elementos de una secuencia y range(n) genera números de 0 a n-1. while repite mientras una condición sea verdadera.

break termina el ciclo y continue salta a la siguiente iteración.', 2, 25, 11),
    (31, 'Funciones', 'Una función se define con def, recibe parámetros y devuelve un valor con return. Dividir un programa en funciones pequeñas lo hace más fácil de leer y probar.', 3, 25, 11),
    (32, 'Listas y tuplas', 'Una lista es una colección ordenada y modificable: notas = [4.5, 3.8, 5.0]. Una tupla es similar pero inmutable.

append() agrega elementos, len() cuenta cuántos hay y las porciones como notas[1:] extraen una parte.', 1, 25, 12),
    (33, 'Diccionarios', 'Un diccionario guarda pares llave-valor: estudiante = {''nombre'': ''Ana'', ''nota'': 4.2}. Es ideal para representar registros.

items() permite recorrer llaves y valores al mismo tiempo.', 2, 25, 12),
    (34, 'Modelo OSI y TCP/IP', 'El modelo OSI divide la comunicación en siete capas: física, enlace de datos, red, transporte, sesión, presentación y aplicación. El modelo TCP/IP las agrupa en cuatro.

Los switches trabajan en la capa 2 con direcciones MAC y los routers en la capa 3 con direcciones IP.', 1, 25, 13),
    (35, 'Direccionamiento IPv4 y subredes', 'Una dirección IPv4 tiene 32 bits divididos en porción de red y porción de host según la máscara. Con /24 quedan 254 hosts utilizables; con /26, 62.

Dividir una red en subredes permite separar departamentos y reducir el tráfico de difusión.', 2, 35, 13),
    (36, 'Cisco Packet Tracer', 'Packet Tracer es el simulador de Cisco para diseñar topologías, configurar dispositivos por consola y probar la conectividad con ping y tracert sin equipos físicos.', 3, 15, 13),
    (37, 'Configuración básica de un switch', 'Los modos de IOS son: usuario (>), privilegiado (#) y configuración global (config)#.

Comandos habituales:
- hostname S1
- enable secret para proteger el modo privilegiado
- interface FastEthernet0/1 para entrar a una interfaz
- copy running-config startup-config para guardar los cambios', 1, 30, 14),
    (38, 'Interfaces de un router', 'Cada interfaz de un router conecta una red distinta. Para habilitarla se asigna una IP y se activa:
- interface GigabitEthernet0/0
- ip address 192.168.10.1 255.255.255.0
- no shutdown

show ip interface brief resume el estado de todas las interfaces.', 2, 30, 14),
    (39, 'VLAN', 'Una VLAN separa lógicamente un switch en varias redes. Se crea con vlan 10 y se asigna a un puerto con switchport access vlan 10 dentro de la interfaz, por ejemplo FastEthernet0/5.

Los enlaces troncales (switchport mode trunk) transportan varias VLAN entre switches.', 1, 30, 15),
    (40, 'Router-on-a-stick', 'Para comunicar VLAN se usa un router con subinterfaces, una por VLAN, sobre un solo enlace troncal. Por ejemplo, GigabitEthernet0/0.10 con encapsulation dot1Q 10.', 2, 35, 15),
    (41, 'Enrutamiento estático', 'Una ruta estática le indica al router cómo llegar a una red remota: ip route 192.168.20.0 255.255.255.0 10.0.0.2.

Es simple y predecible, pero no se adapta sola a cambios en la topología.', 3, 25, 15),
    (42, '¿Qué es un contenedor?', 'Un contenedor ejecuta una aplicación con todas sus dependencias de forma aislada, compartiendo el kernel del sistema. Es más liviano que una máquina virtual.

Una imagen es la plantilla de solo lectura; un contenedor es una instancia en ejecución de esa imagen.', 1, 20, 16),
    (43, 'Escribir un Dockerfile', 'El Dockerfile describe cómo construir una imagen: FROM elige la imagen base, COPY agrega archivos, RUN ejecuta comandos y CMD define qué se ejecuta al iniciar.

Las construcciones multietapa compilan en una etapa y copian solo el resultado a una imagen final pequeña.', 2, 30, 16),
    (44, 'Servicios, redes y volúmenes', 'Docker Compose define varios servicios en un archivo YAML: por ejemplo db, api y web. Los servicios de una misma red se encuentran por nombre.

Los volúmenes guardan datos fuera del contenedor, así la base de datos no se pierde al reconstruir.', 1, 30, 17),
    (45, 'Variables de entorno y secretos', 'Las credenciales no deben escribirse en el código. Compose lee un archivo .env y las pasa a los contenedores como variables de entorno.

El archivo .env se agrega al .gitignore para no subirlo al repositorio.', 2, 20, 17),
    (46, 'Proxy inverso con Traefik', 'Un proxy inverso recibe todo el tráfico de los puertos 80 y 443 y lo reparte a cada contenedor según el dominio. Traefik lee las etiquetas de los contenedores y obtiene certificados HTTPS de Let''s Encrypt automáticamente.', 1, 30, 18),
    (47, 'Actualizar sin perder datos', 'El flujo habitual es: git pull para traer los cambios y docker compose up -d --build para reconstruir. Los volúmenes conservan la información.

Antes de cambios grandes conviene respaldar la base con pg_dump.', 2, 20, 18),
    (48, 'Qué es TSPi', 'TSPi (Team Software Process introductorio) es un proceso definido por el SEI para que equipos pequeños desarrollen software de forma disciplinada y medible.

El trabajo se organiza en ciclos, y cada ciclo recorre las fases de lanzamiento, estrategia, planificación, requerimientos, diseño, implementación, pruebas y postmortem.', 1, 25, 19),
    (49, 'Roles del equipo', 'Cada integrante asume un rol además de desarrollar:
- Líder del equipo
- Líder de desarrollo
- Líder de planeación
- Líder de calidad y procesos
- Líder de soporte

Los roles reparten la responsabilidad de que el proceso se cumpla.', 2, 25, 19),
    (50, 'Estimación de tamaño', 'El tamaño del producto se estima en líneas de código o en puntos, y a partir de él se calcula el esfuerzo con datos históricos del equipo.

Estimar con datos reales mejora en cada ciclo: por eso se registra el tiempo de cada tarea.', 1, 30, 20),
    (51, 'Plan y valor ganado', 'Cada tarea tiene un valor planeado proporcional a su esfuerzo. Al terminarla, el equipo gana ese valor. Comparar el valor ganado con el planeado muestra si el proyecto va adelantado o atrasado.', 2, 30, 20),
    (52, 'Revisiones e inspecciones', 'Encontrar defectos en una revisión de diseño o de código es mucho más barato que encontrarlos en pruebas o en producción.

Las inspecciones usan listas de verificación y registran cada defecto encontrado.', 1, 25, 21),
    (53, 'Postmortem', 'Al final del ciclo el equipo compara lo planeado con lo real, analiza los defectos y propone mejoras para el siguiente ciclo. Es la base de la mejora continua.', 2, 20, 21),
    (54, 'Primeros commits', 'git init crea un repositorio, git add prepara los cambios y git commit los guarda en el historial con un mensaje.

git status muestra qué cambió y git log el historial de commits.', 1, 20, 22),
    (55, 'Ramas', 'Una rama permite trabajar en una funcionalidad sin afectar la principal. git switch -c nueva-rama la crea y git merge integra los cambios.', 2, 25, 22),
    (56, 'Pull requests', 'Un pull request propone integrar una rama a main en GitHub. Permite revisar el código, comentar y ejecutar pruebas antes de fusionar.', 1, 25, 23),
    (57, 'Resolver conflictos', 'Un conflicto ocurre cuando dos ramas cambian las mismas líneas. Git marca la zona con <<<<<<<, ======= y >>>>>>>; se elige el contenido correcto, se guarda y se hace commit.', 2, 20, 23),
    (58, 'Inyección SQL', 'Ocurre cuando datos del usuario se concatenan directamente en una consulta. La prevención es usar consultas parametrizadas, que es lo que hacen JPA y los repositorios de Spring.', 1, 30, 24),
    (59, 'Cross-Site Scripting (XSS)', 'XSS permite ejecutar JavaScript ajeno en la página de la víctima. React escapa el texto por defecto, y las cookies HttpOnly impiden que un script robe la sesión.', 2, 30, 24);

-- ============================================================
-- RECURSOS (43)
-- ============================================================
INSERT INTO recursos (id, nombre, tipo, url, leccion_id) VALUES
    (1, 'Documentación oficial de Spring Boot', 'DOCUMENTO', 'https://docs.spring.io/spring-boot/index.html', 1),
    (2, 'Spring Initializr', 'ENLACE', 'https://start.spring.io/', 1),
    (3, 'Guía: Building an Application with Spring Boot', 'DOCUMENTO', 'https://spring.io/guides/gs/spring-boot', 2),
    (4, 'Referencia: Dependency Injection', 'DOCUMENTO', 'https://docs.spring.io/spring-framework/reference/core/beans/dependencies/factory-collaborators.html', 3),
    (5, 'Guía: Building a RESTful Web Service', 'DOCUMENTO', 'https://spring.io/guides/gs/rest-service', 4),
    (6, 'Códigos de estado HTTP (MDN)', 'DOCUMENTO', 'https://developer.mozilla.org/es/docs/Web/HTTP/Status', 5),
    (7, 'Validación en Spring', 'DOCUMENTO', 'https://docs.spring.io/spring-framework/reference/core/validation/beanvalidation.html', 6),
    (8, 'Spring Data JPA', 'DOCUMENTO', 'https://spring.io/projects/spring-data-jpa', 7),
    (9, 'Guía: Accessing Data with JPA', 'DOCUMENTO', 'https://spring.io/guides/gs/accessing-data-jpa', 8),
    (10, 'Spring Security', 'DOCUMENTO', 'https://docs.spring.io/spring-security/reference/index.html', 9),
    (11, 'Introducción a JWT', 'ENLACE', 'https://jwt.io/introduction', 9),
    (12, 'Documentación de PostgreSQL', 'DOCUMENTO', 'https://www.postgresql.org/docs/current/', 10),
    (13, 'Restricciones en PostgreSQL', 'DOCUMENTO', 'https://www.postgresql.org/docs/current/ddl-constraints.html', 11),
    (14, 'Tutorial: consultar una tabla', 'DOCUMENTO', 'https://www.postgresql.org/docs/current/tutorial-select.html', 13),
    (15, 'Tutorial: uniones entre tablas', 'DOCUMENTO', 'https://www.postgresql.org/docs/current/tutorial-join.html', 14),
    (16, 'Tutorial: funciones de agregación', 'DOCUMENTO', 'https://www.postgresql.org/docs/current/tutorial-agg.html', 15),
    (17, 'Índices en PostgreSQL', 'DOCUMENTO', 'https://www.postgresql.org/docs/current/indexes.html', 16),
    (18, 'Tutorial: transacciones', 'DOCUMENTO', 'https://www.postgresql.org/docs/current/tutorial-transactions.html', 17),
    (19, 'Aprende React', 'DOCUMENTO', 'https://react.dev/learn', 18),
    (20, 'Pasar props a un componente', 'DOCUMENTO', 'https://react.dev/learn/passing-props-to-a-component', 19),
    (21, 'Renderizar listas', 'DOCUMENTO', 'https://react.dev/learn/rendering-lists', 20),
    (22, 'El estado: la memoria de un componente', 'DOCUMENTO', 'https://react.dev/learn/state-a-components-memory', 21),
    (23, 'Referencia de useEffect', 'DOCUMENTO', 'https://react.dev/reference/react/useEffect', 22),
    (24, 'Reutilizar lógica con hooks', 'DOCUMENTO', 'https://react.dev/learn/reusing-logic-with-custom-hooks', 23),
    (25, 'React Router', 'ENLACE', 'https://reactrouter.com/', 24),
    (26, 'Documentación de Axios', 'DOCUMENTO', 'https://axios-http.com/docs/intro', 25),
    (27, 'Tutorial oficial de Python', 'DOCUMENTO', 'https://docs.python.org/es/3/tutorial/', 26),
    (28, 'Introducción informal a Python', 'DOCUMENTO', 'https://docs.python.org/es/3/tutorial/introduction.html', 27),
    (29, 'Herramientas para control de flujo', 'DOCUMENTO', 'https://docs.python.org/es/3/tutorial/controlflow.html', 29),
    (30, 'Definir funciones', 'DOCUMENTO', 'https://docs.python.org/es/3/tutorial/controlflow.html#defining-functions', 31),
    (31, 'Estructuras de datos', 'DOCUMENTO', 'https://docs.python.org/es/3/tutorial/datastructures.html', 32),
    (32, 'Cisco Networking Academy', 'ENLACE', 'https://www.netacad.com/', 34),
    (33, 'Descargar Packet Tracer', 'ENLACE', 'https://www.netacad.com/cisco-packet-tracer', 36),
    (34, 'Docker: primeros pasos', 'DOCUMENTO', 'https://docs.docker.com/get-started/', 42),
    (35, 'Referencia de Dockerfile', 'DOCUMENTO', 'https://docs.docker.com/reference/dockerfile/', 43),
    (36, 'Documentación de Docker Compose', 'DOCUMENTO', 'https://docs.docker.com/compose/', 44),
    (37, 'Documentación de Traefik', 'DOCUMENTO', 'https://doc.traefik.io/traefik/', 46),
    (38, 'Software Engineering Institute', 'ENLACE', 'https://www.sei.cmu.edu/', 48),
    (39, 'ISO/IEC 25010: modelo de calidad', 'ENLACE', 'https://iso25000.com/index.php/normas-iso-25000/iso-25010', 53),
    (40, 'Libro Pro Git (español)', 'DOCUMENTO', 'https://git-scm.com/book/es/v2', 54),
    (41, 'Pull requests en GitHub', 'DOCUMENTO', 'https://docs.github.com/es/pull-requests', 56),
    (42, 'OWASP Top 10', 'ENLACE', 'https://owasp.org/www-project-top-ten/', 58),
    (43, 'OWASP Cheat Sheet Series', 'DOCUMENTO', 'https://cheatsheetseries.owasp.org/', 59);

-- ============================================================
-- EVALUACIONES (15)
-- ============================================================
INSERT INTO evaluaciones (id, titulo, descripcion, porcentaje, curso_id) VALUES
    (1, 'Quiz: fundamentos y APIs REST', 'Conceptos básicos de Spring Boot y diseño de endpoints.', 40.00, 1),
    (2, 'Examen: persistencia y seguridad', 'JPA, relaciones y autenticación.', 60.00, 1),
    (3, 'Quiz: modelo relacional', 'Llaves, restricciones y normalización.', 40.00, 2),
    (4, 'Examen: consultas SQL', 'SELECT, JOIN, agregaciones y transacciones.', 60.00, 2),
    (5, 'Quiz: componentes', 'Componentes, props y listas.', 30.00, 3),
    (6, 'Proyecto: estado, efectos y rutas', 'Hooks, rutas y consumo de APIs.', 70.00, 3),
    (7, 'Quiz: variables y operadores', 'Tipos de datos y operaciones básicas.', 30.00, 4),
    (8, 'Examen final de Python', 'Control de flujo, funciones y estructuras de datos.', 70.00, 4),
    (9, 'Quiz: fundamentos de redes', 'Modelo OSI y direccionamiento.', 40.00, 5),
    (10, 'Laboratorio: VLAN y enrutamiento', 'Configuración de dispositivos Cisco.', 60.00, 5),
    (11, 'Quiz: contenedores', 'Imágenes, contenedores y Dockerfile.', 40.00, 6),
    (12, 'Proyecto: despliegue con Compose', 'Compose, variables de entorno y proxy inverso.', 60.00, 6),
    (13, 'Quiz: proceso y roles', 'Fases y roles de TSPi.', 40.00, 7),
    (14, 'Examen: planeación y calidad', 'Estimación, valor ganado y revisiones.', 60.00, 7),
    (15, 'Quiz: Git y GitHub', 'Comandos básicos y trabajo colaborativo.', 100.00, 8);

-- ============================================================
-- PREGUNTAS (63)
-- ============================================================
INSERT INTO preguntas (id, enunciado, tipo, evaluacion_id) VALUES
    (1, '¿Qué anotación marca la clase principal de una aplicación Spring Boot?', 'SELECCION_MULTIPLE', 1),
    (2, '¿Qué código HTTP corresponde a un recurso creado correctamente?', 'SELECCION_MULTIPLE', 1),
    (3, '¿Cuál es la forma recomendada de inyectar dependencias en Spring?', 'SELECCION_MULTIPLE', 1),
    (4, '¿Qué anotación recibe el cuerpo JSON de una petición?', 'SELECCION_MULTIPLE', 1),
    (5, '¿Qué capa debería contener la lógica de negocio?', 'SELECCION_MULTIPLE', 1),
    (6, '¿Qué interfaz ofrece save, findById y deleteById sin escribir código?', 'SELECCION_MULTIPLE', 2),
    (7, '¿Qué anotación representa que muchas lecciones pertenecen a un módulo?', 'SELECCION_MULTIPLE', 2),
    (8, '¿Por qué se guarda el JWT en una cookie HttpOnly?', 'SELECCION_MULTIPLE', 2),
    (9, '¿Qué código debe responder la API cuando no hay sesión?', 'SELECCION_MULTIPLE', 2),
    (10, '¿Qué garantiza una llave foránea?', 'SELECCION_MULTIPLE', 3),
    (11, '¿Qué restricción impide inscribir dos veces al mismo usuario en un curso?', 'SELECCION_MULTIPLE', 3),
    (12, 'En 1FN, cada celda debe tener...', 'SELECCION_MULTIPLE', 3),
    (13, '¿Qué tipo de dato usarías para guardar si un curso está activo?', 'SELECCION_MULTIPLE', 3),
    (14, '¿Qué comando SQL permite consultar registros?', 'SELECCION_MULTIPLE', 4),
    (15, '¿Qué tipo de JOIN conserva todas las filas de la tabla izquierda?', 'SELECCION_MULTIPLE', 4),
    (16, '¿Qué cláusula filtra grupos después de GROUP BY?', 'SELECCION_MULTIPLE', 4),
    (17, '¿Qué comando deshace una transacción?', 'SELECCION_MULTIPLE', 4),
    (18, '¿Qué muestra EXPLAIN ANALYZE?', 'SELECCION_MULTIPLE', 4),
    (19, '¿Qué devuelve un componente de React?', 'SELECCION_MULTIPLE', 5),
    (20, '¿Las props se pueden modificar dentro del componente hijo?', 'SELECCION_MULTIPLE', 5),
    (21, '¿Qué necesita cada elemento de una lista renderizada con map()?', 'SELECCION_MULTIPLE', 5),
    (22, '¿Con qué letra debe empezar el nombre de un componente?', 'SELECCION_MULTIPLE', 5),
    (23, '¿Qué hook guarda un valor que provoca un nuevo renderizado al cambiar?', 'SELECCION_MULTIPLE', 6),
    (24, '¿Para qué sirve el arreglo de dependencias de useEffect?', 'SELECCION_MULTIPLE', 6),
    (25, '¿Qué hook de React Router lee el id de la URL /curso/3?', 'SELECCION_MULTIPLE', 6),
    (26, '¿Qué opción de Axios hace que el navegador envíe las cookies?', 'SELECCION_MULTIPLE', 6),
    (27, '¿Con qué palabra debe empezar un hook personalizado?', 'SELECCION_MULTIPLE', 6),
    (28, '¿Qué define los bloques de código en Python?', 'SELECCION_MULTIPLE', 7),
    (29, '¿Qué resultado da 7 // 2?', 'SELECCION_MULTIPLE', 7),
    (30, '¿Qué tipo de dato es True?', 'SELECCION_MULTIPLE', 7),
    (31, '¿Qué operador calcula el residuo de una división?', 'SELECCION_MULTIPLE', 7),
    (32, '¿Qué números genera range(3)?', 'SELECCION_MULTIPLE', 8),
    (33, '¿Qué palabra se usa para definir una función?', 'SELECCION_MULTIPLE', 8),
    (34, '¿Cuál de estas estructuras es inmutable?', 'SELECCION_MULTIPLE', 8),
    (35, '¿Qué método agrega un elemento al final de una lista?', 'SELECCION_MULTIPLE', 8),
    (36, '¿Qué instrucción termina un ciclo antes de tiempo?', 'SELECCION_MULTIPLE', 8),
    (37, '¿En qué capa del modelo OSI trabaja un router?', 'SELECCION_MULTIPLE', 9),
    (38, '¿Cuántos hosts utilizables tiene una red /24?', 'SELECCION_MULTIPLE', 9),
    (39, '¿Qué dirección usa un switch para reenviar tramas?', 'SELECCION_MULTIPLE', 9),
    (40, '¿Cuántas capas tiene el modelo OSI?', 'SELECCION_MULTIPLE', 9),
    (41, '¿Qué comando activa una interfaz de un router?', 'SELECCION_MULTIPLE', 10),
    (42, '¿Qué comando muestra un resumen del estado de las interfaces?', 'SELECCION_MULTIPLE', 10),
    (43, '¿Qué modo de puerto transporta varias VLAN entre switches?', 'SELECCION_MULTIPLE', 10),
    (44, '¿Qué encapsulación se usa en las subinterfaces de router-on-a-stick?', 'SELECCION_MULTIPLE', 10),
    (45, '¿Qué comando guarda la configuración para que sobreviva a un reinicio?', 'SELECCION_MULTIPLE', 10),
    (46, '¿Qué instrucción del Dockerfile elige la imagen base?', 'SELECCION_MULTIPLE', 11),
    (47, '¿Cuál es la diferencia entre imagen y contenedor?', 'SELECCION_MULTIPLE', 11),
    (48, '¿Para qué sirve una construcción multietapa?', 'SELECCION_MULTIPLE', 11),
    (49, '¿Qué evita que los datos de PostgreSQL se pierdan al reconstruir?', 'SELECCION_MULTIPLE', 12),
    (50, '¿Dónde deben ir las contraseñas de producción?', 'SELECCION_MULTIPLE', 12),
    (51, '¿Qué hace Traefik en el servidor?', 'SELECCION_MULTIPLE', 12),
    (52, '¿Qué comando reconstruye y levanta los servicios en segundo plano?', 'SELECCION_MULTIPLE', 12),
    (53, '¿Qué organización definió TSPi?', 'SELECCION_MULTIPLE', 13),
    (54, '¿Con qué fase termina cada ciclo de TSPi?', 'SELECCION_MULTIPLE', 13),
    (55, '¿Qué rol se encarga de que el equipo tenga las herramientas que necesita?', 'SELECCION_MULTIPLE', 13),
    (56, '¿Qué indica comparar el valor ganado con el planeado?', 'SELECCION_MULTIPLE', 14),
    (57, '¿Dónde es más barato encontrar un defecto?', 'SELECCION_MULTIPLE', 14),
    (58, '¿Por qué se registra el tiempo de cada tarea?', 'SELECCION_MULTIPLE', 14),
    (59, '¿Qué se hace en el postmortem?', 'SELECCION_MULTIPLE', 14),
    (60, '¿Qué comando prepara los cambios para el próximo commit?', 'SELECCION_MULTIPLE', 15),
    (61, '¿Qué comando crea una rama y se cambia a ella?', 'SELECCION_MULTIPLE', 15),
    (62, '¿Para qué sirve un pull request?', 'SELECCION_MULTIPLE', 15),
    (63, '¿Qué marca Git usa al inicio de un conflicto?', 'SELECCION_MULTIPLE', 15);

-- ============================================================
-- RESPUESTAS (opciones de cada pregunta) (252)
-- ============================================================
INSERT INTO respuestas (id, texto, correcta, pregunta_id) VALUES
    (1, '@SpringBootApplication', true, 1),
    (2, '@EnableWebMvc', false, 1),
    (3, '@Configuration', false, 1),
    (4, '@Component', false, 1),
    (5, '200 OK', false, 2),
    (6, '201 Created', true, 2),
    (7, '204 No Content', false, 2),
    (8, '302 Found', false, 2),
    (9, 'Con new dentro del método', false, 3),
    (10, 'Por constructor', true, 3),
    (11, 'Con variables estáticas', false, 3),
    (12, 'Con reflexión manual', false, 3),
    (13, '@PathVariable', false, 4),
    (14, '@RequestParam', false, 4),
    (15, '@RequestBody', true, 4),
    (16, '@ResponseBody', false, 4),
    (17, 'controller', false, 5),
    (18, 'service', true, 5),
    (19, 'repository', false, 5),
    (20, 'entity', false, 5),
    (21, 'JpaRepository', true, 6),
    (22, 'RestController', false, 6),
    (23, 'PasswordEncoder', false, 6),
    (24, 'AuthenticationManager', false, 6),
    (25, '@OneToOne', false, 7),
    (26, '@ManyToOne', true, 7),
    (27, '@ManyToMany', false, 7),
    (28, '@Embedded', false, 7),
    (29, 'Para que dure más', false, 8),
    (30, 'Para que JavaScript no pueda leerlo', true, 8),
    (31, 'Para que pese menos', false, 8),
    (32, 'Porque es obligatorio en HTTP', false, 8),
    (33, '400', false, 9),
    (34, '401', true, 9),
    (35, '404', false, 9),
    (36, '500', false, 9),
    (37, 'Que la columna sea única', false, 10),
    (38, 'La integridad referencial entre tablas', true, 10),
    (39, 'Que la consulta sea más rápida', false, 10),
    (40, 'Que el valor no sea nulo', false, 10),
    (41, 'CHECK', false, 11),
    (42, 'UNIQUE (usuario_id, curso_id)', true, 11),
    (43, 'DEFAULT', false, 11),
    (44, 'NOT NULL', false, 11),
    (45, 'Un solo valor', true, 12),
    (46, 'Una lista de valores', false, 12),
    (47, 'Un valor numérico', false, 12),
    (48, 'Una llave foránea', false, 12),
    (49, 'varchar', false, 13),
    (50, 'boolean', true, 13),
    (51, 'date', false, 13),
    (52, 'numeric', false, 13),
    (53, 'SELECT', true, 14),
    (54, 'INSERT', false, 14),
    (55, 'DELETE', false, 14),
    (56, 'UPDATE', false, 14),
    (57, 'INNER JOIN', false, 15),
    (58, 'LEFT JOIN', true, 15),
    (59, 'CROSS JOIN', false, 15),
    (60, 'SELF JOIN', false, 15),
    (61, 'WHERE', false, 16),
    (62, 'HAVING', true, 16),
    (63, 'ORDER BY', false, 16),
    (64, 'LIMIT', false, 16),
    (65, 'COMMIT', false, 17),
    (66, 'ROLLBACK', true, 17),
    (67, 'SAVEPOINT', false, 17),
    (68, 'END', false, 17),
    (69, 'Los permisos de la tabla', false, 18),
    (70, 'El plan de ejecución real de una consulta', true, 18),
    (71, 'El tamaño de la base', false, 18),
    (72, 'Los usuarios conectados', false, 18),
    (73, 'Un objeto JSON', false, 19),
    (74, 'JSX', true, 19),
    (75, 'Una promesa', false, 19),
    (76, 'Un archivo CSS', false, 19),
    (77, 'Sí, siempre', false, 20),
    (78, 'No, son de solo lectura', true, 20),
    (79, 'Solo si son números', false, 20),
    (80, 'Solo en componentes de clase', false, 20),
    (81, 'Un id de CSS', false, 21),
    (82, 'Una key estable', true, 21),
    (83, 'Un useEffect', false, 21),
    (84, 'Un estado propio', false, 21),
    (85, 'Minúscula', false, 22),
    (86, 'Mayúscula', true, 22),
    (87, 'Guion bajo', false, 22),
    (88, 'Da igual', false, 22),
    (89, 'useEffect', false, 23),
    (90, 'useState', true, 23),
    (91, 'useRef', false, 23),
    (92, 'useMemo', false, 23),
    (93, 'Para declarar estilos', false, 24),
    (94, 'Para indicar cuándo se vuelve a ejecutar el efecto', true, 24),
    (95, 'Para importar módulos', false, 24),
    (96, 'Para tipar las props', false, 24),
    (97, 'useNavigate', false, 25),
    (98, 'useParams', true, 25),
    (99, 'useLocation', false, 25),
    (100, 'useRoutes', false, 25),
    (101, 'withCredentials', true, 26),
    (102, 'timeout', false, 26),
    (103, 'baseURL', false, 26),
    (104, 'responseType', false, 26),
    (105, 'get', false, 27),
    (106, 'use', true, 27),
    (107, 'hook', false, 27),
    (108, 'with', false, 27),
    (109, 'Las llaves { }', false, 28),
    (110, 'La indentación', true, 28),
    (111, 'Los puntos y coma', false, 28),
    (112, 'La palabra end', false, 28),
    (113, '3.5', false, 29),
    (114, '3', true, 29),
    (115, '4', false, 29),
    (116, '1', false, 29),
    (117, 'str', false, 30),
    (118, 'int', false, 30),
    (119, 'bool', true, 30),
    (120, 'float', false, 30),
    (121, '/', false, 31),
    (122, '//', false, 31),
    (123, '%', true, 31),
    (124, '**', false, 31),
    (125, '1, 2, 3', false, 32),
    (126, '0, 1, 2', true, 32),
    (127, '0, 1, 2, 3', false, 32),
    (128, '3', false, 32),
    (129, 'function', false, 33),
    (130, 'def', true, 33),
    (131, 'fun', false, 33),
    (132, 'lambda', false, 33),
    (133, 'Lista', false, 34),
    (134, 'Diccionario', false, 34),
    (135, 'Tupla', true, 34),
    (136, 'Conjunto', false, 34),
    (137, 'add()', false, 35),
    (138, 'push()', false, 35),
    (139, 'append()', true, 35),
    (140, 'insert_end()', false, 35),
    (141, 'continue', false, 36),
    (142, 'break', true, 36),
    (143, 'pass', false, 36),
    (144, 'return', false, 36),
    (145, 'Capa 1', false, 37),
    (146, 'Capa 2', false, 37),
    (147, 'Capa 3', true, 37),
    (148, 'Capa 7', false, 37),
    (149, '256', false, 38),
    (150, '254', true, 38),
    (151, '255', false, 38),
    (152, '126', false, 38),
    (153, 'Dirección IP', false, 39),
    (154, 'Dirección MAC', true, 39),
    (155, 'Puerto TCP', false, 39),
    (156, 'Nombre de dominio', false, 39),
    (157, '4', false, 40),
    (158, '5', false, 40),
    (159, '7', true, 40),
    (160, '8', false, 40),
    (161, 'enable', false, 41),
    (162, 'no shutdown', true, 41),
    (163, 'interface up', false, 41),
    (164, 'start', false, 41),
    (165, 'show running-config', false, 42),
    (166, 'show ip interface brief', true, 42),
    (167, 'show vlan', false, 42),
    (168, 'show version', false, 42),
    (169, 'access', false, 43),
    (170, 'trunk', true, 43),
    (171, 'monitor', false, 43),
    (172, 'shutdown', false, 43),
    (173, 'PPP', false, 44),
    (174, 'HDLC', false, 44),
    (175, 'dot1Q', true, 44),
    (176, 'Frame Relay', false, 44),
    (177, 'write erase', false, 45),
    (178, 'copy running-config startup-config', true, 45),
    (179, 'reload', false, 45),
    (180, 'show startup-config', false, 45),
    (181, 'RUN', false, 46),
    (182, 'FROM', true, 46),
    (183, 'COPY', false, 46),
    (184, 'CMD', false, 46),
    (185, 'Son lo mismo', false, 47),
    (186, 'La imagen es la plantilla y el contenedor una instancia en ejecución', true, 47),
    (187, 'El contenedor es la plantilla', false, 47),
    (188, 'La imagen solo existe en producción', false, 47),
    (189, 'Para usar varias bases de datos', false, 48),
    (190, 'Para obtener imágenes finales más pequeñas', true, 48),
    (191, 'Para ejecutar varios contenedores', false, 48),
    (192, 'Para cifrar la imagen', false, 48),
    (193, 'Un volumen', true, 49),
    (194, 'Una red', false, 49),
    (195, 'Una etiqueta', false, 49),
    (196, 'Un puerto', false, 49),
    (197, 'En el código fuente', false, 50),
    (198, 'En un archivo .env fuera de git', true, 50),
    (199, 'En el README', false, 50),
    (200, 'En el Dockerfile', false, 50),
    (201, 'Compila el código', false, 51),
    (202, 'Reparte el tráfico por dominio y gestiona HTTPS', true, 51),
    (203, 'Respalda la base de datos', false, 51),
    (204, 'Ejecuta las pruebas', false, 51),
    (205, 'docker compose down', false, 52),
    (206, 'docker compose up -d --build', true, 52),
    (207, 'docker ps', false, 52),
    (208, 'docker logs', false, 52),
    (209, 'IEEE', false, 53),
    (210, 'SEI', true, 53),
    (211, 'ISO', false, 53),
    (212, 'W3C', false, 53),
    (213, 'Lanzamiento', false, 54),
    (214, 'Pruebas', false, 54),
    (215, 'Postmortem', true, 54),
    (216, 'Diseño', false, 54),
    (217, 'Líder de soporte', true, 55),
    (218, 'Líder de calidad', false, 55),
    (219, 'Líder de planeación', false, 55),
    (220, 'Líder de desarrollo', false, 55),
    (221, 'El costo del hardware', false, 56),
    (222, 'Si el proyecto va adelantado o atrasado', true, 56),
    (223, 'La cantidad de usuarios', false, 56),
    (224, 'El número de defectos', false, 56),
    (225, 'En producción', false, 57),
    (226, 'En pruebas del sistema', false, 57),
    (227, 'En una revisión de diseño', true, 57),
    (228, 'Después de la entrega', false, 57),
    (229, 'Para controlar la asistencia', false, 58),
    (230, 'Para mejorar las estimaciones con datos reales', true, 58),
    (231, 'Porque lo exige Git', false, 58),
    (232, 'Para calcular el salario', false, 58),
    (233, 'Se despliega el producto', false, 59),
    (234, 'Se compara lo planeado con lo real y se proponen mejoras', true, 59),
    (235, 'Se escriben los requerimientos', false, 59),
    (236, 'Se asignan los roles', false, 59),
    (237, 'git push', false, 60),
    (238, 'git add', true, 60),
    (239, 'git log', false, 60),
    (240, 'git clone', false, 60),
    (241, 'git switch -c rama', true, 61),
    (242, 'git branch -d rama', false, 61),
    (243, 'git merge rama', false, 61),
    (244, 'git reset rama', false, 61),
    (245, 'Para borrar el repositorio', false, 62),
    (246, 'Para proponer y revisar cambios antes de integrarlos', true, 62),
    (247, 'Para descargar un repositorio', false, 62),
    (248, 'Para crear un usuario', false, 62),
    (249, '#####', false, 63),
    (250, '<<<<<<<', true, 63),
    (251, '-------', false, 63),
    (252, '@@@@@@@', false, 63);

-- ============================================================
-- INSCRIPCIONES (67)
-- ============================================================
INSERT INTO inscripciones (id, usuario_id, curso_id, fecha_inscripcion, activa) VALUES
    (1, 3, 1, '2026-08-21', true),
    (2, 3, 2, '2026-08-23', true),
    (3, 3, 3, '2026-08-29', true),
    (4, 3, 8, '2026-09-14', true),
    (5, 4, 1, '2026-08-20', true),
    (6, 4, 4, '2026-10-13', true),
    (7, 4, 5, '2026-08-23', true),
    (8, 8, 1, '2026-08-19', true),
    (9, 8, 5, '2026-08-16', true),
    (10, 8, 2, '2026-08-15', true),
    (11, 8, 6, '2026-08-27', true),
    (12, 8, 8, '2026-10-20', true),
    (13, 9, 8, '2026-08-20', true),
    (14, 9, 6, '2026-08-22', true),
    (15, 10, 4, '2026-10-19', true),
    (16, 10, 1, '2026-09-26', true),
    (17, 10, 5, '2026-10-04', true),
    (18, 11, 1, '2026-09-13', true),
    (19, 11, 8, '2026-08-28', true),
    (20, 11, 5, '2026-08-30', true),
    (21, 11, 4, '2026-10-18', true),
    (22, 12, 3, '2026-09-05', true),
    (23, 12, 6, '2026-10-02', true),
    (24, 12, 2, '2026-09-01', true),
    (25, 13, 6, '2026-09-09', true),
    (26, 13, 4, '2026-09-19', true),
    (27, 13, 5, '2026-10-17', true),
    (28, 13, 2, '2026-09-17', true),
    (29, 13, 1, '2026-09-22', true),
    (30, 14, 6, '2026-08-31', true),
    (31, 14, 5, '2026-09-09', false),
    (32, 14, 2, '2026-10-09', true),
    (33, 14, 7, '2026-09-01', true),
    (34, 14, 4, '2026-09-09', true),
    (35, 15, 4, '2026-10-01', true),
    (36, 15, 7, '2026-09-04', true),
    (37, 15, 8, '2026-10-06', true),
    (38, 15, 2, '2026-10-13', true),
    (39, 15, 5, '2026-09-21', true),
    (40, 16, 4, '2026-10-17', true),
    (41, 16, 8, '2026-09-03', true),
    (42, 17, 6, '2026-09-09', true),
    (43, 17, 7, '2026-08-23', true),
    (44, 17, 2, '2026-10-07', true),
    (45, 17, 3, '2026-09-15', true),
    (46, 18, 3, '2026-08-16', true),
    (47, 18, 8, '2026-08-17', true),
    (48, 18, 7, '2026-10-24', true),
    (49, 19, 3, '2026-08-23', true),
    (50, 19, 1, '2026-09-10', true),
    (51, 19, 8, '2026-09-26', true),
    (52, 20, 7, '2026-09-26', true),
    (53, 20, 3, '2026-10-08', true),
    (54, 20, 2, '2026-09-20', true),
    (55, 20, 6, '2026-09-11', true),
    (56, 20, 4, '2026-10-17', true),
    (57, 21, 5, '2026-09-22', true),
    (58, 21, 8, '2026-08-25', true),
    (59, 22, 6, '2026-08-20', true),
    (60, 22, 1, '2026-10-03', true),
    (61, 22, 2, '2026-10-21', true),
    (62, 22, 4, '2026-09-11', true),
    (63, 23, 4, '2026-09-16', false),
    (64, 23, 3, '2026-08-20', false),
    (65, 23, 7, '2026-08-18', false),
    (66, 23, 5, '2026-08-26', false),
    (67, 23, 8, '2026-09-13', false);

-- ============================================================
-- LECCIONES COMPLETADAS (282)
-- ============================================================
INSERT INTO lecciones_completadas (id, usuario_id, leccion_id, fecha) VALUES
    (1, 3, 1, '2026-09-13'),
    (2, 3, 2, '2026-10-06'),
    (3, 3, 3, '2026-10-29'),
    (4, 3, 10, '2026-10-29'),
    (5, 3, 18, '2026-09-10'),
    (6, 3, 19, '2026-09-22'),
    (7, 3, 20, '2026-10-04'),
    (8, 3, 21, '2026-10-16'),
    (9, 3, 22, '2026-10-29'),
    (10, 3, 54, '2026-10-06'),
    (11, 3, 55, '2026-10-29'),
    (12, 4, 1, '2026-08-30'),
    (13, 4, 2, '2026-09-09'),
    (14, 4, 3, '2026-09-19'),
    (15, 4, 4, '2026-09-29'),
    (16, 4, 5, '2026-10-09'),
    (17, 4, 6, '2026-10-19'),
    (18, 4, 7, '2026-10-29'),
    (19, 4, 26, '2026-10-17'),
    (20, 4, 27, '2026-10-21'),
    (21, 4, 28, '2026-10-25'),
    (22, 4, 29, '2026-10-29'),
    (23, 4, 34, '2026-09-25'),
    (24, 4, 35, '2026-10-29'),
    (25, 8, 1, '2026-08-30'),
    (26, 8, 2, '2026-09-11'),
    (27, 8, 3, '2026-09-23'),
    (28, 8, 4, '2026-10-05'),
    (29, 8, 5, '2026-10-17'),
    (30, 8, 6, '2026-10-29'),
    (31, 8, 10, '2026-08-24'),
    (32, 8, 11, '2026-09-02'),
    (33, 8, 12, '2026-09-12'),
    (34, 8, 13, '2026-09-21'),
    (35, 8, 14, '2026-09-30'),
    (36, 8, 15, '2026-10-10'),
    (37, 8, 16, '2026-10-19'),
    (38, 8, 17, '2026-10-29'),
    (39, 8, 42, '2026-09-06'),
    (40, 8, 43, '2026-09-17'),
    (41, 8, 44, '2026-09-27'),
    (42, 8, 45, '2026-10-08'),
    (43, 8, 46, '2026-10-18'),
    (44, 8, 47, '2026-10-29'),
    (45, 8, 54, '2026-10-24'),
    (46, 8, 55, '2026-10-29'),
    (47, 9, 54, '2026-09-06'),
    (48, 9, 55, '2026-09-24'),
    (49, 9, 56, '2026-10-11'),
    (50, 9, 57, '2026-10-29'),
    (51, 9, 42, '2026-09-08'),
    (52, 9, 43, '2026-09-25'),
    (53, 9, 44, '2026-10-12'),
    (54, 9, 45, '2026-10-29'),
    (55, 10, 26, '2026-10-20'),
    (56, 10, 27, '2026-10-21'),
    (57, 10, 28, '2026-10-22'),
    (58, 10, 29, '2026-10-24'),
    (59, 10, 30, '2026-10-25'),
    (60, 10, 31, '2026-10-26'),
    (61, 10, 32, '2026-10-27'),
    (62, 10, 33, '2026-10-29'),
    (63, 10, 1, '2026-10-02'),
    (64, 10, 2, '2026-10-09'),
    (65, 10, 3, '2026-10-15'),
    (66, 10, 4, '2026-10-22'),
    (67, 10, 5, '2026-10-29'),
    (68, 10, 34, '2026-10-08'),
    (69, 10, 35, '2026-10-12'),
    (70, 10, 36, '2026-10-16'),
    (71, 10, 37, '2026-10-20'),
    (72, 10, 38, '2026-10-24'),
    (73, 10, 39, '2026-10-29'),
    (74, 11, 54, '2026-09-12'),
    (75, 11, 55, '2026-09-28'),
    (76, 11, 56, '2026-10-13'),
    (77, 11, 57, '2026-10-29'),
    (78, 11, 34, '2026-09-06'),
    (79, 11, 35, '2026-09-14'),
    (80, 11, 36, '2026-09-21'),
    (81, 11, 37, '2026-09-29'),
    (82, 11, 38, '2026-10-06'),
    (83, 11, 39, '2026-10-14'),
    (84, 11, 40, '2026-10-21'),
    (85, 11, 41, '2026-10-29'),
    (86, 11, 26, '2026-10-20'),
    (87, 11, 27, '2026-10-22'),
    (88, 11, 28, '2026-10-24'),
    (89, 11, 29, '2026-10-26'),
    (90, 11, 30, '2026-10-29'),
    (91, 12, 18, '2026-09-18'),
    (92, 12, 19, '2026-10-02'),
    (93, 12, 20, '2026-10-15'),
    (94, 12, 21, '2026-10-29'),
    (95, 12, 42, '2026-10-29'),
    (96, 12, 10, '2026-09-10'),
    (97, 12, 11, '2026-09-20'),
    (98, 12, 12, '2026-09-30'),
    (99, 12, 13, '2026-10-09'),
    (100, 12, 14, '2026-10-19'),
    (101, 12, 15, '2026-10-29'),
    (102, 13, 42, '2026-09-25'),
    (103, 13, 43, '2026-10-12'),
    (104, 13, 44, '2026-10-29'),
    (105, 13, 26, '2026-09-24'),
    (106, 13, 27, '2026-09-29'),
    (107, 13, 28, '2026-10-04'),
    (108, 13, 29, '2026-10-09'),
    (109, 13, 30, '2026-10-14'),
    (110, 13, 31, '2026-10-19'),
    (111, 13, 32, '2026-10-24'),
    (112, 13, 33, '2026-10-29'),
    (113, 13, 34, '2026-10-20'),
    (114, 13, 35, '2026-10-23'),
    (115, 13, 36, '2026-10-26'),
    (116, 13, 37, '2026-10-29'),
    (117, 13, 10, '2026-09-27'),
    (118, 13, 11, '2026-10-08'),
    (119, 13, 12, '2026-10-18'),
    (120, 13, 13, '2026-10-29'),
    (121, 13, 1, '2026-09-27'),
    (122, 13, 2, '2026-10-02'),
    (123, 13, 3, '2026-10-07'),
    (124, 13, 4, '2026-10-13'),
    (125, 13, 5, '2026-10-18'),
    (126, 13, 6, '2026-10-23'),
    (127, 13, 7, '2026-10-29'),
    (128, 14, 42, '2026-09-11'),
    (129, 14, 43, '2026-09-23'),
    (130, 14, 44, '2026-10-05'),
    (131, 14, 45, '2026-10-17'),
    (132, 14, 46, '2026-10-29'),
    (133, 14, 34, '2026-09-19'),
    (134, 14, 35, '2026-09-29'),
    (135, 14, 36, '2026-10-09'),
    (136, 14, 37, '2026-10-19'),
    (137, 14, 38, '2026-10-29'),
    (138, 14, 48, '2026-09-10'),
    (139, 14, 49, '2026-09-20'),
    (140, 14, 50, '2026-09-30'),
    (141, 14, 51, '2026-10-09'),
    (142, 14, 52, '2026-10-19'),
    (143, 14, 53, '2026-10-29'),
    (144, 14, 26, '2026-09-21'),
    (145, 14, 27, '2026-10-04'),
    (146, 14, 28, '2026-10-16'),
    (147, 14, 29, '2026-10-29'),
    (148, 15, 26, '2026-10-15'),
    (149, 15, 27, '2026-10-29'),
    (150, 15, 48, '2026-10-29'),
    (151, 15, 54, '2026-10-11'),
    (152, 15, 55, '2026-10-17'),
    (153, 15, 56, '2026-10-23'),
    (154, 15, 57, '2026-10-29'),
    (155, 15, 10, '2026-10-16'),
    (156, 15, 11, '2026-10-19'),
    (157, 15, 12, '2026-10-22'),
    (158, 15, 13, '2026-10-25'),
    (159, 15, 14, '2026-10-29'),
    (160, 15, 34, '2026-10-29'),
    (161, 16, 26, '2026-10-20'),
    (162, 16, 27, '2026-10-23'),
    (163, 16, 28, '2026-10-26'),
    (164, 16, 29, '2026-10-29'),
    (165, 16, 54, '2026-10-01'),
    (166, 16, 55, '2026-10-29'),
    (167, 17, 42, '2026-09-17'),
    (168, 17, 43, '2026-09-25'),
    (169, 17, 44, '2026-10-04'),
    (170, 17, 45, '2026-10-12'),
    (171, 17, 46, '2026-10-20'),
    (172, 17, 47, '2026-10-29'),
    (173, 17, 48, '2026-09-03'),
    (174, 17, 49, '2026-09-14'),
    (175, 17, 50, '2026-09-25'),
    (176, 17, 51, '2026-10-06'),
    (177, 17, 52, '2026-10-17'),
    (178, 17, 53, '2026-10-29'),
    (179, 17, 10, '2026-10-12'),
    (180, 17, 11, '2026-10-18'),
    (181, 17, 12, '2026-10-23'),
    (182, 17, 13, '2026-10-29'),
    (183, 17, 18, '2026-09-20'),
    (184, 17, 19, '2026-09-26'),
    (185, 17, 20, '2026-10-01'),
    (186, 17, 21, '2026-10-07'),
    (187, 17, 22, '2026-10-12'),
    (188, 17, 23, '2026-10-18'),
    (189, 17, 24, '2026-10-23'),
    (190, 17, 25, '2026-10-29'),
    (191, 18, 18, '2026-08-28'),
    (192, 18, 19, '2026-09-09'),
    (193, 18, 20, '2026-09-22'),
    (194, 18, 21, '2026-10-04'),
    (195, 18, 22, '2026-10-16'),
    (196, 18, 23, '2026-10-29'),
    (197, 18, 54, '2026-09-22'),
    (198, 18, 55, '2026-10-29'),
    (199, 18, 48, '2026-10-25'),
    (200, 18, 49, '2026-10-27'),
    (201, 18, 50, '2026-10-29'),
    (202, 19, 18, '2026-09-01'),
    (203, 19, 19, '2026-09-11'),
    (204, 19, 20, '2026-09-20'),
    (205, 19, 21, '2026-09-30'),
    (206, 19, 22, '2026-10-09'),
    (207, 19, 23, '2026-10-19'),
    (208, 19, 24, '2026-10-29'),
    (209, 19, 1, '2026-09-18'),
    (210, 19, 2, '2026-09-26'),
    (211, 19, 3, '2026-10-04'),
    (212, 19, 4, '2026-10-12'),
    (213, 19, 5, '2026-10-20'),
    (214, 19, 6, '2026-10-29'),
    (215, 19, 54, '2026-10-29'),
    (216, 20, 48, '2026-10-02'),
    (217, 20, 49, '2026-10-09'),
    (218, 20, 50, '2026-10-15'),
    (219, 20, 51, '2026-10-22'),
    (220, 20, 52, '2026-10-29'),
    (221, 20, 18, '2026-10-13'),
    (222, 20, 19, '2026-10-18'),
    (223, 20, 20, '2026-10-23'),
    (224, 20, 21, '2026-10-29'),
    (225, 20, 10, '2026-09-26'),
    (226, 20, 11, '2026-10-03'),
    (227, 20, 12, '2026-10-09'),
    (228, 20, 13, '2026-10-16'),
    (229, 20, 14, '2026-10-22'),
    (230, 20, 15, '2026-10-29'),
    (231, 20, 42, '2026-09-23'),
    (232, 20, 43, '2026-10-05'),
    (233, 20, 44, '2026-10-17'),
    (234, 20, 45, '2026-10-29'),
    (235, 20, 26, '2026-10-23'),
    (236, 20, 27, '2026-10-29'),
    (237, 21, 34, '2026-10-10'),
    (238, 21, 35, '2026-10-29'),
    (239, 21, 54, '2026-09-26'),
    (240, 21, 55, '2026-10-29'),
    (241, 22, 42, '2026-09-06'),
    (242, 22, 43, '2026-09-24'),
    (243, 22, 44, '2026-10-11'),
    (244, 22, 45, '2026-10-29'),
    (245, 22, 1, '2026-10-08'),
    (246, 22, 2, '2026-10-13'),
    (247, 22, 3, '2026-10-18'),
    (248, 22, 4, '2026-10-23'),
    (249, 22, 5, '2026-10-29'),
    (250, 22, 10, '2026-10-23'),
    (251, 22, 11, '2026-10-26'),
    (252, 22, 12, '2026-10-29'),
    (253, 22, 26, '2026-09-19'),
    (254, 22, 27, '2026-09-27'),
    (255, 22, 28, '2026-10-05'),
    (256, 22, 29, '2026-10-13'),
    (257, 22, 30, '2026-10-21'),
    (258, 22, 31, '2026-10-29'),
    (259, 23, 26, '2026-09-23'),
    (260, 23, 27, '2026-09-30'),
    (261, 23, 28, '2026-10-07'),
    (262, 23, 29, '2026-10-14'),
    (263, 23, 30, '2026-10-21'),
    (264, 23, 31, '2026-10-29'),
    (265, 23, 18, '2026-08-30'),
    (266, 23, 19, '2026-09-09'),
    (267, 23, 20, '2026-09-19'),
    (268, 23, 21, '2026-09-29'),
    (269, 23, 22, '2026-10-09'),
    (270, 23, 23, '2026-10-19'),
    (271, 23, 24, '2026-10-29'),
    (272, 23, 48, '2026-09-11'),
    (273, 23, 49, '2026-10-05'),
    (274, 23, 50, '2026-10-29'),
    (275, 23, 34, '2026-09-05'),
    (276, 23, 35, '2026-09-16'),
    (277, 23, 36, '2026-09-27'),
    (278, 23, 37, '2026-10-07'),
    (279, 23, 38, '2026-10-18'),
    (280, 23, 39, '2026-10-29'),
    (281, 23, 54, '2026-10-06'),
    (282, 23, 55, '2026-10-29');

-- ============================================================
-- PROGRESO POR CURSO (67)
-- ============================================================
INSERT INTO progresos (id, usuario_id, curso_id, porcentaje) VALUES
    (1, 3, 1, 33.33),
    (2, 3, 2, 12.50),
    (3, 3, 3, 62.50),
    (4, 3, 8, 50.00),
    (5, 4, 1, 77.78),
    (6, 4, 4, 50.00),
    (7, 4, 5, 25.00),
    (8, 8, 1, 66.67),
    (9, 8, 5, 0.00),
    (10, 8, 2, 100.00),
    (11, 8, 6, 100.00),
    (12, 8, 8, 50.00),
    (13, 9, 8, 100.00),
    (14, 9, 6, 66.67),
    (15, 10, 4, 100.00),
    (16, 10, 1, 55.56),
    (17, 10, 5, 75.00),
    (18, 11, 1, 0.00),
    (19, 11, 8, 100.00),
    (20, 11, 5, 100.00),
    (21, 11, 4, 62.50),
    (22, 12, 3, 50.00),
    (23, 12, 6, 16.67),
    (24, 12, 2, 75.00),
    (25, 13, 6, 50.00),
    (26, 13, 4, 100.00),
    (27, 13, 5, 50.00),
    (28, 13, 2, 50.00),
    (29, 13, 1, 77.78),
    (30, 14, 6, 83.33),
    (31, 14, 5, 62.50),
    (32, 14, 2, 0.00),
    (33, 14, 7, 100.00),
    (34, 14, 4, 50.00),
    (35, 15, 4, 25.00),
    (36, 15, 7, 16.67),
    (37, 15, 8, 100.00),
    (38, 15, 2, 62.50),
    (39, 15, 5, 12.50),
    (40, 16, 4, 50.00),
    (41, 16, 8, 50.00),
    (42, 17, 6, 100.00),
    (43, 17, 7, 100.00),
    (44, 17, 2, 50.00),
    (45, 17, 3, 100.00),
    (46, 18, 3, 75.00),
    (47, 18, 8, 50.00),
    (48, 18, 7, 50.00),
    (49, 19, 3, 87.50),
    (50, 19, 1, 66.67),
    (51, 19, 8, 25.00),
    (52, 20, 7, 83.33),
    (53, 20, 3, 50.00),
    (54, 20, 2, 75.00),
    (55, 20, 6, 66.67),
    (56, 20, 4, 25.00),
    (57, 21, 5, 25.00),
    (58, 21, 8, 50.00),
    (59, 22, 6, 66.67),
    (60, 22, 1, 55.56),
    (61, 22, 2, 37.50),
    (62, 22, 4, 75.00),
    (63, 23, 4, 75.00),
    (64, 23, 3, 87.50),
    (65, 23, 7, 50.00),
    (66, 23, 5, 75.00),
    (67, 23, 8, 50.00);

-- ============================================================
-- CALIFICACIONES (78)
-- ============================================================
INSERT INTO calificaciones (id, usuario_id, evaluacion_id, nota) VALUES
    (1, 3, 1, 2.00),
    (2, 3, 5, 1.25),
    (3, 3, 15, 1.25),
    (4, 4, 1, 5.00),
    (5, 4, 2, 5.00),
    (6, 4, 7, 5.00),
    (7, 8, 1, 5.00),
    (8, 8, 2, 3.75),
    (9, 8, 3, 3.75),
    (10, 8, 4, 5.00),
    (11, 8, 11, 5.00),
    (12, 8, 12, 5.00),
    (13, 9, 15, 2.50),
    (14, 9, 11, 3.33),
    (15, 9, 12, 3.75),
    (16, 10, 7, 5.00),
    (17, 10, 8, 4.00),
    (18, 10, 1, 5.00),
    (19, 10, 9, 5.00),
    (20, 10, 10, 5.00),
    (21, 11, 15, 3.75),
    (22, 11, 9, 3.75),
    (23, 11, 10, 4.00),
    (24, 11, 7, 2.50),
    (25, 12, 5, 5.00),
    (26, 12, 3, 5.00),
    (27, 12, 4, 5.00),
    (28, 13, 11, 1.67),
    (29, 13, 7, 5.00),
    (30, 13, 8, 4.00),
    (31, 13, 9, 2.50),
    (32, 13, 3, 3.75),
    (33, 13, 1, 3.00),
    (34, 13, 2, 1.25),
    (35, 14, 11, 1.67),
    (36, 14, 12, 2.50),
    (37, 14, 9, 5.00),
    (38, 14, 13, 5.00),
    (39, 14, 14, 5.00),
    (40, 14, 7, 3.75),
    (41, 15, 15, 3.75),
    (42, 15, 3, 3.75),
    (43, 16, 7, 0.00),
    (44, 17, 11, 1.67),
    (45, 17, 12, 5.00),
    (46, 17, 13, 1.67),
    (47, 17, 14, 5.00),
    (48, 17, 3, 2.50),
    (49, 17, 5, 3.75),
    (50, 17, 6, 5.00),
    (51, 18, 5, 2.50),
    (52, 18, 6, 1.00),
    (53, 18, 15, 3.75),
    (54, 18, 13, 5.00),
    (55, 19, 5, 5.00),
    (56, 19, 6, 4.00),
    (57, 19, 1, 5.00),
    (58, 20, 13, 1.67),
    (59, 20, 14, 3.75),
    (60, 20, 5, 3.75),
    (61, 20, 3, 3.75),
    (62, 20, 4, 4.00),
    (63, 20, 11, 3.33),
    (64, 20, 12, 3.75),
    (65, 21, 15, 5.00),
    (66, 22, 11, 5.00),
    (67, 22, 12, 5.00),
    (68, 22, 1, 5.00),
    (69, 22, 3, 1.25),
    (70, 22, 7, 5.00),
    (71, 22, 8, 5.00),
    (72, 23, 7, 5.00),
    (73, 23, 8, 5.00),
    (74, 23, 5, 3.75),
    (75, 23, 6, 5.00),
    (76, 23, 13, 5.00),
    (77, 23, 9, 5.00),
    (78, 23, 10, 5.00);

-- ============================================================
-- SECUENCIAS (para que los nuevos registros no choquen con los ids del seed)
-- ============================================================
SELECT setval(pg_get_serial_sequence('usuarios', 'id'), (SELECT MAX(id) FROM usuarios));
SELECT setval(pg_get_serial_sequence('cursos', 'id'), (SELECT MAX(id) FROM cursos));
SELECT setval(pg_get_serial_sequence('modulos', 'id'), (SELECT MAX(id) FROM modulos));
SELECT setval(pg_get_serial_sequence('lecciones', 'id'), (SELECT MAX(id) FROM lecciones));
SELECT setval(pg_get_serial_sequence('recursos', 'id'), (SELECT MAX(id) FROM recursos));
SELECT setval(pg_get_serial_sequence('evaluaciones', 'id'), (SELECT MAX(id) FROM evaluaciones));
SELECT setval(pg_get_serial_sequence('preguntas', 'id'), (SELECT MAX(id) FROM preguntas));
SELECT setval(pg_get_serial_sequence('respuestas', 'id'), (SELECT MAX(id) FROM respuestas));
SELECT setval(pg_get_serial_sequence('inscripciones', 'id'), (SELECT MAX(id) FROM inscripciones));
SELECT setval(pg_get_serial_sequence('lecciones_completadas', 'id'), (SELECT MAX(id) FROM lecciones_completadas));
SELECT setval(pg_get_serial_sequence('progresos', 'id'), (SELECT MAX(id) FROM progresos));
SELECT setval(pg_get_serial_sequence('calificaciones', 'id'), (SELECT MAX(id) FROM calificaciones));

COMMIT;

-- Resumen:
--   usuarios               23
--   cursos                 9
--   modulos                24
--   lecciones              59
--   recursos               43
--   evaluaciones           15
--   preguntas              63
--   respuestas             252
--   inscripciones          67
--   lecciones_completadas  282
--   progresos              67
--   calificaciones         78
--
-- Usuarios de prueba (contraseña 12345678):
--   admin@learnhub.com          ADMINISTRADOR
--   juan@learnhub.com           PROFESOR
--   laura.mendez@learnhub.com   PROFESOR
--   pedro@learnhub.com          ESTUDIANTE
--   maria@learnhub.com          ESTUDIANTE
