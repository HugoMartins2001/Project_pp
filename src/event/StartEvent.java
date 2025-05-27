package event;

public class StartEvent extends Event {

    public StartEvent(int minute) {
        super("-> Match started", minute);
    }

    @Override
    public void exportToJson() {
        // Implement JSON export logic here if needed
    }
}
