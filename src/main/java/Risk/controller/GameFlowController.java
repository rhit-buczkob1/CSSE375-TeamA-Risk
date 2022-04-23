package Risk.controller;

import Risk.model.Card;
import Risk.model.Territory;
import Risk.view.GraphicalUserInterface;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class GameFlowController {
	PhaseController phaseController;
	PlayerController playercontroller;
	private String phase;
	GameBoardController gbcontroller;
	GraphicalUserInterface gui;
	AttackerDefenderController adcontroller;
	Territory attackingTerritory;
	Territory defendingTerritory;
	GameBoardTerritoryController territoryController;
	public static Random rand = new Random();

	ResourceBundle messages;
	String gameMode = "NORMAL";

	public GameFlowController(PhaseController phaseController, PlayerController playercontroller,
							  GameBoardController gbcontroller,
							  AttackerDefenderController adc,
							  GraphicalUserInterface gui,
							  ResourceBundle msg) {
		this.phaseController = phaseController;
		this.playercontroller = playercontroller;
		this.phase = "setup";
		this.gbcontroller = gbcontroller;
		this.territoryController = gbcontroller.territoryController;
		this.adcontroller = adc;
		this.gui = gui;
		this.messages = msg;
		connectToGui();

		changeGuiButtons(true, true, true, true, true);
	}

	public void connectToGui() {
		this.gui.nextTurn.setOnMouseClicked(new NextButtonListener(this));
		AddTerritoryListener addListener = new AddTerritoryListener(this);
		this.gui.addArmy.setOnAction(addListener);
		this.gui.moveFrom.setOnAction(addListener.new MoveListener());
		AttackFromListener attackListener = new AttackFromListener(this);
		this.gui.attackFrom.setOnMouseClicked(attackListener);
		this.gui.attack.setOnMouseClicked(attackListener.new AttackListener());
		this.gui.language.setOnMouseClicked(new PopUpLauncher(this));
		this.gui.chooseGameMode.setOnMouseClicked(new GameModePopUpLauncher(this));
		this.gui.setNumPlayers.setOnMouseClicked(new PlayerSetListener(this));
		this.gui.setMouseListener(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String clicked = gui.checkForPointOnTerritory(new Point2D(event.getX(), event.getY()));
				gui.paintTerritoryBounds();
				if (!clicked.equals("")) {
					Territory territory = territoryController.getTerritory(clicked.replace('_', ' '));

					gui.setTerritoryArmyCount(territory.getArmyCount());
					gui.setCurrentTerritoryOwner(territory.getPlayer());
					if (phaseController.getPhase().equals("attack") && (territory.getPlayer() == playercontroller.getCurrentPlayer().getId())) {
						gui.changeAttackFromButton(false);
					} else if (phaseController.getPhase().equals("attack") && (territory.getPlayer() != playercontroller.getCurrentPlayer().getId())
							&& (!gui.attackingTerritory.equals(""))) {
						changeGuiButtons(true, false, false, true, true);
					} else if ((territory.getPlayer() == playercontroller.getCurrentPlayer().getId() ||
								territory.getPlayer() == 0) && playercontroller.getCurrentPlayer().getPlayerArmies() != 0) {
						gui.changeAddArmyButton(false);
					} else if (!phaseController.getPhase().equals("fortify")){
						if (!phaseController.getPhase().equals("assignment") || playercontroller.getCurrentPlayer().getPlayerArmies() != 0)
							gui.changeAddArmyButton(true);
					} else if (phaseController.getPhase().equals("fortify")) {
						if (gui.clickedTerritory.getText().equals("") || !verifyOwnership(gui.clickedTerritory.getText())) {
							changeGuiButtons(true, false, true, true, true);
							gui.changeMoveFrom(true);
						} else {
							if (!gui.transportingTerritory.equals("")) {
								changeGuiButtons(false, false, true, true, true);
								gui.changeMoveFrom(false);
							} else {
								changeGuiButtons(true, false, true, true, true);
								gui.changeMoveFrom(false);
							}
						}
					}
				} else {
					gui.changeAttackButton(true);
					gui.changeAddArmyButton(true);
					gui.changeAttackFromButton(true);
				}
			}
			
		});
		this.gui.spendCards.setOnMouseClicked(new CardListener(this));
	}

	public void addInfantrytoTerritoryfromString(String string) {
		Territory territory = territoryController.getTerritory(string);

		playercontroller.addInfantryToTerritory(territory, 1);
	}

	public void updatePhase() {
		this.phase = phaseController.getPhase();
	}

	public void initiateCombat(String string, String string2) {
		this.phase = phaseController.getPhase();
		if (!this.phase.equals("attack")) {
			throw new IllegalArgumentException("It's not Attack Phase");
		}

		this.attackingTerritory = territoryController.getTerritory(string);
		this.defendingTerritory = territoryController.getTerritory(string2);
		adcontroller.setAttacker(playercontroller.getCurrentPlayer());
		adcontroller.setDefender(playercontroller.getPlayer(defendingTerritory.getPlayer()));
		adcontroller.setAttackingTerritory(attackingTerritory);
		adcontroller.setDefendingTerritory(defendingTerritory);
	}

	public void randomizeCombat(Random rand2, int attackerdice, int defenderdice){
		adcontroller.setArmies(attackerdice);
		adcontroller.combat(attackerdice, defenderdice, rand2);
		playercontroller.playerLoss();
	}

	public void finishCombat(){
		if (this.territoryController.getTerritoryOwner(defendingTerritory.getName()) == this.playercontroller.getCurrentPlayer().getId()) {
			this.gui.setTerritoryColor(defendingTerritory.getName(), this.playercontroller.getCurrentPlayer().getId());
			gui.currentTerritoryArmyCount = defendingTerritory.getArmyCount();
			gui.currentTerritoryPlayer = defendingTerritory.getPlayer();
			if(defendingTerritory.isHQ) {
				gbcontroller.transferAllTerritories(this.adcontroller.defender.getId(), this.playercontroller.getCurrentPlayer().getId(), gui);
				playercontroller.players.remove(this.adcontroller.defender);
			}
			this.gui.paintTerritoryBounds();
		}

		if (playercontroller.getNumberOfPlayers() == 1) {
			gui.victoryScreen();
		}
	}

	public boolean verifyOwnership(String territoryName) {
		int player = this.playercontroller.getCurrentPlayer().getId();
		return player == this.territoryController.getTerritoryOwner(territoryName);
	}

	public boolean verifyAdjacent(String firstName, String secondName) {
		return this.territoryController.isAdjacent(firstName, secondName);
	}

	public void next_phase() {
		if (phaseController.getUpdateCards()) {
			updateCardsOnGui();
		}
		phaseController.next_phase();
		if (phaseController.getUpdateCards()) {
			updateCardsOnGui();
		}
		this.gui.currentPhase.setText(this.messages.getString(phaseController.getPhase()));
		if (!gui.testMode) {
			gui.paintTerritoryBounds();
		}
		switch (phaseController.getPhase()) {
			case "assignment":
				if (gui.clickedTerritory.getText().equals("") || !verifyOwnership(gui.clickedTerritory.getText())) 
					changeGuiButtons(true, true, true, false, true);
				else changeGuiButtons(false, true, true, false, true);
				gui.changeMoveFrom(true);
				gui.transportingTerritory = "";
				gui.paintTerritoryBounds();
				break;
			case "attack":
				if (gui.clickedTerritory.getText().equals("") || !verifyOwnership(gui.clickedTerritory.getText())) 
					changeGuiButtons(true, false, true, true, true);
				else changeGuiButtons(true, false, true, true, false);
				break;
			case "fortify":
				if (gui.clickedTerritory.getText().equals("") || !verifyOwnership(gui.clickedTerritory.getText())) {
					changeGuiButtons(true, false, true, true, true);
					gui.changeMoveFrom(true);
				} else {
					if (!gui.transportingTerritory.equals("")) {
						changeGuiButtons(false, false, true, true, true);
						gui.changeMoveFrom(false);
					} else {
						changeGuiButtons(true, false, true, true, true);
						gui.changeMoveFrom(false);
					}
				}
				gui.attackingTerritory = "";
				gui.paintTerritoryBounds();
				break;
		}
	}

	public void changeGuiButtons(boolean addArmy, boolean nextTurn,
								 boolean attack, boolean spendCards, boolean attackFrom) {
		this.gui.changeAddArmyButton(addArmy);
		this.gui.changeNextTurnButton(nextTurn);
		this.gui.changeAttackButton(attack);
		this.gui.changeSpendCardsButton(spendCards);
		this.gui.changeAttackFromButton(attackFrom);
	}

	public String getPhase() {
		this.phase = phaseController.getPhase();
		return this.phase;
	}

	public void turnInCards(Card card1, Card card2, Card card3) {
		if (card1.equals(card2) || card2.equals(card3) || card1.equals(card3)) {
			throw new IllegalArgumentException("Cannot play duplicate cards");
		}

		try {
			this.playercontroller.spendCards(card1, card2, card3);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid cards played");
		}
		this.playercontroller.addArmiesToCurrentPlayer(this.gbcontroller.getTradeCounter());
		if (this.territoryController.checkOwnedTerritory(card1.getTerritory(), card2.getTerritory(), card3.getTerritory(),
				this.playercontroller.getCurrentPlayer().getId())) {
			this.playercontroller.addArmiesToCurrentPlayer(2);
		}

		this.gbcontroller.incrementTradeCounter();
		this.updateCardsOnGui();
	}

	public String convertCardForGui(Card toconvert) {
		String territoryToDisplay = messages.getString(toconvert.getTerritory().replace(" ", "_"));
		String troopToDisplay = messages.getString(toconvert.getTroopType().toLowerCase());
		String toreturn = "<html>" + territoryToDisplay + "<br>" + troopToDisplay + "<html/>";
		return toreturn;
	}

	public void updateCardsOnGui() {
		this.gui.card1 = new ComboBox<String>();
		this.gui.card2 = new ComboBox<String>();
		this.gui.card3 = new ComboBox<String>();
		ArrayList<Card> deckToUpdate = this.playercontroller.getCurrentPlayer().getDeck();
		for (Card card : deckToUpdate) {
			this.gui.card1.getItems().add(convertCardForGui(card));
			this.gui.card2.getItems().add(convertCardForGui(card));
			this.gui.card3.getItems().add(convertCardForGui(card));

		}
	}

	public String getMessage(String key) {
		return messages.getString(key);
	}

	public void setMessages(ResourceBundle messagesBundle) {
		messages = messagesBundle;
	}

	public void setLanguage() {
		gui.setLanguage(messages, getPhase());
	}

	public Territory getTerritory(String lastclickedstring) {
		return gbcontroller.getTerritory(lastclickedstring);
	}

	public void setCurrentTerritoryArmyCount(int armyCount) {
		gui.currentTerritoryArmyCount = armyCount;
	}

	public void setCurrentTerritoryPlayer(int player) {
		gui.currentTerritoryPlayer = player;
	}

	public boolean clickedOnValidLocation() {
		return !(gui.clickedTerritory.equals(""));
	}

	public void fortifyTerritory(String fromTerritory, String toTerritory) {
		try {
			playercontroller.moveArmy(gbcontroller.getTerritory(fromTerritory), gbcontroller.getTerritory(toTerritory), 1);
			gui.territoryArmiesNumber
					.setText(String.valueOf(gbcontroller.getTerritory(gui.clickedTerritory.getText()).getArmyCount()));
		} catch (IllegalArgumentException e1) {
			System.err.println(e1.getMessage());
		}
	}

	public void addArmy() {
		int player = playercontroller.getCurrentPlayer().getId();
		addInfantrytoTerritoryfromString(gui.clickedTerritory.getText());
		if (player == gbcontroller.getTerritoryOwner(gui.clickedTerritory.getText())) {
			gui.setTerritoryColor(gui.clickedTerritory.getText(), player);
		}
		Territory territory = gbcontroller.getTerritory(gui.clickedTerritory.getText());

		gui.setCurrentPlayerArmies(Integer.toString(playercontroller.getCurrentPlayer().getPlayerArmies()));
		gui.setCurrentPlayer(String.valueOf(playercontroller.getCurrentPlayer().getId()));
		gui.currentTerritoryArmyCount = territory.getArmyCount();
		gui.currentTerritoryPlayer = territory.getPlayer();
		gui.paintTerritoryBounds();
	}

	public void next() {
		next_phase();
		gui.setCurrentPlayerArmies(Integer.toString(playercontroller.getCurrentPlayer().getPlayerArmies()));
		gui.setCurrentPlayer(String.valueOf(playercontroller.getCurrentPlayer().getId()));
	}

	public void swapToAlternatePhaseController() {
		String phase = this.phaseController.getPhase();
		this.playercontroller.setGameMode(this.gameMode);
		this.phaseController = new AlternatePhaseController(this.playercontroller, this.gbcontroller);
		this.phaseController.setPhase(phase);
	}
}