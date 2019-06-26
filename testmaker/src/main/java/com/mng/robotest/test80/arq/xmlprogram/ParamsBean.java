package com.mng.robotest.test80.arq.xmlprogram;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.conf.SuiteTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;

public class ParamsBean {

    Channel channel = Channel.desktop;
    String[] groups = {};
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

    AppTest app;
    SuiteTest suite;
	
    public ParamsBean(AppTest app, SuiteTest suite) {
    	this.app = app;
    	this.suite = suite;
    }
    
    public String getSuiteName() {
        return this.suite.toString();
    }
    
    public SuiteTest getSuite() {
    	return (suite);
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
        if (this.idSuiteExecution==null || "".compareTo(this.idSuiteExecution)==0) {
            setIdExecutedSuite(idSuiteExecution);
        }
    }
    
    public void setTypeAccess(TypeAccessFmwk typeAccess) {
        if (typeAccess!=null) {
            this.typeAccess = typeAccess;
        }
    }
    
    public void setTypeAccessFromStr(String typeAccess) {
        if (typeAccess!=null) {
            this.typeAccess = TypeAccessFmwk.valueOf(typeAccess);
        }
    }
    
    public void setTypeAccessIfNotSetted(TypeAccessFmwk typeAccess) {
        if (this.typeAccess==null) {
            setTypeAccess(typeAccess);
        }
    }    
    
    public AppTest getAppE() {
        return this.app;
    }
    
    public Channel getChannel() {
        return this.channel;
    }
    
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
    public void setChannel(String channel) {
        this.channel = Channel.valueOf(channel);
    }
    
    public String[] getGroups() {
        return this.groups;
    }
    
    public List<String> getGroupsList() {
    	if (null!=getGroups()) {
    		return Arrays.asList(getGroups());
    	}
    	return null;
    }
    
    public String getGroupsStr() {
        if (null!=getGroups() && getGroups().length>0) {
            return String.join(",", getGroups());
        }
        return "";
    }
    
    public void setGroups(String[] groups) {
        this.groups = groups;
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
        if (getListaPaises()!=null && getListaPaises().length>0) {
            return String.join(",", getListaPaises());
        }
        return "";
    }    
    
    public String[] getListaLineas() {
        return this.ListaLineas;
    }
    
    public String getListaLineasStr() {
        if (getListaLineas()!=null && getListaLineas().length>0) {
            return String.join(",", getListaLineas());
        }
        return "";
    }
    
    public String[] getListaPayments() {
        return this.ListaPayments;
    }
    
    public String getListaPaymentsStr() {
        if (getListaPayments()!=null && getListaPayments().length>0) {
            return String.join(",", getListaPayments());
        }
        return "";
    }    
    
    public String[] getTestCases() {
        return this.ListaTestCases;
    }    
    
    public List<String> getTestCasesList() {
    	if (null!=getTestCases()) {
    		return Arrays.asList(getTestCases());
    	}
    	return null;
    }
    
    public String getListaTestCasesStr() {
        if (getTestCases()!=null && getTestCases().length>0) {
            return String.join(",", getTestCases());
        }
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
        if (ListaPaises!=null) {
            this.ListaPaises = ListaPaises.split("\\s*,\\s*");
        }
    }
    
    public void setListaTestCases(String ListaTestCases) {
        if (ListaTestCases!=null) {
            this.ListaTestCases= ListaTestCases.split("\\s*,\\s*");
        }
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
        if (netAnalysis!=null) {
            this.netAnalysis = netAnalysis;
        }
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
        return "ParamsBean [SuiteName="+ getSuiteName() + ", Browser=" + this.Browser + ", Version=" + this.Version + ", URLBase=" + this.URLBase + ", ListaPaises=" + this.ListaPaises +
                ", toString()=" + super.toString() + "]";
    }        
}