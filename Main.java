/* Name : Amy Mendiola
 * netID: atm190002
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		
		final String FILE_NAME;
		
		//file and input operations
		Scanner scnr = new Scanner(System.in);
		System.out.println("Please input the name of the data file."); //prompt user for file name
		FILE_NAME = scnr.next();
		File in = new File(FILE_NAME);
		Scanner input = new Scanner(in);
		
		ArrayList<Real> objArray = new ArrayList<Real>();
		
		if (in.canRead())
		{
			readFile(input, objArray);
		}
		scnr.close();
		input.close();
	}
	
	//read file and call functions to set number objects and calculate results
	public static void readFile(Scanner inputFile, ArrayList<Real> array)
	{
		String input1, input2;
		String operator;
		String expression = "";
		boolean invalid;
		int index = 0;
		
		while (inputFile.hasNext()) { //loop until last line
			expression = inputFile.nextLine();
			invalid = false;
			if (!(expression.contains(" "))) { //if only 1st operand
				continue;
			}
			if(expression.length() - 1  ==  expression.indexOf(" ") + 1) { // if no 2nd operand
				continue;
			}
			
			//operand#1
			input1 = expression.substring(0, expression.indexOf(" "));
			invalid = checkInvalidOperand(input1); 
			if (invalid == true) { //if invalid ignore and skip line
				continue;
			}
			
			//expression
			operator = expression.substring(expression.indexOf(" ") + 1, expression.indexOf(" ",expression.indexOf(" ")+1));
			invalid = checkInvalidOperator(operator);
			if (invalid == true) {//if invalid ignore and skip line
				continue;
			}

			//operand #2
			input2 = expression.substring(expression.indexOf(" ",expression.indexOf(" "))+3, expression.length());
			invalid = checkInvalidOperand(input2); 
			if (invalid == true) //if invalid ignore
				continue;
			
			//create objects
			setObj(inputFile, array, input1); //create first object
			setObj(inputFile, array, input2); //create second object
			
			System.out.print(expression); //display whole expression
			index += 2;
			getCalculations(array.get(index-2),array.get(index-1), operator);
		}
	}
	
	//create number objects
	public static void setObj(Scanner inputFile, ArrayList<Real> array, String input) {
		double complexTemp;
		double realTemp;
		if (input.contains("i")) { //if Complex object
			if (input.contains("+")) {
				realTemp = Double.parseDouble(input.substring(0,input.indexOf('+')));
				complexTemp = Double.parseDouble(input.substring(input.indexOf('+')+1,input.length()-1));
				array.add(new Complex(realTemp,complexTemp));
			}
			else if (input.contains("-")) {
				realTemp = Double.parseDouble(input.substring(0,input.indexOf('-',1)));
				complexTemp = Double.parseDouble(input.substring(input.indexOf('-',1),input.length()-1));
				array.add(new Complex(realTemp,complexTemp));
			}
			else { 
				complexTemp = Double.parseDouble(input.substring(0,input.length()-1));
				array.add(new Complex(0,complexTemp));
			}
		} 
		else { //Real object
			array.add(new Real(Double.parseDouble(input)));
		}
	}
	
	//calls specific function calls to perform the specific operation 
	public static void getCalculations(Real a, Real b, String operator) {
		if (operator.equals("+"))
			calcAdd(a,b);
		else if (operator.equals("-"))
			calcSub(a,b);
		else if (operator.equals("*"))
			calcMult(a,b);
		else if (operator.equals("/"))
			calcDiv(a,b);
		else if (operator.equals("="))
			calcEq(a,b);
		else if (operator.equals(">") || operator.equals("<"))
			calcCompare(a,b,operator);
		
	}
	
	//addition operation
	public static void calcAdd(Real a, Real b) {
		double complexTemp = 0; // complex number addition variable
		double realTemp; //real number addition variable
		realTemp = a.getRealNum() + b.getRealNum(); 
		if (a instanceof Complex && b instanceof Complex ) {
			complexTemp = ((Complex)a).getImagNum() + ((Complex)b).getImagNum();
		}
		else if (a instanceof Complex && b instanceof Real ) {
			complexTemp = ((Complex)a).getImagNum();
		}
		else if (a instanceof Real && b instanceof Complex ) {
			complexTemp = ((Complex)b).getImagNum();
		}	
		//Display
		if (a instanceof Complex || b instanceof Complex ) {
			Complex calc = new Complex (realTemp, complexTemp);
			System.out.println("\t" + calc.toString());
		}
		else {
			Real calc = new Real (realTemp);
			System.out.println("\t" + calc.toString());
		}
	}
	
	//subtraction operation
	public static void calcSub(Real a, Real b) {
		double complexTemp = 0; //complex number subtraction variable
		double realTemp; //real number subtraction variable
		realTemp = a.getRealNum() - b.getRealNum();
		if (a instanceof Complex && b instanceof Complex ) {
			complexTemp = ((Complex)a).getImagNum() - ((Complex)b).getImagNum();
		}
		else if (a instanceof Complex && b instanceof Real ) {
			complexTemp = ((Complex)a).getImagNum();
		}
		else if (a instanceof Real && b instanceof Complex ) {
			complexTemp = ((Complex)b).getImagNum() * -1;
		}	
		//Display
		if (a instanceof Complex || b instanceof Complex ) {
			Complex calc = new Complex (realTemp, complexTemp);
			System.out.println("\t" + calc.toString());
		}
		else {
			Real calc = new Real (realTemp);
			System.out.println("\t" + calc.toString());
		}
	}
		
	//multiply operation
	public static void calcMult(Real a, Real b) {
		double complexTemp = 0; //complex number multiplication variable
		double realTemp; //real number multiplication variable
		realTemp = a.getRealNum() * b.getRealNum();
		if (a instanceof Complex && b instanceof Complex ) {
			realTemp -= ((Complex)a).getImagNum() * ((Complex)b).getImagNum();
			complexTemp = ((Complex)a).getImagNum() * b.getRealNum() + ((Complex)b).getImagNum() * a.getRealNum();
		}
		else if (a instanceof Complex && b instanceof Real ) {
			complexTemp = b.getRealNum() * ((Complex)a).getImagNum();
		}
		else if (a instanceof Real && b instanceof Complex ) {
			complexTemp = a.getRealNum() * ((Complex)b).getImagNum();
		}	
		//Display
		if (a instanceof Complex || b instanceof Complex ) {
			Complex calc = new Complex (realTemp, complexTemp);
			System.out.println("\t" + calc.toString());
		}
		else {
			Real calc = new Real (realTemp);
			System.out.println("\t" + calc.toString());
		}
	}
		
	//divide operation
	public static void calcDiv(Real a, Real b) {
		double complexTemp = 0; //complex number division variable
		double realTemp = 0; //real number division variable
		double realConjugate, imagConjugate, divisor;
		if (b instanceof Complex) {
			realConjugate = b.getRealNum();			//create conjugate
			imagConjugate = ((Complex)b).getImagNum() * -1;
			Complex conjugate = new Complex(realConjugate, imagConjugate);
			if (a instanceof Complex) {
				//mult by conjugate
				realTemp = a.getRealNum() * b.getRealNum();
				realTemp -= ((Complex)a).getImagNum() * conjugate.getImagNum();
				complexTemp = ((Complex)a).getImagNum() * conjugate.getRealNum() + conjugate.getImagNum() * a.getRealNum();
				//divide by divisor
				divisor = b.getRealNum()*b.getRealNum() + ((Complex)b).getImagNum()*((Complex)b).getImagNum();
				realTemp /= divisor;
				complexTemp/= divisor;
			}
			else if (a instanceof Real) {
				//mult by conjugate
				realTemp = a.getRealNum() * b.getRealNum();
				complexTemp = a.getRealNum() * conjugate.getImagNum();
				//divide by divisor
				divisor = b.getRealNum()*b.getRealNum() + ((Complex)b).getImagNum()*((Complex)b).getImagNum();
				realTemp /= divisor;
				complexTemp/= divisor;
			}
		}
		else if (a instanceof Complex && b instanceof Real ) {
			realTemp = a.getRealNum() / b.getRealNum();
			complexTemp = ((Complex)a).getImagNum() / b.getRealNum();
		}
		else if (a instanceof Real && b instanceof Complex ) {
			realTemp = a.getRealNum() / b.getRealNum();
			complexTemp = a.getRealNum() / ((Complex)b).getImagNum();
		}	
		if (a instanceof Complex || b instanceof Complex ) {
			Complex calc = new Complex (realTemp, complexTemp);
			System.out.println("\t" + calc.toString());
		}
		else {
			realTemp = a.getRealNum() / b.getRealNum();
			Real calc = new Real (realTemp);
			System.out.println("\t" + calc.toString());
		}
}	
		
	//equal operation
	public static void calcEq(Real a, Real b) {
		boolean complexEq = false; //complex equality variable
		boolean realEq = false; //real equality variable
		realEq = a.equals(b);
		if (a instanceof Complex && b instanceof Complex || a instanceof Complex && b instanceof Real ) {
			complexEq = ((Complex)a).equals(b);
		}
		else if (a instanceof Real && b instanceof Complex ) {
			complexEq = ((Complex)b).equals(a);
		}
		//display
		if (a instanceof Complex || b instanceof Complex) {
			if (complexEq == true && realEq == true)
				System.out.println("\t" + "true");
			else
				System.out.println("\t" + "false");
		}
		else {
				System.out.println("\t" + realEq);
		}
	}
		
	//greater-than or less-than operation
	public static void calcCompare(Real a, Real b, String operator) {
		boolean comparison = false; //comparison variable
		if (a instanceof Complex) {
			comparison = ((Complex)a).compareTo(operator, b);
		}
		else if (b instanceof Complex) {
			Complex tempA = new Complex(a.getRealNum(),0);
			comparison = (tempA.compareTo(operator, b));
		}
		else {
			comparison = a.compareTo(operator, b);
		}
		//display
		System.out.println("\t" + comparison);
	}
	
	//check for invalid characters in operand
	public static boolean checkInvalidOperand(String line) {
		int maxDecimals = 1;
		String subString1, subString2;
		//check num decimals
		if (line.contains("+")) {
			subString1 = line.substring(0, line.indexOf("+"));
			subString2 = line.substring(line.indexOf("+") + 1, line.length());
			if (numDecimalPoint(subString1) > maxDecimals || numDecimalPoint(subString2) > maxDecimals ) {
				return true;
			}
		}
		else if (line.substring(1,line.length()).contains("-"))
		{
			subString1 = line.substring(0,line.indexOf("-", 1));
			subString2 = line.substring(line.indexOf("-", 1) + 1, line.length());
			if (numDecimalPoint(subString1) > maxDecimals || numDecimalPoint(subString2) > maxDecimals ) {
				return true;
			}
		}
		else {
			if (numDecimalPoint(line) > maxDecimals) {
				return true;
			}
		}
		
		//check invalid characters
		for(int i = 0; i < line.length(); i++ ) {
			char character = line.charAt(i);
			if (!(character == 'i' || character == '.' || Character.isDigit(character) || character == '+' || character == '-')) {
				return true;
			}
		}
		return false;
	}
	
	//check invalid expression in operator symbol
	public static boolean checkInvalidOperator(String line) {
		if (line.length() == 1) {
			char character = line.charAt(0);
			if (!(character == '+' || character == '-' || character == '*' || character == '/' || character == '=' || character == '>' || character == '<')) {
				return true;
			}
		}
		return false;
	}
	
	public static int numDecimalPoint(String line) {
		int numDecimals = 0;
		for(int i = 0; i < line.length(); i++ ) {
			if (line.charAt(i) == '.') {
				numDecimals++;
			}
		}
		return numDecimals;
	}
}


