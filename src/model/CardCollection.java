package model;


/**
 * An object of type cardCollection represents a cardCollection of cards.  The
 * cards belong to the class Card.  A cardCollection is empty when it
 * is created, and any number of cards can be added to it.
 */
import java.util.ArrayList;

import model.ability.Search;
import model.ability.Search.FILTER;

public class CardCollection<T extends Card> {

    private ArrayList<T> cardCollection;   // The cards in the cardCollection.
    private boolean player = false; 

    /**
     * Create a cardCollection that is initially empty.
     */
    public CardCollection() {
        cardCollection = new ArrayList<T>();
    }

    /**
     * Remove all cards from the cardCollection, leaving it empty.
     */
    public void clear() {
        cardCollection.clear();
    }

    /**
     * Add a card to the cardCollection.  It is added at the end of the current cardCollection.
     * @param c the non-null card to be added.
     * @throws NullPointerException if the parameter c is null.
     */
    public void addCard(T c) {
        if (c == null)
            throw new NullPointerException("Can't add a null card to a cardCollection.");
        cardCollection.add(c);
    }

    /**
     * Remove a card from the cardCollection, if present.
     * @param c the card to be removed.  If c is null or if the card is not in 
     * the cardCollection, then nothing is done.
     */
    public void removeCard(T card) {
        cardCollection.remove(card);
    }

    /**
     * Remove the card in a specified position from the cardCollection.
     * @param position the position of the card that is to be removed, where
     * positions are starting from zero.
     * @throws IllegalArgumentException if the position does not exist in
     * the cardCollection, that is if the position is less than 0 or greater than
     * or equal to the number of cards in the cardCollection.
     */
    public void removeCard(int position) {
        if (position < 0 || position >= cardCollection.size())
            throw new IllegalArgumentException("Position does not exist in cardCollection: "
                    + position);
        cardCollection.remove(position);
    }

    
    public ArrayList<Card> filiterCardBaseOnType(FILTER filter) {
		ArrayList<Card> cards = new ArrayList<Card>();
		if(filter.equals(Search.FILTER.BASICPOKEMON)){
			for(Card card : cardCollection){
				if(card.getType().equals(Card.CardType.POKEMON)){
					Pokemon pokemon = (Pokemon) card;
					if(pokemon.getP_Type().equals(Card.PokemonCategory.BASIC))
						cards.add(card);
				}
			}
			
		}else if(filter.equals(Search.FILTER.ENERGY)){
			for(Card card : cardCollection){
				if(card.getType().equals(Card.CardType.ENERGY)){
						cards.add(card);
				}
			}
			
		}else if(filter.equals(Search.FILTER.ITEM)){
			for(Card card : cardCollection){
				if(card.getType().equals(Card.CardType.TRAINER)){
					Trainer trainer = (Trainer) card;
					if(trainer.trainerCat.equals(Card.TrainerCategory.ITEM))
						cards.add(card);
				}
		}
			
		}else if (filter.equals(Search.FILTER.POKEMON)){
			for(Card card : cardCollection){
				if(card.getType().equals(Card.CardType.POKEMON)){
					cards.add(card);
				}
			}
		}
		return cards;
	} 
    
    
    /**
     * Returns the number of cards in the cardCollection.
     */
    public int getCardCount() {
        return cardCollection.size();
    }

    /**
     * Gets the card in a specified position in the cardCollection.  (Note that this card
     * is not removed from the cardCollection!)
     * @param position the position of the card that is to be returned
     * @throws IllegalArgumentException if position does not exist in the cardCollection
     */
    public  T getCard(int position) {
        if (position < 0 || position >= cardCollection.size())
            throw new IllegalArgumentException("Position does not exist in cardCollection: "
                    + position);
        return cardCollection.get(position);
    }
    
    public  T getCard(T card) {
    	int index = cardCollection.indexOf(card);
        return cardCollection.get(index);
    }
    
    public void setCardAtPosition(int index , T card){
    	cardCollection.set(index, card);
    }

	public boolean isPlayer() {
		return player;
	}

	public void setPlayer(boolean player) {
		this.player = player;
	}
    
	public ArrayList<T> getArrayList(){
		return cardCollection;
	}
    
}
