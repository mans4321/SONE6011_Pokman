package model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import model.Card.PokmonType;

public class StupLoadDecks {

	
	private Deck player_Deck ;
	private Deck AI_Deck;
	
    // Singleton
    private static StupLoadDecks instance = new StupLoadDecks();
    public static StupLoadDecks sharedInstance() {
    	if (instance == null)
    		instance = new StupLoadDecks();
        return instance;
    }
    
    private StupLoadDecks(){
    	player_Deck = new Deck();
    	AI_Deck = new Deck();
    	ReadDecksFromFile();
    }
    
	public  void ReadDecksFromFile(){
		CreateStupDecks();
	}
	
	public Deck assignDecks(boolean player){
		if(player)
			return player_Deck;
		return AI_Deck;
			
	}

	private void CreateStupDecks(){
		
		 int rand ;
		 
		 for(int i = 0 ; i < 4; i++){
			 Energy card  = new Energy("Energy", Card.CardType.ENERGY );
			 player_Deck.addCard(card);
			 AI_Deck.addCard(card);
		 }
		 
     	  ArrayList<Attack> attcks = new ArrayList<Attack>(2);
    	  rand = ThreadLocalRandom.current().nextInt(10, 20);
    	  
    	  Attack attack = new Attack("Hit1", rand, 1);
    	  attcks.add(attack);
    	  rand = ThreadLocalRandom.current().nextInt(20, 40);
    	  attack = new  Attack("Hit2" , rand, 2);
    	  attcks.add(attack);
    	  
              for ( int value = 0; value < 10; value++ ) { 
            	  rand = ThreadLocalRandom.current().nextInt(40, 60);
                  Pokemon card = new Pokemon("Pokemon"+value, rand , PokmonType.LEVEL1, attcks );
     			  player_Deck.addCard(card);
     			  AI_Deck.addCard(card);
              }
	}
}
