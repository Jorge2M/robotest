package com.mng.robotest.domains.compra.steps.envio;

import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;

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
