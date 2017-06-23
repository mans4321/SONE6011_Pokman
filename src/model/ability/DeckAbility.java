package model.ability;

import control.Coordinator;

public class DeckAbility implements Ability {
	private AbilityCard.TRAGET target ;
	public static enum DESTINATION{DISCARD, DECK};
	private Count count ;
	private int amount ;
	private DESTINATION destination;
	private boolean isPlayer;
	private Coordinator coordinator;

	public DeckAbility(AbilityCard.TRAGET target, DESTINATION destination , Count count){
		this.target = target ;
		this.destination = destination;
		this.count = count;
	}
	
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		if(count != null)
			calculateAmount();
		return abilityVisitor.activityability(this);
	}

	private void calculateAmount() {
		amount = count.getCount(coordinator, isPlayer);
		
	}

	public AbilityCard.TRAGET getTarget() {
		return target;
	}

	public int getAmount() {
		return amount;
	}

	public DESTINATION getDestination() {
		return destination;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public Coordinator getCoordinator() {
		return coordinator;
	}

	public void setCoordinator(Coordinator coordinator) {
		this.coordinator = coordinator;
	}

	
}
