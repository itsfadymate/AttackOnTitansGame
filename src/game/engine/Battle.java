package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

import game.engine.base.Wall;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.factory.FactoryResponse;
import game.engine.weapons.factory.WeaponFactory;

public class Battle
{
	private static final int[][] PHASES_APPROACHING_TITANS =
	{
		{ 1, 1, 1, 2, 1, 3, 4 },
		{ 2, 2, 2, 1, 3, 3, 4 },
		{ 4, 4, 4, 4, 4, 4, 4 } 
	}; // order of the types of titans (codes) during each phase
	private static final int WALL_BASE_HEALTH = 10000;

	private int numberOfTurns;
	private int resourcesGathered;
	private BattlePhase battlePhase;
	private int numberOfTitansPerTurn; // initially equals to 1
	private int score; // Number of Enemies Killed
	private int titanSpawnDistance;
	private final WeaponFactory weaponFactory;
	private final HashMap<Integer, TitanRegistry> titansArchives;
	private final ArrayList<Titan> approachingTitans; // treated as a Queue
	private final PriorityQueue<Lane> lanes;
	private final ArrayList<Lane> originalLanes;

	public Battle(int numberOfTurns, int score, int titanSpawnDistance, int initialNumOfLanes,
			int initialResourcesPerLane) throws IOException
	{
		super();
		this.numberOfTurns = numberOfTurns;
		this.battlePhase = BattlePhase.EARLY;
		this.numberOfTitansPerTurn = 1;
		this.score = score;
		this.titanSpawnDistance = titanSpawnDistance;
		this.resourcesGathered = initialResourcesPerLane * initialNumOfLanes;
		this.weaponFactory = new WeaponFactory();
		this.titansArchives = DataLoader.readTitanRegistry();
		this.approachingTitans = new ArrayList<Titan>();
		this.lanes = new PriorityQueue<>();
		this.originalLanes = new ArrayList<>();
		this.initializeLanes(initialNumOfLanes);
	}

	public int getNumberOfTurns()
	{
		return numberOfTurns;
	}

	public void setNumberOfTurns(int numberOfTurns)
	{
		this.numberOfTurns = numberOfTurns;
	}

	public int getResourcesGathered()
	{
		return resourcesGathered;
	}

	public void setResourcesGathered(int resourcesGathered)
	{
		this.resourcesGathered = resourcesGathered;
	}

	public BattlePhase getBattlePhase()
	{
		return battlePhase;
	}

	public void setBattlePhase(BattlePhase battlePhase)
	{
		this.battlePhase = battlePhase;
	}

	public int getNumberOfTitansPerTurn()
	{
		return numberOfTitansPerTurn;
	}

	public void setNumberOfTitansPerTurn(int numberOfTitansPerTurn)
	{
		this.numberOfTitansPerTurn = numberOfTitansPerTurn;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getTitanSpawnDistance()
	{
		return titanSpawnDistance;
	}

	public void setTitanSpawnDistance(int titanSpawnDistance)
	{
		this.titanSpawnDistance = titanSpawnDistance;
	}

	public WeaponFactory getWeaponFactory()
	{
		return weaponFactory;
	}

	public HashMap<Integer, TitanRegistry> getTitansArchives()
	{
		return titansArchives;
	}

	public ArrayList<Titan> getApproachingTitans()
	{
		return approachingTitans;
	}

	public PriorityQueue<Lane> getLanes()
	{
		return lanes;
	}

	public ArrayList<Lane> getOriginalLanes()
	{
		return originalLanes;
	}

	private void initializeLanes(int numOfLanes)
	{
		for (int i = 0; i < numOfLanes; i++)
		{
			Wall w = new Wall(WALL_BASE_HEALTH);
			Lane l = new Lane(w);

			this.getOriginalLanes().add(l);
			this.getLanes().add(l);
		}
	}
	
	  public void refillApproachingTitans() {
		  int battlePhaseIndex = this.getBattlePhase().ordinal();
		  for (int i=0;i<PHASES_APPROACHING_TITANS[battlePhaseIndex].length;i++){
			  int code = PHASES_APPROACHING_TITANS[battlePhaseIndex][i];
			  TitanRegistry reg = titansArchives.get(code);
			  int baseHealth = reg.getBaseHealth();
			  int baseDamage = reg.getBaseDamage();
			  int heightInMeters= reg.getHeightInMeters();
			  int speed= reg.getSpeed();
			  int resourcesValue = reg.getResourcesValue();
			  int dangerLevel = reg.getDangerLevel();
			  if (code == AbnormalTitan.TITAN_CODE) {
				  approachingTitans.add(new AbnormalTitan(code,baseHealth,baseDamage,heightInMeters,speed,resourcesValue,dangerLevel) );
			  }else if (code ==ArmoredTitan.TITAN_CODE) {
				  approachingTitans.add(new ArmoredTitan(code,baseHealth,baseDamage,heightInMeters,speed,resourcesValue,dangerLevel) );	
			  }else if (code ==ColossalTitan.TITAN_CODE) {
				  approachingTitans.add(new ColossalTitan(code,baseHealth,baseDamage,heightInMeters,speed,resourcesValue,dangerLevel) );  
			  }else if (code == PureTitan.TITAN_CODE) {
				  approachingTitans.add(new PureTitan(code,baseHealth,baseDamage,heightInMeters,speed,resourcesValue,dangerLevel) );  	
				}
			  
		  }
		  
	    	
	    }
	    
	    public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException,
	    InvalidLaneException{
	    	if (lane.isLaneLost()) throw new InvalidLaneException();
	    	FactoryResponse response = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
	    	lane.addWeapon(response.getWeapon());
	    	this.resourcesGathered = response.getRemainingResources();
	    }
	    
	    //updated scores w dl gad3ana
	    public void passTurn() {
	    	moveTitans();
	    	this.score += performWeaponsAttacks();
	    	performTitansAttacks();
	    	addTurnTitansToLane();
	    	finalizeTurns();
	    	updateLanesDangerLevels();
	    }
	    
	    //TODO: figure out specifics
	    //what if approachingtitans isn't empty but doesn't have the minimum number needed
	    private void addTurnTitansToLane() {
	    	if (lanes.isEmpty())return;
	    	
	    	Lane lane = lanes.peek();
	    	for (int i=0;i<this.numberOfTitansPerTurn;i++) {
	    		if (approachingTitans.isEmpty()) 
	    			this.refillApproachingTitans();
	    		lane.addTitan(approachingTitans.remove(0));
	    	}
	    }
	    
	    //do we move titans in lost lanes or do they stay in place?
	    private void moveTitans() {
	    	for (Lane lane : originalLanes) {
	    		if (lane.isLaneLost()) continue;
	    		lane.moveLaneTitans();
	    		
	    	}
	    }
	    
	    
	    private int performWeaponsAttacks() {
	    	int resources = 0;
	    	for (Lane lane : originalLanes) {
	    		if (lane.isLaneLost()) continue;
	    		resources +=lane.performLaneWeaponsAttacks();
	    		
	    	}
	    	return resources;
	    }
	    
	    //does it handle removing destroyed lanes?
	    private int performTitansAttacks() {
	    	int resources = 0;
	    	for (Lane lane : originalLanes) {
	    		if (lane.isLaneLost()) continue;
	    		resources +=lane.performLaneTitansAttacks();
	    		
	    	}
	    	return resources;
	    }
	    
	    //handled removing destroyed lanes as well as updating dl
	    private void updateLanesDangerLevels() {
	    	for (Lane lane : originalLanes) {
	    		lane.updateDangerLevel();
	    	}
	    	Stack<Lane> s = new Stack<Lane>();
	    	while (!lanes.isEmpty()) {
	    		Lane l = lanes.poll();
	    		if (l.isLaneLost()) continue;
	    		s.push(l);
	    	}
	    	while (!s.isEmpty()) {
	    		lanes.add(s.pop());
	    	}
	    }
	    
	    private void finalizeTurns() {
	    	if (this.numberOfTurns<15) {
	    		this.battlePhase = battlePhase.EARLY;
	    	}else if (this.numberOfTurns<30) {
	    		this.battlePhase = battlePhase.INTENSE;
	    	}else if (this.numberOfTurns>=30  ) {
	    		this.battlePhase = battlePhase.GRUMBLING;
	    	}else if (this.numberOfTurns>30 && this.numberOfTurns%5==0) {
	    		this.battlePhase = battlePhase.GRUMBLING;
	    		this.numberOfTitansPerTurn*=2;
	    	}
	    	this.numberOfTurns++;
	 
	    }
	    
	    private void performTurn() {
	    	//buy weapon
	    	passTurn();
	    }
	    
	    boolean isGameOver() {
	    	for (Lane l: originalLanes) {
	    		if (!l.isLaneLost()) return false;
	    	}
	    	return true;
	    }	

}
