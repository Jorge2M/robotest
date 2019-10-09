package com.mng.testmaker.utils;

public enum State  {
    Undefined(LevelState.Reservado, 0, "black"),
    Ok(LevelState.OK, 1, "green"),
    Info(LevelState.INFO, 2, "blue"), 
    Warn(LevelState.Warn, 4, "#8000ff"),
    Defect(LevelState.Defect, 6, "crimson"),
    Skip(LevelState.SKIP, 8, "darkGrey"),
    Nok(LevelState.NOK, 11, "red"),
    Stopped(LevelState.Stopped, 11, "red");
    
    private final LevelState levelState;
    private final int criticity;
    private final String colorCss;
 
    State(LevelState levelState, int criticity, String colorCss) {
        this.levelState = levelState;
        this.criticity = criticity;
        this.colorCss = colorCss;
    }
    
    public LevelState getLevel() {
        return this.levelState;
    }
    
    public int getCriticity() {
        return this.criticity;
    }
    
    public boolean isMoreCriticThan(State state) {
    	return (criticity > state.getCriticity());
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
    
    public static State getState(int criticity) {
        for (State estado : State.values()) {
            if (estado.getCriticity() == criticity) {
                return estado;
            }
        }
        
        return null;
    }
}
