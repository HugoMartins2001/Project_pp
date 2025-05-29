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
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;

/**
 * Manages a collection of football match events.
 * Implements the {@link IEventManager} interface.
 *
 * <p>This class allows adding, retrieving, and ordering events by the minute in which they occurred.</p>
 * <p>Duplicate events are ignored (based on {@code equals}).</p>
 *
 */
public class EventManager implements IEventManager {

    private IEvent[] events = new IEvent[1];
    private int eventCount = 0;

    /**
     * Adds a new event to the manager if it does not already exist.
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
     * Finds the index of an event in the current list.
     *
     * @param ievent The event to search for.
     * @return The index if found; -1 otherwise.
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
     * Expands the internal array to allow storing more events.
     */
    private void expandEvent() {
        IEvent[] newEvents = new IEvent[events.length * 2];
        for (int i = 0; i < events.length; i++) {
            newEvents[i] = events[i];
        }
        events = newEvents;
    }

    /**
     * Returns an array containing all stored events.
     *
     * @return An array of {@link IEvent} instances.
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
     * Returns the stored events ordered by the minute they occurred.
     *
     * @return An array of events sorted in ascending order by minute.
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
     * Returns the number of events stored in the manager.
     *
     * @return The total count of events.
     */
    @Override
    public int getEventCount() {
        return eventCount;
    }
}
