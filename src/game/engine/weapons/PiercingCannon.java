package game.engine.weapons;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class PiercingCannon extends Weapon {
	//need to know public or private or none
	public final static int WEAPON_CODE = 1;

	public PiercingCannon(int baseDamage) {
		super(baseDamage);

	}
	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		int resourcesGathered =0;
		ArrayList<Titan> attackedTitans = new ArrayList<>(5);
		int ub = laneTitans.size() >=5? 5 : laneTitans.size();
		//attack first 5 titans
		for (int i=1;i<=ub;i++) {

			Titan titan = laneTitans.remove();
			resourcesGathered += attack(titan);
			if (titan.isDefeated()) {
				continue;
			}
			attackedTitans.add(titan);
		}

		//return those who are still alive to original pq
		for (Titan titan : attackedTitans) {
			laneTitans.add(titan);
		}
		return resourcesGathered;

	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "	PiercingCannon, Damage: " + getBaseDamage();
	}

}
