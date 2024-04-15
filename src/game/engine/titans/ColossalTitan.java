package game.engine.titans;

public class ColossalTitan extends Titan {

	public final static int TITAN_CODE =4;
	
	public ColossalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
		
	}
	public boolean move() {
		boolean ret = super.move();
		this.setSpeed(this.getSpeed() + 1);
		return ret;
	}

}
