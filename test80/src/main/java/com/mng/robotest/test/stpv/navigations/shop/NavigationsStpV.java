package com.mng.robotest.test.stpv.navigations.shop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test.stpv.shop.SecCabeceraStpV;

public class NavigationsStpV {

	public static void gotoPortada(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		SecCabeceraStpV secCabeceraStpV = SecCabeceraStpV.getNew(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
		int i=0;
		while (!secCabeceraStpV.getSecCabecera().isPresentLogoMango(1) && i<5) {
			AllPagesStpV.backNagegador(driver);
			i+=1;
		}
		
		secCabeceraStpV.selecLogo();
	}
	
}
