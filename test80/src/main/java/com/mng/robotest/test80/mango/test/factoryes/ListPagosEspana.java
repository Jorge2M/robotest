package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.appshop.CompraFact;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum;
import com.mng.robotest.test80.mango.test.data.ChannelEnum;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

import org.testng.ITestContext;

@SuppressWarnings("javadoc")
public class ListPagosEspana {
	
	Pais espana = null;
	IdiomaPais castellano = null;
	Pais francia = null;
	IdiomaPais frances = null;
	
    boolean usrReg = true;
    boolean empleado = true;
    boolean testVale = true;
    boolean manyArticles = true;
    boolean twoArticles = true;
    boolean anulPedido = true;
	
    @SuppressWarnings("unused")
    @Factory
    @Test (
        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true, priority=1, 
        description="Factoría que incluye varios tests por cada uno de los pagos de España variando los flags de usuario registrado, empleado y métodos de envío")
    @Parameters({"AppEcom", "Channel"}) 
    public Object[] COM010_PagoFactory(String appStr, String channelStr, ITestContext ctx) throws Exception {
        ArrayList<Object> listTests = new ArrayList<>();
        AppEcom appE = AppEcomEnum.getAppEcom(appStr);
        Channel channel = ChannelEnum.getChannel(channelStr);
        try {
        	getDataCountrys();
        	if (appE!=AppEcom.votf) {
        		createTestPagosEspana(listTests, appE, channel, ctx);
        		createTestPagosFrancia(listTests, appE, channel, ctx);
        	}
        	else
        		createTestPagosVotf(listTests, appE, channel, ctx);
        }
        catch (Throwable e) {
            e.printStackTrace();
            throw e;
        }
    
        return (listTests.toArray(new Object[listTests.size()]));
    }
    
    private void getDataCountrys() throws Exception {
        //Obtenemos la lista de países como lista de enteros
        Integer codEspanya = Integer.valueOf(1);
        Integer codFrancia = Integer.valueOf(11);
        List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya, codFrancia))); 
        this.espana = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
        this.francia = UtilsMangoTest.getPaisFromCodigo("011", listaPaises);
        this.castellano = espana.getListIdiomas().get(0);
        this.frances = francia.getListIdiomas().get(0);
    }
    
    private void createTestPagosEspana(ArrayList<Object> listTests, AppEcom appE, Channel channel, ITestContext ctx) {
        //Crearemos 3 tests para el pago VISA y 1 para los restantes 
        int prioridad = 1;
        List<Pago> listPagosToTest = espana.getListPagosTest(appE, false/*isEmpl*/);
        for (Pago pago : listPagosToTest) {
        	if (pago.isNeededTestPasarelaDependingFilter(channel, ctx)) {
	        	if (pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
		        	if ("VISA".compareTo(pago.getNombre())==0) {
		        		createTestPago(listTests, espana, castellano, pago, appE, channel, !usrReg, !empleado, testVale, manyArticles, !anulPedido, prioridad);
		        		createTestPago(listTests, espana, castellano, pago, appE, channel, usrReg, !empleado, !testVale, !manyArticles, !anulPedido, prioridad);
		        		createTestPago(listTests, espana, castellano, pago, appE, channel, usrReg, empleado, !testVale, !manyArticles, !anulPedido, prioridad);
		        	}
		        	else {
		        		createTestPago(listTests, espana, castellano, pago, appE, channel, usrReg, !empleado, !testVale, !manyArticles, !anulPedido, prioridad);
		            	usrReg=!usrReg; //Iremos alternando entre usr registrado y no-registrado
		        	}
		        	
		    		prioridad+=1;
	        	}
        	}
        }    	
    }
    
    private void createTestPagosFrancia(ArrayList<Object> listTests, AppEcom appE, Channel channel, ITestContext ctx) {
        //Creamos sólo 1 test para el pago VISA-Francia
        List<Pago> listPagosToTest = francia.getListPagosTest(appE, false/*isEmpl*/);
        for (Pago pago : listPagosToTest) {
        	if (pago.isNeededTestPasarelaDependingFilter(channel, ctx)) {
	        	if (pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
	        		if ("VISA".compareTo(pago.getNombre())==0) {
	        			createTestPago(listTests, francia, frances, pago, appE, channel, !usrReg, !empleado, !testVale, !twoArticles, anulPedido, 1);
	        			break;
	        		}
	        	}
        	}
        }    	
    }
    
    public void createTestPagosVotf(ArrayList<Object> listTests, AppEcom appE, Channel channel, ITestContext ctx) {
        List<Pago> listPagosToTest = espana.getListPagosTest(appE, false/*isEmpl*/);
        for (Pago pago : listPagosToTest) {
        	if (pago.isNeededTestPasarelaDependingFilter(channel, ctx)) {
	        	if (pago.getTestpago()!=null && "s".compareTo(pago.getTestpago())==0) {
        			createTestPago(listTests, espana, castellano, pago, appE, channel, !usrReg, !empleado, !testVale, twoArticles, !anulPedido, 1);
        			break;
	        	}
        	}
        }    	
    }

    private void createTestPago(ArrayList<Object> listTests, Pais pais, IdiomaPais idioma, Pago pago, AppEcom appE, Channel channel, 
    							boolean usrRegistrado, boolean empleado, boolean testVale, boolean manyArticles, boolean anulPedido, 
    							int prioridad) {
    	listTests.add(new CompraFact(pais, idioma, pago, appE, channel, usrRegistrado, empleado, testVale, manyArticles, anulPedido, prioridad));
    	System.out.println(
    	    "Creado Test COM010: " +
    	    "Pais=" + pais.getNombre_pais() +
        	",Pago=" + pago.getNameFilter(channel) + 
        	",Envio=" + pago.getTipoEnvioType(appE) +
        	",UserReg=" + usrRegistrado + 
        	",empleado=" + empleado
        );
    }
}
