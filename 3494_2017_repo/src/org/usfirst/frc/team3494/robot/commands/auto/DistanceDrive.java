package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.RobotMap;
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
		dist = distance;
		unit = unitType;
	}

	/**
	 * Alternative constructor.
	 *
	 * @param distance
	 *            The distance to drive, in inches.
	 */
	public DistanceDrive(double distance) {
		this(distance, UnitTypes.INCHES);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.resetRight();
		Robot.driveTrain.resetLeft();
		try {
			Thread.sleep(RobotMap.TALON_RESET_DELAY);
		} catch (InterruptedException e) {
			System.out.println("ah crap");
			e.printStackTrace();
		}
		System.out.println("Driving " + dist + " " + unit.toString() + "(s)");
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (dist > 0) {
			Robot.driveTrain.adjustedTankDrive(0.2, 0.2);
		} else if (dist < 0) {
			Robot.driveTrain.adjustedTankDrive(-0.2, -0.2);
		} else {
			return;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Math.abs(Robot.driveTrain.getAvgDistance(unit)) >= Math.abs(dist);
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
