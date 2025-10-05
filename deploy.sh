#!/bin/bash
set -e

echo "Iniciando despliegue de TicketResolve..."

# Detecta automáticamente la ruta actual
PROJECT_DIR=$(cd "$(dirname "$0")" && pwd)
cd "$PROJECT_DIR"

echo "Actualizando código fuente..."
git pull origin main

echo "Deteniendo contenedores anteriores..."
docker-compose down

echo "🏗Construyendo y levantando contenedores..."
docker-compose up --build -d

echo "Despliegue completado exitosamente."
docker ps