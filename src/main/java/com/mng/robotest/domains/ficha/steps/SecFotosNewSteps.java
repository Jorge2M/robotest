package com.mng.robotest.domains.ficha.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.ficha.pageobjects.DataFoto;
import com.mng.robotest.domains.ficha.pageobjects.SecFotosNew;
import com.mng.robotest.domains.ficha.pageobjects.TipoImagenProducto;

public class SecFotosNewSteps {

	public static void validaLayoutFotosNew(boolean isFichaAccesorios, WebDriver driver) {
		checkTypeOfFirstFoto(driver);
		checkLayoutFicha(isFichaAccesorios, driver);
	}
	
	@Validation
	private static ChecksTM checkTypeOfFirstFoto(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		DataFoto dataFoto = SecFotosNew.getDataFoto(1, 1, driver);
	 	validations.add(
			"La 1a foto es de tipo <b>" + TipoImagenProducto.DETALLES + " o " + 
										  TipoImagenProducto.DETALLES_9 + " o " +
										  TipoImagenProducto.OUTFIT + " o " + 
										  TipoImagenProducto.BODEGON + "</b>",
			dataFoto!=null && (
			dataFoto.typeImage==TipoImagenProducto.DETALLES || 
			dataFoto.typeImage==TipoImagenProducto.DETALLES_9 || 
			dataFoto.typeImage==TipoImagenProducto.OUTFIT || 
			dataFoto.typeImage==TipoImagenProducto.BODEGON), State.Defect);
	 	
	 	return validations;
	}
		  
	@Validation 
	private static ChecksTM checkLayoutFicha(boolean isFichaAccesorios, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		int numFotosExpected1rstLine_A = 1;
		int numFotosExpected1rstLine_B = 2;
		if (isFichaAccesorios) {
			numFotosExpected1rstLine_A = 2;
			numFotosExpected1rstLine_B = 2;
		}
		
		int numFotos1rstLine = SecFotosNew.getNumFotosLine(1, driver);
	 	validations.add(
			"La 1a línea tiene " + numFotosExpected1rstLine_A + " o " + numFotosExpected1rstLine_B + " fotos",
			numFotos1rstLine==numFotosExpected1rstLine_A || numFotos1rstLine==numFotosExpected1rstLine_B, State.Warn);
	 	
	 	int numLinesFotos = SecFotosNew.getNumLinesFotos(driver);
	 	int numFotosLastLine = 0;
		if (numLinesFotos>1) {
			numFotosLastLine = SecFotosNew.getNumFotosLine(numLinesFotos, driver); 
		}
		int minFotosExpectedLastLine = 5;
	 	validations.add(
			"La última línea tiene < " + minFotosExpectedLastLine + " fotos",
			numFotosLastLine<minFotosExpectedLastLine, State.Warn);
	 	
	 	if (numLinesFotos > 2) {
	 		boolean allLinesWith2fotos = true;
			for (int i=2; i<numLinesFotos; i++) {
				int numFotosLine = SecFotosNew.getNumFotosLine(i, driver);
				if (numFotosLine!=2) {
					allLinesWith2fotos = false;
					break;
				}
			}
		 	validations.add(
				"Las líneas intermedias tienen 2 fotos",
				allLinesWith2fotos, State.Warn);
	 	}	
	 	
	 	return validations;
	}
}
