package com.mng.testmaker.testreports.stepstore;

import com.mng.testmaker.domain.suitetree.StepTM;

public class HtmlStorer extends EvidenceStorer {

	public HtmlStorer() {
		super(StepEvidence.html);
	}
	
	@Override
	public void captureContent(StepTM step) {
		this.content = step.getDriver().getPageSource();
	}
}
