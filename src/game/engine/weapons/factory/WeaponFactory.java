package game.engine.weapons.factory;

import java.io.IOException;
import java.util.HashMap;

import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.weapons.*;

public class WeaponFactory {
	private final HashMap<Integer,WeaponRegistry> weaponShop;

	public WeaponFactory() throws IOException {
		weaponShop = DataLoader.readWeaponRegistry();
		
	}
	
	public  HashMap<Integer,WeaponRegistry> getWeaponShop(){return this.weaponShop;}
	 
	
	/**
	 * 
	 * @param resources
	 * @param weaponCode
	 * @return FactoryResponse  
	 * @throws InsufficientResourcesException
	 */
	public FactoryResponse buyWeapon(int resources, int weaponCode) throws
	 InsufficientResourcesException{
		 WeaponRegistry registry = weaponShop.get(weaponCode);
		 if (registry.getPrice() > resources) throw new InsufficientResourcesException(resources);
		 int remainingResources = resources - registry.getPrice();
		 Weapon weapon = registry.buildWeapon();
		 return new FactoryResponse(weapon,remainingResources);
	 }
	 public void addWeaponToShop(int code, int price) {
		 WeaponRegistry registry = new WeaponRegistry(code,price);
		 weaponShop.put(code,registry);
	 }
	 public void addWeaponToShop(int code, int price, int damage, String name) {
		 WeaponRegistry registry = new WeaponRegistry(code,price,damage,name);
		 weaponShop.put(code,registry);
	 }
	 public  void addWeaponToShop(int code, int price, int damage, String name, int minRange,
			 int maxRange) {
		 WeaponRegistry registry = new WeaponRegistry(code,price,damage,name,minRange,maxRange);
		 weaponShop.put(code,registry);
		 
	 }
}
