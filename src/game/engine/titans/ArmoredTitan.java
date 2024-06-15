package game.engine.titans;

public class ArmoredTitan extends Titan {

	public final static int TITAN_CODE =3;

	/**
	 *
	 * @param baseHealth
	 * @param baseDamage
	 * @param heightInMeters
	 * @param distanceFromBase
	 * @param speed
	 * @param resourcesValue
	 * @param dangerLevel
	 */

	public ArmoredTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);

	}
	@Override
	public int takeDamage(int Damage) {
		int currentHealth = this.getCurrentHealth();
		int DamageTaken = (int)(Damage *0.25);
		this.setCurrentHealth(currentHealth - DamageTaken);
		if (isDefeated()) {
			return getResourcesValue();
		} else {
			return 0;
		}

	}
	public int getCode() {
    	return TITAN_CODE;
    }
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "	Armored" + super.toString();
	}

}
