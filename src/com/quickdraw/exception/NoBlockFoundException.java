package com.quickdraw.exception;


public class NoBlockFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	public NoBlockFoundException (String reason) {
		super(reason);
	}
    public String getWhy() {
    	return "This exception will throw while you use the class \"Draw\" and did't use the"
    			+ "method setBlock() yet!";
    }
}
