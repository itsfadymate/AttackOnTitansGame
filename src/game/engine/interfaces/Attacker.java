package game.engine.interfaces;

public interface Attacker {
    public int getDamage();
    
    /**
     * makes target take damage that is done by the attacker 
     * @param target to attack. is of type Attackee
     * @return resource value obtained if target is destroyed and 0 otherwise
     */
    public default int attack(Attackee target) {
    	target.takeDamage(getDamage());
    	if (target.isDefeated()) return target.getResourcesValue();
    	else return 0;
    }
}
