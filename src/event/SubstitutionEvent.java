package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;

public class SubstitutionEvent extends Event {
    private final IPlayer playerIn;
    private final IPlayer playerOut;

    public SubstitutionEvent(IPlayer playerOut, IPlayer playerIn, int minute) {
        super("\uD83D\uDD04 Substitution: " + playerOut.getName() + " out, " + playerIn.getName() + " in", minute);
        this.playerOut = playerOut;
        this.playerIn = playerIn;
    }

    public IPlayer getPlayerOut() {
        return playerOut;
    }

    public IPlayer getPlayerIn() {
        return playerIn;
    }

    @Override
    public void exportToJson() {
    }
}
