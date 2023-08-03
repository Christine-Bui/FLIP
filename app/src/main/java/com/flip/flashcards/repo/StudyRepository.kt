package com.flip.flashcards.repo

import android.content.Context
import androidx.room.Room
import com.flip.flashcards.model.Question
import com.flip.flashcards.model.Subject
import androidx.lifecycle.LiveData


class StudyRepository private constructor(context: Context) {

    companion object {
        private var instance: StudyRepository? = null

        fun getInstance(context: Context): StudyRepository {
            if (instance == null) {
                instance = StudyRepository(context)
            }
            return instance!!
        }
    }

    private val database : StudyDatabase = Room.databaseBuilder(
        context.applicationContext,
        StudyDatabase::class.java,
        "study.db"
    )
        .allowMainThreadQueries()
        .build()

    private val subjectDao = database.subjectDao()
    private val questionDao = database.questionDao()



    fun getSubject(subjectId: Long): LiveData<Subject?> = subjectDao.getSubject(subjectId)

    fun getSubjects(): LiveData<List<Subject>> = subjectDao.getSubjects()

    fun addSubject(subject: Subject) {
        subject.id = subjectDao.addSubject(subject)
    }

    fun deleteSubject(subject: Subject) = subjectDao.deleteSubject(subject)

    fun getQuestion(questionId: Long): LiveData<Question?> = questionDao.getQuestion(questionId)

    fun getQuestions(subjectId: Long): LiveData<List<Question>> = questionDao.getQuestions(subjectId)
    fun addQuestion(question: Question) {
        question.id = questionDao.addQuestion(question)
    }

    fun updateQuestion(question: Question) = questionDao.updateQuestion(question)

    fun deleteQuestion(question: Question) = questionDao.deleteQuestion(question)

    private fun addStarterData() {
        var subjectId = subjectDao.addSubject(Subject(text = "Elementary Math"))
        questionDao.addQuestion(
            Question(
                text = "What is 2 + 3?",
                answer = "2 + 3 = 5",
                subjectId = subjectId
            )
        )
        questionDao.addQuestion(
            Question(
                text = "What is pi?",
                answer = "The ratio of a circle's circumference to its diameter.",
                subjectId = subjectId
            )
        )

        subjectId = subjectDao.addSubject(Subject(text = "History"))
        questionDao.addQuestion(
            Question(
                text = "On what date was the U.S. Declaration of Independence adopted?",
                answer = "July 4, 1776",
                subjectId = subjectId
            )
        )

        subjectDao.addSubject(Subject(text = "Computing"))
    }
}