package com.example.retireaqui.network.providers

interface IHashProvider {
    fun generateHash(payload: String) : String
    fun compare(payload: String, hashed: String) : Boolean
}