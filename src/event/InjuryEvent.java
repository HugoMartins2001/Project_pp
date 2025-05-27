package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

public class InjuryEvent extends PlayerEvent {

    public InjuryEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " got injured at " + minute + " minutes");
    }
}
