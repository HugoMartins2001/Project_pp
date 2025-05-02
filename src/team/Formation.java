package team;

import com.ppstudios.footballmanager.api.contracts.team.IFormation;

import java.io.Serializable;

public class Formation implements IFormation {

    private String displayName;
    private String formation;

    //TODO ACABAR PORQUE FUI DORMIR
    @Override
    public int getTacticalAdvantage(IFormation iFormation) {
        if(iFormation == null){
            throw new IllegalArgumentException("Formation does not exist");
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

    private String getFormation(){
        return formation;
    }
}
