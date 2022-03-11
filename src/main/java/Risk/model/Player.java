package Risk.model;

import java.util.ArrayList;

public class Player {
	int armies;
	int playerId;
	int territories;
	int countries;
	boolean caughtTerritory = false;
	ArrayList<Card> playerdeck;

	public Player() {
		armies = 0;
		territories = 0;
		playerdeck = new ArrayList<Card>();

	}
	
	public void setDeck(ArrayList<Card> deck) {
		this.playerdeck = deck;
		
	}

	public Player(int id) {
		armies = 0;
		playerId = id;
		territories = 0;
		playerdeck = new ArrayList<Card>();

	}

	/*
	 * sets the attacker or defender boolean to the opposite of what it was before
	 */

	public void addPlayerArmies(int armies) {
		if (armies < 0) {
			throw new IllegalArgumentException("cannot add negative armies");
		}

		if ((this.armies + armies) > 90 || (this.armies + armies) < 0) {
			throw new IllegalArgumentException("Invalid Army Count");

		}

		this.armies += armies;

	}

	public void removePlayerArmies(int armies) {
		if (armies < 0) {
			throw new IllegalArgumentException("cannot input negative armies");
		}

		if ((this.armies - armies) < 0) {
			throw new IllegalArgumentException("Invalid Army Count");

		}
		this.armies -= armies;

	}

	public int getPlayerArmies() {
		return this.armies;
	}

	public void addTerritory() {
		if ((this.territories) >= 42) {
			throw new IllegalArgumentException("Player owns too many territories");
		}

		this.territories += 1;

	}

	public int getTerritories() {
		return territories;
	}

	public void removeTerritory() {
		if ((this.territories - 1) < 0) {
			throw new IllegalArgumentException("Player can't own negative territories");
		}

		territories -= 1;

	}

	public int getId() {
		return this.playerId;
	}
	
	public ArrayList<Card> getDeck() {
		return this.playerdeck;
	}

	public void caughtTerritory(boolean val) {
		this.caughtTerritory = val;
	}

	public boolean hasCaughtTerritory() {
		return this.caughtTerritory;
	}

	public void addCardToPlayer(Card card1) {
		if (this.playerdeck.size() == 6) {
			throw new IllegalArgumentException("player deck is full");
		}
		
		this.playerdeck.add(card1);
		
	}

}
