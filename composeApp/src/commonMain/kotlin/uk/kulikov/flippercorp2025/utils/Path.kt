package uk.kulikov.flippercorp2025.utils

import kotlinx.io.files.Path

fun Path.resolve(vararg parts: String): Path = Path(this, parts = parts)
