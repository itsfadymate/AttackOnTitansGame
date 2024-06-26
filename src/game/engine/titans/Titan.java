package game.engine.titans;
import game.engine.interfaces.Attackee;
import game.engine.interfaces.Attacker;
import game.engine.interfaces.Mobil;

public abstract class Titan implements Attackee,Attacker,Mobil,Comparable<Titan>{
	 //read only
      private final int baseHealth;
      //READ and Write using Attackee methods
      private int currentHealth;
      //READ ONLY
      private final int baseDamage;
      private final int heightInMeters;
      //READ and WRITE using mobil methods
      private int distanceFromBase;
      //READ and WRITE using mobil methods
      private int speed;
      //READ ONLY
      private final int resourcesValue;
      private final int dangerLevel;

      public Titan(int baseHealth,int baseDamage,int heightInMeters,int distanceFromBase,int speed,int resourcesValue,int dangerLevel) {
    	  this.baseHealth = baseHealth;
    	  this.currentHealth = baseHealth;
    	  this.baseDamage = baseDamage;
    	  this.heightInMeters = heightInMeters;
    	  this.speed = speed;
    	  this.distanceFromBase = distanceFromBase;
    	  this.resourcesValue = resourcesValue;
    	  this.dangerLevel = dangerLevel;

      }

      @Override
	public int compareTo(Titan o) {
    	  return this.distanceFromBase - o.distanceFromBase;
      }


      public int getBaseHealth() {return this.baseHealth;}

  	  @Override
	public int getCurrentHealth() {return currentHealth;}

  	  public int getHeightInMeters() {return this.heightInMeters;}

  	  @Override
	public void setCurrentHealth(int health) {

        if (health < 0 ) {
	         health = 0;
        }
        this.currentHealth = health;
  	  }

      @Override
	public int getResourcesValue() {
		return this.resourcesValue;
	  }

	  @Override
	public int getDamage() {
		return this.baseDamage;
	  }

	  @Override
	public int getDistance() {
		return this.distanceFromBase;
	  }

	  @Override
	public void setDistance(int distance) {
		  if (distance < 0) { distance = 0;}
	 	  this.distanceFromBase = distance;
	  }

	  @Override
	public int getSpeed() {return this.speed;}

	  @Override
	public void setSpeed(int speed) {
		if (speed<0) {
			speed =0;
		}
		this.speed = speed;
	  }

	  public int getDangerLevel() {return this.dangerLevel;}

	  @Override
	public  String toString() {
		  return "titan at distance: " + getDistance() + ", health: " +  getCurrentHealth() + ", speed: " + getSpeed() + ", damage: " + getDamage();
	  }
}
