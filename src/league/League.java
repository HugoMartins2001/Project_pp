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


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ISeason[] getSeasons() {
        return this.seasons;
    }

    @Override
    public boolean createSeason(ISeason iSeason) {
        if(iSeason == null){
            throw new IllegalArgumentException("Season cannot be null");
        }
        if(iSeason.getYear() <= 0){
            throw new IllegalArgumentException("Season year must be greater than 0");
        }
        if(findSeason(iSeason) != -1){
            throw new IllegalArgumentException("Season already exists");
        }
        if(seasonCount == seasons.length){
            expandSeason();
        }


        seasons[seasonCount] = iSeason;
        seasonCount++;
        System.out.println("Season added: " + iSeason);
        return true;
    }

    @Override
    public ISeason removeSeason(int year) {
        for(int i = 0; i < seasonCount; i++){
            if(seasons[i].getYear() == year){
                ISeason removedSeason = seasons[i];
                for(int j = i; j < seasonCount - 1; j++){
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

        for(int i = 0; i < seasonCount; i++){
            if(seasons[i].getYear() == year){
                return seasons[i];
            }
        }

    throw new IllegalArgumentException("Season not found");

    }


    private int findSeason(ISeason iSeason){
        for(int i = 0; i < seasonCount; i++){
            if(seasons[i].equals(iSeason)){
                return i;
            }
        }

        return -1;
    }

    private void expandSeason(){
        ISeason[] newSeasons = new ISeason[seasons.length * 2];
        for(int i = 0; i < seasons.length; i++){
            newSeasons[i] = seasons[i];
        }

        seasons = newSeasons;
    }

    @Override
    public void exportToJson() throws IOException {

    }

    @Override
    public String toString(){
        String s = "League Name: " + name + "\n";
               s += "Seasons: " + "\n";

               for(int i = 0; i < seasonCount; i++){
                   s += this.seasons[i].toString();
               }


               return s;
    }
}
