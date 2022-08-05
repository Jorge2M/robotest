package com.mng.robotest.test.steps.navigations.shop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.steps.shop.AllPagesSteps;
import com.mng.robotest.test.steps.shop.SecCabeceraSteps;
import com.mng.robotest.test.steps.shop.landing.PageLandingSteps;

public class NavigationsSteps {

	public static void gotoPortada(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		SecCabeceraSteps secCabeceraSteps = SecCabeceraSteps.getNew(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
		int i=0;
		while (!secCabeceraSteps.getSecCabecera().isPresentLogoMango(1) && i<5) {
			AllPagesSteps.backNagegador(driver);
			i+=1;
		}
		
		secCabeceraSteps.selecLogo();
		secCabeceraSteps.selecLogo();
		(new PageLandingSteps(driver)).checkIsPage(5);
	}
	
}
