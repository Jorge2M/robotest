package org.pruebasws.jdbc.to;

/**
 * Bean associated to row in table BROWSERS_SUITE
 */
@SuppressWarnings("javadoc")
public class BrowserSuite {
    String suite;
    String browser;
    String listChannels;
    
    public String getSuite() {
        return this.suite;
    }
    
    public String getBrowser() {
        return this.browser;
    }
    
    public String getListChannels() {
        return this.listChannels;
    }
    
    public void setSuite(String suite) {
        this.suite = suite;
    }
    
    public void setBrowser(String browser) {
        this.browser = browser;
    }
    
    public void setListChannels(String listChannels) {
        this.listChannels = listChannels;
    }
}
