package model.ability;

import java.util.ArrayList;

import control.Coordinator;
import model.Card;
import model.Energy;
import model.Pokemon;

//TODO Ear Influence:redamage:target:opponent:target:opponent:count(target:last:source:damage)
// if i am  going to do this fix get count function
public class Count {
	
	public static enum CountWho
	{YOURACTIVEDAMAGE, YOURACTIVEENERGYPSYCHIC ,
		YOURBENCH , YOURACTIVEENERGY,
		OPPONENTACTIVEENERGY, YOURHAND, OPPOMENTHAND};
		
	private int times ;
	private CountWho countWho;
	
	public Count(int times , CountWho countWho){
		this.times = times ;
		this.countWho = countWho;
	}
	
	public Count(CountWho countWho){
		times = 1;
		this.countWho = countWho;
	}
	
	public int getCount(Coordinator coordinator, boolean isPlayer){
		int num;
		if(countWho.equals(CountWho.YOURBENCH)){
			if(isPlayer)
				num = times * coordinator.getPlayerBench().getCardCount();
			else
				num = times * coordinator.getAIBench().getCardCount();
			return num;
		}else if(countWho.equals(CountWho.YOURHAND)){
			if(isPlayer)
				num = times * coordinator.getPlayerHand().getCardCount();
			else
				num = times * coordinator.getAIHand().getCardCount();
			return num;
		}else if(countWho.equals(CountWho.OPPOMENTHAND)){
			if(!isPlayer)
				num = times * coordinator.getPlayerHand().getCardCount();
			else
				num = times * coordinator.getAIHand().getCardCount();
			return num;
		}else if(countWho.equals(CountWho.YOURACTIVEDAMAGE)){
			if(isPlayer)
				num = times * (coordinator.getPlayerAttackCard().getHp() - coordinator.getPlayerAttackCard().getDamage());
			else
				num = times * (coordinator.getAIAttackCard().getHp() - coordinator.getAIAttackCard().getDamage());
			return num;
		}else if(countWho.equals(CountWho.OPPONENTACTIVEENERGY)){
			if(!isPlayer)
				num = times * (coordinator.getPlayerAttackCard().getNumOfENERGYCard() );
			else
				num = times * (coordinator.getAIAttackCard().getNumOfENERGYCard());
			return num;
		}else if(countWho.equals(CountWho.YOURACTIVEENERGYPSYCHIC)){
			if(isPlayer){
				num = times * countEnergyPsychis(coordinator.getPlayerAttackCard().getEnergyCards());
				return num;
			}
			else{
				num = times * countEnergyPsychis(coordinator.getAIAttackCard().getEnergyCards());
				return num;
			}
		}else{ //YOURACTIVEENERGY
			if(isPlayer)
				num = times * (coordinator.getPlayerAttackCard().getNumOfENERGYCard() );
			else
				num = times * (coordinator.getAIAttackCard().getNumOfENERGYCard());
			return num;
		}
	}
	
	//TODO maybe i have to remove this 
	public int getCount(Pokemon pokemon){
		return pokemon.getLastDamage();
	}
	private int countEnergyPsychis(ArrayList<Energy> energyCards){
		int count = 0;
		for(Energy energy : energyCards){
			if(energy.getCategory().equals(Card.EnegeryCategory.PSYCHIC))
				count++;
		}
		return count;	
	}
}
