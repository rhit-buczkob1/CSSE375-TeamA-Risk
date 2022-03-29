package Risk.controller;

import Risk.model.Card;
import Risk.model.Territory;
import Risk.view.GraphicalUserInterface;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class GameFlowController {
	PlayerController playercontroller;
	String phase;
	GameBoardController gbcontroller;
	GraphicalUserInterface gui;
	AttackerDefenderController adcontroller;
	public static Random rand = new Random();
	
	ResourceBundle messages;

	public GameFlowController(PlayerController playercontroller,
				  GameBoardController gbcontroller,
				  AttackerDefenderController adc,
				  GraphicalUserInterface gui,
				  ResourceBundle msg) {
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

	public void initiateCombat(String string, String string2, Random rand2, int attackerdice, int defenderdice) {
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
		String input = this.getPhase();

		switch (input) {

		case "setup":
			if (!this.playercontroller.getInit()) {

				this.assignment_phase();

				return;
			}

			throw new IllegalArgumentException("Init Phase isn't over");

		case "assignment":
			this.updateCardsOnGui();
			boolean donePlacing = this.playercontroller.playerDonePlacingNew();
			boolean doneCards = this.playercontroller.playerDoneWithCards();

			if (donePlacing && doneCards) {
				this.attack_phase();

				return;
			}
			if (!donePlacing) {
				throw new IllegalArgumentException("Player has unplaced armies");
			} else {
				throw new IllegalArgumentException("Player has too many cards in hand");
			}

		case "attack":
			this.fortify_phase();
			return;

		case "fortify":
			this.playercontroller.nextPlayer();
			this.assignment_phase();
			return;

		default:

			throw new IllegalArgumentException("Invalid Phase");

		}

	}

	public String getPhase() {
		return this.phase;
	}

	public void init_turn() {
		this.phase = "setup";
	}

	public void assignment_phase() {
		this.phase = "assignment";
		this.updateCardsOnGui();

		this.playercontroller.addNewArmiestoPlayer();
		int toaddarmies = this.gbcontroller
				.getNewContinentPlayerArmies(this.playercontroller.getCurrentPlayer().getId());
		this.playercontroller.addArmiesToCurrentPlayer(toaddarmies);
		this.gbcontroller.updateGameBoard();
		this.gui.currentPhase = messages.getString(this.phase);
		if (!gui.testMode) {
			this.gui.component.repaint();
		}
	}

	public void attack_phase() {
		this.phase = "attack";
		this.gui.currentPhase = messages.getString(this.phase);
		if (!gui.testMode) {
			this.gui.component.repaint();
		}
	}

	public void fortify_phase() {
		this.phase = "fortify";
		if (this.playercontroller.getCurrentPlayer().hasCaughtTerritory()) {
			this.playercontroller.addCardToCurrentPlayer(this.gbcontroller.gameBoardDeck.drawCard());
			this.playercontroller.getCurrentPlayer().caughtTerritory(false);
		}
		this.updateCardsOnGui();
		this.gui.currentPhase = messages.getString(this.phase);
		if (!gui.testMode) {
			this.gui.component.repaint();
		}
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

	public void setPhase(String phase) {
		this.phase = phase;
	}
}
