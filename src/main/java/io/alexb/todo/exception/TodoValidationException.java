package io.alexb.todo.exception;

public class TodoValidationException extends RuntimeException{
    public TodoValidationException(String message) {
        super(message);
    }


    public static void validateId(Integer id){
        if (id == null){
            throw new TodoValidationException("ID must not be null or empty");
        }
    }
}
