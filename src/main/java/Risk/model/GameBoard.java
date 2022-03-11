package Risk.model;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

public class GameBoard {

	public Set<Continent> continents = new HashSet<>();
	Deck mainDeck;
	private static GameBoard gameBoard;

	public static GameBoard getGameBoard() {
		if (gameBoard == null) {
			gameBoard = new GameBoard();
		}
		return gameBoard;
	}
	
	public Deck getDeck() {
		return mainDeck;
	}

	public void addContinent(Continent continent) {
		continents.add(continent);
	}

	@Override
	public String toString() {
		return "GameBoard{" + "continents=" + continents + '}';
	}

	public void addArmiesToTerritory(Territory territory, int armies) {
		if (doesTerritoryExist(territory)) {
			territory.addArmies(armies);
		} else {
			throw new IllegalArgumentException("No Territory Found");
		}

	}

	public int getTerritoryArmies(Territory territory) {
		if (doesTerritoryExist(territory)) {
			return territory.getArmyCount();
		} else {
			throw new IllegalArgumentException("No Territory Found");
		}
	}

	public int getContinentPlayer(Continent continent) {
		if (doesContinentExist(continent)) {
			return continent.getPlayer();
		} else {
			throw new IllegalArgumentException("No Continent Found");
		}
	}

	public void setPlayer(Territory territory, int player) {
		if (doesTerritoryExist(territory)) {
			territory.setPlayer(player);
		} else {
			throw new IllegalArgumentException("No Territory Found");
		}
	}

	public int getTerritoryPlayer(Territory territory) {
		if (doesTerritoryExist(territory)) {
			return territory.getPlayer();
		} else {
			throw new IllegalArgumentException("No Territory Found");
		}
	}

	private boolean doesContinentExist(Continent continent) {
		return continents.contains(continent);
	}

	private boolean doesTerritoryExist(Territory territory) {
		for (Continent continent : continents) {
			if (continent.isTerritoryExist(territory)) {
				return true;
			}
		}
		return false;
	}

	public Territory getTerritoryFromString(String name) {
		for (Continent continent : this.continents) {
			for (Territory territory : continent.territories) {
				if (territory.name.equals(name)) {
					return territory;
				}
			}

		}
		throw new IllegalArgumentException("No Territory Found");
	}

	public boolean isAdjacent(String firstTerritory, String secondTerritory) {
		Territory first = this.getTerritoryFromString(firstTerritory);
		Territory second = this.getTerritoryFromString(secondTerritory);
		return first.neighboringterritories.contains(second);
	}

	public void setDeck(Deck deck) {
		this.mainDeck = deck;

	}

	public Card drawFromMainDeck() {
		Card cardToReturn;
		try {
			cardToReturn = this.mainDeck.drawCard();
		} catch (Exception e) {
			throw new NoSuchElementException("Main Deck Empty");
		}
		return cardToReturn;
	}

	public Continent getContinentFromString(String string) {
		for (Continent continent : this.continents) {
			if (continent.getName().equals(string)) {
				return continent;
			}
		}
		throw new IllegalArgumentException("No Continent Found");
	}

}
