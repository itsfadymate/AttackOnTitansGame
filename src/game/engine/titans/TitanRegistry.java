package game.engine.titans;



public class TitanRegistry {
	//all are int and READ ONLY

	private final int code;
	private int baseHealth;
	private int baseDamage;
	private int heightInMeters;
	private int speed;
	private int resourcesValue;
	private int dangerLevel;

	public TitanRegistry(int code,int baseHealth,int baseDamage, int heightInMeters, int speed,int resourcesValue,int dangerLevel) {
		this.code = code;
		this.baseHealth = baseHealth;
		this.baseDamage = baseDamage;
		this.heightInMeters = heightInMeters;
		this.speed = speed;
		this.resourcesValue = resourcesValue;
		this.dangerLevel = dangerLevel;
	}

	public int getCode() {return this.code;}
	public int getBaseHealth() {return this.baseHealth;}
	public int getBaseDamage() {return this.baseDamage;}
	public int getHeightInMeters() {return this.heightInMeters;}
	public int getSpeed() {return this.speed;}
	public int getResourcesValue() {return this.resourcesValue;}
	public int getDangerLevel() {return this.dangerLevel;}
	@Override
	public String toString(){
		return "code"+ code + "  BaseHealth" +baseHealth + "  baseDamage"+baseDamage +"  heightInMeters"+ heightInMeters
				+ "   speed"+ speed +"  resourcesValue"+ resourcesValue + "  dangerLevel" +dangerLevel;
	}

	public Titan spawnTitan(int distanceFromBase) {
		if (this.code==AbnormalTitan.TITAN_CODE) {
			return new AbnormalTitan(baseHealth,baseDamage,heightInMeters,distanceFromBase,speed,resourcesValue,dangerLevel);
		}else if (this.code==ArmoredTitan.TITAN_CODE) {
			return new ArmoredTitan(baseHealth,baseDamage,heightInMeters,distanceFromBase,speed,resourcesValue,dangerLevel);
		}else if (this.code==ColossalTitan.TITAN_CODE) {
			return new ColossalTitan(baseHealth,baseDamage,heightInMeters,distanceFromBase,speed,resourcesValue,dangerLevel);
		}else if (this.code==PureTitan.TITAN_CODE) {
			return new PureTitan(baseHealth,baseDamage,heightInMeters,distanceFromBase,speed,resourcesValue,dangerLevel);
		}
		return null;
	}

}
