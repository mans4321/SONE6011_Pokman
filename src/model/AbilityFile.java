package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import model.ability.Ability;
import model.ability.AbilityCard;
import model.ability.AbilityCard.TRAGET;
import model.ability.Applystat;
import model.ability.Cond;
import model.ability.Cond.CondType;
import model.ability.Count;
import model.ability.Damage;
import model.ability.DeckAbility;
import model.ability.Deenergize;
import model.ability.Draw;
import model.ability.Heal;
import model.ability.Search;
import model.ability.Shuffle;
import model.ability.Swap;

public class AbilityFile {

	private static AbilityFile instance ;
	private int line_num;
	
	private  HashMap<Integer, AbilityCard> abilityHashMap ;;
	private final String abilityFileName = "abilities.txt";
	
	private AbilityFile(){
		abilityHashMap = new HashMap<Integer, AbilityCard>();
		readAbilityFile(abilityFileName);
		AbilityCard ab = abilityHashMap.get(5);
	}
	
	public static AbilityFile sharedInstance() {
	  	if (instance == null)
    		instance = new AbilityFile();
        return instance;
	}
	 
	private void readAbilityFile(String ability_file){
	  File file = new File(ability_file);
	  BufferedReader reader = null;
	  try {
	     reader = new BufferedReader(new FileReader(file));
	     String line;
	     line_num = 1;
	     while ((line = reader.readLine()) != null){
	    	 if (skipAbility(line_num)){
	    		 abilityHashMap.put(line_num, null);
	    		 line_num++;
	    	 }
	    	 else{
		        abilityHashMap.put(line_num, readAbilityLine(line));
		        line_num++;
	    	 }
	     }
	  }catch (IOException e){
	     System.out.println("There is no file :" + e.getMessage());
	  }
	}
	 
	private boolean skipAbility(int line_num) {
		switch (line_num){
		case 1:
			return true ;
		case 36:
			return true ;
		case 33:
			return true ;
		case 58:
			return true ;
		case 67:
			return true ;
		case 72:
			return true ;
		case 34:
			return true ;
		case 74:
			return true ;
		}
		return false;
	}

	private AbilityCard readAbilityLine (String ability_line){
		ArrayList<Ability> oneLineAbility = new ArrayList<>();
		String[] words = ability_line.split(":");
		String name = words[0];
		if (line_num == 51){
			ability_line =  removeFromString(name + ":", ability_line);
            oneLineAbility.add(determineAndCreateAbility(ability_line));
        }else {
        	ability_line = removeFromString(name+ ":", ability_line);
            String[] abilities = ability_line.split(",");
            for (int index = 0; index < abilities.length; index++) {
                oneLineAbility.add(determineAndCreateAbility(abilities[index]));
            }
        }
        return new AbilityCard(name, oneLineAbility);
	}

	private Ability deenergizeAbility(String reString) {
		// TODO Auto-generated method stub
		AbilityCard.TRAGET targets = null;
		String[] showDetail = reString.split(":");
		String target = showDetail[1];
		reString =  removeFromString("target:",reString);
		switch (target) {
		case "opponent-active":
			reString = removeFromString("opponent-active:",reString);
			targets = AbilityCard.TRAGET.OPPONENTACTIVE;
			break;
		case "your-active":
			reString = removeFromString("your-active:",reString);
			targets = AbilityCard.TRAGET.YOURACTIVE;
			break;
		default:
			 System.out.println("Target not exist");
		}
		
		if (reString.indexOf("count") == -1) {
			int amount = Integer.parseInt(showDetail[2]);
			return new Deenergize(targets, amount);	
		} else {
			reString = removeFromString("count", reString);
			Count count = createCount(reString);
			return new Deenergize(targets, count);
		}
	}
	
	private Ability determineAndCreateAbility (String string) {
		String[] getelement = string.split(":");
		String damageType = getelement[0]; 
		string = removeFromString(damageType + ":" , string);
		
		switch (damageType) {
		case "dam":
			return createDamage(string);
		case "heal":
			return healPokemon(string);
		case "deenergize":
			return deenergizeAbility(string);
		case "reenergize":
			return reenergizeAbility(string);
		case "redamage":
			return redamageAbility(string);
		case "swap":
			return swapAbility(string);
		case "destat":
			return destatAbility(string);
		case "applystat":
			return applystatAbility(string);
		case "draw":
			return drawAbility(string);
		case "search":
			return searchAbility(string);
		case "deck":
			return DeckAbility(string);
		case "shuffle":
			return shuffleAbility(string);
		case "cond":
			return condAbility(string);
		case "add":
			return addAbility(string);
		default:
			throw new IllegalArgumentException("no ability with this name ");
		}
	}
	
	private Ability addAbility(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private Ability destatAbility(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private Ability reenergizeAbility(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	private Ability redamageAbility(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Ability condAbility(String reString) {
		 String condString ;
		if (reString.indexOf("count") != -1){
			condString = "count";
		}else{
			condString = reString.split(":")[0];
		}
		Cond.CondType condType = null;
		ArrayList<Ability> abilities = new ArrayList<Ability>();
		//FLIP,HEALED, COUNT, CHOICE
		switch (condString){
            case "flip":
                condType = Cond.CondType.FLIP;
                break;
            case "healed":
                condType = Cond.CondType.HEALED;
                return createCondWithHeal(condType , reString);
            case "count":
            	reString =  removeFromString("count", reString);
                return createCondWithCount(reString);
            case "choice":
                condType = Cond.CondType.CHOICE;
                break;
        }
		reString =removeFromString(condString+":", reString);
        if(reString.indexOf("else") == -1){
		    return createCondWithoutElse(reString, condType);
        }else{
            return createCondwithElse(reString, condType);
        }
	}

	private Ability createCondWithHeal(CondType condType, String reString) {
		ArrayList<Ability> abilities  = new ArrayList<Ability>();
		reString = removeFromString("healed:" , reString);
		reString = removeFromString("target:" , reString);
		reString = removeFromString("your-active:" , reString);
		abilities.add(determineAndCreateAbility(reString));
		return new Cond (condType , AbilityCard.TRAGET.YOURACTIVE, abilities , new ArrayList<Ability>());
	}

	private Ability createCondWithCount(String string){
	    //TODO
        Cond.Operator operator ;
        Count count ;
        ArrayList<Ability> ability = new ArrayList<Ability>();
        String[] aStr = string.split(">");
        count = createCount(aStr[0]);
        operator = Cond.Operator.BIGGERTHAN;
        int num =0;
        String abilityStr = removeFromString("0:",aStr[1]);
        ability.add(determineAndCreateAbility(abilityStr));
        return  new Cond(count , operator, num,ability, new ArrayList<Ability>() );
    }

    private Ability createCondWithoutElse(String string, Cond.CondType condType){
        ArrayList<Ability> abilities = new ArrayList<Ability>();

        if (string.contains("(") && string.contains(")")){
        	string = removeFromString("[(]", string);
        	string = removeFromString("[)]", string);
            String[] condAbilities = string.split(",");
            for (int index = 0; index < 2; index++){
                abilities.add(determineAndCreateAbility(condAbilities[index]));
            }
        }else{
            abilities.add(determineAndCreateAbility(string));
        }
        return new Cond(condType, abilities, new ArrayList<Ability>());
    }

    private Ability createCondwithElse(String string, Cond.CondType condType){
        String[] elseStrings = string.split("else:");
        ArrayList<Ability> abilities = new ArrayList<Ability>();
        ArrayList<Ability> abilitiesElse = new ArrayList<Ability>();
        abilities.add(determineAndCreateAbility(elseStrings[0]));
        abilitiesElse.add(determineAndCreateAbility(elseStrings[1]));
        return new Cond(condType, abilities, abilitiesElse);
    }
 


	private Ability shuffleAbility(String reString) {
		// TODO Auto-generated method stub
		String targetString = reString.split(":")[1];
		AbilityCard.TRAGET target = null;
		switch (targetString){
			case "you":
				target = AbilityCard.TRAGET.YOU;
				break;
			case "opponent":
				target = AbilityCard.TRAGET.OPPONENT;
		}
		Shuffle shuffle = new Shuffle(target);
		return shuffle;
	}
 
	private Ability swapAbility(String reString) {
		// TODO Auto-generated method stub
		AbilityCard.TRAGET target = AbilityCard.TRAGET.YOURACTIVE;
		AbilityCard.TRAGET source = AbilityCard.TRAGET.YOURBENCH;
		return new Swap(target, source);
	}

	
	private Ability DeckAbility(String reString) {
		// TODO Auto-generated method stub
		// target:your:destination:deck:count(your-hand)
		AbilityCard.TRAGET target = null;
		DeckAbility.DESTINATION des = null;
		String[] showDeckDetail =reString.split(":");
		String targetName = showDeckDetail[1];
		String destination = showDeckDetail[3];
		switch (targetName) {
		case "your":
			target = AbilityCard.TRAGET.YOUR;
			reString = removeFromString("target:your:", reString);
			break;
		case "opponent":
			target = AbilityCard.TRAGET.OPPONENT;
			reString = removeFromString("target:opponent:", reString);
			break;
		default:
			throw new IllegalArgumentException("no target with this name ");
		}
		switch (destination) {
		case "deck":
			des = DeckAbility.DESTINATION.DECK;
			reString = removeFromString("destination:deck", reString);
			break;
		case "discard":
			des = DeckAbility.DESTINATION.DISCARD;
			reString = removeFromString("destination:discard", reString);
			break;
		default:
			throw new IllegalArgumentException("no destination");
		}
		reString = removeFromString("count", reString);
		Count count = createCount(reString);
		return new DeckAbility(target, des, count);
	}

	private Ability drawAbility(String reString) {
		// TODO Auto-generated method stub	
		if (reString.indexOf(":") == -1) {
			int number = Integer.parseInt(reString);
			return new Draw(number);
			
		} else {
			String[] showDrawDetail = reString.split(":");
			AbilityCard.TRAGET target = AbilityCard.TRAGET.OPPONENT;
			int amount = Integer.parseInt(showDrawDetail[1]);
			return new Draw(target, amount);
		}
	}



	private Ability healPokemon(String reString) {
		// TODO Auto-generated method stub
		String targetString = reString.split(":")[1];
		reString = removeFromString("target"+":",reString);
		AbilityCard.TRAGET target = null;
		switch (targetString){
			case "choice":
				target = AbilityCard.TRAGET.CHOCICEYOUR;
				reString = removeFromString("choice:your"+":",reString);
				break;
			case "your-active":
				target = AbilityCard.TRAGET.YOURACTIVE;
				reString = removeFromString("your-active"+":",reString);
				break;
		}
		Heal heal = new Heal(target, Integer.valueOf(reString).intValue());
		return heal;
	}
	
	 
		private Ability applystatAbility(String reString) {
			// TODO Auto-generated method stub
			String statusString = reString.split(":")[1];
			String targetString = reString.split(":")[2];
			AbilityCard.TRAGET target = null;
			Card.PokemonStatus status = null;
			//PARALYZED, ASLEEP, STUCK, POISONED, NONE
			switch (statusString){
				case "paralyzed":
					status = Card.PokemonStatus.PARALYZED;
					break;
				case "asleep":
					status = Card.PokemonStatus.ASLEEP;
					break;
				case "stuck":
					status = Card.PokemonStatus.STUCK;
					break;
				case "poisoned":
					status = Card.PokemonStatus.POISONED;
					break;
			}
			switch (targetString){
				case "opponent-active":
					target = AbilityCard.TRAGET.OPPONENTACTIVE;
					break;
			}
			Applystat applystat = new Applystat(status, target);
			return applystat;
		}
		
		private Ability searchAbility(String reString) {
			//Identify the target
			String targetString = reString.split(":")[1];
			AbilityCard.TRAGET target = null;
			switch (targetString){
				case "your":
					target = AbilityCard.TRAGET.YOUR;
					break;
				case "opponent":
					target = AbilityCard.TRAGET.OPPONENT;
					break;
			}
			reString = removeFromString("target:"+targetString+":", reString);

			//Identify the source
			String sourceString = reString.split(":")[1];
			Search.SOURCE source = null;
			switch (sourceString){
				case "deck":
					source = Search.SOURCE.DECK;
					break;
				case "discard":
					source = Search.SOURCE.DISCARD;
					break;
			}
			reString = removeFromString("source:"+sourceString, reString);

			int filterAmount, amount;
			if(reString.indexOf("filter") == -1){
				reString = removeFromString(":", reString);
				amount = Integer.parseInt(reString);
				//public Search(AbilityCard.TRAGET traget , SOURCE source ,FILTER filter, int amount )
				return new Search(target, source,Search.FILTER.NOUN, amount );
			}
			reString = removeFromString("filter:", reString);
			reString = removeFromString(":", reString);
			//Identify the filter
			//and initialize the search ability
			String filterString = reString.split(":")[0];
			Search.FILTER filter = null;
			Search search;
			switch (filterString){
				case "top":
					filter = Search.FILTER.TOP;
					filterAmount = Integer.valueOf(reString.split(":")[1]).intValue();
					amount = Integer.valueOf(reString.split(":")[2]).intValue();
					search = new Search(target, source, filter, filterAmount, amount);
					return search;
				case "energy":
					filter = Search.FILTER.ENERGY;
					amount = Integer.valueOf(reString.split(":")[1]).intValue();
					search = new Search(target, source, filter, amount);
					return search;
				case "cat":
					filter = Search.FILTER.ITEM;
					amount = Integer.valueOf(reString.split(":")[2]).intValue();
					search = new Search(target, source, filter, amount);
					return search;
				case "pokemon":
					if (reString.split(":")[1].equals("cat")){
						filter = Search.FILTER.BASICPOKEMON;
						amount = Integer.valueOf(reString.split(":")[3]).intValue();
						search = new Search(target, source, filter, amount);
					}else {
						filter = Search.FILTER.POKEMON;
						amount = Integer.valueOf(reString.split(":")[1]).intValue();
						search = new Search(target, source, filter, amount);
					}
					return search;
				default:
					throw new IllegalArgumentException("something wrong with search ability. cannot initialize the search object");
			}
		}

	
	
	private Count createCount(String string) {
		if(string.contains("*")){
			return createCountWithTimes(string);
		}else{
			return createCountWithOutTimes(string);
		}
	}
	
	private Count createCountWithTimes(String string) {
		String[] str = string.split("[*]");
			int num ;
			Count.CountWho countWho ;
			if(isInteger(str[0].trim())){
				
				num = Integer.parseInt(str[0].trim());
				countWho = tagetForCount(str[1].trim());
			}else{
				num = Integer.parseInt(str[1].trim());
				countWho = tagetForCount(str[0].trim());
			}
			return new Count(num , countWho);
	}

	private Count.CountWho tagetForCount (String string) {

		string = removeFromString("[(]" , string );
		string = removeFromString("[)]" , string );
		string = removeFromString("target:", string);
		string = removeFromString(":", string);
		string = removeFromString("-", string);
		string = removeFromString(":", string);
		
		switch(string.toUpperCase()){
		 case"YOURACTIVEDAMAGE":
			 return Count.CountWho.YOURACTIVEDAMAGE;
		 case"YOURACTIVEENERGYPSYCHIC":
			 return Count.CountWho.YOURACTIVEENERGYPSYCHIC;
		 case"YOURBENCH":
			 return Count.CountWho.YOURBENCH;
		 case"YOURACTIVEENERGY":
			 return Count.CountWho.YOURACTIVEENERGY;
		 case"OPPONENTACTIVEENERGY":
			 return Count.CountWho.OPPONENTACTIVEENERGY;
		 case"YOURHAND":
			 return Count.CountWho.YOURHAND;
		 case"OPPONENTHAND":
			 return Count.CountWho.OPPOMENTHAND;
			default:
				 throw new IllegalArgumentException("not found");
			}
		
	}

	private Count createCountWithOutTimes(String string) {
		Count.CountWho  countWho= tagetForCount(string);
		return new Count(countWho);
	}

	private Ability createDamage(String string ){
		String targetinfoFromFile = string.split(":")[1];
		AbilityCard.TRAGET target= null;
		switch(targetinfoFromFile){
			case"opponent-active":
				target = AbilityCard.TRAGET.OPPONENTACTIVE;
				string = removeFromString("target:opponent-active:" , string);
				break;
			case"your-active":
				target = AbilityCard.TRAGET.YOURACTIVE;
				string = removeFromString("target:your-active:" , string);
				break;
			case "choice":
				string = removeFromString("target:choice:" , string);
				if(string.split(":")[0].equals("opponent-bench")){
					string = removeFromString("opponent-bench", string );
					target = AbilityCard.TRAGET.CHOICEOPPONENTBENCH ;
				}else{
					string = removeFromString("opponent:", string );
					target =  AbilityCard.TRAGET.CHOICEOPPONENT; 
				}
				break;
			case "opponent":
				target = AbilityCard.TRAGET.CHOICEOPPONENT;
				string = removeFromString("target:opponent:" , string);
				
			break;
			case "your-bench":
				target = AbilityCard.TRAGET.CHOICEYOURBENCH;
				string =removeFromString("target:your-bench:" , string);
				break;
		}
		if (string.contains("count")){
			return createDamageWithCount(target, string);
		}else
			return createDamageWithdamage(target, string);
	}
	
	private String removeFromString(String stringToRemove, String string) {
		return string.replaceFirst(stringToRemove,"");
	}
	private Ability createDamageWithdamage(TRAGET target, String string) {
		string = removeFromString(":",string );
		int damageAmount = Integer.parseInt(string.trim()) ;
		return new Damage(target, damageAmount);
	}
	private Ability createDamageWithCount(TRAGET target, String string) {
		string = removeFromString("count", string);
		Count count = createCount(string);
		return new Damage(target , count);
	}

	private TRAGET dealWithChoiceKeyWord(String string) {
		if(string.split(":")[0].equals("opponent-bench")){
			string = removeFromString("opponent-bench", string );
			return AbilityCard.TRAGET.CHOICEOPPONENTBENCH ;
		}else{
			string = removeFromString("opponent:", string );
			return AbilityCard.TRAGET.CHOICEOPPONENT; 
		}
	}
	
	public static boolean isInteger(String str) {
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}

	public HashMap<Integer, AbilityCard> getAbilityHashMap() {
		return abilityHashMap;
	}


}
