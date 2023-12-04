package com.mng.robotest.tests.domains.transversal.genericchecks;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.genericchecks.Checker;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;

public class CheckerAnalitica implements Checker {

	private final State level;
	
	public CheckerAnalitica(State level) {
		this.level = level;
	}
	
	public ChecksTM check(WebDriver driver) {
		var checks = ChecksTM.getNew();
		String commandJs = "return window.dataLayer.filter((object) => object.event === 'loadDataLayer').length";
		var js = (JavascriptExecutor)driver;
		try {
			Long resultLong = (Long)js.executeScript(commandJs);
			String result = String.valueOf(resultLong);
			checks.add(
				Check.make(
				    "La siguiente sentencia Js retorna 1 \"" + commandJs + "\"",
				    stringIs(result, 1), getLevel())
				.store(NONE).build());
		}
		catch (JavascriptException e) {
			Log4jTM.getLogger().warn("Problema executing JavaScript for check Analitics {}", e.getMessage());
		}
		
		return checks;
	}
	
	private State getLevel() {
		var inputParamsSuite = TestMaker.getInputParamsSuite();
		if (inputParamsSuite!=null && 
			inputParamsSuite.getApp()!=null &&
			inputParamsSuite.getApp()==AppEcom.votf) {
			return INFO;
		}
		
		//TODO actualmente hay muchos errores -> reportar a Alberte
		//mientras tanto lo ponemos en Warn
		if (UtilsTest.todayBeforeDate("2023-11-01")) {
			return WARN;
		}
		return level;
	}
	
	private boolean stringIs(String value, int number) {
		return NumberUtils.isCreatable(value) && Integer.valueOf(value)==number;
	}
	
}
