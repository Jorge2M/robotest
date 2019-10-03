package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.DataRegistro;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.PageRegistroIni;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

public class PageRegistroIniStpV {
	
	private final WebDriver driver;
	private final PageRegistroIni pageRegistroIni;
	
	private PageRegistroIniStpV(WebDriver driver) {
		this.driver = driver;
		this.pageRegistroIni = PageRegistroIni.getNew(driver);
	}
	
	public static PageRegistroIniStpV getNew(WebDriver driver) {
		return new PageRegistroIniStpV(driver);
	}
    
	@Validation (
		description="Aparece la página inicial del proceso de registro (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
    public boolean validaIsPageUntil(int maxSecondsWait) {
        return (pageRegistroIni.isPageUntil(maxSecondsWait));
    }
    
	@Step (
		description=
			"Introducir los datos correctos para el país #{pais.getNombre_pais} " + 
			"(Si aparece, seleccionar link de publicidad: <b>#{lickPubli}</b>)", 
        expected=
			"No aparece ningún mensaje de dato incorrecto")
    public HashMap<String,String> sendDataAccordingCountryToInputs(
    		Pais pais, String emailNonExistent, boolean clickPubli, Channel channel) throws Exception {
        HashMap<String,String> dataSended = new HashMap<>();
        dataSended = pageRegistroIni.sendDataAccordingCountryToInputs(pais, emailNonExistent, clickPubli, channel);
        validateNotAreErrorMessageInCorrectFields();
        return dataSended;
    }
	
	@Validation (
		description="No aparece mensaje de error en los campos con datos correctos",
		level=State.Warn)
	public boolean validateNotAreErrorMessageInCorrectFields() {
		return (!pageRegistroIni.isVisibleAnyInputErrorMessage());
	}
    
	@SuppressWarnings("unused")
	@Step (
		description="Introducir los datos:<br>#{dataToSendInHtmlFormat}",
        expected="En los datos incorrectos aparece error y en los correctos no")
    public void sendFixedDataToInputs(ListDataRegistro dataToSend, String dataToSendInHtmlFormat) {
		pageRegistroIni.sendDataToInputs(dataToSend);       
        validateMessagesErrorDependingInputs(dataToSend);
    }
	
	@Validation
    public ChecksResult validateMessagesErrorDependingInputs(ListDataRegistro dataToSend) {
		ChecksResult validations = ChecksResult.getNew();
        for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
        	String dataInputString = dataInput.getDataRegType() + " (<b>" + dataInput.getData() + "</b>)";
            if (dataInput.isValidPrevRegistro()) {
            	validations.add(
            		"No aparece mensaje de error el el campo con datos correctos: <b>" + dataInputString + "</b>",
            		pageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType) <= 0, State.Warn);
            } else {
            	validations.add(
            		"Sí aparece mensaje de error el el campo con datos incorrectos: <b>" + dataInputString + "</b>",
            		pageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType) > 0, State.Warn);
	        }
        }
        
        return validations;
    }
    
	@Step (
		description="Seleccionar el botón <b>Regístrate</b>")
    public void clickRegistrateButton(Pais paisRegistro, boolean usrExists, AppEcom app, HashMap<String,String> dataRegistro) 
    throws Exception {
		pageRegistroIni.clickButtonRegistrate();
        Thread.sleep(1000);

        //Validaciones
        int maxSecondsWait = 3;
        validaIsInvisibleCapaLoading(maxSecondsWait);
        if (usrExists || pageRegistroIni.getNumInputsObligatoriosNoInformados() > 0) {
        	if (usrExists) {
	        	maxSecondsWait=5;
	        	validaEmailYaRegistradoShown(maxSecondsWait);
        	}
            int numInputsObligatoriosNoInf = pageRegistroIni.getNumInputsObligatoriosNoInformados();
            if (numInputsObligatoriosNoInf > 0) {
            	validateAreInputsWithErrorMessageAssociated(numInputsObligatoriosNoInf, paisRegistro);  
            }
        } else {
            PageRegistroSegundaStpV.validaIsPageRegistroOK(paisRegistro, app, dataRegistro, driver);
        }
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }
    
	@Validation (
		description="Desaparece la capa de loading (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	public boolean validaIsInvisibleCapaLoading(int maxSecondsWait) {
		return (pageRegistroIni.isCapaLoadingInvisibleUntil(maxSecondsWait));
	}
	
	@Validation (
		description="Aparece un error <b>Email ya registrado<\b> (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
    private boolean validaEmailYaRegistradoShown(int maxSecondsWait) {
		return(pageRegistroIni.isVisibleErrorUsrDuplicadoUntil(maxSecondsWait));
    }
	
	@Validation
	public ChecksResult validateAreInputsWithErrorMessageAssociated(int numInputsObligatoriosNoInf, Pais pais) {
		ChecksResult validations = ChecksResult.getNew();
        int numInputsTypePassrod = pageRegistroIni.getNumberInputsTypePassword();
        int numErrCampObligatorio = pageRegistroIni.getNumberMsgCampoObligatorio();
        
    	validations.add(
    		"Aparecen " + numInputsObligatoriosNoInf + " errores de campo obligatorio",
    		(numInputsObligatoriosNoInf + numInputsTypePassrod) >= numErrCampObligatorio, State.Warn);
    	if (pageRegistroIni.isVisibleSelectPais()) {
	    	validations.add(
	    		"Existe desplegable país -> aparece seleccionado el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")",
	    		pageRegistroIni.isSelectedOptionPais(pais.getCodigo_pais()), State.Warn);
    	}
    	
    	return validations;
    }
    
	@Validation (
		description=
        "<b style=\"color:blue\">Rebajas</b></br>" +
        "1) El mensaje de NewsLetter no aparece o si aparece no contiene el símbolo de porcentaje",
        level=State.Info,
        avoidEvidences=true)
    public boolean validaRebajasJun2018(IdiomaPais idioma) {
        String percentageSymbol = UtilsTestMango.getPercentageSymbol(idioma);
		return (!pageRegistroIni.newsLetterTitleContains(percentageSymbol));       
    }

	public void validaIsRGPDVisible(DataCtxShop dCtxSh) {
		if (dCtxSh.pais.getRgpd().equals("S")) {
			validateRGPD_inCountryWithRgpd(dCtxSh);
		} else {
			validateRGPD_inCountryWithoutRgpd(dCtxSh);
		}
	}  
	
	@Validation
	public ChecksResult validateRGPD_inCountryWithRgpd(DataCtxShop dCtxSh) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSeconds = 1;
    	validations.add(
    		"El texto de info de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais(),
    		pageRegistroIni.isTextoRGPDVisible(), State.Defect);
    	validations.add(
    		"El texto legal de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais(),
    		pageRegistroIni.isTextoLegalRGPDVisible(), State.Defect);
    	validations.add(
    		"<b>SI</b> está presente el checkbox para recibir promociones e información personalizada para el pais" +
    		dCtxSh.pais.getCodigo_pais() + " (lo esperamos hasta " + maxSeconds + " segundos)",
    		pageRegistroIni.isCheckboxRecibirInfoPresentUntil(maxSeconds), State.Defect);
    	return validations;
	}
	
	@Validation
	public ChecksResult validateRGPD_inCountryWithoutRgpd(DataCtxShop dCtxSh) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSeconds = 1;
    	validations.add(
    		"El texto de info de RGPD <b>NO</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais(),
    		!pageRegistroIni.isTextoRGPDVisible(), State.Defect);
    	validations.add(
    		"El texto legal de RGPD <b>NO</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais(),
    		!pageRegistroIni.isTextoLegalRGPDVisible(), State.Defect);
    	validations.add(
    		"<b>NO</b> es visible el checkbox para recibir promociones e información personalizada para el pais " + 
    		dCtxSh.pais.getCodigo_pais() + " (lo esperamos hasta " + maxSeconds + " segundos)",
    		!pageRegistroIni.isCheckboxRecibirInfoPresentUntil(maxSeconds), State.Defect);
    	return validations;
	}
}
