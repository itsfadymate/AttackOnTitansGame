package game.engine.titans;

public class PureTitan extends Titan {
	
	
	
	public final static int TITAN_CODE =1;
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
	public PureTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
	}
	
	public String toString() {
		return "	Pure"+ super.toString();
	}
	
}
