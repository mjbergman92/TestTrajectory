package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.util.PaintAlpha;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

public class GraphTrajectory {

	static double counter, timePassed;
	static double originalTime;
	static boolean run = true;
	static boolean disabled = false;
	static int step = 1;
	static boolean paused = false;
	static int traj = 1;
	static char[] trajPossible = {'1','2','3','4','5','6','7','8'};
	static boolean setup = false;
	static int threadI = 0;
	static boolean firstTime = true;
	
	public static void main(String args[]) {
		
		
		
		TrajectorySetup trajectorySetup = new TrajectorySetup();
		
		//create and configure the window
		JFrame window = new JFrame();
		window.setTitle("Trajectory GUI");
		window.setSize(1450,1000);
		window.isResizable();
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		
		JButton button = new JButton("End");
		JPanel Lpanel = new JPanel();
		Lpanel.setLayout(new BorderLayout());
		JPanel Rpanel = new JPanel();
		Rpanel.setLayout(new BorderLayout());
		window.add(button,BorderLayout.NORTH);
		
		//create xy line graph for x and y coordinates
		XYSeries leftSeries = new XYSeries("Left Side Robot");
		XYSeries rightSeries = new XYSeries("Right Side Robot");
		XYSeries lineAcross = new XYSeries("Line Across");
		XYSeries robot = new XYSeries("Robot");
		XYSeriesCollection dataset = new XYSeriesCollection(leftSeries);
		dataset.addSeries(rightSeries);
		dataset.addSeries(lineAcross);
		dataset.addSeries(robot);
		dataset.setAutoWidth(false);
		JFreeChart chart = ChartFactory.createXYLineChart("Pathfinder Trajectory", "Distance X (Inches)", "Distance Y (Inches)", dataset);
		chart.getXYPlot().setBackgroundImage(new ImageIcon("Pic.png").getImage());
		
		NumberAxis numberRangeAxis = (NumberAxis)chart.getXYPlot().getRangeAxis();
		numberRangeAxis.setRange(-161.69, 161.69);
		NumberAxis numberDomainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
		numberDomainAxis.setRange(0, 648);
		
		Lpanel.add(new ChartPanel(chart),BorderLayout.NORTH);
		
		//create xy line graph for velocity
		XYSeries leftVSeries = new XYSeries("Left Velocity");
		XYSeries rightVSeries = new XYSeries("Right Velocity");
		XYSeries leftASeries = new XYSeries("Left Acceleration");
		XYSeries rightASeries = new XYSeries("Right Acceleration");
		XYSeries leftJSeries = new XYSeries("Left Jerk");
		XYSeries rightJSeries = new XYSeries("Right Jerk");
		XYSeriesCollection VAJdataset = new XYSeriesCollection(leftVSeries);
		VAJdataset.addSeries(rightVSeries);
		VAJdataset.addSeries(leftASeries);
		VAJdataset.addSeries(rightASeries);
		VAJdataset.addSeries(leftJSeries);
		VAJdataset.addSeries(rightJSeries);
		JFreeChart VAJchart = ChartFactory.createXYLineChart("Velocity, Acceleration, and Jerk", "Time (Seconds)", "Distance/Second (Inches/Second), Distance/Second/Second (Inches/Second^2), AND Distance/Second/Second/Second (Inches/Second^3)", VAJdataset);
		Rpanel.add(new ChartPanel(VAJchart),BorderLayout.NORTH);
		
		//create xy line graph for position/distance
		XYSeries leftDSeries = new XYSeries("Left Distance");
		XYSeriesCollection dataLset = new XYSeriesCollection(leftVSeries);
		dataLset.addSeries(leftDSeries);
		JFreeChart Lchart = ChartFactory.createXYLineChart("Left Side Distance and Velocity", "Time (Seconds)", "Distance (Inches) AND Distance/Second (Inches/Second", dataLset);
		ChartPanel LCPanel = new ChartPanel(Lchart);
		LCPanel.getChart().setBackgroundPaint(Color.RED);
		Lpanel.add(LCPanel,BorderLayout.SOUTH);
		
		XYSeries rightDSeries = new XYSeries("Left Distance");
		XYSeriesCollection dataRset = new XYSeriesCollection(rightVSeries);
		dataRset.addSeries(rightDSeries);
		JFreeChart Rchart = ChartFactory.createXYLineChart("Right Side Distance and Velocity", "Time (Seconds)", "Distance (Inches) AND Distance/Second (Inches/Second", dataRset);
		ChartPanel RCPanel = new ChartPanel(Rchart);
		RCPanel.getChart().setBackgroundPaint(Color.BLUE);
		Rpanel.add(new ChartPanel(Rchart),BorderLayout.SOUTH);
		
		window.add(Lpanel, BorderLayout.WEST);
		window.add(Rpanel, BorderLayout.EAST);
		
		window.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == 10) {
					
					if(paused == true) {
						
						paused = false;
						
					}else {
						
						paused = true;
						
					}
					
				}else if(e.getKeyChar() == 'r') {
					
					button.doClick();
					
				}else if(e.getKeyChar() == '1') {
					
					traj = 1;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '2') {
					
					traj = 2;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '3') {
					
					traj = 3;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '4') {
					
					traj = 4;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '5') {
					
					traj = 5;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '6') {
					
					traj = 6;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '7') {
					
				}else if(e.getKeyChar() == '8') {
					
				}
				
				System.out.println(setup);
				
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			
			
		});
		
		button.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == 10) {
					
					if(paused == true) {
						
						paused = false;
						
					}else {
						
						paused = true;
						
					}
					
				}else if(e.getKeyChar() == 'r') {
					
					button.doClick();
					
				}else if(e.getKeyChar() == '1') {
					
					traj = 1;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '2') {
					
					traj = 2;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '3') {
					
					traj = 3;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '4') {
					
					traj = 4;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '5') {
					
					traj = 5;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '6') {
					
					traj = 6;
					setup = true;
					firstTime = true;
					
				}else if(e.getKeyChar() == '7') {
					
				}else if(e.getKeyChar() == '8') {
					
				}
				
				System.out.println(e.getKeyCode());
				System.out.println(setup);
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			
			
		});
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(button.getText().equals("Load")) {
					
					originalTime = System.currentTimeMillis();
					button.setText("End");
					run = true;
					disabled = false;
					counter = 0;
					Thread thread = new Thread() {
						@Override public void run() {
							while(run) {
								if(!paused) {
									if(((System.currentTimeMillis()-originalTime)/1000) >= counter*.020 + .020) {
										try {
											switch(step) {
											case 1:
												if(trajectorySetup.setupisFinished()) {
													step++;
													trajectorySetup.setup(2);
													leftSeries.clear();
													rightSeries.clear();
												}
												break;
											case 2:
												if(trajectorySetup.setupisFinished()) {
													step++;
													trajectorySetup.setup(3);
													leftSeries.clear();
													rightSeries.clear();
												}
												break;
											case 3:
												if(trajectorySetup.setupisFinished()) {
													step++;
													trajectorySetup.setup(4);
													leftSeries.clear();
													rightSeries.clear();
												}
												break;
											case 4:
												if(trajectorySetup.setupisFinished()) {
													step++;
													trajectorySetup.setup(5);
													leftSeries.clear();
													rightSeries.clear();
												}
												break;
											default:
												break;
											}
											timePassed = trajectorySetup.timePassed();
											lineAcross.clear();
											double leftX = trajectorySetup.leftXTrajectory(), leftY = trajectorySetup.leftYTrajectory(), rightX = trajectorySetup.rightXTrajectory(), rightY = trajectorySetup.rightYTrajectory();
											leftSeries.add(leftX, leftY);
											rightSeries.add(rightX, rightY);
											trajectorySetup.robotBox(leftX,leftY,rightX,rightY);
											trajectorySetup.counter = 0;
											robot.clear();
											for(int i = 0; i <= 24; i++){
												trajectorySetup.drawRobot();
												robot.add(trajectorySetup.xRobotOut1, trajectorySetup.yRobotOut1);
												robot.add(trajectorySetup.xRobotOut2, trajectorySetup.yRobotOut2);
											}
											lineAcross.add(leftX, leftY);
											lineAcross.add(rightX, rightY);
											
											leftVSeries.add(timePassed, trajectorySetup.leftVelocity());
											rightVSeries.add(timePassed, trajectorySetup.rightVelocity());
											leftASeries.add(timePassed, trajectorySetup.leftAcceleration());
											rightASeries.add(timePassed, trajectorySetup.rightAcceleration());
											leftJSeries.add(timePassed, trajectorySetup.leftJerk());
											rightJSeries.add(timePassed, trajectorySetup.rightJerk());
											
											leftDSeries.add(timePassed, trajectorySetup.leftDistance());
											rightDSeries.add(timePassed, trajectorySetup.rightDistance());
											
										}catch(Exception e) {}
										counter++;
									}
								}
							}
						}
					};
				thread.start();
				}else if(button.getText().equals("End")){
					Thread starterThread = new Thread() {
						@Override public void run() {
							button.setText("Load");
							run = false;
							disabled = true;
							trajectorySetup.resetCounters();
							
							step = 1;
							
							while(!run) {
								if(setup && firstTime) {
									
									trajectorySetup.setup(1);
									firstTime = false;
								}
								leftSeries.clear();
								rightSeries.clear();
								robot.clear();
								lineAcross.clear();
								leftVSeries.clear();
								rightVSeries.clear();
								leftASeries.clear();
								rightASeries.clear();
								leftJSeries.clear();
								rightJSeries.clear();
								
								leftDSeries.clear();
								rightDSeries.clear();
								System.out.print("");
							}
							
						}
					};
					starterThread.start();
				}
			}
			
		});
		
		//show the window
		window.setVisible(true);
		button.doClick();
		
	}
}
