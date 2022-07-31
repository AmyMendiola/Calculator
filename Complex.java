public class Complex extends Real {
	private double imagNum;
	
	//Default Constructor
	public Complex() {
		this(0,0);
	}
	
	//Overloaded Constructor
	public Complex(double r, double i) {
		super(r);
		this.imagNum = i;
	}
	
	//get imaginary number
	public double getImagNum() {
		return imagNum;
	}
	
	 //set imaginary number
	public void setImagNum(double i) {
		this.imagNum = i;
	}
	
	//returns display format
	@Override
	public String toString() {
		if (super.getRealNum() == 0) { //check if there is a real component
			return ((String.format("%.2f", imagNum) + "i" ));
		}
		else if (imagNum < 0) { //Complex number with subtraction
			return (super.toString() + (String.format("%.2f", imagNum) + "i" ));
		}
		else { //Complex number with addition
			return (super.toString() + "+" + (String.format("%.2f",imagNum)) + "i" );
		}
	}
	
	//checks if objects are equal
	@Override
	public boolean equals(Object a) {
		if (a instanceof Complex) {
			if (Double.compare(this.imagNum, ((Complex)a).imagNum) == 0 && super.equals(a)) { //check if imaginary and real components are equal 
				return true;
			}
		}
		return false;
	}

	//checks if compare operator is valid
	public boolean compareTo(String operator, Real a) {
		double magnitude, magnitudeA = 0;
		magnitude = Math.sqrt(this.getRealNum() * this.getRealNum() + this.imagNum * this.imagNum);
		if(a instanceof Complex) {
			magnitudeA = Math.sqrt(a.getRealNum() * a.getRealNum() + ((Complex)a).imagNum * ((Complex)a).imagNum);
		}
		else if (a instanceof Real) {
			magnitudeA = a.getRealNum();
		}
		//compare magnitudes of both objects
		if (operator.equals(">"))
			if (Double.compare(magnitude, magnitudeA) > 0)
				return true;
		if (operator.equals("<"))
			if (Double.compare(magnitude, magnitudeA) < 0)
				return true;
		return false;
	}
}
