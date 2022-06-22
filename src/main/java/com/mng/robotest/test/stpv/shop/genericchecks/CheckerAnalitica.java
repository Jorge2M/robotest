package com.mng.robotest.test.stpv.shop.genericchecks;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
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
			stringIs(result, 1), getLevel(), StoreType.None);
		
		return validations;
	}
	
	private State getLevel() {
		InputParamsTM inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		if (inputParamsSuite!=null && 
			inputParamsSuite.getApp()!=null &&
			inputParamsSuite.getApp()==AppEcom.votf) {
			return State.Info;
		}
		//TODO actualmente hay muchos errores -> reportar a Alberte
		//mientras tanto lo ponemos en Warning
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateLimit = null;
		try {
			dateLimit = sdf.parse("2022-08-01");
		}
		catch (Exception e) {
			//
		}
		Date dateToday = new Date();
		if (dateToday.before(dateLimit)) {
			return State.Warn;
		}
		return GenericCheck.Analitica.getLevel();
	}
	
	private boolean stringIs(String value, int number) {
		return NumberUtils.isCreatable(value) && Integer.valueOf(value)==number;
	}
	
}
