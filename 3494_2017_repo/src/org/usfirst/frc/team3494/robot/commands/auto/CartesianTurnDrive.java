package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.UnitTypes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * WIP coordinate drive system.
 * 
 * @see org.usfirst.frc.team3494.robot.commands.auto.AngleTurn
 * @see org.usfirst.frc.team3494.robot.commands.auto.DistanceDrive
 * @since 0.0.2
 */
public class CartesianTurnDrive extends Command {

	private double rise;
	private double run;
	private double hypot;
	private double angle;
	private boolean isDone = false;

	public CartesianTurnDrive(double rise, double run) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		this.rise = rise;
		this.run = run;
		// angle = inverse tan(rise/run)
		this.angle = Math.toDegrees(Math.atan2(rise, run));
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.ahrs.reset();
		Robot.driveTrain.resetRight();
		double run2 = this.run * this.run;
		double rise2 = this.rise * this.rise;
		this.hypot = Math.sqrt(run2 + rise2);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Scheduler.getInstance().add(new AngleTurn(this.angle));
		Robot.driveTrain.resetRight();
		if (Robot.driveTrain.getRightDistance(UnitTypes.INCHES) > this.hypot) {
			Robot.driveTrain.adjustedTankDrive(0.3, 0.3);
		} else {
			this.isDone = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return this.isDone;
	}

	// Called once after isFinished returns true
	protected void end() {
		this.isDone = false;
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
