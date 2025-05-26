package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

public class ShotsOnGoalEvent extends PlayerEvent{

    public ShotsOnGoalEvent(IPlayer player, int minute) {
        super( player, minute,  player.getName() + " shot the goal at" + minute + " minutes");
    }

    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
