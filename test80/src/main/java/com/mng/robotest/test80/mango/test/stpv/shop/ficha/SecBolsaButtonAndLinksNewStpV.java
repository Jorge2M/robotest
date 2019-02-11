package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.ModCompartirNew;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBolsaButtonAndLinksNew;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDetalleProductNew;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.ModCompartirNew.IconSocial;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBolsaButtonAndLinksNew.LinksAfterBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDetalleProductNew.ItemBreadcrumb;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypePanel;

@SuppressWarnings("javadoc")
public class SecBolsaButtonAndLinksNewStpV {
    
    public static void selectEnvioYDevoluciones(DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Seleccionar el link <b>Envío y devoluciones</b>",
            "Aparece el modal con los datos a nivel de envío y devolución");
        try {
            SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.EnvioYDevoluciones, dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        ModEnvioYdevolNewStpV.validateIsVisible(datosStep, dFTest);
    }
    
    public static void selectDetalleDelProducto(AppEcom app, LineaType lineaType, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Seleccionar el link <b>Detalle del producto</b>",
            "Se scrolla hasta el apartado de \"Descripción\"");
        try {
            SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.DetalleProducto, dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Se scrolla hasta el apartado de \"Descriptión\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecDetalleProductNew.isVisibleUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }        
        
        descripValidac = 
            "1) Figura el bloque de BreadCrumbs<br>" +
            "2) Es visible el item " + ItemBreadcrumb.linea + "<br>" +
            "3) Es visible el item " + ItemBreadcrumb.grupo + "<br>" +
            "4) Es visible el item " + ItemBreadcrumb.galeria;            
        datosStep.setNOKstateByDefault();
        listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecDetalleProductNew.isVisibleBreadcrumbs(0, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.linea, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.grupo, dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
            if (!SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.galeria, dFTest.driver)) {
                listVals.add(4, State.Warn);            
            }
            
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        if (TypePanel.KcSafety.getListApps().contains(app) &&
            (lineaType==LineaType.nina || lineaType==LineaType.nino)) {
            descripValidac = 
                "1) Aparece el bloque de \"KcSafety\"";
            datosStep.setNOKstateByDefault();
            listVals = ListResultValidation.getNew(datosStep);
            try {
                if (!SecDetalleProductNew.isVisibleBlockKcSafety(dFTest.driver)) {
                    listVals.add(1, State.Defect);
                }
                
                datosStep.setListResultValidations(listVals);
            }  
            finally { listVals.checkAndStoreValidations(descripValidac); }
        }
    }
    
    public static DatosStep selectLinkCompartir(String codigoPais, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep (
            "Seleccionar el link <b>Compartir</b>",
            "Aparece el modal para compartir el enlace");
        try {
            SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.Compartir, dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Aparece el modal para compartir a nivel social (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        boolean isPaisChina = (codigoPais.compareTo("720")==0);
        int i=1;
        for (IconSocial icon : IconSocial.values()) {
            i+=1;
            String visible = "SÍ";
            if (isPaisChina != icon.isSpecificChina())
                visible = "NO";
            
            descripValidac+=
            "<br>" + i + ") " + visible  + " es visible el icono de " + icon;
        }        
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModCompartirNew.isVisibleUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            //2...N)
            i=1;
            for (IconSocial icon : IconSocial.values()) {
                i+=1;
                if (isPaisChina != icon.isSpecificChina()) {
                    if (ModCompartirNew.isVisibleIcon(icon, dFTest.driver)) {
                        listVals.add(i, State.Warn);
                    }
                }
                else {
                    if (!ModCompartirNew.isVisibleIcon(icon, dFTest.driver)) {
                        listVals.add(i, State.Warn);                        
                    }
                }
            }
            
            datosStep.setListResultValidations(listVals);
        }  
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }    
}
