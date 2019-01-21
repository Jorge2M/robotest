package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
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
        datosStep datosStep = new datosStep (
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
        datosStep datosStep = new datosStep (
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecDetalleProductNew.isVisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
        
        descripValidac = 
            "1) Figura el bloque de BreadCrumbs<br>" +
            "2) Es visible el item " + ItemBreadcrumb.linea + "<br>" +
            "3) Es visible el item " + ItemBreadcrumb.grupo + "<br>" +
            "4) Es visible el item " + ItemBreadcrumb.galeria;            
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecDetalleProductNew.isVisibleBreadcrumbs(0, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.linea, dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);            
            //3)
            if (!SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.grupo, dFTest.driver))
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4)
            if (!SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.galeria, dFTest.driver))
                fmwkTest.addValidation(4, State.Warn, listVals);            
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        if (TypePanel.KcSafety.getListApps().contains(app) &&
            (lineaType==LineaType.nina || lineaType==LineaType.nino)) {
            descripValidac = 
                "1) Aparece el bloque de \"KcSafety\"";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!SecDetalleProductNew.isVisibleBlockKcSafety(dFTest.driver))
                    fmwkTest.addValidation(1, State.Defect, listVals);
                
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }  
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
    }
    
    public static datosStep selectLinkCompartir(String codigoPais, DataFmwkTest dFTest) throws Exception {
        //Step.
        datosStep datosStep = new datosStep (
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModCompartirNew.isVisibleUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2...N)
            i=1;
            for (IconSocial icon : IconSocial.values()) {
                i+=1;
                if (isPaisChina != icon.isSpecificChina()) {
                    if (ModCompartirNew.isVisibleIcon(icon, dFTest.driver))
                        fmwkTest.addValidation(i, State.Warn, listVals);
                }
                else {
                    if (!ModCompartirNew.isVisibleIcon(icon, dFTest.driver))
                        fmwkTest.addValidation(i, State.Warn, listVals);                        
                }
            }
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }    
}
