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

public class EventManager implements IEventManager {

    private IEvent[] events = new IEvent[1];
    private int eventCount = 0;

    @Override
    public void addEvent(IEvent ievent) {
        if (ievent == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if(findEvent(ievent) == -1){
            if(eventCount == events.length) {
                expandEvent();
            }
            events[eventCount++] = ievent;
        }
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

    @Override
    public int getEventCount() {
        return eventCount;
    }

}
