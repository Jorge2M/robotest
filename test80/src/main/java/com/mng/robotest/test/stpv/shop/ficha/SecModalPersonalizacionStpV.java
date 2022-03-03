package com.mng.robotest.test.stpv.shop.ficha;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;
import com.mng.robotest.test.pageobject.shop.ficha.PageFicha;
import com.mng.robotest.test.pageobject.shop.ficha.SecModalPersonalizacion.ModalElement;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SecModalPersonalizacionStpV extends PageObjTM {

	DataCtxShop dCtxSh;
	PageFicha pageFichaWrap;

	private SecModalPersonalizacionStpV(DataCtxShop dCtxSh, WebDriver driver) {
		super(driver);
		this.dCtxSh = dCtxSh;
		this.pageFichaWrap = PageFicha.newInstance(dCtxSh.channel, dCtxSh.appE, driver);
	}
	
	public static SecModalPersonalizacionStpV getNewOne(DataCtxShop dCtxSh, WebDriver driver) {
		return (new SecModalPersonalizacionStpV(dCtxSh, driver));
	}
	
	public boolean checkArticleCustomizable() {
		return checkArticleCustomizable(State.Defect);
	}
	
	public boolean checkArticleCustomizable(State levelError) {
		ChecksTM checks = checkAreArticleCustomizable(levelError);
		for (Check check : checks.getListChecks()) {
			if (!check.isOvercomed()) {
				return false;
			}
		}
		return true;
	}
	
	@Validation
	private ChecksTM checkAreArticleCustomizable(State levelError) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"El artículo es personalizable (aparece el link \"Añadir bordado\")",
			state(Present, ModalElement.AñadirBordadoLink.getBy(dCtxSh.channel)).wait(1).check(), 
			levelError);
		return validations;
	}

	@Step(
		description="Seleccionamos el link <b>Añadir bordado</b>",
		expected="Aparece el modal para la personalización de la prenda")
	public void selectLinkPersonalizacion () throws Exception {
		click(ModalElement.AñadirBordadoLink.getBy(dCtxSh.channel)).type(javascript).exec();
		validateModal();
	}
	
	@Validation
	private ChecksTM validateModal() {
		int maxSeconds = 3;
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece el modal de personalización con el botón <b>Siguiente</b> (lo esperamos hasta " + maxSeconds + " segundos)",
			isBotonSiguienteVisible(maxSeconds), 
			State.Warn);
		validations.add(
			"Aparece la opción <b>Un icono</b> (la esperamos hasta " + maxSeconds + " segundos)",
			state(Visible, ModalElement.ButtonUnIcono.getBy(dCtxSh.channel)).wait(maxSeconds).check(), 
			State.Warn);
	
		return validations;
	}

	@Validation(
		description="1) Aparece la cabecera correspondiente a la personalizacion de la prenda",
		level=State.Warn)
	private boolean validationInitMblCustomization(int maxSeconds, ModalElement element) {
		return (state(Visible, element.getBy(dCtxSh.channel)).wait(maxSeconds).check());
	}

	@Step(
		description="Seleccionamos la opción <b>Un icono</b>",
		expected="Aparece la lista de iconos")
	public void selectIconCustomization() {
		click(ModalElement.ButtonUnIcono.getBy(dCtxSh.channel)).type(javascript).exec();
		validationIconSelection(2);
	}

	@Validation(
		description="1) Aparece la lista de iconos seleccionables",
		level=State.Warn)
	public boolean validationIconSelection(int maxSeconds) {
		return (state(Visible, ModalElement.IconSelecction.getBy(dCtxSh.channel)).wait(maxSeconds).check());
	}

	@Step(
		description="Seleccionamos el primer icono",
		expected="La selección es correcta")
	public void selectFirstIcon() {
		if (dCtxSh.channel == Channel.desktop) {
			click(ModalElement.IconSelecction.getBy(dCtxSh.channel)).exec();
		} else {
			click(ModalElement.IconSelecction.getBy(dCtxSh.channel)).type(javascript).exec();
		}
	}

	@Validation
	public ChecksTM validateIconSelectedDesktop() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 3;
		validations.add(
			"Aparece seleccionado el primer icono",
			state(Visible, ModalElement.IconSelecction.getBy()).wait(maxSeconds).check(), 
			State.Warn);
		validations.add(
			"Podemos confirmar nuestra seleccion",
			isBotonSiguienteVisible(maxSeconds),
			//state(Visible, ModalElement.Siguiente.getBy(dCtxSh.channel)).wait(maxSeconds).check(),
			State.Warn);
		return validations;
	}

	@Step(
		description="Seleccionamos el botón \"Siguiente\"",
		expected="Se hace visible el paso-2")
	public void selectConfirmarButton () {
		click(getBotonSiguienteVisible()).exec();
		//click(ModalElement.Siguiente.getBy(dCtxSh.channel)).exec();
	}
	
	private WebElement getBotonSiguienteVisible() {
		return getElementVisible(driver, ModalElement.Siguiente.getBy(dCtxSh.channel));
	}
	private boolean isBotonSiguienteVisible(int maxSeconds) {
		for (int i=0; i<maxSeconds; i++) {
			if (getBotonSiguienteVisible()!=null) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	@Validation
	public ChecksTM validateWhereDesktop() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 3;
		validations.add(
			"Aparecen las opciones correspondientes a la ubicación del bordado",
			state(Visible, ModalElement.PositionButton.getBy()).wait(maxSeconds).check(),
			State.Warn);
		validations.add(
			"Podemos confirmar nuestra seleccion",
			isBotonSiguienteVisible(maxSeconds),
			//state(Visible, ModalElement.Siguiente.getBy()).wait(maxSeconds).check(),
			State.Warn);
		return validations;
	}

	@Validation
	public ChecksTM validateSelectionColor() {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 3;
		validations.add(
			"Aparecen los botones correspondientes a los colores",
			state(Visible, ModalElement.ColorsContainer.getBy()).wait(maxSeconds).check(),
			State.Warn);
		validations.add(
			"Aparece el botón de \"Confirmar\"",
			state(Present, ModalElement.Siguiente.getBy()).wait(maxSeconds).check(),
			State.Warn);
		return validations;
	}

	@Step(
		description="Seleccionamos el botón \"Confirmar\"",
		expected="Aparece el apartado 4 de la personalización")
	public void selectSize () throws Exception {
		click(getBotonSiguienteVisible()).exec();
		//click(ModalElement.Siguiente.getBy(dCtxSh.channel)).exec();
		validateSizeList(2);
	}

	@Validation(
		description="1) Aparecen los botones con los posibles tamaños del bordado",
		level=State.Warn)
	private boolean validateSizeList(int maxSeconds) {
		return (state(Visible, ModalElement.SizeContainer.getBy(dCtxSh.channel)).wait(maxSeconds).check());
	}

	@Step(
		description="Seleccionamos el botón \"Siguiente\"",
		expected="Aparece el modal con las opciones para ver la bolsa o seguir comprando")
	public void confirmCustomization() throws Exception {
		click(getBotonSiguienteVisible()).exec();
		//click(ModalElement.Siguiente.getBy(dCtxSh.channel)).exec();
		validateAddBag(2);
	}

	@Validation(
		description="1) Aparece el botón para añadir a la bolsa",
		level=State.Warn)
	private boolean validateAddBag(int maxSeconds) {
		return isBotonSiguienteVisible(maxSeconds);
		//return (state(Visible, ModalElement.Siguiente.getBy(dCtxSh.channel)).wait(maxSeconds).check());
	}

	@Step(
		description="Seleccionar botón <b>Añadir a la bolsa</b>",
		expected="El artículo se da de alta correctamente en la bolsa"	)
	public void checkCustomizationProof () {
		click(getBotonSiguienteVisible()).waitLoadPage(2).exec();
		if (dCtxSh.channel.isDevice()) {
			SecBolsa secBolsa = SecBolsa.make(dCtxSh, driver);
			secBolsa.setBolsaToStateIfNotYet(StateBolsa.Open);
		}
		validateCustomizationProof(2);
	}

	@Validation(
		description="1) En la bolsa aparece el apartado correspondiente a la personalización (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean validateCustomizationProof(int maxSeconds) {
		return (state(Visible, ModalElement.BolsaProof.getBy(dCtxSh.channel)).wait(maxSeconds).check());
	}

	@Validation(
		description="Es visible el apartado <b>#{level}</b>",
		level=State.Warn)
	public boolean validateCabeceraStep(int level) {
		switch (level) {
		case 1:
			return (state(Visible, ModalElement.Step1Proof.getBy()).wait(4).check());
		case 2:
			return (state(Visible, ModalElement.Step2Proof.getBy()).wait(4).check());
		case 3:
			return (state(Visible, ModalElement.Step3Proof.getBy()).wait(4).check());
		case 4:
			return (state(Visible, ModalElement.Step4Proof.getBy()).wait(4).check());
		default:
			return false;
		}
	}
}
