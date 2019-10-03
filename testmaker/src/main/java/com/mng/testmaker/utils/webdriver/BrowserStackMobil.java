package com.mng.testmaker.utils.webdriver;

import com.mng.testmaker.utils.webdriver.BrowserStackSO.PlatformMobilBS;

public interface BrowserStackMobil {

	public String getUser();
	public String getPassword();
	public PlatformMobilBS getSo();
	public String getSoVersion();
	public String getDevice();
	public String getRealMobil();
	public String getBrowser();
	
}
