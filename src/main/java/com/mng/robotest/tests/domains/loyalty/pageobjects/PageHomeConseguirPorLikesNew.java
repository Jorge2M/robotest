package com.mng.robotest.tests.domains.loyalty.pageobjects;

import java.util.regex.Pattern;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageHomeConseguirPorLikesNew extends PageBase implements PageHomeConseguirPorLikes {

	private static final String XP_BUTTON_LIKES_ES = "//*[@data-testid='spaceMangoLikesYou.experienceDetailFooter.button']";
	private static final String XP_ICON_OPERATION_DONE = "//*[@data-testid='snackbar-notification']//*[text()='Pronto podrás disfrutar de tu experiencia']";
	
	@Override
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_BUTTON_LIKES_ES).wait(seconds).check();
	}
	
	@Override
	public int selectConseguirButton() {
		int likes = getLikesFromButton();
		click(XP_BUTTON_LIKES_ES).exec();
		return likes;
	}
	
	@Override
	public boolean isVisibleIconOperationDoneUntil(int seconds) {
		return state(VISIBLE, XP_ICON_OPERATION_DONE).wait(seconds).check();
	}	
	
	private int getLikesFromButton() {
	    String textButton = getElement(XP_BUTTON_LIKES_ES).getText();
	    String sanitizedText = textButton.replaceAll("[.,]", "");
	    var pattern = Pattern.compile("(\\d+)");
	    var matcher = pattern.matcher(sanitizedText);
	    if (matcher.find()) {
	        return Integer.valueOf(matcher.group(1));
	    }
	    return 0;
	}
	
}
