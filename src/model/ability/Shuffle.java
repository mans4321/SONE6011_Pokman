package model.ability;

import control.Coordinator;

public class Shuffle implements Ability {
	
	private AbilityCard.TRAGET traget ;
	private boolean isPlayer;
	private Coordinator coordinator;
	
	public Shuffle(AbilityCard.TRAGET traget){
		this.traget = traget;
	}
	
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		return abilityVisitor.activityability(this);
	}

	public AbilityCard.TRAGET getTraget() {
		return traget;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public Coordinator getCoordinator() {
		return coordinator;
	}
}
