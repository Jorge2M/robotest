package com.mng.robotest.test80.mango.test.stpv.manto;

import org.openqa.selenium.WebDriver;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas.*;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecModalPersonalizacion;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageOrdenacionDePrendasStpV {

	static String refPrenda = "";

	public static void mantoOrdenacionInicio(WebDriver driver) throws Exception {
		selectPreProduccion(driver);
		selectShe(driver);
	}

	public static void mantoSeccionPrendas(WebDriver driver) throws Exception {
		selectSectionPrenda(driver);
		selectTipoPrenda(driver);
		bajarPrenda(driver);
	}

	public static void ordenacionModal(WebDriver driver) throws Exception {
		aplicarOrden(driver);
		aceptarOrdenPais(driver);
	}

    @Validation
    public static ChecksTM validateIsPage(WebDriver driver) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSecondsWait = 10;
        validations.add(
        	"Estamos en la página " + Orden.titulo.getXPath(),
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.initialTitulo, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
        	"Aparece el desplegable de tiendas",
        	PageOrdenacionDePrendas.isElementInStateUntil(Orden.desplegableTiendas, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
        	"El botón <b>Ver Tiendas</b> está en la página",
        	PageOrdenacionDePrendas.isElementInStateUntil(Orden.verTiendas, Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Seleccionamos en el desplegable la opción <b>PreProduccion</b>",
		expected="Aparecen los diferentes indicadores de secciones",
		saveErrorData = SaveWhen.Never)
	private static void selectPreProduccion(WebDriver driver) throws Exception {
		PageOrdenacionDePrendas.selectInDropDown(Orden.desplegableTiendas, "shop.org.pre.mango.com", driver);
		PageOrdenacionDePrendas.clickAndWait(Orden.verTiendas, driver);
		validatePreProductionElements(driver);
	}

    @Validation
	private static ChecksTM validatePreProductionElements(WebDriver driver) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSecondsWait = 10;
        validations.add(
        	"Está presente el enlace de <b>She</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.She, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Está presente el enlace de <b>He</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.He, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Está presente el enlace de <b>Nina</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.Nina, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Aparece correctamente el boton de <b>cancelar</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.Nino, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Está presente el enlace de <b>Violeta</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Section.Violeta, Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Seleccionamos la seccion de <b>She</b>",
		expected="Podemos seleccionar que queremos realizar",
		saveErrorData = SaveWhen.Never)
	private static void selectShe(WebDriver driver) throws Exception {
		SecModalPersonalizacion.selectElement(Section.She, driver);
		validateSectionShe(13, driver);
	}

	@Validation(
		description="1) Está presente el selector de secciones dentro de <b>She</b>",
		level=State.Warn)
	private static boolean validateSectionShe(int maxSecondsWait, WebDriver driver) {
		return (PageOrdenacionDePrendas.isElementInStateUntil(Orden.selectorOrdenacion, Visible, maxSecondsWait, driver));
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
	private static ChecksTM validateSectionPrenda(WebDriver driver) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSecondsWait = 13;
        validations.add(
            "Se vuelve visible el selector de tipo de <b>Prendas</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.selectorPrendas, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Podemos ver el <b>botón</b> para ver las prendas",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.verPrendas, Visible, maxSecondsWait, driver), State.Defect);
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
	private static ChecksTM validateTipoPrenda(WebDriver driver) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSecondsWait = 20;
        validations.add(
            "Aparecen imagenes en la nueva página (lo esperamos hasta " + maxSecondsWait + " segundos)",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.pruebaImagen, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Estamos en la sección que corresponde <b>camisas</b>",
            PageOrdenacionDePrendas.isElementInState(Orden.pruebaCamisa, Visible, driver), State.Defect);
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
    private static ChecksTM validateBajarPrenda(WebDriver driver, int maxSecondsWait) {
        ChecksTM validations = ChecksTM.getNew();
        validations.add(
            "Se sigue viendo la segunda prenda",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.segundaPrenda, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "La primera prenda no se corresponde con la que había inicialmente",
            PageOrdenacionDePrendas.isElementInStateUntil(Orden.primeraPrenda, Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private static void aplicarOrden(WebDriver driver) throws Exception {
		PageOrdenacionDePrendas.selectElement(Orden.aplicarOrden, driver);
		validateAplicarOrden(driver);
	}

    @Validation
	private static ChecksTM validateAplicarOrden(WebDriver driver) {
        ChecksTM validations = ChecksTM.getNew();
        int maxSecondsWait = 15;
        validations.add(
            "Aparece correctamente el modal de confirmacion",
            PageOrdenacionDePrendas.isElementInStateUntil(Modal.container, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Aparece correctamente el boton de <b>aplicar general</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Modal.applyGeneric, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Aparece correctamente el boton de <b>aplicar pais</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Modal.applyCountry, Visible, maxSecondsWait, driver), State.Defect);
        validations.add(
            "Aparece correctamente el boton de <b>cancelar</b>",
            PageOrdenacionDePrendas.isElementInStateUntil(Modal.cancel, Visible, maxSecondsWait, driver), State.Defect);
        return validations;
    }

	@Step(
		description="Aplicamos el nuevo orden a las prendas",
		expected="Se despliega el modal de la ordenacion de predas",
		saveErrorData = SaveWhen.Never)
	private static void aceptarOrdenPais(WebDriver driver) throws Exception {
		SecModalPersonalizacion.selectElement(Modal.applyCountry, driver);
		validateBajarPrenda(driver, 10);
	}
}
