package com.mng.robotest.tests.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import org.openqa.selenium.StaleElementReferenceException;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.manto.pageobjects.PageOrdenacionDePrendas.Modal.*;
import static com.mng.robotest.tests.domains.manto.pageobjects.PageOrdenacionDePrendas.Orden.*;
import static com.mng.robotest.tests.domains.manto.pageobjects.PageOrdenacionDePrendas.Section.*;
import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PageOrdenacionDePrendasSteps extends StepMantoBase {

	public static final String REF_PRENDA = "";
	
	public void mantoOrdenacionInicio() {
		selectDisponibilidadPreAndClickVerTiendas();
		selectShe();
	}

	public void mantoSeccionNuevo() {
		selectSectionNuevoMasVerPrendas();
		bajarPrenda();
	}

	public void ordenacionModal() {
		aplicarOrden();
		aceptarOrdenPais();
	}

	@Validation
	public ChecksTM validateIsPage() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Estamos en la página " + TITULO.getXPath(),
			state(VISIBLE, INITIAL_TITULO.getBy()).wait(10).check());
		
		checks.add(
			"Aparece el desplegable de tiendas",
			state(VISIBLE, DESPLEGABLE_TIENDAS.getBy()).wait(10).check());
		
		checks.add(
			"El botón <b>Ver Tiendas</b> está en la página",
			state(VISIBLE, VER_TIENDAS.getBy()).wait(10).check());
		
		return checks;
	}

	@Step(
		description="Seleccionamos la opción <b>Disponibilidad Pre</b> + Ver tiendas",
		expected="Aparecen los diferentes indicadores de secciones",
		saveErrorData = NEVER)
	private void selectDisponibilidadPreAndClickVerTiendas() {
		clickDesplegableTiendas();
		click(VER_TIENDAS.getBy()).exec();
		validatePreProductionElements();
	}
	
	private void clickDesplegableTiendas() {
		try {
			select(DESPLEGABLE_TIENDAS.getBy(), "availability.ws.pre.mango.com").type(Value).exec();
		}
		catch (StaleElementReferenceException e) {
			//Hay un error en TestMaker donde no se contempla este caso
		}
	}

	@Validation
	private ChecksTM validatePreProductionElements() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Está presente el enlace de <b>She</b>",
			state(VISIBLE, SHE.getBy()).wait(10).check());
		
		checks.add(
			"Está presente el enlace de <b>He</b>",
			state(VISIBLE, HE.getBy()).check());
		
		checks.add(
			"Está presente el enlace de <b>Niños</b>",
			state(VISIBLE, NINOS.getBy()).check());

		checks.add(
			"Está presente el enlace de <b>Home</b>",
			state(VISIBLE, HOME.getBy()).check());		
		
		return checks;
	}

	@Step(
		description="Seleccionamos la seccion de <b>She</b>",
		expected="Podemos seleccionar que queremos realizar",
		saveErrorData = NEVER)
	private void selectShe() {
		click(SHE.getBy()).waitLoadPage(3).exec();
		validateSectionShe(13);
	}

	@Validation(
		description="1) Está presente el selector de secciones dentro de <b>She</b>",
		level=WARN)
	private boolean validateSectionShe(int seconds) {
		return (state(VISIBLE, SELECTOR_ORDENACION.getBy()).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos la sección de <b>Nuevo</b> en el desplegable + <b>Ver prendas</b>",
		expected="Aparece el botón \"Aplicar Orden\" y una lista de prendas",
		saveErrorData = NEVER)
	private void selectSectionNuevoMasVerPrendas() {
		selectNuevo();
		clickVerPrendas();
		checkButtonAplicarOrdenVisible(25);
		checkImagesVisible();
	}
	private void selectNuevo() {
		try {
			select(SELECTOR_ORDENACION.getBy(), "nuevo").type(Value).exec();
		} catch (StaleElementReferenceException e) {
			waitMillis(1000);
			select(SELECTOR_ORDENACION.getBy(), "nuevo").type(Value).exec();
		}
	}
	
	private void clickVerPrendas() {
		try {
			click(VER_PRENDAS.getBy()).waitLink(2).exec();
		}
		catch (StaleElementReferenceException e) {
			waitMillis(1000);
			click(VER_PRENDAS.getBy()).exec();
		}
	}	
	
	@Validation(
        description="Es visible el botón \"Aplicar Orden\" " + SECONDS_WAIT,
        level=WARN)
	private boolean checkButtonAplicarOrdenVisible(int seconds) {
        return state(VISIBLE, APLICAR_ORDEN.getBy()).wait(seconds).check();
	}

	@Validation(description="Aparecen imagenes en la nueva página")
	private boolean checkImagesVisible() {
        return state(VISIBLE, PRUEBA_IMAGEN.getBy()).check();
	}

	@Step(
		description="Enviamos la primera prenda al final",
		expected="La prenda ha cambiado",
		saveErrorData = NEVER)
	private void bajarPrenda() {
		click(PRIMERA_PRENDA.getBy()).exec();
		moveToElement(BAJAR_PRENDA.getBy());
		click(BAJAR_PRENDA.getBy()).waitLink(3).exec();
		validateBajarPrenda(15);
	}

	@Validation
	private ChecksTM validateBajarPrenda(int seconds) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Se sigue viendo la segunda prenda",
			state(VISIBLE, SEGUNDA_PRENDA.getBy()).wait(seconds).check());
		
		checks.add(
			"La primera prenda no se corresponde con la que había inicialmente",
			state(VISIBLE, PRIMERA_PRENDA.getBy()).wait(seconds).check());
		
		return checks;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = NEVER)
	private void aplicarOrden() {
		click(APLICAR_ORDEN.getBy()).waitLoadPage(3).exec();
		validateAplicarOrden();
	}

	@Validation
	private ChecksTM validateAplicarOrden() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece correctamente el modal de confirmacion",
			state(VISIBLE, CONTAINER.getBy()).wait(15).check());
		
		checks.add(
			"Aparece correctamente el boton de <b>aplicar general</b>",
			state(VISIBLE, APPLY_GENERIC.getBy()).wait(15).check());
		
		checks.add(
			"Aparece correctamente el boton de <b>aplicar pais</b>",
			state(VISIBLE, APPLY_COUNTRY.getBy()).wait(15).check());
		
		checks.add(
			"Aparece correctamente el boton de <b>cancelar</b>",
			state(VISIBLE, CANCEL.getBy()).wait(15).check());
		
		return checks;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = NEVER)
	private void aceptarOrdenPais() {
		click(APPLY_COUNTRY.getBy()).waitLoadPage(3).exec();
		validateBajarPrenda(10);
	}
}
