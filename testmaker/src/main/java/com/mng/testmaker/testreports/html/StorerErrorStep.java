package com.mng.testmaker.testreports.html;

import com.mng.testmaker.domain.StepTestMaker;

public interface StorerErrorStep {
	public abstract void store(StepTestMaker step) throws Exception;
}
