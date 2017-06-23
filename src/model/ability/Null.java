package model.ability;

import control.Coordinator;

public class Null implements Ability {

	@Override
	public String activateAbility(AbilityVisitor abilityVisitor,  Coordinator coordinator, boolean isPlayer) {
		return null;
	}

}
