package com.mng.robotest.domains.transversal;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.utils.DataTest;


public abstract class TestBase {

	public abstract void execute() throws Exception;
	
	protected final WebDriver driver;
	protected final Channel channel;
	protected final AppEcom app;
	protected final DataCtxShop dataTest;
	
	public TestBase() throws Exception {
		this.driver = TestMaker.getDriverTestCase();
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		this.app = (AppEcom)inputParamsSuite.getApp();
		this.channel = (Channel)inputParamsSuite.getChannel();
		this.dataTest = DataTest.getData(PaisShop.ESPANA);
	}
}
