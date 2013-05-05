package edu.wcu.cs.cs495.capstonecardgame.views;

import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
//import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.ItemCard;
import android.content.Context;

public class PopUpItemCardView extends PopUpCardView {

	public PopUpItemCardView(Context context) {
		super(context);

		info.addView(descriptionHeader);
		info.addView(description);
	}

	@ Override
	public void setAll(Card card) {
		super.setAll(card);
	}

}
