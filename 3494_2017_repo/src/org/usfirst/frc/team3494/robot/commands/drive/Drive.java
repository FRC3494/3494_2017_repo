package org.usfirst.frc.team3494.robot.commands.drive;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Command to run drivetrain. Only takes input from joysticks.
 *
 * @see org.usfirst.frc.team3494.robot.subsystems.Drivetrain Drivetrain
 * @see org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive Distance
 *      Driving (auto)
 * @see org.usfirst.frc.team3494.robot.commands.auto.AngleTurn Angle Driving
 *      (auto)
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
		SmartDashboard.putNumber("Drive setpoint", Robot.driveTrain.getSetpoint());
		SmartDashboard.putBoolean("Drive PID enabled", Robot.driveTrain.getPIDController().isEnabled());
		int dpad = Robot.oi.stick_l.getPOV();
		if (dpad == 0 || Robot.oi.stick_l.getRawButton(7)) {
			Robot.driveTrain.inverter = 1;
		} else if (dpad == 180 || Robot.oi.stick_l.getRawButton(8)) {
			Robot.driveTrain.inverter = -1;
		} else if (dpad == 270 || Robot.oi.stick_l.getRawButton(9)) {
			Robot.driveTrain.scaleDown = 0.5D;
		} else if (dpad == 90 || Robot.oi.stick_l.getRawButton(10)) {
			Robot.driveTrain.scaleDown = 1.0D;
		}
		SmartDashboard.putNumber("inverter", Robot.driveTrain.inverter);
		SmartDashboard.putNumber("scale down", Robot.driveTrain.scaleDown);
		boolean useX = Robot.prefs.getBoolean("xbone", false);
		if (useX) {
			if (Robot.prefs.getBoolean("arcade", false)) {
				// XBox controller with arcade
				Drive.driveArcade();
			} else if (!Robot.prefs.getBoolean("betapid", false)) {
				// Tank drive via XBox controller
				if (!Robot.driveTrain.getInverted()) {
					Robot.driveTrain.adjustedTankDrive(-Robot.oi.xbox.getY(Hand.kRight) * Robot.driveTrain.inverter,
							-Robot.oi.xbox.getY(Hand.kLeft) * Robot.driveTrain.inverter);
				} else {
					Robot.driveTrain.adjustedTankDrive(-Robot.oi.xbox.getY(Hand.kLeft) * Robot.driveTrain.inverter,
							-Robot.oi.xbox.getY(Hand.kRight) * Robot.driveTrain.inverter);
				}
			} else {
				// BETA - steer using Drivetrain PID off XBox split arcade
				if (!Robot.driveTrain.getPIDController().isEnabled()) {
					Robot.driveTrain.enable(); // enable only if disabled
				}
				if (Math.abs(Robot.oi.xbox.getX(Hand.kRight)) > 0.1) {
					Robot.driveTrain.setSetpoint(Robot.driveTrain.getSetpoint() + 3 * Robot.oi.xbox.getX(Hand.kRight));
				}
				Robot.driveTrain.ArcadeDrive(Robot.oi.xbox.getY(Hand.kLeft), -Robot.driveTrain.PIDTune, true);
			}
		} else {
			// Tank drive from flight sticks
			if (Robot.driveTrain.getInverted()) {
				Robot.driveTrain.TankDrive(-Robot.oi.stick_l.getY(), Robot.oi.stick_r.getY());
			} else {
				Robot.driveTrain.TankDrive(Robot.oi.stick_r.getY(), -Robot.oi.stick_l.getY());
			}
			if (Robot.oi.stick_l.getRawButton(3)) {
				Robot.driveTrain.enable();
				Robot.driveTrain.ArcadeDrive(Robot.oi.stick_l.getY(), -Robot.driveTrain.PIDTune, true);
			} else {
				Robot.driveTrain.disable();
			}
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

	private static void driveArcade() {
		double leftX = Robot.oi.xbox.getX(Hand.kLeft);
		if (Robot.oi.xbox.getX(Hand.kLeft) == 0 || leftX > -0.09 || leftX < 0.01) {
			Robot.driveTrain.setSetpoint(0);
			Robot.driveTrain.enable();
			Robot.driveTrain.ArcadeDrive(
					Robot.oi.xbox.getY(Hand.kLeft) * Robot.driveTrain.inverter * Robot.driveTrain.scaleDown,
					-Robot.driveTrain.PIDTune, true);
		} else {
			Robot.driveTrain.ArcadeDrive(
					Robot.oi.xbox.getY(Hand.kLeft) * Robot.driveTrain.inverter * Robot.driveTrain.scaleDown,
					Robot.oi.xbox.getX(Hand.kLeft) * Robot.driveTrain.inverter * Robot.driveTrain.scaleDown, true);
		}
	}
}
