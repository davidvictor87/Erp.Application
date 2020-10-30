package erp.application.products;

import java.io.Serializable;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "PRODUCTS")
public class Products implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	private String product_category;
	private String product_manufacturer;
	private String product_name;
	private int vat_level;
	private int product_price;
	private String product_code;
	
	public Products() {
		
	}

	public Products(int id, String product_category, String product_manufacturer, String product_name, int vat_level,
			int prodct_price,String product_code) {
		super();
		this.id = id;
		this.product_category = product_category;
		this.product_manufacturer = product_manufacturer;
		this.product_name = product_name;
		this.vat_level = vat_level;
		this.product_price = prodct_price;
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
		return "Products [id=" + id + ", product_category=" + product_category + ", product_manufacturer="
				+ product_manufacturer + ", product_name=" + product_name + ", vat_level=" + vat_level
				+ ", product_price=" + product_price + ", product_code=" + product_code + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((product_category == null) ? 0 : product_category.hashCode());
		result = prime * result + ((product_code == null) ? 0 : product_code.hashCode());
		result = prime * result + ((product_manufacturer == null) ? 0 : product_manufacturer.hashCode());
		result = prime * result + ((product_name == null) ? 0 : product_name.hashCode());
		result = prime * result + product_price;
		result = prime * result + vat_level;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Products other = (Products) obj;
		if (id != other.id)
			return false;
		if (product_category == null) {
			if (other.product_category != null)
				return false;
		} else if (!product_category.equals(other.product_category))
			return false;
		if (product_code == null) {
			if (other.product_code != null)
				return false;
		} else if (!product_code.equals(other.product_code))
			return false;
		if (product_manufacturer == null) {
			if (other.product_manufacturer != null)
				return false;
		} else if (!product_manufacturer.equals(other.product_manufacturer))
			return false;
		if (product_name == null) {
			if (other.product_name != null)
				return false;
		} else if (!product_name.equals(other.product_name))
			return false;
		if (product_price != other.product_price)
			return false;
		if (vat_level != other.vat_level)
			return false;
		return true;
	}

	
	
	
	
}
