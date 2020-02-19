package com.mng.testmaker.domain.suitetree;

public enum StepEvidence {
	imagen("png"), 
	html("html"), 
	errorpage("-error.html"), 
	har("har"), 
	harp("harp");
	
	public String fileExtension;
	private StepEvidence(String fileExtension) {
		this.fileExtension = fileExtension;
	}
}
