package edu.wcu.cs.cs495.capstonecardgame.activities;

import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.Card;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.MonsterCard;
import edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards.NullCard;

public class ActionHandler {
	
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
	
	public void simulate() {
		if (actor instanceof MonsterCard) {
			if (victim instanceof MonsterCard) {
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
		result = attacker.getName() + " attacked " + victim + ".";
		if (victim.getHealth() <= 0) {
			result += victim.getName() + " has died.";
		} else {
			result += "Victim has " + health + " remaining.";
		}
	}
	
	public String retriveResult() {
		return result;
	}
	
}
