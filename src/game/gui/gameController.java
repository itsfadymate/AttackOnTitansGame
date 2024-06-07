package game.gui;

/*TODO:
 * mortar animation 
 * fix titan distance from wall
 * fix abnormal titan not getting removed
 * expand danger level labels
 * Find some bitch to fuck
 * 
 * */

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
	private Label titanPerTurnLabel;
	@FXML
	private Label DL1Label;
	@FXML
	private Label DL2Label;
	@FXML
	private Label DL3Label;
	@FXML
	private Label DL4Label;
	@FXML
	private Label DL5Label;
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
	@FXML
	private Button SettingButton;
	@FXML
	private Button Resume;
	@FXML
	private Button Quit;
	@FXML
	private Rectangle backRec;
	@FXML
	private VBox SettingContainer;
	@FXML
	private Slider Music;
	@FXML
	private Slider SFX;
	@FXML
	private Label C1;
	private int CannonCounter1 = 0;
	@FXML
	private Label C2;
	private int CannonCounter2 = 0;
	@FXML
	private Label C3;
	private int CannonCounter3 = 0;
	@FXML
	private Label C4;
	private int CannonCounter4 = 0;
	@FXML
	private Label C5;
	private int CannonCounter5 = 0;
	@FXML
	private Label S1;
	private int SniperCounter1 = 0;
	@FXML
	private Label S2;
	private int SniperCounter2 = 0;
	@FXML
	private Label S3;
	private int SniperCounter3 = 0;
	@FXML
	private Label S4;
	private int SniperCounter4= 0;
	@FXML
	private Label S5;
	private int SniperCounter5= 0;
	@FXML
	private Label M1;
	private int MortarCounter1= 0;
	@FXML
	private Label M2;
	private int MortarCounter2= 0;
	@FXML
	private Label M3;
	private int MortarCounter3= 0;
	@FXML
	private Label M4;
	private int MortarCounter4= 0;
	@FXML
	private Label M5;
	private int MortarCounter5= 0;
	@FXML
	private HBox firstInfo;
	@FXML
	private HBox secondInfo;
	@FXML
	private HBox thirdInfo;
	@FXML
	private HBox forthInfo;
	@FXML
	private HBox fifthInfo;
	
	private Button lastSelectedWeaponButton;
	private Boolean settingsOpen;
	private Boolean infoOpen;
	private gameOverScene gameOverScene;
	private Stage main;
	

	private int selectedWeaponCode=-1;
	private int previouslySelectedWeapon;

	private HashMap<Titan,TitanView> titanMap = new HashMap<Titan,TitanView>();
	private HashMap<Wall,WallView> wallMap = new HashMap<Wall,WallView>();
	private ArrayList<WeaponView> weaponsList = new ArrayList<WeaponView>(50);
	private HashMap<WeaponView,Lane> weaponLaneMap = new HashMap<WeaponView,Lane>();

	private final double[] titanlaneYCoordinates = new double[] {133,245,375,485,590};
	private final double[] laneYCoordinates = new double[] {60,188,316,444,573};
	private final TilePane[] tilePanes = new TilePane[5];
	private WeaponView[] boughtWallTrap = {null,null,null,null,null};
	private boolean isGameOver= false;




	private Battle battle;
	private CopyOnWriteArrayList<Titan> allTitans;

	public GameController() {
		System.out.println("empty GameView constructor was called");
	}

	public void initialize(Battle b , gameOverScene gameOverScene, Stage main) {
		this.gameOverScene = gameOverScene;
		this.main = main;
		this.battle = b;
		this.settingsOpen = false;
		this.infoOpen = false;
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
			tilePanes[i].setAlignment(Pos.CENTER);
			this.anchorPane.getChildren().add(tilePanes[i]);
		}
		if (battle.getOriginalLanes().size() == 5) {
			tilePanes[0].setOnMouseClicked(e -> {
					if (!infoOpen) {
						infoOpen = true;
						firstInfo.setVisible(true);
						firstInfo.toFront();
						}
						else {firstInfo.setVisible(false);infoOpen = false;}
							
				
			});	
			tilePanes[0].setOnMouseExited(e ->{
				firstInfo.setVisible(false);infoOpen = false;
			});}
			tilePanes[1].setOnMouseClicked(e -> {
				if (!infoOpen) {
					infoOpen = true;
					secondInfo.setVisible(true);
					secondInfo.toFront();
					}
				else {secondInfo.setVisible(false);infoOpen = false;}
			});
			tilePanes[1].setOnMouseExited(e ->{
				secondInfo.setVisible(false);infoOpen = false;
			});
			tilePanes[2].setOnMouseClicked(e -> {
				if (!infoOpen) {
					infoOpen = true;
					thirdInfo.setVisible(true);
					thirdInfo.toFront();
					}
				else {thirdInfo.setVisible(false);infoOpen = false;}
			});
			tilePanes[2].setOnMouseExited(e -> {
				thirdInfo.setVisible(false);infoOpen = false;
			});
			tilePanes[3].setOnMouseClicked(e -> {
				if (!infoOpen) {
					infoOpen = true;
					forthInfo.setVisible(true);
					forthInfo.toFront();
					}
				else {forthInfo.setVisible(false);infoOpen = false;}
			});
			tilePanes[3].setOnMouseExited(e -> {
				forthInfo.setVisible(false);infoOpen = false;
			});
			if (battle.getOriginalLanes().size() == 5) {
				tilePanes[4].setOnMouseClicked(e -> {
					if (!infoOpen) {
						infoOpen = true;
						fifthInfo.setVisible(true);
						fifthInfo.toFront();
						}
					else {fifthInfo.setVisible(false);infoOpen = false;}
				});
				tilePanes[4].setOnMouseExited(e -> {
					fifthInfo.setVisible(false);infoOpen = false;
				});
	}}

	private void loadBackgroundImage(Battle b) {
		try {
			Image bgImagee = new Image(getClass().getResourceAsStream("Images/lanes final2.jpg"));
			if (b.getOriginalLanes().size()==3) {
				bgImagee = new Image(getClass().getResourceAsStream("Images/finalThreeLanes.jpeg"));
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
		if (lastSelectedWeaponButton != null && clickedButton!=this.lastSelectedWeaponButton) {
			this.lastSelectedWeaponButton.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 30;");
		}
		this.lastSelectedWeaponButton = clickedButton;
		this.lastSelectedWeaponButton.setStyle(
  	           "-fx-background-color: #e1c642;"
  	            + "-fx-background-radius: 30; "
  	           	+ "-fx-border-radius: 30;"
  	           	+ "-fx-border-color:A00900;"
  	           	+ "-fx-border-width:4;"
  	           	+ "-fx-border-insets: -2;"
  				);
		
		System.out.println("selected a lane");
		if (clickedButton == this.firstWeaponButton) {
			this.selectedWeaponCode=PiercingCannon.WEAPON_CODE;
		}else if(clickedButton == this.secondWeaponButton) {
			this.selectedWeaponCode=SniperCannon.WEAPON_CODE;
		}else if (clickedButton == this.thirdWeaponButton) {
			this.selectedWeaponCode=VolleySpreadCannon.WEAPON_CODE;
		}else if (clickedButton == this.fourthWeaponButton) {//wallTrap
			this.selectedWeaponCode=WallTrap.WEAPON_CODE;
		}
		this.previouslySelectedWeapon = this.selectedWeaponCode;
	}

	public void buyLaneWeapon(MouseEvent e) {
		Rectangle clickedRectangle = (Rectangle)e.getSource();
		if (this.selectedWeaponCode==-1) {
			this.showMsg("No Weapon was selected!");
			return;
		}
		double translateByY = 100;//forshowing of messages
		int fontSize = 24;
		try {
			if (clickedRectangle == this.firstLaneRectanlge) {

				//if only 3 lanes then 1st and 5th lanes cannot purchase weapons
				if (this.battle.getOriginalLanes().size()==3) return;
				
                if (selectedWeaponCode==WallTrap.WEAPON_CODE && this.boughtWallTrap[0]!=null) {
                	showMsg("Already bought wall Trap in that lane ",firstLaneRectanlge.getLayoutX(),firstLaneRectanlge.getLayoutY(),translateByY,fontSize);return;
                }
				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(0));
				
				if (selectedWeaponCode == PiercingCannon.WEAPON_CODE) {
					CannonCounter1++;
					C1.setText("X" + CannonCounter1);
				}
				if (selectedWeaponCode == SniperCannon.WEAPON_CODE) {
					SniperCounter1++;
					S1.setText("X" + SniperCounter1);
				}
				if (selectedWeaponCode == VolleySpreadCannon.WEAPON_CODE) {
					MortarCounter1++;
					M1.setText("X" + MortarCounter1);
				}
				if (!(this.tilePanes[0].getChildren().size() >=9 && selectedWeaponCode!=WallTrap.WEAPON_CODE)) {
//					showMsg("max number of weapons Placed ");
					buyLaneWeaponHelper(0,this.battle.getOriginalLanes().get(0));
				}
				
			} else if (clickedRectangle == this.secondLaneRectanlge) {
				int laneIndex = this.battle.getOriginalLanes().size()==3 ? 0:1;
				
				
				if (selectedWeaponCode==WallTrap.WEAPON_CODE && this.boughtWallTrap[1]!=null) {
                	showMsg("Already bought wall Trap in that lane ",secondLaneRectanlge.getLayoutX(),secondLaneRectanlge.getLayoutY(),translateByY,fontSize);return;
                }
				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(laneIndex));
				
				if (selectedWeaponCode == PiercingCannon.WEAPON_CODE) {
					CannonCounter2++;
					C2.setText("X" + CannonCounter2);
				}
				if (selectedWeaponCode == SniperCannon.WEAPON_CODE) {
					SniperCounter2++;
					S2.setText("X" + SniperCounter2);
				}
				if (selectedWeaponCode == VolleySpreadCannon.WEAPON_CODE) {
					MortarCounter2++;
					M2.setText("X" + MortarCounter2);
				}
				if (!(this.tilePanes[1].getChildren().size() >=9 && selectedWeaponCode!=WallTrap.WEAPON_CODE)) {
//					showMsg("max number of weapons Placed ");
					buyLaneWeaponHelper(1,this.battle.getOriginalLanes().get(laneIndex));
				}
				

			}
			else if (clickedRectangle== this.thirdLaneRectangle) {
				int laneIndex = this.battle.getOriginalLanes().size()==3 ? 1:2;
				
				
				if (selectedWeaponCode==WallTrap.WEAPON_CODE && this.boughtWallTrap[2]!=null) {
                	showMsg("Already bought wall Trap in that lane ",thirdLaneRectangle.getLayoutX(),thirdLaneRectangle.getLayoutY(),translateByY,fontSize);return;
                }
				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(laneIndex));
				
				if (selectedWeaponCode == PiercingCannon.WEAPON_CODE) {
					CannonCounter3++;
					C3.setText("X" + CannonCounter3);
				}
				if (selectedWeaponCode == SniperCannon.WEAPON_CODE) {
					SniperCounter3++;
					S3.setText("X" + SniperCounter3);
				}
				if (selectedWeaponCode == VolleySpreadCannon.WEAPON_CODE) {
					MortarCounter3++;
					M3.setText("X" + MortarCounter3);
				}

				if (!(this.tilePanes[2].getChildren().size() >=9 && selectedWeaponCode!=WallTrap.WEAPON_CODE)) {
//					showMsg("max number of weapons Placed ");
					buyLaneWeaponHelper(2,this.battle.getOriginalLanes().get(laneIndex));
				}
				


			}
			else if (clickedRectangle== this.fourthLaneRectangle) {
				int laneIndex = this.battle.getOriginalLanes().size()==3 ? 2:3;

				 if (selectedWeaponCode==WallTrap.WEAPON_CODE && this.boughtWallTrap[3]!=null) {
                	showMsg("Already bought wall Trap in that lane ",fourthLaneRectangle.getLayoutX(),fourthLaneRectangle.getLayoutY(),translateByY,fontSize);return;
                }
				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(laneIndex));
				
				if (selectedWeaponCode == PiercingCannon.WEAPON_CODE) {
					CannonCounter4++;
					C4.setText("X" + CannonCounter4);
				}
				if (selectedWeaponCode == SniperCannon.WEAPON_CODE) {
					SniperCounter4++;
					S4.setText("X" + SniperCounter4);
				}
				if (selectedWeaponCode == VolleySpreadCannon.WEAPON_CODE) {
					MortarCounter4++;
					M4.setText("X" + MortarCounter4);
				}

				if (!(this.tilePanes[3].getChildren().size() >=9 && selectedWeaponCode!=WallTrap.WEAPON_CODE)) {
//					showMsg("max number of weapons Placed ");
					buyLaneWeaponHelper(3,this.battle.getOriginalLanes().get(laneIndex));
				}
				

			}
			else if (clickedRectangle== this.fifthLaneRectangle) {
				//if only 3 lanes then 1st and 5th lanes cannot purchase weapons
				if (this.battle.getOriginalLanes().size()==3) return;

				if (selectedWeaponCode==WallTrap.WEAPON_CODE && this.boughtWallTrap[4]!=null) {
                	showMsg("Already bought wall Trap in that lane ",fifthLaneRectangle.getLayoutX(),fifthLaneRectangle.getLayoutY(),translateByY,fontSize);return;
                }
				this.battle.purchaseWeapon(selectedWeaponCode, this.battle.getOriginalLanes().get(4));
				
				if (selectedWeaponCode == PiercingCannon.WEAPON_CODE) {
					CannonCounter5++;
					C5.setText("X" + CannonCounter5);
				}
				if (selectedWeaponCode == SniperCannon.WEAPON_CODE) {
					SniperCounter5++;
					S5.setText("X" + SniperCounter5);
				}
				if (selectedWeaponCode == VolleySpreadCannon.WEAPON_CODE) {
					MortarCounter5++;
					M5.setText("X" + MortarCounter5);
				}
				
				if (!(this.tilePanes[4].getChildren().size() >=9 && selectedWeaponCode!=WallTrap.WEAPON_CODE)) {
//					showMsg("max number of weapons Placed ");
					buyLaneWeaponHelper(4,this.battle.getOriginalLanes().get(4));
				}
				
			}

			this.endTurnButton.setDisable(true);
			this.firstWeaponButton.setDisable(true);
			this.secondWeaponButton.setDisable(true);
			this.thirdWeaponButton.setDisable(true);
			this.fourthWeaponButton.setDisable(true);
			//2.5 is time taken for move action of a titan check walk method for changes
			PauseTransition timer = new PauseTransition(Duration.seconds(TitanView.getTimeToMove()));
			timer.setOnFinished(finishedevent -> {
				if (isGameOver) return;
				this.endTurnButton.setDisable(false);
				this.firstWeaponButton.setDisable(false);
				this.secondWeaponButton.setDisable(false);
				this.thirdWeaponButton.setDisable(false);
				this.fourthWeaponButton.setDisable(false);
				this.selectedWeaponCode = this.previouslySelectedWeapon;
				this.lastSelectedWeaponButton.setStyle(
	           "-fx-background-color: #e1c642;"
	            + "-fx-background-radius: 30; "
	           	+ "-fx-border-radius: 30;"
	           	+ "-fx-border-color:A00900;"
	           	+ "-fx-border-width:4;"
	           	+ "-fx-border-insets: -2;"
				);

			});
			timer.play();

			updateBattleView();
			this.selectedWeaponCode=-1;
			this.lastSelectedWeaponButton.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 30;");


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

	private void showMsg(String msg, double layoutX, double layoutY,double translateBy,int fontSize) {
		// TODO Auto-generated method stub
		Label noMoney = new Label(msg);
		noMoney.setTextFill(Color.DARKRED);
		noMoney.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 24));
		noMoney.setLayoutX(layoutX);


		FadeTransition fady = new FadeTransition(Duration.seconds(2),noMoney);
		fady.setFromValue(1.0);
		fady.setToValue(0.0);

		TranslateTransition translate = new TranslateTransition(Duration.seconds(2),noMoney);
		translate.setFromY(layoutY);
		translate.setByY(translateBy);
		translate.setCycleCount(1);
		translate.setOnFinished(finished-> fady.play());
		this.anchorPane.getChildren().add(noMoney);
		translate.play();

		fady.setOnFinished(finished->this.anchorPane.getChildren().remove(noMoney));
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

	private void buyLaneWeaponHelper(int tilePaneIndex,Lane l){
		if (this.selectedWeaponCode==WallTrap.WEAPON_CODE) {
			System.out.println("buyin wall trap x= " + (WallView.getWallOuterBoundary()-15) +" y="+ (this.titanlaneYCoordinates[tilePaneIndex]));
			
			WeaponView wallTrapView = new WeaponView(selectedWeaponCode,this.anchorPane,this.battle.getWeaponFactory().getWeaponShop().get(selectedWeaponCode));
			wallTrapView.setLayoutX(firstLaneRectanlge.getLayoutX() + 40);
			wallTrapView.setLayoutY(getWalltrapLayoutY(tilePaneIndex));
			this.boughtWallTrap[tilePaneIndex] = wallTrapView;
			this.anchorPane.getChildren().add(wallTrapView);
			this.weaponsList.add(wallTrapView);
			this.weaponLaneMap.put(wallTrapView, l);
		}else {
			WeaponView wv =  new WeaponView(selectedWeaponCode,this.anchorPane,this.battle.getWeaponFactory().getWeaponShop().get(selectedWeaponCode));
			this.weaponsList.add(wv);
			this.tilePanes[tilePaneIndex].getChildren().add(wv);
			this.weaponLaneMap.put(wv, l);
		}
	}


	private double getWalltrapLayoutY(int tilePaneIndex) {

		switch (tilePaneIndex) {
		case 0: return this.firstLaneRectanlge.getLayoutY();
		case 1: return this.secondLaneRectanlge.getLayoutY();
		case 2: return this.thirdLaneRectangle.getLayoutY();
		case 3: return this.fourthLaneRectangle.getLayoutY();
		case 4: return this.fifthLaneRectangle.getLayoutY();
		}
		return 0;
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
            this.selectedWeaponCode = -1;
			this.endTurnButton.setDisable(true);
			this.firstWeaponButton.setDisable(true);
			this.secondWeaponButton.setDisable(true);
			this.thirdWeaponButton.setDisable(true);
			this.fourthWeaponButton.setDisable(true);
			//2.5 is time taken for move action of a titan check walk method for changes
			PauseTransition timer = new PauseTransition(Duration.seconds(TitanView.getTimeToMove()));
			timer.setOnFinished(finishedevent -> {
				if (isGameOver) return;
				this.endTurnButton.setDisable(false);
				this.firstWeaponButton.setDisable(false);
				this.secondWeaponButton.setDisable(false);
				this.thirdWeaponButton.setDisable(false);
				this.fourthWeaponButton.setDisable(false);
                this.selectedWeaponCode = this.previouslySelectedWeapon;
                this.lastSelectedWeaponButton.setStyle(
         	           "-fx-background-color:  #e1c642;"
         	            + "-fx-background-radius: 30; "
         	           	+ "-fx-border-radius: 30;"
         	           	+ "-fx-border-color:A00900;"
         	           	+ "-fx-border-width:4;"
         	           	+ "-fx-border-insets: -2;"
         				);
			});
			timer.play();
			battle.passTurn();
			updateBattleView();
			if(this.lastSelectedWeaponButton!=null)
				this.lastSelectedWeaponButton.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 30;");
			

		}catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("some problem happend pal," + e1.getMessage());
		}
		handleGameOver();
	}

	private void handleGameOver() {
		System.out.println("checking if game is over");
		if (battle.isGameOver()) {
			this.isGameOver = true;
			//logic for going to end of game page ("test.fxml") then going to main menu
			System.out.println("game is over");
			this.endTurnButton.setDisable(true);
			this.firstWeaponButton.setDisable(true);
			this.secondWeaponButton.setDisable(true);
			this.thirdWeaponButton.setDisable(true);
			this.fourthWeaponButton.setDisable(true);
			gameOverAnimation();
		}else {
			System.out.println("game is not over yet: " + battle.getLanes());
		}
	}

	private void gameOverAnimation() {
		// TODO Auto-generated method stub
		Label noMoney = new Label("Game Over!!!");
		noMoney.setTextFill(Color.DARKRED);
		noMoney.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, 30));
		noMoney.setLayoutX(this.anchorPane.getPrefWidth()/2 -noMoney.getWidth());


		ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(3));
        scaleTransition.setByX(1.5f);
        scaleTransition.setByY(1.5f);
        scaleTransition.setCycleCount(1);
		TranslateTransition translate = new TranslateTransition(Duration.seconds(3));
		translate.setFromY(this.anchorPane.getHeight());
		translate.setToY(this.anchorPane.getHeight()/2);
		translate.setCycleCount(1);
		
		
		this.anchorPane.getChildren().add(noMoney);
		ParallelTransition pt = new ParallelTransition(noMoney,translate,scaleTransition);
		pt.play();
		pt.setOnFinished(e ->{
			gameOverScene.setScore(battle.getScore());
			gameOverScene.setTurns(battle.getNumberOfTurns());
			main.setScene(gameOverScene);
		});
	}

	public void updateBattleView() {
		fireWeapons();
		drawTitans();
		updateWalls();
		updateLabels();
		

	}

	private void fireWeapons() {
		for (WeaponView w : this.weaponsList ) {
			if (this.weaponLaneMap.get(w).getTitans().isEmpty()) continue;
			w.fire(this.weaponLaneMap.get(w).getTitans(),this.titanMap);
		}
	}



	private void updateWalls() {

		for (Lane l : battle.getOriginalLanes()) {
			Wall laneWall = l.getLaneWall();
			WallView view = wallMap.get(laneWall);
			view.updateHealthBar(laneWall.getCurrentHealth());
			if (laneWall.isDefeated()) {
				
				wallMap.get(laneWall).defeat();
				removeLaneTitans(l.getTitans());
				removeWallWeapons(l);
				System.out.println(" wall " + (battle.getOriginalLanes().indexOf(l)+1)+"is defeted");
				
			}

		}

	}

	private void removeWallWeapons(Lane lane) {
		// TODO Auto-generated method stub
		    int index = battle.getOriginalLanes().indexOf(lane);
		    index += battle.getOriginalLanes().size()==5? 0 : 1;
			tilePanes[index].getChildren().clear();
			this.anchorPane.getChildren().remove(this.boughtWallTrap[index]);
			
			
		
	}

	private void removeLaneTitans(PriorityQueue<Titan> titans) {
		
		while (!titans.isEmpty()) {
			titanMap.get(titans.poll()).defeat(0.0);
		}
	}

	/*
	 * DO We remove titans from defeated lanes once a lane is defeated?
	 * if so do we move titans -> update wall stats -> remove titans?
	 * if that is the case consider firing a running animation once a wall is defeated
	 * removing would be done in update wall method
	 * */

	private void drawTitans() {
		
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
				titanView.walk(0.1);
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
		titanPerTurnLabel.setText("TitansPerTurn: "+battle.getNumberOfTitansPerTurn());
		this.DL1Label.setText(this.battle.getOriginalLanes().size()==5? ""+this.battle.getOriginalLanes().get(0).getDangerLevel():"" );
		this.DL2Label.setText(this.battle.getOriginalLanes().size()==5? ""+this.battle.getOriginalLanes().get(1).getDangerLevel():""+this.battle.getOriginalLanes().get(0).getDangerLevel() );
		this.DL3Label.setText(this.battle.getOriginalLanes().size()==5? ""+this.battle.getOriginalLanes().get(2).getDangerLevel():""+this.battle.getOriginalLanes().get(1).getDangerLevel() );
		this.DL4Label.setText(this.battle.getOriginalLanes().size()==5? ""+this.battle.getOriginalLanes().get(3).getDangerLevel():""+this.battle.getOriginalLanes().get(2).getDangerLevel() );
		this.DL5Label.setText(this.battle.getOriginalLanes().size()==5? ""+this.battle.getOriginalLanes().get(4).getDangerLevel():"");
	}
	
	public void onButtonEntered(MouseEvent event) {
		Button b = (Button)event.getSource();
		b.setStyle("-fx-background-radius: 30; -fx-background-color: #bec626; -fx-border-color:  #e1c642;-fx-border-radius:  30; -fx-border-width: 4;");
	}
	public void onButtonExited(MouseEvent event) {
		Button b = (Button)event.getSource();
		b.setStyle("-fx-background-radius: 30; -fx-background-color: #755a00;-fx-border-color:  #e1c642;-fx-border-radius:  30; -fx-border-width: 4;");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		SettingButton.setOnAction(e ->{
			backRec.toFront();
			SettingContainer.toFront();
			if (this.settingsOpen){
				this.settingsOpen = false; 
				this.endTurnButton.setDisable(false);
				this.firstWeaponButton.setDisable(false);
				this.secondWeaponButton.setDisable(false);
				this.thirdWeaponButton.setDisable(false);
				this.fourthWeaponButton.setDisable(false);
				backRec.setVisible(false);
				SettingContainer.setVisible(false);
			}else {
				this.settingsOpen = true;
				this.endTurnButton.setDisable(true);
				this.firstWeaponButton.setDisable(true);
				this.secondWeaponButton.setDisable(true);
				this.thirdWeaponButton.setDisable(true);
				this.fourthWeaponButton.setDisable(true);
				backRec.setVisible(true);
				SettingContainer.setVisible(true);
			}
			this.selectedWeaponCode = -1;
			
		});
		try {
		Resume.setOnAction(e ->{
			this.settingsOpen = false; 
			this.endTurnButton.setDisable(false);
			this.firstWeaponButton.setDisable(false);
			this.secondWeaponButton.setDisable(false);
			this.thirdWeaponButton.setDisable(false);
			this.fourthWeaponButton.setDisable(false);
			backRec.setVisible(false);
			SettingContainer.setVisible(false);
		});
		
		Music.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				
				soundSystem.setBackgroundVolume(Music.getValue() * 0.01);			
			}
		});
		SFX.valueProperty().addListener(new ChangeListener<Number>() {

			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				
				soundSystem.setSFXVolume(SFX.getValue() * 0.01);			
			}
		});
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		weaponShopInfoButton.setOnAction(new EventHandler<>() {


			@Override
			public void handle(ActionEvent arg0) {
				
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
			
					e.printStackTrace();
					Alert a = new Alert(AlertType.ERROR);
					a.setTitle("Error");
					a.setContentText("failed to show weaponShop info");
					a.show();
				}

			}

		});


	}
	public void setQuitOnMouseClicked(EventHandler<MouseEvent> e) {
		this.Quit.setOnMouseClicked(e);
	}
	




}
