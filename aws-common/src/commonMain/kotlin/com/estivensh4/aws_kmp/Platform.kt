package com.estivensh4.aws_kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform