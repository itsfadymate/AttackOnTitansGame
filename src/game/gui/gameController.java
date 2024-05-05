package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import game.engine.Battle;
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

public class GameController implements Initializable{
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

	private Battle battle;
	
	public GameController() {
		System.out.println("empty GameView constructor was called");
	}

	public void setBattle(Battle b) {
		try {
			this.battle = b;
			System.out.println("start of gameController initialization");
			Image bgImage = new Image(getClass().getResourceAsStream("Images/threelanes.png"));
			
			if (b.getOriginalLanes().size()==5) {
			   bgImage = new Image(getClass().getResourceAsStream("Images/fivelanes.png"));
			}
			this.bgImage = new ImageView(bgImage);
			System.out.println("loaded bgImage successfully");
			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("could not load image of lanes" + e.getMessage());
		}
		updateLabels();
	}


	public void buyWeaponOne(ActionEvent e) {
		System.out.println("user is trying to buy the first weapon");
	}
	public void onEndTurn(ActionEvent e) {
		System.out.println("EndTurn was clicked");
		battle.passTurn();
		updateBattleView();
		battle.setScore(battle.getScore()+10);
	}
    
	private void updateBattleView() {

		//TODO:drawTitans();
		//TODO: updateWalls();
		updateLabels();
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
