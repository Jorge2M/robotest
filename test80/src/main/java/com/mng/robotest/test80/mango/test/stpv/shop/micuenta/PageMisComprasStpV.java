package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
    public static void validateIsPage(DataCtxShop dataCtxShop, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        if (dataCtxShop.pais.isTicketStoreEnabled()) {
            validateIsPage(datosStep, dFTest);
        } else {
            validateIsPageWhenNotExistTabs(datosStep, dFTest);
        }
    }

    private static void validateIsPageWhenNotExistTabs(DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones.
        int maxSecondsToWait = 2;
        String descripValidac =
        	"1) Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) No aparece el bloque de \"Tienda\"<br>" +
            "3) No aparece el bloque de \"Online\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisCompras.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (PageMisCompras.isPresentBlockUntil(0/*maxSedondsToWait*/, TypeCompra.Tienda, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (PageMisCompras.isPresentBlockUntil(0/*maxSedondsToWait*/, TypeCompra.Online, dFTest.driver)) {
                listVals.add(3, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }

        //Validaciones estÃ¡ndar.
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }

    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones.
    	int maxSecondsToWait = 2;
        String descripValidac = 
            "1) Aparece la página de \"Mis Compras\" (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece el bloque de \"Tienda\"<br>" +
            "3) Aparece el bloque de \"Online\"";
        datosStep.setNOKstateByDefault();      
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisCompras.isPageUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageMisCompras.isPresentBlockUntil(0/*maxSedondsToWait*/, TypeCompra.Tienda, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageMisCompras.isPresentBlockUntil(0/*maxSedondsToWait*/, TypeCompra.Online, dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
        
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
            
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
    }
    
    /**
     * Selección de un bloque para el que esperamos que SÍ existan pedidos
     */
    public static DatosStep selectBlock(TypeCompra typeCompra, boolean ordersExpected, DataFmwkTest dFTest) {
        //Step.
        DatosStep datosStep = new DatosStep     (
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
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisCompras.isSelectedBlockUntil(maxSecondsToWait, typeCompra, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        if (ordersExpected) {
            maxSecondsToWait = 2;
            descripValidac = 
                "1) Aparece una lista con algún artículo (lo esperamos hasta " + maxSecondsToWait + " segundos) <br>" +
                "2) El 1er artículo es de tipo \"" + typeCompra + "\"";
            datosStep.setNOKstateByDefault();            
            listVals = ListResultValidation.getNew(datosStep);
            try {
                boolean isVisibleAnyCompra = PageMisCompras.isVisibleAnyCompraUntil(maxSecondsToWait, dFTest.driver); 
                if (!isVisibleAnyCompra) {
                    listVals.add(1, State.Warn);
                }
                if (isVisibleAnyCompra) {
                    if (PageMisCompras.getTypeCompra(1/*posInLista*/, dFTest.driver)!=typeCompra) {
                        listVals.add(2, State.Warn);
                    }
                }
            
                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }        
        }
        else {
            //Validaciones
            descripValidac = 
                "1) No aparece ningún artículo<br>" +
                "2) Es visible la imagen asociada a \"Lista Vacía\" para " + typeCompra;
            datosStep.setNOKstateByDefault();
            listVals = ListResultValidation.getNew(datosStep);
            try {
                boolean isVisibleAnyCompra = PageMisCompras.isVisibleAnyCompraUntil(0/*maxSecondsToWait*/, dFTest.driver); 
                if (isVisibleAnyCompra) {
                    listVals.add(1, State.Warn);
                }
                if (!PageMisCompras.isVisibleEmptyListImage(typeCompra, dFTest.driver)) {
                    listVals.add(2, State.Warn);
                }
            
                datosStep.setListResultValidations(listVals);
            }
            finally { listVals.checkAndStoreValidations(descripValidac); }            
        }
        
        return datosStep;
    }
    
    public static void validateIsCompraOnlineVisible(String codPedido, boolean isChequeRegalo, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        String descripValidac = 
            "1) Es visible la compra " + TypeCompra.Online + " asociada al pedido <b>" + codPedido + "</b>";
        datosStep.setNOKstateByDefault();       
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisCompras.isVisibleCompraOnline(codPedido, dFTest.driver)) {
                if (isChequeRegalo)
                    listVals.add(1, State.Info_NoHardcopy);
                else
                    listVals.add(1, State.Warn);
            }
        
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static PageDetallePedidoStpV selectCompraOnline(int posInLista, String codPais, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step.
        CompraOnline compraOnline = null;
        DatosStep datosStep = new DatosStep     (
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
    public static DatosStep selectCompraTienda(int posInLista, Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step.
        CompraTienda compraTienda = null;
        DatosStep datosStep = new DatosStep     (
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
        DatosStep datosStep = new DatosStep     (
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
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDetalleMisCompras.isVisible(dFTest.driver)) {
            	listVals.add(1, State.Defect);
            }
            if (!ModalDetalleMisCompras.isReferenciaValidaModal(idArticulo, dFTest.driver)) {
            	listVals.add(2, State.Defect);
            }
        
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }      
		
	}    
}
