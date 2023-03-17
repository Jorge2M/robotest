package com.mng.robotest.domains.loyalty.pageobjects;



import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;


public class PageHomeConseguirPorLikes extends PageBase {

	private static final String XPATH_BUTTON_LIKES = 
			"//button/span[" + 
			    "text()[contains(.,'Conseguir por')] and " + 
				"text()[contains(.,'Likes')]]";
	
	private static final String XPATH_ICON_OPERATION_DONE = 
			"//*[@class[contains(.,'icon-outline-done')]]";
	
	public boolean isPage(int seconds) {
		return state(Visible, XPATH_BUTTON_LIKES).wait(seconds).check();
	}
	
	public void selectConseguirButton() {
		click(XPATH_BUTTON_LIKES).exec();
	}
	
	public boolean isVisibleIconOperationDoneUntil(int seconds) {
		return (state(Visible, XPATH_ICON_OPERATION_DONE).wait(seconds).check());
	}
}
