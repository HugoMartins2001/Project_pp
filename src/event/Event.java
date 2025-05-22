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
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class Event implements IEvent{
    private String description;
    private int minute;
    
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String toString(){
        return "Event: {" + "description=" + description + ", minute=" + minute + '}';
    }
}