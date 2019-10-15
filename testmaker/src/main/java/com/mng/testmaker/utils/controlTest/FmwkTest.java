package com.mng.testmaker.utils.controlTest;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.status.StatusLogger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.xml.XmlSuite;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.data.ConstantesTestMaker;
import com.mng.testmaker.domain.StateRun;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.utils.DataFmwkTest;
import com.mng.testmaker.utils.NetTrafficMng;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.utils;
import com.mng.testmaker.utils.otras.WebDriverArqUtils;

public class FmwkTest {
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);

    public enum TypeStore {step, validation}
    public enum TypeEvidencia {imagen, html, errorpage, har, harp}
    public static String prefixEvidenciaStep = "Step-";
    final public static String log4jFileAppender = "robotestFileAppender";
    final public static String log4jLogger = "robotestLogger";
    final public static String log4jFileName = "robottest.log";

    /**
     * Función que realiza la grabación de un Step. Básicamente graba los datos en BD y genera los ficheros asociados
     */
    @SuppressWarnings({ "unchecked"})    
    public static void grabStep(StepTestMaker datosStep, DataFmwkTest dFTest) {
        storeFileEvidencesIfNeeded(datosStep, TypeStore.step, dFTest);
    }

    @SuppressWarnings({ "unchecked"})
    public static void grabStepValidations(ChecksResult validations, String descripValidac) {
    	
        if (!stepMap.containsKey(datosStep)) {
            stepMap.put(datosStep, new ArrayList<String>());
            stepMapStatus.put(datosStep, new ArrayList<State>());
            screnshootsMap.put(datosStep, new ArrayList<byte[]>());
            htmlsourcesMap.put(datosStep, new ArrayList<byte[]>());
        }            

        if (datosStep.getExcepExists()) {
            datosStep.setResultSteps(State.Nok);
            stepMapStatus.get(datosStep).add(State.Nok);
        } else {
            stepMapStatus.get(datosStep).add(datosStep.getResultSteps());
        }
        if (!datosStep.isAvoidEvidences()) {
            boolean browserGUI = true;
            int grabImg = 0;
            if (dFTest.ctx.getAttribute("grabImg") != null) {
                grabImg = ((Integer)dFTest.ctx.getAttribute("grabImg")).intValue();
            }
            if (dFTest.ctx.getAttribute("browserGUI") != null) {
                browserGUI = ((Boolean)dFTest.ctx.getAttribute("browserGUI")).booleanValue();
            }

            if ((datosStep.getSaveImagePage()==SaveWhen.Always || 
                 grabImg == 1 || /* Está activada la grabación de imagen a nivel general o */
                 datosStep.getResultSteps()!=State.Ok) &&
                 datosStep.getTypePage() == 0/*HTML*/&&
                 browserGUI) {
                try {
                    //Capture hardcopy
                    WebDriver captureDriver = dFTest.driver;
                    if (captureDriver instanceof RemoteWebDriver) {
                        captureDriver = new Augmenter().augment(captureDriver);
                    }
                    byte[] screenshot = ((TakesScreenshot)captureDriver).getScreenshotAs(OutputType.BYTES);
                    screnshootsMap.get(datosStep).add(screenshot);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (datosStep.getSaveHtmlPage()==SaveWhen.Always && datosStep.getTypePage() == 0/* HTML */) {
                    try {
                        //Capture sources
                        htmlsourcesMap.get(datosStep).add(dFTest.driver.getPageSource().getBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }    
                
            }
        }
        skipTestsIfSuiteStopped(dFTest.ctx);
        if (!datosStep.isAvoidEvidences()) {
            storeFileEvidencesIfNeeded(datosStep, TypeStore.validation, dFTest);
        }
    }

    private static void storeFileEvidencesIfNeeded(StepTestMaker datosStep, TypeStore typeStore, DataFmwkTest dFTest) {
        String nameMethodWithFactory = FmwkTest.getMethodWithFactory(dFTest.meth, dFTest.ctx);
        datosStep.setNameMethodWithFactory(nameMethodWithFactory);
        if (typeStore==TypeStore.step) {
        	createPathForEvidencesStore(nameMethodWithFactory, dFTest.ctx);
        	storeNetTrafficIfNeeded(datosStep, dFTest.ctx);
        }
        
    	boolean browserGUI = true;
        Object browserGUIObj = dFTest.ctx.getAttribute("browserGUI");
        if (browserGUIObj!=null) {
            browserGUI = ((Boolean)browserGUIObj).booleanValue();
        }
        boolean storeEvidences = (!datosStep.isAvoidEvidences() && browserGUI);
        		
        storeHardcopyIfNeeded(storeEvidences, datosStep, dFTest);
        storeErrorPageIfNeeded(storeEvidences, datosStep, dFTest);
        storeHTMLIfNeeded(datosStep, dFTest);
    }
    
    private static void createPathForEvidencesStore(String nameMethodWithFactory, ITestContext ctx) {
        String pathEvidencias = getPathFolderEvidenciasStep(ctx.getOutputDirectory(), ctx.getName(), nameMethodWithFactory);
        File directorio = new File(pathEvidencias);
        directorio.mkdirs();
    }
    
    private static void storeHardcopyIfNeeded(boolean storeImage, StepTestMaker datosStep, DataFmwkTest dFTest) {
        try {
            if (isStoreImage(storeImage, datosStep, dFTest)) {
                String nombreImagen = getPathFileEvidenciaStep(dFTest.ctx, datosStep.getNameMethodWithFactory(), datosStep.getStepNumber(), TypeEvidencia.imagen);
                WebDriverArqUtils.captureEntirePageMultipleBrowsers(dFTest.driver, dFTest.ctx, nombreImagen);
            }
        } 
        catch (Exception e) {
            pLogger.warn("Problema grabando imagen", e);
        }
    }
    
    private static void storeErrorPageIfNeeded(boolean browserGUI, StepTestMaker datosStep, DataFmwkTest dFTest) {
    	try {
		    if (isStoreError(browserGUI, datosStep, dFTest) &&
		    	dFTest.storerDataError!=null) {
		        dFTest.storerDataError.store(dFTest, datosStep);
	        } 
    	}
	    catch (Exception e) {
            pLogger.warn("Problema grabando ErrorPage", e);
        }
    }
    
    private static void storeHTMLIfNeeded(StepTestMaker datosStep, DataFmwkTest dFTest) {
        try {
            if (datosStep.getSaveHtmlPage()==SaveWhen.Always && 
            	datosStep.getTypePage() == 0/* HTML */) {
                WebDriverArqUtils.capturaHTMLPage(dFTest, datosStep.getStepNumber());
            }
        }
        catch (Exception e) {
            pLogger.warn("Problema grabando HTML", e);
        }
    }
    
    private static boolean isStoreImage(boolean browserGUI, StepTestMaker datosStep, DataFmwkTest dFTest) {
        int forceGrabInAllSteps = 0;
        Object grabImgObj = dFTest.ctx.getAttribute("grabImg");
        if (grabImgObj!=null) {
        	forceGrabInAllSteps = ((Integer)grabImgObj).intValue();
        }
        SaveWhen saveImageWhen = datosStep.getSaveImagePage();
        boolean saveAlways = (saveImageWhen==SaveWhen.Always || forceGrabInAllSteps==1);
        boolean isErrorAndWeWantHardcopy = (datosStep.getResultSteps()!=State.Ok && saveImageWhen==SaveWhen.IfProblem);
        boolean isPageHardcopyPossible = (datosStep.getTypePage() == 0/*HTML*/ && browserGUI);
        
    	return (
	    	(saveAlways || isErrorAndWeWantHardcopy) &&
	    	 isPageHardcopyPossible
        );
    }
    
    private static boolean isStoreError(boolean browserGUI, StepTestMaker datosStep, DataFmwkTest dFTest) throws Exception {
	    String currentURL = dFTest.driver.getCurrentUrl();
	    URI uri = new URI(currentURL);
    	SaveWhen saveErrorWhen = datosStep.getSaveErrorPage();
        boolean saveAlways = saveErrorWhen==SaveWhen.Always;
        boolean isErrorAndWeWantError = (datosStep.getResultSteps()!=State.Ok && saveErrorWhen==SaveWhen.IfProblem);
        boolean isUrlMango = (((String)dFTest.ctx.getAttribute("appPath")).contains(uri.getHost()) || uri.getHost().contains("mango.com"));
        boolean isGetErrorPossible = (datosStep.getTypePage() == 0/*HTML*/ && browserGUI );
    	
	    return (
		    (saveAlways || isErrorAndWeWantError) &&
		    isGetErrorPossible &&
		    isUrlMango
	    );
    }

    private static void storeNetTrafficIfNeeded(StepTestMaker datosStep, ITestContext ctx) {
	    try {
	    	String methodWithFactory = datosStep.getNameMethodWithFactory();
	        if (datosStep.getSaveNettrafic()==SaveWhen.Always) {
	            String nameFileHar = FmwkTest.getPathFileEvidenciaStep(ctx, methodWithFactory, datosStep.getStepNumber(), TypeEvidencia.har);
	        	NetTrafficMng netTraffic = new NetTrafficMng();
	            netTraffic.storeHarInFile(nameFileHar);
	            netTraffic.copyHarToHarp(nameFileHar);
	            Thread.sleep(1000);
	        }
	    }
	    catch (Exception e) {
	        pLogger.info("Problema grabando NetTraffic", e);
	    }    
    }

    public static String getOutputDirectory(String userDir, String suiteName, String idExecutedSuite) {
        String userDirOK = userDir;
        String lastCharUserDir = userDirOK.substring(userDirOK.length() - 1);
        if (File.separator.compareTo(lastCharUserDir)!=0) {
            userDirOK+=File.separator;
        }
        return (userDirOK + getPathOutputDirectoryFromUserDir(suiteName, idExecutedSuite));
    }

    public static String getPathOutputDirectoryFromUserDir(String suiteName, String idExecutedSuite) {
        return (ConstantesTestMaker.directoryOutputTests + File.separator + suiteName + File.separator + idExecutedSuite);
    }

    public static String getPathReportHTML(String outputDirectory) {
        return outputDirectory + File.separator + ConstantesTestMaker.nameReportHTMLTSuite;
    }

    /**
     * Output Library definido en el onStart(ISuite suite) del InvokeListener (disponible a partir de ese momento)
     */
    public static String getOutputDirectorySuite(ITestContext ctx) {
        return (getOutputDirectorySuite(ctx.getSuite().getXmlSuite()));
    }

    public static String getOutputDirectorySuite(ISuite suite) {
        return (getOutputDirectorySuite(suite.getXmlSuite()));
    }

    public static String getOutputDirectorySuite(XmlSuite suiteXml) {
        return (suiteXml.getParameter(ConstantesTestMaker.paramOutputDirectorySuite));
    }

    /**
     * @param methodWithFactory nombre del método + (id del factory)
     * @return path de la envidencia asociada al método+step
     */
    public static String getPathFolderEvidenciasStep(String outputDirectorySuite, String testRun, String methodWithFactory) {
        return (outputDirectorySuite + File.separator + testRun + File.separator + methodWithFactory);
    }

    public static String getNameFileEvidenciaStep(int stepNumber, TypeEvidencia typeEvidencia) {
        String extension = "." + getSufijoEvidencia(typeEvidencia);
        return (prefixEvidenciaStep + Integer.toString(stepNumber) + extension);
    }

    public static String getPathFileEvidenciaStep(String outputDirectory, StepTestMaker step, TypeEvidencia typeEvidencia) {
        int stepNumber = step.getPositionInTestCase();
        String testCaseNameUnique = step.getTestCaseParent().getNameUnique();
        String testRunName = step.getTestCaseParent().getTestRunParent().getName();
        
        String fileName = getNameFileEvidenciaStep(stepNumber, typeEvidencia);
        return (getPathFolderEvidenciasStep(outputDirectory, testRunName, testCaseNameUnique) + File.separator + fileName);
    }

    public static String getPathFileEvidenciaStep(ITestContext context, String methodWithFactory, int stepNumber, TypeEvidencia typeEvidencia) {
        return (getPathFileEvidenciaStep(context.getOutputDirectory(), context.getName(), methodWithFactory, stepNumber, typeEvidencia));
    }

    public static String getSufijoEvidencia(TypeEvidencia typeEvidencia) {
        switch (typeEvidencia) {
        case imagen:
            return "png";
        case html:
            return "html";
        case errorpage:
            return "-error.html";
        case har:
            return "har";
        case harp:
            return "harp";
        default:
            return "";
        }
    }

    public static String getLinkNetTraffic(int stepNumber, Method method, ITestContext context) {
        String methodWithFactory = getMethodWithFactory(method, context);
        return (ConstantesTestMaker.URL_SOFTWAREISHARD + getPathFileEvidenciaStep(context, methodWithFactory, stepNumber, TypeEvidencia.harp));
    }

    public static String getMethodWithFactory(Method method, ITestContext context) {
        String factory = (String)context.getAttribute("factory-" + String.valueOf(Thread.currentThread().getId()));
        if (factory!=null) {
            return (method.getName() + "(" + factory + ")");
        }
        return method.getName();
    }

    /**
     * @param finDirectory
     *          true:  devuelve la URL de la imagen correspondiente al 'directorio final' donde estarán ubicados los datos de los tests
     *          false: devuelve la URL de la imagen correspondiente al 'output directory' temporal durante la ejecución de los tests
     * @return URL de la hardcopy asociada a un step concreto
     */
    public static String getURLImgStep(StepTestMaker datosStep, boolean finDirectory, Method method, ITestContext context) {
        String methodWithFactory = getMethodWithFactory(method, context);
        String pathImageInit = getPathFileEvidenciaStep(context, methodWithFactory, datosStep.getStepNumber(), TypeEvidencia.imagen);
        File fileImage = new File(pathImageInit);
        
        SuiteContextTestMaker testMakerCtx = SuiteContextTestMaker.getTestMakerContext(context);
        String applicationDNS = testMakerCtx.getInputData().getWebAppDNS();
        String urlImage = utils.obtainDNSFromFile(fileImage.getAbsolutePath(), applicationDNS).replace('\\', '/');
        return (urlImage);
    }

    public static void configLog4java(String outputDirectorySuite) {
        //Configure a layout and appender programatically
        StatusLogger.getLogger().setLevel(Level.OFF);
        LoggerContext ctx = (LoggerContext)LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();

        PatternLayout layout = PatternLayout.newBuilder()
          .withConfiguration(config)
          .withPattern("%d{HH:mm:ss.SSS} %level %msg%n")
          .build();

        Appender appender = FileAppender.newBuilder()
          .setConfiguration(config)
          .withName(log4jFileAppender)
          .withLayout(layout)
          .withFileName(outputDirectorySuite + File.separator + log4jFileName)
          .build();

        //Define a logger using the LoggerConfig class, associate the appender to it, and update the configuration:
        appender.start();
        config.addAppender(appender);

        AppenderRef ref = AppenderRef.createAppenderRef(log4jFileAppender, null, null);
        AppenderRef[] refs = new AppenderRef[] { ref };

        LoggerConfig loggerConfig = LoggerConfig
          .createLogger(false, Level.INFO, log4jLogger, "true", refs, null, config, null);
        loggerConfig.addAppender(appender, null, null);
        config.addLogger(log4jLogger, loggerConfig);
        ctx.updateLoggers();
    }
}
