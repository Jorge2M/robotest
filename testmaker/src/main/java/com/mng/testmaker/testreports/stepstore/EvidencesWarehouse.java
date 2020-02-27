package com.mng.testmaker.testreports.stepstore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.mng.testmaker.domain.suitetree.StepTM;
import com.mng.testmaker.domain.suitetree.TestRunTM;

public class EvidencesWarehouse {
	
	private Map<StepEvidence, String> storedEvidences = new HashMap<StepEvidence,String>();
	private final StepTM step;

	public EvidencesWarehouse(StepTM step) {
		this.step = step;
		storedEvidences.put(StepEvidence.html, "<html></html>");
	}
	
	public Map<StepEvidence, String> getStoredEvidences() {
		return storedEvidences;
	}
	public void setStoredEvidences(Map<StepEvidence, String> storedEvidences) {
		this.storedEvidences = storedEvidences;
	}
	public void putEvidence(StepEvidence evidence, String content) {
		storedEvidences.put(evidence, content);
	}
	public String getEvidence(StepEvidence evidence) {
		return storedEvidences.get(evidence);
	}
	
	public void store() {
		if (isNecessariStorage()) {
			createPathForEvidencesStore();
			for (StepEvidence evidence : StepEvidence.values()) {
				if (step.isNecessaryStorage(evidence)) {
					EvidenceStorer evidenceStorer = evidenceStorerFactory(evidence, step);
					if (evidenceStorer!=null) {
						evidenceStorer.captureContent(step);
						evidenceStorer.storeContentInFile(step);
					}
				}
			}
		}
	}
	
	private EvidenceStorer evidenceStorerFactory(StepEvidence evidence, StepTM step) {
		switch (evidence) {
		case har:
			return new NettrafficStorer();
		case imagen:
			return new HardcopyStorer();
		case errorpage:
			TestRunTM testRun = step.getTestRunParent();
			return testRun.getStorerErrorEvidence();
		case html:
			return new HtmlStorer();
		default:
			return null;
		}
	}

	private boolean isNecessariStorage() {
		for (StepEvidence evidence : StepEvidence.values()) {
			if (step.isNecessaryStorage(evidence)) {
				return true;
			}
		}
		return false;
	}

	private void createPathForEvidencesStore() {
		String pathEvidencias = step.getPathDirectory();
		File directorio = new File(pathEvidencias);
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
	}
}
