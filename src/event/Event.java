package event;

import com.ppstudios.footballmanager.api.contracts.event.IEvent;
import java.io.IOException;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

/**
 *
 * @author hugol
 */
public class Event implements IEvent{
    
    private String description;
    private int minute;
    private ITeam team;
    
    public Event(String description, int minute, ITeam team){
        this.description = description;
        this.minute = minute;
        this.team = team;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public int getMinute() {
        return this.minute;
    }

    public ITeam getTeam(){
        return this.team;
    }

    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
