package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class StageTest extends Command {
	private int counter;
    public StageTest() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.initStaging();
    	Robot.driveTrain.stagedTankDrive(-0.4, 0.4);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	this.counter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return counter > 100;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.snapBackToReality();
    	Robot.driveTrain.setAll(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
