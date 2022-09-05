package com.mng.robotest.test.appshop.nodos;

import java.io.Serializable;
import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test.factoryes.NodoStatus;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class TestNodos implements Serializable {

	private static final long serialVersionUID = 1986211132936039272L;
	
	private String index_fact;
	
	private Map<String, NodoStatus> listaNodos;
	public NodoStatus nodo;
	public int prioridad;
	public String autAddr;
	public String urlStatus;
	public String urlErrorpage;
	public boolean testLinksPie;
	
	/**
	 * @param listaNodos lista de todos los nodos que está previsto testear
	 * @param nodo nodo concreto que se testeará en el test
	 * @param autAddr URL de acceso a la shop/outlet
	 * @param urlStatus URL correspondiente al servicio que devuelve datos a nivel del 'status' del nodo
	 * @param urlErrorpage URL correspondiente al errorPage
	 * @param testLinksPie si se prueban o no los links a pié de página
	 */
	public TestNodos(Map<String, NodoStatus> listaNodos, NodoStatus nodo, int prioridad, String autAddr, String urlStatus, String urlErrorpage, boolean testLinksPie) {
		this.index_fact = "Nodo-" + nodo.getIp();
		this.listaNodos = listaNodos;
		this.nodo = nodo;
		this.prioridad = prioridad;
		this.autAddr = autAddr;
		this.urlStatus = urlStatus;
		this.urlErrorpage = urlErrorpage;
		this.testLinksPie = testLinksPie;
	}	  
	
	@Test (
		groups={"Canal:desktop_App:all"},
		description="Verificar funcionamiento general en un nodo. Validar status, acceso, click banner, navegación por las líneas...")
	public void NOD001_TestNodo() throws Throwable {
		TestCaseTM.addNameSufix(this.index_fact);
		new Nod001(listaNodos, nodo, autAddr).execute();
	}


}