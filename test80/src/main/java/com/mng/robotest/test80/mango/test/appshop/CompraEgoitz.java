package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.datastored.FlagsTestCkout;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.getdata.products.data.Color;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.Size;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class CompraEgoitz {
	
	
    @Test (
        groups={"Compra", "Canal:desktop,mobile_App:all"}, alwaysRun=true,
        description="Compra artículos intimissim partiendo de la URL de ficha")
    public void EGO01_Compra() throws Exception {
    	Pais pais=PaisGetter.get(PaisShop.España);
		IdiomaPais idioma = pais.getListIdiomas().get(0);
		executePurchase(pais, idioma);
    }
    
    private void executePurchase(Pais pais, IdiomaPais idioma) throws Exception {
    	//Data For Test
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest(pais, idioma);
        List<Garment> listArticles = getListArticles();
        
        //Access and add articles
        AccesoStpV.oneStep(dCtxSh, false, driver);
        DataBag dataBag = new DataBag();
    	SecBolsaStpV secBolsaStpV = new SecBolsaStpV(dCtxSh, driver);
        secBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag);
        
        //To checkout Page
        FlagsTestCkout fTCkout = new FlagsTestCkout();
        fTCkout.validaPasarelas = true;  
        fTCkout.validaPagos = true;
        fTCkout.validaPedidosEnManto = false;
        fTCkout.emailExist = false; 
        fTCkout.trjGuardada = false;
        fTCkout.isEmpl = false;
        fTCkout.stressMode = true;
        DataCtxPago dCtxPago = new DataCtxPago(dCtxSh);
        dCtxPago.setFTCkout(fTCkout);
        dCtxPago.getDataPedido().setDataBag(dataBag);
        
        new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago, driver)
        	.pago(dCtxSh.pais.getPago("VISA"))
        	.build()
        	.checkout(From.Bolsa);
    }
    
	private DataCtxShop getCtxShForTest(Pais pais, IdiomaPais idioma) throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais=pais;
		dCtxSh.idioma=idioma;
		return dCtxSh;
	}
	
	private List<Garment> getListArticles() {
		List<Garment> listReturn = new ArrayList<>();
		
        Garment garment1 = new Garment("87092508");
        garment1.setUrlFicha("https://shop.mango.com/es/hombre/camisetas-lisas/camiseta-algodon-stretch_87092508.html?c=99&busqref=true");
        garment1.setStock(1000);
        Color color1 = new Color();
        color1.setId("99");
        color1.setLabel("Negro");
        Size size1 = new Size();
        size1.setId(21);
        size1.setLabel("M");
        color1.setSizes(Arrays.asList(size1));
        garment1.setColors(Arrays.asList(color1));
        listReturn.add(garment1);

        Garment garment2 = new Garment("87092508");
        garment1.setUrlFicha("https://shop.mango.com/es/hombre/camisetas-lisas/camiseta-algodon-stretch_87092508.html?c=99&busqref=true");
        garment2.setStock(1000);
        Color color2 = new Color();
        color2.setId("99");
        color2.setLabel("Negro");
        Size size2 = new Size();
        size2.setId(20);
        size2.setLabel("S");
        color2.setSizes(Arrays.asList(size2));
        garment1.setColors(Arrays.asList(color2));
        listReturn.add(garment2);
        
        return listReturn;
	}
}
