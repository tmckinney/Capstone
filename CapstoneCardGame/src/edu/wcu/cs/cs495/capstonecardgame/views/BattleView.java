package edu.wcu.cs.cs495.capstonecardgame.views;

import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class BattleView extends LinearLayout {

	private TextView     header;
	private LinearLayout cards;
	private LinearLayout leftCard;
	private LinearLayout rightCard;
	private ImageView    leftImage;
	private ImageView    rightImage;
	private ScrollView   scroller;
	private TextView leftName;
	private TextView rightName;
	private TextView leftDescription;
	private TextView rightDescription;
	
	public BattleView(Context context) {
		super(context);
		this.setOrientation(VERTICAL);
		
		header = new TextView(context);
		header.setText("Use this attack or item?");
		header.setTextColor(Color.BLACK);
		
		scroller = new ScrollView(context);
		
		cards = new LinearLayout(context);
		leftCard = new LinearLayout(context);
		rightCard = new LinearLayout(context);
		
		cards.setOrientation(HORIZONTAL);
		leftCard.setOrientation(VERTICAL);
		rightCard.setOrientation(VERTICAL);
		
		leftImage  = new ImageView(context);
		rightImage = new ImageView(context);
		
		leftName = new TextView(context);
		leftName.setTextColor(Color.BLACK);
		rightName = new TextView(context);
		rightName.setTextColor(Color.BLACK);
		
		leftDescription = new TextView(context);
		rightDescription = new TextView(context);
		
		leftCard.addView(leftImage);
		rightCard.addView(rightImage);
		leftCard.addView(leftName);
		rightCard.addView(rightName);
		leftCard.addView(leftDescription);
		rightCard.addView(rightDescription);
		
		cards.addView(leftCard);
		cards.addView(rightCard);
		
		scroller.addView(cards);
		
		this.addView(header);
		this.addView(scroller);
	}

	public void setAll(Card actor, Card target) {
		leftImage.setImageResource(CardGame.getImageId(actor.getImageID()));
		rightImage.setImageResource(CardGame.getImageId(target.getImageID()));
		
		leftName.setText(actor.getName());
		rightName.setText(target.getName());
		
		leftDescription.setText(actor.getDescription());
		rightDescription.setText(target.getDescription());
	}
}
