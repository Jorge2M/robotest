package com.mng.robotest.test80.mango.test.stpv.manto;

import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.manto.PageBolsas;
import com.mng.robotest.test80.mango.test.pageobject.manto.SecCabecera;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMenusManto;

/**
 * Clase que implementa los diferentes steps/validations asociados asociados a la página de Menús en Manto
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class PageMenusMantoStpV {

    public static datosStep goToMainMenusAndClickMenu(String subMenu, DataFmwkTest dFTest) throws Exception {
        //Step. Accedemos al menú de Bolsas
        datosStep datosStep = new datosStep     (
            "Desde la página de menús, seleccionamos el menú \"" + subMenu + "\"", 
            "Aparece la página al menú seleccionado");
        datosStep.setGrab_ErrorPageIfProblem(false);
        String textAlert = "";
        try {
            //Si no estamos en la página de menús nos posicionamos en ella
            if (!PageMenusManto.isPage(dFTest.driver)) {
            	Thread.sleep(1000);
                SecCabecera.clickLinkVolverMenuAndWait(dFTest.driver, 60);
            }
                    
            textAlert = PageMenusManto.clickMenuAndAcceptAlertIfExists(subMenu, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        String descripValidac = 
        	"1) Aparece la página asociada al menú <b>" + subMenu + "</b><br>" +
        	"2) No aparece ninguna ventana de alerta";
        datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMenusManto.validateIsPage(subMenu, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if ("".compareTo(textAlert)!=0) {
                    descripValidac+=
                        "<br>" +
                        "<b>Warning!</b> Ha aparecido una alerta con el mensaje: " + textAlert;
            	fmwkTest.addValidation(2, State.Warn, listVals);
            }
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    /**
     * Se accede a la opción de menú de "Bolsas" (sólo en caso de que no estemos ya en ella)
     */
    public static void goToBolsas(DataFmwkTest dFTest) throws Exception {
        
        //Si ya estamos en la página en cuestión no hacemos nada
        if (!PageBolsas.isPage(dFTest.driver)) {
            //Step
            datosStep datosStep = goToMainMenusAndClickMenu("Bolsas", dFTest);
                
            //Validaciones
            String descripValidac = "1) Aparece la página de Bolsas";
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);              
            try {
                /*1*/assertTrue(PageBolsas.isPage(dFTest.driver));
                                          
                datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
            }  
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
        
    }
    
    /**
     * Se accede a la opción de menú de "Pedidos" (sólo en caso de que no estemos ya en ella)
     */
    public static void goToPedidos(DataFmwkTest dFTest) throws Exception {
        //Si ya estamos en la página en cuestión no hacemos nada
        if (!PagePedidos.isPage(dFTest.driver)) {
            //Step. 
            datosStep datosStep = goToMainMenusAndClickMenu("Pedidos", dFTest);
                
            //Validaciones
            String descripValidac = 
                "1) Aparece la página de Pedidos";
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);              
            try {
                //1)
                assertTrue(PagePedidos.isPage(dFTest.driver));
                                          
                datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
            }  
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }        
    }    
    
    /**
     * Se accede a la opción de menú de "Consultar Tiendas" (sólo en caso de que no estemos ya en ella)
     */
    public static void goToConsultarTiendas(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = goToMainMenusAndClickMenu("Consultar Tiendas", dFTest);
            
        //Validaciones
        PageConsultaTiendaStpV.validateIsPage(datosStep, dFTest);
    }

    /**
     * Se accede a la opción de menú de "ID/EANS" (sólo en caso de que no estemos ya en ella)
     */
	public static void goToIdEans(DataFmwkTest dFTest)  throws Exception{
		//Step
        datosStep datosStep = goToMainMenusAndClickMenu("EANS", dFTest);
            
        //Validaciones
        PageConsultaIdEansStpV.validateIsPage(datosStep, dFTest);
	}

	/**
     * Se accede a la opción de menú de "Gestionar Clientes" (sólo en caso de que no estemos ya en ella)
     */
	public static void goToGestionarClientes(DataFmwkTest dFTest) throws Exception{
		//Step
        datosStep datosStep = goToMainMenusAndClickMenu("Gestionar Clientes", dFTest);
            
        //Validaciones
        PageGestionarClientesStpV.validateIsPage(datosStep, dFTest);
	}
	
	/**
     * Se accede a la opción de menú de "Gestor de Cheques" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	public static void goToGestorCheques(DataFmwkTest dFTest) throws Exception {
		//Step
        datosStep datosStep = goToMainMenusAndClickMenu("Gestor de Cheques", dFTest);
            
        //Validaciones
        PageGestorChequesStpV.validateIsPage(datosStep, dFTest);
	}
	
	/**
     * Se accede a la opción de menú de "Estadísticas Pedidos" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	public static void goToGestorEstadisticasPedido(DataFmwkTest dFTest) throws Exception {
		//Step
        datosStep datosStep = goToMainMenusAndClickMenu("Estadisticas Pedidos", dFTest);
            
        //Validaciones
        PageGestorEstadisticasPedidoStpV.validateIsPage(datosStep, dFTest);
	}
	
	
	/**
     * Se accede a la opción de menú de "Gestor de Saldos de TPV" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	public static void goToGestorSaldosTPV(DataFmwkTest dFTest) throws Exception {
		//Step
        datosStep datosStep = goToMainMenusAndClickMenu("Gestor de Saldos de TPV", dFTest);
            
        //Validaciones
        PageGestorSaldosTPVStpV.validateIsPage(datosStep, dFTest);
		
	}
	
	/**
     * Se accede a la opción de menú de "Consulta y cambio de familia" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	public static void goToGestorConsultaCambioFamilia(DataFmwkTest dFTest) throws Exception {
		//Step
        datosStep datosStep = goToMainMenusAndClickMenu("Gestor de familias", dFTest);
            
        //Validaciones
        PageGestorConsultaCambioFamiliaStpV.validateIsPage(datosStep, dFTest);
		
	}
	
	/**
     * Se accede a la opción de menú de "Ordenacion de prendas" (sólo en caso de que no estemos ya en ella)
	 * @throws Exception 
     */
	
	public static void goToOrdenadorDePrendas(DataFmwkTest dFTest) throws Exception {
		//Step
		datosStep datosStep = goToMainMenusAndClickMenu("Ordenador de Prendas", dFTest);
		
		//Validaciones
		PageOrdenacionDePrendasStpV.validateIsPage(datosStep, dFTest);
	}
	
	/**
     * Se accede a las distintas opciones de menú en Manto por bloques y se comprueba que la página funciona
	 * @param listMenuNames 
	 * @throws Exception 
     */
	public static void comprobarMenusManto(String cabeceraName, String cabeceraNameNext, DataFmwkTest dFTest) throws Exception{
		//Obtenemos todos los Submenus del bloque
		ArrayList<String> listSubMenuNames = PageMenusManto.getListSubMenusName(cabeceraName, cabeceraNameNext, dFTest.driver);

		for (String subMenu : listSubMenuNames)
		    goToMainMenusAndClickMenu(subMenu, dFTest);
		
	}


}











