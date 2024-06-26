package game.engine.weapons;

public class WeaponRegistry {
	private final int code;
	private int price;
	private final int damage;
	private final String name;
	private final int minRange;
	private final int maxRange;


	public WeaponRegistry(int code,int price) {
		this.code = code;
		this.price = price;
		this.damage = 0;
		this.name = null;
		this.minRange = 0;
		this.maxRange = 0;
	}

	public WeaponRegistry(int code,int price,int damage,String name) {
		this.code = code;
		this.price = price;
		this.damage = damage;
		this.name = name;
		this.minRange = 0;
		this.maxRange = 0;
	}

	public WeaponRegistry(int code,int price,int damage,String name,int minRange,int maxRange) {
		this.code = code;
		this.price = price;
		this.damage = damage;
		this.name = name;
		this.minRange = minRange;
		this.maxRange = maxRange;

	}

	public int getCode() {return this.code;}

	public int getPrice() {return this.price;}
	public int getDamage() {return this.damage;}
	public String getName() {return this.name;}
	public int getMinRange() {return this.minRange;}
	public int getMaxRange() {return this.maxRange;}
	public Weapon buildWeapon() {
		Weapon weapon = null;
		if (code == PiercingCannon.WEAPON_CODE) {
			weapon = new PiercingCannon(damage);
		}else if (code ==SniperCannon.WEAPON_CODE) {
			weapon = new SniperCannon(damage);
		}else if (code ==VolleySpreadCannon.WEAPON_CODE) {
			weapon = new VolleySpreadCannon(damage,minRange,maxRange);
		}else if (code == WallTrap.WEAPON_CODE) {
			weapon = new WallTrap(damage);
		}
		return weapon;
	}
	/*public String toString() {
		return "(code: " + code + " price: " + price + " damage: " + damage + " name: "+ name+
				" minRange: " + minRange + " maxRange " + maxRange+")";
	}*/

}
