package org.usfirst.frc.team3494.robot.commands.climb;

import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command for climbing. Climbs both up and down.
 * 
 * @see org.usfirst.frc.team3494.robot.subsystems.Climber
 */
public class Climb extends Command {
	/**
	 * The direction to climb in.
	 */
	private DriveDirections direction;

	/**
	 * Constructor for Climb.
	 * 
	 * @param dir
	 *            The direction to climb in. If this is not
	 *            {@link DriveDirections#UP} or {@link DriveDirections#DOWN},
	 *            default to {@link DriveDirections#UP}
	 */
	public Climb(DriveDirections dir) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.climber);
		requires(Robot.driveTrain);
		if (dir != null) {
			if (dir.equals(DriveDirections.DOWN) || dir.equals(DriveDirections.UP)) {
				this.direction = dir;
			} else {
				this.direction = DriveDirections.UP;
			}
		}
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		float pitch = Robot.ahrs.getPitch();
		if (!(pitch > 30)) {
			Robot.climber.climb(direction);
		} else {
			Robot.climber.stopAll();
			Robot.climber.engagePTO();
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
		Robot.climber.stopAll();
	}
}
