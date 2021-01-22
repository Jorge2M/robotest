package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
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
	
	private final List<String> codPostalesCanada = Arrays.asList( 
		"A0A 1B0",   //CAYHM
		"T6W 0V9",   //CAYEG
		"A0K 4E9",   //CAYMX
		"A0L 0A8",   //CAYUL
		"A0P 1E5",   //CAYMX
		"T2M 1Y0",   //CAYYC
		"V2P 7Y0");  //CAYVR
	
    @Test (
        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
        description="Compra USA o Canadá")
    public void LUQ001_Compra() throws Exception {
    	Pais pais=getPaisRandom(Arrays.asList(PaisShop.USA, PaisShop.Canada, PaisShop.Serbia));
		IdiomaPais idioma = pais.getListIdiomas().get(0);
		executePurchase(pais, idioma);
    }
    
//    @Test (
//        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
//        description="Compra THOR")
//    public void LUQ002_Compra_THOR() throws Exception {
//		Pais pais=getPaisThor();
//		IdiomaPais idioma = pais.getListIdiomas().get(0);
//		executePurchase(pais, idioma);
//    }
//    
//    @Test (
//        groups={"Compra", "Canal:all_App:all"}, alwaysRun=true,
//        description="Compra THOR")
//    public void LUQ003_Compra_CANADA() throws Exception {
//		Pais pais=PaisGetter.get(PaisShop.Canada);
//		IdiomaPais idioma = pais.getListIdiomas().get(0);
//		executePurchase(pais, idioma);
//    }
    
    private void executePurchase(Pais pais, IdiomaPais idioma) throws Exception {
    	//Data For Test
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest(pais, idioma);
        List<Garment> listArticles = getListArticles(pais);
        
        //Access and add articles
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        DataBag dataBag = new DataBag(); 
        SecBolsaStpV.altaListaArticulosEnBolsa(listArticles, dataBag, dCtxSh, driver);
        
        //Modify Postal Code
        pais.setCodpos(getCodPostal(pais));
        
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
		case Canada:
		case Serbia:
		default:
			return getArticles_USA_CANADA_SERBIA();
//			return getListArticlesCANADA();
//		case Latvia:
//		case Lithuania:
//		case Estonia:
//		default:
//			return getListArticlesTHOR();
		}
	}
	
	private String getCodPostal(Pais pais) {
		PaisShop paisShop = PaisShop.getPais(pais.getCodigo_pais());
		switch (paisShop) {
		case Canada:
	        Random rand = new Random(); 
	        return codPostalesCanada.get(rand.nextInt(codPostalesCanada.size())); 
		default:
			return pais.getCodpos();
		}
	}
	
    private Pais getPaisRandom(List<PaisShop> listaPaises) {
    	Random rn = new Random();
    	int randomNumber = rn.nextInt(listaPaises.size());
    	return PaisGetter.get(listaPaises.get(randomNumber));
    }
	
	private List<Garment> getArticles_USA_CANADA_SERBIA() {
		List<Garment> listReturn = new ArrayList<>();
		
    	Random rn = new Random();
		int randomNumber = rn.nextInt(3);

		if (randomNumber>=0) {
	        Garment garment1 = new Garment("87091029");
	        garment1.setStock(1000);
	        Color color1 = new Color();
	        color1.setId("69");
	        color1.setLabel("Dark Navy");
	        Size size1 = new Size();
	        size1.setId(06);
	        size1.setLabel("5-6 years (116cm)");
	        color1.setSizes(Arrays.asList(size1));
	        garment1.setColors(Arrays.asList(color1));
	        listReturn.add(garment1);
		}
		
		if (randomNumber>=1) {
	        Garment garment1 = new Garment("87091029");
	        garment1.setStock(1000);
	        Color color1 = new Color();
	        color1.setId("69");
	        color1.setLabel("Dark Navy");
	        Size size1 = new Size();
	        size1.setId(06);
	        size1.setLabel("5-6 years (116cm)");
	        color1.setSizes(Arrays.asList(size1));
	        garment1.setColors(Arrays.asList(color1));
	        listReturn.add(garment1);
		}
		
		if (randomNumber==2) {
	        Garment garment1 = new Garment("87091029");
	        garment1.setStock(1000);
	        Color color1 = new Color();
	        color1.setId("69");
	        color1.setLabel("Dark Navy");
	        Size size1 = new Size();
	        size1.setId(06);
	        size1.setLabel("5-6 years (116cm)");
	        color1.setSizes(Arrays.asList(size1));
	        garment1.setColors(Arrays.asList(color1));
	        listReturn.add(garment1);
		}
        
//		if (randomNumber>=1) {
//	        Garment garment2 = new Garment("63100536");
//	        garment2.setStock(1000);
//	        Color color2 = new Color();
//	        color2.setId("99");
//	        color2.setLabel("Negro");
//	        Size size2 = new Size();
//	        size2.setId(20);
//	        size2.setLabel("M");
//	        color2.setSizes(Arrays.asList(size2));
//	        garment2.setColors(Arrays.asList(color2));
//	        listReturn.add(garment2);
//		}
//        
//		if (randomNumber==2) {
//	        Garment garment3 = new Garment("77007893");
//	        garment3.setStock(1000);
//	        Color color3 = new Color();
//	        color3.setId("99");
//	        color3.setLabel("Negro");
//	        Size size3 = new Size();
//	        size3.setId(20);
//	        size3.setLabel("S");
//	        color3.setSizes(Arrays.asList(size3));
//	        garment3.setColors(Arrays.asList(color3));
//	        listReturn.add(garment3);
//		}
        
        return listReturn;
	}
	
//	private List<Garment> getListArticlesCANADA() {
//        Garment garment1 = new Garment("61040184");
//        garment1.setStock(1000);
//        Color color1 = new Color();
//        color1.setId("91");
//        color1.setLabel("Negro");
//        Size size1 = new Size();
//        size1.setId(23);
//        size1.setLabel("XL");
//        color1.setSizes(Arrays.asList(size1));
//        garment1.setColors(Arrays.asList(color1));
//        
//        return Arrays.asList(garment1);
//	}
//	
//	private List<Garment> getListArticlesTHOR() {
//        Garment garment1 = new Garment("53030990");
//		//Garment garment1 = new Garment("77060509");
//        garment1.setStock(1000);
//        Color color1 = new Color();
//        color1.setId("01");
//        color1.setLabel("Blanco");
//        Size size1 = new Size();
//        size1.setId(21);
//        size1.setLabel("M");
//        color1.setSizes(Arrays.asList(size1));
//        garment1.setColors(Arrays.asList(color1));
//        
//        Garment garment2 = new Garment("53030990");
//        //Garment garment2 = new Garment("77060509");
//        garment2.setStock(1000);
//        Color color2 = new Color();
//        color2.setId("01");
//        color2.setLabel("Blanco");
//        Size size2 = new Size();
//        size2.setId(22);
//        size2.setLabel("L");
//        color2.setSizes(Arrays.asList(size2));
//        garment2.setColors(Arrays.asList(color2));
//        
//        return Arrays.asList(garment1, garment2);
//	}
}
