#!/usr/bin/env bash
# Crea el primer usuario ADMINISTRADOR en la base de datos de producción.
# Requiere que la plataforma ya esté levantada (así el backend ya creó las tablas).
#
#   ./scripts/crear-admin.sh
set -euo pipefail
cd "$(dirname "$0")/.."

COMPOSE=(docker compose -f docker-compose.prod.yml)

read -rp "Nombre: " NOMBRE
read -rp "Apellido: " APELLIDO
read -rp "Correo: " EMAIL
read -rsp "Contraseña (mínimo 8 caracteres): " PASS
echo
read -rsp "Repite la contraseña: " PASS2
echo

if [ "$PASS" != "$PASS2" ]; then
  echo "Las contraseñas no coinciden." >&2
  exit 1
fi
if [ "${#PASS}" -lt 8 ]; then
  echo "La contraseña debe tener al menos 8 caracteres." >&2
  exit 1
fi

# Hash BCrypt (el formato que usa Spring Security). La contraseña entra por stdin.
HASH=$(printf '%s' "$PASS" | docker run --rm -i httpd:2.4-alpine htpasswd -niBC 10 "" | tr -d ':\n' | sed 's/^\$2y/$2a/')

"${COMPOSE[@]}" exec -T db sh -c \
  'psql -U "$POSTGRES_USER" -d "$POSTGRES_DB" -v ON_ERROR_STOP=1 -v nombre="$1" -v apellido="$2" -v email="$3" -v hash="$4"' \
  sh "$NOMBRE" "$APELLIDO" "$EMAIL" "$HASH" <<'SQL'
INSERT INTO usuarios (nombre, apellido, email, password, rol, activo)
VALUES (:'nombre', :'apellido', :'email', :'hash', 'ADMINISTRADOR', true);
SQL

echo "Listo: ya puedes entrar con $EMAIL"
