package game.gui;

import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public abstract class View extends ImageView {
	private double xCoordinate;
	private double yCoordinate;
	private AnchorPane parent;
	private ProgressBar healthBar;
	private int baseHealth;

	public ProgressBar getHealthBar() {
		return healthBar;
	}

	public View(AnchorPane parent,double x,double y,int barwidth,int barHeight,int baseHealth) {
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.parent = parent;
		this.baseHealth = baseHealth;
		this.setLayoutX(x);
		this.setLayoutY(y);

		this.healthBar = new ProgressBar(100);
		this.healthBar.setStyle("-fx-accent: #adff2f;");
		this.healthBar.setLayoutX(xCoordinate);
		this.healthBar.setLayoutY(yCoordinate);
		this.healthBar.setPrefSize(barwidth, barHeight);
		this.parent.getChildren().addAll(this,healthBar);
	}
	public View(AnchorPane parent,double x,double y) {
		this.xCoordinate = x;
		this.yCoordinate = y;
		this.parent = parent;
		this.setLayoutX(x);
		this.setLayoutY(y);
		this.parent.getChildren().addAll(this);
	}

	public double getxCoordinate() {
		return xCoordinate;
	}

	public void setxCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public void updateHealthBar(int currHealth) {
		if (healthBar==null) {
			return;
		}


		double healthFraction = (double)currHealth/baseHealth;
		if (healthFraction < 0.2) {
			this.healthBar.setStyle("-fx-accent: red;");
		}else if (healthFraction < 0.5) {
			this.healthBar.setStyle("-fx-accent: yellow;");
		}else if (healthFraction < 0.7){
			this.healthBar.setStyle("-fx-accent: #c2ff66;");
		}else if (healthFraction < 0.9) {
			this.healthBar.setStyle("-fx-accent: #adff2f;");
		}else if (healthFraction<0.95) {
			this.healthBar.setStyle("-fx-accent: #adff2f;");
		}

		this.healthBar.setProgress((double)currHealth/baseHealth);
	}
    protected void removeHealthBar() {
    	this.parent.getChildren().remove(this.healthBar);
    }
}
