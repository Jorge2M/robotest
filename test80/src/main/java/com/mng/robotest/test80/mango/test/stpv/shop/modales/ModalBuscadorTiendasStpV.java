package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendas;

public class ModalBuscadorTiendasStpV {

	@Validation
    public static ChecksTM validaBusquedaConResultados(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 5;
	 	validations.add(
			"La capa de búsqueda es visible",
			ModalBuscadorTiendas.isVisible(driver), State.Warn);
	 	validations.add(
			"Se ha localizado alguna tienda (la esperamos hasta " + maxSeconds + " segundos)",
			ModalBuscadorTiendas.isPresentAnyTiendaUntil(driver, maxSeconds), State.Warn);
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
