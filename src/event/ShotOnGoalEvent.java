package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

public class ShotOnGoalEvent extends PlayerEvent {

    public ShotOnGoalEvent(IPlayer player, int minute) {
        super(player, minute, "\uD83E\uDDB6 " + player.getName() + " shot the goal at" + minute + " minutes");
    }

    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
