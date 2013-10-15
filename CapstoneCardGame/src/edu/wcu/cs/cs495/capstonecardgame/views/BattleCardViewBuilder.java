package edu.wcu.cs.cs495.capstonecardgame.views;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterCard;

public class BattleCardViewBuilder {

	public static LinearLayout getView(Context context, Card card) {
		LinearLayout view = null;
		if (card instanceof MonsterCard) {
			view = buildMonsterCard(view, context, (MonsterCard) card);
		}
		return view;
	}

	private static LinearLayout buildMonsterCard(LinearLayout view, Context context, MonsterCard card) {
		view = new LinearLayout(context);
		LinearLayout row1   = new LinearLayout(context);
		LinearLayout row2  = new LinearLayout(context);
		
		view.setOrientation(LinearLayout.VERTICAL);
		row1.setOrientation(LinearLayout.HORIZONTAL);
		row2.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView slot1_1 = new TextView(context);
		TextView slot1_2 = new TextView(context);
		TextView slot2_1 = new TextView(context);
		TextView slot2_2 = new TextView(context);
		
		slot1_1.setPadding(CardGame.PADDING, 0, CardGame.PADDING, 0);
		slot1_2.setPadding(CardGame.PADDING, 0, CardGame.PADDING, 0);
		slot2_1.setPadding(CardGame.PADDING, 0, CardGame.PADDING, 0);
		slot2_2.setPadding(CardGame.PADDING, 0, CardGame.PADDING, 0);
		
		slot1_1.setText(String.format("H : %5d", card.getHealth()));
		slot1_2.setText(String.format("M : %5d", card.getMana()));
		row1.addView(slot1_1);
		row1.addView(slot1_2);
		view.addView(row1);
		
		
		slot2_1.setText(String.format("A : %5d", card.getAttack()));
		slot2_2.setText(String.format("D : %5d", card.getDefense()));
		row2.addView(slot2_1);
		row2.addView(slot2_2);
		view.addView(row2);
		
		return view;
	}
}
