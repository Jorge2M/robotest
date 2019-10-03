package com.mng.robotest.test80.arq.listeners;

import java.io.File;
import java.net.HttpURLConnection;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.testng.*;

import com.mng.robotest.test80.arq.access.InputParamsTestMaker;
import com.mng.robotest.test80.arq.jdbc.Connector;
import com.mng.robotest.test80.arq.jdbc.dao.MethodsDAO;
import com.mng.robotest.test80.arq.jdbc.dao.ParamsDAO;
import com.mng.robotest.test80.arq.jdbc.dao.StepsDAO;
import com.mng.robotest.test80.arq.jdbc.dao.SuitesDAO;
import com.mng.robotest.test80.arq.jdbc.dao.TestRunsDAO;
import com.mng.robotest.test80.arq.jdbc.dao.ValidationsDAO;
import com.mng.robotest.test80.arq.jdbc.to.ResultMethod;
import com.mng.robotest.test80.arq.jdbc.to.ResultTestRun;
import com.mng.robotest.test80.arq.listeners.utils.ResourcesExtractor;
import com.mng.robotest.test80.arq.utils.StateSuite;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.GestorWebDrv;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.indexSuite;
import com.mng.robotest.test80.data.ConstantesTestMaker;
import com.mng.robotest.test80.data.TestMakerContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InvokeListener<T extends Enum<T>, Y extends Enum<Y>, Z extends Enum<Z>> 
		extends TestListenerAdapter implements ISuiteListener {
	
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    ITestContext ctx1erTestRun = null;
    HttpURLConnection httpUrlCallBack = null;
  
    @Override //Start Suite 
    public void onStart(ISuite suite) {
    	TestMakerContext testMakerCtx = TestMakerContext.getTestMakerContext(suite);
        String outdySuite = fmwkTest.getOutputDirectory(System.getProperty("user.dir"), suite.getName(), testMakerCtx.getIdSuiteExecution());
        suite.getXmlSuite().getParameters().put(ConstantesTestMaker.paramOutputDirectorySuite, outdySuite);
        File path = new File(outdySuite);
        path.mkdir();
        fmwkTest.configLog4java(outdySuite);
        grabSqliteBDifNotExists();
        SuitesDAO.insertSuiteInit(suite);
    }
    
    private void grabSqliteBDifNotExists() {
        ResourcesExtractor resExtractor = ResourcesExtractor.getNew();
        File fileSqliteBD = new File(Connector.getSQLiteFilePathAutomaticTestingSchema());
        if (!fileSqliteBD.exists()) {
	        resExtractor.copyDirectoryResources(
	        	"sqlite/", 
	            Connector.getSQLitePathDirectory());
        }
    }
    
    @Override //End Suite
    public void onFinish(ISuite suite) {
        updateSuiteData(this.ctx1erTestRun);
        GestorWebDrv gestorDrv = GestorWebDrv.getGestorFromCtx(this.ctx1erTestRun);
        if (gestorDrv!=null) {
            gestorDrv.removeAllStrWd();
        }
        
        sendEmailIfNeeded(suite);
    }
    
    @Override //Start TestRun... aunque se utiliza para gestionar el inicio de la SUITE
    //Lo utilizamos básicamente para realizar acciones a nivel de inicio de la SUITE (que coincide con el inicio del 1er TestRun) con un ITestContext disponible
    public synchronized void onStart(ITestContext testContext) {
        //Si se trata del 1er TestRun (no hay ningún ITestContext informado) definimos algunos datos específicos a nivel de la SUITE
        if (this.ctx1erTestRun == null) {
            //Realizamos un backup del context de ejecución del TestRun para que esté disponible a nivel del onFinish de la Suite
            this.ctx1erTestRun = testContext;
        }
        
        //Establecemos el test-output directory (lo unificamos con el de la Suite)
        TestRunner runner = (TestRunner) testContext;
        runner.setOutputDirectory(testContext.getCurrentXmlTest().getParameter(ConstantesTestMaker.paramOutputDirectorySuite));
    }
  
    @Override //End TestRun
    public void onFinish(ITestContext testContext) {
        //Grabamos el testrun en la tabla  en la tabla SUITES
        grabarTestRun(testContext);
        purgeHistoricalDataIfNeeded();
    }
    
    public static void purgeHistoricalDataIfNeeded() {
        int numDiasToMaintain = (Integer.valueOf(ParamsDAO.getParam("DIAS_TO_MAINTAIN_REPORTS"))).intValue();
        purgeHistoricalDataRobotest(numDiasToMaintain);
    }
    
    public static void purgeHistoricalDataRobotest(int numDiasToMaintain) {
        pLogger.info("Dias to maintain: " + numDiasToMaintain);
        if (isNeededPurgeHistoricalData(numDiasToMaintain)) {
            long diasInMilis = 86400000L; 
            Date fechaDesde = new Date(System.currentTimeMillis() - (numDiasToMaintain * diasInMilis));
            pLogger.info("Init Purge data SQLite");
            SuitesDAO.deleteHistorical(fechaDesde); 
            MethodsDAO.deleteHistorical(fechaDesde);        
            TestRunsDAO.deleteHistorical(fechaDesde);
            StepsDAO.deleteHistorical(fechaDesde);
            ValidationsDAO.deleteHistorical();
            
            //ManageBD.vacuum(); //With large data this sentence maintain the whole SQLite Busy during many minutes (f.e. 4 minutes with a 1,3GB BD)
            pLogger.info("End Purge data SQLite");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            ParamsDAO.setParam("PURGED_DATA_FROM", sdf.format(fechaDesde));
        }
    }
    
    public static boolean isNeededPurgeHistoricalData(int numDiasToMaintain) {
        String dateLastPurgeAAAAMMDD = ParamsDAO.getParam("PURGED_DATA_FROM");
        if (dateLastPurgeAAAAMMDD!=null && "".compareTo(dateLastPurgeAAAAMMDD)!=0) {
            java.util.Date dateLastPurge;
            String dataFormat = "yyyy-MM-dd";
            try {
                dateLastPurge = new SimpleDateFormat(dataFormat).parse(dateLastPurgeAAAAMMDD);
            }
            catch (ParseException e) {
                pLogger.warn("Data with no data format " + dataFormat, e);
                return false;
            }
            
            Calendar calLastPurge = Calendar.getInstance();
            calLastPurge.setTime(dateLastPurge);
            Calendar calToPurge = Calendar.getInstance();
            calToPurge.add(Calendar.DATE, numDiasToMaintain * -1);
            calToPurge.set(Calendar.HOUR_OF_DAY, 0);
            calToPurge.set(Calendar.MINUTE, 0);
            calToPurge.set(Calendar.SECOND, 0);
            calToPurge.set(Calendar.MILLISECOND, 0);
            if (calLastPurge.before(calToPurge)) {
                return true;
            }
        }
        
        return false;
    }

    @Override //Start Method
    public void onTestStart(ITestResult result) {
        //No funciona. La SkipException no tiene efecto cuando se lanza desde aquí
        //fmwkTest.sendSkipTestExceptionIfSuiteStopping(result.getTestContext());
    }
  
    @Override //End Method Success
    public void onTestSuccess(ITestResult tr) {
        tratamientoFinMetodo(tr);
    }
  
    @Override //End Method Skipped
    public void onTestSkipped(ITestResult tr) {
        tratamientoFinMetodo(tr);  
    }
  
    @Override //End Method Failure
    public void onTestFailure(ITestResult tr) {
        pLogger.error("Exception for TestNG", tr.getThrowable());
        tratamientoFinMetodo(tr);  
    }

    /**
     * Se realiza la grabaci�n de la suite en BD.
     */
    private void updateSuiteData(ITestContext context) {
    	TestMakerContext testMakerCtx = TestMakerContext.getTestMakerContext(context);
        indexSuite suiteId = new indexSuite(testMakerCtx.getIdSuiteExecution(), testMakerCtx.getInputData().getSuiteName());
        ResultTestRun resultTestRun = MethodsDAO.getResultTestRunAccordingMethods(suiteId, context.getName());
        SuitesDAO.updateEndSuiteFromCtx(resultTestRun, context);
    }

    
    protected void sendEmailIfNeeded(ISuite suite) {
    	TestMakerContext testMakerCtx = TestMakerContext.getTestMakerContext(suite);
        if (testMakerCtx.isSendMailInEndSuite()) {
        	testMakerCtx.getSendMailData().sendMail(this.httpUrlCallBack, this.ctx1erTestRun);
        }        
    }
    
    /**
     * Se realiza la grabación de un TestRun en la tabla TESTRUNS
     */
    private void grabarTestRun(ITestContext context) {
    	TestMakerContext testMakerCtx = TestMakerContext.getTestMakerContext(context);
    	InputParamsTestMaker inputData = testMakerCtx.getInputData();
        indexSuite suiteId = new indexSuite(testMakerCtx.getIdSuiteExecution(), inputData.getSuiteName());
        ResultTestRun resultStep = MethodsDAO.getResultTestRunAccordingMethods(suiteId, context.getName()); 
        TestRunsDAO.insertFromCtx(resultStep, context);
    }
  
    private void tratamientoFinMetodo(ITestResult tr) {
    	TestCaseData.clearStackDatosStep();
        ITestContext context = tr.getTestContext();
    	TestMakerContext testMakerCtx = TestMakerContext.getTestMakerContext(context);
    	InputParamsTestMaker inputData = testMakerCtx.getInputData();
        String idExecSuite = testMakerCtx.getIdSuiteExecution();
        StateSuite stateSuite = SuitesDAO.getStateSuite(idExecSuite);
        if (stateSuite!=StateSuite.STOPPING) {
            indexSuite suiteId = new indexSuite(idExecSuite, inputData.getSuiteName());
            String methodWithFactory = fmwkTest.getMethodWithFactory(tr.getMethod().getMethod(), context);
            ResultMethod resultMethod = StepsDAO.getResultMethodAccordingSteps(suiteId, context.getName(), methodWithFactory);
            MethodsDAO.inserMethod(resultMethod, tr);
        }
    }
}