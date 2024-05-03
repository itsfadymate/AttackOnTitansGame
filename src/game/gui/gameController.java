package game.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class gameController extends Application {
	private Stage mainstage;

	private final int windowWidth = 1100;
	private final int windowHeight = 715;
	
	private final GameView gameView = new GameView();
    private final AOTMainMenu mainMenu = new AOTMainMenu(windowWidth,windowHeight);
    private final settings setting = new settings(windowWidth,windowHeight);
	

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
				
					//scene = FXMLLoader.load(getClass().getResource("gameView.fxml"));
				
				//mainstage.setScene(gameView);
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
				mainstage.setScene(setting);
			}
			
		});
		mainMenu.setPlayButtonOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mainstage.close();
			}
			
		});

		setting.setBackButtonOnMouseClicked(new EventHandler<>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mainstage.setScene(mainMenu);
			}
			
		});
		
	
		mainstage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
