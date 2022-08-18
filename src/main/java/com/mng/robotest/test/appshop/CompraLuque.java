package com.mng.robotest.test.appshop;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataBag;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.getdata.products.data.Color;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.getdata.products.data.Size;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecBolsaSteps;
import com.mng.robotest.test.utils.PaisGetter;

public class CompraLuque {
	
	//Más info: https://confluence.mango.com/display/TT/BlackFriday+-+Stress+Tests
	
	private final List<String> codPostalesCanada = Arrays.asList( 
		"A0A 1B0",   //CAYHM
		"T6W 0V9",   //CAYEG
		"A0K 4E9",   //CAYMX
		"A0L 0A8",   //CAYUL
		"A0P 1E5",   //CAYMX
		"T2M 1Y0",   //CAYYC
		"V2P 7Y0");  //CAYVR
	
	private Random rand = SecureRandom.getInstanceStrong();
	
	public CompraLuque() throws Exception {}
	
	@Test (
		groups={"Compra", "Canal:desktop,mobile_App:all"}, alwaysRun=true,
		description="Compra USA o Irlanda")
	public void LUQ001_Compra() throws Exception {
		Pais pais=getPaisRandom(Arrays.asList(PaisShop.USA, PaisShop.IRELAND));
		IdiomaPais idioma = pais.getListIdiomas().get(0);
		executePurchase(pais, idioma);
	}
	
	private void executePurchase(Pais pais, IdiomaPais idioma) throws Exception {
		//Data For Test
		DataCtxShop dCtxSh = getCtxShForTest(pais, idioma);
		List<GarmentCatalog> listArticles = getListArticles(pais);
		
		//Access and add articles
		new AccesoSteps().oneStep(dCtxSh, false);
		DataBag dataBag = new DataBag();
		SecBolsaSteps secBolsaSteps = new SecBolsaSteps(dCtxSh.pais);
		secBolsaSteps.altaListaArticulosEnBolsa(listArticles, dataBag);
		pais.setCodpos(getCodPostal(pais));
		
		ConfigCheckout configCheckout = ConfigCheckout.config()
				.checkManto()
				.stressMode().build();
		
		DataCtxPago dCtxPago = new DataCtxPago(dCtxSh, configCheckout);
		dCtxPago.getDataPedido().setDataBag(dataBag);
		
		new CheckoutFlow.BuilderCheckout(dCtxSh, dCtxPago)
			.pago(dCtxSh.pais.getPago("VISA"))
			.build()
			.checkout(From.BOLSA);
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
	
	private List<GarmentCatalog> getListArticles(Pais pais) {
		PaisShop paisShop = PaisShop.getPais(pais.getCodigo_pais());
		switch (paisShop) {
		case USA:
		case IRELAND:
		default:
			return getArticles();
		}
	}
	
	private String getCodPostal(Pais pais) {
		PaisShop paisShop = PaisShop.getPais(pais.getCodigo_pais());
		switch (paisShop) {
		case CANADA:
			return codPostalesCanada.get(this.rand.nextInt(codPostalesCanada.size())); 
		default:
			return pais.getCodpos();
		}
	}
	
	private Pais getPaisRandom(List<PaisShop> listaPaises) {
		int randomNumber = this.rand.nextInt(listaPaises.size());
		return PaisGetter.get(listaPaises.get(randomNumber));
	}
	
	private List<GarmentCatalog> getArticles() {
		List<GarmentCatalog> listReturn = new ArrayList<>();
		int randomNumber = this.rand.nextInt(2);
		if (randomNumber>=0) {
			GarmentCatalog garment1 = new GarmentCatalog("87092508");
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
		}
		
		if (randomNumber>=1) {
			GarmentCatalog garment1 = new GarmentCatalog("87092508");
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
		}
		
		return listReturn;
	}
	
//	private List<Garment> getListArticlesCANADA() {
//		Garment garment1 = new Garment("61040184");
//		garment1.setStock(1000);
//		Color color1 = new Color();
//		color1.setId("91");
//		color1.setLabel("Negro");
//		Size size1 = new Size();
//		size1.setId(23);
//		size1.setLabel("XL");
//		color1.setSizes(Arrays.asList(size1));
//		garment1.setColors(Arrays.asList(color1));
//		
//		return Arrays.asList(garment1);
//	}
//	
//	private List<Garment> getListArticlesTHOR() {
//		Garment garment1 = new Garment("53030990");
//		//Garment garment1 = new Garment("77060509");
//		garment1.setStock(1000);
//		Color color1 = new Color();
//		color1.setId("01");
//		color1.setLabel("Blanco");
//		Size size1 = new Size();
//		size1.setId(21);
//		size1.setLabel("M");
//		color1.setSizes(Arrays.asList(size1));
//		garment1.setColors(Arrays.asList(color1));
//		
//		Garment garment2 = new Garment("53030990");
//		//Garment garment2 = new Garment("77060509");
//		garment2.setStock(1000);
//		Color color2 = new Color();
//		color2.setId("01");
//		color2.setLabel("Blanco");
//		Size size2 = new Size();
//		size2.setId(22);
//		size2.setLabel("L");
//		color2.setSizes(Arrays.asList(size2));
//		garment2.setColors(Arrays.asList(color2));
//		
//		return Arrays.asList(garment1, garment2);
//	}
}
