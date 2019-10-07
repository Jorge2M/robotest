package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;


public class SecProductDescrOld extends WebdrvWrapp {

    public enum TypeStatePanel {folded, unfolded, missing}
    public enum TypePanel {
        Description(Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.unfolded),
        Composition(Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.folded),
        Shipment(Arrays.asList(AppEcom.shop, AppEcom.votf), TypeStatePanel.folded), 
        Returns(Arrays.asList(AppEcom.shop, AppEcom.votf), TypeStatePanel.folded),
        KcSafety(Arrays.asList(AppEcom.shop, AppEcom.outlet, AppEcom.votf), TypeStatePanel.missing);
        
        private final List<AppEcom> listApps;
        private final TypeStatePanel stateInitial;
        TypePanel(List<AppEcom> listApps, TypeStatePanel stateInitial) {
            this.listApps = listApps;
            this.stateInitial = stateInitial;
        }
        
        public List<AppEcom> getListApps() {
            return this.listApps;
        }
        
        public TypeStatePanel getStateInitial() {
            return this.stateInitial;
        }
    }
    
    static String XPathDivProductDescription = "//div[@class='product-description']";
    static String XPathDescriptionPanel = XPathDivProductDescription + "//div[@id='descriptionPanel']";
    static String XPathCompositionPanel = XPathDivProductDescription + "//div[@id='compositionPanel']";
    static String XPathShipmentPanel = XPathDivProductDescription + "//div[@id='shipmentPanel']";
    static String XPathReturnsPanel = XPathDivProductDescription + "//div[@id='returnsPanel']";
    static String XPathKcSafetyPanel = XPathDivProductDescription + "//div[@id='kcSafetyPanel']";
    
    public static String getXPathPanelLink(TypePanel typePanel) {
        return (getXPathPanel(typePanel) + "//a");
    }
    
    public static String getXPathPanel(TypePanel typePanel) {
        switch (typePanel) {
        case Description:
            return XPathDescriptionPanel;
        case Composition:
            return XPathCompositionPanel;
        case Shipment:
            return XPathShipmentPanel;
        case Returns:
            return XPathReturnsPanel;
        case KcSafety:
            return XPathKcSafetyPanel;
        default:
            return "";
        }
    }
    
    public static TypeStatePanel getStatePanelAfterClick(TypeStatePanel stateOriginal) {
        switch (stateOriginal) {
        case folded:
            return TypeStatePanel.unfolded;
        case unfolded:
            return TypeStatePanel.folded;
        case missing:
        default:
            return TypeStatePanel.missing;
        }
    }
    
    public static TypeStatePanel getStatePanel(TypePanel typePanel, WebDriver driver) throws Exception {
        Thread.sleep(200);
        String xpathPanel = getXPathPanel(typePanel);
        if (!isElementVisible(driver, By.xpath(xpathPanel))) {
            return TypeStatePanel.missing;
        }
        
        WebElement panel = driver.findElement(By.xpath(xpathPanel));
        if (panel.getAttribute("class").contains("-active")) {
            return TypeStatePanel.unfolded;
        }
        return TypeStatePanel.folded;
    }
    
    public static boolean isPanelInStateUntil(TypePanel typePanel, TypeStatePanel stateExpected, int maxSecondsToWait, WebDriver driver) 
    throws Exception {
        TypeStatePanel statePanel = getStatePanel(typePanel, driver);
        int seconds=0;
        while (statePanel!=stateExpected && seconds<maxSecondsToWait) {
            Thread.sleep(1000);
            seconds+=1;
            statePanel = getStatePanel(typePanel, driver);
        }

        return (statePanel==stateExpected);
    }
    
    public static void clickPanel(TypePanel typePanel, WebDriver driver) throws Exception {
        String xpathPanelLink = getXPathPanelLink(typePanel);
        clickAndWaitLoad(driver, By.xpath(xpathPanelLink));
    }
}
