# ===== Etapa 1: compilar la app React =====
FROM node:20-alpine AS build
WORKDIR /app

COPY package.json package-lock.json ./
RUN npm ci

COPY . .

# Vacío = el navegador llama a "/api" en el mismo dominio (Caddy lo reenvía al backend)
ARG VITE_API_URL=
ENV VITE_API_URL=$VITE_API_URL
RUN npm run build

# ===== Etapa 2: servir los archivos con Caddy =====
FROM caddy:2-alpine
COPY Caddyfile /etc/caddy/Caddyfile
COPY --from=build /app/dist /srv
