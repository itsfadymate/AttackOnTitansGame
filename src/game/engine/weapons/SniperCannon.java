package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class SniperCannon extends Weapon {
	public final static int WEAPON_CODE = 2;

	public SniperCannon(int baseDamage) {
		super(baseDamage);
	}
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		if (laneTitans.isEmpty()) return 0;
	    Titan titan = laneTitans.remove();
	    int resources = this.attack(titan);
	    if (titan.isDefeated()) return resources;
	    laneTitans.add(titan);
	    return 0;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "	SniperCannon, Damage: " + getBaseDamage();
	}

}
