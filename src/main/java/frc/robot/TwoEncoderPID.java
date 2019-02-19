package frc.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class TwoEncoderPID implements PIDSource{
	Encoder left, right;
	PIDSourceType type;
	public TwoEncoderPID(Encoder left, Encoder right) {
		this.left = left;
		this.right = right;
		type = PIDSourceType.kDisplacement;
	}
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		pidSource = type;
		
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return type;
	}

	@Override
	public double pidGet() {
		return (left.getDistance()) + (right.getDistance())/2;
	}
	
	public void reset() {
		left.reset();
		right.reset();
	}
	
}
