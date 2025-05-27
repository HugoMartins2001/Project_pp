package event;

public class EndEvent extends Event {
    public EndEvent(int minute) {
        super("-> Match ended", minute);
    }
}
