package game.gui;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.PriorityQueue;
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
    	System.out.println("initializing leaderboards");
        players = new Label[]{player1, player2, player3, player4, player5};
        scores = new Label[]{score1, score2, score3, score4, score5};

        readFromCSV();
    }


    private void readFromCSV() {
    	try {
			BufferedReader br = new BufferedReader(new FileReader("players.csv"));
			PriorityQueue<PlayerData> playersPq = new PriorityQueue<>();
			String line;
			int lineNo =0;

			//we read max 5 top people and add them to pq
			while ((line = br.readLine())!=null ) {
				String[] lineData = line.split(",");
				leaderboard gos = new leaderboard();
				PlayerData pd = gos.new PlayerData(lineData[0],Integer.parseInt(lineData[1]));
				playersPq.add(pd);
				lineNo++;
			}


			System.out.println(playersPq);
			for (int i=0;i<5 && lineNo >=0;i++,lineNo--) {
				PlayerData pd = playersPq.remove();
				players[i].setText(pd.getName());
				scores[i].setText(""+pd.getScore());
			}

			br.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    class  PlayerData implements Comparable<PlayerData>{
		String playerName;
		int score;
		public PlayerData(String playerName,int score) {
			this.playerName = playerName;
			this.score =score;
		}


		@Override
		public int compareTo(PlayerData that) {
			return that.score - this.score;
		}
		public String getName() {return playerName;}
		public int getScore() {return score;}
		@Override
		public String toString() {
			return "["+playerName + "," + score+"]";
		}
	}
}
