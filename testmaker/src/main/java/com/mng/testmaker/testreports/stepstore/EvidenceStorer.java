package com.mng.testmaker.testreports.stepstore;

import java.io.File;
import java.io.FileWriter;

import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.domain.suitetree.StepTM;

public abstract class EvidenceStorer {
	
	final StepEvidence evidenceType;
	String content;

	abstract public void captureContent(StepTM step);
	
	public EvidenceStorer(StepEvidence evidenceType) {
		this.evidenceType = evidenceType;
	}
	
	public void recoveryContent(StepTM step) {
		this.content = step.getEvidencesWarehouse().getEvidence(evidenceType);
	}

	public void storeContentInFile(StepTM step) {
		String pathFile = getPathFile(step);
		if (!new File(pathFile).exists()) {
			saveContentEvidenceInFile(content, pathFile);
		}
	}
	
	public void storeContentInStep(StepTM step) {
		step.getEvidencesWarehouse().putEvidence(evidenceType, content);
	}
	
	public String getPathFile(StepTM step) {
		return evidenceType.getPathFile(step);
	}
	
	public void saveContentEvidenceInFile(String content, String pathFile) {
		try {
			File file = new File(pathFile);
			try (FileWriter fw = new FileWriter(file)) {
				fw.write(content);
			}
		} 
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Problem saving File " + pathFile, e);
		}
	}
	
}
