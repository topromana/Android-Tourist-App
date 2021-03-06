package com.example.calatour.model

class ChatMessage (
        val author: String,
        val content: String,
        val timestamp: String,
        var important: Boolean = false
)