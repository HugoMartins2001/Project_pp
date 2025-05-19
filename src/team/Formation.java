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

    //TODO falta fazer o metodo e a logica
    @Override
    public int getTacticalAdvantage(IFormation formation) {
        if(this.displayName == null || formation.getDisplayName() == null){
            throw new IllegalStateException("Formations are not setted!");
        }
        if(this.displayName.equals(formation.getDisplayName())) {
            return 0;
        }
        if(this.defenders > ((Formation)formation).getDefenders()) {
            return 1;
        }
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

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
