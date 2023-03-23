package com.mng.robotest.domains.compra.steps.envio;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;

public class DataSearchDeliveryPoint {
	
	public enum DataSearchDp { PROVINCIA, CODIGO_POSTAL }
	
	public String data;
	public DataSearchDp typeData;
	public TipoTransporte tipoTransporte;
		
	public static DataSearchDeliveryPoint getInstance(Pago pago, AppEcom app, Pais pais) {
		var dataDp = new DataSearchDeliveryPoint();
		dataDp.tipoTransporte = pago.getTipoEnvioType(app);
		dataDp.typeData = pago.getTipoEnvioType(app).getDataSearchDp();
		if (dataDp.typeData == DataSearchDp.CODIGO_POSTAL) {
			dataDp.data = pais.getCodpos();
		}
		else {
			dataDp.data = pago.getProvinciaEnvio();
		}
		
		return dataDp;
	}
}
