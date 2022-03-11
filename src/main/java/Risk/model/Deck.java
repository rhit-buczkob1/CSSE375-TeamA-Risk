package Risk.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class Deck {
	public ArrayList<Card> cards;

	public Deck() {
		this.cards = new ArrayList<>();
	}

	public Card drawCard() {
		try {
			Card drawnCard = this.cards.get(0);
			this.cards.remove(0);
			return drawnCard;
		} catch (Exception e) {
			throw new NoSuchElementException("Deck is currently Empty!");
		}
	}

	public void addCard(Card card) {
		if (cards.size() < 45) {
			cards.add(card);
		} else {
			throw new ArrayStoreException("Max Size for Deck of Cards reached!");
		}
	}

	public void shuffle() {
		if (!cards.isEmpty()) {
			ArrayList<Card> deckCopy = this.copyDeck();
			Collections.shuffle(cards);
			if (checkForInvalidShuffle(deckCopy, this.cards)) {
				this.shuffle();
			}
		} else {
			throw new IllegalStateException("Empty Deck of Cards cannot be shuffled!");
		}
	}

	private ArrayList<Card> copyDeck() {
		ArrayList<Card> deckCopy = new ArrayList<>();
		for (int i = 0; i < this.cards.size(); i++) {
			deckCopy.add(this.cards.get(i));
		}
		return deckCopy;
	}

	public boolean checkForInvalidShuffle(ArrayList<Card> original, ArrayList<Card> shuffleDeck) {
		for (int i = 0; i < shuffleDeck.size(); i++) {
			if (original.get(i) != shuffleDeck.get(i)) {
				return false;
			}
		}
		return true;
	}
}
