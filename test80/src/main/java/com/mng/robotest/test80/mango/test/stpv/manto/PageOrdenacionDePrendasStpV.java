package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas.*;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageOrdenacionDePrendasStpV extends PageObjTM {

	static String refPrenda = "";
	
	public PageOrdenacionDePrendasStpV(WebDriver driver) {
		super(driver);
	}

	public void mantoOrdenacionInicio() throws Exception {
		selectPreProduccion();
		selectShe();
	}

	public void mantoSeccionPrendas() throws Exception {
		selectSectionPrenda();
		selectTipoPrenda();
		bajarPrenda();
	}

	public void ordenacionModal() throws Exception {
		aplicarOrden();
		aceptarOrdenPais();
	}

    @Validation
    public ChecksTM validateIsPage() {
        ChecksTM validations = ChecksTM.getNew();
        validations.add(
        	"Estamos en la página " + Orden.titulo.getXPath(),
        	state(Visible, Orden.initialTitulo.getBy()).wait(10).check(), State.Defect);
        validations.add(
        	"Aparece el desplegable de tiendas",
        	state(Visible, Orden.desplegableTiendas.getBy()).wait(10).check(), State.Defect);
        validations.add(
        	"El botón <b>Ver Tiendas</b> está en la página",
        	state(Visible, Orden.verTiendas.getBy()).wait(10).check(), State.Defect);
        return validations;
    }

	@Step(
		description="Seleccionamos en el desplegable la opción <b>PreProduccion</b>",
		expected="Aparecen los diferentes indicadores de secciones",
		saveErrorData = SaveWhen.Never)
	private void selectPreProduccion() throws Exception {
		select(Orden.desplegableTiendas.getBy(), "shop.org.pre.mango.com").type(Value).exec();
		click(Orden.verTiendas.getBy()).exec();
		validatePreProductionElements();
	}

	@Validation
	private ChecksTM validatePreProductionElements() {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Está presente el enlace de <b>She</b>",
			state(Visible, Section.She.getBy()).wait(10).check(), State.Defect);
		validations.add(
			"Está presente el enlace de <b>He</b>",
			state(Visible, Section.He.getBy()).wait(10).check(), State.Defect);
		validations.add(
			"Está presente el enlace de <b>Nina</b>",
			state(Visible, Section.Nina.getBy()).wait(10).check(), State.Defect);
		validations.add(
			"Aparece correctamente el boton de <b>cancelar</b>",
			state(Visible, Section.Nino.getBy()).wait(10).check(), State.Defect);
		return validations;
	}

	@Step(
		description="Seleccionamos la seccion de <b>She</b>",
		expected="Podemos seleccionar que queremos realizar",
		saveErrorData = SaveWhen.Never)
	private void selectShe() throws Exception {
		click(Section.She.getBy()).waitLoadPage(3).exec();
		validateSectionShe(13);
	}

	@Validation(
		description="1) Está presente el selector de secciones dentro de <b>She</b>",
		level=State.Warn)
	private boolean validateSectionShe(int maxSeconds) {
		return (state(Visible, Orden.selectorOrdenacion.getBy()).wait(maxSeconds).check());
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
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Se vuelve visible el selector de tipo de <b>Prendas</b>",
			state(Visible, Orden.selectorPrendas.getBy()).wait(13).check(), State.Defect);
		validations.add(
			"Podemos ver el <b>botón</b> para ver las prendas",
			state(Visible, Orden.verPrendas.getBy()).wait(13).check(), State.Defect);
		return validations;
	}

	@Step(
		description="Seleccionamos <b>Camisas</b> en el desplegable de tipo de prenda y confirmamos nuestra seleccion",
		expected="Aparecen fotos en pantalla",
		saveErrorData = SaveWhen.Never)
	private void selectTipoPrenda() throws Exception {
		select(Orden.selectorPrendas.getBy(), "camisas_she").type(Value).exec();
//      Thread.sleep(100);
		click(Orden.verPrendas.getBy()).waitLink(2).exec();
		validateTipoPrenda();
	}

	@Validation
	private ChecksTM validateTipoPrenda() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 20;
		validations.add(
			"Aparecen imagenes en la nueva página (lo esperamos hasta " + maxSeconds + " segundos)",
			state(Visible, Orden.pruebaImagen.getBy()).wait(maxSeconds).check(), State.Defect);
		validations.add(
			"Estamos en la sección que corresponde <b>camisas</b>",
			state(Visible, Orden.pruebaCamisa.getBy()).wait(maxSeconds).check(), State.Defect);
		return validations;
	}

	@Step(
		description="Enviamos la primera prenda al final",
		expected="La prenda ha cambiado",
		saveErrorData = SaveWhen.Never)
	private void bajarPrenda() throws Exception {
		click(Orden.primeraPrenda.getBy()).exec();
		moveToElement(Orden.bajarPrenda.getBy(), driver);
		click(Orden.bajarPrenda.getBy()).waitLink(3).exec();
		validateBajarPrenda(15);
	}

	@Validation
	private ChecksTM validateBajarPrenda(int maxSeconds) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Se sigue viendo la segunda prenda",
			state(Visible, Orden.segundaPrenda.getBy()).wait(maxSeconds).check(), State.Defect);
		validations.add(
			"La primera prenda no se corresponde con la que había inicialmente",
			state(Visible, Orden.primeraPrenda.getBy()).wait(maxSeconds).check(), State.Defect);
		return validations;
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
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece correctamente el modal de confirmacion",
			state(Visible, Modal.container.getBy()).wait(15).check(), State.Defect);
		validations.add(
			"Aparece correctamente el boton de <b>aplicar general</b>",
			state(Visible, Modal.applyGeneric.getBy()).wait(15).check(), State.Defect);
		validations.add(
			"Aparece correctamente el boton de <b>aplicar pais</b>",
			state(Visible, Modal.applyCountry.getBy()).wait(15).check(), State.Defect);
		validations.add(
			"Aparece correctamente el boton de <b>cancelar</b>",
			state(Visible, Modal.cancel.getBy()).wait(15).check(), State.Defect);
		return validations;
	}

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private void aceptarOrdenPais() throws Exception {
		click(Modal.applyCountry.getBy()).waitLoadPage(3).exec();
		validateBajarPrenda(10);
	}
}
