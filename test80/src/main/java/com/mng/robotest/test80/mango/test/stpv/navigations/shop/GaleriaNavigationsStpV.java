package com.mng.robotest.test80.mango.test.stpv.navigations.shop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.ModalArticleNotAvailableStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.github.jorge2m.testmaker.conf.Log4jConfig;

public class GaleriaNavigationsStpV {

    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
	
	public static DataBag selectArticleAvailableFromGaleria(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
	    DataBag dataBag = new DataBag();
	    int posArticulo=1;
	    boolean articleAvailable = false;
    	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
	    while (!articleAvailable && posArticulo<5) {
	    	pageGaleriaStpV.shopTallasArticulo(posArticulo);
	    	int tallaToSelect = 1;
	    	try {
	    		articleAvailable = pageGaleriaStpV.selectTallaArticulo(posArticulo, tallaToSelect, dataBag, dCtxSh);
	    	}
	    	catch (Exception e) {
	    		pLogger.warn("Problem selecting talla " + tallaToSelect + " from article " + posArticulo, e);
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
