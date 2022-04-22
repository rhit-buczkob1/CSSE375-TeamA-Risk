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

		changeGuiButtons(false, true, true, true);
	}

	public void connectToGui() {
		this.gui.nextTurn.setOnMouseClicked(new NextButtonListener(this));
		this.gui.addArmy.setOnAction(new AddTerritoryListener(this));
		this.gui.attack.setOnMouseClicked(new AttackListener(this));
		this.gui.language.setOnMouseClicked(new PopUpLauncher(this));
		this.gui.setNumPlayers.setOnMouseClicked(new PlayerSetListener(this));
		this.gui.setMouseListener(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				String clicked = gui.checkForPointOnTerritory(new Point2D(event.getX(), event.getY()));
				gui.paintTerritoryBounds();
				if (!clicked.equals("")) {
					Territory territory = gbcontroller.getTerritory(clicked.replace('_', ' '));

					gui.setTerritoryArmyCount(territory.getArmyCount());
					gui.setCurrentTerritoryOwner(territory.getPlayer());
					if (phaseController.getPhase().equals("attack")) {
						gui.changeAttackButton(false);
					} else if ((territory.getPlayer() == playercontroller.getCurrentPlayer().getId() ||
								territory.getPlayer() == 0) && playercontroller.getCurrentPlayer().getPlayerArmies() != 0) {
						gui.changeAddArmyButton(false);
					} else if (!phaseController.getPhase().equals("fortify")){
						gui.changeAddArmyButton(true);
					}
				} else {
					gui.changeAttackButton(true);
					gui.changeAddArmyButton(true);
				}
			}
			
		});
		this.gui.spendCards.setOnMouseClicked(new CardListener(this));
	}

	public void addInfantrytoTerritoryfromString(String string) {
		Territory territory = gbcontroller.getTerritory(string);

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

		this.attackingTerritory = gbcontroller.getTerritory(string);
		this.defendingTerritory = gbcontroller.getTerritory(string2);
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
		if (this.gbcontroller.getTerritoryOwner(defendingTerritory.getName()) == this.playercontroller.getCurrentPlayer().getId()) {
			this.gui.setTerritoryColor(defendingTerritory.getName(), this.playercontroller.getCurrentPlayer().getId());
			gui.currentTerritoryArmyCount = defendingTerritory.getArmyCount();
			gui.currentTerritoryPlayer = defendingTerritory.getPlayer();
			this.gui.paintTerritoryBounds();
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
		this.gui.currentPhase.setText(this.messages.getString(phaseController.getPhase()));
		if (!gui.testMode) {
			gui.paintTerritoryBounds();
		}
		switch (phaseController.getPhase()) {
			case "assignment":
				changeGuiButtons(false, true, true, false);
				break;
			case "attack":
				changeGuiButtons(true, false, false, true);
				break;
			case "fortify":
				changeGuiButtons(false, false, true, false);
				break;
		}
	}

	public void changeGuiButtons(boolean addArmy, boolean nextTurn,
								 boolean attack, boolean spendCards) {
		this.gui.changeAddArmyButton(addArmy);
		this.gui.changeNextTurnButton(nextTurn);
		this.gui.changeAttackButton(attack);
		this.gui.changeSpendCardsButton(spendCards);
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
}