package game.gui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;


public class playerInfo extends Scene {

	public playerInfo(Stage mainStage , int width , int height) throws Exception {
		super(createRoot(mainStage , width , height));
		System.out.println("player Info activated!!!!");
		// TODO Auto-generated constructor stub
	}

	private static final String CSV_FILE_PATH = "players.csv";
	private static TextField nameInput;
	private static Label nameLabel;
	private static ProgressBar progressBar;
	private static ImageView loadingImage;
	private static Button submitButton;
	private static String playerName;


	public static Parent createRoot(Stage primaryStage,int width, int height) throws Exception {
		AnchorPane root = new AnchorPane();

		// Load the image using a properly formatted file URI
		String imagePath = "Images/background 2-a.png";
		Image backgroundImage = null;
		try {
			backgroundImage = new Image(playerInfo.class.getResourceAsStream(imagePath));


			// Create a BackgroundImage object
			BackgroundImage background = new BackgroundImage(
					backgroundImage,
					BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT,
					BackgroundPosition.DEFAULT,
					new BackgroundSize(
							BackgroundSize.AUTO,
							BackgroundSize.AUTO,
							false, false, true, true));

			// Set the background to the AnchorPane
			root.setBackground(new Background(background));

			// Create UI elements for player name input
			nameInput = new TextField();
			nameInput.setPromptText("Enter player name");
			nameInput.setBlendMode(BlendMode.OVERLAY);
			submitButton = new Button("Submit");
			submitButton.setPrefSize(100, 40);
			nameLabel = new Label("Please enter the Player name");
			nameLabel.setFont(new Font("Stencil", 30));
			nameLabel.setTextFill(Color.WHITE);

			// Create loading elements
			progressBar = new ProgressBar(0);
			progressBar.setPrefWidth(600); // Wider progress bar
			progressBar.setVisible(false); // Initially hidden
			progressBar.setBlendMode(BlendMode.OVERLAY);
			progressBar.setPadding(new Insets(100,0,0,0));
			Image loadingImageContent = new Image(playerInfo.class.getResourceAsStream("Images/levi .png"));
			loadingImage = new ImageView(loadingImageContent);
			loadingImage.setFitWidth(50); // Smaller image width
			loadingImage.setFitHeight(50); // Smaller image height
			loadingImage.setVisible(false); // Initially hidden


			submitButton.setBlendMode(BlendMode.DIFFERENCE);
			submitButton.setOnMouseClicked(event -> {
				String localPlayerName = "";
				localPlayerName = nameInput.getText().trim(); // Trim whitespace
				if (!(localPlayerName.length() == 0)) { // Check if input is not empty
					System.out.println("Player Name to be written: " + localPlayerName); // Debugging statement
					// Write the player's name to a CSV file
					playerName = localPlayerName;
					// Show loading elements and start loading animation
					nameInput.setDisable(true);
					submitButton.setDisable(true);
					nameLabel.setDisable(true);
					progressBar.setVisible(true);
					loadingImage.setVisible(true);
					startLoadingAnimation(primaryStage);
				} else {
					nameLabel.setText("Don't skip :)");
					nameInput.clear(); // Clear the input field
				}
			});

			// Layout for input and label
			VBox vbox = new VBox(10, nameLabel, nameInput, submitButton, new StackPane(progressBar, loadingImage));
			vbox.setAlignment(Pos.CENTER);
			vbox.setPrefWidth(600); // Adjusted to match progressBar width
			vbox.setLayoutX(340); // Adjusted for centering (1280 - 600) / 2 = 340
			vbox.setLayoutY(300); // Adjusted for better vertical alignment

			root.getChildren().add(vbox);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return root;
	}

	private static void writePlayerNameToCSV(String playerName, int score) {
		System.out.println("Writing to CSV file..."); // Debugging statement
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
			writer.write(playerName + "," + score);
			writer.newLine();
			System.out.println("Successfully wrote to CSV file."); // Debugging statement
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getPlayerName() {
		return playerName;
	}
	public static ProgressBar getProgressBar() {
		return progressBar;
	}


	private static void startLoadingAnimation(Stage primaryStage) {
		Timeline timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(Duration.millis(200), event -> {
			double progress = progressBar.getProgress();
			if (progress < 1.0) {
				progressBar.setProgress(progress + 0.05);
				loadingImage.setTranslateX((progress * progressBar.getPrefWidth())-280); // Slide image along with the progress bar
			} else {
				timeline.stop();
			}
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}



}
