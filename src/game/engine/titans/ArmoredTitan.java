package game.engine.titans;

public class ArmoredTitan extends Titan {

	public final static int TITAN_CODE =3;
	
	public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
		
	}
	public int takeDamage(int Damage) {
		int currentHealth = this.getCurrentHealth();
		int DamageTaken = (int)(Damage *0.25);
		this.setCurrentHealth(currentHealth - DamageTaken);
		if (isDefeated())
		     return getResourcesValue();
		else return 0;
		
	}

}
