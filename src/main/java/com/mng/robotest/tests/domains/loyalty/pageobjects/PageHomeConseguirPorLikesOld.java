package com.mng.robotest.tests.domains.loyalty.pageobjects;

import java.util.regex.Pattern;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeConseguirPorLikesOld extends PageBase implements PageHomeConseguirPorLikes {

	private static final String XP_BUTTON_LIKES_ES = 
			"//button/span[" + 
			    "text()[contains(.,'Conseguir por')] and " + 
				"text()[contains(.,'Likes')]]";
	
	private static final String XP_BUTTON_LIKES_ENG = 
			"//button/span[" + 
			    "text()[contains(.,'Get for')] and " + 
				"text()[contains(.,'Likes')]]";
	
	private static final String XP_ICON_OPERATION_DONE = 
			"//*[@class[contains(.,'icon-outline-done')]]";
	
	private String getXPathButtonLikes() {
		return "(" + XP_BUTTON_LIKES_ES + " | " + XP_BUTTON_LIKES_ENG + ")";
	}
	
	@Override
	public boolean isPage(int seconds) {
		return state(VISIBLE, getXPathButtonLikes()).wait(seconds).check();
	}
	
	@Override
	public int selectConseguirButton() {
		int likes = getLikesFromButton();
		click(getXPathButtonLikes()).exec();
		return likes;
	}
	
	@Override
	public boolean isVisibleIconOperationDoneUntil(int seconds) {
		return (state(VISIBLE, XP_ICON_OPERATION_DONE).wait(seconds).check());
	}	
	
	private int getLikesFromButton() {
	    String textButton = getElement(getXPathButtonLikes()).getText();
	    String sanitizedText = textButton.replaceAll("[.,]", "");
	    var pattern = Pattern.compile("(\\d+)");
	    var matcher = pattern.matcher(sanitizedText);
	    if (matcher.find()) {
	        return Integer.valueOf(matcher.group(1));
	    }
	    return 0;
	}
	
}
