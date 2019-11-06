package erp.application.employee.model;

public class Taxes {
	
	private double casTax;
	private double cassTax;
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
