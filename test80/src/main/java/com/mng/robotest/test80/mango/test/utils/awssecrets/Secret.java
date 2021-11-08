package com.mng.robotest.test80.mango.test.utils.awssecrets;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.mng.robotest.test80.mango.test.utils.awssecrets.GetterSecrets.SecretType;

public class Secret {
	
	private String type;
	private String user;
	private String password;
	private String user_standard;
	private String password_standard;
	private String user_robot;
	private String password_robot;
	private String user_jorge;
	private String password_jorge;
	private String user_francia;
	private String password_francia;
	private String nif;
	private String nombre;
	
	public String getType() {
		return type;
	}
	
	@XmlAttribute(name="type")
	public void setType(String type) {
		this.type = type;
	}
	public void setType(SecretType secretType) {
		setType(secretType.toString());
	}
//	
//	public UserPassword getShopUser(TypeShopUser typeShopUser) {
//		switch (typeShopUser) {
//		case STANDARD:
//			return new UserPassword(user_standard, password_standard);
//		case ROBOT:
//			return new UserPassword(user_robot, password_robot);
//		case PERFORMANCE:
//		default:
//			return new UserPassword(user, password);
//		}
//	}
	
	//TODO remove when each enum have a Aws Secret
	@SuppressWarnings("incomplete-switch")
	public String getUser() {
		switch (SecretType.valueOf(type)) {
		case SHOP_STANDARD_USER:
			return user_standard;
		case SHOP_ROBOT_USER:
			return user_robot;
		case SHOP_JORGE_USER:
			return user_jorge;
		case SHOP_FRANCIA_USER:
			return user_francia;
		}
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	@SuppressWarnings("incomplete-switch")
	public String getPassword() {
		switch (SecretType.valueOf(type)) {
		case SHOP_STANDARD_USER:
			return password_standard;
		case SHOP_ROBOT_USER:
			return password_robot;
		case SHOP_FRANCIA_USER:
			return password_francia;
		case SHOP_JORGE_USER:
			return password_jorge;
		}
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
    @XmlElement(name="user_standard") 
	public void setUserStandard(String user_standard) {
		this.user_standard = user_standard;
	}
    @XmlElement(name="password_standard") 
	public void setPasswordStandard(String password_standard) {
		this.password_standard = password_standard;
	}
    @XmlElement(name="user_jorge") 
	public void setUserJorge(String user_jorge) {
		this.user_jorge = user_jorge;
	}
    @XmlElement(name="password_jorge") 
	public void setPasswordJorge(String password_jorge) {
		this.password_jorge = password_jorge;
	}
    @XmlElement(name="user_robot") 
	public void setUserRobot(String user_robot) {
		this.user_robot = user_robot;
	}
    @XmlElement(name="password_robot") 
	public void setPasswordRobot(String password_robot) {
		this.password_robot = password_robot;
	}
    @XmlElement(name="user_francia") 
	public void setUserFrancia(String user_francia) {
		this.user_francia = user_francia;
	}
    @XmlElement(name="password_francia") 
	public void setPasswordFrancia(String password_francia) {
		this.password_francia = password_francia;
	}

	
	public String getNif() {
		return nif;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
