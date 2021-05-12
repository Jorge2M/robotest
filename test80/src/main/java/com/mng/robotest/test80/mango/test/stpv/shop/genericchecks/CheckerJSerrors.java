package com.mng.robotest.test80.mango.test.stpv.shop.genericchecks;

import java.util.logging.Level;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.maker.FactoryWebdriverMaker.EmbeddedDriver;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SeleniumUtils;
import com.github.jorge2m.testmaker.service.webdriver.utils.WebUtils;
import com.github.jorge2m.testmaker.testreports.html.ResultadoErrores;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class CheckerJSerrors implements Checker {

	public ChecksTM check(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		
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
				resultadoOK || (resultadoLogs.getResultado()==ResultadoErrores.Resultado.MAX_ERRORES), GenericCheck.JSerrors.getLevel(), true);
    	}
    	
    	return validations;
	}
	
}
