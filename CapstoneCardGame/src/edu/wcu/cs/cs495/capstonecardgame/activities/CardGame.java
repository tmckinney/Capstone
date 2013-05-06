//23456789//23456789//23456789//23456789//23456789//23456789//23456789//23456789
package edu.wcu.cs.cs495.capstonecardgame.activities;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import edu.wcu.cs.cs495.capstonecardgame.R;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.ItemCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.NullCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.Deck;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.Player;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.Table;
import edu.wcu.cs.cs495.capstonecardgame.views.PopUpCardView;
import edu.wcu.cs.cs495.capstonecardgame.views.PopUpItemCardView;
import edu.wcu.cs.cs495.capstonecardgame.views.PopUpMonsterCardView;
import edu.wcu.cs.cs495.capstonecardgame.database.DataBaseHelper;
//import edu.wcu.cs.cs495.capstonecardgame.database.DatabaseInterface;
import edu.wcu.cs.cs495.capstonecardgame.database.DatabaseInterface;


/**
 * CardGame.java
 * An Android Activity that displays and controls the playing of a card game.
 *
 * @author Tyler McKinney
 * @version 2013.2.7.1
 */
public class CardGame extends Activity {

	/** Debugging Tag. **/
	public static final String TAG = "Card Game";

	/** The number of cards possible on the table **/
	public static final int NUM_OF_CARDS = 8;
	
	/** The padding for the card views in pixels. */
	public static final int PADDING = 5;
	
	/**
	 * The factor used to divide the table <code>LinearLayout</code>
	 * horizontally. Essentially the number of cards in each row.
	 */
	private static final int CARD_WIDTH_DIVDER = 4;

	/**
	 * The factor used to divide the table <code>LinearLayout</code> 
	 * vertically. Essentially the number of rows of cards.
	 */
	private static final int CARD_HIEGHT_DIVDER = 2;

	/** The number of players in the current game. */
	private int numOfPlayers;
	
	/** The current player selected to view table. */
	private int currentPlayer;
	
	/** The next slot in the player's hand available for use. */
	private int handIndex;

	/** The number of cards on the player's table. */
	private int cardsOnTable;
	
	/** Determines whether or not the player can draw a card. */
	private boolean canDraw;
	
	/** Determines whether of not the player can play a card to the table. */
	private boolean canPlay;

	/** Flag indicating whether or not the player can discard from their hand. */
	private boolean canDiscardHand;

	/** Flag indicating whether or not the player can discard from their table. */
	private boolean canDiscardTable;

	/** 
	 * Determines whether or not the cards have been drawn on the table for 
	 * the first time.
	 */
	private boolean drawn = false;
	
	/** Flag indicating if the player is currently play a card from their hand to the table. */
	private boolean playingCard;
	
	/** OnClickListener for normal operation of the table. */
	private OnClickListener normalListener;

	/**
	 * <code>LinearLayout</code> representing the grid of <code>Card</code>s
	 * on the table.
	 */
	private LinearLayout tableLayout;

	/** Holds the <code>View</code> representing the discard pile. **/ 
	private ImageView discard;

	/** Holds the <code>View</code> representing the deck of cards. **/
	private ImageView deck;

	/** Array containing the cards on the table **/
	private ImageView[] tableCards;
	
	/**
	 * Holds the <code>RadioButton</code> containing the individual player 
	 * cards.
	 */
	private RadioGroup playerRadios;
	
	/** The dimensions of the <code>ImageView</code> representing each card. */
	private LayoutParams cardParams;
	
	/** The table to draw. */
	private Table table;

	/** The player's hand/ */
	private Table hand;

	/** Deck modeling the discard pile of a card game. */
	private Deck discardObject;

	/** Data structure holding the deck of <code>Card</code>s. **/
	private Deck deckObject;

	/** An array containing all of the <code>Player</code>s. */
	private Player[] players;
	
	/** A custom view showing a card and all of it's information. */
	private PopUpCardView cardView;

	/** 
	 * Specifies the behavior of the <code>Activity</code> as soon as it is
	 * created.
	 * 
	 * @param savedInstanceState A <code>Bundle</code> containing a saved 
	 * 						     state of the <code>Activity</code>
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_landscape);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		tableLayout =
				(LinearLayout) findViewById(R.id.table);

		playerRadios =
				(RadioGroup) findViewById(R.id.player_radios);

		ViewTreeObserver tableObserver = tableLayout.getViewTreeObserver();

		tableObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				if (!drawn) {
					int cardHeight = 
							tableLayout.getHeight() / CARD_HIEGHT_DIVDER;
					int cardWidth  = 
							tableLayout.getWidth() / CARD_WIDTH_DIVDER;
					cardParams = 
							new LinearLayout.LayoutParams(cardWidth, cardHeight);	
					addCardsToTable();
					drawTable();
					setClickListeners();
					drawn = true;
				}
			}
		});
		
		init(savedInstanceState);
	}

	/**
	 * Creates an <code>ImageView</code> for each card and adds it to both the
	 * table <code>LinearLayout</code> and the array holding references to the
	 * cards.
	 */
	private void addCardsToTable() {

		LinearLayout tableRow1 = (LinearLayout) 
				findViewById(R.id.row1);

		LinearLayout tableRow2 = (LinearLayout) 
				findViewById(R.id.row2);


		tableCards = new ImageView[NUM_OF_CARDS];

		ImageView card1, card2;

		for (int i = 0; i < NUM_OF_CARDS / 2; i++) {
			card1 = new ImageView(this);
			card2 = new ImageView(this);

			card1.setLayoutParams(cardParams);
			card1.setImageResource(R.drawable.ic_launcher);
			card1.setTag(i);
			card1.setPadding(PADDING, PADDING, PADDING, PADDING);

			card2.setLayoutParams(cardParams);
			card2.setImageResource(R.drawable.ic_launcher);
			card2.setTag(i + 4);
			card2.setPadding(PADDING, PADDING, PADDING, PADDING);
			
			Log.d(TAG, "adding card " + i);
			tableRow1.addView(card1);
			Log.d(TAG, "adding card " + (i + 4));
			tableRow2.addView(card2);

			tableCards[i]                    = card1;
			tableCards[i + NUM_OF_CARDS / 2] = card2;
		}
	}
	
	private void readDeck() {
   
        SQLiteDatabase db = null;
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        
        try {
        	
	        myDbHelper.openDataBase();
	        db = myDbHelper.getMyDataBase();
	        
        } catch(SQLException sqle){

        	Log.e(TAG, "Caught SQL exception");

        }
        
		Cursor cur = db.query(DatabaseInterface.MONSTER_TABLE, null, null, null, null, null, null);
		cur.moveToFirst();
		
		deckObject = new Deck(cur.getCount(), false);
		
		while (!cur.isAfterLast()) {
			
			Log.d(DatabaseInterface.MONSTER_TABLE, "" + cur.getCount());
			
			int defense = 15; //cur.getInt(cur.getColumnIndex(DatabaseInterface.DEFENSE_POINTS));
			String effect = cur.getString(cur.getColumnIndex(DatabaseInterface.EFFECT));			
			int id= cur.getInt(cur.getColumnIndex(DatabaseInterface.M_CARD_ID));
			String name = cur.getString(cur.getColumnIndex(DatabaseInterface.M_NAME));
			String description = cur.getString(cur.getColumnIndex(DatabaseInterface.M_DISC));
			String type = cur.getString(cur.getColumnIndex(DatabaseInterface.TYPE));
			int health = cur.getInt(cur.getColumnIndex(DatabaseInterface.HP));
			int attack = 10;// = cur.getInt(cur.getColumnIndex(DatabaseInterface.ATTACK_POINTS));
			int accuracy = 100; //cur.getInt(cur.getColumnIndex(DatabaseInterface.ACCURACY));
			float regen_rate = cur.getFloat(cur.getColumnIndex(DatabaseInterface.REGEN_RATE));

			
			MonsterCard card = new MonsterCard(id, name, description, type, health, attack, defense, accuracy, regen_rate, effect);
			deckObject.addCard(card);
			
			cur.moveToNext();
		}
		
		cur = db.query(DatabaseInterface.ITEM_TRAP_TABLE, null, null, null, null, null, null);
		cur.moveToFirst();
		while (!cur.isAfterLast()) {
/*			Log.d(DatabaseInterface.ITEM_TRAP_TABLE, "" + cur.getCount());
			Log.d(DatabaseInterface.ITEM_TRAP_TABLE, "" + cur.getInt(cur.getColumnIndex(DatabaseInterface.I_CARD_ID)));
			Log.d(DatabaseInterface.ITEM_TRAP_TABLE, cur.getString(cur.getColumnIndex(DatabaseInterface.I_NAME)));
			Log.d(DatabaseInterface.ITEM_TRAP_TABLE, cur.getString(cur.getColumnIndex(DatabaseInterface.I_POWER)));
			Log.d(DatabaseInterface.ITEM_TRAP_TABLE, "" + cur.getString(cur.getColumnIndex(DatabaseInterface.I_DISCRIPTION)));
			Log.d(DatabaseInterface.ITEM_TRAP_TABLE, "" + cur.getInt(cur.getColumnIndex(DatabaseInterface.ONE_TIME_USE)));
			*/
			int id = cur.getInt(cur.getColumnIndex(DatabaseInterface.I_CARD_ID));
			String name = cur.getString(cur.getColumnIndex(DatabaseInterface.I_NAME));
			String description = cur.getString(cur.getColumnIndex(DatabaseInterface.I_DISCRIPTION));
			String power = cur.getString(cur.getColumnIndex(DatabaseInterface.EFFECT));
			boolean oneTimeUse = (cur.getInt(cur.getColumnIndex(DatabaseInterface.ONE_TIME_USE)) == 0 ? true : false);
			
			ItemCard card = new ItemCard(id, name, description, power, oneTimeUse);
			deckObject.addCard(card);
			
			cur.moveToNext();
		}
		
		myDbHelper.close();
	}
	
	/** Helper method to initialize some of the fields. */
	private void init(Bundle bundle) {
		deck = (ImageView) findViewById(R.id.deck);
		discard = (ImageView) findViewById(R.id.discard);
		deck.setImageResource(R.drawable.ic_launcher);
		discard.setImageResource(R.drawable.nc);
		

		this.handIndex       = 0;
		this.numOfPlayers    = 4; // TODO:Remove after testing;
		this.canDraw         = true;
		this.canPlay         = true;
		this.canDiscardHand  = true;
		this.canDiscardTable = true;
		this.players         = new Player[numOfPlayers];
		this.discardObject   = new Deck(NUM_OF_CARDS, false);
		this.hand            = new Table();
		this.table           = hand;
		
		players[0] = new Player("Tyler");
		players[1] = new Player("Tamara");
		players[2] = new Player("Jae");
		players[3] = new Player("Michael");

		readDeck();
		deckObject.shuffleDeck();
		
		normalListener = new OnClickListener() {

			@Override
			public void onClick(View tableCard) {
				Log.d(TAG, "Clicked card " + tableCard.getTag());
				cardClicked((ImageView) tableCard);
			}
		};
	}

	/** Helper method to set <code>OnClickListener</code>s to each GUI element. */
	private void setClickListeners() {
		setTableListeners();
		setDeckListeners();
		setRadioListener();	
	}
	
	/** Helper method to set the listeners for the table cards. */
	private void setTableListeners() {
		for (View card : tableCards) {
			card.setOnClickListener(normalListener);
		}
	}
	
	/** Helper method to set the Deck listeners. */
	private void setDeckListeners() {
		deck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (canDraw) {
					table = hand;
					Card card = deckObject.drawCard();
					if (card == NullCard.getInstance()) {
						deck.setImageResource(R.drawable.nc);
					}
					if (card == null) {
						Log.e(TAG, "Card is null.");
					}
					
					if (card.getName() == null) {
						Log.e(TAG, "Card name is null");
						card.setName("Null Card");
					}
					
					Log.d(TAG, "Drew a " + card.getName());
					hand.setCard(handIndex, card);
					handIndex++;
					drawTable();
					if (handIndex == NUM_OF_CARDS) {
						canDraw = false;
					}
				}
			}
		});
		
		discard.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.d(TAG, "Discarded a card.");
			}
		});
	}
	
	/** 
	 * Helper method to set the <code>OnClickListener</code> for the 
	 * <code>RadioGroup</code>. 
	 * @return 
	 */
	private void setRadioListener() {
		playerRadios.setOnCheckedChangeListener(new
				OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group,
					int checkedId) {
				viewChanged();
				RadioButton rb =
						(RadioButton) findViewById(checkedId);
				Log.d(TAG,
						"Player view changed to " + rb.getText() + "; with tag " + rb.getTag());
				int tag = Integer.parseInt((String) rb.getTag());
				currentPlayer = tag;
				table = players[tag].getTable();
				drawTable();
			}
		});
	}
	
	/** 
	 * Observer notified when the player changes the view and resets the 
	 * table listeners if the player was playing a card. 
	 */
	private void viewChanged() {
		if (playingCard) {
			playingCard = false;
			setTableListeners();
		}
	}
	
	/** 
	 * Helper method displaying a choice dialog when a card is clicked. 
	 * 
	 * @param tableCard The clicked card. 
	 */
	private void cardClicked(ImageView card) {
		if (table.getCard((Integer) card.getTag()) != NullCard.getInstance()) {
			if (table == hand) {
				clickedHandCard(card);
			} else if (table == players[0].getTable()){
				clickedTableCard(card);
			}
		}
	}
	
	/** Helper method displaying a choice dialog when a card in the player's hand is clicked. */
	private void clickedHandCard(ImageView handCard) {
		final View card = handCard;
		AlertDialog.Builder promptBuilder = new AlertDialog.Builder(this);

		promptBuilder.setTitle("Choose an Action");
		if (canPlay && canDiscardHand) {
			promptBuilder.setMessage("Play, Discard, or Cancel.");
		} else if (canPlay) {
			promptBuilder.setMessage("Play or Cancel");
		} else if (canDiscardHand){
			promptBuilder.setMessage("Discard or Cancel");
		} else {
			promptBuilder.setMessage("Cancel to Exit");
		}
		//promptBuilder.setView(View v);
		if (table.getCard((Integer) handCard.getTag()) instanceof MonsterCard) {
			cardView = new PopUpMonsterCardView(this);
		} else {
			cardView = new PopUpItemCardView(this);
		}
		cardView.setAll(table.getCard((Integer) handCard.getTag()));
		promptBuilder.setView(cardView);

		promptBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}

		});
		
		if (canDiscardHand) {
			promptBuilder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					discard((ImageView) card);
				}
			});
		}
		
		if (canPlay) {
			promptBuilder.setPositiveButton("Play", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
					playCard(card);
				}
			});
		}

		AlertDialog prompt = promptBuilder.create();
		prompt.show();
	}
	
	/** Helper method displaying a choice dialog when a card on the player's table is clicked. */
	private void clickedTableCard(ImageView tableCard) {
		final View card = tableCard;

		AlertDialog.Builder promptBuilder = new AlertDialog.Builder(this);


		promptBuilder.setTitle("Choose an Action");
		if (canDiscardTable) {
			promptBuilder.setMessage("Discard or Cancel.");
		} else {
			promptBuilder.setMessage("Cancel to Exit");
		}
		//promptBuilder.setView(View v);
		cardView = new PopUpCardView(this);
		cardView.setAll(table.getCard((Integer) tableCard.getTag()));
		promptBuilder.setView(cardView);

		promptBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}

		});
		
		if (canDiscardTable) {
			promptBuilder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					discard((ImageView) card);
				}
			});
		}

		AlertDialog prompt = promptBuilder.create();
		prompt.show();
	}
	/** 
	 * Helper method to move a card from the player's hand to the player's table. 
	 */
	private void playCard(View viewCard) {
		final int handIndex = (Integer) viewCard.getTag();
		Log.d(TAG, "playing card : " + handIndex);
		final Card handCard  = hand.getCard(handIndex);
		table = players[0].getTable();
		setRadio1Selected();
		drawTable();
		playingCard = true;
		for (View view : tableCards) {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View tableCard) {
					int tag = (Integer) tableCard.getTag();
					Log.d(TAG, "clicked card : " + tag);
					if (table.getCard(tag) == NullCard.getInstance()
							&& table == players[0].getTable()) {
						Log.d(TAG,"Seting card " + tag + " to " + handCard.getName());
						players[0].getTable().setCard(tag, handCard);
						cardsOnTable++;
						Log.d(TAG, "Done");
						drawTable();
						Log.d(TAG, "removeing card");
						removeCard(handIndex);
						Log.d(TAG, "done");
						table = hand;
						drawTable();
						if (cardsOnTable == NUM_OF_CARDS) {
							canPlay = false;
						}
					}
					setTableListeners();
				} 
			});
		}
	}
	
	/** Helper method to set a <code>RadioButton</code> to selected. */
	private void setRadio1Selected() {
		Log.d(TAG, "ID:" + playerRadios.getCheckedRadioButtonId());
		RadioButton rb = (RadioButton) findViewById(playerRadios.getCheckedRadioButtonId());
		rb.setSelected(false);
		rb = (RadioButton) findViewById(R.id.player_1_radio);
		rb.setSelected(true);
	}
	
	/** Helper method to remove a card from the hand array. */
	private void removeCard(int tag) {
		for (int i = tag; i < NUM_OF_CARDS - 1; i++) {
			hand.setCard(i, hand.getCard(i + 1));
		}
		hand.setCard(NUM_OF_CARDS - 1, NullCard.getInstance());
		if (handIndex > 0) {
			handIndex--;
		}
		drawTable();
		canDraw = true;
	}

	/**
	 * Sets the <code>ImageView</code> backgrounds to the corresponding 
	 * imageID in the currently viewed table.
	 */
	private void drawTable() {
		for (int i = 0; i < NUM_OF_CARDS; i++) {
			tableCards[i].setImageResource(getImageId(table.getCard(i).getImageID()));
		}
	}

	//TODO: Remove  after testing
	private void test() {
		for (int i = 0; i < numOfPlayers; i++) {
			for (int j = 0; j < NUM_OF_CARDS; j++) {
				Card card = deckObject.drawCard();
				if (card == NullCard.getInstance()) {
					deck.setImageResource(R.drawable.nc);
				}
				players[i].getTable().setCard(j, card);
			}
		}
		for (int i = 0; i < NUM_OF_CARDS; i++) {
			Card card = deckObject.drawCard();
			if (card == NullCard.getInstance()) {
				deck.setImageResource(R.drawable.nc);
			}
			hand.setCard(i, card);
		}
		drawTable();

	}

	/**
	 * Moves a <code>Card</code> from a the <code>Player</code>'s
	 * <code>Table</code> or hand to the discard pile.
	 *
	 * @param card The card to discard.
	 */
	public void discard(ImageView card) {
		int tag = (Integer) card.getTag();
		if (table.getCard(tag) != NullCard.getInstance()) {
			if (table == hand) {
				discard.setImageResource(getImageId(hand.getCard(tag).getImageID()));
				discardObject.addCard(hand.getCard(tag));
				removeCard(tag);
			} else if (table == players[0].getTable()){
				Table playerTable  = players[0].getTable();
				discard.setImageResource(playerTable.getCard(tag).getImageID()); 
				discardObject.addCard(playerTable.getCard(tag));
				playerTable.setCard(tag, NullCard.getInstance());
				cardsOnTable--;
				drawTable();
				canDraw = true;
			}
		}
	}
	
	public static int getImageId(int id) {
		int imageID;
		switch (id) {
		case 1:
			imageID = R.drawable.zn;
			break;
		case 2:
			imageID = R.drawable.fs;
			break;
		case 3:
			imageID = R.drawable.is;
			break;
		case 4:
			imageID = R.drawable.ytsorf;
			break;
		case 101:
		case 103:
			imageID = R.drawable.hp;
			break;
		case 102:
			imageID = R.drawable.mp;
			break;
		case 104:
			imageID = R.drawable.bitter_bomb;
			break;
		default:
			imageID = -999999999;
			break;
		}
		return imageID;
	}

	/**
	 * Specifies the behavior of the <code>Menu</code> as soon as it is 
	 * created.
	 * 
	 * @param menu The <code>Menu</code> object to be inflated.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is
		// present.
		getMenuInflater().inflate(R.menu.activity_card_game, menu);
		return true;
	}

	/**
	 * Controls the behavior of the attack button.
	 * 
	 * @param v The view that trigger the event.
	 */
	public void attack(View v) {
		Log.d(TAG, "Attack!");
		test();
	}

	/**
	 * Controls the behavior of the pass button.
	 * 
	 * @param v The view that trigger the event.
	 */
	public void pass(View v) {
		Log.d(TAG, "pass");
	}

	/**
	 * Controls the behavior of the view hand button.
	 * 
	 * @param v The view that trigger the event.
	 */
	public void viewHand(View v) {
		viewChanged();
		Log.d(TAG, "view hand");
		table = hand;
		drawTable();
	}

	/**
	 * Controls the behavior of the view table button.
	 * 
	 * @param v The view that trigger the event.
	 */
	public void viewTable(View v) {
		viewChanged();
		Log.d(TAG, "view table");
		table = players[currentPlayer].getTable();
		drawTable();
	}

	/**
	 * Sets the number of players in the game.
	 *
	 * @param num The number of players in the game.
	 */
	public void setNumOfPlayers(int num) {
		this.numOfPlayers = num;
	}
	
	/** 
	 * Sets whether or not the player can draw a card.
	 * 
	 * @param canDraw Whether or not the player can draw a card.
	 */
	public void setCanDraw(boolean canDraw) {
		this.canDraw = canDraw;
	}
	
	/** 
	 * Sets whether or not the player can play a card to the table.
	 * 
	 * @param canDraw Whether or not the player can play a card to the table.
	 */
	public void setCanPlay(boolean canPlay) {
		this.canPlay = canPlay;
	}
	
	/** 
	 * Sets whether or not the player can discard a card from their hand.
	 * 
	 * @param canDraw Whether or not the player can discard a card from their hand.
	 */
	public void setCanDiscardHand(boolean canDiscardHand) {
		this.canDiscardHand = canDiscardHand;
	}
	
	/** 
	 * Sets whether or not the player can discard a card from their table.
	 * 
	 * @param canDraw Whether or not the player can discard a card from their table.
	 */
	public void setCanDiscardTable(boolean canDiscardTable) {
		this.canDiscardTable = canDiscardTable;
	}
}