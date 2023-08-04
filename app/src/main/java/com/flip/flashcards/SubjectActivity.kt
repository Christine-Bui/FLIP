package com.flip.flashcards

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ActionMode
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flip.flashcards.model.Subject
import com.flip.flashcards.viewmodel.SubjectListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton


class SubjectActivity : AppCompatActivity(),
    SubjectDialogFragment.OnSubjectEnteredListener {
    private var loadSubjectList = true
    private var subjectAdapter = SubjectAdapter(mutableListOf())
    private lateinit var subjectRecyclerView: RecyclerView
    private lateinit var subjectColors: IntArray
    private val subjectListViewModel: SubjectListViewModel by lazy {
        ViewModelProvider(this).get(SubjectListViewModel::class.java)
    }
    private lateinit var selectedSubject: Subject
    private var selectedSubjectPosition = RecyclerView.NO_POSITION
    private var actionMode: ActionMode? = null

    //Create our four fragments object
    lateinit var homeFragment: HomeFragment
    lateinit var profileFragment: ProfileFragment
    private lateinit var dialog: BottomSheetDialog

    //Holds cards, but will be held into CardSetsModels later.
//    val cardModels = ArrayList<Card>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        //now let's create our framelayout and bottomnav variables
        val bottomnav = findViewById<BottomNavigationView>(R.id.BottomNavMenu)
        subjectColors = resources.getIntArray(R.array.subjectColors)
        subjectRecyclerView = findViewById(R.id.subject_recycler_view)
        subjectRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        // Show the subjects
        subjectListViewModel.subjectListLiveData.observe(
            this
        ) { subjectList ->
            if (loadSubjectList) {
                updateUI(subjectList)
            }
            // Check if there are subjects available, if yes, hide the HomeFragment and show RecyclerView
            if (subjectList.isNotEmpty()) {
                showRecyclerView()
            } else {
                showHomeFragment()
            }
        }



//        supportActionBar?.apply {
//            title = "GfG | Action Bar"
//            subtitle = "Design a custom Action Bar"
//            setIcon(R.drawable.app_logo)
//            setDisplayUseLogoEnabled(true)
//            setDisplayShowHomeEnabled(true)
//        }

        //now we will need to create our different fragments
        //Now let's add the menu event
        bottomnav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    // Handle home menu item selection
                    if (subjectListViewModel.subjectListLiveData.value.isNullOrEmpty()) {
                        showHomeFragment()
                    } else {
                        showRecyclerView()
                    }
                }
                R.id.new_set -> {
                    // Handle new_set menu item selection
                    val dialog = SubjectDialogFragment()
                    dialog.show(supportFragmentManager, "subjectDialog")
                }
                R.id.profile -> {
                    showProfileFragment()
                }
            }
            true
        }

    }

    private fun showRecyclerView() {
        subjectRecyclerView.visibility = View.VISIBLE
        subjectRecyclerView.layoutManager = GridLayoutManager(this, 2)
        subjectRecyclerView.adapter = subjectAdapter

        // Hide the FrameLayout holding fragments
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        frameLayout.visibility = View.GONE
    }

    private fun showHomeFragment() {
        // Create or retrieve the HomeFragment
        if (supportFragmentManager.findFragmentByTag("homeFragment") == null) {
            homeFragment = HomeFragment()
        }

        // Show the HomeFragment in the FrameLayout
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, homeFragment, "homeFragment")
            .commit()

        // Hide the RecyclerView
        subjectRecyclerView.visibility = View.GONE

        // Show the FrameLayout holding fragments
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        frameLayout.visibility = View.VISIBLE
    }

    private fun showProfileFragment() {
        // Create or retrieve the ProfileFragment
        if (supportFragmentManager.findFragmentByTag("profileFragment") == null) {
            profileFragment = ProfileFragment()
        }

        // Show the ProfileFragment in the FrameLayout
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, profileFragment, "profileFragment")
            .commit()

        subjectRecyclerView.visibility = View.GONE

        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        frameLayout.visibility = View.VISIBLE
    }

    private fun updateUI(subjectList: List<Subject>) {
        subjectAdapter = SubjectAdapter(subjectList as MutableList<Subject>)
        subjectRecyclerView.adapter = subjectAdapter
    }

    override fun onSubjectEntered(subjectText: String) {
        if (subjectText.isNotEmpty()) {
            val subject = Subject(0, subjectText)
            // Stop updateUI() from being called
            loadSubjectList = false

            subjectListViewModel.addSubject(subject)

            // Add subject to RecyclerView
            subjectAdapter.addSubject(subject)
            Toast.makeText(this, "Added $subjectText", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class SubjectHolder(inflater: LayoutInflater, parent: ViewGroup?) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_view_items, parent, false)),
        View.OnLongClickListener,

        View.OnClickListener {

        private var subject: Subject? = null
        private val subjectTextView: TextView

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            subjectTextView = itemView.findViewById(R.id.subject_text_view)
        }

        fun bind(subject: Subject, position: Int) {
            this.subject = subject
            subjectTextView.text = subject.text
            if (selectedSubjectPosition == position) {
                // Make selected subject stand out
                subjectTextView.setBackgroundColor(Color.RED)
            }
            else {
                // Make the background color dependent on the length of the subject string
                val colorIndex = subject.text.length % subjectColors.size
                subjectTextView.setBackgroundColor(subjectColors[colorIndex])
            }
        }

        override fun onLongClick(view: View): Boolean {
            if (actionMode != null) {
                return false
            }
            selectedSubject = subject!!
            selectedSubjectPosition = absoluteAdapterPosition

            // Re-bind the selected item
            subjectAdapter.notifyItemChanged(selectedSubjectPosition)

            // Show the CAB
            actionMode = this@SubjectActivity.startActionMode(actionModeCallback)
            return true
        }



        override fun onClick(view: View) {
            // Start QuestionActivity with the selected subject
            val intent = Intent(this@SubjectActivity, QuestionActivity::class.java)
            intent.putExtra(QuestionActivity.EXTRA_SUBJECT_ID, subject!!.id)
            intent.putExtra(QuestionActivity.EXTRA_SUBJECT_TEXT, subject!!.text)

            startActivity(intent)
        }
    }

    private val actionModeCallback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Provide context menu for CAB
            val inflater = mode.menuInflater
            inflater.inflate(R.menu.context_menu, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            if (item.itemId == R.id.delete) {
                // Stop updateUI() from being called
                loadSubjectList = false

                // Delete from ViewModel
                subjectListViewModel.deleteSubject(selectedSubject)

                // Remove from RecyclerView
                subjectAdapter.removeSubject(selectedSubject)

                // Close the CAB
                mode.finish()
                return true
            }

            return false
        }

        override fun onDestroyActionMode(mode: ActionMode) {
            actionMode = null

            // CAB closing, need to deselect item if not deleted
            subjectAdapter.notifyItemChanged(selectedSubjectPosition)
            selectedSubjectPosition = RecyclerView.NO_POSITION
        }
    }

    private inner class SubjectAdapter(private val subjectList: MutableList<Subject>) :
        RecyclerView.Adapter<SubjectHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder {
            val layoutInflater = LayoutInflater.from(applicationContext)
            return SubjectHolder(layoutInflater, parent)
        }

        override fun onBindViewHolder(holder: SubjectHolder, position: Int) {
            holder.bind(subjectList[position], position)
        }

        override fun getItemCount(): Int {
            return subjectList.size
        }
        fun addSubject(subject: Subject) {

            // Add the new subject at the beginning of the list
            subjectList.add(0, subject)

            // Notify the adapter that item was added to the beginning of the list
            notifyItemInserted(0)

            // Scroll to the top
            subjectRecyclerView.scrollToPosition(0)
        }
        fun removeSubject(subject: Subject) {

            // Find subject in the list
            val index = subjectList.indexOf(subject)
            if (index >= 0) {

                // Remove the subject
                subjectList.removeAt(index)

                // Notify adapter of subject removal
                notifyItemRemoved(index)
            }
        }
    }


    // methods to control the operations that will
    // happen when user clicks on the action buttons
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.search -> {
//                Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show()
//                return true
//            }
//
//            R.id.refresh -> {
//                Toast.makeText(this, "Refresh Clicked", Toast.LENGTH_SHORT).show()
//                return true
//            }
//
//            R.id.more -> {
//                Toast.makeText(this, "Copy Clicked", Toast.LENGTH_SHORT).show()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

}
