package game.gui;

import javafx.application.Application;
import javafx.stage.Stage;

public class tester extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
        stage.setWidth(1280);
        stage.setHeight(730);
        stage.setScene(new GameView());
        stage.setResizable(false);
        stage.show();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
         launch(args);
	}

}
