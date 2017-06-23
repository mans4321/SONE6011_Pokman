package model.ability;

import control.Coordinator;
import model.Pokemon;
//TODO make gui to pick from bench 
public class Swap implements Ability {
	
	//Switch:swap:your-active:your:choice:bench
	private boolean isPlayer;
	private Coordinator coordinator;
	private AbilityCard.TRAGET traget ;
	private AbilityCard.TRAGET source ;
	private Pokemon sourcePokemon; 
	private Pokemon tragetPokemon ;
	
	public Swap(AbilityCard.TRAGET traget , AbilityCard.TRAGET source){
		this.traget = traget;
		this.source = source;
	}
	
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		// view to pick pokemons (what if trget has no energy)
		
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		if(isPlayer){sourcePokemon = coordinator.getAIAttackCard();}
		
		dealWithPokemonSlection();
		return abilityVisitor.activityability(this);
	}
	
	//TODO use pick from bench frame and assign sourcePokemon = active Pokemon
	private void dealWithPokemonSlection(){
		
	}
	
	
	
	public Pokemon getSourcePokemon() {
		return sourcePokemon;
	}

	public void setSourcePokemon(Pokemon sourcePokemon) {
		this.sourcePokemon = sourcePokemon;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public Coordinator getCoordinator() {
		return coordinator;
	}

	public Pokemon getTragetPokemon() {
		return tragetPokemon;
	}

}
