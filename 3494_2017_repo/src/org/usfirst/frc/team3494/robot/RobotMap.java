package org.usfirst.frc.team3494.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	// drivetrain left
	public static final int leftTalonOne = 0;
	public static final int leftTalonTwo = 1;
	public static final int leftTalonThree = 2;
	// drivetrain right
	public static final int rightTalonOne = 13;
	public static final int rightTalonTwo = 14;
	public static final int rightTalonThree = 15;
	/**
	 * Minumum drive speed. Make sure you never drive at a speed less than this.
	 */
	public static final double DRIVE_TOLERANCE = 0.1;
	// encoders
	public static final int ENCODER_RIGHT_A = 0;
	public static final int ENCODER_RIGHT_B = 1;
	// intake
	public static final int INTAKE_MOTOR = 60;
}
