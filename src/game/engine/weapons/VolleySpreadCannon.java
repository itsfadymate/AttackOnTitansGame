package game.engine.weapons;

import java.util.ArrayList;
import java.util.PriorityQueue;

import game.engine.titans.Titan;

public class VolleySpreadCannon extends Weapon {
	private final int minRange;
	private final int maxRange;
	public final static int WEAPON_CODE = 3;

	public VolleySpreadCannon(int baseDamage,int minRange,int maxRange) {
		super(baseDamage);
		this.minRange = minRange;
		this.maxRange = maxRange;

	}
	@Override
	public int turnAttack(PriorityQueue<Titan> laneTitans) {
		ArrayList<Titan> attackedTitans = new ArrayList<>();
		int resourcesGathered =0;
		while (!laneTitans.isEmpty()) {
			Titan titan = laneTitans.remove();
			if (titan.getDistance() >= getMinRange() && titan.getDistance() <= getMaxRange()) {
				resourcesGathered += this.attack(titan);
			}
			if (titan.isDefeated()) {
				continue;
			}
			attackedTitans.add(titan);
		}
		for (Titan titan : attackedTitans) {
			laneTitans.add(titan);
		}
		return resourcesGathered;
	}

	public int getMinRange() {return this.minRange;}
	public int getMaxRange() {return this.maxRange;}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "	VolleySpreadCannon, Damage: " + getBaseDamage();
	}

}
