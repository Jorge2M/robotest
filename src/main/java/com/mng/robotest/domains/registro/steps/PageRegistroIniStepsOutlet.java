package com.mng.robotest.domains.registro.steps;

import java.util.HashMap;
import java.util.Map;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.registro.beans.DataRegistro;
import com.mng.robotest.domains.registro.beans.ListDataRegistro;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroIniOutlet;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.utils.UtilsTest;

public class PageRegistroIniStepsOutlet extends StepBase {
	
	private final PageRegistroIniOutlet pageRegistroIni = new PageRegistroIniOutlet();
	private final Pais pais = dataTest.pais;
	
	@Validation (
		description="Aparece la página inicial del proceso de registro (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean validaIsPageUntil(int maxSeconds) {
		return (pageRegistroIni.isPageUntil(maxSeconds));
	}

	@Step (
		description=
			"Introducir:<br>" + 
			"  - Un email no existente: <b>#{emailNonExistent}</b><br>" +
			"  - Password usuario: *****<br>" + 
			"  - Seleccionar el link de publicidad<br>" +
			"  - El resto de datos específicos para el país \"#{pais.getNombre_pais()}\"", 
		expected=
			"No aparece ningún mensaje de dato incorrecto")
	public Map<String,String> sendDataAccordingCountryToInputs(String emailNonExistent, boolean clickPubli) 
			throws Exception {
		Map<String,String> dataSended = new HashMap<>();
		dataSended = pageRegistroIni.sendDataAccordingCountryToInputs(emailNonExistent, clickPubli, channel);
		validateNotAreErrorMessageInCorrectFields();
		return dataSended;
	}
	
	@Validation (
		description="No aparece mensaje de error en los campos con datos correctos",
		level=State.Warn)
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
		ChecksTM checks = ChecksTM.getNew();
		for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
			String dataInputString = dataInput.getDataRegType() + " (<b>" + dataInput.getData() + "</b>)";
			if (dataInput.isValidPrevRegistro()) {
				checks.add(
					"No aparece mensaje de error el el campo con datos correctos: <b>" + dataInputString + "</b>",
					pageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType) <= 0, State.Warn);
			} else {
				checks.add(
					"Sí aparece mensaje de error el el campo con datos incorrectos: <b>" + dataInputString + "</b>",
					pageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType) > 0, State.Warn);
			}
		}
		return checks;
	}

	public enum ErrorRegister {
		None,
		InputWarnings,
		UsrExistsInMango,
		UsrNoExistsInGmail
	}
	
	public void clickRegistrateButton(Map<String,String> dataRegistro) {
		clickRegistrateButton(dataRegistro, ErrorRegister.None);
	}
	
	@Step (
		description="Seleccionar el botón <b>Regístrate</b>")
	public void clickRegistrateButton(Map<String,String> dataRegistro, ErrorRegister errorExpected) {
		pageRegistroIni.clickButtonRegistrate();
		PageBase.waitMillis(1000);
		validaIsInvisibleCapaLoading(15);
		
		switch (errorExpected) {
		case None:
			new PageRegistroSegundaStepsOutlet().validaIsPageRegistroOK(dataRegistro);
			break;
		case InputWarnings:
			int numInputsObligatoriosNoInf = pageRegistroIni.getNumInputsObligatoriosNoInformados();
			if (numInputsObligatoriosNoInf > 0) {
				validateAreInputsWithErrorMessageAssociated(numInputsObligatoriosNoInf);  
			}
			break;
		case UsrExistsInMango:
			validaEmailYaRegistradoShown(5);
			break;
		case UsrNoExistsInGmail:
			validaEmailIncorrectShown(5);
			break;
		}
		GenericChecks.checkDefault(driver);
	}
	
	@Validation (
		description="Desaparece la capa de loading (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Warn)
	public boolean validaIsInvisibleCapaLoading(int maxSeconds) {
		return (pageRegistroIni.isCapaLoadingInvisibleUntil(maxSeconds));
	}
	
	@Validation (
		description="Aparece un error <b>Email ya registrado</b> (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean validaEmailYaRegistradoShown(int maxSeconds) {
		return(pageRegistroIni.isVisibleErrorUsrDuplicadoUntil(maxSeconds));
	}
	
	@Validation (
		description="Aparece un error <b>Email incorrecto</b> (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean validaEmailIncorrectShown(int maxSeconds) {
		return(pageRegistroIni.isVisibleErrorEmailIncorrecto(maxSeconds));
	}
	
	@Validation
	public ChecksTM validateAreInputsWithErrorMessageAssociated(int numInputsObligatoriosNoInf) {
		ChecksTM checks = ChecksTM.getNew();
		int numInputsTypePassrod = pageRegistroIni.getNumberInputsTypePassword();
		int numErrCampObligatorio = pageRegistroIni.getNumberMsgCampoObligatorio();
		
		checks.add(
			"Aparecen " + numInputsObligatoriosNoInf + " errores de campo obligatorio",
			(numInputsObligatoriosNoInf + numInputsTypePassrod) >= numErrCampObligatorio, State.Warn);
		if (pageRegistroIni.isVisibleSelectPais()) {
			checks.add(
				"Existe desplegable país -> aparece seleccionado el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")",
				pageRegistroIni.isSelectedOptionPais(pais.getCodigo_pais()), State.Warn);
		}
		
		return checks;
	}
	
	@Validation (
		description=
		"<b style=\"color:blue\">Rebajas</b></br>" +
		"1) El mensaje de NewsLetter no aparece o si aparece no contiene el símbolo de porcentaje",
		level=State.Info,
		store=StoreType.None)
	public boolean validaRebajasJun2018() {
		String percentageSymbol = UtilsTest.getPercentageSymbol(dataTest.idioma);
		return (!pageRegistroIni.newsLetterTitleContains(percentageSymbol));	   
	}

	public void validaIsRGPDVisible() {
		if (pais.getRgpd().equals("S")) {
			validateRGPD_inCountryWithRgpd(pais.getCodigo_alf());
		} else {
			validateRGPD_inCountryWithoutRgpd(pais.getCodigo_pais());
		}
	}  
	
	@Validation
	public ChecksTM validateRGPD_inCountryWithRgpd(String codigoPais) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 1;
		checks.add(
			"El texto de info de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + codigoPais,
			pageRegistroIni.isTextoRGPDVisible(), State.Defect);
		checks.add(
			"El texto legal de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + codigoPais,
			pageRegistroIni.isTextoLegalRGPDVisible(), State.Defect);
		checks.add(
			"<b>SI</b> está presente el checkbox para recibir promociones e información personalizada para el pais" +
			codigoPais + " (lo esperamos hasta " + maxSeconds + " segundos)",
			pageRegistroIni.isCheckboxRecibirInfoPresentUntil(maxSeconds), State.Defect);
		return checks;
	}
	
	@Validation
	public ChecksTM validateRGPD_inCountryWithoutRgpd(String codigoPais) {
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 1;
		checks.add(
			"El texto de info de RGPD <b>NO</b> aparece en la pantalla de inicio de registro para el pais " + codigoPais,
			!pageRegistroIni.isTextoRGPDVisible(), State.Defect);
		checks.add(
			"El texto legal de RGPD <b>NO</b> aparece en la pantalla de inicio de registro para el pais " + codigoPais,
			!pageRegistroIni.isTextoLegalRGPDVisible(), State.Defect);
		checks.add(
			"<b>NO</b> es visible el checkbox para recibir promociones e información personalizada para el pais " + 
			codigoPais + " (lo esperamos hasta " + maxSeconds + " segundos)",
			!pageRegistroIni.isCheckboxRecibirInfoPresentUntil(maxSeconds), State.Defect);
		return checks;
	}
}
