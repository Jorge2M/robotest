package com.mng.robotest.test80.mango.test.stpv.shop.banner;

import java.net.URI;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
	
	public SecBannersStpV(int maxBannersToLoad, WebDriver driver) {
		managerBannersScreen = new ManagerBannersScreen(maxBannersToLoad, driver);
	}
	
    public void testPageBanners(DataCtxShop dCtxSh, int maximoBanners, DataFmwkTest dFTest) 
    throws Exception { 
        String urlPagPrincipal = dFTest.driver.getCurrentUrl();
        int sizeListBanners = managerBannersScreen.getListDataBanners().size();
        for (int posBanner=1; posBanner<=sizeListBanners && posBanner<=maximoBanners; posBanner++) {
        	boolean makeValidations = true;
            seleccionarBanner(posBanner, makeValidations, dCtxSh.appE, dCtxSh.channel, dFTest);
            dFTest.driver.get(urlPagPrincipal);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver);
            managerBannersScreen.reloadBanners(dFTest.driver); //For avoid StaleElement Exception
            sizeListBanners = managerBannersScreen.getListDataBanners().size();
        }
    }
    
    public void testCampanas(CampanasData listCampanas, DataCtxShop dCtxSh, LineaType lineaType,	DataFmwkTest dFTest) throws Exception {
    	ArrayList<DataCampana> listCampanasToTest = listCampanas.getListCampanas(dCtxSh.pais.getCodigo_pais(), 
    																			 dCtxSh.idioma.getCodigo().name(), 
    																			 lineaType.toString());
    	for (DataCampana dataCampToTest : listCampanasToTest) {
    		int posBanner = Integer.valueOf(dataCampToTest.posicion);
    		boolean makeValidations = true;
    		DatosStep datosStep = seleccionarBanner(posBanner, makeValidations, dCtxSh.appE, dCtxSh.channel, dFTest);
    		DataBanner dataBanner = managerBannersScreen.getBanner(posBanner);
    		validateCamapanaWithBannerInScreen(dataCampToTest, dataBanner, datosStep);
    	}
    }
    
    private void validateCamapanaWithBannerInScreen(DataCampana dataCampana, DataBanner dataBanner, DatosStep datosStep) {
        String descripValidac = 
            "1) Los datos de la campaña son correctos<br>" +
            	getReportCompareDataInCuteHtml(dataCampana, dataBanner);
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
//            if (getReportCompareDataCampanaTableHTML(dataCampana, dataBanner)) {
//                listVals.add(1, State.Defect);
//        	  }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }            
    }
    
    public DatosStep seleccionarBanner(int posBanner, boolean validaciones, AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        DataBanner dataBanner = this.managerBannersScreen.getBanner(posBanner);
        String urlPagPrincipal = dFTest.driver.getCurrentUrl();
        URI uriPagPrincipal = new URI(urlPagPrincipal);
        int elementosPagPrincipal = dFTest.driver.findElements(By.xpath("//*")).size();
        
        //Step
        String descripcion = "Seleccionar el <b>Banner " + dataBanner.getPosition() + "</b> y obtener sus datos:<br>" + 
        	"<b>URL</b>: " + dataBanner.getUrlBanner() + "<br>" + 
        	"<b>imagen</b>: " + dataBanner.getSrcImage() + "<br>" + 
            "<b>texto</b>: " + dataBanner.getText();
        DatosStep datosStep = new DatosStep(
            descripcion, 
            "Aparece una página correcta (con banners o artículos)");
        try {
           datosStep.setDescripcion(descripcion);
           this.managerBannersScreen.clickBannerAndWaitLoad(posBanner, dFTest.driver);

           datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        dataBanner.setUrlDestino(dFTest.driver.getCurrentUrl());
        if (validaciones) {
            //Validaciones
            validacionesGeneralesBanner(urlPagPrincipal, uriPagPrincipal, elementosPagPrincipal, datosStep, dFTest);
            switch (dataBanner.getDestinoType()) {
            case Ficha:
            	PageFichaArtStpV pageFichaStpV = new PageFichaArtStpV(app, channel);
            	pageFichaStpV.validateIsFichaCualquierArticulo(datosStep);
                break;
            default:                
            case Otros:
                validacionesBannerEstandar(app, datosStep, dFTest);
                break;
            }
        }

        return datosStep;
    }
        
    public void validacionesGeneralesBanner(String urlPagPadre, URI uriPagPadre, int elementosPagPadre, DatosStep datosStep, DataFmwkTest dFTest) 
    throws Exception {
    	int maxSecondsWait1 = 3;
    	int maxSecondsWait2 = 1;
    	int marginElements = 3;
        String descripValidac = 
            "1) La URL de la página cambia (lo esperamos hasta un máximo de " + maxSecondsWait1 + " segundos)<br>" +
            "2) La página cambia; el número de elementos DOM ha variado (en " + marginElements + " o más) " +
            	"con respecto al original (" + elementosPagPadre + ")<br>" +
            "3) No hay imágenes cortadas<br>" +
            "4) El dominio de la página se corresponde con el de la página padre:" + uriPagPadre.getHost();
        datosStep.setNOKstateByDefault();
        //datosStep.setExcepExists(false); datosStep.setResultSteps(State.Nok);
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!AllPages.validateUrlNotMatchUntil(urlPagPadre, maxSecondsWait1, dFTest.driver)) {
                 listVals.add(1, State.Defect);
            }
            if (!AllPages.validateElementsNotEqualsUntil(elementosPagPadre, marginElements, maxSecondsWait2, dFTest.driver)) {
                listVals.add(2,State.Warn); 
            }
            //3)
            ResultadoErrores resultadoImgs = WebDriverMngUtils.imagesBroken(dFTest.driver, Channel.desktop, 1/* maxErrors */, dFTest.ctx);
            if (resultadoImgs.getResultado() != ResultadoErrores.Resultado.OK) { // Si hay error lo pintamos en la descripción de la validación
                descripValidac += resultadoImgs.getlistaLogError().toString();
                if (resultadoImgs.getResultado() != ResultadoErrores.Resultado.MAX_ERRORES) {
                    listVals.add(3, State.Defect);
                }
            }
            //4)
            String urlPagActual = dFTest.driver.getCurrentUrl();
            URI uriPagActual = new URI(urlPagActual);
            if (uriPagPadre.getHost().compareTo(uriPagActual.getHost()) != 0)
                listVals.add(4, State.Warn);

            datosStep.setListResultValidations(listVals);
            
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    public void validacionesBannerEstandar(AppEcom app, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        //Validaciones
        String descripValidac = 
            "1) Aparece una página con secciones, galería, banners, bloque de contenido con imágenes o página acceso";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageLanding.haySecc_Art_Banners(app, dFTest.driver)) {
                boolean contenidoConImgs = PageLanding.hayImgsEnContenido(dFTest.driver);
                if (!contenidoConImgs) {
                    listVals.add(1, State.Warn);
                }
            }

            datosStep.setListResultValidations(listVals);
            
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    


    /**
     * Validación que comprueba que se está cargando el bloque de contenido (banners) de las homes (SHE, HE, KIDS, VIOLETA...)
     */
    public void validaBannEnContenido(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) El bloque de contenido (homeContent o bannerHome) existe y tiene >= 1 banner o >=1 map o >=1 items-edit";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            boolean existBanners = managerBannersScreen.existBanners();
            boolean existsMaps = PageLanding.hayMaps(dFTest.driver);
            boolean existsEditItems = PageLanding.hayItemsEdits(dFTest.driver);
            if (!(existBanners || existsMaps || existsEditItems)) {
                listVals.add(1, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
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