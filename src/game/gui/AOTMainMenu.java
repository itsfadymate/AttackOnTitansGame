package game.gui;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.geometry.*;

public class AOTMainMenu extends Application {
	
	private static double menuWidth = 1120;
	private static double menuHeight = 728;
	private final Insets defaultInset = new Insets(30, 0, 30, 0);
	private final String backgroundImageUrl = "Images/wallPaper1920by1200.jpg";
    private final String titleImageUrl = "Images/Title4.png";
    private static settings setting = new settings();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
         AOTMainMenu.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		
		
		// TODO Auto-generated method stub
		BorderPane root= new BorderPane();
			
				MenuBox centerPane = new MenuBox();
					MenuItem PlayButton = new MenuItem("Play");
				
					MenuItem viewHighScoresButton =new MenuItem("LeaderBoards");
					MenuItem howToPlayButton = new MenuItem("How to Play?");
					MenuItem SettingsButton = new MenuItem("Settings");
					MenuItem QuitButton = new MenuItem("Quit");
				centerPane.getChildren().addAll(PlayButton,viewHighScoresButton,howToPlayButton,SettingsButton,QuitButton);
				//centerPane.setBackground(Background.fill(Color.BLUEVIOLET));
				centerPane.setAlignment(Pos.CENTER);
				centerPane.setSpacing(20);
				
				BorderPane TopPane = new BorderPane();
				setbackgroundImage(root);
				setTitleImage(TopPane);
		
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
		howToPlayButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				 
			}
			
		});		
		SettingsButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				setting.switchToSettings(stage, s);
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
		stage.setResizable(false);
		stage.show();
		
	}
	public static void setDimensions(double width,double height) {
		menuWidth = width;
		menuHeight = height;
	}
	public  void setbackgroundImage(BorderPane root) {
		try {
            Image bgImage = new Image(getClass().getResourceAsStream(backgroundImageUrl));    
            BackgroundImage backgroundImage = new BackgroundImage(bgImage, 
            	    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
            	    new BackgroundSize(menuWidth, menuHeight, true, true, true, false));
            root.setBackground(new Background(backgroundImage));

	    }catch(Exception e) {
	    	e.printStackTrace();
	    	System.out.println("failed to load background Image");
	    	root.setBackground(Background.fill(Color.RED));
	    }
	}
    public  void setTitleImage(BorderPane TopPane ) {
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
			TopPane.setCenter(Title);
		}
		
    }
	public static double getmenuWidth() {return menuWidth;}
	public static double getmenuHeight() {return menuHeight;}
	
	 private static class MenuBox extends VBox {
	        public MenuBox(MenuItem... items) {
	           // getChildren().add(createSeparator());

	            for (MenuItem item : items) {
	                getChildren().addAll(item, createSeparator());
	            }
	        }

	        private Line createSeparator() {
	            Line sep = new Line();
	            sep.setEndX(200);
	            sep.setStroke(Color.DARKGREY);
	            return sep;
	        }
	  }

	 

    
}
