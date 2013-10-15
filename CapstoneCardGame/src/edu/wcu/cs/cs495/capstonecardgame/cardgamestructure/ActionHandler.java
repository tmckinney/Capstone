package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure;

import android.util.Log;
import edu.wcu.cs.cs495.capstonecardgame.activities.CardGame;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterCard;
import edu.wcu.cs.cs495.capstonecardgame.server.CallCodes;

public class ActionHandler {
	
	private static final String TAG = null;
	private static ActionHandler instance;
	private Card actor;
	private Card victim;
	private String result;
	
    /** 
     * Method to retrieve the Singleton. If it has not been created, i.e.
     * instance is null, creates it. If it has been created, returns instance. 
     *
     * @return The instance of this Singleton.
     */
	public static ActionHandler getInstance() {
		if (instance == null) {
			instance = new ActionHandler();
		}
		return instance;
	}
	
	public void setup(Card actor, Card victim) {
		this.actor = actor;
		this.victim = victim;
		result = "";

	}
	
	public void simulate(CardGame game) {
		if (actor instanceof MonsterCard) {
			if (victim instanceof MonsterCard) {
				Log.d(TAG, actor.getName()  + "- A = " +  ((MonsterCard) actor).getAttack() + " D = " 
						                    + ((MonsterCard) actor).getDefense() 
						                    + " : " + victim.getName() + " A = " 
						                    + ((MonsterCard) victim).getAttack() + " D = " 
						                    + ((MonsterCard) victim).getDefense());
				game.addToQueue(CallCodes.ATTACK 
								+ CallCodes.SEPARATOR + actor.getOwner() 
							    + CallCodes.SEPARATOR + actor.getImageID()
							    + CallCodes.SEPARATOR + victim.getOwner()
							    + CallCodes.SEPARATOR + victim.getImageID()
							    + CallCodes.SEPARATOR);
				simualateAttack((MonsterCard) actor, (MonsterCard) victim);
			} else {
				
			}
		} else {
			if (victim instanceof MonsterCard) {
				
			} else {
				
			}
		}	
	}
	
	public void simualateAttack(MonsterCard attacker, MonsterCard victim) {
		int health = attacker.attack(victim);
		result = attacker.getName() + " attacked " + victim.getName() + ". ";
		if (victim.getHealth() <= 0) {
			result += victim.getName() + " has died.";
			victim.kill();
		} else {
			result += victim.getName() + " has " + health + " health remaining.";
		}
	}
	
	public String retriveResult() {
		return result;
	}
	
}
