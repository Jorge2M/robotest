package com.mng.robotest.test80.mango.test.stpv.votfcons;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep.SaveWhen;
import com.mng.robotest.test80.mango.test.pageobject.votfcons.IframeResult;
import com.mng.robotest.test80.mango.test.pageobject.votfcons.PageConsola;

@SuppressWarnings("javadoc")
public class ConsolaVotfStpV {

    /**
     * Acceso a la página inicial de la cónsola de VOTF
     * @param urlVOTF URL de la página inicial de la cónsola VOTF
     */
    public static DatosStep accesoPagInicial(String urlVOTF, DataFmwkTest dFTest) {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Accedemos a la página inicial de VOTF", 
            "Aparece la página inicial de VOTF");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            dFTest.driver.get(urlVOTF);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
        
        //Validaciones
        String descripValidac = 
            "1) Aparece el apartado \"Test servicios VOTF\"<br>" +
            "2) Aparece el apartado \"Consola comandos VOTF";
        datosStep.setExcepExists(true);  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {  
            if (!PageConsola.existTestServVOTF(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageConsola.existConsolaComVOTF(dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    /**
     * Seleccionamos un determinado entorno en ambos apartados: "Test servicios VOTF" y "Consola comandos VOTF"
     * @param entorno identifica el entorno a través del literal que aparece a la izquierda de cada una de las opciones ("Local", "Test", "Preproducción" o "Producción"
     */
    public static DatosStep selectEntornoTestAndCons(String entorno, DataFmwkTest dFTest) {
        
        //Step. Seleccionamos los entornos de preproducción
        DatosStep datosStep = new DatosStep       (
            "Seleccionamos el entorno de  " + entorno + " en los apartados \"Test servicios VOTF\" y \"Consola comandos VOTF\"", 
            "El entorno se selecciona correctamente");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Seleccionamos el valor del desplegable "Entorno" (de Test) correspondiente al entorno de Preproducción
            PageConsola.selectEntornoTestServ(dFTest.driver, entorno);
                
            //Seleccionamos el valor del desplegable "Entorno" (de Consola) correspondiente al entorno de Preproducción
            PageConsola.selectEntornoConsolaCom(dFTest.driver, entorno);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
    
    /**
     * Introducción de un artículo concreto y selección del botón "Consultar tipos de envío"
     */
    public static DatosStep inputArticleAndTiendaDisp(String articulo, String tienda, DataFmwkTest dFTest) throws Exception {
        //Step. Introducimos un determinado artículo y seleccionamos el botón "Consultar tipos de envío"
        DatosStep datosStep = new DatosStep       (
            "Introducimos el artículo disponible " + articulo + " (a nivel de  artículo disponible y de compra) + la tienda " + tienda, 
            "Aparecen datos correspondientes a " + PageConsola.msgConsTiposEnvioOK);
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            PageConsola.inputArticDispYCompra(dFTest.driver, articulo);
            PageConsola.inputTiendas(tienda, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
    
    /**
     * Introducción de un artículo concreto y selección del botón "Consultar tipos de envío"
     */
    public static DatosStep consultarTiposEnvio(DataFmwkTest dFTest) throws Exception {
        //Step. Introducimos un determinado artículo y seleccionamos el botón "Consultar tipos de envío"
        DatosStep datosStep = new DatosStep       (
            "Selección botón \"Consultar tipos de envío\"", 
            "Aparecen datos correspondientes a " + PageConsola.msgConsTiposEnvioOK);
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Seleccionamos el botón "Consultar tipos de envío"
            PageConsola.clickButtonConsTiposEnvios(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
    
        //Obtenemos el handle de la página actual
        String paginaPadre = dFTest.driver.getWindowHandle();
        
        //Validaciones
        String descripValidac = 
            "1) En el bloque de \"Petición/Resultado\" aparece el literal \"" + PageConsola.msgConsTiposEnvioOK + "\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            //Nos posicionamos en el iframe correspondiente al resultado
            PageConsola.switchToResultIFrame(dFTest.driver);
            if (!IframeResult.resultadoContainsText(dFTest.driver, PageConsola.msgConsTiposEnvioOK)) {
                listVals.add(1, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        } 
        finally {
            //Salimos del iframe de resultado hacia la página padre
            dFTest.driver.switchTo().window(paginaPadre);
            listVals.checkAndStoreValidations(descripValidac);
        }
        
        return datosStep;
    }
    
    /**
     * Introducción de un artículo concreto y selección del botón "Consultar disponibilidad envío a domicilio"
     */
    public static DatosStep consultarDispEnvDomic(String articulo, DataFmwkTest dFTest) throws Exception {
        //Step. Seleccionar el botón "Consultar disponibilidad envío domicilio"
        String codigosTransporte = "";
        DatosStep datosStep = new DatosStep       (
            "Introducimos el artículo " + articulo + " (a nivel de  artículo disponible y de compra) + Seleccionar el botón \"Consultar Disponibilidad Envío Domicilio\"", 
            "Aparece la tabla de transportes con los tipos");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Si todavía no están introducidos, introducimos el código de artículo en los inputs compra y disponiblidad
            PageConsola.inputArticDispYCompra(dFTest.driver, articulo);
            PageConsola.clickButtonConsultarDispEnvioDomicilio(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Obtenemos el handle de la página actual
        String paginaPadre = dFTest.driver.getWindowHandle();
        
        //Validaciones
        int maxSecondsToWait = 5;
        String descripValidac =
            "1) En el desplegable \"Código de Transporte\" aparecen datos (lo esperamos hasta XX segundos)<br>" +
            "2) En el bloque de \"Petición/Resultado\" aparece una tabla \"transportes__content\"<br>" +
            "3) En la tabla figuran los tipos " + codigosTransporte.replace("\n", ",");
        datosStep.setExcepExists(true); 
        datosStep.setResultSteps(State.Ok);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {  
            if (!PageConsola.isDataSelectCodigoTransporte(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            else {
                codigosTransporte = PageConsola.getCodigoTransporte(dFTest.driver);
            }

            PageConsola.switchToResultIFrame(dFTest.driver);
            if (!IframeResult.existsTransportes(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (!IframeResult.transportesContainsTipos(dFTest.driver, codigosTransporte)) {
                listVals.add(3, State.Defect);
            }
    
           datosStep.setListResultValidations(listVals);
        }
        finally {
           //Salimos del iframe de resultado hacia la página padre
            dFTest.driver.switchTo().window(paginaPadre);
            listVals.checkAndStoreValidations(descripValidac);
        }
        
        return datosStep;
    }
    
    /**
     * Introducción de un artículo concreto y selección del botón "Consultar disponibilidad envío a tienda"
     */
    public static DatosStep consultarDispEnvTienda(String articulo, DataFmwkTest dFTest) throws Exception {
        //Step. Seleccionar el botón "Consultar disponibilidad envío tienda"
        DatosStep datosStep = new DatosStep       (
            "Introducimos el artículo " + articulo + " (a nivel de  artículo disponible y de compra) + Seleccionar el botón \"Consultar Disponibilidad Envío Tienda\"", 
            "Aparece el bloque de transportes y el tipo de stock");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Si todavía no están introducidos, introducimos el código de artículo en los inputs compra y disponiblidad
            PageConsola.inputArticDispYCompra(dFTest.driver, articulo);
            
            //Seleccionamos el botón "Consultar Disponibilidad Envío Tienda"
            PageConsola.consDispEnvioTienda(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Obtenemos el handle de la página actual
        String paginaPadre = dFTest.driver.getWindowHandle();
        
        //Validaciones
        String descripValidac = 
            "1) En el bloque de \"Petición/Resultado\" NO aparece una tabla \"transportes__content\"<br>" +
            "2) Aparece una línea de \"TipoStock:\" con contenido";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);          
        paginaPadre = dFTest.driver.getWindowHandle();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {  
            PageConsola.switchToResultIFrame(dFTest.driver);
            if (IframeResult.existsTransportes(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!IframeResult.isPresentTipoStock(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally {
            //Salimos del iframe de resultado hacia la página padre
            dFTest.driver.switchTo().window(paginaPadre);
            listVals.checkAndStoreValidations(descripValidac);
        }
        
        return datosStep;
    }
    
    /**
     * Introducción de un artículo concreto y selección del botón "Realizar Solicitud a Tienda"
     */
    public static String realizarSolicitudTienda(String articulo, DataFmwkTest dFTest) throws Exception {
        String codigoPedido = "";
        
        //Step. Seleccionar el botón "Realizar Solicitud a Tienda"
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón \"Realizar Solicitud A Tienda\"", 
            "El pedido se crea correctamente");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Si todavía no están introducidos, introducimos el código de artículo en los inputs compra y disponiblidad
            PageConsola.inputArticDispYCompra(dFTest.driver, articulo);
            
            //Seleccionamos el botón "Consultar Disponibilidad Envío Domicilio" (y esperamos hasta que se carga la página)
            PageConsola.clickButtonSolATienda(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Obtenemos el handle de la página actual
        String paginaPadre = dFTest.driver.getWindowHandle();
        
        //Validaciones
        String descripValidac = 
            "1) En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Código de pedido\"<br>" +
            "2) Aparece un código de pedido<br>" +
            "3) Aparece el literal \"Resultado creación pedido: (0) Total\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {  
            PageConsola.switchToResultIFrame(dFTest.driver);
            if (!IframeResult.isPresentCodigoPedido(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            codigoPedido = IframeResult.getCodigoPedido(dFTest.driver);
            if ("".compareTo(codigoPedido)==0) {
                listVals.add(2, State.Defect);
            }
            if (!IframeResult.resCreacionPedidoOk(dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally {
            //Salimos del iframe de resultado hacia la página padre
            dFTest.driver.switchTo().window(paginaPadre);
            listVals.checkAndStoreValidations(descripValidac);
        } 
        
        return codigoPedido;
    }
    
    /**
     * Obtener los pedidos mediante selección del botón "Obtener pedidos" (del apartado "Consola comandos")
     * @return el código de pedido completo (+2 dígitos por la derecha)
     */
    public static String obtenerPedidos(String codigoPedido, DataFmwkTest dFTest) throws Exception {
        
        String codigoPedidoFull = "";
        
        //Step. Seleccionar el botón "Obtener pedidos" (del apartado "Consola comandos")
        DatosStep datosStep = new DatosStep (
            "Seleccionar el botón \"Obtener Pedidos\"", 
            "Aparece la lista de pedidos");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Seleccionamos el botón "Obtener pedidos" (y esperamos hasta que se carga la página)
            PageConsola.clickButtonObtenerPedidos(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Obtenemos el handle de la página actual
        String paginaPadre = dFTest.driver.getWindowHandle();
        
        //Validaciones
        String descripValidac = 
            "1) En el bloque de \"Petición/Resultado\" aparece una línea correspondiente al \"Pedidos\"<br>" +
            "2) En la lista de pedidos aparece el generado anteriormente: " + codigoPedido;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {  
            PageConsola.switchToResultIFrame(dFTest.driver);
            if (!IframeResult.isPresentListaPedidos(dFTest.driver)) {
            	listVals.add(1, State.Warn);    
            }
            codigoPedidoFull = IframeResult.getPedidoFromListaPedidos(dFTest.driver, codigoPedido);
            if ("".compareTo(codigoPedidoFull)==0) {
            	listVals.add(2, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally {
            //Salimos del iframe de resultado hacia la página padre
            dFTest.driver.switchTo().window(paginaPadre);
            listVals.checkAndStoreValidations(descripValidac);
        }
                
        return codigoPedidoFull;
    }
    
    /**
     * Selecciona un pedido mediante click en botón "Seleccionar pedido" (del apartado "Consola comandos")
     * @param codigoPedidoFull código de pedido en formato largo (+2 dígitos por la derecha)
     */
    public static void seleccionarPedido(String codigoPedidoFull, DataFmwkTest dFTest) throws Exception {
        //Step. Seleccionar el botón "Seleccionar pedido"
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el pedido " + codigoPedidoFull + "en el desplegable \"Pedido\" y pulsar \"Seleccionar pedido\"", 
            "Aparece el pedido seleccionado");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Seleccionar el pedido en el desplegable
            PageConsola.selectPedido(dFTest.driver, codigoPedidoFull);
            
            //Seleccionamos el botón "Seleccionar Pedidos" (y esperamos hasta que se carga la página)
            PageConsola.clickButtonSelectPedido(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
    
        //Obtenemos el handle de la página actual
        String paginaPadre = dFTest.driver.getWindowHandle();
        
        //Validaciones
        String descripValidac = 
            "1) En el bloque de \"Petición/Resultado\" aparece una línea \"Seleccionado: " + codigoPedidoFull + "\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {  
            PageConsola.switchToResultIFrame(dFTest.driver);
            if (IframeResult.resSelectPedidoOk(dFTest.driver, codigoPedidoFull)) {
                listVals.add(1, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally {
            //Salimos del iframe de resultado hacia la página padre
            dFTest.driver.switchTo().window(paginaPadre);
            listVals.checkAndStoreValidations(descripValidac);
        }
    }
    
    /**
     * Selecciona el botón "Preconfirmar Pedido" (del apartado "Consola comandos")
     * @param codigoPedidoFull código de pedido en formato largo (+2 dígitos por la derecha)
     */    
    public static void selectPreconfPedido(String codigoPedidoFull, DataFmwkTest dFTest) throws Exception {
        
        //Step. Seleccionar el botón "Preconfirmar Pedido"
        DatosStep datosStep = new DatosStep       (
            "Pulsar el botón \"Preconfirmar Pedido\"", 
            "Aparece el pedido como preconfirmado");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Seleccionamos el botón "Seleccionar Pedidos" (y esperamos hasta que se carga la página)
            PageConsola.clickButtonPreconfPedido(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Obtenemos el handle de la página actual
        String paginaPadre = dFTest.driver.getWindowHandle();
        
        //Validaciones
        String descripValidac = 
            "1) En el bloque de \"Petición/Resultado\" aparece una línea \"Preconfirmado\"<br>" +
            "2) Aparece un XML con el dato \"&lt;pedido&gt;" + codigoPedidoFull + "&lt;/pedido&gt;\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);          
        paginaPadre = dFTest.driver.getWindowHandle();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {  
            PageConsola.switchToResultIFrame(dFTest.driver);
            boolean[] resultado = IframeResult.resSelPreconfPedidoOk(dFTest.driver, codigoPedidoFull);
            if (!resultado[0]) {
                listVals.add(1, State.Warn);
            }
            if (!resultado[1]) {
                listVals.add(2, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally {
            //Salimos del iframe de resultado hacia la página padre
            dFTest.driver.switchTo().window(paginaPadre);
            listVals.checkAndStoreValidations(descripValidac);
        }
    }
    
    /**
     * Selecciona el botón "Confirmar Pedido" (del apartado "Consola comandos")
     * @param codigoPedidoFull código de pedido en formato largo (+2 dígitos por la derecha)
     */    
    public static void selectConfPedido(String codigoPedidoFull, DataFmwkTest dFTest) throws Exception {
        
        //STEP - SELECCIONAR EL BOTÓN "CONFIRMAR PEDIDO"
        DatosStep datosStep = new DatosStep       (
            "Pulsar el botón \"Confirmar Pedido\"", 
            "Aparece el pedido confirmado");
	    datosStep.setSaveErrorPage(SaveWhen.Never);
        try {
            //Seleccionamos el botón "Seleccionar Pedidos" (y esperamos hasta que se carga la página)
            PageConsola.clickButtonConfPedido(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Obtenemos el handle de la página actual
        String paginaPadre = dFTest.driver.getWindowHandle();
        
        //Validaciones
        String descripValidac = 
            "1) En el bloque de \"Petición/Resultado\" aparece una línea \"Confirmado: " + codigoPedidoFull + "\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Ok);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {  
            PageConsola.switchToResultIFrame(dFTest.driver);
            if (!IframeResult.resConfPedidoOk(dFTest.driver, codigoPedidoFull)) {
                listVals.add(1, State.Warn);    
            }
    
            datosStep.setListResultValidations(listVals);        
        }
        finally {
            //Salimos del iframe de resultado hacia la página padre
            dFTest.driver.switchTo().window(paginaPadre);
            listVals.checkAndStoreValidations(descripValidac); 
        }       
    }
}
