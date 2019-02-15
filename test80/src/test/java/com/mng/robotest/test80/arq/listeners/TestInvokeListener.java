package com.mng.robotest.test80.arq.listeners;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.mng.robotest.test80.mango.test.jdbc.dao.ParamsDAO;

@RunWith(PowerMockRunner.class)
@PowerMockIgnore("javax.management.*")
@PrepareForTest(ParamsDAO.class)
public class TestInvokeListener {

    @Test
    public void testNeededPurgeHistoricalData() {
        PowerMockito.mockStatic(ParamsDAO.class);
        PowerMockito.when(ParamsDAO.getParam("PURGED_DATA_FROM")).thenReturn("2018-01-01");
        boolean isNeededPurge = InvokeListener.isNeededPurgeHistoricalData(40/*numDiasToMaintain*/); //Code to test
        assertTrue(isNeededPurge);
    }
    
    @Test
    public void testNoNeededPurgeHistoricalData() {
        PowerMockito.mockStatic(ParamsDAO.class);
        PowerMockito.when(ParamsDAO.getParam("PURGED_DATA_FROM")).thenReturn("2018-01-01");
        boolean isNeededPurge = InvokeListener.isNeededPurgeHistoricalData(9999/*numDiasToMaintain*/); //Code to test
        assertTrue(!isNeededPurge);        
    }
    
    @Test
    public void testNoNeededPurgeWhenNoParam() {
        PowerMockito.mockStatic(ParamsDAO.class);
        PowerMockito.when(ParamsDAO.getParam("PURGED_DATA_FROM")).thenReturn(null);
        boolean isNeededPurge = InvokeListener.isNeededPurgeHistoricalData(60/*numDiasToMaintain*/); //Code to test
        assertTrue(!isNeededPurge);        
    }    
    
    @Test
    public void testNoNeededPurgeWhenParamNotFormatDate() {
        PowerMockito.mockStatic(ParamsDAO.class);
        PowerMockito.when(ParamsDAO.getParam("PURGED_DATA_FROM")).thenReturn("291122");
        boolean isNeededPurge = InvokeListener.isNeededPurgeHistoricalData(60/*numDiasToMaintain*/); //Code to test
        assertTrue(!isNeededPurge);        
    }
}
