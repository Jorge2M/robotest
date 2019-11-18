package com.mng.robotest.test80;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;

import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.conf.TypeAccessFmwk;
import com.mng.testmaker.domain.InputParamsTM;

public class InputParamsMango extends InputParamsTM {

    public static String CountrysNameParam = "countrys";
    public static String LineasNameParam = "lineas";
    public static String PaymentsNameParam = "payments";
    
    public static String UrlManto = "urlmanto";
    public static String TypeAccessParam = "typeAccess";    
    public static String CallBackResource = "callbackresource";
    public static String CallBackMethod = "callbackmethod";
    public static String CallBackSchema = "callbackschema";
    public static String CallBackParams = "callbackparams";
    public static String CallBackUser = "callbackuser";
    public static String CallBackPassword = "callbackpassword";
	
    private String[] listaPaises = {};
    private String[] listaLineas = {};
    private String[] listaPayments = {};   
    private String urlManto = null;
    private TypeAccessFmwk typeAccess = TypeAccessFmwk.CommandLine;
    private CallBack callBack = null;
    
    private static String lineSeparator = System.getProperty("line.separator");
    
    public InputParamsMango(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
    	super(suiteEnum, appEnum);
    }
    
    public InputParamsMango(CmdLineMaker cmdLineAccess) {
    	super(cmdLineAccess);
    	CommandLine cmdLineData = cmdLineAccess.getComandLineData();
		setListaPaises(cmdLineData.getOptionValues(CountrysNameParam));
		setListaLineas(cmdLineData.getOptionValues(LineasNameParam));
		setListaPayments(cmdLineData.getOptionValues(PaymentsNameParam));        
		setUrlManto(cmdLineData.getOptionValue(UrlManto));
		setTypeAccessFromStr(cmdLineData.getOptionValue(TypeAccessParam));
        if (cmdLineData.getOptionValue(CallBackResource)!=null) {
            CallBack callBack = new CallBack();
            callBack.setCallBackResource(cmdLineData.getOptionValue(CallBackResource));
            callBack.setCallBackMethod(cmdLineData.getOptionValue(CallBackMethod));
            callBack.setCallBackUser(cmdLineData.getOptionValue(CallBackUser));
            callBack.setCallBackPassword(cmdLineData.getOptionValue(CallBackPassword));
            callBack.setCallBackSchema(cmdLineData.getOptionValue(CallBackSchema));
            callBack.setCallBackParams(cmdLineData.getOptionValue(CallBackParams));
            setCallBack(callBack);
        }   
    }
    
    public static InputParamsMango from(CmdLineMaker cmdLineAccess) {
    	return new InputParamsMango(cmdLineAccess);
    }

    public static InputParamsMango getNew(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
    	return new InputParamsMango(suiteEnum, appEnum);
    }
    
    @Override
    public String getMoreInfo() {
    	StringBuilder moreInfo = new StringBuilder();
    	if (listaPaises!=null && listaPaises.length > 0) {
    		moreInfo.append("List Countrys : " + Arrays.asList(listaPaises) + lineSeparator);
    	}
    	if (listaLineas!=null && listaLineas.length > 0) {
    		moreInfo.append("List Lines: " + Arrays.asList(listaLineas) + lineSeparator);
    	}
    	if (listaPayments!=null && listaPayments.length > 0) {
    		moreInfo.append("List Payments: " + Arrays.asList(listaPayments));
    	}
    	
    	return moreInfo.toString();
    }


    public TypeAccessFmwk getTypeAccess() {
        return this.typeAccess;
    }
    
    void setTypeAccess(TypeAccessFmwk typeAccess) {
        if (typeAccess!=null) {
            this.typeAccess = typeAccess;
        }
    }
    
    void setTypeAccessFromStr(String typeAccess) {
        if (typeAccess!=null) {
            this.typeAccess = TypeAccessFmwk.valueOf(typeAccess);
        }
    }
    
    void setTypeAccessIfNotSetted(TypeAccessFmwk typeAccess) {
        if (this.typeAccess==null) {
            setTypeAccess(typeAccess);
        }
    }    

    public String[] getListaPaises() {
        return this.listaPaises;
    }
    
    public String getListaPaisesStr() {
        if (getListaPaises()!=null && getListaPaises().length>0) {
            return String.join(",", getListaPaises());
        }
        return "";
    }    
    
    public String[] getListaLineas() {
        return this.listaLineas;
    }
    
    public String getListaLineasStr() {
        if (getListaLineas()!=null && getListaLineas().length>0) {
            return String.join(",", getListaLineas());
        }
        return "";
    }
    
    public String[] getListaPayments() {
        return this.listaPayments;
    }
    
    public String getListaPaymentsStr() {
        if (getListaPayments()!=null && getListaPayments().length>0) {
            return String.join(",", getListaPayments());
        }
        return "";
    }    
    
    public void setListaLineas(String[] ListaLineas) {
        this.listaLineas = ListaLineas;
    }
    
    public void setListaPayments(String[] ListaPayments) {
        this.listaPayments = ListaPayments;
    }    
    
    public void setListaPaises(String[] ListaPaises) {
        this.listaPaises = ListaPaises;
    }
    
    public void setListaPaises(String ListaPaises) {
        if (ListaPaises!=null) {
            this.listaPaises = ListaPaises.split("\\s*,\\s*");
        }
    }
    
    public String getUrlManto() {
        return this.urlManto;
    }
    
    public void setUrlManto(String urlManto) {
        this.urlManto = urlManto;
    }
    
    public CallBack getCallBack() {
        return this.callBack;
    }
    
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }
    
    @Override
    public String toString() {
        return 
        	super.toString() + lineSeparator +
        	getMoreInfo();
    }        
}
