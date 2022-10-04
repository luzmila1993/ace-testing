# templates

Templates Azure Pipeline que serán invocados por el pipeline de la aplicación para la construcción del artefacto.

## ibm-ace-build.yml

Template que contiene todas las tareas necesarias para construir y validar que el artefacto cumpla con los requisitos solicitados y se encuentra en correcto funcionamiento.

### supportfiles

Los archivos de soporte son archivos que serán utilizados durante la ejecución de los pipelines y que reciben parámetros para su reutilización.

Dentro de la carpeta se encuentran los siguientes files:
| SupportFile  | Descripción |
| ------------- | ------------- |
| Dockerfile | Archivo Docker que contiene los comandos Docker necesarios para construir la aplicación y ejecutar sus pruebas unitarias. |
| testresultformatting.sh | Archivo encargado de procesar el resultado de las pruebas unitarias para que Azure DevOps pueda interpretarlo de forma gráfica. |
