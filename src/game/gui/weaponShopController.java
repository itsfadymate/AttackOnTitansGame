package game.gui;

import java.util.HashMap;

import game.engine.Battle;
import game.engine.weapons.WeaponRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class weaponShopController {
	@FXML
	private Label name1;
	@FXML
	private Label name2;
	@FXML
	private Label name3;
	@FXML
	private Label name4;
	@FXML
	private Label damage1;
	@FXML
	private Label damage2;
	@FXML
	private Label damage3;
	@FXML
	private Label damage4;
	@FXML
	private Label price1;
	@FXML
	private Label price2;
	@FXML
	private Label price3;
	@FXML
	private Label price4;
	@FXML
	private VBox volleySpreadvbox;
	
    public weaponShopController() {}
    public void setValues(HashMap<Integer,WeaponRegistry> hm) {
    	
    	WeaponRegistry wr = hm.get(1);
    	name1.setText(wr.getName());
    	damage1.setText("Damage: " + wr.getDamage());
    	price1.setText("Price: " + wr.getPrice());
    	
    	WeaponRegistry wr2 = hm.get(2);
    	name2.setText(wr2.getName());
    	damage2.setText("Damage: " + wr2.getDamage());
    	price2.setText("Price: " + wr2.getPrice());
    	
    	WeaponRegistry wr3 = hm.get(4);
    	name3.setText(wr3.getName());
    	damage3.setText("Damage: " + wr3.getDamage());
    	price3.setText("Price: " + wr3.getPrice());
       
    	WeaponRegistry wr4 = hm.get(3);
    	name4.setText(wr4.getName());
    	damage4.setText("Damage: " + wr4.getDamage());
    	price4.setText("Price: " + wr4.getPrice());
    	
    	
    	    
    	
    }
    
    
}
