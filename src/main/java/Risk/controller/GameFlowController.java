package Risk.controller;

import Risk.model.Card;
import Risk.model.Territory;
import Risk.view.GraphicalUserInterface;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class GameFlowController {
	PhaseController phaseController;
	PlayerController playercontroller;
	String phase;
	GameBoardController gbcontroller;
	GraphicalUserInterface gui;
	AttackerDefenderController adcontroller;
	public static Random rand = new Random();
	
	ResourceBundle messages;

	public GameFlowController(PhaseController phaseController, PlayerController playercontroller,
				  GameBoardController gbcontroller,
				  AttackerDefenderController adc,
				  GraphicalUserInterface gui,
				  ResourceBundle msg) {
		this.phaseController = phaseController;
		this.playercontroller = playercontroller;
		this.phase = "setup";
		this.gbcontroller = gbcontroller;
		this.adcontroller = adc;
		this.gui = gui;
		this.messages = msg;
		connectToGui();
	}

	public void connectToGui() {
		this.gui.nextTurn.addActionListener(new NextButtonListener(this));
		this.gui.addArmy.addActionListener(new AddTerritoryListener(this));
		this.gui.attack.addActionListener(new AttackListener(this));
		this.gui.language.addActionListener(new PopUpLauncher(this));
		this.gui.setMouseListener(new ClickListener(this));
		this.gui.spendCards.addActionListener(new CardListener(this));
	}

	public void addInfantrytoTerritoryfromString(String string) {
		Territory territory = gbcontroller.getTerritory(string);

		playercontroller.addInfantryToTerritory(territory, 1);
	}

	public void updatePhase() {
		this.phase = phaseController.getPhase();
	}

	public void initiateCombat(String string, String string2, Random rand2, int attackerdice, int defenderdice) {
		this.phase = phaseController.getPhase();
		if (!this.phase.equals("attack")) {
			throw new IllegalArgumentException("It's not Attack Phase");
		}

		Territory attackingTerritory = gbcontroller.getTerritory(string);
		Territory defendingTerritory = gbcontroller.getTerritory(string2);
		adcontroller.setAttacker(playercontroller.getCurrentPlayer());
		adcontroller.setDefender(playercontroller.getPlayer(defendingTerritory.getPlayer()));
		adcontroller.setAttackingTerritory(attackingTerritory);
		adcontroller.setDefendingTerritory(defendingTerritory);

		adcontroller.setArmies(attackerdice);
		adcontroller.combat(attackerdice, defenderdice, rand2);
		playercontroller.playerLoss();

		if (this.gbcontroller.getTerritoryOwner(string2) == this.playercontroller.getCurrentPlayer().getId()) {
			this.gui.setTerritoryColor(string2, this.playercontroller.getCurrentPlayer().getId());
			gui.currentTerritoryArmyCount = defendingTerritory.getArmyCount();
			gui.currentTerritoryPlayer = defendingTerritory.getPlayer();
			this.gui.component.repaint();
		}
		
		if (playercontroller.getNumberOfPlayers() == 1) {
			gui.victoryScreen();
		}
	}

	public boolean verifyOwnership(String territoryName) {
		int player = this.playercontroller.getCurrentPlayer().getId();
		return player == this.gbcontroller.getTerritoryOwner(territoryName);
	}

	public boolean verifyAdjacent(String firstName, String secondName) {
		return this.gbcontroller.isAdjacent(firstName, secondName);
	}

	public void next_phase() {
		if (phaseController.getUpdateCards()) {
			updateCardsOnGui();
		}
		phaseController.next_phase();
		if (phaseController.getUpdateCards()) {
			updateCardsOnGui();
		}
		this.gui.currentPhase = this.messages.getString(phaseController.getPhase());
		if (!gui.testMode) {
			gui.component.repaint();
		}
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
		if (this.gbcontroller.checkOwnedTerritory(card1.getTerritory(), card2.getTerritory(), card3.getTerritory(),
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
		this.gui.card1.removeAllItems();
		this.gui.card2.removeAllItems();
		this.gui.card3.removeAllItems();
		ArrayList<Card> deckToUpdate = this.playercontroller.getCurrentPlayer().getDeck();
		for (Card card : deckToUpdate) {
			this.gui.card1.addItem(convertCardForGui(card));
			this.gui.card2.addItem(convertCardForGui(card));
			this.gui.card3.addItem(convertCardForGui(card));
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

	public String checkForPointOnTerritory() {
		return gui.checkForPointOnTerritory();
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

	public void performCardTransaction() {
		int card1Index = gui.card1.getSelectedIndex();
		int card2Index = gui.card2.getSelectedIndex();
		int card3Index = gui.card3.getSelectedIndex();
		ArrayList<Card> availableCards = playercontroller.getCurrentPlayer().getDeck();
		turnInCards(availableCards.get(card1Index),
				availableCards.get(card2Index),
				availableCards.get(card3Index));
		gui.setCurrentPlayerArmies(Integer.toString(playercontroller.getCurrentPlayer().getPlayerArmies()));
		gui.setCurrentPlayer(String.valueOf(playercontroller.getCurrentPlayer().getId()));
		gui.component.repaint();
	}

	public boolean clickedOnValidLocation() {
			return !(gui.clickedTerritory.equals(""));
	}

	public void fortifyTerritory(String fromTerritory, String toTerritory) {
		try {
			playercontroller.moveArmy(gbcontroller.getTerritory(fromTerritory), gbcontroller.getTerritory(toTerritory), 1);
			gui.territoryArmiesNumber
					.setText(String.valueOf(gbcontroller.getTerritory(gui.clickedTerritory).getArmyCount()));
		} catch (IllegalArgumentException e1) {
			System.err.println(e1.getMessage());
		}
	}

	public void addArmy() {
		int player = playercontroller.getCurrentPlayer().getId();
		addInfantrytoTerritoryfromString(gui.clickedTerritory);
		if (player == gbcontroller.getTerritoryOwner(gui.clickedTerritory)) {
			gui.setTerritoryColor(gui.clickedTerritory, player);
		}
		Territory territory = gbcontroller.getTerritory(gui.clickedTerritory);

		gui.setCurrentPlayerArmies(Integer.toString(playercontroller.getCurrentPlayer().getPlayerArmies()));
		gui.setCurrentPlayer(String.valueOf(playercontroller.getCurrentPlayer().getId()));
		gui.currentTerritoryArmyCount = territory.getArmyCount();
		gui.currentTerritoryPlayer = territory.getPlayer();
		gui.component.repaint();
	}

	public void next() {
		next_phase();
		gui.setCurrentPlayerArmies(Integer.toString(playercontroller.getCurrentPlayer().getPlayerArmies()));
		gui.setCurrentPlayer(String.valueOf(playercontroller.getCurrentPlayer().getId()));
	}
}
