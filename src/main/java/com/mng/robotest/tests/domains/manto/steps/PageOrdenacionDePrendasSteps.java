package com.mng.robotest.tests.domains.manto.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepMantoBase;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

import org.openqa.selenium.StaleElementReferenceException;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.manto.pageobjects.PageOrdenacionDePrendas.Modal.*;
import static com.mng.robotest.tests.domains.manto.pageobjects.PageOrdenacionDePrendas.Orden.*;
import static com.mng.robotest.tests.domains.manto.pageobjects.PageOrdenacionDePrendas.Section.*;
import static com.github.jorge2m.testmaker.conf.State.*;

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
			state(Visible, INITIAL_TITULO.getBy()).wait(10).check());
		
		checks.add(
			"Aparece el desplegable de tiendas",
			state(Visible, DESPLEGABLE_TIENDAS.getBy()).wait(10).check());
		
		checks.add(
			"El botón <b>Ver Tiendas</b> está en la página",
			state(Visible, VER_TIENDAS.getBy()).wait(10).check());
		
		return checks;
	}

	@Step(
		description="Seleccionamos la opción <b>Disponibilidad Pre</b> + Ver tiendas",
		expected="Aparecen los diferentes indicadores de secciones",
		saveErrorData = SaveWhen.Never)
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
			state(Visible, SHE.getBy()).wait(10).check());
		
		checks.add(
			"Está presente el enlace de <b>He</b>",
			state(Visible, HE.getBy()).check());
		
		checks.add(
			"Está presente el enlace de <b>Niños</b>",
			state(Visible, NINOS.getBy()).check());

		checks.add(
			"Está presente el enlace de <b>Home</b>",
			state(Visible, HOME.getBy()).check());		
		
		return checks;
	}

	@Step(
		description="Seleccionamos la seccion de <b>She</b>",
		expected="Podemos seleccionar que queremos realizar",
		saveErrorData = SaveWhen.Never)
	private void selectShe() {
		click(SHE.getBy()).waitLoadPage(3).exec();
		validateSectionShe(13);
	}

	@Validation(
		description="1) Está presente el selector de secciones dentro de <b>She</b>",
		level=Warn)
	private boolean validateSectionShe(int seconds) {
		return (state(Visible, SELECTOR_ORDENACION.getBy()).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos la sección de <b>Nuevo</b> en el desplegable + <b>Ver prendas</b>",
		expected="Aparece el botón \"Aplicar Orden\" y una lista de prendas",
		saveErrorData = SaveWhen.Never)
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
        level=Warn)
	private boolean checkButtonAplicarOrdenVisible(int seconds) {
        return state(Visible, APLICAR_ORDEN.getBy()).wait(seconds).check();
	}

	@Validation(description="Aparecen imagenes en la nueva página")
	private boolean checkImagesVisible() {
        return state(Visible, PRUEBA_IMAGEN.getBy()).check();
	}

	@Step(
		description="Enviamos la primera prenda al final",
		expected="La prenda ha cambiado",
		saveErrorData = SaveWhen.Never)
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
			state(Visible, SEGUNDA_PRENDA.getBy()).wait(seconds).check());
		
		checks.add(
			"La primera prenda no se corresponde con la que había inicialmente",
			state(Visible, PRIMERA_PRENDA.getBy()).wait(seconds).check());
		
		return checks;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private void aplicarOrden() {
		click(APLICAR_ORDEN.getBy()).waitLoadPage(3).exec();
		validateAplicarOrden();
	}

	@Validation
	private ChecksTM validateAplicarOrden() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece correctamente el modal de confirmacion",
			state(Visible, CONTAINER.getBy()).wait(15).check());
		
		checks.add(
			"Aparece correctamente el boton de <b>aplicar general</b>",
			state(Visible, APPLY_GENERIC.getBy()).wait(15).check());
		
		checks.add(
			"Aparece correctamente el boton de <b>aplicar pais</b>",
			state(Visible, APPLY_COUNTRY.getBy()).wait(15).check());
		
		checks.add(
			"Aparece correctamente el boton de <b>cancelar</b>",
			state(Visible, CANCEL.getBy()).wait(15).check());
		
		return checks;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private void aceptarOrdenPais() {
		click(APPLY_COUNTRY.getBy()).waitLoadPage(3).exec();
		validateBajarPrenda(10);
	}
}
