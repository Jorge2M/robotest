package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecModalPersonalizacion;

public class PageOrdenacionDePrendasStpV {

	static String refPrenda = "";

	public static void mantoOrdenacionInicio(DataFmwkTest dFTest) throws Exception {
		selectPreProduccion(dFTest);
		selectShe(dFTest);
	}

	public static void mantoSeccionPrendas(DataFmwkTest dFTest) throws Exception {
		selectSectionPrenda(dFTest);
		selectTipoPrenda(dFTest);
		bajarPrenda(dFTest);
	}

	public static void ordenacionModal(DataFmwkTest dFTest) throws Exception {
		aplicarOrden(dFTest);
		aceptarOrdenPais(dFTest);
	}

    @Validation
    public static ListResultValidation validateIsPage(DatosStep datosStep, WebDriver driver) {
        ListResultValidation validations = ListResultValidation.getNew(datosStep);
        int maxSecondsWait = 10;
        validations.add(
                "Estamos en la página " + Orden.titulo.getXPath() + "<br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.initialTitulo, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Aparece el desplegable de tiendas<br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.desplegableTiendas, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "El botón <b>Ver Tiendas</b> está en la página",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.verTiendas, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
			description="Seleccionamos en el desplegable la opción <b>PreProduccion</b>",
			expected="Aparecen los diferentes indicadores de secciones",
			saveErrorPage = SaveWhen.Never)
	private static void selectPreProduccion(DataFmwkTest dFTest) throws Exception {
		PageOrdenacionDePrendas.selectInDropDown(Orden.desplegableTiendas, "shop.pre.mango.com", dFTest.driver);
		PageOrdenacionDePrendas.clickAndWait(Orden.verTiendas, dFTest.driver);

		//Validaciones
		validatePreProductionElements(dFTest.driver);
	}

    @Validation
	private static ListResultValidation validatePreProductionElements(WebDriver driver) {
        ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 10;
        validations.add(
                "Está presente el enlace de <b>She</b><br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Section.She, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Está presente el enlace de <b>He</b><br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Section.He, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Está presente el enlace de <b>Nina</b><br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Section.Nina, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Aparece correctamente el boton de <b>cancelar</b><br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Section.Nino, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Está presente el enlace de <b>Violeta</b>",
                PageOrdenacionDePrendas.isElementInStateUntil(Section.Violeta, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
			description="Seleccionamos la seccion de <b>She</b>",
			expected="Podemos seleccionar que queremos realizar",
			saveErrorPage = SaveWhen.Never)
	private static void selectShe(DataFmwkTest dFTest) throws Exception {
		SecModalPersonalizacion.selectElement(Section.She, dFTest.driver);

		//Validaciones
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
			saveErrorPage = SaveWhen.Never)
	private static void selectSectionPrenda(DataFmwkTest dFTest){
		PageOrdenacionDePrendas.selectInDropDown(Orden.selectorOrdenacion, "prendas_she", dFTest.driver);

		//Validaciones
		validateSectionPrenda(dFTest.driver);
	}

    @Validation
	private static ListResultValidation validateSectionPrenda(WebDriver driver) {
        ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 13;
        validations.add(
                "Se vuelve visible el selector de tipo de <b>Prendas</b><br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.selectorPrendas, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "2) Podemos ver el <b>botón</b> para ver las prendas",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.verPrendas, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
			description="Seleccionamos <b>Camisas</b> en el desplegable de tipo de prenda y confirmamos nuestra seleccion",
			expected="Aparecen fotos en pantalla",
			saveErrorPage = SaveWhen.Never)
	private static void selectTipoPrenda(DataFmwkTest dFTest) throws Exception {
		SecModalPersonalizacion.selectInDropDown(Orden.selectorPrendas, "camisas_she", dFTest.driver);
		int secondsWaitElement = 10;
		SecModalPersonalizacion.selectElementWaitingForAvailability(Orden.verPrendas, secondsWaitElement, dFTest.driver);
		//Validaciones
		validateTipoPrenda(dFTest.driver, secondsWaitElement);
	}

	@Validation
	private static ListResultValidation validateTipoPrenda(WebDriver driver, int maxSecondsWait) {
        ListResultValidation validations = ListResultValidation.getNew();
        validations.add(
                "Aparecen imagenes en la nueva página<br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.pruebaImagen, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Estamos en la sección que corresponde <b>camisas</b>",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.pruebaCamisa, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
			description="Enviamos la primera prenda al final",
			expected="La prenda ha cambiado",
			saveErrorPage = SaveWhen.Never)
	private static void bajarPrenda(DataFmwkTest dFTest) throws Exception {
		PageOrdenacionDePrendas.clickAndWait(Orden.primeraPrenda, dFTest.driver);
		PageOrdenacionDePrendas.moveToAndSelectElement(Orden.bajarPrenda, dFTest.driver);

		//Validaciones
		validateBajarPrenda(dFTest.driver, 15);
	}

    @Validation
    private static ListResultValidation validateBajarPrenda(WebDriver driver, int maxSecondsWait) {
        ListResultValidation validations = ListResultValidation.getNew();
        validations.add(
                "Se sigue viendo la segunda prenda<br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.segundaPrenda, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "La primera prenda no se corresponde con la que había inicialmente",
                PageOrdenacionDePrendas.isElementInStateUntil(Orden.primeraPrenda, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
			description="Aplicamos el nuevo orden a las prendas",
			expected="Se despliega el modal de la ordenacion de predas",
			saveErrorPage = SaveWhen.Never)
	private static void aplicarOrden (DataFmwkTest dFTest) throws Exception {
		PageOrdenacionDePrendas.selectElement(Orden.aplicarOrden, dFTest.driver);

		//Validaciones
		validateAplicarOrden(dFTest.driver);
	}

    @Validation
	private static ListResultValidation validateAplicarOrden(WebDriver driver) {
        ListResultValidation validations = ListResultValidation.getNew();
        int maxSecondsWait = 15;
        validations.add(
                "Aparece correctamente el modal de confirmacion<br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Modal.container, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Aparece correctamente el boton de <b>aplicar general</b><br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Modal.applyGeneric, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Aparece correctamente el boton de <b>aplicar pais</b><br>",
                PageOrdenacionDePrendas.isElementInStateUntil(Modal.applyCountry, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
                "Aparece correctamente el boton de <b>cancelar</b>",
                PageOrdenacionDePrendas.isElementInStateUntil(Modal.cancel, StateElem.Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
			description="Aplicamos el nuevo orden a las prendas",
			expected="Se despliega el modal de la ordenacion de predas",
			saveErrorPage = SaveWhen.Never)
	private static void aceptarOrdenPais (DataFmwkTest dFTest) throws Exception {
		SecModalPersonalizacion.selectElement(Modal.applyCountry, dFTest.driver);

		//Validaciones
		validateBajarPrenda(dFTest.driver, 10);
	}

	private static String validationPrenda(DataFmwkTest dFTest, Orden prenda){
		WebElement Prenda = dFTest.driver.findElement(By.xpath(prenda.getXPath()));
		String refPrenda = Prenda.getAttribute("id");
		return refPrenda;
	}
}
