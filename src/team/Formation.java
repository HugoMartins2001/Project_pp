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

import com.ppstudios.footballmanager.api.contracts.player.IPlayerPosition;
import com.ppstudios.footballmanager.api.contracts.team.IFormation;

public class Formation implements IFormation {

    private String displayName;
    private int forwards;
    private int defenders;
    private int midfielders;

    public Formation(String displayName, int defenders, int midfielders, int forwards) {
        if (defenders + midfielders + forwards != 10) {
            throw new IllegalArgumentException("A formation must have exactly 10 players (excluding the goalkeeper).");
        }
        this.displayName = displayName;
        this.defenders = defenders;
        this.midfielders = midfielders;
        this.forwards = forwards;
    }

    public int getDefenders() {
        return defenders;
    }

    public int getMidfielders() {
        return midfielders;
    }

    public int getForwards() {
        return forwards;
    }

    public String toString() {
        return defenders + "-" + midfielders + "-" + forwards;
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

    public int getPositionFormation(IPlayerPosition playerPosition) {
        String description = playerPosition.getDescription();

        if (description.equals("Defender")) {
            return this.defenders;
        } else if (description.equals("Midfielder")) {
            return this.midfielders;
        } else if (description.equals("Forward")) {
            return this.forwards;
        } else {
            return 1;
        }
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