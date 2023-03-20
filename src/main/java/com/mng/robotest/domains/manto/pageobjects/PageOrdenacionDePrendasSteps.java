package com.mng.robotest.domains.manto.pageobjects;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepMantoBase;
import com.mng.robotest.domains.manto.pageobjects.PageOrdenacionDePrendas.*;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.StaleElementReferenceException;

public class PageOrdenacionDePrendasSteps extends StepMantoBase {

	public static final String REF_PRENDA = "";
	
	public void mantoOrdenacionInicio() {
		selectDisponibilidadPreAndClickVerTiendas();
		selectShe();
	}

	public void mantoSeccionNuevo() {
		selectSectionNuevo();
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
			"Estamos en la página " + Orden.TITULO.getXPath(),
			state(Visible, Orden.INITIAL_TITULO.getBy()).wait(10).check(), State.Defect);
		
		checks.add(
			"Aparece el desplegable de tiendas",
			state(Visible, Orden.DESPLEGABLE_TIENDAS.getBy()).wait(10).check(), State.Defect);
		
		checks.add(
			"El botón <b>Ver Tiendas</b> está en la página",
			state(Visible, Orden.VER_TIENDAS.getBy()).wait(10).check(), State.Defect);
		
		return checks;
	}

	@Step(
		description="Seleccionamos la opción <b>Disponibilidad Pre</b> + Ver tiendas",
		expected="Aparecen los diferentes indicadores de secciones",
		saveErrorData = SaveWhen.Never)
	private void selectDisponibilidadPreAndClickVerTiendas() {
		clickDesplegableTiendas();
		click(Orden.VER_TIENDAS.getBy()).exec();
		validatePreProductionElements();
	}
	
	private void clickDesplegableTiendas() {
		try {
			select(Orden.DESPLEGABLE_TIENDAS.getBy(), "availability.ws.pre.mango.com").type(Value).exec();
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
			state(Visible, Section.She.getBy()).wait(10).check(), State.Defect);
		
		checks.add(
			"Está presente el enlace de <b>He</b>",
			state(Visible, Section.He.getBy()).check(), State.Defect);
		
		checks.add(
			"Está presente el enlace de <b>Niños</b>",
			state(Visible, Section.Ninos.getBy()).check(), State.Defect);

		checks.add(
			"Está presente el enlace de <b>Home</b>",
			state(Visible, Section.Home.getBy()).check(), State.Defect);		
		
		return checks;
	}

	@Step(
		description="Seleccionamos la seccion de <b>She</b>",
		expected="Podemos seleccionar que queremos realizar",
		saveErrorData = SaveWhen.Never)
	private void selectShe() {
		click(Section.She.getBy()).waitLoadPage(3).exec();
		validateSectionShe(13);
	}

	@Validation(
		description="1) Está presente el selector de secciones dentro de <b>She</b>",
		level=State.Warn)
	private boolean validateSectionShe(int seconds) {
		return (state(Visible, Orden.SELECTOR_ORDENACION.getBy()).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos la sección de <b>Nuevo</b> en el desplegable + <b>Ver prendas</b>",
		expected="Aparece el botón \"Aplicar Orden\" y una lista de prendas",
		saveErrorData = SaveWhen.Never)
	private void selectSectionNuevo() {
		select(Orden.SELECTOR_ORDENACION.getBy(), "nuevo").type(Value).exec();
		clickVerPrendas();
		checkButtonAplicarOrdenVisible(15);
		checkImagesVisible();
	}

	
	private void clickVerPrendas() {
		try {
			click(Orden.VER_PRENDAS.getBy()).waitLink(2).exec();
		}
		catch (StaleElementReferenceException e) {
			waitMillis(1000);
			click(Orden.VER_PRENDAS.getBy()).exec();
		}
	}	
	
	@Validation(
        description="Es visible el botón \"Aplicar Orden\" (lo esperamos #{seconds} segundos)",
        level=State.Warn)
	private boolean checkButtonAplicarOrdenVisible(int seconds) {
        return state(Visible, Orden.APLICAR_ORDEN.getBy()).wait(seconds).check();
	}

	@Validation(
        description="Aparecen imagenes en la nueva página",
        level=State.Defect)
	private boolean checkImagesVisible() {
        return state(Visible, Orden.PRUEBA_IMAGEN.getBy()).check();
	}

	@Step(
		description="Enviamos la primera prenda al final",
		expected="La prenda ha cambiado",
		saveErrorData = SaveWhen.Never)
	private void bajarPrenda() {
		click(Orden.PRIMERA_PRENDA.getBy()).exec();
		moveToElement(Orden.BAJAR_PRENDA.getBy());
		click(Orden.BAJAR_PRENDA.getBy()).waitLink(3).exec();
		validateBajarPrenda(15);
	}

	@Validation
	private ChecksTM validateBajarPrenda(int seconds) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Se sigue viendo la segunda prenda",
			state(Visible, Orden.SEGUNDA_PRENDA.getBy()).wait(seconds).check(), State.Defect);
		
		checks.add(
			"La primera prenda no se corresponde con la que había inicialmente",
			state(Visible, Orden.PRIMERA_PRENDA.getBy()).wait(seconds).check(), State.Defect);
		
		return checks;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private void aplicarOrden() {
		click(Orden.APLICAR_ORDEN.getBy()).waitLoadPage(3).exec();
		validateAplicarOrden();
	}

	@Validation
	private ChecksTM validateAplicarOrden() {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece correctamente el modal de confirmacion",
			state(Visible, Modal.CONTAINER.getBy()).wait(15).check(), State.Defect);
		
		checks.add(
			"Aparece correctamente el boton de <b>aplicar general</b>",
			state(Visible, Modal.APPLY_GENERIC.getBy()).wait(15).check(), State.Defect);
		
		checks.add(
			"Aparece correctamente el boton de <b>aplicar pais</b>",
			state(Visible, Modal.APPLY_COUNTRY.getBy()).wait(15).check(), State.Defect);
		
		checks.add(
			"Aparece correctamente el boton de <b>cancelar</b>",
			state(Visible, Modal.CANCEL.getBy()).wait(15).check(), State.Defect);
		
		return checks;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private void aceptarOrdenPais() {
		click(Modal.APPLY_COUNTRY.getBy()).waitLoadPage(3).exec();
		validateBajarPrenda(10);
	}
}
