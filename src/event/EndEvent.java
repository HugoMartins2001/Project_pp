package event;

public class EndEvent extends Event{
    public EndEvent(int minute) {
        super("Match ended", minute);
    }

    @Override
    public String getDescription() {
        return "Match ended at " + getMinute() + " minutes";
    }

    @Override
    public void exportToJson() {
        // Implement JSON export logic here if needed
    }
}
