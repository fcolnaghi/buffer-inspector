package br.com.autoprocess.inspector.exceptions;

public class InvalidLayoutException extends Exception {

	private static final long serialVersionUID = -1167003868454382L;

	public InvalidLayoutException(String numeroLinha) {
		super("Syntax error at line "+numeroLinha+". Please review the layout file.");
	}
	
}
