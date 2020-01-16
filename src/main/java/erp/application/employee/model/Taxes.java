package erp.application.employee.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="taxes")
public class Taxes implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	

}
