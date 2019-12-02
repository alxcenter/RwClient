package io.bot.exceptions;

public class StationNotFoundException extends RuntimeException{
    public StationNotFoundException(int id) {
        super("Could not find station " + id);
    }
}
