package game.gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;





public class HowToJouerPage extends Scene{

	private static Button root1BackButton = new Button("Back");
	private static Button root1NextButton = new Button("Next");

	private static Button root2BackButton = new Button("Back");
	private static Button root2NextButton = new Button("Next");

	private static Button root3BackButton = new Button("Main Menu");
	private static Button root3NextButton = new Button("let's go"); // play the game


	private static Image[] images= new Image[]{new Image(HowToJouerPage.class.getResourceAsStream("Images/TitansInfo.png")),
			new Image(HowToJouerPage.class.getResourceAsStream("Images/weaponShop.png")),
			new Image(HowToJouerPage.class.getResourceAsStream("Images/HTP1.png")),
			new Image(HowToJouerPage.class.getResourceAsStream("Images/HTP2.png")),
			new Image(HowToJouerPage.class.getResourceAsStream("Images/HTP3.png")),
			new Image(HowToJouerPage.class.getResourceAsStream("Images/HTP4.png")),
			new Image(HowToJouerPage.class.getResourceAsStream("Images/HTP5.png")),
			new Image(HowToJouerPage.class.getResourceAsStream("Images/HTP6.png"))}; ;

	private static String[] text = {"The game consist of 3 or 5 lanes (depends on which difficulty you choose)\r\n" + "There are four types of titans that will attack your wall:\r\n",
			"Of course you have weapons to defend them. Each weapon has its cost, damage and the number of titans it can attack at once\r",
			"This is the main screen when you first enter the game",
			"To place a weapon you need to choose the type of the weapon you want just left click on it and there will a blue border around the weapon you selected just as shown in the below figure (Take care! You can place just one weapon once a turn)",
			"To place it you will need to choose which lane to place it in. So select the lane you want then you left click on it to place the weapon the lane that you choose will be highlighted by blue just as the figure below: ",
			"After the weapon is deployed it will appear on the wall like that:",
			"After a long fight with the titans you may run out of resources and you can’t place any weapons for this turn. What to do in this case?",
	"Just press the end turn button on the bottom left corner and leave it for your defenses to do the work for you ;)"};

	private static String[] text2 = {"",
			"You collect resources by defeating a titan so try to defeat them as fast as possible to collect more resources :)",
			"",
			"",
			"",
			"",
			"",
	""};

	private static int imageCounter = 0;
	private static ImageView root2Image = new ImageView(images[0]);
	private static Label root2TopLabel = new Label(text[0]);
	private static Label root2BottomLabel = new Label(text2[0]);

	public HowToJouerPage(int width, int height) {
		super(createRoot1(width , height),width,height);
		
		

		root1NextButton.setOnAction(e ->{
			this.setRoot(createRoot2(width, height));
		});
		root2NextButton.setOnAction(e ->{
			imageCounter++;
			if (imageCounter >= 0 && imageCounter< text.length ) {
				
				root2Image.setImage(images[imageCounter]);
				root2TopLabel.setText(text[imageCounter]);
				root2BottomLabel.setText(text2[imageCounter]);

			}else  {
				this.setRoot(createRoot3(width, height));
				imageCounter = 0;
				root2Image.setImage(images[0]);
				root2TopLabel.setText(text[0]);
				root2BottomLabel.setText(text2[0]);
			}
		});
		root2BackButton.setOnAction(e ->{
			imageCounter--;
			if (imageCounter >= 0 && imageCounter< text.length ) {
				root2Image.setImage(images[imageCounter]);
				root2TopLabel.setText(text[imageCounter]);
				root2BottomLabel.setText(text2[imageCounter]);

			}else {
				this.setRoot(createRoot1(width, height));
			}
		});
		setButtonAttributes(root1BackButton);
		setButtonAttributes(root1NextButton);
		setButtonAttributes(root2BackButton);
		setButtonAttributes(root2NextButton);
		setButtonAttributes(root3BackButton);
		setButtonAttributes(root3NextButton);
		

		
	}
	private static void setButtonAttributes(Button b) {
		b.setPrefSize(150, 100);
		b.setFont(Font.font("Comic Sans MS", 32));
		b.setBlendMode(BlendMode.MULTIPLY);
	}
    private static void setLabelAttributes(Label l,String font,int fontSize) {
    	l.setFont( Font.font(font,FontWeight.BOLD, fontSize));
		l.setTextAlignment(TextAlignment.CENTER);
		//l.setPrefSize(width-100, height);
		l.setWrapText(true);
    }

	public static Parent createRoot1(int width , int height) {
		StackPane root = new StackPane();
		BorderPane content = new BorderPane();
		BorderPane bottomroot = new BorderPane();
		StackPane stackTitle = new StackPane();
		StackPane btmright = new StackPane();
		StackPane btmleft = new StackPane();
		Label title = new Label("Welcome to Attack on Titans: \n Utopia");
		setLabelAttributes(title,"Comic Sans MS",72);
		Label subtitle = new Label("The titans are attacking our city but luckily we have a wall that separates them from us. Your mission is to defend them for as long as possible.");
		setLabelAttributes(subtitle,"Comic Sans MS",42);


		Image bgImage = new Image(HowToJouerPage.class.getResourceAsStream("Images/wallPaper1920by1200.jpg"));
		ImageView bgImageView = new ImageView(bgImage);
		bgImageView.setFitWidth(width);
		bgImageView.setFitHeight(height);
		bgImageView.setOpacity(0.5);  // Set opacity to 50%
		bottomroot.setPrefSize(width, 200);
		btmright.setPrefSize(350, 200);
		btmleft.setPrefSize(350, 200);
		content.setBottom(bottomroot);
		bottomroot.setLeft(btmleft);
		bottomroot.setRight(btmright);
		stackTitle.getChildren().add(title);
		btmright.getChildren().add(root1NextButton);
		btmleft.getChildren().add(root1BackButton);
		content.setTop(stackTitle);
		content.setCenter(subtitle);
		root.getChildren().addAll(bgImageView, content);

		return root;

	}


	public Parent createRoot2(int width, int height) {
		root2Image.setPreserveRatio(true);
		root2Image.setFitWidth(width/2);
		root2BottomLabel.setPrefWidth(width/2);
		BorderPane root = new BorderPane();
		root.setPrefWidth(width);
		root.setPrefHeight(height);
		Image bgImage = new Image(HowToJouerPage.class.getResourceAsStream("Images/background3.PNG"));
	   ImageView bg = new ImageView(bgImage);
	   bg.setFitHeight(height);
	   bg.setFitWidth(width);
	   bg.setOpacity(0.5);
	   root.getChildren().add(bg);
        
			
			StackPane btmLeft = new StackPane();
				btmLeft.setPrefWidth(width/4);
				btmLeft.getChildren().add(root2BackButton);
			StackPane btmRight = new StackPane();
				btmRight.setPrefWidth(width/4);
				btmRight.getChildren().add(root2NextButton);
			HBox btmPane = new HBox();
				btmPane.setPrefHeight(200);
			    btmPane.setAlignment(Pos.CENTER);
			 	btmPane.getChildren().addAll(btmLeft,root2BottomLabel,btmRight);
		root.setBottom(btmPane);
	    root.setCenter(root2Image);
	    	
	    root.setTop(root2TopLabel);	     
	    root.setAlignment(root2TopLabel,Pos.CENTER );
	    setLabelAttributes(root2TopLabel,"Comic Sans MS",28);
	    setLabelAttributes(root2BottomLabel,"Comic Sans MS",28);
		
		return root;
	}

	public static Parent createRoot3(int width, int height) {
		StackPane root = new StackPane();
		BorderPane content = new BorderPane();

		Label label = new Label("That's all you need to play this game :) \n Let’s go and takedown those titans");
		label.setWrapText(true);
		label.setFont(new Font("Comic Sans MS", 32));
		label.setTextAlignment(TextAlignment.CENTER);

		

		VBox centerBox = new VBox(label);
		centerBox.setPrefHeight(300);  // Adjust this value as needed
		centerBox.setAlignment(Pos.CENTER);
		content.setTop(centerBox);
        root3NextButton.setPrefWidth(350);
		root3BackButton.setPrefWidth(350);
		HBox buttonHolder = new HBox(root3BackButton, root3NextButton);
		buttonHolder.setSpacing(200);
		HBox.setMargin(root3NextButton, new Insets(80));  // Add margin to the 'go' button
		HBox.setMargin(root3BackButton, new Insets(80));  // Add margin to the 'mainMenu' button

		root3NextButton.setBlendMode(BlendMode.MULTIPLY);
		root1BackButton.setBlendMode(BlendMode.MULTIPLY);

		content.setCenter(buttonHolder);

		Image bgImage = new Image(HowToJouerPage.class.getResourceAsStream("Images/background6.jpg"));
		ImageView bgImageView = new ImageView(bgImage);
		bgImageView.setFitWidth(width);
		bgImageView.setFitHeight(height);
		bgImageView.setOpacity(0.5);  // Set opacity to 50%

		root.getChildren().addAll(bgImageView, content);

		return root;
	}


	public void setRoot1BackbuttonOnMouseClicked(EventHandler<MouseEvent> e) {
		root1BackButton.setOnMouseClicked(e);
	}

	public void setRoot3NextButtonMouseClicked (EventHandler<MouseEvent> e) {
		root3NextButton.setOnMouseClicked(e);
	}
	public void setRoot3BackButtonMouseClicked (EventHandler<MouseEvent> e) {
		root3BackButton.setOnMouseClicked(e);
	}

}

