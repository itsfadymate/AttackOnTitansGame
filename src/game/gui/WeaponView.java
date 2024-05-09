package game.gui;

import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class WeaponView extends ImageView {
	private String PiercingCannonUrl ="Images/cannon 1.png";
	private String SniperCannonUrl ="";
	private String VolleySpreadCannonUrl ="";
	private String wallTrapUrl ="Images/Spring Trap 1.png";

	public WeaponView(int weaponCode) {
		try {
		switch(weaponCode) {
		case PiercingCannon.WEAPON_CODE:
			this.setImage(new Image(getClass().getResourceAsStream(PiercingCannonUrl)));
			this.setFitWidth(44);
			this.setFitHeight(41);
			break;
		case SniperCannon.WEAPON_CODE:
			this.setImage(new Image(getClass().getResourceAsStream(SniperCannonUrl)));
			break;
		case VolleySpreadCannon.WEAPON_CODE:
			this.setImage(new Image(getClass().getResourceAsStream(VolleySpreadCannonUrl)));
			break;
		case WallTrap.WEAPON_CODE:
			this.setImage(new Image(getClass().getResourceAsStream(wallTrapUrl)));
			this.setPreserveRatio(true);
			this.setFitWidth(100);
			break;

		}
		}catch (Exception e) {
			System.out.println("failed to load weapon image");
			System.out.println(e.getMessage());
		}

	}

}
