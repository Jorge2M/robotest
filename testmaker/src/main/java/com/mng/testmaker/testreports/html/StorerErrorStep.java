package com.mng.testmaker.testreports.html;

import com.mng.testmaker.domain.StepTM;

public interface StorerErrorStep {
	public abstract void store(StepTM step) throws Exception;
}
