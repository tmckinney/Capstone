package edu.wcu.cs.cs495.capstonecardgame.views;

import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
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
	private   TextView             leftName;
	private   TextView             rightName;
	protected TextView             leftDescription;
	protected TextView             rightDescription;
	private   Context              context;
	private   boolean              finished;
	private   boolean              drawn;
	private   boolean              done;
	private   int                  lastSample;
	private   int                  sameLastSample;
	
	
	public BattleView(Context context) {
		super(context);
		this.setOrientation(VERTICAL);
		
		this.context = context;
		this.finished = false;
		this.drawn = false;
		this.done = false;
		
		scroller  = new ScrollView(context);
		
		cards = new LinearLayout(context);
		leftCard = new LinearLayout(context);
		rightCard = new LinearLayout(context);

		ViewTreeObserver tableObserver = cards.getViewTreeObserver();

		tableObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			private final int MAX_LAST_SAMPLE = 10;
			
			@Override
			public void onGlobalLayout() {
				Log.d("BCV", "Layout changed");
				if (!drawn && !done && !finished) {
					int thisSample = updateWidths();
					if (thisSample == lastSample) {
						sameLastSample++;
					} else {
						sameLastSample = 0;
					}
					lastSample = thisSample;
					Log.d("BCV", "Updating widths - " + sameLastSample);
					if (sameLastSample == MAX_LAST_SAMPLE) {
						done = true;
					}
				} else if (!finished) {
					drawn = true;
				}
			}
		});
		
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
	    
		this.addView(scroller);
		
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
	
	public int updateWidths() {
		leftDescription.setWidth(cards.getWidth() / 2);
		rightDescription.setWidth(cards.getWidth() / 2);
		Log.d("BCV", "cardswidth = " + cards.getWidth() + " : leftwidth  = " + leftDescription.getWidth() + " : rightwidth = " + rightDescription.getWidth());
		return cards.getWidth() + leftDescription.getWidth() + rightDescription.getWidth();
	}
}
