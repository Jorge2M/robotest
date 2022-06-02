package com.mng.robotest.test.appshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.datastored.FlagsTestCkout;
import com.mng.robotest.test.getdata.products.data.Color;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.Size;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.stpv.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test.utils.PaisGetter;

public class CompraEgoitz {
	
	
	@Test (
		groups={"Compra", "Canal:desktop,mobile_App:all"}, alwaysRun=true,
		description="Compra artículos intimissim partiendo de la URL de ficha")
	public void EGO001_Compra() throws Exception {
		Pais pais=PaisGetter.get(PaisShop.Espana);
		IdiomaPais idioma = pais.getListIdiomas().get(0);
		executePurchase(pais, idioma);
	}
	
	private void executePurchase(Pais pais, IdiomaPais idioma) throws Exception {
		//Data For Test
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest(pais, idioma);
		List<GarmentCatalog> listArticles = getListArticles();
		
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
			.pago(dCtxSh.pais.getPago("PAYPAL"))
			.build()
			.checkout(From.Bolsa);
	}
	
	private DataCtxShop getCtxShForTest(Pais pais, IdiomaPais idioma) throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais=pais;
		dCtxSh.idioma=idioma;
		return dCtxSh;
	}
	
	private List<GarmentCatalog> getListArticles() throws Exception {
		List<GarmentCatalog> listReturn = new ArrayList<>();
		
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		String domain = inputParamsSuite.getDnsUrlAcceso();
		
		GarmentCatalog garment1 = new GarmentCatalog("87092508");
		garment1.setUrlFicha(domain + "/es/mujer/braguita-brasilena-estilo-anos-80-confeccionada-en-encaje-forro-interior-100-algodon_91267934.html?c=99");
		garment1.setStock(1000);
		Color color1 = new Color();
		color1.setId("99");
		color1.setLabel("Negro");
		Size size1 = new Size();
		size1.setId(20);
		size1.setLabel("S");
		color1.setSizes(Arrays.asList(size1));
		garment1.setColors(Arrays.asList(color1));
		listReturn.add(garment1);

		GarmentCatalog garment2 = new GarmentCatalog("87092508");
		garment2.setUrlFicha(domain + "/es/mujer/culotte-efecto-pantalon-corto-en-suave-y-liviano-algodon-natural--ideal-para-llevar-todos-los-dias_99483318.html?c=01");
		garment2.setStock(1000);
		Color color2 = new Color();
		color2.setId("01");
		color2.setLabel("Blanco");
		Size size2 = new Size();
		size2.setId(5);
		size2.setLabel("44");
		color2.setSizes(Arrays.asList(size2));
		garment2.setColors(Arrays.asList(color2));
		listReturn.add(garment2);
		
		return listReturn;
	}
}
