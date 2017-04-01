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
	// Joystick Numbers
	public static final int XBOX_ONE = 0;
	public static final int XBOX_TWO = 1;
	// drivetrain left
	public static final int leftTalonOne = 17;
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
	public static final int ENCODER_LEFT_A = 2;
	public static final int ENCODER_LEFT_B = 3;
	// intake
	public static final int INTAKE_MOTOR = 5;
	public static final int UP_MOTOR = 4;
	// climber
	public static final int CLIMBER_MOTOR = 1;
	public static final int CLIMBER_MOTOR_PDP = 11;
	public static final int CLIMBER_PTO_FORWARD = 6;
	public static final int CLIMBER_PTO_BACKARD = 7;
	// turret
	public static final int UNSCRAMBLER = 3;
	public static final int TURRET_CONVEYER = 12;
	public static final int TURRET_LOWER = 6;
	public static final int TURRET_UPPER = 7;
	
	public static final int TURRET_ENCUPPER_A = 3;
	public static final int TURRET_ENCUPPER_B = 4;
	public static final int TURRET_ENCLOWER_A = 5;
	public static final int TURRET_ENCLOWER_B = 6;
	
	public static final int COMPRESSOR = 0;
	// gear holder pistons
	public static final int GEAR_RAMP_CHONE = 2;
	public static final int GEAR_RAMP_CHTWO = 3;
	public static final int GEAR_GRASP_CHONE = 4;
	public static final int GEAR_GRASP_CHTWO = 5;

	public static final int GEAR_GRASP_S2_FORWARD = 1;
	public static final int GEAR_GRASP_S2_BACKWARD = 0;

	// conveyer
	public static final int CONVEYER_M = 14;
	// single piston gear holder
	public static final int MONO_GEAR_FORWARD = 10;
	public static final int MONO_GEAR_REVERSE = 11;

	public static final long TALON_RESET_DELAY = 100;
}
