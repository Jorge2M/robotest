package com.mng.robotest.test.getdata.products.data;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProductList {
	
	private String cacheId;
	private String titleh1;
	private List<Group> groups;
	
	public ProductList() {}
	
	public String getCacheId() {
		return cacheId;
	}
	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}
	public String getTitleh1() {
		return titleh1;
	}
	public void setTitleh1(String titleh1) {
		this.titleh1 = titleh1;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	public void addGroups(List<Group> groups) {
		if (groups!=null) {
			this.groups = groups;
		}
		this.groups.addAll(groups);
	}

	public String getStockId() {
		Pattern pattern = Pattern.compile("([0-9]{3}.\\w{2}.[0-9]{1}.(true|false).(true|false).v[0-9])");
		Matcher matcher = pattern.matcher(getCacheId());
		if (matcher.find()) {
			String stockId = matcher.group(0);
			String codPais = stockId.substring(4,6);
			return (
				stockId.subSequence(0, 4) + 
				codPais.toUpperCase() + 
				stockId.substring(6));
		}
		return null;
	}
	
	public List<GarmentCatalog> getAllGarments() {
		return groups.stream()
				.map(s -> s.getGarments())
				.collect(Collectors.toList()).stream()
				.flatMap(Collection::stream)
				.toList();
	}
}
