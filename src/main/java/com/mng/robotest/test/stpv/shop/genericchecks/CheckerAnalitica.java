package com.mng.robotest.test.stpv.shop.genericchecks;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class CheckerAnalitica implements Checker {

	public ChecksTM check(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		
		String commandJs = "return window.dataLayer.filter((object) => object.event === 'loadDataLayer').length";
		JavascriptExecutor js = (JavascriptExecutor)driver;
		Long resultLong = (Long)js.executeScript(commandJs);
		String result = String.valueOf(resultLong);
		validations.add(
			"La siguiente sentencia Js retorna 1 \"" + commandJs + "\"",
			stringIs(result, 1), getLevel(), true);
		
		return validations;
	}
	
	private State getLevel() {
		InputParamsTM inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		if (inputParamsSuite!=null && 
			inputParamsSuite.getApp()!=null &&
			inputParamsSuite.getApp()==AppEcom.votf) {
			return State.Info;
		}
		return GenericCheck.Analitica.getLevel();
	}
	
	private boolean stringIs(String value, int number) {
		return NumberUtils.isCreatable(value) && Integer.valueOf(value)==number;
	}
	
}
