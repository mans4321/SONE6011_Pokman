package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import model.Card.EnegeryCategory;
import model.ability.AbilityCard;

public class LoadingDeck {

	
	private Deck player_Deck ;
	private Deck AI_Deck;
	private AbilityFile abilityFile;
	
    // Singleton
    private static LoadingDeck instance ;
    public static LoadingDeck sharedInstance() {
    	if (instance == null)
    		instance = new LoadingDeck();
        return instance;
    }
    
    private LoadingDeck(){
    	player_Deck = new Deck();
    	AI_Deck = new Deck();
    	abilityFile = AbilityFile.sharedInstance();
    	ReadDecksFromFile();
    }
    
	public  void ReadDecksFromFile(){
		CreateStupDecks(AI_Deck,"deck1.txt");
		CreateStupDecks(player_Deck,"deck2.txt");
		int num = 0;
//		for(Card card : AI_Deck.getDeck()){
//			System.out.println(card.getType().toString());
//			num++;
//		}
//		System.out.println(num);
	}
	
	public Deck assignDecks(boolean player){
		if(player)
			return player_Deck;
		return AI_Deck;
			
	}
	
	/**
	 * create the deck denpends on different player.
	 * @param deck
	 * @param deck_file
	 */
	private void CreateStupDecks(Deck deck, String deck_file) {
		// TODO Auto-generated method stub
			try {
				List<String> numbers = new ArrayList<String>();
				Scanner deckFile = new Scanner(new File(deck_file));
				while (deckFile.hasNextLine()) {
					numbers.add(deckFile.nextLine());
				}
				for(String liString : numbers) {
					int eachNum = Integer.parseInt(liString);
					String aString = Files.readAllLines(Paths.get("cards.txt")).get(eachNum-1);
					if ((!aString.equals(""))&&(!aString.equals("#"))) {
						String[] getElement = aString.split(":");
						if (getElement[1].equalsIgnoreCase("energy")) {
							createEnergyCard(deck, aString);	
						}else if (getElement[1].equalsIgnoreCase("pokemon")) {
							createPokemonCard(deck, aString);
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("There is no file :" + e.getMessage());
			}
	}

	/**
	 * create a pokemon card
	 * @param deck ( player or AI)
	 * @param aString = Each line in the card file denpends on "pokemon" type
	 */
	private void createPokemonCard(Deck deck, String aString) {

		String[] pokemonInfoArr = aString.split(":");

		/**
		 * getting name ready
		 */
		String nameString = pokemonInfoArr[0];

		/**
		 * getting the pokemon type ready
		 * getting the evolved from pokemon name if any
		 * BASIC , STAGEONE
		 */
		Card.PokemonCategory p_cat = null;
		String evolvedFrom;
		String pokemon_cat = pokemonInfoArr[3];
		switch (pokemon_cat) {
			case "basic":
				p_cat = Card.PokemonCategory.BASIC;
				evolvedFrom = null;
				break;
			case "stage-one":
				p_cat = Card.PokemonCategory.STAGEONE;
				evolvedFrom = pokemonInfoArr[4];
				break;
			default:
				throw new IllegalArgumentException("can not find pokemon category");
		}

		/**
		* getting retreat num
		*/
		int retreat;
		if (aString.indexOf("retreat") == -1){
			retreat = 0;
		}else {
			String retreatString = aString.substring(aString.indexOf("retreat"));
			retreat = Integer.parseInt(retreatString.split(":")[3]);
		}

		/**
		 * getting the HP
		 */
		int hp = setHp(pokemonInfoArr);

		/**
		 * getting abilities ready
		 */
		ArrayList<AbilityCard> abilities = new ArrayList<AbilityCard>();
		String abilityString = aString.substring( aString.indexOf("attacks"));
		abilityString = removeFromString("attacks:",abilityString);
		int line_num;
		if (abilityString.indexOf(",") == -1) {
			line_num = Integer.parseInt(abilityString.split(":")[3]);
			if(abilityFile.getAbilityHashMap().get(line_num) == null){
				replacePokemonWithEnergy(deck);
				return ;
			}
			AbilityCard abilityCard = abilityFile.getAbilityHashMap().get(line_num);
			abilityCard.setNumOfEnergyCard(Integer.parseInt(abilityString.split(":")[2]));
			abilityCard.setCat(setEnergyType(abilityString.split(":")[1]));
			abilities.add(abilityCard);
		} else {
			String[] abilityStrings = abilityString.split(",");
			for (int index = 0; index < abilityStrings.length; index++) {
				line_num = Integer.parseInt(abilityStrings[index].split(":")[3]);
				if(abilityFile.getAbilityHashMap().get(line_num) == null){
					replacePokemonWithEnergy(deck);
					return;
				}
				AbilityCard abilityCard = abilityFile.getAbilityHashMap().get(line_num);
				abilities.add(abilityCard);
			}
		
			
		}

		/**
		 * making up the pokemon
		 * setting retreat num
		 * adding this pokemon to the deck
		 */
		Pokemon pokemon = new Pokemon(nameString, hp, p_cat, abilities);
		pokemon.setRetreat(retreat);
		pokemon.setEvolvedFrom(evolvedFrom);

		deck.addCard(pokemon);
	}

	private void replacePokemonWithEnergy(Deck deck) {
		Energy energy = new Energy("replacingPokemon",Card.CardType.ENERGY, Card.EnegeryCategory.COLORLESS);
		deck.addCard(energy);
	}

	private int setHp(String[] pokemonInfoArr){
		if (pokemonInfoArr[0].equals("Frogadier") || pokemonInfoArr[0].equals("Raichu") || 
				pokemonInfoArr[0].equals("Seaking") || pokemonInfoArr[0].equals("Swanna") ){
			return Integer.parseInt(pokemonInfoArr[7]) ;
		}else{

		int num = 0 ;
		for(int i = 0 ; i < pokemonInfoArr.length ; i++){
			if(pokemonInfoArr[i].equals("retreat")){
				num = i;
				break;
			}
		}
		
//		buildString(pokemonInfoArr);
			return Integer.parseInt(pokemonInfoArr[num-1]) ;
		}
		
	}

	private void buildString(String[] pokemonInfoArr) {
		String str = "";
		for(String string : pokemonInfoArr)
			str += ":" + string;
		System.out.println(str);
		
	}

	/**
	 * reading the energy type for the ability
	 * @param string cat:ENERGY_TYPE:num_of_energy:ability_line
	 * @return the energy type for the ability, one of the followng:
	 * 			COLORLESS ,WATER,LIGHTNING, FIGHTING ,PSYCHIC
	 *
	 */
	private Card.EnegeryCategory setEnergyType(String string){
		switch (string){
			case "colorless":
				return EnegeryCategory.COLORLESS;
			case "water":
				return EnegeryCategory.WATER;
			case "lightning":
				return EnegeryCategory.LIGHTNING;
			case "fighting":
				return EnegeryCategory.FIGHTING;
			case "psychic":
				return EnegeryCategory.PSYCHIC;
			case "fight":
				return EnegeryCategory.FIGHTING;
				
			default:
				throw new IllegalArgumentException("energy can not be found");
		}
	}





	/**
	 * create a energy card
	 * @param deck ( player or AI)
	 * @param aString = each line of card file depends on "energy" type
	 */
	private void createEnergyCard(Deck deck, String aString) {
		// TODO Auto-generated method stub
		 String[] getEnergyCard = aString.split(":");
		 EnegeryCategory category = null;
		 String engCat = getEnergyCard[3];
		 engCat.toUpperCase();
		 switch(engCat){
		 case "COLORLESS":
			 category = Card.EnegeryCategory.COLORLESS;
			 break;
		 case "WATER":
			 category = Card.EnegeryCategory.WATER;
			 break;
		 case "LIGHTNING":
			 category = Card.EnegeryCategory.LIGHTNING;
			 break;
		 case "FIGHTING":
			 category = Card.EnegeryCategory.FIGHTING;
			 break;
		 case "PSYCHIC":
			 category = Card.EnegeryCategory.PSYCHIC;
			 break;
		 }
		 Energy card  = new Energy(getEnergyCard[0], Card.CardType.ENERGY , category);
		//save to hashmap
		deck.addToHash(card);
		 deck.addCard(card);
	}

	private String removeFromString(String stringToRemove, String string) {
		return string.replaceFirst(stringToRemove,"");
	}


}
