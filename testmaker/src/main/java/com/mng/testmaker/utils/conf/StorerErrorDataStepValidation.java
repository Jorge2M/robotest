package com.mng.testmaker.utils.conf;

import com.mng.testmaker.utils.DataFmwkTest;
import com.mng.testmaker.utils.controlTest.DatosStep;

public interface StorerErrorDataStepValidation {
	public abstract void store(DataFmwkTest dFTest, DatosStep datosStep) throws Exception;
}
