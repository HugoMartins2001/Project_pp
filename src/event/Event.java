package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import java.io.IOException;

/**
 *
 * @author hugol
 */
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
