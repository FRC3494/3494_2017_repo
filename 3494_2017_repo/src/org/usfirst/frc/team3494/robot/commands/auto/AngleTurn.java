package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AngleTurn extends Command {

	private double angle;

	public AngleTurn(double angle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		this.angle = angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.ahrs.reset();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (!((Robot.ahrs.getAngle() > this.angle - 10) && (Robot.ahrs.getAngle() < this.angle + 10))) {
			System.out.println(this.angle);
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
		return ((Robot.ahrs.getAngle() > this.angle - 10) && (Robot.ahrs.getAngle() < this.angle + 10));
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
