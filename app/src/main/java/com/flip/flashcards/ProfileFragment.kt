package com.flip.flashcards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.flip.flashcards.model.Question
import com.flip.flashcards.model.Subject
import com.flip.flashcards.viewmodel.QuestionListViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.animation.AnimationUtils
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class ProfileFragment : Fragment() {


    private lateinit var settingsButton: Button
    //private lateinit var aboutButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)




        //settingsButton = find

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonOpenWebsite = view.findViewById<Button>(R.id.about)
        buttonOpenWebsite.setOnClickListener {
            val websiteUrl = "https://github.com/Christine-Bui/FLIP"
            openWebsite(websiteUrl)
        }

        val buttonSettings = view.findViewById<Button>(R.id.settings)
        buttonSettings.setOnClickListener {
            val intent = Intent(requireContext(), SettingsActivity::class.java)
            startActivity(intent)
        }
    }


    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}

