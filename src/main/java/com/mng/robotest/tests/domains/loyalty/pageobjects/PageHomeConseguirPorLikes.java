package com.mng.robotest.tests.domains.loyalty.pageobjects;



import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;


public class PageHomeConseguirPorLikes extends PageBase {

	private static final String XP_BUTTON_LIKES = 
			"//button/span[" + 
			    "text()[contains(.,'Conseguir por')] and " + 
				"text()[contains(.,'Likes')]]";
	
	private static final String XP_ICON_OPERATION_DONE = 
			"//*[@class[contains(.,'icon-outline-done')]]";
	
	public boolean isPage(int seconds) {
		return state(Visible, XP_BUTTON_LIKES).wait(seconds).check();
	}
	
	public void selectConseguirButton() {
		click(XP_BUTTON_LIKES).exec();
	}
	
	public boolean isVisibleIconOperationDoneUntil(int seconds) {
		return (state(Visible, XP_ICON_OPERATION_DONE).wait(seconds).check());
	}
}
