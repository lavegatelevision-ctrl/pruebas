# OTT TV Starter (Android Studio)

Plantilla inicial para una app **Android TV OTT** con:

- Inicio de sesión y registro (demo de flujo).
- Membresía **Gratis** y **Premium (5€)**.
- Secciones para contenido **24/7**, **Live**, **PPV (pagar por ver)** y **Vimeo**.
- UI moderna/minimalista con Jetpack Compose.

## Abrir en Android Studio

1. Abre Android Studio (Hedgehog o superior).
2. `File > Open` y selecciona esta carpeta.
3. Sincroniza Gradle.
4. Ejecuta en un emulador Android TV o dispositivo Android TV.

## Qué incluye esta base

- Flujo de pantallas: `Login -> Register -> Home`.
- Catálogo mock de tarjetas con tipos OTT.
- Estructura lista para conectar backend real.

## Integraciones recomendadas para producción

1. **Autenticación**: Firebase Auth o backend propio (JWT + refresh token).
2. **Membresías**:
   - Android TV: Google Play Billing.
   - Web/API externa: Stripe para suscripción mensual 5€.
3. **PPV**:
   - Modelo de compra por contenido/evento.
   - Validación de acceso por usuario/evento.
4. **Vimeo**:
   - Vimeo API para catálogo.
   - Reproductor embebido según políticas de Vimeo.
5. **Live y 24/7**:
   - HLS/DASH para streams continuos.
   - ExoPlayer/Media3 para reproducción robusta.

## Siguientes tareas sugeridas

- Añadir arquitectura `data/domain/ui`.
- Conectar API REST/GraphQL.
- Persistencia de sesión segura.
- Sistema de roles (admin para publicar videos).
- CMS/backoffice para subir contenido y gestionar catálogo.

## ¿Cómo agrego contenido?

Ahora tienes una lista central en:

- `app/src/main/java/com/example/otttv/data/SampleCatalog.kt`

### Ejemplo rápido

Añade un nuevo elemento dentro de `sampleCatalog`:

```kotlin
ContentItem(
    title = "Concierto especial",
    type = "LIVE",
    access = "Pagar por ver"
)
```

### Qué significa cada campo

- `title`: título que ve el usuario.
- `type`: tipo de contenido (`24/7`, `LIVE`, `PPV`, `VIMEO`).
- `access`: regla de acceso (`Incluido`, `Premium`, `Pagar por ver`).

### En producción (recomendado)

En vez de lista fija, normalmente se hace así:

1. Subes videos y metadata a un backend/CMS.
2. La app consulta API (`/catalog`, `/live`, `/ppv`, etc.).
3. Según membresía/compra, el backend devuelve permiso de reproducción.
4. Reproduces con Media3/ExoPlayer usando URLs HLS/DASH.
