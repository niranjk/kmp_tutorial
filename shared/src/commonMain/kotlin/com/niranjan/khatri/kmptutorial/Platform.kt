package com.niranjan.khatri.kmptutorial

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform