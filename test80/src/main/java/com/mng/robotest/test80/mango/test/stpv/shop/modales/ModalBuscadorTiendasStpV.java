package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendas;

public class ModalBuscadorTiendasStpV {

	@Validation
    public static ChecksResult validaBusquedaConResultados(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
	 	validations.add(
			"La capa de búsqueda es visible",
			ModalBuscadorTiendas.isVisible(driver), State.Warn);
	 	validations.add(
			"Se ha localizado alguna tienda (la esperamos hasta " + maxSecondsWait + " segundos)",
			ModalBuscadorTiendas.isPresentAnyTiendaUntil(driver, maxSecondsWait), State.Warn);
		return validations;
    }
    
	@Step (
		description="Cerramos la capa correspondiente al resultado del buscador", 
        expected="La capa correspondiente a la búsqueda desaparece")
    public static void close(WebDriver driver) {
        ModalBuscadorTiendas.clickAspaForClose(driver);
        checkModalSearchInvisible(driver);
    }
	
	@Validation (
		description="La capa correspondiente a la búsqueda desaparece",
		level=State.Defect)
	private static boolean checkModalSearchInvisible(WebDriver driver) {
		return (!ModalBuscadorTiendas.isVisible(driver));
	}
}
