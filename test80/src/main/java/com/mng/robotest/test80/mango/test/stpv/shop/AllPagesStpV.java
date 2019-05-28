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
import com.mng.robotest.test80.arq.utils.otras.ResultadoErrores;
import com.mng.robotest.test80.arq.utils.otras.WebDriverArqUtils;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

public class AllPagesStpV {
    
    public static void validacionesEstandar(StdValidationFlags flagsVal, WebDriver driver) 
    throws Exception {
    	ITestContext ctx = TestCaseData.getdFTest().ctx;
    	flagsVal.stateValidaSEO = State.Info;
    	flagsVal.stateValidaJS = State.Info;
    	flagsVal.stateValidaImgBroken = State.Warn;
        validacionesEstandar(flagsVal, driver, ctx);
    }
    
    @Validation
    public static ChecksResult validacionesEstandar(StdValidationFlags flagsVal, WebDriver driver, ITestContext ctx) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	if (flagsVal.validaSEO) {
    		ResultadoErrores resValidac = AllPagesSEO.validacionesGenericasSEO(driver);
    		String descripValidac = "Se cumplen las validaciones genéricas de SEO";
    		if (!resValidac.isOK()) {
    			descripValidac += resValidac.getlistaLogError().toString();
    		}
			validations.add(
				descripValidac,
				resValidac.isOK(), flagsVal.stateValidaSEO, true);
    	}
    	
    	if (flagsVal.validaJS) {
        	//Nota: No funciona con GeckoDriver porque no están implementados los servicios al no formar parte del protocolo W3C https://github.com/w3c/webdriver/issues/406
        	if (WebdrvWrapp.getTypeDriver(driver)!=TypeWebDriver.firefox &&
        		WebdrvWrapp.getTypeDriver(driver)!=TypeWebDriver.firefoxhless) {
        		int maxErrors = 1;
        		ResultadoErrores resultadoLogs = WebDriverArqUtils.getLogErrors(Level.WARNING, driver, maxErrors, ctx);
        		String descripValidac = "No hay errores JavaScript";
        		boolean resultadoOK = resultadoLogs.getResultado() == ResultadoErrores.Resultado.OK;
                if (!resultadoOK) { 
                	descripValidac += resultadoLogs.getlistaLogError().toString();
                }
                
                //Sólo mostraremos warning en caso que alguno no se haya mostrado ya un máximo de veces durante el test
    			validations.add(
					descripValidac,
					resultadoOK || (resultadoLogs.getResultado()==ResultadoErrores.Resultado.MAX_ERRORES), flagsVal.stateValidaJS, true);
        	}
    	}
    	
    	if (flagsVal.validaImgBroken) {
            int maxErrors = 1;
            ResultadoErrores resultadoImgs = WebDriverMngUtils.imagesBroken(driver, Channel.desktop, maxErrors);
            boolean resultadoOK = (resultadoImgs.getResultado() == ResultadoErrores.Resultado.OK);
    		String descripValidac = "No hay ninguna imagen cortada";
            if (!resultadoOK) {
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
			"Aparece el footer",
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
