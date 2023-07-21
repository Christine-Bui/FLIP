package com.flip.flashcards;


import java.util.ArrayList;

public class CardSet {

    ArrayList<Card> cards;
    String cardSetName;


    public CardSet(ArrayList<Card> cards, String cardSetName) {
        this.cards = cards;
        this.cardSetName = cardSetName;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getCardSetName() {
        return cardSetName;
    }

    public void setCardSetName(String cardSetName) {
        this.cardSetName = cardSetName;
    }
}
