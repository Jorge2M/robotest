package com.mng.robotest.domains.compra.tests;

import java.sql.Timestamp;
import java.time.Instant;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.pageobject.DirectionData;
import com.mng.robotest.domains.compra.pageobject.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.domains.compra.steps.ModalDirecEnvioNewSteps;
import com.mng.robotest.domains.compra.steps.ModalMultidirectionSteps;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.domains.compra.steps.envio.SecMetodoEnvioSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.utils.PaisGetter;

public class Com009 extends TestBase {
	
	private final ModalMultidirectionSteps modalMultidirectionSteps = new ModalMultidirectionSteps();
	private final ModalDirecEnvioNewSteps modalDirecEnvioSteps = new ModalDirecEnvioNewSteps();
    private final CompraSteps compraSteps = new CompraSteps();

    public Com009() throws Exception {
        dataTest.setUserConnected("e2e.es.test@mango.com");
        dataTest.setPasswordUser("hsXPv7rUoYw3QnMKRhPT");
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
    	//Selección envío a domicilio
    	selectEnvioEstandard();
    	
        //Definimos datos de direcciones
    	DirectionData directionSecondary = makeDireccionSecundaria();
    	DirectionData directionPrincipal = makeDireccionPrincipal();
        DirectionData directionEdit = editDirection(directionSecondary);
		String addressSecondary = directionSecondary.getDireccion();
		String adressEdit = directionEdit.getDireccion();
        
		//Añadimos direccion
    	new CheckoutSteps().clickEditarDirecEnvio();
    	modalMultidirectionSteps.checkInitialContent();
    	modalMultidirectionSteps.clickAnyadirOtraDireccion();
    	modalDirecEnvioSteps.inputDataAndSave(directionSecondary);

        //Editamos dirección
        new CheckoutSteps().clickEditarDirecEnvio();
        modalMultidirectionSteps.clickEditAddress(adressEdit);
        modalDirecEnvioSteps.inputDataAndEdit(directionEdit);

        //Eliminamos dirección
        new CheckoutSteps().clickEditarDirecEnvio();
        modalMultidirectionSteps.clickEditAddress(addressSecondary);
        modalDirecEnvioSteps.clickEliminarButton();
        modalDirecEnvioSteps.confirmEliminarDirection(addressSecondary);
        
		if (!isPRO()) {
			//Añadimos direccion
	    	modalMultidirectionSteps.clickAnyadirOtraDireccion();
	    	modalDirecEnvioSteps.inputDataAndSave(directionPrincipal);
	        
	        //Cerrar el modal
	        modalMultidirectionSteps.closeModal();			
			
	        //Comprar
			DataPago dataPago = executeVisaPayment();
			
			//Validar mis compras
			checkMisCompras(dataPago);
		}        
    }
    
    private DataPago executeVisaPayment() throws Exception {
        DataPago dataPago = getDataPago();
        dataPago.setPago(dataTest.getPais().getPago("VISA"));
        compraSteps.startPayment(dataPago, true);

        new PageResultPagoSteps().validateIsPageOk(dataPago);
        return dataPago;
    }

    private void selectEnvioEstandard() throws Exception {
    	DataPago dataPago = getDataPago();
    	Pago pagoVISA = dataTest.getPais().getPago("VISA");
    	pagoVISA.setTipoEnvio(TipoTransporte.STANDARD);
    	dataPago.setPago(pagoVISA);
    	new SecMetodoEnvioSteps().selectMetodoEnvio(dataPago, "VISA");
    }
    
    private DirectionData makeDireccionSecundaria() {
		DirectionData direction = new DirectionData();
		direction.setNombre("Jorge");
		direction.setApellidos("Muñoz Martínez");
		direction.setDireccion("c./mossen trens nº6 5º1ª " + Timestamp.from(Instant.now()));
		direction.setCodPostal(dataTest.getPais().getCodpos());
		direction.setMobil(dataTest.getPais().getTelefono());
		direction.setPrincipal(false);
		return direction;
    }
    
    private DirectionData makeDireccionPrincipal() {
		DirectionData direction = new DirectionData();
		direction.setNombre("Sonia");
		direction.setApellidos("Vidal");
		direction.setDireccion("c./xxxx nº6 5º1ª " + Timestamp.from(Instant.now()));
		direction.setCodPostal(dataTest.getPais().getCodpos());
		direction.setMobil(dataTest.getPais().getTelefono());
		direction.setPrincipal(true);
		return direction;
    }    
    
    private DirectionData editDirection(DirectionData direction) {
        direction.setNombre("Robotest");
        direction.setApellidos("Pruebas");
        direction.setDireccion(""+Timestamp.from(Instant.now()));
        return direction;
    }
    
	private void checkMisCompras(DataPago dataPago) throws Exception {
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		new PageMisComprasSteps().validateIsCompraOnline(codigoPedido);
	}
}
