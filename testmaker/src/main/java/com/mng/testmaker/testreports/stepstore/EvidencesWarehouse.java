package com.mng.testmaker.testreports.stepstore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mng.testmaker.domain.suitetree.StepTM;
import com.mng.testmaker.domain.suitetree.TestRunTM;

public class EvidencesWarehouse {
	
	public enum Storage {
		File(true, false), 
		Memory(false, true), 
		FileAndMemory(true, true);
		
		boolean file;
		boolean memory;
		private Storage(boolean file, boolean memory) {
			this.file = file;
			this.memory = memory;
		}
		public boolean inFile() {
			return file;
		}
		public boolean inMemory() {
			return memory;
		}
	}
	
	private List<StepEvidenceContent> storedEvidences = new ArrayList<StepEvidenceContent>();
	private StepTM step;

	public EvidencesWarehouse() {}
	
	public EvidencesWarehouse(StepTM step) {
		this.step = step;
	}
	
	public void setStep(StepTM step) {
		this.step = step;
	}
	
	public List<StepEvidenceContent> getStoredEvidences() {  
		return storedEvidences;
	}
	public void setStoredEvidences(List<StepEvidenceContent> storedEvidences) {
		this.storedEvidences = storedEvidences;
	}
	public void addEvidence(StepEvidenceContent stepEvidenceContent) {
		storedEvidences.add(stepEvidenceContent);
	}
	public String getEvidenceContent(StepEvidence evidence) {
		for (StepEvidenceContent evidenceContent : storedEvidences) {
			if (evidenceContent.getStepEvidence()==evidence) {
				return evidenceContent.getContent();
			}
		}
		return "";
	}
	
	public void captureAndStore() {
		if (isNecessariStorage(step)) {
			createPathForEvidencesStore(step);
			for (StepEvidence evidence : StepEvidence.values()) {
				if (step.isNecessaryStorage(evidence)) {
					EvidenceStorer evidenceStorer = evidenceStorerFactory(evidence);
					if (evidenceStorer!=null) {
						evidenceStorer.captureAndStoreContent(step);
						evidenceStorer.storeContentInFile(step);
						addEvidence(new StepEvidenceContent(evidence, evidenceStorer.getContent()));
					}
				}
			}
		}
	}
	
	public void moveContentEvidencesToFile() {
		List<StepEvidenceContent> listStepEvidences = getStoredEvidences();
		if (listStepEvidences.size()==0) {
			return;
		}
		
		createPathForEvidencesStore(step);
		for (StepEvidenceContent evidence : listStepEvidences) {
			EvidenceStorer evidenceStorer = evidenceStorerFactory(evidence.getStepEvidence());
			if (evidenceStorer!=null) {
				evidenceStorer.saveContentEvidenceInFile(
					evidence.getContent(), 
					evidenceStorer.getPathFile(step));
			}
		}
		storedEvidences.clear();
	}
	
	private EvidenceStorer evidenceStorerFactory(StepEvidence evidence) {
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

	private boolean isNecessariStorage(StepTM step ) {
		for (StepEvidence evidence : StepEvidence.values()) {
			if (step.isNecessaryStorage(evidence)) {
				return true;
			}
		}
		return false;
	}

	private void createPathForEvidencesStore(StepTM step) {
		String pathEvidencias = step.getPathDirectory();
		File directorio = new File(pathEvidencias);
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
	}
}
