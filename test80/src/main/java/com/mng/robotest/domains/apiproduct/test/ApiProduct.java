//PROBLEMAS DETECTADOS PENDINTES
	// - No se cargan correctamente los precios de los artículos rebajados (Sergio) -> Parece que ya funciona -> Avisar a Sergio
	// - Problema productos relacionados:
		// - No están apareciendo los colores 'relacionados' (David) -> CheckColorImages
		// - Los productos de toallas sólo llegan con 1 talla (David)
	// - En algunos colores no aparece el bloque price -> CheckColorPricesCurrency -> ???


//NO HA SIDO POSIBLE CHEQUEAR
	//seller
	//tariffHeading
	//style
	//families.erpId
	//size.ean
	//colors.videos	
	//color.price.starPrice
	//color.price.opi
	//color.price.type
	//color.price.promotionName

	//size.descriptions.further -> Esto en principio no lo vamos a sacar

//OTRAS
	//Aclarar contenido campo GenderType
	//Código idioma ISO -> https://country-configuration-service.pre.k8s.mango/v1/country/?hasOnlineSale=true


package com.mng.robotest.domains.apiproduct.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.testng.annotations.InitObject;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.apiproduct.entity.ColorRedis;
import com.mng.robotest.domains.apiproduct.entity.LabelRedis;
import com.mng.robotest.domains.apiproduct.entity.ProductRedis;
import com.mng.robotest.domains.apiproduct.entity.RelatedModelRedis;
import com.mng.robotest.domains.apiproduct.entity.SizeRedis;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.getdata.products.data.Color;
import com.mng.robotest.test.getdata.products.data.Size;
import com.mng.robotest.test.getdata.products.data.Image;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.getdata.garment.GetterGarment;
import com.mng.robotest.test.getdata.garment.data.ColorGarment;
import com.mng.robotest.test.getdata.garment.data.GarmentFicha;
import com.mng.robotest.test.getdata.garment.data.ImageGarment;
import com.mng.robotest.test.getdata.garment.data.LabelGarment;
import com.mng.robotest.test.getdata.garment.data.RelatedModel;
import com.mng.robotest.test.getdata.garment.data.SizeGarment;
import com.mng.robotest.test.getdata.garment.data.WashingRule;
import com.mng.robotest.test.getdata.products.GetterProductApiCanonical;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.MenuI;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.utils.PaisGetter;


public class ApiProduct {
	
	private final Pais pais;
	private final LineaType linea;
	private final String idioma;
	private final MenuI menu;
	
	public ApiProduct(String pais, String idioma, String linea, MenuI menu) {
		this.pais = PaisGetter.get(pais);
		this.linea = LineaType.valueOf(linea);
		this.idioma = idioma;
		this.menu = menu;
	}
	
	@Test (
		groups={"Api", "Canal:desktop,mobile_App:all"}, alwaysRun=true,
		description="Prueba Canonical API Product",
		create=InitObject.None)
	public void API001() throws Exception {
		TestCaseTM.addNameSufix(
				pais.getCodigo_pais() + "_" + 
				linea + "_" + 
				idioma + "_" + 
				menu.getSeccion() + "_" + 
				menu.getGaleria() + "_" +
				menu.getFamilia(AppEcom.shop, false));
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		AppEcom app = (AppEcom)inputParamsSuite.getApp();
		
		GetterProducts getterProducts = new GetterProducts.Builder(pais.getCodigo_alf(), app, null)
				.idiom(idioma)
				.linea(linea)
				.menusCandidates(Arrays.asList(menu))
				.numProducts(60)
				.build();
		
		List<GarmentCatalog> catalogProducts = getterProducts.getAll();
		addProductNumberToTestDescription(catalogProducts.size());
		if (catalogProducts.size()>0) {
			String stockId = getterProducts.getProductList().getStockId();
			getDataProduct(catalogProducts, stockId, app);
		}
	}

	@Step (
		description="Chequear los datos de Catálogo (<strong>#{catalogProducts.size()}</strong> productos) y la ficha con los de la API Canónica de Producto", 
		expected="Los datos de la API canónica son correctos")
	private void getDataProduct(List<GarmentCatalog> catalogProducts, String stockId, AppEcom app) 
	throws Exception {
		
		checkData(catalogProducts, stockId);
	}

	@Validation
	private ChecksTM checkData(List<GarmentCatalog> catalogProducts, String stockId) throws Exception {
		ChecksTM validations = ChecksTM.getNew();
		String koChecks = getKoChecksHTML(catalogProducts, stockId);
		validations.add(
			"El chequeo de los datos es correcto<br>" + koChecks,
			"".compareTo(koChecks)==0, State.Warn);
		
		return validations;
	}
	
	private String getKoChecksHTML(List<GarmentCatalog> catalogProducts, String stockId) throws Exception {
		List<Check> checks = check(catalogProducts, stockId);
		return getKoChecksHTML(checks);
	}
	
	private String getKoChecksHTML(List<Check> checks) {
		String tableError = "<div>";
		for (Check check : checks) {
			if (!check.getCheck().getLeft()) {
				tableError+=
						"<div style=\"border:1px solid black;\">" + 
						(String.format("ProductID: <strong>%s</strong>. %s", check.getIdProduct(), check.getCheck().getRight())) + 
						"</div>";
			}
		}
		tableError+="</div>";
		return tableError;
	}
	
	private List<Check> check(List<GarmentCatalog> catalogProducts, String stockId) throws Exception {
		List<Check> checks = new ArrayList<>();
		for (GarmentCatalog garmentCatalog : catalogProducts) {
			Optional<ProductRedis> productApiOpt = getProductFromApi(garmentCatalog);
			GarmentFicha garmentFicha = getProductFromGarment(stockId, garmentCatalog.getGarmentId());
			if (garmentFicha==null) {
				Pair<Boolean, String> pair = Pair.of(false, String.format("Check Product Exists in Ficha")); 
				checks.add(Check.from(garmentCatalog.getGarmentId(), pair));
			} else {
				if (productApiOpt.isPresent()) {
					ProductRedis productApi = productApiOpt.get();
					String productId = productApi.getId();
					
					checks.add(Check.from(productId, checkType(productApi, garmentCatalog)));
					checks.add(Check.from(productId, checkGeneric(productApi, garmentCatalog)));
					checks.add(Check.from(productId, checkCollection(productApi, garmentCatalog)));
					checks.add(Check.from(productId, checkGenderType(productApi)));
					checks.add(Check.from(productId, checkGender(productApi, garmentCatalog)));
					checks.add(Check.from(productId, checkShortDescription(productApi, garmentCatalog)));
					
					String urlBase = ((InputParamsMango)TestMaker.getInputParamsSuite()).getUrlBase();
					if (urlBase.contains("shop.pre.mango.com") || urlBase.contains("shop.mango.com")) {
						checks.add(Check.from(productId, checkLongDescription(productApi, garmentFicha)));
					}
					
					checks.add(Check.from(productId, checkComposition(productApi, garmentFicha)));
					checks.add(Check.from(productId, checkWashingRules(productApi, garmentFicha)));
					checks.add(Check.from(productId, checkFamilyOnlineId(productApi, garmentFicha)));
					checks.add(Check.from(productId, checkFamilyLabel(productApi, garmentFicha)));
					if (productApi.getRelatedModels("COLOR")==null) {
						checks.add(Check.from(productId, checkNumberColorsWithStock(productApi, garmentCatalog)));
						checks.add(Check.from(productId, checkNumberColors(productApi, garmentFicha)));
					}
					
					for (int i=0; i<productApi.getColors().size(); i++) {
						ColorRedis color1 = productApi.getColors().get(i);
						Optional<ColorGarment> color2opt = garmentFicha.getColor(color1.getId());
						if (!color2opt.isPresent()) {
							Pair<Boolean, String> pair = Pair.of(false, String.format("Check Color Exists in Ficha")); 
							checks.add(Check.from(productId + " / " + color1.getId(), pair));
						} else {
							ColorGarment color2 = color2opt.get();
							checks.add(Check.from(productId, checkColorId(color1, color2)));
							checks.add(Check.from(productId, checkColorDescription(color1, color2)));
							if (productApi.getRelatedModels("SIZE")==null) {
								checks.add(Check.from(productId, checkNumberSizes(color1, color2)));
							}
							checks.add(Check.from(productId, checkColorImages(color1, color2)));
							
							if (garmentFicha.isStock()) {
								checks.add(Check.from(productId, checkColorPricesCurrency(color1, color2)));
								checks.add(Check.from(productId, checkColorPricesPrice(color1, color2)));
								checks.add(Check.from(productId, checkColorPricesBase(color1, color2)));
							}
							
							for (int ii=0; ii<color1.getSizes().size(); ii++) {
								SizeRedis size1 = color1.getSizes().get(ii);
								Optional<SizeGarment> sizeOpt = color2.getSize(Integer.valueOf(size1.getId()));
								if (sizeOpt.isPresent()) {
									SizeGarment size2 = sizeOpt.get();
									checks.add(Check.from(productId + " / " + color1.getId(), checkSizeId(size1, size2)));
									checks.add(Check.from(productId + " / " + color1.getId(), checkSizeDescriptionBase(size1, size2)));
									checks.add(Check.from(productId + " / " + color1.getId(), checkSizeWarehouse(size1, size2)));
								} else {
									Pair<Boolean, String> pair = Pair.of(false, String.format("CheckSizeExists. SizeApi: %s",	size1.getId())); 
									checks.add(Check.from(productId + " / " + color1.getId(), pair));
								}
							}
						}
					}
					
					if (urlBase.contains("shop.pre.mango.com") || urlBase.contains("shop.mango.com")) {
						for (int i=0; i<productApi.getColorsWithStock().size(); i++) {
							ColorRedis color1 = productApi.getColorsWithStock().get(i);
							Color color2 = garmentCatalog.getColor(color1.getId());
							checks.add(Check.from(productId, checkColorImages(color1, color2)));
							for (int ii=0; ii<color1.getSizes().size(); ii++) {
								SizeRedis size1 = color1.getSizes().get(ii);
								Size size2 = color2.getSizes().get(ii);
								checks.add(Check.from(productId + " / " + color1.getId(), checkSizeStock(size1, size2)));
							}
						}
					}
					
					checks.add(Check.from(productId, checkNumberRelatedModels(productApi, garmentFicha)));
					if (productApi.getRelatedModels()!=null) {
						for (int i=0; i<productApi.getRelatedModels().size(); i++) {
							RelatedModelRedis related1 = productApi.getRelatedModels().get(i);
							RelatedModel related2 = garmentFicha.getRelatedModels().get(i);
							checks.add(Check.from(productId + " / " + related1.getId(), checkRelatedModelsId(related1, related2)));
							checks.add(Check.from(productId + " / " + related1.getId(), checkRelatedModelsType(related1, related2)));
							checks.add(Check.from(productId + " / " + related1.getId(), checkRelatedModelsProducts(productId, related1, related2)));
						}
					}
					
					if (urlBase.contains("shop.pre.mango.com") || urlBase.contains("shop.mango.com")) {
						checks.add(Check.from(productId, checkNumberLabels(productApi, garmentFicha)));
						if (garmentFicha.getGarmentLabels()!=null) {
							for (int i=0; i<garmentFicha.getGarmentLabels().size(); i++) {
								LabelGarment labelGarment = garmentFicha.getGarmentLabels().get(i);
								Optional<LabelRedis> labelRedis = productApi.getLabel(labelGarment.getKey());
								if (labelRedis.isPresent()) { 
									checks.add(Check.from(productId, checkLabelId(labelRedis.get(), labelGarment)));
									checks.add(Check.from(productId, checkLabelTranslateId(labelRedis.get(), labelGarment)));
								} else {
									checks.add(Check.from(garmentCatalog.getGarmentId(), Pair.of(
											false, 
											String.format("Label: %s no encontrado en API Canonica", labelGarment.getKey()))));								
								}
							}
						}
					}
				} else {
					if (garmentCatalog.getStock()!=0) {
						checks.add(Check.from(garmentCatalog.getGarmentId(), Pair.of(
								false, 
								String.format("Producto con stock: %s no encontrado en la API canónica", garmentCatalog.getGarmentId()))));
					}
				}
			}
		}
		
		return checks;
	}
	
	private Pair<Boolean, String> checkGenderType(ProductRedis product) {
		//TODO pendiente aclarar qué debería llegar en estos campos
		if (true) {
			return Pair.of(true,"CheckGenderType");
		}
		return Pair.of(
				product.getGenderType().getName().toLowerCase().compareTo(linea.name())==0,
				"CheckGenderType");
	}
	private Pair<Boolean, String> checkType(ProductRedis product, GarmentCatalog garment) {
		return Pair.of(
				product.getProductType().compareTo(garment.getAnalyticsEventsData().getGarmentType())==0,
				String.format(
					"CheckType.<br><strong>TypeApi</strong>: %s,<br><strong>TypeCatalog</strong>: %s", 
					product.getProductType(), garment.getAnalyticsEventsData().getGarmentType()));
	}
	private Pair<Boolean, String> checkGeneric(ProductRedis product, GarmentCatalog garment) {
		return Pair.of(
				garment.getAnalyticsEventsData().getName().contains(product.getGeneric().toLowerCase()),
				String.format(
					"CheckGeneric.<br><strong>GenericApi</strong>: %s,<br><strong>GenericCatalog</strong>: %s", 
					garment.getAnalyticsEventsData().getName(), product.getGeneric().toLowerCase()));
	}
	private Pair<Boolean, String> checkCollection(ProductRedis product, GarmentCatalog garment) {
		return Pair.of(
				product.getCollection().compareTo(garment.getAnalyticsEventsData().getCollection())==0,
				String.format(
					"CheckCollection.<br><strong>CollectionApi</strong>: %s,<br><strong>CollectionCatalog</strong>: %s", 
					product.getCollection(), garment.getAnalyticsEventsData().getCollection()));
	}
	private Pair<Boolean, String> checkGender(ProductRedis product, GarmentCatalog garment) {
		return Pair.of(
				product.getGender().getId().compareTo(garment.getGenre())==0,
				String.format(
					"CheckGender.<br><strong>GenderIdApi</strong>: %s,<br><strong>GenderIdCatalog</strong>: %s", 
					product.getGender().getId(), garment.getGenre()));
	}
	private Pair<Boolean, String> checkShortDescription(ProductRedis product, GarmentCatalog garment) {
		return Pair.of(
				product.getDescriptions().getShortDescription().compareTo(garment.getShortDescription())==0,
				String.format(
					"CheckShortDescription.<br><strong>ShortDescriptionApi</strong>: %s,<br><strong>ShortDescriptionCatalog</strong>: %s",
					product.getDescriptions().getShortDescription(), garment.getShortDescription()));
	}
	private Pair<Boolean, String> checkLongDescription(ProductRedis product, GarmentFicha garmentFicha) {
		String descriptionsApi = product.getDescriptions().getLongDescription().toString().replace("[", "").replace("]", "");
		List<String> listBulletsGarment = garmentFicha.getDetails().getDescriptions().getBullets();
		String bulletsFicha = "";
		if (listBulletsGarment!=null) {
			bulletsFicha = listBulletsGarment.toString().replace("[", "").replace("]", "");
		}
		String descriptionsFicha = bulletsFicha;
		List<String> listCapsules = garmentFicha.getDetails().getDescriptions().getCapsules(); 
		if (listCapsules!=null) {
			descriptionsFicha = descriptionsFicha + ", " + listCapsules.toString().replace("[", "").replace("]", "");
		}
		return Pair.of(
				descriptionsApi.toLowerCase().compareTo(descriptionsFicha.toLowerCase())==0,
				String.format(
					"CheckLongDescription.<br><strong>LongDescriptionApi</strong>: %s,<br><strong>LongDescriptionFicha</strong>: %s",
					descriptionsApi.toLowerCase(), descriptionsFicha.toLowerCase()));
	}
	
	private Pair<Boolean, String> checkComposition(ProductRedis product, GarmentFicha garmentFicha) {
		String compositionApi = product.getComposition().toString().replace("[", "").replace("]", "");
		String compositionFicha = garmentFicha.getDetails().getComposition().getComposition();
		if (compositionFicha.indexOf(":")!=0) {
			compositionFicha = compositionFicha.substring(compositionFicha.indexOf(":") + 2).replace(".", ","); 
		}
		return Pair.of(
				compositionApi.compareTo(compositionFicha)==0,
				String.format(
					"CheckComposition.<br><strong>CompositionApi</strong>: %s,<br><strong>CompositionFicha</strong>: %s", 
					compositionApi, compositionFicha));
	}
	
	private Pair<Boolean, String> checkWashingRules(ProductRedis product, GarmentFicha garmentFicha) {
		List<String> rulesProduct = product.getWashingRules();
		List<WashingRule> rulesFichaObj = garmentFicha.getDetails().getComposition().getWashingRules();
		if (rulesProduct==null && rulesFichaObj==null) {
			return Pair.of(true, "");
		}
		
		List<String> rulesFicha = Arrays.asList("");
		if (rulesFichaObj!=null) {
			rulesFicha = rulesFichaObj.stream()
				.map(s -> s.getText())
				.collect(Collectors.toList());
		}
		
		return Pair.of(
				rulesProduct.toString().compareTo(rulesFicha.toString())==0,
				String.format(
					"CheckWashingRules.<br><strong>RulesApi</strong>: %s,<br><strong>RulesFicha</strong>: %s", 
					rulesProduct.toString(), rulesFicha.toString()));
	}
	
	private Pair<Boolean, String> checkFamilyOnlineId(ProductRedis product, GarmentFicha garment) {
		return Pair.of(
				product.getFamilies().getFamiliesOnline().get(0).getId().compareTo(garment.getCategoryId())==0,
				String.format(
					"CheckFamilyOnlineId.<br><strong>FamilyOnlineIdApi</strong>: %s,<br><strong>FamilyOnlineIdFicha</strong>: %s", 
					product.getFamilies().getFamiliesOnline().get(0).getId(), garment.getCategoryId()));
	}
	private Pair<Boolean, String> checkFamilyLabel(ProductRedis product, GarmentFicha garment) {
		return Pair.of(
				product.getFamilies().getFamiliesOnline().get(0).getName().compareTo(garment.getFamilyLabel())==0,
				String.format(
					"CheckFamilyLabel.<br><strong>FamilyLabelApi</strong>: %s,<br><strong>FamilyLabelFicha</strong>: %s", 
					product.getFamilies().getFamiliesOnline().get(0).getName(), garment.getFamilyLabel()));
	}
	
	private Pair<Boolean, String> checkNumberColors(ProductRedis product, GarmentFicha garment) {
		return Pair.of(
				product.getColors().size()==garment.getColors().getColors().size(),
				String.format(
					"CheckNumberColors.<br><strong>NumberColorsApi</strong>: %s,<br><strong>NumberColorsCatalog</strong>: %s (No están apareciendo los colores 'relacionados' (David))",
					product.getColors().size(), garment.getColors().getColors().size()));	
	}
	
	private Pair<Boolean, String> checkNumberColorsWithStock(ProductRedis product, GarmentCatalog garment) {
		return Pair.of(
				product.getColorsWithStock().size()==garment.getColors().size(),
				String.format(
					"CheckNumberColorsWithStock.<br><strong>NumberColorsWithStockApi</strong>: %s,<br><strong>NumberColorsWithStockCatalog</strong>: %s",
					product.getColorsWithStock().size(), garment.getColors().size()));
	}
	
	private Pair<Boolean, String> checkColorId(ColorRedis colorApi, ColorGarment colorGarment) {
		return Pair.of(
				colorApi.getId().compareTo(colorGarment.getId())==0,
				String.format(
					"CheckColorId.<br><strong>ColorApiId</strong>: %s,<br><strong>ColorApiGarment</strong>: %s",
					colorApi.getId(), colorGarment.getId()));
	}
	
	private Pair<Boolean, String> checkColorDescription(ColorRedis colorApi, ColorGarment colorGarment) {
		return Pair.of(
				colorApi.getDescription().compareTo(colorGarment.getLabel())==0,
				String.format(
						"CheckColorDescription.<br>ColorId: %s,<br><strong>DescriptionApi</strong>: %s,<br>DescriptionGarment: %s", 
						colorApi.getId(), colorApi.getDescription(), colorGarment.getLabel()));
	}

	private Pair<Boolean, String> checkColorPricesCurrency(ColorRedis colorApi, ColorGarment colorGarment) {
		//Esto funciona OK en pro y KO en pre.
		if (colorApi.getPrice()==null) {
			return Pair.of(
					false,
					String.format("CheckColorPricesCurrency.<br><strong>ColorId</strong>: %s (Bloque Prices Lost (???)", colorApi.getId())); 
		}
		return Pair.of(
				colorApi.getPrice().getCurrency().compareTo(colorGarment.getPrice().getCurrency())==0,
				String.format(
						"CheckColorPricesCurrency.<br><strong>ColorId</strong>: %s,<br><strong>CurrencyApi</strong>: %s, CurrencyGarment: %s", 
						colorApi.getId(), colorApi.getPrice().getCurrency(), colorGarment.getPrice().getCurrency()));
	}

	private Pair<Boolean, String> checkColorPricesPrice(ColorRedis colorApi, ColorGarment colorGarment) {
		if (colorApi.getPrice()==null) {
			return Pair.of(
					false,
					String.format("CheckColorPricesPrice.<br><strong>ColorId</strong>: %s (Bloque Prices Lost (???)", colorApi.getId())); 
		}
		return Pair.of(
				colorApi.getPrice().getPrice().compareTo(colorGarment.getPrice().getPrice())==0,
				String.format(
						"CheckColorPricesPrice.<br><strong>ColorId</strong>: %s,<br><strong>PriceApi</strong>: %s, PriceGarment: %s", 
						colorApi.getId(), colorApi.getPrice().getPrice(), colorGarment.getPrice().getPrice()));				
	}
	
	private Pair<Boolean, String> checkColorPricesBase(ColorRedis colorApi, ColorGarment colorGarment) {
		if (colorApi.getPrice()==null) {
			return Pair.of(
					false,
					String.format("CheckColorPricesBase.<br><strong>ColorId</strong>: %s (Bloque Prices Lost (???)", colorApi.getId())); 
		}
		if (colorGarment.getPrice().getCrossedOutPrices()!=null) {
			String precioBaseApi = String.valueOf(colorApi.getPrice().getBasePrice());
			String precioBaseGarment = colorGarment.getPrice().getCrossedOutPrices().get(0).replace(",", ".");
			return Pair.of(
					precioBaseGarment.contains(precioBaseApi),
					String.format(
							"CheckColorPricesPrice.<br>ColorId: %s,<br><strong>CrossPriceApi</strong>: %s,<br><strong>CrossPriceGarment</strong>: %s", 
							colorApi.getId(), precioBaseApi, precioBaseGarment));					
		}
		return Pair.of(true, "");
	}
	
	private Pair<Boolean, String> checkNumberSizes(ColorRedis colorApi, ColorGarment colorGarment) {
		if (colorGarment.getSizes()==null) {
			return Pair.of(
					false, 
					String.format(
							"CheckNumberSizes.<br>ColorId: %s,<br><strong>NumberSizesApi</strong>: %s,<br><strong>NumberSizesGarment</strong>: %s", 
							colorApi.getId(), colorApi.getSizes().size(), null));
		}
		return Pair.of(
				colorApi.getSizes().size()==(colorGarment.getSizes().size()),
				String.format(
						"CheckNumberSizes.<br>ColorId: %s,<br><strong>NumberSizesApi</strong>: %s,<br><strong>NumberSizesGarment</strong>: %s", 
						colorApi.getId(), colorApi.getSizes().size(), colorGarment.getSizes().size()-1));
				
	}
	private Pair<Boolean, String> checkColorImages(ColorRedis colorApi, Color colorGarment) {
		for (Image image2 : colorGarment.getImages()) {
			if (colorApi.getImage(image2.getImg1Src())!=null) {
				return Pair.of(true, "");
			}
		}
		return Pair.of(
				false, 
				String.format("CheckColorImages.<br><strong>ColorId</strong>: %s (Pendiente reportar)", colorApi.getId()));
	}
	
	private Pair<Boolean, String> checkColorImages(ColorRedis colorApi, ColorGarment colorGarment) {
		List<ImageGarment> images = colorGarment.getImagesGarment();
		for (ImageGarment imageGarment : images) {
			if (colorApi.getImage(imageGarment.getUrl())!=null) {
				return Pair.of(true, "");
			}
		}
		return Pair.of(false, 
				String.format(
						"CheckColorImages.<br><strong>ColorId</strong>: %s (Pendiente reportar)", colorApi.getId()));
	}
	private Pair<Boolean, String> checkSizeId(SizeRedis sizeApi, SizeGarment sizeGarment) {
		return Pair.of(
				Integer.valueOf(sizeApi.getId())==Integer.valueOf(sizeGarment.getId()),
				String.format(
						"CheckSizeId. SizeApi: %s,<br><strong>SizeGarment</strong>: %s", sizeApi.getId(), sizeGarment.getId()));
	}
	private Pair<Boolean, String> checkSizeDescriptionBase(SizeRedis sizeApi, SizeGarment sizeGarment) {
		if ("U".compareTo(sizeApi.getDescriptions().getBaseDescription())==0) {
			return Pair.of(true, "");
		}
		
		String sizeGarmentStr = sizeGarment.getLabel();
		if (sizeGarmentStr.indexOf("[")>-1) {
			sizeGarmentStr = sizeGarmentStr.substring(0,sizeGarmentStr.indexOf("[")-1);
		}
		return Pair.of(
				sizeApi.getDescriptions().getBaseDescription().compareTo(sizeGarmentStr)==0,
				String.format(
						"CheckSizeDescriptionBase. SizeId: %s,<br><strong>SizeApi</strong>: %s,<br><strong>SizeGarment</strong>: %s", 
						sizeApi.getId(), sizeApi.getDescriptions().getBaseDescription(), sizeGarmentStr));				
	}
	private Pair<Boolean, String> checkSizeWarehouse(SizeRedis sizeApi, SizeGarment sizeGarment) {
		if (sizeGarment.isAvailable() && sizeApi.getStockDetails()!=null) {
			Pattern pattern = Pattern.compile(".*\\[.*: (.*)\\]");
			Matcher mat = pattern.matcher(sizeGarment.getLabel());
			if (mat.find()) {
				String almacenGarment = mat.group(1);
				String almacenApi = "";
				almacenApi = sizeApi.getStockDetails().get(0).getWarehouse();
				return Pair.of(
						almacenGarment.compareTo(almacenApi)==0,
						String.format(
								"CheckSizeWarehouse.SizeId: %s,<br><strong>SizeApi</strong>: %s,<br><strong>SizeGarment</strong>: %s", 
								sizeApi.getId(), almacenApi, almacenGarment));						
			}
		}
		return Pair.of(true, "");
	}
	
	private Pair<Boolean, String> checkSizeStock(SizeRedis sizeApi, Size sizeGarment) {
		if (sizeApi.getStockDetails()==null) {
			if (sizeGarment.getStock()==0) {
				return Pair.of(true, "");
			}
			return Pair.of(
					false, 
					String.format("CheckSizeStock. SizeId: %s,<br><strong>SizeApi-Stock</strong>: 0,<br><strong>SizeGarment-Stock</strong>: %s", 
							sizeApi.getId(), sizeGarment.getStock()));
		}
		return (Pair.of(
				sizeApi.getStockDetails().get(0).getStock()==sizeGarment.getStock(),
				String.format("CheckSizeStock. SizeId: %s,<br><strong>SizeApi-Stock</strong>: %s,<br><strong>SizeGarment-Stock</strong>: %s", 
						sizeApi.getId(), sizeApi.getStockDetails().get(0).getStock(), sizeGarment.getStock())));
	}
	
	private Pair<Boolean, String> checkNumberRelatedModels(ProductRedis product, GarmentFicha garment) {
		int sizeRelatedModelsApi = 0;
		int sizeRelatedModelsFicha = 0;
		if (product.getRelatedModels()!=null) {
			sizeRelatedModelsApi = product.getRelatedModels().size(); 
		}
		if (garment.getRelatedModels()!=null) {
			sizeRelatedModelsFicha = garment.getRelatedModels().size(); 
		}
		
		return Pair.of(
				sizeRelatedModelsApi==sizeRelatedModelsFicha,
				String.format(
					"CheckNumberRelatedModels.<br><strong>NumberRelatedApi</strong>: %s,<br><strong>NumberRelatedFicha</strong>: %s",
					sizeRelatedModelsApi, sizeRelatedModelsFicha));		
	}
	
	private Pair<Boolean, String> checkRelatedModelsId(RelatedModelRedis modelApi, RelatedModel modelFicha) {
		String modelApiId = (modelApi.getId()==null) ? "" : modelApi.getId();
		String modelFichaId = (modelFicha.getId()==null) ? "": modelFicha.getId();
		return Pair.of(
				modelApiId.compareTo(modelFichaId)==0,
				String.format(
					"CheckRelatedModelsId.<br><strong>RelatedModelApi</strong>: %s,<br><strong>RelatedModelFicha</strong>: %s",
					modelApiId, modelFichaId)); 
	}
	
	private Pair<Boolean, String> checkRelatedModelsType(RelatedModelRedis modelApi, RelatedModel modelFicha) {
		return Pair.of(
				modelFicha.getType().toLowerCase().contains(modelApi.getType().toLowerCase()),
				String.format(
					"CheckRelatedModelsType.<br><strong>RelatedModelTypeApi</strong>: %s,<br><strong>RelatedModelTypeFicha</strong>: %s",
					modelApi.getType(), modelFicha.getType())); 
	}
	
	private Pair<Boolean, String> checkRelatedModelsProducts(String productId, RelatedModelRedis modelApi, RelatedModel modelFicha) {
		if (modelApi.getProductIds()!=null && modelFicha.getProductIdsWithout(productId)!=null) {
			Collections.sort(modelApi.getProductIds());
			Collections.sort(modelFicha.getProductIds());
			return Pair.of(
					modelApi.getProductIds().toString().compareTo(modelFicha.getProductIdsWithout(productId).toString())==0,
					String.format(
						"CheckRelatedModelsProducts.<br><strong>RelatedModelProductsApi</strong>: %s,<br><strong>RelatedModelProductsFicha</strong>: %s",
						modelApi.getProductIds().toString(), modelFicha.getProductIdsWithout(productId).toString())); 
		}
		
		if (modelApi.getProductIds()==null && modelFicha.getProductIdsWithout(productId)==null) {
			return Pair.of(true, "");
		}
		
		return Pair.of(
				false, 
				String.format("CheckRelatedModelsProducts.<br><strong>RelatedModelProductsApi</strong>: %s,<br><strong>RelatedModelProductsFicha</strong>: %s",
				modelApi.getProductIds(), modelFicha.getProductIdsWithout(productId)));
	}
	
	private Pair<Boolean, String> checkNumberLabels(ProductRedis product, GarmentFicha garment) {
		int sizeLabelsApi = 0;
		int sizeLabelsFicha = 0;
		if (product.getLabels()!=null) {
			sizeLabelsApi = product.getLabels().size(); 
		}
		if (garment.getGarmentLabels()!=null) {
			sizeLabelsFicha = garment.getGarmentLabels().size(); 
		}
		
		return Pair.of(
				sizeLabelsApi==sizeLabelsFicha,
				String.format(
					"CheckNumberLabels.<br><strong>NumberLabelsApi</strong>: %s,<br><strong>NumberLabelsFicha</strong>: %s",
					sizeLabelsApi, sizeLabelsFicha));		
	}
	
	private Pair<Boolean, String> checkLabelId(LabelRedis labelApi, LabelGarment labelFicha) {
		return Pair.of(
				labelApi.getId().compareTo(labelFicha.getKey())==0,
				String.format(
					"CheckLabelId.<br><strong>LabelIdApi</strong>: %s,<br><strong>LabelIdFicha</strong>: %s",
					labelApi.getId(), labelFicha.getKey())); 
	}
	
	private Pair<Boolean, String> checkLabelTranslateId(LabelRedis labelApi, LabelGarment labelFicha) {
		return Pair.of(
				labelApi.getName().compareTo(labelFicha.getDescription())==0,
				String.format(
					"CheckLabelId.<br><strong>LabelTranslateIdApi</strong>: %s,<br><strong>LabelDescriptionFicha</strong>: %s",
					labelApi.getTranslateId(), labelFicha.getDescription())); 
	}

	private Optional<ProductRedis> getProductFromApi(GarmentCatalog garment) throws Exception {
		GetterProductApiCanonical getterProduct = new GetterProductApiCanonical(pais.getCodigo_alf(), idioma);
		return getterProduct.getProduct(garment.getGarmentId());
	}
	
	private GarmentFicha getProductFromGarment(String stockId, String idGarment) throws Exception {
		GetterGarment getterProduct = new GetterGarment(stockId);
		return getterProduct.getGarment(idGarment);
	}

	private static class Check {

		private String idProduct;
		private Pair<Boolean, String> check;
		
		private Check(String idProduct, Pair<Boolean, String> check) {
			super();
			this.idProduct = idProduct;
			this.check = check;
		}
		
		public static Check from(String idProduct, Pair<Boolean, String> check) {
			return new Check(idProduct, check);
		}
		
		public String getIdProduct() {
			return idProduct;
		}
		public Pair<Boolean, String> getCheck() {
			return check;
		}
	}

	private void addProductNumberToTestDescription(int catalogSize) {
		String description = TestCaseTM.getTestCaseInExecution().get().getResult().getMethod().getDescription();
		description+=" (<strong>" + catalogSize +"</strong> productos)";
		TestCaseTM.getTestCaseInExecution().get().getResult().getMethod().setDescription(description);
	}
}
