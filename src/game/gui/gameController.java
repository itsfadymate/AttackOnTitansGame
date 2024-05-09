package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import game.engine.Battle;
import game.engine.base.Wall;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import game.engine.weapons.WallTrap;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
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
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
//TODO: Game crashes after round 52
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
	@FXML
	private Button firstWeaponButton;
	@FXML
	private Button secondWeaponButton;
	@FXML
	private Button thirdWeaponButton;
	@FXML
	private Button fourthWeaponButton;
	@FXML
	private Rectangle firstLaneRectanlge;
	@FXML
	private Rectangle secondLaneRectanlge;
	@FXML
	private Rectangle thirdLaneRectangle;
	@FXML
	private Rectangle fourthLaneRectangle;
	@FXML
	private Rectangle fifthLaneRectangle;

	private int selectedWeaponCode=-1;

	private HashMap<Titan,TitanView> titanMap = new HashMap<Titan,TitanView>();
	private HashMap<Wall,WallView> wallMap = new HashMap<Wall,WallView>();

	private final double[] titanlaneYCoordinates = new double[] {133,245,375,485,590};
	private final double[] laneYCoordinates = new double[] {60,188,316,444,573};
	private final TilePane[] tilePanes = new TilePane[5];



	private Battle battle;
	private CopyOnWriteArrayList<Titan> allTitans;

	public GameController() {
		System.out.println("empty GameView constructor was called");
	}

	public void initialize(Battle b) {
		this.battle = b;
		this.allTitans = new CopyOnWriteArrayList<Titan>();
		this.loadBackgroundImage(b);
		this.Initializewalls();
		this.InitializeTilePanes();
		this.updateLabels();

	}

	private void InitializeTilePanes() {
		for (int i=0;i<5;i++) {
			tilePanes[i] = new TilePane();
			tilePanes[i].setPrefWidth(135);
			tilePanes[i].setPrefHeight(123);
			tilePanes[i].setLayoutX(WallView.getWalllayoutx());
			tilePanes[i].setLayoutY(this.laneYCoordinates[i]);
			this.anchorPane.getChildren().add(tilePanes[i]);
		}

	}

	private void loadBackgroundImage(Battle b) {
		try {
			Image bgImagee = new Image(getClass().getResourceAsStream("Images/fivelanes.png"));
			if (b.getOriginalLanes().size()==3) {
				bgImagee = new Image(getClass().getResourceAsStream("Images/threelanes.png"));
			}
			this.bgImage.setImage(bgImagee);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("could not load image of lanes" + e.getMessage());
		}
	}

	private void Initializewalls() {
		int laneYCoordinatesIndex =battle.getOriginalLanes().size()==5 ?  0 : 1;
		if (battle.getOriginalLanes().size()==3) {
			new WallView(this.anchorPane,this.laneYCoordinates[0]);
		}
		for (Lane lane:  battle.getOriginalLanes()) {
			Wall laneWall = lane.getLaneWall();
			WallView wall = new WallView(laneWall,this.anchorPane,this.laneYCoordinates[laneYCoordinatesIndex]);
			wallMap.put(laneWall, wall);
			laneYCoordinatesIndex++;
		}
		if (battle.getOriginalLanes().size()==3) {
			new WallView(this.anchorPane,this.laneYCoordinates[4]);
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

	}

	public void selectWeapon(ActionEvent e) {
		Button clickedButton = (Button) e.getSource();
		System.out.println("selected a lane");
		if (clickedButton == this.firstWeaponButton) {
			this.selectedWeaponCode=1;
		}else if(clickedButton == this.secondWeaponButton) {
			this.selectedWeaponCode=2;
		}else if (clickedButton == this.thirdWeaponButton) {
			this.selectedWeaponCode=3;
		}else if (clickedButton == this.fourthWeaponButton) {
			this.selectedWeaponCode=4;
		}
	}

	public void buyLaneWeapon(MouseEvent e) {
		Rectangle clickedRectangle = (Rectangle)e.getSource();
		if (this.selectedWeaponCode==-1) {
			this.showMsg("No Weapon was selected!");
			return;
		}
		try {
			if (clickedRectangle == this.firstLaneRectanlge) {

				//if only 3 lanes then 1st and 5th lanes cannot purchase weapons
				if (this.battle.getOriginalLanes().size()==3) return;

				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(0));
				buyLaneWeaponHelper(0);



			} else if (clickedRectangle == this.secondLaneRectanlge) {

				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(1));
				buyLaneWeaponHelper(1);



			}
			else if (clickedRectangle== this.thirdLaneRectangle) {


				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(2));
				buyLaneWeaponHelper(2);




			}
			else if (clickedRectangle== this.fourthLaneRectangle) {



				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(3));
				buyLaneWeaponHelper(3);




			}
			else if (clickedRectangle== this.fifthLaneRectangle) {
				//if only 3 lanes then 1st and 5th lanes cannot purchase weapons
				if (this.battle.getOriginalLanes().size()==3) return;


				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(4));
				buyLaneWeaponHelper(4);

			}

			this.endTurnButton.setDisable(true);
			this.firstWeaponButton.setDisable(true);
			this.secondWeaponButton.setDisable(true);
			this.thirdWeaponButton.setDisable(true);
			this.fourthWeaponButton.setDisable(true);
			//2.5 is time taken for move action of a titan check walk method for changes
			PauseTransition timer = new PauseTransition(Duration.seconds(2.5));
			timer.setOnFinished(finishedevent -> {
				this.endTurnButton.setDisable(false);
				this.firstWeaponButton.setDisable(false);
				this.secondWeaponButton.setDisable(false);
				this.thirdWeaponButton.setDisable(false);
				this.fourthWeaponButton.setDisable(false);

			});
			timer.play();

			updateBattleView();


		} catch (InsufficientResourcesException e1) {
			System.out.println("not enough money ya 7bb");
			showMsg("Not Enough Money!");

		}catch (InvalidLaneException e2) {
			showMsg("InvalidLane choosen!");
		}catch (Exception e3) {
			System.out.println("something wrong happened " + e3.getMessage());
			e3.printStackTrace();
		}
		handleGameOver();

	}

	private void showMsg(String msg) {
		Label noMoney = new Label(msg);
		noMoney.setTextFill(Color.DARKRED);
		noMoney.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
		noMoney.setLayoutX(800);


		FadeTransition fady = new FadeTransition(Duration.seconds(2),noMoney);
		fady.setFromValue(1.0);
		fady.setToValue(0.0);

		TranslateTransition translate = new TranslateTransition(Duration.seconds(2),noMoney);
		translate.setFromY(600);
		translate.setToY(500);
		translate.setCycleCount(1);
		translate.setOnFinished(finished-> fady.play());
		this.anchorPane.getChildren().add(noMoney);
		translate.play();

		fady.setOnFinished(finished->this.anchorPane.getChildren().remove(noMoney));
	}

	private void buyLaneWeaponHelper(int tilePaneIndex){
		// TODO Auto-generated method stub
		if (this.selectedWeaponCode==WallTrap.WEAPON_CODE) {
			System.out.println("buyin wall trap x= " + (WallView.getWallOuterBoundary()-15) +" y="+ (this.titanlaneYCoordinates[tilePaneIndex]));
			WeaponView wallTrapView = new WeaponView(selectedWeaponCode);
			wallTrapView.setLayoutX(WallView.getWallOuterBoundary()-15);
			wallTrapView.setLayoutY(this.titanlaneYCoordinates[tilePaneIndex]);
			this.anchorPane.getChildren().add(wallTrapView);
		}else {
			this.tilePanes[tilePaneIndex].getChildren().add(new WeaponView(selectedWeaponCode));
		}
	}

	public void showRectangle(MouseEvent e) {
		Rectangle enteredRectangle =(Rectangle)e.getSource(); 
		if ((enteredRectangle==this.firstLaneRectanlge || enteredRectangle ==this.fifthLaneRectangle) && this.battle.getOriginalLanes().size()==3) {
			return;
		}
		if (this.selectedWeaponCode==-1) return;
		enteredRectangle.setOpacity(0.18);
	}

	public void hideRectange(MouseEvent e) {
		Rectangle enteredRectangle =(Rectangle)e.getSource(); 
		if ((enteredRectangle==this.firstLaneRectanlge || enteredRectangle ==this.fifthLaneRectangle) && this.battle.getOriginalLanes().size()==3) {
			return;
		}
		enteredRectangle.setOpacity(0);
	}

	public void onEndTurn(ActionEvent e) {
		System.out.println("skipping turn");
		try {

			this.endTurnButton.setDisable(true);
			this.firstWeaponButton.setDisable(true);
			this.secondWeaponButton.setDisable(true);
			this.thirdWeaponButton.setDisable(true);
			this.fourthWeaponButton.setDisable(true);
			//2.5 is time taken for move action of a titan check walk method for changes
			PauseTransition timer = new PauseTransition(Duration.seconds(2.5));
			timer.setOnFinished(finishedevent -> {
				this.endTurnButton.setDisable(false);
				this.firstWeaponButton.setDisable(false);
				this.secondWeaponButton.setDisable(false);
				this.thirdWeaponButton.setDisable(false);
				this.fourthWeaponButton.setDisable(false);

			});
			timer.play();
			battle.passTurn();
			updateBattleView();

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

	public void updateBattleView() {
		//TODO Ask all buttons should be disabled till cycle is done

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
			System.out.println();

		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();


	}

	private void drawLaneTitans(PriorityQueue<Titan> titans, double yCoordinate) {
		Stack<Titan> titanStack = new Stack<Titan>();
		for (Titan t : allTitans) {
			if (t.isDefeated()) {
				titanMap.get(t).defeat(0.3);
				this.anchorPane.getChildren().remove(titanMap.get(t));
				titanMap.remove(t);
				allTitans.remove(t);
			}
		}
		while (!titans.isEmpty()) {
			titanStack.push(titans.remove());

			Titan currentTitan = titanStack.peek();
			if (!allTitans.contains(currentTitan)) {
				allTitans.add(currentTitan);
			}



			if (!titanMap.containsKey(currentTitan)) {//1200 is start x titan coord
				titanMap.put(currentTitan, new TitanView(currentTitan,anchorPane,yCoordinate));
				System.out.println("hasmap" + titanMap );
			}
			TitanView titanView = titanMap.get(currentTitan);

			if (!currentTitan.hasReachedTarget()) {
				titanView.walk(titanStack.peek().getSpeed(),0.1);
			}
			else if (currentTitan.hasReachedTarget()) {
				titanView.attack(currentTitan instanceof AbnormalTitan,0.2);
				System.out.println("attack ");
			}
			titanView.updateHealthBar(currentTitan.getCurrentHealth());
			System.out.println("current titan distance: "+ currentTitan.getDistance());

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
