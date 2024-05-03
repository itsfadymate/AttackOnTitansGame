package game.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameView extends Scene {
	public GameView() {
		super(CreateRoot());
	}

	private static Parent CreateRoot() {
		// TODO Auto-generated method stub
		
		Parent root;
        try {
			root = FXMLLoader.load(GameView.class.getResource("game.fxml"));
			System.out.println("pas de problem");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				root = FXMLLoader.load(GameView.class.getResource("test.fxml"));
				System.out.println("should display gameOver");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				root = new BorderPane();
				System.out.println("should display borderPane");
			}
			
		}
		return root;
	}


}
