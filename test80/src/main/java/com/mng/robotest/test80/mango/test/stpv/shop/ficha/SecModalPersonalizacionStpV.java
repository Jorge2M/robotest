package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageOrdenacionDePrendas;
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
import org.openqa.selenium.WebDriver;

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

		validateModal(3, dFTest.driver);
	}

	@Validation(
			description="1) Aparece el modal de personalización de la prenda",
			level=State.Warn)
	public static boolean validateModal(int maxSecondsWait, WebDriver driver) {
		return (!SecModalPersonalizacion.isElementInStateUntil(ModalElement.Modal, StateElem.Visible, maxSecondsWait, driver));
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
    		validationInitMblCustomization(2, ModalElement.HeaderProof, channel, dFTest.driver);
    	}
    }

    @Validation(
			description="1) Aparece la cabecera correspondiente a la personalizacion de la prenda",
			level=State.Warn)
	public static boolean validationInitMblCustomization(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
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
			validationIconSelection(2, ModalElement.Icons, channel, dFTest.driver);
		} else {
			validateCabeceraMvl("La cabecera ha cambiado",2, channel, dFTest.driver);
			validationIconSelection(2, ModalElement.BackProof, channel, dFTest.driver);
		}
	}

	@Validation(
			description="1) Aparece la lista de iconos seleccionables",
			level=State.Warn)
	public static boolean validationIconSelection(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
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
			validateIconSelected(datosStep, dFTest.driver);
		} else {
			validateFirstIconSelectionMvl(2, ModalElement.PositionButton, channel, dFTest.driver);
		}
	}

	@Validation
	public static ListResultValidation validateIconSelected(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"Aparece seleccionado el primer icono<br>",
				PageOrdenacionDePrendas.isElementInStateUntil(ModalElement.IconSelecction, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
				"Podemos confirmar nuestra seleccion",
				PageOrdenacionDePrendas.isElementInStateUntil(ModalElement.PositionButton, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Validation(
			description="1) Aparece seleccionado el primer icono y podemos confirmar nuestra seleccion",
			level=State.Warn)
	public static boolean validateFirstIconSelectionMvl(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
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
			validateWhere(datosStep, dFTest.driver);
		} else {
			validateCabeceraMvl("Seguimos en el proceso de personalizacion", 2, channel, dFTest.driver);
			validateColorsMvl(2, ModalElement.ColorsContainer, channel, dFTest.driver);
		}
	}

	@Validation
	public static ListResultValidation validateWhere(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"Aparecen los radio-button correspondientes a la seccion<br>",
				PageOrdenacionDePrendas.isElementInStateUntil(ModalElement.PositionButton, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
				"Podemos confirmar nuestra seleccion",
				PageOrdenacionDePrendas.isElementInStateUntil(ModalElement.StepProof, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Validation(
			description="1) En el flujo mobil, ahora aparecen los colores disponibles",
			level=State.Warn)
	public static boolean validateColorsMvl(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
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
			validateSelectionColor(datosStep, dFTest.driver);
		} else {
			validateCabeceraMvl("Cambia la cabecera pero seguimos en la personalizacion del producto", 2, channel, dFTest.driver);
			validateContinuesMvl(2, channel, dFTest.driver);
		}
	}

	@Validation
	public static ListResultValidation validateSelectionColor(DatosStep datosStep, WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew(datosStep);
		int maxSecondsWait = 3;
		validations.add(
				"Aparecen los radio-button correspondientes a los colores<br>",
				PageOrdenacionDePrendas.isElementInStateUntil(ModalElement.ColorsContainer, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
				"Aparece el botón de \"Confirmar\"",
				PageOrdenacionDePrendas.isElementInStateUntil(ModalElement.Continue, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Validation(
			description="1) Podemos continuar con nuestro proceso de personalizacion",
			level=State.Warn)
	public static boolean validateContinuesMvl(int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Continue, StateElem.Visible, maxSecondsWait, channel, driver));
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
			validateSizeList(2, channel, dFTest.driver);
		} else {
			validateAddBagMvl("que nos permite añadir ese producto a la bolsa", 2, channel, dFTest.driver);
		}
    }

	@Validation(
			description="1) Aparecen los posibles tamaños",
			level=State.Warn)
	public static boolean validateSizeList(int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.SizeContainer, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Validation(
			description="1) Es visible el botón #{descripcion}que nos permite añadir ese producto a la bolsa",
			level=State.Warn)
	public static boolean validateAddBagMvl(String descripcion, int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.addToBag, StateElem.Visible, maxSecondsWait, channel, driver));
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
			validateAddBag(2, channel, dFTest.driver);
			
		} else {
			validateAddBagMvl("<b>ir a la bolsa</b>", 2, channel, dFTest.driver);
		}
	}

	@Validation(
			description="1) Aparece el botón para añadir a la bolsa",
			level=State.Warn)
	public static boolean validateAddBag(int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Continue, StateElem.Visible, maxSecondsWait, channel, driver));
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
		validateCustomizationProof(2, ModalElement.BolsaProof, channel, dFTest.driver);
	}

	@Validation(
			description="1) En la bolsa aparece el apartado correspondiente a la personalización (lo esperamos hasta #{maxSecondsWait} segundos)",
			level=State.Defect)
	public static boolean validateCustomizationProof(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
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

		validateSection(4, modalToValidate, numApartado, channel, dFTest.driver);
	}

	@Validation(
			description="1) Es visible el apartado #{numApartado} de la personalización",
			level=State.Defect)
	public static boolean validateSection(int maxSecondsWait, ModalElement element, int numApartado, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	//Validacion cabecera movil
	@Validation(
			description="1) #{descripcion}",
			level=State.Warn)
	public static boolean validateCabeceraMvl(String descripcion, int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.HeaderProof, StateElem.Visible, maxSecondsWait, channel, driver));
	}

}
