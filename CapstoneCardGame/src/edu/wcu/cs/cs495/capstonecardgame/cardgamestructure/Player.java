package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure;

import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.NullCard;

/**
 * Player.java
 * A class to model a player in a card game.
 *
 * @author Tyler McKinney
 * @version 2013.2.7.1
 */
public class Player {
	
	/** The players max health. */
	private static final int MAX_HEALTH = 50;
    
    /** The player's name. */
	private String name;
	
    /** A Table holding the player's cards. */
	private Table table;
	
	/** The player's health */
	private int health;

	/** The player's hand; */
	private Table hand;
	
	/** The index into the players hand. */
	private int handIndex;

	/** Boolean indicating whether or not the player can draw. */
	private boolean canDraw;
	
    /**
     * Constructor to set up the <code>Player</code> fields.
     *
     * @param name The player's name.
     */
	public Player(String name) {
		if (name != null) {
			this.name = name;
		} else {
			this.name = "null";
		}
		//this.deck = new Deck(52, true);
		
		//this.cards = new Card[CardGame.NUM_OF_CARDS];
		
		//for (Card card: cards) {
		//	card = deck.drawCard();
		//}
		
		hand   = new Table();
		table  = new Table();
		health = MAX_HEALTH;
	}
	
    /**
     * Returns the player's name.
     *
     * @return The player's name.
     */
	public String getName() {
		return name;
	}
	
    /**
     * Returns the <code>Player</code>s <code>Table</code>.
     *
     * @return The <code>Player</code>s <code>Table</code>.
     */
	public Table getTable() {
		return table;
	}
	
	/**
	 * Returns the <code>Player</code>s health.
	 * 
	 * @return The <code>Player</code>s health.
	 */
	public int getHealth() {
		return health;
	}
    
    /** 
     * Returns a <code>String</code> representation of the <code>Player</code>.
     */
	public String toString() {
		return name + ": " + health + ":" + table.toString();
	}

	public Table getHand() {
		return hand;
	}

	public void setHand(Table hand) {
		this.hand = hand;
	}

	public int getHandIndex() {
		return handIndex;
	}

	public void addToHand(Card card) {
		hand.setCard(handIndex, card);
		handIndex++;
	}
	
	public void removeFromHand(int tag) {
		for (int i = tag; i < CardGame.NUM_OF_CARDS - 1; i++) {
			hand.setCard(i, hand.getCard(i + 1));
		}
		hand.setCard(CardGame.NUM_OF_CARDS - 1, NullCard.getInstance());
		if (handIndex > 0) {
			handIndex--;
		}
		canDraw = true;
	}

	public void setCanDraw(boolean canDraw) {
		this.canDraw = canDraw;
	}
	
	public boolean canDraw() {
		return canDraw;
	}

}
