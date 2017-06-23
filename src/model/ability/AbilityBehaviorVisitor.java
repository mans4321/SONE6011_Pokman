package model.ability;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import control.Coordinator;
import model.Card;
import model.Coin;
import model.Energy;
import model.Pokemon;
import model.Users;

//search , swap, heal, dam
public class AbilityBehaviorVisitor implements AbilityVisitor{

	@Override
	public String activityability(Null nullability) {
		return "ability is null so nothing has happend";
	}

	@Override
	public String activityability(Damage damage) {
		//dam:target:opponent-active:20
		//dam:target:opponent-active:20*count(target:your-bench)
		//dam:target:your-active:10
		//dam:target:choice:opponent:30
		//dam:target:opponent-active:count(target:opponent-active:energy)*10
		//dam:target:opponent:10
		//dam:target:your-bench:10
		//dam:target:choice:opponent-bench:30
		if(damage.getTraget().equals(AbilityCard.TRAGET.OPPONENTACTIVE)){
			return inflectedDamage(damage);
		}
		
		else if (damage.getTraget().equals(AbilityCard.TRAGET.YOURACTIVE)) {
			damage.setPlayer(!damage.isPlayer());
			return inflectedDamage(damage);
		}else if (damage.getTraget().equals(AbilityCard.TRAGET.CHOICEOPPONENT) || 
				  damage.getTraget().equals(AbilityCard.TRAGET.CHOICEOPPONENTBENCH) || 
				  damage.getTraget().equals(AbilityCard.TRAGET.CHOICEYOURBENCH)){
			return inflectedDamageToPokemon(damage);	
		}
		return "trage don't ";
	}
	
	private String  inflectedDamageToPokemon(Damage damage){
		String message = "the selected pokemon got shoot";
		if(damage.getPokemon() == null ){
			return "no target pokemon to inflected Damage To ";
		}
	
		if(!damage.getPokemon().hitPokmon(damage.getDamage())){
			if(damage.getTraget().equals(AbilityCard.TRAGET.CHOICEYOURBENCH))
				damage.getCoordinator().deletePokemon(damage.getPokemon() , damage.getPokeomnLocation(), damage.isPlayer());
			else 
				damage.getCoordinator().deletePokemon(damage.getPokemon() , damage.getPokeomnLocation(), !damage.isPlayer());
				message +="selected got killed"; 
			}
		return message;
	}
	
	private String  inflectedDamage(Damage damage){
		String message = "";
		if(damage.isPlayer()){
			message = "AI or \"may be you\" has inflected damage to your active Pokemon";
			message += "damage inflected : " + damage.getDamage();
			if(!damage.getCoordinator().getAIAttackCard().hitPokmon(damage.getDamage())){
				damage.getCoordinator().updateAIAttackCard();
				damage.getCoordinator().dealPrizeCard(true);
				message += "your active pokemon has killd";
			}
		}else{
			message = "AI active pokemon has recived damage of " + damage.getDamage();
			if(!damage.getCoordinator().getPlayerAttackCard().hitPokmon(damage.getDamage())){
				damage.getCoordinator().UpdatePlayerAttackCard();
				damage.getCoordinator().dealPrizeCard(false);
				message += "AI active pokemon has killd";
			}
		}
		return message;
	}
	
	@Override
	public String activityability(Heal heal) {
		//heal:target:your-active:20
		//heal:target:choice:your:30
		String message = "a Pokemon has heald";
		if(heal.getTraget().equals(AbilityCard.TRAGET.YOURACTIVE)){ 
			if(heal.isPlayer())
				healPokemon(heal.getCoordinator().getPlayerAttackCard(), heal.getAmount());
			else
				heal.getCoordinator().getAIAttackCard().healPokemon(heal.getAmount());		
		}else if(heal.getTraget().equals(AbilityCard.TRAGET.CHOCICEYOUR)){
			if(heal.isPlayer()){
				//TODO create the frame 
			}else{
				healPokemon(heal.getPokemon(), heal.getAmount());
			}
		}else {
			message = "this part is not implemented";
		}
		
		return message;
	}
	
	private void healPokemon(Pokemon pokemon , int amount){
		pokemon.healPokemon(amount);
	}


	@Override
	public String activityability(Deenergize deenergize) {
		//deenergize:target:your-active:count(target:your-active:energy)
		//deenergize:target:opponent-active:1
		//
		if(deenergize.getTraget().equals(AbilityCard.TRAGET.YOURACTIVE)){
			deenergizePokemon(deenergize.isPlayer(), deenergize.getCoordinator(), deenergize.getAmount());
			return "an active pokemon deenergize " ;
		}else if (deenergize.getTraget().equals(AbilityCard.TRAGET.OPPONENTACTIVE)){
			deenergizePokemon(!deenergize.isPlayer(), deenergize.getCoordinator(), deenergize.getAmount());
			return "an active pokemon deenergize " ;
		}
		return "not include in file ";
	}
	
	private void deenergizePokemon(boolean isPalyer, Coordinator coordinator, int amount){
		if(isPalyer)
			coordinator.getPlayerAttackCard().deenergize(amount);
		else 
			coordinator.getAIAttackCard().deenergize(amount);
	}

	@Override
	public String activityability(Reenergize reenergize) {
		ArrayList<Energy> moveEnergy = reenergize.getSourcePokemon().reenergizeFrom();
		reenergize.getTragetPokemon().reenergizeTO(moveEnergy);
		return "reenergize from " +  reenergize.getSourcePokemon().getName() + " TO " + reenergize.getTragetPokemon().getName();
	}

	@Override
	public String activityability(Redamage redamage) {
		redamage.getTragetPokemon().gotHit(redamage.getDamage());
		return "a Damge has moved from " +  redamage.getSourcePokemon() + " TO " +  redamage.getTragetPokemon() + "\n amount of damage moved is " + redamage.getDamage();
	}

	@Override
	public String activityability(Swap swap) {
		//Switch:swap:source:your-active:destination:choice:your-bench
		swap.getCoordinator().swapPokemon(swap.getTragetPokemon(), Users.CardLocation.BENCH ,swap.isPlayer());
		return "swap active pokemon with a bench Pokemon";
	}

	@Override
	public String activityability(Applystat applyStatus) {
		//applystat:status:paralyzed:opponent-active
		
		if(applyStatus.getTarget().equals(AbilityCard.TRAGET.OPPONENTACTIVE)){
			if(applyStatus.isPlayer()){
				applyStatus.getCoordinator().getAIAttackCard().setStatus(applyStatus.getStatus());
				return "AI active Pokemon status is now" + applyStatus.getStatus();
			}else{
				applyStatus.getCoordinator().getPlayerAttackCard().setStatus(applyStatus.getStatus());
				return "your active Pokemon status is now" + applyStatus.getStatus();
			}
		}
		return "only OPPONENTACTIVE exit in the file";

	}

	@Override
	public String activityability(Draw draw) {
		//Tierno:draw:3
		//draw:opponent:4
		if(draw.getTraget() == null)
			draw.getCoordinator().dealCard(draw.isPlayer(), draw.getAmount());
		else if(draw.getTraget().equals(AbilityCard.TRAGET.OPPONENT)){
			draw.getCoordinator().dealCard(!draw.isPlayer(), draw.getAmount());
		}else{
			return "target only can be OPPONENT!!! ";
		}
		return "a "+ draw.getAmount() + "cards has drawn from deck to hand" ;
	}

	@Override
	public String activityability(Search search) {
		if(search.getFilter().equals(Search.FILTER.TOP))
			TopSearching(search);
		else if(search.getFilter() == null)
			searchWithNoFiliter(search);
		else
			searchForCardType(search);
		
			
		return "Search ability activated";
	}

	private void searchForCardType(Search search) {
		//Clemont:search:target:your:source:deck:filter:energy:4
		//Poké Ball:cond:flip:search:target:your:source:deck:filter:pokemon:1
		//Pokémon Fan Club:search:target:your:source:deck:filter:pokemon:cat:basic:2
		ArrayList<Card> cards ;
		if(search.getSource().equals(Search.SOURCE.DECK)){
			if(search.isPlayer()){
				cards = search.getCoordinator().getPlayerDeck().filiterCardBaseOnType(search.getFilter());
				//TODO the frame and delete cards 
			}else{
				cards = search.getCoordinator().getPlayerDeck().filiterCardBaseOnType(search.getFilter());
				for(int i =0 ; i < search.getAmount(); i++){
					search.getCoordinator().addCardTOAIHand(cards.get(i));
					search.getCoordinator().getAIDeck().removeCard(cards.get(i));
				}
			}
		}
}

	private void searchWithNoFiliter(Search search) {
		//Wish:search:target:your:source:deck:1
		if(search.isPlayer()){
			if(search.getSource().equals(Search.SOURCE.DECK)){
				ArrayList<Card> cards = search.getCoordinator().getPlayerDeck().getTopCard(search.getCoordinator().getPlayerDeck().cardsLeft());
				//TODO precent the screen and delete cards
			}
		}else{
			if(search.getSource().equals(Search.SOURCE.DECK))
				search.getCoordinator().dealCard(false, search.getAmount());
		}
	}

	private void TopSearching(Search search){
		//search:target:opponent:source:deck:filter:top:1:0
		if(search.isPlayer() && search.getTraget().equals(AbilityCard.TRAGET.OPPONENT)){
			if(search.getSource().equals(Search.SOURCE.DECK)){
				ArrayList<Card> cards = search.getCoordinator().getPlayerDeck().getTopCard(search.getAmount());
				//TODO prcentView 
		}
				
		}else{
			dealTheTOpSearchLogicForAI(search);
		}
			
	}
	
	private void dealTheTOpSearchLogicForAI(Search search) {
		if((!search.isPlayer() && search.getTraget().equals(AbilityCard.TRAGET.YOUR)))
			if(search.getSource().equals(Search.SOURCE.DECK))
				search.getCoordinator().dealCard(false, search.getAmount());
	}

	@Override
	public String activityability(DeckAbility deckAbility) {
		//Shauna:deck:target:your:destination:deck:count(your-hand),shuffle:target:you,draw:5
		//Red Card:deck:target:opponent:destination:deck:count(opponent-hand),shuffle:target:opponent,draw:opponent:4
		if(deckAbility.getTarget().equals(AbilityCard.TRAGET.YOUR)){
				removeCards(deckAbility, deckAbility.isPlayer());
		}else{
				removeCards(deckAbility, !deckAbility.isPlayer());
		}
		
		return "deck ability has activiate";
	}

	private void removeCards(DeckAbility deckAbility, boolean player) {
			deckAbility.getCoordinator().removeFromHandToDeck(player,deckAbility.getAmount());
	}

	@Override
	public String activityability(Shuffle shuffle) {
		//shuffle:target:opponent
		//shuffle:target:you
		if(shuffle.getTraget().equals(AbilityCard.TRAGET.YOU))
			shuffleDeck( shuffle.isPlayer(),shuffle.getCoordinator());
		else
			shuffleDeck( !shuffle.isPlayer(),shuffle.getCoordinator());
		return "A deck has been shuffled";
	}
	
	private void shuffleDeck(boolean isPlayer , Coordinator coordinator){
		if(isPlayer)
			coordinator.shufflePlayerDeck();
		else 
			coordinator.shuffleAIDeck();
	}
	
	@Override
	public String activityability(Cond cond) {
		//cond:flip
		//cond:healed:target:your-active
		//cond:count(target:your-active:energy:psychic)>0:dam:target:opponent-active:20
		//cond:choice
		Cond.CondType condType = cond.getCondType(); 
		String message = "";
		
		switch(condType){
			
			case FLIP:
				if(new Coin().isSuccess()){
					for(Ability ability : cond.getAbilities()){
						ability.activateAbility(cond.getAbilityVisitor(), cond.getCoordinator(), cond.isPlayer());
					}
					message += "flip condition has pass and abilities has activated";
				}else{
					for(Ability ability : cond.getElseAbilities()){
						ability.activateAbility(cond.getAbilityVisitor(), cond.getCoordinator(), cond.isPlayer());
					}
					message += "flip condition has not pass and if there abilities with it has activated";
				}
				 break;
			case HEALED:
				if(cond.getTraget().equals(AbilityCard.TRAGET.YOURACTIVE)){
					if(cond.isPlayer()){
						message += ifPokemonHeald(cond.getCoordinator().getPlayerAttackCard(), cond);
					}else{
						message += ifPokemonHeald(cond.getCoordinator().getAIAttackCard(), cond);
					}
				}
				 break;
				
			case COUNT :
				int count = cond.getCount().getCount(cond.getCoordinator(), cond.isPlayer());
				if(cond.getOperator().equals(Cond.Operator.BIGGERTHAN)){
					if( count > cond.getNum()){
						for(Ability ability : cond.getAbilities()){
							ability.activateAbility(cond.getAbilityVisitor(), cond.getCoordinator(), cond.isPlayer());
						}
						message += "count condition has pass abilitie associated  with it has activated";
					}else{
						for(Ability ability : cond.getElseAbilities()){
							ability.activateAbility(cond.getAbilityVisitor(), cond.getCoordinator(), cond.isPlayer());	
						}
						message += "count condition has not pass and if there abilitie associated  with else it has activated";
					}
				}else {
					message = " only one operator in file >";
				}
				
				 break;
				 
			case CHOICE:
		        int input= 2;
		        while(input == JOptionPane.CLOSED_OPTION)
		             input = JOptionPane.showConfirmDialog(null, "Do you want to pass this condition?", "Confirm",
		                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		        if(input == JOptionPane.YES_OPTION){
					for(Ability ability : cond.getAbilities()){
						ability.activateAbility(cond.getAbilityVisitor(), cond.getCoordinator(), cond.isPlayer());
					}
					message += "you pass condition  abilities associated  with it has activated";
		        }else{
		    		for(Ability ability : cond.getElseAbilities()){
						ability.activateAbility(cond.getAbilityVisitor(), cond.getCoordinator(), cond.isPlayer());
					}
		    		message += "you did not pass condition  abilitie associated  with else if any has activated";
		        }
		        
		        message += "your CHOICE";
		        break;

			default :
				message += "cond implementation cannot handler this condition";
		}//end of switch 
		return message;
	}

	private String ifPokemonHeald(Pokemon pokemon, Cond cond){
		if(pokemon.isHasHeald()){
			for(Ability ability : cond.getAbilities()){
				ability.activateAbility(cond.getAbilityVisitor(), cond.getCoordinator(), cond.isPlayer());
			}
			return "pokemon has used heal so activating abilits";
		}else{
			for(Ability ability : cond.getElseAbilities()){
				ability.activateAbility(cond.getAbilityVisitor(), cond.getCoordinator(), cond.isPlayer());
			}
			return "pokemon has not used heal so activating abilits folloowing else if any";
		}
	}
	
	@Override
	public String activityability(Add cond) {
		return null;
	}

	
}
