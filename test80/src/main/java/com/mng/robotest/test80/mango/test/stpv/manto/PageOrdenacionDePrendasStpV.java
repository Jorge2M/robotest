package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
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

	public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
		//Validations
		int maxSecondsWait = 3;
		String descripValidac =
				"1) Estamos en la página" + Orden.titulo.getXPath()+"<br>" +
						"2) Aparece el desplegable de tiendas<br>" +
						"3) El botón <b>Ver Tiendas</b> está en la página";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.initialTitulo, StateElem.Visible, maxSecondsWait, dFTest.driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.desplegableTiendas, StateElem.Visible, maxSecondsWait, dFTest.driver)) {
				listVals.add(2, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.verTiendas, StateElem.Visible, maxSecondsWait, dFTest.driver)) {
				listVals.add(3, State.Defect);
			}
			datosStep.setListResultValidations(listVals);
		}
		finally {
			listVals.checkAndStoreValidations(descripValidac);
		}
	}

	private static void selectPreProduccion(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep (
				"Seleccionamos en el desplegable la opción <b>PreProduccion</b>",
				"Aparecen los diferentes indicadores de secciones");
		try {
			SecModalPersonalizacion.selectInDropDown(Orden.desplegableTiendas, "shop.pre.mango.com", dFTest.driver);
			SecModalPersonalizacion.clickAndWait(Orden.verTiendas, dFTest.driver);
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		//Validations
		datosStep.setGrab_ErrorPageIfProblem(false);
		validatePreProductionElements(datosStep, dFTest.driver);
	}

	public static void validatePreProductionElements(DatosStep datosStep, WebDriver driver) {
		int maxSecondsWait = 3;
		String descripValidac =
				"1) Está presente el enlace de <b>She</b><br>" +
						"2) Está presente el enlace de <b>He</b><br>" +
						"3) Está presente el enlace de <b>Nina</b><br>" +
						"4) Está presente el enlace de <b>Nino</b><br>" +
						"5) Está presente el enlace de <b>Violeta</b>";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Section.She, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Section.He, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(2, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Section.Nina, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(3, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Section.Nino, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(4, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Section.Violeta, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(5, State.Defect);
			}
			datosStep.setListResultValidations(listVals);
		}
		finally {
			listVals.checkAndStoreValidations(descripValidac);
		}
	}

	private static void selectShe(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep (
				"Seleccionamos la seccion de <b>She</b>",
				"Podemos seleccionar que queremos realizar");
		try {
			SecModalPersonalizacion.selectElement(Section.She, dFTest.driver);
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		//Validations
		datosStep.setGrab_ErrorPageIfProblem(false);
		validateSectionShe(datosStep, 3, "1) Está presente el selector de secciones dentro de <b>She</b>", dFTest.driver);
	}

	@Validation(
			description="1) Está presente el selector de secciones dentro de <b>She</b>",
			level=State.Warn)
	public static boolean validateSectionShe(DatosStep datosStep, int maxSecondsWait, String validacion, WebDriver driver) {
		return (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.selectorOrdenacion, StateElem.Visible, maxSecondsWait, driver));
	}

	private static void selectSectionPrenda(DataFmwkTest dFTest){
		DatosStep datosStep = new DatosStep (
				"Seleccionamos la sección de <b>prendas</b> en el desplegable",
				"Nos aparece otro desplegable con los tipos de prenda");
		try {
			PageOrdenacionDePrendas.selectInDropDown(Orden.selectorOrdenacion, "prendas_she", dFTest.driver);
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		//Validaciones
		datosStep.setGrab_ErrorPageIfProblem(false);
		validateSectionPrenda(datosStep, dFTest.driver);
	}

	public static void validateSectionPrenda(DatosStep datosStep, WebDriver driver) {
		//Validations
		int maxSecondsWait = 3;
		String descripValidac =
				"1) Se vuelve visible el selector de tipo de <b>Prendas</b><br>" +
						"2) Podemos ver el <b>botón</b> para ver las prendas";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.selectorPrendas, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.verPrendas, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(2, State.Defect);
			}
			datosStep.setListResultValidations(listVals);
		}
		finally {
			listVals.checkAndStoreValidations(descripValidac);
		}
	}

	private static void selectTipoPrenda(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep (
				"Seleccionamos <b>Camisas</b> en el desplegable de tipo de prenda y confirmamos nuestra seleccion", "Aparecen fotos en pantalla");
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		try {
			SecModalPersonalizacion.selectInDropDown(Orden.selectorPrendas, "camisas_she", dFTest.driver);
			int secondsWaitElement = 2;
			SecModalPersonalizacion.selectElementWaitingForAvailability(Orden.verPrendas, secondsWaitElement, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		//Validations
		int maxSecondsWait = 10;
		datosStep.setGrab_ErrorPageIfProblem(false);
		validateTipoPrenda(datosStep, dFTest.driver, maxSecondsWait);
	}

	public static void validateTipoPrenda(DatosStep datosStep, WebDriver driver, int maxSecondsWait) {
		//Validations
		String descripValidac =
				"1) Aparecen imagenes en la nueva página<br>" +
						"2) Estamos en la sección que corresponde <b>camisas</b>";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.pruebaImagen, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.pruebaCamisa, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(2, State.Defect);
			}
			datosStep.setListResultValidations(listVals);
		}
		finally {
			listVals.checkAndStoreValidations(descripValidac);
		}
	}

	private static void bajarPrenda(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep (
				"Enviamos la primera prenda al final", "La prenda ha cambiado");
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		refPrenda = validationPrenda(dFTest, Orden.primeraPrenda);

		try {
			SecModalPersonalizacion.clickAndWait(Orden.primeraPrenda, dFTest.driver);
			SecModalPersonalizacion.moveToAndSelectElement(Orden.bajarPrenda, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		//Validations
		datosStep.setGrab_ErrorPageIfProblem(false);
		validateBajarPrenda(datosStep, dFTest.driver, 15);
	}

	public static void validateBajarPrenda(DatosStep datosStep, WebDriver driver, int maxSecondsWait) {
		//Validations
		String descripValidac =
				"1) Se sigue viendo la segunda prenda<br>" +
						"2) La primera prenda no se corresponde con la que había inicialmente";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.segundaPrenda, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Orden.primeraPrenda, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(2, State.Defect);
			}
			datosStep.setListResultValidations(listVals);
		}
		finally {
			listVals.checkAndStoreValidations(descripValidac);
		}
	}

	private static void aplicarOrden(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep (
				"Aplicamos el nuevo orden a las prendas", "Se despliega el modal de la ordenacion de predas");
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);

		try {
			SecModalPersonalizacion.selectElement(Orden.aplicarOrden, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		//Validations
		datosStep.setGrab_ErrorPageIfProblem(false);
		validateAplicarOrden(datosStep, dFTest.driver);
	}

	public static void validateAplicarOrden(DatosStep datosStep, WebDriver driver) {
		//Validations
		int maxSecondsWait = 10;
		String descripValidac =
				"1) Aparece correctamente el modal de confirmacion<br>" +
						"2) Aparece correctamente el boton de <b>aplicar general</b><br>" +
						"3) Aparece correctamente el boton de <b>aplicar pais</b><br>" +
						"4) Aparece correctamente el boton de <b>cancelar</b>";
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Modal.container, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(1, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Modal.applyGeneric, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(2, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Modal.applyCountry, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(3, State.Defect);
			}
			if (!PageOrdenacionDePrendas.isElementInStateUntil(Modal.cancel, StateElem.Visible, maxSecondsWait, driver)) {
				listVals.add(4, State.Defect);
			}
			datosStep.setListResultValidations(listVals);
		}
		finally {
			listVals.checkAndStoreValidations(descripValidac);
		}
	}

	private static void aceptarOrdenPais(DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep (
				"Aplicamos el orden por pais", "La prenda desaparece");
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);

		try {
			SecModalPersonalizacion.selectElement(Modal.applyCountry, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		//Validations
		datosStep.setGrab_ErrorPageIfProblem(false);
		validateBajarPrenda(datosStep, dFTest.driver, 10);
	}

	private static String validationPrenda(DataFmwkTest dFTest, Orden prenda){
		WebElement Prenda = dFTest.driver.findElement(By.xpath(prenda.getXPath()));
		String refPrenda = Prenda.getAttribute("id");
		return refPrenda;
	}
}
