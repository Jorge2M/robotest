package com.mng.robotest.tests.domains.registro.steps;

import java.util.Map;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.registro.beans.DataRegistro;
import com.mng.robotest.tests.domains.registro.beans.ListDataRegistro;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroIniOld;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;
import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class PageRegistroIniStepsOld extends StepBase {
	
	private final PageRegistroIniOld pgRegistroIni = new PageRegistroIniOld();
	private final Pais pais = dataTest.getPais();
	
	@Validation (
		description="Aparece la página inicial del proceso de registro " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) {
		return pgRegistroIni.isPage(seconds);
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
		var dataSended = pgRegistroIni.sendDataAccordingCountryToInputs(emailNonExistent, clickPubli);
		validateNotAreErrorMessageInCorrectFields();
		return dataSended;
	}
	
	@Validation (
		description="No aparece mensaje de error en los campos con datos correctos",
		level=WARN)
	public boolean validateNotAreErrorMessageInCorrectFields() {
		return !pgRegistroIni.isVisibleAnyInputErrorMessage();
	}

	@Step (
		description="Introducir los datos:<br>#{dataToSendInHtmlFormat}",
		expected="En los datos incorrectos aparece error y en los correctos no")
	public void sendFixedDataToInputs(ListDataRegistro dataToSend, String dataToSendInHtmlFormat) {
		pgRegistroIni.sendDataToInputs(dataToSend);
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
					pgRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType) <= 0, WARN);
			} else {
				checks.add(
					"Sí aparece mensaje de error el el campo con datos incorrectos: <b>" + dataInputString + "</b>",
					pgRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType) > 0, WARN);
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
		pgRegistroIni.clickButtonRegistrate();
		waitMillis(1000);
		validaIsInvisibleCapaLoading(15);
		
		switch (errorExpected) {
		case NONE:
			new PageRegistroSegundaStepsOutlet().validaIsPageRegistroOK(dataRegistro);
			break;
		case INPUT_WARINGS:
			int numInputsObligatoriosNoInf = pgRegistroIni.getNumInputsObligatoriosNoInformados();
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
		level=WARN)
	public boolean validaIsInvisibleCapaLoading(int seconds) {
		return (pgRegistroIni.isCapaLoadingInvisibleUntil(seconds));
	}
	
	@Validation (
		description="Aparece un error <b>Email ya registrado</b> " + SECONDS_WAIT)
	private boolean validaEmailYaRegistradoShown(int seconds) {
		return pgRegistroIni.isVisibleErrorUsrDuplicadoUntil(seconds);
	}
	
	@Validation (
		description="Aparece un error <b>Email incorrecto</b>")
	private boolean validaEmailIncorrectShown() {
		return pgRegistroIni.isVisibleErrorEmailIncorrecto();
	}
	
	@Validation
	public ChecksTM validateAreInputsWithErrorMessageAssociated(int numInputsObligatoriosNoInf) {
		var checks = ChecksTM.getNew();
		int numInputsTypePassrod = pgRegistroIni.getNumberInputsTypePassword();
		int numErrCampObligatorio = pgRegistroIni.getNumberMsgCampoObligatorio();
		
		checks.add(
			"Aparecen " + numInputsObligatoriosNoInf + " errores de campo obligatorio",
			(numInputsObligatoriosNoInf + numInputsTypePassrod) >= numErrCampObligatorio, WARN);
		if (pgRegistroIni.isVisibleSelectPais()) {
			checks.add(
				"Existe desplegable país -> aparece seleccionado el país con código " + pais.getCodigoPais() + " (" + pais.getNombrePais() + ")",
				pgRegistroIni.isSelectedOptionPais(pais.getCodigoPais()), WARN);
		}
		
		return checks;
	}
	
	@Validation (
		description=
		"<b style=\"color:blue\">Rebajas</b></br>" +
		"1) El mensaje de NewsLetter no aparece o si aparece no contiene el símbolo de porcentaje",
		level=INFO, store=NONE)
	public boolean validaRebajasJun2018() {
		String percentageSymbol = UtilsTest.getPercentageSymbol(dataTest.getIdioma());
		return !pgRegistroIni.newsLetterTitleContains(percentageSymbol);	   
	}

	public void validaIsRGPDVisible() {
		if (isCountry(COREA_DEL_SUR)) {
			validateRGPDInCorea();
			return;
		}
		if (pais.getRgpd().equals("S")) {
			validateRGPDInCountryWithRgpd(pais.getCodigoAlf());
		}
	}
	
	@Validation
	public ChecksTM validateRGPDInCountryWithRgpd(String codigoPais) {
		var checks = ChecksTM.getNew();
		int seconds = 1;
		checks.add(
			"El texto de info de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + codigoPais,
			pgRegistroIni.isTextoRGPDVisible());
		
		checks.add(
			"El texto legal de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + codigoPais,
			pgRegistroIni.isTextoLegalRGPDVisible());
		
		checks.add(
			"<b>SI</b> está presente el checkbox para recibir promociones e información personalizada para el pais" +
			codigoPais + getLitSecondsWait(seconds),
			pgRegistroIni.isCheckboxRecibirInfoPresentUntil(seconds));
		
		return checks;
	}
	
	@Validation
	public ChecksTM validateRGPDInCorea() {
		var checks = ChecksTM.getNew();
		int seconds = 1;
		checks.add(
			"El texto de info de RGPD <b>NO</b> aparece en la pantalla de inicio de registro",
			!pgRegistroIni.isTextoRGPDVisible());
		
		checks.add(
			"El texto legal de RGPD <b>NO</b> aparece en la pantalla de inicio de registro", 
			!pgRegistroIni.isTextoLegalRGPDVisible());
		
		checks.add(
			"<b>SI</b> está presente el checkbox para recibir promociones e información personalizada" +
			getLitSecondsWait(seconds),
			pgRegistroIni.isCheckboxRecibirInfoPresentUntil(seconds));
		
		return checks;
	}
	
}
