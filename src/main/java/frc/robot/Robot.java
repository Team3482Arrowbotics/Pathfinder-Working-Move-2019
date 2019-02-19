/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.PathfinderFRC;
import jaci.pathfinder.Trajectory; 
import jaci.pathfinder.followers.EncoderFollower;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.commands.Move;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {
  public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
  public static OI m_oi;

  Command m_autonomousCommand;
  public static boolean driveEnabled;

  public SendableChooser<Command> m_chooser = new SendableChooser<>();
  public SendableChooser<String> pathChooser; 
  public SendableChooser<String> hatchOrCargo; 

  public static String turning = "Not Turning";
  public static double MOVE_SPEED = 0.6;

  private EncoderFollower l_enc_follower; 
  private EncoderFollower r_enc_follower; 

  private static final int k_ticks_per_rev = 360; 
  //private static final int k_ticks_per_rev = 1024; 
  
  private static final double k_wheel_diameter = 0.19431; //7.6 inches in meters; 
  private static final double k_max_velocity = 0.25; 
  //left to right wheel space 0.5715 m; 
  private static String pathname = "";
  private static final String pathnamee = "LeftTurn";
  
  /*public enum autoState
  {
    FIRSTPATH, HATCHOUTTAKE, SECONDPATH, HATCHINTAKE, THIRDPATH; 
  }*/

  private Notifier follower_notifier; 
  private int state; 
  private boolean hatch; 
  
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    RobotMap.init();
    m_oi = new OI();
    //m_autonomousCommand = new Move(10); 

    driveEnabled = true; 
    pathChooser = new SendableChooser<String>(); 
    hatchOrCargo = new SendableChooser<String>(); 

    
    pathChooser.setDefaultOption("Default", "????");
    pathChooser.addOption("LtoFrontRight", "LtoFrontRight");
    pathChooser.addOption("RtoFrontRight", "RtoFrontRight");
    pathChooser.addOption("MtoFrontRight", "MtoFrontRight");
    pathChooser.addOption("LtoFrontLeft", "LtoFrontLeft");
    pathChooser.addOption("RtoFrontLeft", "RtoFrontLeft");
    pathChooser.addOption("MtoFrontLeft", "MtoFrontLeft");
    pathChooser.addOption("LtoFrontRocket", "LtoFrontRocket");
    pathChooser.addOption("BacktoRocket", "BackToRocket");
    pathChooser.addOption("Leftturn", "LeftTurn");
    pathChooser.addOption("Straight", "newre");

    hatchOrCargo.setDefaultOption("Hatch", "Hatch");
    hatchOrCargo.addOption("Cargo", "Cargo");

    SmartDashboard.putData("Path Chooser", pathChooser); 
    SmartDashboard.putData("Hatch or Cargo", hatchOrCargo);
    //m_chooser.setDefaultOption("Default Auto", new ExampleCommand());
    //SmartDashboard.putData(m_chooser);
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //System.out.println("Navx Robot Periodic: " + RobotMap.navx.getAngle());
    //System.out.println("Lidar: " + RobotMap.lidar.getDistance()); 
    SmartDashboard.putNumber("Lidar", RobotMap.lidar.getDistance());
    SmartDashboard.putString("Turning", turning);
    SmartDashboard.putData("Drivebase", RobotMap.drive);
    SmartDashboard.putData("Navx", RobotMap.navx);
    SmartDashboard.putNumber("Left Encoder Graph",  RobotMap.encoderLeft.get());
    SmartDashboard.putNumber("Right Encoder Graph",  RobotMap.encoderRight.get());
    SmartDashboard.putNumber("Left Encoder",  RobotMap.encoderLeft.get());
    SmartDashboard.putNumber("Right Encoder",  RobotMap.encoderRight.get());
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();
    
    pathname = pathChooser.getSelected(); 
    SmartDashboard.putString("Selected Path", pathname);

    if(hatchOrCargo.getSelected().equals("Hatch"))
    {
      hatch = true;
    }
    else
    {
      hatch = false; 
    }
    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */
    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }    
    
    RobotMap.navx.reset(); 
    RobotMap.encoderLeft.reset();
    RobotMap.encoderRight.reset();     

    Trajectory left_trajectory = PathfinderFRC.getTrajectory(pathname + ".left");
    Trajectory right_trajectory = PathfinderFRC.getTrajectory(pathname+ ".right");

    l_enc_follower = new EncoderFollower(left_trajectory);
    r_enc_follower = new EncoderFollower(right_trajectory);
    l_enc_follower.configureEncoder(RobotMap.encoderLeft.get(), k_ticks_per_rev, k_wheel_diameter);
    l_enc_follower.configurePIDVA(0.8, 0.0, 0.0, 1 / k_max_velocity, 0);
    r_enc_follower.configureEncoder(RobotMap.encoderRight.get(), k_ticks_per_rev, k_wheel_diameter);
    r_enc_follower.configurePIDVA(0.8, 0.0, 0.0, 1 / k_max_velocity, 0);
    follower_notifier = new Notifier(this::followPath);
    follower_notifier.startPeriodic(left_trajectory.get(0).dt);
    //intake 
    //new path 
  }

  private void followPath() {
    if (l_enc_follower.isFinished() || r_enc_follower.isFinished()) {
      follower_notifier.stop();
      RobotMap.left.set(0); 
      RobotMap.right.set(0); 
      System.out.println("Stop Notifier");
    } else {
      double left_speed = l_enc_follower.calculate(RobotMap.encoderLeft.get());
      double right_speed = r_enc_follower.calculate(RobotMap.encoderRight.get());
      double heading = RobotMap.navx.getAngle();
      double desired_heading = Pathfinder.r2d(l_enc_follower.getHeading());
      //check to see if + or - heading difference 
      double heading_difference = Pathfinder.boundHalfDegrees(desired_heading + heading);
      double turn =  0.8 * (-1.0/80.0) * heading_difference;
      RobotMap.left.set(0.6*(left_speed + turn));
      RobotMap.right.set(0.6*(right_speed - turn));
    }
  }

  /*public static double isLineDetected() {
    double threshold = 0.5;
    boolean right = RobotMap.input0.getVoltage() < threshold;
    boolean center = RobotMap.input1.getVoltage() < threshold;
    boolean left = RobotMap.input2.getVoltage() < threshold;
    if(left || center|| right) 
    {
      if(left && center && right) 
      {
        return 1000;
      }
       else if(left && center) 
      {
        return -0.5;
      }
       else if (right && center) 
      {
        return 0.5;
      }
      else if(left && !center && !right) 
      {
        return -1;
      }
      else if(!left && center && !right)
      {
        return 0;
      }
      else if(!left && !center && right) 
      {
        return 1;
      }
    }
    return 100;
  }*/

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
/*
    if(hatch)
    {
      if(state == 1)
      {
        Trajectory left_trajectory = PathfinderFRC.getTrajectory(pathname + ".right");
        Trajectory right_trajectory = PathfinderFRC.getTrajectory(pathname+ ".left");
    
        l_enc_follower = new EncoderFollower(left_trajectory);
        r_enc_follower = new EncoderFollower(right_trajectory);
        l_enc_follower.configureEncoder(RobotMap.encoderLeft.get(), k_ticks_per_rev, k_wheel_diameter);
        l_enc_follower.configurePIDVA(0.8, 0.0, 0.0, 1 / k_max_velocity, 0);
        r_enc_follower.configureEncoder(RobotMap.encoderLeft.get(), k_ticks_per_rev, k_wheel_diameter);
        r_enc_follower.configurePIDVA(0.8, 0.0, 0.0, 1 / k_max_velocity, 0);
        follower_notifier = new Notifier(this::followPath);
        follower_notifier.startPeriodic(left_trajectory.get(0).dt);
        state = 2; 
      }
      else if(state ==2)
      {
        //start vision
        state = 3; 
      }
      else if(state ==3)
      {
        //hatch outtake 
        state = 4; 
      }
      else if (state ==4)
      {
        //back up turn around
        state = 5; 
      }
      else if(state ==5)
      {
        if(pathname.equals(""))
        {
          Trajectory left_trajectory = PathfinderFRC.getTrajectory(pathname + ".right");
          Trajectory right_trajectory = PathfinderFRC.getTrajectory(pathname+ ".left");
      
          l_enc_follower = new EncoderFollower(left_trajectory);
          r_enc_follower = new EncoderFollower(right_trajectory);
          l_enc_follower.configureEncoder(RobotMap.encoderLeft.get(), k_ticks_per_rev, k_wheel_diameter);
          l_enc_follower.configurePIDVA(0.8, 0.0, 0.0, 1 / k_max_velocity, 0);
          r_enc_follower.configureEncoder(RobotMap.encoderLeft.get(), k_ticks_per_rev, k_wheel_diameter);
          r_enc_follower.configurePIDVA(0.8, 0.0, 0.0, 1 / k_max_velocity, 0);
          follower_notifier = new Notifier(this::followPath);
          follower_notifier.startPeriodic(left_trajectory.get(0).dt);
        }
        else if(pathname.equals(""))
        {
          Trajectory left_trajectory = PathfinderFRC.getTrajectory(pathname + ".right");
          Trajectory right_trajectory = PathfinderFRC.getTrajectory(pathname+ ".left");
      
          l_enc_follower = new EncoderFollower(left_trajectory);
          r_enc_follower = new EncoderFollower(right_trajectory);
          l_enc_follower.configureEncoder(RobotMap.encoderLeft.get(), k_ticks_per_rev, k_wheel_diameter);
          l_enc_follower.configurePIDVA(0.8, 0.0, 0.0, 1 / k_max_velocity, 0);
          r_enc_follower.configureEncoder(RobotMap.encoderLeft.get(), k_ticks_per_rev, k_wheel_diameter);
          r_enc_follower.configurePIDVA(0.8, 0.0, 0.0, 1 / k_max_velocity, 0);
          follower_notifier = new Notifier(this::followPath);
          follower_notifier.startPeriodic(left_trajectory.get(0).dt);
        } 
        state = 6; 
      }
      else if(state == 6)
      {
        //hatch intake 
      }
    }*/
    

    

    /*
    isLineDetected();
    if(RobotMap.intakeLidar.getDistance() >= 47)
    {
      RobotMap.drive.arcadeDrive(0,0.4);
    }
    else
    {
      RobotMap.drive.arcadeDrive(0,0);
    }


    System.out.println("Voltage One: " + RobotMap.input0.getVoltage());
    System.out.println("Voltage Two: " + RobotMap.input1.getVoltage());
    System.out.println("Voltage Three: " + RobotMap.input2.getVoltage());
    /*if(RobotMap.intakeLidar.getDistance() >= 47)
    {
      switch((int)(isLineDetected()*10)) 
      {
        case -10:
          RobotMap.drive.arcadeDrive(-0.5,0);
          turning = "Turning Left";
          break;

        case -5:
          RobotMap.drive.arcadeDrive(-0.45,0);
          turning = "Turning Slight Left";
          break;

        case 5:
          RobotMap.drive.arcadeDrive(0.45,0);
          turning = "Turning Slight Right";
          break;

        case 10:
          RobotMap.drive.arcadeDrive(0.5,0);
          turning = "Turning Right";
          break;

        default:
          RobotMap.drive.arcadeDrive(0,MOVE_SPEED-0.1);
          turning = "Not Turning";
          break;
      }
    }
    else
    {
      RobotMap.drive.arcadeDrive(0, 0);
    }*/

  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.

    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    //follower_notifier.stop(); 

  }
  
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();

    double speed = OI.j.getRawAxis(1);
    double turnSpeed = OI.j.getRawAxis(4);

    if (driveEnabled) {
			RobotMap.drive.arcadeDrive(0.7*turnSpeed, -0.7*speed);
		}
    //SmartDashboard.putNumber("Speed", RobotMap.left.getSelectedSensorVelocity());


  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
