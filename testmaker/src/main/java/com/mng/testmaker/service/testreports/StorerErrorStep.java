package com.mng.testmaker.service.testreports;

import com.mng.testmaker.domain.StepTestMaker;

public interface StorerErrorStep {
	public abstract void store(StepTestMaker step) throws Exception;
}
