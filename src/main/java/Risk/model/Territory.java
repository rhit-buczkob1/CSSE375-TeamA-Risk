package Risk.model;

import java.util.ArrayList;

public class Territory {
	String name;
	int player;
	int armies;
	ArrayList<Territory> neighboringterritories;
	public boolean isHQ = false;

	public Territory(String territoryName) {
		this.name = territoryName;
		this.player = 0;
		this.armies = 0;
		this.neighboringterritories = new ArrayList<Territory>();
	}

	public String getName() {
		return this.name;

	}

	public int getPlayer() {
		return this.player;
	}

	public void setPlayer(int newplayer) {
		if (newplayer > 6 || newplayer < 0) {
			throw new IllegalArgumentException("Invalid player number");
		}
		this.player = newplayer;
	}

	public void addArmies(int i) {
		if ((this.armies + i) > 90 || (this.armies + i) < 0) {
			throw new IllegalArgumentException("Invalid army number");
		}
		this.armies += i;

	}

	public int getArmyCount() {
		return this.armies;
	}

	public ArrayList<String> getNeighboring() {
		ArrayList<String> toreturn = new ArrayList<String>();
		for (Territory entry : this.neighboringterritories) {
			toreturn.add(entry.getName());

		}
		return toreturn;
	}

	public void addNeighboring(Territory territory) {
		this.neighboringterritories.add(territory);
	}

	public void removeArmies(int i) {
		if ((this.armies - i) < 0) {
			throw new IllegalArgumentException("Armies can't be below 0");
		}
		this.armies = this.armies - i;
	}

	public void setArmyCount(int armies) {
		if (armies > 90 || armies < 0) {
			throw new IllegalArgumentException("Invalid Army Number");

		} else {
			this.armies = armies;
		}

	}

	@Override
	public String toString() {
		return "\n\tTerritory{"
				+ "name='"
				+ this.name
				+ '\''
				+ ", player=" + player
				+ ", armies=" + armies
				+ ", neighboringterritories=" + getNeighboring() + '}';
	}

    public void setHQ(boolean isHQ) {
		this.isHQ = true;
    }
}