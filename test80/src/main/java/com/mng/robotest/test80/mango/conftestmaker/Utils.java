package com.mng.robotest.test80.mango.conftestmaker;

import java.lang.reflect.Method;
import org.testng.ITestContext;

import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.utils.conf.StorerErrorDataStepValidation;
import com.mng.testmaker.service.webdriver.maker.FactoryWebdriverMaker.WebDriverType;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;

public class Utils {

    public static void storeDataShopForTestMaker(WebDriverType WebDriverType, String datosFactoria, DataCtxShop dCtxSh, 
    											 ITestContext context, Method method) throws Exception {
	    TestCaseData.storeData(Constantes.idCtxSh, dCtxSh.clone());
	    StorerErrorDataStepValidation storerDataError = new StorerErrorDataStepValidationMango();
	    TestCaseData.getAndStoreDataFmwk(WebDriverType, dCtxSh.urlAcceso, datosFactoria, dCtxSh.channel, storerDataError, context, method);
    }
    
    public static void storeDataMantoForTestMaker(WebDriverType WebDriverType, String datosFactoria, DataMantoAccess dMantoAcc) 
    throws Exception {
	    StorerErrorDataStepValidation storerDataError = new StorerErrorDataStepValidationMango();
	    TestCaseData.getAndStoreDataFmwk(WebDriverType, dMantoAcc.urlManto, datosFactoria, dMantoAcc.channel, storerDataError, context, method);
    }
}
