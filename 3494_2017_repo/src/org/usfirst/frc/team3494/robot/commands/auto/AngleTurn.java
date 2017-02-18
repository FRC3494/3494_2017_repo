package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Turns the robot using the gyro board mounted to the RoboRIO. The angle to
 * turn by must be specified in the constructor. Angle tolerance is specified by
 * Robot.prefs (key {@code angle tolerance}.)
 * 
 * @since 0.0.2
 * @see org.usfirst.frc.team3494.robot.Robot
 * @see org.usfirst.frc.team3494.robot.subsystems.Drivetrain
 */
public class AngleTurn extends Command {

	private double angle;
	private static double tolerance;

	/**
	 * Constructor.
	 * 
	 * @param angle
	 *            The number of degrees to turn.
	 */
	public AngleTurn(double angle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		this.angle = angle;
		try {
			tolerance = Robot.prefs.getDouble("angle tolerance", 2.5);
		} catch (NullPointerException e) {
			tolerance = 2.5;
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.ahrs.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!((Robot.ahrs.getAngle() > this.angle - tolerance) && (Robot.ahrs.getAngle() < this.angle + tolerance))) {
			if (this.angle > 0) {
				Robot.driveTrain.adjustedTankDrive(-0.4, 0.4);
				Robot.driveTrain.resetRight();
				return;
			} else if (this.angle < 0) {
				Robot.driveTrain.adjustedTankDrive(0.4, -0.4);
				Robot.driveTrain.resetRight();
				return;
			} else {
				Robot.driveTrain.adjustedTankDrive(0.4, 0.4);
			}
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return ((Robot.ahrs.getAngle() > this.angle - tolerance) && (Robot.ahrs.getAngle() < this.angle + tolerance));
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
