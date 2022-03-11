package Risk.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CardTest {

	@Test
	public void createCardTest() {
		Card testCard = new Card("Afghanistan", "Cavalry");
		assertEquals(testCard.getClass(), testCard.getClass());
	}

	@Test
	public void getTerritoryFromCardTest() {
		Card testCard = new Card("Afghanistan", "Cavalry");
		String territory = testCard.getTerritory();
		assertEquals("Afghanistan", territory);
	}

	@Test
	public void getTroopTypeFromCardTest() {
		Card testCard = new Card("Afghanistan", "Cavalry");
		String troopType = testCard.getTroopType();
		assertEquals("Cavalry", troopType);
	}

}
