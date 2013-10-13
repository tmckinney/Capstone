package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards;


import android.content.Context;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;


/**
 * NullCard.java
 * A class modeling a empty slot on a table of cards.
 * Uses the Singleton Pattern and a modified version the NullObject
 * pattern. 
 *
 * @author Tyler McKinney
 * @version 2013.2.7.1
 */
public class NullCard implements Card {

    /** Singleton <code>NullCard</code> instance. */
	private static NullCard instance;

    /** The name of the card, mainly used for logging. */
	private String name;

    /** Integer value contain Android resource ID. */
	private int imageID;
	
	/** The card's description. */
	private String description;
	
    /** Private constructor to initiate the fields of the Singleton. */
	private NullCard() {
		this.name        = "Null Card";
		this.imageID     = 0;
		this.description = "A card representing an empty space on the table.";
	}
	
    /** 
     * Method to retrieve the Singleton. If it has not been created, i.e.
     * instance is null, creates it. If it has been created, returns instance. 
     *
     * @return The instance of this Singleton.
     */
	public static NullCard getInstance() {
		if (instance == null) {
			instance = new NullCard();
		}
		return instance;
	}
    
    /**
     * Returns the name of the <code>NullCard</code>, in this case, always 
     * "Null Card".
     *
     * @return The name of the <code>NullCard</code>.
     */
	@Override
	public String getName() {
		return name;
	}

    /**
     * Returns the image ID of the <code>NullCard</code>, in this case, always
     * "R.drawable.nc".
     *
     * @return The imageID of the <code>NullCard</code>.
     */
	@Override
	public int getImageID() {
		return imageID;
	}

	
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
		return -1;
	}

	@Override
	public void setOwner(int owner) {
		// TODO Auto-generated method stub
		
	}
}
