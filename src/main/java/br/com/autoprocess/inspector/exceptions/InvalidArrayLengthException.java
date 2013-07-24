package br.com.autoprocess.inspector.exceptions;

public class InvalidArrayLengthException extends Exception {

	private static final long serialVersionUID = -1167003868454382L;

	public InvalidArrayLengthException() {
		super("Array length error.");
	}

}
