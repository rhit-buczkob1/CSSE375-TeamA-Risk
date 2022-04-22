package Risk.controller;

import Risk.model.Continent;
import Risk.model.GameBoard;
import Risk.model.Territory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GameBoardTerritoryController {
    GameBoard gameBoard;
    String map;
    public GameBoardTerritoryController(GameBoard gameBoard){
        this.gameBoard = gameBoard;
    }

    public void loadTerritoryNeighboring() {
        String territoryLineHeader = "[Territory]";
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader("src/main/resources/TerritoryData"+map));
            String line = reader.readLine();

            String currentTerritory = "";
            ArrayList<Territory> neighboringTerritories = new ArrayList<Territory>();
            while (line != null) {

                line = line.trim();
                if (line.contains(territoryLineHeader)) {

                    if (!neighboringTerritories.isEmpty()) {

                        Territory territory = this.gameBoard.getTerritoryFromString(currentTerritory);
                        for (Territory neighboring : neighboringTerritories) {
                            territory.addNeighboring(neighboring);
                        }

                    }

                    currentTerritory = line.substring(territoryLineHeader.length());
                    neighboringTerritories = new ArrayList<Territory>();

                } else if (!line.isEmpty()) {
                    String territoryName = line;
                    Territory neighboring = this.gameBoard.getTerritoryFromString(territoryName);
                    neighboringTerritories.add(neighboring);

                }

                line = reader.readLine();
            }

            if (!currentTerritory.isEmpty()) {

                Territory territory = this.gameBoard.getTerritoryFromString(currentTerritory);
                for (Territory neighboring : neighboringTerritories) {
                    territory.addNeighboring(neighboring);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int getTerritoryOwner(String territoryName) {
        Territory territory = this.gameBoard.getTerritoryFromString(territoryName);
        int player = this.gameBoard.getTerritoryPlayer(territory);

        return player;
    }

    public boolean isAdjacent(String firstTerritory, String secondTerritory) {
        return this.gameBoard.isAdjacent(firstTerritory, secondTerritory);
    }

    public Territory getTerritory(String territoryName) {
        return gameBoard.getTerritoryFromString(territoryName);
    }

    public Boolean checkOwnedTerritory(String territory1, String territory2, String territory3, int player) {
        for (Continent continent : this.gameBoard.continents) {
            for (Territory territory : continent.territories) {
                if (territory.getName().equals(territory1) || territory.getName().equals(territory2)
                        || territory.getName().equals(territory3)) {
                    if (territory.getPlayer() == player) {
                        return true;
                    }
                }

            }

        }
        return false;
    }
}
