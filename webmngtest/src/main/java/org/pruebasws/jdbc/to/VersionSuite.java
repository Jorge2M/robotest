package org.pruebasws.jdbc.to;

/**
 * Bean associated to row in VERSIONS_SUITE
 */
@SuppressWarnings("javadoc")
public class VersionSuite {
    String suite;
    String version;
    String description;
    String listChannels;
    
    public String getSuite() {
        return this.suite;
    }
    
    public String getVersion() {
        return this.version;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getListChannels() {
        return this.listChannels;
    }
    
    public void setSuite(String suite) {
        this.suite = suite;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setListChannels(String listChannels) {
        this.listChannels = listChannels;
    }
}
