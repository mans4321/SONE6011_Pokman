package model.ability;

import control.Coordinator;

public class Deenergize implements Ability {

	private int amount ;
	private boolean isPlayer;
	private Coordinator coordinator;
	private AbilityCard.TRAGET traget ;
	private Count count;
	
	public Deenergize(AbilityCard.TRAGET traget , int amount){
		 this.traget = traget;
		 this.amount = amount;
		 count = null;
	}
	
	public Deenergize(AbilityCard.TRAGET traget , Count count){
		 this.traget = traget;
		 this.count = count;
	}
	
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		if(count != null)
			calculateDamge();
		return abilityVisitor.activityability(this);
	}

	private void calculateDamge() {
		amount = count.getCount(coordinator, isPlayer);
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
