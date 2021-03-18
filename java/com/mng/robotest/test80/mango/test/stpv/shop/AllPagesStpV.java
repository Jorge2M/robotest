package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.logging.Level;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.service.webdriver.utils.WebUtils;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

public class AllPagesStpV {
    
    public static void validacionesEstandar(StdValidationFlags flagsVal, WebDriver driver) {
    	flagsVal.stateValidaSEO = State.Info;
    	flagsVal.stateValidaJS = State.Info;
    	flagsVal.stateValidaImgBroken = State.Warn;
    	checksStandar(flagsVal, driver);
    }
    
    @Validation
    public static ChecksTM checksStandar(StdValidationFlags flagsVal, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
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
        	if (SeleniumUtils.getTypeDriver(driver)!=EmbeddedDriver.firefox &&
        		SeleniumUtils.getTypeDriver(driver)!=EmbeddedDriver.firefoxhless) {
        		int maxErrors = 1;
        		ResultadoErrores resultadoLogs = WebUtils.getLogErrors(Level.WARNING, driver, maxErrors);
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
    public static ChecksTM validatePageWithFooter(Pais pais, AppEcom app, WebDriver driver) throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece el footer",
			(new SecFooter(app, driver)).isPresent(), State.Warn);
		
		if (pais!=null) {
			validations.add(
				"Aparece el div de contenido asociado al país " + pais.getCodigo_pais(),
				state(Present, By.xpath("//div[@class[contains(.,'main-content')] and @data-pais='" + pais.getCodigo_pais() + "']"), driver).check(),
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
        int maxSeconds = 10;
        SeleniumUtils.waitForPageLoaded(driver, maxSeconds);
    }
}
