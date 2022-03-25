package Risk.controller;

import Risk.model.Card;
import Risk.model.Territory;
import Risk.view.GraphicalUserInterface;
import Risk.view.GraphicalUserInterface.GuiComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
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
		this.gui.nextTurn.addActionListener(new NextButtonListener());
		this.gui.addArmy.addActionListener(new AddTerritoryListener());
		this.gui.attack.addActionListener(new AttackListener());
		this.gui.language.addActionListener(new PopUpLauncher());
		this.gui.setMouseListener(new ClickListener());
		this.gui.spendCards.addActionListener(new CardListener());
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

	int currPhase = 0;

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
		currPhase = 0;
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

	// FOR YIJI: These listeners couldn't be TDD'd fully because that would involve
	// GUI mocking, however
	// they delegate all real logic to TDD'd methods in GameFlowController. They
	// mainly just call logic in the gameflowcontroller class and then
	// set gui fields for display. They don't really have any original logic in
	// them, and as thus we believed they were acceptable
	// They follow the MVC because the gui doesn't know about the model/controller
	// it just gets set by the logic and listeners in this controller.

	public class NextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			next_phase();
			gui.setCurrentPlayerArmies(Integer.toString(playercontroller.getCurrentPlayer().getPlayerArmies()));
			gui.setCurrentPlayer(String.valueOf(playercontroller.getCurrentPlayer().getId()));
		}
	}

	public class TurnInCardListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

		}

	}

	public class AttackListener implements ActionListener {

		String attackingterritory = "";

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton toset = (JButton) e.getSource();
			if (phase.equals("attack") && attackingterritory.equals("")) {
				if (!(gui.clickedTerritory.equals(""))) {
					if (!verifyOwnership(gui.clickedTerritory)) {
						return;
					}

					attackingterritory = gui.clickedTerritory;
				}
			} else if (phase.equals("attack")) {
				if (!(gui.clickedTerritory.equals(""))) {
					String attack = this.attackingterritory;
					this.attackingterritory = "";

					initiateCombat(attack, gui.clickedTerritory, rand, gui.attackerDiceSlider.getValue(),
							gui.defenderDiceSlider.getValue());
					Territory defendingTerritory = gbcontroller.getTerritory(gui.clickedTerritory);

					gui.setCurrentPlayerArmies(Integer.toString(playercontroller.getCurrentPlayer().getPlayerArmies()));
					gui.setCurrentPlayer(String.valueOf(playercontroller.getCurrentPlayer().getId()));
					gui.territoryArmiesNumber.setText(String.valueOf(defendingTerritory.getArmyCount()));
					gui.territoryPlayerNumber.setText(String.valueOf(defendingTerritory.getPlayer()));

				}
			}

		}

	}

	public class AddTerritoryListener implements ActionListener {
		String fromTerritory = "";
		String toTerritory = "";

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton toset = (JButton) e.getSource();
			if (phase.equals("fortify") && fromTerritory.equals("")) {
				if (!(gui.clickedTerritory.equals(""))) {
					fromTerritory = gui.clickedTerritory;
				}
			} else if (phase.equals("fortify") && toTerritory.equals("")) {
				if (!(gui.clickedTerritory.equals(""))) {
					toTerritory = gui.clickedTerritory;

					if (!verifyOwnership(fromTerritory) || !verifyOwnership(toTerritory)
							|| !verifyAdjacent(fromTerritory, toTerritory)) {
						fromTerritory = "";
						toTerritory = "";
						return;
					}

				}
			} else if (phase.equals("fortify")) {
				try {
					playercontroller.moveArmy(gbcontroller.getTerritory(fromTerritory),
							gbcontroller.getTerritory(toTerritory), 1);
					gui.territoryArmiesNumber
							.setText(String.valueOf(gbcontroller.getTerritory(gui.clickedTerritory).getArmyCount()));
				} catch (IllegalArgumentException e1) {
					System.err.println(e1.getMessage());
				}

			} else if (toset.getText().equals(messages.getString("addArmy"))) {
				if (!(gui.clickedTerritory.equals(""))) {
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
				} else {
					throw new IllegalArgumentException("Mouse not clicked");
				}

			}

		}
	}
	
	public class CardListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(phase.equals("assignment"))) {
				return;
			}
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
		
	}

	public class ClickListener implements MouseListener {

		private Point lastClick = null;
		private GuiComponent guiComponent;
		private String lastclickedstring;

		/*
		 * Returns the point that was most recently clicked, and then sets lastClick to
		 * null.
		 */
		public Point getLastClick() {
			if (lastClick == null) {
				return null;
			}
			Point toReturn = new Point(lastClick.x, lastClick.y);
			lastClick = null;

			return toReturn;
		}

		@Override
		public void mousePressed(MouseEvent event) {
			int x = event.getX();
			int y = event.getY();
			lastClick = new Point(x, y);
			this.lastclickedstring = gui.checkForPointOnTerritory();
			this.lastclickedstring = this.lastclickedstring.replace("_", " ");
			Territory toupdate = gbcontroller.getTerritory(this.lastclickedstring);
			gui.currentTerritoryArmyCount = toupdate.getArmyCount();
			gui.currentTerritoryPlayer = toupdate.getPlayer();
			if (this.guiComponent != null) {
				this.guiComponent.repaint();
			}
		}

		/*
		 * Unused, but needed to be added to the file due to extends
		 */
		@Override
		public void mouseClicked(MouseEvent event) {
			// TODO Auto-generated method stub

		}

		/*
		 * Unused, but needed to be added to the file due to extends
		 */
		@Override
		public void mouseEntered(MouseEvent event) {
			// TODO Auto-generated method stub

		}

		/*
		 * Unused, but needed to be added to the file due to extends
		 */
		@Override
		public void mouseExited(MouseEvent event) {
			// TODO Auto-generated method stub

		}

		/*
		 * Unused, but needed to be added to the file due to extends
		 */
		@Override
		public void mouseReleased(MouseEvent event) {
			// TODO Auto-generated method stub

		}

		public void addComponent(GuiComponent component) {
			this.guiComponent = component;
		}

	}
	
	public class PopUpLauncher implements ActionListener {
        

		@Override
		public void actionPerformed(ActionEvent arg0) {
			EventQueue.invokeLater(new Runnable() {
				@Override
				public void run() {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception ex) {
						System.err.println(ex.getMessage());
					}

					JPanel panel = new JPanel();
					panel.add(new JLabel(messages.getString("changeLangPrompt")));
					DefaultComboBoxModel model = new DefaultComboBoxModel();
					model.addElement(messages.getString("eng"));
					model.addElement(messages.getString("ger"));
					JComboBox comboBox = new JComboBox(model);
					panel.add(comboBox);

					int result = JOptionPane.showConfirmDialog(
						null,
						panel,
						"Language",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.QUESTION_MESSAGE);

					switch (result) {
					case JOptionPane.OK_OPTION:
						String lang = (String) comboBox.getSelectedItem();
						Locale l;
						if (lang == messages.getString("eng")) {
							l = new Locale("en", "US");
						} else {
							l = new Locale("de", "DE");
						}
						messages = ResourceBundle.getBundle("MessagesBundle", l);
						gui.setLanguage(messages, getPhase());
						updateCardsOnGui();
						break;
						
					default:
						break;
					}
				}
			});
		}
    	}

	public String convertCardForGui(Card toconvert) {
		String territoryToDisplay = messages.getString(toconvert.getTerritory().replace(" ", "_"));
		String troopToDisplay = messages.getString(toconvert.getTroopType().toLowerCase());
		String toreturn = "<html>" + territoryToDisplay + "<br>" + troopToDisplay + "<html/>";
		return toreturn;
	}

	// GUI method so not tdd'd
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
