package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.logging.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.otras.WebDriverArqUtils;
import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.ResultadoErrores;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

public class AllPagesStpV {
    
    public static void validacionesEstandar(StdValidationFlags flagsVal, WebDriver driver) 
    throws Exception {
    	ITestContext ctx = TestCaseData.getdFTest().ctx;
    	flagsVal.stateValidaSEO = State.Info_NoHardcopy;
    	flagsVal.stateValidaJS = State.Info_NoHardcopy;
    	flagsVal.stateValidaImgBroken = State.Warn;
        validacionesEstandar(flagsVal, driver, ctx);
    }
    
    @Validation
    public static ChecksResult validacionesEstandar(StdValidationFlags flagsVal, WebDriver driver, ITestContext ctx) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	if (flagsVal.validaSEO) {
    		ResultadoErrores resValidac = AllPagesSEO.validacionesGenericasSEO(driver);
    		String descripValidac = "Se cumplen las validaciones genéricas de SEO<br>";
    		if (!resValidac.isOK()) {
    			descripValidac += resValidac.getlistaLogError().toString() + "<br>";
    		}
			validations.add(
				descripValidac,
				resValidac.isOK(), flagsVal.stateValidaSEO);
    	}
    	
    	if (flagsVal.validaJS) {
        	//Nota: No funciona con GeckoDriver porque no están implementados los servicios al no formar parte del protocolo W3C https://github.com/w3c/webdriver/issues/406
        	if (WebdrvWrapp.getTypeDriver(driver)!=TypeDriver.firefox &&
        		WebdrvWrapp.getTypeDriver(driver)!=TypeDriver.firefoxhless) {
        		int maxErrors = 1;
        		ResultadoErrores resultadoLogs = WebDriverArqUtils.getLogErrors(Level.WARNING, driver, maxErrors, ctx);
        		String descripValidac = "No hay errores JavaScript<br>";
        		boolean resultadoOK = resultadoLogs.getResultado() == ResultadoErrores.Resultado.OK;
                if (!resultadoOK) { 
                	descripValidac += resultadoLogs.getlistaLogError().toString()  + "<br>";
                }
                
                //Sólo mostraremos warning en caso que alguno no se haya mostrado ya un máximo de veces durante el test
    			validations.add(
					descripValidac,
					resultadoOK || (resultadoLogs.getResultado()==ResultadoErrores.Resultado.MAX_ERRORES), flagsVal.stateValidaJS);
        	}
    	}
    	
    	if (flagsVal.validaImgBroken) {
            int maxErrors = 1;
            ResultadoErrores resultadoImgs = WebDriverMngUtils.imagesBroken(driver, Channel.desktop, maxErrors);
            boolean resultadoOK = (resultadoImgs.getResultado() == ResultadoErrores.Resultado.OK);
    		String descripValidac = "No hay ninguna imagen cortada<br>";
            if (resultadoOK) {
            	descripValidac+=resultadoImgs.getlistaLogError().toString();
            }
            
         	//Sólo mostraremos warning en caso que alguno no se haya mostrado ya un máximo de veces durante el test
			validations.add(
				descripValidac,
				resultadoOK || (resultadoImgs.getResultado()==ResultadoErrores.Resultado.MAX_ERRORES), flagsVal.stateValidaImgBroken);
    	}
    	
    	return validations;
    }
    
    @Validation
    public static ChecksResult validatePageWithFooter(Pais pais, AppEcom app, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
		validations.add(
			"Aparece el footer<br>",
			SecFooter.isPresent(app, driver), State.Warn);
		
		if (pais!=null) {
			validations.add(
				"Aparece el div de contenido asociado al país " + pais.getCodigo_pais(),
				WebdrvWrapp.isElementPresent(driver, By.xpath("//div[@class[contains(.,'main-content')] and @data-pais='" + pais.getCodigo_pais() + "']")), 
				State.Warn);
		}
		return validations;
    }
    
    @Validation (
    	description="Aparece el div de contenido asociado al país #{pais.getNombre_pais()} (#{pais.getCodigo_pais()})",
    	level=State.Warn)
    public static boolean validateMainContentPais(Pais pais, WebDriver driver) {
        return (AllPages.isPresentMainContent(pais, driver));
    }

    @Step (
    	description="Realizamos un <b>back</b> del navegador", 
        expected="Se vuelve a la página anterior")
    public static void backNagegador(WebDriver driver) throws Exception {
        driver.navigate().back();
        int maxSecondsWait = 10;
        WebdrvWrapp.waitForPageLoaded(driver, maxSecondsWait);
    }
}
