package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraOnline;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraTienda;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras.TypeCompra;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalDetalleMisCompras;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

@SuppressWarnings("javadoc")
public class PageMisComprasStpV {

    public static SecDetalleCompraTiendaStpV SecDetalleCompraTienda; 
    public static SecQuickViewArticuloStpV SecQuickViewArticulo;

    public static void validateIsPage(DataCtxShop dataCtxShop, datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        if (dataCtxShop.pais.isTicketStoreEnabled()) {
            validateIsPage(datosStep, dFTest);
        } else {
            validateIsPageWhenNotExistTabs(datosStep, dFTest);
        }
    }

    private static void validateIsPageWhenNotExistTabs(datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones.
        int maxSecondsToWait = 2;
        String descripValidac =
        	"1) Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) No aparece el bloque de \"Tienda\"<br>" +
            "3) No aparece el bloque de \"Online\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisCompras.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (PageMisCompras.isPresentBlockUntil(0/*maxSedondsToWait*/, TypeCompra.Tienda, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (PageMisCompras.isPresentBlockUntil(0/*maxSedondsToWait*/, TypeCompra.Online, dFTest.driver))
                fmwkTest.addValidation(3, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }

        //Validaciones estÃ¡ndar.
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }

    public static void validateIsPage(datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones.
    	int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece el bloque de \"Tienda\"<br>" +
            "3) Aparece el bloque de \"Online\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisCompras.isPageUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageMisCompras.isPresentBlockUntil(0/*maxSedondsToWait*/, TypeCompra.Tienda, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageMisCompras.isPresentBlockUntil(0/*maxSedondsToWait*/, TypeCompra.Online, dFTest.driver))  
                fmwkTest.addValidation(3, State.Warn, listVals);
        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
            
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
    
    /**
     * Selección de un bloque para el que esperamos que SÍ existan pedidos
     */
    public static datosStep selectBlock(TypeCompra typeCompra, boolean ordersExpected, DataFmwkTest dFTest) {
        //Step.
        datosStep datosStep = new datosStep     (
            "Seleccionar el bloque \"" + typeCompra + "\"", 
            "Se hace visible el bloque de " + typeCompra);
        try {
            PageMisCompras.clickBlock(typeCompra, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        int maxSecondsToWait = 2;
        String descripValidac =
            "1) Queda seleccionado el bloque de \"" + typeCompra + "\" (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisCompras.isSelectedBlockUntil(maxSecondsToWait, typeCompra, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        if (ordersExpected) {
            maxSecondsToWait = 2;
            descripValidac = 
                "1) Aparece una lista con algún artículo (lo esperamos hasta " + maxSecondsToWait + " segundos) <br>" +
                "2) El 1er artículo es de tipo \"" + typeCompra + "\"";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                boolean isVisibleAnyCompra = PageMisCompras.isVisibleAnyCompraUntil(maxSecondsToWait, dFTest.driver); 
                if (!isVisibleAnyCompra)
                    fmwkTest.addValidation(1, State.Warn, listVals);
                //2)
                if (isVisibleAnyCompra) {
                    if (PageMisCompras.getTypeCompra(1/*posInLista*/, dFTest.driver)!=typeCompra)
                        fmwkTest.addValidation(2, State.Warn, listVals);
                }
            
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
        }
        else {
            //Validaciones
            descripValidac = 
                "1) No aparece ningún artículo<br>" +
                "2) Es visible la imagen asociada a \"Lista Vacía\" para " + typeCompra;
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                boolean isVisibleAnyCompra = PageMisCompras.isVisibleAnyCompraUntil(0/*maxSecondsToWait*/, dFTest.driver); 
                if (isVisibleAnyCompra)
                    fmwkTest.addValidation(1, State.Warn, listVals);
                //2)
                if (!PageMisCompras.isVisibleEmptyListImage(typeCompra, dFTest.driver))
                    fmwkTest.addValidation(2, State.Warn, listVals);
            
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }            
        }
        
        return datosStep;
    }
    
    public static void validateIsCompraOnlineVisible(String codPedido, boolean isChequeRegalo, datosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac = 
            "1) Es visible la compra " + TypeCompra.Online + " asociada al pedido <b>" + codPedido + "</b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisCompras.isVisibleCompraOnline(codPedido, dFTest.driver))
                if (isChequeRegalo)
                    fmwkTest.addValidation(1, State.Info_NoHardcopy, listVals);
                else
                    fmwkTest.addValidation(1, State.Warn, listVals);
        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static PageDetallePedidoStpV selectCompraOnline(int posInLista, String codPais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step.
        CompraOnline compraOnline = null;
        datosStep datosStep = new datosStep     (
            "Seleccionamos la " + posInLista + "a compra (tipo Online) de la lista", 
            "Aparece la página con los detalles del pedido");
        try {
            compraOnline = PageMisCompras.getDataCompraOnline(posInLista, channel, dFTest.driver);
            PageMisCompras.clickCompra(posInLista, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }             
        
        //Validaciones
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(dFTest.driver);
        pageDetPedidoStpV.validateIsPageOk(compraOnline, codPais, datosStep, dFTest);
        
        return pageDetPedidoStpV;        
    }
    
    @SuppressWarnings("static-access")
    public static datosStep selectCompraTienda(int posInLista, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step.
        CompraTienda compraTienda = null;
        datosStep datosStep = new datosStep     (
            "Seleccionamos la " + posInLista + "a compra (tipo Tienda) de la lista", 
            "Aparece una sección con los detalles de la Compra");
        try {
            compraTienda = PageMisCompras.getDataCompraTienda(posInLista, channel, dFTest.driver);
            PageMisCompras.clickCompra(posInLista, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }             
        
        //Validaciones
        SecDetalleCompraTienda.validateIsOk(compraTienda, channel, datosStep, dFTest);
        
        return datosStep;        
    }

    
	public static void clickMoreInfo(DataFmwkTest dFTest) {
		//Step.
		String idArticulo = "";
        datosStep datosStep = new datosStep     (
            "Seleccionamos el primer artículo que aparece con referencia ", 
            "Aparece el modal con información del artículo");
        try {
        	
        	idArticulo = PageMisCompras.getReferenciaPrimerArticulo(dFTest.driver);
            PageMisCompras.clickMasInfoArticulo(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }             
        
        //Validaciones
        String descripValidac = 
                "1) Aparece el modal con información del artículo<br>" + 
                "2) La referencia que aparece en el modal es correcta " + idArticulo;
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            
            //1)
            if (!ModalDetalleMisCompras.isVisible(dFTest.driver))
            		fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!ModalDetalleMisCompras.isReferenciaValidaModal(idArticulo, dFTest.driver))
            		fmwkTest.addValidation(2, State.Defect, listVals);
        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }      
		
	}    
}
