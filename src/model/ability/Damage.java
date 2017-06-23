package model.ability;

import control.Coordinator;
import model.Pokemon;
import model.Users;

//TODO Stretch Kick:dam:target:choice:opponent-bench:30
//TODO Random Spark:dam:target:choice:opponent:30
//TODO Earthquake:dam:target:opponent-active:60,dam:target:your-bench:10

public class Damage implements Ability {

	private AbilityCard.TRAGET traget ;
	private int damage;
	private boolean isPlayer;
	private Coordinator coordinator;
	private Count count;
	private Pokemon pokemon;
	private Users.CardLocation pokeomnLocation;
	
	
	public Damage(AbilityCard.TRAGET traget , int damage){
		this.damage = damage;
		this.traget = traget;
		count = null;
	}
	
	public Damage(AbilityCard.TRAGET traget, Count count){
		this.traget = traget;
		this.count =count;
	}
	
	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		setCoordinator(coordinator);
		this.isPlayer = isPlayer; 
		
		if(traget.equals(AbilityCard.TRAGET.CHOICEOPPONENT) || 
				traget.equals(AbilityCard.TRAGET.CHOICEOPPONENTBENCH) || 
				traget.equals(AbilityCard.TRAGET.CHOICEYOURBENCH)){
			if(isPlayer)
				dealWithPokemonSlection();
			else
				selectForAI();
		}
			
		if(count != null)
			calculateDamge();//this change damage value be applying count 
		return abilityVisitor.activityability(this);
	}
	
	private void selectForAI() {
		if(traget.equals(AbilityCard.TRAGET.CHOICEOPPONENT)){
			pokemon = coordinator.getPlayerAttackCard();
			pokeomnLocation = Users.CardLocation.ATTACK;
		}else if(traget.equals(AbilityCard.TRAGET.CHOICEOPPONENTBENCH)){
			if(coordinator.getPlayerBench().getCardCount()> 0){
				pokemon = coordinator.getPlayerBench().getCard(0);
				pokeomnLocation = Users.CardLocation.BENCH;
			}
		}else{
			if(coordinator.getAIBench().getCardCount()> 0){
				pokemon = coordinator.getAIBench().getCard(0);
				pokeomnLocation = Users.CardLocation.BENCH;
			}
		}
	}

	//TODO build the the screen then fill the pokemon attribute 
	private void dealWithPokemonSlection(){
		if(traget.equals(AbilityCard.TRAGET.CHOICEOPPONENT)){
			
			//pokeomnLocation = 
		}else if(traget.equals(AbilityCard.TRAGET.CHOICEOPPONENTBENCH)){
			pokeomnLocation = Users.CardLocation.BENCH;
		}else{//traget.equals(AbilityCard.TRAGET.CHOICEYOURBENCH)
			pokeomnLocation = Users.CardLocation.BENCH;
		}
	}
	
	private void calculateDamge() {
		damage = count.getCount(coordinator, isPlayer);
	}

	public AbilityCard.TRAGET getTraget() {
		return traget;
	}

	public int getDamage() {
		return damage;
	}


	public Coordinator getCoordinator() {
		
		return coordinator;
	}

	public void setCoordinator(Coordinator coordinator) {
		if ( this.coordinator != null)
			this.coordinator = coordinator;
	}

	public boolean isPlayer() {
		return isPlayer;
	}

	public void setPlayer(boolean isPlayer) {
		this.isPlayer = isPlayer;
	}

	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public Users.CardLocation getPokeomnLocation() {
		return pokeomnLocation;
	}

	public void setPokeomnLocation(Users.CardLocation pokeomnLocation) {
		this.pokeomnLocation = pokeomnLocation;
	}
	
	
}
