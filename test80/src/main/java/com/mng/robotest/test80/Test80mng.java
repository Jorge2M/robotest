package com.mng.robotest.test80;

import java.net.HttpURLConnection;       
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.testmaker.boundary.access.CmdLineMaker;
import com.mng.testmaker.boundary.access.OptionTMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.testfilter.TestMethod;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.suites.*;

public class Test80mng { 
	
	static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);

    public enum TypeCallbackSchema {http, https}
    public enum TypeCallBackMethod {POST, GET}
    
    public static void main(String[] args) throws Exception { 
    	List<OptionTMaker> optionsTest80 = specificMangoOptions();
    	CmdLineMaker cmdLineAccess = CmdLineMaker.from(args, optionsTest80, Suites.class, AppEcom.class);
    	if (cmdLineAccess.checkOptionsValue().isOk()) {
            execSuite(InputParamsMango.from(cmdLineAccess));
    	}
    }
    
    private static List<OptionTMaker> specificMangoOptions() {
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
    	OptionTMaker urlManto = OptionTMaker.builder(InputParamsMango.UrlManto)
            .required(false)
            .hasArgs()
            .pattern(patternUrl)
            .desc("URL of the Backoffice of mangoshop (Manto application)")
            .build();    
             
    	OptionTMaker callbackResource = OptionTMaker.builder(InputParamsMango.CallBackResource)
            .required(false)
            .hasArgs()
            .desc("CallBack URL (without schema and params) to invoke in the end of the TestSuite")
            .build();
        
    	OptionTMaker callbackMethod = OptionTMaker.builder(InputParamsMango.CallBackMethod)
            .required(false)
            .hasArgs()
            .possibleValues(TypeCallBackMethod.class)
            .desc("Method of the CallBack URL. Possible values: " + Arrays.asList(TypeCallBackMethod.values()))
            .build();        
        
    	OptionTMaker callbackSchema = OptionTMaker.builder(InputParamsMango.CallBackSchema)
            .required(false)
            .hasArgs()
            .possibleValues(TypeCallbackSchema.class)
            .desc("Schema of the CallBack URL. Possible values: " + Arrays.asList(TypeCallbackSchema.values()))
            .build();        
        
    	OptionTMaker callbackParams = OptionTMaker.builder(InputParamsMango.CallBackParams)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Params of the CallBack URL (in format param1:value1,param2:value2...)")
            .build();        
        
    	OptionTMaker callbackUser = OptionTMaker.builder(InputParamsMango.CallBackUser)
            .required(false)
            .hasArgs()
            .desc("User credential needed to invoke the CallBack URL")
            .build();        

    	OptionTMaker callbackPassword = OptionTMaker.builder(InputParamsMango.CallBackPassword)
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
    
    /**
     * Indirect access from Command Line, direct access from Online
     */
    public static void execSuite(InputParamsMango inputParams) throws Exception {
		CreatorSuiteRun executor = CreatorSuiteRunMango.getNew(inputParams);
		execSuite(executor);
    }
    public static void execSuite(CreatorSuiteRun executor) throws Exception {
		SuiteTM suite = TestMaker.execSuite(executor);
    	callBackIfNeeded(suite);
    }
    
    private static void callBackIfNeeded(SuiteTM suite) {
    	InputParamsMango inputParams = (InputParamsMango)suite.getInputParams();
    	CallBack callBack = inputParams.getCallBack();
        if (callBack!=null) {
            String reportTSuiteURL = suite.getDnsReportHtml();
            callBack.setReportTSuiteURL(reportTSuiteURL);
            try {
            	HttpURLConnection urlConnection = callBack.callURL();
            	pLogger.error("Called CallbackURL" + urlConnection);
            }
            catch (Exception e) {
                pLogger.error("Problem procesing CallBack", e);
            }
        }
    }

    public static List<TestMethod> getDataTestAnnotationsToExec(InputParamsMango inputParams) throws Exception {
        Suites suiteValue = (Suites)inputParams.getSuite();
        switch (suiteValue) {
        case SmokeTest:
            SmokeTestSuite smokeTest = new SmokeTestSuite(inputParams);
            return smokeTest.getListTests();
        case SmokeManto:
            SmokeMantoSuite smokeManto = new SmokeMantoSuite(inputParams);
            return smokeManto.getListTests();            
        default:
            return null;
        }
    }
    
    public static TreeSet<String> getListPagoFilterNames(Channel channel, AppEcom appE, boolean isEmpl) 
    throws Exception {
        return (UtilsMangoTest.getListPagoFilterNames(channel, appE, isEmpl));
    }
    
    public static TreeSet<String> getListPagoFilterNames(String codCountrysCommaSeparated, Channel channel, AppEcom appE, boolean isEmpl) 
    throws Exception {
        if ("".compareTo(codCountrysCommaSeparated)==0 || 
        	"*".compareTo(codCountrysCommaSeparated)==0 || 
        	"X".compareTo(codCountrysCommaSeparated)==0) {
            return (getListPagoFilterNames(channel, appE, isEmpl));
        }
            
        String[] listCountrys = codCountrysCommaSeparated.split(",");
        ArrayList<Integer> listCodCountrys = new ArrayList<>();
        for (String codCountry : listCountrys) {
            listCodCountrys.add(Integer.valueOf(codCountry));
        }
        
        return (UtilsMangoTest.getListPagoFilterNames(listCodCountrys, channel, appE, isEmpl));
    }
}
