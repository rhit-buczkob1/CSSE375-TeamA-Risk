package Risk.controller;

import Risk.model.Player;
import Risk.model.Territory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class AttackerDefenderController {
	Player attacker;
	Player defender;
	Territory attackerTerritory;
	Territory defenderTerritory;
	int attackerArmies;
	int defenderArmies;

	public void setAttacker(Player player) {
		// TODO Auto-generated method stub
		attacker = player;
	}

	public Player getAttacker() {
		// TODO Auto-generated method stub
		return attacker;
	}

	public void setDefender(Player player) {
		// TODO Auto-generated method stub
		defender = player;
	}

	public Player getDefender() {
		// TODO Auto-generated method stub
		return defender;
	}

	public Integer[] rollDice(int numDice, Random rand) {
		// TODO Auto-generated method stub
		if (numDice <= 0) {
			throw new IllegalArgumentException("Incorrect Amount of Dice rolled");
		}
		Integer[] toReturn = new Integer[numDice];
		for (int i = 0; i < numDice; i++) {
			toReturn[i] = rand.nextInt(6);
		}
		return toReturn;
	}

	public void setAttackingTerritory(Territory t) {
		// TODO Auto-generated method stub
		if (attacker.getId() != t.getPlayer()) {
			throw new IllegalArgumentException("Player does not own this territory " + attacker.getId() + " is the attacker " + t.getPlayer() + " is the owner");
		}
		attackerTerritory = t;
		
	}

	public Territory getAttackingTerritory() {
		// TODO Auto-generated method stub
		return attackerTerritory;
	}

	public Territory getDefendingTerritory() {
		// TODO Auto-generated method stub
		return defenderTerritory;
	}

	public void setDefendingTerritory(Territory t) {
		// TODO Auto-generated method stub
		defenderTerritory = t;
		
	}

	public void setArmies(int i) {
		// TODO Auto-generated method stub
		if (attackerTerritory.getArmyCount() - i < 2) {
			throw new IllegalArgumentException("need a minimum of 2 armies left in attacking territory");
		}
		this.attackerArmies = i;
		this.attackerTerritory.removeArmies(i);
		this.defenderArmies = defenderTerritory.getArmyCount();
		
	}

	public void combat(int i, int j, Random rand) {
		// TODO Auto-generated method stub
		if (i > attackerArmies || j > defenderArmies) {
			throw new IllegalArgumentException("Must have dice equal to or less than the amount of armies");
		}
		
		Integer[] attackerDice = rollDice(i, rand);
		Integer[] defenderDice = rollDice(j, rand);
		Arrays.sort(attackerDice, Collections.reverseOrder());
		Arrays.sort(defenderDice, Collections.reverseOrder());
		if (attackerDice.length >= defenderDice.length) {
			for (int k = 0; k < defenderDice.length; k++) {
				if (attackerDice[k].compareTo(defenderDice[k]) <= 0) {
					attackerArmies -= 1;
				}
				if (attackerDice[k].compareTo(defenderDice[k]) > 0) {
					defenderArmies -= 1;
				}
				
				
			}
		}
		if (attackerDice.length < defenderDice.length) {
			for (int k = 0; k < attackerDice.length; k++) {
				if (attackerDice[k].compareTo(defenderDice[k]) <= 0) {
					attackerArmies -= 1;
				}
				if (attackerDice[k].compareTo(defenderDice[k]) > 0) {
					defenderArmies -= 1;
				}
				
				
			}
		}
		if (defenderArmies == 0) {
			defender.removeTerritory();
			attacker.addTerritory();
			attacker.caughtTerritory(true);
			defenderTerritory.setArmyCount(attackerArmies);
			defenderTerritory.setPlayer(attacker.getId());
		} else {
			defenderTerritory.setArmyCount(defenderArmies);
			attackerTerritory.addArmies(attackerArmies);
		}
		
		
	}
	

}
