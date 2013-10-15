package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards;

import android.content.Context;

/**
 * Interface describing the basic functions of a playing card or game card.
 *
 * @author Tyler McKinney
 * @version 2013.7.2.1
 */
public interface Card {
    
    /**
     * Returns the name of the card. The name of the card is a 
     * <code>String</code> describing the card.
     *
     * @return The name of the card.
     */
	public String getName();

    /**
     * Returns the imageID of the card. The image ID is an int mapping to 
     * an Android resource ID in R.java.
     *
     * @return The imageID of the card.
     */
	public int getImageID();
	
    /**
     * Returns the card's full description.
     *
     * @return The imageID of the card.
     */
	public String getDescription();

	public void setName(String string);

	public boolean canBeUsed();
	
	public void restoreImageID();

	boolean toast(Context context);

	public int getOwner();

	public void setOwner(int owner);
}
