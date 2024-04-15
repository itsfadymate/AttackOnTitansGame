package game.engine.exceptions;

import java.io.IOException;

public class InvalidCSVFormat extends IOException{

	private static final String MSG = "Invalid input detected while reading csv file, input = \n";
	private String inputLine; // read & write
	
	public InvalidCSVFormat(String inputLine) {
        super(MSG + inputLine);
        this.inputLine = inputLine;
    }
	
	public InvalidCSVFormat(String message, String inputLine) {
        super(message);
        this.inputLine = inputLine;
    }

	public String getInputLine() {
		return inputLine;
	}

	public void setInputLine(String inputLine) {
		this.inputLine = inputLine;
	}

	
	
}
