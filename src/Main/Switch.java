package Main;

public class Switch {
	
	int xCounter, yCounter;
	int yEvenOrOdd;
	double xOut, yOut;
	
	public Switch() {
		
	}
	
	public double drawX() {
		xOut =  140 + (xCounter * 0.25);
		xCounter++;
		if(xOut > 196) {
			xOut = 196;
		}
		return xOut;
	}
	
	public double drawY() {
		yEvenOrOdd = yCounter;
		if(xOut >= 140 && xOut <= 196) {
			while(yEvenOrOdd > 1) {
				yEvenOrOdd -= 2;
			}
			if(yEvenOrOdd == 0) {
				yOut = -72;
			}else {
				yOut = 72;
			}
		}else {
			yOut = 0;
		}
		yCounter++;
		return yOut;
	}

}
