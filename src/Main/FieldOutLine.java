package Main;

public class FieldOutLine {
	
	int xCounterTop, yCounterTop;
	int yEvenOrOddTop;
	double xOutTop, yOutTop, xOutBottom, yOutBottom;
	
	public FieldOutLine() {
		
	}
	
	public double drawTopX() {
		xOutTop =  (xCounterTop * 1);
		xCounterTop++;
		if(xOutTop > 300) {
			xOutTop = 300;
		}
		return xOutTop;
	}
	
	public double drawTopY() {
		yEvenOrOddTop = yCounterTop;
		if(xOutTop >= 0 && xOutTop <= 300) {
			while(yEvenOrOddTop > 1) {
				yEvenOrOddTop -= 2;
			}
			if(yEvenOrOddTop == 0) {
				yOutTop = 162;
			}else {
				yOutTop = 165;
			}
		}else {
			yOutTop = 0;
		}
		yCounterTop++;
		return yOutTop;
	}
	
	public double drawBottomX() {
		xOutBottom = xOutTop;
		return xOutBottom;
	}
	
	public double drawBottomY() {
		if(yOutTop == 0) {
			yOutBottom = 0;
		}else {
			yOutBottom = yOutTop - (60 + 264 + 3);
		}
		return yOutBottom;
	}
	
	public double drawTopX2() {
		return 600 - xOutTop;
	}
	
	public double drawTopY2() {
		return yOutTop;
	}
	
	public double drawBottomX2() {
		return drawTopX2();
	}
	
	public double drawBottomY2() {
		return yOutBottom;
	}

}
