package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import java.io.IOException;

/**
 *
 * @author hugol
 */
public class Event implements IEvent{
    
    private String Description;
    private int Minute;
    
    public Event(String Description, int Minute){
        this.Description = Description;
        this.Minute = Minute;
    }

    @Override
    public String getDescription() {
        return this.Description;
    }

    @Override
    public int getMinute() {
        return this.Minute;
    }

    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
