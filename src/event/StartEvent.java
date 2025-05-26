package event;

public class StartEvent extends Event {

    public StartEvent(int minute) {
        super("\uD83D\uDFE2 Match started", minute);
    }

    @Override
    public void exportToJson() {
        // Implement JSON export logic here if needed
    }
}
