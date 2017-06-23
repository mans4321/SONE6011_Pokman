package model.ability;

import control.Coordinator;

public class Draw implements Ability {

	private int amount ;
	private boolean isPlayer;
	private Coordinator coordinator;
	private AbilityCard.TRAGET traget ;
	
	public Draw(int amount){
		this.amount = amount;
		traget = null;
	}
	
	public Draw( AbilityCard.TRAGET traget, int amount){
		this.amount = amount;
		this.traget =traget ;
	}
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		return abilityVisitor.activityability(this);
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
