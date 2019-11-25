package com.mng.testmaker.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.domain.RepositoryI;
import com.mng.testmaker.domain.SuiteTM;
import com.mng.testmaker.domain.SuitesExecuted;
import com.mng.testmaker.domain.data.SuiteData;

public class FilterSuites {

	public enum SetSuiteRun {running, finished, all}
	
	private final String suite;
	private final Channel channel; 
	private final String application; 
	private final SetSuiteRun state; 
	private final Date desde;
	
	private RepositoryI repository = TestMaker.getRepository();
	
	private FilterSuites (String suite, Channel channel, String application, SetSuiteRun state, Date fechaDesde) {
		this.suite = suite;
		this.application = application;
		this.channel = channel;
		this.state = state;
		this.desde = fechaDesde;
	}
	
	public static FilterSuites getNew() {
		return (getNew(null, null, null, null, null));
	}
	
	public static FilterSuites getNew(String suite, Channel channel, String application, SetSuiteRun state, Date fechaDesde) {
		return new FilterSuites(suite, channel, application, state, fechaDesde);
	}
	
	
	public List<SuiteData> getListSuites() throws Exception {
		List<SuiteData> listSuitesInMemory;
		List<SuiteData> listSuitesStored;
		if (desde==null) {
			listSuitesInMemory = filter(getListSuitesInMemory());
			listSuitesStored = filter(repository.getListSuites());
		} else {
			listSuitesInMemory = filter(getListSuitesInMemoryAfter(desde));
			listSuitesStored = filter(repository.getListSuitesAfter(desde));
		}

		List<SuiteData> listToReturn = new ArrayList<>();
		listToReturn.addAll(listSuitesInMemory);
		for (SuiteData suiteStored : listSuitesStored) {
			if (!listSuitesInMemory.contains(suiteStored)) {
				listToReturn.add(suiteStored);
			}
		}
		return listToReturn;
	}
	
	private List<SuiteData> filter(List<SuiteData> listToFilter) {
		List<SuiteData> listFiltered = new ArrayList<>();
		for (SuiteData suiteData : listToFilter) {
			if (filterMatches(suiteData)) {
				listFiltered.add(suiteData);
			}
		}
		return listFiltered;
	}
	
	private boolean filterMatches(SuiteData suiteData) {
		if (suite!=null) {
			if (suite.compareTo(suiteData.getName())!=0) {
				return false;
			}
		}
		if (channel!=null) {
			if (channel!=suiteData.getChannel()) {
				return false;
			}
		}
		if (application!=null) {
			if (application.compareTo(suiteData.getApp())!=0) {
				return false;
			}
		}
		if (state!=null && state!=SetSuiteRun.all) {
			if (state==SetSuiteRun.running && suiteData.getStateExecution().isFinished()) {
				return false;
			}
			if (state==SetSuiteRun.finished && !suiteData.getStateExecution().isFinished()) {
				return false;
			}
		}
		return true;
	}
	
	void setRepository(RepositoryI repository) {
		this.repository = repository;
	}
	
	List<SuiteData> getListSuitesInMemory() {
		List<SuiteData> listSuitesToReturn = new ArrayList<>();
		for (SuiteTM suite : SuitesExecuted.getSuitesExecuted()) {
			listSuitesToReturn.add(SuiteData.from(suite));
		}
		return listSuitesToReturn;
	}
	
	List<SuiteData> getListSuitesInMemoryAfter(Date fechaDesde) {
		List<SuiteData> listSuitesReturn = new ArrayList<>();
		List<SuiteData> listSuites = getListSuitesInMemory();
		for (SuiteData suiteData : listSuites) {
			if (suiteData.getInicioDate().after(fechaDesde)) {
				listSuitesReturn.add(suiteData);
			}
		}
		return listSuitesReturn;
	}
	
}
