package com.mng.robotest.test80;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.mng.robotest.test80.arq.access.CommandLineAccess;
import com.mng.robotest.test80.arq.listeners.CallBack;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.filter.TestMethod;
import com.mng.robotest.test80.arq.utils.otras.TypeAccessFmwk;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.arq.xmlprogram.ParamsBean;
import com.mng.robotest.test80.arq.xmlprogram.SuiteMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.suites.*;

public class Test80mng { 

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
    
    public enum TypeCallbackSchema {http, https}
    public enum TypeCallBackMethod {POST, GET}
    
    /**
     * Direct access from Command Line
     * Parseamos la línea de comandos y ejecutamos la TestSuite correspondiente mediante la XML programática 
     */
    public static void main(String[] args) throws Exception { 
    	OptionGroup optionsTest80 = defineMoreOptions();
    	CommandLineAccess cmdLineAccess = CommandLineAccess.getNew(optionsTest80);
    	
        if (!checkHelpParameterCase(args)) {
            Options options = setOptionsRelatedCommandLine();
            ParamsBean params = null;
            try {
                params = checkAndGetParams(options, args);     
            }
            catch (ParseException e) {
                System.out.println(e.getLocalizedMessage());
                printHelpSyntaxis(options);
                return;
            }
            
            if (params!=null) {
                params.setTypeAccessIfNotSetted(TypeAccessFmwk.CommandLine);
                execSuite(params);
            }
        }
    }
    
    private static OptionGroup defineMoreOptions() {
    	OptionGroup options = new OptionGroup();
             
        Option countrys = Option.builder(CountrysNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of codes of countrys comma separated")
            .build();
        
        Option lineas = Option.builder(LineasNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of lines comma separated (p.e. she,he,...)")
            .build();        

        Option payments = Option.builder(PaymentsNameParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("List of payments comma separated (p.e. VISA,TARJETA MANGO,...)")
            .build();
        
        Option urlManto = Option.builder(UrlManto)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("URL of the Backoffice of mangoshop (Manto application)")
            .build();    
        
        Option bat = Option.builder(TypeAccessParam)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Type of access. Posible values: " + Arrays.toString(getNames(TypeAccessFmwk.class)))
            .build();               
        
        Option callbackResource = Option.builder(CallBackResource)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("CallBack URL (without schema and params) to invoke in the end of the TestSuite")
            .build();
        
        Option callbackMethod = Option.builder(CallBackMethod)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Method of the CallBack URL (POST o GET)")
            .build();        
        
        Option callbackSchema = Option.builder(CallBackSchema)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Schema of the CallBack URL (http or https)")
            .build();        
        
        Option callbackParams = Option.builder(CallBackParams)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Params of the CallBack URL (in format param1:value1,param2:value2...)")
            .build();        
        
        Option callbackUser = Option.builder(CallBackUser)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("User credential needed to invoke the CallBack URL")
            .build();        
        
        Option callbackPassword = Option.builder(CallBackPassword)
            .required(false)
            .hasArgs()
            .valueSeparator(',')
            .desc("Password credential needed to invoke the CallBack URL")
            .build();        
                
        //Optional
        options.addOption(countrys);
        options.addOption(lineas);
        options.addOption(payments);        
        options.addOption(urlManto);;
        options.addOption(bat);
        options.addOption(callbackResource);
        options.addOption(callbackMethod);
        options.addOption(callbackSchema);
        options.addOption(callbackParams);
        options.addOption(callbackUser);
        options.addOption(callbackPassword);
        
        return options;
    }
    

    
    /**
     * Indirect access from Command Line, direct access from Online
     */
    public static void execSuite(ParamsBean params) throws Exception {
    	SuiteMaker suite = makeSuite(params);
    	suite.run();
    }
    
    public static SuiteMaker makeSuite(ParamsBean params) throws Exception {
        params.setTypeAccessIfNotSetted(TypeAccessFmwk.Online);
        try {
            switch ((Suites)params.getSuite()) {
            case SmokeTest:
                return (new SmokeTestSuite(params));
            case SmokeManto:
                return (new SmokeMantoSuite(params));             
            case PagosPaises:
                return (new PagosPaisesSuite(params));           
            case ValesPaises:
                return (new ValesPaisesSuite(params));          
            case PaisIdiomaBanner:
                return (new PaisIdiomaSuite(params));                    
            case MenusPais:
                return (new MenusPaisSuite(params));
            case MenusManto:
                return (new MenusMantoSuite(params));            
            case Nodos:
                return (new NodosSuite(params));           
            case ConsolaVotf:
                return (new ConsolaVotfSuite(params));              
            case ListFavoritos:
            case ListMiCuenta:
                return (new GenericFactorySuite(params));               
            case RegistrosPaises:
                return (new RegistrosSuite(params));       
            case RebajasPaises:
                return (new RebajasSuite(params));             
            default:
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println("Suite Name not valid. Posible values: " + Arrays.toString(getNames(Suites.class)));
        }
        
        return null;
    }

    
    private static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }

    public static List<TestMethod> getDataTestAnnotationsToExec(ParamsBean params) throws Exception {
        params.setTypeAccessIfNotSetted(TypeAccessFmwk.Online);
        Suites suiteValue = Suites.valueOf(params.getSuiteName());
        switch (suiteValue) {
        case SmokeTest:
            SmokeTestSuite smokeTest = new SmokeTestSuite(params);
            return smokeTest.getListTests();
        case SmokeManto:
            SmokeMantoSuite smokeManto = new SmokeMantoSuite(params);
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

    public static String getOutputDirectory(String userDir, String suiteName, String idExecutedSuite) {
        return fmwkTest.getOutputDirectory(userDir, suiteName, idExecutedSuite);
    }
    
    public static String getPathOutputDirectoryFromUserDir(String suiteName, String idExecutedSuite) {
        return (fmwkTest.getPathOutputDirectoryFromUserDir(suiteName, idExecutedSuite));
    }
}
