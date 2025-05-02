package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import com.ppstudios.footballmanager.api.contracts.event.IEventManager;

/**
 *
 * @author hugol
 */
public class EventManager implements IEventManager {

    private static final int MAX_EVENTS = 20;
    private IEvent[] events = new IEvent[MAX_EVENTS];
    private int eventCount = 0;

    @Override
    public void addEvent(IEvent ievent) {
        if (ievent == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (eventCount >= MAX_EVENTS) {
            throw new IllegalStateException("Event list is full");
        }
        if(findEvent(ievent) == -1){
            throw new IllegalArgumentException("Event already exists");
        }
        if (eventCount == events.length) {
            expandEvent();
        }

        events[eventCount] = ievent;
        eventCount++;
        System.out.println("Event added: " + ievent);
    }

    private int findEvent(IEvent ievent){
        for(int i = 0 ; i < eventCount; i++){
            if(events[i].equals(ievent)){
                return i;
            }
        }

        return -1;
    }

    private void expandEvent(){
        IEvent[] newEvents = new IEvent[events.length * 2];
        for(int i = 0; i < events.length; i++){
            newEvents[i] = events[i];
        }

        events = newEvents;
    }

    @Override
    public IEvent[] getEvents() {
        IEvent[] result = new IEvent[eventCount];
        for(int i = 0; i < eventCount; i++){
            result[i] = events[i];
        }

        return result;
    }

    @Override
    public int getEventCount() {
        return eventCount;
    }

}
