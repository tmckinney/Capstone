package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards;

import android.content.Context;

/**
 * Class modeling a playing card.
 *
 * @author Tyler McKinney
 * @version 2013.7.2.1
 */
public class PlayingCard implements Card {
    
    /** Integer ID mapping to an Android resource ID. */
	private int imageID;

    /** The name of the card. */
	private String name;
	
	/** The card's description. */
	private String description;
    
    /** Constructor to initialize the <code>PlayingCard</code>s fields. */
	public PlayingCard(int id, String name, String description) {
		this.imageID     = id;
		this.name        = name;
		this.description = description;
	}
	
    /** Constructor to initialize the <code>PlayingCard</code>s fields. */
	public PlayingCard(int id, String name) {
		this.imageID     = id;
		this.name        = name;
		this.description = "I'm too lazy right now to add descriptions to every card.";
	}
    
    /** 
     * Returns the name of the <code>PlayingCard</code>.
     *
     * @return The name of the <code>PlayingCard</code>.
     */
	public String getName() {
		return name;
	}
	
    /**
     * Returns the imageID of the <code>PlayingCard</code>.
     *
     * @return The imageID of the <code>PlayingCard</code>.
     */
	public int getImageID() {
		return imageID;
	}
	
	/**
	 * Returns the card's description.
	 * 
	 * @return The card's description.
	 */

	public String getDescription() {
		return description;
	}

	@Override
	public void setName(String string) {
		name = string;
		
	}

	@Override
	public boolean canBeUsed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void restoreImageID() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean toast(Context context) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getOwner() {
		return 0;
	}

	@Override
	public void setOwner(int owner) {
		// TODO Auto-generated method stub
		
	}
}
