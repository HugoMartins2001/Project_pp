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

public class League implements ILeague {
    private String name;
    private ISeason[] seasons = new ISeason[1];
    private int seasonCount = 0;

    public League(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("League name cannot be null or empty");
        }
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ISeason[] getSeasons() {
        ISeason[] activeSeasons = new ISeason[seasonCount];
        for (int i = 0; i < seasonCount; i++) {
            activeSeasons[i] = seasons[i];
        }
        return activeSeasons;
    }


    @Override
    public boolean createSeason(ISeason season) {
        if (findSeason(season) != -1 || season == null) {
            throw new IllegalArgumentException("The season already exists or is null");
        }
        if (seasonCount == seasons.length) {
            expandSeasonArray();
        }

        seasons[seasonCount] = season;
        seasonCount++;
        System.out.println("Season added: " + season);
        return true;
    }

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

    @Override
    public ISeason getSeason(int year) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].getYear() == year) {
                return seasons[i];
            }
        }

        throw new IllegalArgumentException("Season not found");

    }


    private int findSeason(ISeason iSeason) {
        for (int i = 0; i < seasonCount; i++) {
            if (seasons[i].equals(iSeason)) {
                return i;
            }
        }
        return -1;
    }

    private void expandSeasonArray() {
        ISeason[] newSeasons = new ISeason[seasons.length * 2];
        for (int i = 0; i < seasons.length; i++) {
            newSeasons[i] = seasons[i];
        }
        seasons = newSeasons;
    }

    @Override
    public void exportToJson() throws IOException {

    }

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
