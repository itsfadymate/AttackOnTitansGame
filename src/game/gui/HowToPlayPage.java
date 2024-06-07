package game.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;





public class HowToPlayPage extends Scene{
	
	private static Button mainMenu = new Button("Main Menu");
	private static Button switchToRoot2 = new Button("Next");
//	private static Button back = new Button(); 
	private static Button switchToRoot1 = new Button("Back");
	private static Button switchToRoot3 = new Button("Next");
//	private static Button next = new Button();
	private static Button go = new Button("Let's go"); // play the game
	private int windowWidth;
	private int windowHeight;
	
	static Image[] images = {new Image(HowToPlayPage.class.getResourceAsStream("Images/TitansInfo.jpeg")),
			new Image(HowToPlayPage.class.getResourceAsStream("Images/weaponShop.png")),
			new Image(HowToPlayPage.class.getResourceAsStream("Images/HTP1.png")),
			new Image(HowToPlayPage.class.getResourceAsStream("Images/HTP2.png")),
			new Image(HowToPlayPage.class.getResourceAsStream("Images/HTP3.png")),
			new Image(HowToPlayPage.class.getResourceAsStream("Images/HTP4.png")),
			new Image(HowToPlayPage.class.getResourceAsStream("Images/HTP5.png")),
			new Image(HowToPlayPage.class.getResourceAsStream("Images/HTP6.png")),
			new Image(HowToPlayPage.class.getResourceAsStream("Images/HTP1.png"))
			};

	private static String[] text = {"The game consist of 3 or 5 lanes (depends on which difficulty you choose)\r\n" + "There are four types of titans that will attack your wall:\r\n",
		 		"Of course you have weapons to defend them. Each weapon has its cost, damage and the number of titans it can attack at once\r\n" + "There are four types of weapons you have to defend them:\r\n",
		 		"This is the main screen when you first enter the game",
		 		"To place a weapon you need to choose the type of the weapon you want just left click on it and there will a blue border around the weapon you selected just as shown in the below figure",
		 		"To place it you will need to choose which lane to place it in. So select the lane you want then you left click on it to place the weapon the lane that you choose will be highlighted by blue just as the figure below: ",
		 		"After the weapon is deployed it will appear on the wall like that:",
		 		"After a long fight with the titans you may run out of resources and you can’t place any weapons for this turn. What to do in this case?",
		 		"Just press the end turn button on the bottom left corner and leave it for your defenses to do the work for you ;)",
		 		"The green Labels are the danger levels of each lane.\nThe higher the danger level the more titans there are"
		 		};
	
	private static String[] text2 = {"",
				"You collect resources by defeating a titan so try to defeat them as fast as possible to collect more resources :)",
				"",
				"Take care! You can place just one weapon once a turn \n So choose wisely ;)",
				"",
				"",
				"",
				"",
				"Titans spawn into the lanes with the least danger level"};
	
	private static int Imagecounter = 0;
	
	public HowToPlayPage(int width, int height) {
		super(createRoot1(width , height),width,height);
		windowWidth = width;
		windowHeight = height;
		switchToRoot1.setOnAction(e ->{
        	this.setRoot(createRoot1(width, height));
        });
		switchToRoot2.setOnAction(e ->{
        	this.setRoot(createRoot2(width, height));
        });
		switchToRoot3.setOnAction(e ->{
        	this.setRoot(createRoot3(width, height));
        });
	}
	
	public static Parent createRoot1(int width , int height) {
		StackPane root = new StackPane();
        BorderPane content = new BorderPane();
        BorderPane bottomroot = new BorderPane();
        StackPane stackTitle = new StackPane();
        StackPane btmright = new StackPane();
        StackPane btmleft = new StackPane();
        Label title = new Label("Welcome to Attack on Titans: \n Utopia");
        title.setWrapText(true);
        title.setFont(new Font("Comic Sans MS", 72));
        title.setTextAlignment(TextAlignment.CENTER);
        Label subtitle = new Label("The titans are attacking our city but luckily we have a wall that separates them from us. Your mission is to defend them for as long as possible.");
        subtitle.setFont(new Font("Comic Sans MS", 42));
        subtitle.setTextAlignment(TextAlignment.CENTER);
        subtitle.setPrefSize(width-100, height);
        subtitle.setWrapText(true);
        
        
        switchToRoot2.setPrefSize(250, 100);
        switchToRoot2.setFont(new Font("Comic Sans MS", 32));
			
	
//        mainMenu = new Button("Back");
        mainMenu.setPrefSize(250, 100);
        mainMenu.setFont(new Font("Comic Sans MS", 32));
        Image bgImage = new Image(HowToPlayPage.class.getResourceAsStream("Images/wallPaper1920by1200.jpg"));
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(width);
        bgImageView.setFitHeight(height);
        bgImageView.setOpacity(0.5);  // Set opacity to 50%
        bottomroot.setPrefSize(width, 200);
        btmright.setPrefSize(350, 200);
        btmleft.setPrefSize(350, 200);
        btmright.setAlignment(Pos.CENTER);
        btmleft.setAlignment(Pos.CENTER);
        content.setBottom(bottomroot);
        bottomroot.setLeft(btmleft);
        bottomroot.setRight(btmright);
        stackTitle.getChildren().add(title);
        btmright.getChildren().add(switchToRoot2);
        btmleft.getChildren().add(mainMenu);
        content.setTop(stackTitle);
        content.setCenter(subtitle);
        switchToRoot2.setBlendMode(BlendMode.MULTIPLY);
        mainMenu.setBlendMode(BlendMode.MULTIPLY);

        root.getChildren().addAll(bgImageView, content);

        return root;
		
	}
	
	
	
	public void setmainMenuOnMouseClicked(EventHandler<MouseEvent> e) {
		this.mainMenu.setOnMouseClicked(e);
	}
	
	public void setGoOnMouseClicked (EventHandler<MouseEvent> e) {
		this.go.setOnMouseClicked(e);
	}

	
	public Parent createRoot2(int width , int height) {
		// Create a new StackPane as the root
		StackPane root = new StackPane();

		// Create a BorderPane for the content
		BorderPane contentPane = new BorderPane();
		StackPane top = new StackPane();
		BorderPane bottomroot = new BorderPane(); 
		StackPane btmright = new StackPane(); btmright.setPrefSize(350, 150);
		StackPane btmleft = new StackPane(); btmleft.setPrefSize(350, 150);
		StackPane btmcenter = new StackPane(); btmcenter.setPrefSize(350, 150);
		ImageView centerImage = new ImageView(images[Imagecounter]);
		centerImage.setFitHeight(300);
		centerImage.setPreserveRatio(true);
		VBox centerBox = new VBox(centerImage);
		centerBox.setMaxHeight(250);  // Adjust this value as needed
		centerBox.setAlignment(Pos.CENTER);
		Label label1 = new Label(text[Imagecounter]);
		contentPane.setCenter(centerBox);
		label1.setWrapText(true);
		label1.setPrefSize(width-100, 200);
		label1.setFont(new Font("Comic Sans MS", 32));
		label1.setTextAlignment(TextAlignment.CENTER);
		Label label2 = new Label(text2[Imagecounter]);
		label2.setFont(new Font("Comic Sans MS", 24));
		label2.setTextAlignment(TextAlignment.CENTER);
		label2.setPrefSize(width-100, 150);
		label2.setWrapText(true);
		contentPane.setTop(top); // top stack pane
		top.getChildren().add(label1); // put the label in it
		btmcenter.getChildren().add(label2);
		contentPane.setBottom(bottomroot); // bottom border pane
		bottomroot.setCenter(btmcenter);
		bottomroot.setLeft(btmleft);
		bottomroot.setRight(btmright);

		Button next = new Button("Next");
		next.setPrefSize(150, 100);
		next.setFont(new Font("Comic Sans MS", 32));

		next.setOnAction(e ->{
			Imagecounter = Imagecounter + 1 ;
		    if(Imagecounter >= 0 && Imagecounter < text.length)
		    	updateUI(centerImage, label1, label2);
		    else
		    	this.setRoot(createRoot3(width, height));
		});

		Button back = new Button("Back");
		back.setPrefSize(150, 100);
		back.setFont(new Font("Comic Sans MS", 32));
		back.setOnAction(e ->{
		    Imagecounter = Imagecounter - 1 ;
		    if(Imagecounter >= 0 && Imagecounter < text.length)
		    	updateUI(centerImage, label1, label2);
		    else {
		    	Imagecounter = 0;
		    	this.setRoot(createRoot1(width, height));
		    }
		});

		btmright.getChildren().add(next);
		btmleft.getChildren().add(back); // put the buttons in their place

		btmleft.setPrefSize(360, 200);
		btmright.setPrefSize(360, 200);

		// Load the image
		Image bgImage = new Image(HowToPlayPage.class.getResourceAsStream("Images/background3.PNG"));

		// Create an ImageView
		ImageView bgImageView = new ImageView(bgImage);
		bgImageView.setFitWidth(width);
		bgImageView.setFitHeight(height);
		bgImageView.setPreserveRatio(true);

		// Set the opacity
		bgImageView.setOpacity(0.5);

		// Create a new StackPane to hold the ImageView
		StackPane bgPane = new StackPane();
		bgPane.getChildren().add(bgImageView);

		// Add the bgPane and contentPane to the root StackPane
		root.getChildren().addAll(bgPane, contentPane);

		btmright.setAlignment(Pos.CENTER);
		btmleft.setAlignment(Pos.CENTER);
		next.setBlendMode(BlendMode.MULTIPLY);
		back.setBlendMode(BlendMode.MULTIPLY);

		return root;
		}

	private static void updateUI(ImageView centerImage, Label label1, Label label2) {
	    centerImage.setImage(images[Imagecounter]);
	    label1.setText(text[Imagecounter]);
	    label2.setText(text2[Imagecounter]);
	}
	
	public static Parent createRoot3(int width, int height) {
		Imagecounter = 0;
		StackPane root = new StackPane();
        BorderPane content = new BorderPane();

        Label label = new Label("That's all you need to play this game :) \n Let’s go and takedown those titans");
        label.setWrapText(true);
        label.setFont(new Font("Comic Sans MS", 32));
        label.setTextAlignment(TextAlignment.CENTER);

//        go = new Button("Let's go");
        go.setPrefSize(300, 100);
        go.setFont(new Font("Comic Sans MS", 28));

//        mainMenu = new Button("Nah, Maybe later");
        mainMenu.setPrefSize(300, 100);
        mainMenu.setFont(new Font("Comic Sans MS", 28));

        VBox centerBox = new VBox(label);
        centerBox.setPrefHeight(300);  // Adjust this value as needed
        centerBox.setAlignment(Pos.CENTER);
        content.setTop(centerBox);
        

        HBox buttonHolder = new HBox(mainMenu, go);
        buttonHolder.setSpacing(200);
        HBox.setMargin(go, new Insets(120));  // Add margin to the 'go' button
        HBox.setMargin(mainMenu, new Insets(120));  // Add margin to the 'mainMenu' button

        go.setBlendMode(BlendMode.MULTIPLY);
        mainMenu.setBlendMode(BlendMode.MULTIPLY);

        content.setCenter(buttonHolder);

        Image bgImage = new Image(HowToPlayPage.class.getResourceAsStream("Images/background6.jpg"));
        ImageView bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(width);
        bgImageView.setFitHeight(height);
        bgImageView.setOpacity(0.5);  // Set opacity to 50%

        root.getChildren().addAll(bgImageView, content);

        return root;
	}
	
	
	
	
}

