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

import com.ppstudios.footballmanager.api.contracts.event.IGoalEvent;
import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

import java.io.IOException;

public class GoalEvent extends Event implements IGoalEvent{

    private IPlayer player;

    public GoalEvent(IPlayer player, String description, int minute, ITeam team) {
        super(description,minute,team);
        this.player = player;
    }

    @Override
    public IPlayer getPlayer() {
        return this.player;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + "Goal by: " + player.getName() + " at " + getMinute() + " minutes";
    }

    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
