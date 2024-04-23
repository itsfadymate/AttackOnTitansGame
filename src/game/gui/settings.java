package game.gui;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
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

	
	public static void switchToSettings(Stage stage,Scene previousScene) {
		// TODO Auto-generated method stub
		
		GridPane settings = new GridPane();
			
		
		
		ComboBox<difficulty> difficultyBox= addSelectionUI(settings,"Difficulty: ",FXCollections.observableArrayList(difficulty.Easy,difficulty.Medium,difficulty.Hard),difficulty.Easy,0);	
		ComboBox<Integer> laneBox= addSelectionUI(settings,"Number of lanes: ",FXCollections.observableArrayList(4,5,6,7,8),4,1);	
		ComboBox<String> soundTrackBox = addSelectionUI(settings,"SoundTrack: ",FXCollections.observableArrayList("soundtrack 1","soundtrack 2","soundtrack 3"),"soundTrack 1",2);
		ComboBox<String> resolutionBox = addSelectionUI(settings,"Resolution: ",FXCollections.observableArrayList("700x600","1024x728","1280x728","1360x1080","fullscreen"),"1024x720",3);
		
		
		resolutionBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
		    System.out.print("New selection: " + newValue);
		    
		    System.out.println("	choice: "  );
		    if (resolutionBox.getValue().equals("700x600")) {
		    	AOTMainMenu.setDimensions(700, 600);
		    }else if (resolutionBox.getValue().equals("1024x728")) {
		    	AOTMainMenu.setDimensions(1024, 728);
		    }else if (resolutionBox.getValue().equals("1280x728")) {
		    	AOTMainMenu.setDimensions(1280, 728);
		    }else if (resolutionBox.getValue().equals("1360x1080")) {
		    	AOTMainMenu.setDimensions(1360, 728);
		    }else if (resolutionBox.getValue().equals("fullScreen")) {
		    	//implemnt
		    }
		    System.out.println("updated menu height: " + AOTMainMenu.getmenuHeight() + " updated " + AOTMainMenu.getmenuWidth());
		    }
		   
		);
		settings.setAlignment(Pos.CENTER);
		settings.setPadding(insets);
		settings.setGridLinesVisible(false);

		BorderPane SceneLayout = new BorderPane();
			Button backButton = new Button("Back");
				backButton.setTranslateX(5);
				backButton.setTranslateY(5);
				backButton.setOnMouseClicked( new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				difficultyLevel = difficultyBox.getValue();
				noOfLanes = laneBox.getValue();
				soundTrack = soundTrackBox.getValue();
				stage.setHeight(AOTMainMenu.getmenuHeight());
				stage.setWidth(AOTMainMenu.getmenuWidth());
				stage.setScene(previousScene);
				System.out.println("diff: " + difficultyLevel + " Lane number: " + noOfLanes +  "dimensions: " + stage.getWidth() + "x" + stage.getHeight());
			}

		 });
		   
		SceneLayout.setTop(backButton);
		SceneLayout.setCenter(settings);
		SceneLayout.setBackground(Background.fill(Color.BLUEVIOLET));


		stage.setScene(new Scene(SceneLayout,AOTMainMenu.getmenuWidth(),AOTMainMenu.getmenuHeight()));
		
	}
	private static <T> ComboBox<T>  addSelectionUI(GridPane settings,String labelTxt,ObservableList<T> choices,T defaultValue,int rowNum) {
		Label label = new Label(labelTxt);
		label.setFont(labelFont);
		ComboBox<T> box = new ComboBox<T>();
		box.setItems(choices);
		box.setValue(choices.get(0));		
		settings.add(label,0,rowNum);
		settings.add(box, 1, rowNum);
		return box;
	}
    public static int getnoOfLanes() {return noOfLanes;}
    public static difficulty getDifficulty() { return difficultyLevel;}
    public static String getSoundTrack() {return soundTrack;}
	
}

	


