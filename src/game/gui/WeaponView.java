package game.gui;



import java.util.PriorityQueue;

import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class WeaponView extends ImageView {

	private static Image piercingCannonImage;
	private static Image sniperImage;
	private static Image volleySpreadImage;
	private static Image wallTrapImage;
	private final int  weaponCode;
	
	static {
		try {
		piercingCannonImage = new Image(WeaponView.class.getResourceAsStream("Images/cannonSpriteSheet90x64.png"));
		sniperImage = new Image(WeaponView.class.getResourceAsStream("Images/sniperSpriteSheet.png"));
		volleySpreadImage = new Image(WeaponView.class.getResourceAsStream("Images/mortar.png"));
		wallTrapImage = new Image(WeaponView.class.getResourceAsStream("Images/wallTrapSpriteSheet170x245.png"));
		}catch (Exception exc) {
			System.out.println("failed to load weapon image");
			System.out.println(exc.getMessage());
		
		}
	}

	public WeaponView(int weaponCode) {
		this.weaponCode = weaponCode;
		this.setPreserveRatio(true);
		switch(weaponCode) {
		case PiercingCannon.WEAPON_CODE:
			this.setImage(piercingCannonImage);
			this.setViewport(new Rectangle2D(0,0,90,64));
			this.setFitWidth(33);
			this.setFitHeight(41);
			break;
		case SniperCannon.WEAPON_CODE:
			this.setImage(sniperImage);
			this.setViewport(new Rectangle2D(0,0,64,64));
			this.setFitWidth(43);
			this.setFitHeight(38);
			break;
		case VolleySpreadCannon.WEAPON_CODE:
			this.setImage(volleySpreadImage);
			this.setViewport(new Rectangle2D(25,50,120,120));
			this.setFitWidth(41);
			this.setFitHeight(41);
			break;
		case WallTrap.WEAPON_CODE:
			this.setImage(wallTrapImage);
			this.setViewport(new Rectangle2D(0,0,170,245));
			this.setFitWidth(95);
			this.setFitHeight(122);
			break;

		}
		

	}
	
	public void fire(PriorityQueue<Titan> titans) {
		final int maxX;
		int cycleCount =0;
		int durationMillis=100;
		switch(weaponCode) {
		case PiercingCannon.WEAPON_CODE:
			cycleCount = 6;
			maxX = cycleCount * 90;
			durationMillis = 50;
			break;
		case SniperCannon.WEAPON_CODE:
			cycleCount = 12;
			maxX = cycleCount * 64;
			break;
		case VolleySpreadCannon.WEAPON_CODE:
			cycleCount = 0;
			maxX = 0;
			break;
		case WallTrap.WEAPON_CODE:
			cycleCount =6;
			maxX = cycleCount * 170;
			durationMillis = 120;
			break;
        default:
        	maxX = 100;
		}
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(durationMillis),e->{
			if (weaponCode==VolleySpreadCannon.WEAPON_CODE) return;
			this.setViewport(new Rectangle2D(
					this.getViewport().getMinX() + this.getViewport().getWidth(),
					this.getViewport().getMinY(),
					this.getViewport().getWidth(),
					this.getViewport().getHeight()
					));
			if (this.getViewport().getMinX() > maxX) {
				this.setViewport(new Rectangle2D(
						0,
						this.getViewport().getMinY(),
						this.getViewport().getWidth(),
						this.getViewport().getHeight()
						));
			}
				
			}
		));
		timeline.setCycleCount(cycleCount);
		/*if (weaponCode==WallTrap.WEAPON_CODE) {
			timeline.setAutoReverse(true);
		}*/
		timeline.play();
		timeline.setOnFinished(e->{
			this.setViewport(new Rectangle2D(
					0,
					this.getViewport().getMinY(),
					this.getViewport().getWidth(),
					this.getViewport().getHeight()
					));
		});
		
	}

}
