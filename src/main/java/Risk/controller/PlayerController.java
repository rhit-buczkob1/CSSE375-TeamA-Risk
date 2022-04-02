package Risk.controller;

import Risk.model.Card;
import Risk.model.Player;
import Risk.model.Territory;

import java.util.ArrayList;

public class PlayerController {

	ArrayList<Player> players = new ArrayList<>();
	int currentIndex = 0;
	boolean initSetup = true;

	public void addPlayer(Player player) {
		this.players.add(player);
	}

	public Player getCurrentPlayer() {
		return players.get(currentIndex);
	}

	public int getcurrentIndex() {
		return this.currentIndex;
	}

	public void initializePlayer() throws Exception {
		if(players.size() < 3){
			throw new Exception("Cannot initialize with fewer than three players");
		}
		else if(players.size() > 6){
			throw new Exception("Cannot initialize with more than six players");
		}
		int numArmiesPerPlayer = 35 - (3 - players.size())*5;

		for (Player player : players) {
			player.addPlayerArmies(numArmiesPerPlayer);
		}
	}

	public void addInfantryToTerritory(Territory territory, int armies) {

		Player currentPlayer = players.get(currentIndex);


		if (territory.getPlayer() == currentPlayer.getId()) {
			transferToOwnedTerritory(territory, armies, currentPlayer);
		}
		if (territory.getPlayer() == 0) {
			transferToNewTerritory(territory, armies, currentPlayer);
		}
		if (territory.getPlayer() != currentPlayer.getId()) {
			return;
		}

		if ((currentIndex == players.size() - 1)
				&& (players.get(currentIndex).getPlayerArmies()
				== 0) && initSetup) {
			initSetup = false;
			nextPlayer();
		}

		if (initSetup) {
			nextPlayer();
		}
	}

	private void transferToOwnedTerritory(Territory territory, int armies, Player currPlayer){
		currPlayer.removePlayerArmies(armies);
		territory.addArmies(armies);
	}

	private void transferToNewTerritory(Territory territory, int armies, Player currPlayer){
		setTerritoryOwnership(territory);
		currPlayer.removePlayerArmies(armies);
		territory.addArmies(armies);
		currPlayer.addTerritory();
	}

	public void setTerritoryOwnership(Territory territory) {
		Player currentPlayer = players.get(currentIndex);
		territory.setPlayer(currentPlayer.getId());
	}

	public void nextPlayer() {
		currentIndex += 1;
		if (currentIndex == players.size()) {
			currentIndex = 0;
		}

	}

	public void setInitSetup(boolean initSetup) {
		this.initSetup = initSetup;
	}

	public int calculateTurnArmies() {
		int toaddarmies = players.get(currentIndex).getTerritories() / 3;
		if (toaddarmies < 4) {
			return 3;
		}
		return toaddarmies;
	}

	public void addNewArmiestoPlayer() {
		players.get(currentIndex).addPlayerArmies(calculateTurnArmies());
	}

	public void moveArmy(Territory territoryFrom, Territory territoryTo, int pieces) {
		ArrayList<String> neighbors = territoryFrom.getNeighboring();
		if (!neighbors.contains(territoryTo.getName())) {
			throw new IllegalArgumentException("Territories are not adjacent.");
		}
		if (territoryFrom.getArmyCount() <= pieces) {
			throw new IllegalArgumentException("Not enough pieces to move.");
		}
		if (pieces <= 0) {
			throw new IllegalArgumentException("Pieces is out of range.");
		}
		territoryFrom.removeArmies(pieces);
		territoryTo.addArmies(pieces);
	}

	public boolean getInit() {
		return this.initSetup;
	}

	public boolean playerDonePlacingNew() {
		return (players.get(currentIndex).getPlayerArmies() == 0);
	}

	public boolean playerDoneWithCards() {
		return (players.get(currentIndex).getDeck().size() < 5);
	}

	public void addArmiesToCurrentPlayer(int i) {
		if (i > 90 || i < 0) {
			throw new IllegalArgumentException("Invalid armies");
		}
		players.get(currentIndex).addPlayerArmies(i);

	}

	public void addCardToCurrentPlayer(Card card) {
		try {
			players.get(currentIndex).addCardToPlayer(card);
		} catch (Exception e) {
			throw new IllegalArgumentException("Current player deck is full");
		}

	}

	public void spendCards(Card card1, Card card2, Card card3) {
		if (this.getCurrentPlayer().getDeck().size() < 3) {
			throw new IllegalArgumentException("Not enough cards in deck");
		}


		if (cardsAreValid(card1, card2, card3)) {

			ArrayList<Card> tocheck
					= new ArrayList<Card>(this.getCurrentPlayer().getDeck());
			for (Card card : tocheck) {
				if (card == card1 || card == card2 || card == card3) {
					this.getCurrentPlayer().getDeck().remove(card);
				}

			}
		} else {
			throw new IllegalArgumentException("Cards don't match");
		}
	}

	public boolean checkAllCardsAreSameType(Card card1, Card card2, Card card3) {
		return card1.troopType.equals(card2.troopType) && card2.troopType.equals(card3.troopType);
	}

	public boolean checkTwoSameAndOneWildcard(Card wild, Card card1, Card card2) {
		return wild.troopType.equals("Wildcard") && card1.troopType.equals(card2.troopType);
	}

	public boolean checkAllCardsAreUniqueType(Card card1, Card card2, Card card3){
		return !card1.troopType.equals(card2.troopType) && !card1.troopType.equals(card3.troopType) && !card2.troopType.equals(card3.troopType);
	}
	private boolean cardsAreValid(Card card1, Card card2, Card card3){
		return (checkAllCardsAreSameType(card1, card2, card3)
				|| checkTwoSameAndOneWildcard(card2, card1, card3)
				|| checkTwoSameAndOneWildcard(card1, card2, card3)
				|| checkTwoSameAndOneWildcard(card3, card1, card2)
				|| checkAllCardsAreUniqueType(card1, card2, card3));
	}

	public ArrayList<Card> getCurrentPlayerCards() {
		return this.getCurrentPlayer().getDeck();
	}

	public void playerLoss() {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).getTerritories() == 0) {
				players.remove(i);
				playerLoss();
			}
		}
	}

	public Player getPlayer(int player) {
		for (Player p : players) {
			if (p.getId() == player) {
				return p;
			}
		}
		throw new NullPointerException();
	}

	public int getNumberOfPlayers() {
		return players.size();
	}

}