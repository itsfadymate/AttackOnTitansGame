package game.gui;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
public class settings  {
	private final static Font labelFont = new Font("BOLD",24);
	private final static javafx.geometry.Insets insets = new javafx.geometry.Insets(20,0,20,0);
	private static difficulty difficultyLevel = difficulty.Medium;
	private static int noOfLanes = 4;
	private static String soundTrack = "soundtrack 1";
	private static String backgroundImageURL = "Images/background2.png";

	
	
	

	
	public void switchToSettings(Stage stage,Scene previousScene) {
		// TODO Auto-generated method stub
		
		GridPane settings = new GridPane();
			
		
		
		ComboBox<difficulty> difficultyBox= addSelectionUI(settings,"Difficulty: ",FXCollections.observableArrayList(difficulty.Easy,difficulty.Medium,difficulty.Hard),difficultyLevel,0);	
		ComboBox<Integer> laneBox= addSelectionUI(settings,"Number of lanes: ",FXCollections.observableArrayList(4,5,6,7,8),noOfLanes,1);	
		ComboBox<String> soundTrackBox = addSelectionUI(settings,"SoundTrack: ",FXCollections.observableArrayList("soundtrack 1","soundtrack 2","soundtrack 3"),soundTrack,2);
		
		settings.setAlignment(Pos.CENTER);
		settings.setPadding(insets);
		settings.setGridLinesVisible(false);

		BorderPane SceneLayout = new BorderPane();
			MenuItem backButton = new MenuItem("Back",15,10);
				//backButton.setTranslateX(5);
				//backButton.setTranslateY(5);
			    backButton.setAlignment(Pos.CENTER_LEFT);
			  //  backButton.setPadding(insets);
				backButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				difficultyLevel = difficultyBox.getValue();
				noOfLanes = laneBox.getValue();
				soundTrack = soundTrackBox.getValue();
				stage.setScene(previousScene);
			}

		 });
		
		SceneLayout.setTop(backButton);
		SceneLayout.setCenter(settings);
		try {
			Image bgImage = new Image(getClass().getResourceAsStream(backgroundImageURL));    
            BackgroundImage backgroundImage = new BackgroundImage(bgImage, 
            	    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
            	    new BackgroundSize(AOTMainMenu.getmenuWidth(), AOTMainMenu.getmenuHeight(), true, true, true, false));
            SceneLayout.setBackground(new Background(backgroundImage));
		}catch(Exception e) {
			System.out.println("couldn't find settings background");
		SceneLayout.setBackground(Background.fill(Color.BLUEVIOLET));
		}


		stage.setScene(new Scene(SceneLayout,AOTMainMenu.getmenuWidth(),AOTMainMenu.getmenuHeight()));
		
	}
	private static <T> ComboBox<T>  addSelectionUI(GridPane settings,String labelTxt,ObservableList<T> choices,T defaultValue,int rowNum) {
		Label label = new Label(labelTxt);
		label.setFont(labelFont);
		ComboBox<T> box = new ComboBox<T>();
		box.setItems(choices);
		box.setValue(defaultValue);		
		settings.add(label,0,rowNum);
		settings.add(box, 1, rowNum);
		return box;
	}
    public static int getnoOfLanes() {return noOfLanes;}
    public static difficulty getDifficulty() { return difficultyLevel;}
    public static String getSoundTrack() {return soundTrack;}
   



	
	
}

	


