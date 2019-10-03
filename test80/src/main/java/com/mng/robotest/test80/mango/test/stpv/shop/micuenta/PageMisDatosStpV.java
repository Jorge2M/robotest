package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.HashMap;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.annotations.step.Step;
import com.mng.testmaker.utils.DataFmwkTest;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.annotations.validation.ChecksResult;
import com.mng.testmaker.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisDatos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

public class PageMisDatosStpV {

    @Validation
    public static ChecksResult validaIsPage (String usuarioReg, WebDriver driver) throws Exception {
        ChecksResult validations = ChecksResult.getNew();
        validations.add(
            "Aparece una página con el la cabecera \"Mis datos\"",
            PageMisDatos.isPage(driver), State.Warn);
        validations.add(
            "El campo de email está bloqueado",
            PageMisDatos.emailIsDisabled(driver), State.Warn);
        validations.add(
            "El campo de email contiene " + usuarioReg,
            PageMisDatos.getValueEmailInput(driver).compareTo(usuarioReg.toUpperCase())==0, State.Warn);

        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);

        return validations;
    }

    @Validation
    public static ChecksResult validaIsDataAssociatedToRegister (HashMap<String,String> datosRegOk, String codpais, WebDriver driver) 
    throws Exception {
        String nombre = datosRegOk.get("cfName");
        String apellidos = datosRegOk.get("cfSname");
        String email = datosRegOk.get("cfEmail");
        String direccion = datosRegOk.get("cfDir1");
        String codpostal = datosRegOk.get("cfCp");
        String poblacion = datosRegOk.get("cfCity");
        String provincia = datosRegOk.get("estadosPais");

        ChecksResult validations = ChecksResult.getNew();
        validations.add(
            "Aparece un campo de contraseña de tipo password",
            PageMisDatos.isVisiblePasswordTypePassword(driver), State.Defect);
        validations.add(
            "El resto de campos de tipo \"inputContent\" están informados",
            PageMisDatos.emailIsDisabled(driver), State.Defect);
        validations.add(
            "El Nombre contiene el definido durante el registro: <b>" + nombre + "</b>",
            !(PageMisDatos.getNumInputContentVoid(driver) > 1), State.Defect);
        validations.add(
            "El Apellidos contiene el definido durante el registro: <b>" + apellidos + "</b>",
            (PageMisDatos.getText_inputNombre(driver).compareTo(nombre)==0), State.Defect);
        validations.add(
            "El Email contiene el definido durante el registro: <b>" + email + "</b>",
            (PageMisDatos.getText_inputEmail(driver).toLowerCase().compareTo(email.toLowerCase())==0), State.Defect);
        validations.add(
            "La Dirección contiene la definida durante el registro: <b>" + direccion + "</b>",
            (PageMisDatos.getText_inputDireccion(driver).compareTo(direccion)==0), State.Defect);
        validations.add(
            "El Código postal contiene el definido durante el registro: <b>" + codpostal + "</b>",
            (PageMisDatos.getText_inputCodPostal(driver).compareTo(codpostal)==0), State.Defect);
        validations.add(
            "La población contiene la definida durante el registro: <b>" + poblacion + "</b>",
            (PageMisDatos.getText_inputPoblacion(driver).compareTo(poblacion)==0), State.Defect);
        validations.add(
            "Está seleccionado el país definido durante el registro: <b>" + codpais + "</b>",
            (PageMisDatos.getCodPaisSelected(driver).compareTo(codpais)==0), State.Defect);
        if (provincia != null) {
            validations.add(
                "Está seleccionada la provincia definida durante el registro: <b>" + provincia + "</b>",
                (PageMisDatos.getProvinciaSelected(driver).compareTo(provincia)==0), State.Defect);
        }
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);

        return validations;
    }

    @Step(
        description = "Modificar el nombre (cambio de mayúsculas<->minúsculas) + Botón \"Modificar Datos\"",
        expected = "Aparece la confirmación que los datos se han modificados")
    public static String modificaNombreYGuarda (DataFmwkTest dFTest) throws Exception {
        String nombreActual = PageMisDatos.getValueNombreInput(dFTest.driver);
        if (nombreActual.compareTo(nombreActual.toUpperCase()) == 0) {
            nombreActual = nombreActual.toLowerCase();
        } else {
            nombreActual = nombreActual.toUpperCase();
        }

        PageMisDatos.setNombreInput(dFTest.driver, nombreActual);
        PageMisDatos.clickGuardarCambios(dFTest.driver);
        validateModificationOfData(dFTest.driver);

        return nombreActual;
    }

    @Validation(
        description = "1) Aparece una pantalla con el literal \"Tus datos han sido modificados en nuestra base de datos\"",
        level = State.Defect)
    private static boolean validateModificationOfData (WebDriver driver) {
        return (PageMisDatos.pageResOK(driver));
    }

    @Validation (
    	description="En el campo del nombre figura<b>: #{nombre}<b>",
    	level=State.Warn)
    public static boolean validaContenidoNombre(String nombre, WebDriver driver) {
        return (PageMisDatos.getValueNombreInput(driver).contains(nombre));
    }
}
