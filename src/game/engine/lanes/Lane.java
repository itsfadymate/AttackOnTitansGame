package game.engine.lanes;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Stack;

import game.engine.base.Wall;
import game.engine.titans.Titan;
import game.engine.weapons.Weapon;


public class Lane implements Comparable<Lane> {

	//All attributes are READ ONLY

	private final Wall laneWall;

	//READ and WRITE
	private int dangerLevel;

	private final PriorityQueue<Titan> titans; //closest to base is front of pq

	private final ArrayList<Weapon> weapons;

	public Lane(Wall laneWall) {
		this.laneWall = laneWall;
		this.dangerLevel = 0;
		this.titans = new PriorityQueue<>();
		this.weapons = new ArrayList<>();

	}

	//battleClass needs lane with least danger level as priority
	@Override
	public int compareTo(Lane other) {
		return  this.dangerLevel - other.dangerLevel;
	}

	public int getDangerLevel() {return this.dangerLevel;}
	public void setDangerLevel(int DangerLevel) {this.dangerLevel=DangerLevel;}
	public Wall getLaneWall() {return this.laneWall;}
	public PriorityQueue<Titan> getTitans(){return this.titans;}
	public ArrayList<Weapon> getWeapons(){return this.weapons;}

	public void addTitan(Titan titan) {this.titans.add(titan);}
	public void addWeapon(Weapon weapon) {this.weapons.add(weapon);}
	public void moveLaneTitans() {
		if (this.isLaneLost()) {
			return;
		}
		Stack<Titan> removedTitans = new Stack<>();
		while (!titans.isEmpty()) {
			Titan titan = titans.remove();
			 if (!titan.hasReachedTarget()) {
				titan.move();
			}
			removedTitans.push(titan);
		}
		while (!removedTitans.isEmpty()) {
			titans.add(removedTitans.pop());
		}

	}

	public int performLaneTitansAttacks() {
		//System.out.println("	invoking laneTitansAttacks...");
		Stack<Titan> removedTitans = new Stack<>();
		int resourcesGathered =0;
		while (!titans.isEmpty() && titans.peek().hasReachedTarget()) {
			Titan titan = titans.remove();
			resourcesGathered += titan.attack(getLaneWall());
			removedTitans.push(titan);
		}
		while (!removedTitans.isEmpty()) {
			titans.add(removedTitans.pop());
		}
		//System.out.println("	End of performLaneTitansAttacks... resourcesGathered= " + resourcesGathered);
		return resourcesGathered;

	}

	public int performLaneWeaponsAttacks() {
		int resourcesGathered =0;
		for (Weapon weapon : weapons) {
			resourcesGathered+= weapon.turnAttack(titans);
		}
		return resourcesGathered;
	}
	public boolean isLaneLost() {
		return laneWall.isDefeated();
	}
	public void updateLaneDangerLevel() {
		if (this.isLaneLost()) {
			return;
		}
		Stack<Titan> removedTitans = new Stack<>();
		int cumulativeDangerLevel =0;
		while (!titans.isEmpty()) {
			Titan titan = titans.remove();
			cumulativeDangerLevel+= titan.getDangerLevel();
			removedTitans.push(titan);
		}
		while (!removedTitans.isEmpty()) {
			titans.add(removedTitans.pop());
		}

		this.setDangerLevel(cumulativeDangerLevel);

	}

	public void consoleRepresntLane() {
		for (Object o : titans.toArray()) {
			Titan t = (Titan) o;
			System.out.println(t);

		}
		System.out.println();
		for (Weapon w : weapons) {
			System.out.println(w);
		}
		System.out.println("	wallhealth: " + getLaneWall().getCurrentHealth());
		this.updateLaneDangerLevel();
		System.out.println("	lane dangerLevel: " + this.getDangerLevel());
		System.out.println("	lane is " + (this.isLaneLost()? " sadly " :" not yet ") + "lost" );
		System.out.println();
		System.out.println();
		System.out.println();
	}



}
