package com.mng.robotest.test80.arq.utils.testab.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.conf.AppTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.testab.ActivationData;
import com.mng.robotest.test80.arq.utils.testab.TestAB;
import com.mng.robotest.test80.arq.utils.testab.TestAB.TypeTestAB;
import com.mng.robotest.test80.arq.utils.testab.TestABGoogleExp;
import com.mng.robotest.test80.arq.utils.testab.TestABOptimize;

public interface TestABmanager {
	
	public void activateTestAB(int variante) throws Exception;
	public void activateTestAB() throws Exception;
	public void activateRandomTestABInBrowser() throws Exception;
	public int getVariant() throws UnsupportedOperationException;

	
	public static TestABmanager getInstance(TestAB testAB, Channel channel, AppTest app, WebDriver driver) {
		switch (testAB.getType()) {
		case GoogleExperiments:
			return (new TestABGoogleExpManager((TestABGoogleExp)testAB, channel, app, driver));
		case Optimize:
		default:
			return (new TestABOptimizeManager((TestABOptimize)testAB, channel, app, driver));
		}
	}

	public static void activateTestsAB(List<ActivationData> testsABtoActive, Channel channel, AppTest app, WebDriver driver) 
	throws Exception {
		List<ActivationData> listOptimize = filterByTestABtype(testsABtoActive, TypeTestAB.Optimize);
		if (listOptimize.size() > 0) {
			TestABOptimizeManager.activateTestsAB(listOptimize, channel, app, driver);
		}
		
		List<ActivationData> listExperiments = filterByTestABtype(testsABtoActive, TypeTestAB.GoogleExperiments);
		if (listExperiments.size() > 0) {
			TestABGoogleExpManager.activateTestsAB(listExperiments, channel, app, driver);
		}
	}

	static List<ActivationData> filterByTestABtype(List<ActivationData> listTestABs, TypeTestAB typeTestAB) {
		List<ActivationData> listToReturn = new ArrayList<>();
		for (ActivationData testAB : listTestABs) {
			if (testAB.getTestAB().getType()==typeTestAB) {
				listToReturn.add(testAB);
			}
		}
		
		return listToReturn;
	}

	default Cookie getClonedWithNewValue(Cookie actualCookie, String newValue) {
        Map<String,Object> jsonCookie = actualCookie.toJson();
        Cookie newCookie = 
        	new Cookie(
        		(String)jsonCookie.get("name"), 
        		newValue, 
        		(String)jsonCookie.get("domain"), 
        		(String)jsonCookie.get("path"), 
        		(Date)jsonCookie.get("expiry"), 
        		(boolean)jsonCookie.get("secure"), 
        		(boolean)jsonCookie.get("httpOnly"));
        return newCookie;
	}
}