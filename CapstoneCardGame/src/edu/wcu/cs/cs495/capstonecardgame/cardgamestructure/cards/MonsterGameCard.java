package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards;

import java.util.Locale;
import java.util.Random;

public abstract class MonsterGameCard implements Card {
	
	protected static final int ATTACK   = 0;
	protected static final int DEFENSE  = 1;
	public static final int HEALTH   = 2;
	protected static final int MANA     = 3;
	protected static final int ERROR    = -999999999;
	
	protected static final int     NUM_STATUS    = 5;
	
	protected static final int     FREEZE        = 0;
	protected static final int     SLEEP         = 1;
	protected static final int     BURN          = 2;
	protected static final int     PARALYSIS     = 3;
	protected static final int     POISON        = 4;
	protected static final int     ADD           = 5;
	protected static final int     SUB           = 6;
	protected static final int     DIV           = 7;

	protected static final boolean ON = true;
	protected static final boolean OFF = false;
	
	protected static final int EFFECT = 0;
	protected static final int TURNS  = 1;
	protected static final int DAMAGE = 2;
	protected static final int CHANCE = 3;
	protected static final int RADIUS = 4;

	protected static final String FREEZE_STRING    = "freeze";
	protected static final String BURN_STRING      = "burn";
	protected static final String SLEEP_STRING     = "sleep";
	protected static final String POISON_STRING    = "poison";
	protected static final String PARALYSIS_STRING = "paralysis";
	protected static final String ADD_STRING       = "add";
	protected static final String SUB_STRING       = "sub";
	protected static final String DIV_STRING       = "div";
    
	protected static final int FIRST_NON_GENERIC_EFFECT = 5;
	
    /** Integer ID mapping to an Android resource ID. */
	private int imageID;

    /** The name of the card. */
	private String name;
	
	/** The card's description. */
	private String description;
	
	private Random random;
	
	protected int effect;
	protected int turns;
	protected int damage;
	protected int chance;
	protected int radius;

	public MonsterGameCard(int imageID, String name, String description) {
		this.imageID = imageID;
		this.name = name;
		this.description = description;
		
		this.random = new Random();
	}
	
	public String[] parseEffect(String string) {
		// "<EFFECT> <TURNS> <DAMAGE> <CHANCE> <RADIUS>"
		String[] pieces = string.split(" ");
		String effect   = pieces[EFFECT];
		
		turns           = ERROR;
		chance          = ERROR;
		radius          = ERROR;
		damage          = ERROR;
		
		if (effect.toLowerCase(Locale.getDefault()).equals(FREEZE_STRING)) {
			this.setEffect(FREEZE);
		} else if (effect.toLowerCase(Locale.getDefault()).equals(BURN_STRING)) {
			this.setEffect(BURN);
		} else if (effect.toLowerCase(Locale.getDefault()).equals(SLEEP_STRING)) {
			this.setEffect(SLEEP);
		} else if (effect.toLowerCase(Locale.getDefault()).equals(PARALYSIS_STRING)) {
			this.setEffect(PARALYSIS);
		} else if (effect.toLowerCase(Locale.getDefault()).equals(POISON_STRING)) {
			this.setEffect(POISON);
		} else if (effect.toLowerCase(Locale.getDefault()).equals(ADD_STRING)) {
			this.setEffect(ADD);
		} else if (effect.toLowerCase(Locale.getDefault()).equals(SUB_STRING)) {
			this.setEffect(SUB);
		} else if (effect.toLowerCase(Locale.getDefault()).equals(DIV_STRING)) {
			this.setEffect(DIV);
		} else {
			System.err.println("Invalid effect String: Invalid effect code");
		}
		
		try {
			turns  = Integer.parseInt(pieces[TURNS]);
			damage = Integer.parseInt(pieces[DAMAGE]);
			chance = Integer.parseInt(pieces[CHANCE]);
			radius = Integer.parseInt(pieces[RADIUS]);
		} catch (NumberFormatException e) {
			System.err.println("Invalid effect String: Invalid integer value");
		}
		
		return pieces;
	}

	
	public String generateEffectString() {
		StringBuilder sb = new StringBuilder();

	    if (effect == MonsterCard.FREEZE) {
	    	sb.append("Has a ").append(chance).append("% chance of freezing the target for ");
	    	sb.append(turns).append(" turns.");
	    } else if (effect == MonsterCard.SLEEP) {
	    	sb.append("Has a ").append(chance).append("% chance of puting the target to sleep for ");
	    	sb.append(turns).append(" turns.");
		} else if (effect == MonsterCard.BURN) {
			sb.append("Has a ").append(chance).append("% chance of burning the target for ");
			sb.append(turns).append(" turns doing ").append(damage).append(" damage per turn.");
		} else if (effect == MonsterCard.PARALYSIS) {
			sb.append("Has a ").append(chance).append("% chance of poisoning the target for ");
			sb.append(turns).append(" turns doing ").append(damage).append(" damage per turn.");
		} else if (effect == MonsterCard.SLEEP) {
	    	sb.append("Has a ").append(chance).append("% chance of paralysing the target for ");
	    	sb.append(turns).append(" turns.");
		} else if (effect == MonsterCard.ADD) {
			sb.append("Has a ").append(chance).append("% chance of immeadiatly healing the user by ");
			sb.append(damage).append(" health points.");
		} else if (effect == MonsterCard.SUB) {
			sb.append("Has a ").append(chance).append("% chance of immeadiatly dealing");
			sb.append(damage).append(" damage to the target.");
		} else if (effect == MonsterCard.DIV) {
			sb.append("Has a ").append(chance).append("% chance of immeadiatly cutting the target's health by");
			sb.append((100 / damage)).append("%.");
		}
	    
	    if (radius > 1)
	    	sb.append(" Also effects ").append(radius - 1).append(" more of the target players cards.");
	    
	    return sb.toString();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getImageID() {
		return imageID;
	}

	@Override
	public String getDescription() {
		return description;
	}
	

	public int getEffect() {
		return effect;
	}

	public void setEffect(int effect) {
		this.effect = effect;
	}

	public int getTurns() {
		return turns;
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getChance() {
		return chance;
	}

	public void setChance(int chance) {
		this.chance = chance;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}
	

	
	public abstract int getStat(int statCode);
	
	public abstract int effect1(MonsterGameCard target);
	
	public int effect(MonsterGameCard target) {
		
		boolean hit = false;
		int hits = random.nextInt(100);
		
		if (hits <= chance) {
			hit = true;
		}
		
		if (hit)
				((MonsterCard) target).affect(effect, turns + 1, damage, ON);
		
		return hits;

	}
	
	public abstract int effect3(MonsterGameCard target);

}
