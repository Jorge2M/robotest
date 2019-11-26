package com.mng.robotest.test80;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;

import com.mng.robotest.test80.Test80mng.TypeCallBackMethod;
import com.mng.robotest.test80.Test80mng.TypeCallbackSchema;
import com.mng.testmaker.boundary.access.OptionTMaker;
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
    
    public InputParamsMango(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
    	super(suiteEnum, appEnum);
    }
    public static InputParamsMango getNew(Class<? extends Enum<?>> suiteEnum, Class<? extends Enum<?>> appEnum) {
    	return new InputParamsMango(suiteEnum, appEnum);
    }
    
    @Override
    public List<OptionTMaker> getSpecificParameters() {
    	List<OptionTMaker> options = new ArrayList<>();
        
    	OptionTMaker countrys = OptionTMaker.builder(InputParamsMango.CountrysNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of 3-digit codes of countrys comma separated or \'X\' for indicate all countrys")
            .pattern("\\d{3}|X")
            .build();
        
    	OptionTMaker lineas = OptionTMaker.builder(InputParamsMango.LineasNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of lines comma separated (p.e. she,he,...)")
            .build();        

    	OptionTMaker payments = OptionTMaker.builder(InputParamsMango.PaymentsNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',') 
            .desc("List of payments comma separated (p.e. VISA,TARJETA MANGO,...)")
            .build();
        
    	String patternUrl = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    	OptionTMaker urlManto = OptionTMaker.builder(InputParamsMango.UrlMantoParam)
            .required(false)
            .hasArgs()
            .pattern(patternUrl)
            .desc("URL of the Backoffice of mangoshop (Manto application)")
            .build();    
             
    	OptionTMaker callbackResource = OptionTMaker.builder(InputParamsMango.CallBackResourceParam)
            .required(false)
            .hasArgs()
            .desc("CallBack URL (without schema and params) to invoke in the end of the TestSuite")
            .build();
        
    	OptionTMaker callbackMethod = OptionTMaker.builder(InputParamsMango.CallBackMethodParam)
            .required(false)
            .hasArgs()
            .possibleValues(TypeCallBackMethod.class)
            .desc("Method of the CallBack URL. Possible values: " + Arrays.asList(TypeCallBackMethod.values()))
            .build();        
        
    	OptionTMaker callbackSchema = OptionTMaker.builder(InputParamsMango.CallBackSchemaParam)
            .required(false)
            .hasArgs()
            .possibleValues(TypeCallbackSchema.class)
            .desc("Schema of the CallBack URL. Possible values: " + Arrays.asList(TypeCallbackSchema.values()))
            .build();        
        
    	OptionTMaker callbackParams = OptionTMaker.builder(InputParamsMango.CallBackParamsParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Params of the CallBack URL (in format param1:value1,param2:value2...)")
            .build();        
        
    	OptionTMaker callbackUser = OptionTMaker.builder(InputParamsMango.CallBackUserParam)
            .required(false)
            .hasArgs()
            .desc("User credential needed to invoke the CallBack URL")
            .build();        

    	OptionTMaker callbackPassword = OptionTMaker.builder(InputParamsMango.CallBackPasswordParam)
            .required(false)
            .hasArgs()
            .desc("Password credential needed to invoke the CallBack URL")
            .build();        
                
        options.add(countrys);
        options.add(lineas);
        options.add(payments);        
        options.add(urlManto);
        options.add(callbackResource);
        options.add(callbackMethod);
        options.add(callbackSchema);
        options.add(callbackParams);
        options.add(callbackUser);
        options.add(callbackPassword);
        
        return options;
    }
    
    @Override
    public void setSpecificDataFromCommandLine(CommandLine cmdLineData) {
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
