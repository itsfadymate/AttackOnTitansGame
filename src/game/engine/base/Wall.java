package game.engine.base;

import game.engine.interfaces.Attackee;

public class Wall implements Attackee {
	private final int baseHealth;
	
	private int currentHealth;

	public Wall(int baseHealth) {
		this.baseHealth = baseHealth;
		this.currentHealth = baseHealth;
		
	}
	
	public int getBaseHealth() {return this.baseHealth;}

	
	public int getCurrentHealth() {
		return this.currentHealth;
	}

	
	public void setCurrentHealth(int health) {
		if (health < 0) health = 0;
		this.currentHealth = health;
		
	}

	//not deducted from player resources if destroyed
	public int getResourcesValue() {
		return -1;
	}

}
