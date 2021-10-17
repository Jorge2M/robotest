package com.mng.robotest.test80.mango.test.utils.awssecrets;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import com.github.jorge2m.testmaker.conf.Log4jTM;


public class GetterLocalSecrets implements GetterSecrets {

	private final static String PATH_FILE = "C:\\mango\\ficheros\\secrets\\secrets.xml";
	
	@Override
	public Secret getCredentials(SecretType secretType) {
		try {
			return getCredentials_WithException(secretType);
		}
		catch (Exception e) {
			Log4jTM.getLogger().warn("Problem retrieving Local Secret ", e);
			return null;
		}
	}
	
	private Secret getCredentials_WithException(SecretType secretType) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance( Secrets.class );
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Secrets secrets = (Secrets) jaxbUnmarshaller.unmarshal(getInputStreamSecrets());		
		return getSecret(secrets, secretType);
	}
	
	InputStream getInputStreamSecrets() throws Exception {
		return new FileInputStream(new File(PATH_FILE));
	}
	
	private Secret getSecret(Secrets secrets, SecretType secretType) {
		for (Secret secret : secrets.getSecrets()) {
			if (secret.getType().compareTo(secretType.getSecretName())==0) {
				secret.setType(secretType);
				return secret;
			}
		}
		return null;
	}
	
}
