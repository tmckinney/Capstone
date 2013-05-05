package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure;

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
	

}
