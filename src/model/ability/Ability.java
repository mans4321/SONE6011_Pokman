package model.ability;

import control.Coordinator;

public interface  Ability {
	public String activateAbility(AbilityVisitor abilityVisitor, Coordinator coordinator, boolean isPlayer);

}
