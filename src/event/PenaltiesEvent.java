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

public class PenaltiesEvent extends PlayerEvent {

    public PenaltiesEvent(IPlayer player, int minute) {
        super(player, minute, "-> Penalty by " + player.getName() + " at " + minute + " minutes");
    }
}
