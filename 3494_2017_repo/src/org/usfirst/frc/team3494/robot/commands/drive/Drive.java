package org.usfirst.frc.team3494.robot.commands.drive;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command to run drivetrain. Only takes input in the form of joysticks
 * (commands for auto moving coming soon<sup>tm</sup>)
 * 
 * @see org.usfirst.frc.team3494.robot.subsystems.Drivetrain
 */
public class Drive extends Command {

	public Drive() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super("Drive");
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (Robot.prefs.getBoolean("xcontrol", true)) {
			if (Robot.prefs.getBoolean("arcade", true)) {
				Robot.driveTrain.wpiDrive.arcadeDrive(Robot.oi.xbox.getY(Hand.kLeft), Robot.oi.xbox.getX(Hand.kLeft));
			} else {
				Robot.driveTrain.TankDrive(Robot.oi.xbox.getY(Hand.kRight), -Robot.oi.xbox.getY(Hand.kLeft));
			}
		} else {
			Robot.driveTrain.TankDrive(-Robot.oi.leftStick.getY(), -Robot.oi.rightStick.getY());
		}
		if (Robot.oi.xbox.getPOV() == 0) {
			Robot.driveTrain.adjustedTankDrive(0.4, 0.4);
		} else if (Robot.oi.xbox.getPOV() == 90) {
			Robot.driveTrain.adjustedTankDrive(-0.4, 0.4);
		} else if (Robot.oi.xbox.getPOV() == 180) {
			Robot.driveTrain.adjustedTankDrive(-0.4, -0.4);
		} else if (Robot.oi.xbox.getPOV() == 270) {
			Robot.driveTrain.adjustedTankDrive(0.4, -0.4);
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
