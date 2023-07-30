package com.flip.flashcards.model

data class Card(
    var id: Long = 0,
    var front: String = "",
    var answer: String = "",
    var subjectId: Long = 0)
{}