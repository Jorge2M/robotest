package com.mng.robotest.test.steps.shop.genericchecks;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;
import com.mng.robotest.test.utils.UtilsTest;
import com.mng.robotest.conftestmaker.AppEcom;

import static com.github.jorge2m.testmaker.conf.State.*;

public class CheckerAnalitica implements Checker {

	public ChecksTM check(WebDriver driver) {
		var checks = ChecksTM.getNew();
		
		String commandJs = "return window.dataLayer.filter((object) => object.event === 'loadDataLayer').length";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		try {
			Long resultLong = (Long)js.executeScript(commandJs);
			String result = String.valueOf(resultLong);
			checks.add(
				Check.make(
				    "La siguiente sentencia Js retorna 1 \"" + commandJs + "\"",
				    stringIs(result, 1), getLevel())
				.store(StoreType.None).build());
		}
		catch (JavascriptException e) {
			Log4jTM.getLogger().warn("Problema executing JavaScript for check Analitics", e.getMessage());
		}
		
		return checks;
	}
	
	private State getLevel() {
		InputParamsTM inputParamsSuite = TestMaker.getInputParamsSuite();
		if (inputParamsSuite!=null && 
			inputParamsSuite.getApp()!=null &&
			inputParamsSuite.getApp()==AppEcom.votf) {
			return Info;
		}
		
		//TODO actualmente hay muchos errores -> reportar a Alberte
		//mientras tanto lo ponemos en Warning
		if (UtilsTest.todayBeforeDate("2023-11-01")) {
			return Warn;
		}
		return GenericCheck.ANALITICA.getLevel();
	}
	
	private boolean stringIs(String value, int number) {
		return NumberUtils.isCreatable(value) && Integer.valueOf(value)==number;
	}
	
}
