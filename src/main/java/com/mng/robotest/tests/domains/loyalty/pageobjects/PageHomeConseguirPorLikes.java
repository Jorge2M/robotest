package com.mng.robotest.tests.domains.loyalty.pageobjects;

import java.util.regex.Pattern;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeConseguirPorLikes extends PageBase {

	private static final String XP_BUTTON_LIKES = 
			"//button/span[" + 
			    "text()[contains(.,'Conseguir por')] and " + 
				"text()[contains(.,'Likes')]]";
	
	private static final String XP_ICON_OPERATION_DONE = 
			"//*[@class[contains(.,'icon-outline-done')]]";
	
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_BUTTON_LIKES).wait(seconds).check();
	}
	
	public int selectConseguirButton() {
		int likes = getLikesFromButton();
		click(XP_BUTTON_LIKES).exec();
		return likes;
	}
	
	private int getLikesFromButton() {
		String textButton = getElement(XP_BUTTON_LIKES).getText();
		var pattern = Pattern.compile(".(\\d+).");
		var matcher = pattern.matcher(textButton);
		if (matcher.find()) {
			return Integer.valueOf(matcher.group(1));
		}
		return 0;
	}
	
	public boolean isVisibleIconOperationDoneUntil(int seconds) {
		return (state(VISIBLE, XP_ICON_OPERATION_DONE).wait(seconds).check());
	}
	
}
