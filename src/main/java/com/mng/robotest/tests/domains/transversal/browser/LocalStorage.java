package com.mng.robotest.tests.domains.transversal.browser;

import org.openqa.selenium.JavascriptExecutor;

import com.mng.robotest.tests.domains.base.PageBase;

public class LocalStorage extends PageBase {
	
    private JavascriptExecutor js = (JavascriptExecutor)driver;

    protected void removeItemFromLocalStorage(String item) {
	    js.executeScript(String.format(
		    "window.localStorage.removeItem('%s');", item));
    }

    protected boolean isItemPresentInLocalStorage(String item) {
	    return (js.executeScript(String.format(
		    "return window.localStorage.getItem('%s');", item)) != null);
    }

    protected String getItemFromLocalStorage(String key) {
	    return (String) js.executeScript(String.format(
		    "return window.localStorage.getItem('%s');", key));
    }

    protected String getKeyFromLocalStorage(int key) {
	    return (String) js.executeScript(String.format(
		    "return window.localStorage.key('%s');", key));
    }

    protected Long getLocalStorageLength() {
	    return (Long) js.executeScript("return window.localStorage.length;");
    }

    protected void setItemInLocalStorage(String item, String value) {
	    js.executeScript(String.format(
		    "window.localStorage.setItem('%s','%s');", item, value));
    }

    protected void clearLocalStorage() {
	    js.executeScript(String.format("window.localStorage.clear();"));
    }
    
}
