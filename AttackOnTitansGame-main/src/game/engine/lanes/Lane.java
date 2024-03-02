package game.engine.lanes;

import java.util.*;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;


public class Lane implements Comparable<Lane> {
	
	//All attributes are READ ONLY
	
	private final Wall laneWall;
	
	//READ and WRITE 
	private int dangerLevel;
	
	private final PriorityQueue<Titan> titans; //how does it get filled? 
	
	private final ArrayList<Weapon> weapons; //how does it get filled?

	public Lane(Wall laneWall) {
		this.laneWall = laneWall;
		this.dangerLevel = 0;
		this.titans = new PriorityQueue<Titan>();
		this.weapons = new ArrayList<Weapon>();
		
	}

	//battleClass needs lane with least danger level as priority
	public int compareTo(Lane other) {
		return  this.dangerLevel - other.dangerLevel;
	}
	
	public int getDangerlevel() {return this.dangerLevel;}
	
	public Wall getLaneWall() {return this.laneWall;}
	public PriorityQueue<Titan> getTitans(){return this.titans;}
	public ArrayList<Weapon> getWeapons(){return this.weapons;}
	
	

}
