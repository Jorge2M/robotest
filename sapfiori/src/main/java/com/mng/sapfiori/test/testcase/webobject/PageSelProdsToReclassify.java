package com.mng.sapfiori.test.testcase.webobject;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader.FieldFilterFromListI;
import com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader.FilterHeaderSection;
import com.mng.sapfiori.test.testcase.generic.webobject.sections.filterheader.ModalSetFieldFromListI;
import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;

public class PageSelProdsToReclassify extends FilterHeaderSection {
	
	private final WebDriver driver;
	public static Option option = Option.ClasificarProductos;
	
	private final static String XPathTitle = "//h1[starts-with(text(), '" + option.getTitlePage() + "')]";
	private final static String XPathIrButton = "//button[@id[contains(.,'btnGo')]]";
	private final static String XPathVolverClasificarButton = "//button[@id[contains(.,'btnReclassify')]]";
	private final static String XPathTableDataProducs = "//table[@id[contains(.,'Table-table')]]";
	private final static String XPathRadioProduct = "//div[@id[contains(.,'rowsel')]]";

	
	private PageSelProdsToReclassify(WebDriver driver) {
		super(driver);
		this.driver = driver;
	}
	
	public static PageSelProdsToReclassify getNew(WebDriver driver) {
		return new PageSelProdsToReclassify(driver);
	}
	
	private enum ElemTable {
		Id("-Product"),
		Description("-Product_Text"),
		CodEstadMerc("-CommodityCode"),
		GroupProducts("-ProductGroup"),
		Sector("-Division");
		
		String id;
		private ElemTable(String id) {
			this.id = id;
		}
		public String getXPath() {
			return XPathTableDataProducs + getTd();
		}
		
		public String getXPath(int position) {
			return XPathTableDataProducs + "//tr[@data-sap-ui-rowindex='" + position + "']" + getTd();
		}
		
		public String getText(int position, WebDriver driver) {
			return (driver.findElement(By.xpath(getXPath(position))).getText());
		}
		
		private String getTd() {
			return "//td[@data-sap-ui-colid[contains(.,'" + id + "')]]";
		}
	}
	
	private String getXPathCellWithIdProduct(String idProduct) {
		return (
			ElemTable.Id.getXPath() + "//span[text()='" + idProduct + "']/../..");
	}
	
	private final String getXPathRadioProduct(int posProduct) {
		return (XPathRadioProduct + "//self::*[@data-sap-ui-rowindex='" + posProduct + "']");
	}
	
	private final String getXPathRadioProduct(String productId) {
		int posInTheTable = getNumRowWithIdProduct(productId);
		return (getXPathRadioProduct(posInTheTable));
	}
	
	/**
	 * @param idProduct the data to search in the column "Producto"
	 * @return the position in the table of products. -1 if the product is not in the list
	 */
	private int getNumRowWithIdProduct(String idProduct) {
		By byTd = By.xpath(getXPathCellWithIdProduct(idProduct));
		WebdrvWrapp.isElementVisibleUntil(driver, byTd, 5);
		String idTd = driver.findElement(byTd).getAttribute("id");
		Pattern rowNumberPattern = Pattern.compile(".*row(\\d*).*");
		Matcher matcher = rowNumberPattern.matcher(idTd);
		if (matcher.matches()) {
			return Integer.valueOf(matcher.group(1));
		}
		return -1;
	}
	
	public boolean isVisiblePage(int maxSeconds) {
		return WebdrvWrapp.isElementVisibleUntil(driver, By.xpath(XPathTitle), maxSeconds);
	}
	
	public ModalSetFieldFromListI clickIconSetFilter(FieldFilterFromListI fieldFilter) {
		return (
			getInputSetFieldFromList(fieldFilter, driver)
				.clickIconSetFilter());
	}
	
	public void clickIrButton() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathIrButton));
	}
	
	public void selectProducts(List<String> listIdsProductos) {
		for (String productId : listIdsProductos) {
			String xpathRadio = getXPathRadioProduct(productId);
			driver.findElement(By.xpath(xpathRadio)).click();
		}
	}
	
	public PageReclassifProducts clickVolverAclasificar() throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathVolverClasificarButton));
		return (PageReclassifProducts.getNew(driver));
	}
	
	public List<ProductData> getData(List<String> productsId) {
		List<ProductData> listProductData = new ArrayList<>();
		for (String productId : productsId) {
			int posInTheTable = getNumRowWithIdProduct(productId);
			if (posInTheTable>=0) {
				ProductData productData = new ProductData();
				productData.id = ElemTable.Id.getText(posInTheTable, driver);
				productData.description = ElemTable.Description.getText(posInTheTable, driver);
				productData.codEstadMerc = ElemTable.CodEstadMerc.getText(posInTheTable, driver);
				productData.grupoProductos = ElemTable.GroupProducts.getText(posInTheTable, driver);
				productData.sector = ElemTable.Sector.getText(posInTheTable, driver);
				listProductData.add(productData);
			}
		}
		return listProductData;
	}
	
	public class ProductData {
		public String id;
		public String description;
		public String codEstadMerc;
		public String grupoProductos;
		public String sector;
	}
}
