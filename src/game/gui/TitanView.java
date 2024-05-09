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
	private String pureTitanSpriteSheetUrl = "Images/zombieSpritesheet.png";
	private String abnormalTitanSpriteSheetUrl = "Images/zombieSpritesheet.png";
	private String ArmoredTitanSpriteSheetUrl = "Images/zombieSpritesheet.png";
	private String colossalTitanSpriteSheetUrl = "Images/zombieSpritesheet.png";

	private final static int healthBarWidth = 50;
	private final static int healthBarHeight = 10;
	private final static double titanLayoutX =1200;


	/*
	 * distance between titan and wall = titan's X - wallouterBoundary's X
	 * a titan's speed in the gui = 10 * titan's engine speed
	 * so engine spawn distance should be distance between (titan and wall) /10
	 */


	public TitanView(Titan titan, AnchorPane anchorPane, double yCoordinate) {
		super(anchorPane,titanLayoutX,yCoordinate,healthBarWidth,healthBarHeight,titan.getBaseHealth());
		if (titan instanceof PureTitan) {
			this.setImage(new Image(getClass().getResourceAsStream(pureTitanSpriteSheetUrl)));

		}else if (titan instanceof AbnormalTitan) {
			this.setImage(new Image(getClass().getResourceAsStream(abnormalTitanSpriteSheetUrl)));
		}else if (titan instanceof ArmoredTitan) {
			this.setImage(new Image(getClass().getResourceAsStream(ArmoredTitanSpriteSheetUrl)));
		}else if (titan instanceof ColossalTitan) {
			this.setImage(new Image(getClass().getResourceAsStream(colossalTitanSpriteSheetUrl)));
		}

	}



	public void walk(double distanceToMove,double keyFrameDuration) {
		double timeToMove = 2.5;
		this.setViewport(new javafx.geometry.Rectangle2D(0, 9*64, 64, 64)); // Set the viewport to the first frame of the walking animation

		// Create a walking animation using the frames in the 10th row
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(keyFrameDuration), e -> {
					this.setViewport(new javafx.geometry.Rectangle2D(
							this.getViewport().getMinX() + 64,  // Move to the next frame by changing the X coordinate
							this.getViewport().getMinY(),
							this.getViewport().getWidth(),
							this.getViewport().getHeight()
							));

					// If the sprite has moved beyond the first frame, reset the viewport
					if (this.getViewport().getMinX() > 512) {
						this.setViewport(new javafx.geometry.Rectangle2D(
								0,  // Move to the first frame of the walking animation
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
					0,  
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
		this.setViewport(new javafx.geometry.Rectangle2D(0, 20*64, 64, 64)); // Set the viewport to the first frame of the walking animation
        System.out.println("titan has been defeated");
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
			//this.setImage(null);
			this.removeHealthBar();
		});
	
	}
	public void attack(boolean isAbnormal,double keyFrameDuration) {
		final int noOfFrames = 8;
		final int sizeofFrame = 64;
		//animation is after 20 64 heightPixels
		this.setViewport(new javafx.geometry.Rectangle2D(0, 5*64, 64, 64)); // Set the viewport to the first frame of the walking animation

		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(keyFrameDuration), e -> {
					this.setViewport(new javafx.geometry.Rectangle2D(
							this.getViewport().getMinX() + sizeofFrame,  // Move to the next frame by changing the X coordinate
							this.getViewport().getMinY(),
							this.getViewport().getWidth(),
							this.getViewport().getHeight()
							));

					// If the sprite has moved beyond the first frame, reset the viewport
					if (this.getViewport().getMinX() > (noOfFrames-1) * sizeofFrame) {
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
		this.setViewport(new javafx.geometry.Rectangle2D(0, 5*64, 64, 64)); // Set the viewport to the first frame of the walking animation


	}
	public static double getTitanPixelSpawnDistance() {
		return titanLayoutX;
	}
}
