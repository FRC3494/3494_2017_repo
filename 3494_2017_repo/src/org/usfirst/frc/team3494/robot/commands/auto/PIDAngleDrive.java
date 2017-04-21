package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Turns the robot to an angle using PID. Hello, PID.
 */
public class PIDAngleDrive extends Command {

	private double angle;

	/**
	 * Constructor.
	 *
	 * @param angle
	 *            The angle to turn, in degrees.
	 */
	public PIDAngleDrive(double angle) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
		this.angle = angle;
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
		Robot.driveTrain.ArcadeDrive(0, -Robot.driveTrain.PIDTune, true);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return Robot.driveTrain.onTarget();
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.disable();
		Robot.driveTrain.stopAll();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}
