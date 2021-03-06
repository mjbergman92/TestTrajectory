package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.JComboBox;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import jaci.pathfinder.Pathfinder;

import javax.script.*;

public class GraphTrajectory { 

	static String baseInstructions = "Enter Trajectory #. Then Press the 'r' Key", trajSet = "Trajectory ", running = " Running", pause = " Paused", pressR = " Press the 'r' Key to Run Trajectory", wait = "Please Wait";
	static double counter, timePassed; //tracking and calculating for loop and time sensitive data for the graphs
	static double originalTime; //set at the beginning when load is clicked. current time of the system in milliseconds
	static boolean run = false; //for the while loop for the "Load" and "End"
	static int step = 1; //the step of the trajectory. default step 1
	static boolean paused = false; //the boolean used to determine if the graph is paused controlled by the enter/return key
	static int traj = 0; //the trajectory choice
	static boolean setup = false;
	static boolean firstTime = true;
	static boolean wait1 = true;
	static int percentage = 0;
	static JLabel label;
	static int pastTraj = 0;
	
	public static void main(String args[]) {
		
		TrajectorySetup trajectorySetup = new TrajectorySetup();
		
		//create and configure the window
		JFrame window = new JFrame();
		window.setTitle("Trajectory GUI");
		window.setSize(1550,1000);
		window.isResizable();
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//create define the jframe for setting the new waypoints
		JFrame coordinateWindow = new JFrame();
		
		//create and configure the button and label
		JTextField textBox = new JTextField();
		JButton button = new JButton("End");
		button.setAlignmentX(145);
		label = new JLabel();
		label.setText(baseInstructions);
		label.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.add(button, BorderLayout.EAST);
		topPanel.add(textBox, BorderLayout.CENTER);
		topPanel.add(label, BorderLayout.WEST);
		window.add(topPanel,BorderLayout.NORTH);
		
		//create panels for window
		JPanel Lpanel = new JPanel();
		Lpanel.setLayout(new BorderLayout());
		JPanel Rpanel = new JPanel();
		Rpanel.setLayout(new BorderLayout());
		
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
		chart.setPadding(new RectangleInsets(0, 20, 0, 20));
		NumberAxis numberRangeAxis = (NumberAxis)chart.getXYPlot().getRangeAxis();
		numberRangeAxis.setRange(-161.69, 161.69);
		NumberAxis numberDomainAxis = (NumberAxis)chart.getXYPlot().getDomainAxis();
		numberDomainAxis.setRange(0, 648);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(875, 650));
		Lpanel.add(chartPanel,BorderLayout.NORTH);
		
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
		ChartPanel VAJPanel = new ChartPanel(VAJchart);
		VAJPanel.setPreferredSize(new Dimension(625, 400));
		Rpanel.add(VAJPanel,BorderLayout.NORTH);
		
		//create xy line graph for left position/distance
		XYSeries leftDSeries = new XYSeries("Left Distance");
		XYSeriesCollection dataLset = new XYSeriesCollection(leftVSeries);
		dataLset.addSeries(leftDSeries);
		JFreeChart Lchart = ChartFactory.createXYLineChart("Left Side Distance and Velocity", "Time (Seconds)", "Distance (Inches) AND Distance/Second (Inches/Second", dataLset);
		ChartPanel LCPanel = new ChartPanel(Lchart);
		LCPanel.getChart().setBackgroundPaint(Color.RED);
		LCPanel.setPreferredSize(new Dimension(550, 250));
		Lpanel.add(LCPanel,BorderLayout.SOUTH);
		
		//create xy line graph for right position/distance
		XYSeries rightDSeries = new XYSeries("Right Distance");
		XYSeriesCollection dataRset = new XYSeriesCollection(rightVSeries);
		dataRset.addSeries(rightDSeries);
		JFreeChart Rchart = ChartFactory.createXYLineChart("Right Side Distance and Velocity", "Time (Seconds)", "Distance (Inches) AND Distance/Second (Inches/Second", dataRset);
		ChartPanel RCPanel = new ChartPanel(Rchart);
		RCPanel.getChart().setBackgroundPaint(Color.BLUE);
		RCPanel.setPreferredSize(new Dimension(550, 250));
		Rpanel.add(RCPanel,BorderLayout.SOUTH);
		
		//add panels to window
		window.add(Lpanel, BorderLayout.WEST);
		window.add(Rpanel, BorderLayout.EAST);
		
		textBox.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode() == 10) { //enter/return key 
					
					if(traj == Integer.parseInt(textBox.getText())) {
					
						if(paused == true) {
							
							paused = false;
							label.setText(trajSet + traj + running);
							
						}else {
							
							paused = true;
							label.setText(trajSet + traj + pause);
							
						}
						
					}else {
						
						traj = Integer.parseInt(textBox.getText());
						setup = true;
						firstTime = true;
						label.setText(trajSet + traj + "." + pressR);
						label.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
						
					}
					
				}else if(!paused) {
					
					if(e.getKeyChar() == 'r') {
						
						String text = textBox.getText();
						String newText = text.trim();
						String bestText = newText.substring(0, text.length());
						textBox.setText(bestText);
						
						if(setup) {
							
							if(wait1) {
								
								label.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
								label.setText(trajSet + traj + ". Please Wait");
								
							}else {
								if(button.getText() == "Load") {
									
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
									trajectorySetup.iTime = 0;
									
									button.doClick();
									label.setText(trajSet + traj + running);
									label.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
									
								}else {
									
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
									trajectorySetup.iTime = 0;
									
									button.doClick();
									label.setText(trajSet + traj + "." + pressR + " OR Select a Different Trajectory.");
									label.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
									firstTime = true;
									
	
								}
							}
						}else {
							
							label.setText(baseInstructions);
							label.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
							
						}
						
					}else if((((e.getKeyChar() != '1' && e.getKeyChar() != '2') && (e.getKeyChar() != '3' && e.getKeyChar() != '4')) && ((e.getKeyChar() != '5' && e.getKeyChar() != '6') && (e.getKeyChar() != '7' && e.getKeyChar() != '8'))) && (e.getKeyChar() != '9' && e.getKeyChar() != '0')){
						
						if(!setup) {
							
							label.setText(baseInstructions);
							label.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
							
						}else {
							
						}
					}
				}
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
					
					button.setText("End");
					
					run = true;
					counter = 0;
					originalTime = System.currentTimeMillis();
					
					Thread thread = new Thread() {
						@Override public void run() {
							while(run) {
								
								if(!paused) {
									
									if(((System.currentTimeMillis() - originalTime)/1000) >= counter*trajectorySetup.robotLoopTime + trajectorySetup.robotLoopTime) {
										
										try {
											switch(step) {
											case 1:
												if(trajectorySetup.setupisFinished()) {
													step++;
													trajectorySetup.setup(2, false);
													originalTime = System.currentTimeMillis() - (((counter * trajectorySetup.robotLoopTime) + trajectorySetup.robotLoopTime) * 1000);	
													leftSeries.clear();
													rightSeries.clear();
												}
												break;
											case 2:
												if(trajectorySetup.setupisFinished()) {
													step++;
													trajectorySetup.setup(3, false);
													originalTime = System.currentTimeMillis() - (((counter * trajectorySetup.robotLoopTime) + trajectorySetup.robotLoopTime) * 1000);	
													leftSeries.clear();
													rightSeries.clear();
												}
												break;
											case 3:
												if(trajectorySetup.setupisFinished()) {
													step++;
													trajectorySetup.setup(4, false);
													originalTime = System.currentTimeMillis() - (((counter * trajectorySetup.robotLoopTime) + trajectorySetup.robotLoopTime) * 1000);	
													leftSeries.clear();
													rightSeries.clear();
												}
												break;
											case 4:
												if(trajectorySetup.setupisFinished()) {
													step++;
													trajectorySetup.setup(5, false);
													originalTime = System.currentTimeMillis() - (((counter * trajectorySetup.robotLoopTime) + trajectorySetup.robotLoopTime) * 1000);	
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
							trajectorySetup.resetCounters();
							
							step = 1;
							wait1 = true;
							
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
							trajectorySetup.iTime = 0;
							
							while(!run) {
								if(setup && firstTime) {
									percentage = 0;
									if(traj != pastTraj) {
									
										trajectorySetup.setup(2, true);
										loadedMorePercentage();
										trajectorySetup.setup(3, true);
										loadedMorePercentage();
										trajectorySetup.setup(4, true);
										loadedMorePercentage();
										trajectorySetup.setup(5, true); 
										loadedMorePercentage();
										trajectorySetup.setup(1, true);
										pastTraj = traj;
										
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
									trajectorySetup.iTime = 0;
									
									trajectorySetup.setup(1, false);
									label.setText(trajSet + traj + ". " + "Ready." + pressR);
									wait1 = false;
									firstTime = false;
									System.out.print(" ");
								}
								
								System.out.print("");
								
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
							trajectorySetup.iTime = 0;
							
							
							while(!trajectorySetup.checkDone) {
								
							}
							
							System.out.print("");
								
						}
					};
					starterThread.start();
				}
			}
			
		});
		
		chartPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
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
				trajectorySetup.iTime = 0;
				
				if(button.getText() != "Load"){
					
					button.doClick();
					label.setText(trajSet + traj + "." + pressR + " OR Select a Different Trajectory.");
					label.setFont(new Font(Font.DIALOG, Font.PLAIN, 25));
					//firstTime = true;
					

				}
				
				Point2D p = chartPanel.translateScreenToJava2D(new ChartMouseEvent(chart, e, null).getTrigger().getPoint());
				Rectangle2D plotArea = chartPanel.getScreenDataArea();
				XYPlot plot = (XYPlot) chart.getPlot(); // your plot
				double chartX = plot.getDomainAxis().java2DToValue(p.getX(), plotArea, plot.getDomainAxisEdge());
				double chartY = plot.getRangeAxis().java2DToValue(p.getY(), plotArea, plot.getRangeAxisEdge());
				coordinateWindow.dispose();
				coordinateWindow.setTitle("Change Waypoint");
				coordinateWindow.setSize(700,400);
				coordinateWindow.isResizable();
				coordinateWindow.setLayout(new BorderLayout());
				
				JPanel fieldLabels = new JPanel();
				fieldLabels.setLayout(null);
				fieldLabels.setPreferredSize(new Dimension(350, 400));
				JPanel fieldEntries = new JPanel();
				fieldEntries.setLayout(null);
				fieldEntries.setPreferredSize(new Dimension(350, 400));
				
				JLabel trajStepLabel = new JLabel("Trajectory | Step");
				trajStepLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				JLabel waypointNumberLabel = new JLabel("Waypoint #");
				waypointNumberLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				JLabel xLabel = new JLabel("X Coordinate");
				xLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				JLabel yLabel = new JLabel("Y Coordinate");
				yLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				JLabel headingLabel = new JLabel("Heading");
				headingLabel.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				
				fieldLabels.add(trajStepLabel);
				fieldLabels.add(waypointNumberLabel);
				fieldLabels.add(xLabel);
				fieldLabels.add(yLabel);
				fieldLabels.add(headingLabel);
				
				String trajs[] = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
				String steps[] = {"1", "2", "3", "4", "5"};
				String waypoints[] = {"1", "2", "3"};
				
				JComboBox trajBox = new JComboBox(trajs);
				JComboBox stepBox = new JComboBox(steps);
				trajBox.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				stepBox.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				JComboBox waypointNumberBox = new JComboBox(waypoints);
				waypointNumberBox.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				JTextField xCoordinate = new JTextField(chartX + "");
				xCoordinate.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				JTextField yCoordinate = new JTextField(chartY + "");
				yCoordinate.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				JTextField heading = new JTextField(0.0 + "");
				heading.setFont(new Font(Font.DIALOG, Font.PLAIN, 30));
				
				fieldEntries.add(trajBox);
				fieldEntries.add(stepBox);
				fieldEntries.add(waypointNumberBox);
				fieldEntries.add(xCoordinate);
				fieldEntries.add(yCoordinate);
				fieldEntries.add(heading);
				
				Insets labelInsets = fieldLabels.getInsets();
				Insets entriesInsets = fieldEntries.getInsets();
				
				Dimension size = trajStepLabel.getPreferredSize();
				trajStepLabel.setBounds(10 + labelInsets.left, 10 + labelInsets.top, size.width, size.height);
				size = waypointNumberLabel.getPreferredSize();
				waypointNumberLabel.setBounds(10 + labelInsets.left, 55 + labelInsets.top, size.width, size.height);
				size = xLabel.getPreferredSize();
				xLabel.setBounds(10 + labelInsets.left, 100 + labelInsets.top, size.width, size.height);
				size = yLabel.getPreferredSize();
				yLabel.setBounds(10 + labelInsets.left, 145 + labelInsets.top, size.width, size.height);
				size = headingLabel.getPreferredSize();
				headingLabel.setBounds(10 + labelInsets.left, 190 + labelInsets.top, size.width, size.height);
				
				trajBox.setBounds(entriesInsets.right, 10 + entriesInsets.top, 175, size.height);
				stepBox.setBounds(175 + entriesInsets.right, 10 + entriesInsets.top, 175, size.height);
				waypointNumberBox.setBounds(entriesInsets.right, 55 + entriesInsets.top, 350, size.height);
				xCoordinate.setBounds(entriesInsets.right, 100 + entriesInsets.top, 350, size.height);
				yCoordinate.setBounds(entriesInsets.right, 145 + entriesInsets.top, 350, size.height);
				heading.setBounds(entriesInsets.right, 190 + entriesInsets.top, 350, size.height);

				coordinateWindow.add(fieldLabels, BorderLayout.WEST);
				coordinateWindow.add(fieldEntries, BorderLayout.EAST);
				
				coordinateWindow.setLocation(window.getLocation().x + 675, window.getLocation().y + 55);
				
				heading.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						
						if(e.getKeyCode() == 10) {
							
							System.out.println("Hello Hello");
									
							Scanner scanner;
							String lines[] = {"", "", ""};
							try {
								int i = 0;
								scanner = new Scanner(new File("trajectory" + trajBox.getSelectedItem() + "/points" + "/trajectory" + trajBox.getSelectedItem() + "step" + stepBox.getSelectedItem() + "points.csv"));
								while(scanner.hasNextLine()){
						
									lines[i] = scanner.nextLine();
									i++;
									
								}
								scanner.close();
								
							} catch (FileNotFoundException e1) {
								
								e1.printStackTrace();
								
							}
							double parsedHeading = Double.parseDouble(heading.getText());
							lines[Integer.parseInt((String) waypointNumberBox.getSelectedItem()) - 1] = xCoordinate.getText() + "," + yCoordinate.getText() + "," + Pathfinder.d2r(parsedHeading);
							
							new File("trajectory" + trajBox.getSelectedItem() + "/points" + "/trajectory" + trajBox.getSelectedItem() + "step" + stepBox.getSelectedItem() + "points.csv").delete();
							
							FileWriter fw = null;
							try {
								fw = new FileWriter("trajectory" + trajBox.getSelectedItem() + "/points" + "/trajectory" + trajBox.getSelectedItem() + "step" + stepBox.getSelectedItem() + "points.csv");
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							BufferedWriter bw = new BufferedWriter(fw);
							PrintWriter pw = new PrintWriter(bw);
							
							for(String s : lines) {
									
								pw.println(s);
								
							}
							
							pw.flush();
							pw.close();
							
							leftSeries.clear();
							rightSeries.clear();
							traj = Integer.parseInt(trajBox.getSelectedItem().toString());
							trajectorySetup.setup(Integer.parseInt(stepBox.getSelectedItem().toString()), false);
							while(!trajectorySetup.setupisFinished()) {
								
								double leftX = trajectorySetup.leftXTrajectory(), leftY = trajectorySetup.leftYTrajectory(), rightX = trajectorySetup.rightXTrajectory(), rightY = trajectorySetup.rightYTrajectory();
								leftSeries.add(leftX, leftY);
								rightSeries.add(rightX, rightY);
								
							}
							
						}
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					
					
				});
				
				trajBox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						leftSeries.clear();
						rightSeries.clear();
						traj = Integer.parseInt(trajBox.getSelectedItem().toString());
						trajectorySetup.setup(Integer.parseInt(stepBox.getSelectedItem().toString()), false);
						while(!trajectorySetup.setupisFinished()) {
							
							double leftX = trajectorySetup.leftXTrajectory(), leftY = trajectorySetup.leftYTrajectory(), rightX = trajectorySetup.rightXTrajectory(), rightY = trajectorySetup.rightYTrajectory();
							leftSeries.add(leftX, leftY);
							rightSeries.add(rightX, rightY);
							
						}
						
						Scanner scanner;
						String lines[] = {"", "", ""};
						try {
							int i = 0;
							scanner = new Scanner(new File("trajectory" + trajBox.getSelectedItem() + "/points" + "/trajectory" + trajBox.getSelectedItem() + "step" + stepBox.getSelectedItem() + "points.csv"));
							while(scanner.hasNextLine()){
					
								lines[i] = scanner.nextLine();
								i++;
								
							}
							scanner.close();
							String values[] = lines[Integer.parseInt(waypointNumberBox.getSelectedItem().toString()) - 1].split(",");
							xCoordinate.setText(values[0]);
							yCoordinate.setText(values[1]);
							heading.setText((Double.parseDouble(values[2]) / (2 * Math.PI)) * 360 + "");
							
						} catch (FileNotFoundException e1) {
							
							e1.printStackTrace();
							
						}
						
						
					}
					
				});
				
				stepBox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						leftSeries.clear();
						rightSeries.clear();
						traj = Integer.parseInt(trajBox.getSelectedItem().toString());
						trajectorySetup.setup(Integer.parseInt(stepBox.getSelectedItem().toString()), false);
						while(!trajectorySetup.setupisFinished()) {
							
							double leftX = trajectorySetup.leftXTrajectory(), leftY = trajectorySetup.leftYTrajectory(), rightX = trajectorySetup.rightXTrajectory(), rightY = trajectorySetup.rightYTrajectory();
							leftSeries.add(leftX, leftY);
							rightSeries.add(rightX, rightY);
							
						}
						
						Scanner scanner;
						String lines[] = {"", "", ""};
						try {
							int i = 0;
							scanner = new Scanner(new File("trajectory" + trajBox.getSelectedItem() + "/points" + "/trajectory" + trajBox.getSelectedItem() + "step" + stepBox.getSelectedItem() + "points.csv"));
							while(scanner.hasNextLine()){
					
								lines[i] = scanner.nextLine();
								i++;
								
							}
							scanner.close();
							String values[] = lines[Integer.parseInt(waypointNumberBox.getSelectedItem().toString()) - 1].split(",");
							xCoordinate.setText(values[0]);
							yCoordinate.setText(values[1]);
							heading.setText((Double.parseDouble(values[2]) / (2 * Math.PI)) * 360 + "");
							
						} catch (FileNotFoundException e1) {
							
							e1.printStackTrace();
							
						}
						
					}
					
				});
				
				waypointNumberBox.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						
						leftSeries.clear();
						rightSeries.clear();
						traj = Integer.parseInt(trajBox.getSelectedItem().toString());
						trajectorySetup.setup(Integer.parseInt(stepBox.getSelectedItem().toString()), false);
						while(!trajectorySetup.setupisFinished()) {
							
							double leftX = trajectorySetup.leftXTrajectory(), leftY = trajectorySetup.leftYTrajectory(), rightX = trajectorySetup.rightXTrajectory(), rightY = trajectorySetup.rightYTrajectory();
							leftSeries.add(leftX, leftY);
							rightSeries.add(rightX, rightY);
							
						}
						
						Scanner scanner;
						String lines[] = {"", "", ""};
						try {
							int i = 0;
							scanner = new Scanner(new File("trajectory" + trajBox.getSelectedItem() + "/points" + "/trajectory" + trajBox.getSelectedItem() + "step" + stepBox.getSelectedItem() + "points.csv"));
							while(scanner.hasNextLine()){
					
								lines[i] = scanner.nextLine();
								i++;
								
							}
							scanner.close();
							String values[] = lines[Integer.parseInt(waypointNumberBox.getSelectedItem().toString()) - 1].split(",");
							xCoordinate.setText(values[0]);
							yCoordinate.setText(values[1]);
							heading.setText((Double.parseDouble(values[2]) / (2 * Math.PI)) * 360 + "");
							
						} catch (FileNotFoundException e1) {
							
							e1.printStackTrace();
							
						}
						
					}
					
				});
				
				xCoordinate.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						
						if(e.getKeyCode() == 10) {
							
							ScriptEngineManager mgr = new ScriptEngineManager();
						    ScriptEngine engine = mgr.getEngineByName("JavaScript");
						    try {
								xCoordinate.setText(engine.eval(xCoordinate.getText()) + "");
							} catch (ScriptException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					
					
				});
				
				yCoordinate.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyPressed(KeyEvent e) {
						
						if(e.getKeyCode() == 10) {
							
							ScriptEngineManager mgr = new ScriptEngineManager();
						    ScriptEngine engine = mgr.getEngineByName("JavaScript");
						    try {
								yCoordinate.setText(engine.eval(yCoordinate.getText()) + "");
							} catch (ScriptException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						
					}

					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					
					
				});
				
				coordinateWindow.setVisible(true);
				
				
			
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		
		//show the window
		window.setVisible(true);
		button.doClick();
		
	}
	
	public static void loadedMorePercentage() {
		
		if(!run) {
			percentage += 5;
			label.setText(trajSet + traj + ". " + percentage + "%");
		}
		
	}
	
}
