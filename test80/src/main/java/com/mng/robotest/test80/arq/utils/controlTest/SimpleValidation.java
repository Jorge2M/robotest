package com.mng.robotest.test80.arq.utils.controlTest;

import com.mng.robotest.test80.arq.utils.State;

@SuppressWarnings("javadoc")
public class SimpleValidation {

    private int id;
    private State result;
    
    public SimpleValidation() {}
    
    public SimpleValidation(int id, State result) {
        this.id = id;
        this.result = result;
    }
    
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public State getResult() {
        return this.result;
    }
    
    public void setResult(State result) {
        this.result = result;
    }
}
