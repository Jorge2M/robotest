package com.mng.robotest.domains.apiproduct.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mng.robotest.domains.apiproduct.domain.entity.Color;
import com.mng.robotest.domains.apiproduct.domain.entity.Composition;
import com.mng.robotest.domains.apiproduct.domain.entity.FamilyOnline;
import com.mng.robotest.domains.apiproduct.domain.entity.Product;
import com.mng.robotest.domains.apiproduct.domain.entity.ProductId;
import com.mng.robotest.domains.apiproduct.domain.entity.Size;
import com.mng.robotest.domains.apiproduct.domain.entity.WashingRule;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductRedis implements Serializable {
	
	private static final long serialVersionUID = -1677013488829003602L;
	
	String id; 
	String seller; 
	String tariffHeading; 
	String style;
    String generic; 
    String collection;
    String sizeGroup;
    @JsonProperty("type") String productType; 
    String dangerousMatterCode;
    GenderTypeRedis genderType; 
    GenderRedis gender; 
    DescriptionsRedis descriptions;
    FamiliesRedis families; 
    List<String> composition; 
    List<String> washingRules;
    List<ColorRedis> colors; 
    List<RelatedModelRedis> relatedModels;
    List<LabelRedis> labels;
    @JsonIgnore String countryId; 
    @JsonIgnore String languageId;

    public ProductRedis() { }
    
    public ProductRedis(
    		String id,
	    	String seller, 
	    	String tariffHeading, 
	    	String style,
	        String generic, 
	        String collection,
	        String productType, 
	        String dangerousMatterCode,
	        GenderTypeRedis genderType, 
	        GenderRedis gender,
	        DescriptionsRedis descriptions,
	        FamiliesRedis families,
	        List<String> composition, 
	        List<String> washingRules,
	        List<ColorRedis> colors,
	        List<RelatedModelRedis> relatedModels,
	        String countryId,
	        String languageId) {
    	this.id = id; 
    	this.seller = seller;
    	this.tariffHeading = tariffHeading;
    	this.style = style;
        this.generic = generic;
        this.collection = collection;
        this.productType = productType;
        this.dangerousMatterCode = dangerousMatterCode;
        this.genderType = genderType;
        this.gender = gender;
        this.descriptions = descriptions;
        this.families = families;
        this.composition = composition;
        this.washingRules = washingRules;
        this.colors = colors;
        this.relatedModels = relatedModels;
        this.countryId = countryId;
        this.languageId = languageId;
    }
    
    public static ProductRedis toProductRedis(Product product) {
        return new ProductRedis(
        		product.getId().getId(),
                product.getSeller(),
                product.getTariffHeading(),
                product.getStyle(),
                product.getGeneric(),
                product.getCollection(),
                product.getProductType(),
                product.getDangerousMatterCode(),
                toGenderTypeRedis(product),
                toGenderRedis(product),
                toDescriptionsRedis(product),
                toFamiliesRedis(product),
                toCompositionRedis(product),
                toWashingRulesRedis(product),
                toColorsRedis(product),
                toRelatedModelsRedis(product),
                product.getCountryId(),
                product.getLanguageId());
    }

    private static GenderTypeRedis toGenderTypeRedis(Product product) {
        return new GenderTypeRedis(null, product.getBrand());
    }

    private static GenderRedis toGenderRedis(Product product) {
        return product.getGender() != null ?
                new GenderRedis(product.getGender().getId(), product.getGender().getName())
                : null;
    }

    private static DescriptionsRedis toDescriptionsRedis(Product product) {
        return product.getDescriptions() != null ?
                new DescriptionsRedis(product.getDescriptions().getShortDescription(), product.getDescriptions().getLongDescription())
                : null;
    }

    private static FamiliesRedis toFamiliesRedis(Product product) {
        return product.getFamilies() != null ?
                new FamiliesRedis(String.valueOf(product.getFamilies().getFamilyErpId()),
                        toFamiliesOnlineRedis(product.getFamilies().getFamiliesOnline()), product.getFamilies().getSubfamiliesOnline())
                : null;
    }

    private static List<FamilyOnlineRedis> toFamiliesOnlineRedis(List<FamilyOnline> familiesOnline) {
        return familiesOnline != null ?
                familiesOnline.stream()
                        .map(family -> new FamilyOnlineRedis(String.valueOf(family.getId()), family.getName()))
                        .collect(Collectors.toList())
                : null;
    }

    private static List<String> toCompositionRedis(Product product) {
        return product.getComposition() != null ?
                product.getComposition().stream()
                        .map(Composition::getDescription)
                        .collect(Collectors.toList())
                : null;
    }

    private static List<String> toWashingRulesRedis(Product product) {
        return product.getWashingRules() != null ?
                product.getWashingRules().stream()
                        .map(WashingRule::getDescription)
                        .collect(Collectors.toList())
                : null;
    }

    private static List<ColorRedis> toColorsRedis(Product product) {
        return product.getColors().stream()
                .map(color -> new ColorRedis(color.getId(), color.getDescription(), toSizesRedis(color), toImagesRedis(color), toVideosRedis(color), null))
                .collect(Collectors.toList());
    }

    private static List<SizeRedis> toSizesRedis(Color color) {
        return color.getSizes().stream()
                .map(size -> new SizeRedis(String.valueOf(size.getId()), toSizeDescriptionsRedis(size), size.getEan(), null))
                .collect(Collectors.toList());
    }

    private static SizeDescriptionsRedis toSizeDescriptionsRedis(Size size) {
        return new SizeDescriptionsRedis(size.getDescriptions().getBaseDescription(), size.getDescriptions().getFurtherDescription());
    }

    private static List<ImageRedis> toImagesRedis(Color color) {
        return color.getImages() != null ?
                color.getImages().stream()
                        .map(image -> new ImageRedis(image.getUrl(), image.getType(), "default"))
                        .collect(Collectors.toList())
                : null;
    }

    private static List<VideoRedis> toVideosRedis(Color color) {
        return color.getVideos() != null ?
                color.getVideos().stream()
                        .map(video -> new VideoRedis(video.getUrl(), video.getSize()))
                        .collect(Collectors.toList())
                : null;
    }

    private static List<RelatedModelRedis> toRelatedModelsRedis(Product product) {
        return product.getRelatedModels() != null ?
                product.getRelatedModels().stream()
                        .map(relatedModel -> new RelatedModelRedis(relatedModel.getType(), toRelatedProductList(relatedModel.getProductIds())))
                        .collect(Collectors.toList())
                : null;
    }

    private static List<String> toRelatedProductList(List<ProductId> productIds) {
        return productIds != null ?
                productIds.stream()
                        .map(ProductId::getId)
                        .collect(Collectors.toList())
                : null;
    }

    @JsonIgnore
    public String getCacheKey() {
        return String.format("%s-%s-%s", id, countryId, languageId);
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

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getDangerousMatterCode() {
		return dangerousMatterCode;
	}

	public void setDangerousMatterCode(String dangerousMatterCode) {
		this.dangerousMatterCode = dangerousMatterCode;
	}

	public GenderTypeRedis getGenderType() {
		return genderType;
	}

	public void setGenderType(GenderTypeRedis genderType) {
		this.genderType = genderType;
	}

	public GenderRedis getGender() {
		return gender;
	}

	public void setGender(GenderRedis gender) {
		this.gender = gender;
	}

	public DescriptionsRedis getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(DescriptionsRedis descriptions) {
		this.descriptions = descriptions;
	}

	public FamiliesRedis getFamilies() {
		return families;
	}

	public void setFamilies(FamiliesRedis families) {
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

	public List<ColorRedis> getColors() {
		return colors;
	}
	
	public List<ColorRedis> getColorsWithStock() {
		List<ColorRedis> listColorsReturn = new ArrayList<>();
		for (ColorRedis color : getColors()) {
			for (SizeRedis size : color.getSizes()) {
				if (size.getStockDetails()!=null) {
					listColorsReturn.add(color);
					break;
				}
			}
		}
		return listColorsReturn;
	}

	public void setColors(List<ColorRedis> colors) {
		this.colors = colors;
	}

	public List<RelatedModelRedis> getRelatedModels() {
		return relatedModels;
	}
	
	public List<RelatedModelRedis> getRelatedModels(String type) {
		if (getRelatedModels()==null) {
			return null;
		}
		return getRelatedModels().stream()
				.filter(s -> s.getType().compareTo(type)==0)
				.collect(Collectors.toList()); 
	}

	public void setRelatedModels(List<RelatedModelRedis> relatedModels) {
		this.relatedModels = relatedModels;
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

	public List<LabelRedis> getLabels() {
		return labels;
	}
	
	public Optional<LabelRedis> getLabel(String id) {
		if (getLabels()!=null) {
			return getLabels().stream()
					.filter(s -> s.getId().compareTo(id)==0)
					.findFirst();
		}
		return Optional.empty();
	}

	public void setLabels(List<LabelRedis> labels) {
		this.labels = labels;
	}

	public String getSizeGroup() {
		return sizeGroup;
	}

	public void setSizeGroup(String sizeGroup) {
		this.sizeGroup = sizeGroup;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
