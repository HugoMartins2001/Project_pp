package league;

import com.ppstudios.footballmanager.api.contracts.league.ILeague;
import com.ppstudios.footballmanager.api.contracts.league.ISeason;

import java.io.IOException;

/**
 * Represents a football league that can contain multiple seasons.
 * Implements the {@link ILeague} interface.
 * Provides methods to add, remove and retrieve seasons.
 *
 * <p>Each league has a name and a dynamically expanding array of seasons.</p>
 *
 * <p>Seasons are unique by year, and cannot be null or duplicated.</p>
 *
 * @author Rúben Tiago Martins Pereira
 * number 8230162
 * class LsircT2
 * @author Hugo Leite Martins
 * number 8230273
 * class LsircT2
 *
 */
public class League implements ILeague {
    private String name;
    private ISeason[] seasons = new ISeason[1];
    private int seasonCount = 0;

    /**
     * Constructs a League with the specified name.
     *
     * @param name the name of the league
     * @throws IllegalArgumentException if the name is null or empty
     */
    public League(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("League name cannot be null or empty");
        }
        this.name = name;
    }

    /**
     * Returns the name of the league.
     *
     * @return the league name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns all the seasons registered in the league.
     *
     * @return an array of seasons
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
     * Adds a new season to the league if it doesn't already exist.
     *
     * @param season the season to add
     * @return true if the season was added successfully
     * @throws IllegalArgumentException if the season is null or already exists
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
        System.out.println("Season added: " + season.getName() + " | year: " + season.getYear());
        return true;
    }

    /**
     * Removes and returns the season corresponding to the given year.
     *
     * @param year the year of the season to remove
     * @return the removed season
     * @throws IllegalArgumentException if the season is not found
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
     * Returns the season corresponding to the given year.
     *
     * @param year the year of the season to retrieve
     * @return the season for the specified year
     * @throws IllegalArgumentException if the season is not found
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
     * Checks whether a season is already present.
     *
     * @param iSeason the season to find
     * @return index of the season if found, -1 otherwise
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
     * Expands the internal array used to store seasons.
     */
    private void expandSeasonArray() {
        ISeason[] newSeasons = new ISeason[seasons.length * 2];
        for (int i = 0; i < seasons.length; i++) {
            newSeasons[i] = seasons[i];
        }
        seasons = newSeasons;
    }

    /**
     * Exports the league and its seasons to a JSON representation.
     * (Currently not implemented.)
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void exportToJson() throws IOException {
        // To be implemented if required
    }

    /**
     * Returns a string representation of the league.
     *
     * @return a string containing the league name and all seasons
     */
    @Override
    public String toString() {
        String s = "League Name: " + name + "\n";
        s += "Seasons: \n";
        for (int i = 0; i < seasonCount; i++) {
            s += this.seasons[i].toString();
        }
        return s;
    }
}
