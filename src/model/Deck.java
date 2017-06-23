package model;

import java.util.ArrayList;
import java.util.HashMap;

import model.ability.Search;
import model.ability.Search.FILTER;


/**
 *  An object of type Deck represents a deck of playing cards.
 */
public class Deck {
	public HashMap<Integer, Card> hm = new HashMap<Integer, Card>();
	
	int hashkey = 0;
	
	public void addToHash (Card card) {
		hm.put(hashkey, card);
		hashkey++;
	}
	
    /**
     * An array of 60 cards. 
     */
    private ArrayList<Card> deck;
    

    /**
     * Keeps track of the number of cards that have been dealt from
     * the deck so far.
     */
    private int cardsUsed;

    /**
     * Constructs a 
 	The shuffle() method can be called to
     * randomize the order.  (Note that "new Deck()" is equivalent
     * to "new Deck(false)".)
     */
    public Deck() {
    	deck = new ArrayList<Card>(60);
    	cardsUsed = 0;
    }

    
    public void addCard(Card card){
    	if(deck.size() > 60)
    		 throw new IllegalArgumentException("Deck only contain 60 This card you are adding is number" + deck.size());
    	deck.add(card);
 
    }
    /**
     * Put all the used cards back into the deck (if any), and
     * shuffle the deck into a random order.
     */
    public void shuffle() {
        for ( int i = deck.size()-1; i > 0; i-- ) {
            int rand = (int)(Math.random()*(i+1));
            Card temp = deck.get(i);
            deck.set(i, deck.get(rand));
            deck.set(rand, temp);
        }
    }

    /**
     * As cards are dealt from the deck, the number of cards left
     * decreases.  This function returns the number of cards that
     * are still left in the deck.
     */
    public int cardsLeft() {
        return deck.size();
    }

    /**
     * Removes the next card from the deck and return it.  It is illegal
     * to call this method if there are no more cards in the deck.  You can
     * check the number of cards remaining by calling the cardsLeft() function.
     * @return the card which is removed from the deck.
     * @throws IllegalStateException if there are no cards left in the deck
     */
    public Card dealCard() {
        if (deck.size() == 0)
            throw new IllegalStateException("No cards are left in the deck.");
        cardsUsed++;
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
    
    public ArrayList<Card> getTopCard(int amount){
    	ArrayList<Card> cards = new ArrayList<Card>();
    	for(int i = 0 ; i < amount ; i++)
    		cards.add(deck.get(i));
    	return cards;
    }
    public int getCardsUsed(){return cardsUsed;}


	public ArrayList<Card> filiterCardBaseOnType(FILTER filter) {
		ArrayList<Card> cards = new ArrayList<Card>();
		if(filter.equals(Search.FILTER.BASICPOKEMON)){
			for(Card card : deck){
				if(card.getType().equals(Card.CardType.POKEMON)){
					Pokemon pokemon = (Pokemon) card;
					if(pokemon.getP_Type().equals(Card.PokemonCategory.BASIC))
						cards.add(card);
				}
			}
			
		}else if(filter.equals(Search.FILTER.ENERGY)){
			for(Card card : deck){
				if(card.getType().equals(Card.CardType.ENERGY)){
						cards.add(card);
				}
			}
			
		}else if(filter.equals(Search.FILTER.ITEM)){
			for(Card card : deck){
				if(card.getType().equals(Card.CardType.TRAINER)){
					Trainer trainer = (Trainer) card;
					if(trainer.trainerCat.equals(Card.TrainerCategory.ITEM))
						cards.add(card);
				}
		}
			
		}else if (filter.equals(Search.FILTER.POKEMON)){
			for(Card card : deck){
				if(card.getType().equals(Card.CardType.POKEMON)){
					cards.add(card);
				}
			}
		}
		return cards;
	}


	public void removeCard(Card card) {
		deck.remove(card);
	} 
	
}
