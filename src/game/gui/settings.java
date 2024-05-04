package game.gui;



import java.io.FileInputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
//TODO: fix lanky resizing and image resizing and implement leaderBoards
public class settings extends Scene  {
	private final static Font labelFont = new Font("BOLD",35);
	private final static javafx.geometry.Insets insets = new javafx.geometry.Insets(20,0,20,0);
	private static MenuItem backButton;
	private static difficulty difficultyLevel = difficulty.Hard;
	private static int noOfLanes = 5;
	private static String soundTrack = "soundtrack 1";
	private final static String backgroundImageURL = "src/game/gui/Images/background2.png";
	
	public settings(int width,int height) {
		super(createRoot(width,height),width,height);
	}
	private static Parent createRoot(int width,int height) {
		BorderPane root = new BorderPane();
		GridPane settings = new GridPane();



		ComboBox<difficulty> difficultyBox= addSelectionUI(settings,"Difficulty: ",FXCollections.observableArrayList(difficulty.Easy,difficulty.Hard),difficultyLevel,0);	
		
		ComboBox<String> soundTrackBox = addSelectionUI(settings,"SoundTrack: ",FXCollections.observableArrayList("soundtrack 1","soundtrack 2","soundtrack 3"),soundTrack,2);

		settings.setAlignment(Pos.CENTER);
		settings.setPadding(insets);
		settings.setGridLinesVisible(false);

		
		 backButton = new MenuItem("Back",35,10);
		
		backButton.setAlignment(Pos.CENTER_LEFT);
		
		root.setTop(backButton);
		root.setCenter(settings);
		try {
			Image bgImage = new Image(new FileInputStream(backgroundImageURL));    
			BackgroundImage backgroundImage = new BackgroundImage(bgImage, 
					BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
					new BackgroundSize(width,height, false, false, true, true));
			root.setBackground(new Background(backgroundImage));
		}catch(Exception e) {
			System.out.println("couldn't find settings background");
			root.setBackground(Background.fill(Color.BLUEVIOLET));
		}
		
		return root;
	
	}
	
	
	public void setBackButtonOnMouseClicked(EventHandler<MouseEvent> e) {
		this.backButton.setOnMouseClicked(e);
	}
	
	
	private static <T> ComboBox<T>  addSelectionUI(GridPane settings,String labelTxt,ObservableList<T> choices,T defaultValue,int rowNum) {
		Label label = new Label(labelTxt);
		label.setFont(labelFont);
		label.setPrefSize(200, 50);
		ComboBox<T> box = new ComboBox<T>();
		box.setItems(choices);
		box.setValue(defaultValue);	
		box.setPrefSize(200, 50);
		
		settings.add(label,0,rowNum);
		settings.add(box, 1, rowNum);
		return box;
	}
    public  int getnoOfLanes() {return noOfLanes;}
    public  difficulty getDifficulty() { return difficultyLevel;}
    public  String getSoundTrack() {return soundTrack;}
   



	
	
}

	


