package com.mng.testmaker.utils.controlTest;


public class indexSuite {

    private String idExecSuite;
    private String suite;
    
    public indexSuite(String c_idExecSuite, String c_suite) {
        this.idExecSuite = c_idExecSuite;
    	this.suite = c_suite;
    }
    
    public void setidExecSuite (String c_idExecSuite) { 
        this.idExecSuite = c_idExecSuite; 
    }
    
    public void setSuite(String c_suite) { 
        this.suite = c_suite; 
    }
    
    public String getidExecSuite() { 
        return this.idExecSuite; 
    }
    
    public String getSuite() { 
        return this.suite; 
    }
}

