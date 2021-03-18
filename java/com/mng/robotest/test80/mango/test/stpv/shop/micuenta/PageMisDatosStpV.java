package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisDatos;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.StdValidationFlags;

public class PageMisDatosStpV {

	private final PageMisDatos pageMisDatos;
	private final WebDriver driver;
	
	public PageMisDatosStpV(WebDriver driver) {
		pageMisDatos = new PageMisDatos(driver);
		this.driver = driver;
	}
	
    @Validation
    public ChecksTM validaIsPage (String usuarioReg) {
        ChecksTM validations = ChecksTM.getNew();
        validations.add(
            "Aparece una página con el la cabecera \"Mis datos\"",
            pageMisDatos.isPage(), State.Warn);
        validations.add(
            "El campo de email está bloqueado",
            pageMisDatos.emailIsDisabled(), State.Warn);
        validations.add(
            "El campo de email contiene " + usuarioReg,
            pageMisDatos.getValueEmailInput().compareTo(usuarioReg.toUpperCase())==0, State.Warn);

        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);

        return validations;
    }

    @Validation
    public ChecksTM validaIsDataAssociatedToRegister(Map<String,String> datosRegOk, String codpais) {
        String nombre = datosRegOk.get("cfName");
        String apellidos = datosRegOk.get("cfSname");
        String email = datosRegOk.get("cfEmail");
        String direccion = datosRegOk.get("cfDir1");
        String codpostal = datosRegOk.get("cfCp");
        String poblacion = datosRegOk.get("cfCity");
        String provincia = datosRegOk.get("estadosPais");

        ChecksTM validations = ChecksTM.getNew();
        validations.add(
            "Aparece un campo de contraseña de tipo password",
            pageMisDatos.isVisiblePasswordTypePassword(), State.Defect);
        validations.add(
            "El resto de campos de tipo \"inputContent\" están informados",
            pageMisDatos.emailIsDisabled(), State.Defect);
        validations.add(
            "El Nombre contiene el definido durante el registro: <b>" + nombre + "</b>",
            !(pageMisDatos.getNumInputContentVoid() > 1), State.Defect);
        validations.add(
            "El Apellidos contiene el definido durante el registro: <b>" + apellidos + "</b>",
            (pageMisDatos.getText_inputNombre().compareTo(nombre)==0), State.Defect);
        validations.add(
            "El Email contiene el definido durante el registro: <b>" + email + "</b>",
            (pageMisDatos.getText_inputEmail().toLowerCase().compareTo(email.toLowerCase())==0), State.Defect);
        validations.add(
            "La Dirección contiene la definida durante el registro: <b>" + direccion + "</b>",
            (pageMisDatos.getText_inputDireccion().compareTo(direccion)==0), State.Defect);
        validations.add(
            "El Código postal contiene el definido durante el registro: <b>" + codpostal + "</b>",
            (pageMisDatos.getText_inputCodPostal().compareTo(codpostal)==0), State.Defect);
        validations.add(
            "La población contiene la definida durante el registro: <b>" + poblacion + "</b>",
            (pageMisDatos.getText_inputPoblacion().compareTo(poblacion)==0), State.Defect);
        validations.add(
            "Está seleccionado el país definido durante el registro: <b>" + codpais + "</b>",
            (pageMisDatos.getCodPaisSelected().compareTo(codpais)==0), State.Defect);
        if (provincia != null) {
            validations.add(
                "Está seleccionada la provincia definida durante el registro: <b>" + provincia + "</b>",
                (pageMisDatos.getProvinciaSelected().compareTo(provincia)==0), State.Defect);
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
    public String modificaNombreYGuarda() {
        String nombreActual = pageMisDatos.getValueNombreInput();
        if (nombreActual.compareTo(nombreActual.toUpperCase()) == 0) {
            nombreActual = nombreActual.toLowerCase();
        } else {
            nombreActual = nombreActual.toUpperCase();
        }

        pageMisDatos.setNombreInput(nombreActual);
        pageMisDatos.clickGuardarCambios();
        validateModificationOfData();

        return nombreActual;
    }

    @Validation(
        description = "1) Aparece una pantalla de resultado Ok de la suscripción",
        level = State.Defect)
    private boolean validateModificationOfData() {
        return (pageMisDatos.pageResOK());
    }

    @Validation (
    	description="En el campo del nombre figura<b>: #{nombre}<b>",
    	level=State.Warn)
    public boolean validaContenidoNombre(String nombre) {
        return (pageMisDatos.getValueNombreInput().contains(nombre));
    }
}
