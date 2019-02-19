/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

/**
 * An example command.  You can replace me with your own command.
 */
public class Move extends Command {
    double distance;
  public Move(double distance) {
    // Use requires() here to declare subsystem dependencies
    requires(Robot.m_subsystem);
    this.distance = distance;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    Robot.driveEnabled = false;
    RobotMap.driveDiff.setTurning(false);
    RobotMap.encoders.reset(); 
    RobotMap.driveController.enable();
    RobotMap.driveController.setSetpoint(distance);
    //RobotMap.counteractDrift.setSetpoint(RobotMap.navx.getYaw());
    //RobotMap.counteractDrift.enable();
    }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    System.out.println( "PID Error: " + RobotMap.driveController.getError());
    return RobotMap.driveController.onTarget();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    RobotMap.counteractDrift.disable();
    RobotMap.driveController.disable();
    Robot.driveEnabled = true;
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
