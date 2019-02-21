package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.HashMap;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisDatos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;


public class PageMisDatosStpV {
    
    public static DatosStep validaIsPage(String usuarioReg, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones.
        String descripValidac = 
            "1) Aparece una página con el la cabecera \"Mis datos\"<br>" +
            "2) El campo de email está bloqueado<br>" +
            "3) El campo de email contiene " + usuarioReg;
        datosStep.setNOKstateByDefault();       
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisDatos.isPage(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageMisDatos.emailIsDisabled(dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (PageMisDatos.getValueEmailInput(dFTest.driver).compareTo(usuarioReg.toUpperCase())!=0) {
                listVals.add(3, State.Warn);
            }
    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/);
        
        return datosStep;
    }
    
    public static DatosStep validaIsDataAssociatedToRegister(HashMap<String,String> datosRegOk, String codpais, DatosStep datosStep, DataFmwkTest dFTest) {
        String nombre = datosRegOk.get("cfName"); 
        String apellidos = datosRegOk.get("cfSname");
        String email = datosRegOk.get("cfEmail");
        String direccion = datosRegOk.get("cfDir1");
        String codpostal = datosRegOk.get("cfCp");
        String poblacion = datosRegOk.get("cfCity");
        String provincia = datosRegOk.get("estadosPais");
        
        String descripValidac = 
            "1) Aparece un campo de contraseña de tipo password<br>" +
            "2) El resto de campos de tipo \"inputContent\" están informados<br>" +
            "3) El Nombre contiene el definido durante el registro: <b>" + nombre + "</b><br>" +
            "4) El Apellidos contiene el definido durante el registro: <b>" + apellidos + "</b><br>" +
            "5) El Email contiene el definido durante el registro: <b>" + email + "</b><br>" +
            "6) La Dirección contiene la definida durante el registro: <b>" + direccion + "</b><br>" +
            "7) El Código postal contiene el definido durante el registro: <b>" + codpostal + "</b><br>" +
            "8) La población contiene la definida durante el registro: <b>" + poblacion + "</b><br>" +
            "9) Está seleccionado el país definido durante el registro: <b>" + codpais + "</b><br>" +
            "10) Está seleccionada la provincia definida durante el registro: <b>" + provincia + "</b>";
        datosStep.setNOKstateByDefault();           
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisDatos.isVisiblePasswordTypePassword(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (PageMisDatos.getNumInputContentVoid(dFTest.driver) > 1) {
                listVals.add(2, State.Defect);
            }
            if (PageMisDatos.getText_inputNombre(dFTest.driver).compareTo(nombre)!=0) {
                listVals.add(3, State.Defect);
            }
            if (PageMisDatos.getText_inputApellidos(dFTest.driver).compareTo(apellidos)!=0) {
                listVals.add(4, State.Defect);
            }
            if (PageMisDatos.getText_inputEmail(dFTest.driver).toLowerCase().compareTo(email.toLowerCase())!=0) {
                listVals.add(5, State.Defect);
            }
            if (PageMisDatos.getText_inputDireccion(dFTest.driver).compareTo(direccion)!=0) {
                listVals.add(6, State.Defect);
            }
            if (PageMisDatos.getText_inputCodPostal(dFTest.driver).compareTo(codpostal)!=0) {
                listVals.add(7, State.Defect);
            }
            if (PageMisDatos.getText_inputPoblacion(dFTest.driver).compareTo(poblacion)!=0) {
                listVals.add(8, State.Defect);
            }
            if (PageMisDatos.getCodPaisSelected(dFTest.driver).compareTo(codpais)!=0) {
                listVals.add(9, State.Defect);
            }
            if (provincia!=null) {
	            if (PageMisDatos.getProvinciaSelected(dFTest.driver).compareTo(provincia)!=0) {
	                listVals.add(10, State.Defect);
	            }
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
    
    public static String modificaNombreYGuarda(DataFmwkTest dFTest) throws Exception {
        String nombreActual = "";
        DatosStep datosStep = new DatosStep       (
            "Modificar el nombre (cambio de mayúsculas<->minúsculas) + Botón \"Modificar Datos\"", 
            "Aparece la confirmación que los datos se han modificado");
        try {
            //Modificamos el nombre (lo cambiamos entre mayúsculas <-> minúsculas)
            nombreActual = PageMisDatos.getValueNombreInput(dFTest.driver);
            if (nombreActual.compareTo(nombreActual.toUpperCase())==0)
                nombreActual = nombreActual.toLowerCase();
            else
                nombreActual = nombreActual.toUpperCase();
                            
            PageMisDatos.setNombreInput(dFTest.driver, nombreActual);
                    
            //Seleccionamos el botón \"GUARDAR CAMBIOS\"
            PageMisDatos.clickGuardarCambios(dFTest.driver);
                                                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }           
            
        //Validaciones.
        String descripValidac = 
            "1) Aparece una pantalla con el literal \"Tus datos han sido modificados en nuestra base de datos\"";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisDatos.pageResOK(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                                    
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return nombreActual;
    }
    
    public static void validaContenidoNombre(String nombre, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) En el campo del nombre figura<b>: " + nombre + "<b>"; 
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMisDatos.getValueNombreInput(dFTest.driver).contains(nombre)) {
                listVals.add(1,State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
