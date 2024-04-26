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
import game.engine.titans.Titan;
import game.engine.titans.TitanRegistry;
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
			  approachingTitans.add(reg.spawnTitan(this.getTitanSpawnDistance()));  	
				
			  
		  }
		  
	    	
	    }
	    
	    public void purchaseWeapon(int weaponCode, Lane lane) throws InsufficientResourcesException,
	    InvalidLaneException{
	    	if (lane.isLaneLost()) throw new InvalidLaneException();
	    	if (!originalLanes.contains(lane)) throw new InvalidLaneException();
	    	if (!this.lanes.contains(lane)) throw new InvalidLaneException();
	    	
	    	FactoryResponse response = weaponFactory.buyWeapon(resourcesGathered, weaponCode);
	    	this.resourcesGathered = response.getRemainingResources();
	    	lane.addWeapon(response.getWeapon());
	    	performTurn();
	    	
	    }
	    
	    //updated dl gad3ana
	    public void passTurn() {
	    	performTurn();
	    
	    }
	    
	    //TODO: figure out specifics
	
	    private void addTurnTitansToLane() {
	    	if (lanes.isEmpty())return;
	    	
	    	Lane lane = lanes.peek();
	    	for (int i=0;i<this.numberOfTitansPerTurn;i++) {
	    		if (approachingTitans.isEmpty()) 
	    			this.refillApproachingTitans();
	    		lane.addTitan(approachingTitans.remove(0));
	    	}
	    }
	    
	    
	    private void moveTitans() {
	    	Stack<Lane> removedLanes = new Stack<Lane>();
	    	while (!lanes.isEmpty()) {
	    		lanes.peek().moveLaneTitans();
	    		removedLanes.push(lanes.remove());
	    	}
	    	while (!removedLanes.isEmpty()) {
	    		lanes.add(removedLanes.pop());
	    	}
	    }
	    
	    ////updated scores and resourcesGathered
	    private int performWeaponsAttacks() {
	    	int resources = 0;
	    	Stack<Lane> removedLanes = new Stack<Lane>();
	    	while (!lanes.isEmpty()) {
	    		resources +=lanes.peek().performLaneWeaponsAttacks();
	    		removedLanes.push(lanes.remove());
	    	}
	    	while (!removedLanes.isEmpty()) {
	    		lanes.add(removedLanes.pop());
	    	}

	    	this.resourcesGathered += resources;
	    	this.score+= resources;
	    	return resources;
	    }
	    
	  //handled removing destroyed lanes as well
	    private int performTitansAttacks() {
	    	//System.out.println("invoking performTitansAttacks...");
	    	int resources = 0;
	    	Stack<Lane> s = new Stack<Lane>();
	    	while (!lanes.isEmpty()) {
	    		Lane l = lanes.poll();
	    		resources +=l.performLaneTitansAttacks();
	    		if (l.isLaneLost()) 
	    			continue;
	    		s.push(l);
	    	}
	    	while (!s.isEmpty()) {
	    		lanes.add(s.pop());
	    	}
	    	//System.out.println("End of performTitansAttacks... resourcesGathered= " + resources);
	    	return resources;
	    }
	    
	    //handled removing destroyed lanes as well
	    private void updateLanesDangerLevels() {
	    	Stack<Lane> removedLanes = new Stack<Lane>();
	    	while (!lanes.isEmpty()) {
	    		lanes.peek().updateLaneDangerLevel();;
	    		removedLanes.push(lanes.remove());
	    	}
	    	while (!removedLanes.isEmpty()) {
	    		lanes.add(removedLanes.pop());
	    	}
	    
	    }
	    
	    //do we update number of turns before or after?
	    private void finalizeTurns() {
	    	//System.out.println("finalizeTurns has been called noOfTurns : " + this.numberOfTurns);
	    	this.numberOfTurns++;
	    	if (this.numberOfTurns<15) {
	    		this.battlePhase = BattlePhase.EARLY;
	    	}else if (this.numberOfTurns<30) {
	    		this.battlePhase = BattlePhase.INTENSE;
	    	}else if (this.numberOfTurns>=30  ) {
	    		this.battlePhase = BattlePhase.GRUMBLING;	
	    	}
	    
	    	if (this.numberOfTurns>30 && this.numberOfTurns%5==0)
    			this.numberOfTitansPerTurn*=2;	
	    	
	        //System.out.println("	noOfTurns: " + this.numberOfTurns + " bp: "+ this.battlePhase);
	    }
	    
	    private void performTurn() {
	    	moveTitans();
	    	performWeaponsAttacks();
	    	performTitansAttacks();
	    	addTurnTitansToLane();
	    	updateLanesDangerLevels();
	    	finalizeTurns();
	    }
	    
	    public boolean isGameOver() {
	    	return lanes.size()==0;
	    }	
	    
	   
	    
	    public static void main(String[] args) throws IOException {
	    	/*Battle b = new Battle(1,0,5,3,5);
	    	ArrayList<Lane> ol = b.getOriginalLanes();
	    	b.refillApproachingTitans();
	    	b.addTurnTitansToLane();
	    	consoleRepresentLanes(ol);
	    	for (int i=0;i<9;i++) {
	    		System.out.println("passingTurn...");
	    		b.passTurn();
	    		consoleRepresentLanes(ol);
	    	}
	    	*/
	    	/*ArrayList<Lane> ol = b.getOriginalLanes();
	    	
	    	consoleRepresentLanes(ol);
	    	
	    	System.out.println("adding Titans");
	    	
	    	AbnormalTitan t= new AbnormalTitan(1000,250,7,5,1,10,2);
	        ColossalTitan t2 = new ColossalTitan(1000,1000,7,5,1,10,4);
	    	PureTitan t3 = new PureTitan(1000,250,7,2,1,10,1);
	    	ArmoredTitan t4 = new ArmoredTitan(1000,500,7,4,1,10,3);
	    	ol.get(0).addTitan(t);
	    	ol.get(1).addTitan(t2);
	    	ol.get(2).addTitan(t3);
	    	ol.get(2).addTitan(t4);
	    	
	    	consoleRepresentLanes(ol);
	    	
	    	System.out.println("moving Titans");
	    	b.moveTitans();
	    	
	    	consoleRepresentLanes(ol);
	    	
	    	ol.get(0).getLaneWall().setCurrentHealth(0);
	    	b.moveTitans();
	    	
	    	System.out.println("destoyed first lane and moving Titans");
	    	
	    	consoleRepresentLanes(ol);
	    	
	    	System.out.println(" moving titans and making attacks");
	    	b.moveTitans();
	    	b.performTitansAttacks();
	    	
	    	consoleRepresentLanes(ol);
	    	
	    	System.out.println(" moving titans and making attacks");
	    	b.moveTitans();
	    	b.performTitansAttacks();
	    	
	    	consoleRepresentLanes(ol);*/
	    	
	    	
	    	
	    	
	    }
	    public static void consoleRepresentLanes(ArrayList<Lane> ol) {
	    	for (int i=1;i<=ol.size();i++) {
	    		System.out.println("lane " + i + ":");
	    		Lane l = ol.get(i-1);
	    		l.consoleRepresntLane();
	    	}
	    }

}
