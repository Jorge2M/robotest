package com.mng.robotest.tests.domains.compra.steps.envio;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;

public class DataSearchDeliveryPoint {
	
	public enum DataSearchDp { PROVINCIA, CODIGO_POSTAL }
	
	private final String data;
	private final DataSearchDp typeData;
	private final TipoTransporte tipoTransporte;
		
	public DataSearchDeliveryPoint(Pago pago, AppEcom app, Pais pais) {
		tipoTransporte = pago.getTipoEnvioType(app);
		typeData = pago.getTipoEnvioType(app).getDataSearchDp();
		if (typeData == DataSearchDp.CODIGO_POSTAL) {
			data = pais.getCodpos();
		}
		else {
			data = pago.getProvinciaEnvio();
		}
	}

	public String getData() {
		return data;
	}

	public DataSearchDp getTypeData() {
		return typeData;
	}

	public TipoTransporte getTipoTransporte() {
		return tipoTransporte;
	}
	
}
