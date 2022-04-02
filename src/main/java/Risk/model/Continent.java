package Risk.model;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Continent {
	public ArrayList<Territory> territories;
	private int player = 0;
	private String name;
	private int value;

	public Continent(ArrayList<Territory> territories, String name) {
		this.territories = territories;
		this.name = name;
	}

	public int getPlayer() {
		return this.player;
	}
	
	public String getName() {
		if (this.name.isEmpty()) {
			throw new NoSuchElementException("Continent name is empty!");
		}
		return this.name;
	}

	public void setPlayer(int player) {
		if (player > 6 || player < 0) {
			this.player = player;
			throw new IllegalArgumentException("Invalid Player");

		} else {
			this.player = player;
		}

	}

	public int isPlayerControlled() {
		int playerchecker = -1;

		for (Territory territory : territories) {
			if (playerchecker == -1) {
				playerchecker = territory.getPlayer();
			} else {
				if (territory.getPlayer() != playerchecker) {
					return 0;

				}
			}
		}

		return playerchecker;

	}

	public boolean isTerritoryExist(Territory territory) {
		if (!territories.isEmpty()) {
			return territories.contains(territory);
		}

		return false;
	}

	@Override
	public String toString() {
		return "\nContinent{\n"
			+ "\tname='"
			+ name + '\n'
			+ "\tplayer=" + player
			+ '\n' + "\tvalue="
			+ value + '\n'
			+ "\tterritories="
			+ territories + '\'' + '}';
	}

	public void setValue(int i) {
		if (i > 7 || i < 2) {
			throw new IllegalArgumentException("invalid value");
		}
		this.value = i;
	}

	public int getValue() {
		return value;
	}
}
