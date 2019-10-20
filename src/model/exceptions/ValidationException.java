/*
 * This class will validate the DepartmentForm field fill
 */
package model.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Iara Santos
 */
public class ValidationException extends RuntimeException {

    private Map<String, String> errors = new HashMap<>();

    public Map<String, String> getErrors() {
        return errors;
    }
    
    public void addError(String fieldName, String errorMessage){
        errors.put(fieldName, errorMessage);
    }

    //enforce heritage
    public ValidationException(String msg) {
        super(msg);
    }
}

