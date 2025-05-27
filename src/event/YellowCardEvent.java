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

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

import java.io.IOException;

public class YellowCardEvent extends PlayerEvent {

    public YellowCardEvent(IPlayer player, int minute) {
        super(player, minute, "⚠\uFE0F Yellow card to " + player.getName() + " at " + minute + " minutes");
    }
}
