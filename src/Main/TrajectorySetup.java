package Main;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.Trajectory.FitMethod;
import jaci.pathfinder.modifiers.TankModifier;

public class TrajectorySetup {
	
	double wheelBase_width = 36;
	double robot_width = 40, robot_length = 38;
	Trajectory left, right;
	Trajectory.Segment segLeftX, segLeftY, segRightX, segRightY, segLV, segRV, segLA, segRA, segLJ, segRJ, segLP, segRP, segTime, segTraj;
	int i1, i2, i3, i4, iLV, iRV, iLA, iRA, iLJ, iRJ, iLP, iRP, iTime, iTraj;
	public double left1x,  right1x, left1y, right1y, left2x,  right2x, left2y, right2y;
	Trajectory trajectory;
	boolean backward;
	public int counter;
	public double xRobotOut1, yRobotOut1, xRobotOut2, yRobotOut2;
	public int posTraj = 1;
	Waypoint[] points;
	
	
	public TrajectorySetup() {
		
	}
	
	public void setup(int step) {
		resetCounters();
		waypointsStep(step);
		Trajectory.Config config = new Trajectory.Config(FitMethod.HERMITE_CUBIC, 10000, 0.020, 96, 180, 120);
		trajectory = Pathfinder.generate(points, config);
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
		if(step == 1) {												//STEP 1
			if(posTraj == 1) {//center left switch
				points = new Waypoint[] {
					new Waypoint(32,-(robot_width/2)+12,0),
					new Waypoint(140, 72 -(robot_width/2),0)	
				};
			}else if(posTraj == 2) {//center right switch
				points = new Waypoint[] {
					new Waypoint(32,-(robot_width/2)+12,0),
					new Waypoint(140,-72+(robot_width/2),0)
				};
			}else if(posTraj == 3) {//left side switch
				points = new Waypoint[] {
					new Waypoint(32,-(robot_width/2)+132,0),
					new Waypoint(140, 115,Pathfinder.d2r(-22.5)),
					new Waypoint(168, 72,Pathfinder.d2r(270))
				};
			}else if(posTraj == 4) {//right side switch
				points = new Waypoint[] {
					new Waypoint(32,(robot_width/2)-132,0),
					new Waypoint(140, -115,Pathfinder.d2r(22.5)),
					new Waypoint(168, -72,Pathfinder.d2r(90))
				};
			}else if(posTraj == 5) {//left side scale
				points = new Waypoint[] {
					new Waypoint(32,-(robot_width/2)+132,0),
					new Waypoint(280, 130, Pathfinder.d2r(-2.5)),
					new Waypoint(314, 90, Pathfinder.d2r(270))
				};
			}else if(posTraj == 6) {//right side scale
				points = new Waypoint[] {
					new Waypoint(32,(robot_width/2)-132,0),
					new Waypoint(280, -130, Pathfinder.d2r(2.5)),
					new Waypoint(314, -90, Pathfinder.d2r(90))
				};
			}else if(posTraj == 7) {//left side scale cross
				points = new Waypoint[] {
					new Waypoint(32,-(robot_width/2)+132,0),
					new Waypoint(185, 72 + 4 + (robot_width/2),Pathfinder.d2r(0)),
					new Waypoint(209 + 4 + (robot_width/2), 70, Pathfinder.d2r(-90))
					//new Waypoint(300, -90 + 7/*6.5*/, Pathfinder.d2r(0))
				};
			}else if(posTraj == 8) {//right side scale cross
				points = new Waypoint[] {
					new Waypoint(32,(robot_width/2)-132,0),
				};
			}
		}else if(step == 2) {										//STEP 2
			if(posTraj == 1) {//center left switch
				points = new Waypoint[] {
					new Waypoint(140, 72 -(robot_width/2),0),
					new Waypoint(45, 0, 0)
				};
			}else if(posTraj == 2) {//center right switch
				points = new Waypoint[] {
					new Waypoint(140,-72+(robot_width/2),0),
					new Waypoint(45, 0, 0)
				};
			}else if(posTraj == 3) {//left side switch
				points = new Waypoint[] {
					new Waypoint(168, 72,Pathfinder.d2r(270)),
					new Waypoint(243, 120,Pathfinder.d2r(-180 + 40))
				};
			}else if(posTraj == 4) {//right side switch
				points = new Waypoint[] {
					new Waypoint(168, -72,Pathfinder.d2r(90)),
					new Waypoint(243, -120,Pathfinder.d2r(180 - 40))
				};
			}else if(posTraj == 5) {//left side scale
				points = new Waypoint[] {
					new Waypoint(314, 90, Pathfinder.d2r(270)),
					new Waypoint(314, 120, Pathfinder.d2r(270))
				};
			}else if(posTraj == 6) {//right side scale
				points = new Waypoint[] {
					new Waypoint(314, -90, Pathfinder.d2r(90)),
					new Waypoint(314, -120, Pathfinder.d2r(90))
				};
			}else if(posTraj == 7) {	
				points = new Waypoint[] {
					new Waypoint(209 + 4 + (robot_width/2), 70, Pathfinder.d2r(-90)),
					new Waypoint(209 + 4 + (robot_width/2), -50, Pathfinder.d2r(-90)),
					new Waypoint(300, -90 + 7/*6.5*/, Pathfinder.d2r(0))
				};
			}else if(posTraj == 8) {
				points = new Waypoint[] {
						
				};
			}
		}else if(step == 3) {										//STEP 3
			if(posTraj == 1) {//center left switch
				points = new Waypoint[] {
					new Waypoint(45, 0, 0),
					new Waypoint(100,0,0)
				};
			}else if(posTraj == 2) {//center right switch
				points = new Waypoint[] {
					new Waypoint(45, 0, 0),
					new Waypoint(100,0,0)
				};
			}else if(posTraj == 3) {//left side switch
				points = new Waypoint[] {
					new Waypoint(243, 120,Pathfinder.d2r(-180 + 40)),
					new Waypoint(209, 70 ,Pathfinder.d2r(-180+45))
				};
			}else if(posTraj == 4) {//right side switch
				points = new Waypoint[] {
					new Waypoint(243, -120,Pathfinder.d2r(180 - 40)),
					new Waypoint(209, -70 ,Pathfinder.d2r(180-45))
				};
			}else if(posTraj == 5) {//left side scale
				points = new Waypoint[] {
					new Waypoint(314, 120, Pathfinder.d2r(270)),
					new Waypoint(250, 90, Pathfinder.d2r(225)),
					new Waypoint(209, 72-6.5,Pathfinder.d2r(180))
				};
			}else if(posTraj == 6) {//right side scale
				points = new Waypoint[] {
					new Waypoint(314, -120, Pathfinder.d2r(90)),
					new Waypoint(250, -90, Pathfinder.d2r(135)),
					new Waypoint(209, -72+6.5,Pathfinder.d2r(180))
				};
			}else if(posTraj == 7) {
				points = new Waypoint[] {
					
				};
			}
		}else if(step == 4) {										//STEP 4
			if(posTraj == 1) {//center left switch
				points = new Waypoint[] {
					new Waypoint(100,0,0),
					new Waypoint(45, 0, 0)
				};
			}else if(posTraj == 2) {//center right switch
				points = new Waypoint[] {
					new Waypoint(100,0,0),
					new Waypoint(45, 0, 0)
				};
			}else if(posTraj == 3) {//left side switch
				points = new Waypoint[] {
					new Waypoint(209, 70 ,Pathfinder.d2r(-180+45)),
					new Waypoint(243, 120,Pathfinder.d2r(-180 + 40))
				};
			}else if(posTraj == 4) {//right side switch
				points = new Waypoint[] {
					new Waypoint(209, -70 ,Pathfinder.d2r(180-45)),
					new Waypoint(243, -120,Pathfinder.d2r(180 - 40))
				};
			}else if(posTraj == 5) {//left side scale
				points = new Waypoint[] {
					new Waypoint(209, 72-6.5,Pathfinder.d2r(180)),
					new Waypoint(250, 90, Pathfinder.d2r(225)),
					new Waypoint(314, 120, Pathfinder.d2r(270))
				};
			}else if(posTraj == 6) {//right side scale
				points = new Waypoint[] {
					new Waypoint(209, -72+6.5,Pathfinder.d2r(180)),
					new Waypoint(250, -90, Pathfinder.d2r(135)),
					new Waypoint(314, -120, Pathfinder.d2r(90))
				};
			}else if(posTraj == 7) {
				points = new Waypoint[] {
					
				};
			}
		}else if(step == 5) {										//STEP 5
			if(posTraj == 1) {//center left switch
				points = new Waypoint[] {
					new Waypoint(45, 0, 0),
					new Waypoint(140, 72 -(wheelBase_width/2),0)
				};
			}else if(posTraj == 2) {//center right switch
				points = new Waypoint[] {
					new Waypoint(45, 0, 0),
					new Waypoint(140,-72+(wheelBase_width/2),0)
				};
			}else if(posTraj == 3) {//left side switch
				points = new Waypoint[] {
					new Waypoint(243, 120,Pathfinder.d2r(-180 + 40)),
					new Waypoint(168, 72,Pathfinder.d2r(270))
				};
			}else if(posTraj == 4) {//right side switch
				points = new Waypoint[] {
					new Waypoint(243, -120,Pathfinder.d2r(180 - 40)),
					new Waypoint(168, -72,Pathfinder.d2r(90))
				};
			}else if(posTraj == 5) {//left side scale
				points = new Waypoint[] {
					new Waypoint(314, 120, Pathfinder.d2r(270)),
					new Waypoint(314, 90, Pathfinder.d2r(270))
				};
			}else if(posTraj == 6) {//right side scale
				points = new Waypoint[] {
					new Waypoint(314, -120, Pathfinder.d2r(90)),
					new Waypoint(314, -90, Pathfinder.d2r(90))
				};
			}else if(posTraj == 7) {
				points = new Waypoint[] {
					
				};
			}
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
		double angle = segTraj.heading;
		while(angle > Math.PI) {
			angle -= 2 * Math.PI;
		}
		while(angle < -Math.PI) {
			angle += 2 * Math.PI;
		}
		if(backward) {
			if(ly == ry) {
				if(lx > rx) {
					left2x = lx - (robot_width - wheelBase_width)/2;
					right2x = rx + (robot_width - wheelBase_width)/2;
					left2y = ly;
					right2y = ry;
					left1x = left2x;
					right1x = right2x;
					left1y = ly + (robot_length);
					right1y = ry + (robot_length);
				}else {
					left2x = lx + (robot_width - wheelBase_width)/2;
					right2x = rx - (robot_width - wheelBase_width)/2;
					left2y = ly;
					right2y = ry;
					left1x = left2x;
					right1x = right2x;
					left1y = ly - (robot_length);
					right1y = ry - (robot_length);
				}
			}else if(lx == rx) {
				System.out.println("Hello");
				if(ly > ry) {
					left1x = lx - (robot_length);
					right1x = rx - (robot_length);
					left1y = ly - (robot_width - wheelBase_width)/2;
					right1y = ry + (robot_width - wheelBase_width)/2;
					left2x = lx;
					right2x = rx;
					left2y = ly - (robot_width - wheelBase_width)/2;
					right2y = ry + (robot_width - wheelBase_width)/2;
				}else {
					left1x = lx + (robot_length);
					right1x = rx + (robot_length);
					left1y = ly + (robot_width - wheelBase_width)/2;
					right1y = ry - (robot_width - wheelBase_width)/2;
					left2x = lx;
					right2x = rx;
					left2y = ly + (robot_width - wheelBase_width)/2;
					right2y = ry - (robot_width - wheelBase_width)/2;
				}
			}else {
				if(ly > ry) {
					if(lx > rx) { //angle > about 1.57 and < about 3.14
						double usableAngle = -angle;
						left2x = lx - (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2x = rx + (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						left2y = ly - (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2y = ry + (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						left1x = left2x + (Math.cos(usableAngle) * robot_length);
						right1x = right2x + (Math.cos(usableAngle) * robot_length);
						left1y = left2y - (Math.sin(usableAngle) * robot_length);
						right1y = right2y - (Math.sin(usableAngle) * robot_length);
					}else{
						//angle > -3.14 and < about -1.57
						double usableAngle = angle;
						left2x = lx + (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2x = rx - (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						left2y = ly - (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2y = ry + (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						left1x = left2x + (Math.cos(usableAngle) * robot_length);
						right1x = right2x + (Math.cos(usableAngle) * robot_length);
						left1y = left2y + (Math.sin(usableAngle) * robot_length);
						right1y = right2y + (Math.sin(usableAngle) * robot_length);
						
					}
				}else {
					if(lx > rx) {
						//angle > 0 and < about 1.57	
						double usableAngle = -(angle - Math.PI);
						left2x = lx + (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2x = rx - (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						left2y = ly - (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2y = ry + (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						left1x = left2x - (Math.cos(usableAngle) * robot_length);
						right1x = right2x - (Math.cos(usableAngle) * robot_length);
						left1y = left2y + (Math.sin(usableAngle) * robot_length);
						right1y = right2y + (Math.sin(usableAngle) * robot_length);
						
					}else{
						//angle > about -1.57 and < 0
						double usableAngle = Math.PI - angle;
						left2x = lx + (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2x = rx - (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						left2y = ly + (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2y = ry - (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						left1x = left2x - (Math.cos(usableAngle) * robot_length);
						right1x = right2x - (Math.cos(usableAngle) * robot_length);
						left1y = left2y + (Math.sin(usableAngle) * robot_length);
						right1y = right2y + (Math.sin(usableAngle) * robot_length);
						
					}
				}
			}
		}else {
			if(ly == ry) {
				if(lx > rx) {
					left1x = lx + (robot_width - wheelBase_width)/2;
					right1x = rx - (robot_width - wheelBase_width)/2;
					left1y = ly + (robot_length);
					right1y = ry + (robot_length);
					left2x = lx + (robot_width - wheelBase_width)/2;
					right2x = rx - (robot_width - wheelBase_width)/2;
					left2y = ly;
					right2y = ry;
				}else {
					left1x = lx - (robot_width - wheelBase_width)/2;
					right1x = rx + (robot_width - wheelBase_width)/2;
					left1y = ly - (robot_length);
					right1y = ry - (robot_length);
					left2x = lx - (robot_width - wheelBase_width)/2;
					right2x = rx + (robot_width - wheelBase_width)/2;
					left2y = ly;
					right2y = ry;
				}
			}else if(lx == rx) {
				if(ly > ry) {
					left1x = lx - (robot_length);
					right1x = rx - (robot_length);
					left1y = ly + (robot_width - wheelBase_width)/2;
					right1y = ry - (robot_width - wheelBase_width)/2;
					left2x = lx;
					right2x = rx;
					left2y = ly + (robot_width - wheelBase_width)/2;
					right2y = ry - (robot_width - wheelBase_width)/2;
				}else {
					left1x = lx + (robot_length);
					right1x = rx + (robot_length);
					left1y = ly - (robot_width - wheelBase_width)/2;
					right1y = ry + (robot_width - wheelBase_width)/2;
					left2x = lx;
					right2x = rx;
					left2y = ly - (robot_width - wheelBase_width)/2;
					right2y = ry + (robot_width - wheelBase_width)/2;
				}
			}else {
				if(ly > ry) {
					if(lx > rx) { 
						//angle > about -1.57 and < 0
						double usableAngle = -angle;
						left2x = lx + (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2x = rx - (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						left2y = ly + (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2y = ry - (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						left1x = left2x - (Math.cos(usableAngle) * robot_length);
						right1x = right2x - (Math.cos(usableAngle) * robot_length);
						left1y = left2y + (Math.sin(usableAngle) * robot_length);
						right1y = right2y + (Math.sin(usableAngle) * robot_length);
					}else{
						//angle > 0 and < about 1.57
						double usableAngle = angle;
						left2x = lx - (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2x = rx + (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						left2y = ly + (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2y = ry - (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						left1x = left2x - (Math.cos(usableAngle) * robot_length);
						right1x = right2x - (Math.cos(usableAngle) * robot_length);
						left1y = left2y - (Math.sin(usableAngle) * robot_length);
						right1y = right2y - (Math.sin(usableAngle) * robot_length);
					}
				}else {
					if(lx > rx) {
						//angle > about -3.14 and < about -1.57	
						double usableAngle = -(angle - Math.PI);
						left2x = lx + (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2x = rx - (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						left2y = ly - (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2y = ry + (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						left1x = left2x + (Math.cos(usableAngle) * robot_length);
						right1x = right2x + (Math.cos(usableAngle) * robot_length);
						left1y = left2y - (Math.sin(usableAngle) * robot_length);
						right1y = right2y - (Math.sin(usableAngle) * robot_length);
					}else{
						//angle > about 1.57 and < about 3.14
						double usableAngle = Math.PI - angle;
						left2x = lx - (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2x = rx + (Math.cos(usableAngle) * ((robot_width - wheelBase_width)/2));
						left2y = ly - (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						right2y = ry + (Math.sin(usableAngle) * ((robot_width - wheelBase_width)/2));
						left1x = left2x + (Math.cos(usableAngle) * robot_length);
						right1x = right2x + (Math.cos(usableAngle) * robot_length);
						left1y = left2y - (Math.sin(usableAngle) * robot_length);
						right1y = right2y - (Math.sin(usableAngle) * robot_length);
					}
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

