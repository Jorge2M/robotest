package com.mng.robotest.test80.mango.test.stpv.shop.modales;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendas;

public class ModalBuscadorTiendasStpV {

	private final ModalBuscadorTiendas modalBuscadorTiendas;
	
	public ModalBuscadorTiendasStpV(Channel channel, WebDriver driver) {
		modalBuscadorTiendas = new ModalBuscadorTiendas(channel, driver);
	}
	
	@Validation
	public ChecksTM validaBusquedaConResultados() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 5;
	 	validations.add(
			"La capa de búsqueda es visible",
			modalBuscadorTiendas.isVisible(1), State.Warn);
	 	validations.add(
			"Se ha localizado alguna tienda (la esperamos hasta " + maxSeconds + " segundos)",
			modalBuscadorTiendas.isPresentAnyTiendaUntil(maxSeconds), State.Warn);
		return validations;
	}
	
	@Step (
		description="Cerramos la capa correspondiente al resultado del buscador", 
		expected="La capa correspondiente a la búsqueda desaparece")
	public void close() {
		modalBuscadorTiendas.clickAspaForClose();
		checkModalSearchInvisible();
	}
	
	@Validation (
		description="La capa correspondiente a la búsqueda desaparece",
		level=State.Defect)
	private boolean checkModalSearchInvisible() {
		return (!modalBuscadorTiendas.isVisible());
	}
}
