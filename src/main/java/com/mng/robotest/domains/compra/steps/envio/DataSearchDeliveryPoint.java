package com.mng.robotest.domains.compra.steps.envio;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.beans.Pais;

public class DataSearchDeliveryPoint {
	public enum DataSearchDp {Provincia, CodigoPostal}
	
	public String data;
	public DataSearchDp typeData;
	public TipoTransporte tipoTransporte;
		
	public static DataSearchDeliveryPoint getInstance(Pago pago, AppEcom app, Pais pais) {
		DataSearchDeliveryPoint dataDp = new DataSearchDeliveryPoint();
		dataDp.tipoTransporte = pago.getTipoEnvioType(app);
		dataDp.typeData = pago.getTipoEnvioType(app).getDataSearchDp();
		switch (dataDp.typeData) {
		case CodigoPostal:
			dataDp.data = pais.getCodpos();
			break;
		case Provincia:
			dataDp.data = pago.getProvinciaEnvio();
			break;
		}
		
		return dataDp;
	}
}
