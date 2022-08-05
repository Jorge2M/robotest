package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.ficha.pageobjects.PageFicha;
import com.mng.robotest.domains.ficha.pageobjects.SecModalPersonalizacion.ModalElement;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa;
import com.mng.robotest.test.pageobject.shop.bolsa.SecBolsa.StateBolsa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SecModalPersonalizacionSteps extends PageObjTM {

	DataCtxShop dCtxSh;
	PageFicha pageFichaWrap;

	private SecModalPersonalizacionSteps(DataCtxShop dCtxSh, WebDriver driver) {
		super(driver);
		this.dCtxSh = dCtxSh;
		this.pageFichaWrap = PageFicha.newInstance(dCtxSh.channel, dCtxSh.appE);
	}
	
	public static SecModalPersonalizacionSteps getNewOne(DataCtxShop dCtxSh, WebDriver driver) {
		return (new SecModalPersonalizacionSteps(dCtxSh, driver));
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
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"El artículo es personalizable (aparece el link \"Añadir bordado\")",
			state(Present, ModalElement.ANADIR_BORDADO_LINK.getBy(dCtxSh.channel)).wait(1).check(), 
			levelError);
		return checks;
	}

	@Step(
		description="Seleccionamos el link <b>Añadir bordado</b>",
		expected="Aparece el modal para la personalización de la prenda")
	public void selectLinkPersonalizacion () throws Exception {
		click(ModalElement.ANADIR_BORDADO_LINK.getBy(dCtxSh.channel)).type(javascript).exec();
		validateModal();
	}
	
	@Validation
	private ChecksTM validateModal() {
		int maxSeconds = 3;
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece el modal de personalización con el botón <b>Siguiente</b> (lo esperamos hasta " + maxSeconds + " segundos)",
			isBotonSiguienteVisible(maxSeconds), 
			State.Warn);
		checks.add(
			"Aparece la opción <b>Un icono</b> (la esperamos hasta " + maxSeconds + " segundos)",
			state(Visible, ModalElement.BUTTON_UN_ICONO.getBy(dCtxSh.channel)).wait(maxSeconds).check(), 
			State.Warn);
	
		return checks;
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
		click(ModalElement.BUTTON_UN_ICONO.getBy(dCtxSh.channel)).type(javascript).exec();
		validationIconSelection(2);
	}

	@Validation(
		description="1) Aparece la lista de iconos seleccionables",
		level=State.Warn)
	public boolean validationIconSelection(int maxSeconds) {
		return (state(Visible, ModalElement.ICON_SELECTION.getBy(dCtxSh.channel)).wait(maxSeconds).check());
	}

	@Step(
		description="Seleccionamos el primer icono",
		expected="La selección es correcta")
	public void selectFirstIcon() {
		if (dCtxSh.channel == Channel.desktop) {
			click(ModalElement.ICON_SELECTION.getBy(dCtxSh.channel)).exec();
		} else {
			click(ModalElement.ICON_SELECTION.getBy(dCtxSh.channel)).type(javascript).exec();
		}
	}

	@Validation
	public ChecksTM validateIconSelectedDesktop() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 3;
		checks.add(
			"Aparece seleccionado el primer icono",
			state(Visible, ModalElement.ICON_SELECTION.getBy()).wait(maxSeconds).check(), 
			State.Warn);
		checks.add(
			"Podemos confirmar nuestra seleccion",
			isBotonSiguienteVisible(maxSeconds),
			//state(Visible, ModalElement.Siguiente.getBy(dCtxSh.channel)).wait(maxSeconds).check(),
			State.Warn);
		return checks;
	}

	@Step(
		description="Seleccionamos el botón \"Siguiente\"",
		expected="Se hace visible el paso-2")
	public void selectConfirmarButton () {
		click(getBotonSiguienteVisible()).exec();
		//click(ModalElement.Siguiente.getBy(dCtxSh.channel)).exec();
	}
	
	private WebElement getBotonSiguienteVisible() {
		return getElementVisible(driver, ModalElement.SIGUIENTE.getBy(dCtxSh.channel));
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
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 3;
		checks.add(
			"Aparecen las opciones correspondientes a la ubicación del bordado",
			state(Visible, ModalElement.POSITION_BUTTON.getBy()).wait(maxSeconds).check(),
			State.Warn);
		checks.add(
			"Podemos confirmar nuestra seleccion",
			isBotonSiguienteVisible(maxSeconds),
			//state(Visible, ModalElement.Siguiente.getBy()).wait(maxSeconds).check(),
			State.Warn);
		return checks;
	}

	@Validation
	public ChecksTM validateSelectionColor() {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 3;
		checks.add(
			"Aparecen los botones correspondientes a los colores",
			state(Visible, ModalElement.COLORS_CONTAINER.getBy()).wait(maxSeconds).check(),
			State.Warn);
		checks.add(
			"Aparece el botón de \"Confirmar\"",
			state(Present, ModalElement.SIGUIENTE.getBy()).wait(maxSeconds).check(),
			State.Warn);
		return checks;
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
		return (state(Visible, ModalElement.SIZE_CONTAINER.getBy(dCtxSh.channel)).wait(maxSeconds).check());
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
			SecBolsa secBolsa = SecBolsa.make(dCtxSh);
			secBolsa.setBolsaToStateIfNotYet(StateBolsa.OPEN);
		}
		validateCustomizationProof(2);
	}

	@Validation(
		description="1) En la bolsa aparece el apartado correspondiente a la personalización (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean validateCustomizationProof(int maxSeconds) {
		return (state(Visible, ModalElement.BOLSA_PROOF.getBy(dCtxSh.channel)).wait(maxSeconds).check());
	}

	@Validation(
		description="Es visible el apartado <b>#{level}</b>",
		level=State.Warn)
	public boolean validateCabeceraStep(int level) {
		switch (level) {
		case 1:
			return (state(Visible, ModalElement.STEP1_PROOF.getBy()).wait(4).check());
		case 2:
			return (state(Visible, ModalElement.STEP2_PROOF.getBy()).wait(4).check());
		case 3:
			return (state(Visible, ModalElement.STEP3_PROOF.getBy()).wait(4).check());
		case 4:
			return (state(Visible, ModalElement.STEP4_PROOF.getBy()).wait(4).check());
		default:
			return false;
		}
	}
}
