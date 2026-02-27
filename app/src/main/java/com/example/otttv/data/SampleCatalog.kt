package com.example.otttv.data

import com.example.otttv.ContentItem

/**
 * Catálogo de ejemplo editable.
 *
 * Para agregar contenido nuevo, añade un ContentItem en la lista.
 * - title: nombre visible del contenido
 * - type: categoría (24/7, LIVE, PPV, VIMEO)
 * - access: regla de acceso (Incluido, Premium, Pagar por ver)
 */
val sampleCatalog = listOf(
    ContentItem(
        title = "Canal 24/7 Noticias",
        type = "24/7",
        access = "Incluido"
    ),
    ContentItem(
        title = "Evento en vivo",
        type = "LIVE",
        access = "Premium"
    ),
    ContentItem(
        title = "Película estreno",
        type = "PPV",
        access = "Pagar por ver"
    ),
    ContentItem(
        title = "Vimeo Creator Pick",
        type = "VIMEO",
        access = "Incluido"
    )
)
