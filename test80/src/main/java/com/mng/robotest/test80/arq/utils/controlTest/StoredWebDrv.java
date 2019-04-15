package com.mng.robotest.test80.arq.utils.controlTest;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Constantes.TypeDriver;


public class StoredWebDrv {

    public enum stateWd {busy, free}
    
    private TypeDriver typeWdrv = TypeDriver.firefox;
    private String moreDataWdrv = "";
    private WebDriver webdriver;
    private stateWd state = stateWd.free;

    public StoredWebDrv(WebDriver webdriver, stateWd state, TypeDriver typeWdrv, String moreDataWdrv) {
        this.webdriver   = webdriver;
        this.state       = state;
        this.typeWdrv    = typeWdrv;
        this.moreDataWdrv = moreDataWdrv;
    }
    
    public TypeDriver getTypeWdrv() {
        return this.typeWdrv;
    }
    
    public String getMoreDataWdrv() {
        return this.moreDataWdrv;
    }
    
    public void setMoreDataWdrv(String moreDataWdrv) {
        this.moreDataWdrv = moreDataWdrv;
    }
    
    public WebDriver getWebDriver() {
        return this.webdriver;
    }
    
    public void setWebDriver(WebDriver driver) {
        this.webdriver = driver;
    }

    public stateWd getState() {
        return (this.state);
    }
    
    public void setState(stateWd state) {
        this.state = state;
    }
    
    public boolean isFree() {
        if (this.state == stateWd.free) {
            return true;
        }
        return false;
    }
    
    public boolean isBusy() {
        if (this.state == stateWd.busy) {
            return true;
        }
        return false;
    }

    public void markAsBusy() {
        this.setState(stateWd.busy);
    }
    
    public void markAsFree() {
        this.setState(stateWd.free);
    }
}


