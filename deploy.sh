#!/bin/bash
set -e

echo "Iniciando despliegue de TicketResolve..."

cd /home/ubuntu/mi-proyecto

echo "Actualizando código fuente..."
git pull origin main

echo "Deteniendo contenedores antiguos..."
docker-compose down

echo "🏗Construyendo e iniciando contenedores..."
docker-compose up --build -d

echo "Despliegue completado correctamente."
docker ps