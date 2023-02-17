package br.com.phs.kmmnotes

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform