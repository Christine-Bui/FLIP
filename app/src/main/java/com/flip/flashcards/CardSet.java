package com.flip.flashcards;


import java.util.ArrayList;

public class CardSetModel {

    ArrayList<CardModel> cards;
    String cardSetName;


    public CardSetModel(ArrayList<CardModel> cards, String cardSetName) {
        this.cards = cards;
        this.cardSetName = cardSetName;
    }

    public ArrayList<CardModel> getCards() {
        return cards;
    }

    public void setCards(ArrayList<CardModel> cards) {
        this.cards = cards;
    }

    public String getCardSetName() {
        return cardSetName;
    }

    public void setCardSetName(String cardSetName) {
        this.cardSetName = cardSetName;
    }
}
