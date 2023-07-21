package com.flip.flashcards
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CardSetAdapter(private val cardSetList: List<CardSet>) :
    RecyclerView.Adapter<CardSetAdapter.CardSetViewHolder>() {

    // ViewHolder class for the card_set layout views
    class CardSetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.card_image)
        val textView1: TextView = itemView.findViewById(R.id.text_view_1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardSetViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_set, parent, false)
        return CardSetViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardSetViewHolder, position: Int) {
        val currentCardSet = cardSetList[position]

        // Bind the data to the card_set layout views
        holder.imageView.setImageResource(R.drawable.flashcard_icon) // Replace with your image resource ID
        holder.textView1.text = currentCardSet.getCardSetName()
    }

    override fun getItemCount() = cardSetList.size
}
