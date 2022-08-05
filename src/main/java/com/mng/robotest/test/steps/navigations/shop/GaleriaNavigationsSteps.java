package com.mng.robotest.test.steps.navigations.shop;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

public class GaleriaNavigationsSteps {
	
	public static DataBag selectArticleAvailableFromGaleria(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		DataBag dataBag = new DataBag();
		int posArticulo=1;
		boolean articleAvailable = false;
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		while (!articleAvailable && posArticulo<5) {
			pageGaleriaSteps.shopTallasArticulo(posArticulo);
			int tallaToSelect = 1;
			try {
				articleAvailable = pageGaleriaSteps.selectTallaAvailableArticulo(posArticulo, tallaToSelect, dataBag, dCtxSh);
			}
			catch (Exception e) {
				Log4jTM.getLogger().warn("Problem selecting talla " + tallaToSelect + " from article " + posArticulo, e);
			}
			if (!articleAvailable) {
//				ModalArticleNotAvailableSteps modalArticleNotAvailableSteps = ModalArticleNotAvailableSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
//				modalArticleNotAvailableSteps.clickAspaForClose(driver);
				posArticulo+=1;
			}
		}
		
		return dataBag;
	}
}
