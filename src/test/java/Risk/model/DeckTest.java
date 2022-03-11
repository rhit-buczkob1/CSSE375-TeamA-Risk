package Risk.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class DeckTest {

	@Test
	public void drawFromEmptyDeckTest() {
		Deck emptyDeck = new Deck();
		try {
			emptyDeck.drawCard();
			fail("Expecting NoSuchElementException thrown for empty Deck!");
		} catch (NoSuchElementException e) {
			assertEquals("Deck is currently Empty!", e.getMessage());
		}
	}
    
	@Test
	public void checkForInvalidShuffleTest() {
		Deck deck = new Deck();
       
		ArrayList<Card> list = new ArrayList<Card>();
		list.add(new Card("test"));
       
		assertEquals(true, deck.checkForInvalidShuffle(list, list));
	}

	@Test
	public void drawFromNonEmptyDeckTest() {
		Deck nonEmptyDeck = new Deck();
		Card cardTest = new Card("Indonesia", "Cavalry");
		nonEmptyDeck.addCard(cardTest);

		Card result = nonEmptyDeck.drawCard();

		assertEquals(result, cardTest);
	}

	@Test
	public void addSingleCardToDeckTest() {
		Deck deck = new Deck();
		Card cardTest = new Card("Indonesia", "Cavalry");

		deck.addCard(cardTest);

		assertEquals(1, deck.cards.size());
	}

	@Test
	public void addMultipleCardsToDeckTest() {
		Deck deck = new Deck();
		Card cardTest = new Card("Indonesia", "Cavalry");
		Card cardTestTwo = new Card("Eastern Australia", "Artillery");
		Card cardTestThree = new Card("Western Australia", "Infantry");

		deck.addCard(cardTest);
		deck.addCard(cardTestTwo);
		deck.addCard(cardTestThree);

		assertEquals(3, deck.cards.size());
	}

	@Test
	public void addMaxAmountOfCardToDeckTest() {
		Deck deck = new Deck();
		ArrayList<Card> cardTestDeck = new ArrayList<Card>();
		for (int i = 0; i < 45; i++) {
			cardTestDeck.add(new Card("Indonesia", "Cavalry"));
		}

		for (int i = 0; i < cardTestDeck.size(); i++) {
			deck.addCard(cardTestDeck.get(i));
		}

		try {
			deck.addCard(cardTestDeck.get(0));
			fail("Expected Max Size for Deck of Cards to be reached!");
		} catch (ArrayStoreException e) {
			assertEquals("Max Size for Deck of Cards reached!", e.getMessage());
		}
	}

	@Test
	public void shuffleEmptyDeckTest() {
		Deck deckEmpty = new Deck();
		try {
			deckEmpty.shuffle();
			fail("Expecting Empty Deck of Cards to not be shuffled!");
		} catch (Exception e) {
			assertEquals("Empty Deck of Cards cannot be shuffled!", e.getMessage());
		}
	}

	@Test
	public void shuffleDeckTest() {

		Deck deck = new Deck();
		Card cardTest = new Card("Indonesia", "Cavalry");
		Card cardTestTwo = new Card("Eastern Australia", "Artillery");

		deck.addCard(cardTest);
		deck.addCard(cardTestTwo);

		ArrayList<Card> firstDeckShuffle = new ArrayList<>();
		firstDeckShuffle.add(deck.cards.get(0));
		firstDeckShuffle.add(deck.cards.get(1));

		deck.shuffle();

		if (deck.cards.get(0).territory == firstDeckShuffle.get(0).territory
				&& deck.cards.get(1).territory == firstDeckShuffle.get(1).territory) {
			fail("Expecting cards to be shuffled");
		} else {
			assertTrue(true);
		}
	}
}
