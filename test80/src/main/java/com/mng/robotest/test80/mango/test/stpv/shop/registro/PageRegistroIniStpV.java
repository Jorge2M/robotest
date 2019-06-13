package com.mng.robotest.test80.mango.test.stpv.shop.registro;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
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
    
	@Validation (
		description="Aparece la página inicial del proceso de registro (la esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
    public static boolean validaIsPageUntil(int maxSecondsWait, WebDriver driver) {
        return (PageRegistroIni.isPageUntil(maxSecondsWait, driver));
    }
    
	@Step (
		description="Introducir los datos correctos para el país #{pais.getNombre_pais} (Si aparece, seleccionar link de publicidad: <b>#{lickPubli}</b>)", 
        expected="No aparece ningún mensaje de dato incorrecto")
    public static HashMap<String,String> sendDataAccordingCountryToInputs(
    		Pais pais, String emailNonExistent, boolean clickPubli, Channel channel, DataFmwkTest dFTest) throws Exception {
        HashMap<String,String> dataSended = new HashMap<>();
        dataSended = PageRegistroIni.sendDataAccordingCountryToInputs(pais, emailNonExistent, clickPubli, channel, dFTest.driver);
        validateNotAreErrorMessageInCorrectFields(dFTest.driver);
        return dataSended;
    }
	
	@Validation (
		description="No aparece mensaje de error en los campos con datos correctos",
		level=State.Warn)
	public static boolean validateNotAreErrorMessageInCorrectFields(WebDriver driver) {
		return (!PageRegistroIni.isVisibleAnyInputErrorMessage(driver));
	}
    
	@SuppressWarnings("unused")
	@Step (
		description="Introducir los datos:<br>#{dataToSendInHtmlFormat}",
        expected="En los datos incorrectos aparece error y en los correctos no")
    public static void sendFixedDataToInputs(ListDataRegistro dataToSend, String dataToSendInHtmlFormat, DataFmwkTest dFTest) {
        PageRegistroIni.sendDataToInputs(dataToSend, dFTest.driver);       
            
        //Validaciones
        validateMessagesErrorDependingInputs(dataToSend, dFTest.driver);
    }
	
	@Validation
    public static ChecksResult validateMessagesErrorDependingInputs(ListDataRegistro dataToSend, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
        	String dataInputString = dataInput.getDataRegType() + " (<b>" + dataInput.getData() + "</b>)";
            if (dataInput.isValidPrevRegistro()) {
            	validations.add(
            		"No aparece mensaje de error el el campo con datos correctos: <b>" + dataInputString + "</b>",
            		PageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType, driver) <= 0, State.Warn);
            } else {
            	validations.add(
            		"Sí aparece mensaje de error el el campo con datos incorrectos: <b>" + dataInputString + "</b>",
            		PageRegistroIni.getNumberMsgInputInvalid(dataInput.dataRegType, driver) > 0, State.Warn);
	        }
        }
        
        return validations;
    }
    
	@Step (
		description="Seleccionar el botón <b>Regístrate</b>")
    public static void clickRegistrateButton(Pais paisRegistro, boolean usrExists, AppEcom app, HashMap<String,String> dataRegistro, DataFmwkTest dFTest) 
    throws Exception {
        PageRegistroIni.clickButtonRegistrate(dFTest.driver);
        Thread.sleep(1000);

        //Validaciones
        int maxSecondsWait = 3;
        validaIsInvisibleCapaLoading(maxSecondsWait, dFTest);
        if (usrExists || PageRegistroIni.getNumInputsObligatoriosNoInformados(dFTest.driver) > 0) {
        	if (usrExists) {
	        	maxSecondsWait=5;
	        	validaEmailYaRegistradoShown(maxSecondsWait, dFTest.driver);
        	}
            int numInputsObligatoriosNoInf = PageRegistroIni.getNumInputsObligatoriosNoInformados(dFTest.driver);
            if (numInputsObligatoriosNoInf > 0) {
            	validateAreInputsWithErrorMessageAssociated(numInputsObligatoriosNoInf, paisRegistro, dFTest.driver);  
            }
        } else {
            PageRegistroSegundaStpV.validaIsPageRegistroOK(paisRegistro, app, dataRegistro, dFTest.driver);
        }
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, dFTest.driver);
    }
    
	@Validation (
		description="Desaparece la capa de loading (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Warn)
	public static boolean validaIsInvisibleCapaLoading(int maxSecondsWait, DataFmwkTest dFTest) {
		return (PageRegistroIni.isCapaLoadingInvisibleUntil(maxSecondsWait, dFTest.driver));
	}
	
	@Validation (
		description="Aparece un error <b>Email ya registrado<\b> (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
    private static boolean validaEmailYaRegistradoShown(int maxSecondsWait, WebDriver driver) {
		return(PageRegistroIni.isVisibleErrorUsrDuplicadoUntil(driver, maxSecondsWait));
    }
	
	@Validation
	public static ChecksResult validateAreInputsWithErrorMessageAssociated(int numInputsObligatoriosNoInf, Pais pais, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
        int numInputsTypePassrod = PageRegistroIni.getNumberInputsTypePassword(driver);
        int numErrCampObligatorio = PageRegistroIni.getNumberMsgCampoObligatorio(driver);
        
    	validations.add(
    		"Aparecen " + numInputsObligatoriosNoInf + " errores de campo obligatorio",
    		(numInputsObligatoriosNoInf + numInputsTypePassrod) >= numErrCampObligatorio, State.Warn);
    	if (PageRegistroIni.isVisibleSelectPais(driver)) {
	    	validations.add(
	    		"Existe desplegable país -> aparece seleccionado el país con código " + pais.getCodigo_pais() + " (" + pais.getNombre_pais() + ")",
	    		PageRegistroIni.isSelectedOptionPais(driver, pais.getCodigo_pais()), State.Warn);
    	}
    	
    	return validations;
    }
    
	@Validation (
		description=
        "<b style=\"color:blue\">Rebajas</b></br>" +
        "1) El mensaje de NewsLetter no aparece o si aparece no contiene el símbolo de porcentaje",
        level=State.Info,
        avoidEvidences=true)
    public static boolean validaRebajasJun2018(IdiomaPais idioma, DataFmwkTest dFTest) {
        String percentageSymbol = UtilsTestMango.getPercentageSymbol(idioma);
		return (!PageRegistroIni.newsLetterTitleContains(percentageSymbol, dFTest.driver));       
    }

	public static void validaIsRGPDVisible(DataCtxShop dCtxSh, WebDriver driver) {
		if (dCtxSh.pais.getRgpd().equals("S")) {
			validateRGPD_inCountryWithRgpd(dCtxSh, driver);
		} else {
			validateRGPD_inCountryWithoutRgpd(dCtxSh, driver);
		}
	}  
	
	@Validation
	public static ChecksResult validateRGPD_inCountryWithRgpd(DataCtxShop dCtxSh, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSeconds = 1;
    	validations.add(
    		"El texto de info de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais(),
    		PageRegistroIni.isTextoRGPDVisible(driver), State.Defect);
    	validations.add(
    		"El texto legal de RGPD <b>SI</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais(),
    		PageRegistroIni.isTextoLegalRGPDVisible(driver), State.Defect);
    	validations.add(
    		"<b>SI</b> está presente el checkbox para recibir promociones e información personalizada para el pais" +
    		dCtxSh.pais.getCodigo_pais() + " (lo esperamos hasta " + maxSeconds + " segundos)",
    		PageRegistroIni.isCheckboxRecibirInfoPresentUntil(maxSeconds, driver), State.Defect);
    	return validations;
	}
	
	@Validation
	public static ChecksResult validateRGPD_inCountryWithoutRgpd(DataCtxShop dCtxSh, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
		int maxSeconds = 1;
    	validations.add(
    		"El texto de info de RGPD <b>NO</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais(),
    		!PageRegistroIni.isTextoRGPDVisible(driver), State.Defect);
    	validations.add(
    		"El texto legal de RGPD <b>NO</b> aparece en la pantalla de inicio de registro para el pais " + dCtxSh.pais.getCodigo_pais(),
    		!PageRegistroIni.isTextoLegalRGPDVisible(driver), State.Defect);
    	validations.add(
    		"<b>NO</b> es visible el checkbox para recibir promociones e información personalizada para el pais " + 
    		dCtxSh.pais.getCodigo_pais() + " (lo esperamos hasta " + maxSeconds + " segundos)",
    		!PageRegistroIni.isCheckboxRecibirInfoPresentUntil(maxSeconds, driver), State.Defect);
    	return validations;
	}
}
