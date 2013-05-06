package edu.wcu.cs.cs495.capstonecardgame.views;

import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterGameCard;
import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class PopUpMonsterCardView extends PopUpCardView {
	
	private static final String HLT_HEADER = "Heath Remaining: ";
	
	private static final String EFF_HEADER = "Effect:";
	
	/** <code>TextView</code> displaying the <code>Card</code>'s health. */
	private TextView health;
	
	/** <code>TextView</code> displaying the <code>Card</code>'s mana. */
	private TextView mana;
	
	/** <code>TextView</code> displaying the <code>Card</code>'s effect. */
	private TextView effectHeader;
	
	/** <code>TextView</code> displaying the <code>Card</code>'s effect. */
	private TextView effect;
	
	
	public PopUpMonsterCardView(Context context) {
		super(context);
		health = new TextView(context);
		mana   = new TextView(context);
		effect = new TextView(context);
		
		effectHeader = new TextView(context);
		
		health.setTextColor(Color.BLACK);
		mana.setTextColor(Color.BLACK);
		effect.setTextColor(Color.BLACK);
		effectHeader.setTextColor(Color.BLACK);
		
		effectHeader.setText(EFF_HEADER);
		
		info.addView(health);
		info.addView(mana);
		info.addView(effectHeader);
		info.addView(effect);
		info.addView(descriptionHeader);
		info.addView(description);
		
	}
	
	public void setAll(Card card) {
		super.setAll(card);
		description.setText(card.getDescription());
		health.setText(HLT_HEADER + ((MonsterCard) card).getStat(MonsterGameCard.HEALTH));
		setEffect(card);
	}

	private void setEffect(Card card) {
	    effect.setText(((MonsterCard) card).generateEffectString());
	}

}
