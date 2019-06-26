package com.mng.robotest.test80.arq.utils.conf;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;

public interface StorerErrorDataStepValidation {
	public abstract void store(DataFmwkTest dFTest, DatosStep datosStep) throws Exception;
}
