package event;

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import java.io.IOException;

/**
 *
 * @author hugol
 */
public class GoalEvent extends Event implements IGoalEvent{

    private IPlayer player;

    public GoalEvent(IPlayer player, String description, int minute) {
        super(description,minute);
        this.player = player;
    }

    @Override
    public IPlayer getPlayer() {
        return this.player;
    }

    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
