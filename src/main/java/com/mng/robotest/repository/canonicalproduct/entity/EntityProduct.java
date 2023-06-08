package com.mng.robotest.repository.canonicalproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;


@JsonInclude(NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntityProduct implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("_id")private String idMongo;
	private String id;
	private String seller; 
	private String tariffHeading; 
	private String style;
	private String generic; 
	private String collection;
	private String type;
	private String dangerousMatterCode; 
	private String sizeGroup;
	private EntityGenderType genderType; 
	private EntityGender gender;
	private EntityDescriptions descriptions;
	private EntityFamilies families;
	private List<String> composition; 
	private List<String> washingRules;
	private List<EntityColor> colors;
	private List<EntityRelatedModel> relatedModels;
	private List<EntityLabel> labels;
	private String countryId;
	private String languageId;
	private String channelId;
	
	public String get_id() {
		return idMongo;
	}
	public void set_id(String _id) {
		this.idMongo = _id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSeller() {
		return seller;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public String getTariffHeading() {
		return tariffHeading;
	}
	public void setTariffHeading(String tariffHeading) {
		this.tariffHeading = tariffHeading;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getGeneric() {
		return generic;
	}
	public void setGeneric(String generic) {
		this.generic = generic;
	}
	public String getCollection() {
		return collection;
	}
	public void setCollection(String collection) {
		this.collection = collection;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDangerousMatterCode() {
		return dangerousMatterCode;
	}
	public void setDangerousMatterCode(String dangerousMatterCode) {
		this.dangerousMatterCode = dangerousMatterCode;
	}
	public String getSizeGroup() {
		return sizeGroup;
	}
	public void setSizeGroup(String sizeGroup) {
		this.sizeGroup = sizeGroup;
	}
	public EntityGenderType getGenderType() {
		return genderType;
	}
	public void setGenderType(EntityGenderType genderType) {
		this.genderType = genderType;
	}
	public EntityGender getGender() {
		return gender;
	}
	public void setGender(EntityGender gender) {
		this.gender = gender;
	}
	public EntityDescriptions getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(EntityDescriptions descriptions) {
		this.descriptions = descriptions;
	}
	public EntityFamilies getFamilies() {
		return families;
	}
	public void setFamilies(EntityFamilies families) {
		this.families = families;
	}
	public List<String> getComposition() {
		return composition;
	}
	public void setComposition(List<String> composition) {
		this.composition = composition;
	}
	public List<String> getWashingRules() {
		return washingRules;
	}
	public void setWashingRules(List<String> washingRules) {
		this.washingRules = washingRules;
	}
	public List<EntityColor> getColors() {
		return colors;
	}
	public void setColors(List<EntityColor> colors) {
		this.colors = colors;
	}
	public List<EntityRelatedModel> getRelatedModels() {
		return relatedModels;
	}
	public void setRelatedModels(List<EntityRelatedModel> relatedModels) {
		this.relatedModels = relatedModels;
	}
	public List<EntityLabel> getLabels() {
		return labels;
	}
	public void setLabels(List<EntityLabel> labels) {
		this.labels = labels;
	}
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getLanguageId() {
		return languageId;
	}
	public void setLanguageId(String languageId) {
		this.languageId = languageId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
