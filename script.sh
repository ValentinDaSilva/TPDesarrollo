#!/bin/bash

# 📂 Ruta raíz del proyecto (puede ser el directorio actual o uno que le pases como argumento)
RAIZ="${1:-.}"

# 📄 Archivo de salida
SALIDA="ProyectoConsolidado.java"

# 🧹 Limpio el archivo de salida si ya existe
> "$SALIDA"

# 🔍 Recorro todos los archivos .java desde la raíz
find "$RAIZ" -type f -name "*.java" | while read -r archivo; do
    # Quito el prefijo de la ruta raíz y agrego encabezado tipo //Carpeta/Archivo.java
    ruta_relativa="${archivo#$RAIZ/}"

    echo "//$ruta_relativa" >> "$SALIDA"
    echo "{" >> "$SALIDA"
    cat "$archivo" >> "$SALIDA"
    echo -e "\n}\n" >> "$SALIDA"
done

echo "✅ Consolidación completada en '$SALIDA'"
