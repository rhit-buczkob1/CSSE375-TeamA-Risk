package Risk.view;

import Risk.view.GraphicalUserInterface.GuiComponent;
import org.junit.Test;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertTrue;

public class GuiComponentTest {
	Locale locale = new Locale("en", "US");
	ResourceBundle msg = ResourceBundle.getBundle("MessagesBundle", locale);

	@Test
	public void testConstructor() {
		GraphicalUserInterface gui = new GraphicalUserInterface(msg);
		GuiComponent backgroundImage = gui.new GuiComponent(gui.territoriesBounds);
		assertTrue(backgroundImage.original != null);
	}

	/*
	 * Test all of the paint methods in GuiComponent by running GraphicalUserInterface as
	 * a java project. Checklist follows:
	 * 1. Image displayed in background.
	 * 2. Image scales with screen size.
	 * 3. Boxes are on each territory.
	 * 4. Boxes on each territory scale with screen size.
	 * 5. Clicking on a box displays the name of the territory in the sidebar.
	 * 6. You can see current player and current Player armies on the bottom of the screen
	 * 7. You can see current Territory owner and armies on it on the right hand side of the screen
	 */
}
