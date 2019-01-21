package com.mng.robotest.test80.arq.utils.selenium;

@SuppressWarnings("javadoc")
public class EnumsBrowserStack {
    
    public enum PlatformMobilBS {
        Android("android"), iOS("iPhone");
        
        String valueAPI;
        private PlatformMobilBS(String valueAPI) {
            this.valueAPI = valueAPI;
        }
        
        public String getValueaAPI() {
            return this.valueAPI;
        }
    }
    
    public enum PlatformDesktopBS {
        Windows("Windows"), OSX("OS X");
        
        String valueAPI;
        private PlatformDesktopBS(String valueAPI) {
            this.valueAPI = valueAPI;
        }
        
        public String getValueaAPI() {
            return this.valueAPI;
        }        
    }
}
