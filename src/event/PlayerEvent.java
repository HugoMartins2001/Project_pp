package event;

import com.ppstudios.footballmanager.api.contracts.player.IPlayer;
import java.io.IOException;

public class PlayerEvent extends Event{
    private IPlayer player;

    public PlayerEvent(IPlayer player, int minute, String description) {
        super(description, minute);
        this.player = player;
    }


    public IPlayer getPlayer() {
        return this.player;
    }


    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public void exportToJson() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
