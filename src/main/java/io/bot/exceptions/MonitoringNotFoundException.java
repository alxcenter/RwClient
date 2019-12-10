package io.bot.exceptions;

public class MonitoringNotFoundException extends RuntimeException {
    public MonitoringNotFoundException(long id) {
        super("Could not find monitoring " + id);
    }
}
