package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.UnitTypes;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives a given distance.
 */
public class DistanceDrive extends Command {
	double dist = 0;
	UnitTypes unit;

	public DistanceDrive(double distance, UnitTypes unitType) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		super("DistanceDrive");
		requires(Robot.driveTrain);
		this.dist = distance;
		this.unit = unitType;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.stopAll();
		Robot.driveTrain.resetRight();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.driveTrain.TankDrive(0.2, 0.2);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.driveTrain.getRightDistance(UnitTypes.INCHES) >= 8;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.StopDrive();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.driveTrain.StopDrive();
	}
}
