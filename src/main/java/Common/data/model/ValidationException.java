package Common.data.model;

/**
 * Created by tudor on 14/01/16.
 */
public class ValidationException extends Exception {
    public ValidationException(String error) {
        super(error);
    }
}