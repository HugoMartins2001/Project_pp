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

public class ShotEvent extends PlayerEvent {

    public ShotEvent(IPlayer player, int minute) {
        super(player, minute, "-> " + player.getName() + " shot at " + minute + " minutes");
    }
}
