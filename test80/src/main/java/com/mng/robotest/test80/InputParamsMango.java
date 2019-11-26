package com.mng.robotest.test80;

import java.util.Arrays;

import org.apache.commons.cli.CommandLine;

import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.domain.InputParamsTM;

public class InputParamsMango extends InputParamsTM {

    public static String CountrysNameParam = "countrys";
    public static String LineasNameParam = "lineas";
    public static String PaymentsNameParam = "payments";
    public static String UrlMantoParam = "urlmanto";
    public static String TypeAccessParam = "typeAccess";    
    public static String CallBackResourceParam = "callbackresource";
    public static String CallBackMethodParam = "callbackmethod";
    public static String CallBackSchemaParam = "callbackschema";
    public static String CallBackParamsParam = "callbackparams";
    public static String CallBackUserParam = "callbackuser";
    public static String CallBackPasswordParam = "callbackpassword";
	
    private String[] listaPaises = {};
    private String[] listaLineas = {};
    private String[] listaPayments = {};   
    private String urlManto = null;
    private CallBack callBack = null;
    
    private static String lineSeparator = System.getProperty("line.separator");
    
    public InputParamsMango() {
    	super();
    }
    
    public InputParamsMango(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
    	super(suiteEnum, appEnum);
    }
    
    public InputParamsMango(CmdLineMaker cmdLineAccess) {
    	super(cmdLineAccess);
    	CommandLine cmdLineData = cmdLineAccess.getComandLineData();
		setListaPaises(cmdLineData.getOptionValues(CountrysNameParam));
		setListaLineas(cmdLineData.getOptionValues(LineasNameParam));
		setListaPayments(cmdLineData.getOptionValues(PaymentsNameParam));
		setUrlManto(cmdLineData.getOptionValue(UrlMantoParam));
        if (cmdLineData.getOptionValue(CallBackResourceParam)!=null) {
            CallBack callBack = new CallBack();
            callBack.setCallBackResource(cmdLineData.getOptionValue(CallBackResourceParam));
            callBack.setCallBackMethod(cmdLineData.getOptionValue(CallBackMethodParam));
            callBack.setCallBackUser(cmdLineData.getOptionValue(CallBackUserParam));
            callBack.setCallBackPassword(cmdLineData.getOptionValue(CallBackPasswordParam));
            callBack.setCallBackSchema(cmdLineData.getOptionValue(CallBackSchemaParam));
            callBack.setCallBackParams(cmdLineData.getOptionValue(CallBackParamsParam));
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
