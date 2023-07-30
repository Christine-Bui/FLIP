package com.flip.flashcards
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flip.flashcards.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val cardSetList = ArrayList<CardSet>()
    private lateinit var recyclerView: RecyclerView // Declare recyclerView at the class level


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //when button is clicked, new_set_name fragment is shown.
        recyclerView = binding.cardSetsRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = CardSetAdapter(cardSetList)
        //when button is clicked, new_set_name fragment is shown.
        binding.newSet.setOnClickListener {
            // Your button click logic here
            //showDialog2()
            //Toast.makeText(requireContext(), "New Set Made", Toast.LENGTH_SHORT).show()

            cardSetList.add(CardSet("New Empty Card Set"))
            recyclerView.adapter?.notifyDataSetChanged()
            Toast.makeText(requireContext(),cardSetList[0].getCardSetName(), Toast.LENGTH_SHORT).show()
        }
    }


    private fun showDialog2(){
        val dialog = Dialog(requireContext())

    }
    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.new_set_name)

        val editTextCardSetName = dialog.findViewById<EditText>(R.id.editTextCardSetName)

        dialog.findViewById<Button>(R.id.buttonDone)?.setOnClickListener {
            val cardSetName = editTextCardSetName.text.toString().trim()
            if (cardSetName.isNotEmpty()) {
                // Create a new CardSet object and set the cardSetName
                val cardSet = CardSet(cardSetName)
                cardSetList.add(cardSet)
                // Notify the RecyclerView adapter that data has changed
                recyclerView?.adapter?.notifyDataSetChanged()
                // Do something with the cardSet object, for example, add it to a list
                // or save it to a database, etc.
                // For demonstration, we'll just show a Toast message
                //Toast.makeText(requireContext(), "Card set name saved: $cardSetName", Toast.LENGTH_SHORT).show()

                dialog.dismiss() // Dismiss the dialog after saving the data

            } else {
                Toast.makeText(requireContext(), "Please enter a card set name", Toast.LENGTH_SHORT).show()
            }
            }
            dialog.show()
        }
    }

