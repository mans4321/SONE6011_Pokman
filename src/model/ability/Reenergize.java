package model.ability;

import control.Coordinator;
import model.Pokemon;
//TODO make screen to pick pokemon 
public class Reenergize implements Ability {
	
	private boolean isPlayer;
	private Coordinator coordinator;
	private AbilityCard.TRAGET traget ;
	private AbilityCard.TRAGET source ;
	private Pokemon sourcePokemon ;
	private Pokemon tragetPokemon;
	// private creatView and getInput 
	
	
	//reenergize:[target:source]:[target:destination]:[amount] amount ??
	public Reenergize(AbilityCard.TRAGET source , AbilityCard.TRAGET traget){
		this.traget = traget;
		this.source = source;
	}
			
	
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		// view to pick pokemons (what if trget has no energy)
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		return abilityVisitor.activityability(this);
	}


	public Pokemon getSourcePokemon() {
		return sourcePokemon;
	}
	public Pokemon getTragetPokemon() {
		return tragetPokemon;
	}
}
