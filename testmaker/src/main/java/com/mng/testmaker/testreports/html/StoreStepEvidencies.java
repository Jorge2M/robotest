package com.mng.testmaker.testreports.html;

import java.io.File;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.StepTM;
import com.mng.testmaker.domain.suitetree.StepEvidence;
import com.mng.testmaker.domain.suitetree.SuiteTM;
import com.mng.testmaker.domain.suitetree.TestCaseTM;
import com.mng.testmaker.domain.suitetree.TestRunTM;
import com.mng.testmaker.service.webdriver.utils.WebUtils;

public class StoreStepEvidencies {
	
	private final StepTM step;
	private final TestRunTM testRunParent;
	private final TestCaseTM testCaseParent;
	private final String outputDirectorySuite;
	private final WebDriver driver;
	private final StorerErrorStep storerError;
	
	public static String prefixEvidenciaStep = "Step-";

	public StoreStepEvidencies(StepTM step) {
		this.step = step;
		this.testCaseParent = step.getTestCaseParent();
		this.testRunParent = testCaseParent.getTestRunParent();
		this.storerError = testRunParent.getStorerErrorStep();
		this.outputDirectorySuite = testRunParent.getTestNgContext().getOutputDirectory();
		this.driver = testCaseParent.getDriver();
	}

    private boolean isNecessariStorage() {
    	for (StepEvidence evidence : StepEvidence.values()) {
    		if (isNecessaryStorage(evidence)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean isNecessaryStorage(StepEvidence evidencia) {
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

	public void storeStepEvidencies() {
		if (isNecessariStorage()) {
			createPathForEvidencesStore();
			if (isNecessaryStorage(StepEvidence.har)) {
				storeNetTraffic();
			}
			if (isNecessaryStorage(StepEvidence.imagen)) {
				storeHardcopy();
			}
			if (isNecessaryStorage(StepEvidence.errorpage)) {
				storeErrorPage();
			}
			if (isNecessaryStorage(StepEvidence.html)) {
				storeHTML();
			}
		}
	}

	private void createPathForEvidencesStore() {
		String suitePath = SuiteTM.getPathDirectory(
				step.getSuiteParent().getName(), 
				step.getSuiteParent().getIdExecution());
		
		String pathEvidencias = 
			suitePath + File.separator + 
			step.getTestRunParent().getName() + File.separator +
			step.getTestCaseParent().getNameUnique();

		File directorio = new File(pathEvidencias);
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
	}

	private void storeHardcopy() {
		String nombreImagen = getPathFileEvidenciaStep(StepEvidence.imagen);
		try {
			WebUtils.captureEntirePageMultipleBrowsers(driver, nombreImagen);
		} 
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Problema grabando imagen", e);
		}
	}

	private void storeErrorPage() {
		String fileError = getPathFileEvidenciaStep(StepEvidence.errorpage);
		if (!new File(fileError).exists()) {
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

	private void storeHTML() {
		String nombreImagen = getPathFileEvidenciaStep(StepEvidence.html);
		if (!new File(nombreImagen).exists()) {
			try {
				WebUtils.capturaHTMLPage(step);
			}
			catch (Exception e) {
				Log4jConfig.pLogger.warn("Problema grabando HTML", e);
			}
		}
	}
    
    private void storeNetTraffic() {
    	String nameFileHar = getPathFileEvidenciaStep(StepEvidence.har);
    	if (!new File(nameFileHar).exists()) {
		    try {
	        	NetTrafficSaver netTraffic = new NetTrafficSaver();
	            netTraffic.storeHarInFile(nameFileHar);
	            netTraffic.copyHarToHarp(nameFileHar);
	            Thread.sleep(1000);
		    }
		    catch (Exception e) {
		    	Log4jConfig.pLogger.info("Problema grabando NetTraffic", e);
		    }    
    	}
    }

	public String getPathFolderEvidenciasStep() {
		return (
			outputDirectorySuite + File.separator + 
			step.getTestRunParent().getName() + File.separator + 
			testCaseParent.getNameUnique());
	}

	public String getNameFileEvidenciaStep(StepEvidence evidence) {
		String extension = "." + evidence.fileExtension;
		return (prefixEvidenciaStep + Integer.toString(step.getNumber()) + extension);
	}

//    public static String getPathFileEvidenciaStep(StepBean step, StepEvidence evidence) {
//    	String outputDirectory = step.getTestRunParent().getTestNgContext().getOutputDirectory();
//    	return (getPathFileEvidenciaStep(outputDirectory, step, evidence));
//    }

	public String getPathFileEvidenciaStep(StepEvidence evidence) {
		String fileName = getNameFileEvidenciaStep(evidence);
		return (getPathFolderEvidenciasStep() + File.separator + fileName);
	}
}
