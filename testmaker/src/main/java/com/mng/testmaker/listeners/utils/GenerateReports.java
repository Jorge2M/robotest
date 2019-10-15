package com.mng.testmaker.listeners.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Date;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.reporters.EmailableReporter;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.mng.testmaker.data.ConstantesTestMaker;
import com.mng.testmaker.domain.InputParamsTestMaker;
import com.mng.testmaker.domain.StepTestMaker;
import com.mng.testmaker.domain.SuiteTestMaker;
import com.mng.testmaker.domain.TestCaseTestMaker;
import com.mng.testmaker.domain.TestRunTestMaker;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.utils;
import com.mng.testmaker.utils.controlTest.FmwkTest;
import com.mng.testmaker.utils.controlTest.FmwkTest.TypeEvidencia;


public class GenerateReports extends EmailableReporter {
	
    static Logger pLogger = LogManager.getLogger(FmwkTest.log4jLogger);

    @Override
    public void generateReport(final List<XmlSuite> xmlSuites, final List<ISuite> suites, final String outputDirectory) {
        super.generateReport(xmlSuites, suites, outputDirectory);
        
        ITestContext context = null;
        Map<String, ISuiteResult> r = suites.get(0).getResults();
        for (ISuiteResult r2 : r.values()) {
            context = r2.getTestContext();
        }
        if (context!=null) {
        	SuiteTestMaker suiteTestMaker = (SuiteTestMaker)suites.get(0).getXmlSuite();
            try {
            	deployStaticsIfNotExist(outputDirectory);
                generateReportHTML(suiteTestMaker, outputDirectory);
            } 
            catch (Exception e) {
                pLogger.fatal("Problem generating ReportHTML", e);
            }
        }
    }
    
    private void deployStaticsIfNotExist(String outputDirectory) throws Exception {
    	ResourcesExtractor resExtractor = ResourcesExtractor.getNew();
    	String pathDirectoryInFromResources =  ConstantesTestMaker.nameDirectoryStatics;
    	resExtractor.copyDirectoryResources(
    		pathDirectoryInFromResources, 
    		outputDirectory + "/../../" + pathDirectoryInFromResources);
    }

    private void generateReportHTML(SuiteTestMaker suite, String outputDirectory) throws Exception {
        BuildingReport buildReport = new BuildingReport(outputDirectory);
        buildReport.serverDNS = suite.getInputData().getWebAppDNS();
        pintaCabeceraHTML(buildReport);
        pintaHeadersTableMain(buildReport, suite);        
        pintaTestRunsSuite(buildReport, suite);
        pintaCierreHTML(buildReport);
        buildReport.replaceTagWithTree("@VALUES_TREE");
        createFileReportHTML(buildReport);
    }

    /**
     * Pinta los headers del Tablemain
     * @param buildReport objeto en el que vamos construyendo el report
     * @param suiteBDHash datos a nivel de BD de la suite (tabla SUITE)
     * @param context contexto de ejecución del test a nivel de TestNG
     */
    public void pintaHeadersTableMain(BuildingReport buildReport, SuiteTestMaker suite) {
    	InputParamsTestMaker inputData = suite.getInputData();

        buildReport.addToReport(
        	"<table id=\"tableMain\" class=\"tablemain\">" + 
            "<thead>\n" + 
            "  <tr id=\"header1\">\n" + 
            "    <th colspan=\"13\" class=\"head\">" + 
            "      <div id=\"titleReport\">" + suite.getName() + " - " + inputData.getApp() + ", " + inputData.getChannel() + " (Id: " + suite.getIdExecution() + ")" +
            "        <span id=\"descrVersion\">" + inputData.getVersionSuite() + "</span>" +
            "        <span id=\"browser\">" + inputData.getWebDriverType() + "</span>" + 
            "        <span id=\"url\"><a id=\"urlLink\" href=\"" + inputData.getUrlBase() + "\">" + inputData.getUrlBase() + "</a></span>" + 
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
            "    <th rowspan=\"2\">Class</th>" + 
            "  </tr>\n" + 
            "  <tr></tr>\n" +
        	"   </thead>\n");
    }
    
    /**
     * Pinta el Head y el inicio del Body del Report HTML
     * @param buildReport objeto en el que vamos construyendo el report
     */    
    public void pintaCabeceraHTML(BuildingReport buildReport) {
        
        //String outputReports = "../../../..";
        String output_library = "../..";
        String pathStatics = output_library + "/static";

        buildReport.addToReport("<html>\n");

        //Head
        buildReport.addToReport("<head>\n");
        buildReport.addToReport("<meta charset=\"utf-8\">\n");
        buildReport.addToReport("       <title>JQTreeTable</title>\n");
        buildReport.addToReport("       <script src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js\" type=\"text/javascript\"></script>");
        buildReport.addToReport("       <script type=\"text/javascript\" src=\"" + pathStatics + "/js/ReportLibrary.js\"></script>\n");

        buildReport.addToReport("<script type=\"text/javascript\">\n");
        buildReport.addToReport("$(function(){//I       AS" + " " + "+ nitialise the treetable\n");
        buildReport.addToReport("  var map2=[@VALUES_TREE];\n");
        buildReport.addToReport("  var options1 = {openImg: \"" + pathStatics + "/images/tv-collapsable.gif\", " + 
                                                  "shutImg: \"" + pathStatics + "/images/tv-expandable.gif\", " +
                                                  "leafImg: \"" + pathStatics + "/images/tv-item.gif\", " +
                                                  "lastOpenImg: \"" + pathStatics + "/images/tv-collapsable-last.gif\", " + 
                                                  "lastShutImg: \"" + pathStatics + "/images/tv-expandable-last.gif\", " + 
                                                  "lastLeafImg: \"" + pathStatics + "/images/tv-item-last.gif\", " + 
                                                  "vertLineImg: \"" + pathStatics + "/images/vertline.gif\", " + 
                                                  "blankImg: \"" + pathStatics + "/images/blank.gif\", collapse: false, column: 1, striped: true, highlight: true, state:true};\n");
        buildReport.addToReport("  $(\"#treet2\").jqTreeTable(map2, {" + 
                                       "openImg: \"" + pathStatics + "/images/fopen.gif\", " + 
                                       "shutImg: \"" + pathStatics + "/images/fshut.gif\", " + 
                                       "leafImg: \"" + pathStatics + "/images/new.gif\", " + 
                                       "lastOpenImg: \"" + pathStatics + "/images/fopen.gif\", " + 
                                       "lastShutImg: \"" + pathStatics + "/images/fshut.gif\", " + 
                                       "lastLeafImg: \"" + pathStatics + "/images/new.gif\", " + 
                                       "vertLineImg: \"" + pathStatics + "/images/blank.gif\", " + 
                                       "blankImg: \"" + pathStatics + "/images/blank.gif\", collapse: false, column: 1, striped: true, highlight: true, state:false});\n");
        buildReport.addToReport("});\n");
        buildReport.addToReport("var outputReports = '" + output_library + "';");
        buildReport.addToReport("</script>\n");
        buildReport.addToReport("       <link href=\"" + pathStatics + "/css/Report.css\" rel=\"stylesheet\" type=\"text/css\">\n");
        buildReport.addToReport("</head>\n");
        
        //Body
        buildReport.addToReport("<body class=\"body\" onload=\"setBrowserType();setSizeTable();\">\n");
        buildReport.addToReport("<div id=\"divShow\">\n");
        buildReport.addToReport("   <a href=\"javascript:show_hide_all('tableMain',true, outputReports);\" id=\"linkShow\">Show All</a>\n");
        buildReport.addToReport("</div>\n");
        buildReport.addToReport("<div class=\"divTestNG\">");
        buildReport.addToReport("  <a href=\"emailable-report.html\" target=\"_blank\" class=\"linkTestNG\">Emailable Report</a>");
        buildReport.addToReport("</div>\n");
        buildReport.addToReport("<div class=\"divTestNG\">");
        buildReport.addToReport("  <a href=\"" + FmwkTest.log4jFileName + "\" target=\"_blank\" class=\"linkTestNG\">" + FmwkTest.log4jFileName + "</a>");
        buildReport.addToReport("</div>");        
        buildReport.addToReport("<br>\n");
        buildReport.addToReport("<br>\n");
    }
    
    /**
     * Añadimos al report los métodos asociados a un determinado testrun
     * @param buildReport objeto en el que vamos construyendo el report
     * @param suite identificador de la suite
     */    
    private void pintaTestRunsSuite(BuildingReport buildReport, SuiteTestMaker suite) {
        buildReport.addToReport("<tbody id=\"treet2\">\n");
        for (XmlTest xmlTestRun : suite.getTests()) {
        	TestRunTestMaker testRun = (TestRunTestMaker)xmlTestRun;
            buildReport.increaseCountRows();
            buildReport.addValueToTree(0);
            buildReport.setLastTestRun(buildReport.getCountRows());

        	ITestContext ctxTng = testRun.getTestNgContext();
        	java.util.Date startDate = ctxTng.getStartDate();
        	java.util.Date endDate = ctxTng.getEndDate();
        	long timeMillis = endDate.getTime() - startDate.getTime();
        	
        	String deviceBSmovil = "";
        	if (testRun.getBrowserStackMobil()!=null) {
        		deviceBSmovil = testRun.getBrowserStackMobil().getDevice();
        	}

            DateFormat format = DateFormat.getDateTimeInstance();
            buildReport.addToReport( 
            	"<tr class=\"testrun\">" +
            	"  <td style=\"display:none;\"></td>\n" + 
            	"  <td class=\"nowrap\">" + testRun.getName() + "</td>" + 
            	"  <td class=\"nowrap\"><div id=\"device\">" + deviceBSmovil + "</div></td>" + 
            	"  <td>" + testRun.getNumTestCases() + "</td>" + 
            	"  <td><div class=\"result" + testRun.getState() + "\">" + testRun.getState() + "</div></td>" + 
            	"  <td>" + timeMillis + "</td>" + "               <td></td>" + 
            	"  <td><br><br></td>" + 
            	"  <td></td>" + 
            	"  <td></td>" +
            	"  <td>" + format.format(startDate) + "</td>" + 
            	"  <td>" + format.format(endDate) + "</td>" +
            	"  <td></td>" + 
            	"</tr>\n");
            
            pintaMethodsTestRun(buildReport, testRun);
        }
    }    
    
    private void pintaMethodsTestRun(BuildingReport buildReport, TestRunTestMaker testRun) {
    	List<TestCaseTestMaker> listTestCases = testRun.getListTestCases();
    	for (int i=0; i<listTestCases.size(); i++) {
    		TestCaseTestMaker testCase = listTestCases.get(i);
            buildReport.setIMethod(i);
            buildReport.setTimeoutStep(false);
            buildReport.increaseCountRows();
            buildReport.addValueToTree(buildReport.getLastTestRun());
            buildReport.setLastMethod(buildReport.getCountRows());

            long startMillis = testCase.getResult().getStartMillis();
            long endMillis = testCase.getResult().getEndMillis();
            long duration = endMillis - startMillis;
            DateFormat format = DateFormat.getDateTimeInstance();
            buildReport.addToReport( 
                "<tr class=\"method\"" + " met=\"" + buildReport.getIMethod() + "\">" +
                "  <td style=\"display:none;\"></td>\n" + 
                "  <td class=\"nowrap\">" + testCase.getNameUnique() + "</td>" + 
                "  <td class=\"nowrap\">" + testCase.getResult().getInstanceName() + "</td>" + 
                "  <td>" + testCase.getStepsList().size() + "</td>" + 
                "  <td><div class=\"result" + testCase.getStateResult() + "\">" + testCase.getStateResult() + "</div></td>" + 
                "  <td>" + duration + "</td>" + 
                "  <td></td>" + 
                "  <td><br><br></td>" + 
                "  <td colspan=2>" + testCase.getResult().getMethod().getDescription() + "</td>" + 
                "  <td>" + "@TIMEOUTSTEP" + format.format(new Date(startMillis)) + "</td>" + 
                "  <td>" + "@TIMEOUTSTEP" + format.format(new Date(endMillis)) + "</td>" +
                "  <td>" + testCase.getResult().getMethod().getClass() + "</td>" + 
                "</tr>\n");
            
            pintaStepsMethod(buildReport, testCase);

            //A posteriori, si en alguno de los Steps se ha detectado un timeout, se marca el método con un estilo determinado que resalte este hecho
            String fontTimeout = "<font>";
            if (buildReport.getTimeoutStep()) {
                fontTimeout = "<font class=\"timeout\">";
            }
            buildReport.setReportHTML(buildReport.getReportHTML().replaceAll("@TIMEOUTSTEP", fontTimeout));
        }
    }    
    
    private void pintaStepsMethod(BuildingReport buildReport, TestCaseTestMaker testCase) {
        String outputDir = buildReport.outputDirectory;
        for (StepTestMaker step : testCase.getStepsList()) {
            buildReport.increaseCountRows();
            buildReport.setLastStep(buildReport.getCountRows());
            buildReport.addValueToTree(buildReport.getLastMethod());

            int stepNumber = step.getPositionInTestCase();
            String ImageFileStep = FmwkTest.getPathFileEvidenciaStep(outputDir, step, TypeEvidencia.imagen);
            File indexFile = new File(ImageFileStep);
            String litPNGNewStep = "";
            String PNGNewStep = "#";
            if (indexFile.exists()) {
                litPNGNewStep = "HardCopy";
                PNGNewStep = getRelativePathEvidencia(step, TypeEvidencia.imagen);
            }

            String ErrorFileStep = FmwkTest.getPathFileEvidenciaStep(outputDir, step, TypeEvidencia.errorpage);
            indexFile = new File(ErrorFileStep);
            String linkErrorNew = "";
            if (indexFile.exists()) {
                linkErrorNew = " \\ <a href=\"" + getRelativePathEvidencia(step, TypeEvidencia.errorpage) + "\" target=\"_blank\">ErrorPage</a>";
            }

            String HARPFileStep = FmwkTest.getPathFileEvidenciaStep(outputDir, step, TypeEvidencia.harp);
            indexFile = new File(HARPFileStep);
            String linkHarpNew = "";
            if (indexFile.exists()) {
                String pathHARP = utils.obtainDNSFromFile(indexFile.getAbsolutePath(), buildReport.serverDNS).replace('\\', '/');
                linkHarpNew = " \\ <a href=\"" + ConstantesTestMaker.URL_SOFTWAREISHARD + pathHARP + "\" target=\"_blank\">NetTraffic</a>";
            }
            
            String HARFileStep = FmwkTest.getPathFileEvidenciaStep(outputDir, step, TypeEvidencia.har);
            String linkHarNew = "";
            indexFile = new File(HARFileStep);
            if (indexFile.exists()) {
                linkHarNew = " \\ <a href=\"" + getRelativePathEvidencia(step, TypeEvidencia.har) + "\" target=\"_blank\">NetJSON</a>";
            }

            String HtmlFileStep = FmwkTest.getPathFileEvidenciaStep(outputDir, step, TypeEvidencia.html);
            indexFile = new File(HtmlFileStep);
            String linkHtmlNew = "";
            if (indexFile.exists()) {
                linkHtmlNew = "<a href=\"" + getRelativePathEvidencia(step, TypeEvidencia.html) + "\">HTML Page</a>";
            }

            long diffInMillies = step.getHoraFin().getTime() - step.getHoraInicio().getTime();
            String tdClassDate = "<td>";
            if (diffInMillies > 30000) {
                tdClassDate = "<td><font class=\"timeout\">";
                buildReport.setTimeoutStep(true);
            }

            DateFormat format = DateFormat.getDateTimeInstance();
            buildReport.addToReport(
                "<tr class=\"step collapsed\"" + " met=\"" + buildReport.getIMethod() + "\">" +
                "     <td style=\"display:none;\"></td>\n" +
                "     <td class=\"nowrap\">Step " + stepNumber + "</td>" + 
                "     <td></td>" + 
                "     <td>" + step.getNumValidations() + "</td>" + 
                "     <td><div class=\"result" + step.getResultSteps() + "\">" + step.getResultSteps() + "</div></td>" + 
                "     <td>" + diffInMillies + "</td>" + 
                "     <td><a href=\"" + PNGNewStep + "\" target=\"_blank\">" + litPNGNewStep + "</a>" + linkErrorNew + linkHarpNew + linkHarNew + "</td>" + 
                "     <td>" + linkHtmlNew + "</td>" +
                "     <td>" + step.getDescripcion() + "</td>" + 
                "     <td>" + step.getResExpected() + "</td>" +
                tdClassDate + format.format(step.getHoraInicio()) + "</td>" + 
                tdClassDate + format.format(step.getHoraFin()) + "</td>" +
                "     <td></td>" +
                "</tr>\n");

            pintaValidacionesStep(buildReport, step);
        }        
    }

    private String getRelativePathEvidencia(StepTestMaker step, TypeEvidencia typeEvidencia) {
        String fileName = FmwkTest.getNameFileEvidenciaStep(step.getPositionInTestCase(), typeEvidencia);
        String testRunName = step.getTestRunParent().getName();
        String testCaseNameUnique = step.getTestCaseParent().getNameUnique();
        return ("./" + testRunName + "/" + testCaseNameUnique + "/" + fileName);
    }
    
    private void pintaValidacionesStep(BuildingReport buildReport, StepTestMaker step) {
    	
        for (int iValid = 0; iValid < listValidations.size(); iValid++) {
            HashMap<String, Object> validationH = listValidations.get(iValid);
            buildReport.increaseCountRows();
            buildReport.addValueToTree(buildReport.getLastStep());
            String validationNumber = "";
            String descriptValid = "";
            String listResultVals = "";

            validationNumber = (String) validationH.get("VALIDATION_NUMBER");
            String result = utils.getLitEstado((String) validationH.get("RESULTADO"));
            descriptValid = (String) validationH.get("DESCRIPTION");
            listResultVals = (String)validationH.get("LIST_RESULTS");
            
            //Formateamos el string con las validaciones según el resultado obtenido para cada una de ellas
            descriptValid = this.formatDescripValidations(descriptValid, listResultVals);

            buildReport.addToReport( 
            	"<tr class=\"validation collapsed\"" + " met=\"" + buildReport.getIMethod() + "\">" +
            	"    <td style=\"display:none;\"></td>\n" + 
            	"    <td class=\"nowrap\">Validation " + validationNumber + "</td>" + 
            	"    <td></td>" + 
            	"    <td></td>" + 
            	"    <td><div class=\"result" + result + "\">" + result + "</div></td>" + 
            	"    <td></td>" + 
            	"    <td></td>" + 
            	"    <td></td>" + 
            	"    <td>" + descriptValid + "</td>" + 
            	"    <td></td>" +
            	"    <td></td>" + 
                "    <td></td>" +                        
                "    <td></td>" + 
                "</tr>\n");
        }
    }
    
    /**
     * Pinta los tags de cierre de la tabla y el html
     * @param buildReport objeto en el que vamos construyendo el report
     */    
    public void pintaCierreHTML(BuildingReport buildReport) {
        buildReport.addToReport(
        	"</tbody>" + 
            "</table><br />\n" +
            "</body>\n" +
            "</html>\n");
    }    
    
    public void createFileReportHTML(BuildingReport buildReport) {
        String file = FmwkTest.getPathReportHTML(buildReport.getOutputDirectory());
        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF8"))) {
            out.write(buildReport.getReportHTML());
            out.close();
        } 
        catch (Exception e) {
            pLogger.fatal("Problem creating file ReportHTML", e);
        } 
    }
    
    /**
     * Formateamos el string con las validaciones según el resultado obtenido para cada una de ellas
     */
    //TODO refactor en una clase aparte que genere un CheckResults
    private String formatDescripValidations(String descripValidOrig, String listResultVals) {
        String descripValidReturn = descripValidOrig;
        if (listResultVals.compareTo("")!=0) {
            List<String> listResValidacs = Arrays.asList(listResultVals.split("\\s*,\\s*"));
            int iValidac = 1;
            if (listResValidacs.size()>1) {
	            for (String resValidac : listResValidacs) {
	                int resValidacInt = Integer.valueOf(resValidac).intValue();
	                
	                //Formatear el texto de la validación en el color correspondiente
	                if (resValidacInt!=State.Ok.getIdNumerid()) {
	                    String color = State.getState(resValidacInt).getColorCss();
	                    
	                    //Incrustamos el estilo con el color en la validación del descripion
	                    int iniValidac = descripValidOrig.indexOf(iValidac + ")"); 
	                    if (iniValidac > -1) {
	                        //Obtenemos el literal correspondiente a la validación
	                        String restoDescripValid = descripValidOrig.substring(iniValidac);
	                        int finValidac = restoDescripValid.indexOf(iValidac + 1 + ")");
	                        if (finValidac < 0) {
	                            finValidac = restoDescripValid.indexOf("<br>");
	                        }
	                        if (finValidac < 0) {
	                            finValidac = restoDescripValid.length();
	                        }
	                        if (finValidac > 0) {
	                            //Finalmente sustituímos el literal con la validación por el mismo literal con el tag de estilo/color correspondiente
	                            String validacStr = restoDescripValid.substring(0, finValidac);
	                            String validacStrNew = "<validac style=\"color:" + color + "\">" + validacStr + "</validac>";
	                            descripValidReturn = descripValidReturn.replace(validacStr, validacStrNew);
	                        }
	                    }
	                }
	                
	                iValidac+=1;
	            }
            } else {
            	//1 sola validación
            	int intValidac = Integer.valueOf(listResValidacs.get(0));
            	State stateValidac = State.getState(intValidac);
            	if (stateValidac!=State.Ok) {
                    String color = stateValidac.getColorCss();
                    descripValidReturn = "<validac style=\"color:" + color + "\">" + descripValidOrig + "</validac>";
            	}
            }
        }
        
        return descripValidReturn;
    }

    /**
     * Objecto utilizado para ir construyendo el reportHTML
     * @author jorge.munoz
     *
     */
    private class BuildingReport {
        //Report HTML
        String reportHTML = "";
        
        //Lista de valores que marcan la jerarquía en la tabla de cada una de sus filas. Tiene formato del tipo [0,1,1,3,3,1,6,6,1,1,10,10,1,13,13,0,16,16]
        //Hay que tener en cuenta lo siguiente:
        //- Cada valor hace referencia a la jerarquía en la tabla de la fila 
        //- Si un valor es superior al anterior entonces la fila es hija de la anterior
        //- Si un valor es igual o superior al anterior entonces la fila es hermana de la anterior fila con el mismo valor
        String valuesTree = "";
        
        //Directorio dónde se grabará el report
        String outputDirectory = "";
        
        //Contador de filas
        int countRows = 0;
        
        //Contador del método en curso (se utiliza para el atributo "met" de cada una de las filas de la tabla)
        int iMethod = 0;
        
        //Valor correspondiente a la posición en la tabla del último testrun (empezando desde el 1)
        int iLastTestRun = 0;
        
        //Valor correspondiente a la posición en la tabla del último método (empezando desde el 1)
        int iLastMethod = 0;
        
        //Valor correspondiente a la posición en la tabla del último step (empezando desde el 1)
        int iLastStep = 0;
        
        //Indicador que indica si se ha producido un timeout en alguno de los pasos asociados al método en curso
        boolean timeoutStep = false;
        
        String serverDNS = "";
        
        public BuildingReport(String outputDirectory) {
            this.outputDirectory = outputDirectory;
        }
        
        public String getReportHTML() {
            return this.reportHTML;
        }
        
        public void setReportHTML(String reportHTML) {
            this.reportHTML = reportHTML;
        }
        
        public void addToReport(String strToAdd) {
            this.reportHTML = this.reportHTML + strToAdd;
        }
        
        public void addValueToTree(int value) {
            
            if ("".compareTo(this.valuesTree)==0) {
                this.valuesTree = this.valuesTree + value;
            } else {
                this.valuesTree = this.valuesTree + "," + value;
            }
        }
        
        public String getOutputDirectory () {
            return this.outputDirectory;
        }

        public int getCountRows() {
            return this.countRows;
        }
        
        public void increaseCountRows() {
            this.countRows+=1;
        }
        
        public int getIMethod() {
           return this.iMethod;
        }
        
        public void setIMethod(int iMethod) {
            this.iMethod = iMethod;
        }
        
        public int getLastTestRun() {
            return this.iLastTestRun;
        }
        
        public void setLastTestRun(int iLastTestRun) {
            this.iLastTestRun = iLastTestRun;
        }
        
        public int getLastMethod() {
            return this.iLastMethod;
        }
        
        public void setLastMethod(int iLastMethod) {
            this.iLastMethod = iLastMethod;
        }
        
        public int getLastStep() {
            return this.iLastStep;
        }
        
        public void setLastStep(int iLastStep) {
            this.iLastStep = iLastStep;
        }
        
        public boolean getTimeoutStep() {
            return this.timeoutStep;
        }
        
        public void setTimeoutStep(boolean timeoutStep) {
            this.timeoutStep = timeoutStep;
        }
        
        //Función que modifica el reportHTML sustituyendo un determinado tag por el valor completo de valuesTree
        public void replaceTagWithTree(String tagHTML) {
            this.reportHTML = this.reportHTML.replaceAll(tagHTML, this.valuesTree);
        }
    }    
}


