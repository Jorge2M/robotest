package org.pruebasws.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeSet;

import com.mng.robotest.test80.Test80mng;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.testmaker.conf.Channel;

import org.pruebasws.jdbc.Connector;
import org.pruebasws.jdbc.to.ApplicationSuite;
import org.pruebasws.jdbc.to.BrowserSuite;
import org.pruebasws.jdbc.to.SuiteTestData;
import org.pruebasws.jdbc.to.VersionSuite;

@SuppressWarnings("javadoc")
public class TestsDAO {

    public enum DataTSuiteChangeable {browser, application, version, net, url, countrys, tcases, payments}
    
    public static String SQLSelectDataTests = 
    "SELECT SUITE, CHANNEL, VERSION, DESCRIPCION, APPLICATION, BROWSER, URLBASE, NETTRAFIC, FILTRO_PAIS, LISTA_PAISES, FILTRO_LINEAS, FILTRO_PAGOS, LISTA_PAGOS, FILTRO_TCASES, LISTA_TCASES " + 
    "  FROM TEST_SUITES " + 
    " WHERE " +
    " SUITE LIKE ? AND " + 
    " CHANNEL LIKE ? " + 
    " ORDER BY POSICION, CHANNEL ASC, SUITE DESC";
    
    public static String SQLSelectVersionsSuite =
    "SELECT SUITE, VERSION, DESCRIPTION, CHANNEL_LIST " +
    "  FROM VERSIONS_SUITE " +
    "WHERE SUITE = ?";
    
    public static String SQLSelectApplicationsSuite =
    "SELECT SUITE, APPLICATION, CHANNEL_LIST " +
    "  FROM APPLICATIONS_SUITE " +
    "WHERE SUITE = ?";    
    
    public static String SQLSelectBrowsersSuite =
    "SELECT SUITE, BROWSER, CHANNEL_LIST " +
    "  FROM BROWSERS_SUITE " +
    "WHERE SUITE = ?";    
    
    public static String SQLUpdateBrowserTestSuite = 
    "UPDATE TEST_SUITES " +
    "SET BROWSER = ? " +
    "WHERE " +
    "SUITE = ? AND CHANNEL = ?";
    
    public static String SQLUpdateApplicationTestSuite = 
    "UPDATE TEST_SUITES " +
    "SET APPLICATION = ? " +
    "WHERE " +
    "SUITE = ? AND CHANNEL = ?";
    
    public static String SQLUpdateNetTrafficTestSuite = 
    "UPDATE TEST_SUITES " +
    "SET NETTRAFIC = ? " +
    "WHERE " +
    "SUITE = ? AND CHANNEL = ?";    
    
    public static String SQLUpdateVersionTestSuite = 
    "UPDATE TEST_SUITES " +
    "SET VERSION = ? " +
    "WHERE " +
    "SUITE = ? AND CHANNEL = ?";
    
    public static String SQLUpdateURLBaseTestSuite = 
    "UPDATE TEST_SUITES " +
    "SET URLBASE = ? " +
    "WHERE " +
    "SUITE = ? AND CHANNEL = ?";
    
    public static String SQLUpdateCountrysTestSuite = 
    "UPDATE TEST_SUITES " +
    "SET LISTA_PAISES = ? " +
    "WHERE " +
    "SUITE = ? AND CHANNEL = ?";
    
    public static String SQLUpdateTCasesTestSuite = 
    "UPDATE TEST_SUITES " +
    "SET LISTA_TCASES = ? " +
    "WHERE " +
    "SUITE = ? AND CHANNEL = ?";    
    
    public static String SQLUpdatePaymentsTestSuite = 
    "UPDATE TEST_SUITES " +
    "SET LISTA_PAGOS = ? " +
    "WHERE " +
    "SUITE = ? AND CHANNEL = ?";    
    
    public static String SQLSelectLinesShop = 
    "SELECT ID FROM LINES_SHOP";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        return getConnection(true/*forReadOnly*/);
    }
    
    public static Connection getConnection(boolean forReadOnly) throws ClassNotFoundException, SQLException {
        return Connector.getConnection(forReadOnly);
    }

    public static ArrayList<SuiteTestData> getListaTestSuites() throws Exception {
        return getListaTestSuites("%", "%");
    }
    
    /**
     * Obtiene toda la información de todos los scripts definidos para la aplicación
     */
    public static ArrayList<SuiteTestData> getListaTestSuites(String filterSuite, String filterChannel) throws Exception {
        ArrayList<SuiteTestData> listSuites = new ArrayList<>();
        ArrayList<String> listLineas = getListLinesShop();
        try (Connection conn = getConnection();
            PreparedStatement select = conn.prepareStatement(SQLSelectDataTests)) {            
            select.setString(1, filterSuite);
            select.setString(2, filterChannel);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                    SuiteTestData suiteTest = new SuiteTestData();
                    String suiteId = resultado.getString("SUITE");
                    suiteTest.setSuite(suiteId);
                    suiteTest.setChannel(resultado.getString("CHANNEL"));
                    suiteTest.setIdVersionActual(resultado.getString("VERSION"));
                    suiteTest.setVersionList(getListVersionsTestSuite(suiteId));
                    suiteTest.setDescription(resultado.getString("DESCRIPCION"));
                    suiteTest.setIdApplicationActual(resultado.getString("APPLICATION"));
                    suiteTest.setListApplications(getListApplicationsTestSuite(suiteId));
                    suiteTest.setIdBrowser(resultado.getString("BROWSER"));
                    suiteTest.setListBrowsers(getListBrowsersTestSuite(suiteId));
                    suiteTest.setUrlBase(resultado.getString("URLBASE"));
                    suiteTest.setNettrafic(resultado.getString("NETTRAFIC"));
                    suiteTest.setFiltroPaises(resultado.getInt("FILTRO_PAIS"));
                    suiteTest.setListPaises(resultado.getString("LISTA_PAISES"));
                    suiteTest.setFiltroLineas(resultado.getInt("FILTRO_LINEAS"));
                    suiteTest.setFiltroPagos(resultado.getInt("FILTRO_PAGOS"));
                    suiteTest.setListPagos(resultado.getString("LISTA_PAGOS"));
                    suiteTest.setFiltroTCases(resultado.getInt("FILTRO_TCASES"));                    
                    suiteTest.setListTCases(resultado.getString("LISTA_TCASES"));
                    if (suiteTest.getFiltroLineas()==1)
                        suiteTest.setListLineas(String.join(",", listLineas));
                    
                    listSuites.add(suiteTest);
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return listSuites;
    }
    
    public static ArrayList<VersionSuite> getListVersionsTestSuite(String testSuiteId) {
        ArrayList<VersionSuite> listVersionsSuite = new ArrayList<>();
        try (Connection conn = getConnection();
            PreparedStatement select = conn.prepareStatement(SQLSelectVersionsSuite)) {            
            select.setString(1, testSuiteId);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                    VersionSuite versionSuite = new VersionSuite();
                    versionSuite.setSuite(resultado.getString("SUITE"));
                    versionSuite.setVersion(resultado.getString("VERSION"));
                    versionSuite.setDescription(resultado.getString("DESCRIPTION"));
                    versionSuite.setListChannels(resultado.getString("CHANNEL_LIST"));
                    listVersionsSuite.add(versionSuite);
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return listVersionsSuite;
    }
    
    public static ArrayList<ApplicationSuite> getListApplicationsTestSuite(String testSuiteId) {
        ArrayList<ApplicationSuite> listApplicationsSuite = new ArrayList<>();
        try (Connection conn = getConnection();
            PreparedStatement select = conn.prepareStatement(SQLSelectApplicationsSuite)) {            
            select.setString(1, testSuiteId);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                    ApplicationSuite applicationSuite = new ApplicationSuite();
                    applicationSuite.setSuite(resultado.getString("SUITE"));
                    applicationSuite.setApplication(resultado.getString("APPLICATION"));
                    applicationSuite.setListChannels(resultado.getString("CHANNEL_LIST"));
                    listApplicationsSuite.add(applicationSuite);
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return listApplicationsSuite;
    }    
    
    public static ArrayList<BrowserSuite> getListBrowsersTestSuite(String testSuiteId) {
        ArrayList<BrowserSuite> listBrowsersSuite = new ArrayList<>();
        try (Connection conn = getConnection();
            PreparedStatement select = conn.prepareStatement(SQLSelectBrowsersSuite)) {            
            select.setString(1, testSuiteId);
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next()) {
                    BrowserSuite browserSuite = new BrowserSuite();
                    browserSuite.setSuite(resultado.getString("SUITE"));
                    browserSuite.setBrowser(resultado.getString("BROWSER"));
                    browserSuite.setListChannels(resultado.getString("CHANNEL_LIST"));
                    listBrowsersSuite.add(browserSuite);
                }
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return listBrowsersSuite;
    }    
    
    public static ArrayList<String> getListLinesShop() {
        ArrayList<String> listLinesShop = new ArrayList<>();
        try (Connection conn = getConnection();
            PreparedStatement select = conn.prepareStatement(SQLSelectLinesShop)) {            
            try (ResultSet resultado = select.executeQuery()) {
                while (resultado.next())
                    listLinesShop.add(resultado.getString("ID"));
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        
        return listLinesShop;
    }    
    
    public static TreeSet<String> getListPagos(String codCountrysCommaSeparated, Channel channel, AppEcom appE) 
    throws Exception {
        if ("".compareTo(codCountrysCommaSeparated)==0 || 
        	"*".compareTo(codCountrysCommaSeparated)==0 || 
        	"X".compareTo(codCountrysCommaSeparated)==0) {
            return (Test80mng.getListPagoFilterNames(channel, appE, false/*isEmpl*/));
        }
            
        return (Test80mng.getListPagoFilterNames(codCountrysCommaSeparated, channel, appE, false));
    }
    
    /**
     * @return 1 row/s updated, 0 no rows updated
     */
    public static int updateDataTestSuite(String suite, String channel, DataTSuiteChangeable dataToChange, String newData, String[] newDataMOption) {
        String SQLUpdateToExecute = "";
        String dataToUpdate = newData;
        switch (dataToChange) {
        case browser:
            SQLUpdateToExecute = SQLUpdateBrowserTestSuite;
            break;
        case application:
            SQLUpdateToExecute = SQLUpdateApplicationTestSuite;
            break;
        case net:
            SQLUpdateToExecute = SQLUpdateNetTrafficTestSuite;
            break;            
        case version:
            SQLUpdateToExecute = SQLUpdateVersionTestSuite;
            break;
        case url:
            SQLUpdateToExecute = SQLUpdateURLBaseTestSuite;
            break;
        case countrys:
            SQLUpdateToExecute = SQLUpdateCountrysTestSuite;
            break;
        case payments:
            if (newDataMOption!=null && newDataMOption.length>0)
                dataToUpdate = String.join(",", newDataMOption);
            
            SQLUpdateToExecute = SQLUpdatePaymentsTestSuite;
            break;            
        case tcases:
            if (newDataMOption!=null && newDataMOption.length>0)
                dataToUpdate = String.join(",", newDataMOption);
            
            SQLUpdateToExecute = SQLUpdateTCasesTestSuite;
            break;            
        default:
            break;
        }
        
        try (Connection conn = getConnection(false/*forReadOnly*/)) {
            try (PreparedStatement updateData = conn.prepareStatement (SQLUpdateToExecute)) {
                updateData.setString(1, dataToUpdate) ;
                updateData.setString(2, suite);
                updateData.setString(3, channel); 
                return (updateData.executeUpdate());
            } 
            catch (SQLException ex) {
                throw new RuntimeException(ex);
            }          
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
