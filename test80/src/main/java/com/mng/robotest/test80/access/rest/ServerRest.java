package com.mng.robotest.test80.access.rest;

import com.mng.robotest.test80.access.CreatorSuiteRunMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.testmaker.boundary.access.ServerCmdLine;
import com.mng.testmaker.boundary.access.ServerCmdLine.ResultCmdServer;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

//For renewal robotest.pro.mango.com certificate manually:
//-> Execute Powershell with Administrator Privileges
//-> Path C:\\users\jorge.muñoz\Programas\win-acme
//-> Exec .\wacs.exe --force
//-> Option "S Renew specific"
//-> Option "1" [Manual] -> Nos pide un TXT-Content
//-> AWS 
//    -> Acceder al AWS de mango-test
//    -> Buscar servicio "DNS"
//    -> Clickar "Zonas hospedadas"
//    -> Seleccionar "pro.mango.com"
//    -> Buscar "robotest"
//    -> Modificar el registro _acme-challenge.robotest.pro.mango.com. introduciendo el TXT-Content 
//-> Press Enter
//-> Certificate in C:\ProgramData\win-acme\acme-v02.api.letsencrypt.org\Certificates ..
//Último resultado (02-03-2020)
//[INFO] Requesting certificate [Manual] robotest.pro.mango.com
//[INFO] Store with CertificateStore...
//[INFO] Installing certificate in the certificate store
//[INFO] Adding certificate [Manual] robotest.pro.mango.com 2020/3/2 15:41:38 to store WebHosting
//[INFO] Installing with None...
//[INFO] Uninstalling certificate from the certificate store
//[INFO] Removing certificate [Manual] robotest.pro.mango.com 2019/12/11 10:21:39 from store WebHosting
//[INFO] Next renewal scheduled at 04/26/2020 16:41:38
//[INFO] Renewal for [Manual] robotest.pro.mango.com succeeded

//Probably the automation of the renewal is not possible because is utilized --manual method with TXT autentication (dns01)
//(https://community.letsencrypt.org/t/automate-renewal-for-lets-encrypt-certificate/73972/10)
//Also, the client wacs.exe is not instaled in the windows machine because a lack of .dll
//If we want to automate the certificate renewal perhaps I must install .\wacs.exe in the Windows machine and choose the http01 autentication

public class ServerRest {

	public static void main(String[] args) throws Exception {
		ResultCmdServer result = ServerCmdLine.parse(args);
		if (result!=null && result.isOk()) {
			CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunMango.getNew();
			ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, AppEcom.class)
				.restApi(RestApiMango.class)
				.setWithParams(result)
//				.portHttp(result.getPort())
//				.portHttps(result.getSecurePort())
//				.urlServerHub(result.getUrlServerHub())
//				.urlServerSlave(result.getUrlServerSlave())
				.certificate(
					ServerRestTM.class.getResource("/robotest.pro.mango.com.pfx").toExternalForm(), 
					"yvuF62JiD6HsGVS9lqY9CsZZC/unbW1MMR3dLotF48Q=")
				.build();
			serverRest.start();
		} else {
			System.out.println("Error. Jetty Server Not Started");
		}
	}
}
