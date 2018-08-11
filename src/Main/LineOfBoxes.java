package Main;

public class LineOfBoxes {
	
	int xCounterTop, yCounterTop;
	int yEvenOrOddTop;
	double xOutTop, yOutTop, xOutBottom, yOutBottom;
	
	public LineOfBoxes() {
		
	}
	
	public double drawX1() {
		xOutTop =  196 + (xCounterTop * 0.25);
		xCounterTop++;
		if(xOutTop > 209) {
			xOutTop = 209;
		}
		return xOutTop;
	}
	
	public double drawY1() {
		yEvenOrOddTop = yCounterTop;
		if(xOutTop >= 196 && xOutTop <= 209) {
			while(yEvenOrOddTop > 1) {
				yEvenOrOddTop -= 2;
			}
			if(yEvenOrOddTop == 0) {
				yOutTop = 72;
			}else {
				yOutTop = 59;
			}
		}else {
			yOutTop = 72;
		}
		yCounterTop++;
		return yOutTop;
	}
	
	public double drawBottomX() {
		xOutBottom = xOutTop;
		return xOutBottom;
	}
	
	public double drawBottomY(int level) {
		level -=1;
		if(yOutTop == 72) {
			yOutBottom = 72 - (level*(13 + 13.2));
		}else {
			yOutBottom = 59 - (level*(13 + 13.2));
		}
		return yOutBottom;
	}

}
