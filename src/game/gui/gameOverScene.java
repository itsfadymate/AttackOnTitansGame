package game.gui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class gameOverScene extends Scene {
	private static Label scoreLabel;
	private static Label turns;
	private static Button mainMenu;
	private static final String CSV_FILE_PATH = "players.csv";
	
    public gameOverScene() {
        super(createRoot(), 1280, 780);
//        scoreLabel.setText("Score: " + s);
//        turns.setText("Survived Turns: " + t);
    }
    
    public static void setScore(int s) {
    	scoreLabel.setText("Score: " + s);
    	writePlayerInfoToCSV("PlayerName", s);
    }
    public static void setTurns(int t) {
    	turns.setText("Survived Turns: " + t);
    }

    private static Parent createRoot() {
        BorderPane root = new BorderPane();

        // Load the background image
        Image bg = new Image(gameOverScene.class.getResourceAsStream("images/GameOver.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(
                bg, 
                BackgroundRepeat.NO_REPEAT, 
                BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.CENTER, 
                new BackgroundSize(
                        BackgroundSize.AUTO, 
                        BackgroundSize.AUTO, 
                        true,true,true,true));
        root.setBackground(new Background(backgroundImage));
        
        //Game over label
        StackPane top = new StackPane();
        Label gameOver = new Label("GAME OVER");
        gameOver.setFont(new Font("Comic Sans MS", 100));
        gameOver.setTextFill(Color.WHITE);
        top.getChildren().add(gameOver);
        top.setPadding(new Insets(50, 0, 0, 0));
        root.setTop(top);
        BorderPane.setAlignment(top, Pos.CENTER);
        
        //Left VBox for Score and Turns
        VBox left = new VBox(20);
        left.setPadding(new Insets(60, 0, 0, 20));
        scoreLabel = new Label("SCORE " + 150);
        turns = new Label("TURNS SURVIVED " + 50);
        scoreLabel.setFont(new Font("Comic Sans MS", 50));
        scoreLabel.setTextFill(Color.WHITE);
        turns.setFont(new Font("Comic Sans MS", 50));
        turns.setTextFill(Color.WHITESMOKE);
        left.getChildren().addAll(scoreLabel, turns);
        root.setLeft(left);
        BorderPane.setAlignment(left, Pos.CENTER_LEFT);
        

        
        //Main Menu button
        StackPane bottom = new StackPane();
        mainMenu = new Button("Main Menu");
        mainMenu.setPrefWidth(400);
        mainMenu.setPrefHeight(200);
        mainMenu.setFont(new Font("Comic Sans MS", 60));
        mainMenu.setTextFill(Color.WHITE);
        mainMenu.setBackground(Background.EMPTY);
        mainMenu.setBorder(Border.EMPTY);
        bottom.getChildren().add(mainMenu);
        mainMenu.setOnMouseMoved(event -> mainMenu.setTextFill(Color.YELLOW));
        mainMenu.setOnMouseExited(event -> mainMenu.setTextFill(Color.WHITE));
        bottom.setPadding(new Insets(0, 0, 50, 0));
        root.setBottom(bottom);
        return root;
    }
    private static void writePlayerInfoToCSV(String playerName, int score) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE_PATH, true))) {
            writer.write("," + score); // Write player name and score separated by a comma
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setmainMenuOnMouseClicked(EventHandler<ActionEvent> e) {
    	mainMenu.setOnAction(e);
    }
    
}
