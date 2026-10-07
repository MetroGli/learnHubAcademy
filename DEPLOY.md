# Despliegue en el servidor: dominio con Academy y Docs

## 1. Cómo va a quedar

```
tudominio.com            →  Página de inicio: elegir Academy o Docs     (carpeta landing/)
academy.tudominio.com    →  LearnHub Academy, la plataforma de cursos   (este proyecto)
docs.tudominio.com       →  LearnHub Docs, los entregables              (WebLearnHubDocs, el que ya tienes)
```

Los tres proyectos son independientes (cada uno con su propio `docker-compose`). Los junta
**Traefik**, el mismo que ya usa tu proyecto de Docs: recibe las visitas de los puertos 80/443,
saca los certificados HTTPS solo y reparte cada visita según el nombre (`academy.`, `docs.` o el
dominio solo). Por eso todos los `docker-compose` se conectan a la red `learnhub-proxy`.

LearnHub Academy levanta tres contenedores:

| Contenedor | Qué hace |
|---|---|
| `db`  | PostgreSQL (los datos quedan guardados en un volumen y no se pierden al actualizar) |
| `api` | El backend Spring Boot |
| `web` | Caddy: entrega la app React y reenvía todo lo que empiece con `/api/` al backend |

Solo `web` queda expuesto a Traefik; la base de datos y el backend no se pueden alcanzar desde internet.

## 2. Revisar el servidor antes de empezar

```bash
docker ps                                # debe aparecer el contenedor de traefik
docker network ls | grep learnhub-proxy  # debe existir esa red
free -h                                  # memoria disponible
```

Academy compila Java y React al desplegar. Si el servidor tiene **1 GB de RAM** (o menos), crea
memoria de intercambio antes de seguir, o la compilación se puede cortar:

```bash
sudo fallocate -l 2G /swapfile && sudo chmod 600 /swapfile
sudo mkswap /swapfile && sudo swapon /swapfile
echo '/swapfile none swap sw 0 0' | sudo tee -a /etc/fstab
```

Carpetas sugeridas en el servidor:

```
~/learnhub/docs/       ← el proyecto WebLearnHubDocs (ya lo tienes)
~/learnhub/academy/    ← este proyecto (incluye la carpeta landing/)
```

## 3. DNS (hazlo primero)

En el panel de tu dominio crea estos registros, todos hacia la **IP pública** de tu servidor
(la misma que ya usa tu sitio de Docs):

| Tipo | Nombre | Valor |
|---|---|---|
| A | `@` (el dominio solo) | IP del servidor |
| A | `academy` | IP del servidor |
| A | `docs` | IP del servidor |

Comprueba que ya resuelven antes de seguir (puede tardar unos minutos):

```bash
nslookup academy.tudominio.com
nslookup docs.tudominio.com
```

> Importante: los registros deben existir **antes** de levantar los contenedores. Si Traefik pide
> el certificado y el nombre todavía no resuelve, Let's Encrypt lo rechaza y hay que esperar.

## 4. Mover Docs a su subdominio

Hasta ahora Docs vivía en el dominio principal. En la carpeta de Docs, edita el archivo `.env`:

```
DOMAIN=docs.tudominio.com
```

y vuelve a levantarlo (no necesita reconstruirse):

```bash
cd ~/learnhub/docs
docker compose up -d
```

Comprueba que abre `https://docs.tudominio.com`.

## 5. Desplegar LearnHub Academy

```bash
cd ~/learnhub/academy        # aquí está el proyecto (backend/, frontend/, docker-compose.prod.yml)
cp .env.example .env
nano .env
```

Valores que debes poner en `.env`:

| Variable | Qué poner |
|---|---|
| `ACADEMY_HOST` | `academy.tudominio.com` |
| `DB_PASSWORD` | una clave larga. Genera una con `openssl rand -hex 24` |
| `JWT_SECRET` | otra clave, mínimo 32 caracteres. Genera una con `openssl rand -hex 32` |

Las demás variables pueden quedar como están. Luego:

```bash
docker compose -f docker-compose.prod.yml up -d --build
```

La primera vez tarda varios minutos (descarga imágenes y compila). Para ver cuándo el backend
terminó de arrancar:

```bash
docker compose -f docker-compose.prod.yml logs -f api     # Ctrl+C para salir
```

Cuando veas una línea que dice `Started ...Application`, abre `https://academy.tudominio.com`.
Verás el login, pero todavía no existe ningún usuario.

## 6. Crear el primer usuario

**Administrador real** (recomendado):

```bash
chmod +x scripts/crear-admin.sh
./scripts/crear-admin.sh
```

Te pide nombre, apellido, correo y contraseña. Con ese usuario entras, y desde el panel creas
profesores y estudiantes.

> Si el proyecto pasó por Windows y el script da `bad interpreter` o errores raros, tiene finales
> de línea de Windows. Arréglalo con: `sed -i 's/\r$//' scripts/crear-admin.sh`

**Datos de ejemplo** (para una demostración): carga los usuarios, cursos y evaluaciones de prueba.
Solo en una base de datos vacía, porque este archivo borra lo que haya:

```bash
docker compose -f docker-compose.prod.yml cp database/learnhub_seed.sql db:/tmp/seed.sql
docker compose -f docker-compose.prod.yml exec -T db sh -c 'psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -f /tmp/seed.sql'
```

Esos usuarios (`admin@learnhub.com`, `juan@learnhub.com`...) tienen la contraseña `12345678`.
Cámbiala enseguida desde el menú ⋮ → *Mi perfil y configuración*, o bórralos antes de
compartir la dirección.

## 7. Página de inicio del dominio

```bash
cd ~/learnhub/academy/landing
cp .env.example .env
nano .env                    # DOMAIN=tudominio.com
docker compose up -d
```

Abre `https://tudominio.com`: debe verse la página con las dos opciones, y cada una lleva a su
subdominio. Los enlaces se arman solos con el dominio con el que entraste.

Si prefieres otros nombres de subdominio (por ejemplo `learnhubacademy`), cambia en
`landing/site/index.html` los valores `data-sub="academy"` y `data-sub="docs"`, y usa los mismos
nombres en los registros DNS, en `ACADEMY_HOST` (Academy) y en `DOMAIN` (Docs). El contenido de
`landing/site/` se sirve en vivo: al editarlo basta con recargar el navegador.

## 8. Lista de comprobación

- `https://tudominio.com` → página con las dos tarjetas
- `https://docs.tudominio.com` → el sitio de entregables
- `https://academy.tudominio.com` → login de LearnHub; entras con el usuario del paso 6
- Recargar con F5 estando dentro de un curso (`/curso/1`) sigue mostrando la página, no un error 404

## 9. Actualizar más adelante

Después de cambiar el código (por ejemplo con `git pull`):

```bash
cd ~/learnhub/academy
docker compose -f docker-compose.prod.yml up -d --build
```

Los datos de la base de datos se conservan. Para ver cómo van los contenedores: `docker compose -f docker-compose.prod.yml ps`.

## 10. Si algo falla

| Síntoma | Qué revisar |
|---|---|
| Traefik responde `404 page not found` | El nombre no coincide con `ACADEMY_HOST` / `DOMAIN`, o el contenedor no está en la red `learnhub-proxy`. Mira `docker logs <contenedor-de-traefik>` |
| El navegador avisa de certificado inválido | El DNS aún no resolvía cuando se pidió el certificado. Espera unos minutos y revisa los logs de Traefik |
| `502 Bad Gateway` en academy | El backend sigue arrancando o falló: `docker compose -f docker-compose.prod.yml logs api` |
| El login responde error y el backend se queja de la clave | `JWT_SECRET` tiene menos de 32 caracteres |
| Contenedores que se reinician solos | Falta memoria (`docker stats`, `free -h`). Baja `API_JAVA_OPTS` (por ejemplo `-Xmx256m`) y crea swap (paso 2) |
| `network learnhub-proxy declared as external, but could not be found` | La red de Traefik se llama distinto: mira `docker network ls` y cambia el nombre en los `docker-compose` |

## Importante antes de compartir la dirección

Este despliegue pone la plataforma en internet tal como está el código hoy. El backend todavía
no restringe por rol la mayoría de los endpoints: cualquier usuario con sesión podría llamar a la
API directamente y, por ejemplo, modificar notas. Para una demostración o entrega está bien, pero
no la uses con datos reales ni la difundas hasta hacer el repaso de seguridad (permisos por rol y
por propietario en el backend).
