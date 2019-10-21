package com.mng.testmaker.utils.controlTest;

import java.io.File;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.StepTestMaker.StepEvidence;
import com.mng.testmaker.service.testreports.StorerErrorStep;
import com.mng.testmaker.utils.NetTrafficMng;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.conf.Log4jConfig;
import com.mng.testmaker.utils.otras.WebDriverArqUtils;

public class StoreStepEvidencies {
	
    public static String prefixEvidenciaStep = "Step-";

    private static boolean isNecessariStorage(StepTestMaker step) {
    	for (StepEvidence evidence : StepEvidence.values()) {
    		if (isNecessaryStorage(evidence, step)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private static boolean isNecessaryStorage(StepEvidence evidencia, StepTestMaker step) {
        SaveWhen saveEvidenceWhen = step.getWhenSave(evidencia);
        switch (saveEvidenceWhen) {
        case Always:
        	return true;
        case Never:
        	return false;
        case IfProblem:
        	if (step.getResultSteps()!=State.Ok &&
        	    !step.isAllValidationsWithAvoidEvidences()) {
        		return true;
        	}
        }
        return false;
    }
     
    public static void storeStepEvidencies(StepTestMaker step) {
    	if (isNecessariStorage(step)) {
	        createPathForEvidencesStore(step);
	        if (isNecessaryStorage(StepEvidence.har, step)) {
	        	storeNetTraffic(step);
	        }
	        if (isNecessaryStorage(StepEvidence.imagen, step)) {
	        	storeHardcopy(step);
	        }
	        if (isNecessaryStorage(StepEvidence.errorpage, step)) {
	        	storeErrorPage(step);
	        }
	        if (isNecessaryStorage(StepEvidence.html, step)) {
	        	storeHTML(step);
	        }
    	}
    }
    
    private static void createPathForEvidencesStore(StepTestMaker step) {
    	String suitePath = step.getSuiteParent().getPathDirectory();
        String pathEvidencias = 
        	suitePath + File.separator + 
        	step.getTestRunParent().getName() + File.separator +
        	step.getTestCaseParent().getNameUnique();
        
        File directorio = new File(pathEvidencias);
        if (!directorio.exists()) {
        	directorio.mkdirs();
        }
    }
    
    private static void storeHardcopy(StepTestMaker step) {
        String nombreImagen = getPathFileEvidenciaStep(step, StepEvidence.imagen);
    	//if (!new File(nombreImagen).exists()) {
	        try {
	            WebDriver driver = step.getTestCaseParent().getDriver();
	            WebDriverArqUtils.captureEntirePageMultipleBrowsers(driver, nombreImagen);
	        } 
	        catch (Exception e) {
	        	Log4jConfig.pLogger.warn("Problema grabando imagen", e);
	        }
    	//}
    }
    
    private static void storeErrorPage(StepTestMaker step) {
        String fileError = getPathFileEvidenciaStep(step, StepEvidence.errorpage);
        if (!new File(fileError).exists()) {
	    	StorerErrorStep storerError = step.getTestRunParent().getStorerErrorStep();
	    	if (storerError!=null) {
		    	try {
		    		storerError.store(step);
				}
			    catch (Exception e) {
		            Log4jConfig.pLogger.warn("Problema grabando ErrorPage", e);
		        }
	    	}
        }
    }
    
    private static void storeHTML(StepTestMaker step) {
        String nombreImagen = getPathFileEvidenciaStep(step, StepEvidence.html);
	        if (!new File(nombreImagen).exists()) {
	        try {
	        	WebDriverArqUtils.capturaHTMLPage(step);
	        }
	        catch (Exception e) {
	            Log4jConfig.pLogger.warn("Problema grabando HTML", e);
	        }
        }
    }
    
    private static void storeNetTraffic(StepTestMaker step) {
    	String nameFileHar = getPathFileEvidenciaStep(step, StepEvidence.har);
    	if (!new File(nameFileHar).exists()) {
		    try {
	        	NetTrafficMng netTraffic = new NetTrafficMng();
	            netTraffic.storeHarInFile(nameFileHar);
	            netTraffic.copyHarToHarp(nameFileHar);
	            Thread.sleep(1000);
		    }
		    catch (Exception e) {
		    	Log4jConfig.pLogger.info("Problema grabando NetTraffic", e);
		    }    
    	}
    }

    
    public static String getPathFolderEvidenciasStep(String outputDirectorySuite, String testRun, String methodWithFactory) {
        return (outputDirectorySuite + File.separator + testRun + File.separator + methodWithFactory);
    }

    public static String getNameFileEvidenciaStep(int stepNumber, StepEvidence evidence) {
        String extension = "." + evidence.fileExtension;
        return (prefixEvidenciaStep + Integer.toString(stepNumber) + extension);
    }

    public static String getPathFileEvidenciaStep(StepTestMaker step, StepEvidence evidence) {
    	String outputDirectory = step.getTestRunParent().getTestNgContext().getOutputDirectory();
    	return (getPathFileEvidenciaStep(outputDirectory, step, evidence));
    }
    
    public static String getPathFileEvidenciaStep(String outputDirectory, StepTestMaker step, StepEvidence evidence) {
        int stepNumber = step.getPositionInTestCase();
        String testCaseNameUnique = step.getTestCaseParent().getNameUnique();
        String testRunName = step.getTestCaseParent().getTestRunParent().getName();
        
        String fileName = getNameFileEvidenciaStep(stepNumber, evidence);
        return (getPathFolderEvidenciasStep(outputDirectory, testRunName, testCaseNameUnique) + File.separator + fileName);
    }

}
