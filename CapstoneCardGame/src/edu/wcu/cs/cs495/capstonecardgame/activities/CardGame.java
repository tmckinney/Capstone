//23456789//23456789//23456789//23456789//23456789//23456789//23456789//23456789
package edu.wcu.cs.cs495.capstonecardgame.activities;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import edu.wcu.cs.cs495.capstonecardgame.R;
import edu.wcu.cs.cs495.capstonecardgame.activities.activityhelpers.DeckBuilder;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.ItemCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.NullCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.ActionHandler;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.Deck;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.Player;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.Table;
import edu.wcu.cs.cs495.capstonecardgame.server.CallCodes;
import edu.wcu.cs.cs495.capstonecardgame.server.NetworkQueue;
import edu.wcu.cs.cs495.capstonecardgame.views.BattleView;
import edu.wcu.cs.cs495.capstonecardgame.views.PopUpCardView;
import edu.wcu.cs.cs495.capstonecardgame.views.PopUpItemCardView;
import edu.wcu.cs.cs495.capstonecardgame.views.PopUpMonsterCardView;


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

	public static final long PROMPT_DELAY = 0001;

	/** The number of players in the current game. */
	private int numOfPlayers;
	
	/** The current player selected to view table. */
//	private int currentPlayerIndex;
	
	/** The next slot in the player's hand available for use. */
//	private int handIndex;

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
//	private Table hand;

	/** Deck modeling the discard pile of a card game. */
	private Deck discardObject;

	/** Data structure holding the deck of <code>Card</code>s. **/
	private Deck deckObject;

	/** An array containing all of the <code>Player</code>s. */
	private Player[] players;
	
	/** A custom view showing a card and all of it's information. */
	private View cardView;

	private boolean canUse;

	private int playerID;

	private int turn;
	
	private long seed;

	private Button health;

	private boolean selectingTarget;

	protected boolean cardSelected;

	private Card activatedCard;

	private ActionHandler handler;

	private AlertDialog.Builder promptBuilder;

	private AlertDialog prompt;

	private Card targetCard;
	
	private NetworkQueue networkQueue;
	
//	private Player currentPlayer;

	private Player thisPlayer;
	
	private Table myHand;

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
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		tableLayout =
				(LinearLayout) findViewById(R.id.table);

		playerRadios =
				(RadioGroup) findViewById(R.id.player_radios);
		
		canUse = true;

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
							new LinearLayout.LayoutParams(cardWidth, cardHeight, 1);	
					addCardsToTable();
					drawTable();
					setClickListeners();
					loadHistory();
					drawn = true;
				}
			}
		});
		
		init(savedInstanceState);
	}

	protected void loadHistory() {
		SharedPreferences prefs = this.getPreferences(MODE_PRIVATE);
		String history = prefs.getString("HISTORY", "");
		Log.d(TAG, "History = \"" + history + "\"");
		if (!history.equals("")) 
			this.parseCallCodes(history);
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

			tableRow1.addView(card1);
			tableRow2.addView(card2);

			tableCards[i]                    = card1;
			tableCards[i + NUM_OF_CARDS / 2] = card2;
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(TAG, "Stopping");
		SharedPreferences.Editor prefsEdit = this.getPreferences(MODE_PRIVATE).edit();
		String queue = this.networkQueue.toString();
		Log.d(TAG, "queue = " + queue);
		prefsEdit.remove("HISTORY");
		prefsEdit.putString("HISTORY", queue);
		prefsEdit.commit();
	}

	/** Helper method to initialize some of the fields. */
	private void init(Bundle bundle) {
		deck = (ImageView) findViewById(R.id.deck);
		discard = (ImageView) findViewById(R.id.discard);
		deck.setImageResource(R.drawable.ic_launcher);
		discard.setImageResource(R.drawable.nc);
		health = (Button) findViewById(R.id.menu_1);


		this.numOfPlayers    = 4;
		this.canDraw         = true;
		this.canPlay         = true;
		this.canDiscardHand  = true;
		this.canDiscardTable = true;
		this.players         = new Player[numOfPlayers];
		this.discardObject   = new Deck(NUM_OF_CARDS, false);
		this.selectingTarget = false;
		//TODO Remove after testing
		this.players[0] = new Player("Tyler");
		this.players[1] = new Player("Tamara");
		this.players[2] = new Player("Jae");
		this.players[3] = new Player("Michael");
		
		this.playerID   = 0;
		//TODO End testing 
		
//		this.currentPlayer = players[0];
		this.thisPlayer    = players[playerID];
		this.myHand        = thisPlayer.getHand();
		
		this.table    = myHand;

		deckObject = DeckBuilder.readDeck(deckObject, this);
		
		//TODO: Fix after testing.
		seed = 1382920215105l; //System.currentTimeMillis();
				
		deckObject.shuffleDeck(seed);
		
		this.networkQueue = new NetworkQueue();
	
		networkQueue.add(CallCodes.SET_SEED 
			         + CallCodes.SEPARATOR + seed
			         + CallCodes.SEPARATOR);
		
		normalListener = new OnClickListener() {

			@Override
			public void onClick(View tableCard) {
				cardClicked((ImageView) tableCard);
			}
		};
		
		health.setText("" + thisPlayer.getHealth());
		
		handler = ActionHandler.getInstance();
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
				if (canDraw && !deckObject.isEmpty()) {
					table = myHand;
					Card card = deckObject.drawCard();
					networkQueue.add(CallCodes.DRAW_CARD 
									 + CallCodes.SEPARATOR + playerID 
									 + CallCodes.SEPARATOR);
					if (card == null) {
						card = NullCard.getInstance();
					}
					
					card.setOwner(playerID);
					thisPlayer.addToHand(card);
					drawTable();
					if (thisPlayer.getHandIndex() == NUM_OF_CARDS) {
						canDraw = false;
					}
					if (deckObject.isEmpty()) {
						deck.setImageResource(R.drawable.nc);
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
				int tag = Integer.parseInt((String) rb.getTag());
				table = players[tag].getTable();
				drawTable();
				
				health.setText("" + players[tag].getHealth());
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
			if (selectingTarget) {
				targetCard = getTargetCard(card);
			} else if (table == myHand) {
				clickedHandCard(card);
			} else if (table == thisPlayer.getTable()){
				clickedTableCard(card);
			}
		}
	}
	
	/** Helper method displaying a choice dialog when a card in the player's hand is clicked. */
	private void clickedHandCard(ImageView handCard) {
		final View card = handCard;
		
		if (promptBuilder == null)
			promptBuilder = new AlertDialog.Builder(this);

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
		((PopUpCardView) cardView).setAll(table.getCard((Integer) handCard.getTag()));
		promptBuilder.setView((View) cardView);

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

		prompt = promptBuilder.create();
		prompt.show();
	}
	
	/** Helper method displaying a choice dialog when a card on the player's table is clicked. */
	private void clickedTableCard(ImageView tableCard) {
		final View card = tableCard;
		
		if (promptBuilder == null)
			promptBuilder = new AlertDialog.Builder(this);
		
		Card cardCard = table.getCard((Integer) tableCard.getTag());

		promptBuilder.setTitle("Choose an Action");
		if (canUse && canDiscardTable && cardCard.canBeUsed()) {
			if (cardCard instanceof MonsterCard) {
				promptBuilder.setMessage("Attack, Discard, or Cancel");
			} else if (cardCard instanceof ItemCard) {
				promptBuilder.setMessage("Use, Discard, or Cancel");
			}
		} else if (canDiscardTable) {
			promptBuilder.setMessage("Discard or Cancel.");
		} else {
			promptBuilder.setMessage("Cancel to Exit");
		}
		//promptBuilder.setView(View v);

		if (cardCard instanceof MonsterCard) {
			cardView = new PopUpMonsterCardView(this);
		} else {
			cardView = new PopUpItemCardView(this);
		}
		((PopUpCardView) cardView).setAll(table.getCard((Integer) tableCard.getTag()));
		promptBuilder.setView((View) cardView);

		promptBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}

		});
		
		if (canDiscardTable) {
			if (cardCard instanceof MonsterCard);
			promptBuilder.setNeutralButton("Discard", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					discard((ImageView) card);
				}
			});
		}
		
		if (canUse && cardCard.canBeUsed()) {
			String title = "Error";
			if (cardCard instanceof MonsterCard) {
				title = "Attack";
			} else if (cardCard instanceof ItemCard) {
				title = "Use";
			}
			promptBuilder.setPositiveButton(title, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					activate(table.getCard((Integer) card.getTag()));
				}

			});
		}

		prompt = promptBuilder.create();
		prompt.show();
	}
	
	/**
	 * Allow's the player to select a target card.
	 * @param card The attacking or activated card selected.
	 */
	protected void activate(Card card) {
		selectingTarget = true;
		activatedCard   = card;
	}

	/** 
	 * Allows the player to confirm a selected target. 
	 * @param tableCard The card view selected
	 * @return The card object of the selected view.
	 */
	private Card getTargetCard(View tableCard) {
		
		//final View card = tableCard;
		cardSelected = false;
		final Card card = table.getCard((Integer) tableCard.getTag());
		
		if (!card.canBeUsed()) {
			card.toast(this);
			return card;
		}

		if (promptBuilder == null)
			promptBuilder = new AlertDialog.Builder(this);
		
		String action = "";
		
		if ( card instanceof MonsterCard) {
			action = "Confirm This Attack?";
		} else {
			action = "Use This Item?";
		}


		promptBuilder.setTitle(action);
	
		//promptBuilder.setView(View v);
		cardView = new BattleView(this);
		((BattleView) cardView).setAll(activatedCard, table.getCard((Integer) tableCard.getTag()));
		promptBuilder.setView((View) cardView);
		
		promptBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				cardSelected(card);
				selectingTarget = false;
			}
		});
		
		promptBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		
		promptBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				selectingTarget = false;
				dialog.cancel();
			}

		});

		prompt = promptBuilder.create();
		prompt.show();
		return card;
	}

	/**
	 * Initiates a battle once a target has been selected.
	 * @param card The target card.
	 */
	protected void cardSelected(Card card) {
		
		if (card.canBeUsed()) {
			card.toast(this);
			handler.setup(activatedCard, targetCard);
			handler.simulate(this);
			showResult();
		}
		
	}

	/**
	 * Shows the result of a battle.
	 */
	private void showResult() {
		
		promptBuilder = new AlertDialog.Builder(this);
		
		promptBuilder.setTitle("Aftermath");
		
		cardView = new BattleView(this);
		((BattleView) cardView).setAll(activatedCard, targetCard);
		promptBuilder.setView((View) cardView);
		
		prompt = promptBuilder.create();

		prompt.show();
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				prompt.dismiss();
				
			}
		}, PROMPT_DELAY);
		
		drawTable();
		
	}

	/** 
	 * Helper method to move a card from the player's hand to the player's table. 
	 */
	private void playCard(View viewCard) {
		final int handIndex = (Integer) viewCard.getTag();
		final Card handCard  = myHand.getCard(handIndex);
		table = thisPlayer.getTable();
		playerRadios.check(R.id.player_1_radio);
		drawTable();
		playingCard = true;
		for (View view : tableCards) {
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View tableCard) {
					int tag = (Integer) tableCard.getTag();
					if (table.getCard(tag) == NullCard.getInstance()
							&& table == thisPlayer.getTable()) {
						networkQueue.add(CallCodes.PLAY_CARD 
										 + CallCodes.SEPARATOR + playerID 
										 + CallCodes.SEPARATOR + handCard.getImageID() 
										 + CallCodes.SEPARATOR + tag
										 + CallCodes.SEPARATOR);
						thisPlayer.getTable().setCard(tag, handCard);
						cardsOnTable++;
						drawTable();
						removeCard(playerID, handIndex);
						table = myHand;
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
	
	/** Helper method to remove a card from the hand array. */
	private void removeCard(int player, int tag) {
		players[player].removeFromHand(tag);
		drawTable();
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

	/**
	 * Moves a <code>Card</code> from a the <code>Player</code>'s
	 * <code>Table</code> or hand to the discard pile.
	 *
	 * @param card The card to discard.
	 */
	public void discard(ImageView card) {
		int tag = (Integer) card.getTag();
		Card tableCard = table.getCard(tag);
		tableCard.restoreImageID();
		
		if (table.getCard(tag) != NullCard.getInstance()) {
			if (table == myHand) {
				int imageID = getImageId(myHand.getCard(tag).getImageID());
				discard.setImageResource(imageID);
				discardObject.addCard(myHand.getCard(tag));
				removeCard(playerID, tag);
				canDraw = true;
			} else if (table == thisPlayer.getTable()){
				Table playerTable  = thisPlayer.getTable();
				discard.setImageResource(getImageId(playerTable.getCard(tag).getImageID())); 
				discardObject.addCard(playerTable.getCard(tag));
				playerTable.setCard(tag, NullCard.getInstance());
				cardsOnTable--;
				canPlay = true;
			}
			drawTable();
		}
	}
	
	/**
	 * Returns the drawable id associated with the id given.
	 * @param id The ID of the card 
	 * @return The drawable id of the Card.
	 */
	public static int getImageId(int id) {
		int imageID;
		switch (id) {
		case -1:
			imageID = R.drawable.dead;
			break;
		case 0:
			imageID = R.drawable.nc;
			break;
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
		case 5:
			imageID = R.drawable.gp;
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
			imageID = R.drawable.error;
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
		playerRadios.check(R.id.player_1_radio);
		table = myHand;
		drawTable();
	}

	/**
	 * Controls the behavior of the view table button.
	 * 
	 * @param v The view that trigger the event.
	 */
	public void viewTable(View v) {
		viewChanged();
		table = thisPlayer.getTable();
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
	
	/**
	 * Set's the current turn.
	 * @param turn The turn to set.
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	/**
	 * Sets this <code>CardGame</code>s player ID.
	 * @param playerID The new player ID
	 */
	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}
	
	/**
	 * Advances the game to the next turn.
	 */
	public void nextTurn() {
		turn = (turn + 1) % numOfPlayers;
		if (turn == playerID)
			canPlay = canUse = canDraw = canDiscardHand = canDiscardTable = true;
	}

	/**
	 * Returns the seed used for deck randomization.
	 * @return the seed
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * Sets the seed for deck randomization.
	 * @param seed the seed to set
	 */
	public void setSeed(long seed) {
		this.seed = seed;
		//TODO Remove after testing
		seed = Long.parseLong("1381629156316");
	}
	
	/**
	 * Parses a string sent from the game server containing a list of commands. 
	 * 
	 * @param callCodes <code>String</code> containing a list of commands encoded 
	 * in the <code>CallCodes</code> class.
	 */
	public void parseCallCodes(String callCodes) {
		String[] tokens = callCodes.split(CallCodes.SEPARATOR);
		String command;
		Integer token = 0;
		while (token < tokens.length) {
			command = tokens[token++];
			if (command.equals(CallCodes.ATTACK)) {
				int attackingPlayer = Integer.parseInt(tokens[token++]);
				int attacker = Integer.parseInt(tokens[token++]);
				int victimPlayer = Integer.parseInt(tokens[token++]);
				int victimCard = Integer.parseInt(tokens[token++]);
				attack(attackingPlayer, attacker, victimPlayer, victimCard);
			} else if (command.equals(CallCodes.DRAW_CARD)) {
				int player = Integer.parseInt(tokens[token++]);
				drawToPlayer(player);
			} else if (command.equals(CallCodes.SET_SEED)) {
				setSeed(Long.parseLong(tokens[token++]));
			} else if (command.equals(CallCodes.USE)) {
				useItem(tokens[token++]);
			} else if (command.equals(CallCodes.PLAY_CARD)) {

				int player = Integer.parseInt(tokens[token++]);
				int cardID = Integer.parseInt(tokens[token++]);
				int index  = Integer.parseInt(tokens[token++]);
				Card card = players[player].getHand().getCard(cardID);
				players[player].getTable().setCard(index, card);
			}
			drawTable();
		}
	}

	private void useItem(String arg) {
		// TODO Auto-generated method stub
	}

	/**
	 * Draws a card from the deck and places it in the given players hand.
	 * @param player
	 * @param index
	 */
	private void drawToPlayer(int player) {
		Card card = deckObject.drawCard();
		players[player].addToHand(card);
	}

	private void attack(int attackingPlayerID, 
						int attackerCardID, 
						int victimPlayerID, 
						int victimCardID) {
		Card actor  = players[attackingPlayerID].getTable().getCard(attackerCardID);
		Card victim = players[victimPlayerID].getTable().getCard(victimCardID);
		handler.setup(actor, victim);
		handler.simulate(this);
		showResult();
	}

	public void addToQueue(String callCode) {
		networkQueue.add(callCode);
	}
	
	public void viewHealth(View v) {
		promptBuilder = new AlertDialog.Builder(this);
		
		promptBuilder.setTitle("Player Health");
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < numOfPlayers; i++) {
			sb.append(players[i].getName() + " " + players[i].getHealth() + "\n");
		}
		
		TextView tv = new TextView(this);
		tv.setText(sb.toString());
		promptBuilder.setView(tv);
		
		prompt = promptBuilder.create();
		prompt.show();
	}
}