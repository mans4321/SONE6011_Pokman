package model;

import java.util.ArrayList;

import model.ability.AbilityCard;


public class Pokemon extends Card {

	//attributes for pokemon 
	private int hp ;
	private int damage; 
	private PokemonCategory P_Type;
	private ArrayList <AbilityCard> abilityCards;
	private int numOfENERGYCard; //TODO to be remove 
	private PokemonStatus status;
	private ArrayList<Energy> energyCards;
	private int lastDamage;
	private boolean hasHeald = false;
	private int retreat;
	private String evolvedFrom ;
	
	/*
	 * construct for pokmon
	 */
	public Pokemon(String name , int hp , PokemonCategory P_Type, ArrayList <AbilityCard> abilityCards){
		super(name , Card.CardType.POKEMON);
		this.hp = hp;
		this.P_Type = P_Type;
		this.abilityCards = abilityCards;
		damage = hp; 
		numOfENERGYCard = 0;
		status = PokemonStatus.NONE;
		energyCards = new ArrayList<Energy>();
	}

	public void  gotHit(int damageReceived){
		damage = damage - damageReceived;
	}
	
	/*
	 * true is alive 
	 */
	public boolean isPokmonAlive(){
		   return (damage  >= 1);
	}
	
	public void healPokemon(int heal){
		hasHeald = true;
		damage = damage + heal;
	}
	/*
	 * hit the Pokmon and check if the pokmon is still alive
	 * true is alive 
	 */
	public  boolean hitPokmon(int amount ){
		lastDamage = amount;
		damage = damage - amount;
		return isPokmonAlive();
	}

	public boolean canPokemonRetreat(){
		if(status.equals(PokemonStatus.STUCK) || status.equals(PokemonStatus.PARALYZED))
			return false;
		else if(status.equals(PokemonStatus.ASLEEP))
			new Coin().isSuccess();
			return true;
	}
	public boolean isPokemonPoisoned(){
		return status.equals(PokemonStatus.STUCK);
	}
	
	public boolean canPokemonAttack(){
		if(status.equals(PokemonStatus.PARALYZED) || status.equals(PokemonStatus.ASLEEP) )
			return false;
		return true;
	}
	
	public void removestatus(){
		status = PokemonStatus.NONE;
	}
	
	public void deenergize(int num){
		for(int i = 0 ; i < num; i++ ){
			if(energyCards.size() > 0)
				energyCards.remove(0);
		}
	}
	
	
	public String toString() {//TODO to be fix
		String info = "";
		
		info += " Name: " + getName() + "\n Type: " +type.toString();
		info += "\n HP: " + hp + "\n P_Type " + P_Type.toString();
		info += "\n numOfENERGYCard: " + numOfENERGYCard;
		info += "\n damage: " + damage ;
		info +="\n-------------\n";
//		for(int i = 0 ; i < attacks.size() ; i++){
//			info += " Ability:  " + (i+1);
//			info += "\n Name: " + abilityCards.get(i).getAttacksName();
//			info +="\n Damage: " + abilityCards.get(i).getDamage();
//			info +="\n NumOfEnergyCard " +  attacks.get(i).getNumOfEnergyCard();
//			info +="\n-------------\n";
//		}
			return info;
	}
	
	public boolean isThereEnoughEnergyToActivateAbility(Card.EnegeryCategory eneryCategory, int num){
		int count = 0 ;
		for(Energy energy : energyCards)
			if(energy.getCategory().equals(eneryCategory))
				count++;
		if(count >=num)
			return true;
		return false;
	}
	

	public void chargePokemonForRetreasting(int retreat2) {
		int count = 0;
		for(int i =0 ; i < energyCards.size() ; i++){
			if(energyCards.get(i).getCategory().equals(Card.EnegeryCategory.COLORLESS)){
				if(count < retreat2){
					count++;
					energyCards.remove(i);
				}
			}
		}
		
	}	
	

		public int getNumOfENERGYCard() {
			return numOfENERGYCard;
		}

		public void setNumOfENERGYCard(int numOfENERGYCard) {
			this.numOfENERGYCard = numOfENERGYCard;
		}

		public int getHp() {
			return hp;
		}

		public void setHp(int hp) {
			this.hp = hp;
		}

		public int getDamage() {
			return damage;
		}

		public void setDamage(int damage) {
			this.damage = damage;
		}

		public PokemonCategory getP_Type() {
			return P_Type;
		}

		public void setP_Type(PokemonCategory p_Type) {
			P_Type = p_Type;
		}

		public ArrayList<AbilityCard> getAbilityCards() {
			return abilityCards;
		}

		public void setAbilityCards(ArrayList<AbilityCard> abilityCard) {
			this.abilityCards = abilityCard;
		}

		public PokemonStatus getStatus() {
			return status;
		}

		public void setStatus(PokemonStatus status) {
			this.status = status;
		}

		public ArrayList<Energy> getEnergyCards() {
			return energyCards;
		}
		
		public void addEnergyCard(Energy energy){
			energyCards.add(energy);
		}

		public ArrayList<Energy> reenergizeFrom() {
			ArrayList<Energy> reenergize = energyCards;
			energyCards = new ArrayList<Energy>();
			return reenergize;
		}

		public void reenergizeTO(ArrayList<Energy> moveEnergy) {
			for(Energy energy :moveEnergy )
				energyCards.add(energy);
		}

		public int getLastDamage() {
			return lastDamage;
		}

		public int getRetreat() {
			return retreat;
		}

		public void setRetreat(int retreat) {
			this.retreat = retreat;
		}

		public boolean isHasHeald() {
			return hasHeald;
		}

		public void setEvolvedFrom(String evolvedFrom) {
			this.evolvedFrom = evolvedFrom;
		}
}
