package game.engine;

import game.engine.weapons.factory.WeaponFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import game.engine.titans.*;
import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.lanes.Lane;

public class Battle {
	
	public static final int[][] PHASES_APPROACHING_TITANS = new int[4][];//size may be wrong
	
	public static final int WALL_BASE_HEALTH = 10000;
	
	//READ and WRITE
	private int numberOfTurns;
	
	//READ and WRITE
	private int resourcesGathered;
	
	//READ and WRITE
	private  BattlePhase battlePhase;
	
	//READ and WRITE
    private int numberOfTitansPerTurn;
    
	//READ and WRITE
    private int score;
    
	//READ and WRITE
    private int titanSpawnDistance;
    
    private final WeaponFactory weaponFactory;
    
    private final  HashMap<Integer,TitanRegistry> titanArcives;
    
    private final ArrayList<Titan> approachingTitans;
    
    private final PriorityQueue<Lane> lanes;
    
    private final ArrayList<Lane> originalLanes;
    
	public Battle(int numberOfTurns,int score,int titanSpawnDistance,int initialNumOfLanes,int initialResourcesPerLane) throws IOException{
		this.numberOfTurns = numberOfTurns;
		this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes ;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.weaponFactory = new WeaponFactory();
		this.titanArcives = DataLoader.readTitanregistry();
		this.approachingTitans = new ArrayList<>();
		this.lanes = new PriorityQueue<>();
		this.originalLanes = new ArrayList<>();
		
		
		
	}

	
	private void initializeLanes(int numOfLanes) {
		for (int i=1;i<=numOfLanes;i++) {
			Lane lane = new Lane(new Wall(WALL_BASE_HEALTH));
			lanes.add(lane);
			originalLanes.add(lane);
		}
	}

	
    public int getNumberOfTurns() {return this.numberOfTurns;}
    
    public void setNumberOfTurns(int numberOfTurns) {this.numberOfTurns = numberOfTurns;}
    
    public int getResourcesGathered() {return this.resourcesGathered;}
    
    public void setResourcesGathered(int resourcesGathered) {this.resourcesGathered = resourcesGathered;}
    
    public BattlePhase getBattlePhase() {return this.battlePhase;}
    
    public void setBattlePhase(BattlePhase battlePhase) {this.battlePhase = battlePhase;}
    
    public int getNumberOfTitansPerTurn() {return this.numberOfTitansPerTurn;}
    
    public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn) {this.numberOfTitansPerTurn = numberOfTitansPerTurn;}
    
    public int getScore() {return this.score;}
    
    public void setScore(int score) {this.score = score;}
    
    public int getTitanSpawnDistance() {return this.titanSpawnDistance;}
    
    public void setTitanSpawnDistance(int titanSpawnDistance) {this.titanSpawnDistance = titanSpawnDistance;}
    
    public WeaponFactory getWeaponFactory() {return this.weaponFactory;}
    
    public HashMap<Integer,TitanRegistry> getTitanArchives(){return this.titanArcives;}
    
    public ArrayList<Titan> getApproachingTitans(){return this.approachingTitans;}
    
    public PriorityQueue<Lane> getLanes(){return this.lanes;}
    
    public ArrayList<Lane> getOriginalLanes(){return this.originalLanes;}
    
    
}
