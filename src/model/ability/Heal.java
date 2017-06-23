package model.ability;

import control.Coordinator;
import model.Pokemon;

public class Heal implements Ability {

	private int amount ;
	private boolean isPlayer;
	private Coordinator coordinator;
	private AbilityCard.TRAGET traget ;
	private Pokemon pokemon ;
	
	public Heal(AbilityCard.TRAGET traget , int amount){
		 this.traget = traget;
		 this.amount = amount;
	}
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		if(traget.equals(AbilityCard.TRAGET.CHOCICEYOUR))
			dealWithPokemonSlection();
		return abilityVisitor.activityability(this);
	}

//TODO create the frame to selsect the pokemon 
	private void dealWithPokemonSlection(){
		
	}
	
	
	public Pokemon getPokemon() {
		return pokemon;
	}
	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}
	public int getAmount() {
		return amount;
	}
	public boolean isPlayer() {
		return isPlayer;
	}
	public Coordinator getCoordinator() {
		return coordinator;
	}
	public AbilityCard.TRAGET getTraget() {
		return traget;
	}
	
}
