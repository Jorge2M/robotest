package com.mng.robotest.domains.transversal;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;


public abstract class StepBase {

	protected final WebDriver driver;
	protected final Channel channel;
	protected final AppEcom app;
	protected final InputParamsMango inputParamsSuite;
	
	protected StepBase() {
		this.driver = TestMaker.getDriverTestCase();
		this.inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		this.app = (AppEcom)inputParamsSuite.getApp();
		this.channel = inputParamsSuite.getChannel();
	}
}
