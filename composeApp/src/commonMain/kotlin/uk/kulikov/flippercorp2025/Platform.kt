package uk.kulikov.flippercorp2025

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform