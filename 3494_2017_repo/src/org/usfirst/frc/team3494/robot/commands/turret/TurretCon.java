package org.usfirst.frc.team3494.robot.commands.turret;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		int dpad = Robot.oi.stick_l.getPOV();
		if (dpad == 0) {
			Robot.turret.multi += 0.1;
		} else if (dpad == 180) {
			Robot.turret.multi -= 0.1;
		}
		SmartDashboard.putNumber("Shooter multiplier", Robot.turret.multi);
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
