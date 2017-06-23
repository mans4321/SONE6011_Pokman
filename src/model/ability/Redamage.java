package model.ability;

import control.Coordinator;
import model.Pokemon;
//TODO screen to Pick 
public class Redamage implements Ability {
	
	private boolean isPlayer;
	private Coordinator coordinator;
	private int damage;
	private AbilityCard.TRAGET traget ;
	private AbilityCard.TRAGET source ;
	private Pokemon sourcePokemon ;
	private Pokemon tragetPokemon;
	private Count count ;
	//Ear Influence:redamage:target:opponent:target:opponent:count(target:last:source:damage)
	
	public Redamage(AbilityCard.TRAGET source ,AbilityCard.TRAGET traget , Count count ){
		this.source  = source ; 
		this.traget = traget;
		this.count = count;
	}
	
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		//TODO screen to Pick 
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		if(count != null)
			calculateDamge();
		return abilityVisitor.activityability(this);
	}
	
	private void calculateDamge() {//TODO make sure 
		damage = count.getCount(sourcePokemon);
	}

	
	
	public int getDamage() {
		return damage;
	}

	public Pokemon getSourcePokemon() {
		return sourcePokemon;
	}

	public Pokemon getTragetPokemon() {
		return tragetPokemon;
	}
	
	
}
