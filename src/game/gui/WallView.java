package game.gui;

import game.engine.base.Wall;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

public class WallView extends View {
	private final static String wallImageUrl = "Images/wall.png";
	private final static String destoyedWallImageUrl="";

	private final static int healthBarWidth = 90;
	private final static int healthBarHeight = 10;
	private final static double wallLayoutX = 270;
	public static double getWalllayoutx() {
		return wallLayoutX;
	}
	private final static int wallFitWidth =190;
	private final static int wallFitHeight =164;

	public WallView(Wall wall,AnchorPane parent,double yCoord)  {
		super(parent,wallLayoutX,yCoord,healthBarWidth,healthBarHeight,wall.getBaseHealth());
		try {
			this.setImage(new Image(getClass().getResourceAsStream(wallImageUrl)));
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("couldn't find wall image " + e.getMessage());
		}
		this.setPreserveRatio(false);
		this.setFitWidth(wallFitWidth);
		this.setFitHeight(wallFitHeight);
		super.getHealthBar().setTranslateY(wallFitHeight/3);
		super.getHealthBar().setTranslateX(wallFitWidth/1.5);
		super.getHealthBar().setRotate(90);
	}
	public WallView(AnchorPane parent,double yCoord)  {
		super(parent,wallLayoutX,yCoord);
		try {
			this.setImage(new Image(getClass().getResourceAsStream(wallImageUrl)));
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("couldn't find wall image " + e.getMessage());
		}this.setPreserveRatio(false);
		this.setFitWidth(wallFitWidth);
		this.setFitHeight(wallFitHeight);

	}
	public void defeat() {
		try {
			this.setImage(new Image(getClass().getResourceAsStream(destoyedWallImageUrl)));
		}catch (Exception e) {
			this.setImage(null);
			System.out.println("no destoyed wall image was found");
		}
		super.removeHealthBar();
	}
    public static double getWallOuterBoundary() {
    	return wallLayoutX + wallFitWidth;
    }
}
