package org.usfirst.frc.team3494.robot.commands.climb;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Stops the climber when
 * <a href="https://www.youtube.com/watch?v=2k0SmqbBIpQ">it's time to stop.</a>
 *
 * @see org.usfirst.frc.team3494.robot.subsystems.Climber
 */
public class StopClimber extends Command {

	public StopClimber() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climber);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.climber.stopAll();
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
