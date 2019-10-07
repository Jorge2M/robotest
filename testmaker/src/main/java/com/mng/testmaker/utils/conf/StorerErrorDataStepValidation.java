package com.mng.testmaker.utils.conf;

import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.utils.DataFmwkTest;

public interface StorerErrorDataStepValidation {
	public abstract void store(DataFmwkTest dFTest, StepTestMaker datosStep) throws Exception;
}
