package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

public class FoulEvent extends PlayerEvent {

    public FoulEvent(IPlayer player, int minute) {
        super(player, minute, "-> Foul commited by " + player.getName() + " at " + minute + " minutes");
    }
}
