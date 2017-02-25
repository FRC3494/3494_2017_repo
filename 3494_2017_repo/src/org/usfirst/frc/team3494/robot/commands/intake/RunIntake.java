package org.usfirst.frc.team3494.robot.commands.intake;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Runs the intake. Default command for Intake subsystem.
 */
public class RunIntake extends Command {

	boolean running = false;
	double speed;

	public RunIntake() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.intake);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.oi.xbox_rt.get()) {
			Robot.intake.runIntake(0.75);
		} else if (Robot.oi.xbox_lt.get()) {
			Robot.intake.runIntake(-0.75);
		} else {
			Robot.intake.stopAll();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
