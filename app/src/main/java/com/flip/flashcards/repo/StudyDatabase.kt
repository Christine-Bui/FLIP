package com.flip.flashcards.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import com.flip.flashcards.model.Question
import com.flip.flashcards.model.Subject

@Database(entities = [Question::class, Subject::class], version = 1)
abstract class StudyDatabase : RoomDatabase() {

    abstract fun questionDao(): QuestionDao
    abstract fun subjectDao(): SubjectDao
}