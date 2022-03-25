package Risk.controller;

import Risk.model.Player;
import Risk.model.Territory;
import org.easymock.EasyMock;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class AttackerDefenderControllerTests {

	@Test
	public void setAndGetAttackerTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);

		adc.setAttacker(player1);

		assertEquals(player1, adc.getAttacker());

	}

	@Test
	public void setAndGetDefenderTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);

		adc.setDefender(player1);

		assertEquals(player1, adc.getDefender());

	}

	@Test
	public void roll2DiceTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Random rand = EasyMock.strictMock(Random.class);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);

		EasyMock.replay(rand);

		Integer[] actual = adc.rollDice(2, rand);
		Integer[] expected = new Integer[3];
		expected[0] = 4;
		expected[1] = 3;

		EasyMock.verify(rand);
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
	}

	@Test
	public void roll3DiceTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Random rand = EasyMock.strictMock(Random.class);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(6);

		EasyMock.replay(rand);

		Integer[] actual = adc.rollDice(3, rand);
		Integer[] expected = new Integer[3];
		expected[0] = 6;
		expected[1] = 4;
		expected[2] = 3;

		EasyMock.verify(rand);
		assertEquals(expected[0], actual[0]);
		assertEquals(expected[1], actual[1]);
		assertEquals(expected[2], actual[2]);
	}

	@Test
	public void rollNegativeDice() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Random rand = EasyMock.strictMock(Random.class);
		try {
			adc.rollDice(-1, rand);
			fail();
		} catch (IllegalArgumentException e) {
			System.out.println("Passed");
		}
	}
	
	@Test
	public void rollZeroDice() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Random rand = EasyMock.strictMock(Random.class);
		try {
			adc.rollDice(0, rand);
			fail();
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("Incorrect Amount of Dice rolled"));
		}
	}

	@Test
	public void addTerritoriesAsAttackerAndDefenderTest() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		AttackerDefenderController adc = new AttackerDefenderController();
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		test1.setPlayer(1);
		test2.setPlayer(2);

		adc.setAttacker(player1);
		adc.setDefender(player2);

		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		assertEquals(test1, adc.getAttackingTerritory());
		assertEquals(test2, adc.getDefendingTerritory());
	}

	@Test
	public void addArmiesFromTerritoriesToBattle() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		AttackerDefenderController adc = new AttackerDefenderController();
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);

		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		assertEquals(5, test1.getArmyCount());
		assertEquals(20, test2.getArmyCount());

	}
	
	@Test
	public void addArmiesFromTerritoriesToBattleTest2() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		AttackerDefenderController adc = new AttackerDefenderController();
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(3);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);

		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(1);
		assertEquals(2, test1.getArmyCount());
	}

	@Test
	public void battleTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(6);

		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);

		EasyMock.replay(rand);

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		adc.combat(3, 2, rand);

		assertEquals(19, test1.getArmyCount());
		assertEquals(19, test2.getArmyCount());

		EasyMock.verify(rand);

	}

	@Test
	public void battleTest2() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);

		EasyMock.expect(rand.nextInt(6)).andReturn(5);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);

		EasyMock.replay(rand);

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		adc.combat(1, 2, rand);

		assertEquals(19, test1.getArmyCount());
		assertEquals(20, test2.getArmyCount());

		EasyMock.verify(rand);

	}

	@Test
	public void addTerritoryNotOwnedByAttackerTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player p = new Player(1);
		adc.setAttacker(p);
		Territory test1 = new Territory("test1");

		try {
			adc.setAttackingTerritory(test1);
			fail();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}

	@Test
	public void addTooManyArmiesFromTerritoriesToBattle() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		AttackerDefenderController adc = new AttackerDefenderController();
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");


		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);

		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		try {
			adc.setArmies(20);
			fail();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}


	}

	@Test
	public void addTooManyArmiesFromTerritoriesToBattleTest2() {
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		AttackerDefenderController adc = new AttackerDefenderController();
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);

		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		try {
			adc.setArmies(21);
			fail();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}


	}

	@Test
	public void rollTooManyDiceAttackerTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);		
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);

		EasyMock.replay(rand);
		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(2);

		try {
			adc.combat(3, 2, rand);
			fail();
		} catch (IllegalArgumentException e) {
			EasyMock.verify(rand);
		}
	}

	@Test
	public void rollTooManyDiceDefenderTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);
		EasyMock.replay(rand);

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(1);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		try {
			adc.combat(3, 2, rand);
			fail();
		} catch (IllegalArgumentException e) {
			EasyMock.verify(rand);
		}
	}
	
	@Test
	public void battleTerritoryTakeoverTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(6);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(5);

		EasyMock.replay(rand);
		player1.addTerritory();
		player2.addTerritory();

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(2);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		adc.combat(3, 2, rand);

		assertEquals(5, test1.getArmyCount());
		assertEquals(15, test2.getArmyCount());
		assertEquals(0, player2.getTerritories());
		assertEquals(1, test2.getPlayer());
		assertTrue(player1.hasCaughtTerritory());

		EasyMock.verify(rand);

	}
	
	@Test
	public void battleDefenderDiceMoreTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(2);

		EasyMock.replay(rand);

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		adc.combat(1, 2, rand);

		assertEquals(19, test1.getArmyCount());
		assertEquals(20, test2.getArmyCount());

		EasyMock.verify(rand);

	}
	
	@Test
	public void battleEqualDiceTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(2);

		EasyMock.replay(rand);

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		adc.combat(2, 2, rand);

		assertEquals(20, test1.getArmyCount());
		assertEquals(18, test2.getArmyCount());

		EasyMock.verify(rand);

	}
	
	@Test
	public void battleAttackerDiceMoreTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(2);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(2);

		EasyMock.replay(rand);

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		adc.combat(3, 2, rand);

		assertEquals(19, test1.getArmyCount());
		assertEquals(19, test2.getArmyCount());

		EasyMock.verify(rand);

	}
	
	@Test
	public void battleTerritoryTakeoverIntegrationTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		Random rand = EasyMock.strictMock(Random.class);
		
		EasyMock.expect(rand.nextInt(6)).andReturn(3);
		EasyMock.expect(rand.nextInt(6)).andReturn(4);
		EasyMock.expect(rand.nextInt(6)).andReturn(6);

		EasyMock.expect(rand.nextInt(6)).andReturn(2);
		EasyMock.expect(rand.nextInt(6)).andReturn(2);
		
		EasyMock.replay(rand);
		player1.addTerritory();
		player2.addTerritory();

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(2);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		adc.combat(3, 2, rand);

		assertEquals(5, test1.getArmyCount());
		assertEquals(15, test2.getArmyCount());
		assertEquals(0, player2.getTerritories());
		assertEquals(2, player1.getTerritories());
		assertEquals(1, test2.getPlayer());

		EasyMock.verify(rand);

	}
	
	@Test
	public void battleDefenderDiceMoreIntegrationTest() {
		AttackerDefenderController adc = new AttackerDefenderController();
		Player player1 = new Player(1);
		Player player2 = new Player(2);
		
		Territory test1 = new Territory("test1");
		Territory test2 = new Territory("test2");
		
		Random rand = EasyMock.strictMock(Random.class);

		EasyMock.expect(rand.nextInt(6)).andReturn(3);

		EasyMock.expect(rand.nextInt(6)).andReturn(2);
		EasyMock.expect(rand.nextInt(6)).andReturn(2);

		EasyMock.replay(rand);

		test1.setPlayer(1);
		test2.setPlayer(2);

		test1.addArmies(20);
		test2.addArmies(20);

		adc.setAttacker(player1);
		adc.setDefender(player2);
		adc.setAttackingTerritory(test1);
		adc.setDefendingTerritory(test2);
		adc.setArmies(15);

		adc.combat(1, 2, rand);

		assertEquals(20, test1.getArmyCount());
		assertEquals(19, test2.getArmyCount());

		EasyMock.verify(rand);

	}
	

}
