package model.ability;

import java.util.ArrayList;

import control.Coordinator;

public class Cond implements Ability {

	
    public static enum CondType{FLIP,HEALED, COUNT, CHOICE};
    public static enum Operator{LESSTHAN , BIGGERTHAN, LESSOREQUAL, BIGGEROREQUAL , EQUAL, NOTEQUAL }
    
	private boolean isPlayer;
	private Coordinator coordinator;
	private CondType condType ;
	private ArrayList<Ability> abilities;
	private ArrayList<Ability> elseAbilities;
	private int num ;
	private AbilityCard.TRAGET traget;
	private AbilityVisitor abilityVisitor;
	private Count count;
	private Operator operator;
	

	
    // most 
    public Cond(CondType condType, ArrayList<Ability> abilities , ArrayList<Ability> elseAbilities ){
		this.condType = condType;
		this.abilities = abilities;
	 	this.elseAbilities = elseAbilities;
	}
    // count 
	public Cond(Count count , Operator operator, int num,  ArrayList<Ability> abilities, ArrayList<Ability> elseAbilities ){
		this.count = count ;
		condType = CondType.COUNT;
		this.num = num ;
		this.abilities = abilities;
	 	this.elseAbilities = elseAbilities;
	}
	
	//  healed
	public Cond(CondType condType ,  AbilityCard.TRAGET traget, ArrayList<Ability> abilities, ArrayList<Ability> elseAbilities){
		this.condType = condType;
		this.abilities = abilities;
		this.traget = traget;
		this.elseAbilities = elseAbilities ;

	}

	
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		this.abilityVisitor  =  abilityVisitor;
		if(this.coordinator == null)
			this.coordinator = coordinator;
		this.isPlayer = isPlayer;
		return abilityVisitor.activityability(this);
	}


	public boolean isPlayer() {
		return isPlayer;
	}
	public Coordinator getCoordinator() {
		return coordinator;
	}
	public CondType getCondType() {
		return condType;
	}
	public ArrayList<Ability> getAbilities() {
		return abilities;
	}
	public ArrayList<Ability> getElseAbilities() {
		return elseAbilities;
	}
	public int getNum() {
		return num;
	}
	public AbilityCard.TRAGET getTraget() {
		return traget;
	}
	public AbilityVisitor getAbilityVisitor() {
		return abilityVisitor;
	}
	public Count getCount() {
		return count;
	}
	public Operator getOperator() {
		return operator;
	}
	
}
