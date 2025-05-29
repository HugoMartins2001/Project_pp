/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
package league;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;

import java.io.IOException;

/**
 * Represents a football league composed of multiple seasons.
 * Implements the {@link ILeague} interface.
 *
 * <p>This class supports creating, retrieving, and removing seasons,
 * as well as dynamically resizing the internal storage of seasons.</p>
 *
 * <p>Ensures no duplicate or null seasons can be added.</p>
 *
 *
 */
public class League implements ILeague {
    private String name;
    private ISeason[] seasons = new ISeason[1];
    private int seasonCount = 0;

    /**
     * Constructs a League with a given name.
     *
     * @param name The name of the league.
     * @throws IllegalArgumentException If the name is null or empty.
     */
    public League(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("League name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Constructs a League with a name and an initial list of seasons.
     *
     * @param name    The name of the league.
     * @param seasons An array of seasons to initialize the league with.
     * @throws IllegalArgumentException If name or seasons are null.
     */
    public League(String name, ISeason[] seasons) {
        if (name == null || seasons == null) {
            throw new IllegalArgumentException("League cannot be null or empty");
        }
        this.name = name;
        this.seasons = new ISeason[seasons.length];
        for (int i = 0; i < seasons.length; i++) {
            if (seasons[i] != null) {
                this.seasons[seasonCount++] = seasons[i];
            }
        }
    }

    /**
     * Returns the name of the league.
     *
     * @return The league name.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns an array of all seasons currently in the league.
     *
     * @return An array of {@link ISeason} objects.
     */
    @Override
    public ISeason[] getSeasons() {
        ISeason[] activeSeasons = new ISeason[seasonCount];
        for (int i = 0; i < seasonCount; i++) {
            activeSeasons[i] = seasons[i];
        }
        return activeSeasons;
    }

    /**
     * Adds a new season to the league if it does not already exist.
     *
     * @param season The season to add.
     * @return true if the season was added successfully.
     * @throws IllegalArgumentException If the season already exists or is null.
     */
    @Override
    public boolean createSeason(ISeason season) {
        if (findSeason(season) != -1 || season == null) {
            throw new IllegalArgumentException("The season already exists or is null");
        }
        if (seasonCount == seasons.length) {
            expandSeasonArray();
        }

        seasons[seasonCount++] = season;
        System.out.println();
        System.out.println("Season added: " + season.getName() + " | year: " + season.getYear());
        return true;
    }

    /**
     * Removes a season from the league by year.
     *
     * @param year The year of the season to remove.
     * @return The removed season.
     * @throws IllegalArgumentException If the season is not found.
     */
    @Override
    public ISeason removeSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                ISeason removedSeason = seasons[i];
                for (int j = i; j < seasonCount - 1; j++) {
                    seasons[j] = seasons[j + 1];
                }
                seasons[seasonCount - 1] = null;
                seasonCount--;
                return removedSeason;
            }
        }
        throw new IllegalArgumentException("Season not found");
    }

    /**
     * Retrieves a season by its year.
     *
     * @param year The year of the season.
     * @return The corresponding {@link ISeason} object.
     * @throws IllegalArgumentException If the season is not found.
     */
    @Override
    public ISeason getSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                return seasons[i];
            }
        }
        throw new IllegalArgumentException("Season not found");
    }

    /**
     * Finds the index of a season in the array.
     *
     * @param iSeason The season to find.
     * @return The index if found, -1 otherwise.
     */
    private int findSeason(ISeason iSeason) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].equals(iSeason)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Expands the internal array to hold more seasons.
     */
    private void expandSeasonArray() {
        ISeason[] newSeasons = new ISeason[seasons.length * 2];
        for (int i = 0; i < seasons.length; i++) {
            newSeasons[i] = seasons[i];
        }
        seasons = newSeasons;
    }

    /**
     * Exports the league data to JSON format.
     * (Currently not implemented.)
     *
     * @throws IOException If export fails.
     */
    @Override
    public void exportToJson() throws IOException {
        // Not implemented
    }

    /**
     * Returns a string representation of the league and its seasons.
     *
     * @return A formatted string containing the league name and seasons.
     */
    @Override
    public String toString() {
        String s = "League Name: " + name + "\n";
        s += "Seasons: " + "\n";
        for (int i = 0; i < seasonCount; i++) {
            s += this.seasons[i].toString();
        }
        return s;
    }
}
