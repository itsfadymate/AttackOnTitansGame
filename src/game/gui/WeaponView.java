package game.gui;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Stack;

import game.engine.titans.ColossalTitan;
import game.engine.titans.Titan;
import game.engine.weapons.PiercingCannon;
import game.engine.weapons.SniperCannon;
import game.engine.weapons.VolleySpreadCannon;
import game.engine.weapons.WallTrap;
import game.engine.weapons.WeaponRegistry;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.util.Duration;

public class WeaponView extends ImageView {

	private static Image piercingCannonImage;
	private static Image sniperImage;
	private static Image volleySpreadImage;
	private static Image wallTrapImage;
	private static Image projectileImage;
	private static Image fireProjectileImage;
	private static Image cannonProjectileImage;
	private final int  weaponCode;
	private final AnchorPane parent;
	private final WeaponRegistry wr;

	static {
		try {
		piercingCannonImage = new Image(WeaponView.class.getResourceAsStream("Images/cannonSpriteSheet90x64.png"));
		sniperImage = new Image(WeaponView.class.getResourceAsStream("Images/sniperSpriteSheet.png"));
		volleySpreadImage = new Image(WeaponView.class.getResourceAsStream("Images/mortar.png"));
		wallTrapImage = new Image(WeaponView.class.getResourceAsStream("Images/wallTrapSpriteSheet170x245.png"));
		projectileImage = new Image(WeaponView.class.getResourceAsStream("Images/rockProjectileSpriteSheet.png"));
		fireProjectileImage = new Image(WeaponView.class.getResourceAsStream("Images/sniperBulletSheet.png"));
		cannonProjectileImage = new Image(WeaponView.class.getResourceAsStream("Images/CannonProjectile.png"));

		}catch (Exception exc) {
			System.out.println("failed to load weapon image");
			System.out.println(exc.getMessage());

		}
	}

	public WeaponView(int weaponCode,AnchorPane parent,WeaponRegistry wr) {
		this.wr = wr;
		this.parent = parent;
		this.weaponCode = weaponCode;
		this.setPreserveRatio(true);
		switch(weaponCode) {
		case PiercingCannon.WEAPON_CODE:
			this.setImage(piercingCannonImage);
			this.setViewport(new Rectangle2D(0,0,90,64));
			this.setFitWidth(33);
			this.setFitHeight(41);
			break;
		case SniperCannon.WEAPON_CODE:
			this.setImage(sniperImage);
			this.setViewport(new Rectangle2D(0,0,64,64));
			this.setFitWidth(43);
			this.setFitHeight(38);
			break;
		case VolleySpreadCannon.WEAPON_CODE:
			this.setImage(volleySpreadImage);
			this.setViewport(new Rectangle2D(25,50,120,120));
			this.setFitWidth(41);
			this.setFitHeight(41);

			break;
		case WallTrap.WEAPON_CODE:
			this.setImage(wallTrapImage);
			this.setViewport(new Rectangle2D(0,0,170,245));
			this.setFitWidth(95);
			this.setFitHeight(122);
			break;

		}


	}

	public void fire(PriorityQueue<Titan> titans,HashMap<Titan,TitanView> titanMap) {
		final int maxX;
		int cycleCount =0;
		int durationMillis=100;
		switch(weaponCode) {
		case PiercingCannon.WEAPON_CODE:
			cycleCount = 6;
			maxX = cycleCount * 90;
			durationMillis = 50;
			break;
		case SniperCannon.WEAPON_CODE:
			cycleCount = 12;
			maxX = cycleCount * 64;
			break;
		case VolleySpreadCannon.WEAPON_CODE:
			cycleCount = 0;
			maxX = 0;
			Bounds thisBounds = this.localToScene(getBoundsInLocal());
			Bounds anchorPaneBounds = this.parent.sceneToLocal(thisBounds);
			ArrayList<Double> firingList= titansInRange(titans,titanMap,wr.getMinRange(),wr.getMaxRange());
			for (double fireX :firingList ) {
				this.shootMortarProjectile(anchorPaneBounds.getCenterX(), anchorPaneBounds.getCenterY(), (fireX), anchorPaneBounds.getCenterY() +30, false);
			}

			break;
		case WallTrap.WEAPON_CODE:
			cycleCount =6;
			maxX = cycleCount * 170;
			durationMillis = 120;
			break;
        default:
        	maxX = 100;
		}
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(durationMillis),e->{
			if (weaponCode==VolleySpreadCannon.WEAPON_CODE) {
				return;
			}
			this.setViewport(new Rectangle2D(
					this.getViewport().getMinX() + this.getViewport().getWidth(),
					this.getViewport().getMinY(),
					this.getViewport().getWidth(),
					this.getViewport().getHeight()
					));
			if (this.getViewport().getMinX() > maxX) {
				this.setViewport(new Rectangle2D(
						0,
						this.getViewport().getMinY(),
						this.getViewport().getWidth(),
						this.getViewport().getHeight()
						));
			}
			if (this.getViewport().getMinX()==2 * this.getViewport().getWidth() && this.weaponCode== SniperCannon.WEAPON_CODE) {
				System.out.println("got here in sniper fire");
				Bounds thisBounds = this.localToScene(getBoundsInLocal());
				Bounds viewAnchorPaneBounds = this.parent.sceneToLocal(thisBounds);
				double titanX = titanMap.get(titans.peek()).localToScene(titanMap.get(titans.peek()).getBoundsInLocal()).getMinX();
				titanX = (titans.peek() instanceof ColossalTitan)? titanX + 70 : titanX;
				this.shootFireProjectile(viewAnchorPaneBounds.getMaxX(),titanX, viewAnchorPaneBounds.getMinY(),titanMap.get(titans.peek()).getTitanPixelPerSecondVelocity(),titans.peek().hasReachedTarget());
			}else if (this.weaponCode ==PiercingCannon.WEAPON_CODE ) { //creates cool animation when invoked in the timelines
			   ArrayList<Double> titanXs = this.cannonTitansXCoordinates(titans, titanMap);
			   ArrayList<Double> titanVelocities = this.cannonTitansPixelVelocity(titans, titanMap);
			   ArrayList<Boolean> titanReached = this.cannonTitanshasReachedTarget(titans, titanMap);
			    Bounds thisBounds = this.localToScene(getBoundsInLocal());
				Bounds viewAnchorPaneBounds = this.parent.sceneToLocal(thisBounds);
			   for (int i =0; i< titanXs.size();i++) {
				   this.shootCannonProjectile(viewAnchorPaneBounds.getMaxX()
						   ,titanXs.get(i)
						   , viewAnchorPaneBounds.getMinY()
						   ,titanVelocities.get(i)
						   ,titanReached.get(i)
						   );
			   }
			}

			}
		));
		if (this.weaponCode== PiercingCannon.WEAPON_CODE) 
			soundSystem.playCannonEffect();
		timeline.setCycleCount(cycleCount);
		/*if (weaponCode==WallTrap.WEAPON_CODE) {
			timeline.setAutoReverse(true);
		}*/
		timeline.play();
		timeline.setOnFinished(e->{
			this.setViewport(new Rectangle2D(
					0,
					this.getViewport().getMinY(),
					this.getViewport().getWidth(),
					this.getViewport().getHeight()
					));
		});

	}
	private ArrayList<Double> titansInRange(PriorityQueue<Titan> titans, HashMap<Titan, TitanView> titanMap, int minRange,
			int maxRange) {
		ArrayList<Double> list = new ArrayList<>();
		for (Titan t : titans) {
			if (t.getDistance() > minRange && t.getDistance()<maxRange) {
				if (t instanceof ColossalTitan) {
				    	list.add(titanMap.get(t).localToScene(titanMap.get(t).getBoundsInLocal()).getCenterX());
				}
				else {
					list.add( titanMap.get(t).localToScene(titanMap.get(t).getBoundsInLocal()).getMinX()  + titanMap.get(titans.peek()).getTitanWalkDisttance()/4 );
				}
			}
		}
		return list;
	}
     
	private ArrayList<Double> cannonTitansXCoordinates(PriorityQueue<Titan> titans, HashMap<Titan, TitanView> titanMap){
		ArrayList<Double> list = new ArrayList<>();
		Stack<Titan> s = new Stack<>();
		for (int i=0;!titans.isEmpty() && i <5;i++ ) {
			s.push(titans.poll());
			double titanX = titanMap.get(s.peek()).localToScene(titanMap.get(s.peek()).getBoundsInLocal()).getCenterX();
			//titanX = (titans.peek() instanceof ColossalTitan)? titanX + 70 : titanX;
			list.add(titanX);
		}
		while (!s.isEmpty()) {
			titans.add(s.pop());
		}
		System.out.println(list.toString());
		return list;
	}
	private ArrayList<Boolean> cannonTitanshasReachedTarget(PriorityQueue<Titan> titans, HashMap<Titan, TitanView> titanMap){
		ArrayList<Boolean> list = new ArrayList<>();
		Stack<Titan> s = new Stack<>();
		for (int i=0;!titans.isEmpty() && i <5;i++ ) {
			s.push(titans.poll());
			list.add((s.peek().hasReachedTarget()));
			
		}
		while (!s.isEmpty()) {
			titans.add(s.pop());
		}
		
		return list;
	}
	private ArrayList<Double> cannonTitansPixelVelocity(PriorityQueue<Titan> titans, HashMap<Titan, TitanView> titanMap){
		ArrayList<Double> list = new ArrayList<>();
		Stack<Titan> s = new Stack<>();
		for (int i=0;!titans.isEmpty() && i <5;i++ ) {
			s.push(titans.poll());
			list.add( titanMap.get(s.peek()).getTitanPixelPerSecondVelocity());
		}
		while (!s.isEmpty()) {
			titans.add(s.pop());
		}
		return list;
	}
	
	public void shootMortarProjectile(double startX,double startY,double endX,double endY,boolean showpath) {
    	ImageView view = new ImageView(projectileImage);
    	view.setFitWidth(32);
    	view.setFitHeight(32);
   	 view.setViewport(new Rectangle2D(0,0,64,64));
   	 Timeline animate = new Timeline(new KeyFrame(
   			 Duration.millis(100) ,
   			 e->{
   		        view.setViewport(new Rectangle2D(view.getViewport().getMinX() + view.getViewport().getWidth(),0,view.getViewport().getWidth(),view.getViewport().getHeight()));
   		        if (view.getViewport().getMinX()> 3*view.getViewport().getWidth()) {
   		        	view.setViewport(new Rectangle2D(0,0,64,64));
   		        }
   	         })
        );
   	 animate.setCycleCount(15);
   	 view.setViewport(new Rectangle2D(0,4*64,64,64));
   	 Timeline destroyed = new Timeline(new KeyFrame(
   			 Duration.millis(100) ,
   			 e->{
   		        view.setViewport(new Rectangle2D(view.getViewport().getMinX() + view.getViewport().getWidth(),0,view.getViewport().getWidth(),view.getViewport().getHeight()));
   		       // System.out.println(view.getViewport().getWidth());
   		       // System.out.println(view.getViewport().getMinX());
   		        if (view.getViewport().getMinX()> 6*view.getViewport().getWidth()) {
   		        	view.setViewport(new Rectangle2D(4*64,0,64,64));
   		        }
   	         })
        );
   	destroyed.setCycleCount(2);
   	FadeTransition fade = new FadeTransition();
   	fade.setFromValue(1);
   	fade.setToValue(0);
   	fade.setDuration(Duration.millis(750));
   	fade.setNode(view);


        MoveTo moveTo = new MoveTo();
        moveTo.setX(startX);
        moveTo.setY(startY);

        QuadCurveTo arcTo = new QuadCurveTo();
        arcTo.setX(endX);
        arcTo.setY(endY);
        arcTo.setControlX((startX + endX)/2);
        arcTo.setControlY(50);

        Path path = new Path();
        path.setVisible(showpath);
        path.getElements().add(moveTo);
        path.getElements().add(arcTo);

        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(1500));
        pathTransition.setNode(view);
        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.NONE);
        pathTransition.setCycleCount(1);

        ParallelTransition pt  = new ParallelTransition(pathTransition,animate);
        pt.play();

        this.parent.getChildren().addAll(view,path);
        pt.setOnFinished(e->{destroyed.play();});
        destroyed.setOnFinished(r->{fade.play();});
        fade.setOnFinished(e->{parent.getChildren().remove(view);});
    }

	public void shootFireProjectile(double startX,double titanX,double y,double titanSpeed,boolean hasReachedTarget) {
		final double speed = 2000;
		soundSystem.playSniperEffect();
		double endX = hasReachedTarget? titanX : titanX-50;
		//double sniperTitanDistance = titanX - startX;
		//double endX = (sniperTitanDistance * speed) / (titanSpeed + speed);//related to setByX as well. works but creates exception later on so nvm
		System.out.println("fireImage start x: " + startX);
		System.out.println("fireImage end   x: " + endX);
		System.out.println("fireImage start y: " + y);
    	ImageView fireImage = new ImageView(fireProjectileImage);
    	fireImage.toFront();
    	fireImage.setLayoutX(startX);
    	fireImage.setLayoutY(y);
    	fireImage.setViewport(new Rectangle2D(0,0,64,64));
    	TranslateTransition translate = new TranslateTransition();
    	translate.setNode(fireImage);
    	translate.setFromX(0);
    	translate.setDuration(Duration.seconds(  (endX-startX) / speed   ));
    	translate.setByX(endX-startX);
    	Timeline explosion = new Timeline(new KeyFrame(Duration.millis(25), next ->{
    		//System.out.println("updating viewport");
    		fireImage.setViewport(   new Rectangle2D(fireImage.getViewport().getMinX() + 64,0,fireImage.getViewport().getWidth(),fireImage.getViewport().getHeight())   );
    	}));
    	explosion.setCycleCount(8);
    	FadeTransition remove = new FadeTransition();
    	remove.setNode(fireImage);
    	remove.setFromValue(1);
    	remove.setToValue(0);
    	remove.setDuration(Duration.seconds(2));
    	remove.setOnFinished(e->{this.parent.getChildren().remove(fireImage);});

    	explosion.setOnFinished(e->{remove.play();});
    	translate.setOnFinished(e->{
            explosion.play();

    	});
    	this.parent.getChildren().add(fireImage);
    	translate.play();

    }
    
	public void shootCannonProjectile(double startX,double titanX,double y,double titanSpeed,boolean hasReachedTarget) {
		final double speed = 1500;
		double endX = hasReachedTarget? titanX : titanX-50;
		ImageView projectileImage = new ImageView(fireProjectileImage);
		projectileImage.setPreserveRatio(true);
		projectileImage.setFitWidth(32);
		projectileImage.toFront();
		projectileImage.setLayoutX(startX);
		projectileImage.setLayoutY(y);
		TranslateTransition translate = new TranslateTransition();
    	translate.setNode(projectileImage);
    	translate.setFromX(0);
    	translate.setDuration(Duration.seconds(  (endX-startX) / speed   ));
    	translate.setByX(endX-startX);
    	translate.setOnFinished(e->{this.parent.getChildren().remove(projectileImage);});
    	this.parent.getChildren().add(projectileImage);
    	translate.play();

		
	}

}
