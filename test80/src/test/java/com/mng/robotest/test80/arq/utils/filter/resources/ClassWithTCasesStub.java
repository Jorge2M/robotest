package com.mng.robotest.test80.arq.utils.filter.resources;

import java.lang.reflect.Method;

import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras.TypeCompra;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.micuenta.PageMisComprasStpV;

@Test
@SuppressWarnings({"javadoc", "unused"})
public class ClassWithTCasesStub {
    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    ThreadLocal<DataCtxShop> dCtsShThread = new ThreadLocal<>();
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
            
    @BeforeMethod(groups={"Micuenta", "Canal:all_App:all"})
    @Parameters({"brwsr-path","urlBase", "AppEcom", "Channel", "xmlPaises"})
    public void login(String bpath, String urlBase, String appEcom, String channel, String xmlPaises, ITestContext context, Method method) throws Exception {
        //
    }

    @AfterMethod (groups={"Micuenta", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        //
    }       

    @Test (
        groups={"Micuenta", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, alwaysRun=true, 
        description="Verificar opciones de 'mi cuenta'")
    @Parameters({"userConDevolucionPeroSoloEnPRO", "passwordUserConDevolucion"})
    public void MIC001_Opciones_Mi_Cuenta(String userConDevolucionPeroNoEnPRO, String passwordUserConDevolucion, ITestContext context, Method method) throws Exception {
        //
    }
    
    @Test (
        groups={"Micuenta", "Canal:movil_web_App:shop"}, alwaysRun=true, 
        description="Consulta de mis compras con un usuario con datos a nivel de Tienda y Online")
    @Parameters({"userConComprasPeroSoloOnlineEnPRO", "passwordUserConCompras"})
    public void MIC002_CheckConsultaMisCompras(String userConCompras, String passwordUserConCompras, ITestContext context, Method method) throws Exception {
        //
    }    
    
    @Test (
        groups={"GaleriaProducto", "Canal:all_App:all"}, alwaysRun=true, 
        description="[Usuario registrado] Acceder a galería camisas. Filtros y ordenación. Seleccionar producto y color")    
    public void GPO001_Galeria_Camisas(ITestContext context, Method method) throws Exception {
        //
    }
    
    @Test (
        groups={"Bolsa", "Canal:desktop_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Añadir artículo a la bolsa")
    public void BOR001_AddBolsaFromGaleria_NoReg(ITestContext context, Method method) throws Exception {
        //
    }
}
