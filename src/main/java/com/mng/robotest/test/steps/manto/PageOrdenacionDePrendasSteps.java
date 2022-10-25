package com.mng.robotest.test.steps.manto;

import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.manto.PageOrdenacionDePrendas.*;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageOrdenacionDePrendasSteps extends PageBase {

	static String refPrenda = "";
	
	public PageOrdenacionDePrendasSteps(WebDriver driver) {
		super(driver);
	}

	public void mantoOrdenacionInicio() {
		selectPreProduccion();
		selectShe();
	}

	public void mantoSeccionPrendas() {
		selectSectionPrenda();
		selectTipoPrenda();
		bajarPrenda();
	}

	public void ordenacionModal() {
		aplicarOrden();
		aceptarOrdenPais();
	}

	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Estamos en la página " + Orden.titulo.getXPath(),
			state(Visible, Orden.initialTitulo.getBy()).wait(10).check(), State.Defect);
		checks.add(
			"Aparece el desplegable de tiendas",
			state(Visible, Orden.desplegableTiendas.getBy()).wait(10).check(), State.Defect);
		checks.add(
			"El botón <b>Ver Tiendas</b> está en la página",
			state(Visible, Orden.verTiendas.getBy()).wait(10).check(), State.Defect);
		return checks;
	}

	@Step(
		description="Seleccionamos en el desplegable la opción <b>PreProduccion</b>",
		expected="Aparecen los diferentes indicadores de secciones",
		saveErrorData = SaveWhen.Never)
	private void selectPreProduccion() {
		select(Orden.desplegableTiendas.getBy(), "shop.org.pre.mango.com").type(Value).exec();
		click(Orden.verTiendas.getBy()).exec();
		validatePreProductionElements();
	}

	@Validation
	private ChecksTM validatePreProductionElements() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Está presente el enlace de <b>She</b>",
			state(Visible, Section.She.getBy()).wait(10).check(), State.Defect);
		checks.add(
			"Está presente el enlace de <b>He</b>",
			state(Visible, Section.He.getBy()).wait(10).check(), State.Defect);
		checks.add(
			"Está presente el enlace de <b>Nina</b>",
			state(Visible, Section.Nina.getBy()).wait(10).check(), State.Defect);
		checks.add(
			"Aparece correctamente el boton de <b>cancelar</b>",
			state(Visible, Section.Nino.getBy()).wait(10).check(), State.Defect);
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
		return (state(Visible, Orden.selectorOrdenacion.getBy()).wait(seconds).check());
	}

	@Step(
		description="Seleccionamos la sección de <b>prendas</b> en el desplegable",
		expected="Nos aparece otro desplegable con los tipos de prenda",
		saveErrorData = SaveWhen.Never)
	private void selectSectionPrenda() {
		select(Orden.selectorOrdenacion.getBy(), "prendas_she").type(Value).exec();
		validateSectionPrenda();
	}

	@Validation
	private ChecksTM validateSectionPrenda() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Se vuelve visible el selector de tipo de <b>Prendas</b>",
			state(Visible, Orden.selectorPrendas.getBy()).wait(13).check(), State.Defect);
		checks.add(
			"Podemos ver el <b>botón</b> para ver las prendas",
			state(Visible, Orden.verPrendas.getBy()).wait(13).check(), State.Defect);
		return checks;
	}

	@Step(
		description="Seleccionamos <b>Camisas</b> en el desplegable de tipo de prenda y confirmamos nuestra seleccion",
		expected="Aparecen fotos en pantalla",
		saveErrorData = SaveWhen.Never)
	private void selectTipoPrenda() {
		select(Orden.selectorPrendas.getBy(), "camisas_she").type(Value).exec();
		click(Orden.verPrendas.getBy()).waitLink(2).exec();
		validateTipoPrenda();
	}


	@Validation
	private ChecksTM validateTipoPrenda() {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 20;
		checks.add(
			"Aparecen imagenes en la nueva página (lo esperamos hasta " + seconds + " segundos)",
			state(Visible, Orden.pruebaImagen.getBy()).wait(seconds).check(), State.Defect);
		checks.add(
			"Estamos en la sección que corresponde <b>camisas</b>",
			state(Visible, Orden.pruebaCamisa.getBy()).wait(seconds).check(), State.Defect);
		return checks;
	}

	@Step(
		description="Enviamos la primera prenda al final",
		expected="La prenda ha cambiado",
		saveErrorData = SaveWhen.Never)
	private void bajarPrenda() {
		click(Orden.primeraPrenda.getBy()).exec();
		moveToElement(Orden.bajarPrenda.getBy());
		click(Orden.bajarPrenda.getBy()).waitLink(3).exec();
		validateBajarPrenda(15);
	}

	@Validation
	private ChecksTM validateBajarPrenda(int seconds) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Se sigue viendo la segunda prenda",
			state(Visible, Orden.segundaPrenda.getBy()).wait(seconds).check(), State.Defect);
		checks.add(
			"La primera prenda no se corresponde con la que había inicialmente",
			state(Visible, Orden.primeraPrenda.getBy()).wait(seconds).check(), State.Defect);
		return checks;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private void aplicarOrden() {
		click(Orden.aplicarOrden.getBy()).waitLoadPage(3).exec();
		validateAplicarOrden();
	}

	@Validation
	private ChecksTM validateAplicarOrden() {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece correctamente el modal de confirmacion",
			state(Visible, Modal.container.getBy()).wait(15).check(), State.Defect);
		checks.add(
			"Aparece correctamente el boton de <b>aplicar general</b>",
			state(Visible, Modal.applyGeneric.getBy()).wait(15).check(), State.Defect);
		checks.add(
			"Aparece correctamente el boton de <b>aplicar pais</b>",
			state(Visible, Modal.applyCountry.getBy()).wait(15).check(), State.Defect);
		checks.add(
			"Aparece correctamente el boton de <b>cancelar</b>",
			state(Visible, Modal.cancel.getBy()).wait(15).check(), State.Defect);
		return checks;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private void aceptarOrdenPais() {
		click(Modal.applyCountry.getBy()).waitLoadPage(3).exec();
		validateBajarPrenda(10);
	}
}
