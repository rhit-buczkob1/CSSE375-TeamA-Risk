package Risk.view;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
//import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

public class GraphicalUserInterface {

	private EventHandler<MouseEvent> mouseListener;
	public ArrayList<Rectangle> territoriesBounds = new ArrayList<Rectangle>(42);
	public ArrayList<Rectangle> currentBounds = new ArrayList<Rectangle>(42);
	public ArrayList<String> territoryNames = new ArrayList<String>(42);
	public ArrayList<Color> territoryColors = new ArrayList<Color>(42);
	private Stage stage;
	public int screenWidth = 1500;
	public int screenHeight = 750;
	public int boxWidthAndHeight = 40;
	public int currentTerritoryArmyCount = 0;
	public int currentTerritoryPlayer = 0;
	private ResourceBundle messages;
	
//	private int xShift = 9;
//	private int yShift = 38;
	
	public GuiComponent component;
	public Button nextTurn;
	public Text currentPlayer;
	public Text currentPlayerId = new Text("1");
	public Text playerArmies;
	public Text playerArmiesNumber = new Text("30");
	public Text territoryArmies;
	public Text territoryArmiesNumber = new Text("0");
	public Text territoryPlayer;
	public Text territoryPlayerNumber = new Text("0");
	public JButton attack;
	public Button language;
	public JLabel info = new JLabel("");
	public Button addArmy;
	public JLabel attackerDice;
	public JLabel defenderDice;
	public JSlider attackerDiceSlider = new JSlider(1, 3, 1);
	public JSlider defenderDiceSlider = new JSlider(1, 2, 1);
	public JComboBox card1 = new JComboBox();
	public JComboBox card2 = new JComboBox();
	public JComboBox card3 = new JComboBox();
	public JButton spendCards;
	public String currentPhase = "Setup";


	public Text currentTerritoryDesc;
	public Text clickedTerritory;
	public boolean testMode = false;
	public int clickedIndex = -1;
	private ArrayList<Rectangle> territoryInsides = new ArrayList<Rectangle>();
	
	

	public GraphicalUserInterface(ResourceBundle msg) {
		this.setUpTerritoryNamesAndLocation("src/main/resources/TerritoryNamesAndLocations.txt");
		
		this.messages = msg;
		clickedTerritory = new Text("");
		for (int i = 0; i < 42; i++) {
			territoryColors.add(Color.DARKGRAY);
		}
		
		this.language = new Button(messages.getString("lang"));
		this.nextTurn = new Button(messages.getString("nextPhase"));
		this.currentPlayer = new Text(messages.getString("player"));
		this.playerArmies = new Text(messages.getString("armiesInPlayersHands"));
		this.territoryArmies = new Text(messages.getString("armiesTerr"));
		this.territoryPlayer = new Text(messages.getString("terrPlayer"));
		this.attack = new JButton(messages.getString("attack"));
		this.addArmy = new Button(messages.getString("addArmy"));
		this.attackerDice = new JLabel(messages.getString("selectAttDice"));
		this.defenderDice = new JLabel(messages.getString("selectDefDice"));
		this.spendCards = new JButton(messages.getString("spendCards"));
	}
	
	public GraphicalUserInterface(ResourceBundle msg, Stage stage) {
		
		this.messages = msg;
		clickedTerritory = new Text("");
		
		for (int i = 0; i < 42; i++) {
			territoryColors.add(Color.DARKGRAY);
		}
		
		this.stage = stage;
		
		this.setUpTerritoryNamesAndLocation("src/main/resources/TerritoryNamesAndLocations.txt");
		
		currentTerritoryDesc = new Text(messages.getString("select"));
		this.language = new Button(messages.getString("lang"));
		this.nextTurn = new Button(messages.getString("nextPhase"));
		this.currentPlayer = new Text(messages.getString("player"));
		this.playerArmies = new Text(messages.getString("armiesInPlayersHands"));
		this.territoryArmies = new Text(messages.getString("armiesTerr"));
		this.territoryPlayer = new Text(messages.getString("terrPlayer"));
		this.attack = new JButton(messages.getString("attack"));
		this.addArmy = new Button(messages.getString("addArmy"));
		this.attackerDice = new JLabel(messages.getString("selectAttDice"));
		this.defenderDice = new JLabel(messages.getString("selectDefDice"));
		this.spendCards = new JButton(messages.getString("spendCards"));
	}
	
	public void setLanguage(ResourceBundle msg, String phase) {
		this.messages = msg;
		this.currentTerritoryDesc.setText(messages.getString("select"));
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
		this.stage.setTitle(messages.getString("risk"));
		this.currentPhase = messages.getString(phase);
//		this.component.repaint();
	}

	public void setMouseListener(EventHandler<MouseEvent> listener) {
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
	public String checkForPointOnTerritory(Point2D click) {
		if (click == null) {
			return "";
		}
		for (int i = 0; i < territoriesBounds.size(); i++) {
			if (territoriesBounds.get(i) != null && this.territoriesBounds.get(i).contains(click)) {
				clickedTerritory.setText(messages.getString(territoryNames.get(i)));
				this.clickedIndex  = i;
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
					int y = Integer.parseInt(line);
					this.territoriesBounds.add(this.getTerritoryBox(x, y));
					
					Rectangle rect = this.getTerritoryBox(x, y);
					rect.widthProperty().bind(stage.widthProperty().divide(37.5).subtract(5));
					rect.heightProperty().bind(stage.heightProperty().divide(18.75).subtract(5));
					
					rect.xProperty().bind(stage.widthProperty().divide(2000.0).multiply(x).add(2.5));
					rect.yProperty().bind(stage.heightProperty().divide(1000.0).multiply(y).add(2.5));
					
					this.territoryInsides.add(rect);
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
		stage.setTitle(this.messages.getString("risk"));
		stage.setMaxWidth(screenWidth);
		stage.setMinWidth(screenWidth);
		stage.setMaxHeight(screenHeight);
		stage.setMinHeight(screenHeight);
		
		GridPane pane = new GridPane();
		
		this.component = new GuiComponent(this.territoriesBounds);
		try {
			BackgroundImage bImg = new BackgroundImage(new Image(new FileInputStream("src/main/resources/background.png")),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(75, 75,
                    		true, true, true, false));
			
			pane.setBackground(new Background(bImg));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Group root = new Group();
		this.addTerritoryBoundsToPane(root);
		this.paintTerritoryBounds();
		
		pane.vgapProperty().bind(stage.heightProperty().divide(15));
		pane.hgapProperty().bind(stage.widthProperty().divide(33.33));
		
		pane.setOnMouseClicked(this.mouseListener);
		
		JPanel currentPlayerPanel = new JPanel();
		JPanel currentTerritoryAndCardsPanel = new JPanel();
		
		currentTerritoryAndCardsPanel.add(card1);
		currentTerritoryAndCardsPanel.add(card2);
		currentTerritoryAndCardsPanel.add(card3);
		currentTerritoryAndCardsPanel.add(spendCards);
		
		currentPlayerPanel.setLayout(new GridLayout(2, 6));
//		currentPlayerPanel.add(currentPlayer);
		
		GridPane options = new GridPane();
		
		Group player = new Group();
		
		player.getChildren().add(currentPlayer);
		player.getChildren().add(currentPlayerId);
		player.getChildren().add(playerArmies);
		player.getChildren().add(playerArmiesNumber);
		
		player.getChildren().add(currentTerritoryDesc);
		player.getChildren().add(clickedTerritory);
		player.getChildren().add(territoryPlayer);
		player.getChildren().add(territoryPlayerNumber);
		player.getChildren().add(territoryArmies);
		player.getChildren().add(territoryArmiesNumber);
		
		Font standard = Font.font("Tahoma", FontWeight.NORMAL, 20);
		
		currentPlayer.setFont(standard);
		currentPlayerId.setFont(standard);
		
		playerArmies.setFont(standard);
		playerArmiesNumber.setFont(standard);
		
		territoryPlayer.setFont(standard);
		territoryPlayerNumber.setFont(standard);
		
		territoryArmies.setFont(standard);
		territoryArmiesNumber.setFont(standard);
		
		currentTerritoryDesc.setFont(standard);
		clickedTerritory.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
		
		currentPlayerId.setX(220);
		
		playerArmies.setY(35);
		playerArmiesNumber.setY(35);
		playerArmiesNumber.setX(300);
		
		currentTerritoryDesc.setY(70);
		clickedTerritory.setY(105);
		
		territoryPlayer.setY(150);
		territoryPlayerNumber.setY(150);
		territoryPlayerNumber.setX(300);
		
		territoryArmies.setY(185);
		territoryArmiesNumber.setY(185);
		territoryArmiesNumber.setX(300);

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
		
		Group buttons = new Group();
		
		buttons.getChildren().add(addArmy);
		buttons.getChildren().add(nextTurn);
		buttons.getChildren().add(language);
		
		nextTurn.setTranslateY(30);
		language.setTranslateY(60);
		
		currentTerritoryAndCardsPanel.setLayout(new GridLayout(5, 1));
//		currentTerritoryAndCardsPanel.add(language);
		currentTerritoryAndCardsPanel.setPreferredSize(new Dimension((int) stage.getWidth() / 8, (int) stage.getHeight()));
		
		pane.setAlignment(Pos.TOP_LEFT);
		
		options.add(player, 0, 0);
		options.add(buttons, 0, 1);
		
		pane.add(root, 1, 1);
		pane.add(options, 2, 1);
		
		Scene scene = new Scene(pane);
		
		stage.setScene(scene);
		
		stage.show();
	}
	
	private void addTerritoryBoundsToPane(Group root) {
		for (int i = 0; i < this.territoriesBounds.size(); i++) {
			root.getChildren().add(this.territoriesBounds.get(i));
			root.getChildren().add(this.territoryInsides.get(i));
		}
	}
	
	public void paintTerritoryBounds() {
		for (int i = 0; i < this.territoriesBounds.size(); i++) {
			
			Rectangle border = this.territoriesBounds.get(i);
			
			Rectangle rect = this.territoryInsides.get(i);
			
			Color color = Color.BLACK;
			if (clickedIndex == i) {
				color = Color.WHITE;
			}
			
			border.setFill(color);
			color = territoryColors.get(i);
			rect.setFill(color);
		}
	}

	private Rectangle getTerritoryBox(int x, int y) {

		Rectangle rect = new Rectangle(0, 0, 0, 0);
		
		rect.widthProperty().bind(stage.widthProperty().divide(37.5).add(10));
		rect.heightProperty().bind(stage.heightProperty().divide(18.75).add(10));
		
		rect.xProperty().bind(stage.widthProperty().divide(2000.0).multiply(x).subtract(5));
		rect.yProperty().bind(stage.heightProperty().divide(1000.0).multiply(y).subtract(5));

		return rect;
	}
	
	public class GuiComponent extends JComponent {

//		/**
//		 * Auto generated to prevent the waring from not having this.
//		 */
//		private static final long serialVersionUID = 1L;
//		Image background;
//		Image original;
//		private ArrayList<Rectangle> territoryBounds;
//
		public GuiComponent(ArrayList<Rectangle> territoryBounds) {
//			this.territoryBounds = (ArrayList<Rectangle>) territoryBounds.clone();
//			try {
//				this.original = ImageIO.read(new File("src/main/resources/background.png"));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}

		@Override
		public void paint(Graphics g) {
//			g.translate(-xShift, -yShift);
//			this.paintBackgroundImage(g);
//			this.paintTerritoryBounds(g);
//			this.drawInfoScreen(g);
//			g.translate(xShift, yShift);
		}

//		private void drawInfoScreen(Graphics g) {
//			territoryArmiesNumber.setText(Integer.toString(currentTerritoryArmyCount));
//			territoryPlayerNumber.setText(Integer.toString(currentTerritoryPlayer));
//			java.awt.Color previousColor = g.getColor();
//			g.setColor(Color.WHITE);
//			g.setFont(new Font("TimesRoman", Font.PLAIN, (int) ((stage.getWidth()) / 50)));
//			g.translate(getBackgroundWidth(), 0);
//			g.drawString(currentPhase, 30, 100);
//			g.translate(-getBackgroundWidth(), 0);
//			g.setColor(previousColor);
//		}
//
//		private void paintTerritoryBounds(Graphics g) {
//			Color previous = g.getColor();
//			Graphics2D g2 = (Graphics2D) g;
//			currentBounds.clear();
//			for (int i = 0; i < territoryBounds.size(); i++) {
//				Rectangle2D.Double border = getTerritoryBox(i);
//				border.width = border.width + 10;
//				border.height = border.height + 10;
//				border.x = border.x - 5;
//				border.y = border.y - 5;
//				
//				Rectangle2D.Double rect = getTerritoryBox(i);
//				
//				g2.setColor(Color.DARK_GRAY);
//				if (clickedIndex == i) {
//					g2.setColor(Color.WHITE);
//				}
//				g2.fill(border);
//				g2.setColor(territoryColors.get(i));
//				g2.fill(rect);
//			}
//			g.setColor(previous);
//		}
//
//		private Rectangle2D.Double getTerritoryBox(int pos) {
//			return new Rectangle2D.Double(
//					((double) territoryBounds.get(pos).x) * stage.getWidth() / screenWidth,
//					((double) territoryBounds.get(pos).y) * stage.getHeight() / screenHeight,
//					((double) stage.getWidth()) / 50, ((double) stage.getHeight()) / 25);
//		}
//
//		private void paintBackgroundImage(Graphics g) {
////			this.background = original.getScaledInstance(getBackgroundWidth(), (int) stage.getHeight(),
////					Image.SCALE_DEFAULT);
//			g.fillRect(0, 0, 10000, 10000);
////			g.drawImage(background, 0, 0, null);
//		}
//		
//		private int getBackgroundWidth() {
//			return (int) (3 * stage.getWidth() / 4);
//		}
//	}
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
		return Color.BLACK;
	}

	public void setTerritoryColor(String territory, int player) {
		territory = territory.replace(' ', '_');
		Color playerColor = this.getColorForPlayer(player);
		for (int i = 0; i < this.territoryNames.size(); i++) {
			if (this.territoryNames.get(i).equals(territory)) {
				this.territoryColors.set(i, playerColor);
				return;
			}
		}
	}

	public void setTerritoryArmyCount(int armyCount) {
		this.territoryArmiesNumber.setText("" + armyCount);
	}

	public void setCurrentTerritoryOwner(int player) {
		this.setTerritoryPlayer("" + player);
	}


}