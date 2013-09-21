package edu.wcu.cs.cs495.capstonecardgame.views;

import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import android.content.Context;
import android.graphics.Color;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class BattleView extends LinearLayout {

	private   LinearLayout         cards;
	protected LinearLayout         leftCard;
	protected LinearLayout         rightCard;
	private   ImageView            leftImage;
	private   ImageView            rightImage;
	private   ScrollView           scroller;
	private   HorizontalScrollView hscroller;
	private   TextView             leftName;
	private   TextView             rightName;
	protected TextView             leftDescription;
	protected TextView             rightDescription;
	private   Context              context;
	
	public BattleView(Context context) {
		super(context);
		this.setOrientation(VERTICAL);
		
		this.context = context;
		
		hscroller = new HorizontalScrollView(context);
		scroller  = new ScrollView(context);
		
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
		leftDescription.setTextColor(Color.BLACK);
		rightDescription.setTextColor(Color.BLACK);

		leftCard.addView(leftImage);
		rightCard.addView(rightImage);
		leftCard.addView(leftName);
		rightCard.addView(rightName);


		cards.addView(leftCard);
		cards.addView(rightCard);
		
		scroller.addView(cards);
		hscroller.addView(scroller);
		
		this.addView(hscroller);
		
		this.setBackgroundColor(Color.WHITE);
	}

	public void setAll(Card actor, Card target) {
		
		leftCard.addView(BattleCardViewBuilder.getView(context, actor));
		rightCard.addView(BattleCardViewBuilder.getView(context, target));
		
		leftCard.addView(leftDescription);
		rightCard.addView(rightDescription);
		
		leftImage.setImageResource(CardGame.getImageId(actor.getImageID(), actor.getName()));
		rightImage.setImageResource(CardGame.getImageId(target.getImageID(), actor.getName()));
		
		leftName.setText(actor.getName());
		rightName.setText(target.getName());
		
		leftDescription.setText(actor.getDescription());
		rightDescription.setText(target.getDescription());
	}
}
