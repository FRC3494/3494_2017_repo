package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.UnitTypes;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class XYDrive extends CommandGroup {
	
	private double hypot;
	private double angle;
	
    public XYDrive(double rise, double run) {
		double run2 = run * run;
		double rise2 = rise * rise;
		this.hypot = Math.sqrt(run2 + rise2);
		this.angle = Math.toDegrees(Math.atan2(rise, run));
    	requires(Robot.driveTrain);
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new AngleTurn(angle));
    	addSequential(new DistanceDrive(hypot, UnitTypes.INCHES));

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
