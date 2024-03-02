package game.engine.weapons.factory;

import java.io.IOException;
import java.util.HashMap;

import game.engine.dataloader.DataLoader;
import game.engine.weapons.*;

public class WeaponFactory {
	private final HashMap<Integer,WeaponRegistry> weaponShop;

	public WeaponFactory() throws IOException {
		weaponShop = DataLoader.readWeaponRegistry();
		
	}
	
	public  HashMap<Integer,WeaponRegistry> getWeaponShop(){return this.weaponShop;}

}
