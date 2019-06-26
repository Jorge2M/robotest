package com.mng.robotest.test80.arq.utils;

import org.testng.xml.XmlGroups;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * @author jorge.munoz
 * This class is needed becaus XmlTest not exposes the x_xmlGroups attribute and then results in impossibility of modify dependency-groups
 */

public class XmlTestP80 extends XmlTest {
    private static final long serialVersionUID = -4002416107477209626L;
    public XmlGroups x_xmlGroupsVisible;

    public XmlTestP80(XmlSuite suite, int index) {
        super(suite, index);
    }

    public XmlTestP80(XmlSuite suite) {
        super(suite);
    }
    
    public XmlGroups getGroups() {
        return this.x_xmlGroupsVisible;
    }
    
    @Override
    public void setGroups(XmlGroups xmlGroups) {
        super.setGroups(xmlGroups);
        this.x_xmlGroupsVisible = xmlGroups;
    }
}
