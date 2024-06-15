package game.engine.interfaces;

public interface Attacker {
    public int getDamage();

    /**
     * makes target take damage that is done by the attacker
     * @param target to attack. is of type Attackee
     * @return resource value obtained if target is destroyed and 0 otherwise
     */
    public default int attack(Attackee target) {
    	//System.out.println("		attacking " + target );
    	int ret =  target.takeDamage(getDamage());
    	//System.out.println("		End of attack, resources = " + ret);
    	return ret;


    }
}
