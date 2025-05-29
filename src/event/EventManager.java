package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;

/**
 * Manages football match events.
 * <p>
 * Stores events dynamically in an internal array, ensures no duplicate events,
 * and allows access to events in their insertion order or sorted by match minute.
 * </p>
 *
 * <p>
 * This class implements {@link IEventManager}.
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
 * @see IEventManager
 */
public class EventManager implements IEventManager {

    private IEvent[] events = new IEvent[1];
    private int eventCount = 0;

    /**
     * Adds a new event to the manager, ensuring no duplicates.
     *
     * @param ievent The event to add.
     * @throws IllegalArgumentException if the event is null.
     */
    @Override
    public void addEvent(IEvent ievent) {
        if (ievent == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (findEvent(ievent) == -1) {
            if (eventCount == events.length) {
                expandEvent();
            }
            events[eventCount++] = ievent;
        }
    }

    /**
     * Checks if the given event is already stored.
     *
     * @param ievent The event to find.
     * @return The index of the event if found, or -1 otherwise.
     */
    private int findEvent(IEvent ievent) {
        for (int i = 0; i < eventCount; i++) {
            if (events[i].equals(ievent)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Doubles the size of the internal events array.
     */
    private void expandEvent() {
        IEvent[] newEvents = new IEvent[events.length * 2];
        for (int i = 0; i < events.length; i++) {
            newEvents[i] = events[i];
        }
        events = newEvents;
    }

    /**
     * Returns all stored events in insertion order.
     *
     * @return An array of stored events.
     */
    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[eventCount];
        for (int i = 0; i < eventCount; i++) {
            result[i] = events[i];
        }
        return result;
    }

    /**
     * Returns a copy of the stored events ordered by minute (ascending).
     *
     * @return An array of events sorted by match minute.
     */
    public IEvent[] getEventsOrderedByMinute() {
        IEvent[] result = new IEvent[eventCount];
        for (int i = 0; i < eventCount; i++) {
            result[i] = events[i];
        }
        for (int i = 0; i < eventCount - 1; i++) {
            for (int j = 0; j < eventCount - i - 1; j++) {
                if (result[j].getMinute() > result[j + 1].getMinute()) {
                    IEvent temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }
        return result;
    }

    /**
     * Returns the number of stored events.
     *
     * @return The total number of events.
     */
    @Override
    public int getEventCount() {
        return eventCount;
    }
}
