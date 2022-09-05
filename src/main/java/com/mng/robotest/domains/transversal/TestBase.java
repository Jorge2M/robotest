package com.mng.robotest.domains.transversal;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataTest;
import com.mng.robotest.test.data.PaisShop;


public abstract class TestBase {

	public abstract void execute() throws Exception;
	
	protected final WebDriver driver;
	protected final Channel channel;
	protected final AppEcom app;
	protected final DataTest dataTest;
	protected final InputParamsMango inputParamsSuite;
	
	public static ThreadLocal<DataTest> DATA_TEST = new ThreadLocal<>();
	
	protected TestBase() {
		this.driver = TestMaker.getDriverTestCase();
		this.inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		this.app = (AppEcom)inputParamsSuite.getApp();
		this.channel = inputParamsSuite.getChannel();
		this.dataTest = DataTest.getData(PaisShop.ESPANA);
		DATA_TEST.set(dataTest);
	}
}
