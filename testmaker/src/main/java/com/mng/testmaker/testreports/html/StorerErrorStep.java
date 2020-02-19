package com.mng.testmaker.testreports.html;

import com.mng.testmaker.domain.suitetree.StepTM;

public interface StorerErrorStep {
	public abstract void store(StepTM step) throws Exception;
}
