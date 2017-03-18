package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.UnitTypes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives straight using the drivetrain's PID loop.
 */
public class PIDStraightDrive extends Command {

	private double distance;
	private double angle;

	public PIDStraightDrive(double dist, double angle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		this.distance = dist;
	}
	
	public PIDStraightDrive(double dist) {
		this(dist, 0);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.ahrs.reset();
		Robot.driveTrain.enable();
		Robot.driveTrain.setSetpoint(this.angle);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putNumber("angle", Robot.ahrs.getAngle());
		System.out.println(Robot.ahrs.getAngle());
		// System.out.println(Robot.driveTrain.PIDTune);
		Robot.driveTrain.ArcadeDrive(0.4, Robot.driveTrain.PIDTune, true);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.driveTrain.getAvgDistance(UnitTypes.INCHES) >= this.distance;
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
