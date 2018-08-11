package Main;

public class BoxOfCubes {
	
	int xCounter, yCounter;
	int yEvenOrOdd;
	double xOut, yOut;
	
	public BoxOfCubes() {
		
	}
	
	public double drawX() {
		xOut =  140 -39 + (xCounter * 0.25);
		xCounter++;
		if(xOut > 140) {
			xOut = 140;
		}
		return xOut;
	}
	
	public double drawY() {
		yEvenOrOdd = yCounter;
		xOut -=140;
		if(xOut >= -39 && xOut < -26) {
			while(yEvenOrOdd > 1) {
				yEvenOrOdd -= 2;
			}
			if(yEvenOrOdd == 0) {
				yOut = -6.5;
			}else {
				yOut = 6.5;
			}
		}else if(xOut >= -26 && xOut < -13) {
			while(yEvenOrOdd > 1) {
				yEvenOrOdd -= 2;
			}
			if(yEvenOrOdd == 0) {
				yOut = -13;
			}else {
				yOut = 13;
			}
		}else if(xOut >= -13 && xOut <=0) {
			while(yEvenOrOdd > 1) {
				yEvenOrOdd -= 2;
			}
			if(yEvenOrOdd == 0) {
				yOut = -19.5;
			}else {
				yOut = 19;
			}
		}else {
			yOut = 0;
		}
		yCounter++;
		return yOut;
	}

}
