package game.gui;

import javafx.scene.text.Font;

import java.awt.image.BufferedImage;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.geometry.*;

public class AOTMainMenu extends Application {
	private  static double menuWidth = 1024;
	private static double menuHeight = 728;
	private final int buttonWidth = 300;
	private final Font buttonFont = new Font("Arial",20);
	private final Insets defaultInset = new Insets(30, 0, 30, 0);
	private final String backgroundImageUrl = "Images/wallPaper1920by1200.jpg";
    private final String titleImageUrl = "Images/Title2.png";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         AOTMainMenu.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		
		
		// TODO Auto-generated method stub
		BorderPane root= new BorderPane();
			
				VBox centerPane = new VBox();
					Button PlayButton = createButton("Play");
					Button viewHighScoresButton = createButton("LeaderBoards");
					Button SettingsButton = createButton("Settings");
					Button QuitButton = createButton("Quit");
				centerPane.getChildren().addAll(PlayButton,viewHighScoresButton,SettingsButton,QuitButton);
				//centerPane.setBackground(Background.fill(Color.BLUEVIOLET));
				centerPane.setAlignment(Pos.CENTER);
				centerPane.setSpacing(20);
				
				BorderPane TopPane = new BorderPane();
		try {
              Image bgImage = new Image(getClass().getResourceAsStream(backgroundImageUrl));    
				BackgroundImage backgroundImage = new BackgroundImage(bgImage, 
                        BackgroundRepeat.NO_REPEAT, 
                        BackgroundRepeat.NO_REPEAT, 
                        BackgroundPosition.CENTER, 
                        new BackgroundSize(menuWidth, menuHeight, false, false, true, false));
	    root.setBackground(new Background(backgroundImage));
	    }catch(Exception e) {
	    	e.printStackTrace();
	    	System.out.println("failed to load background Image");
	    	root.setBackground(Background.fill(Color.RED));
	    }
		
		try {
			Image titleImage = new Image(getClass().getResourceAsStream(titleImageUrl));
		
            ImageView titleView = new ImageView();
            titleView.setImage(titleImage);
            
            TopPane.setCenter(titleView);
            TopPane.setTranslateY(50);
            //BorderPane.setAlignment(TopPane, Pos.BASELINE_CENTER);
            
		}catch (Exception e) {
			e.printStackTrace();
			Label Title = new Label("Attack on Titans");
			//Title.setBackground(Background.fill(Color.DARKGREEN));
			Title.setFont(new Font("Arial",24));
			TopPane.setCenter(Title);
			TopPane.setPadding(defaultInset);
			root.setTop(Title);
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
		stage.setResizable(false);
		stage.show();
		
	}
	private Button createButton(String buttonTxt) {
		Button b =new Button(buttonTxt);
		b.setPrefWidth(buttonWidth);
		b.setFont(buttonFont);
		return b;
	}
	public static void setDimensions(double width,double height) {
		menuWidth = width;
		menuHeight = height;
	}
	public static double getmenuWidth() {return menuWidth;}
	public static double getmenuHeight() {return menuHeight;}

    
}
