package com.mng.robotest.test80.mango.test.utils.awssecrets;

import java.io.ByteArrayInputStream;
import java.io.InputStream;


public class GetterHardCodeSecrets extends GetterSecretsFromInputStream {
	
	@Override
	InputStream getInputStreamSecrets() throws Exception {
		String lineJump = System.getProperty ("line.separator");
		String dataString = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + lineJump + 
			"<secrets>" + lineJump + 
			"	<secret type=\"SHOP_USER\">" + lineJump +
			"		<user>test.performance01@mango.com</user>" + lineJump +
			"		<password>11111</password>" + lineJump +
			"		<user_standard>ticket_digital_es@mango.com</user_standard>" + lineJump +
			"		<password_standard>AAAAA</password_standard>" + lineJump +
			"		<user_robot>robot.test@mango.com</user_robot>" + lineJump +
			"		<password_robot>BBBBB</password_robot>" + lineJump +
			"		<user_jorge>jorge.munoz.sge@mango.com</user_jorge>" + lineJump +
			"		<password_jorge>CCCCC</password_jorge>" + lineJump +
			"	</secret>" + lineJump +
			"	<secret type=\"MANTO_USER\">" + lineJump +
			"		<user>00556106</user>" + lineJump +
			"		<password>22222</password>" + lineJump +
			"	</secret>" + lineJump +
			"	<secret type=\"BROWSERSTACK_USER\">" + lineJump +
			"		<user>equipoqa1</user>" + lineJump +
			"		<password>33333</password>" + lineJump +
			"	</secret>" + lineJump +
			"	<secret type=\"VOTF_USER\">" + lineJump +
			"		<user>tda00003</user>" + lineJump +
			"		<password>44444</password>" + lineJump +
			"	</secret>" + lineJump +
			"	<secret type=\"EMPLOYEE_DATA\">" + lineJump +
			"		<user>8000007330287</user>" + lineJump +
			"		<nif>55555</nif>" + lineJump +
			"		<nombre>66666</nombre>" + lineJump +
			"	</secret>" + lineJump +
			"</secrets>";
		
		return new ByteArrayInputStream(dataString.getBytes());
	}
	
}
