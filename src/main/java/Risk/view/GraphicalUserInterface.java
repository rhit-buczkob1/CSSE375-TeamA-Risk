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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

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
	
	public Button nextTurn;
	public Text currentPlayer;
	public Text currentPlayerId = new Text("1");
	public Text playerArmies;
	public Text playerArmiesNumber = new Text("30");
	public Text territoryArmies;
	public Text territoryArmiesNumber = new Text("0");
	public Text territoryPlayer;
	public Text territoryPlayerNumber = new Text("0");
	public Button attack;
	public Button language;
	public Button addArmy;
	public Text attackerDice;
	public Text defenderDice;
	public Slider attackerDiceSlider;
	public Slider defenderDiceSlider;
	public ComboBox<String> card1;
	public ComboBox<String> card2;
	public ComboBox<String> card3;
	public Button spendCards;
	public Text currentPhase = new Text("Setup");
	public Button chooseGameMode;


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
		this.attack = new Button(messages.getString("attack"));
		this.addArmy = new Button(messages.getString("addArmy"));
		this.attackerDice = new Text(messages.getString("selectAttDice"));
		this.defenderDice = new Text(messages.getString("selectDefDice"));
		this.spendCards = new Button(messages.getString("spendCards"));
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
		currentTerritoryDesc.setId("currentTerritoryDesc");

		this.language = new Button(messages.getString("lang"));
		language.setId("language");

		this.nextTurn = new Button(messages.getString("nextPhase"));
		nextTurn.setId("nextTurn");

		this.currentPlayer = new Text(messages.getString("player"));
		currentPlayer.setId("currentPlayer");

		this.playerArmies = new Text(messages.getString("armiesInPlayersHands"));
		playerArmies.setId("playerArmies");

		this.territoryArmies = new Text(messages.getString("armiesTerr"));
		territoryArmies.setId("territoryArmies");

		this.territoryPlayer = new Text(messages.getString("terrPlayer"));
		territoryPlayer.setId("territoryPlayer");

		this.attack = new Button(messages.getString("attack"));
		attack.setId("attack");

		this.addArmy = new Button(messages.getString("addArmy"));
		addArmy.setId("addArmy");

		this.attackerDice = new Text(messages.getString("selectAttDice"));
		attackerDice.setId("attackerDice");

		this.defenderDice = new Text(messages.getString("selectDefDice"));
		defenderDice.setId("defenderDice");

		this.spendCards = new Button(messages.getString("spendCards"));
		spendCards.setId("spendCards");

		this.chooseGameMode = new Button("Choose Game Mode"); //add to language resource file
		chooseGameMode.setId("chooseGameMode");

		this.attackerDiceSlider = new Slider(1, 3, 1);
		attackerDiceSlider.setId("attackerDiceSlider");

		this.defenderDiceSlider = new Slider(1, 2, 1);
		defenderDiceSlider.setId("defenderDiceSlider");

		this.card1 = new ComboBox<String>();
		card1.setId("card1");

		this.card2 = new ComboBox<String>();
		card2.setId("card2");

		this.card3 = new ComboBox<String>();
		card3.setId("card3");
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
		this.currentPhase.setText(messages.getString(phase));
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
		Stage victory = new Stage(); // creates jframe f

		GridPane pane = new GridPane();
		
		try {
			BackgroundImage bImg = new BackgroundImage(new Image(new FileInputStream("src/main/resources/victoryscreen.jpg")),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
			
			pane.setBackground(new Background(bImg));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		victory.setScene(new Scene(pane));
		
		victory.show();
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
		clickedTerritory.setText("");
		this.setCurrentTerritoryOwner(0);
		this.setTerritoryArmyCount(0);
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
		
		GridPane options = new GridPane();
		
		Group player = new Group();
		
		player.getChildren().add(currentPhase);
		
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
		
		currentPhase.setFont(Font.font("Tahoma", FontWeight.BOLD, 25));
		
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
		
		currentPlayer.setY(35);
		currentPlayerId.setY(35);
		currentPlayerId.setX(220);
		
		playerArmies.setY(70);
		playerArmiesNumber.setY(70);
		playerArmiesNumber.setX(300);
		
		currentTerritoryDesc.setY(105);
		clickedTerritory.setY(140);
		
		territoryPlayer.setY(185);
		territoryPlayerNumber.setY(185);
		territoryPlayerNumber.setX(300);
		
		territoryArmies.setY(220);
		territoryArmiesNumber.setY(220);
		territoryArmiesNumber.setX(300);
		
		attackerDiceSlider.setSnapToTicks(true);
		attackerDiceSlider.setMinorTickCount(0);
		attackerDiceSlider.setMajorTickUnit(1);
		attackerDiceSlider.setShowTickMarks(true);
		attackerDiceSlider.setShowTickLabels(true);
		
		defenderDiceSlider.setSnapToTicks(true);
		defenderDiceSlider.setMinorTickCount(0);
		defenderDiceSlider.setMajorTickUnit(1);
		defenderDiceSlider.setShowTickMarks(true);
		defenderDiceSlider.setShowTickLabels(true);
		
		Group buttons = new Group();
		
		buttons.getChildren().add(addArmy);
		buttons.getChildren().add(nextTurn);
		buttons.getChildren().add(language);
		buttons.getChildren().add(attack);
		buttons.getChildren().add(chooseGameMode);
		
		buttons.getChildren().add(attackerDiceSlider);
		buttons.getChildren().add(defenderDiceSlider);
		buttons.getChildren().add(attackerDice);
		buttons.getChildren().add(defenderDice);
		
		nextTurn.setTranslateY(30);
		attack.setTranslateY(60);
		language.setTranslateY(90);
		chooseGameMode.setTranslateY(120);
		
		attackerDice.setTranslateY(170);
		defenderDice.setTranslateY(200);
		attackerDiceSlider.setTranslateY(160);
		defenderDiceSlider.setTranslateY(200);
		attackerDiceSlider.setTranslateX(125);
		defenderDiceSlider.setTranslateX(125);
		
		Group cards = new Group();
		
		cards.getChildren().add(card1);
		cards.getChildren().add(card2);
		cards.getChildren().add(card3);
		cards.getChildren().add(spendCards);
		
		card1.setPrefWidth(100);
		card2.setPrefWidth(100);
		card3.setPrefWidth(100);
		
		card2.setTranslateX(100);
		card3.setTranslateX(200);
		spendCards.setTranslateY(120);
		
		pane.setAlignment(Pos.TOP_LEFT);
		
		options.add(player, 0, 0);
		options.add(buttons, 0, 1);
		options.add(cards, 0, 2);
		
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