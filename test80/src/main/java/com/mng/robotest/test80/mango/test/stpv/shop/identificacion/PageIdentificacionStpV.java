package com.mng.robotest.test80.mango.test.stpv.shop.identificacion;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.identificacion.PageIdentificacion;
import com.mng.robotest.test80.mango.test.stpv.shop.AllPagesStpV;

public class PageIdentificacionStpV {

	@Step (
		description="Seleccionar el link 'Iniciar Sesión' e introducir credenciales incorrectas: <b>#{usrExistente}, #{password}</b>",
        expected="Aparece el correspondiente mensaje de error")
    public static void inicioSesionDatosKO(String usrExistente, String password, Channel channel, AppEcom appE, WebDriver driver) 
    throws Exception {
        PageIdentificacion.iniciarSesion(usrExistente, password, channel, appE, driver);

        //Validaciones
        checkTextoCredencialesKO(driver);
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/);      
    }
	
	@Validation (
		description="Aparece el texto \"#{PageIdentificacion.getLiteralAvisiCredencialesKO()}\"",
		level=State.Defect)
	private static boolean checkTextoCredencialesKO(WebDriver driver) {
        return (PageIdentificacion.isErrorEmailoPasswordKO(driver));
	}
    
	@Step (
		description="Seleccionar el link \"¿Has olvidado tu contraseña?\"", 
        expected="Aparece la página de cambio de contraseña")
    public static void selectHasOlvidadoTuContrasenya(WebDriver driver) 
    throws Exception {
        PageIdentificacion.clickHasOlvidadoContrasenya(driver); 

        //Validaciones
        PageRecuperaPasswdStpV.isPage(driver); 
        AllPagesStpV.validacionesEstandar(true/*validaSEO*/, false/*validaJS*/, false/*validaImgBroken*/);
    }
}
