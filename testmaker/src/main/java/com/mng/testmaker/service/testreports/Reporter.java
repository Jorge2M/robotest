package com.mng.testmaker.service.testreports;

import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import com.mng.testmaker.listeners.utils.GenerateReports;
import com.mng.testmaker.utils.controlTest.FmwkTest;

/**
 * @author Jorge Muñoz
 */

public class Reporter implements IReporter {
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);
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
        for (ISuiteResult r2 : r.values()) {
        	context = r2.getTestContext();
        }
        if (context!=null) {
            String outputDirectorySuite = FmwkTest.getOutputDirectorySuite(context);
            GenerateReports report = new GenerateReports();
            report.generateReport(xmlSuites, suites, outputDirectorySuite);
        } else {
            pLogger.error("Not found context testng in suite");
        }
    }
}