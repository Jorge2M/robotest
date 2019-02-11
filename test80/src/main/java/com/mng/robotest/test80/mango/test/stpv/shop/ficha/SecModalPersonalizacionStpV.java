package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.By;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecModalPersonalizacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecModalPersonalizacion.ModalElement;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterCollection;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

public class SecModalPersonalizacionStpV {
	
    DataFmwkTest dFTest;
    Channel channel;
    AppEcom app;
    PageFicha pageFichaWrap;
	
    public SecModalPersonalizacionStpV(AppEcom appE, Channel channel, DataFmwkTest dFTest) {
        this.dFTest = dFTest;
        this.channel = channel;
        this.app = appE;
        this.pageFichaWrap = PageFicha.newInstance(appE, channel, dFTest.driver);
    }
	
    //Funciones agrupadas por pasos lógicos
    
    public void searchForCustomization(Channel channel, DataFmwkTest dFTest, DataCtxShop dCtxSh) throws Exception {
    	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, dCtxSh.appE, dFTest);

    	boolean customizable = false;
    	int maxArticlesToReview = 8;
    	DatosStep datosStep;
    	int i = 1;
    	String galeriaToSelect = "camisas";
    	do {
            Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.he, null, galeriaToSelect));
            SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest);
            SecMenusWrapperStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason, dCtxSh.channel, dCtxSh.appE, dFTest);
            LocationArticle articleNum = LocationArticle.getInstanceInCatalog(i);
            datosStep =  pageGaleriaStpV.selectArticulo(articleNum, dCtxSh).datosStep;
            if (SecModalPersonalizacion.isElementInStateUntil(ModalElement.BotonIniciar, StateElem.Present, 1, channel, dFTest.driver))
            	customizable = true;
            i = i + 1;
    	} while (!customizable && i<(maxArticlesToReview + 1));
    	
	    //Validaciones
        String descripValidac = 
            "1) Alguno de los " + maxArticlesToReview + " primeros artículos de la galería " + galeriaToSelect + " es personalizable";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
    		if (!customizable) {
    			listVals.add(1, State.Defect);
    		}
    		
            datosStep.setListResultValidations(listVals);
        } finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public void startCustomizationProcces(Channel channel, DataFmwkTest dFTest) throws Exception {
    	selectCustomization(channel, dFTest);
    	startCustomization(channel, dFTest);
    }
    
    public void customizationProcces(Channel channel, DataFmwkTest dFTest) throws Exception {
    	selectIconCustomization(channel, dFTest);
    	selectFirstIcon(channel, dFTest);
    	selectWhere(channel, dFTest);
    	selectColor(channel, dFTest);
    	selectSize(channel, dFTest);
    }
    
    public void endAndCheckCustomization(Channel channel, DataFmwkTest dFTest) throws Exception {
    	confirmCustomization(channel, dFTest);
    	checkCustomizationProof(channel, dFTest);
    }
    
    //Funciones donde comprobaremos los diferentes pasos de la personalizacion
    
	private void selectCustomization(Channel channel, DataFmwkTest dFTest) throws Exception {
		//Step
		DatosStep datosStep = new DatosStep       (
			"Seleccionamos el link <b>Añadir personalización</b>", 
	        "Aparece el modal para la personalización de la prenda");
		try {
			if(channel == Channel.movil_web) 
				WebdrvWrapp.moveToElement(By.xpath(ModalElement.BotonIniciar.getXPath(channel)), dFTest.driver);
			SecModalPersonalizacion.selectElement(ModalElement.BotonIniciar, channel, dFTest.driver);
	        	
	        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
	        
	    //Validaciones
		customizationValidation(channel, ModalElement.Modal, State.Warn, datosStep, dFTest,
			"1) Aparece el modal de personalización de la prenda");
        
	}

    private void startCustomization(Channel channel, DataFmwkTest dFTest) throws Exception {
    	//Step
    	DatosStep datosStep = new DatosStep		  (
    		"Seleccionamos el botón <b>Empezar</b>", 
    		"Aparecen las primeras opciones de personalizacion del artículo");
    	try {
    		//Seleccionamos el boton de continuar para después hacer las diversas validaciones
			SecModalPersonalizacion.selectElement(ModalElement.StartProcces, channel, dFTest.driver);
    		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
	    } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
	        
	    //Validaciones
    	if (channel == Channel.desktop) { 
    		validateIsApartadoVisible(1, datosStep, dFTest, channel);
    	} else {
    		customizationValidation(channel, ModalElement.HeaderProof, State.Warn, datosStep, dFTest,
    			"1) Aparece la cabecera correspondiente a la personalizacion de la prenda");
    	}
    }
    	
	private void selectIconCustomization(Channel channel, DataFmwkTest dFTest) throws Exception {
		//Step
		DatosStep datosStep = new DatosStep			(
			"Seleccionamos la opción <b>Un icono</b>",
			"Aparece la lista de iconos");
		try {
			SecModalPersonalizacion.selectElement(ModalElement.RadioIcon, channel, dFTest.driver);
						
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		if (channel == Channel.desktop) {
			validateIsApartadoVisible(1,  datosStep, dFTest, channel);
			customizationValidation(channel, ModalElement.Icons, State.Warn, datosStep, dFTest,
				"1) Aparece la lista de iconos seleccionables");
				
		} else {			
			customizationValidation(channel, ModalElement.HeaderProof, State.Warn, datosStep, dFTest,
				"1) La cabecera ahora ha cambiado");
			customizationValidation(channel, ModalElement.BackProof, State.Warn, datosStep, dFTest,
				"2) Aparecen los iconos por seleccionar");

		}
	}
	
	private void selectFirstIcon(Channel channel, DataFmwkTest dFTest) throws Exception {
		//Step
		DatosStep datosStep = new DatosStep 		 (
			"Seleccionamos el primer icono",
			"Aparece el botón Confirmar");
		try {
			SecModalPersonalizacion.selectElement(ModalElement.IconSelecction, channel, dFTest.driver, WebdrvWrapp.TypeOfClick.javascript);

			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} 
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validaciones		
		if (channel == Channel.desktop) {
			doubleCustomizationValidations(channel, dFTest, ModalElement.IconSelecction, ModalElement.Continue, State.Warn, datosStep,
				"1) Aparece seleccionado el primer icono<br>" + 
				"2) Podemos confirmar nuestra seleccion");
			
		} else {
			customizationValidation(channel, ModalElement.PositionButton, State.Warn, datosStep, dFTest,
				"1) Aparece seleccionado el primer icono y podemos confirmar nuestra seleccion");
		}
	}
	
	private void selectWhere(Channel channel, DataFmwkTest dFTest) throws Exception {
		//Step
		DatosStep datosStep = new DatosStep			(
			"Seleccionamos el botón \"Confirmar\"",
			"Se hace visible el paso-2");
		try {
			if (channel == Channel.desktop)
				SecModalPersonalizacion.selectElement(ModalElement.Continue, channel, dFTest.driver);
			SecModalPersonalizacion.selectElement(ModalElement.PositionButton, channel, dFTest.driver);
			
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} 
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validaciones
		if (channel == Channel.desktop) {
			validateIsApartadoVisible(2, datosStep, dFTest, channel);
			customizationValidation(channel, ModalElement.PositionButton, State.Warn, datosStep, dFTest,
				"1) Aparecen los radio-button correspondientes a la seccion");
			doubleCustomizationValidations(channel, dFTest, ModalElement.StepProof, ModalElement.Continue, State.Warn, datosStep,
				"2) Aparece seleccionado la primera opción y podemos confirmar nuestra seleccion");
			
		} else {
			customizationValidation(channel, ModalElement.HeaderProof, State.Warn, datosStep, dFTest,
				"1) Seguimos en el proceso de personalizacion");
			customizationValidation(channel, ModalElement.ColorsContainer, State.Warn, datosStep, dFTest,
				"2) En el flujo mobil, ahora aparecen los colores disponibles");
		}
	}
	
	private void selectColor(Channel channel, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep			(
			"Seleccionamos el botón \"Confirmar\"",
			"Aparece el apartado 3 de la personalización");
		try {
			SecModalPersonalizacion.selectElement(ModalElement.Continue, channel, dFTest.driver, WebdrvWrapp.TypeOfClick.javascript);
			
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} 
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validacion
		if (channel == Channel.desktop) {
			validateIsApartadoVisible(3, datosStep, dFTest, channel);
			customizationValidation(channel, ModalElement.ColorsContainer, State.Warn, datosStep, dFTest,
				"1) Aparecen los radio-button correspondientes a los colores");
			doubleCustomizationValidations(channel, dFTest, ModalElement.Step3Proof, ModalElement.Continue, State.Warn, datosStep,
				"2) Aparece el botón de \"Confirmar\"");
			
		} else {
			customizationValidation(channel, ModalElement.HeaderProof, State.Warn, datosStep, dFTest,
				"1) Cambia la cabecera pero seguimos en la personalizacion del producto");
			customizationValidation(channel, ModalElement.Continue, State.Warn, datosStep, dFTest,
				"2) Podemos continuar con nuestro proceso de personalizacion");
		}
	}

	private void selectSize(Channel channel, DataFmwkTest dFTest) throws Exception {
		DatosStep datosStep = new DatosStep			(
			"Seleccionamos el botón \"Confirmar\"",
			"Aparece el apartado 4 de la personalización");
		try {
			SecModalPersonalizacion.clickAndWait(channel, ModalElement.Continue, dFTest.driver);
			
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} 
		finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

		//Validacion
		if (channel != Channel.movil_web) {
			customizationValidation(channel, ModalElement.SizeContainer, State.Warn, datosStep, dFTest,
				"1) Aparecen los posibles tamaños");
		} else {
			customizationValidation(channel, ModalElement.addToBag, State.Warn, datosStep, dFTest,
				"1) Es visible el botón que nos permite añadir ese producto a la bolsa");
		}
    }

	private void confirmCustomization(Channel channel, DataFmwkTest dFTest) throws Exception {
		//Step
		DatosStep datosStep = new DatosStep			(
			"Seleccionamos el botón \"Añadir a la bolsa\"",
			"Aparece el modal con las opciones para ver la bolsa o seguir comprando");
		try {
			if (channel != Channel.movil_web) {
				SecModalPersonalizacion.selectElement(ModalElement.Continue, dFTest.driver);
			} else {
				SecModalPersonalizacion.selectElement(ModalElement.addToBag, channel, dFTest.driver);
			}
			datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		} finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
		
		//Validaciones
		if (channel == Channel.desktop) {
			customizationValidation(channel, ModalElement.Continue, State.Warn, datosStep, dFTest,
				"1) Aparece el botón para añadir a la bolsa");
			
		} else {
			customizationValidation(channel, ModalElement.addToBag, State.Warn, datosStep, dFTest,
				"1) Aparece el boton de <b>ir a la bolsa</b>");
		}
	}

	private void checkCustomizationProof(Channel channel, DataFmwkTest dFTest) throws Exception {
		//Step
		DatosStep datosStep = new DatosStep			(
			"Seleccionamos el botón \"Añadir a la bolsa\"",
			"El artículo se da de alta correctamente en la bolsa");
		
		if (channel == Channel.desktop) {
			SecModalPersonalizacion.selectElement(ModalElement.Continue, channel, dFTest.driver);
		} else if (channel == Channel.movil_web) {
			SecModalPersonalizacion.selectElement(ModalElement.StartProcces, channel, dFTest.driver);
			SecModalPersonalizacion.selectElement(ModalElement.GoToBag, channel, dFTest.driver);
		}	
		
		//Step	
		datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
		datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest));
		
		//Validaciones 
		int maxSeconds = 2;
		customizationValidation(channel, ModalElement.BolsaProof, maxSeconds, State.Defect, datosStep, dFTest,
			"1) En la bolsa aparece el apartado correspondiente a la personalización (lo esperamos hasta " + maxSeconds + " segundos)");
	}

	private void customizationValidation(Channel channel, ModalElement element,
		State state, DatosStep datosStep, DataFmwkTest dFTest, String descripValidac) {
		int maxSecondsToWait = 1;
		customizationValidation(channel, element, maxSecondsToWait, state, datosStep, dFTest, descripValidac);
	}

	private void customizationValidation(Channel channel, ModalElement element, int maxSecondsToWait,
										State state, DatosStep datosStep, DataFmwkTest dFTest, String descripValidac) {
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Present, maxSecondsToWait, channel, dFTest.driver)) {
				listVals.add(1, state);
			}
			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}

	private void doubleCustomizationValidations(Channel channel, DataFmwkTest dFTest,
										ModalElement firstElement, ModalElement secondElement, 
										State state, DatosStep datosStep, String descripValidac) {
		datosStep.setNOKstateByDefault();
		ListResultValidation listVals = ListResultValidation.getNew(datosStep);
		try {
			if (!SecModalPersonalizacion.isElementInStateUntil(firstElement, StateElem.Present, 1, channel, dFTest.driver) &&
				!SecModalPersonalizacion.isElementInStateUntil(secondElement, StateElem.Present, 1, channel, dFTest.driver)) {
				listVals.add(1, state);
			}
			
			datosStep.setListResultValidations(listVals);
		} 
		finally { listVals.checkAndStoreValidations(descripValidac); }
	}
	
	private static void validateIsApartadoVisible(int numApartado, DatosStep datosStep, DataFmwkTest dFTest, Channel channel) {
		ModalElement modalToValidate;
		switch (numApartado) {
		case 1:
			modalToValidate = ModalElement.Step1Proof;
			break;
		case 2:
			modalToValidate = ModalElement.Step2Proof;
			break;
		case 3:
			modalToValidate = ModalElement.Step3Proof;
			break;
		case 4:
		default:
			modalToValidate = ModalElement.Step4Proof;
		}
		
	    String descripValidac =                
	    	"1) Es visible el apartado " + numApartado + " de la personalización";
	    datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
	    try {
			if (!SecModalPersonalizacion.isElementInStateUntil(modalToValidate, StateElem.Present, 1, channel, dFTest.driver)) {
				listVals.add(1, State.Warn);
			}
			
	        datosStep.setListResultValidations(listVals);
	    } 
	    finally { listVals.checkAndStoreValidations(descripValidac); }
	}
}
