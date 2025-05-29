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

public class SubstitutionEvent extends Event {
    private final IPlayer playerIn;
    private final IPlayer playerOut;

    public SubstitutionEvent(IPlayer playerOut, IPlayer playerIn, int minute) {
        super("-> Substitution: " + playerOut.getName() + " out, " + playerIn.getName() + " in", minute);
        this.playerOut = playerOut;
        this.playerIn = playerIn;
    }

    public IPlayer getPlayerOut() {
        return playerOut;
    }

    public IPlayer getPlayerIn() {
        return playerIn;
    }
}
