package com.mng.robotest.test80.mango.conftestmaker;

import java.lang.reflect.Method;
import org.testng.ITestContext;

import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.utils.conf.StorerErrorDataStepValidation;
import com.mng.testmaker.utils.webdriver.maker.FactoryWebdriverMaker.TypeWebDriver;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;

public class Utils {

    public static void storeDataShopForTestMaker(TypeWebDriver typeWebDriver, String datosFactoria, DataCtxShop dCtxSh, 
    											 ITestContext context, Method method) throws Exception {
	    TestCaseData.storeData(Constantes.idCtxSh, dCtxSh.clone());
	    StorerErrorDataStepValidation storerDataError = new StorerErrorDataStepValidationMango();
	    TestCaseData.getAndStoreDataFmwk(typeWebDriver, dCtxSh.urlAcceso, datosFactoria, dCtxSh.channel, storerDataError, context, method);
    }
    
    public static void storeDataMantoForTestMaker(TypeWebDriver typeWebDriver, String datosFactoria, DataMantoAccess dMantoAcc, 
    											  ITestContext context, Method method) throws Exception {
	    StorerErrorDataStepValidation storerDataError = new StorerErrorDataStepValidationMango();
	    TestCaseData.getAndStoreDataFmwk(typeWebDriver, dMantoAcc.urlManto, datosFactoria, dMantoAcc.channel, storerDataError, context, method);
    }
}
