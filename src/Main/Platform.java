package Main;

public class Platform {
	
	int xCounter, yCounter;
	int yEvenOrOdd;
	double xOut, yOut;
	
	public Platform() {
		
	}
	
	public double drawX() {
		xOut =  261.5 + (xCounter * 0.25);
		xCounter++;
		if(xOut > 300) {
			xOut = 300;
		}
		return xOut;
	}
	
	public double drawY() {
		yEvenOrOdd = yCounter;
		if(xOut >= 261.5 && xOut <= 300) {
			while(yEvenOrOdd > 1) {
				yEvenOrOdd -= 2;
			}
			if(yEvenOrOdd == 0) {
				yOut = -62;
			}else {
				yOut = 62;
			}
		}else {
			yOut = 0;
		}
		yCounter++;
		return yOut;
	}

}
