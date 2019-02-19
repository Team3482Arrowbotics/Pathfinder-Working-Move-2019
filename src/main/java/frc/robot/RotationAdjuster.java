package frc.robot;

import edu.wpi.first.wpilibj.PIDOutput;

public class RotationAdjuster implements PIDOutput{
	double adjustment = 0;
	@Override
	public void pidWrite(double output) {
		adjustment = output;
	}

}