package com.mng.robotest.test80.mango.test.stpv.shop.galeria;

import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.utils.DataFichaArt;

public class ResultSelectArtStep {
	public DatosStep datosStep;
	public DataFichaArt dataFichaArticulo;
	
	public static ResultSelectArtStep getNew(DatosStep datosStep, DataFichaArt dataFichaArticulo) {
		ResultSelectArtStep result = new ResultSelectArtStep();
		result.datosStep = datosStep;
		result.dataFichaArticulo = dataFichaArticulo;
		return result;
	}
}
