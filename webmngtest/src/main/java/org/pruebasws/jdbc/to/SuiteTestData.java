package org.pruebasws.jdbc.to;

import java.util.ArrayList;
import java.util.Arrays;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.testmaker.conf.Channel;

/**
 * Bean associated to TESTS_SUITES
 */
@SuppressWarnings("javadoc")
public class SuiteTestData {
    String suite;
    String channel;
    String idVersionActual;
    ArrayList<VersionSuite> versionList;
    String description;
    String idApplicationActual;
    ArrayList<ApplicationSuite> listApplications;
    String idBrowser;
    ArrayList<BrowserSuite> listBrowsers;
    String netTraffic;
    String urlBase;
    int filtroPagos;
    String listPagos;
    int filtroTCases;
    String listTCases;
    int filtroPaises;
    String listPaises;
    int filtroLineas;
    String listLineas;

    public String getSuite() {
        return this.suite;
    }
    
    public String getChannel() {
        return this.channel;
    }
    
    public Channel getChannelType() {
        return (Channel.valueOf(getChannel()));
    }
    
    public String getVersionActual() {
        return this.idVersionActual;
    }
    
    public ArrayList<VersionSuite> getVersionList() {
        return this.versionList;
    }
    
    public ArrayList<VersionSuite> getVersionChannelList() {
        ArrayList<VersionSuite> versionsChannelSuite = new ArrayList<>(); 
        for (VersionSuite versionSuite : getVersionList()) {
            if (versionSuite.getListChannels().contains(this.channel))
                versionsChannelSuite.add(versionSuite);
        }
            
        return versionsChannelSuite;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getApplicationActual() {
        return this.idApplicationActual;
    }
    
    public AppEcom getAppType() {
        return (AppEcom.valueOf(getApplicationActual()));
    }
    
    public ArrayList<ApplicationSuite> getListApplications() {
        return this.listApplications;
    }
    
    public ArrayList<ApplicationSuite> getListApplicationsChannel() {
        ArrayList<ApplicationSuite> applicationsChannelSuite = new ArrayList<>(); 
        for (ApplicationSuite applicationSuite : getListApplications()) {
            if (applicationSuite.getListChannels().contains(this.channel))
                applicationsChannelSuite.add(applicationSuite);
        }
            
        return applicationsChannelSuite;
    }
    
    public String getIdBrowser() {
        return this.idBrowser;
    }
    
    public ArrayList<BrowserSuite> getListBrowsers() {
        return this.listBrowsers;
    }
    
    public ArrayList<BrowserSuite> getListBrowsersChannel() {
        ArrayList<BrowserSuite> browsersChannelSuite = new ArrayList<>(); 
        for (BrowserSuite browserSuite : getListBrowsers()) {
            if (browserSuite.getListChannels().contains(this.channel))
                browsersChannelSuite.add(browserSuite);
        }
            
        return browsersChannelSuite;
    }
    
    public String getNettrafic() {
        return this.netTraffic;
    }
    
    public void setNettrafic(String netTrafic) {
        this.netTraffic = netTrafic;
    }
    
    public String getUrlBase() {
        return this.urlBase;
    }
    
    public int getFiltroTCases() {
        return this.filtroTCases;
    }
    
    public int getFiltroPaises() {
        return this.filtroPaises;
    }
    
    public String getListPagos() {
        return this.listPagos;
    }
    
    public ArrayList<String> getListPagosArray() {
        if (getListPagos()==null || "".compareTo(getListPagos())==0)
            return new ArrayList<>();
            
        return new ArrayList<>(Arrays.asList(getListPagos().split(",")));
    }
    
    public String getListTCases() {
        return this.listTCases;
    }
    
    public ArrayList<String> getListTCasesArray() {
        if (getListTCases()==null || "".compareTo(getListTCases())==0) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(getListTCases().split(",")));
    }
    
    public String getListPaises() {
        return this.listPaises;
    }
    
    public int getFiltroPagos() {
        return this.filtroPagos;
    }
    
    public int getFiltroLineas() {
        return this.filtroLineas;
    }
    
    public String getListLineas() {
        return this.listLineas;
    }
    
    public ArrayList<String> getListLineasArray() {
        if ("".compareTo(getListLineas())==0)
            return new ArrayList<>();
            
        return new ArrayList<>(Arrays.asList(getListLineas().split(",")));
    }
    
    public void setSuite(String suite) {
        this.suite = suite;
    }
    
    public void setChannel(String channel) {
        this.channel = channel;
    }
    
    public void setIdVersionActual(String idVersionActual) {
        this.idVersionActual = idVersionActual;
    }
    
    public void setVersionList(ArrayList<VersionSuite> versionList) {
        this.versionList = versionList;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setIdApplicationActual(String idApplicationActual) {
        this.idApplicationActual = idApplicationActual;
    }
    
    public void setListApplications(ArrayList<ApplicationSuite> listApplications) {
        this.listApplications = listApplications;
    }
    
    public void setIdBrowser(String idBrowser) {
        this.idBrowser = idBrowser;
    }
    
    public void setListBrowsers(ArrayList<BrowserSuite> listBrowsers) {
        this.listBrowsers = listBrowsers;
    }
    
    public void setUrlBase(String urlBase) {
        this.urlBase = urlBase;
    }
    
    public void setFiltroTCases(int filtroTCases) {
        this.filtroTCases = filtroTCases;
    }
    
    public void setFiltroPaises(int filtroPaises) {
        this.filtroPaises = filtroPaises;
    }
    
    public void setListPagos(String listPagos) {
        this.listPagos = listPagos;
    }
    
    public void setListTCases(String listTCases) {
        this.listTCases = listTCases;
    }
    
    public void setListPaises(String listPaises) {
        this.listPaises = listPaises;
    }
    
    public void setFiltroLineas(int filtroLineas) {
        this.filtroLineas = filtroLineas;
    }    
    
    public void setListLineas(String listLineas) {
        this.listLineas = listLineas;
    }
    
    public void setFiltroPagos(int filtroPagos) {
        this.filtroPagos = filtroPagos;
    }
}