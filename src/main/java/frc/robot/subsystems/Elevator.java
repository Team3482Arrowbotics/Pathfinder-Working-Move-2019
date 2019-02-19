/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.subsystems;

import frc.robot.Robot;
import frc.robot.RobotMap;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;



public class Elevator extends Subsystem implements Runnable
{
  	protected static WPI_TalonSRX elevatorTalon;
  	protected static double targetPos, maxPos, minPos;
	protected static int currentStage, numStages;
	public static final int BOTTOM_POSITION = 0, TOP_POSITION = 40000, SWITCH_POSITION = 8000, SCALE_POSITION = 40000;
	public static final int THRESHOLD_HEIGHT = TOP_POSITION-4000;
	private static final String RobotMap = null;
	public static boolean locked;
 
 
  	public Elevator() {
		//elevatorTalon = RobotMap.elevTalon;
		targetPos = BOTTOM_POSITION;
		currentStage = 0;
		minPos = BOTTOM_POSITION;
		maxPos = TOP_POSITION;
		locked=true;
	}
	
	@Override
	protected void initDefaultCommand() {

	}

  
	public static double getCurrentPos() {
		return elevatorTalon.getSelectedSensorPosition(0);
	}

	public static double getTargetPos() {
		return targetPos;
	}
	
	private static void setTargetPos(double tPos) {
	
		if(tPos > maxPos) {
			tPos = maxPos;
		}
		if(tPos < minPos) {
			tPos = minPos;
		}
		targetPos = tPos;
		
  	}
  
	public void run() {
		
	}
	
	//ELevator set position
	public void set(double pos, boolean relative) {
		if(relative) {
			setTargetPos(getCurrentPos() + pos);
			return;
		}
		setTargetPos(pos);
	}
	
	public void set(double pos) {
		set(pos, false);
	}
	
	public void changePosition(double deltaPos, int axis) {
		double a = Robot.m_oi.j.getRawAxis(axis);
		set(Math.abs(a) * deltaPos, true);
	}
	
	public void lock(boolean islocked) {
		targetPos = getCurrentPos();
		locked = islocked;
	}
	
	public boolean isTop() {
		return elevatorTalon.getSelectedSensorPosition(0) >= TOP_POSITION;
	}
  
		
}
