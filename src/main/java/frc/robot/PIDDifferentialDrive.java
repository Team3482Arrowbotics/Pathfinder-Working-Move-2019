package frc.robot;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class PIDDifferentialDrive extends DifferentialDrive implements PIDOutput {
	// turns if true, straight if false
	boolean turning;
	RotationAdjuster rot;

	public PIDDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor, RotationAdjuster r,
			boolean turn) {
		super(leftMotor, rightMotor);
		turning = turn;
		this.rot = r;
	}

	public PIDDifferentialDrive(SpeedController leftMotor, SpeedController rightMotor, RotationAdjuster r) {
		super(leftMotor, rightMotor);
		turning = false;
		this.rot = r;
	}

	public void pidWrite(double output) {
		if (turning) {
			arcadeDrive(0, output);
		} else {
			curvatureDrive(output, rot.adjustment, false);
		}

	}

	public boolean isTurning() {
		return turning;
	}

	public void setTurning(boolean turning) {
		this.turning = turning;
	}

	public void arcadeDrive(double xSpeed, double zRotation) {
		if(zRotation < this.m_deadband && zRotation > -m_deadband) {
			if(turning && xSpeed > m_deadband && xSpeed < -m_deadband) {
				startAdjusting();
				turning = false;
			}
			curvatureDrive(xSpeed, rot.adjustment, false);
		} else {
			if(!turning) {
				stopAdjusting();
			}
			turning = true;
			super.arcadeDrive(xSpeed, zRotation);
		}
	}

	private void stopAdjusting() {
		RobotMap.counteractDrift.disable();
		
	}

	private void startAdjusting() {
		RobotMap.counteractDrift.setSetpoint(RobotMap.navx.getYaw());
		RobotMap.counteractDrift.enable();
		
	}

}
