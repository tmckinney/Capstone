package edu.wcu.cs.cs495.capstonecardgame.views;

import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/** 
 * PopUpCardView.java
 * Class modeling a pop up window to display a <code>Card</code>'s data.
 * 
 * @author Tyler McKinney
 * @version 2013.2.10.1
 */
public class PopUpCardView extends LinearLayout {
	
	/** The description header. */
	private static final String DES_HEADER = "Description:";

	/** <code>ImageView</code> displaying the <code>Card</code>'s image. */
	private ImageView imageView;
	
	/** <code>TextView</code> displaying the <code>Card</code>'s name. */
	private TextView nameView;
	
	/** <code>TextView</code> displaying the description header. */
	protected TextView descriptionHeader;
	
	/** <code>TextView</code> displaying the <code>Card</code>'s description. */
	protected TextView description;
	
	/** Structure holding all of the cards info. */
	protected LinearLayout info;
	
	protected ScrollView scroller;

	/** 
	 * Simple constructor to use when creating a view from code.
	 * 
	 * @param context The Context the view is running in, through which it can access the 
	 * 				  current theme, resources, etc.
	 */
	public PopUpCardView(Context context) {
		super(context);
		this.setOrientation(HORIZONTAL);
		
		scroller = new ScrollView(context);

		info = new LinearLayout(context);
		info.setOrientation(VERTICAL);
		
		imageView = new ImageView(context);
		nameView  = new TextView(context);

		imageView.setPadding(CardGame.PADDING, CardGame.PADDING, 
				CardGame.PADDING, CardGame.PADDING);
		
		nameView.setTextColor(Color.BLACK);
		
		descriptionHeader = new TextView(context);
		description       = new TextView(context);
		
		
		descriptionHeader.setText(DES_HEADER);
		
		descriptionHeader.setTextColor(Color.BLACK);
		description.setTextColor(Color.BLACK);

		
		descriptionHeader.setText(DES_HEADER);
		
		descriptionHeader.setTextColor(Color.BLACK);
		description.setTextColor(Color.BLACK);

		
		this.addView(imageView);
		this.addView(scroller);
		scroller.addView(info);
		info.addView(nameView);

		
		this.setBackgroundColor(Color.WHITE);
	}
	
	public void setAll(Card card) {
		imageView.setImageResource(CardGame.getImageId(card.getImageID(), card.getName()));
		nameView.setText(card.getName());
	}
	

}
