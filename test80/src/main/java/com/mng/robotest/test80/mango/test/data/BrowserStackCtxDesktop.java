package com.mng.robotest.test80.mango.test.data;

import com.mng.testmaker.service.webdriver.maker.brwstack.BrowserStackDesktop;
import com.mng.testmaker.service.webdriver.maker.brwstack.BrowserStackSO.PlatformDesktopBS;

public enum BrowserStackCtxDesktop implements BrowserStackDesktop {

    //Win10_Edge16 (PlatformDesktopBS.Windows, "10", "Edge", "16.0", "1920x1080"),
    Win8_Firefox68 (PlatformDesktopBS.Windows, "8", "Firefox", "68.0", "1920x1080"),
    macOSMojave_Safari(PlatformDesktopBS.OSX, "Mojave", "Safari", "12.1", "1920x1080");
    //macOSMojave_Chrome68(PlatformDesktopBS.OSX, "Mojave", "Chrome", "75.0", "1920x1080");
    //Win8_Firefox62 (PlatformDesktopBS.Windows, "8", "Firefox", "62.0", "1920x1080"),
    //OSX_Safari11 (PlatformDesktopBS.OSX, "High Sierra", "Safari", "11.0", "1920x1080");
	
	PlatformDesktopBS so;
	String soVersion;
	String browser;
	String browserVersion;
	String resolution;
	private BrowserStackCtxDesktop(PlatformDesktopBS so, String soVersion, String browser, String browserVersion, String resolution) {
		this.so = so;
		this.soVersion = soVersion;
		this.browser = browser;
		this.browserVersion = browserVersion;
		this.resolution = resolution;
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
	public PlatformDesktopBS getSo() {
		return this.so;
	}
	@Override
	public String getSoVersion() {
		return this.soVersion;
	}
	@Override
	public String getBrowser() {
		return this.browser;
	}
	@Override
	public String getBrowserVersion() {
		return this.browserVersion;
	}
	@Override
	public String getResolution() {
		return this.resolution;
	}
}
