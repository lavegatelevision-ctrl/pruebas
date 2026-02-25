# La Vega Television Android TV

Aplicación Android TV nativa que consume la API de WordPress de `https://lavegatelevision.com/`.

## Qué hace

- Carga publicaciones desde: `https://lavegatelevision.com/wp-json/wp/v2/posts`.
- Muestra listado optimizado para control remoto (DPAD).
- Permite abrir detalle de cada publicación sin usar `WebView` principal.

## Requisitos

- Android Studio Jellyfish o superior.
- Android SDK 34.

## Ejecutar

1. Abrir el proyecto en Android Studio.
2. Sincronizar Gradle.
3. Si Android Studio te pide regenerar wrapper, ejecuta en terminal del proyecto:
   - `gradle wrapper --gradle-version 8.7 --no-validate-url`
4. Ejecutar en un emulador Android TV o dispositivo físico.

## Nota sobre ramas/Git

- Este repo excluye `gradle/wrapper/gradle-wrapper.jar` para evitar bloqueos en plataformas que no aceptan archivos binarios en commits/PR.

## ZIP listo para subir

- En la raíz del proyecto: `LaVegaTelevisionTV-androidtv.zip`.
- Puedes importarlo/descomprimirlo directamente en Android Studio.
