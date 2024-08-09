package com.mng.robotest.tests.domains.compra.tests;

import java.sql.Timestamp;
import java.time.Instant;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DirectionData;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.ModalDirecEnvioNewSteps;
import com.mng.robotest.tests.domains.compra.steps.ModalMultidirectionSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.compra.steps.envio.SecMetodoEnvioSteps;
import com.mng.robotest.tests.domains.micuenta.steps.ModalDetalleCompraSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;

public class Com009 extends TestBase {
	
	private final ModalMultidirectionSteps modalMultidirectionSteps = new ModalMultidirectionSteps();
	private final ModalDirecEnvioNewSteps modalDirecEnvioSteps = new ModalDirecEnvioNewSteps();

    public Com009(Pais pais, IdiomaPais idioma) {
    	if (pais!=null) {
    		dataTest.setPais(pais);
    		dataTest.setIdioma(idioma);
    	}
        dataTest.setUserRegistered(true);
    }

    @Override
    public void execute() throws Exception {
        accessLoginAndClearBolsa();
        altaArticulosBolsaAndClickComprar();
        changeDirecciones();
    }
    private void accessLoginAndClearBolsa() throws Exception {
        access();
        new BolsaSteps().clear();
    }
    
    private void changeDirecciones() throws Exception {
    	selectEnvioEstandard();
    	
    	DirectionData directionSecondary = makeDireccionSecundaria();
    	DirectionData directionPrincipal = makeDireccionPrincipal();
        DirectionData directionEdit = editDirection(directionSecondary);
		String addressSecondary = directionSecondary.getDireccion();
		String adressEdit = directionEdit.getDireccion();
        
    	addDireccion(directionSecondary);
        editDireccion(directionEdit, adressEdit);
        removeDireccion(addressSecondary);
        
		if (!isPRO()) {
	    	addAnotherDireccion(directionPrincipal);
    		modalMultidirectionSteps.closeModal();
			comprarAndValidateCompras(directionPrincipal);
		}        
    }

	private void addAnotherDireccion(DirectionData directionPrincipal) {
		modalMultidirectionSteps.clickAnyadirOtraDireccion();
		modalDirecEnvioSteps.inputDataAndSave(directionPrincipal);
	}

	private void comprarAndValidateCompras(DirectionData directionPrincipal) throws Exception {
		executeVisaPayment();
		checkMisCompras(directionPrincipal.getDireccion());
	}

	private void removeDireccion(String addressSecondary) {
		modalMultidirectionSteps.clickEditAddress(addressSecondary);
        modalDirecEnvioSteps.clickEliminarButton();
        modalDirecEnvioSteps.confirmEliminarDirection(addressSecondary);
	}

	private void editDireccion(DirectionData directionEdit, String adressEdit) {
		modalMultidirectionSteps.clickEditAddress(adressEdit);
        modalDirecEnvioSteps.inputDataAndEdit(directionEdit);
	}

	private void addDireccion(DirectionData directionSecondary) {
		new CheckoutSteps().clickEditarDirecEnvio();
    	modalMultidirectionSteps.checkInitialContent();
    	addAnotherDireccion(directionSecondary);
	}
    
    private void selectEnvioEstandard() {
    	Pago pagoVISA = dataTest.getPais().getPago("VISA");
    	pagoVISA.setTipoEnvio(TipoTransporte.STANDARD);
    	dataTest.getDataPago().setPago(pagoVISA);
    	new SecMetodoEnvioSteps().selectMetodoEnvio("VISA");
    }
    
	private void checkMisCompras(String address) {
		var dataPago = dataTest.getDataPago();
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		
		var pageMisComprasSteps = new PageMisComprasSteps();
		if (pageMisComprasSteps.checkIsCompraOnline(codigoPedido)) {
			pageMisComprasSteps.selectCompra(codigoPedido);
			new ModalDetalleCompraSteps().checkIsVisibleDirection(address);
		}
	}    
    
    private DirectionData makeDireccionSecundaria() {
		var direction = new DirectionData();
		direction.setNombre("Jorge");
		direction.setApellidos("Muñoz Martínez");
		direction.setDireccion("c./mossen trens nº6 5º1ª " + Timestamp.from(Instant.now()));
		direction.setCodPostal(dataTest.getPais().getCodpos());
		direction.setMobil(dataTest.getPais().getTelefono());
		direction.setPrincipal(false);
		return direction;
    }
    
    private DirectionData makeDireccionPrincipal() {
		var direction = new DirectionData();
		direction.setNombre("Sonia");
		direction.setApellidos("Vidal");
		direction.setDireccion("c./xxxx nº6 5º1ª " + Timestamp.from(Instant.now()));
		direction.setCodPostal(dataTest.getPais().getCodpos());
		direction.setMobil(dataTest.getPais().getTelefono());
		direction.setPrincipal(true);
		return direction;
    }    
    
    private DirectionData editDirection(DirectionData direction) {
    	var directionReturn = DirectionData.from(direction);
    	directionReturn.setNombre("Robotest");
    	directionReturn.setApellidos("Pruebas");
        return directionReturn;
    }

}
