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
		attacker = player;
	}

	public Player getAttacker() {
		return attacker;
	}

	public void setDefender(Player player) {
		defender = player;
	}

	public Player getDefender() {
		return defender;
	}

	public Integer[] rollDice(int numDice, Random rand) {
		if (numDice <= 0) {
			throw new IllegalArgumentException("Incorrect Amount of Dice rolled");
		}
		Integer[] toReturn = new Integer[numDice];
		for (int i = 0; i < numDice; i++) {
			toReturn[i] = rand.nextInt(6);
		}

		sortDice(toReturn);
		return toReturn;
	}

	public void setAttackingTerritory(Territory t) {
		if (attacker.getId() != t.getPlayer()) {
			throw new IllegalArgumentException("Player does not own this territory " + attacker.getId() + " is the attacker " + t.getPlayer() + " is the owner");
		}
		attackerTerritory = t;
	}

	public Territory getAttackingTerritory() {
		return attackerTerritory;
	}

	public Territory getDefendingTerritory() {
		return defenderTerritory;
	}

	public void setDefendingTerritory(Territory t) {
		defenderTerritory = t;
	}

	public void setArmies(int i) {
		if (attackerTerritory.getArmyCount() - i < 2) {
			throw new IllegalArgumentException("need a minimum of 2 armies left in attacking territory");
		}
		this.attackerArmies = i;
		this.attackerTerritory.removeArmies(i);
		this.defenderArmies = defenderTerritory.getArmyCount();
	}

	public void combat(int i, int j, Random rand) {
		if (i > attackerArmies || j > defenderArmies) {
			throw new IllegalArgumentException("Must have dice equal to or less than the amount of armies");
		}
		
		Integer[] attackerDice = rollDice(i, rand);
		Integer[] defenderDice = rollDice(j, rand);

		attack(attackerDice, defenderDice);

		update();
	}

	private void update() {
		if (defenderArmies == 0) {
			defenderLoses();
		} else {
			defenderTerritory.setArmyCount(defenderArmies);
			attackerTerritory.addArmies(attackerArmies);
		}
	}

	private void defenderLoses() {
		defender.removeTerritory();
		attacker.addTerritory();
		attacker.caughtTerritory(true);
		defenderTerritory.setArmyCount(attackerArmies);
		defenderTerritory.setPlayer(attacker.getId());
	}

	private void attack(Integer[] attackerDice, Integer[] defenderDice) {
		for (int k = 0; k < Math.min(attackerDice.length, defenderDice.length); k++) {
			if (attackerDice[k].compareTo(defenderDice[k]) <= 0) {
				attackerArmies -= 1;
			}
			if (attackerDice[k].compareTo(defenderDice[k]) > 0) {
				defenderArmies -= 1;
			}
		}
	}

	private void sortDice(Integer[] dice) {
		Arrays.sort(dice, Collections.reverseOrder());
	}
}
