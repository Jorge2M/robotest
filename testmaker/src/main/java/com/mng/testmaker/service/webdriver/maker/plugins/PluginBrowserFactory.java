package com.mng.testmaker.service.webdriver.maker.plugins; 

import com.mng.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome;
import com.mng.testmaker.service.webdriver.maker.plugins.chrome.PluginHTML5Autoplay;
import com.mng.testmaker.service.webdriver.maker.plugins.chrome.PluginChrome.typePluginChrome;
import com.mng.testmaker.service.webdriver.maker.plugins.firefox.PluginFirefox;
import com.mng.testmaker.service.webdriver.maker.plugins.firefox.PluginFirefox.typePluginFirefox;


public class PluginBrowserFactory {
    
    public static PluginChrome makePluginChrome(typePluginChrome typePlugin) {
        PluginChrome pluginMaked = null; 
        switch (typePlugin) {
        case HTML5Autoplay:
            pluginMaked = new PluginHTML5Autoplay();
            break;
        default:
            break;
        }
        
        return pluginMaked;
    }
    
    @SuppressWarnings("unused")
	public static PluginFirefox makePluginFirefox(typePluginFirefox typePlugin) {
        PluginFirefox extensionMaked = null; 
        //switch (typePlugin) {
        //case HARExportTrigger:
            //extensionMaked = new PluginHARExportTrigger();
        //    break;
        //default:
        //    break;
        //}
        
        return extensionMaked;
    }    
}
