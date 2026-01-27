package com.falling.anwar

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform