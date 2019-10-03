package com.mng.testmaker.utils.webdriver;

import com.mng.testmaker.utils.webdriver.BrowserStackSO.PlatformDesktopBS;

public interface BrowserStackDesktop {

	public String getUser();
	public String getPassword();
	public PlatformDesktopBS getSo();
	public String getSoVersion();
	public String getBrowser();
	public String getBrowserVersion();
	public String getResolution();
}
