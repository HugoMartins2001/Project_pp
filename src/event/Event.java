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
import player.Player;

import java.io.IOException;

public class Event implements IEvent{
    private final String description;
    private final int minute;
    
    public Event(String description, int minute){
        this.description = description;
        this.minute = minute;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getMinute() {
        return this.minute;
    }


    @Override
    public void exportToJson() throws IOException {
        String json = "event : {\n" +
                "  \"type\": \"PlayerEvent\",\n" +
                "  \"description\": \"" + getDescription() + "\",\n" +
                "  \"minute\": " + getMinute() + ",\n" +
                "}";

        System.out.println(json);
    }

    public String toString(){
        return minute + " | " + description;
    }

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