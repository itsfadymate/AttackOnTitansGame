package game.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class leaderboard implements Initializable {
    @FXML
    private Button mainMenu;

    @FXML
    private AnchorPane Anchorpane;
    @FXML
    private ImageView crown;
    @FXML
    private Label n1;
    @FXML
    private Label n2;
    @FXML
    private Label n3;
    @FXML
    private Label n4;
    @FXML
    private Label n5;
    @FXML
    private Label player;
    @FXML
    private Label player1;
    @FXML
    private Label player2;
    @FXML
    private Label player3;
    @FXML
    private Label player4;
    @FXML
    private Label player5;
    @FXML
    private Label score;
    @FXML
    private Label score1;
    @FXML
    private Label score2;
    @FXML
    private Label score3;
    @FXML
    private Label score4;
    @FXML
    private Label score5;
    @FXML
    private Label title;
    @FXML
    private ImageView background41;
    private Label[] players;
    private Label[] scores;

    @FXML
    void onMouseAction(ActionEvent event) {
    }

    public void onmainMenuMouseClicked(EventHandler<ActionEvent> e) {
        mainMenu.setOnAction(e);
    }

    @FXML
    void onMouseEntered(MouseEvent event) {
        mainMenu.setTextFill(Color.YELLOW);
    }

    @FXML
    void onMouseExited(MouseEvent event) {
        mainMenu.setTextFill(Color.WHITE);
    }

    @FXML
    void onMouseMoved(MouseEvent event) {
    }

    public void mainMenuOnMouseClicked(EventHandler<MouseEvent> e) {
        this.mainMenu.setOnMouseClicked(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Initialize the players and scores arrays here
        players = new Label[]{player1, player2, player3, player4, player5};
        scores = new Label[]{score1, score2, score3, score4, score5};

        readFromCSV();
    }

    private void readFromCSV() {
        String csvFile = "players.csv";
        String line;
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int i = 0;
            while ((line = br.readLine()) != null) {
                // Split the line by comma
                String[] data = line.split(csvSplitBy);

                // Ensure we have enough data and not exceeding the arrays' lengths
                if (data.length < 2 || i >= players.length || i >= scores.length) {
                    break;
                }

                players[i].setText(data[0]);
                scores[i].setText(data[1]);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
