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
import player.Player;

import java.io.IOException;

/**
 * Represents a generic football match event associated with a specific player.
 * Inherits from the {@link Event} class and adds information about the player
 * involved.
 *
 * This class is extended by more specific player-related events such as goals,
 * fouls, cards, and other match actions.
 *
 * The event description and minute are passed to the parent {@link Event}
 * class. The player instance is stored and accessible for further use.
 *
 * Overrides {@code exportToJson()} to include player details in the export.
 *
 */
public class PlayerEvent extends Event {

    private IPlayer player;

    /**
     * Constructs a new PlayerEvent.
     *
     * @param player The player involved in the event.
     * @param minute The minute the event occurred.
     * @param description A description of the event.
     */
    public PlayerEvent(IPlayer player, int minute, String description) {
        super(description, minute);
        this.player = player;
    }

    /**
     * Returns the player involved in the event.
     *
     * @return The player object.
     */
    public IPlayer getPlayer() {
        return this.player;
    }

    /**
     * Returns the event description.
     *
     * @return The event description string.
     */
    @Override
    public String getDescription() {
        return super.getDescription();
    }

    /**
     * Exports the event as a JSON-like string to the console, including player
     * name and club code.
     *
     * @throws IOException If any I/O error occurs (not used here).
     */
    @Override
    public void exportToJson() throws IOException {
        String json = "event : {\n"
                + "  \"type\": \"PlayerEvent\",\n"
                + "  \"description\": \"" + getDescription() + "\",\n"
                + "  \"minute\": " + getMinute() + ",\n"
                + "  \"player\": {\n"
                + "    \"name\": \"" + player.getName() + "\",\n"
                + "    \"clubCode\": \"" + ((Player) player).getClub() + "\"\n"
                + "  }\n"
                + "}";
        System.out.println(json);
    }

    /**
     * Compares this PlayerEvent to another object for equality. Two
     * PlayerEvents are equal if they have the same description, minute, and
     * player.
     *
     * @param obj The object to compare.
     * @return True if the events are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PlayerEvent)) {
            return false;
        }

        PlayerEvent playerEvent = (PlayerEvent) obj;
        return super.equals(obj) && player.equals(playerEvent.player);
    }
}
