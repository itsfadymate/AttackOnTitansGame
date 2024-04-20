package game.gui;

import javafx.scene.text.Font;

import java.io.File;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.*;

public class AOTMainMenu extends Application {
	public final static double menuWidth = 700;
	public final static double menuHeight = 500;
	private final int buttonWidth = 300;
	private final Font buttonFont = new Font("Arial",20);
	private final Insets defaultInset = new Insets(30, 0, 30, 0);
	private final String imageUrl = "\\wallPaper474by296.png";

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         AOTMainMenu.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		
		
		// TODO Auto-generated method stub
		BorderPane root= new BorderPane();
			BorderPane TopPane = new BorderPane();
				Label Title = new Label("Attack on Titans");
				//Title.setBackground(Background.fill(Color.DARKGREEN));
				Title.setFont(new Font("Arial",24));
			TopPane.setCenter(Title);
			TopPane.setPadding(defaultInset);
			//TopPane.setBackground(Background.fill(Color.RED));
	       
			
				VBox centerPane = new VBox();
					Button PlayButton = createButton("Play");
					Button viewHighScoresButton = createButton("LeaderBoards");
					Button SettingsButton = createButton("Settings");
					Button QuitButton = createButton("Quit");
				centerPane.getChildren().addAll(PlayButton,viewHighScoresButton,SettingsButton,QuitButton);
				//centerPane.setBackground(Background.fill(Color.BLUEVIOLET));
				centerPane.setAlignment(Pos.CENTER);
				centerPane.setSpacing(20);
				
		try {
			Image image = new Image(imageUrl);
			    
				BackgroundImage backgroundImage = new BackgroundImage(image, 
                        BackgroundRepeat.NO_REPEAT, 
                        BackgroundRepeat.NO_REPEAT, 
                        BackgroundPosition.CENTER, 
                        new BackgroundSize(menuWidth, menuHeight, false, false, true, false));
	    
	    root.setBackground(new Background(backgroundImage));
	    }catch(Exception e) {
	    	e.printStackTrace();
	    }
	    root.setTop(TopPane);
		root.setCenter(centerPane);
		
		Scene s = new Scene(root);
		PlayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		viewHighScoresButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				LeaderBoardPage.switchToLeaderBoards(stage,s);
			}
			
		});
		SettingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				settings.switchToSettings(stage, s);
			}
			
		});
		QuitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				stage.close();
			}
			
		});
		
		stage.setHeight(menuHeight);
		stage.setWidth(menuWidth);
		stage.setScene(s);
		stage.setTitle("AttackOnTitans");
		stage.setTitle("Attack On Titans");
		stage.show();
		
	}
	private Button createButton(String buttonTxt) {
		Button b =new Button(buttonTxt);
		b.setPrefWidth(buttonWidth);
		b.setFont(buttonFont);
		return b;
	}
	

}
