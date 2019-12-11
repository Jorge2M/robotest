package com.mng.robotest.test80.access.rest;

import com.mng.robotest.test80.access.CreatorSuiteRunMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;
import com.mng.testmaker.domain.CreatorSuiteRun;
import com.mng.testmaker.restcontroller.ServerRestTM;

//For renewal robotest.pro.mango.com certificate manually:
//-> Execute Powershell with Administrator Privileges
//-> Path C:\\users\jorge.muñoz\Programas\win-acme
//-> Exec .\wacs.exe --force
//-> Option "S Renew specific"
//-> Certificate in C:\ProgramData\win-acme\acme-v02.api.letsencrypt.org\Certificates
//[INFO] Adding renewal for [Manual] robotest.pro.mango.com
//[INFO] Next renewal scheduled at 02/04/2020 10:21:39

//Probably the automation of the renewal is not possible because is utilized --manual method with TXT autentication (dns01)
//(https://community.letsencrypt.org/t/automate-renewal-for-lets-encrypt-certificate/73972/10)
//Also, the client wacs.exe is not instaled in the windows machine because a lack of .dll
//If we want to automate the certificate renewal perhaps I must install .\wacs.exe in the Windows machine and choose the http01 autentication

public class ServerRest {

	public static void main(String[] args) throws Exception {
		CreatorSuiteRun creatorSuiteRun = CreatorSuiteRunMango.getNew();
		ServerRestTM serverRest = new ServerRestTM.Builder(creatorSuiteRun, Suites.class, AppEcom.class).
					restApi(RestApiMango.class).
					portHttp(80).
					portHttps(443).
					certificate(
						ServerRestTM.class.getResource("/robotest.pro.mango.com.pfx").toExternalForm(), 
						"yvuF62JiD6HsGVS9lqY9CsZZC/unbW1MMR3dLotF48Q=").
					build();
		serverRest.start();
	}
}
