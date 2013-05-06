package edu.wcu.cs.cs495.capstonecardgame.cardgamestructure.cards;

public class MonsterCard extends MonsterGameCard {

	private static final String  TAG           = "Monster Card";

	private int health;
	private int attack;
	private int defense;
	private double accuracy;
	private float regen_rate;
	private final int MAX_DEFENSE;
	private final int MAX_HEALTH;
	private final int MAX_ATTACK;
//	private final double MAX_ACCURACY;
	private boolean[] statuses;
	private boolean imm;
	private int[] statusTime;
	
	private int[] statusDamage;	
	
	public MonsterCard(int imageID, String name, String description, String type, int health, int attack, int defense, double accuracy, float regen_rate, String effect) {
		super(imageID, name, description);
		this.health       = health;
		this.MAX_HEALTH   = health;
		this.attack       = attack;
		this.MAX_ATTACK   = attack;
		this.regen_rate   = regen_rate;
		this.defense      = defense;
		this.MAX_DEFENSE  = defense;
		this.statuses     = new boolean[NUM_STATUS];
		this.statusTime   = new int[NUM_STATUS];
		this.statusDamage = new int[NUM_STATUS];
		this.imm          = false;
		this.accuracy     = accuracy;
//		this.MAX_ACCURACY = accuracy;
		
		parseEffect(effect);
	}

	public int effect1(MonsterGameCard target) {
		if (target instanceof MonsterCard) {
			return attack(null);
		} else {
			return target.getStat(HEALTH);
		}
	}

	@Override
	public int effect3(MonsterGameCard target) {
		return 0;
	}
	
	@Override
	public String[] parseEffect(String effect) {
		// "<EFFECT> <TURNS> <DAMAGE> <CHANCE> <RADIUS>"
		return super.parseEffect(effect);	
	}
	
	public int attack(MonsterCard card) {
		if (!imm)
		return card.block(this.attack);
		return ERROR;
	}
	
	public int block(int incomingAttack) {
		incomingAttack -= this.defense;
		if (incomingAttack > 0) {
			this.health -= incomingAttack;
		}

		return this.health;
	}
	
	public int getStat(int statCode) {
		switch (statCode) {
		case ATTACK:
			return attack;
		case DEFENSE:
			return defense;
		case HEALTH:
			return health;
		default:
			return ERROR;
		}
	}
	
	public int changeStat(int statCode, int amount) {
		switch (statCode) {
		case ATTACK:
			attack += amount;
			return attack;
		case DEFENSE:
			defense += amount;
			return defense;
		case HEALTH:
			health += amount;
			return health;
		default:
			return ERROR;
		}
	}
	
	public void resetStats(int[] statCodes) {
		for (int i = 0; i < statCodes.length; i++) {
			switch (statCodes[i]) {
			case ATTACK:
				attack = MAX_ATTACK;
				break;
			case DEFENSE:
				defense = MAX_DEFENSE;
				break;
			case HEALTH:
				health = MAX_HEALTH;
				break;
			default:
				System.out.println(TAG + "" + ERROR);
			}
		}
	}
	
	public int regenerate(float rate) {
		defense += (int)(defense * rate);
		return defense;
	}
	

	public void affect(int status, int turns, int damage, boolean toggle) {
		statuses[status]     = toggle;
		statusTime[status]   = turns;
		statusDamage[status] = damage;
	}
	
	public void regen() {
		changeStat(DEFENSE, (int)(MAX_DEFENSE * regen_rate));
	}
	
	public void checkStatus() {
		if (statuses[BURN] || statuses[POISON]) {
			this.health -= getDamage();
		}

		for (int i = 0; i < NUM_STATUS; i++) {
			statusTime[i]--;
			if (statusTime[i] == 0) {
				statuses[i] = OFF;
			}
		}

		if (statuses[FREEZE] || statuses[SLEEP] || statuses[PARALYSIS]) {
			imm = true;
		}
		if (!statuses[FREEZE] && !statuses[SLEEP] && !statuses[PARALYSIS]) {
			imm = false;
		}
	}

	public int getHealth() {
		return health;
	}
	
	public int getAttack() {
		return attack;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public double getAccuracy() {
		return accuracy;
	}
	
	public boolean[] getStatuses() {
		return statuses;
	}

	public int[] getStatusTimes() {
		return statusTime;
	}
	
	public boolean isImmobilized() {
		return imm;
	}

	@Override
	public void setName(String string) {
		// TODO Auto-generated method stub
		
	}

}