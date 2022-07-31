public class Real {
	private double realNum;
	
	//Default Constructor
	public Real() {
		this(0);
	}
	
	//Overloaded Constructor
	public Real(double num) {
		this.realNum = num;
	}
	
	//get real number
	public double getRealNum() {
		return realNum;
	}
	
	//set real number
	public void setRealNum(double r) {
		this.realNum = r;
	}
	
	//returns display format
	@Override
	public String toString() {
		return (String.format("%.2f", this.realNum));
	}
	
	//checks if objects are equal
	@Override
	public boolean equals(Object a) {
		if (a instanceof Real) {
			if (Double.compare(this.realNum, ((Real)a).realNum) == 0)
				return true;
		}
		return false;
	}
	
	//checks if compare operator is valid
	public boolean compareTo(String operator, Real a) {
		if (operator.equals(">"))
			if (Double.compare(this.realNum, a.getRealNum()) > 0)
				return true;
		if (operator.equals("<"))
			if (Double.compare(this.realNum, a.getRealNum()) < 0)
				return true;
		return false;	
	}
}
