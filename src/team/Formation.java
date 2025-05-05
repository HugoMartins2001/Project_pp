package team;

import com.ppstudios.footballmanager.api.contracts.team.IFormation;

import java.io.Serializable;

public class Formation implements IFormation {

    private String displayName;
    private String formation;

    //TODO ACABAR PORQUE FUI DORMIR
    @Override
    public int getTacticalAdvantage(IFormation iFormation) {
        if(this.formation == null && iFormation == null){
            throw new IllegalArgumentException("Formations does not exist to compare");
        }
        if(this.formation.equals(iFormation)) {
            return 0;
        }
        return x;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }
}
