package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.UnitTypes;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives a given distance. Currently suffering from encoder issues.
 * 
 * @see org.usfirst.frc.team3494.robot.subsystems.Drivetrain
 * @since 0.0.2
 */
public class DistanceDrive extends Command {

	private double dist;
	private UnitTypes unit;

	/**
	 * Constructor.
	 * 
	 * @param distance
	 *            The distance to drive.
	 * @param unitType
	 *            The unit that the distance is in.
	 * @see org.usfirst.frc.team3494.robot.UnitTypes
	 */
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
		Robot.driveTrain.resetRight();
		Robot.driveTrain.resetLeft();
		System.out.println("Driving " + this.dist + " " + this.unit.toString() + "(s)");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (this.dist > 0) {
			Robot.driveTrain.adjustedTankDrive(0.185, 0.2);
		} else if (this.dist < 0) {
			Robot.driveTrain.adjustedTankDrive(-0.185, -0.2);
		} else {
			return;
		}
		System.out.println("Average distance: " + Robot.driveTrain.getAvgDistance(this.unit));
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.driveTrain.getAvgDistance(unit)) >= Math.abs(this.dist);
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.stopAll();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.driveTrain.stopAll();
	}
}
