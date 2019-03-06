package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import org.openqa.selenium.By;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
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

	public void searchForCustomization(Channel channel, DataFmwkTest dFTest, DataCtxShop dCtxSh) throws Exception {
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(channel, dCtxSh.appE);

		boolean customizable = false;
		int maxArticlesToReview = 8;
		int i = 1;
		String galeriaToSelect = "camisas";
		do {
			Menu1rstLevel menuCamisas = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.he, null, galeriaToSelect));
			SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuCamisas, dCtxSh, dFTest);
			SecMenusWrapperStpV.selectFiltroCollectionIfExists(FilterCollection.nextSeason, dCtxSh.channel, dCtxSh.appE, dFTest);
			LocationArticle articleNum = LocationArticle.getInstanceInCatalog(i);
			pageGaleriaStpV.selectArticulo(articleNum, dCtxSh);
			if (SecModalPersonalizacion.isElementInStateUntil(ModalElement.BotonIniciar, StateElem.Present, 1, channel, dFTest.driver)) {
				customizable = true;
			}
			i = i + 1;
		} while (!customizable && i<(maxArticlesToReview + 1));

		checkAreArticleCustomizable(customizable, galeriaToSelect, maxArticlesToReview, dFTest.driver);
	}
	
	@SuppressWarnings("unused")
	@Validation (
		description="Alguno de los #{maxArticlesToReview} primeros artículos de la galería #{galeriaToSelect} es personalizable",
		level=State.Defect)
	private boolean checkAreArticleCustomizable(boolean resultCheck, String galeriaToSelect, int maxArticlesToReview, WebDriver driver) {
		return resultCheck;
	}

	public void startCustomizationProcces(Channel channel, DataFmwkTest dFTest) throws Exception {
		selectCustomization(channel, dFTest.driver);
		startCustomization(channel, dFTest.driver);
	}

	public void customizationProcces(Channel channel, DataFmwkTest dFTest) throws Exception {
		selectIconCustomization(channel, dFTest.driver);
		selectFirstIcon(channel, dFTest.driver);
		selectWhere(channel, dFTest.driver);
		selectColor(channel, dFTest.driver);
		selectSize(channel, dFTest.driver);
	}

	public void endAndCheckCustomization(Channel channel, DataFmwkTest dFTest) throws Exception {
		confirmCustomization(channel, dFTest.driver);
		checkCustomizationProof(channel, dFTest.driver);
	}

	//Funciones donde comprobaremos los diferentes pasos de la personalizacion

	@Step(
		description="Seleccionamos el link <b>Añadir personalización</b>",
		expected="Aparece el modal para la personalización de la prenda")
	private void selectCustomization (Channel channel, WebDriver driver) throws Exception {
		if(channel == Channel.movil_web) {
			WebdrvWrapp.moveToElement(By.xpath(ModalElement.BotonIniciar.getXPath(channel)), driver);
		}
		
		SecModalPersonalizacion.selectElement(ModalElement.BotonIniciar, channel, driver);
		validateModal(3, driver);
	}

	@Validation(
		description="1) Aparece el modal de personalización de la prenda",
		level=State.Warn)
	private static boolean validateModal(int maxSecondsWait, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Modal, StateElem.Visible, maxSecondsWait, driver));
	}

	@Step(
		description="Seleccionamos el botón <b>Empezar</b>",
		expected="Aparecen las primeras opciones de personalizacion del artículo"
	)
	private void startCustomization (Channel channel, WebDriver driver) throws Exception {
		//Step
		SecModalPersonalizacion.selectElement(ModalElement.StartProcces, channel, driver);

		//Validation
		if (channel == Channel.desktop) {
			validateIsApartadoVisible(1, driver, channel);
		} 
		else {
			validationInitMblCustomization(2, ModalElement.HeaderProof, channel, driver);
		}
	}

	@Validation(
		description="1) Aparece la cabecera correspondiente a la personalizacion de la prenda",
		level=State.Warn)
	private static boolean validationInitMblCustomization(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Step(
		description="Seleccionamos la opción <b>Un icono</b>",
		expected="Aparece la lista de iconos")
	private void selectIconCustomization (Channel channel, WebDriver driver) throws Exception {
		SecModalPersonalizacion.selectElement(ModalElement.RadioIcon, channel, driver);
		if (channel == Channel.desktop) {
			validateIsApartadoVisible(1, driver, channel);
			validationIconSelection(2, ModalElement.Icons, channel, driver);
		} 
		else {
			validateCabeceraMvl(2, channel, driver);
			validationIconSelection(2, ModalElement.BackProof, channel, driver);
		}
	}

	@Validation(
		description="1) Aparece la lista de iconos seleccionables",
		level=State.Warn)
	private static boolean validationIconSelection(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Step(
		description="Seleccionamos el primer icono",
		expected="Aparece el botón Confirmar")
	private void selectFirstIcon (Channel channel, WebDriver driver) throws Exception {
		SecModalPersonalizacion.selectElement(ModalElement.IconSelecction, channel, driver, WebdrvWrapp.TypeOfClick.javascript);
		if (channel == Channel.desktop) {
			validateIconSelected(driver);
		} 
		else {
			validateFirstIconSelectionMvl(2, ModalElement.PositionButton, channel, driver);
		}
	}

	@Validation
	private static ListResultValidation validateIconSelected(WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Aparece seleccionado el primer icono<br>",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.IconSelecction, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
			"Podemos confirmar nuestra seleccion",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.Continue, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Validation(
		description="1) Aparece seleccionado el primer icono y podemos confirmar nuestra seleccion",
		level=State.Warn)
	private static boolean validateFirstIconSelectionMvl(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Step(
		description="Seleccionamos el botón \"Confirmar\"",
		expected="Se hace visible el paso-2")
	private void selectWhere (Channel channel, WebDriver driver) throws Exception {
		//Step
		if (channel == Channel.desktop) {
			SecModalPersonalizacion.selectElement(ModalElement.Continue, channel, driver);
		}
		SecModalPersonalizacion.selectElement(ModalElement.PositionButton, channel, driver);

		//Validation
		if (channel == Channel.desktop) {
			validateIsApartadoVisible(2, driver, channel);
			validateWhere(driver);
		} 
		else {
			validateCabeceraMvl(2, channel, driver);
			validateColorsMvl(2, ModalElement.ColorsContainer, channel, driver);
		}
	}

	@Validation
	private static ListResultValidation validateWhere(WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Aparecen los radio-button correspondientes a la seccion<br>",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.PositionButton, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
			"Podemos confirmar nuestra seleccion",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.Continue, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Validation(
		description="1) En el flujo mobil, ahora aparecen los colores disponibles",
		level=State.Warn)
	private static boolean validateColorsMvl(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Step(
		description="Seleccionamos el botón \"Confirmar\"",
		expected="Aparece el apartado 3 de la personalización")
	private void selectColor (Channel channel, WebDriver driver) throws Exception {
		SecModalPersonalizacion.selectElement(ModalElement.Continue, channel, driver, WebdrvWrapp.TypeOfClick.javascript);
		if (channel == Channel.desktop) {
			validateIsApartadoVisible(3, driver, channel);
			validateSelectionColor(driver);
		} 
		else {
			validateCabeceraMvl(2, channel, driver);
			validateContinuesMvl(2, channel, driver);
		}
	}

	@Validation
	private static ListResultValidation validateSelectionColor(WebDriver driver) {
		ListResultValidation validations = ListResultValidation.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Aparecen los radio-button correspondientes a los colores<br>",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.ColorsContainer, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
			"Aparece el botón de \"Confirmar\"",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.Continue, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Validation(
		description="1) Podemos continuar con nuestro proceso de personalizacion",
		level=State.Warn)
	private static boolean validateContinuesMvl (int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Continue, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Step(
		description="Seleccionamos el botón \"Confirmar\"",
		expected="Aparece el apartado 4 de la personalización")
	private void selectSize (Channel channel, WebDriver driver) throws Exception {
		SecModalPersonalizacion.clickAndWait(channel, ModalElement.Continue, driver);
		if (channel != Channel.movil_web) {
			validateSizeList(2, channel, driver);
		} 
		else {
			validateAddBagMvl(2, channel, driver);
		}
	}

	@Validation(
		description="1) Aparecen los posibles tamaños",
		level=State.Warn)
	private static boolean validateSizeList(int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.SizeContainer, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Validation(
		description="1) Es visible el botón #{descripcion} que nos permite añadir ese producto a la bolsa",
		level=State.Warn)
	private static boolean validateAddBagMvl(int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.addToBag, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Step(
		description="Seleccionamos el botón \"Añadir a la bolsa\"",
		expected="Aparece el modal con las opciones para ver la bolsa o seguir comprando")
	private void confirmCustomization (Channel channel, WebDriver driver) throws Exception {
		if (channel != Channel.movil_web) {
			SecModalPersonalizacion.selectElement(ModalElement.Continue, driver);
		}

		if (channel == Channel.desktop) {
			validateAddBag(2, channel, driver);
		} 
		else {
			validateAddBagMvl(2, channel, driver);
		}
	}

	@Validation(
		description="1) Aparece el botón para añadir a la bolsa",
		level=State.Warn)
	private static boolean validateAddBag(int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Continue, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Step(
		description="Comprobamos que el artículo se haya dado de alta correctamente observando la bolsa",
		expected="El artículo se da de alta correctamente en la bolsa"	)
	private void checkCustomizationProof (Channel channel, WebDriver driver) throws Exception {
		if (channel == Channel.desktop) {
			SecModalPersonalizacion.selectElement(ModalElement.Continue, channel, driver);
		} 
		else if (channel == Channel.movil_web) {
			SecModalPersonalizacion.selectElement(ModalElement.StartProcces, channel, driver);
			SecModalPersonalizacion.selectElement(ModalElement.GoToBag, channel, driver);
		}

		validateCustomizationProof(2, channel, driver);
	}

	@Validation(
		description="1) En la bolsa aparece el apartado correspondiente a la personalización (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private static boolean validateCustomizationProof(int maxSecondsWait, Channel channel, WebDriver driver) {
		if (channel==Channel.movil_web){
			return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.BolsaProof, StateElem.Present, maxSecondsWait, channel, driver));
		} 
		else {
			return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.BolsaProof, StateElem.Visible, maxSecondsWait, channel, driver));
		}
	}

	private static void validateIsApartadoVisible(int numApartado, WebDriver driver, Channel channel) {
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

		validateSection(4, modalToValidate, channel, driver);
	}

	@Validation(
		description="1) Es visible el apartado #{numApartado} de la personalización",
		level=State.Defect)
	private static boolean validateSection(int maxSecondsWait, ModalElement element, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, channel, driver));
	}

	@Validation(
		description="1) #{descripcion}",
		level=State.Warn)
	private static boolean validateCabeceraMvl(int maxSecondsWait, Channel channel, WebDriver driver) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.HeaderProof, StateElem.Visible, maxSecondsWait, channel, driver));
	}
}
