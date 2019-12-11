package se.lexicon.erik.order_management.exception;

import java.util.function.Supplier;

public class Exceptions {
    public static Supplier<EntityNotFoundException> entityNotFoundException(String message){
        return () -> new EntityNotFoundException(message);
    }
}
