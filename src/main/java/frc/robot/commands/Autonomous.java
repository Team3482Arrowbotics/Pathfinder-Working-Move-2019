/*package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class Autonomous extends CommandGroup{
	public enum StartPosition {
		LEFT, MIDDLE, RIGHT;
	}
	boolean rocket, cargoLeft, cargoRight, restock;
	StartPosition sPos;
	public Autonomous(boolean rocket, boolean cargoLeft, boolean cargoRight, boolean restock, StartPosition sPos) {
		super();
		this.rocket = rocket;
		this.cargoLeft = cargoLeft;
        this.cargoRight = cargoRight;
        this.restock = restock; 
		this.sPos = sPos;
		addSequential(new Move(6));
		switch(sPos) {*/
		/*case LEFT:
			if(rocket) {
				addSequential(new StartToScale(false));
				addSequential(new PlaceBoxOnScale(false));
				addSequential(new ScaleToSameSideBox(false));
				if(restock) {
					addSequential(new FindBox());
					addSequential(new GetBox());
					addSequential(new PlaceBoxOnSwitch());
				} else {
					addSequential(new AcrossSwitchLane(false));
					addSequential(new FindBox(true));
					addSequential(new GetBox());
					addSequential(new PlaceBoxOnSwitch());
				}
            } 
            else          
            {
				if(crossBaseline) {
					addSequential(new AcrossBaseline(false));
					addSequential(new StartToScale(true));
				}
				else {
					addSequential(new StartToOppoAutoLine(false));
					addSequential(new AutoLineToScale());
				}
				addSequential(new PlaceBoxOnScale(true));
				addSequential(new ScaleToSameSideBox(true));
				if(switchOnLeft) {
					addSequential(new AcrossSwitchLane(true));
					addSequential(new FindBox());
					addSequential(new GetBox());
					addSequential(new PlaceBoxOnSwitch());
				} else {
					addSequential(new FindBox(true));
					addSequential(new GetBox());
					addSequential(new PlaceBoxOnSwitch());
				}
			}
			break;
		case MIDDLE:
            if(scaleOnLeft) 
            {
				if(crossBaseline) {
					addSequential(new AcrossBaselineFromMiddle(false));
					addSequential(new StartToScale(false));
				} else {
					addSequential(new MiddleStartToLeftAutoLine());
					addSequential(new AutoLineToScale());
				}
				addSequential(new PlaceBoxOnScale(false));
				addSequential(new ScaleToSameSideBox(false));
				
				if(switchOnLeft) {
					addSequential(new FindBox()); // true or false?

				} else {
					addSequential(new AcrossSwitchLane(false));
					addSequential(new FindBox(true)); // true or false?
				}
				addSequential(new GetBox());
				addSequential(new PlaceBoxOnSwitch());
				
            } 
            else 
            {
				if(crossBaseline) {
					addSequential(new AcrossBaselineFromMiddle(true));
				} else {
					addSequential(new MiddleStartToRightAutoLine());
					addSequential(new AutoLineToScale());
				}
				addSequential(new PlaceBoxOnScale(true));
				addSequential(new ScaleToSameSideBox(true));
				
				if(!switchOnLeft) {
					addSequential(new FindBox()); // true or false?

				} else {
					addSequential(new AcrossSwitchLane(true));
					addSequential(new FindBox(true)); // true or false?
				}
				addSequential(new GetBox());
				addSequential(new PlaceBoxOnSwitch());
			}
			break;
		case RIGHT:
            if(scaleOnLeft) 
            {
				if(crossBaseline) {
					addSequential(new AcrossBaseline(true));
					addSequential(new StartToScale(false));
				}
				else {
					addSequential(new StartToOppoAutoLine(true));
					addSequential(new AutoLineToScale());
				}
				addSequential(new PlaceBoxOnScale(false));
				addSequential(new ScaleToSameSideBox(false));
				if(switchOnLeft) {
					addSequential(new FindBox());
					addSequential(new GetBox());
					addSequential(new PlaceBoxOnSwitch());
				} else {
					addSequential(new AcrossSwitchLane(false));
					addSequential(new FindBox(true));
					addSequential(new GetBox());
					addSequential(new PlaceBoxOnSwitch());
				}
            } 
            else 
            {
				addSequential(new StartToScale(true));
				addSequential(new PlaceBoxOnScale(true));
				addSequential(new ScaleToSameSideBox(true));
				if(switchOnLeft) {
					addSequential(new AcrossSwitchLane(true));
					addSequential(new FindBox(true));
					addSequential(new GetBox());
					addSequential(new PlaceBoxOnSwitch());
				} else {
					addSequential(new FindBox());
					addSequential(new GetBox());
					addSequential(new PlaceBoxOnSwitch());
				}
			}
			break;
		}
		
	}
	
}*/
