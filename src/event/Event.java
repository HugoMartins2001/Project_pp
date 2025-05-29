/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;

import java.io.IOException;

/**
 * Represents a generic event in a football match.
 * Implements the {@link IEvent} interface and provides common behavior
 * such as description, minute tracking, JSON export, and equality checking.
 *
 * <p>This class can be extended to define more specific types of events.</p>
 *
 * <p>Example description format: "-> Match ended", "-> Goal by Player X", etc.</p>
 *
 */
public class Event implements IEvent {
    private final String description;
    private final int minute;

    /**
     * Constructs a generic event with a description and the minute it occurred.
     *
     * @param description A textual description of the event.
     * @param minute The minute during the match when the event occurred.
     */
    public Event(String description, int minute) {
        this.description = description;
        this.minute = minute;
    }

    /**
     * Gets the description of the event.
     *
     * @return The event description.
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the minute at which the event occurred.
     *
     * @return The event minute.
     */
    @Override
    public int getMinute() {
        return this.minute;
    }

    /**
     * Exports the event data in a JSON-like format to the console.
     * This method is primarily for debugging or demonstration purposes.
     *
     * @throws IOException If an I/O error occurs during export.
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
     * Returns a string representation of the event in the format "minute | description".
     *
     * @return A string describing the event.
     */
    @Override
    public String toString() {
        return minute + " | " + description;
    }

    /**
     * Compares this event to another object for equality.
     * Two events are considered equal if they have the same minute and description.
     *
     * @param obj The object to compare to.
     * @return true if the events are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Event)) return false;

        Event event = (Event) obj;
        return this.minute == event.minute && this.description.equals(event.description);
    }
}
