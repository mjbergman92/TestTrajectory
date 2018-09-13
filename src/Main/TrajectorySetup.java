package Main;

import java.io.File;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectorySetup {
	
	//assumes the wheel base is in the "exact" center of the robot
	double wheelBase_width = 36, wheelBase_length = 32;
	double robot_width = 40, robot_length = 38;
	Trajectory left, right;
	Trajectory.Segment segLeftX, segLeftY, segRightX, segRightY, segLV, segRV, segLA, segRA, segLJ, segRJ, segLP, segRP, segTime, segTraj;
	int i1, i2, i3, i4, iLV, iRV, iLA, iRA, iLJ, iRJ, iLP, iRP, iTime, iTraj;
	public double left1x,  right1x, left1y, right1y, left2x,  right2x, left2y, right2y;
	Trajectory trajectory;
	boolean backward;
	public int counter;
	public double xRobotOut1, yRobotOut1, xRobotOut2, yRobotOut2;
	public int posTraj = 2;
	Waypoint[] points;
	
	
	public TrajectorySetup() {
		
	}
	
	public void setup(int step) {
		resetCounters();
		waypointsStep(step);
		Trajectory.Config config = new Trajectory.Config(FitMethod.HERMITE_CUBIC, 1000, 0.020, 96, 180, 120);
		trajectory = Pathfinder.generate(points, config);
		Pathfinder.writeToCSV(new File("trajectoryStep#" + step + "Traj#" + posTraj + ".csv"), trajectory);
		TankModifier modifier = new TankModifier(trajectory);
		modifier.modify(wheelBase_width);
		if(posTraj != 7 && posTraj != 8) {
			if(step == 1) {
				left = modifier.getLeftTrajectory();
				right = modifier.getRightTrajectory();
				backward = false;
			}else if(step == 2) {
				left = modifier.getRightTrajectory();
				right = modifier.getLeftTrajectory();
				backward = true;
			}else if(step == 3) {
				left = modifier.getLeftTrajectory();
				right = modifier.getRightTrajectory();
				backward = false;
			}else if(step == 4) {
				left = modifier.getRightTrajectory();
				right = modifier.getLeftTrajectory();
				backward = true;
			}else if(step == 5) {
				left = modifier.getLeftTrajectory();
				right = modifier.getRightTrajectory();
				backward = false;
			}
		}else if(posTraj == 7 || posTraj == 8){
			if(step == 1) {
				left = modifier.getLeftTrajectory();
				right = modifier.getRightTrajectory();
				backward = false;
			}else if(step == 2) {
				left = modifier.getLeftTrajectory();
				right = modifier.getRightTrajectory();
				backward = false;
			}else if(step == 3) {
				left = modifier.getRightTrajectory();
				right = modifier.getLeftTrajectory();
				backward = true;
			}
		}
	}
	
	private void waypointsStep(int step) {
		switch(posTraj) {
		case 1: //center left switch
			
			this.getTraj1Points(step);
			
			break;
		case 2: //center right switch
			
			this.getTraj2Points(step);
			
			break;
		case 3: //left side switch
			
			this.getTraj3Points(step);
			
			break;
		case 4: //right side switch
			
			this.getTraj4Points(step);
			
			break;
		case 5: //left side scale
			
			this.getTraj5Points(step);
			
			break;
		case 6: //right side scale
			
			this.getTraj6Points(step);
			
			break;
		case 7: //left side scale cross
			
			this.getTraj7Points(step);
			
			break;
		case 8: //right side scale cross
			
			this.getTraj8Points(step);
			
			break;
	
		}
	}
	
	private void getTraj1Points(int step) {
		
		if(step == 1) {
			
			points = new Waypoint[] {
					new Waypoint(robot_length/2,(-robot_width/2)+12,0),
					new Waypoint(140 - (robot_length/2), 72 - (robot_width/2),0)	
				};
			
		}else if(step == 2) {
			
			points = new Waypoint[] {
					new Waypoint(140 - (robot_length/2), 72 -(robot_width/2),0),
					new Waypoint(robot_length/2, 0, 0)
				};
			
		}else if(step == 3) {
			
			points = new Waypoint[] {
					new Waypoint(robot_length/2, 0, 0),
					new Waypoint(100 - robot_length/2,0,0)
				};
			
		}else if(step == 4) {
			
			points = new Waypoint[] {
					new Waypoint(100 - robot_length/2,0,0),
					new Waypoint(robot_length/2, 0, 0)
				};
			
		}else if(step == 5) {
			
			points = new Waypoint[] {
					new Waypoint((robot_length/2), 0, 0),
					new Waypoint(140 - (robot_length/2), 72 -(wheelBase_width/2),0)
				};
			
		}
		
	}
	
	private void getTraj2Points(int step) {
		
		if(step == 1) {
			
			points = new Waypoint[] {
					new Waypoint(robot_length/2,(-robot_width/2)+12,0),
					new Waypoint(140 - (robot_length/2),-72+(robot_width/2),0)
				};
			
		}else if(step == 2) {
			
			points = new Waypoint[] {
					new Waypoint(140 - (robot_length/2),-72+(robot_width/2),0),
					new Waypoint(robot_length/2, 0, 0)
				};
			
		}else if(step == 3) {
			
			points = new Waypoint[] {
					new Waypoint(robot_length/2, 0, 0),
					new Waypoint(100 - robot_length/2,0,0)
				};
			
		}else if(step == 4) {
			
			points = new Waypoint[] {
					new Waypoint(100 - robot_length/2,0,0),
					new Waypoint(robot_length/2, 0, 0)
				};
			
		}else if(step == 5) {
			
			points = new Waypoint[] {
					new Waypoint((robot_length/2), 0, 0),
					new Waypoint(140 - (robot_length/2),-72+(wheelBase_width/2),0)
				};
			
		}
		
	}
	
	private void getTraj3Points(int step) {
		
		if(step == 1) {
			
			points = new Waypoint[] {
					new Waypoint((robot_length/2),-(robot_width/2)+132,0),
					new Waypoint(140, 115,Pathfinder.d2r(-22.5)),
					new Waypoint(168, 72,Pathfinder.d2r(270))
				};
			
		}else if(step == 2) {
			
			points = new Waypoint[] {
					new Waypoint(168, 72,Pathfinder.d2r(270)),
					new Waypoint(243, 120,Pathfinder.d2r(-180 + 40))
				};
			
		}else if(step == 3) {
			
			points = new Waypoint[] {
					new Waypoint(243, 120,Pathfinder.d2r(-180 + 40)),
					new Waypoint(209, 70 ,Pathfinder.d2r(-180+45))
				};
			
		}else if(step == 4) {
			
			points = new Waypoint[] {
					new Waypoint(209, 70 ,Pathfinder.d2r(-180+45)),
					new Waypoint(243, 120,Pathfinder.d2r(-180 + 40))
				};
			
		}else if(step == 5) {
			
			points = new Waypoint[] {
					new Waypoint(243, 120,Pathfinder.d2r(-180 + 40)),
					new Waypoint(168, 72,Pathfinder.d2r(270))
				};
			
		}
		
	}
	
	private void getTraj4Points(int step) {
		
		if(step == 1) {
			
			points = new Waypoint[] {
					new Waypoint((robot_length/2),(robot_width/2)-132,0),
					new Waypoint(140, -115,Pathfinder.d2r(22.5)),
					new Waypoint(168, -72,Pathfinder.d2r(90))
				};
			
		}else if(step == 2) {
			
			points = new Waypoint[] {
					new Waypoint(168, -72,Pathfinder.d2r(90)),
					new Waypoint(243, -120,Pathfinder.d2r(180 - 40))
				};
			
		}else if(step == 3) {
			
			points = new Waypoint[] {
					new Waypoint(243, -120,Pathfinder.d2r(180 - 40)),
					new Waypoint(209, -70 ,Pathfinder.d2r(180-45))
				};
			
		}else if(step == 4) {
			
			points = new Waypoint[] {
					new Waypoint(209, -70 ,Pathfinder.d2r(180-45)),
					new Waypoint(243, -120,Pathfinder.d2r(180 - 40))
				};
			
		}else if(step == 5) {
			
			points = new Waypoint[] {
					new Waypoint(243, -120,Pathfinder.d2r(180 - 40)),
					new Waypoint(168, -72,Pathfinder.d2r(90))
				};
			
		}
		
	}
	
	private void getTraj5Points(int step) {
		
		if(step == 1) {
			
			points = new Waypoint[] {
					new Waypoint((robot_length/2),-(robot_width/2)+132,0),
					new Waypoint(280, 130, Pathfinder.d2r(-2.5)),
					new Waypoint(314, 90, Pathfinder.d2r(270))
				};
			
		}else if(step == 2) {
			
			points = new Waypoint[] {
					new Waypoint(314, 90, Pathfinder.d2r(270)),
					new Waypoint(314, 120, Pathfinder.d2r(270))
				};
			
		}else if(step == 3) {
			
			points = new Waypoint[] {
					new Waypoint(314, 120, Pathfinder.d2r(270)),
					new Waypoint(250, 90, Pathfinder.d2r(225)),
					new Waypoint(209, 72-6.5,Pathfinder.d2r(180))
				};
			
		}else if(step == 4) {
			
			points = new Waypoint[] {
					new Waypoint(209, 72-6.5,Pathfinder.d2r(180)),
					new Waypoint(250, 90, Pathfinder.d2r(225)),
					new Waypoint(314, 120, Pathfinder.d2r(270))
				};
			
		}else if(step == 5) {
			
			points = new Waypoint[] {
					new Waypoint(314, 120, Pathfinder.d2r(270)),
					new Waypoint(314, 90, Pathfinder.d2r(270))
				};
			
		}
		
	}
	
	private void getTraj6Points(int step) {
		
		if(step == 1) {
			
			points = new Waypoint[] {
					new Waypoint((robot_length/2),(robot_width/2)-132,0),
					new Waypoint(280, -130, Pathfinder.d2r(2.5)),
					new Waypoint(314, -90, Pathfinder.d2r(90))
				};
			
		}else if(step == 2) {
			
			points = new Waypoint[] {
					new Waypoint(314, -90, Pathfinder.d2r(90)),
					new Waypoint(314, -120, Pathfinder.d2r(90))
				};
			
		}else if(step == 3) {
			
			points = new Waypoint[] {
					new Waypoint(314, -120, Pathfinder.d2r(90)),
					new Waypoint(250, -90, Pathfinder.d2r(135)),
					new Waypoint(209, -72+6.5,Pathfinder.d2r(180))
				};
			
		}else if(step == 4) {
			
			points = new Waypoint[] {
					new Waypoint(209, -72+6.5,Pathfinder.d2r(180)),
					new Waypoint(250, -90, Pathfinder.d2r(135)),
					new Waypoint(314, -120, Pathfinder.d2r(90))
				};
			
		}else if(step == 5) {
			
			points = new Waypoint[] {
					new Waypoint(314, -120, Pathfinder.d2r(90)),
					new Waypoint(314, -90, Pathfinder.d2r(90))
				};
			
		}
		
	}
	
	private void getTraj7Points(int step) {
		
		if(step == 1) {
			
		}else if(step == 2) {
			
		}else if(step == 3) {
			
		}else if(step == 4) {
			
		}else if(step == 5) {
			
		}
		
		/*
		}else if(posTraj == 7) {//left side scale cross
			points = new Waypoint[] {
				new Waypoint((robot_length/2),-(robot_width/2)+132,0),
				new Waypoint(185, 72 + 4 + (robot_width/2),Pathfinder.d2r(0)),
				new Waypoint(209 + 4 + (robot_width/2), 70, Pathfinder.d2r(-90))
				remove line ? new Waypoint(300, -90 + 7, Pathfinder.d2r(0))
			};*/
				/*
		}else if(posTraj == 7) {	
			points = new Waypoint[] {
				new Waypoint(209 + 4 + (robot_width/2), 70, Pathfinder.d2r(-90)),
				new Waypoint(209 + 4 + (robot_width/2), -50, Pathfinder.d2r(-90)),
				new Waypoint(300, -90 + 7, Pathfinder.d2r(0))
			};*/
		
	}
	
	private void getTraj8Points(int step) {
		
		if(step == 1) {
			
		}else if(step == 2) {
			
		}else if(step == 3) {
			
		}else if(step == 4) {
			
		}else if(step == 5) {
			
		}
		
	}
	
	public boolean setupisFinished() {
		return i1 == left.length();
	}
	
	private void resetCounters() {
		i1 = 0; i2 = 0; i3 = 0; i4 = 0; iLV = 0; iRV = 0; iLA = 0; iRA = 0; iLJ = 0; iRJ = 0; iLP = 0; iRP = 0; iTraj = 0;
	}
	
	public double leftXTrajectory() {
		if(i1 < left.length()) {
			segLeftX = left.get(i1);
			i1++;
		}
		return segLeftX.x;
	}
	
	public double leftYTrajectory() {
		if(i2 < left.length()) {
			segLeftY = left.get(i2);
			i2++;
		}
		return segLeftY.y;
	}
	
	public double rightXTrajectory() {
		if(i3 < right.length()) {
			segRightX = right.get(i3);
			i3++;
		}
		return segRightX.x;
	}
	
	public double rightYTrajectory() {
		if(i4 < right.length()) {
			segRightY = right.get(i4);
			i4++;
		}
		return segRightY.y;
	}
	
	public double leftVelocity() {
		if(iLV < left.length()) {
			segLV = left.get(iLV);
			iLV++;
		}
		return segLV.velocity;
	}
	
	public double rightVelocity() {
		if(iRV < right.length()) {
			segRV = right.get(iRV);
			iRV++;
		}
		return segRV.velocity;
	}
	
	public double leftAcceleration() {
		if(iLA < left.length()) {
			segLA = left.get(iLA);
			iLA++;
		}
		return segLA.acceleration;
	}
	
	public double rightAcceleration() {
		if(iRA < right.length()) {
			segRA = right.get(iRA);
			iRA++;
		}
		return segRA.acceleration;
	}
	
	public double leftJerk() {
		if(iLJ < left.length()) {
			segLJ = left.get(iLJ);
			iLJ++;
		}
		return segLJ.jerk;
	}
	
	public double rightJerk() {
		if(iRJ < right.length()) {
			segRJ = right.get(iRJ);
			iRJ++;
		}
		return segRJ.jerk;
	}
	
	public double leftDistance() {
		if(iLP < left.length()) {
			segLP = left.get(iLP);
			iLP++;
		}
		return segLP.position;
	}
	
	public double rightDistance() {
		if(iRP < right.length()) {
			segRP = right.get(iRP);
			iRP++;
		}
		return segRP.position;
	}
	
	public double timePassed() {
		//number of segments * timeStep = total time passed
			iTime++;
		return  iTime * 0.020;
	}
	
	public void robotBox(double lx, double ly, double rx, double ry) {
		if(iTraj < trajectory.length()) {
			segTraj = trajectory.get(iTraj);
			iTraj++;
		}
		double xChange = 0;
		double xSmall = 0;
		double yChange = 0;
		double yLarge = 0;
		double lengthMult = robot_length/2;
		double widthMult = (robot_width - wheelBase_width)/2;
		double angle = segTraj.heading;
		while(angle > Math.PI) {
			angle -= 2 * Math.PI;
		}
		while(angle < -Math.PI) {
			angle += 2 * Math.PI;
		}
		double usableAngle = Math.abs(angle);
		
		if(!backward) {
			
			usableAngle = Math.abs(Math.PI - usableAngle);
			
		}
		System.out.println(usableAngle);
		if(ly != ry && lx != rx && usableAngle > (Math.PI/2)) {
			
			double x1 = Math.abs((lengthMult - (widthMult / Math.tan(Math.PI - usableAngle))) * Math.cos(Math.PI - usableAngle));
			double x2 = Math.abs(widthMult / Math.sin(Math.PI - usableAngle));
			xSmall = Math.abs((lengthMult + (widthMult / Math.tan(Math.PI - usableAngle))) * Math.cos(Math.PI - usableAngle));
			xChange = x1 + x2;
			yChange = Math.abs((lengthMult - (widthMult / Math.tan(Math.PI - usableAngle))) * Math.sin(Math.PI - usableAngle));
			yLarge = Math.abs((lengthMult + (widthMult / Math.tan(Math.PI - usableAngle))) * Math.sin(Math.PI - usableAngle));
		
		}else if(ly != ry && lx != rx){

			double x1 = Math.abs((lengthMult - (widthMult / Math.tan((Math.PI/2) - usableAngle))) * Math.cos((Math.PI/2) - usableAngle));
			double x2 = Math.abs(widthMult / Math.sin((Math.PI/2) - usableAngle));
			xSmall = Math.abs((lengthMult + (widthMult / Math.tan((Math.PI/2) - usableAngle))) * Math.cos((Math.PI/2) - usableAngle));
			xChange = x1 + x2;
			yChange = Math.abs((lengthMult - (widthMult / Math.tan((Math.PI/2) - usableAngle))) * Math.sin((Math.PI/2) - usableAngle));
			yLarge = Math.abs((lengthMult + (widthMult / Math.tan((Math.PI/2) - usableAngle))) * Math.sin((Math.PI/2) - usableAngle));
			
		}
		System.out.println(xSmall + "\t" + xChange + "\t" + yChange + "\t" + yLarge);
		
		if((ly >= ry - 6 && ly <= ry + 6)||(ry >= ly - 6 && ry <= ly + 6)) {
			if(lx > rx) {
				left1x = lx + widthMult;
				right1x = rx - widthMult;
				left1y = ly - lengthMult;
				right1y = ry - lengthMult;
				left2x = lx + widthMult;
				right2x = rx - widthMult;
				left2y = ly + lengthMult;
				right2y = ry + lengthMult;
			}else {
				left1x = lx - widthMult;
				right1x = rx + widthMult;
				left1y = ly + lengthMult;
				right1y = ry + lengthMult;
				left2x = lx - widthMult;
				right2x = rx + widthMult;
				left2y = ly - lengthMult;
				right2y = ry - lengthMult;
			}
		}else if((lx >= rx - 6 && lx <= rx + 6)||(rx >= lx - 6 && rx <= lx + 6)) {
			if(ly > ry) {
				left1x = lx + lengthMult;
				right1x = rx + lengthMult;
				left1y = ly + widthMult;
				right1y = ry - widthMult;
				left2x = lx - lengthMult;
				right2x = rx - lengthMult;
				left2y = ly + widthMult;
				right2y = ry - widthMult;
			}else {
				left1x = lx - lengthMult;
				right1x = rx - lengthMult;
				left1y = ly - widthMult;
				right1y = ry + widthMult;
				left2x = lx + lengthMult;
				right2x = rx + lengthMult;
				left2y = ly - widthMult;
				right2y = ry + widthMult;
			}
		}else {
			if(ly > ry) {
				if(lx > rx) { 						//angle > about 1.57 and < about 3.14
					left1x = lx - xChange;
					right1x = rx - xSmall;
					left1y = ly + yChange;
					right1y = ry + yLarge;
					left2x = lx + xSmall ;
					right2x = rx + xChange;
					left2y = ly - yLarge;
					right2y = ry - yChange;
				}else{ 								//angle > -3.14 and < about -1.57
					left1x = lx - yChange;
					right1x = rx - yLarge;
					left1y = ly - xChange;
					right1y = ry - xSmall;
					left2x = lx + yLarge;
					right2x = rx + yChange;
					left2y = ly + xSmall;
					right2y = ry + xChange;
					
				}
			}else {
				if(lx > rx) {
					//System.out.println(usableAngle);
					//angle < -1.57 and > about -3.14
					left1x = lx - yChange;
					right1x = rx - yLarge;
					left1y = ly - xChange;
					right1y = ry - xSmall;
					left2x = lx + yLarge;
					right2x = rx + yChange;
					left2y = ly + xSmall;
					right2y = ry + xChange;
					
				}else{
					//angle > about -1.57 and < 0
					left1x = lx + xSmall;
					right1x = rx + xChange;
					left1y = ly - yLarge;
					right1y = ry - yChange;
					left2x = lx - xChange;
					right2x = lx - xSmall;
					left2y = ly + yChange;
					right2y = ry + yLarge;
					
				}
			}
		}
	
	}	
	
	public void drawRobot(){
		int countEvenOdd = counter;			

		double xChange1 = (left2x - left1x)/24;
		double yChange1 = (left2y - left1y)/24;
		double xChange2 = (left1x - right1x)/24;
		double yChange2 = (left1y - right1y)/24;
			
		while(countEvenOdd > 1){
			countEvenOdd -= 2;
		}
			
		if(countEvenOdd == 0){
			xRobotOut1 = left1x + xChange1*counter;
			yRobotOut1 = left1y + yChange1*counter;
			xRobotOut2 = left1x - xChange2*counter;
			yRobotOut2  = left1y - yChange2*counter;
		}else{
			xRobotOut1 = right1x + xChange1*counter;
			yRobotOut1 = right1y + yChange1*counter;
			xRobotOut2 = left2x - xChange2*counter;
			yRobotOut2  = left2y - yChange2*counter;
		}
	
		counter++;
	}

}

