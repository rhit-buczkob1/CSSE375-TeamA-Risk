package Risk.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class PlayerTests {
	/*
	 * BVA for Player getAttacker() / getDefender(): takes no input and returns a
	 * boolean addInfantry(int) / addCavalry(int) / addArtillery(int) : minimum
	 * input is 1, no reason to add zero will test minimum integer possible maximum
	 * input is 99, there should never be more than 99 added will test the maximum
	 * integer possible should not go past 99 for any of these 3 any number should
	 * work in between these Check collection to see if value updated
	 * 
	 * removeInfantry(int) / removeCavalry(int) / removeArtillery(int) : minimum
	 * input is 1 maximum input is however many is left in the player's hand should
	 * never hold negative of any of the 3
	 * 
	 * addTerritory(Territory) / removeTerritory(Territory) : this refers to a
	 * collection, so will test the size before and after adding a territory, adding
	 * when no elements, 1 elements, a few elements, and maximum elements
	 * TERRITORIES WILL BE REPRESENTED AS STRINGS UNTIL MERGING OCCURS
	 * 
	 * setAttacker() / setDefender() : this takes no input and changes a boolean
	 * entry, so to test that the tests will alter the values in a way by flipping
	 * the bit
	 * 
	 * getId() : should return whatever the id of the player is, as long as it is
	 * not below the minimum or above the maximum
	 * 
	 * 
	 */

	// TODO: update bva

	/*
	 * Players start as false for attackers and defenders
	 */

	@Test
	public void addPlayerArmiesGetPlayerArmiesTests() {
		Player player = new Player(1);
		assertEquals(player.getPlayerArmies(), 0);

		player.addPlayerArmies(0);

		assertEquals(player.getPlayerArmies(), 0);

		try {
			player.addPlayerArmies(-1);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "cannot add negative armies");
		}

		player.addPlayerArmies(1);
		assertEquals(player.getPlayerArmies(), 1);

		player.addPlayerArmies(89);
		assertEquals(player.getPlayerArmies(), 90);

		try {
			player.addPlayerArmies(Integer.MAX_VALUE);
			System.out.println(player.getPlayerArmies());
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid Army Count");
		}

	}

	@Test
	public void removePlayerArmiesGetPlayerArmiesTests() {
		Player player = new Player(1);
		assertEquals(player.getPlayerArmies(), 0);

		player.removePlayerArmies(0);

		assertEquals(player.getPlayerArmies(), 0);

		try {
			player.removePlayerArmies(1);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Invalid Army Count");
		}

		player.addPlayerArmies(1);
		player.removePlayerArmies(1);

		assertEquals(player.getPlayerArmies(), 0);

		try {
			player.removePlayerArmies(Integer.MIN_VALUE);
			System.out.println(player.getPlayerArmies());
			fail("shouldn't reach this point");

		} catch (Exception e) {
			assertEquals(e.getMessage(), "cannot input negative armies");
		}

	}


	@Test
	public void addTerritorygetTerritorytests() {
		Player player = new Player(1);
		assertEquals(0, player.getTerritories());
		player.addTerritory();
		assertEquals(player.getTerritories(), 1);

		for (int i = 0; i < 41; i++) {
			player.addTerritory();
		}

		try {
			player.addTerritory();
			fail();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Player owns too many territories");
		}

	}

	@Test
	public void addTerritoryEqualMaxTest() {
		Player player = new Player(1);

		for (int i = 0; i < 42; i++) {
			player.addTerritory();
		}

		assertEquals(42, player.getTerritories());

	}

	@Test
	public void addTerritoryValidTest() {
		Player player = new Player(1);

		for (int i = 0; i < 34; i++) {
			player.addTerritory();
		}

		assertEquals(34, player.getTerritories());

	}

	@Test
	public void addTerritoryMinTest() {
		Player player = new Player(1);

		player.territories = -2;
		player.addTerritory();

		assertEquals(player.getTerritories(), -1);
	}

	@Test
	public void removeTerritorygetTerritorytests() {
		Player player = new Player(1);

		try {
			player.removeTerritory();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "Player can't own negative territories");
		}

		player.addTerritory();
		player.addTerritory();
		player.removeTerritory();
		assertEquals(player.getTerritories(), 1);
		player.removeTerritory();
		assertEquals(player.getTerritories(), 0);
	}

	@Test
	public void getIdTest() {
		Player player = new Player(12);
		assertEquals(12, player.getId());
	}

	@Test
	public void getMinimalIdTest() {
		Player player = new Player(Integer.MIN_VALUE);
		assertEquals(Integer.MIN_VALUE, player.getId());
	}

	public void getMaximumIdTest() {
		Player player = new Player(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, player.getId());
	}

	public void getIdMultiplePlayersTest() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		assertEquals(1, player1.getId());
		assertEquals(2, player2.getId());
	}

	@Test
	public void addCardToPlayerGetCardFromPlayerTestMin() {
		Card card1 = new Card("test");

		Player tocheckplayer = new Player(1);
		tocheckplayer.addCardToPlayer(card1);

		ArrayList<Card> tocheckdeck = tocheckplayer.getDeck();

		assertEquals(tocheckdeck.size(), 1);
		assertEquals(tocheckdeck.get(0), card1);

	}

	@Test
	public void addCardToPlayerGetCardFromPlayerTestAddMultiple() {
		Card card1 = new Card("test1");
		Card card2 = new Card("test2");
		Card card3 = new Card("test3");

		Player tocheckplayer = new Player(1);
		tocheckplayer.addCardToPlayer(card1);
		tocheckplayer.addCardToPlayer(card2);
		tocheckplayer.addCardToPlayer(card3);

		ArrayList<Card> tocheckdeck = tocheckplayer.getDeck();

		assertEquals(tocheckdeck.size(), 3);
		assertEquals(tocheckdeck.get(0), card1);
		assertEquals(tocheckdeck.get(1), card2);
		assertEquals(tocheckdeck.get(2), card3);

	}

	@Test
	public void addCardToPlayerGetCardFromPlayerTestMax() {
		Card card1 = new Card("test1");
		Card card2 = new Card("test2");
		Card card3 = new Card("test3");
		Card card4 = new Card("test3");
		Card card5 = new Card("test3");
		Card card6 = new Card("test3");
		Card card7 = new Card("test3");

		Player tocheckplayer = new Player(1);
		tocheckplayer.addCardToPlayer(card1);
		tocheckplayer.addCardToPlayer(card2);
		tocheckplayer.addCardToPlayer(card3);
		tocheckplayer.addCardToPlayer(card4);
		tocheckplayer.addCardToPlayer(card5);
		tocheckplayer.addCardToPlayer(card6);
		try {
			tocheckplayer.addCardToPlayer(card7);
			fail("shouldn't reach this point");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "player deck is full");

		}

		ArrayList<Card> tocheckdeck = tocheckplayer.getDeck();

		assertEquals(tocheckdeck.size(), 6);
		assertEquals(tocheckdeck.get(0), card1);
		assertEquals(tocheckdeck.get(1), card2);
		assertEquals(tocheckdeck.get(2), card3);
		assertEquals(tocheckdeck.get(3), card4);
		assertEquals(tocheckdeck.get(4), card5);
		assertEquals(tocheckdeck.get(5), card6);

	}
	
	@Test
	public void setDeckPlayerTest() {
		Card card1 = new Card("test1");
		Card card2 = new Card("test2");
		Card card3 = new Card("test3");
		ArrayList<Card> deck = new ArrayList<>();
		deck.add(card1);
		deck.add(card2);
		deck.add(card3);
		Player tocheckplayer = new Player(1);
		tocheckplayer.setDeck(deck);

		ArrayList<Card> tocheckdeck = tocheckplayer.getDeck();

		assertEquals(deck, tocheckdeck);

	}



}
