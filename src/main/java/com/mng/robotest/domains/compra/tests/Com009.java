package com.mng.robotest.domains.compra.tests;
import java.sql.Timestamp;
import java.time.Instant;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.pageobject.DataDireccion;
import com.mng.robotest.domains.compra.pageobject.DataDireccion.DataDirType;
import com.mng.robotest.domains.compra.pageobject.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.domains.compra.steps.ModalDirecEnvioSteps;
import com.mng.robotest.domains.compra.steps.ModalMultidirectionSteps;
import com.mng.robotest.domains.compra.steps.envio.SecMetodoEnvioSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.utils.PaisGetter;

public class Com009 extends TestBase {
	
	private final ModalMultidirectionSteps modalMultidirectionSteps = new ModalMultidirectionSteps();
	private final ModalDirecEnvioSteps modalDirecEnvioSteps = new ModalDirecEnvioSteps(); 

    public Com009() throws Exception {
        dataTest.setUserRegistered(true);
        dataTest.setPais(PaisGetter.get(PaisShop.ESPANA));
        dataTest.setIdioma(dataTest.getPais().getListIdiomas().get(0));
    }

    @Override
    public void execute() throws Exception {
        accessLoginAndClearBolsa();
        altaArticulosBolsaAndClickComprar();
        CTA_direcciones();
    }
    private void accessLoginAndClearBolsa() throws Exception {
        access();
        new SecBolsaSteps().clear();
    }
    private void altaArticulosBolsaAndClickComprar() throws Exception {
        new SecBolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
        new SecBolsaSteps().selectButtonComprar();
    }
    
    private void CTA_direcciones() throws Exception {
    	selectEnvioEstandard();
    	new CheckoutSteps().clickEditarDirecEnvio();
    	modalMultidirectionSteps.checkInitialContent();
    	modalMultidirectionSteps.clickAnyadirOtraDireccion();
    	
    	DataDireccion dataDireccion = makeDataDireccion();
    	modalDirecEnvioSteps.inputDataAndActualizar(dataDireccion);
    	
		String addressAdded = dataDireccion.getValue(DataDirType.direccion);
    	modalMultidirectionSteps.checkAfterAddDirection(addressAdded);
    	modalMultidirectionSteps.clickEditAddress(addressAdded);
    	modalDirecEnvioSteps.clickEliminarButton(addressAdded);
        //checkoutSteps.CtaMainDirection();
    }
    
    private void selectEnvioEstandard() throws Exception {
    	DataPago dataPago = getDataPago();
    	Pago pagoVISA = dataTest.getPais().getPago("VISA");
    	pagoVISA.setTipoEnvio(TipoTransporte.STANDARD);
    	dataPago.setPago(pagoVISA);
    	new SecMetodoEnvioSteps().selectMetodoEnvio(dataPago, "VISA");
    }
    
    private DataDireccion makeDataDireccion() {
		DataDireccion dataDirEnvio = new DataDireccion();
		dataDirEnvio.put(DataDirType.codigoPais, dataTest.getPais().getCodigo_pais());
		dataDirEnvio.put(DataDirType.codpostal, dataTest.getPais().getCodpos());					
		dataDirEnvio.put(DataDirType.name, "Jorge");
		dataDirEnvio.put(DataDirType.apellidos, "Muñoz Martínez");
		dataDirEnvio.put(DataDirType.direccion, "c./mossen trens nº6 5º1ª " + Timestamp.from(Instant.now()));
		dataDirEnvio.put(DataDirType.email, dataTest.getUserConnected());
		dataDirEnvio.put(DataDirType.telefono, dataTest.getPais().getMobiluser());
		return dataDirEnvio;
    }

}
