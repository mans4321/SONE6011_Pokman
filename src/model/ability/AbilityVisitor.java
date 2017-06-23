package model.ability;

public interface AbilityVisitor {
	
	public String activityability(Null nullability );
	public String activityability(Damage damager);
	public String activityability(Heal healAbility);
	public String activityability(Deenergize deenergize);
	public String activityability(Reenergize reenergize);
	public String activityability(Redamage redamage);
	public String activityability(Swap swap );
	public String activityability(Applystat swap );
	public String activityability(Draw draw );
	public String activityability(Search search );
	public String activityability(DeckAbility search );
	public String activityability(Shuffle shuffle );
	public String activityability(Cond cond );
	public String activityability(Add cond );	
}
