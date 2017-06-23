package model.ability;

import java.util.ArrayList;

import control.Coordinator;
import model.Card;
import model.Card.EnegeryCategory;

public class AbilityCard {

	    public static enum TRAGET{THEM, OPPONENTACTIVE,YOURBENCH,YOURACTIVE,CHOICEOPPONENTBENCH,CHOICEOPPONENT,CHOICEYOURBENCH, CHOCICEYOUR, YOUR, LAST, OPPONENT, YOU , SELF};
//	    public static enum Filter{TOP, TYPE, CAT, POKEMON, NONE};
	    public static enum CondType{FLIP,HEALED, COUNT,CHOICE};
		private String abilityCardName ;
		private int numOfEnergyCard;
		private Card.EnegeryCategory cat;
		private ArrayList<Ability> abilities ; 
		
		
		
	//TODO add the energy  card to the attack 
		
		public AbilityCard(String abilityCardName ,ArrayList<Ability> abilities){
			this.abilityCardName = abilityCardName;
			this.abilities = abilities;
		}
	
		public String toString(){
			return abilityCardName;
		}

		
		public String getAbilityCardName() {
			return abilityCardName;
		}
	
		public void setAbilityCardName(String abilityCardName) {
			this.abilityCardName = abilityCardName;
		}

		public int getNumOfEnergyCard() {
			return numOfEnergyCard;
		}
		
		public void setNumOfEnergyCard(int numOfEnergyCard) {
			this.numOfEnergyCard = numOfEnergyCard;
		}

		public Card.EnegeryCategory getCat() {
			return cat;
		}

		public ArrayList<Ability> getAbilities() {
			return abilities;
		}
		
		
		public String activityAbilities(AbilityVisitor abilityVisitor, Coordinator coordinator, boolean isPlayer){
			String message = "" ;
			for(Ability ability : abilities){
				message += ability.activateAbility(abilityVisitor, coordinator , isPlayer );
				message +="/n";
			}
			return message;
		}

		public void setCat(EnegeryCategory setEnergyType) {
			cat = setEnergyType;
		}


		
	}
