package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import player.Player;

import java.io.IOException;

public class ShotEvent extends PlayerEvent {

    public ShotEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " shot at " + minute + " minutes");
    }
}
