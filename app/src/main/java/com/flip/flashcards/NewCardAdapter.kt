package com.flip.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

//import kotlin.android.synthetic.main.

class NewCardAdapter(private val exampleList: List<CardModel>): RecyclerView.Adapter<NewCardAdapter.CardViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val cardView = LayoutInflater.from(parent.context).inflate(R.layout.new_card_input, parent, false)


        return CardViewHolder(cardView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = exampleList[position]
        //
    }

    override fun getItemCount() = exampleList.size

    class CardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.findViewById(R.id.front_card_text)
        val textView2: TextView = itemView.findViewById(R.id.back_card_text)
        val textInput1: TextInputEditText = itemView.findViewById(R.id.front_card_text_field)
        val textInput2: TextInputEditText = itemView.findViewById(R.id.back_card_text_field)





    }
}