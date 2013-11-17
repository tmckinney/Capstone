package edu.wcu.cs.cs495.capstonecardgame.activities.activityhelpers;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.Deck;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.ItemCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterCard;
import edu.wcu.cs.cs495.capstonecardgame.database.DataBaseHelper;
import edu.wcu.cs.cs495.capstonecardgame.database.DatabaseInterface;

public class DeckBuilder {
	public static Deck readDeck(Context context) {
		   
        SQLiteDatabase db = null;
        DataBaseHelper myDbHelper = new DataBaseHelper(context);
        Deck deck;
        
        try {
        	
	        myDbHelper.openDataBase();
	        db = myDbHelper.getMyDataBase();
	        
        } catch(SQLException sqle){

        	Log.e("DBH", "Caught SQL exception");

        }
        
        Cursor cur = db.query(DatabaseInterface.ITEM_TRAP_TABLE, null, null, null, null, null, null);
		cur.moveToFirst();
		int totalCards = cur.getCount();
		cur = db.query(DatabaseInterface.MONSTER_TABLE, null, null, null, null, null, null);
		cur.moveToFirst();
		totalCards += cur.getCount();
		
		deck = new Deck(totalCards, false);
		
		while (!cur.isAfterLast()) {
			
			int defense = cur.getInt(cur.getColumnIndex(DatabaseInterface.DEFENSE_POINTS));
			String effect = cur.getString(cur.getColumnIndex(DatabaseInterface.EFFECT));			
			int id= cur.getInt(cur.getColumnIndex(DatabaseInterface.M_CARD_ID));
			String name = cur.getString(cur.getColumnIndex(DatabaseInterface.M_NAME));
			String description = cur.getString(cur.getColumnIndex(DatabaseInterface.M_DISC));
			String type = cur.getString(cur.getColumnIndex(DatabaseInterface.TYPE));
			int health = cur.getInt(cur.getColumnIndex(DatabaseInterface.HP));
			int attack = cur.getInt(cur.getColumnIndex(DatabaseInterface.ATTACK_POINTS));
			int accuracy = 100; //cur.getInt(cur.getColumnIndex(DatabaseInterface.ACCURACY));
			float regen_rate = cur.getFloat(cur.getColumnIndex(DatabaseInterface.REGEN_RATE));

			
			MonsterCard card = new MonsterCard(id, name, description, type, health, attack, defense, accuracy, regen_rate, effect);
			deck.addCard(card);
			
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
			deck.addCard(card);
			
			cur.moveToNext();
		}
		
		myDbHelper.close();
		return deck;
	}
}
