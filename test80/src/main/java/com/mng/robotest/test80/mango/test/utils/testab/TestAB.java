package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.List;
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
			return (new TestABGoogleOptimize(idTestAB, channel, app, driver));
		}
	}
	
	public static void activateTestsAB(List<ActivationData> testsABtoActive, Channel channel, AppEcom app, WebDriver driver) throws Exception {
		List<ActivationData> listOptimize = filterByTestABtype(testsABtoActive, TypeTestAB.GoogleOptimize);
		if (listOptimize.size() > 0) {
			TestABGoogleOptimize.activateTestsAB(listOptimize, channel, app, driver);
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
    
    public static void currentTestABsToActivate(Channel channel, AppEcom app, WebDriver driver) throws Exception {
    	List<ActivationData> listTestABsToActivate = new ArrayList<>();
    	listTestABsToActivate.add(ActivationData.getNew(TestABid.GaleriaDesktopReact, 0));
    	listTestABsToActivate.add(ActivationData.getNew(TestABid.MVPCheckoutDesktop, 0));
    	listTestABsToActivate.add(ActivationData.getNew(TestABid.MobileSelectorTallaColor, 0));
    	activateTestsAB(listTestABsToActivate, channel, app, driver);
    }

}