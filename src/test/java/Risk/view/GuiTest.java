package Risk.view;

import org.junit.Test;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;

public class GuiTest {
	Locale local = new Locale("en", "US");
	ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", local);
	/* can't test gui yet
	@Test
	public void testCheckForPointOnTerritory() {
		
		ClickListener mouseListener = new ClickListener();
		MouseEvent eventMock = EasyMock.strictMock(MouseEvent.class);
		EasyMock.expect(eventMock.getX()).andReturn(25);
		EasyMock.expect(eventMock.getY()).andReturn(25);
		EasyMock.replay(eventMock);
		GraphicalUserInterface gui = new GraphicalUserInterface(mouseListener);
		gui.initializeFrame();
		gui.territoryNames.add(0, "Alaska");
		gui.territoriesBounds.add(0, new Rectangle(0, 0, 50, 50));
		mouseListener.mousePressed(eventMock);
		String territoryName = gui.checkForPointOnTerritory();
		EasyMock.verify(eventMock);
		assertEquals("Alaska", territoryName);

		eventMock = EasyMock.strictMock(MouseEvent.class);
		EasyMock.expect(eventMock.getX()).andReturn(30000);
		EasyMock.expect(eventMock.getY()).andReturn(30000);
		EasyMock.replay(eventMock);
		gui = new GraphicalUserInterface(mouseListener);
		gui.initializeFrame();
		gui.territoryNames.add(0, "Alaska");
		gui.territoriesBounds.add(0, new Rectangle(0, 0, 50, 50));
		mouseListener.mousePressed(eventMock);
		territoryName = gui.checkForPointOnTerritory();
		EasyMock.verify(eventMock);
		assertEquals("", territoryName); 

	}
	*/

	/* can't test gui yet
	@Test
	public void testCheckForPointOnTerritoryEdge() {
		ClickListener mouseListener = new ClickListener();
		MouseEvent eventMock = EasyMock.strictMock(MouseEvent.class);
		EasyMock.expect(eventMock.getX()).andReturn(25);
		EasyMock.expect(eventMock.getY()).andReturn(0);
		EasyMock.replay(eventMock);
		GraphicalUserInterface gui = new GraphicalUserInterface(mouseListener);
		gui.initializeFrame();
		gui.territoryNames.add(0, "Alaska");
		gui.territoriesBounds.add(0, new Rectangle(0, 0, 50, 50));
		mouseListener.mousePressed(eventMock);
		String territoryName = gui.checkForPointOnTerritory();
		EasyMock.verify(eventMock);
		assertEquals("Alaska", territoryName);
	}
	*/

	@Test
	public void testSetUpTerritoryNamesAndLocation() {
		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
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
		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
		assertEquals(Color.RED, gui.getColorForPlayer(1));
		assertEquals(Color.BLUE, gui.getColorForPlayer(2));
		assertEquals(Color.YELLOW, gui.getColorForPlayer(3));
		assertEquals(Color.GREEN, gui.getColorForPlayer(4));
	}
	
	@Test
	public void testGetColorForPlayerInvalid() {
		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
		assertEquals(Color.DARK_GRAY, gui.getColorForPlayer(Integer.MIN_VALUE));
		assertEquals(Color.DARK_GRAY, gui.getColorForPlayer(0));
		assertEquals(Color.DARK_GRAY, gui.getColorForPlayer(5));
		assertEquals(Color.DARK_GRAY, gui.getColorForPlayer(Integer.MAX_VALUE));
	}
	
	@Test
	public void testSetTerritoryColorValid() {
		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
		gui.setTerritoryColor("Afghanistan", 1);
		gui.setTerritoryColor("Alaska", 2);
		gui.setTerritoryColor("Alberta", 3);
		gui.setTerritoryColor("Argentina", 4);
		assertEquals(Color.RED, gui.territoryColors.get(0));
		assertEquals(Color.BLUE, gui.territoryColors.get(1));
		assertEquals(Color.YELLOW, gui.territoryColors.get(2));
		assertEquals(Color.GREEN, gui.territoryColors.get(3));
		for (int i = 4; i < gui.territoryColors.size(); i++) {
			assertEquals(Color.DARK_GRAY, gui.territoryColors.get(i));
		}
	}
	
	@Test
	public void testSetTerritoryColorInvalid() {
		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
		gui.setTerritoryColor("Afghanistan", Integer.MIN_VALUE);
		gui.setTerritoryColor("Alaska", 0);
		gui.setTerritoryColor("Alberta", 5);
		gui.setTerritoryColor("Argentina", Integer.MAX_VALUE);
		gui.setTerritoryColor("Not a real territory", 2);
		gui.setTerritoryColor("", 3);
		for (int i = 0; i < gui.territoryColors.size(); i++) {
			assertEquals(Color.DARK_GRAY, gui.territoryColors.get(i));
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