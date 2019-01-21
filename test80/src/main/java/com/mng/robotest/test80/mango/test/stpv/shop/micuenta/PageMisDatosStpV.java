package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisDatos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

@SuppressWarnings("javadoc")
public class PageMisDatosStpV {
    
    public static datosStep validaIsPage(String usuarioReg, datosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones.
        String descripValidac = 
            "1) Aparece una página con el la cabecera \"Mis datos\"<br>" +
            "2) El campo de email está bloqueado<br>" +
            "3) El campo de email contiene " + usuarioReg;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisDatos.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!PageMisDatos.emailIsDisabled(dFTest.driver))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (PageMisDatos.getValueEmailInput(dFTest.driver).compareTo(usuarioReg.toUpperCase())!=0)  
                fmwkTest.addValidation(3, State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validaciones estándar. 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, true/*validaJS*/, false/*validaImgBroken*/, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static datosStep validaIsDataAssociatedToRegister(HashMap<String,String> datosRegOk, String codpais, datosStep datosStep, DataFmwkTest dFTest) {
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisDatos.isVisiblePasswordTypePassword(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (PageMisDatos.getNumInputContentVoid(dFTest.driver) > 1)
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            if (PageMisDatos.getText_inputNombre(dFTest.driver).compareTo(nombre)!=0)
                fmwkTest.addValidation(3, State.Defect, listVals);
            //4)
            if (PageMisDatos.getText_inputApellidos(dFTest.driver).compareTo(apellidos)!=0)
                fmwkTest.addValidation(4, State.Defect, listVals);
            //5)
            if (PageMisDatos.getText_inputEmail(dFTest.driver).toLowerCase().compareTo(email.toLowerCase())!=0)
                fmwkTest.addValidation(5, State.Defect, listVals);
            //6)
            if (PageMisDatos.getText_inputDireccion(dFTest.driver).compareTo(direccion)!=0)
                fmwkTest.addValidation(6, State.Defect, listVals);
            //7)
            if (PageMisDatos.getText_inputCodPostal(dFTest.driver).compareTo(codpostal)!=0)
                fmwkTest.addValidation(7, State.Defect, listVals);
            //8)
            if (PageMisDatos.getText_inputPoblacion(dFTest.driver).compareTo(poblacion)!=0)
                fmwkTest.addValidation(8, State.Defect, listVals);
            //9)
            if (PageMisDatos.getCodPaisSelected(dFTest.driver).compareTo(codpais)!=0)
                fmwkTest.addValidation(9, State.Defect, listVals);
            //10)
            if (provincia!=null) {
	            if (PageMisDatos.getProvinciaSelected(dFTest.driver).compareTo(provincia)!=0)
	                fmwkTest.addValidation(10, State.Defect, listVals);
            }
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
    
    public static String modificaNombreYGuarda(DataFmwkTest dFTest) throws Exception {
        String nombreActual = "";
        datosStep datosStep = new datosStep       (
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }           
            
        //Validaciones.
        String descripValidac = 
            "1) Aparece una pantalla con el literal \"Tus datos han sido modificados en nuestra base de datos\"";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisDatos.pageResOK(dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return nombreActual;
    }
    
    public static void validaContenidoNombre(String nombre, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) En el campo del nombre figura<b>: " + nombre + "<b>"; 
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisDatos.getValueNombreInput(dFTest.driver).contains(nombre))
                fmwkTest.addValidation(1,State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
