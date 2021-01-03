package erp.application.employee.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Table(name="taxes")
public class Taxes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PI_ADDRESS_SEQ")
	@NotNull
	@Column(name="tax_id")
	private int tax_id;
	@Column(name="casTax")
	private double casTax;
	@Column(name="cassTax")
	private double cassTax;
	@Column(name = "incomeTax")
	private double incomeTax;
	
	public Taxes() {}
	
	public Taxes(double casTax, double cassTax, double incomeTax) {
		this.casTax = casTax;
		this.cassTax = cassTax;
		this.incomeTax = incomeTax;
	}

	public double getCasTax() {
		return casTax;
	}

	public void setCasTax(double casTax) {
		this.casTax = casTax;
	}

	public double getCassTax() {
		return cassTax;
	}

	public void setCassTax(double cassTax) {
		this.cassTax = cassTax;
	}

	public double getIncomeTax() {
		return incomeTax;
	}

	public void setIncomeTax(double incomeTax) {
		this.incomeTax = incomeTax;
	}

	@Override
	public String toString() {
		return "Taxes [casTax=" + casTax + ", cassTax=" + cassTax + ", incomeTax=" + incomeTax + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(casTax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(cassTax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(incomeTax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + tax_id;
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
		Taxes other = (Taxes) obj;
		if (Double.doubleToLongBits(casTax) != Double.doubleToLongBits(other.casTax))
			return false;
		if (Double.doubleToLongBits(cassTax) != Double.doubleToLongBits(other.cassTax))
			return false;
		if (Double.doubleToLongBits(incomeTax) != Double.doubleToLongBits(other.incomeTax))
			return false;
		if (tax_id != other.tax_id)
			return false;
		return true;
	}
	
	

}
