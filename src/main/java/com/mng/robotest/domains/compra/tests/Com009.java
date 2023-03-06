package com.mng.robotest.domains.compra.tests;

import java.sql.Timestamp;
import java.time.Instant;

import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.domains.compra.pageobjects.DirectionData;
import com.mng.robotest.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.domains.compra.steps.ModalDirecEnvioNewSteps;
import com.mng.robotest.domains.compra.steps.ModalMultidirectionSteps;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.domains.compra.steps.envio.SecMetodoEnvioSteps;
import com.mng.robotest.domains.micuenta.steps.ModalDetalleCompraSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.datastored.DataPago;

public class Com009 extends TestBase {
	
	private final ModalMultidirectionSteps modalMultidirectionSteps = new ModalMultidirectionSteps();
	private final ModalDirecEnvioNewSteps modalDirecEnvioSteps = new ModalDirecEnvioNewSteps();
    private final CompraSteps compraSteps = new CompraSteps();

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
        new SecBolsaSteps().clear();
    }
    private void altaArticulosBolsaAndClickComprar() throws Exception {
        new SecBolsaSteps().altaListaArticulosEnBolsa(getArticles(1));
        new SecBolsaSteps().selectButtonComprar();
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
		DataPago dataPago = executeVisaPayment();
		checkMisCompras(dataPago, directionPrincipal.getDireccion());
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
    
    private DataPago executeVisaPayment() throws Exception {
        DataPago dataPago = getDataPago();
        compraSteps.startPayment(dataPago, true);

        new PageResultPagoSteps().validateIsPageOk(dataPago);
        return dataPago;
    }

    private void selectEnvioEstandard() {
    	DataPago dataPago = getDataPago();
    	Pago pagoVISA = dataTest.getPais().getPago("VISA");
    	pagoVISA.setTipoEnvio(TipoTransporte.STANDARD);
    	dataPago.setPago(pagoVISA);
    	new SecMetodoEnvioSteps().selectMetodoEnvio(dataPago, "VISA");
    }
    
	private void checkMisCompras(DataPago dataPago, String address) {
		String codigoPedido = dataPago.getDataPedido().getCodpedido();
		new PageResultPagoSteps().selectMisCompras();
		
		PageMisComprasSteps pageMisComprasSteps = new PageMisComprasSteps();
		pageMisComprasSteps.validateIsCompraOnline(codigoPedido);
		pageMisComprasSteps.selectCompra(codigoPedido);
		new ModalDetalleCompraSteps().checkIsVisibleDirection(address);
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
    	DirectionData directionReturn = DirectionData.from(direction);
    	directionReturn.setNombre("Robotest");
    	directionReturn.setApellidos("Pruebas");
        return directionReturn;
    }

}
