package com.mng.testmaker.testreports.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.reporters.EmailableReporter;
import org.testng.xml.XmlSuite;

import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.conf.ConstantesTM;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.domain.InputParamsTM;
import com.mng.testmaker.domain.InputParamsTM.TypeAccess;
import com.mng.testmaker.domain.StepTM;
import com.mng.testmaker.domain.StepTM.StepEvidence;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.TestCaseTM;
import com.mng.testmaker.domain.TestRunTM;
import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.domain.data.TestCaseData;
import com.mng.testmaker.domain.data.TestRunData;


public class GenerateReports extends EmailableReporter {
	
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
    private SuiteTM suite;
    private List<Integer> treeTable;
    private String outputDirectory = "";
    private String reportHtml = "";
    
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        super.generateReport(xmlSuites, suites, outputDirectory);
    	this.suite = (SuiteTM)xmlSuites.get(0);
    	this.treeTable = getMapTree(suite);
    	this.outputDirectory = outputDirectory;
        try {
        	deployStaticsIfNotExist();
            generateReportHTML();
        } 
        catch (Exception e) {
            pLogger.fatal("Problem generating ReportHTML", e);
        }
    }
    
    private void deployStaticsIfNotExist() throws Exception {
    	ResourcesExtractor resExtractor = ResourcesExtractor.getNew();
    	String pathDirectoryInFromResources =  ConstantesTM.nameDirectoryStatics;
    	resExtractor.copyDirectoryResources(
    		pathDirectoryInFromResources, 
    		outputDirectory + "/../../" + pathDirectoryInFromResources);
    }

    private void generateReportHTML() throws Exception {
        pintaCabeceraHTML();
        pintaHeadersTableMain();        
        pintaTestRunsOfSuite();
        pintaCierreHTML();
        createFileReportHTML();
    }

    public void pintaHeadersTableMain() {
    	SuiteData suiteD = SuiteData.from(suite);
    	reportHtml+=
        	"<table id=\"tableMain\" class=\"tablemain\">" + 
            "<thead>\n" + 
            "  <tr id=\"header1\">\n" + 
            "    <th colspan=\"13\" class=\"head\">" + 
            "      <div id=\"titleReport\">" + suiteD.getName() + " - " + suiteD.getApp() + ", " + suiteD.getChannel() + " (Id: " + suiteD.getIdExecSuite() + ")" +
            "        <span id=\"descrVersion\">" + suiteD.getVersion() + "</span>" +
            "        <span id=\"browser\">" + suiteD.getWebDriverType() + "</span>" + 
            "        <span id=\"url\"><a id=\"urlLink\" href=\"" + suiteD.getUrlBase() + "\">" + suiteD.getUrlBase() + "</a></span>" + 
            "      </div>" + 
            "    </th>\n" + 
            "  </tr>\n" +
            "  <tr id=\"header2\">" + 
            "    <th style=\"display:none;\"></th>\n" + 
            "    <th rowspan=\"2\">Methods Sort: <a href=\"#\" class=\"link-sort-table asc\">A-Z</a> <a href=\"#\" class=\"link-sort-table desc\">Z-A</a> <a href=\"#\" onclick=\"location.reload()\" class=\"link-sort-table reset\">Reset</a></th>" + 
            "    <th rowspan=\"2\">Data</th>" + 
            "    <th rowspan=\"2\">Sons</th>" + 
            "    <th rowspan=\"2\">Result</th>" + 
            "    <th rowspan=\"2\">Time</th>" + 
            "    <th rowspan=\"2\">HardCopy</th>" + 
            "    <th rowspan=\"2\">HTML</th>" + 
            "    <th class=\"size20\" rowspan=\"2\">Description / Action / Validation</th>" + 
            "    <th class=\"size15\" rowspan=\"2\">Result expected</th>" +
            "    <th rowspan=\"2\">Init</th>" + 
            "    <th rowspan=\"2\">End</th>" +                                
            "    <th rowspan=\"2\">Class / Method</th>" + 
            "  </tr>\n" + 
            "  <tr></tr>\n" +
        	"   </thead>\n";
    }
    
    public void pintaCabeceraHTML() {
        String output_library = "../..";
        String pathStatics = output_library + "/static";

        reportHtml+="<html>\n";
        reportHtml+="<head>\n";
        reportHtml+="<meta charset=\"utf-8\">\n";
        reportHtml+="       <title>JQTreeTable</title>\n";
        reportHtml+="       <script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js\" type=\"text/javascript\"></script>";
        reportHtml+="       <script type=\"text/javascript\" src=\"" + pathStatics + "/js/ReportLibrary.js\"></script>\n";

        reportHtml+="<script type=\"text/javascript\">\n";
        reportHtml+="$(function(){//I       AS" + " " + "+ nitialise the treetable\n";
        reportHtml+="  var map2=" + treeTable + ";\n";
        reportHtml+="  var options1 = {openImg: \"" + pathStatics + "/images/tv-collapsable.gif\", " + 
                                      "shutImg: \"" + pathStatics + "/images/tv-expandable.gif\", " +
                                      "leafImg: \"" + pathStatics + "/images/tv-item.gif\", " +
                                      "lastOpenImg: \"" + pathStatics + "/images/tv-collapsable-last.gif\", " + 
                                      "lastShutImg: \"" + pathStatics + "/images/tv-expandable-last.gif\", " + 
                                      "lastLeafImg: \"" + pathStatics + "/images/tv-item-last.gif\", " + 
                                      "vertLineImg: \"" + pathStatics + "/images/vertline.gif\", " + 
                                      "blankImg: \"" + pathStatics + "/images/blank.gif\", collapse: false, column: 1, striped: true, highlight: true, state:true};\n";
        reportHtml+="  $(\"#treet2\").jqTreeTable(map2, {" + 
                                       "openImg: \"" + pathStatics + "/images/fopen.gif\", " + 
                                       "shutImg: \"" + pathStatics + "/images/fshut.gif\", " + 
                                       "leafImg: \"" + pathStatics + "/images/new.gif\", " + 
                                       "lastOpenImg: \"" + pathStatics + "/images/fopen.gif\", " + 
                                       "lastShutImg: \"" + pathStatics + "/images/fshut.gif\", " + 
                                       "lastLeafImg: \"" + pathStatics + "/images/new.gif\", " + 
                                       "vertLineImg: \"" + pathStatics + "/images/blank.gif\", " + 
                                       "blankImg: \"" + pathStatics + "/images/blank.gif\", collapse: false, column: 1, striped: true, highlight: true, state:false});\n";
        reportHtml+="});\n";
        reportHtml+="var outputReports = '" + output_library + "';";
        reportHtml+="</script>\n";
        reportHtml+="       <link href=\"" + pathStatics + "/css/Report.css\" rel=\"stylesheet\" type=\"text/css\">\n";
        reportHtml+="</head>\n";
        
        //Body
        reportHtml+="<body class=\"body\" onload=\"setBrowserType();setSizeTable();\">\n";
        reportHtml+="<div id=\"divShow\">\n";
        reportHtml+="   <a href=\"javascript:show_hide_all('tableMain',true, outputReports);\" id=\"linkShow\">Show All</a>\n";
        reportHtml+="</div>\n";
        reportHtml+="<div class=\"divTestNG\">";
        reportHtml+="  <a href=\"emailable-report.html\" target=\"_blank\" class=\"linkTestNG\">Emailable Report</a>";
        reportHtml+="</div>\n";
        reportHtml+="<div class=\"divTestNG\">";
        reportHtml+="  <a href=\"" + Log4jConfig.log4jFileName + "\" target=\"_blank\" class=\"linkTestNG\">" + Log4jConfig.log4jFileName + "</a>";
        reportHtml+="</div>";        
        reportHtml+="<br>\n";
        reportHtml+="<br>\n";
    }
    
    private void pintaTestRunsOfSuite() {
    	reportHtml+="<tbody id=\"treet2\">\n";
        for (TestRunTM testRun : suite.getListTestRuns()) {
        	TestRunData testRunD = TestRunData.from(testRun);
            DateFormat format = DateFormat.getDateTimeInstance();
            reportHtml+= 
            	"<tr class=\"testrun\">" +
            	"  <td style=\"display:none;\"></td>\n" + 
            	"  <td class=\"nowrap\">" + testRunD.getName() + "</td>" + 
            	"  <td class=\"nowrap\"><div id=\"device\">" + testRunD.getDevice() + "</div></td>" + 
            	"  <td>" + testRunD.getNumberTestCases() + "</td>" + 
            	"  <td><div class=\"result" + testRunD.getResult() + "\">" + testRunD.getResult() + "</div></td>" + 
            	"  <td>" + testRunD.getDurationMillis() + "</td>" + "               <td></td>" + 
            	"  <td><br><br></td>" + 
            	"  <td></td>" + 
            	"  <td></td>" +
            	"  <td>" + format.format(testRunD.getInicioDate()) + "</td>" + 
            	"  <td>" + format.format(testRunD.getFinDate()) + "</td>" +
            	"  <td></td>" + 
            	"</tr>\n";
            
            pintaTestCasesOfTestRun(testRun);
        }
    }    
    
    private void pintaTestCasesOfTestRun(TestRunTM testRun) {
    	List<TestCaseTM> listTestCases = testRun.getListTestCases();
    	String TagTimeout = "@TIMEOUTSTEP";
    	for (int i=0; i<listTestCases.size(); i++) {
    		TestCaseData testCaseD = TestCaseData.from(listTestCases.get(i));
            DateFormat format = DateFormat.getDateTimeInstance();
            reportHtml+= 
                "<tr class=\"method\"" + " met=\"" + testCaseD.getIndexInTestRun() + "\">" +
                "  <td style=\"display:none;\"></td>\n" + 
                "  <td class=\"nowrap\">" + testCaseD.getNameUnique() + "</td>" + 
                "  <td class=\"nowrap\"></td>" + 
                "  <td>" + testCaseD.getNumberSteps() + "</td>" + 
                "  <td><div class=\"result" + testCaseD.getResult() + "\">" + testCaseD.getResult() + "</div></td>" + 
                "  <td>" + testCaseD.getDurationMillis() + "</td>" + 
                "  <td></td>" + 
                "  <td><br><br></td>" + 
                "  <td colspan=2>" + testCaseD.getDescription() + "</td>" + 
                "  <td>" + TagTimeout + format.format(testCaseD.getInicioDate()) + "</td>" + 
                "  <td>" + TagTimeout + format.format(testCaseD.getFinDate()) + "</td>" +
                "  <td>" + testCaseD.getClassSignature() + "</td>" + 
                "</tr>\n";
            
            boolean timeoutStep = pintaStepsOfTestCase(listTestCases.get(i));
            String font = "<font>";
            if (timeoutStep) {
                font = "<font class=\"timeout\">";
            }
            reportHtml = reportHtml.replaceAll(TagTimeout, font);
        }
    }    
    
    /**
     * @return if Timeout
     */
    private boolean pintaStepsOfTestCase(TestCaseTM testCase) {
    	boolean timeout = false;
        for (StepTM step : testCase.getStepsList()) {
            int stepNumber = step.getPositionInTestCase();
            String ImageFileStep = StoreStepEvidencies.getPathFileEvidenciaStep(outputDirectory, step, StepEvidence.imagen);
            File indexFile = new File(ImageFileStep);
            String litPNGNewStep = "";
            String PNGNewStep = "#";
            if (indexFile.exists()) {
                litPNGNewStep = "HardCopy";
                PNGNewStep = getRelativePathEvidencia(step, StepEvidence.imagen);
            }

            String ErrorFileStep = StoreStepEvidencies.getPathFileEvidenciaStep(outputDirectory, step, StepEvidence.errorpage);
            indexFile = new File(ErrorFileStep);
            String linkErrorNew = "";
            if (indexFile.exists()) {
                linkErrorNew = " \\ <a href=\"" + getRelativePathEvidencia(step, StepEvidence.errorpage) + "\" target=\"_blank\">ErrorPage</a>";
            }

            String HARPFileStep = StoreStepEvidencies.getPathFileEvidenciaStep(outputDirectory, step, StepEvidence.harp);
            indexFile = new File(HARPFileStep);
            String linkHarpNew = "";
            if (indexFile.exists()) {
            	InputParamsTM inputParams = testCase.getSuiteParent().getInputParams();
                String pathHARP = getDnsOfFileReport(
                		indexFile.getAbsolutePath(), 
                		inputParams.getWebAppDNS(), 
                		inputParams.getTypeAccess()).replace('\\', '/');
                linkHarpNew = " \\ <a href=\"" + ConstantesTM.URL_SOFTWAREISHARD + pathHARP + "\" target=\"_blank\">NetTraffic</a>";
            }
            
            String HARFileStep = StoreStepEvidencies.getPathFileEvidenciaStep(outputDirectory, step, StepEvidence.har);
            String linkHarNew = "";
            indexFile = new File(HARFileStep);
            if (indexFile.exists()) {
                linkHarNew = " \\ <a href=\"" + getRelativePathEvidencia(step, StepEvidence.har) + "\" target=\"_blank\">NetJSON</a>";
            }

            String HtmlFileStep = StoreStepEvidencies.getPathFileEvidenciaStep(outputDirectory, step, StepEvidence.html);
            indexFile = new File(HtmlFileStep);
            String linkHtmlNew = "";
            if (indexFile.exists()) {
                linkHtmlNew = "<a href=\"" + getRelativePathEvidencia(step, StepEvidence.html) + "\">HTML Page</a>";
            }

            long diffInMillies = step.getHoraFin().getTime() - step.getHoraInicio().getTime();
            String tdClassDate = "<td>";
            if (diffInMillies > 30000) {
                tdClassDate = "<td><font class=\"timeout\">";
                timeout = true;
            }

            DateFormat format = DateFormat.getDateTimeInstance();
            reportHtml+=
                "<tr class=\"step collapsed\"" + " met=\"" + testCase.getIndexInTestRun() + "\">" +
                "     <td style=\"display:none;\"></td>\n" +
                "     <td class=\"nowrap\">Step " + stepNumber + "</td>" + 
                "     <td></td>" + 
                "     <td>" + step.getNumChecksResult() + "</td>" + 
                "     <td><div class=\"result" + step.getResultSteps() + "\">" + step.getResultSteps() + "</div></td>" + 
                "     <td>" + diffInMillies + "</td>" + 
                "     <td><a href=\"" + PNGNewStep + "\" target=\"_blank\">" + litPNGNewStep + "</a>" + linkErrorNew + linkHarpNew + linkHarNew + "</td>" + 
                "     <td>" + linkHtmlNew + "</td>" +
                "     <td>" + step.getDescripcion() + "</td>" + 
                "     <td>" + step.getResExpected() + "</td>" +
                tdClassDate + format.format(step.getHoraInicio()) + "</td>" + 
                tdClassDate + format.format(step.getHoraFin()) + "</td>" +
                "     <td>" + step.getNameClass() + " / " + step.getNameMethod() + "</td>" +
                "</tr>\n";

            pintaValidacionesStep(step);
        }        
        
        return timeout;
    }

    private String getRelativePathEvidencia(StepTM step, StepEvidence evidence) {
        String fileName = StoreStepEvidencies.getNameFileEvidenciaStep(step.getPositionInTestCase(), evidence);
        String testRunName = step.getTestRunParent().getName();
        String testCaseNameUnique = step.getTestCaseParent().getNameUnique();
        return ("./" + testRunName + "/" + testCaseNameUnique + "/" + fileName);
    }
    
    private void pintaValidacionesStep(StepTM step) {
    	List<ChecksResult> listChecksResult = step.getListChecksResult();
        for (ChecksResult checksResult : listChecksResult) {
            String descriptValid = checksResult.getHtmlValidationsBrSeparated();
            reportHtml+= 
            	"<tr class=\"validation collapsed\"" + " met=\"" + step.getTestCaseParent().getIndexInTestRun() + "\">" +
            	"    <td style=\"display:none;\"></td>\n" + 
            	"    <td class=\"nowrap\">Validation " + checksResult.getPositionInStep() + "</td>" + 
            	"    <td></td>" + 
            	"    <td></td>" + 
            	"    <td><div class=\"result" + checksResult.getStateValidation() + "\">" + checksResult.getStateValidation() + "</div></td>" + 
            	"    <td></td>" + 
            	"    <td></td>" + 
            	"    <td></td>" + 
            	"    <td>" + descriptValid + "</td>" + 
            	"    <td></td>" +
            	"    <td></td>" + 
                "    <td></td>" +                        
                "    <td>" + checksResult.getNameClass() + " / " + checksResult.getNameMethod() + "</td>" + 
                "</tr>\n";
        }
    }
    
    public void pintaCierreHTML() {
    	reportHtml+=
        	"</tbody>" + 
            "</table><br />\n" +
            "</body>\n" +
            "</html>\n";
    }    
    
    public void createFileReportHTML() {
        String fileReport = suite.getPathReportHtml();;
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileReport), "UTF8"))) {
            out.write(reportHtml.toString());
            out.close();
        } 
        catch (Exception e) {
            pLogger.fatal("Problem creating file ReportHTML", e);
        } 
    }

    static List<Integer> getMapTree(SuiteTM suite) {
    	List<Integer> listMapReturn = new ArrayList<>();
    	for (TestRunTM testRun : suite.getListTestRuns()) {
    		listMapReturn.add(0);
    		int posLastTestRun = listMapReturn.size();
    		for (TestCaseTM testCase : testRun.getListTestCases()) {
    			listMapReturn.add(posLastTestRun);
        		int posLastTest = listMapReturn.size();
    			for (StepTM step : testCase.getStepsList()) {
    				listMapReturn.add(posLastTest);
            		int posLastStep = listMapReturn.size();
            		for (int i=0; i<step.getListChecksResult().size(); i++) {
        				listMapReturn.add(posLastStep);
            		}
    			}
    		}
    	}
    	return listMapReturn;
    }

    /**
     * Obtiene la DNS de un fichero ubicado dentro del contexto de la aplicación de tests
     * @param serverDNS: del tipo "http://robottest.mangodev.net + :port si fuera preciso)  
     */
    public static String getDnsOfFileReport(String filePath, String applicationDNS, TypeAccess typeAccess) {
        String pathReport = filePath.substring(filePath.indexOf(ConstantesTM.directoryOutputTests));
        if (applicationDNS!=null && "".compareTo(applicationDNS)!=0) {
            return (applicationDNS + "\\" + pathReport);
        } else {
        	switch (typeAccess) {
        	case CmdLine:
        	case Bat:
        		Pattern patternDrive = Pattern.compile("^[a-zA-Z]:");
        		return (patternDrive.matcher(filePath).replaceFirst("\\\\\\\\" + getNamePC()));
        	case Rest:
        	default:
        		return (pathReport.replace("\\", "/"));
        	}
        }
    }
	
    private static String getNamePC() {
        String hostname = "";
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            hostname = addr.getHostName();
        }
        catch (UnknownHostException ex) {
            hostname = "Unknown";
        }
		
        return hostname;
    }
}