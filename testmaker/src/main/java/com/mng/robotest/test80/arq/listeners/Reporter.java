package com.mng.robotest.test80.arq.listeners;

import java.util.*;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import com.mng.robotest.test80.arq.listeners.utils.GenerateReports;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;

/**
 * @author Jorge Muñoz
 */

public class Reporter /*extends TestHTMLReporter*/ implements IReporter {
	
    public static final String TESTNG_FAILED_XML = "testng-failed.xml";
    
    @SuppressWarnings("unused")
    private XmlSuite m_xmlSuite;
  
  
    public Reporter() {
    }

    public Reporter(XmlSuite xmlSuite) {
        this.m_xmlSuite = xmlSuite;	
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        ITestContext context = null;
        Map<String, ISuiteResult> r = suites.get(0).getResults();
        for (ISuiteResult r2 : r.values())
          context = r2.getTestContext();
      
        if (context!=null) {
            //Obtenemos el output-directory de la Suite
            String outputDirectorySuite = fmwkTest.getOutputDirectorySuite(context);
          
            //Invocamos a la generación de report (genera el emailable-report + el reportHTML)
            GenerateReports report = new GenerateReports();
            report.generateReport(xmlSuites, suites, outputDirectorySuite);
        }
    }
}