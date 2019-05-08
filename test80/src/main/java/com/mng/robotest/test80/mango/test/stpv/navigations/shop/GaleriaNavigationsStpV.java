package com.mng.robotest.test80.mango.test.stpv.navigations.shop;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.ModalArticleNotAvailableStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;

public class GaleriaNavigationsStpV {

	public static DataBag selectArticleAvailableFromGaleria(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
	    DataBag dataBag = new DataBag();
	    int posArticulo=1;
	    boolean articleAvailable = false;
    	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
	    while (!articleAvailable && posArticulo<5) {
	    	pageGaleriaStpV.selectLinkAddArticuloToBag(posArticulo);
	    	try {
	    		articleAvailable = pageGaleriaStpV.selectTallaArticulo(posArticulo, 1/*posTalla*/, dataBag, dCtxSh);
	    	}
	    	catch (Exception e) {
	    		//
	    	}
	        if (!articleAvailable) {
	            ModalArticleNotAvailableStpV modalArticleNotAvailableStpV = ModalArticleNotAvailableStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
	            modalArticleNotAvailableStpV.clickAspaForClose(driver);
	            posArticulo+=1;
	        }
	    }
	    
	    return dataBag;
	}
}
