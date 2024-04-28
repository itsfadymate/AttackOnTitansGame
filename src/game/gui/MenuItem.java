package game.gui;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

	public  class MenuItem extends StackPane {
 	private final static int buttonWidth = 300;
 	private final static int buttonHeight = 40;
	private final Font buttonFont = Font.font("Tw Cen MT Condensed", FontWeight.SEMI_BOLD, 30);

	public MenuItem(String name) {
        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(0.1, Color.RED),
                new Stop(0.2, Color.BLACK),
                new Stop(0.8, Color.BLACK),
                new Stop(0.9, Color.RED)
        });

        Rectangle bg = new Rectangle(buttonWidth, buttonHeight);
        bg.setOpacity(0.4);

        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(buttonFont);

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);
        });


        setOnMouseExited(event -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
        });

        setOnMousePressed(event -> {
            bg.setFill(Color.RED);
        });

        setOnMouseReleased(event -> {
            bg.setFill(gradient);
        });
    }
    public MenuItem(String name,int width,int height) {
    	LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                new Stop(0.1, Color.RED),
                new Stop(0.2, Color.BLACK),
                new Stop(0.8, Color.BLACK),
                new Stop(0.9, Color.RED)
        });

        Rectangle bg = new Rectangle(width, height);
        bg.setOpacity(0.4);

        Text text = new Text(name);
        text.setFill(Color.DARKGREY);
        text.setFont(buttonFont);

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg, text);

        setOnMouseEntered(event -> {
            bg.setFill(gradient);
            text.setFill(Color.WHITE);
        });


        setOnMouseExited(event -> {
            bg.setFill(Color.BLACK);
            text.setFill(Color.DARKGREY);
        });

        setOnMousePressed(event -> {
            bg.setFill(Color.RED);
        });

        setOnMouseReleased(event -> {
            bg.setFill(gradient);
        });
    	
    }
	}
