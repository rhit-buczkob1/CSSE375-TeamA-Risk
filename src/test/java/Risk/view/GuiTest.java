package Risk.view;

import org.easymock.EasyMock;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.shape.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class GuiTest extends ApplicationTest {
	Locale local = new Locale("en", "US");
	ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", local);

	GraphicalUserInterface gui;

    @BeforeClass
    public static void headless() {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("glass.platform", "Monocle");
        System.setProperty("monocle.platform", "Headless");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("testfx.setup.timeout", "2500");
    }


	@Override
	public void start(Stage stage) throws Exception {
		gui = new GraphicalUserInterface(msg, stage);
	}

	@Test
	public void testSetUpTerritoryNamesAndLocation() {
		gui.territoryNames.clear();
		gui.territoriesBounds.clear();
		assertEquals(0, gui.territoryNames.size());
		assertEquals(0, gui.territoriesBounds.size());
		gui.setUpTerritoryNamesAndLocation("src/main/resources/TerritoryNamesAndLocations.txt");
		assertEquals(42, gui.territoryNames.size());
		assertEquals(42, gui.territoriesBounds.size());
	}

	@Test
	public void testGetColorForPlayerValid() {
		assertEquals(Color.RED, gui.getColorForPlayer(1));
		assertEquals(Color.BLUE, gui.getColorForPlayer(2));
		assertEquals(Color.YELLOW, gui.getColorForPlayer(3));
		assertEquals(Color.GREEN, gui.getColorForPlayer(4));
	}

	@Test
	public void testGetColorForPlayerInvalid() {
		assertEquals(Color.DARKGRAY, gui.getColorForPlayer(Integer.MIN_VALUE));
		assertEquals(Color.DARKGRAY, gui.getColorForPlayer(0));
		assertEquals(Color.DARKGRAY, gui.getColorForPlayer(7));
		assertEquals(Color.DARKGRAY, gui.getColorForPlayer(Integer.MAX_VALUE));
	}

	@Test
	public void testSetTerritoryColorValid() {
		gui.setUpTerritoryNamesAndLocation("src/main/resources/TerritoryNamesAndLocations.txt");
		gui.setTerritoryColor("Afghanistan", 1);
		gui.setTerritoryColor("Alaska", 2);
		gui.setTerritoryColor("Alberta", 3);
		gui.setTerritoryColor("Argentina", 4);
		assertEquals(Color.RED, gui.territoryColors.get(0));
		assertEquals(Color.BLUE, gui.territoryColors.get(1));
		assertEquals(Color.YELLOW, gui.territoryColors.get(2));
		assertEquals(Color.GREEN, gui.territoryColors.get(3));
		for (int i = 4; i < gui.territoryColors.size(); i++) {
			assertEquals(Color.DARKGRAY, gui.territoryColors.get(i));
		}
	}

	@Test
	public void testSetTerritoryColorInvalid() {
		gui.setUpTerritoryNamesAndLocation("src/main/resources/TerritoryNamesAndLocations.txt");
		gui.setTerritoryColor("Afghanistan", Integer.MIN_VALUE);
		gui.setTerritoryColor("Alaska", 0);
		gui.setTerritoryColor("Alberta", 7);
		gui.setTerritoryColor("Argentina", Integer.MAX_VALUE);
		gui.setTerritoryColor("Not a real territory", 2);
		gui.setTerritoryColor("", 3);
		for (int i = 0; i < gui.territoryColors.size(); i++) {
			assertEquals(Color.DARKGRAY, gui.territoryColors.get(i));
		}
	}

	/*
	 * This test is not tested automatically, instead it is manual.
	 *
	 * This test will not create the frame in a nonstatic setting.
	 * To test this method run the GraphicalUserInterface.java file
	 * as a java program.
	 */

}