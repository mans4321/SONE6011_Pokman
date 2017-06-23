package control;

import java.util.ArrayList;

import model.Card;
import model.Pokemon;
import model.Users;
import model.ability.AbilityCard;


public class AI_Mind {

	private Coordinator coordinator;
	private Users AI_Info;
	
	public AI_Mind(Coordinator coordinator){
		this.coordinator = coordinator;
		AI_Info = new Users();
	}
	
	
	public void  play(){
		
		// do not attack and add energy or evloe  in the first round 
		if(AI_Info.isFirstTurn()){
			moveCardsToBench();
			MoveCardToAttackArea();
			setFirstTurn(false);
		}else{
			moveCardsToBench();
			if(AI_Info.getAttackCard() == null)
				MoveCardToAttackArea();
			findEnergyCardAndChargePokemon();
			tryToAttackPlayer();
		}
		endTurn();
	}
	
	
	public void moveCardsToBench(){
		Card cardToPass = null;
		for(int i = 0 ; i < AI_Info.getHand().getCardCount() ; i++){
			if(AI_Info.getHand().getCard(i).getType().equals(Card.CardType.POKEMON)){
				if(AI_Info.getBench().getCardCount() < 5){
					cardToPass = AI_Info.getHand().getCard(i);
					AI_Info.validateMovingCardToBench(AI_Info.getHand().getCard(i), Users.CardLocation.HAND);
					cardToPass = null;
					coordinator.updateCommicuntionPanel("AI has moved a card to bench");
					sleep();
				}
			}
		}
	}
	
	/**
	 * can be change with dynamic programming approach
	 */
	public void MoveCardToAttackArea(){//TODO to Be fix  
		
		Pokemon handPokemon = findPokemonInHand(); 
		Pokemon BenchPokemon = findPokemonInBench();


		if (handPokemon != null && BenchPokemon != null){
				if(handPokemon.getHp() < BenchPokemon.getHp())
					AI_Info.validateMovingCardToAttackArea(BenchPokemon , Users.CardLocation.BENCH);
				else
					AI_Info.validateMovingCardToAttackArea(handPokemon , Users.CardLocation.HAND);
		}else if(handPokemon == null  && BenchPokemon != null){
			AI_Info.validateMovingCardToAttackArea(BenchPokemon , Users.CardLocation.BENCH);
		}else if(handPokemon != null  && BenchPokemon == null){
			AI_Info.validateMovingCardToAttackArea(handPokemon , Users.CardLocation.HAND);
		}else{
			//TODO player win 
		}
		coordinator.updateAIAttackCard(); // to change the active pokemon view after having a new pokemon in the active area
	}

	private Pokemon findPokemonInHand(){
		
		Pokemon bestpokemon = null;
		Pokemon candidate = null;
		
		for (int i = 0 ; i < AI_Info.getHand().getCardCount() ; i++){
			if(AI_Info.getHand().getCard(i).getType().equals(Card.CardType.POKEMON)){
				candidate = (Pokemon) AI_Info.getHand().getCard(i);
				if(bestpokemon == null){
					bestpokemon =  candidate;
					candidate =null;
				}else{
					if(bestpokemon.getHp() < candidate.getHp()){
						bestpokemon = null;
						bestpokemon = candidate;
						candidate =null;
				}
			}
		}
	}
		return bestpokemon;
	}
	

	private Pokemon findPokemonInBench(){
		
		Pokemon bestpokemon = null;
		Pokemon candidate = null;
		
		for (int i = 0 ; i < AI_Info.getBench().getCardCount() ; i++){
			if(AI_Info.getBench().getCard(i).getType().equals(Card.CardType.POKEMON)){
				candidate = (Pokemon) AI_Info.getBench().getCard(i);
				if(bestpokemon == null){
					bestpokemon =  candidate;
					candidate =null;
				}else{
					if(bestpokemon.getHp() < candidate.getHp()){
						bestpokemon = null;
						bestpokemon = candidate;
						candidate =null;
				}
			}
		}
	}
		return bestpokemon;
	}
	
	public void findEnergyCardAndChargePokemon(){
		
		Card energyCard = null ;
		String message = "";
		for(int i = 0 ; i < AI_Info.getHand().getCardCount() ; i ++){
			if(AI_Info.getHand().getCard(i).getType().equals(Card.CardType.ENERGY)){
				energyCard = AI_Info.getHand().getCard(i);
				break; 
			}
		}
		
		if (energyCard != null){
			
			if(AI_Info.getAttackCard() != null && !hasFullEnergyCard(AI_Info.getAttackCard())){
				AI_Info.validateMovingCardToAttackArea(energyCard,Users.CardLocation.HAND);
				sleep();
				message = "AI charge it's attack card with energy card"; 
				coordinator.updateCommicuntionPanel(message);
			} else{
				if (findPokemonOnBenchToCharge(energyCard)){
					sleep();
					message = "AI have charged a pokemone on bench with energy card";  
					coordinator.updateCommicuntionPanel(message);
				}
					
			}
		}
	}
	
	public void tryToAttackPlayer(){
		
		if(!AI_Info.getAttackCard().canPokemonAttack())// cannot attack
			return;
		
			ArrayList<AbilityCard> abilityCard = AI_Info.getAttackCard().getAbilityCards();
			AbilityCard bestAttack = null ;
			
			for ( AbilityCard attack : abilityCard)
				if(AI_Info.getAttackCard().isThereEnoughEnergyToActivateAbility(attack.getCat(), 
						attack.getNumOfEnergyCard())){
						bestAttack = attack;
				}
			if(bestAttack != null){
				sleep();
				coordinator.activateAbility(bestAttack, false);			
				coordinator.updateCommicuntionPanel("AI has activate ability ): ");
			}
	}
	

	private boolean findPokemonOnBenchToCharge(Card energyCard){
			for(int i = 0 ; i < AI_Info.getBench().getCardCount() ; i++){
				Card card  = AI_Info.getBench().getCard(i);
				if(AI_Info.getBench().getCard(i).equals(Card.CardType.POKEMON)){
					if(!hasFullEnergyCard((Pokemon)card)){
						AI_Info.vaildateChargingOrEvolvingPokemonOnBench((Pokemon)card, energyCard);
						return true;
				}
			}
		}
			return false;
	}
	
	public boolean hasFullEnergyCard(Pokemon card){
		ArrayList<AbilityCard> abilitiescards = card.getAbilityCards();
		for(AbilityCard abilit : abilitiescards){
			if(!card.isThereEnoughEnergyToActivateAbility(abilit.getCat(), abilit.getNumOfEnergyCard()))
				return false;
		}
		return true;
	}
	
	private void endTurn(){
		AI_Info.removeStatus();
		coordinator.dealCard(true,1);
		coordinator.setAIInControl(false);
	}
	
	private void sleep(){
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Users getAIInfo(){
		return AI_Info;
	}

	public void setFirstTurn(boolean firstTurn){
		AI_Info.setFirstTurn(firstTurn);
	}
}
