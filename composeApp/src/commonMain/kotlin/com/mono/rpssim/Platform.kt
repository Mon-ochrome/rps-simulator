package com.mono.rpssim

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform