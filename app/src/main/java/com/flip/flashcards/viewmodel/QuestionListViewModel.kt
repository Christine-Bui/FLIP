package com.flip.flashcards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.flip.flashcards.model.Question
import com.flip.flashcards.repo.StudyRepository

class QuestionListViewModel(application: Application) : AndroidViewModel(application) {

    private val studyRepo = StudyRepository.getInstance(application)

    private val subjectIdLiveData = MutableLiveData<Long>()

    val questionListLiveData: LiveData<List<Question>> =
        Transformations.switchMap(subjectIdLiveData) { subjectId ->
            studyRepo.getQuestions(subjectId)
        }

    fun loadQuestions(subjectId: Long) {
        subjectIdLiveData.value = subjectId
    }

    fun addQuestion(question: Question) = studyRepo.addQuestion(question)

    fun deleteQuestion(question: Question) = studyRepo.deleteQuestion(question)
}