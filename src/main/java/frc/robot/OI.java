/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Move; 
//import frc.robot.commands.Turn; 

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  public static Joystick j;
  public static JoystickButton a; 
  public static JoystickButton b; 
  public static JoystickButton x;  
  public static JoystickButton y;
  private static final double ticks_per_centimeter = 2000/317.5; //travels 125in in 2000 encoder ticks

  public OI()
  {
    j = new Joystick(1);
    a = new JoystickButton(j, 1);
    b = new JoystickButton(j, 2);
    x = new JoystickButton(j, 3);
    y = new JoystickButton(j, 4);

    a.whenPressed(new Move(2000)); 
    b.whenPressed(new Move(ticks_per_centimeter*RobotMap.lidar.getDistance()));


    //a.whenPressed(new Move(RobotMap.lidar.getDistance())); 

    //b.whenPressed(new Turn(30)); 

  }
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());
}
