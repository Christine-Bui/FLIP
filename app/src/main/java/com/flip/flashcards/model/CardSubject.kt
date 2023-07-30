package com.flip.flashcards.model

data class CardSubject(
    var id: Long = 0,
    var test: String,
    var updateTime: Long = System.currentTimeMillis()
) {
}