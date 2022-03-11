package Risk.view;

import Risk.controller.GameFlowController.ClickListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GraphicalUserInterface {

	private ClickListener mouseListener;
	public ArrayList<Rectangle> territoriesBounds = new ArrayList<Rectangle>(42);
	public ArrayList<Rectangle> currentBounds = new ArrayList<Rectangle>(42);
	public ArrayList<String> territoryNames = new ArrayList<String>(42);
	public ArrayList<Color> territoryColors = new ArrayList<Color>(42);
	private JFrame frame;
	public int screenWidth = 2000;
	public int screenHeight = 1000;
	public int boxWidthAndHeight = 40;
	public int currentTerritoryArmyCount = 0;
	public int currentTerritoryPlayer = 0;
	private ResourceBundle messages;
	
	public GuiComponent component;
	public JButton nextTurn;
	public JLabel currentPlayer;
	public JLabel currentPlayerId = new JLabel("1");
	public JLabel playerArmies;
	public JLabel playerArmiesNumber = new JLabel("30");
	public JLabel territoryArmies;
	public JLabel territoryArmiesNumber = new JLabel("0");
	public JLabel territoryPlayer;
	public JLabel territoryPlayerNumber = new JLabel("0");
	public JButton attack;
	public JButton language;
	public JLabel info = new JLabel("");
	public JButton addArmy;
	public JLabel attackerDice;
	public JLabel defenderDice;
	public JSlider attackerDiceSlider = new JSlider(1, 3, 1);
	public JSlider defenderDiceSlider = new JSlider(1, 2, 1);
	public JComboBox card1 = new JComboBox();
	public JComboBox card2 = new JComboBox();
	public JComboBox card3 = new JComboBox();
	public JButton spendCards;
	public String currentPhase = "Setup";


	public Point click;
	public String clickedTerritory;
	public boolean testMode = false;
	public int clickedIndex = -1;
	
	

	public GraphicalUserInterface(ResourceBundle msg) {
		this.setUpTerritoryNamesAndLocation("src/main/resources/TerritoryNamesAndLocations.txt");
		this.messages = msg;
		this.click = new Point();
		clickedTerritory = "";
		for (int i = 0; i < 42; i++) {
			territoryColors.add(Color.DARK_GRAY);
		}
		this.language = new JButton(messages.getString("lang"));
		this.nextTurn = new JButton(messages.getString("nextPhase"));
		this.currentPlayer = new JLabel(messages.getString("player"));
		this.playerArmies = new JLabel(messages.getString("armiesInPlayersHands"));
		this.territoryArmies = new JLabel(messages.getString("armiesTerr"));
		this.territoryPlayer = new JLabel(messages.getString("terrPlayer"));
		this.attack = new JButton(messages.getString("attack"));
		this.addArmy = new JButton(messages.getString("addArmy"));
		this.attackerDice = new JLabel(messages.getString("selectAttDice"));
		this.defenderDice = new JLabel(messages.getString("selectDefDice"));
		this.spendCards = new JButton(messages.getString("spendCards"));
	}
	
	public void setLanguage(ResourceBundle msg, String phase) {
		this.messages = msg;
		this.nextTurn.setText(messages.getString("nextPhase"));
		this.currentPlayer.setText(messages.getString("player"));
		this.playerArmies.setText(messages.getString("armiesInPlayersHands"));
		this.territoryArmies.setText(messages.getString("armiesTerr"));
		this.territoryPlayer.setText(messages.getString("terrPlayer"));
		this.attack.setText(messages.getString("attack"));
		this.addArmy.setText(messages.getString("addArmy"));
		this.attackerDice.setText(messages.getString("selectAttDice"));
		this.defenderDice.setText(messages.getString("selectDefDice"));
		this.spendCards.setText(messages.getString("spendCards"));
		this.language.setText(messages.getString("lang"));
		this.frame.setTitle(messages.getString("risk"));
		this.currentPhase = messages.getString(phase);
		this.component.repaint();
	}

	public void setMouseListener(ClickListener listener) {
		this.mouseListener = listener;
	}

	public void setCurrentPlayer(String player) {
		this.currentPlayerId.setText(player);
	}

	public void setCurrentPlayerArmies(String player) {
		this.playerArmiesNumber.setText(player);
	}

	public void setTerritoryArmies(String armies) {
		this.territoryArmiesNumber.setText(armies);
	}

	public void setTerritoryPlayer(String armies) {
		this.territoryPlayerNumber.setText(armies);
	}
	
	public void victoryScreen() {
		if (testMode) {
			return;
		}
		JFrame f = new JFrame(); // creates jframe f

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // this is your screen size

		f.setUndecorated(true); // removes the surrounding border

		ImageIcon image = new ImageIcon("src/main/resources/victoryscreen.jpg"); //imports the image

		JLabel lbl = new JLabel(image); // puts the image into a jlabel

		f.getContentPane().add(lbl); // puts label inside the jframe

		f.setSize(image.getIconWidth(), image.getIconHeight()); // gets h and w of image and sets jframe to the size

		int x = (screenSize.width - f.getSize().width) / 2; // These two lines are the dimensions
		int y = (screenSize.height - f.getSize().height) / 2; // of the center of the screen

		f.setLocation(x, y); // sets the location of the jframe
		f.setVisible(true); // makes the jframe visible
	}

	/*
	 * Returns the name of the territory that the mouse recently clicked on. Returns
	 * the empty string if no territory was clicked.
	 */
	public String checkForPointOnTerritory() {
		click = this.mouseListener.getLastClick();
		if (click == null) {
			return "";
		}
		for (int i = 0; i < territoriesBounds.size(); i++) {
			if (territoriesBounds.get(i) != null
					&& click.x >= territoriesBounds.get(i).x * frame.getWidth() / screenWidth
					&& click.x <= territoriesBounds.get(i).x * frame.getWidth() / screenWidth + frame.getWidth() / 50
							- 1
					&& click.y >= territoriesBounds.get(i).y * frame.getHeight() / screenHeight
					&& click.y <= territoriesBounds.get(i).y * frame.getHeight() / screenHeight + frame.getHeight() / 25
							- 1) {
				System.out.println("the name is " + territoryNames.get(i));
				clickedTerritory = territoryNames.get(i);
				this.clickedIndex  = i;
				clickedTerritory = clickedTerritory.replace('_', ' ');
				return territoryNames.get(i);
			}
		}
		this.clickedIndex = -1;
		return "";
	}

	public void setUpTerritoryNamesAndLocation(String filename) {
		try {
			Scanner scanner = new Scanner(new FileReader(filename));
			int i = 0; // File is set up so that i=0 should happen every 3 runs of the while loop
			int x = 0;
			while (scanner.hasNext()) {
				String line = scanner.next();
				if (i == 0) {
					this.territoryNames.add(line);
				} else if (i == 1) {
					x = Integer.parseInt(line);
				} else if (i == 2) {
					this.territoriesBounds.add(
						new Rectangle(x, Integer.parseInt(line), this.boxWidthAndHeight, this.boxWidthAndHeight));
				}
				i++;
				if (i == 3) {
					i = 0;
				}
			}
			scanner.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initializeFrame() {
		frame = new JFrame();
		frame.setTitle(this.messages.getString("risk"));
		frame.setSize(screenWidth, screenHeight);
		this.component = new GuiComponent(this.territoriesBounds);
		frame.add(component);
		JPanel currentPlayerPanel = new JPanel();
		JPanel currentTerritoryAndCardsPanel = new JPanel();
		
		currentTerritoryAndCardsPanel.add(card1);
		currentTerritoryAndCardsPanel.add(card2);
		currentTerritoryAndCardsPanel.add(card3);
		currentTerritoryAndCardsPanel.add(spendCards);
		
		currentPlayerPanel.setLayout(new GridLayout(2, 6));
		currentPlayerPanel.add(currentPlayer);
		currentPlayerPanel.add(currentPlayerId);
		currentPlayerPanel.add(playerArmies);
		currentPlayerPanel.add(playerArmiesNumber);
		currentPlayerPanel.add(nextTurn);
		currentPlayerPanel.add(attack);
		
		
		attackerDiceSlider.setMajorTickSpacing(1);
		attackerDiceSlider.setMinorTickSpacing(1);
		attackerDiceSlider.setPaintTicks(true);
		attackerDiceSlider.setPaintLabels(true);
		
		defenderDiceSlider.setMajorTickSpacing(1);
		defenderDiceSlider.setMinorTickSpacing(1);
		defenderDiceSlider.setPaintTicks(true);
		defenderDiceSlider.setPaintLabels(true);
		
		currentPlayerPanel.add(attackerDice);
		currentPlayerPanel.add(attackerDiceSlider);

		currentPlayerPanel.add(defenderDice);
		currentPlayerPanel.add(defenderDiceSlider);


		frame.add(currentPlayerPanel, BorderLayout.SOUTH);
		currentTerritoryAndCardsPanel.setLayout(new GridLayout(5, 1));
		currentTerritoryAndCardsPanel.add(territoryPlayer);
		currentTerritoryAndCardsPanel.add(territoryPlayerNumber);
		currentTerritoryAndCardsPanel.add(territoryArmies);
		currentTerritoryAndCardsPanel.add(territoryArmiesNumber);
		currentTerritoryAndCardsPanel.add(addArmy);
		currentTerritoryAndCardsPanel.add(language);
		currentTerritoryAndCardsPanel.setPreferredSize(new Dimension(frame.getWidth() / 8, frame.getHeight()));
		frame.add(currentTerritoryAndCardsPanel, BorderLayout.EAST);
		
		
		this.mouseListener.addComponent(component);
		frame.addMouseListener(this.mouseListener);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public class GuiComponent extends JComponent {

		/**
		 * Auto generated to prevent the waring from not having this.
		 */
		private static final long serialVersionUID = 1L;
		Image background;
		Image original;
		private ArrayList<Rectangle> territoryBounds;

		public GuiComponent(ArrayList<Rectangle> territoryBounds) {
			this.territoryBounds = (ArrayList<Rectangle>) territoryBounds.clone();
			try {
				this.original = ImageIO.read(new File("src/main/resources/background.png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void paint(Graphics g) {
			g.translate(-9, -38);
			this.paintBackgroundImage(g);
			this.paintTerritoryBounds(g);
			this.drawInfoScreen(g);
			g.translate(9, 38);
		}

		private void drawInfoScreen(Graphics g) {
			territoryArmiesNumber.setText(Integer.toString(currentTerritoryArmyCount));
			territoryPlayerNumber.setText(Integer.toString(currentTerritoryPlayer));
			Color previousColor = g.getColor();
			g.setColor(Color.WHITE);
			g.setFont(new Font("TimesRoman", Font.PLAIN, (frame.getWidth()) / 50));
			g.translate(3 * frame.getWidth() / 4, 0);
			g.drawString(currentPhase, 30, 100);
			g.translate(-3 * frame.getWidth() / 4, 0);
			g.setColor(previousColor);
		}

		private void paintTerritoryBounds(Graphics g) {
			Color previous = g.getColor();
			Graphics2D g2 = (Graphics2D) g;
			currentBounds.clear();
			for (int i = 0; i < territoryBounds.size(); i++) {
				Rectangle2D.Double border = new Rectangle2D.Double(
						((double) territoryBounds.get(i).x) * frame.getWidth() / screenWidth - 5,
						((double) territoryBounds.get(i).y) * frame.getHeight() / screenHeight - 5,
						((double) frame.getWidth()) / 50 + 10, ((double) frame.getHeight()) / 25 + 10);
				Rectangle2D.Double rect = new Rectangle2D.Double(
						((double) territoryBounds.get(i).x) * frame.getWidth() / screenWidth,
						((double) territoryBounds.get(i).y) * frame.getHeight() / screenHeight,
						((double) frame.getWidth()) / 50, ((double) frame.getHeight()) / 25);
				g2.setColor(Color.DARK_GRAY);
				if (clickedIndex == i) {
					g2.setColor(Color.WHITE);
				}
				g2.fill(border);
				g2.setColor(territoryColors.get(i));
				g2.fill(rect);
			}
			g.setColor(previous);
		}

		private void paintBackgroundImage(Graphics g) {
			this.background = original.getScaledInstance(3 * frame.getWidth() / 4, frame.getHeight(),
					Image.SCALE_DEFAULT);
			g.fillRect(0, 0, 10000, 10000);
			g.drawImage(background, 0, 0, null);
		}
	}

	public Color getColorForPlayer(int player) {
		if (player == 1) {
			return Color.RED;
		} else if (player == 2) {
			return Color.BLUE;
		} else if (player == 3) {
			return Color.YELLOW;
		} else if (player == 4) {
			return Color.GREEN;
		}
		return Color.DARK_GRAY;
	}

	public void setTerritoryColor(String territory, int player) {
		territory = territory.replace(' ', '_');
		Color playerColor = this.getColorForPlayer(player);
		for (int i = 0; i < this.territoryNames.size(); i++) {
			if (this.territoryNames.get(i).equals(territory)) {
				this.territoryColors.set(i, playerColor);
			}
		}
	}

}