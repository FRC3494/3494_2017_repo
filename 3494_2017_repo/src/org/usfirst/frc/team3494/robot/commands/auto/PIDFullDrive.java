package org.usfirst.frc.team3494.robot.commands.auto;

import org.usfirst.frc.team3494.robot.Robot;
import org.usfirst.frc.team3494.robot.RobotMap;
import org.usfirst.frc.team3494.robot.UnitTypes;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drives straight/forward with an angle (WIP) using the drivetrain's PID loop.
 * Only works in inches.
 */
public class PIDFullDrive extends Command {

	private double distance;
	private double angle;
	private boolean uselb;

	/**
	 * Other constructor. Uses default angle of 0 degrees.
	 *
	 * @param dist
	 *            The distance to drive in inches.
	 */
	public PIDFullDrive(double dist) {
		this(dist, 0);
	}

	/**
	 * Constructor.
	 *
	 * @param dist
	 *            The distance to drive, in inches.
	 * @param angle
	 *            The angle to turn to.
	 */
	public PIDFullDrive(double dist, double angle) {
		this(dist, angle, false);
	}

	/**
	 * Constructor.
	 *
	 * @param dist
	 *            The distance to drive, in inches.
	 * @param angle
	 *            The angle to turn to.
	 * @param uselinebreaker
	 *            Whether to use the linebreak sensor instead of encoder
	 *            distance to stop
	 */
	public PIDFullDrive(double dist, double angle, boolean uselinebreaker) {
		requires(Robot.driveTrain);
		this.distance = dist;
		this.angle = angle;
		this.uselb = uselinebreaker;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.driveTrain.resetLeft();
		Robot.driveTrain.resetRight();
		try {
			Thread.sleep(RobotMap.TALON_RESET_DELAY);
		} catch (InterruptedException e) {
			System.out.println("ah crap");
			e.printStackTrace();
		}
		Robot.ahrs.reset();
		Robot.driveTrain.enable();
		Robot.driveTrain.setSetpoint(this.angle);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		SmartDashboard.putNumber("angle", Robot.ahrs.getAngle());
		// System.out.println(Robot.driveTrain.PIDTune);
		if (this.distance < Robot.driveTrain.getAvgDistance(UnitTypes.INCHES)) {
			Robot.driveTrain.ArcadeDrive(0.5, -Robot.driveTrain.PIDTune, true);
		} else {
			Robot.driveTrain.ArcadeDrive(-0.5, -Robot.driveTrain.PIDTune, true);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		if (!this.uselb) {
			return Math.abs(Robot.driveTrain.getAvgDistance(UnitTypes.INCHES)) >= Math.abs(this.distance);
		} else {
			return Robot.gearTake.lb.get();
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		System.out.println("done PID driving");
		Robot.driveTrain.disable();
		Robot.driveTrain.stopAll();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		this.end();
	}
}
