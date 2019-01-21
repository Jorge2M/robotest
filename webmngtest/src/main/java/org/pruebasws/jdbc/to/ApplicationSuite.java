package org.pruebasws.jdbc.to;

/**
 * Bean associated to row in table APPLICATIONS_SUITE
 */
@SuppressWarnings("javadoc")
public class ApplicationSuite {
    String suite;
    String application;
    String listChannels;
    
    public String getSuite() {
        return this.suite;
    }
    
    public String getApplication() {
        return this.application;
    }
    
    public String getListChannels() {
        return this.listChannels;
    }
    
    public void setSuite(String suite) {
        this.suite = suite;
    }
    
    public void setApplication(String application) {
        this.application = application;
    }
    
    public void setListChannels(String listChannels) {
        this.listChannels = listChannels;
    }
}
