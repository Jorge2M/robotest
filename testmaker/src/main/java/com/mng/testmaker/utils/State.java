package com.mng.testmaker.utils;

public enum State  {
    Undefined(LevelState.Reservado, 0, "black"),
    Ok(LevelState.OK, 1, "green"),
    Info(LevelState.INFO, 2, "blue"), 
    //Info_NoHardcopy(LevelState.INFO, 3, "blue"), 
    Warn(LevelState.Warn, 4, "#8000ff"),
    //Warn_NoHardcopy(LevelState.Warn, 5, "#8000ff"), 
    Defect(LevelState.Defect, 6, "crimson"),
    Skip(LevelState.SKIP, 8, "darkGrey"),
    Nok(LevelState.NOK, 11, "red"),
    Stopped(LevelState.Stopped, 11, "red");
    
    private final LevelState levelState;
    private final int idNumeric;
    private final String colorCss;
 
    State(LevelState levelState, int idNumeric, String colorCss) {
        this.levelState = levelState;
        this.idNumeric = idNumeric;
        this.colorCss = colorCss;
    }
    
    public int getIdNumerid() {
        return this.idNumeric;
    }
    
    public LevelState getLevel() {
        return this.levelState;
    }
    
    public int getCriticity() {
        return this.idNumeric;
    }
    
    public boolean isMoreCriticThan(State state) {
    	return (idNumeric > state.getIdNumerid());
    }
    
    public static State getMoreCritic(State state1, State state2) {
    	if (state1.isMoreCriticThan(state2)) {
    		return state1;
    	}
    	
    	return state2;
    }
    
    public String getColorCss() {
        return this.colorCss;
    }
    
    public static State getState(int idNumericI) {
        for (State estado : State.values()) {
            if (estado.getIdNumerid() == idNumericI) {
                return estado;
            }
        }
        
        return null;
    }
}
