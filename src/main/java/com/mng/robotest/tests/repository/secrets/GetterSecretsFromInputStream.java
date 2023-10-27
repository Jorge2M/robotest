package com.mng.robotest.tests.repository.secrets;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.repository.secrets.entity.Secret;
import com.mng.robotest.tests.repository.secrets.entity.Secrets;

public abstract class GetterSecretsFromInputStream implements GetterSecrets {
	
	abstract InputStream getInputStreamSecrets() throws Exception;
	
	@Override
	public Secret getCredentials(SecretType secretType) {
		try {
			return getCredentials_WithException(secretType);
		}
		catch (Exception e) {
			Log4jTM.getLogger().info("Problem retrieving Local Secret {} ({})", secretType, e.getCause());
			return null;
		}
	}
	
	private Secret getCredentials_WithException(SecretType secretType) throws Exception {
		var jaxbContext = JAXBContext.newInstance( Secrets.class );
		var jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		var secrets = (Secrets) jaxbUnmarshaller.unmarshal(getInputStreamSecrets());
		return getSecret(secrets, secretType);
	}
	
	private Secret getSecret(Secrets secrets, SecretType secretType) {
		for (var secret : secrets.getSecrets()) {
			if (secret.getType().compareTo(secretType.getSecretName())==0) {
				secret.setType(secretType);
				return secret;
			}
		}
		return null;
	}
	
}
