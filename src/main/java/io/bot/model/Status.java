package io.bot.model;

public enum Status {
    ACTIVE(0),
    FINISHED(1),
    PAUSED(2),
    DELETED(3),
    EXPIRED(4);

    Status() {
    }

    Status(int statusId) {
        this.statusId = statusId;
    }

    private int statusId;

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
}
