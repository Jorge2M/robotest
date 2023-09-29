package com.mng.robotest.tests.domains.registro.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.beans.DataRegistro;
import com.mng.robotest.tests.domains.registro.beans.ListDataRegistro;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroIniOutlet;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRegistroIniStepsOutlet extends StepBase {
	
	private final PageRegistroIniOutlet pageRegistroIni = new PageRegistroIniOutlet();
	private final Pais pais = dataTest.getPais();
	
	@Validation (
		description="Aparece la página inicial del proceso de registro " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pageRegistroIni.isPageUntil(seconds);
	}
	
	@Step (
		description=
			"Introducir:<br>" + 
			"  - Un email no existente: <b>#{emailNonExistent}</b><br>" +
			"  - Password usuario: *****<br>" + 
			"  - Seleccionar el link de publicidad<br>" +
			"  - El resto de datos específicos para el país", 
		expected=
			"No aparece ningún mensaje de dato incorrecto")
	public Map<String,String> sendDataAccordingCountryToInputs(String emailNonExistent, boolean clickPubli) {
		Map<String,String> dataSended = 
				pageRegistroIni.sendDataAccordingCountryToInputs(emailNonExistent, clickPubli, channel);
		validateNotAreErrorMessageInCorrectFields();
		return dataSended;
	}
	
	@Validation (
		description="No aparece mensaje de error en los campos con datos correctos",
		level=Warn)
	public boolean validateNotAreErrorMessageInCorrectFields() {
		return (!pageRegistroIni.isVisibleAnyInputErrorMessage());
	}

	@Step (
		description="Introducir los datos:<br>#{dataToSendInHtmlFormat}",
		expected="En los datos incorrectos aparece error y en los correctos no")
	public void sendFixedDataToInputs(ListDataRegistro dataToSend, String dataToSendInHtmlFormat) {
		pageRegistroIni.sendDataToInputs(dataToSend);
		validateMessagesErrorDependingInputs(dataToSend);
	}
	
	@Validation
	public ChecksTM validateMessagesErrorDependingInputs(ListDataRegistro dataToSend) {
		var checks = ChecksTM.getNew();
		for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
			String dataInputString = dataInput.getDataRegType() + " (<b>" + dataInput.getData() + "</b>)";
			if (dataInput.isValidPrevRegistro()) {
				checks.add(
					"No aparece mensaje de error el el campo con datos correctos: <b>" + dataInputString + "</b>",
					pageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType) <= 0, Warn);
			} else {
				checks.add(
					"Sí aparece mensaje de error el el campo con datos incorrectos: <b>" + dataInputString + "</b>",
					pageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType) > 0, Warn);
			}
		}
		return checks;
	}

	public enum ErrorRegister {
		NONE,
		INPUT_WARINGS,
		USR_EXISTS_IN_MANGO,
		USR_NO_EXISTS_IN_GMAIL
	}
	
	public void clickRegistrateButton(Map<String,String> dataRegistro) {
		clickRegistrateButton(dataRegistro, ErrorRegister.NONE);
	}
	
	@Step (
		description="Seleccionar el botón <b>Regístrate</b>")
	public void clickRegistrateButton(Map<String,String> dataRegistro, ErrorRegister errorExpected) {
		pageRegistroIni.clickButtonRegistrate();
		waitMillis(1000);
		validaIsInvisibleCapaLoading(15);
		
		switch (errorExpected) {
		case NONE:
			new PageRegistroSegundaStepsOutlet().validaIsPageRegistroOK(dataRegistro);
			break;
		case INPUT_WARINGS:
			int numInputsObligatoriosNoInf = pageRegistroIni.getNumInputsObligatoriosNoInformados();
			if (numInputsObligatoriosNoInf > 0) {
				validateAreInputsWithErrorMessageAssociated(numInputsObligatoriosNoInf);  
			}
			break;
		case USR_EXISTS_IN_MANGO:
			validaEmailYaRegistradoShown(5);
			break;
		case USR_NO_EXISTS_IN_GMAIL:
			validaEmailIncorrectShown();
			break;
		}
		checksDefault();
	}
	
	@Validation (
		description="Desaparece la capa de loading " + SECONDS_WAIT,
		level=Warn)
	public boolean validaIsInvisibleCapaLoading(int seconds) {
		return (pageRegistroIni.isCapaLoadingInvisibleUntil(seconds));
	}
	
	@Validation (
		description="Aparece un error <b>Email ya registrado</b> " + SECONDS_WAIT)
	private boolean validaEmailYaRegistradoShown(int seconds) {
		return(pageRegistroIni.isVisibleErrorUsrDuplicadoUntil(seconds));
	}
	
	@Validation (
		description="Aparece un error <b>Email incorrecto</b>")
	private boolean validaEmailIncorrectShown() {
		return(pageRegistroIni.isVisibleErrorEmailIncorrecto());
	}
	
	@Validation
	public ChecksTM validateAreInputsWithErrorMessageAssociated(int numInputsObligatoriosNoInf) {
		var checks = ChecksTM.getNew();
		int numInputsTypePassrod = pageRegistroIni.getNumberInputsTypePassword();
		int numErrCampObligatorio = pageRegistroIni.getNumberMsgCampoObligatorio();
		
		checks.add(
			"Aparecen " + numInputsObligatoriosNoInf + " errores de campo obligatorio",
			(numInputsObligatoriosNoInf + numInputsTypePassrod) >= numErrCampObligatorio, Warn);
		if (pageRegistroIni.isVisibleSelectPais()) {
			checks.add(
				"Existe desplegable país -> aparece seleccionado el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")",
				pageRegistroIni.isSelectedOptionPais(pais.getCodigo_pais()), Warn);
		}
		
		return checks;
	}
	
	@Validation (
		description=
		"<b style=\"color:blue\">Rebajas</b></br>" +
		"1) El mensaje de NewsLetter no aparece o si aparece no contiene el símbolo de porcentaje",
		level=Info,
		store=StoreType.None)
	public boolean validaRebajasJun2018() {
		String percentageSymbol = UtilsTest.getPercentageSymbol(dataTest.getIdioma());
		return !pageRegistroIni.newsLetterTitleContains(percentageSymbol);	   
	}

	public void validaIsRGPDVisible() {
		if (PaisShop.COREA_DEL_SUR.isEquals(pais)) {
			validateRGPDInCorea();
			return;
		}
		if (pais.getRgpd().equals("S")) {
			validateRGPDInCountryWithRgpd(pais.getCodigo_alf());
			return;
		}
	}
	
	@Validation
	public ChecksTM validateRGPDInCountryWithRgpd(String codigoPais) {
		var checks = ChecksTM.getNew();
		int seconds = 1;
		checks.add(
			"El texto de info de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + codigoPais,
			pageRegistroIni.isTextoRGPDVisible());
		
		checks.add(
			"El texto legal de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + codigoPais,
			pageRegistroIni.isTextoLegalRGPDVisible());
		
		checks.add(
			"<b>SI</b> está presente el checkbox para recibir promociones e información personalizada para el pais" +
			codigoPais + getLitSecondsWait(seconds),
			pageRegistroIni.isCheckboxRecibirInfoPresentUntil(seconds));
		
		return checks;
	}
	
	@Validation
	public ChecksTM validateRGPDInCorea() {
		var checks = ChecksTM.getNew();
		int seconds = 1;
		checks.add(
			"El texto de info de RGPD <b>NO</b> aparece en la pantalla de inicio de registro",
			!pageRegistroIni.isTextoRGPDVisible());
		
		checks.add(
			"El texto legal de RGPD <b>NO</b> aparece en la pantalla de inicio de registro", 
			!pageRegistroIni.isTextoLegalRGPDVisible());
		
		checks.add(
			"<b>SI</b> está presente el checkbox para recibir promociones e información personalizada" +
			getLitSecondsWait(seconds),
			pageRegistroIni.isCheckboxRecibirInfoPresentUntil(seconds));
		
		return checks;
	}
	
}
