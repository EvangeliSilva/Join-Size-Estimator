package hw8;

/**
 * The MissingArgumentException class use to indicate 
 * if a required argument(s) is missing
 * 
 * @author Evangeli Silva (esilva2@albany.edu)
 *
 */
public class MissingArgumentException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructs a MissingArgumentException with the specified detail message
	 * @param message 
	 * 		The detail message (which is saved for later retrieval by the getMessage method)
	 */
	public MissingArgumentException(String message) {
        super(message);
    }
}
