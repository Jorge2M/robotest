package com.mng.robotest.test80;

import com.mng.robotest.test80.arq.listeners.InvokeListener;

public class PurgeHistoricalDataBD {

    public static void main(String[] args) throws Exception {
    	int numDiasToMaintain = Integer.valueOf(args[0]);
    	InvokeListener.purgeHistoricalDataIfNeeded(numDiasToMaintain);
    }
}
