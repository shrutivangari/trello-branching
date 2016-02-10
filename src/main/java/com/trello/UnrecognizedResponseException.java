package com.trello;


/**
 * Custom exception that is thrown when a HTTP response code is
 * not found in the {@link HttpResponseCode} ENUM class
 * 
 * @author Shruti Vangari
 * 
 */

public class UnrecognizedResponseException extends RuntimeException {
	

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5443797443544225643L;

	/**
     * Overrides the default constructor of
     * the Exception class
     * 
     */
    public UnrecognizedResponseException() {
        super();
    }
    
    /**
     * Passes the custom error message 
     * as a parameter
     * 
     * @param message Exception message
     * 
     */
    public UnrecognizedResponseException(String message) {
        super(message);
    }
    
    /**
     * Passes the custom error message
     * as a parameter
     * 
     * @param message Exception message
     * 
     * @param cause The cause due to which the exception occurred
     *              A null value is permitted which indicates that the cause 
     *              is nonexistant or unknown
     * 
     */
    public UnrecognizedResponseException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new exception and a detailed message
     *  
     * @param cause The cause due to which the exception occurred
     *              A null value is permitted which indicates that the cause 
     *              is nonexistant or unknown
     *              
     */
    public UnrecognizedResponseException(Throwable cause) {
        super(cause);
    }
}
