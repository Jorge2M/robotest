package com.mng.robotest.test.utils.awssecrets;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class GetterHardCodeSecrets extends GetterSecretsFromInputStream {
	
	@Override
	InputStream getInputStreamSecrets() throws Exception {
		String lineJump = System.getProperty ("line.separator");
		String dataString = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + lineJump + 
			"<secrets>" + lineJump + 
			"   <secret type=\"SHOP_USER\">" + lineJump +
			"      <user>test.performance01@mango.com</user>" + lineJump +
			"      <password>mango457</password>" + lineJump +
			"      <user_standard>ticket_digital_es@mango.com</user_standard>" + lineJump +
			"      <password_standard>mango457</password_standard>" + lineJump +
			"      <user_robot>robot.test@mango.com</user_robot>" + lineJump +
			"      <password_robot>sirjorge75</password_robot>" + lineJump +
			"      <user_francia>francia.test@mango.com</user_francia>" + lineJump +
			"      <password_francia>mango123</password_francia>" + lineJump +
			"      <user_francia_votf>francia.test@mango.com</user_francia_votf>" + lineJump +
			"      <password_francia_votf>mango123</password_francia_votf>" + lineJump +
			"   </secret>" + lineJump +
			"   <secret type=\"MANTO_USER\">" + lineJump +
			"      <user>00556106</user>" + lineJump +
			"      <password>Ireneloca2022</password>" + lineJump +
			"   </secret>" + lineJump +
			"   <secret type=\"BROWSERSTACK_USER\">" + lineJump +
			"      <user>equipoqa1</user>" + lineJump +
			"      <password>qp3dr5VJbFMAxPsT4k1b</password>" + lineJump +
			"   </secret>" + lineJump +
			"   <secret type=\"VOTF_USER\">" + lineJump +
			"      <user>tda00003</user>" + lineJump +
			"      <password>mng00003</password>" + lineJump +
			"   </secret>" + lineJump +
			"   <secret type=\"EMPLOYEE_DATA\">" + lineJump +
			"      <user>8000007330287</user>" + lineJump +
			"      <nif>32070858A</nif>" + lineJump +
			"      <nombre>OCA MUÑOZ, ADRIAN</nombre>" + lineJump +
			"   </secret>" + lineJump +
			"</secrets>";
		
		return new ByteArrayInputStream(dataString.getBytes(StandardCharsets.UTF_8));
	}
	
}
