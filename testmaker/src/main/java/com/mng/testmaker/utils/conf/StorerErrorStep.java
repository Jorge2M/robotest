package com.mng.testmaker.utils.conf;

import com.mng.testmaker.domain.StepTestMaker;

public interface StorerErrorStep {
	public abstract void store(StepTestMaker step) throws Exception;
}
