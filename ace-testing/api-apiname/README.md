# api-apiname

Repositorio que contiene el código fuente, pipeline y archivos necesarios para la compilación de la aplicación.

## ap-build.yml

Pipeline que orquesta la construcción del artefacto a través el uso de templates Azure Pipeline.

Se debe actualizar las siguientes variables definidas con el siguiente formato `<variable>`:
| Variable  | Descripción |
| ------------- | ------------- |
| endpoint | Endpoint de conexión al repositorio GIT. |
| organizacion | Nombre de la organizacion asociada al repositorio GIT. |
| repositorio | Nombre del repositorio GIT.  |
| poolname | Nombre del agente.  |

## ace-apiname

Carpeta que contiene el código fuente.

## ace-apiname_Test

Carpeta que contiene las pruebas unitarias de la aplicación


## variables.yml

Archivo que contiene todos los variable groups requeridos en el proceso de compilación.

El group name definido es el siguiente:

### build-api-apiname-001

| Nombre  | Descripción |
| ------------- | ------------- |
| aceImage | ACE Server Image que se setea en el Dockerfile. |
| aceVersion | Versión de ACE que se añadirá como propiedad al build en JFrog. |
| appName | Nombre de la aplicación. |
| appRepo | Nombre del repositorio definido desde resources. |
| barName | Nombre del BAR que será cargado en JFrog. |
| buildNameJFrog | Nombre del build que será publicado en JFrog. |
| orgName | Nombre de la organización acorde al directorio en JFrog. |
| projectName | Nombre del proyecto ACE. |
| testBar | Nombre del BAR del proyecto de Test. |
| testProject | Nombre del proyecto de Test. |
