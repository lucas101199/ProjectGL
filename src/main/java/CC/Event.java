package CC;

public enum Event {
    NEW_FLOOR,
    EMMERGENCY_STOP,
    READY_TO_GO,
    USER_REQUEST,
    STOPPED;

    public Event linkQuery(Query q){userQuery = q;return  this;}

    public Query userQuery;
}
