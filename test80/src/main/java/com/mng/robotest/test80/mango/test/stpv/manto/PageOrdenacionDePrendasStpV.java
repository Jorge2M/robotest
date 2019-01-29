package com.mng.robotest.test80.mango.test.stpv.manto;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.ElementPage;
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
	
	public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) {
		//Validations 
		mantoOrdenacionValidation(datosStep, dFTest, 
			1, "Estamos en la página \"" + Orden.titulo.getXPath(), Orden.initialTitulo);
		mantoOrdenacionValidation(datosStep, dFTest, 
			2, "Aparece el desplegable de tiendas", Orden.desplegableTiendas);
		mantoOrdenacionValidation(datosStep, dFTest, 
			3, "El botón <b>Ver Tiendas</b> está en la página", Orden.verTiendas);
	}
	
	private static void selectPreProduccion(DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep (
			"Seleccionamos en el desplegable la opción <b>PreProduccion</b>", 
			"Aparecen los diferentes indicadores de secciones"); 
		try {
			SecModalPersonalizacion.selectInDropDown(Orden.desplegableTiendas, "shop.pre.mango.com", dFTest.driver);
			SecModalPersonalizacion.clickAndWait(Orden.verTiendas, dFTest.driver);
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validations
		mantoOrdenacionValidation(datosStep, dFTest, 
			1, "Está presente el enlace de <b>She</b>", Section.She);
		mantoOrdenacionValidation(datosStep, dFTest, 
			2, "Está presente el enlace de <b>He</b>", Section.He);
		mantoOrdenacionValidation(datosStep, dFTest, 
			3, "Está presente el enlace de <b>Nina</b>", Section.Nina);
		mantoOrdenacionValidation(datosStep, dFTest, 
			4, "Está presente el enlace de <b>Nino</b>", Section.Nino);
		mantoOrdenacionValidation(datosStep, dFTest, 
			5, "Está presente el enlace de <b>Violeta</b>", Section.Violeta);
	}
	
	private static void selectShe(DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep (
			"Seleccionamos la seccion de <b>She</b>", 
			"Podemos seleccionar que queremos realizar"); 
		try {
			SecModalPersonalizacion.selectElement(Section.She, dFTest.driver);
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validations
		mantoOrdenacionValidation(datosStep, dFTest, 
			1, "Está presente el selector de secciones dentro de <b>She</b>", Orden.selectorOrdenacion);
	}
	
	private static void selectSectionPrenda(DataFmwkTest dFTest){
		datosStep datosStep = new datosStep (
			"Seleccionamos la sección de <b>prendas</b> en el desplegable", 
			"Nos aparece otro desplegable con los tipos de prenda");
		try {
			SecModalPersonalizacion.selectInDropDown(Orden.selectorOrdenacion, "prendas_she", dFTest.driver);
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validaciones 
		mantoOrdenacionValidation(datosStep, dFTest, 
			1, "Se vuelve visible el selector de tipo de <b>Prendas</b>", Orden.selectorPrendas);
		mantoOrdenacionValidation(datosStep, dFTest,
			2, "Podemos ver el <b>botón</b> para ver las prendas", Orden.verPrendas);		
	}
	
	private static void selectTipoPrenda(DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep (
			"Seleccionamos <b>Camisas</b> en el desplegable de tipo de prenda y confirmamos nuestra seleccion", "Aparecen fotos en pantalla");
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		try {
			SecModalPersonalizacion.selectInDropDown(Orden.selectorPrendas, "camisas_she", dFTest.driver);
			int secondsWaitElement = 2;
			SecModalPersonalizacion.selectElementWaitingForAvailability(Orden.verPrendas, secondsWaitElement, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validations
		int maxSecondsWait = 10;
		mantoOrdenacionValidation(datosStep, dFTest, 
			1, "Aparecen imagenes en la nueva página",	Orden.pruebaImagen, maxSecondsWait);
		mantoOrdenacionValidation(datosStep, dFTest, 
			2, "Estamos en la sección que corresponde <b>camisas</b>", Orden.pruebaCamisa);
	}
	
	private static void bajarPrenda(DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep (
			"Enviamos la primera prenda al final", "La prenda ha cambiado");
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		refPrenda = validationPrenda(dFTest, Orden.primeraPrenda);
		
		try {
			SecModalPersonalizacion.clickAndWait(Orden.primeraPrenda, dFTest.driver);
			SecModalPersonalizacion.moveToAndSelectElement(Orden.bajarPrenda, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validations
		mantoOrdenacionValidation(datosStep, dFTest, 
			1, "Se sigue viendo la segunda prenda", Orden.segundaPrenda);
		validateOrdenacionFunction(datosStep, dFTest, 
			2, "La primera prenda no se corresponde con la que había inicialmente", Orden.primeraPrenda);
	}
	
	private static void aplicarOrden(DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep (
			"Aplicamos el nuevo orden a las prendas", "Se despliega el modal de la ordenacion de predas");
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		
		try {
			SecModalPersonalizacion.selectElement(Orden.aplicarOrden, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validations
		mantoOrdenacionValidation(datosStep, dFTest, 
			1, "Aparece correctamente el modal de confirmacion", Modal.container);
		mantoOrdenacionValidation(datosStep, dFTest, 
			2, "Aparece correctamente el boton de <b>aplicar general</b>", Modal.applyGeneric);
		mantoOrdenacionValidation(datosStep, dFTest, 
			3, "Aparece correctamente el boton de <b>aplicar pais</b>", Modal.applyCountry);
		mantoOrdenacionValidation(datosStep, dFTest, 
			4, "Aparece correctamente el boton de <b>cancelar</b>", Modal.cancel);
	}
	
	private static void aceptarOrdenPais(DataFmwkTest dFTest) throws Exception {
		datosStep datosStep = new datosStep (
			"Aplicamos el orden por pais", "La prenda desaparece");
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		
		try {
			SecModalPersonalizacion.selectElement(Modal.applyCountry, dFTest.driver);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validations
		mantoOrdenacionValidation(datosStep,dFTest, 
			1, "La segunda imagen sigue siendo visible", Orden.segundaPrenda);
		validateOrdenacionFunction(datosStep, dFTest, 
			2, "La primera prenda no se corresponde con la que había inicialmente", Orden.primeraPrenda);
	}
	
	private static String validationPrenda(DataFmwkTest dFTest, Orden prenda){
		WebElement Prenda = dFTest.driver.findElement(By.xpath(prenda.getXPath()));
		String refPrenda = Prenda.getAttribute("id");
		return refPrenda;
	}
	
	private static void validateOrdenacionFunction(datosStep datosStep, DataFmwkTest dFTest, 
												   int numValidac, String descripValidac, Orden prenda){
		String descripValidacion = numValidac + ") " + descripValidac;
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			
			if (refPrenda == validationPrenda(dFTest, prenda))
				fmwkTest.addValidation(numValidac, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} finally { fmwkTest.grabStepValidation(datosStep, descripValidacion, dFTest); }
	}
	
	private static void mantoOrdenacionValidation(datosStep datosStep, DataFmwkTest dFTest, int numValidac, String descripValidac,
												  ElementPage element) {
		mantoOrdenacionValidation(datosStep, dFTest, numValidac, descripValidac, element, 0);
	}
	
	private static void mantoOrdenacionValidation(datosStep datosStep, DataFmwkTest dFTest, int numValidac, String descripValidac,
												  ElementPage element, int maxSecondsWait) {
		String descripValidacion = numValidac + ") " + descripValidac;
		if (maxSecondsWait > 0)
			descripValidacion+=" (esperamos hasta un máximo de " + maxSecondsWait + " segundos)";
			
		datosStep.setGrab_ErrorPageIfProblem(false);
		datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
		try {
			List<SimpleValidation> listVals = new ArrayList<>();
			//1)
			if (!SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Present, maxSecondsWait, dFTest.driver))
				fmwkTest.addValidation(numValidac, State.Defect, listVals);

			datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
		} finally { fmwkTest.grabStepValidation(datosStep, descripValidacion, dFTest); }
	}
}
