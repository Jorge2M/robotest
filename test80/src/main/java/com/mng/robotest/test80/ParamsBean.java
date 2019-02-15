package com.mng.robotest.test80;

import com.mng.robotest.test80.Test80mng.TypeAccessFmwk;
import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum;
import com.mng.robotest.test80.mango.test.data.ChannelEnum;
import com.mng.robotest.test80.mango.test.data.Suites;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;


public class ParamsBean {

    String SuiteName = "";
    AppEcom appE = AppEcom.shop;
    Channel channel = Channel.desktop;
    String Browser = "";
    String recicleWD = null;
    String netAnalysis = "false";
    String Version = "";
    String URLBase = "";
    String[] ListaPaises = {};
    String[] ListaLineas = {};
    String[] ListaPayments = {};    
    String[] ListaTestCases = {};
    String applicationDNS = "";
    String idSuiteExecution = "";
    String urlManto = null;
    String envioCorreo = null;
    TypeAccessFmwk typeAccess = null;
    CallBack callBack = null;
	
    public String getSuiteName() {
        return this.SuiteName;
    }
    
    public Suites getSuite() {
    	return (Suites.valueOf(getSuiteName()));
    }
	
    public void setSuiteName(String SuiteName) {
        this.SuiteName = SuiteName;
    }
    
    public String getIdSuiteExecution() {
        return this.idSuiteExecution;
    }
    
    public TypeAccessFmwk getTypeAccess() {
        return this.typeAccess;
    }
    
    public void setIdExecutedSuite(String idSuiteExecution) {
        this.idSuiteExecution = idSuiteExecution;
    }
    
    public void setIdExecutedSuiteIfNotSetted(String idSuiteExecution) {
        if (this.idSuiteExecution==null || "".compareTo(this.idSuiteExecution)==0)
            setIdExecutedSuite(idSuiteExecution);
    }
    
    public void setTypeAccess(TypeAccessFmwk typeAccess) {
        if (typeAccess!=null)
            this.typeAccess = typeAccess;
    }
    
    public void setTypeAccessFromStr(String typeAccess) {
        if (typeAccess!=null)
            this.typeAccess = TypeAccessFmwk.valueOf(typeAccess);
    }
    
    public void setTypeAccessIfNotSetted(TypeAccessFmwk typeAccess) {
        if (this.typeAccess==null)
            setTypeAccess(typeAccess);
    }    
    
    public AppEcom getAppE() {
        return this.appE;
    }
    
    public void setAppE(AppEcom appE) {
        this.appE = appE;
    }
    
    public void setAppE(String appE) {
        this.appE = AppEcomEnum.getAppEcom(appE);
    }
    
    public Channel getChannel() {
        return this.channel;
    }
    
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
    public void setChannel(String channel) {
        this.channel = ChannelEnum.getChannel(channel);
    }
	
    public String getBrowser() {
        return this.Browser;
    }
        
    public void setBrowser(String Browser) {
        this.Browser = Browser;
    }
        
    public String getVersion() {
        return this.Version;
    }
        
    public void setVersion(String Version) {
        this.Version = Version;
    }
        
    public String getURLBase() {
        return this.URLBase;
    }
        
    public void setURLBase(String URLBase) {
        this.URLBase = URLBase;
    }
        
    public String[] getListaPaises() {
        return this.ListaPaises;
    }
    
    public String getListaPaisesStr() {
        if (getListaPaises()!=null && getListaPaises().length>0)
            return String.join(",", getListaPaises());
        
        return "";
    }    
    
    public String[] getListaLineas() {
        return this.ListaLineas;
    }
    
    public String getListaLineasStr() {
        if (getListaLineas()!=null && getListaLineas().length>0)
            return String.join(",", getListaLineas());
        
        return "";
    }
    
    public String[] getListaPayments() {
        return this.ListaPayments;
    }
    
    public String getListaPaymentsStr() {
        if (getListaPayments()!=null && getListaPayments().length>0)
            return String.join(",", getListaPayments());
        
        return "";
    }    
    
    public String[] getListaTestCases() {
        return this.ListaTestCases;
    }    
    
    public String getListaTestCasesStr() {
        if (getListaTestCases()!=null && getListaTestCases().length>0)
            return String.join(",", getListaTestCases());
        
        return "";
    }
    
    public String getApplicationDNS() {
        return this.applicationDNS;
    }
    
    public void setListaLineas(String[] ListaLineas) {
        this.ListaLineas = ListaLineas;
    }
    
    public void setListaPayments(String[] ListaPayments) {
        this.ListaPayments = ListaPayments;
    }    
    
    public void setListaPaises(String[] ListaPaises) {
        this.ListaPaises = ListaPaises;
    }
    
    public void setListaPaises(String ListaPaises) {
        if (ListaPaises!=null)
            this.ListaPaises = ListaPaises.split("\\s*,\\s*");
    }
    
    public void setListaTestCases(String ListaTestCases) {
        if (ListaTestCases!=null)
            this.ListaTestCases= ListaTestCases.split("\\s*,\\s*");
    }
    
    public void setListaTestCases(String[] ListaTestCases) {
        this.ListaTestCases = ListaTestCases;
    }    
        
    public void setApplicationDNS(String applicationDNS) {
        this.applicationDNS = applicationDNS;
    }
    
    public String getUrlManto() {
        return this.urlManto;
    }
    
    public void setUrlManto(String urlManto) {
        this.urlManto = urlManto;
    }
    
    public String getRecicleWD() {
        return this.recicleWD;
    }
    
    public void setRecicleWD(String recicleWD) {
        this.recicleWD = recicleWD;
    }
    
    public String getNetAnalysis() {
        return this.netAnalysis;
    }
    
    public void setNetAnalysis(String netAnalysis) {
        if (netAnalysis!=null)
            this.netAnalysis = netAnalysis;
    }    
    
    public String getEnvioCorreo() {
        return this.envioCorreo;
    }
    
    public void setEnvioCorreo(String envioCorreo) {
        this.envioCorreo = envioCorreo;
    }
    
    public CallBack getCallBack() {
        return this.callBack;
    }
    
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public String toString() {
        return "ParamsBean [SuiteName="+ this.SuiteName + ", Browser=" + this.Browser + ", Version=" + this.Version + ", URLBase=" + this.URLBase + ", ListaPaises=" + this.ListaPaises +
                ", toString()=" + super.toString() + "]";
    }        
}