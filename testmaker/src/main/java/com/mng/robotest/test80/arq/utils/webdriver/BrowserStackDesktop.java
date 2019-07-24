package com.mng.robotest.test80.arq.utils.webdriver;

import com.mng.robotest.test80.arq.utils.webdriver.BrowserStackSO.PlatformDesktopBS;

public interface BrowserStackDesktop {

	public String getUser();
	public String getPassword();
	public PlatformDesktopBS getSo();
	public String getSoVersion();
	public String getBrowser();
	public String getBrowserVersion();
	public String getResolution();
}
