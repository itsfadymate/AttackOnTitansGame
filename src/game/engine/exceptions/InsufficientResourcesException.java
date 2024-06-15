package game.engine.exceptions;

public class InsufficientResourcesException extends GameActionException {


	private int resourcesProvided; // read & write
	private static final String MSG = "Not enough resources, resources provided = " ;

	public InsufficientResourcesException(int resourcesProvided) {
		super(MSG+ resourcesProvided);
		this.resourcesProvided = resourcesProvided;
	}

	public InsufficientResourcesException(String message , int resourcesProvided) {
		super(message);
		this.resourcesProvided = resourcesProvided;
	}

	public int getResourcesProvided() {
		return resourcesProvided;
	}

	public void setResourcesProvided(int resourcesProvided) {
		this.resourcesProvided = resourcesProvided;
	}

}
