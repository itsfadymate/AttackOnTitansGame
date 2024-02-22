package game.engine.dataloader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import game.engine.titans.*;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WeaponRegistry;

public  class DataLoader {
    private final static String TITANS_FILE_NAME = "titans.csv";
    private final static String WEAPONS_FILE_NAME = "weapons.csv";
	
	public static HashMap<Integer,TitanRegistry> readTitanregistry() throws IOException{
		    HashMap<Integer,TitanRegistry> hm = new HashMap<>();
		
			BufferedReader bf = new BufferedReader(new FileReader(TITANS_FILE_NAME));
			String line;
			while ((line = bf.readLine())!=null) {
				String[] lineData = line.split(",");
				
				int code = Integer.parseInt(lineData[0]);
				int baseHealth =Integer.parseInt(lineData[1]);
				int baseDamage =Integer.parseInt(lineData[2]);
				int heightInMeters =Integer.parseInt(lineData[3]);
				int speed =Integer.parseInt(lineData[4]);
				int resourcesValue=Integer.parseInt(lineData[5]);
				int dangerLevel=Integer.parseInt(lineData[6]);
				
				
				
				TitanRegistry registry = new TitanRegistry(code,baseHealth,baseDamage,heightInMeters,speed,resourcesValue,dangerLevel);
				hm.put(code, registry);
				
				
			}
			bf.close();
		
		    return hm;
		
	}
    
	public static HashMap<Integer,WeaponRegistry> readWeaponRegistry() throws IOException{
		    HashMap<Integer,WeaponRegistry> hm = new HashMap<>();
		
			BufferedReader bf = new BufferedReader(new FileReader(WEAPONS_FILE_NAME));
			String line;
			while ((line = bf.readLine())!=null) {
				String[] lineData = line.split(",");
				WeaponRegistry registry;
				int code = Integer.parseInt(lineData[0]);
				int price = Integer.parseInt(lineData[1]);
				int damage = Integer.parseInt(lineData[2]);
				String name = lineData[3];
				if (code == VolleySpreadCannon.WEAPON_CODE) {
					int minRange = Integer.parseInt(lineData[4]);
					int maxRange = Integer.parseInt(lineData[5]);
				    registry = new WeaponRegistry(code,price,damage,name,minRange,maxRange);
				}else {
					registry = new WeaponRegistry(code,price,damage,name);
				}
				hm.put(code, registry);
				
		    }
			bf.close();
		
		    return hm;
	}
	
}
