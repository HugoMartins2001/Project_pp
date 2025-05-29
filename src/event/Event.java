package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import player.Player;

import java.io.IOException;

/**
 * Represents a generic event in a football match.
 * <p>
 * This class implements {@link IEvent} and provides basic properties such as
 * the event description and the minute at which the event occurred. It also
 * supports exporting the event to a JSON-like format and comparing events by their content.
 * </p>
 *
 * <p>
 * Example descriptions: {@code "-> Foul committed"}, {@code "-> Goal scored"}, etc.
 * </p>
 *
 * <p>
 * Authors:
 * <ul>
 *   <li>Rúben Tiago Martins Pereira (8230162) - LsircT2</li>
 *   <li>Hugo Leite Martins (8230273) - LsircT2</li>
 * </ul>
 * </p>
 *
 * @see IEvent
 */
public class Event implements IEvent {
    private final String description;
    private final int minute;

    /**
     * Constructs an Event with the specified description and minute.
     *
     * @param description A brief description of the event.
     * @param minute The minute in which the event occurred.
     */
    public Event(String description, int minute){
        this.description = description;
        this.minute = minute;
    }

    /**
     * Returns the description of the event.
     *
     * @return Event description.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Returns the minute when the event occurred.
     *
     * @return Match minute.
     */
    @Override
    public int getMinute() {
        return this.minute;
    }

    /**
     * Exports the event to a JSON-like structure and prints it to the console.
     *
     * @throws IOException if an I/O error occurs (not thrown in this implementation).
     */
    @Override
    public void exportToJson() throws IOException {
        String json = "event : {\n" +
                "  \"type\": \"PlayerEvent\",\n" +
                "  \"description\": \"" + getDescription() + "\",\n" +
                "  \"minute\": " + getMinute() + ",\n" +
                "}";
        System.out.println(json);
    }

    /**
     * Returns a string representation of the event.
     *
     * @return Formatted string with minute and description.
     */
    public String toString(){
        return minute + " | " + description;
    }

    /**
     * Checks whether this event is equal to another object.
     *
     * @param obj The object to compare with.
     * @return {@code true} if the events are the same; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Event)) {
            return false;
        }
        Event event = (Event) obj;
        return minute == event.minute && description.equals(event.description);
    }
}
