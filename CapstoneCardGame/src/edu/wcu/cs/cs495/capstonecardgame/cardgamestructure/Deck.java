package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure;

import java.util.Date;
import java.util.Random;

import android.util.Log;
import edu.wcu.cs.cs495.capstonecardgame.R;
import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.ItemCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.NullCard;


/**
 * A class modeling a deck of cards.
 *
 * @author Tyler McKinney
 * @version 2013.2.7.1
 */
public class Deck {

	private static final int DEFAULT_DECK_SIZE = 50;

	private static final String TAG = "Deck: ";

	/** Array of cards in the deck. */
	private Card[] cards;

    /** The top card int the deck. */
	private int top;

	private boolean isFull;
    
    /**
     * Constructor to initiate the values of the <code>Deck</code>, possibly
     * to custom specifications.
     *
     * @param num The number of cards in the <code>Deck</code>.
     * @param fillDefault Flag specifing whether or not to fill the
     * <code>Deck</code> with default cards.
     */
	public Deck(int num, boolean fillDefault) {
		cards = new Card[num];
		top = 0;
	}
	
    /** Constructor to initialize a default <code>Deck</code>. */
	public Deck() {
		this(DEFAULT_DECK_SIZE, true);
		Log.d(CardGame.TAG, "" + cards.length);
	}
	
    public Deck(int num) {
		this(num, false);
	}

	
    /**
     * Returns the top card in the <code>Deck</code>.
     *
     * @return The drawn <code>Card</code>.
     */
	public Card drawCard() {
		Card card;
		Log.d(TAG, "top = " + top);
		if (top > 0) {
			card = cards[top];
			top--;
			Log.d(TAG, "Cards remaining: " + (top + 1));
		} else {
			card = NullCard.getInstance();
			Log.d(TAG, "Out of Cards");
		}
		return card;
	}
	
	public void addCard(Card card) {
		if (!isFull) {
			cards[top] = card;
			top++;
		}
		if (top == cards.length - 1)
			isFull = true;
	}

    /** Shuffles the deck using a Fisher-Yates shuffling algorithm. */
	public void shuffleDeck() {
		Date date = new Date();
		Random random = new Random(date.getTime());
		int j;
		for (int i = cards.length - 1; i >= 1; i--) {
			j = random.nextInt(i);
			Card temp = cards[i];
			cards[i] = cards[j];
			cards[j] = temp;
		}
	}
}
