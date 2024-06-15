package game.gui;

import java.io.FileInputStream;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

public class MainMenuPage extends Scene {

	private final static Insets defaultInset = new Insets(30, 0, 30, 0);
	private final static String backgroundImageUrl = "src/game/gui/Images/background 4-1.png";
    private final static String titleImageUrl = "src/game/gui/Images/Title4.png";

    private static AppButton PlayButton;
    private static AppButton viewHighScoresButton;
    private static AppButton howToPlayButton;
    private static AppButton SettingsButton;
    private static AppButton QuitButton;


	public MainMenuPage(int width,int height) {
		super(createRoot(width,height),width,height);
	}

	private static Parent createRoot(int width,int height) {
		// TODO Auto-generated method stub
		BorderPane root = new BorderPane();
		MenuBox centerPane = new MenuBox();
			PlayButton = new AppButton("Play");
			viewHighScoresButton =new AppButton("LeaderBoards");
		    howToPlayButton = new AppButton("How to Play?");
		    SettingsButton = new AppButton("Settings");
			QuitButton = new AppButton("Quit");
		centerPane.getChildren().addAll(PlayButton,viewHighScoresButton,howToPlayButton,SettingsButton,QuitButton);
		centerPane.setAlignment(Pos.CENTER);
		centerPane.setSpacing(20);

		BorderPane TopPane = new BorderPane();
		setbackgroundImage(root,width,height);
		setTitleImage(TopPane);

		root.setTop(TopPane);
		root.setCenter(centerPane);
		return root;
	}

	public static  void setbackgroundImage(BorderPane root,int width,int height) {
		root.setBackground(Background.fill(Color.BLACK));
		try {
			FileInputStream inStream = new FileInputStream(backgroundImageUrl);
            Image bgImage = new Image(inStream);
            BackgroundImage backgroundImage = new BackgroundImage(bgImage,
            	    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
            	    new BackgroundSize(width, height, false, false, true, true));
            root.setBackground(new Background(backgroundImage));

	    }catch(Exception e) {
	    	e.printStackTrace();
	    	System.out.println("failed to load background Image");
	    	root.setBackground(Background.fill(Color.CYAN));
	    }
	}
    private static  void setTitleImage(BorderPane TopPane ) {
    	try {
    		FileInputStream inStream = new FileInputStream(titleImageUrl);
			Image titleImage = new Image(inStream);

            ImageView titleView = new ImageView();
            titleView.setImage(titleImage);

            TopPane.setCenter(titleView);
            TopPane.setTranslateY(50);


		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("failed to load title Image");
			Label Title = new Label("Attack on Titans");
			//Title.setBackground(Background.fill(Color.DARKGREEN));
			Title.setFont(new Font("Arial",24));
			TopPane.setCenter(Title);
			TopPane.setPadding(defaultInset);
			TopPane.setCenter(Title);
		}

    }

 public void setPlayButtonOnMouseClicked(EventHandler<MouseEvent> e) {
		MainMenuPage.PlayButton.setOnMouseClicked(e);
	}
	public void setleaderBoardsButtonOnMouseClicked(EventHandler<MouseEvent> e) {
		MainMenuPage.viewHighScoresButton.setOnMouseClicked(e);
	}
	public void sethowtoPlayButtonOnMouseClicked(EventHandler<MouseEvent> e) {
		MainMenuPage.howToPlayButton.setOnMouseClicked(e);
	}
	public void setsettingsButtonOnMouseClicked(EventHandler<MouseEvent> e) {
		MainMenuPage.SettingsButton.setOnMouseClicked(e);
	}
	public void setQuitButtonOnMouseClicked(EventHandler<MouseEvent> e) {
		MainMenuPage.QuitButton.setOnMouseClicked(e);
	}

	 private static class MenuBox extends VBox {
	        public MenuBox(AppButton... items) {
	           // getChildren().add(createSeparator());

	            for (AppButton item : items) {
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
