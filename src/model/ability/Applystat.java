package model.ability;

import control.Coordinator;
import model.Card;

public class Applystat implements Ability {

	
	private Card.PokemonStatus status;
	private boolean isPlayer;
	private Coordinator coordinator;
	private AbilityCard.TRAGET target; 
	
	public Applystat (Card.PokemonStatus status, AbilityCard.TRAGET target){
		this.status = status;
		this.target = target; 
	}
	
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		if(this.coordinator == null)
				this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		
		abilityVisitor.activityability(this);
		return null;
	}

	public Card.PokemonStatus getStatus() {
		return status;
	}
	public boolean isPlayer() {
		return isPlayer;
	}

	public Coordinator getCoordinator() {
		return coordinator;
	}

	public AbilityCard.TRAGET getTarget() {
		return target;
	}
	
	
}
