package game.engine.interfaces;

public interface Attackee {
   public int getCurrentHealth();

   public void setCurrentHealth(int health);

   public int getResourcesValue();

   public default boolean isDefeated() {
	   return getCurrentHealth()==0;
   }

   public default int takeDamage(int Damage) {
	   int currHealth = getCurrentHealth();
	   //if (currHealth==0) return 0;
	   int newHealth = currHealth - Damage;
	   setCurrentHealth(newHealth);
	   if (isDefeated()) {
		return getResourcesValue();
	} else {
		return 0;
	}
   }

}
