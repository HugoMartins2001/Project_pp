package event;

public class StartEvent extends Event {

    public StartEvent(int minute) {
        super("Match started", minute);
    }

    @Override
    public String getDescription() {
        return "Match started at " + getMinute() + " minutes";
    }

    @Override
    public void exportToJson() {
        // Implement JSON export logic here if needed
    }
}
