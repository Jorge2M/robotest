package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public interface TestAB {
	
	public void activateTestAB(int variante) throws Exception;
	public void activateTestAB() throws Exception;
	public void activateRandomTestABInBrowser() throws Exception;
	public int getVariant() throws UnsupportedOperationException;
	
	public enum TypeTestAB {
		GoogleExperiments,
		GoogleOptimize;
	}
	
	public static TestAB getInstance(TestABid idTestAB, Channel channel, AppEcom app, WebDriver driver) {
		switch (idTestAB.getType()) {
		case GoogleExperiments:
			return (new TestABGoogleExperiments(idTestAB, channel, app, driver));
		case GoogleOptimize:
		default:
			return (new TestABOptimize(idTestAB, channel, app, driver));
		}
	}
	
    public static void currentTestABsToActivate(Channel channel, AppEcom app, WebDriver driver) throws Exception {
    	List<ActivationData> listTestABsToActivate = new ArrayList<>();
    	listTestABsToActivate.add(ActivationData.getNew(TestABid.GaleriaDesktopReact, 0));
    	//listTestABsToActivate.add(ActivationData.getNew(TestABid.SHOP191_BuscadorDesktop, 1));
    	listTestABsToActivate.add(ActivationData.getNew(TestABid.MVPCheckoutDesktop, 0));
    	listTestABsToActivate.add(ActivationData.getNew(TestABid.SHOP126_HeaderNuevosIconosDesktop, 1));
    	activateTestsAB(listTestABsToActivate, channel, app, driver);
    }
    
	public static void activateTestsAB(List<ActivationData> testsABtoActive, Channel channel, AppEcom app, WebDriver driver) throws Exception {
		List<ActivationData> listOptimize = filterByTestABtype(testsABtoActive, TypeTestAB.GoogleOptimize);
		if (listOptimize.size() > 0) {
			TestABOptimize.activateTestsAB(listOptimize, channel, app, driver);
		}
		
		List<ActivationData> listExperiments = filterByTestABtype(testsABtoActive, TypeTestAB.GoogleExperiments);
		if (listExperiments.size() > 0) {
			TestABGoogleExperiments.activateTestsAB(listExperiments, channel, app, driver);
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