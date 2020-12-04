package erp.application.products;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="ProductsXmlFormat")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsXmlFormat {
	
	@XmlAttribute(name= "product_id")
	private int id;
	@XmlElement(name = "product_category")
	private String product_category;
	@XmlElement(name = "product_manufacturer")
	private String product_manufacturer;
	@XmlElement(name = "product_name")
	private String product_name;
	@XmlElement(name = "vat_level")
	private int vat_level;
	@XmlElement(name = "product_price")
	private int product_price;
	@XmlElement(name = "product_code")
	private String product_code;
	
	public ProductsXmlFormat() {}

	public ProductsXmlFormat(int id, String product_category, String product_manufacturer, String product_name,
			int vat_level, int product_price, String product_code) {
		super();
		this.id = id;
		this.product_category = product_category;
		this.product_manufacturer = product_manufacturer;
		this.product_name = product_name;
		this.vat_level = vat_level;
		this.product_price = product_price;
		this.product_code = product_code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct_category() {
		return product_category;
	}

	public void setProduct_category(String product_category) {
		this.product_category = product_category;
	}

	public String getProduct_manufacturer() {
		return product_manufacturer;
	}

	public void setProduct_manufacturer(String product_manufacturer) {
		this.product_manufacturer = product_manufacturer;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public int getVat_level() {
		return vat_level;
	}

	public void setVat_level(int vat_level) {
		this.vat_level = vat_level;
	}

	public int getProduct_price() {
		return product_price;
	}

	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}

	public String getProduct_code() {
		return product_code;
	}

	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}

	@Override
	public String toString() {
		return "ProductsXmlFormat [id=" + id + ", product_category=" + product_category + ", product_manufacturer="
				+ product_manufacturer + ", product_name=" + product_name + ", vat_level=" + vat_level
				+ ", product_price=" + product_price + ", product_code=" + product_code + "]";
	}
	
	

}
