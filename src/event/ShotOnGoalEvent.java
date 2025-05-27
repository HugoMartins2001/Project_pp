package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

public class ShotOnGoalEvent extends PlayerEvent {

    public ShotOnGoalEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " shot on goal at" + minute + " minutes");
    }
}
