package org.usfirst.frc.team3494.robot.commands.turret;

import org.usfirst.frc.team3494.robot.DriveDirections;
import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurretCon extends Command {

	public TurretCon() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.turret);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		int dpad = Robot.oi.xbox_2.getPOV();
		if (dpad == -1) {
			Robot.turret.stopHood();
		} else if (dpad == 0) {
			Robot.turret.stopTurret();
			Robot.turret.setHood(0.25);
		} else if (dpad == 180) {
			Robot.turret.stopTurret();
			Robot.turret.setHood(-0.25);
		} else if (dpad == 90) {
			Robot.turret.stopHood();
			Robot.turret.turnTurret(DriveDirections.RIGHT);
		} else if (dpad == 270) {
			Robot.turret.stopHood();
			Robot.turret.turnTurret(DriveDirections.LEFT);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
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
