package org.usfirst.frc.team3494.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to cause delays in autonomous. WIP.
 */
public class DelayCommand extends Command {

	double time;

	public DelayCommand(double t) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		this.time = t;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		try {
			Thread.sleep((long) time);
		} catch (InterruptedException e) {
			System.out.println("HOW LONG DID YOU WAIT?!");
			e.printStackTrace();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
