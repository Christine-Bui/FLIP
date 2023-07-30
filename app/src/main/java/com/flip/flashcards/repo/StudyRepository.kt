package com.flip.flashcards.repo

    import android.content.Context
    import com.flip.flashcards.model.Card
    import com.flip.flashcards.model.CardSubject
    import java.util.*

    class StudyRepository private constructor(context: Context) {

        private val subjectList = mutableListOf<CardSubject>()
        private val questionMap = mutableMapOf<Long, MutableList<Card>>()

        companion object {
            private var instance: StudyRepository? = null

            fun getInstance(context: Context): StudyRepository {
                if (instance == null) {
                    instance = StudyRepository(context)
                }
                return instance!!
            }
        }

        init {
            addStarterData()
        }

        fun addSubject(subject: CardSubject) {
            subjectList.add(subject)
            questionMap[subject.id] = mutableListOf()
        }

        fun getSubjects(): List<CardSubject> {
            return Collections.unmodifiableList(subjectList)
        }

        fun addQuestion(question: Card) {
            questionMap[question.subjectId]?.add(question)
        }

        fun getQuestions(subjectId: Long): List<Card> {
            return Collections.unmodifiableList(questionMap[subjectId]!!)
        }

        private fun addStarterData() {

            addSubject(CardSubject(1, "Android Studio"))
            addQuestion(Card(1, "What is 2 + 3?", "2 + 3 = 5", 1))
            addQuestion(Card(2, "What is pi?",
                "The ratio of a circle's circumference to its diameter.", 1))

            addSubject(CardSubject(2, "History"))
            addQuestion(Card(3,
                "On what date was the U.S. Declaration of Independence adopted?",
                "July 4, 1776", 2))

            addSubject(CardSubject(3, "Computing"))
        }
    }
