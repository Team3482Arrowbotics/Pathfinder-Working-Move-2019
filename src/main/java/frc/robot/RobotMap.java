/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.DrivePIDOutput;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.util.WPILibVersion;
import frc.robot.subsystems.PWMLidar;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  public static WPI_TalonSRX frontLeft;
  public static WPI_TalonSRX frontRight;
  public static WPI_TalonSRX backLeft;
  public static WPI_TalonSRX backRight;
  public static WPI_TalonSRX elevTalon;
	public static WPI_TalonSRX elevTalon2;
  public static SpeedControllerGroup left;
  public static SpeedControllerGroup right;
  public static DifferentialDrive drive;
  public static AnalogInput input0;
  public static AnalogInput input1;
  public static AnalogInput input2;
  public static PWMLidar lidar;
  public static AHRS navx; 
  public static Encoder encoderRight; 
  public static Encoder encoderLeft; 
  //public static DoubleSolenoid hatchPiston;
  
  public static PIDDifferentialDrive driveDiff; 
  public static DrivePIDOutput drivePID; 
  public static TwoEncoderPID encoders; 
  public static PIDController driveController;
  public static PIDController counteractDrift; 
  public static RotationAdjuster rotationAdjuster;
  public static PIDController rotationController;
  public static SpeedControllerGroup rightDiff; 


  public static void init(){
    frontLeft = new WPI_TalonSRX(9);
    backLeft = new WPI_TalonSRX(10);
    frontRight = new WPI_TalonSRX(2);
    backRight = new WPI_TalonSRX(1);

    //elevTalon = new WPI_TalonSRX(99);
    //elevTalon2 = new WPI_TalonSRX(200);

    left = new SpeedControllerGroup(frontLeft, backLeft);
    right = new SpeedControllerGroup(frontRight, backRight);
    rightDiff = new SpeedControllerGroup(frontRight, backRight);
    rightDiff.setInverted(true);
    drive = new DifferentialDrive(left,right);
    //input0 = new AnalogInput(0);
    //input1 = new AnalogInput(1);
    //input2 = new AnalogInput(2);
    lidar = new PWMLidar(5);
    navx = new AHRS(Port.kUSB); 
    encoderLeft = new Encoder(8,9, true); // For left encoder it reads -ve values so reverse direction 
    encoderRight = new Encoder(0,1); 
    encoders = new TwoEncoderPID(encoderLeft, encoderRight);

    //hatchPiston = new DoubleSolenoid(4,5);

    rotationAdjuster = new RotationAdjuster();

    driveDiff = new PIDDifferentialDrive(left, rightDiff, rotationAdjuster, false);
    drivePID = new DrivePIDOutput();
    driveController = new PIDController(0.05, 0, 0, encoderLeft, driveDiff);
    driveController.setOutputRange(-.3, .3);
		drive.setDeadband(0.1);
		drive.setSafetyEnabled(false);
		driveController.setAbsoluteTolerance(2);

    rotationController = new PIDController(0.1, 0, 0, navx, driveDiff);
		rotationController.setInputRange(-180, 180);
		rotationController.setOutputRange(-.65, .65);
		rotationController.setContinuous(true);
    rotationController.setAbsoluteTolerance(2);
    
    counteractDrift = new PIDController(0.08, 0, 0, navx, rotationAdjuster);
		counteractDrift.setInputRange(-180, 180);
		counteractDrift.setOutputRange(-.7, .7);
		counteractDrift.setContinuous(true);
		counteractDrift.setAbsoluteTolerance(1);

    //System.out.println("RobotMapInit: " + RobotMap.intakeLidar.getDistance());
		//elevTalon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		//elevTalon.config_kP(0, 1.5, 0);
		//elevTalon.setInverted(false);
		
//		elevator = new TwoMotorsOneEncoder(elevatorTalon);
  }
  // For example to map the left and right motors, you could define the
  // following variables to use with your drivetrain subsystem.
  // public static int leftMotor = 1;
  // public static int rightMotor = 2;

  // If you are using multiple modules, make sure to define both the port
  // number and the module. For example you with a rangefinder:
  // public static int rangefinderPort = 1;
  // public static int rangefinderModule = 1;
}