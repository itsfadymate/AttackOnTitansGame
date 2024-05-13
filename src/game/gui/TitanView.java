package game.gui;

import game.engine.titans.AbnormalTitan;
import game.engine.titans.ArmoredTitan;
import game.engine.titans.ColossalTitan;
import game.engine.titans.PureTitan;
import game.engine.titans.Titan;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.skin.ProgressBarSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class TitanView extends View {

	private final static int healthBarWidth = 50;
	private final static int healthBarHeight = 10;
	private final static double titanLayoutX =1200;
	private final AnchorPane anchorPane ;
	private static Image pureImage;
	private static Image AbnormalImage;
	private static Image ArmoredImage;
	private static Image ColossalImage;
	private Titan titan;
	
	static {
		try {
		pureImage = new Image(TitanView.class.getResourceAsStream("Images/pureTitanSpriteSheet.png"));
		AbnormalImage = new Image(TitanView.class.getResourceAsStream("Images/abnormalTitanSheetFinal.png"));
		ArmoredImage = new Image(TitanView.class.getResourceAsStream("Images/armoredTitanSpriteSheet.png"));
		ColossalImage = new Image(TitanView.class.getResourceAsStream("Images/zombieSpritesheet.png"));
		}catch (Exception exc) {
			exc.printStackTrace();
		
		}
	}


	/*
	 * distance between titan and wall = titan's X - wallouterBoundary's X
	 * a titan's speed in the gui = 10 * titan's engine speed
	 * so engine spawn distance should be distance between (titan and wall) /10
	 */


	public TitanView(Titan titan, AnchorPane anchorPane, double yCoordinate) {
		super(anchorPane,titanLayoutX,yCoordinate,healthBarWidth,healthBarHeight,titan.getBaseHealth());
		this.toFront();
		this.titan = titan;
		this.anchorPane = anchorPane;
		if (titan instanceof PureTitan) {
			this.setImage(pureImage);
            getHealthBar().setLayoutY(yCoordinate-10);
		}else if (titan instanceof AbnormalTitan) {
			this.setImage(AbnormalImage);
			getHealthBar().setTranslateX(20);
		}else if (titan instanceof ArmoredTitan) {
			this.setImage(ArmoredImage);
		}else if (titan instanceof ColossalTitan) {
			this.setImage(ColossalImage);
			this.setLayoutY(yCoordinate-70);//70 is height of colossal imageView
			getHealthBar().setLayoutY(yCoordinate-70);
			getHealthBar().setTranslateX(75);//width of colossal /2
		}

	}



	public void walk(double distanceToMove,double keyFrameDuration) {
		this.toFront();
		double timeToMove = 2.5;
		 final int width ;
		 final int height;
		 final int minX;
		 final int maxX;
		 final int minY;
		 this.setPreserveRatio(false);
		if (titan instanceof PureTitan) {
           width = 64; height = 128; minX = 0; maxX = 7*width-1; minY = 0;
           this.setFitWidth(45); this.setFitHeight(90);
		}else if (titan instanceof AbnormalTitan) {
			width = 64; height = 64; minX = 0; maxX = 8*width; minY = 0;
	        this.setFitWidth(width);
	        this.setFitHeight(height);
		}else if (titan instanceof ArmoredTitan) {
			width = 64; height = 128; minX = 0; maxX = 8*width-1; minY = 0;
	           this.setFitWidth(45); this.setFitHeight(90);
		}else if (titan instanceof ColossalTitan) {
			width = 64; height = 64; minX = 0; maxX = 512; minY = 9*64;
	        this.setFitWidth(200); this.setFitHeight(150);
		}else {
			width = 64; height = 64; minX = 0; maxX = 512; minY = 9*64;
	        this.setFitWidth(200); this.setFitHeight(150);
		}
		
		this.setViewport(new javafx.geometry.Rectangle2D(minX, minY, width, height)); // Set the viewport to the first frame of the walking animation

		// Create a walking animation using the frames in the 10th row
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(keyFrameDuration), e -> {
					this.setViewport(new javafx.geometry.Rectangle2D(
							this.getViewport().getMinX() + width,  // Move to the next frame by changing the X coordinate
							this.getViewport().getMinY(),
							this.getViewport().getWidth(),
							this.getViewport().getHeight()
							));

					// If the sprite has moved beyond the first frame, reset the viewport
					if (this.getViewport().getMinX() > maxX) {
						this.setViewport(new javafx.geometry.Rectangle2D(
								minX,  // Move to the first frame of the walking animation
								this.getViewport().getMinY(),
								this.getViewport().getWidth(),
								this.getViewport().getHeight()
								));
					}
				})
				);
		timeline.setCycleCount(javafx.animation.Animation.INDEFINITE);
		timeline.play();

		TranslateTransition transition = new TranslateTransition();
		transition.setNode(this);
		transition.setDuration(Duration.seconds(timeToMove));


		double distance =Math.max(-distanceToMove*10, -(this.titanLayoutX +this.getTranslateX() - WallView.getWallOuterBoundary())-25);
		//-25 adds a little correction as the wall imageView has an empty part

		transition.setByX(distance);
		this.setxCoordinate(this.getLayoutX()+this.getTranslateX());




		transition.play();

		transition.setOnFinished(e->{
			timeline.stop();
			this.setViewport(new javafx.geometry.Rectangle2D(
					minX,  
					this.getViewport().getMinY(),
					this.getViewport().getWidth(),
					this.getViewport().getHeight()
					));

		});
		TranslateTransition healthTransition = new TranslateTransition();
		healthTransition.setNode(getHealthBar());
		healthTransition.setDuration(Duration.seconds(timeToMove));
		healthTransition.setByX(distance);
		healthTransition.play();
	}
	public void defeat(double keyFrameDuration) {
		this.toFront();
		System.out.println("titan has been defeated");
		if (!(titan instanceof AbnormalTitan)) { //abnormal titan is the only one with a death animation 
			this.setImage(null);
			this.removeHealthBar();
			this.anchorPane.getChildren().remove(this);
			return;
		}
		this.setViewport(new javafx.geometry.Rectangle2D(0, 20*64, 64, 64)); // Set the viewport to the first frame of the walking animation
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(keyFrameDuration), e -> {
					this.setViewport(new javafx.geometry.Rectangle2D(
							this.getViewport().getMinX() + 64,  // Move to the next frame by changing the X coordinate
							this.getViewport().getMinY(),
							this.getViewport().getWidth(),
							this.getViewport().getHeight()
							));

					// If the sprite has moved beyond the first frame, reset the viewport
					if (this.getViewport().getMinX() > 6*64) {
						this.setViewport(new javafx.geometry.Rectangle2D(
								0,  // Move to the first frame of the walking animation
								this.getViewport().getMinY(),
								this.getViewport().getWidth(),
								this.getViewport().getHeight()
								));
					}
				})
				);
		timeline.play();
		timeline.setOnFinished(e->{
			this.setImage(null);
			this.removeHealthBar();
			this.anchorPane.getChildren().remove(this);
		});
	
	}
	public void attack(boolean isAbnormal,double keyFrameDuration) {
		 this.toFront();
		 final int width ;
		 final int height;
		 final int minX;
		 final int maxX;
		 final int minY;
		 final int noOfFrames;
		 this.setPreserveRatio(false);
		 if (titan instanceof PureTitan) { //looks good
			 noOfFrames = 5; width = 90; height = 132; minX = 0; maxX = noOfFrames*width-1; minY = 132;
	           this.setFitWidth(80); this.setFitHeight(64);
			}else if (titan instanceof AbnormalTitan) {//doesn't appear aslan
				noOfFrames = 6; width = 64; height = 64; minX = 0; maxX = noOfFrames * width-1; minY = 2*height; 
		       
			}else if (titan instanceof ArmoredTitan) {//looks good
				noOfFrames = 4; width = 124; height = 130; minX = 0; maxX = noOfFrames*width-1; minY = 130; 
		           this.setFitWidth(100); this.setFitHeight(80); 
			}else if (titan instanceof ColossalTitan) {//look for another
				noOfFrames = 8; width = 64; height = 64; minX = 0; maxX = noOfFrames * width-1; minY = 5*height;
		        this.setFitWidth(150); this.setFitHeight(150);
			}else {
				noOfFrames = 8; width = 64; height = 64; minX = 0; maxX = noOfFrames * width-1; minY = 5*height;
		        this.setFitWidth(150); this.setFitHeight(150);
			}
		this.setViewport(new javafx.geometry.Rectangle2D(minX, minY, width, height)); // Set the viewport to the first frame of the walking animation

		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(keyFrameDuration), e -> {
					this.setViewport(new javafx.geometry.Rectangle2D(
							this.getViewport().getMinX() + width,  // Move to the next frame by changing the X coordinate
							this.getViewport().getMinY(),
							this.getViewport().getWidth(),
							this.getViewport().getHeight()
							));

					// If the sprite has moved beyond the first frame, reset the viewport
					if (this.getViewport().getMinX() > maxX) {
						this.setViewport(new javafx.geometry.Rectangle2D(
								0,  // Move to the first frame of the walking animation
								this.getViewport().getMinY(),
								this.getViewport().getWidth(),
								this.getViewport().getHeight()
								));
					}
				})
				);
		timeline.setCycleCount(noOfFrames+1);
		timeline.play();
		timeline.setOnFinished(e->{
			this.setViewport(new javafx.geometry.Rectangle2D(minX, minY, width, height)); // Set the viewport to the first frame of the walking animation
		});

	}
	public static double getTitanPixelSpawnDistance() {
		return titanLayoutX;
	}
}
