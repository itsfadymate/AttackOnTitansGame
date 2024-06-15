package game.gui;



import java.io.FileInputStream;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
//TODO: implement leaderBoards
public class SettingsPage extends Scene  {
	private final static Font labelFont = new Font("BOLD",35);
	private final static javafx.geometry.Insets insets = new javafx.geometry.Insets(20,0,20,0);
	private static AppButton backButton;
	private static difficulty difficultyLevel = difficulty.Hard;
	private static int noOfLanes = 5;
	private static String soundTrack = "soundtrack 1";
	private final static String backgroundImageURL = "src/game/gui/Images/background2.png";
	private static ComboBox<difficulty> difficultyBox;
	private static ComboBox<String> soundTrackBox;
	private static Slider BackgroundMusicVolume;
	private static Slider SFXVolume;

	public SettingsPage(int width,int height) {
		super(createRoot(width,height),width,height);
	}
	private static Parent createRoot(int width,int height) {
		BorderPane root = new BorderPane();
		GridPane settings = new GridPane();



		 difficultyBox= addSelectionUI(settings,"Difficulty: ",FXCollections.observableArrayList(difficulty.Easy,difficulty.Hard),difficultyLevel,0);

		 soundTrackBox = addSelectionUI(settings,"SoundTrack: ",FXCollections.observableArrayList("soundtrack 1","soundtrack 2","soundtrack 3"),soundTrack,2);

		 BackgroundMusicVolume = addSliderUI(settings, "Music Volume: ", 0, 100, 50, 4);

		 BackgroundMusicVolume.valueProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

					soundSystem.setBackgroundVolume(BackgroundMusicVolume.getValue() * 0.01);
				}
			});

//	     SFXVolume = addSliderUI(settings, "SFX Volume: ", 0, 100, 50, 5);
//	     SFXVolume.valueProperty().addListener(new ChangeListener<Number>() {
//
//				public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
//
//					soundSystem.setSFXVolume(SFXVolume.getValue() * 0.01);
//				}
//			});

		settings.setAlignment(Pos.CENTER);
		settings.setPadding(insets);
		settings.setGridLinesVisible(false);


		 backButton = new AppButton("Back",100,40);

		backButton.setAlignment(Pos.CENTER);
		HBox topLayout = new HBox();
		topLayout.setPadding(new Insets(20,20,20,20));
		topLayout.setAlignment(Pos.BOTTOM_LEFT);
		topLayout.getChildren().add(backButton);

		root.setTop(topLayout);
		root.setCenter(settings);
		setbgImage(width, height, root);

		return root;

	}
	private static void setbgImage(int width, int height, BorderPane root) {
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
	}


	public void setBackButtonOnMouseClicked(EventHandler<MouseEvent> e) {
		difficultyLevel = difficultyBox.getValue();
		soundTrack = soundTrackBox.getValue();
		SettingsPage.backButton.setOnMouseClicked(e);
	}


	private static <T> ComboBox<T>  addSelectionUI(GridPane settings,String labelTxt,ObservableList<T> choices,T defaultValue,int rowNum) {
		Label label = new Label(labelTxt);
		label.setFont(labelFont);
		label.setPrefSize(250, 50);
		ComboBox<T> box = new ComboBox<>();
		box.setItems(choices);
		box.setValue(defaultValue);
		box.setPrefSize(200, 50);

		settings.add(label,0,rowNum);
		settings.add(box, 1, rowNum);
		box.setBlendMode(BlendMode.MULTIPLY);
		return box;
	}
	private static Slider addSliderUI(GridPane settings, String labelTxt, double min, double max, double defaultValue, int rowNum) {
        Label label = new Label(labelTxt);
        label.setFont(labelFont);
        label.setPrefSize(250, 50);
        Slider slider = new Slider(min, max, defaultValue);
        slider.setPrefSize(200, 50);

        settings.add(label, 0, rowNum);
        settings.add(slider, 1, rowNum);
        return slider;
    }
    public  static int getnoOfLanes() {
       if (difficultyBox.getValue()==difficulty.Hard) {
		return 5;
	}
       return 3;
    }
    public static  int getInitialResourcesperLane() {
    	 if (difficultyBox.getValue()==difficulty.Hard) {
			return 125;
		}
         return 250;
    }

    public  String getSoundTrack() {return soundTrackBox.getValue();}
    public double getBackgroundVolume() {return BackgroundMusicVolume.getValue();}
    public double getSFXVolume() {return SFXVolume.getValue();}






}




