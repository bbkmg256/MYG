#!/bin/env bash

# Este script elimina el contenedor y limpia el volumen completo de la BD
# Usar el caso de quere borrar todo al carajo xd

# ELIMINA EL CONTENEDOR
docker rm postgres_container &> /dev/null

if [ $? -gt 0 ]; then
	echo -e "[-] Fallo al eliminar el contenedor!\n"
	exit 1
fi

# ELIMINA EL VOLUMEN DE ALMACENAMIENTO DEL CONTENEDOR (Y TODAS LAS BD'S)
docker volume rm postgres_bd_postgres_data &> /dev/null

if [ $? -gt 0 ]; then
	echo -e "[-] Fallo al eliminar el volumen del contenedor!\n"
	exit 1
fi

echo -e "[+] Limpieza correcta!\n"
exit 0
