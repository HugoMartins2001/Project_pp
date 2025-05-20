/*
 * Name: <Rúben Tiago Martins Pereira>
 * Number: <8230162>
 * Class: <LsircT2>
 *
 * Name: <Hugo Leite Martins>
 * Number: <8230273>
 * Class: <LsircT2>
 */
package team;

import com.ppstudios.footballmanager.api.contracts.team.IFormation;

public class Formation implements IFormation {

    private String displayName;
    private int forwards;
    private int defenders;
    private int midfielders;

    public int getDefenders() {
        return defenders;
    }

    public int getMidfielders() {
        return midfielders;
    }

    public int getForwards() {
        return forwards;
    }

    @Override
    public int getTacticalAdvantage(IFormation formation) {
        if (this.displayName == null || formation.getDisplayName() == null) {
            throw new IllegalStateException("Formations are not setted!");
        }

        if (this.displayName.equals(formation.getDisplayName())) {
            return 0;
        }

        return calculateAdvantageTacticalValue((Formation) formation);
    }

    private int calculateAdvantageTacticalValue(Formation formation) {
        int[] homeTeam = {this.forwards, this.midfielders, this.defenders};
        int[] awayTeam = {formation.getForwards(), formation.getMidfielders(), formation.getDefenders()};

        for (int i = 0; i < 3; i++) {
            if (homeTeam[i] > awayTeam[i]) return 1;
            if (homeTeam[i] < awayTeam[i]) return -1;
        }
        return 0;
    }


    @Override
    public String getDisplayName() {
        return displayName;
    }
}