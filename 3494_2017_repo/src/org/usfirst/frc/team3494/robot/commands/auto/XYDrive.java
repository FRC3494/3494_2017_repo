package org.usfirst.frc.team3494.robot.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives to a point relative to the robot.
 *
 * @since 0.0.3
 */
public class XYDrive extends CommandGroup {

	private double hypot;
	private double angle;

	/**
	 * Constructor.
	 *
	 * @param rise
	 *            The distance along the Y axis that the point is from the
	 *            robot.
	 * @param run
	 *            The distance along the X axis that the point is from the
	 *            robot.
	 */
	public XYDrive(double rise, double run) {
		this(rise, run, true);
	}

	/**
	 * More verbose(?) constructor.
	 *
	 * @param rise
	 *            The distance along the Y axis that the point is from the
	 *            robot.
	 * @param run
	 *            The distance along the X axis that the point is from the
	 *            robot.
	 * @param PID
	 *            Whether or not to use the drivetrain's PID control to drive to
	 *            the point. {@code true} is the recommended value, for obvious
	 *            reasons.
	 */
	public XYDrive(double rise, double run, boolean PID) {
		double run2 = run * run;
		double rise2 = rise * rise;
		this.hypot = Math.sqrt(run2 + rise2);
		this.angle = Math.toDegrees(Math.atan2(rise, run));
		if (PID) {
			addSequential(new PIDAngleDrive(angle));
			addSequential(new PIDFullDrive(hypot));
		} else {
			addSequential(new AngleTurn(angle));
			addSequential(new DistanceDrive(hypot)); // default to inches
		}
	}
}
