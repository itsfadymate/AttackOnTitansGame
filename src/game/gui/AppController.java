package game.gui;

import java.io.IOException;

import game.engine.Battle;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppController extends Application {
	private Stage mainstage;

	private final int windowWidth = 1280;
	private final int windowHeight = 760;
	
	private GameController gameController; 
    private final MainMenuPage mainMenu = new MainMenuPage(windowWidth,windowHeight);
    private final SettingsPage setting = new SettingsPage(windowWidth,windowHeight);
	

	@Override
	public void start(Stage arg0) throws Exception {
		
		mainstage = new Stage();
		mainstage.setHeight(windowHeight);
		mainstage.setWidth(windowWidth);
		mainstage.setScene(mainMenu);
		mainstage.setTitle("AttackOnTitans");
		mainstage.setResizable(false);
		
		// set main menu button functionalities
		mainMenu.setPlayButtonOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				startGame();
				
			}
	                
			
		});
		mainMenu.setleaderBoardsButtonOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
				
			}
			
		});
		mainMenu.sethowtoPlayButtonOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		mainMenu.setsettingsButtonOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				//mainstage.setHeight(1280);
				//mainstage.setWidth(720);
				mainstage.setScene(setting);
				
			}
			
		});
        mainMenu.setQuitButtonOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mainstage.close();
			}
			
		});
       setting.setBackButtonOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mainstage.setScene(mainMenu);
			}
			
		});
		
	
		mainstage.show();
	}
	private void startGame() {
		// TODO Auto-generated method stub
		try {
			int spawnDistance = (int)(TitanView.getTitanPixelSpawnDistance() - WallView.getWallOuterBoundary())/10;
			System.out.println(SettingsPage.getnoOfLanes());
			Battle b = new Battle(0,0,spawnDistance,SettingsPage.getnoOfLanes(),SettingsPage.getInitialResourcesperLane());
			
			
			Scene gameScene = new Scene(CreateRoot());
			this.gameController.initialize(b);
			mainstage.setScene(gameScene);
		
			/*
			 * crashes the game
			 * double updateInterval = 1000000000 * 25; double delta = 0; long lastTime =
			 * System.nanoTime(); long currentTime; while (!b.isGameOver()) { currentTime =
			 * System.nanoTime(); delta+= (currentTime - lastTime) / updateInterval; if
			 * (delta < 1) continue;
			 * 
			 * b.passTurn(); this.gameController.updateBattleView(); delta--; }
			 */
			
			
			
		
		}catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("IO Problem");
			alert.setContentText("Oops...The csv file cannot be read!");
			alert.show();
		}
	}
	private Parent CreateRoot() {
		// TODO Auto-generated method stub
		
		Parent root;
        try {
        	FXMLLoader loader = new FXMLLoader(GameController.class.getResource("StartGame.fxml"));
			root = loader.load();
			this.gameController = loader.getController();
			
			System.out.println("pas de problem");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("LoadFailure");
			alert.setContentText("It seems like the was a problem loading the game fxml files");
			alert.show();
			
			try {
				root = FXMLLoader.load(GameController.class.getResource("test.fxml"));
				System.out.println("failure loading StartGame.FXML" + e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				root = new BorderPane();
				System.out.println("failure loading text.FXML" + e1.getMessage());
			}
			
		}
		return root;
        }
	public static void main(String[] args) {
		System.out.println("started app");
		AppController.launch(args);
	}

}
