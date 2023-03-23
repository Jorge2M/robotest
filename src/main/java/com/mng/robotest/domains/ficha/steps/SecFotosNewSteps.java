package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.ficha.beans.DataFoto;
import com.mng.robotest.domains.ficha.pageobjects.SecFotosNew;
import com.mng.robotest.domains.ficha.pageobjects.TipoImagenProducto;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecFotosNewSteps extends StepBase {

	private final SecFotosNew secFotosNew = new SecFotosNew();
	
	public void validaLayoutFotosNew(boolean isFichaAccesorios) {
		checkTypeOfFirstFoto();
		checkLayoutFicha(isFichaAccesorios);
	}
	
	@Validation
	private ChecksTM checkTypeOfFirstFoto() {
		var checks = ChecksTM.getNew();
		DataFoto dataFoto = secFotosNew.getDataFoto(1, 1);
	 	checks.add(
			"La 1a foto es de tipo <b>" + TipoImagenProducto.DETALLES + " o " + 
										  TipoImagenProducto.DETALLES_9 + " o " +
										  TipoImagenProducto.OUTFIT + " o " + 
										  TipoImagenProducto.BODEGON + "</b>",
			dataFoto!=null && (
			dataFoto.typeImage==TipoImagenProducto.DETALLES || 
			dataFoto.typeImage==TipoImagenProducto.DETALLES_9 || 
			dataFoto.typeImage==TipoImagenProducto.OUTFIT || 
			dataFoto.typeImage==TipoImagenProducto.BODEGON), Defect);
	 	
	 	return checks;
	}
		  
	@Validation 
	private ChecksTM checkLayoutFicha(boolean isFichaAccesorios) {
		var checks = ChecksTM.getNew();
		int numFotosExpected1rstLineA = 1;
		int numFotosExpected1rstLineB = 2;
		if (isFichaAccesorios) {
			numFotosExpected1rstLineA = 2;
			numFotosExpected1rstLineB = 2;
		}
		
		int numFotos1rstLine = secFotosNew.getNumFotosLine(1);
	 	checks.add(
			"La 1a línea tiene " + numFotosExpected1rstLineA + " o " + numFotosExpected1rstLineB + " fotos",
			numFotos1rstLine==numFotosExpected1rstLineA || numFotos1rstLine==numFotosExpected1rstLineB, Warn);
	 	
	 	int numLinesFotos = secFotosNew.getNumLinesFotos();
	 	int numFotosLastLine = 0;
		if (numLinesFotos>1) {
			numFotosLastLine = secFotosNew.getNumFotosLine(numLinesFotos); 
		}
		int minFotosExpectedLastLine = 5;
	 	checks.add(
			"La última línea tiene < " + minFotosExpectedLastLine + " fotos",
			numFotosLastLine<minFotosExpectedLastLine, Warn);
	 	
	 	if (numLinesFotos > 2) {
	 		boolean allLinesWith2fotos = true;
			for (int i=2; i<numLinesFotos; i++) {
				int numFotosLine = secFotosNew.getNumFotosLine(i);
				if (numFotosLine!=2) {
					allLinesWith2fotos = false;
					break;
				}
			}
		 	checks.add(
				"Las líneas intermedias tienen 2 fotos",
				allLinesWith2fotos, Warn);
	 	}	
	 	
	 	return checks;
	}
}
