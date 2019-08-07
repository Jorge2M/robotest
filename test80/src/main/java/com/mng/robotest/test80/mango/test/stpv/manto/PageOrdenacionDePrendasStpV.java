package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.arq.webdriverwrapper.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecModalPersonalizacion;

public class PageOrdenacionDePrendasStpV {

	static String refPrenda = "";

	public static void mantoOrdenacionInicio(DataFmwkTest dFTest) throws Exception {
		selectPreProduccion(dFTest);
		selectShe(dFTest);
	}

	public static void mantoSeccionPrendas(WebDriver driver) throws Exception {
		selectSectionPrenda(driver);
		selectTipoPrenda(driver);
		bajarPrenda(driver);
	}

	public static void ordenacionModal(DataFmwkTest dFTest) throws Exception {
		aplicarOrden(dFTest);
		aceptarOrdenPais(dFTest);
	}

    @Validation
    public static ChecksResult validateIsPage(WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 10;
        validations.add(
        	"Estamos en la página " + Orden.titulo.getXPath(),
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.initialTitulo, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
        	"Aparece el desplegable de tiendas",
        	PageOrdenacionDePrendas.isElementInStateUntil(Orden.desplegableTiendas, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
        	"El botón <b>Ver Tiendas</b> está en la página",
        	PageOrdenacionDePrendas.isElementInStateUntil(Orden.verTiendas, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Seleccionamos en el desplegable la opción <b>PreProduccion</b>",
		expected="Aparecen los diferentes indicadores de secciones",
		saveErrorData = SaveWhen.Never)
	private static void selectPreProduccion(DataFmwkTest dFTest) throws Exception {
		PageOrdenacionDePrendas.selectInDropDown(Orden.desplegableTiendas, "shop.org.pre.mango.com", dFTest.driver);
		PageOrdenacionDePrendas.clickAndWait(Orden.verTiendas, dFTest.driver);
		validatePreProductionElements(dFTest.driver);
	}

    @Validation
	private static ChecksResult validatePreProductionElements(WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 10;
        validations.add(
        	"Está presente el enlace de <b>She</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.She, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Está presente el enlace de <b>He</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.He, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Está presente el enlace de <b>Nina</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.Nina, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Aparece correctamente el boton de <b>cancelar</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.Nino, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Está presente el enlace de <b>Violeta</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.Violeta, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Seleccionamos la seccion de <b>She</b>",
		expected="Podemos seleccionar que queremos realizar",
		saveErrorData = SaveWhen.Never)
	private static void selectShe(DataFmwkTest dFTest) throws Exception {
		SecModalPersonalizacion.selectElement(Section.She, dFTest.driver);
		validateSectionShe(13, dFTest.driver);
	}

	@Validation(
		description="1) Está presente el selector de secciones dentro de <b>She</b>",
		level=State.Warn)
	private static boolean validateSectionShe(int maxSecondsWait, WebDriver driver) {
		return (PageOrdenacionDePrendas.isElementInStateUntil(Orden.selectorOrdenacion, StateElem.Visible, maxSecondsWait, driver));
	}

	@Step(
		description="Seleccionamos la sección de <b>prendas</b> en el desplegable",
		expected="Nos aparece otro desplegable con los tipos de prenda",
		saveErrorData = SaveWhen.Never)
	private static void selectSectionPrenda(WebDriver driver){
		PageOrdenacionDePrendas.selectInDropDown(Orden.selectorOrdenacion, "prendas_she", driver);
		validateSectionPrenda(driver);
	}

    @Validation
	private static ChecksResult validateSectionPrenda(WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 13;
        validations.add(
            "Se vuelve visible el selector de tipo de <b>Prendas</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.selectorPrendas, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Podemos ver el <b>botón</b> para ver las prendas",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.verPrendas, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Seleccionamos <b>Camisas</b> en el desplegable de tipo de prenda y confirmamos nuestra seleccion",
		expected="Aparecen fotos en pantalla",
		saveErrorData = SaveWhen.Never)
	private static void selectTipoPrenda(WebDriver driver) throws Exception {
		SecModalPersonalizacion.selectInDropDown(Orden.selectorPrendas, "camisas_she", driver);
		int secondsWaitElement = 2;
		SecModalPersonalizacion.selectElementWaitingForAvailability(Orden.verPrendas, secondsWaitElement, driver);
		validateTipoPrenda(driver);
	}

	@Validation
	private static ChecksResult validateTipoPrenda(WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 20;
        validations.add(
            "Aparecen imagenes en la nueva página (lo esperamos hasta " + maxSecondsWait + " segundos)",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.pruebaImagen, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Estamos en la sección que corresponde <b>camisas</b>",
            PageOrdenacionDePrendas.isElementInState(Orden.pruebaCamisa, StateElem.Visible, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Enviamos la primera prenda al final",
		expected="La prenda ha cambiado",
		saveErrorData = SaveWhen.Never)
	private static void bajarPrenda(WebDriver driver) throws Exception {
		PageOrdenacionDePrendas.clickAndWait(Orden.primeraPrenda, driver);
		PageOrdenacionDePrendas.moveToAndSelectElement(Orden.bajarPrenda, driver);
		validateBajarPrenda(driver, 15);
	}

    @Validation
    private static ChecksResult validateBajarPrenda(WebDriver driver, int maxSecondsWait) {
        ChecksResult validations = ChecksResult.getNew();
        validations.add(
            "Se sigue viendo la segunda prenda",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.segundaPrenda, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "La primera prenda no se corresponde con la que había inicialmente",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.primeraPrenda, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private static void aplicarOrden (DataFmwkTest dFTest) throws Exception {
		PageOrdenacionDePrendas.selectElement(Orden.aplicarOrden, dFTest.driver);
		validateAplicarOrden(dFTest.driver);
	}

    @Validation
	private static ChecksResult validateAplicarOrden(WebDriver driver) {
        ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 15;
        validations.add(
            "Aparece correctamente el modal de confirmacion",
            PageOrdenacionDePrendas.isElementInStateUntil(Modal.container, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Aparece correctamente el boton de <b>aplicar general</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Modal.applyGeneric, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Aparece correctamente el boton de <b>aplicar pais</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Modal.applyCountry, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Aparece correctamente el boton de <b>cancelar</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Modal.cancel, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private static void aceptarOrdenPais (DataFmwkTest dFTest) throws Exception {
		SecModalPersonalizacion.selectElement(Modal.applyCountry, dFTest.driver);
		validateBajarPrenda(dFTest.driver, 10);
	}
}
