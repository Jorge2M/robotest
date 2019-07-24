package com.mng.robotest.test80.mango.test.data;

import com.mng.robotest.test80.arq.utils.webdriver.BrowserStackMobil;
import com.mng.robotest.test80.arq.utils.webdriver.BrowserStackSO.PlatformMobilBS;

public enum BrowserStackCtxMobil implements BrowserStackMobil {

    SamsungGalaxyS8_Android7 (PlatformMobilBS.Android, "7.0", "Samsung Galaxy S8", "true", "chrome"),
    IPhone8_iOS11 (PlatformMobilBS.iOS, "11.0", "iPhone 8", "true", "safari");
	
	PlatformMobilBS so; 
	String soVersion;
	String device;
	String realMobil;
	String browser;
	private BrowserStackCtxMobil (PlatformMobilBS so, String soVersion, String device, String realMobil, String browser) {
		this.so = so; 
		this.soVersion = soVersion;
		this.device = device;
		this.realMobil = realMobil;
		this.browser = browser;
	}
	@Override
	public String getUser() {
		return Constantes.UserBrowserStack;
	}
	@Override 
	public String getPassword() {
		return Constantes.PassBrowserStack;
	}
	@Override
	public PlatformMobilBS getSo() {
		return this.so;
	}
	@Override
	public String getSoVersion() {
		return this.soVersion;
	}
	@Override
	public String getDevice() {
		return this.device;
	}
	@Override
	public String getRealMobil() {
		return this.realMobil;
	}
	@Override
	public String getBrowser() {
		return this.browser;
	}
}
