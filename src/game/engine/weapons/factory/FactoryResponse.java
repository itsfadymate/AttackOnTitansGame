package game.engine.weapons.factory;

import game.engine.weapons.Weapon;

public class FactoryResponse {
	private final Weapon weapon;
	private final int remainingResources;
	
	public FactoryResponse(Weapon weapon,int remainingResources){
		this.weapon = weapon;
		this.remainingResources = remainingResources;
		
	}
	public Weapon getWeapon() {return this.weapon;}
	public int getRemainingResources() {return this.remainingResources;} 
}
