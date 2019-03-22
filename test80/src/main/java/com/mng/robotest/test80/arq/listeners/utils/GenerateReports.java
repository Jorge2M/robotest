package com.mng.robotest.test80.arq.listeners.utils;

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

import com.mng.robotest.test80.arq.jdbc.dao.MethodsDAO;
import com.mng.robotest.test80.arq.jdbc.dao.StepsDAO;
import com.mng.robotest.test80.arq.jdbc.dao.SuitesDAO;
import com.mng.robotest.test80.arq.jdbc.dao.TestRunsDAO;
import com.mng.robotest.test80.arq.jdbc.dao.ValidationsDAO;
import com.mng.robotest.test80.arq.jdbc.to.Suite;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.utils;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.indexSuite;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest.TypeEvidencia;
import com.mng.robotest.test80.arq.utils.otras.Constantes;


public class GenerateReports extends EmailableReporter {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    @Override
    public void generateReport(final List<XmlSuite> xmlSuites, final List<ISuite> suites, final String outputDirectory) {
        // Esto nos generará el emailable-report.html junto con todos sus componentes
        super.generateReport(xmlSuites, suites, outputDirectory);
        
        ITestContext context = null;
        Map<String, ISuiteResult> r = suites.get(0).getResults();
        for (ISuiteResult r2 : r.values())
            context = r2.getTestContext();

        if (context!=null) {
            indexSuite suite = new indexSuite(context.getCurrentXmlTest().getParameter(Constantes.paramSuiteExecInCtx), context.getSuite().getName());
            try {
                //this.generateReportHTML(suite, utils.getOutDirectoryFin(context), context);
                this.generateReportHTML(suite, outputDirectory, context);
            } 
            catch (Exception e) {
                pLogger.fatal("Problem generating ReportHTML", e);
            }
        }
    }

    private void generateReportHTML(indexSuite suite, String outputDirectory, ITestContext context) throws Exception {
        BuildingReport buildReport = new BuildingReport(outputDirectory);
        buildReport.serverDNS = context.getCurrentXmlTest().getParameter(Constantes.paramApplicationDNS);

        Suite suiteBD = SuitesDAO.getSuite(suite);
        pintaCabeceraHTML(buildReport);
        pintaHeadersTableMain(buildReport, suiteBD, context);        
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
    public void pintaHeadersTableMain(BuildingReport buildReport, Suite suiteBD, ITestContext context) {
        buildReport.addToReport(
        	"<table id=\"tableMain\" class=\"tablemain\">" + 
            "<thead>\n" + 
            "  <tr id=\"header1\">\n" + 
            "    <th colspan=\"13\" class=\"head\">" + 
            "      <div id=\"titleReport\">" + suiteBD.getSuiteName() + " - " + context.getName() + " (Suite Date: " + suiteBD.getIdExecution() + ")" +
            "        <span id=\"descrVersion\">" + context.getSuite().getXmlSuite().getParameter(Constantes.paramVersionSuite) + "</span>" +
            "        <span id=\"browser\">" + (String) context.getAttribute("bpath") + "</span>" + 
            "        <span id=\"url\"><a id=\"urlLink\" href=\"" + (String) context.getAttribute("appPath") + "\">" + (String) context.getAttribute("appPath") + "</a></span>" + 
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
        buildReport.addToReport("  <a href=\"" + fmwkTest.log4jFileName + "\" target=\"_blank\" class=\"linkTestNG\">" + fmwkTest.log4jFileName + "</a>");
        buildReport.addToReport("</div>");        
        buildReport.addToReport("<br>\n");
        buildReport.addToReport("<br>\n");
    }
    
    /**
     * Añadimos al report los métodos asociados a un determinado testrun
     * @param buildReport objeto en el que vamos construyendo el report
     * @param suite identificador de la suite
     */    
    private void pintaTestRunsSuite(BuildingReport buildReport, indexSuite suiteId) {
        Vector<HashMap<String, Object>> listTestRuns = TestRunsDAO.listTestRunsSuite(suiteId);
        buildReport.addToReport("<tbody id=\"treet2\">\n");
        for (int iTestRun = 0; iTestRun < listTestRuns.size(); iTestRun++) {
            
            //Obtenemos los datos de la tabla
            HashMap<String, Object> testRunHash = listTestRuns.get(iTestRun);

            //Aumentamos el contador de filas
            buildReport.increaseCountRows();
            
            //Todos loa valores de tipo método tendrán un valor 0 (son siempre padres de steps y hermanos entre ellos)
            buildReport.addValueToTree(0);

            //Marcamos como último testrun el correspondiente al contador de filas
            buildReport.setLastTestRun(buildReport.getCountRows());

            //Variables registros m�todo
            Date dateInitTestRun = null;
            Date dateFinTestRun = null;
            String timeTestRun = "";
 
            String testMeth = (String) testRunHash.get("TEST");
            String deviceMeth = (String) testRunHash.get("DEVICE");
            String numberMethods = (String) testRunHash.get("NUMBER_METHODS");
            
            String resultTestRun = utils.getLitEstadoMethod((String) testRunHash.get("RESULT_SCRIPT"), (String)testRunHash.get("RESULT_TNG"));
            dateInitTestRun = new Date(Long.parseLong((String) testRunHash.get("INICIO")));
            dateFinTestRun = new Date(Long.parseLong((String) testRunHash.get("FIN")));
            timeTestRun = (String) testRunHash.get("TIME_MS");

            DateFormat format = DateFormat.getDateTimeInstance();
            buildReport.addToReport( 
            	"<tr class=\"testrun\">" +
            	"  <td style=\"display:none;\"></td>\n" + 
            	"  <td class=\"nowrap\">" + testMeth + "</td>" + 
            	"  <td class=\"nowrap\"><div id=\"device\">" + deviceMeth + "</div></td>" + 
            	"  <td>" + numberMethods + "</td>" + 
            	"  <td><div class=\"result" + resultTestRun + "\">" + resultTestRun + "</div></td>" + 
            	"  <td>" + timeTestRun + "</td>" + "               <td></td>" + 
            	"  <td><br><br></td>" + 
            	"  <td></td>" + 
            	"  <td></td>" +
            	"  <td>" + format.format(dateInitTestRun) + "</td>" + 
            	"  <td>" + format.format(dateFinTestRun) + "</td>" +
            	"  <td></td>" + 
            	"</tr>\n");
            
            //Pintamos los METHODS / STEPS / VALIDATIONS asociados al Method
            pintaMethodsTestRun(buildReport, suiteId, testMeth);
        }
    }    
    
    /**
     * Añadimos al report los métodos asociados a un determinado testrun
     * @param buildReport objeto en el que vamos construyendo el report
     * @param suite identificador de la suite
     */    
    private void pintaMethodsTestRun(BuildingReport buildReport, indexSuite suiteId, String testRun) {
        Vector<HashMap<String, Object>> listMethods = MethodsDAO.listMethodsTestRun(suiteId, testRun);
        for (int iMethod = 0; iMethod < listMethods.size(); iMethod++) {
            //Actualizamos el iMethod que indica el contador correspondiente último método tratado 
            buildReport.setIMethod(iMethod);
            
            //Inicialmente el indicador de steps con timeout
            buildReport.setTimeoutStep(false);

            //Obtenemos los datos de la tabla
            HashMap<String, Object> methodHash = listMethods.get(buildReport.getIMethod());

            //Aumentamos el contador de filas
            buildReport.increaseCountRows();
            
            //Todos los methods serán hermanos (mismo 'valueTree') e hijos del último testrun (posición en la tabla del último testrun, que equivale al valor+1 del valor asignado al último testrun)
            buildReport.addValueToTree(buildReport.getLastTestRun());
            
            //Marcamos como último método el correspondiente al contador de filas
            buildReport.setLastMethod(buildReport.getCountRows());

            // Variables registros m�todo
            Date dateInitMeth = null;
            Date dateFinMeth = null;
            String timeNewMeth = "";
            String classMeth = "";
 
            String testMeth = (String) methodHash.get("TEST");
            String metodoMeth = (String) methodHash.get("METHOD");
            String descrMeth = (String) methodHash.get("DESCRIPTION");
            String instanceMeth = (String) methodHash.get("INSTANCIA");
            String numberSteps = (String) methodHash.get("NUMBER_STEPS");
            
            String resultNewMeth = utils.getLitEstadoMethod((String) methodHash.get("RESULT_SCRIPT"), (String) methodHash.get("RESULT_TNG"));
            dateInitMeth = new Date(Long.parseLong((String) methodHash.get("INICIO")));
            dateFinMeth = new Date(Long.parseLong((String) methodHash.get("FIN")));
            timeNewMeth = (String) methodHash.get("TIME_MS");
            classMeth = (String) methodHash.get("CLASS_SIGNATURE");

            DateFormat format = DateFormat.getDateTimeInstance();
            buildReport.addToReport( 
                "<tr class=\"method\"" + " met=\"" + buildReport.getIMethod() + "\">" +
                "  <td style=\"display:none;\"></td>\n" + 
                "  <td class=\"nowrap\">" + metodoMeth + "</td>" + 
                "  <td class=\"nowrap\">" + instanceMeth + "</td>" + 
                "  <td>" + numberSteps + "</td>" + 
                "  <td><div class=\"result" + resultNewMeth + "\">" + resultNewMeth + "</div></td>" + 
                "  <td>" + timeNewMeth + "</td>" + 
                "  <td></td>" + 
                "  <td><br><br></td>" + 
                "  <td colspan=2>" + descrMeth + "</td>" + 
                "  <td>" + "@TIMEOUTSTEP" + format.format(dateInitMeth) + "</td>" + 
                "  <td>" + "@TIMEOUTSTEP" + format.format(dateFinMeth) + "</td>" +                        
                "  <td>" + classMeth + "</td>" + 
                "</tr>\n");
            
            //Pintamos los STEPS / VALIDATIONS asociados al Method
            pintaStepsMethod(buildReport, suiteId, testMeth, metodoMeth);

            //A posteriori, si en alguno de los Steps se ha detectado un timeout, se marca el método con un estilo determinado que resalte este hecho
            String fontTimeout = "<font>";
            if (buildReport.getTimeoutStep()) {
                fontTimeout = "<font class=\"timeout\">";
            }
            buildReport.setReportHTML(buildReport.getReportHTML().replaceAll("@TIMEOUTSTEP", fontTimeout));
        }
    }    
    
    /**
     * Añadimos al report los steps asociados a un determinado method (suite - testrun - method)
     * @param buildReport objeto en el que vamos construyendo el report
     * @param suite identificador de la suite
     * @param metodo identificador del method
     */
    private void pintaStepsMethod(BuildingReport buildReport, indexSuite suiteId1, String testRun, String metodo) {
        //Obtenemos la lista de pasos de un method
        Vector<HashMap<String, Object>> listSteps = StepsDAO.listStepsMethod(suiteId1, testRun, metodo);
        
        String outputDir = buildReport.outputDirectory;
        for (int iStep = 0; iStep < listSteps.size(); iStep++) {
            HashMap<String, Object> stepHash = listSteps.get(iStep);

            //Aumentamos el contador de filas
            buildReport.increaseCountRows();
            
            //Almacenamos la posición asociada al último step tratado
            buildReport.setLastStep(buildReport.getCountRows());
            
            //Todas los steps serán hermanos (mismo 'valueTree') e hijos del último method (posición en la tabla del último method, que equivale al valor+1 del valor asignado al último method)
            buildReport.addValueToTree(buildReport.getLastMethod());
            
            // Variables registros step
            Date dateInitSt = null;
            Date dateFinSt = null;
            String stepNumber = "";
            String stepValidations = "";
            String resuNewStep = "";
            String timeNewStep = "";
            String PNGNewStep = "";
            String litPNGNewStep = "";
            String linkErrorNew = "";
            String linkHtmlNew = "";
            String linkHarpNew = "";
            String linkHarNew = "";
            String description = "";
            String resExpected = "";
            String typePagNew = "";
            String testNewLit = "";

            stepValidations = (String) stepHash.get("NUM_VALIDATIONS");
            stepNumber = (String) stepHash.get("STEP_NUMBER");
            dateInitSt = new Date(Long.parseLong((String) stepHash.get("INICIO")));
            dateFinSt = new Date(Long.parseLong((String) stepHash.get("FIN")));
            resuNewStep = utils.getLitEstado((String) stepHash.get("RESULTADO"));
            timeNewStep = (String) stepHash.get("TIME_MS");
            typePagNew = (String) stepHash.get("TYPE_PAGE");
            testNewLit = (String) stepHash.get("TEST");

            if (Integer.parseInt(typePagNew) == Constantes.CONST_HTML) {
                // Comprobación existencia hardcopy página
                String methodName = (String)stepHash.get("METHOD");
                String ImageFileStep = fmwkTest.getPathFileEvidenciaStep(outputDir, testNewLit, methodName, Integer.parseInt(stepNumber), TypeEvidencia.imagen);
                String HtmlFileStep = fmwkTest.getPathFileEvidenciaStep(outputDir, testNewLit, methodName, Integer.parseInt(stepNumber), TypeEvidencia.html);
                String ErrorFileStep = fmwkTest.getPathFileEvidenciaStep(outputDir, testNewLit, methodName, Integer.parseInt(stepNumber), TypeEvidencia.errorpage);
                String HARPFileStep = fmwkTest.getPathFileEvidenciaStep(outputDir, testNewLit, methodName, Integer.parseInt(stepNumber), TypeEvidencia.harp);
                String HARFileStep = fmwkTest.getPathFileEvidenciaStep(outputDir, testNewLit, methodName, Integer.parseInt(stepNumber), TypeEvidencia.har);
                
                File indexFile = new File(ImageFileStep);
                if (indexFile.exists()) {
                    litPNGNewStep = "HardCopy";
                    PNGNewStep = getRelativePathEvidencia(testRun, methodName, Integer.parseInt(stepNumber), TypeEvidencia.imagen);
                } else {
                    litPNGNewStep = "";
                    PNGNewStep = "#";
                }

                // Comprobación existencia página error errorPagefaces
                indexFile = new File(ErrorFileStep);
                if (indexFile.exists())
                    linkErrorNew = " \\ <a href=\"" + getRelativePathEvidencia(testRun, methodName, Integer.parseInt(stepNumber), TypeEvidencia.errorpage) + "\" target=\"_blank\">ErrorPage</a>";
                
                // Comprobación existencia de archivo .HARP
                indexFile = new File(HARPFileStep);
                if (indexFile.exists()) {
                    String pathHARP = utils.obtainDNSFromFile(indexFile.getAbsolutePath(), buildReport.serverDNS).replace('\\', '/');
                    linkHarpNew = " \\ <a href=\"" + Constantes.URL_SOFTWAREISHARD + pathHARP + "\" target=\"_blank\">NetTraffic</a>";
                }
                
                // Comprobación existencia de archivo .HAR
                indexFile = new File(HARFileStep);
                if (indexFile.exists())
                    linkHarNew = " \\ <a href=\"" + getRelativePathEvidencia(testRun, methodName, Integer.parseInt(stepNumber), TypeEvidencia.har) + "\" target=\"_blank\">NetJSON</a>";
                
                // Comprobación de la existencia del archivo .HTML
                indexFile = new File(HtmlFileStep);
                if (indexFile.exists())
                    linkHtmlNew = "<a href=\"" + getRelativePathEvidencia(testRun, methodName, Integer.parseInt(stepNumber), TypeEvidencia.html) + "\">HTML Page</a>";
            }

            description = (String) stepHash.get("DESCRIPTION");
            resExpected = (String) stepHash.get("RES_EXPECTED");

            Calendar fechaInitSt = Calendar.getInstance();
            fechaInitSt.setTime(dateInitSt);

            Calendar fechaFinSt = Calendar.getInstance();
            fechaFinSt.setTime(dateFinSt);

            //Si observamos que se ha producido un tiempo > 30 segundos en algún step lo marcamos
            long diffInMillies = dateFinSt.getTime() - dateInitSt.getTime();
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
                "     <td>" + stepValidations + "</td>" + 
                "     <td><div class=\"result" + resuNewStep + "\">" + resuNewStep + "</div></td>" + 
                "     <td>" + timeNewStep + "</td>" + 
                "     <td><a href=\"" + PNGNewStep + "\" target=\"_blank\">" + litPNGNewStep + "</a>" + linkErrorNew + linkHarpNew + linkHarNew + "</td>" + 
                "     <td>" + linkHtmlNew + "</td>" +
                "     <td>" + description + "</td>" + 
                "     <td>" + resExpected + "</td>" +
                tdClassDate + /* litFechaInitSt */format.format(dateInitSt) + "</td>" + 
                tdClassDate + /* litFechaFinSt */format.format(dateFinSt) + "</td>" +                        
                "     <td></td>" +
                "</tr>\n");

            pintaValidacionesStep(buildReport, suiteId1, testRun, metodo, stepNumber);
        }        
        
    }

    private String getRelativePathEvidencia(String testRunName, String methodNameWithFactory, int stepNumber, TypeEvidencia typeEvidencia) {
        String fileName = fmwkTest.getNameFileEvidenciaStep(stepNumber, typeEvidencia);
        return ("./" + testRunName + "/" + methodNameWithFactory + "/" + fileName);
    }
    
    /**
     * Añadimos al report las validaciones asociadas a un determinado step (suite - testrun - method - step)
     * @param buildReport objeto en el que vamos construyendo el report
     * @param suite identificador de la suite
     * @param metodo identificador del method
     * @param stepNumber identifocador del step
     */
    private void pintaValidacionesStep(BuildingReport buildReport, indexSuite suiteId, String testRun, String metodo, String stepNumber) {
        Vector<HashMap<String, Object>> listValidations = ValidationsDAO.listValidationsStep(suiteId, testRun, metodo, stepNumber);
        for (int iValid = 0; iValid < listValidations.size(); iValid++) {
            HashMap<String, Object> validationH = listValidations.get(iValid);
            
            //Aumentamos el contador de filas
            buildReport.increaseCountRows();
            
            //Todas las validaciones serán hermanas (mismo 'valueTree') e hijas del último paso (posición en la tabla del último step, que equivale al valor+1 del valor asignado al último step)
            buildReport.addValueToTree(buildReport.getLastStep());

            // Variables registros validation
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
        String file = fmwkTest.getPathReportHTML(buildReport.getOutputDirectory());
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
            }
            else {
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
            
            if ("".compareTo(this.valuesTree)==0)
                this.valuesTree = this.valuesTree + value;
            else
                this.valuesTree = this.valuesTree + "," + value;
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


