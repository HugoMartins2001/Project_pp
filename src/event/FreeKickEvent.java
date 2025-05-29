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

public class FreeKickEvent extends PlayerEvent {

    public FreeKickEvent(IPlayer player, int minute) {
        super(player, minute, "-> Free kick by " + player.getName() + " at " + minute + " minutes");

    }
}
