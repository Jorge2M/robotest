package com.mng.robotest.test80.mango.test.appshop;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.getdata.products.data.Color;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.getdata.products.data.Size;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.PagoNavigationsStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

public class CompraLuque {
	
    @Test (
        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
        description="Compra USA")
    public void LUQ001_Compra_USA() throws Exception {
		Pais pais=PaisGetter.get(PaisShop.USA);
		IdiomaPais idioma = pais.getListIdiomas().get(0);
		executePurchase(pais, idioma);
    }
    
    @Test (
        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
        description="Compra THOR")
    public void LUQ002_Compra_THOR() throws Exception {
		Pais pais=getPaisThor();
    	//Pais pais=PaisGetter.get(PaisShop.España);
		IdiomaPais idioma = pais.getListIdiomas().get(0);
		executePurchase(pais, idioma);
    }
    
    private void executePurchase(Pais pais, IdiomaPais idioma) throws Exception {
    	//Data For Test
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest(pais, idioma);
        List<Garment> listArticles = getListArticles(pais);
        
        //Access and add articles
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        DataBag dataBag = new DataBag(); 
        SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, driver);
        
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
        PagoNavigationsStpV.testFromBolsaToCheckoutMetPago(dCtxSh, dCtxPago, driver);
        
        //Payment
        Pago pagoVISA = dCtxSh.pais.getPago("VISA");
        dCtxPago.getDataPedido().setPago(pagoVISA);
        PagoNavigationsStpV.checkPasarelaPago(dCtxPago, dCtxSh, driver);
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
	
	private List<Garment> getListArticles(Pais pais) {
		PaisShop paisShop = PaisShop.getPais(pais.getCodigo_pais());
		switch (paisShop) {
		case USA:
			return getListArticlesUSA();
		case Latvia:
		case Lithuania:
		case Estonia:
		default:
			return getListArticlesTHOR();
		}
	}
	
    private Pais getPaisThor() {
    	Random rn = new Random();
    	int randomNumber = rn.nextInt(3) + 1;
    	switch (randomNumber) {
    	case 1:
    		return PaisGetter.get(PaisShop.Latvia);
    	case 2:
    		return PaisGetter.get(PaisShop.Lithuania);
    	case 3:
    	default:
    		return PaisGetter.get(PaisShop.Estonia);
    	}
    }
	
	private List<Garment> getListArticlesUSA() {
        Garment garment1 = new Garment("63100536");
        garment1.setStock(1000);
        Color color1 = new Color();
        color1.setId("99");
        color1.setLabel("Negro");
        Size size1 = new Size();
        size1.setId(20);
        size1.setLabel("S");
        color1.setSizes(Arrays.asList(size1));
        garment1.setColors(Arrays.asList(color1));
        
        Garment garment2 = new Garment("63100536");
        garment2.setStock(1000);
        Color color2 = new Color();
        color2.setId("99");
        color2.setLabel("Negro");
        Size size2 = new Size();
        size2.setId(21);
        size2.setLabel("M");
        color2.setSizes(Arrays.asList(size2));
        garment2.setColors(Arrays.asList(color2));
        
        return Arrays.asList(garment1, garment2);
	}
	
	private List<Garment> getListArticlesTHOR() {
        Garment garment1 = new Garment("53030990");
		//Garment garment1 = new Garment("77060509");
        garment1.setStock(1000);
        Color color1 = new Color();
        color1.setId("01");
        color1.setLabel("Blanco");
        Size size1 = new Size();
        size1.setId(21);
        size1.setLabel("M");
        color1.setSizes(Arrays.asList(size1));
        garment1.setColors(Arrays.asList(color1));
        
        Garment garment2 = new Garment("53030990");
        //Garment garment2 = new Garment("77060509");
        garment2.setStock(1000);
        Color color2 = new Color();
        color2.setId("01");
        color2.setLabel("Blanco");
        Size size2 = new Size();
        size2.setId(22);
        size2.setLabel("L");
        color2.setSizes(Arrays.asList(size2));
        garment2.setColors(Arrays.asList(color2));
        
        return Arrays.asList(garment1, garment2);
	}
}
