package com.flip.flashcards

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.DialogInterface
import android.widget.Button

import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.flip.flashcards.databinding.FragmentHomeBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    // TODO: Rename and change types of parameters


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.card_sets)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //when button is clicked, new_set_name fragment is shown.
        binding.newSet.setOnClickListener {
            // Your button click logic here


            showDialog()
            }
        }
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.new_set_name)
        dialog.show()
    }
}
