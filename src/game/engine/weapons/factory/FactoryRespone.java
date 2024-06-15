package game.engine.weapons.factory;

import game.engine.weapons.Weapon;

public class FactoryRespone {
	private final Weapon weapon;
	private final int remainingResources;

	public FactoryRespone(Weapon weapon,int remainingResources){
		this.weapon = weapon;
		this.remainingResources = remainingResources;

	}
	public Weapon getWeapon() {return this.weapon;}
	public int getRemainingResources() {return this.remainingResources;}

}
