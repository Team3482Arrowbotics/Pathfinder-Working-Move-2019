package frc.robot.subsystems;

import java.util.ArrayList;


import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PWMLidar extends Subsystem{
	private final int channel;
	private final DigitalInput in;
	private final Counter counter;
	private int printedWarningCount = 5;
	static final int CALIBRATION_OFFSET = 0;
	private double last = 0;
	public boolean drasticChange = false;

	public PWMLidar(int channel) {
		this.channel = channel;
		in = new DigitalInput(channel);
		counter = new Counter(in);
		counter.setMaxPeriod(1.0);
		counter.setSemiPeriodMode(true);
		counter.reset();
	}

	public double getDistance() {

		double cm;
		if (counter.get() < 1) {
			if (printedWarningCount-- > 0) {
				System.out.println("Lidar waiting for distance measurement");
			}
			return 0;
		}
		// Period is in microseconds, multiply by 1 million to make numbers
		// nice, divide by 10 to get cm
		cm = (counter.getPeriod() * 1000000.0 / 10.0) + CALIBRATION_OFFSET;
		if (Math.abs(last - cm) > 100 && last != 0 && cm < 60) {
			drasticChange = true;
			// System.out.println("DRASTIC");
		} else {
			if (cm > 65)
				drasticChange = false;
		}
		last = cm;
		return cm;
    }
    
    @Override
    protected void initDefaultCommand() {

    }

}
