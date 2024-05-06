package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import java.util.Stack;

import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController implements Initializable{
	@FXML 
	private AnchorPane anchorPane;
	@FXML
	private ImageView bgImage;
	@FXML
	private Label scoreLabel;
	@FXML
	private Label turnLabel;
	@FXML
	private Label resourcesLabel;
	@FXML
	private Label phaseLabel;
	@FXML
	private Button endTurnButton;
	@FXML
	private Button weaponShopInfoButton;
	private HashMap<Titan,TitanView> titanMap = new HashMap<Titan,TitanView>();
	private HashMap<Wall,WallView> wallMap = new HashMap<Wall,WallView>();

	private final double[] titanlaneYCoordinates = new double[] {133,245,375,485,590};
	private final double[] LaneYCoordinates = new double[] {60,188,293,426,553};



	private Battle battle;

	public GameController() {
		System.out.println("empty GameView constructor was called");
	}

	public void initialize(Battle b) {
		try {
			this.battle = b;
			System.out.println("start of gameController initialization");
			Image bgImagee = new Image(getClass().getResourceAsStream("Images/fivelanes.png"));
			System.out.println("loaded bgImage of 5 lanes successfully");

			if (b.getOriginalLanes().size()==3) {
				bgImagee = new Image(getClass().getResourceAsStream("Images/threelanes.png"));
				System.out.println("loaded bgImage of 3 lanes successfully");
			}
			this.bgImage.setImage(bgImagee);



		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("could not load image of lanes" + e.getMessage());
		}
		this.Initializewalls();
		updateLabels();
	}

	private void Initializewalls() {
		int laneYCoordinatesIndex =battle.getOriginalLanes().size()==5 ?  0 : 1;
		if (battle.getOriginalLanes().size()==3) {
			new WallView(this.anchorPane,this.LaneYCoordinates[0]);
		}
		for (Lane lane:  battle.getOriginalLanes()) {
			Wall laneWall = lane.getLaneWall();
			WallView wall = new WallView(laneWall,this.anchorPane,this.LaneYCoordinates[laneYCoordinatesIndex]);
			wallMap.put(laneWall, wall);
			laneYCoordinatesIndex++;
		}
		if (battle.getOriginalLanes().size()==3) {
			new WallView(this.anchorPane,this.LaneYCoordinates[4]);
		}

	}
	public void buyWeaponOne(ActionEvent e) {
		System.out.println("user is trying to buy the first weapon");
	}
	public void onEndTurn(ActionEvent e) {

		try {
			System.out.println("EndTurn was clicked");
			battle.passTurn();
			updateBattleView();
			battle.setScore(battle.getScore()+10);//remove when doing actual game
			battle.getLanes().peek().getLaneWall().takeDamage(2500);
		}catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("some problem happend pal," + e1.getMessage());
		}
		handleGameOver();
	}
	private void handleGameOver() {
		if (battle.isGameOver()) {
			//logic for going to end of game page ("test.fxml") then going to main menu
		}
	}

	private void updateBattleView() {
		//TODO Ask around how tf to disable the button till the animations finsish

		drawTitans();
		updateWalls();
		updateLabels();

	}

	private void updateWalls() {

		for (Lane l : battle.getOriginalLanes()) {
			Wall laneWall = l.getLaneWall();
			WallView view = wallMap.get(laneWall);
			view.updateHealthBar(laneWall.getCurrentHealth());
			if (laneWall.isDefeated()) {
				wallMap.get(laneWall).defeat();
				System.out.println(" wall " + (battle.getOriginalLanes().indexOf(laneWall)+1)+"is defeted");
			}

		}

	}


	/*
	 * DO We remove titans from defeated lanes once a lane is defeated?
	 * if so do we move titans -> update wall stats -> remove titans?
	 * if that is the case consider firing a running animation once a wall is defeated
	 * removing would be done in update wall method
	 * */
	private void drawTitans() {
		// TODO Auto-generated method stub
		int laneYCoordinatesIndex =battle.getOriginalLanes().size()==5 ?  0 : 1;

		for (Lane l : battle.getOriginalLanes()) {
			drawLaneTitans(l.getTitans(),titanlaneYCoordinates[laneYCoordinatesIndex]);
			laneYCoordinatesIndex++;
		}

	}

	private void drawLaneTitans(PriorityQueue<Titan> titans, double yCoordinate) {
		Stack<Titan> titanStack = new Stack<Titan>();
		while (!titans.isEmpty()) {
			titanStack.push(titans.remove());

			Titan currentTitan = titanStack.peek();



			if (!titanMap.containsKey(currentTitan)) {//1200 is start x titan coord
				titanMap.put(currentTitan, new TitanView(currentTitan,anchorPane,yCoordinate,1100));
			}
			TitanView titanView = titanMap.get(currentTitan);
			if (currentTitan.isDefeated()) {
				System.out.print("defeated ");
				titanView.defeat(0.1); 
				anchorPane.getChildren().remove(titanView);
				titanMap.remove(currentTitan);
			}
			else if (!currentTitan.hasReachedTarget()) {
				titanView.walk(titanStack.peek().getSpeed(),0.1);
			}
			else if (currentTitan.hasReachedTarget()) {
				titanView.attack(currentTitan instanceof AbnormalTitan,0.2);
				System.out.print("attack ");
			}
		}

		while (!titanStack.isEmpty()) {
			titans.add(titanStack.pop());
		}
	}

	private void updateLabels() {

		scoreLabel.setText("score: " + battle.getScore());
		resourcesLabel.setText("resources: " + battle.getResourcesGathered());
		phaseLabel.setText("Phase: "+ battle.getBattlePhase());
		turnLabel.setText("Turn: "+battle.getNumberOfTurns());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub




		endTurnButton.setOnMouseEntered(new EventHandler<>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("entered the holy button");
				endTurnButton.setStyle("-fx-background-radius: 30; -fx-background-color: #bec626;");
			}

		});
		endTurnButton.setOnMouseExited(new EventHandler<>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("exited the holy button");
				endTurnButton.setStyle("-fx-background-radius: 30; -fx-background-color: #755a00;");
			}

		});
		weaponShopInfoButton.setOnAction(new EventHandler<>() {


			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Stage weaponshopstage = new Stage();
				try {
					FXMLLoader loader = new FXMLLoader(GameController.class.getResource("weaponShop.fxml"));
					Parent root = loader.load();
					weaponshopstage.setScene(new Scene(root));
					weaponshopstage.setResizable(false);
					weaponshopstage.show();
					weaponShopController wsc = loader.getController();
					wsc.setValues(battle.getWeaponFactory().getWeaponShop());



				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error");
					a.setContentText("failed to show weaponShop info");
					a.show();
				}

			}

		});


	}




}
