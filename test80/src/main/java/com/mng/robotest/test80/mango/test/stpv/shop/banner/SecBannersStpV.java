package com.mng.robotest.test80.mango.test.stpv.shop.banner;

import java.net.URI;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.appshop.campanas.CampanasData;
import com.mng.robotest.test80.mango.test.appshop.campanas.DataCampana;
import com.mng.robotest.test80.mango.test.appshop.campanas.DataCampana.AtributoCampana;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.ResultadoErrores;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.AllPages;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.DataBanner;
import com.mng.robotest.test80.mango.test.pageobject.shop.bannersNew.ManagerBannersScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.landing.PageLanding;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.utils.WebDriverMngUtils;

public class SecBannersStpV {
	
	int maxBannersToLoad;
	ManagerBannersScreen managerBannersScreen;
	WebDriver driver;
	
	public SecBannersStpV(int maxBannersToLoad, WebDriver driver) {
		this.driver = driver;
		managerBannersScreen = new ManagerBannersScreen(maxBannersToLoad, driver);
	}
	
	public ManagerBannersScreen getManagerBannerScreen() {
		return managerBannersScreen;
	}
	
    public void testPageBanners(DataCtxShop dCtxSh, int maximoBanners) 
    throws Exception { 
        String urlPagPrincipal = driver.getCurrentUrl();
        int sizeListBanners = managerBannersScreen.getListDataBanners().size();
        for (int posBanner=1; posBanner<=sizeListBanners && posBanner<=maximoBanners; posBanner++) {
        	boolean makeValidations = true;
            seleccionarBanner(posBanner, makeValidations, dCtxSh.appE, dCtxSh.channel);
            driver.get(urlPagPrincipal);
            WebdrvWrapp.waitForPageLoaded(driver);
            managerBannersScreen.reloadBanners(driver); //For avoid StaleElement Exception
            sizeListBanners = managerBannersScreen.getListDataBanners().size();
        }
    }
    
    public void testCampanas(CampanasData listCampanas, DataCtxShop dCtxSh, LineaType lineaType) throws Exception {
    	ArrayList<DataCampana> listCampanasToTest = 
    		listCampanas.getListCampanas(
    			dCtxSh.pais.getCodigo_pais(), 
    			dCtxSh.idioma.getCodigo().name(), 
    			lineaType.toString());
    	
    	for (DataCampana dataCampToTest : listCampanasToTest) {
    		int posBanner = Integer.valueOf(dataCampToTest.posicion);
    		boolean makeValidations = true;
    		seleccionarBanner(posBanner, makeValidations, dCtxSh.appE, dCtxSh.channel);
    		DataBanner dataBanner = managerBannersScreen.getBanner(posBanner);
    		validateCamapanaWithBannerInScreen(dataCampToTest, dataBanner);
    	}
    }
    
    @Validation
    private ChecksResult validateCamapanaWithBannerInScreen(DataCampana dataCampana, DataBanner dataBanner) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Los datos de la campaña son correctos<br>\" +\n" + getReportCompareDataInCuteHtml(dataCampana, dataBanner),
			true, State.Defect);    	
	 	return validations;
    }
    
    public void seleccionarBanner(int posBanner, boolean validaciones, AppEcom app, Channel channel) 
    throws Exception {
        DataBanner dataBanner = this.managerBannersScreen.getBanner(posBanner);
        seleccionarBanner(dataBanner, validaciones, app, channel);
    }
    
    @Step (
    	description=
    		"Seleccionar el <b>Banner #{dataBanner.getPosition()}</b> y obtener sus datos:<br>" + 
            	"<b>URL</b>: #{dataBanner.getUrlBanner()}<br>" + 
            	"<b>imagen</b>: #{dataBanner.getSrcImage()}<br>" + 
                "<b>texto</b>: #{dataBanner.getText()}",
        expected="Aparece una página correcta (con banners o artículos)")
    public void seleccionarBanner(DataBanner dataBanner, boolean validaciones, AppEcom app, Channel channel) 
    throws Exception {
        String urlPagPrincipal = driver.getCurrentUrl();
        URI uriPagPrincipal = new URI(urlPagPrincipal);
        int elementosPagPrincipal = driver.findElements(By.xpath("//*")).size();
        
        this.managerBannersScreen.clickBannerAndWaitLoad(dataBanner, driver);
        
        dataBanner.setUrlDestino(driver.getCurrentUrl());
        if (validaciones) {
            //Validaciones
            validacionesGeneralesBanner(urlPagPrincipal, uriPagPrincipal, elementosPagPrincipal);
            switch (dataBanner.getDestinoType()) {
            case Ficha:
            	PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(app, channel);
            	pageFichaStpV.validateIsFichaCualquierArticulo();
                break;
            default:                
            case Otros:
                validacionesBannerEstandar(app);
                break;
            }
        }
    }
        
    @Validation
    public ChecksResult validacionesGeneralesBanner(String urlPagPadre, URI uriPagPadre, int elementosPagPadre) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	int maxSecondsWait1 = 3;
    	int marginElements = 3;
    	int maxSecondsWait2 = 1;
	 	validations.add(
	 		"La URL de la página cambia (lo esperamos hasta un máximo de " + maxSecondsWait1 + " segundos)",
	 		AllPages.validateUrlNotMatchUntil(urlPagPadre, maxSecondsWait1, driver), State.Defect);    
	 	validations.add(
	 		"La página cambia; el número de elementos DOM ha variado (en " + marginElements + " o más) " + 
	 		"con respecto al original (" + elementosPagPadre + ")",
	 		AllPages.validateElementsNotEqualsUntil(elementosPagPadre, marginElements, maxSecondsWait2, driver), State.Warn); 
	 	
	 	int maxErrors = 1;
        ResultadoErrores resultadoImgs = WebDriverMngUtils.imagesBroken(driver, Channel.desktop, maxErrors);
        if (resultadoImgs.getResultado() != ResultadoErrores.Resultado.OK) { // Si hay error lo pintamos en la descripción de la validación
		 	validations.add(
		 		"No hay imágenes cortadas" + resultadoImgs.getlistaLogError().toString(),
		 		resultadoImgs.getResultado()==ResultadoErrores.Resultado.MAX_ERRORES, State.Defect);     
        }

        String urlPagActual = driver.getCurrentUrl();
        URI uriPagActual = new URI(urlPagActual);
	 	validations.add(
	 		"El dominio de la página se corresponde con el de la página padre:" + uriPagPadre.getHost(),
	 		uriPagPadre.getHost().compareTo(uriPagActual.getHost())==0, State.Defect);    
	 	
	 	return validations;
    }
    
    @Validation (
    	description="Aparece una página con secciones, galería, banners, bloque de contenido con imágenes o página acceso",
    	level=State.Warn)
    public boolean validacionesBannerEstandar(AppEcom app) throws Exception {
        if (!PageLanding.haySecc_Art_Banners(app, driver)) {
            return (PageLanding.hayImgsEnContenido(driver));
        }
        
        return true; 
    }
    
    @Validation (
    	description="El bloque de contenido (homeContent o bannerHome) existe y tiene >= 1 banner o >=1 map o >=1 items-edit",
    	level=State.Warn)
    public boolean validaBannEnContenido() {
        boolean existBanners = managerBannersScreen.existBanners();
        boolean existsMaps = PageLanding.hayMaps(driver);
        boolean existsEditItems = PageLanding.hayItemsEdits(driver);
        return (existBanners || existsMaps || existsEditItems);
    }
    
    private static String getReportCompareDataInCuteHtml(DataCampana dataCampana, DataBanner dataBanner) {
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append("<iframe width=\"800px;\" style=\"border:none;\" srcdoc=\"");
    	stringBuilder.append("<head><style>");
    	stringBuilder.append("table, th, td {");
    	stringBuilder.append("  border: 1px solid black;");
    	stringBuilder.append("  font: normal 8pt Arial;");
    	stringBuilder.append("  border-collapse: collapse;");
    	stringBuilder.append("  table-layout: fixed;");
    	stringBuilder.append("}");
    	stringBuilder.append("th {");
    	stringBuilder.append("  font-weight: bold;");
    	stringBuilder.append("  background-color: PaleGreen;");
    	stringBuilder.append("}");    	
    	stringBuilder.append("td {");
    	stringBuilder.append("  word-wrap:break-word;");
    	stringBuilder.append("}");    	
    	stringBuilder.append("</style></head>");
    	stringBuilder.append("<table>");
    	stringBuilder.append("<tbody>");
    	stringBuilder.append("<tr>");
    	stringBuilder.append("  <th></th>");
    	stringBuilder.append("  <th>Campaña</th>");
    	stringBuilder.append("  <th>Screen Banner</th>");
    	stringBuilder.append("</tr>");
    	stringBuilder.append(getCompareDataCampanaHTML(AtributoCampana.URL, dataCampana, dataBanner));	
    	stringBuilder.append(getCompareDataCampanaHTML(AtributoCampana.SRC, dataCampana, dataBanner));
    	stringBuilder.append(getCompareDataCampanaHTML(AtributoCampana.Destino, dataCampana, dataBanner));
    	stringBuilder.append(getCompareDataCampanaHTML(AtributoCampana.All_Text, dataCampana, dataBanner));
    	stringBuilder.append("</tbody>");
    	stringBuilder.append("</table>");
    	stringBuilder.append("\"></iframe>");
    	return (stringBuilder.toString()); 
    }
    
    private static String getCompareDataCampanaHTML(AtributoCampana atributo, DataCampana dataCampana, DataBanner dataBanner) {
    	String attCampana = dataCampana.getAtributo(atributo);
    	String attBanner = dataBanner.getEquivalentCampanaAtributo(atributo);
    	String color = "blue";
    	if ((attCampana!=null && attBanner==null) ||
    		attCampana.compareTo(attBanner)!=0)
    		color = "darkOrange";
    	
    	StringBuilder stringBuilder = new StringBuilder();
    	stringBuilder.append("<tr>");
    	stringBuilder.append("  <th>" + atributo + "</th>");
    	stringBuilder.append("	<td style='color:" + color + "'>" + attCampana + "</td>");
    	stringBuilder.append("	<td style='color:" + color + "'>" + attBanner + "</td>");
    	stringBuilder.append("</tr>");
    	return (stringBuilder.toString());
    }
}