package Risk.controller;

import Risk.model.*;
import Risk.view.GraphicalUserInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoardController {

	GameBoard gameBoard = GameBoard.getGameBoard();
	Deck gameBoardDeck = new Deck();
	int tradeCounter = 4;
	public String map = "";
	GameBoardTerritoryController territoryController;

	public void initGame() {

		// TODO: (maybe) only invoke the init method
		// when the corresponding GUI action is detected
		loadGameBoard();
		territoryController = new GameBoardTerritoryController(gameBoard);
		territoryController.map = map;
		territoryController.loadTerritoryNeighboring();
		populateGameBoardDeckTroops();
		// other setup if needed...
	}

	public void loadGameBoard() {

		String continentLineHeader = "[continent]";
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader("src/main/resources/mapdata"+map));
			String line = reader.readLine();
			int continentvalue = 0;
			String currentContinent = "";
			ArrayList<Territory> continentTerritories = new ArrayList<Territory>();
			while (line != null) {

				line = line.trim();
				if (line.contains(continentLineHeader)) {

					if (!continentTerritories.isEmpty()) {
						Continent continent = new Continent(continentTerritories, currentContinent);
						continent.setValue(continentvalue);

						gameBoard.addContinent(continent);
					}

					currentContinent = line.substring(continentLineHeader.length(), line.length() - 2);
					continentvalue = Integer.parseInt(String.valueOf(line.charAt(line.length() - 1)));

					continentTerritories = new ArrayList<Territory>();

				} else if (!line.isEmpty()) {
					String territoryName = line;

					Territory territory = new Territory(territoryName);
					continentTerritories.add(territory);
					Card cardToAdd = new Card(territoryName);
					this.gameBoardDeck.addCard(cardToAdd);
				}

				line = reader.readLine();
			}

			if (!currentContinent.isEmpty()) {
				Continent continent = new Continent(continentTerritories, currentContinent);
				continent.setValue(continentvalue);
				gameBoard.addContinent(continent);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}



	public void updatePlayer(Continent continent) {

		continent.setPlayer(continent.isPlayerControlled());

	}






	public void populateGameBoardDeckTroops() {
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader("src/main/resources/CardTroopData"+map));
			String line = reader.readLine();
			int index = 0;
			while (line != null) {
				this.gameBoardDeck.cards.get(index).troopType = line;
				index++;
				line = reader.readLine();
			}
			this.gameBoardDeck.shuffle();
			gameBoard.setDeck(this.gameBoardDeck);
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getNewContinentPlayerArmies(int i) {
		if (i < 1 || i > 4) {
			throw new IllegalArgumentException("invalid player");

		}
		int toreturn = 0;
		for (Continent continent : this.gameBoard.continents) {
			if (i == continent.getPlayer()) {
				toreturn += continent.getValue();

			}
		}
		return toreturn;

	}


	public void updateGameBoard() {
		for (Continent continent : this.gameBoard.continents) {
			updatePlayer(continent);
		}

	}

	public int getTradeCounter() {
		return this.tradeCounter;
	}

	public void incrementTradeCounter() {
		if (this.tradeCounter == 55) {
			throw new IllegalArgumentException("Out of cards to trade in");
		}
		if (this.tradeCounter == 12) {
			this.tradeCounter = 15;
		} else if (this.tradeCounter >= 15) {
			this.tradeCounter += 5;
		} else {
			this.tradeCounter += 2;
		}

	}





    public void transferAllTerritories(int fromId, int toId, GraphicalUserInterface gui) {
		for(Continent continent : this.gameBoard.continents) {
			for(Territory territory : continent.territories) {
				if(territory.getPlayer() == fromId) {
					territory.setPlayer(toId);
					gui.setTerritoryColor(territory.getName(), toId);
				}
			}
		}
    }
}
