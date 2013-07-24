package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards;

import android.content.Context;

public class ItemCard extends MonsterGameCard {
	
	private static final int NOW            = 0;
	private static final int AT_NEXT_ATTACK = 1;
	private static final int AFTER_T_TURNS  = 2;
	private static final String BEFORE_STRING = "before";

//	private static boolean BEFORE = true;
//	private static boolean AFTER  = false;
	
//	private String power;
	
	private int time;
    private int numTurns;
    private boolean before;
	private boolean oneTimeUse;
	private boolean used;
	

	public ItemCard(int imageID, String name, String description, String power, Boolean oneTimeUse) {
		super(imageID, name, description);
//		this.power = power;
		this.oneTimeUse = oneTimeUse;
		this.used = false;
		
		parseEffect(power);
	}
	
	

	@Override
	public int effect(MonsterGameCard target) {
				used = true;
		return 0;
	}

	@Override
	public int effect1(MonsterGameCard target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int effect3(MonsterGameCard target) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getStat(int statCode) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String[] parseEffect(String effect) {
		// "<EFFECT> <TURNS> <DAMAGE> <CHANCE> <RADIUS> | <TIME> <DELAY> <BEOFRE>
		String[] pieces = super.parseEffect(effect);
		
		try {
			time = Integer.parseInt(pieces[FIRST_NON_GENERIC_EFFECT]);
			numTurns = Integer.parseInt(pieces[FIRST_NON_GENERIC_EFFECT + 1]);
		} catch (NumberFormatException ex) {
			System.err.println("Invalid effect String: Invalid integer value");
		}
		
		before = pieces[FIRST_NON_GENERIC_EFFECT + 2].equals(BEFORE_STRING);
		
		return pieces;	
	}
	
	@Override
	public String generateEffectString() {
		String effectString = super.generateEffectString();
		
		if (time == NOW) {
			effectString += " Activates immeadiatly.";
		} else if (time == AT_NEXT_ATTACK) {
			effectString += " Trap activates " + (before ? "before" : "after") + " the target's next attack.";
		} else if (time == AFTER_T_TURNS) {
			effectString += " Item activates after " + numTurns + " turns.";
		}
		
		if (oneTimeUse) {
			effectString += " Discards after use."; 
		}
		
		if (used) {
			effectString += " This card has already been used" + (oneTimeUse ?  "and cannot be used again." : ".");
		}
		
		return effectString;

	}
	
	public void use() {
		used = true;
	}
	
	public boolean usedUp() {
		return (oneTimeUse && used);
	}



	@Override
	public void setName(String string) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public boolean canBeUsed() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean toast(Context context) {
		// TODO Auto-generated method stub
		return false;
	}
}
