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
import com.ppstudios.footballmanager.api.contracts.team.ITeam;
import data.Exporter;
import player.Player;

import java.io.IOException;

public class CornerKickEvent extends PlayerEvent {

    public CornerKickEvent(IPlayer player, int minute) {
        super(player, minute, "\uD83E\uDD7E Corner kick by " + player.getName() + " at " + minute + " minutes");
    }
}
