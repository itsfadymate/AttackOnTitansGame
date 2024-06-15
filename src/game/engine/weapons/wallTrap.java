package game.engine.weapons;

import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class WallTrap extends Weapon {
	public final static int WEAPON_CODE = 4;

	public WallTrap(int baseDamage) {
		super(baseDamage);
	}

	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		// TODO Auto-generated method stub
		if (laneTitans.isEmpty()) {
			return 0;
		}
		Titan titan = laneTitans.remove();
		int resourcesGathered =0;
		if (titan.hasReachedTarget()) {
			resourcesGathered= attack(titan);
		}
		if (titan.isDefeated()) {
			return resourcesGathered;
		}
		laneTitans.add(titan);
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "	WallTrap, Damage: " + getBaseDamage();
	}

}
