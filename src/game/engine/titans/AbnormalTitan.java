package game.engine.titans;

import game.engine.base.Wall;
import game.engine.interfaces.Attackee;


public class AbnormalTitan extends Titan {
	public final static int TITAN_CODE =2;
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

	public AbnormalTitan(int baseHealth, int baseDamage, int heightInMeters, int distanceFromBase, int speed,
			int resourcesValue, int dangerLevel) {
		super(baseHealth, baseDamage, heightInMeters, distanceFromBase, speed, resourcesValue, dangerLevel);
		
	}
	
	public int attack(Attackee target) {
		//System.out.println("		AbnormalTitan Attacking "+target);
		int resources = super.attack(target);
    	if (target.isDefeated()) return resources;
    	resources = super.attack(target);
    	//System.out.println("		End of attack, resources = " + resources);
    	return resources;
    	
    }
    public static void main(String[] args) {
    	Wall w = new Wall(1500);
    	AbnormalTitan t= new AbnormalTitan(1000,250,7,0,1,10,2);
    	PureTitan t1= new PureTitan(1000,100,7,0,1,10,2);
    	ArmoredTitan t2= new ArmoredTitan(1000,150,7,0,1,10,2);
    	ColossalTitan t3= new ColossalTitan(1000,900,7,0,1,10,2);
    	
    	t.attack(w);
    	System.out.println(w);
    	t1.attack(w);
    	System.out.println(w);
    	t2.attack(w);
    	System.out.println(w);
    	t3.attack(w);
    	System.out.println(w);
    	t.attack(w);
    	System.out.println(w);
    	t1.attack(w);
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "	Abnormal" + super.toString();
	}
}
