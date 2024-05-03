package game.gui;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class GameView extends Scene {
	public GameView() {
		super(CreateRoot());
	}

	private static Parent CreateRoot() {
		// TODO Auto-generated method stub
		return new BorderPane();
	}

}
