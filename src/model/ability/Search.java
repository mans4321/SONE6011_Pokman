package model.ability;

import control.Coordinator;
import model.Card;

public class Search implements Ability {

	
    public static enum SOURCE{DISCARD, DECK};
    public static enum FILTER {TOP,ENERGY,NOUN,ITEM,BASICPOKEMON,POKEMON};
    
	private boolean isPlayer;
	private Coordinator coordinator;
	private AbilityCard.TRAGET traget ;
	private int amount ;
	private SOURCE source;
	private FILTER filter;
	private int filterAmount;

    
	public Search(AbilityCard.TRAGET traget , SOURCE source ,FILTER filter, int filterAmount, int amount ){
		this.amount = amount ;
		this.traget = traget;
		this.source = source;
		this.filter= filter;
		this.filterAmount = filterAmount;
	}
	
	public Search(AbilityCard.TRAGET traget , SOURCE source ,FILTER filter, int amount ){
		this.amount = amount ;
		this.traget = traget;
		this.source = source;
		this.filter= filter;
		this.filterAmount = 0;
	}
	 
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		// view to pick pokemons (what if trget has no energy)
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

	public AbilityCard.TRAGET getTraget() {
		return traget;
	}

	public int getAmount() {
		return amount;
	}

	public SOURCE getSource() {
		return source;
	}

	public FILTER getFilter() {
		return filter;
	}

	public int getFilterAmount() {
		return filterAmount;
	}

	
}
