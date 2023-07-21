package com.flip.flashcards

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText

//import kotlin.android.synthetic.main.

class NewCardAdapter(private val cardList: List<Card>): RecyclerView.Adapter<NewCardAdapter.ExampleViewHolder>(){



//Called by RecyclerView when its time to create a new viewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExampleViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.new_card_input, parent, false)

        return ExampleViewHolder(itemView as TextInputEditText)
    }


    override fun onBindViewHolder(holder: ExampleViewHolder, position: Int) {
        TODO("Not yet implemented")
        //val currentCard = cardList[position]


    }
    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    class ExampleViewHolder(itemView: TextInputEditText) : RecyclerView.ViewHolder(itemView){
        val frontCardInput: TextInputEditText = itemView.findViewById(R.id.front_card_text_field)
        val backCardInput: TextInputEditText=itemView.findViewById(R.id.back_card_text_field)


    }
    /*
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

    */




}