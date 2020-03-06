package com.mng.testmaker.testreports.stepstore;

import com.mng.testmaker.domain.suitetree.StepTM;

public class HtmlStorer extends EvidenceStorer {

	public HtmlStorer() {
		super(StepEvidence.html);
	}
	
	@Override
	protected String captureContent(StepTM step) {
		return step.getDriver().getPageSource();
	}
}
