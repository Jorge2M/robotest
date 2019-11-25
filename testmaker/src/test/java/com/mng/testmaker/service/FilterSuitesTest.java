package com.mng.testmaker.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import static com.mng.testmaker.conf.Channel.*;
import static com.mng.testmaker.domain.StateExecution.*;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.RepositoryI;
import com.mng.testmaker.domain.StateExecution;
import com.mng.testmaker.domain.data.SuiteData;
import com.mng.testmaker.service.FilterSuites.SetSuiteRun;


public class FilterSuitesTest {

	LocalDate fechaHoy = LocalDate.now();
	SuiteData suite1 = getNew("Suite1", Finished_Normally, "SmokeTest", desktop, "shop", fechaHoy);
	SuiteData suite2 = getNew("Suite2", Started, "SmokeTest", movil_web, "shop", fechaHoy);
	SuiteData suite3 = getNew("Suite3", Stopping, "MenusPais", desktop, "outlet", fechaHoy.minusDays(3));
	SuiteData suite4 = getNew("Suite4", Stopped, "MenusPais", movil_web, "shop", fechaHoy.minusDays(1));
	SuiteData suite5 = getNew("Suite5", Finished_Normally, "SmokeTest", desktop, "outlet", fechaHoy.minusDays(5));
	
	List<SuiteData> listSuitesInMemory = Arrays.asList(suite1, suite2, suite3);
	List<SuiteData> listSuitesInRepository = Arrays.asList(suite3, suite4, suite5);
	List<SuiteData> listSuitesInRepositoryDesde = Arrays.asList(suite4);
	
	
	@Test
	public void testGetAllSuites() throws Exception {
		//Given
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew());
		
		//When
		List<SuiteData> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite1));
		assertTrue(listSuites.contains(suite2));
		assertTrue(listSuites.contains(suite3));
		assertTrue(listSuites.contains(suite4));
		assertTrue(listSuites.contains(suite5));
		assertTrue(listSuites.size()==5);
	}
	
	@Test
	public void testGetRunningSuites() throws Exception {
		//Given
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew(null, null, null, SetSuiteRun.running, null));
		
		//When
		List<SuiteData> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite2));
		assertTrue(listSuites.contains(suite3));
		assertTrue(listSuites.size()==2);
	}
	
	@Test
	public void testGetSuitesMemoryAndRepository() throws Exception {
		//Given
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew("SmokeTest", null, null, null, null));
		
		//When
		List<SuiteData> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite1));
		assertTrue(listSuites.contains(suite2));
		assertTrue(listSuites.contains(suite5));
		assertTrue(listSuites.size()==3);
	}
	
	@Test
	public void testGetSuitesDesde() throws Exception {
		//Given
		LocalDate fechaDesde = fechaHoy.minusDays(2);
		FilterSuites filterSuitesSpy = createFilterSuitesSpy(FilterSuites.getNew(null, null, null, null, getDate(fechaDesde)));
		
		//When
		List<SuiteData> listSuites = filterSuitesSpy.getListSuites();
		
		//Then
		assertTrue(listSuites.contains(suite1));
		assertTrue(listSuites.contains(suite2));
		assertTrue(listSuites.contains(suite4));
		assertTrue(listSuites.size()==3);
	}
	
	private FilterSuites createFilterSuitesSpy(FilterSuites filterSuites) throws Exception {
		FilterSuites filterSuitesSpy = Mockito.spy(filterSuites);
		when(filterSuitesSpy.getListSuitesInMemory()).thenReturn(listSuitesInMemory);
		
		RepositoryI repository = Mockito.mock(RepositoryI.class);
		filterSuitesSpy.setRepository(repository);
		when(repository.getListSuites()).thenReturn(listSuitesInRepository);
		when(repository.getListSuitesAfter(Mockito.any(Date.class))).thenReturn(listSuitesInRepositoryDesde);
		return filterSuitesSpy;
	}

	private SuiteData getNew(String idExecSuite, 
							 StateExecution stateExecution, 
							 String nameSuite, 
							 Channel channel, 
							 String app, 
							 LocalDate iniFecha) {
		SuiteData suiteData = new SuiteData();
		suiteData.setIdExecSuite(idExecSuite);
		suiteData.setStateExecution(stateExecution);
		suiteData.setName(nameSuite);
		suiteData.setChannel(channel);
		suiteData.setApp(app);
		suiteData.setInicioDate(getDate(iniFecha));
		return suiteData;
	}
	
	private Date getDate(LocalDate localDate) {
		return (Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
	}

}
