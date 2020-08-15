package com.sectumsempra.auth.password

interface PasswordHash {

    fun hash(password: String) : String
}