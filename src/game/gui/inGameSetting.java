package game.gui;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
//TODO: Disable Buttons whenever the scene is displayed
public class inGameSetting extends Scene{

	public inGameSetting() {
		super(createRoot());
	}

	private static Button resumeButton = new Button("Resume");
	private static Button quitButton = new Button("Quit");
	private static Slider backgroundSlider = new Slider();
	private static Slider sfxSlider = new Slider();
	private static Font labelFont = new Font("Comic sans MS" , 40);

    public static Parent createRoot() {
        BorderPane root = new BorderPane();
        root.setVisible(true);
        root.setPrefSize(500, 500);
        VBox container = new VBox();
        GridPane content = new GridPane();
        content.setAlignment(Pos.CENTER);
        Label title = new Label("Setting");
        title.setFont(labelFont);
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setCenter(container);

        //buttons
        container.getChildren().addAll(resumeButton , quitButton, content);
        container.setAlignment(Pos.CENTER);container.setSpacing(20);
        tazbeetButton(resumeButton);
        tazbeetButton(quitButton);


        addSliderUI(content , "Music: ", 0 , 100 , 50 , 1);
        addSliderUI(content , "SFX", 0 , 100 , 50 , 2);


        return root;
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

    public static void tazbeetButton(Button button) {
    	button.setPrefSize(300, 100);
		button.setFont(new Font("Comic Sans MS", 50));
    }


}

