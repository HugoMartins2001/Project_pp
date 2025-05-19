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

import com.ppstudios.footballmanager.api.contracts.league.IStanding;
import com.ppstudios.footballmanager.api.contracts.team.ITeam;

public class Standing implements IStanding {


    @Override
    public ITeam getTeam() {
        return null;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public void addPoints(int i) {

    }

    @Override
    public void addWin(int i) {

    }

    @Override
    public void addDraw(int i) {

    }

    @Override
    public void addLoss(int i) {

    }

    @Override
    public int getWins() {
        return 0;
    }

    @Override
    public int getDraws() {
        return 0;
    }

    @Override
    public int getLosses() {
        return 0;
    }

    @Override
    public int getTotalMatches() {
        return 0;
    }

    @Override
    public int getGoalScored() {
        return 0;
    }

    @Override
    public int getGoalsConceded() {
        return 0;
    }

    @Override
    public int getGoalDifference() {
        return 0;
    }
}
