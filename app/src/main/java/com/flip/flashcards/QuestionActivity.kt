package com.flip.flashcards

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.flip.flashcards.model.Question
import com.flip.flashcards.model.Subject
import com.flip.flashcards.viewmodel.QuestionListViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import android.app.Activity
import android.content.Intent
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar



class QuestionActivity : AppCompatActivity() {

    private lateinit var subject: Subject
    private lateinit var questionList: List<Question>
    private lateinit var backCardTextView: TextView
    private lateinit var flipButton: Button
    private lateinit var frontCardTextView: TextView
    private lateinit var showQuestionLayout: ViewGroup
    private lateinit var noCardLayout: ViewGroup
    private var currentQuestionIndex = 0


    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet


    private var isShowingFront = true


    private val questionListViewModel: QuestionListViewModel by lazy {
        ViewModelProvider(this).get(QuestionListViewModel::class.java)
    }
    private val addQuestionResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            // Display the added question, which will appear at end of list
            currentQuestionIndex = questionList.size

            Toast.makeText(this, "Card added", Toast.LENGTH_SHORT).show()
        }
    }
    private lateinit var deletedQuestion: Question


    companion object {
        const val EXTRA_SUBJECT_ID = "com.flip.flashcards.subject_id"
        const val EXTRA_SUBJECT_TEXT = "com.flip.flashcards.subject_text"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer)


        frontCardTextView = findViewById(R.id.front_card_text_view)
        backCardTextView = findViewById(R.id.back_card_text_view)


        val scale = applicationContext.resources.displayMetrics.density

        frontCardTextView.cameraDistance = 8000 * scale
        backCardTextView.cameraDistance = 8000 * scale

        front_anim=AnimatorInflater.loadAnimator(applicationContext, R.animator.front_animator) as AnimatorSet
        back_anim=AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        flipButton = findViewById(R.id.flip_button)
        showQuestionLayout = findViewById(R.id.show_question_layout)
        noCardLayout = findViewById(R.id.no_question_layout)


        // Add click callbacks
        flipButton.setOnClickListener { toggleAnswerVisibility() }
        backCardTextView.setOnClickListener{toggleAnswerVisibility()}
        frontCardTextView.setOnClickListener{toggleAnswerVisibility()}
        findViewById<Button>(R.id.add_question_button).setOnClickListener { addQuestion() }

        // SubjectActivity should provide the subject ID and text
        val subjectId = intent.getLongExtra(EXTRA_SUBJECT_ID, 0)
        val subjectText = intent.getStringExtra(EXTRA_SUBJECT_TEXT)
        subject = Subject(subjectId, subjectText!!)


        questionList = emptyList()
        questionListViewModel.loadQuestions(subjectId)
        questionListViewModel.questionListLiveData.observe(
            this
        ) { questionList ->
            this.questionList = questionList
            updateUI()
        }
    }


    private fun toggleAnswerVisibility() {



        if (isShowingFront)
        {

            front_anim.setTarget(frontCardTextView)
            back_anim.setTarget(backCardTextView)
            front_anim.start()
            back_anim.start()
            backCardTextView.visibility = View.VISIBLE
            isShowingFront = false

        } else
        {
            front_anim.setTarget(backCardTextView)
            back_anim.setTarget(frontCardTextView)
            back_anim.start()
            front_anim.start()
            backCardTextView.visibility = View.INVISIBLE
            isShowingFront = true


        }


    }

    private fun updateUI() {
        showQuestion(currentQuestionIndex)

        if (questionList.isEmpty()) {
            updateAppBarTitle()
            displayQuestion(false)
        } else {
            displayQuestion(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.question_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //  Determine which app bar item was chosen
        return when (item.itemId) {
            R.id.previous -> {
                showQuestion(currentQuestionIndex - 1)
                true
            }
            R.id.next -> {
                showQuestion(currentQuestionIndex + 1)
                true
            }
            R.id.add -> {
                addQuestion()
                true
            }
            R.id.edit -> {
                editQuestion()
                true
            }
            R.id.delete -> {
                deleteQuestion()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun displayQuestion(display: Boolean) {
        if (display) {
            showQuestionLayout.visibility = View.VISIBLE
            noCardLayout.visibility = View.GONE
        } else {
            showQuestionLayout.visibility = View.GONE
            noCardLayout.visibility = View.VISIBLE
        }
    }

    private fun updateAppBarTitle() {

        // Display subject and number of questions in app bar
        val title = resources.getString(R.string.question_number, subject.text,
            currentQuestionIndex + 1, questionList.size)
        setTitle(title)
    }

    private fun addQuestion() {
        val intent = Intent(this, QuestionEditActivity::class.java)
        intent.putExtra(EXTRA_SUBJECT_ID, subject.id)
        addQuestionResultLauncher.launch(intent)
    }

    private val editQuestionResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, R.string.question_updated, Toast.LENGTH_SHORT).show()
        }
    }

    private fun editQuestion() {
        if (currentQuestionIndex >= 0) {
            val intent = Intent(this, QuestionEditActivity::class.java)
            intent.putExtra(
                QuestionEditActivity.EXTRA_QUESTION_ID,
                questionList[currentQuestionIndex].id
            )
            editQuestionResultLauncher.launch(intent)
        }
    }

    private fun deleteQuestion() {
        if (currentQuestionIndex >= 0) {
            val question = questionList[currentQuestionIndex]
            questionListViewModel.deleteQuestion(question)
            // Save question in case user wants to undo delete
            deletedQuestion = question

            // Show delete message with Undo button
            val snackbar = Snackbar.make(
                findViewById(R.id.coordinator_layout),
                R.string.question_deleted, Snackbar.LENGTH_LONG
            )
            snackbar.setAction(R.string.undo) {
                // Add question back
                questionListViewModel.addQuestion(deletedQuestion)
            }
            snackbar.show()
        }
    }

    private fun showQuestion(questionIndex: Int) {

        // Show question at the given index
        if (questionList.isNotEmpty()) {
            var newQuestionIndex = questionIndex

            if (questionIndex < 0) {
                newQuestionIndex = questionList.size - 1
            } else if (questionIndex >= questionList.size) {
                newQuestionIndex = 0
            }

            currentQuestionIndex = newQuestionIndex
            updateAppBarTitle()

            val question = questionList[currentQuestionIndex]
            frontCardTextView.text = question.text
            backCardTextView.text = question.answer


            //backCardTextView.visibility = View.INVISIBLE



        } else {
            // No questions yet
            currentQuestionIndex = -1
        }
    }

}
