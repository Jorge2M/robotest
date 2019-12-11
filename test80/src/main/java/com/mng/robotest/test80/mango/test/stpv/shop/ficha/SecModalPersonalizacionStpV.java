package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.webdriver.wrapper.ElementPageFunctions.StateElem;
import com.mng.testmaker.service.webdriver.wrapper.TypeOfClick;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecModalPersonalizacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecModalPersonalizacion.ModalElement;
import org.openqa.selenium.WebDriver;

public class SecModalPersonalizacionStpV {

	WebDriver driver;
	DataCtxShop dCtxSh;
	PageFicha pageFichaWrap;

	private SecModalPersonalizacionStpV(DataCtxShop dCtxSh, WebDriver driver) {
		this.driver = driver;
		this.dCtxSh = dCtxSh;
		this.pageFichaWrap = PageFicha.newInstance(dCtxSh.channel, dCtxSh.appE, driver);
	}
	
	public static SecModalPersonalizacionStpV getNewOne(DataCtxShop dCtxSh, WebDriver driver) {
		return (new SecModalPersonalizacionStpV(dCtxSh, driver));
	}
	
	@SuppressWarnings("unused")
	@Validation (
		description="El artículo es personalizable (aparece el link \"Añadir bordado\")",
		level=State.Defect)
	public boolean checkAreArticleCustomizable() {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.AñadirBordadoLink, StateElem.Present, 1, dCtxSh.channel, driver));
	}

	@Step(
		description="Seleccionamos el link <b>Añadir bordado</b>",
		expected="Aparece el modal para la personalización de la prenda")
	public void selectLinkPersonalizacion () throws Exception {
		SecModalPersonalizacion.selectElement(ModalElement.AñadirBordadoLink, dCtxSh.channel, driver, TypeOfClick.javascript);
		validateModal(3);
	}

	@Validation(
		description="Aparece el modal de personalización con el botón <b>Siguiente</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	private boolean validateModal(int maxSecondsWait) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Siguiente, StateElem.Visible, maxSecondsWait, driver));
	}

//	@Step(
//		description="Seleccionamos el botón <b>Siguiente</b>",
//		expected="Aparecen las primeras opciones de personalizacion del artículo")
//	public void startCustomization () throws Exception {
//		SecModalPersonalizacion.selectElement(ModalElement.BotonSiguiente, dCtxSh.channel, driver, TypeOfClick.javascript);
//		if (dCtxSh.channel==Channel.desktop) {
//			validateCabeceraStep(1);
//		} else {
//			validationInitMblCustomization(2, ModalElement.HeaderProof);
//		}
//	}

	@Validation(
		description="1) Aparece la cabecera correspondiente a la personalizacion de la prenda",
		level=State.Warn)
	private boolean validationInitMblCustomization(int maxSecondsWait, ModalElement element) {
		return (SecModalPersonalizacion.isElementInStateUntil(element, StateElem.Visible, maxSecondsWait, dCtxSh.channel, driver));
	}

	@Step(
		description="Seleccionamos la opción <b>Un icono</b>",
		expected="Aparece la lista de iconos")
	public void selectIconCustomization() throws Exception {
		SecModalPersonalizacion.selectElement(ModalElement.ButtonUnIcono, dCtxSh.channel, driver, TypeOfClick.javascript);
	    validationIconSelection(2);
	}

	@Validation(
		description="1) Aparece la lista de iconos seleccionables",
		level=State.Warn)
	public boolean validationIconSelection(int maxSecondsWait) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.IconSelecction, StateElem.Visible, maxSecondsWait, dCtxSh.channel, driver));
	}

	@Step(
		description="Seleccionamos el primer icono",
		expected="La selección es correcta")
	public void selectFirstIcon() throws Exception {
		if (dCtxSh.channel == Channel.desktop) {
			SecModalPersonalizacion.selectElement(ModalElement.IconSelecction, dCtxSh.channel, driver);
		} else {
			SecModalPersonalizacion.selectElement(ModalElement.IconSelecction, dCtxSh.channel, driver, TypeOfClick.javascript);
		}
	}

	@Validation
	public ChecksResult validateIconSelectedDesktop() {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Aparece seleccionado el primer icono",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.IconSelecction, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
			"Podemos confirmar nuestra seleccion",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.Siguiente, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Step(
		description="Seleccionamos el botón \"Siguiente\"",
		expected="Se hace visible el paso-2")
	public void selectConfirmarButton () throws Exception {
		SecModalPersonalizacion.selectElement(ModalElement.Siguiente, dCtxSh.channel, driver);
	}

	@Validation
	public ChecksResult validateWhereDesktop() {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Aparecen las opciones correspondientes a la ubicación del bordado",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.PositionButton, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
			"Podemos confirmar nuestra seleccion",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.Siguiente, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Validation
	public ChecksResult validateSelectionColor() {
		ChecksResult validations = ChecksResult.getNew();
		int maxSecondsWait = 3;
		validations.add(
			"Aparecen los botones correspondientes a los colores",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.ColorsContainer, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		validations.add(
			"Aparece el botón de \"Confirmar\"",
			SecModalPersonalizacion.isElementInStateUntil(ModalElement.Siguiente, StateElem.Visible, maxSecondsWait, driver), State.Warn);
		return validations;
	}

	@Step(
		description="Seleccionamos el botón \"Confirmar\"",
		expected="Aparece el apartado 4 de la personalización")
	public void selectSize () throws Exception {
		SecModalPersonalizacion.clickAndWait(dCtxSh.channel, ModalElement.Siguiente, driver);
		validateSizeList(2);
	}

	@Validation(
		description="1) Aparecen los botones con los posibles tamaños del bordado",
		level=State.Warn)
	private boolean validateSizeList(int maxSecondsWait) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.SizeContainer, StateElem.Visible, maxSecondsWait, dCtxSh.channel, driver));
	}

	@Step(
		description="Seleccionamos el botón \"Siguiente\"",
		expected="Aparece el modal con las opciones para ver la bolsa o seguir comprando")
	public void confirmCustomization() throws Exception {
		SecModalPersonalizacion.selectElement(ModalElement.Siguiente, driver);
		validateAddBag(2);
	}

	@Validation(
		description="1) Aparece el botón para añadir a la bolsa",
		level=State.Warn)
	private boolean validateAddBag(int maxSecondsWait) {
		return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Siguiente, StateElem.Visible, maxSecondsWait, dCtxSh.channel, driver));
	}

	@Step(
		description="Seleccionar botón <b>Añadir a la bolsa</b>",
		expected="El artículo se da de alta correctamente en la bolsa"	)
	public void checkCustomizationProof () throws Exception {
		SecModalPersonalizacion.selectElement(ModalElement.Siguiente, dCtxSh.channel, driver);
		if (dCtxSh.channel == Channel.movil_web) {
			SecModalPersonalizacion.selectElement(ModalElement.GoToBag, dCtxSh.channel, driver);
		}
		validateCustomizationProof(2);
	}

	@Validation(
		description="1) En la bolsa aparece el apartado correspondiente a la personalización (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private boolean validateCustomizationProof(int maxSecondsWait) {
		if (dCtxSh.channel==Channel.movil_web) {
			return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.BolsaProof, StateElem.Present, maxSecondsWait, dCtxSh.channel, driver));
		} else {
			return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.BolsaProof, StateElem.Visible, maxSecondsWait, dCtxSh.channel, driver));
		}
	}

	@Validation(
		description="Es visible el apartado <b>#{level}</b>",
		level=State.Warn)
	public boolean validateCabeceraStep(int level) {
		switch (level) {
		case 1:
			return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Step1Proof, StateElem.Visible, 4, driver));
		case 2:
			return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Step2Proof, StateElem.Visible, 4, driver));
		case 3:
			return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Step3Proof, StateElem.Visible, 4, driver));
		case 4:
			return (SecModalPersonalizacion.isElementInStateUntil(ModalElement.Step4Proof, StateElem.Visible, 4, driver));
		default:
			return false;
		}
	}
}
