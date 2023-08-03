package com.flip.flashcards.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.flip.flashcards.model.Question
import com.flip.flashcards.repo.StudyRepository

class QuestionDetailViewModel(application: Application) : AndroidViewModel(application) {

    private val studyRepo = StudyRepository.getInstance(application)

    private val questionIdLiveData = MutableLiveData<Long>()

    val questionLiveData: LiveData<Question> =
        Transformations.switchMap(questionIdLiveData) { questionId ->
            studyRepo.getQuestion(questionId)
        }

    fun loadQuestion(questionId: Long) {
        questionIdLiveData.value = questionId
    }

    fun addQuestion(question: Question) = studyRepo.addQuestion(question)

    fun updateQuestion(question: Question) = studyRepo.updateQuestion(question)
}