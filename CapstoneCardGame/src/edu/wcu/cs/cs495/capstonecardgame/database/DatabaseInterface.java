package edu.wcu.cs.cs495.capstonecardgame.database;

/**
 * 
 * This interface is used to store the labels for the database columns.
 * No method headers are within this file, only named constants.
 * @author Michael Penland
 * @author Tyler Mckinney
 */
public interface DatabaseInterface {
	
	/* Name for the database. */
	static final String DB_NAME        = "cardDB";
	
	/* _id for unique id's */
	static final String ID             = "_id";
	
	/* Monster Card Table within database. */
	static final String MONSTER_TABLE  = "Monsters";
	
	/* Id for monster card.  String. */
	static final String M_CARD_ID       = "MonsterID";
	
	/* Name of Monster. String. */
	static final String M_NAME          = "MonsterName";
	
	/* Discription of the monster. */
	static final String M_DISC          = "MonsterDiscription";
	
	/* Number of hit_points monster begins with. Integer. */
	static final String HP              = "MonsterHP";
	
	/* The type of monster. String. */
	static final String TYPE            = "MonsterType";
	
	/* The Attack Points for the monster. */
	static final String ATTACK_POINTS = "MonsterAttack";
	
	/* The Defense Points for monster.  Regenerate.  Integer. */
	static final String DEFENSE_POINTS  = "MonsterDP";
	
	/* The Rate that defense is regenerated for a monster.  Double. */
	static final String REGEN_RATE      = "MonsterDefenseRegenRate";
	
	/* The number of reward points received for defeating monster. */
	static final String REWARD_POINTS   = "MonsterReward";
	
	/* Item and trap table within database. */
	static final String ITEM_TRAP_TABLE = "ItemTrap";
	
	/* The Id for Item and trap cards. */
	static final String I_CARD_ID       = "itemID";
	
	/* The name of the Item or Trap. */
	static final String I_NAME          = "itemTrapName";
	
	/* The description of the Item or Trap. */
	static final String I_DISCRIPTION   = "itemTrapDisc";
	
	/* If an Item or Trap affects a type. */
	static final String I_AFFECTS       = "itemAffects";
	
	/* The power of an Item.  Integer.  Pos or Neg. */
	static final String I_POWER         = "itemTrapPower";
	
	/* States if the item is equipment or disposable. Boolean.*/
	static final String ONE_TIME_USE    = "itemTrapOneTime";
	
	/* The image that is to be used for the car. */
	static final String I_IMAGE         = "itemTrapImage";

	static final String EFFECT          = "effect";

}
