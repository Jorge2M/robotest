package com.mng.robotest.test80.mango.test.utils.testab;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;

public interface TestAB {
	public void activateTestAB(int variante, WebDriver driver) throws Exception;
	public void activateTestAB(WebDriver driver) throws Exception;
	public void activateRandomTestABInBrowser(WebDriver driver) throws Exception;
	public int getVariant(WebDriver driver) throws UnsupportedOperationException;
	
	public enum TypeTestAB {
		GoogleExperiments,
		GoogleOptimize;
	}
	
	public static TestAB getInstance(TestABid idTestAB, AppEcom app) {
		switch (idTestAB.getType()) {
		case GoogleExperiments:
			return (new TestABGoogleExperiments(idTestAB, app));
		case GoogleOptimize:
		default:
			return (new TestABGoogleOptimize(idTestAB));
		}
	}
    
	//TODO cuando se elimine el Test A/B habrá que eliminar este código
	//si el TestAB que gana no es el actual habrá que adaptar el script de test
    public static void activateTestABcheckoutMovilEnNPasos(int versionTestAB, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	if (dCtxSh.channel==Channel.movil_web) {
	    	TestAB testAB = TestAB.getInstance(TestABid.CheckoutMovilNpasos, dCtxSh.appE);
	    	testAB.activateTestAB(versionTestAB, driver);
    	}
    }
    
	//TODO cuando se elimine el Test A/B habrá que eliminar este código
	//si el TestAB que gana no es el actual habrá que adaptar el script de test
    public static void activateTestABgaleriaReact(int versionTestAB, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	if (channel==Channel.desktop) {
	    	TestAB testAB = TestAB.getInstance(TestABid.GaleriaDesktopReact, app);
	    	testAB.activateTestAB(versionTestAB, driver);
	    	
	    	testAB = TestAB.getInstance(TestABid.GaleriaDesktopReactPRESemanal, app);
	    	testAB.activateTestAB(versionTestAB, driver);
    	}
    }
    
    public static void activateTestABcabeceraDesktop(int versionTestAB, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	if (channel==Channel.desktop) {
	    	TestAB testAB = TestAB.getInstance(TestABid.HeaderDesktopNewIcons, app);
	    	testAB.activateTestAB(versionTestAB, driver);
    	}
    }
    
    public static void activateTestABselectorTallaColorMobil(int versionTestAB, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	if (channel==Channel.movil_web) {
	    	TestAB testAB = TestAB.getInstance(TestABid.MobileSelectorTallaColor, app);
	    	testAB.activateTestAB(versionTestAB, driver);
    	}
    }
    
    public static void currentTestABsToActivate(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		int versionSinReact = 0;
		activateTestABgaleriaReact(versionSinReact, dCtxSh.channel, dCtxSh.appE, driver);
		
		int versionOriginal = 0;
		activateTestABselectorTallaColorMobil(versionOriginal, dCtxSh.channel, dCtxSh.appE, driver);
		
		//Foorzamos cabecera desktop sin iconos
//		int versionSinIconos = 0;
//		TestAB.activateTestABcabeceraDesktop(versionSinIconos, dCtxSh.channel, dCtxSh.appE, driver);
    }
}