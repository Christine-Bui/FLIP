package com.flip.flashcards

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

//import kotlin.android.synthetic.main.

class NewCardAdapter: RecyclerView.Adapter<NewCardAdapter.CardViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.findViewById(R.id.front_card_text)
        val textView2: TextView = itemView.findViewById(R.id.back_card_text)
        val textInput1: TextInputEditText = itemView.findViewById(R.id.front_card_text_field)
        val textInput2: TextInputEditText = itemView.findViewById(R.id.back_card_text_field)





    }
}