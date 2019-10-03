package com.mng.robotest.test80;

import com.mng.testmaker.listeners.InvokeListener;
import com.mng.robotest.test80.mango.test.jdbc.dao.ProductCacheDAO;

public class PurgeHistoricalDataBD {

    public static void main(String[] args) throws Exception {
    	int numDiasToMaintain = Integer.valueOf(args[0]);
    	InvokeListener.purgeHistoricalDataRobotest(numDiasToMaintain);
        ProductCacheDAO.deleteProductsCaducados();
    }
}
