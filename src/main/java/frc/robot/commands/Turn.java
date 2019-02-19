/*package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class Turn extends Command{
	double angle;
	public Turn(double angle) {
		this.angle = angle;
	}
	protected void initialize() {
		RobotMap.driveDiff.setTurning(true);
//		RobotMap.navx.reset();
		RobotMap.navx.zeroYaw();
		Robot.driveEnabled = false;
		RobotMap.rotationController.enable();
		RobotMap.rotationController.setSetpoint(angle);
	}
	protected void end() {
		RobotMap.rotationController.disable();
	}
	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		System.out.println( "PID Error: " + RobotMap.rotationController.getError());
		System.out.println( "PID Speed: " + RobotMap.rotationController.get());
		return RobotMap.rotationController.onTarget();
	}
}*/
