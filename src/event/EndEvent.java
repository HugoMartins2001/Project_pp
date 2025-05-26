package event;

public class EndEvent extends Event {
    public EndEvent(int minute) {
        super("\uD83D\uDED1 Match ended", minute);
    }


    @Override
    public void exportToJson() {
        // Implement JSON export logic here if needed
    }
}
