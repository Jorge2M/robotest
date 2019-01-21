package com.mng.robotest.test80.arq.utils.controlTest;

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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.xml.XmlSuite;

import com.mng.robotest.test80.arq.jdbc.dao.StepsDAO;
import com.mng.robotest.test80.arq.jdbc.dao.SuitesDAO;
import com.mng.robotest.test80.arq.jdbc.dao.ValidationsDAO;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.NetTrafficMng;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.StateSuite;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.arq.utils.otras.WebDriverArqUtils;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

@SuppressWarnings("javadoc")
public class fmwkTest {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

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
    public static int grabStep(datosStep datosStep, DataFmwkTest dFTest) {
        if ("ROBOTEST2".equals(System.getProperty("ROBOTEST2"))) {
            // No queremos crear una dependencia ciclica robotest2 report, por lo que es licito realizar este workaround intercambiando datos atraves de listas y hashes
            Map<datosStep, List<String>> stepMap = (Map<datosStep, List<String>>) dFTest.ctx.getSuite()
                    .getAttribute("ROBOTEST2_STEP_VALIDATIONS");
            Map<datosStep, List<State>> stepMapStatus = (Map<datosStep, List<State>>) dFTest.ctx.getSuite()
                    .getAttribute("ROBOTEST2_VALIDATION_STATUS_LIST");              
            Map<datosStep, List<byte[]>> screnshootsMap = (Map<datosStep, List<byte[]>>) dFTest.ctx.getSuite()
                    .getAttribute("ROBOTEST2_SCRENSHOT_LIST");
            Map<datosStep, List<byte[]>> htmlsourcesMap = (Map<datosStep, List<byte[]>>) dFTest.ctx.getSuite()
                    .getAttribute("ROBOTEST2_SOURCES_LIST");
            if (!stepMap.containsKey(datosStep)) {
                stepMap.put(datosStep, new ArrayList<String>());
                stepMapStatus.put(datosStep, new ArrayList<State>());
                screnshootsMap.put(datosStep, new ArrayList<byte[]>());
                htmlsourcesMap.put(datosStep, new ArrayList<byte[]>());
            }
            if (datosStep.getHoraFin()==null)
                datosStep.setHoraFin(new Date(System.currentTimeMillis()));
            System.out.println("ROBOTEST2: LAZY REPORT STEP REDIRECT");
            return 0;
        }
        sendSkipTestExceptionIfSuiteStopping(dFTest.ctx);                
        int stepNumber = StepsDAO.grabStep(datosStep, dFTest.meth, dFTest.ctx);
        storeFileEvidencesIfNeeded(datosStep, TypeStore.step, dFTest);
        return stepNumber;
    }

    /**
     * Realiza la grabación de una validación
     */
    @SuppressWarnings({ "unchecked"})
    public static void grabStepValidation(datosStep datosStep, String descripValidac, DataFmwkTest dFTest) {
        boolean avoidEvidences = false;
        switch (datosStep.getResultSteps()) {
        case Warn_NoHardcopy:
            datosStep.setResultSteps(State.Warn);
            avoidEvidences = true;
            break;
        case Info_NoHardcopy:
            datosStep.setResultSteps(State.Info);
            avoidEvidences = true;
            break;
        default:
            avoidEvidences = false;
        }
        if ("ROBOTEST2".equals(System.getProperty("ROBOTEST2"))) {
            Map<datosStep, List<String>> stepMap = (Map<datosStep, List<String>>) dFTest.ctx.getSuite()
                    .getAttribute("ROBOTEST2_STEP_VALIDATIONS");
            Map<datosStep, List<State>> stepMapStatus = (Map<datosStep, List<State>>) dFTest.ctx.getSuite()
                    .getAttribute("ROBOTEST2_VALIDATION_STATUS_LIST");              
            Map<datosStep, List<byte[]>> screnshootsMap = (Map<datosStep, List<byte[]>>) dFTest.ctx.getSuite()
                    .getAttribute("ROBOTEST2_SCRENSHOT_LIST");
            Map<datosStep, List<byte[]>> htmlsourcesMap = (Map<datosStep, List<byte[]>>) dFTest.ctx.getSuite()
                    .getAttribute("ROBOTEST2_SOURCES_LIST");     
            if (!stepMap.containsKey(datosStep)) {
                stepMap.put(datosStep, new ArrayList<String>());
                stepMapStatus.put(datosStep, new ArrayList<State>());
                screnshootsMap.put(datosStep, new ArrayList<byte[]>());
                htmlsourcesMap.put(datosStep, new ArrayList<byte[]>());
            }            
            List<Integer> listVals = datosStep.getSimpleValidacsList();
            if (listVals!=null)
                stepMap.get(datosStep).add(descripValidac + "<br />" + listVals.toString());
            else
                stepMap.get(datosStep).add(descripValidac);
            if (datosStep.getExcepExists()) {
                datosStep.setResultSteps(State.Nok);
                stepMapStatus.get(datosStep).add(State.Nok);
            } else {
                stepMapStatus.get(datosStep).add(datosStep.getResultSteps());
            }
            System.out.println("ROBOTEST2: LAZY REPORT VALIDATION REDIRECT");
            if (!avoidEvidences) {
                boolean browserGUI = true;
                int grabImg = 0;
                if (dFTest.ctx.getAttribute("grabImg") != null)
                    grabImg = ((Integer)dFTest.ctx.getAttribute("grabImg")).intValue();
    
                if (dFTest.ctx.getAttribute("browserGUI") != null)
                    browserGUI = ((Boolean)dFTest.ctx.getAttribute("browserGUI")).booleanValue();
    
                if ((datosStep.getGrabImage() || /* Está activada la grabación de imagen por paso o */
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
                        System.out.println("ROBOTEST2: LAZY REPORT SCREENSHOT REDIRECT");
                        
                         //Capture errorPage.faces
                         String currentURL = dFTest.driver.getCurrentUrl();
                         URI uri = new URI(currentURL);
                         if (datosStep.getGrab_ErrorPageIfProblem() &&
                             (((String)dFTest.ctx.getAttribute("appPath")).contains(uri.getHost()) || uri.getHost().contains("mango.com")) &&
                             datosStep.getResultSteps()!=State.Ok) {
                             String windowHandle = WebDriverMngUtils.loadErrorPage(dFTest.driver);
                             try {
                                 byte[] errorFaces=dFTest.driver.getPageSource().getBytes();
                                 htmlsourcesMap.get(datosStep).add(errorFaces);                                                              
                             } finally {
                                 JavascriptExecutor js = (JavascriptExecutor) dFTest.driver;
                                 js.executeScript("window.close('" + Thread.currentThread().getName() + "');");
                                 dFTest.driver.switchTo().window(windowHandle);
                             }

                         }    
                         System.out.println("ROBOTEST2: LAZY REPORT ERROR.FACES REDIRECT");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (datosStep.getGrabHTML() && datosStep.getTypePage() == 0/* HTML */) {
                        try {
                            //Capture sources
                            htmlsourcesMap.get(datosStep).add(dFTest.driver.getPageSource().getBytes());
                            System.out.println("ROBOTEST2: LAZY REPORT HTML.SRC REDIRECT");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }    
                    
                }
            }
            return;
        }
        sendSkipTestExceptionIfSuiteStopping(dFTest.ctx);
        ValidationsDAO.insertValidationInStep(descripValidac, datosStep, dFTest.meth, dFTest.ctx);
        if (!avoidEvidences)
            storeFileEvidencesIfNeeded(datosStep, TypeStore.validation, dFTest);
    }

    /**
     * Si se ha marcado la Suite como STOPPED en BD skipeamos el Test
     */
    public static void sendSkipTestExceptionIfSuiteStopping(ITestContext context) {
        String executionId = context.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx);
        StateSuite stateSuite = SuitesDAO.getStateSuite(executionId);
        if (stateSuite==StateSuite.STOPPING)
            throw new SkipException("Received Signal for stop TestSuite");
    }

    private static void storeFileEvidencesIfNeeded(datosStep datosStep, TypeStore typeStore, DataFmwkTest dFTest) {
        String nameMethodWithFactory = fmwkTest.getMethodWithFactory(dFTest.meth, dFTest.ctx);
        datosStep.setNameMethodWithFactory(nameMethodWithFactory);
        if (typeStore==TypeStore.step) {
        	createPathForEvidencesStore(nameMethodWithFactory, dFTest.ctx);
        	storeNetTrafficIfNeeded(datosStep, dFTest.ctx);
        }
        
        storeHardcopyIfNeeded(datosStep, dFTest);
        storeHTMLIfNeeded(datosStep, dFTest);
    }
    
    private static void createPathForEvidencesStore(String nameMethodWithFactory, ITestContext ctx) {
        String pathEvidencias = getPathFolderEvidenciasStep(ctx.getOutputDirectory(), ctx.getName(), nameMethodWithFactory);
        File directorio = new File(pathEvidencias);
        directorio.mkdirs();
    }
    
    private static void storeHardcopyIfNeeded(datosStep datosStep, DataFmwkTest dFTest) {
        try {
            boolean browserGUI = true;
            int grabImg = 0;
            if (dFTest.ctx.getAttribute("grabImg") != null)
                grabImg = ((Integer)dFTest.ctx.getAttribute("grabImg")).intValue();

            if (dFTest.ctx.getAttribute("browserGUI") != null)
                browserGUI = ((Boolean)dFTest.ctx.getAttribute("browserGUI")).booleanValue();

            if ((datosStep.getGrabImage() || /* Está activada la grabación de imagen por paso o */
                 grabImg == 1 || /* Está activada la grabación de imagen a nivel general o */
                 datosStep.getResultSteps()!=State.Ok) &&
                 datosStep.getTypePage() == 0/*HTML*/&&
                 browserGUI) {
                 //Capture hardcopy
                 String nombreImagen = getPathFileEvidenciaStep(dFTest.ctx, datosStep.getNameMethodWithFactory(), datosStep.getStepNumber(), TypeEvidencia.imagen);
                 WebDriverArqUtils.captureEntirePageMultipleBrowsers(dFTest.driver, dFTest.ctx, nombreImagen);

                 //Capture errorPage.faces
                 String currentURL = dFTest.driver.getCurrentUrl();
                 URI uri = new URI(currentURL);
                 if (datosStep.getGrab_ErrorPageIfProblem() &&
                     (((String)dFTest.ctx.getAttribute("appPath")).contains(uri.getHost()) || uri.getHost().contains("mango.com")) &&
                     datosStep.getResultSteps()!=State.Ok)
                     WebDriverMngUtils.capturaErrorPage(dFTest, datosStep.getStepNumber());
            }
        } catch (Exception e) {
            pLogger.warn("Problema grabando imagen", e);
        }
    }
    
    private static void storeHTMLIfNeeded(datosStep datosStep, DataFmwkTest dFTest) {
        try {
            if (datosStep.getGrabHTML() && datosStep.getTypePage() == 0/* HTML */)
                WebDriverArqUtils.capturaHTMLPage(dFTest, datosStep.getStepNumber());
        }
        catch (Exception e) {
            pLogger.warn("Problema grabando HTML", e);
        }
    }
    
    private static void storeNetTrafficIfNeeded(datosStep datosStep, ITestContext ctx) {
	    try {
	    	String methodWithFactory = datosStep.getNameMethodWithFactory();
	        if (datosStep.getGrabNettrafic()) {
	            String nameFileHar = fmwkTest.getPathFileEvidenciaStep(ctx, methodWithFactory, datosStep.getStepNumber(), TypeEvidencia.har);
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

    public static void addValidation(int id, State result, List<SimpleValidation> listValidations) {
        SimpleValidation validation = new SimpleValidation(id, result);
        listValidations.add(validation);
    }

    public static String getOutputDirectory(String userDir, String suiteName, String idExecutedSuite) {
        String userDirOK = userDir;
        String lastCharUserDir = userDirOK.substring(userDirOK.length() - 1);
        if (File.separator.compareTo(lastCharUserDir)!=0)
            userDirOK+=File.separator;

        return (userDirOK + getPathOutputDirectoryFromUserDir(suiteName, idExecutedSuite));
    }

    public static String getPathOutputDirectoryFromUserDir(String suiteName, String idExecutedSuite) {
        return (Constantes.directoryOutputTests + File.separator + suiteName + File.separator + idExecutedSuite);
    }

    public static String getPathReportHTML(String outputDirectory) {
        return outputDirectory + File.separator + Constantes.nameReportHTMLTSuite;
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
        return (suiteXml.getParameter(Constantes.paramOutputDirectorySuite));
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

    public static String getPathFileEvidenciaStep(String outputDirectory, String testRunName, String methodWithFactory, int stepNumber, TypeEvidencia typeEvidencia) {
        String fileName = getNameFileEvidenciaStep(stepNumber, typeEvidencia);
        return (getPathFolderEvidenciasStep(outputDirectory, testRunName, methodWithFactory) + File.separator + fileName);
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
        return (Constantes.URL_SOFTWAREISHARD + getPathFileEvidenciaStep(context, methodWithFactory, stepNumber, TypeEvidencia.harp));
    }

    public static String getMethodWithFactory(Method method, ITestContext context) {
        String factory = (String)context.getAttribute("factory-" + String.valueOf(Thread.currentThread().getId()));
        if (factory!=null)
            return (method.getName() + "(" + factory + ")");

        return method.getName();
    }

    /**
     * @param finDirectory
     *          true:  devuelve la URL de la imagen correspondiente al 'directorio final' donde estarán ubicados los datos de los tests
     *          false: devuelve la URL de la imagen correspondiente al 'output directory' temporal durante la ejecución de los tests
     * @return URL de la hardcopy asociada a un step concreto
     */
    public static String getURLImgStep(datosStep datosStep, boolean finDirectory, Method method, ITestContext context) {
        String methodWithFactory = getMethodWithFactory(method, context);
        String pathImageInit = getPathFileEvidenciaStep(context, methodWithFactory, datosStep.getStepNumber(), TypeEvidencia.imagen);
        File fileImage = new File(pathImageInit);
        String applicationDNS = context.getCurrentXmlTest().getParameter(Constantes.paramApplicationDNS);
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
