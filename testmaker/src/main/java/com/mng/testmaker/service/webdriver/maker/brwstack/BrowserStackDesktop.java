package com.mng.testmaker.service.webdriver.maker.brwstack;

import com.mng.testmaker.service.webdriver.maker.brwstack.BrowserStackSO.PlatformDesktopBS;

public interface BrowserStackDesktop {

	public String getUser();
	public String getPassword();
	public PlatformDesktopBS getSo();
	public String getSoVersion();
	public String getBrowser();
	public String getBrowserVersion();
	public String getResolution();
}
