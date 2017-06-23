package model;


public abstract class Card {
	
	
	public static enum CardType{POKEMON , TRAINER , ENERGY};
	public static enum PokemonCategory{BASIC , STAGEONE};
	public static enum EnegeryCategory{COLORLESS ,WATER,LIGHTNING, FIGHTING ,PSYCHIC};
	public static enum TrainerCategory{STADIUM ,SUPPORTER ,ITEM};
	public static enum PokemonStatus{PARALYZED, ASLEEP, STUCK, POISONED, NONE};
	
	 
	protected  String name ;
	protected  CardType type;
	
	public  String toString(){
		return " Name: " + name + "\n Type: " +type.toString();
	}
	

	/*
	 * construct for TRAINER and  ENERGY
	 */
	public Card(String name ,  CardType type){
		this.name = name ;
		this.type = type ;
	}
	
	//-----------------------------Setter & getter -------------------------------------------
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CardType getType() {
		return type;
	}
	
	public void setType(CardType type) {
		this.type = type;
	}

}
