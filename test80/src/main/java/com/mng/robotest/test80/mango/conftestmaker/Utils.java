package com.mng.robotest.test80.mango.conftestmaker;

import java.lang.reflect.Method;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.conf.StorerErrorDataStepValidation;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.stpv.manto.DataMantoAccess;

public class Utils {

    public static void storeDataShopForTestMaker(String bpath, String datosFactoria, DataCtxShop dCtxSh, 
    											 ITestContext context, Method method) throws Exception {
	    TestCaseData.storeData(Constantes.idCtxSh, dCtxSh.clone());
	    StorerErrorDataStepValidation storerDataError = new StorerErrorDataStepValidationMango();
	    TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, datosFactoria, dCtxSh.channel, storerDataError, context, method);
    }
    
    public static void storeDataMantoForTestMaker(String bpath, String datosFactoria, DataMantoAccess dMantoAcc, 
    											  ITestContext context, Method method) throws Exception {
	    StorerErrorDataStepValidation storerDataError = new StorerErrorDataStepValidationMango();
	    TestCaseData.getAndStoreDataFmwk(bpath, dMantoAcc.urlManto, datosFactoria, dMantoAcc.channel, storerDataError, context, method);
    }
}
