package Main;

public class Scale {
	
	int xCounter, yCounter;
	int yEvenOrOdd;
	double xOut, yOut;
	
	public Scale() {
		
	}
	
	public double drawX() {
		xOut =  300 + (xCounter * 0.25);
		xCounter++;
		if(xOut > 348) {
			xOut = 348;
		}
		return xOut;
	}
	
	public double drawY() {
		yEvenOrOdd = yCounter;
		if(xOut >= 300 && xOut <= 348) {
			while(yEvenOrOdd > 1) {
				yEvenOrOdd -= 2;
			}
			if(yEvenOrOdd == 0) {
				yOut = -90;
			}else {
				yOut = 90;
			}
		}else {
			yOut = 0;
		}
		yCounter++;
		return yOut;
	}

}
