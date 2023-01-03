
public enum PartOfDay {
    MORNING("05:00:00", "12:00:00"),
    AFTERNOON("12:00:00", "17:00:00"),
    EVENING("17:00:00", "23:59:59"),
    NIGHT("00:00:00", "05:00:00");
    private final String from;
    private final String to;
    PartOfDay(String from, String to){
        this.from=from;
        this.to=to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}
