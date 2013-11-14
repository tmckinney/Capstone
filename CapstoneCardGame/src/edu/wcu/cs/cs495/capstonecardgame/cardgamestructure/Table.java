package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure;

import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.NullCard;


/**
*  Table.java
*  Class modeling a table holding a specified number of cards.
*
*  @author Tyler McKinney
*  @version 2013.2.7.1
*/
public class Table {
	
	/**
	*  An array of <code>Card</code>s holding the cards to be displayed
	*  on the table.
	*/	
	private Card[] cards;
	
	/**
	*  Constructor to initialize the <code>Card</code> array to all
	*  <code>NullCard</code>s.
	*/
	public Table() {
		cards = new Card[CardGame.NUM_OF_CARDS];
		for (int i = 0; i < CardGame.NUM_OF_CARDS; i++) {
			cards[i] = NullCard.getInstance();
		}
	}
	
	/**
	*  Returns the <code>Card</code> at the specified index.
	*
	*  @param index The index of the desired <code>Card</code>.
	*   
	*  @return The <code>Card</code> at the index. 
	*/
	public Card getCard(int index) {
		return cards[index];
	}
	
	/**
	*  Sets the <code>Card</code> at the specified index to the
	*  <code>Card</code> specified.
	*
	*  @param index The index to the <code>Card</code> to be changed.
	*  @param card  The <code>Card</code> to be set at the index.
	*/ 	
	public void setCard(int index, Card card) {
		cards[index] = card;
	}
}
