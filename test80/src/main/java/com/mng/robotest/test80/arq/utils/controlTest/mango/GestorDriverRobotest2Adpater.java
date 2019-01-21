/**
 * 
 */
package com.mng.robotest.test80.arq.utils.controlTest.mango;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataWebdriver;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;

/** 
 *
 * Contrato con ROBOTEST2
 */
public interface GestorDriverRobotest2Adpater {

    public void setDriver(WebDriver wd);
    
    public DataWebdriver getDriver();
    
    public DataCtxShop getdCtxSh();

    public void setdCtxSh(DataCtxShop dCtxSh);
    
    public void setDCtsShThread(DataCtxShop dCtxSh);
    
    public DataCtxShop getDCtsShThread();
    
    public void clonePerThreadCtx();
    
}
