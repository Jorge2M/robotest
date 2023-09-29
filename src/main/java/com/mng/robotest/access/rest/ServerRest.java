package com.mng.robotest.access.rest;

import com.mng.robotest.access.CreatorSuiteRunMango;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.conf.Suites;
import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine;
import com.github.jorge2m.testmaker.boundary.access.ServerCmdLine.ResultCmdServer;
import com.github.jorge2m.testmaker.restcontroller.ServerRestTM;

//For renewal robotest.pro.mango.com certificate manually:
//-> Execute Powershell with Administrator Privileges
//-> Path C:\\users\jorge.muñoz\Programas\win-acme
//-> Exec .\wacs.exe --force
//-> Option "S Renew specific"
//-> Option "1" [Manual] -> Nos pide un TXT-Content
//-> AWS 
//	-> Acceder al AWS de mangomng
//	-> Buscar servicio "DNS" (Route 53)
//	-> Clickar "Zonas hospedadas"
//	-> Seleccionar "pro.mango.com"
//	-> Buscar "robotest"
//	-> Modificar el registro _acme-challenge.robotest.pro.mango.com. introduciendo el TXT-Content que pinta wacs.exe
//	  Notas: 
//			-Esperar que esté disponible mediante "nslookup -q=txt _acme-challenge.robotest.pro.mango.com" en PS (tarda unos minutos)
//			-Desconectar la VPN
//-> Press Enter
//-> Certificate in C:\ProgramData\win-acme\acme-v02.api.letsencrypt.org\Certificates ..
//-> Mover el .pfx a /robotest.pro.mango.com.pfx
//
//Último resultado (04-6-2021)
//[WARN] Cached certificate available but not used with --force. Use 'Renew specific' or 'Renew all' in the main menu to run unscheduled renewals without hitting rate limits.
//[INFO] Requesting certificate [Manual] robotest.pro.mango.com
//[INFO] Store with CertificateStore...
//[INFO] Installing certificate in the certificate store
//[INFO] Adding certificate [Manual] robotest.pro.mango.com 2021/6/4 9:53:04 to store WebHosting
//[INFO] Installing with None...
//[INFO] Uninstalling certificate from the certificate store
//[INFO] Removing certificate [Manual] robotest.pro.mango.com 2021/6/4 9:27:11 from store WebHosting
//[INFO] Next renewal scheduled at 07/29/2021 09:53:04
//[INFO] Renewal for [Manual] robotest.pro.mango.com succeeded

//Probably the automation of the renewal is not possible because is utilized --manual method with TXT autentication (dns01)
//(https://community.letsencrypt.org/t/automate-renewal-for-lets-encrypt-certificate/73972/10)
//Also, the client wacs.exe is not instaled in the windows machine because a lack of .dll
//If we want to automate the certificate renewal perhaps I must install .\wacs.exe in the Windows machine and choose the http01 autentication


//** CREATION / RENEWAL **
//1) Accder a la máquina-3 de Robotest
//2) cd D:/Utils/win-acme
//3) ./wacs.exe --validation route53 --route53iamrole x

//** CREATION **/
//4)  Option M: Create certificate (full options)
//5)  Option 2: Manual input
//6)  Host: robotest.pro.mango.com + Enter
//7)  Option 6: [dns-01] Create verification records in AWS Route 53 + Enter
//8)  Option 2: RSA Key
//9)  Option 3: PFX archive + d:/TestWebdriver/test80-1.0
//10) Option 1: None
//11) Option 5: No (additional) store steps
//12) Option 4: No (additional) installation steps (este podríamos cambiarlo para que ejecute un .bat con el zipado)

public class ServerRest {

	public static void main(String[] args) throws Exception {
		ResultCmdServer result = ServerCmdLine.parse(args);
		if (result!=null && result.isOk()) {
			var creatorSuiteRun = CreatorSuiteRunMango.getNew();
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
